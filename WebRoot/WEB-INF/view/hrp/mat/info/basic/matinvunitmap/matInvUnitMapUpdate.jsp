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
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        inv_id:$("#inv_id").val(),
        pack_code:$("#pack_code").val(),
        unit_code:$("#unit_code").val(),
        map_amount:$("#map_amount").val()
        };
        ajaxJsonObjectByUrl("updateMatInvUnitMap.do",formPara,function(responseData){
            
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
   
    function saveMatInvUnitMap(){
        if($("form").valid()){
            save();
        }
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
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资材料ID：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_id" type="text" id="inv_id" ltype="text" value="${inv_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">包装单位：</td>
            <td align="left" class="l-table-edit-td"><input name="pack_code" type="text" id="pack_code" ltype="text" value="${pack_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量单位：</td>
            <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" value="${unit_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">转换数量：</td>
            <td align="left" class="l-table-edit-td"><input name="map_amount" type="text" id="map_amount" ltype="text" value="${map_amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
