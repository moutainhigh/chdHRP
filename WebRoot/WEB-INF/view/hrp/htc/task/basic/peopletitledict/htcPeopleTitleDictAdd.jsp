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
         loadDict()//加载下拉框
         loadForm();
        
     });  
     function  save(){
        var formPara={
           title_code:$("#title_code").val(),
           title_name:$("#title_name").val(),
           title_desc:$("#title_desc").val(),
           is_stop : liger.get("is_stop").getValue()
         };
        ajaxJsonObjectByUrl("addHtcPeopleTitleDict.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='title_code']").val('');
				 $("input[name='title_name']").val('');
				 $("input[name='title_desc']").val('');
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
		 function savePeopleTitleDict(){
		     if($("form").valid()){
		         save();
		     }
		}
    function loadDict(){
            //字典下拉框
    	autocomplete("#is_stop", "../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,'0');
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职称编码：</td>
                <td align="left" class="l-table-edit-td"><input name="title_code" type="text" id="title_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职称名称：</td>
                <td align="left" class="l-table-edit-td"><input name="title_name" type="text" id="title_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
				<td align="right" class="l-table-edit-td"style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"/>
				<td align="left"></td>
			</tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">描述：</td>
                <td align="left" class="l-table-edit-td" colspan="2"> 
                <textarea cols="100" rows="4" class="l-textarea" id="title_desc" name="title_desc" style="width:300px;resize:none" validate="{maxlength:200}"></textarea>
                </td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
