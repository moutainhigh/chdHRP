<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 if(liger.get("type_code").getValue()=="" && liger.get("natur_code").getValue()=="" && liger.get("out_code").getValue()==""){
    		 $.ligerDialog.error('请选择其中一个');
         	
         	return;
         	
    	 }else{
    		 var formPara={

    		           dept_id:'${dept_ids}',
    		            
    		           type_code:liger.get("type_code").getValue(),
    		            
    		           natur_code:liger.get("natur_code").getValue()==''?"":liger.get("natur_code").getValue(),
    		            
    		           out_code:liger.get("out_code").getValue()==''?"":liger.get("out_code").getValue(),
    		            
    		           emp_id:"",
    		            
    		           is_manager:"0",
    		            
    		           is_stock:"0"
    		            
    		         };
    		        ajaxJsonObjectByUrl("../accdeptattr/batchUpdateAccDeptAttr.do",formPara,function(responseData){
    		            
    		            if(responseData.state=="true"){
    						 //$("input[name='type_code']").val('');
    						 //$("input[name='natur_code']").val('');
    						 //$("input[name='out_code']").val('');
    						 //$("input[name='emp_code']").val('');
    						 //$("input[name='is_manager']").val('');
    						 //$("input[name='is_stock']").val('');
    		                parentFrameUse().query();
    		            }
    		        });
    		 
    	 }
        
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
   
    function saveAccDeptAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    		
            autocomplete("#type_code","../../sys/queryDeptType.do?isCheck=false","id","text",true,true);
           
            autocomplete("#natur_code","../../sys/queryDeptNatur.do?isCheck=false","id","text",true,true);
            
            autocomplete("#out_code","../../sys/queryAccDeptOut.do?isCheck=false","id","text",true,true);
            
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
       		 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">部门类型：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text"  id="type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">部门性质：</td>
                <td align="left" class="l-table-edit-td"><input name="natur_code" type="text"  id="natur_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">支出性质：</td>
                <td align="left" class="l-table-edit-td"><input name="out_code" type="text"  id="out_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            
        </table>
    </form>
   
    </body>
</html>
