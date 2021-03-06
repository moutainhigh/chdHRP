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
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
    });  
     
    function save(){
        var formPara={
        		identity_code:$("#identity_code").val(),
        		identity_name:$("#identity_name").val(),
        		is_stop:liger.get("is_stop").getValue()
        };
        ajaxJsonObjectByUrl("updateHtcgIdentityType.do",formPara,function(responseData){
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
   
    function saveIdentityType(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	 $("#identity_code").ligerTextBox({disabled:true});
    	 autocomplete("#is_stop", "../../base/queryHtcgYearOrNo.do?isCheck=false", "id", "text", true, true,"",false,"${is_stop}");
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医保类型编码：</td>
                <td align="left" class="l-table-edit-td"><input name="identity_code" type="text" id="identity_code" ltype="text"  value="${identity_code }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医保类型名称：</td>
                <td align="left" class="l-table-edit-td"><input name="identity_name" type="text" id="identity_name" ltype="text"  value="${identity_name }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"   validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
