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
<script type="text/javascript">

    var dataFormat;
    
    $(function (){
   
    	loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        		asset_code:$("#asset_code").val(),
                
                asset_name:$("#asset_name").val(),
                 
                asset_type_code:liger.get("asset_type_code").getValue(),
                 
                prim_value:$("#prim_value").val(),
                 
                start_date:$("#start_date").val(),
                 
                end_date:$("#end_date").val(),
                 
                dep_year:$("#dep_year").val()
        };
        ajaxJsonObjectByUrl("updateHtcIassetDict.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
 
                parent.query();
                
            }
        });
    }
     
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
    }       
   
    function saveIassetDict(){
        if($("form").valid()){
            save();
        }
    }

    function loadDict(){
        
    	autocomplete("#asset_type_code","../../../info/base/queryHtcIassetTypeDict.do?isCheck=false","id","text",true,true);
    	liger.get("asset_type_code").setValue('${asset_type_code}');
 		liger.get("asset_type_code").setText('${asset_type_name}');	
    	$("#asset_code").ligerTextBox({disabled:true});
     }
    </script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin:  0px  0px  0px  20px">
			<tr>
			    <td align="right" class="l-table-edit-td"style="padding-left: 20px;">卡片号：</td>
				<td align="left" class="l-table-edit-td"><input name="asset_code" type="text" id="asset_code" ltype="text" value='${asset_code}' validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产名称：</td>
				<td align="left" class="l-table-edit-td"><input name="asset_name" type="text" id="asset_name" ltype="text" value='${asset_name}' validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产类型编码：</td>
				<td align="left" class="l-table-edit-td"><input name="asset_type_code" type="text" id="asset_type_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产原值：</td>
				<td align="left" class="l-table-edit-td"><input name="prim_value" type="text" id="prim_value" ltype="text"  value='${prim_value}'/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">启用年月：</td>
				<td align="left" class="l-table-edit-td"><input name="start_date" class="Wdate" type="text" id="start_date" ltype="text" value='${start_date}' onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">结束年月：</td>
				<td align="left" class="l-table-edit-td"><input name="end_date" type="text" class="Wdate" id="end_date" ltype="text"  value='${end_date}' onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">折旧年限：</td>
				<td align="left" class="l-table-edit-td"><input name="dep_year" type="text" id="dep_year" ltype="text" value='${dep_year}' /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
