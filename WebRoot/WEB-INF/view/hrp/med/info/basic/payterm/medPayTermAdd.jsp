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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
            
           pay_term_id:$("#pay_term_id").val(),
            
           pay_term_code:$("#pay_term_code").val(),
            
           pay_term_name:$("#pay_term_name").val(),
            
            
            
           is_stop:$("#is_stop").val(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("addMedPayTerm.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='pay_term_id']").val('');
				 $("input[name='pay_term_code']").val('');
				 $("input[name='pay_term_name']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='note']").val('');
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
   
    function saveMedPayTerm(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
           
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款条件ID：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_term_id" type="text" id="pay_term_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款条件编码：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_term_code" type="text" id="pay_term_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款条件名称：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_term_name" type="text" id="pay_term_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
