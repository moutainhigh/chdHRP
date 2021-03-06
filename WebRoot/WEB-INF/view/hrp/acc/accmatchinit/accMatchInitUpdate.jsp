<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
        		proj_id:liger.get("proj_id").getValue().split(".")[0],
        		proj_no:liger.get("proj_id").getValue().split(".")[1],
        		bal_os:$("#bal_os").val(),
           		sum_od:0,
          		sum_oc:0,
           		end_os:0       		
         };
        ajaxJsonObjectByUrl("updateAccMatchInit.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveAccPayType(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	
    	autocompleteAsync("#proj_id","../../sys/queryProjDictDict.do?isCheck=false","id","text",true,true,'',false,'${proj_id}');  
    	liger.get("proj_id").setValue("${proj_id}.${proj_no}");
        liger.get("proj_id").setText("${proj_code} ${proj_name}");
		$("#proj_id").ligerTextBox({ disabled: true });
		$("#con_emp_id").ligerTextBox({disabled:true });
    	$("#dept_id").ligerTextBox({disabled:true });
    	$("#bal_os").val(new Number(${bal_os}))
     } 
    function projChange(){
    	$.post("queryAccProjAttrByProj.do?isCheck=false",{"proj_id":liger.get("proj_id").getValue().split(".")[0]},function(data){
    		$("#con_emp_id").val(data.con_emp_name);
    		$("#dept_id").val(data.dept_name);
    	},"json");
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:100}" onchange="projChange()"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
                <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请科室：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:50}" disabled="disabled"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年初余额：</td>
                <td align="left" class="l-table-edit-td"><input name="bal_os" type="text" id="bal_os" ltype="text" validate="{required:true,maxlength:15}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
