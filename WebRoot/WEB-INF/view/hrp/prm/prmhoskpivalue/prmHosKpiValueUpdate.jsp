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
        acc_year:$("#acc_year").val(),
        acc_month:$("#acc_month").val(),
        goal_code:$("#goal_code").val(),
        kpi_code:$("#kpi_code").val(),
        check_hos_id:$("#check_hos_id").val(),
        kpi_value:$("#kpi_value").val(),
        is_audit:$("#is_audit").val(),
        user_code:$("#user_code").val(),
        audit_date:$("#audit_date").val()
        };
        ajaxJsonObjectByUrl("updatePrmHosKpiValue.do",formPara,function(responseData){
            
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
   
    function savePrmHosKpiValue(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text"  value="${acc_year}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" ltype="text"  value="${acc_month}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标编码：</td>
                <td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text"  value="${goal_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码：</td>
                <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text"  value="${kpi_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核医院：</td>
                <td align="left" class="l-table-edit-td"><input name="check_hos_id" type="text" id="check_hos_id" ltype="text"  value="${check_hos_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标值：</td>
                <td align="left" class="l-table-edit-td"><input name="kpi_value" type="text" id="kpi_value" ltype="text"  value="${kpi_value}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">1:审核 0:未审核：</td>
                <td align="left" class="l-table-edit-td"><input name="is_audit" type="text" id="is_audit" ltype="text"  value="${is_audit}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
                <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text"  value="${user_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">YYYY-MM-DD：</td>
                <td align="left" class="l-table-edit-td"><input name="audit_date" type="text" id="audit_date" ltype="text"  value="${audit_date}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
