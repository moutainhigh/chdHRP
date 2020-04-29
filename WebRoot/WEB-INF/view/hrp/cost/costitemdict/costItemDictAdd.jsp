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
	
var first_length=0;
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
      //获取第一级编码长度
		first_length=${first_length};
        
     });  
     
     function  save(){
    	 
    	 var v_length=$("#cost_item_code").val();
 		if(v_length.length==first_length){
 			if(liger.get("cost_type").getValue()==""){
 				$.ligerDialog.error('成本分类不能为空');
 		    	 return ;
 			}
 	     } 
        var formPara={
            
           cost_type_id:liger.get("cost_type").getValue().split(".")[0],
            
           cost_type_no:liger.get("cost_type").getValue().split(".")[1],
            
           cost_item_id:'',
            
           cost_item_code:$("#cost_item_code").val(),
            
           cost_item_name:$("#cost_item_name").val(),
            
           nature_id:liger.get("nature_id").getValue(),
           
           para_type_code:liger.get("para_type_code").getValue(),
           
           busi_data_source:liger.get("busi_data_source").getValue(),
            
           is_last:$("#is_last").val(),
            
           is_stop:$("#is_stop").val()
            
         };
        
        ajaxJsonObjectByUrl("addCostItemDict.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
				 $("input[name='cost_type']").val('');
				 $("input[name='cost_item_code']").val('');
				 $("input[name='cost_item_name']").val('');
				 $("input[name='nature_id']").val('');
				 $("input[name='para_type_code']").val('');
				 $("input[name='busi_data_source']").val('');
				 $("input[name='is_last']").val('');
				 $("input[name='is_stop']").val('');
                	parent.query();
                	parent.loadTree();
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
   
    function saveCostItemDict(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#nature_id","../queryDeptNature.do?isCheck=false","id","text",true,true);
            
		 autocomplete("#cost_type","../queryDeptTypeDictNo.do?isCheck=false","id","text",true,true);
		 //成本项目来源
		 autocomplete("#busi_data_source","../queryDataSource.do?isCheck=false","id","text",true,true,{busi_data_source_type:2});
		 //成本项目分摊类型配置
		 autocomplete("#para_type_code","../queryParaType.do?isCheck=false","id","text",true,true);
     } 
    
    function charge_disabled() {
		var v_length=$("#cost_item_code").val();
	     if(v_length.length>first_length){
	    	 $("input[name='cost_type']").val('');
	         $("#cost_type").ligerComboBox({disabled:true,cancelable: false});
	     }else{
	         $("#cost_type").ligerComboBox({disabled:false,cancelable: true});
	     }
	}
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" onchange="charge_disabled()" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目名称：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_name" type="text" id="cost_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本分类：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_type" type="text" id="cost_type" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本习性：</td>
                <td align="left" class="l-table-edit-td"><input name="nature_id" type="text" id="nature_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊类型：</td>
                <td align="left" class="l-table-edit-td"><input name="para_type_code" type="text" id="para_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目来源：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_data_source" type="text" id="busi_data_source" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">末级标志：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_last" name="is_last" style="width: 135px;">
			                		<option value="1">是</option>
			                		<option value="0">否</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">停用标志：</td>
                <td align="left" class="l-table-edit-td">
               		<select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
