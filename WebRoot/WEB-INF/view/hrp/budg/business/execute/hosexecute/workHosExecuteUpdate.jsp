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
        loadForm();
        $("#year").ligerTextBox({width:160});
        
    });  
     
    function save(){
        var formPara={
        year:$("#year").val(),
        month:$("#month").val(),
        index_code:$("#index_code").val(),
        execute_value:$("#execute_value").val(),
        remark:$("#remark").val()
        };
        ajaxJsonObjectByUrl("updateBudgWorkHosExecute.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
  //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'year',value:$("#year").val()}); 
    	  grid.options.parms.push({name:'month',value:$("#month").val()}); 
    	  grid.options.parms.push({name:'index_code',value:$("#index_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
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
   
    function savebudgWorkHosExecute(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //加载年度   
    autocomplete("#year","../../../queryBudgYear.do?isCheck=false","id","text",true,true);
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" value="${year}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码：</td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" value="${index_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			 <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行值：</td>
            <td align="left" class="l-table-edit-td"><input name="execute_value" type="text" id="execute_value" ltype="text" value="${execute_value}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" value="${remark}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        </table>
    </form>
    </body>
</html>
