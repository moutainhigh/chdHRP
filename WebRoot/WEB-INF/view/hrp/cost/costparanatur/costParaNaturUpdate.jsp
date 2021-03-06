<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
        loadDict();
        loadForm();
        $("#para_code").ligerTextBox({ disabled: true});
        
    });  
     
    function save(){
        var formPara={
        para_code:$("#para_code").val(),
        para_name:$("#para_name").val(),
        para_value:liger.get("para_value").getValue(),
        bill_type:liger.get("bill_type").getValue(),
        note:$("#note").val()
        };
        ajaxJsonObjectByUrl("updateCostParaNatur.do",formPara,function(responseData){
            
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
   
    function saveCostParaNatur(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	//字典下拉框
		autocomplete("#para_value", "../queryDeptParaDict.do?isCheck=false",
				"id", "text", true, true, '', false, '${para_value}');
            
		//字典下拉框
		autoCompleteByData("#bill_type", cost_bill_type_state.Rows, "id",
				"text", true, true, "", false, '${bill_type}');
        
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊性质编码：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" value="${para_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊性质名称：</td>
            <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" value="${para_name}" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
             </tr> 
              <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">默认分摊参数：</td>
            <td align="left" class="l-table-edit-td"><input name="para_value" type="text" id="para_value" ltype="text" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊级次：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_type" type="text" id="bill_type" ltype="text" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" value="${note}" validate="{maxlength:100}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
