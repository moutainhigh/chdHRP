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
         YearMonth();
     });  
     
     function  save(){
        var formPara={
            
            
           acct_year:$("#acct_year").val(),
            
           acct_month:$("#acct_month").val(),
            
           dept_id:liger.get("dept_id").getValue().split(",")[0],
           
           dept_no:liger.get("dept_id").getValue().split(",")[1],
            
           f_score:$("#f_score").val(),
           
           c_score:$("#c_score").val(),
           
           p_score:$("#p_score").val(),
           
           l_score:$("#l_score").val(),
           
           root_score:$("#root_score").val()
            
         };
        
        ajaxJsonObjectByUrl("addHpmDeptScoreData.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='acct_year']").val('');
				 $("input[name='acct_month']").val('');
				 $("input[name='dept_id']").val('');
				 $("input[name='f_score']").val('');
				 $("input[name='c_score']").val('');
				 $("input[name='p_score']").val('');
				 $("input[name='l_score']").val('');
				 $("input[name='root_score']").val('');
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
    
    function YearMonth(){
		var acct_yearm = parent.$("#acct_yearm").val();
		var acct_year = acct_yearm.substring(0,4);
		var acct_month = acct_yearm.substring(4);
		$("#acct_year").val(acct_year);
		$("#acct_month").val(acct_month);
	}
    
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年度：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_year" class="Wdate" type="text" id="acct_year" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算月份：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_month" class="Wdate" type="text" id="acct_month" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">财务收益：</td>
				<td align="left" class="l-table-edit-td"><input name="f_score" type="text" id="f_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">客户关系：</td>
				<td align="left" class="l-table-edit-td"><input name="c_score" type="text" id="c_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">内部流程：</td>
				<td align="left" class="l-table-edit-td"><input name="p_score" type="text" id="p_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">学习成长：</td>
				<td align="left" class="l-table-edit-td"><input name="l_score" type="text" id="l_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">综合得分：</td>
				<td align="left" class="l-table-edit-td"><input name="root_score" type="text" id="root_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
