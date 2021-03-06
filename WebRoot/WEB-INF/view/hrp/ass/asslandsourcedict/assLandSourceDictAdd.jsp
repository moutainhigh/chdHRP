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
         $("#land_source_code").ligerTextBox({width:160});
         $("#is_stop").ligerTextBox({width:160});
         $("#land_source_name").ligerTextBox({width:300});
     });  
     
     function  save(){
        var formPara={

           land_source_code:$("#land_source_code").val(),
            
           land_source_name:$("#land_source_name").val(),
           is_stop:liger.get("is_stop").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addAssLandSourceDict.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='land_source_code']").val('');
				 $("input[name='land_source_name']").val('');
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
   
    function saveAssLandSourceDict(){
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
				cancelable:true
		})
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top: 45px">土地来源编码<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td" style="padding-top: 45px;"><input name="land_source_code" type="text" id="land_source_code" ltype="text" validate="{required:true,maxlength:30}" /></td>
                <td align="left" style="padding-top: 45px;"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top: 45px">土地来源名称<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td" style="padding-top: 45px;"><input name="land_source_name" type="text" id="land_source_name" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left" style="padding-top: 45px;"></td>
            </tr>  
            <tr >
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top: 30px;">是&nbsp&nbsp否&nbsp&nbsp停&nbsp用&nbsp<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td" style="padding-top: 30px;">
                <!-- <select name="is_stop" id="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
			    </select> -->
			    <input name="is_stop" type="text" id="is_stop" validate="{required:true,maxlength:30}" />
                <td align="left" style="padding-top: 30px;"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
