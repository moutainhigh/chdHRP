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
    	
        loadDict();
        
        loadForm();

        $("#asset_type_code").ligerTextBox({disabled: true});
        
    });  
     
    function save(){
    	
    	//var cost_item = liger.get("cost_item_id").getValue();
    	
        var formPara={
	
        id:$("#id").val(),
        		
        asset_type_code:liger.get("asset_type_code").getValue(),
        
        cost_item_code:liger.get("cost_item_code").getValue()
        
        };
        ajaxJsonObjectByUrl("updateCostIassetCostRela.do",formPara,function(responseData){
            
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
   
    function saveCostIassetCostRela(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){

			autocomplete("#asset_type_code","../queryIassetTypeArrt.do?isCheck=false","id","text",true,true);
        
         	liger.get("asset_type_code").setValue('${asset_type_code}');
         	
	     	liger.get("asset_type_code").setText('${asset_type_name}');
	     
	     
		 	autocomplete("#cost_item_code","../queryItemDictCodeLast.do?isCheck=false","id","text",true,true);
		 	
		 	liger.get("cost_item_code").setValue('${cost_item_code}');	
		 	
	    	liger.get("cost_item_code").setText('${cost_item_name}');	

     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   		<input name="id" type="hidden" id="id" value="${id}"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         	<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">无形分类：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="asset_type_code" type="text" id="asset_type_code" ltype="text"  validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>  
        </table>
    </form>
    </body>
</html>
