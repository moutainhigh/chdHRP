<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
    });  
     
    function save(){
        if($("form").valid()){
	        var formPara={
		        instru_type_id : $("#instru_type_id").val(),
		        instru_type_code : $("#instru_type_code").val(),
		        instru_type_name : $("#instru_type_name").val(),
		        super_code : liger.get("super_code").getValue(),
		        is_last : liger.get("is_last").getValue(),
				is_stop : liger.get("is_stop").getValue(),
		        group_id : $("#group_id").val(),
		        hos_id : $("#hos_id").val(),
		        copy_code : $("#copy_code").val()
	        };
			//alert(JSON.stringify(formPara));
	        ajaxJsonObjectByUrl("saveMatInstruType.do",formPara,function(responseData){
	            if(responseData.state=="true"){
	            	parent.loadTree();
	                parent.query(responseData.instru_type_id);
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
        liger.get("super_code").setValue("${matInstruType.super_code}");
		liger.get("super_code").setText("${matInstruType.super_code}" + " " + "${matInstruType.super_name}");
    	liger.get("is_last").setValue("${matInstruType.is_last}");
        if("${matInstruType.is_last}" == 1){
    		liger.get("is_last").setText("是");
        }else{
    		liger.get("is_last").setText("否");
        }
		
        autocomplete("#is_stop", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "${matInstruType.is_stop}");
    	
    	$("#instru_type_code").ligerTextBox({width:160});
    	$("#instru_type_name").ligerTextBox({width:160});
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
						<span style="color:red">*</span>医疗器械分类编码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="instru_type_id" type="hidden" id="instru_type_id" ltype="text" value="${matInstruType.instru_type_id}" />
						<input name="instru_type_code" type="text" id="instru_type_code" ltype="text" value="${matInstruType.instru_type_code}" required="true" validate="{required:true,maxlength:40}" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<span style="color:red">*</span>医疗器械分类名称：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="instru_type_name" type="text" id="instru_type_name" ltype="text" value="${matInstruType.instru_type_name}" required="true" validate="{required:true,maxlength:80}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						上级编码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="super_code" disabled="disabled" type="text" id="super_code" ltype="text" value="${matInstruType.super_code}" required="false" validate="{required:false}" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 0px;">
						<span style="color:red">*</span>是否末级：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="is_last" disabled="disabled" type="text" id="is_last" ltype="text" value="${matInstruType.is_last}" required="true" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<span style="color:red">*</span>是否停用：
					</td>
					<td align="left"  class="l-table-edit-td" >
						<input name="is_stop" type="text" id="is_stop" ltype="text" value="${matInstruType.is_stop}" required="true" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						拼音码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="spell_code" disabled="disabled" type="text" id="spell_code" ltype="text" value="${matInstruType.spell_code}" required="false" validate="{required:false,maxlength:40}" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						五笔码：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="wbx_code" disabled="disabled" type="text" id="wbx_code" ltype="text" value="${matInstruType.wbx_code}" required="false" validate="{required:false,maxlength:40}" />
					</td>
				</tr>
				<tr>
					<td style="display: none">
						<input name="group_id" type="hidden" id="group_id" ltype="text" value="${matInstruType.group_id}" />
						<input name="hos_id" type="hidden" id="hos_id" ltype="text" value="${matInstruType.hos_id}" />
						<input name="copy_code" type="hidden" id="copy_code" ltype="text" value="${matInstruType.copy_code}" />
					</td>
				</tr>
			</table>
		</form>
    </body>
</html>