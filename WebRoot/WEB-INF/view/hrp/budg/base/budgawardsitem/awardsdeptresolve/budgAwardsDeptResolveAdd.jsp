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
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
           awards_item_code:liger.get("awards_item_code").getValue(),
            
           resolve_way:liger.get("resolve_way").getValue()
            
        };
        
        ajaxJsonObjectByUrl("addBudgAwardsDeptResolve.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='awards_item_code']").val('');
				 $("input[name='resolve_way']").val('');
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
   
    function saveBudgAwardsDeptResolve(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
         //字典下拉框
		 autocomplete("#awards_item_code","../../../queryBudgAwardsItem.do?isCheck=false","id","text",true,true);
         
		 autocomplete("#resolve_way","../../../queryBudgResolveWay.do?isCheck=false","id","text",true,true);
    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>奖金项目<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="awards_item_code" type="text" id="awards_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>分解方法<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="resolve_way" type="text" id="resolve_way" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
