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
     });  
     
     function  save(){
        var formPara={
            
            
           acct_year:$("#acct_year").val(),
            
           acct_month:$("#acct_month").val(),
            
           income_item_code:liger.get("income_item_code").getValue(),
            
           income_money:$("#income_money").val(),
            
           income_ret_money:$("#income_ret_money").val()
            
         };
        
        ajaxJsonObjectByUrl("updateHpmIncomeitemIncreaseData.do",formPara,function(responseData){
            
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
   
    function saveIncomeitemIncreaseData(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#income_item_code","../queryIncomeItemSeq.do?isCheck=false","id","text",true,true);
            
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
				<td align="left" class="l-table-edit-td"><input name="acct_year" class="Wdate" type="text" id="acct_year" ltype="text" disabled="disabled"
					value="${acct_year }" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算月份：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_month" class="Wdate" type="text" id="acct_month" ltype="text" disabled="disabled"
					value="${acct_month }" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收入项目：</td>
				<td align="left" class="l-table-edit-td"><input name="income_item_code" type="text" id="income_item_code" disabled="disabled" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">医疗收入：</td>
				<td align="left" class="l-table-edit-td"><input name="income_money" type="text" id="income_money" value="${income_money }" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计提收入：</td>
				<td align="left" class="l-table-edit-td"><input name="income_ret_money" type="text" id="income_ret_money" value="${income_ret_money }" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
