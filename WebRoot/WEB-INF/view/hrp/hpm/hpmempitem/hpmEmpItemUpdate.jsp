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

       /* if('${is_avg}'=='1'){
    	   
			liger.get("is_avg").setValue(1);liger.get("is_avg").setText("是");	
     		
     	}
     	
     	if('${is_avg}'=='0'){
     		
     		liger.get("is_avg").setValue(0);liger.get("is_avg").setText("否");	
     			
     	} */
     	
     	var is_stop = ${is_stop};
     	
     	if(is_stop==1){
     		
     		liger.get("is_stop").setValue(1);liger.get("is_stop").setText("是");	
     			
     	}
     	
     	if(is_stop==0){
     		
     		liger.get("is_stop").setValue(0);liger.get("is_stop").setText("否");	
     			
     	}
     	
     });  
     
     function  save(){
    	 
     	var formPara={
     			item_code:$("#item_code").val(),
     			item_name:$("#item_name").val(),
     			item_note:$("#item_note").val(),
     			is_avg:1,
     			is_stop:$("#is_stop").val()
     	};
     	
     	ajaxJsonObjectByUrl("updateHpmEmpItem.do",formPara,function(responseData){
            
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
   
	function saveItem(){
		if($("form").valid()){save();}
   }
	function loadDict(){
	
		$("#item_code").ligerTextBox({disabled:true});
		
	}

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">奖金项目编码：</td>
				<td align="left" class="l-table-edit-td"><input name="item_code" type="text" disabled="disabled" id="item_code" ltype="text" value="${item_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">奖金项目名称：</td>
				<td align="left" class="l-table-edit-td"><input name="iitem_name" type="text" id="item_name" ltype="text" value="${item_name}"
					validate="{required:true,maxlength:50}" /></td>
				<td align="left"></td>
			</tr>
			<!-- <tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">是否参与人均奖核算：</td>
				<td align="left" class="l-table-edit-td"><select name="is_avg" id="is_avg">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr> -->
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">项目说明：</td>
				<td align="left" class="l-table-edit-td"><input name="item_note" type="text" id="item_note" ltype="text" value="${item_note}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
