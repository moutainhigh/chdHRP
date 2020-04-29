<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
     
     function  save(){
         
        var formPara={
          
        		proj_dept_code:$("#proj_dept_code").val(),
            
        		proj_dept_name:$("#proj_dept_name").val(),
            
        		natur_code:liger.get("natur_code").getValue(),
            
        		is_stop:liger.get("is_stop").getValue(),
            
        		is_last:liger.get("is_last").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addHtcProDept.do",formPara,function(responseData){
			if(responseData.state=="true"){
				$("input[name='proj_dept_code']").val('');
				$("input[name='proj_dept_name']").val('');
				$("input[name='natur_code']").val('');
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
   
    function saveDeptDict(){
    	
        if($("form").valid()){
        	
            save();
            
        }
   }

    function loadDict(){
        
	    	autocomplete("#is_last", "../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",false,"0");
	    	
			autocomplete("#is_stop", "../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",false,"0");
			
			autocomplete("#natur_code","../../base/queryHtcDeptNatur.do?isCheck=false","id","text",true,true);
			
        }
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">核算科室编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="proj_dept_code" type="text" id="proj_dept_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">核算科室名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="proj_dept_name" type="text" id="proj_dept_name" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">科室性质：</td>
				<td align="left" class="l-table-edit-td"><input
					name="natur_code" type="text" id="natur_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否末级：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true}"/>
				</td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
