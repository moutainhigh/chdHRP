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
        loadDict();
        loadForm();
        
    });  
     
    function save(){
    	var formPara={
    			dept_id:liger.get("dept_id").getValue().split(",")[0],
    			dept_no:'${dept_no}',
    	        dept_percent:$("#dept_percent").val()
    	         };
        ajaxJsonObjectByUrl("updateHpmDeptBalancePercConf.do",formPara,function(responseData){
            
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
    function loadDict(){
    	
    	autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	
    	$("#dept_id").ligerTextBox({ disabled: true });
    	
    	liger.get("dept_id").setValue("${dept_id}");
 		liger.get("dept_id").setText("${dept_name}");	

    	
     }   
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" value="${dept_id}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计提比例：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_percent" type="text" id="dept_percent" ltype="text" value="${dept_percent}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>