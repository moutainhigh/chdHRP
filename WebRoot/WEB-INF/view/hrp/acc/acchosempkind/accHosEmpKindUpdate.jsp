<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        $("#kind_code").ligerTextBox({ disabled: true});
        
        if(${is_stop}==true){
       		liger.get("is_stop").setValue('1');
       		liger.get("is_stop").setText("是");	
       		
       	}else{
       		liger.get("is_stop").setValue('0');
       		liger.get("is_stop").setText("否");	
       		
       	}
        
    });  
     
    function save(){
    	var formPara={
        		
    	           kind_code:$("#kind_code").val(),
    	            
    	           kind_name:$("#kind_name").val(),
    	           
    	           spell_code:$("#spell_code").val(),
    	           
    	           wbx_code:$("#wbx_code").val(),
    	           
    	           is_stop:$("#is_stop").val(),
    	            
    	           note:$("#note").val(),
    	           
    	           is_auto:liger.get("is_auto").getValue()
    	            
    	         };
    	        
        ajaxJsonObjectByUrl("updateAccHosEmpKind.do",formPara,function(responseData){
            
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
   
    function saveHosEmpKind(){
    	
            save();
            
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" value="${kind_code }" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类名称：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_name" type="text" id="kind_name" ltype="text" value="${kind_name }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">拼音码：</td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text" id="spell_code" ltype="text" value="${spell_code }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
          
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">五笔码：</td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text" id="wbx_code" ltype="text" value="${wbx_code }" validate="{required:true,maxlength:20}"   /></td>
                <td align="left"><input type="checkbox" id="is_auto" />重新生成</td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
					<option value="0">否</option>
					<option value="1">是</option>
				</select></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" value="${note }"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
    </body>
</html>
