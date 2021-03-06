<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){

        var formPara={
           store_id:'',
           
           store_code:$("#store_code").val(),
            
           type_code:liger.get("type_code").getValue(),
            
           store_name:$("#store_name").val(),
           
           sort_code:$("#sort_code").val(),
            
       	   is_stop:liger.get("is_stop").getValue(),
            
           note:$("#note").val(), 
           is_mat:$("#is_mat").is(":checked") ? 1 : 0,
       	   is_med:$("#is_med").is(":checked") ? 1 : 0,
       	   is_ass:$("#is_ass").is(":checked") ? 1 : 0 ,
       		is_sup:$("#is_sup").is(":checked") ? 1 : 0 
         };
        
        ajaxJsonObjectByUrl("addStore.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='store_code']").val('');
				 $("input[name='type_code']").val('');
				 $("input[name='store_name']").val('');
				 $("#note").val('');
				// $("input[name='sort_code']").val('');
				  $("input[name='is_stop']").val(''); 
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
   
    function saveStore(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_code","../queryStoreTypeDict.do?isCheck=false","id","text",true,true);
    	 $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		})
		 $("#sort_code").ligerTextBox({width:180,disabled:true});
    } 
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>库房编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="store_code" type="text" id="store_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>库房名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="store_name" type="text" id="store_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select> -->
                	<input name="is_stop" type="text" id="is_stop"  />
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>库房分类:</b></td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td   align="left" class="l-table-edit-td" ></td>	
                	
                <td align="left" class="l-table-edit-td" colspan="2" style="padding-left:20px">
                <input name=is_mat"  id ="is_mat" type="checkbox" />物流管理
                <input name="is_med"  id ="is_med"  type="checkbox" />药品管理
                <input name="is_ass"  id ="is_ass" type="checkbox" />固定资产
                <input name="is_sup"  id ="is_sup" type="checkbox" />供应商平台&nbsp;&nbsp;
                </td>
                <td   align="left" class="l-table-edit-td" ></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                	<textarea rows="3" cols="60" id="note" name="note" ltype="text" validate="{maxlength:20}"></textarea>
                </td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
