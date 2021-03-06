<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
   <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        charge_stan_code:$("#charge_stan_code").val(),
        charge_stan_name:$("#charge_stan_name").val(),
        charge_stan_nature:liger.get("charge_stan_nature").getValue(),
        charge_stan_describe:$("#charge_stan_describe").val(),
        unit:$("#unit").val(),
        data_precision:$("#data_precision").val(),
        is_stop:liger.get("is_stop").getValue()
        };
        ajaxJsonObjectByUrl("updateBudgChargeStanDict.do?isCheck=false",formPara,function(responseData){
            
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
     //$("form").ligerForm();
    }       
   
    function saveBudgChargeStanDict(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	 autocomplete("#charge_stan_nature","../../../queryBudgCharStanNature.do?isCheck=false","id","text",true,true);
 			liger.get("charge_stan_nature").setValue('${charge_stan_nature}');
 			liger.get("charge_stan_nature").setText('${charge_stan_nature_name}');
         autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_stop}");
     	
         $("#charge_stan_code").ligerTextBox({width:180,disabled:true});
         $("#charge_stan_name").ligerTextBox({width:180});
         $("#charge_stan_nature").ligerTextBox({width:180});
         $("#data_precision").ligerTextBox({width:180});
         $("#charge_stan_describe").ligerTextBox({width:555});
     	 $("#unit").ligerTextBox({width:180}); 
         $("#is_stop").ligerTextBox({width:180});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   	<form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>费用标准编码<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="charge_stan_code" type="text" id="charge_stan_code" value="${charge_stan_code}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>费用标准名称<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="charge_stan_name" type="text" id="charge_stan_name" value="${charge_stan_name}" ltype="text" validate="{required:true,maxlength:40}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>费用标准性质<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="charge_stan_nature" type="text" id="charge_stan_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>数据精度<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="data_precision" type="text" id="data_precision" ltype="text" value="${data_precision}" validate="{required:true,digits:true,maxlength:4}" /></td>
	            <td align="left"></td>
	           
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font>是否停用<span style="color:red">*</span>:</font></td>
	            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:4}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量单位：</td>
	            <td align="left" class="l-table-edit-td"><input name="unit" type="text" id="unit" ltype="text" value="${unit}" validate="{maxlength:20}" /></td>
	            <td align="left"></td>
	            
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用标准描述：</td>
	            <td align="left" colspan="5" class="l-table-edit-td">
	            	<input name="charge_stan_describe" type="text" id="charge_stan_describe"  value="${charge_stan_describe}" ltype="text" validate="{maxlength:40}" /></td>
	            </td>
	        </tr> 
      
    	</table>
    </form>
 </body>
</html>
