<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
           acct_year:$("#acct_year").val(),
            
           acct_month:$("#acct_month").val(),
            
           dept_id:liger.get("dept_id").getValue().split(",")[0],
           
           dept_no:liger.get("dept_id").getValue().split(",")[1],
            
           dept_duty_amount:$("#dept_duty_amount").val()
            
         };
        
        ajaxJsonObjectByUrl("addHpmDeptDutyData.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='acct_year']").val('');
				 $("input[name='acct_month']").val('');
				 $("input[name='dept_id']").val('');
				 $("input[name='dept_duty_amount']").val('');
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
   
    function saveEmpTargetData(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true); 
     } 
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 20px;">核算年度：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="acct_year" class="Wdate" type="text" id="acct_year" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left" style="padding-top: 20px;"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 20px;">核算月份：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="acct_month" class="Wdate" type="text" id="acct_month" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left" style="padding-top: 20px;"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="dept_id" type="text" id="dept_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left" style="padding-top: 20px;"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding_top: 20px;">岗位系数：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="dept_duty_amount" type="text" id="dept_duty_amount" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left" style="padding-top: 20px;"></td>
			</tr>

		</table>
	</form>

</body>
</html>
