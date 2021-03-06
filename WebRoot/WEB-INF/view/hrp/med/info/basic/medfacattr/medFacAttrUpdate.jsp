<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:auto;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/med/med.js" type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    var grid;
    var gridManager;
    var mod_codeOld = "${mod_code}";
    var mod_code = "${mod_code}" ;
    var fac_codeOld = "${fac_code}"
    $(function (){
    	
        loadDict();
        loadForm();
        loadHead(null);
       if("${is_mat}" == 1){
        	$("#is_mat").attr("checked","checked");
		} 
        if("${is_med}" == 1){
        	$("#is_med").attr("checked","checked");
		}
        if("${is_ass}" == 1){
        	//liger.get("is_ass").setValue(true)
        	$("#is_ass").attr("checked","checked");
		} 
        if("${is_sup}" == 1){
        	$("#is_sup").attr("checked","checked");
		}
        queryFac();
    });  
     
    function save(){
    	mat=$("#is_mat").is(":checked") ? 1 : 0;
    	med=$("#is_med").is(":checked") ? 1 : 0;
    	ass=$("#is_ass").is(":checked") ? 1 : 0;
    	sup=$("#is_sup").is(":checked") ? 1 : 0 ;		    
    	if(mat=='0' && med=='0' && ass=='0' && sup=='0'){
    		 $.ligerDialog.error("系统模块不能为空");
    		 return ; 
    	}
    	var reg=/^[1][3578][0-9]{9}$/;
		if($("#phone").val()!=null && ($("#phone").val()!="")){
		   	if(reg.test($("#phone").val())==false){
			  $.ligerDialog.error("电话输入不合法!");
		
			  return;
		  	}
		}
		if($("#mobile").val()!=null && ($("#mobile").val()!="")){
			if(reg.test($("#mobile").val())==false){
				$.ligerDialog.error("手机号输入不合法!");
		
				return;
			}
		}
    	
        var formPara={
			group_id:$("#group_id").val(),
			hos_id:$("#hos_id").val(),
	        fac_id:$("#fac_id").val(),
	        fac_code:$("#fac_code").val(),
	        fac_codeOld: fac_codeOld ,
	        fac_name: $("#fac_name").val(),
	        type_code: liger.get("type_code").getValue(),
	        fac_sort:$("#fac_sort").val(),
	        spell_code:$("#spell_code").val(),
	        wbx_code:$("#wbx_code").val(),
	        mod_codeOld:mod_codeOld ,
	        mod_code: mod_code ,
	        is_mat : $("#is_mat").is(":checked") ? 1 : 0,
	        is_med : $("#is_med").is(":checked") ? 1 : 0,
        	is_ass : $("#is_ass").is(":checked") ? 1 : 0 ,
        	is_sup : $("#is_sup").is(":checked") ? 1 : 0 ,
	        legal:$("#legal").val(),
	        regis_no:$("#regis_no").val(),
	        phone:$("#phone").val(),
	        mobile:$("#mobile").val(),
	        contact:$("#contact").val(),
	        fax:$("#fax").val(),
	        email:$("#email").val(),
	        region:$("#region").val(),
	        zip_code:$("#zip_code").val(),
	        address:$("#address").val(),
	        note:$("#note").val(),
	        is_stop: liger.get("is_stop").getValue(),
	        history: $("#history").prop("checked"),
	        is_auto: $("#is_auto").prop("checked"),
			bankData : JSON.stringify(gridManager.getData())
        };
        
        ajaxJsonObjectByUrl("updateMedFacAttr.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	parentFrameUse().query();
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
   
    function saveAssFacAttr(){
    	
    	grid.endEdit();
    	
        if($("form").valid()){
        	
        	
            save();
        }
    }
    function loadDict(){
		//字典下拉框
        //$("#fac_code").ligerComboBox({width:413, disabled:true, cancelable: false});
		//liger.get("fac_code").setValue("${fac_id}");
    	//liger.get("fac_code").setText("${fac_code} ${fac_name}");
    	
    	autocomplete("#type_code","../../../../sys/queryFacTypeDict.do?isCheck=false","id","text",true,true);
    	
    	liger.get("type_code").setValue("${type_code}");
    	liger.get("type_code").setText("${type_name}");
    	
    	if('${is_stop}' != null && '${is_stop}' != '' && '${is_stop}' != 'undefined'){
    		autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text",true,true, "", false, "${is_stop}",'180');
        }else{
        	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text",true, true,'',true,false,'180');
        }
    	$("#note").val("${note}");
    	$("#fac_code").ligerTextBox({width:160,disabled:true});
    	$("#fac_name").ligerTextBox({width:160});
    	$("#legal").ligerTextBox({width:160});
    	$("#regis_no").ligerTextBox({width:160});
    	$("#phone").ligerTextBox({width:160});
    	$("#mobile").ligerTextBox({width:160});
    	$("#contact").ligerTextBox({width:160});
    	$("#fax").ligerTextBox({width:160});
    	$("#email").ligerTextBox({width:160});
    	$("#region").ligerTextBox({width:160});
    	$("#zip_code").ligerTextBox({width:160});
    	$("#address").ligerTextBox({width:440});
    	$("#note").ligerTextBox({width:440});
    	$("#is_count").ligerTextBox({width:160});
    	$("#is_stop").ligerTextBox({width:160});
    	$("#spell_code").ligerTextBox({width:160});
    	$("#wbx_code").ligerTextBox({width:160});
    	
    	 
     }   

	function queryFac(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'group_id',
			value : '${group_id}'
		});
		grid.options.parms.push({
			name : 'hos_id',
			value : '${hos_id}'
		});
		grid.options.parms.push({
			name : 'fac_id',
			value : '${fac_id}'
		});

    	//加载查询条件
    	setTimeout("grid.loadData(grid.where); ",500);
    	
	}
	
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '银行账号', name: 'bank_no', align: 'left', width: '45%', 
				editor :{
					type : 'text',
				}
			}, { 
				display: '开户银行', name: 'bank_name', align: 'left', width: '45%', 
				editor :{
					type : 'text',
				}
			} ],
			dataAction: 'server',dataType: 'server',usePager:false,isAddRow:false,
			width: '98%', height: '300', checkbox: true,rownumbers:true,
			url: "queryHosFacBank.do?isCheck=false",
			enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
			//isScroll : false,
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				{ text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true },
				{ text: '保存', id:'save', click: saveBank, icon:'save' },{ line:true }
			]}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}		
	      
	var rowindex_id = "";
	var column_name="";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name=e.column.name;
	}
	 		
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
	 	return true;
	 }
	 
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
	 		
		return true;
	}
	
	//保存数据
	function saveBank() {
		grid.endEdit();
    	if(validateGrid()){
			var formPara = {
		        group_id : $("#group_id").val(),
		        hos_id : $("#hos_id").val(),
		        copy_code : $("#copy_code").val(),
				fac_id : $("#fac_id").val(),
				bankData : JSON.stringify(gridManager.getData())
		 	};
		 	ajaxJsonObjectByUrl("addHosFacBank.do?isCheck=false", formPara,function(responseData) {
				if (responseData.state == "true") {
					queryBank();
		 		}
			});
    	}
	}
	
	//自动添加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}
	//手动添加行
	function addCenterRow() {
		grid.addRow();
	}
	
	//删除选中行
	function deleteRow(){
		gridManager.deleteSelectedRow();
	}
    
    function validateGrid() {  
 		//明细
 		var msg="";
 		var data = gridManager.getData();
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
			var key=v.bank_no;
			var value="第"+(i+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"银行账号重复" + "<br>";
			}
 		});
 		if(msg != ""){
 			$.ligerDialog.warn(msg+"请修改！");  
			return false;  
 		} 	 	
 		return true;
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display:none;" ></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left:60px">
            <tr>
                <td align="left" colspan="9" class="l-table-edit-td">
                	<input name="group_id" type="hidden" disabled="disabled"   id="group_id" ltype="text"  value="${group_id}" validate="{required:true}" />
                	<input name="hos_id" type="hidden" disabled="disabled"   id="hos_id" ltype="text"  value="${hos_id}" validate="{required:true}" />
                	<input name="fac_id" type="hidden" id="fac_id" ltype="text" disabled="disabled"  value="${fac_id}" validate="{required:true}" />
                </td>
            </tr> 
            <tr>
	        	<td colspan="9" align="left" class="l-table-edit-td" >
	        		<h3>>>生成厂商主表信息</h3>
	        	</td>
	        </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="fac_code" type="text" id="fac_code" value="${fac_code}"  disabled="disabled" ltype="text" validate="{maxlength:50}" /></td>
                <td align="left"></td>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td" ><input name="fac_name" type="text" id="fac_name" value="${fac_name}" ltype="text" validate="{required:true,maxlength:100}"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商类别<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
             </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号:</b></td>
                <td align="left" class="l-table-edit-td"><input name="fac_sort" type="text" id="fac_sort" value="${fac_sort}" disabled="disabled" ltype="text" validate="{required:true,digits:true , maxlength:10}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" colspan="4" class="l-table-edit-td">
                	<input id="note" name="note" ltype="text" value="${note }"  validate="{maxlength:50}"></input>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>拼音码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text"  value="${spell_code }" id="spell_code" ltype="text" validate="{maxlength:100}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>五笔码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text"  id="wbx_code" value="${wbx_code }" ltype="text" /></td>
                <td align="left"></td>
                <td align="left" colspan="3">
					<input name="history"  id="history"  type="checkbox" checked="checked"/><b>是否保留历史变更</b>
					<input name="is_auto"  id="is_auto"  type="checkbox" checked="checked"/><b>是否自动生成拼音码、五笔码</b>
				</td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font style="color:red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"  validate="{required:true}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>所属模块<font style="color:red">*</font>:</b></td>
				<td align="left">
					<input name="is_mat"  id ="is_mat"  type="checkbox" />物流管理&nbsp;&nbsp;
             		<input name="is_med"  id ="is_med" type="checkbox" />药品管理&nbsp;&nbsp;
             		<input name="is_ass"  id ="is_ass" type="checkbox" />固定资产&nbsp;&nbsp;
             		<input name="is_sup"  id ="is_sup" type="checkbox" />供应商平台&nbsp;&nbsp;
				</td>
				<td align="left"></td>
            </tr>
            <tr>
	        	<td colspan="9" align="left" class="l-table-edit-td" >
	        		<h3>>>生成厂商附属表表信息</h3>
	        	</td>
	        </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">法人：</td>
                <td align="left" class="l-table-edit-td"><input name="legal" type="text" id="legal" ltype="text"  value="${legal}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">纳税人登记号：</td>
                <td align="left" class="l-table-edit-td"><input name="regis_no" type="text" id="regis_no" ltype="text"  value="${regis_no}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系人：</td>
                <td align="left" class="l-table-edit-td"><input name="contact" type="text" id="contact" ltype="text"  value="${contact}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">电话：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text" id="phone" ltype="text"  value="${phone}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手机：</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text" id="mobile" ltype="text"  value="${mobile}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">传真：</td>
                <td align="left" class="l-table-edit-td"><input name="fax" type="text" id="fax" ltype="text"  value="${fax}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">EMAIL：</td>
                <td align="left" class="l-table-edit-td"><input name="email" type="text" id="email" ltype="text"  value="${email}" validate="{email:true,maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地区：</td>
                <td align="left" class="l-table-edit-td"><input name="region" type="text" id="region" ltype="text"  value="${region}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">邮政编码：</td>
                <td align="left" class="l-table-edit-td"><input name="zip_code" type="text" id="zip_code" ltype="text"  value="${zip_code}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地址：</td>
                <td align="left" colspan="4" class="l-table-edit-td"><input name="address" type="text" id="address" ltype="text"  value="${address}" validate="{maxlength:50}" /></td>
               
            </tr>
	        <tr>
	        	<td colspan="9" align="left" class="l-table-edit-td" >
	        		<h3>>>生成厂商账户信息</h3>
	        	</td>
	        </tr>
	        <tr>
	        	<td colspan="9" class="l-table-edit-td" >
	        		<div id="maingrid"></div>
	        	</td>
	        </tr>
        </table>
    </form>
    </body>
</html>
