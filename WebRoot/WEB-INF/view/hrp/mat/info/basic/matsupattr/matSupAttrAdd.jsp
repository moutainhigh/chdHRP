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
    <script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var mod_code ='04' ;//（ 模块编码  该页面为物流系统使用 默认 物流系统模块'04' , 其他系统模块用该页面时 改为 相应的系统模块编码 ）
     var is_auto = "${is_auto}";
     $(function (){
        loadDict()//加载下拉框
        loadForm();
        loadHead();
   /*      $("#mod_code1").click(function(){
        	 if($("#mod_code1").prop("checked")){
        		 $("#mod_code2").prop("checked",false);
        		 $("#mod_code3").prop("checked",false);
        		 mod_code = "04";
        	 }
 	   })
 	   $("#mod_code2").click(function(){
 	    	if($("#mod_code2").prop("checked")){
 	   		 $("#mod_code1").prop("checked",false);
 	   		 $("#mod_code3").prop("checked",false);
 	   		 mod_code = "05";
 	   	 	}
 	   })
 	   $("#mod_code3").click(function(){
	 		  if($("#mod_code3").prop("checked")){
	 	 		 $("#mod_code1").prop("checked",false);
	 	 		 $("#mod_code2").prop("checked",false);
	 	 		 mod_code = "08";
	 	 	 }
 	   }) */
 	   if(is_auto != 1){
 		  $("#sup_code").val("");
 		  $("#sup_code").ligerTextBox({disabled:false});
 	   }
 	   if(liger.get("type_code").getValue() != ''){
 		  $("#type_code").ligerTextBox({disabled:true});
 	   }
     });  
     
     function loadHead(){
 		grid = $("#maingrid").ligerGrid({
 			columns: [ { 
				display: '银行账号', name: 'bank_no', align: 'left', width: '15%', 
				editor :{
					type : 'text',
				}
			},{display: '交易对方银行名称', name: 'bank_name', align: 'left', width: '15%', 
 				editor :{
 					type : 'text',
 				}},
 				{display: '所在城市名称', name: 'bank_area_name', align: 'left', width: '10%', 
 	 				editor :{
 	 					type : 'select',
 						valueField : 'text',
 						textField : 'text',
 						selectBoxWidth : 300,
 						selectBoxHeight : 200,
 						url: '../../../../acc/internetbank/queryAccBankNetICBCCode.do?isCheck=false',
 						width: '98%', height: '98%', 
 						keySupport : true,
 						autocomplete : true
 						
 	 				}},
 	 			{display: '对方行行号', name: 'bank_code', align: 'left', width: '15%', 
 	 				editor :{
 	 					type : 'text',
 	 				},hide:true},
 	 			{display: '收款工行地区号', name: 'bank_area_number', align: 'left', width: '10%', 
 	 				editor :{
 	 					type : 'text',
 	 				},hide:true},
 	 			{display: '交易对方银行全称', name: 'bank_full_name', align: 'left', width: '25%', 
 	 				editor :{
 	 					type : 'text',
 	 				},hide:true
 	 			},
 	 			{
 	 			display : '是否默认', name : 'is_default', align : 'center',
 	 			render : function(rowdata, rowindex,value) {
 	 				if(value == 1){
 	 					return "<input name='is_default"+rowindex+"' checked='checked'  type='checkbox' id='is_default"+rowindex+"' ltype='text'"
 	 					+" style='margin-top:5px;' />";
 	 				}
 	 				return "<input name='is_default"+rowindex+"'  type='checkbox' id='is_default"+rowindex+"' ltype='text'"
 	 					+" style='margin-top:5px;' />";
 	 				}
 	 			} 
 	 			],
 			dataAction: 'server',dataType: 'server',usePager:false,isAddRow:false,
 			width: '98%', height: '200', checkbox: true,rownumbers:true,
 			//url: "queryHosSupBank.do?isCheck=false",
 			enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
 			onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
 			//isScroll : false,
 			selectRowButtonOnly:true,//heightDiff: -10,
 			toolbar: { items: [
 				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
 				{ text: '添加行', id:'add', click: addCenterRow, icon:'add' },{ line:true },
 				/* { text: '保存', id:'save', click: saveBank, icon:'save' },{ line:true } */
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
 				sup_id : $("#sup_id").val(),
 				bankData : JSON.stringify(gridManager.getData())
 		 	};
 		 	ajaxJsonObjectByUrl("addHosSupBank.do?isCheck=false", formPara,function(responseData) {
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
     
     
     function  save(){
    	 mat=$("#is_mat").is(":checked") ? 1 : 0;
     	med=$("#is_med").is(":checked") ? 1 : 0;
     	ass=$("#is_ass").is(":checked") ? 1 : 0;
     	sup=$("#is_sup").is(":checked") ? 1 : 0 ;		    
     	if(mat=='0' && med=='0' && ass=='0' && sup=='0'){
     		 $.ligerDialog.error("系统模块不能为空");
     		 return ; 
     	}
    	/* var reg=/^[1][3578][0-9]{9}$/;
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
	    } */
        
        var data = gridManager.getData();
        var count = 0;
        var rows = 0;
        if(data.length != 0){
	        $.each(data,function(rowindex,item){
	        	if(item.bank_no != 'undefined' && item.bank_no != null && item.bank_no != ''){
	        		var is_default = $("#is_default"+rowindex).is(":checked")? 1 : 0;
	        		if(is_default == 1){
	        			count++;
	        		}
	        		item["is_default"] = is_default.toString();
	        		rows++;
	        	}
	        });
        }
        
        if(rows != 0){
        	if(count != 1){
        		$.ligerDialog.warn('是否默认必选且只能默认一个 ');
        		return ; 
        	}
        }
        
        var formPara={
        		sup_id:'',
        		sup_code:$("#sup_code").val(),
        		sup_name:$("#sup_name").val(),
        		//mod_code:mod_code,
        		is_mat:$("#is_mat").is(":checked") ? 1 : 0,
				is_med:$("#is_med").is(":checked") ? 1 : 0,
				is_ass:$("#is_ass").is(":checked") ? 1 : 0 ,
				is_sup:$("#is_sup").is(":checked") ? 1 : 0 ,
				is_delivery: $("#is_delivery").is(":checked") ? 1 : 0,
        		sort_code:$("#sort_code").val(),
        		type_code:liger.get("type_code").getValue(),
        		pay_code:liger.get("pay_code").getValue(),
        		note:$("#note").val(),
				sup_alias:$("#sup_alias").val(),
				legal: $("#legal").val(),
		        regis_no: $("#regis_no").val(),
		        phone: $("#phone").val(),
		        mobile: $("#mobile").val(),
		        contact: $("#contact").val(),
		        fax: $("#fax").val(),
		        email: $("#email").val(),
		        region: $("#region").val(),
		        zip_code: $("#zip_code").val(),
		        address: $("#address").val(),
		        note: $("#note").val(),
		        sup_alias: $("#sup_alias").val(),
		        ven_trade: $("#ven_trade").val(),
		        prov: $("#prov").val(),
		        city: $("#city").val(),
		        ven_dis_rate: $("#ven_dis_rate").val(),
		        ven_pay_con: liger.get("ven_pay_con").getValue(),
		        ven_person: $("#ven_person").val(),
		        ven_dir_address: $("#ven_dir_address").val(),
		        ven_cre_grade: $("#ven_cre_grade").val(),
		        end_date: $("#end_date").val(),
		        is_count: liger.get("is_count").getValue(),
		        dept_id:  liger.get("dept_code").getValue(),
		        ven_cre_line: $("#ven_cre_line").val(),
		        bven_tax: liger.get("bven_tax").getValue(),
		        ven_dev_date: $("#ven_dev_date").val(),
		        products: $("#products").val(),
		        is_stop: liger.get("is_stop").getValue(),
		        business_charter: $("#business_charter").val(),
		        frequency: $("#frequency").val(),
				bankData : JSON.stringify(data)
         };
        
        ajaxJsonObjectByUrl("addMatSupAttr.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 //$("input[name='sup_code']").val('');
				 $("input[name='sup_name']").val('');
				 $("input[name='sup_alias']").val('');
				 $("input[name='bank_name']").val('');
				 $("input[name='bank_number']").val('');
				 $("input[name='legal']").val('');
				 $("input[name='regis_no']").val('');
				 $("input[name='phone']").val('');
				 $("input[name='mobile']").val('');
				 $("input[name='contact']").val('');
				 $("input[name='fax']").val('');
				 $("input[name='email']").val('');
				 $("input[name='region']").val('');
				 $("input[name='zip_code']").val('');
				 $("input[name='address']").val('');
				 $("input[name='note']").val('');
				 $("input[name='is_count']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='ven_trade']").val('');
				 $("input[name='prov']").val('');
				 $("input[name='city']").val('');
				 $("input[name='ven_dis_rate']").val('');
				 $("input[name='ven_person']").val('');
				 $("input[name='ven_dir_address']").val('');
				 $("input[name='ven_cre_grade']").val('');
				 $("input[name='end_date']").val('');
				 $("input[name='ven_cre_line']").val('');
				 $("input[name='ven_dev_date']").val('');
				 $("input[name='products']").val('');
				 $("input[name='business_charter']").val('');
				 $("input[name='frequency']").val('');
				 $("input[name='type_code']").val('');
				 parentFrameUse().query();
            }
        }); 
    }
    function validateGrid() {  
  		//供应商信息校验
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
   
    function saveAssSupAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
        //    autocomplete("#sup_id","../queryHosSupDict.do?isCheck=false","id","text",true,true);
            //供应商下拉框
    	//autocomplete("#sup_code","../../../queryHosSupDict.do?isCheck=false","id","text",true,true,'',false,'',160);
    	$("#sup_code").ligerTextBox({width:160,disabled:true});
    	autocomplete("#pay_code", "../../../queryMatPayType.do?isCheck=false", "id", "text", true, true, '', false, '', 160);

    	autocomplete("#type_code","../../../queryHosSupType.do?isCheck=false","id","text",true,true,'',false,'${type_code}',160);
    	liger.get("type_code").setValue("${type_code}");
    	autocomplete("#dept_code","../../../queryHosDept.do?isCheck=false","id","text",true,true, {is_last : 1},false);
    	
    	autocomplete("#ven_pay_con","../../../queryMatPayTerm.do?isCheck=false","id","text",true,true);
    	
    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text",true,true, "", true,'',160);
    	autoCompleteByData("#is_count", yes_or_no.Rows, "id", "text",true, true,'',true,'',160);
    	autoCompleteByData("#bven_tax", yes_or_no.Rows, "id", "text",true, true,'',true,'',160);
    	
    	$("#sup_alias").ligerTextBox({width:160});
    	$("#business_charter").ligerTextBox({width:160});
    	$("#ven_trade").ligerTextBox({width:160});
    	$("#bank_name").ligerTextBox({width:160});
    	$("#bank_number").ligerTextBox({width:160});
    	$("#legal").ligerTextBox({width:160});
    	$("#regis_no").ligerTextBox({width:160});
    	$("#phone").ligerTextBox({width:160});
    	$("#mobile").ligerTextBox({width:160});
    	$("#contact").ligerTextBox({width:160});
    	$("#fax").ligerTextBox({width:160});
    	$("#email").ligerTextBox({width:160});
    	$("#region").ligerTextBox({width:160});
    	$("#zip_code").ligerTextBox({width:160});
    	$("#address").ligerTextBox({width:160});
    	$("#note").ligerTextBox({width:160});
    	$("#is_count").ligerTextBox({width:160});
    	$("#is_stop").ligerTextBox({width:160});
    	
    	$("#prov").ligerTextBox({width:160});
    	$("#city").ligerTextBox({width:160});
    	$("#ven_person").ligerTextBox({width:160});
    	
    	$("#ven_dis_rate").ligerTextBox({width:160});
    	$("#ven_dir_address").ligerTextBox({width:160});
    	$("#ven_cre_grade").ligerTextBox({width:160});
    	$("#ven_cre_line").ligerTextBox({width:160});
    	$("#frequency").ligerTextBox({width:160});
    	$("#ven_dev_date").ligerTextBox({width:160});
    	$("#end_date").ligerTextBox({width:160});
    	$("#products").ligerTextBox({width:160});
    	
    	
    	$("#sup_name").ligerTextBox({width:160});
    	$("#type_code").ligerTextBox({width:160});
    	
    	$("#sort_code").ligerTextBox({width:160,disabled:true});
    	$("#note").ligerTextBox({width:160});
    	
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
			<tr>
	        	<td colspan="9" align="left" class="l-table-edit-td" >
	        		<h3>>>供应商主表信息</h3>
	        	</td>
	        </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商编码：</td>
				<td align="left" class="l-table-edit-td"><input name="sup_code" type="text"   id="sup_code" ltype="text" value="自动生成"  validate="{maxlength:50}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商名称<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input name="sup_name" type="text"   id="sup_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">类型编码<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input name="type_code" type="text"   id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">排序号<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input name="sort_code" type="text"   id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,maxlength:10}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
				<td align="left" class="l-table-edit-td"><input name="note" type="text"   id="note" ltype="text" validate="{maxlength:50}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">所属模块:</td>
				<td align="left">
<!-- 					<input name="mod_code1"  id="mod_code1"  checked="checked" type="checkbox" /><b>物流</b> -->
<!-- 					<input name="mod_code2"  id="mod_code2"  type="checkbox" /><b>固定资产</b> -->
<!-- 					<input name="mod_code3"  id="mod_code3"  type="checkbox" /><b>药品</b> -->
					<input name="is_mat"  id ="is_mat" type="checkbox" />物流管理&nbsp;&nbsp;
                	<input name="is_med"  id ="is_med"  type="checkbox" />药品管理&nbsp;&nbsp;
                	<input name="is_ass"  id ="is_ass" type="checkbox" />固定资产&nbsp;&nbsp;
                	<input name="is_sup"  id ="is_sup" type="checkbox" />供应商平台&nbsp;&nbsp;
				</td>
				<td align="left"></td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  ></td>
				<td align="left" class="l-table-edit-td"><input name="is_delivery"  id ="is_delivery" type="checkbox" />&nbsp;&nbsp;是否允许自制送货</td>
				<td align="left"></td>
            </tr>
            <tr>
	        	<td colspan="9" align="left" class="l-table-edit-td" >
	        		<h3>>>供应商附属表表信息</h3>
	        	</td>
	        </tr>
            <tr>	
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否区县：</td>
                <td align="left" class="l-table-edit-td"><input name="is_count" type="text" id="is_count" ltype="text"  validate="{required:true}"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"  validate="{required:true}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商简称：</td>
				<td align="left" class="l-table-edit-td"><input name="sup_alias" type="text"  id="sup_alias" ltype="text"  validate="{maxlength:50}" /></td>
				<td align="left"></td>
            </tr>
            <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">所属行业：</td>
				<td align="left" class="l-table-edit-td"><input name="ven_trade" type="text"  id="ven_trade" ltype="text" validate="{maxlength:50}" /></td>
				<td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">法人：</td>
                <td align="left" class="l-table-edit-td"><input name="legal" type="text" id="legal" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">纳税人登记号：</td>
                <td align="left" class="l-table-edit-td"><input name="regis_no" type="text" id="regis_no" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>     
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">营业执照：</td>
                <td align="left" class="l-table-edit-td"><input name="business_charter" type="text" id="business_charter" ltype="text"  value="${business_charter}" validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地区：</td>
                <td align="left" class="l-table-edit-td"><input name="region" type="text" id="region" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">省：</td>
                <td align="left" class="l-table-edit-td"><input name="prov" type="text" id="prov" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
             </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">市：</td>
                <td align="left" class="l-table-edit-td"><input name="city" type="text" id="city" ltype="text"  validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系人：</td>
                <td align="left" class="l-table-edit-td"><input name="contact" type="text" id="contact" ltype="text"  validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地址：</td>
                <td align="left" class="l-table-edit-td"><input name="address" type="text" id="address" ltype="text"  validate="{maxlength:50}" /></td>
                <td align="left"></td>
             </tr> 
            <tr>   
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">邮编：</td>
                <td align="left" class="l-table-edit-td"><input name="zip_code" type="text" id="zip_code" ltype="text"  validate="{maxlength:50}" /></td>
                <td align="left"></td>
            
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手机：</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text" id="mobile" ltype="text"  validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">电话：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text" id="phone" ltype="text"  validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">传真：</td>
                <td align="left" class="l-table-edit-td"><input name="fax" type="text" id="fax" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">EMAIL：</td>
                <td align="left" class="l-table-edit-td"><input name="email" type="text" id="email" ltype="text"  validate="{email:true,maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">专营业务员：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_person" type="text" id="ven_person" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款条件：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_pay_con" type="text" id="ven_pay_con" ltype="text" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价是否含税：</td>
                <td align="left" class="l-table-edit-td"><input name="bven_tax" type="text" id="bven_tax" ltype="text"  validate="{maxlength:100}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">扣率：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_dis_rate" type="text" id="ven_dis_rate" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
             </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">到货地址：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_dir_address" type="text" id="ven_dir_address" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">信用等级：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_cre_grade" type="text" id="ven_cre_grade" ltype="text"   validate="{maxlength:50}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">信用额度：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_cre_line" type="text" id="ven_cre_line" ltype="text"   validate="{maxlength:100}" /></td>
                <td align="left"></td>
           </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用频度：</td>
                <td align="left" class="l-table-edit-td"><input name="frequency" type="text" id="frequency" ltype="text"   validate="{maxlength:100}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分管部门：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发展日期：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_dev_date" type="text" id="ven_dev_date" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
           </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">停用日期：</td>
                <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" ltype="text"   class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主要产品：</td>
                <td align="left" class="l-table-edit-td"><input name="products" type="text" id="products" ltype="text"  validate="{maxlength:100}"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款方式：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_code" type="text" id="pay_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
	        <tr>
	        	<td colspan="9" align="left" class="l-table-edit-td" >
	        		<h3>>>供应商账户信息</h3>
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
