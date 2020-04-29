<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script type="text/javascript">
     var dataFormat;
     $(function (){
    	loadDict()//加载下拉框
        loadForm();
     });  
     
     function  save(){
         
         if(liger.get("is_stop").getValue()==null || liger.get("is_stop").getValue() ==""){
        	    $.ligerDialog.error('是否停用不能为空')
                 return false
             }
         
        var formPara={
            recipe_type_code:$("#recipe_type_code").val(),
            recipe_type_name:$("#recipe_type_name").val(),
            is_stop:liger.get("is_stop").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addHtcgRecipeType.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='recipe_type_code']").val('');
				 $("input[name='recipe_type_name']").val('');
				 $("input[name='is_stop']").val('');
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
   
    function saveRecipeType(){
        if($("form").valid()){
            save();
        }
   } 

    function loadDict(){


 	     autocomplete("#is_stop", "../../base/queryHtcgYearOrNo.do?isCheck=false", "id", "text", true, true);
 	     
      } 
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医嘱时效分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="recipe_type_code" type="text" id="recipe_type_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医嘱时效分类名称：</td>
                <td align="left" class="l-table-edit-td"><input name="recipe_type_name" type="text" id="recipe_type_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
