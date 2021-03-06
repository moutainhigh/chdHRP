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
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
           equi_usage_code:$("#equi_usage_code").val(),
            
           equi_usage_name:$("#equi_usage_name").val(),
            
           is_stop:liger.get("is_stop").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addAssFileTypeDict.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='equi_usage_code']").val('');
				 $("input[name='equi_usage_name']").val('');
			
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
   
    function saveAssFileTypeDict(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	$('#is_stop').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
	});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文档类别编码<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td"><input name="equi_usage_code" type="text" id="equi_usage_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文档类别名称<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td"><input name="equi_usage_name" type="text" id="equi_usage_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是&nbsp否&nbsp停&nbsp用&nbsp<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select> -->
                	 <input name="is_stop" type="text" id="is_stop" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
