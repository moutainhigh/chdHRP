<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
        		charge_kind_id:'',
            
           charge_kind_code:$("#charge_kind_code").val(),
            
           income_type_id:liger.get("income_type_id").getValue(),
            
           charge_kind_name:$("#charge_kind_name").val(),
            
           income_item_id_in:liger.get("income_item_id_in").getValue(),
            
           income_item_id_out:liger.get("income_item_id_out").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addCostChargeKindArrt.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
				
				
				 $("input[name='charge_kind_code']").val('');
				 $("input[name='income_type_id']").val('');
				 $("input[name='charge_kind_name']").val('');
				 $("input[name='income_item_id_in']").val('');
				 $("input[name='income_item_id_out']").val('');
				
				
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
   
    function saveCostChargeKindArrt(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#income_type_id","../queryIncomeType.do?isCheck=false","id","text",true,true);
		 autocomplete("#income_item_id_in","../queryIncomeItemArrt.do?isCheck=false","id","text",true,true);
		 autocomplete("#income_item_id_out","../queryIncomeItemArrt.do?isCheck=false","id","text",true,true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别编码：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_kind_code" type="text" id="charge_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别名称：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_kind_name" type="text" id="charge_kind_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入类别：</td>
                <td align="left" class="l-table-edit-td"><input name="income_type_id" type="text" id="income_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入项目_门诊：</td>
                <td align="left" class="l-table-edit-td"><input name="income_item_id_in" type="text" id="income_item_id_in" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入项目_住院：</td>
                <td align="left" class="l-table-edit-td"><input name="income_item_id_out" type="text" id="income_item_id_out" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
            

        </table>
    </form>
   
    </body>
</html>
