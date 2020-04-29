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
            
           acc_year:$("#year_month").val().substring(0,4),
                
           acc_month:$("#year_month").val().substring(4,6),
            
           dept_id:liger.get("dept_code").getValue().split(".")[0],
           
           dept_no:liger.get("dept_code").getValue().split(".")[1],
            
           emp_id:liger.get("emp_code").getValue().split(".")[0],
           
           emp_no:liger.get("emp_code").getValue().split(".")[1],
            
           doctor_num:$("#doctor_num").val()
            
         };
        ajaxJsonObjectByUrl("addCostDoctorWork.do",formPara,function(responseData){
            if(responseData.state=="true"){
            	 $("input[name='dept_code']").val('');
				 $("input[name='emp_code']").val('');
				 $("input[name='doctor_num']").val('0');
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
   
    function saveCostDoctorWork(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        
    	autocomplete("#dept_code","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true);

    	autocomplete("#emp_code","../queryCostEmpDict.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#patient_type_code","../queryCostPatientTypeDict.do?isCheck=false","id","text",true,true);
           
     } 
    </script>
  </head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form" method="post"  id="form" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工作量：</td>
                <td align="left" class="l-table-edit-td"><input name="doctor_num" type="text" id="doctor_num" ltype="text" value="0" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
