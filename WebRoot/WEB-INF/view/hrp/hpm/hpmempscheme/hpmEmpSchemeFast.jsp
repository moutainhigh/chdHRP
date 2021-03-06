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
    	 
        autocomplete("#formula_code","../../hpm/queryFormula.do?isCheck=false","id","text",true,true);
        liger.get("formula_code").setValue("${formula_code}");
    	liger.get("formula_code").setText("${formula_name}");
      	$("#formula_code").ligerTextBox({width:160 }); 
        loadForm();
        
     });  
     
     function  save(){
    	if($('#wrap input[name="rbtnl"]:checked').val() == "check"){
    		
    		if($("#checkIds").val() == ''){
    			$.ligerDialog.warn('请选择要填充的数据 ');
    			return ; 
    		}
    	}
    	 
        var formPara={
        
        	formula_code:liger.get("formula_code").getValue(),
        	
	        checkIds:$("#checkIds").val(),
	        
	        rbtnl:$('#wrap input[name="rbtnl"]:checked').val()
	        
         };
        
        ajaxJsonObjectByUrl("fastHpmEmpScheme.do",formPara,function(responseData){
            
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
   
    function saveDeptBalancePercConf(){
        if($("form").valid()){
            save(); 
        }
   }

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<input type="hidden" name="checkIds" id="checkIds" value="${checkIds}" />
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">填充类型：</td>
				<td align="left" class="l-table-edit-td">
					<div id="wrap">
						<input id="rbtnl_0" type="radio" name="rbtnl" value="all" checked="checked" /> <label for="rbtnl_0">填充全部</label> 
						<input id="rbtnl_0" type="radio" name="rbtnl" value="check" /> <label for="rbtnl_1">填充选择</label>
					</div>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 20px">公式名称：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 30px"><input name="formula_code" type="text" id="formula_code" ltype="text"
					style="width: 160px;" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>


		</table>
	</form>

</body>
</html>
