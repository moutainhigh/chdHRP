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
	 	
	    loadForm();
	    
	 });  

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
	
	function  save(){

	   var formPara={
	   	
	    };
	   
	   ajaxJsonObjectByUrl("extendCostIassetCostRela.do",formPara,function(responseData){
	       
	       if(responseData.state=="true"){
				
	    	   $("input[name='year_month']").val('');
				 
	           parent.query();
	       }
	   });
	}
	
	//继承数据
	function saveCostWageCostRela(){
		 if($("form").valid()){
	            save();
	     }
	}     
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
	    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>源年月：</td>
	            <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" class="Wdate"  ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" validate="{required:true}"/></td>
	        </tr>
	        
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>目标年月：</td>
	            <td align="left" class="l-table-edit-td"><input name="end_year_month" type="text" id="end_year_month" class="Wdate"  ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" validate="{required:true}"/></td>
	        </tr> 
	    </table>
    </form>
</body>
</html>
