<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        note:$("#note").val("${matType.note}");
    });  
     
    function save(){
        if($("form").valid()){
	        var formPara={
		        mat_type_id : $("#mat_type_id").val(),
		        mat_type_code : $("#mat_type_code").val(),
		        mat_type_name : $("#mat_type_name").val(),
		        super_code : liger.get("super_code").getValue(),
		        is_last : liger.get("is_last").getValue(),
				is_stop : liger.get("is_stop").getValue(),
				is_auto_exp : liger.get("is_auto_exp").getValue(),
		        note : $("#note").val(),
		        group_id : $("#group_id").val(),
		        hos_id : $("#hos_id").val(),
		        copy_code : $("#copy_code").val(),
		        is_save_change : $("#is_save_change").attr("checked") ? 1 : 0
	        };
			//alert(JSON.stringify(formPara));
	        ajaxJsonObjectByUrl("saveBudgTypeDict.do?isCheck=false",formPara,function(responseData){
	            if(responseData.state=="true"){
	            	parent.loadTree();
	                parent.query(responseData.mat_type_id);
	            }
	        });
        }
    }

    function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element){
	             if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
		$("form").ligerForm();
    }       
   
    function loadDict(){
		//字典下拉框
		$("#super_code").ligerComboBox({disabled:true,cancelable: false});
        $("#is_last").ligerComboBox({disabled:true,cancelable: false});
        liger.get("super_code").setValue("${matType.super_code}");
		liger.get("super_code").setText("${matType.super_code}" + " " + "${matType.super_name}");
    	liger.get("is_last").setValue("${matType.is_last}");
        if("${matType.is_last}" == 1){
    		liger.get("is_last").setText("是");
        }else{
    		liger.get("is_last").setText("否");
        }
    	//autocomplete("#super_code", "../../../queryMatType.do?isCheck=false", "id", "text", true, true, "", false);	
    	//autocomplete("#is_last", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "1");
    	/* autocomplete("#is_stop", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "${matType.is_stop}");
    	autocomplete("#is_auto_exp", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "${matType.is_auto_exp}");
    	 */
    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true, "", false, "${matType.is_stop}");
    	autoCompleteByData("#is_auto_exp", yes_or_no.Rows, "id", "text", true, true, "", false, "${matType.is_auto_exp}");
    	$("#mat_type_code").ligerTextBox({width:160});
    	$("#mat_type_name").ligerTextBox({width:160});
    	$("#spell_code").ligerTextBox({width:160,disabled:true});
    	$("#wbx_code").ligerTextBox({width:160,disabled:true});
	}  
    </script>
  
  </head>
  
	<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
		<form name="form1" method="post"  id="form1" >
			<table cellpadding="0" cellspacing="0" style="width:100%" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<span style="color:red">*</span>物资类别编码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="mat_type_id" type="hidden" id="mat_type_id" ltype="text" value="${matType.mat_type_id}" />
						<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" value="${matType.mat_type_code}" required="true" validate="{required:true,maxlength:40}" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<span style="color:red">*</span>物资类别名称：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="mat_type_name" type="text" id="mat_type_name" ltype="text" value="${matType.mat_type_name}" required="true" validate="{required:true,maxlength:80}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						上级编码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="super_code" disabled="disabled" type="text" id="super_code" ltype="text" value="${matType.super_code}" required="false" validate="{required:false}" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 0px;">
						<span style="color:red">*</span>是否末级：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="is_last" disabled="disabled" type="text" id="is_last" ltype="text" value="${matType.is_last}" required="true" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<span style="color:red">*</span>是否停用：
					</td>
					<td align="left"  class="l-table-edit-td" >
						<input name="is_stop" type="text" id="is_stop" ltype="text" value="${matType.is_stop}" required="true" validate="{required:true}" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 0px;">
						<span style="color:red">*</span>是否自动有效期：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="is_auto_exp" type="text" id="is_auto_exp" ltype="text" value="${matType.is_auto_exp}" required="true" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						拼音码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="spell_code" disabled="disabled" type="text" id="spell_code" ltype="text" value="${matType.spell_code}" required="false" validate="{required:false,maxlength:40}" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						五笔码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="wbx_code" disabled="disabled" type="text" id="wbx_code" ltype="text" value="${matType.wbx_code}" required="false" validate="{required:false,maxlength:40}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						备注：
					</td>
					<td align="left" colspan="3" class="l-table-edit-td">
						<textarea name="note" id="note" rows="3" cols="100%"></textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2" class="l-table-edit-td" style="padding-left: 20px;">
						<input type="checkbox" id="is_save_change" />保留历史变更记录
					</td>
				</tr>
				<tr>
					<td style="display: none">
						<input name="group_id" type="hidden" id="group_id" ltype="text" value="${matType.group_id}" />
						<input name="hos_id" type="hidden" id="hos_id" ltype="text" value="${matType.hos_id}" />
						<input name="copy_code" type="hidden" id="copy_code" ltype="text" value="${matType.copy_code}" />
					</td>
				</tr>
			</table>
		</form>
    </body>
</html>