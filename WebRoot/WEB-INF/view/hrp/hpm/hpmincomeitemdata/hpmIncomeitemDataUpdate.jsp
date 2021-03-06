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
         $("#income_item_code").ligerTextBox({ disabled:true  });
         $("#dept_id").ligerTextBox({ disabled:true  });
     });  
     
     function  save(){
        var formPara={
            
            
           acct_year:$("#acct_year").val(),
            
           acct_month:$("#acct_month").val(),
            
           dept_id:liger.get("dept_id").getValue(),
           
           dept_no:'${dept_no}',
            
           income_item_code:liger.get("income_item_code").getValue(),
            
           order_money:$("#order_money").val(),
            
           order_ret_money:$("#order_ret_money").val(),
            
           perform_money:$("#perform_money").val(),
            
           perform_ret_money:$("#perform_ret_money").val(),
            
           income_tot_money:$("#income_tot_money").val()
            
         };
        
        ajaxJsonObjectByUrl("updateHpmIncomeitemData.do",formPara,function(responseData){
            
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
   
    function saveIncomeitemData(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#income_item_code","../../hpm/queryAphiIncomeItem.do?isCheck=false","id","text",true,true);
    	
    	liger.get("dept_id").setValue("${dept_id}");
    	liger.get("dept_id").setText("${dept_name}");
    	
    	liger.get("income_item_code").setValue("${income_item_code}");
    	liger.get("income_item_code").setText("${income_item_name}");
     } 
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年度：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_year" class="Wdate" type="text" id="acct_year" disabled="disabled" value="${acct_year }"
					ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算月份：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_month" class="Wdate" type="text" id="acct_month" disabled="disabled" value="${acct_month }"
					ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收入项目：</td>
				<td align="left" class="l-table-edit-td"><input name="income_item_code" type="text" id="income_item_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开单收入：</td>
				<td align="left" class="l-table-edit-td"><input name="order_money" type="text" id="order_money" ltype="text" value="${order_money }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开单计提：</td>
				<td align="left" class="l-table-edit-td"><input name="order_ret_money" type="text" id="order_ret_money" ltype="text" value="${order_ret_money }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">执行收入：</td>
				<td align="left" class="l-table-edit-td"><input name="perform_money" type="text" id="perform_money" ltype="text" value="${perform_money }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">执行计提：</td>
				<td align="left" class="l-table-edit-td"><input name="perform_ret_money" type="text" id="perform_ret_money" ltype="text" value="${perform_ret_money }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收入计提合计：</td>
				<td align="left" class="l-table-edit-td"><input name="income_tot_money" type="text" id="income_tot_money" ltype="text" value="${income_tot_money }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
