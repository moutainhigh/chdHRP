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
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        
    });  
     
    function save(){
    	if(liger.get("disease_code").getValue() == null || liger.get("disease_code").getValue() == '' ){
    		$.ligerDialog.warn("病种名称不得为空!")
    		return;
    	}
    	if(liger.get("insurance_code").getValue() == null || liger.get("insurance_code").getValue() == '' ){
    		$.ligerDialog.warn("医保类型不得为空!")
    		return;
    	}
        var formPara={
        		disease_code_old : "${disease_code}",		
        		insurance_code_old : "${insurance_code}",
        		disease_code:liger.get("disease_code").getValue(),
                insurance_code:liger.get("insurance_code").getValue()
        };
        ajaxJsonObjectByUrl("updateBudgYbSingleDisease.do?isCheck=false&",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	  parent.query();
            	parent.$.ligerDialog.close();
            	parent.$(".l-dialog,.l-window-mask").remove();
              
            }
        });
    }
     
    function saveBudgYbSingleDisease(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
    	autocomplete("#insurance_code","../../../queryBudgYBTypeByMode.do?isCheck=false&pay_mode_code=DB","id","text",true,true,"",false,"${insurance_code}");
    	autocomplete("#disease_code","../../../queryBudgSingleDisease.do?isCheck=false&","id","text",true,true,"",false,"${disease_code}");// 病种名称
     	$("#disease_code").ligerTextBox({width:160});
        $("#insurance_code").ligerTextBox({width:160});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>病种编码:</b></td>
            <td align="left" class="l-table-edit-td"><input name="disease_code" type="text" id="disease_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             </tr>
             <tr>
              <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>医保类型:</b></td>
            <td align="left" class="l-table-edit-td"><input name="insurance_code" type="text" id="insurance_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        
             </tr>
           
			
        </table>
    </form>
    </body>
</html>
