<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<!-- jsp:include page="${path}/inc.jsp"/-->
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/stringbuffer.js"></script>
	<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
	  <style> 
    	.l-loading {  
		    display: block;
		    width: 100%;
		    height: 100%;
		    top: 0;
		    left: 0;
		    bottom: 0;
		    right: 0;
		    background-position: center center;
    	}
    </style>
	<script type="text/javascript">
		var grid;
		var gridManager;
		var by_sup_inv = '${p04021 }';
		var by_batch_price = '${p04030 }';
		var isUseAffiStore = '${p04044 }' == 1 ? true : false;
		var by_cont_prot = '${p04082 }';
		var inv_id_url;
		var is_total_cont =0;
		var is_price_cont =0;
		$(function () {
			//alert(division(12,7,4));
			//alert(formatNumber(123344455.1234567,4,-1));
			loadDict()//加载下拉框
			//loadForm();
			loadHead();
			loadHotkeys();
			
			$("#bus_type_code").ligerComboBox({cancelable: false });
			
			$("#bus_type_code").bind("change", function () { changeBus(); });
			
			$("#store_code").bind("change", function () {
				charge_store();
				if(!$("#examiner").val()){
					change_Examiner();
				} 
				if(isUseAffiStore){
					grid.columns[5].editor.grid.url = '../../queryMatInvListIn.do?isCheck=false&store_id=' + liger.get("store_code").getValue().split(",")[0] + '&sup_id=' + liger.get("sup_code").getValue().split(",")[0];
				}else{
					grid.columns[5].editor.grid.url = '../../queryMatInvListIn.do?isCheck=false&is_com=0&store_id=' + liger.get("store_code").getValue().split(",")[0] + '&sup_id=' + liger.get("sup_code").getValue().split(",")[0];
				}
				
				grid.reRender();
			});
			
			$("#sup_code").bind("change", function () {
				var sup_id = liger.get("sup_code").getValue().split(",")[0];
				var in_date = liger.get("in_date").getValue();
				if(sup_id && in_date){
					$.ajax({
						url:"../../getMatPactFkxyInfo.do?isCheck=false",
						data:{
							sup_id:sup_id,
							in_date :in_date	
						},
						type:"post",
						dataType:"json",
						success:function(msg){
							var pactFkxyInfo=msg.pactFkxyInfo?msg.pactFkxyInfo:"";
							
							liger.get("protocol_code").setValue("");
							liger.get("protocol_code").setValue(pactFkxyInfo);
							is_total_cont =msg.is_total_cont?msg.is_total_cont:0;
							is_price_cont =msg.is_price_cont?msg.is_price_cont:0;
						},
						async:false
					})
				}
				
				if(isUseAffiStore){
					grid.columns[5].editor.grid.url = '../../queryMatInvListIn.do?isCheck=false&store_id=' + liger.get("store_code").getValue().split(",")[0] + '&sup_id=' + liger.get("sup_code").getValue().split(",")[0];
				}else{
					grid.columns[5].editor.grid.url = '../../queryMatInvListIn.do?isCheck=false&is_com=0&store_id=' + liger.get("store_code").getValue().split(",")[0] + '&sup_id=' + liger.get("sup_code").getValue().split(",")[0];
				}
				grid.reRender();
			});
			if(!$("#examiner").val()){change_Examiner();}
			
			
		});

		function validateGrid() {
			
			//主表
			if (!liger.get("bus_type_code").getValue()) {
				$.ligerDialog.warn("业务类型不能为空");
				return false;
			}
			if (!liger.get("store_code").getValue()) {
				$.ligerDialog.warn("仓库不能为空");
				return false;
			}
			if (!$("#in_date").val()) {
				$.ligerDialog.warn("制单日期不能为空");
				return false;
			}
			if (liger.get("bus_type_code").getValue() && liger.get("bus_type_code").getValue() == 2) {
				if (!liger.get("stocker").getValue()) {
					$.ligerDialog.warn("采购员不能为空");
					return false;
				}
				if (!liger.get("stock_type_code").getValue()) {
					$.ligerDialog.warn("采购类型不能为空");
					return false;
				}
				if (!liger.get("sup_code").getValue()) {
					$.ligerDialog.warn("供应商不能为空");
					return false;
				}
			}
			//明细
			var rowm = "";
			var msg = "";
			var priceMsg = "";
			var rows = 0;
			var store_inv = "";  //用于判断是否属于该仓库
			var sup_inv = ""; //用于判断唯一供应商
			var certMsg = "";
			var data = gridManager.getData();
			var mydate=new Date();
			//alert(JSON.stringify(data));
			//判断grid 中的数据是否重复或者为空
			var targetMap = new HashMap();
			$.each(data, function (i, v) {
				rowm = "";
				if (v.inv_id) {
					if(v.inva_date)
                	{
					  if(v.inva_date < mydate){
						  msg += "第" + (i + 1) + "行" + "[有效日期]不能小于当前日期。<br>";
					  }
                	}
					if(v.fac_date)
                	{
					  if(v.fac_date > mydate){
						  msg += "第" + (i + 1) + "行" + "[生产日期]不能大于当前日期。<br>";
					  }
                	}
					//如果批号为空给默认批号用于判断
					if (!v.batch_no) {
						v.batch_no = '-';
					}
					if (!v.amount) {
						rowm += "[数量]、";
					}
					if(v.amount<0){
						rowm += "[数量]、";
					}
					if (v.price == "" || v.price == null || v.price == 'undefined') {
						rowm += "[单价]、";
					}
					
					if (rowm != "") {
						rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空并且数量不能小于或等于0。" + "<br>";
					}
					msg += rowm;
					if (v.is_bar == 1 && !v.sn) {
						msg += "第" + (i + 1) + "行按条码管理的材料必须输入条形码。<br>";
					}
					 if (v.is_quality == 1) {
						if (!v.inva_date) {
							msg += "第" + (i + 1) + "行按保质期管理的材料必须输入有效日期。<br>";
						} 
					} 
					 
					 /*
				 	if (v.is_disinfect == 1) {
						if (!v.disinfect_date) {
							msg += "第" + (i + 1) + "行灭菌材料必须输入灭菌日期。<br>";
						} 
					} 
					*/
					if(v.cert_id != null || v.cert_id != ''){
						
						//如果所选材料带有注册证 判断注册证是否过期并提示
						var para = {
							cert_id: v.cert_id
							
						}
						ajaxJsonObjectByUrl("../../queryMatCertDate.do?isCheck=false", para, function (responseData){
							if(responseData.state=="false"){
								certMsg += "第"+(i+1)+"行【证件号："+v.cert_code+"到期日期为"+responseData.end_date+",已过期"+Math.abs(responseData.days)+"天】;";
							}
						}, false);
					}
					//如果条码为空给默认条码用于判断
					if (!v.sn) {
						v.sn = '-';
						v.bar_code = '-';
					}
					var key = v.batch_no == '-' ? v.inv_id + "|" + v.price + "|" + v.batch_no + "|" + v.sn + "|" + v.location_id + "|" + v.bar_code : v.inv_id + "|" + v.batch_no + "|" + v.sn + "|" + v.location_id + "|" + v.bar_code;

					var value = "第" + (i + 1) + "行";
					if (targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
						targetMap.put(key, value+v.inv_code+" "+v.inv_name+" "+v.batch_no+" "+v.price);
					} else {
						msg += targetMap.get(key) + "与" + value + (v.batch_no == '-' ? "材料编码、单价、生产批号、条形码、货位不能重复" : "材料编码、生产批号、条形码、货位不能重复") + "<br>";
					}
					rows = rows + 1;
					store_inv += v.inv_id + ",";
					if (v.is_single_ven == 1) { 
						sup_inv += v.inv_id + ",";
					}
				}
			});
			if (rows == 0) {
				$.ligerDialog.warn("请先添加材料！");
				return false;
			}
		
			//判断仓库材料关系
			if (store_inv.length > 0) {
				//仓库材料对应关系
				var para = {
					inv_ids: store_inv.substring(0, store_inv.length - 1),
					store_id: liger.get("store_code").getValue().split(",")[0]
				}
				ajaxJsonObjectByUrl("../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData) {
					if (responseData.state == "false") {
						msg += "材料" + responseData.inv_ids + "不在该仓库中！<br>";
					}
				}, false);

				if (by_sup_inv == 1) {
					//供应商材料对应关系
					para = {
						inv_ids: store_inv.substring(0, store_inv.length - 1),
						sup_id: liger.get("sup_code").getValue().split(",")[0]
					}
					ajaxJsonObjectByUrl("../../existsMatSupIncludeInv.do?isCheck=false", para, function (responseData) {
						if (responseData.state == "false") {
							msg += "材料" + responseData.inv_ids + "不属于该供应商！<br>";
						}
					}, false);
				}
			}
		
			
			 if(certMsg != ""){
	       		if(!confirm(certMsg+'是否继续保存？')){
	       			
	       			return false;
	       		}
	 		}
			if (msg != "") {
				$.ligerDialog.warn(msg);
				return false;
			}
			if(priceMsg){
				//提示单价不同是否继续保存
				if(by_batch_price  == 0){
					
					if(!confirm(priceMsg+'确定继续保存单据?')){
						return false;
					}
				}else{//提示单价不同并中断保存操作
					$.ligerDialog.warn(priceMsg);
					return false;
				}
			}
			
			var protocolCode =liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue();
			
			var work_detail_data=[];
			if(data.length > 0){
	 			$.each(data, function(d_index, d_content){
	 				if(typeof(d_content.inv_id) != "undefined" && d_content.inv_id != null && d_content.inv_id != ""
	 					&& typeof(d_content.amount_money) != "undefined" && d_content.amount_money != null && d_content.amount_money != ""
	 				    && typeof(d_content.price) != "undefined" && d_content.price != null && d_content.price != ""
	 				){
	 					var detailPara={
	 	 		        	work_item_code:d_content.inv_id,
	 	 		        	work_item_price:d_content.price,
	 	 		        	work_item_money:d_content.amount_money
		 				};
	 					work_detail_data.push(detailPara);
	 				}
	 			});
			}
			var protocol_msg = "";
			if(protocolCode != ""){
				if (by_cont_prot ==1){
					if(is_price_cont >0){
						var parm ={
								work_code:protocolCode,	
								work_note:'priceControl',
								work_select_id:'',
								work_update_id:'',	
								work_sup_id:liger.get("sup_code").getValue().split(",")[0],					
								work_detail_data:JSON.stringify(work_detail_data)
						}
						ajaxJsonObjectByUrl("../../../mate/base/queryControlExecProcess.do?isCheck=false",parm,function (responseData){
							if(responseData.work_flag =="5"){
            					$.ligerDialog.error(responseData.work_msg);
            					protocol_msg =responseData.work_msg;
            					return false;
            				}   			
						},false);
					}
					if (protocol_msg !="") return false;
					if(is_total_cont >0){
						var parm ={
								work_code:protocolCode,	
								work_note:'totalControl',
								work_select_id:'',
								work_update_id:'',	
								work_sup_id:liger.get("sup_code").getValue().split(",")[0],					
								work_detail_data:JSON.stringify(work_detail_data)
						}
						ajaxJsonObjectByUrl("../../../mate/base/queryControlExecProcess.do?isCheck=false",parm,function (responseData){
							if(responseData.work_flag =="5"){
            					$.ligerDialog.error(responseData.work_msg);
            					protocol_msg = responseData.work_msg
            					return false;
            				} 
							else if(responseData.work_flag =="4"){
								if(!confirm(responseData.work_msg)){
									protocol_msg = responseData.work_msg
									return false;
								}
							}
						},false);
					}
					if (protocol_msg!="") return false;
				}
			}
			return true;
		}

		function save() {
			grid.endEdit();
			var inv_datas=gridManager.getData();
			var msg_str="";
			$.each(inv_datas, function (i, v) {
				if(v.is_sec_whg==1){
					msg_str+=v.inv_code+" "+v.inv_name+" ";
				}
			})
			if(msg_str.length>0){
				if(!liger.get("temperature").getValue()){
					$.ligerDialog.warn(msg_str+"是冷链管理材料,需填写验收温度才能成功保存!");
					return;
				}
			}
			if (validateGrid()) {
				var formPara = {
					in_no: $("#in_no").val(),
					bus_type_code: liger.get("bus_type_code").getValue(),
					store_id: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
					store_no: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
					in_date: $("#in_date").val(),
					stocker: liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
					sup_id: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
					sup_no: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
					stock_type_code: liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
					app_dept: liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue().split(",")[0],
					protocol_code: liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue(),
					proj_id: liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
					brief: $("#brief").val(),
					delivery_code: $("#delivery_code").val(),
					examiner: liger.get("examiner").getValue() == null ? "" : liger.get("examiner").getValue(),
					come_from : 1,
					bill_no: $("#bill_no").val(),
					bill_date: $("#bill_date").val(),
					temperature: $("#temperature").val(),
					detailData: JSON.stringify(gridManager.getData())
				}; 
				
				ajaxJsonObjectByUrl("addMatStorageIn.do", formPara, function (responseData) {
					if (responseData.state == "true") {
						//parentFrameUse().query();
						//parentFrameUse().update_open(responseData.update_para);
						//this_close();
						
						$.ligerDialog.confirm('是否继续添加入库单?', function (yes) {
							if (yes) {
								this_refresh();
							} else {
								parentFrameUse().update_open(responseData.update_para);
								this_close();
							}
						}); 
					}
				});
			}
		}

		function loadForm() {

			$.metadata.setType("attr", "validate");
			var v = $("form").validate({
				errorPlacement: function (lable, element) {
					if (element.hasClass("l-textarea")) {
						element.ligerTip({ content: lable.html(), target: element[0] });
					}
					else if (element.hasClass("l-text-field")) {
						element.parent().ligerTip({ content: lable.html(), target: element[0] });
					}
					else {
						lable.appendTo(element.parents("td:first").next("td"));
					}
				},
				success: function (lable) {
					lable.ligerHideTip();
					lable.remove();
				},
				submitHandler: function () {
					$("form .l-text,.l-textarea").ligerHideTip();
				}
			});
			//$("form").ligerForm(); 
		}

		function loadDict() {
			//字典下拉框
			autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, { codes : '2,4,6,8,10,16,18,22,47', is_write:1 }, true);
			//autocompleteAsync("#store_code", "../../queryMatStoreStocker.do?isCheck=false", "id", "text", true, true, isUseAffiStore ? "" : {is_com : 0}, true);
			autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, isUseAffiStore ? {is_write:'1'} : {is_com : 0,is_write:'1'}, true);
			//即墨需求   2017/04/06  根据仓库有多个采购员   gaopei
			var store_id = liger.get("store_code").getValue().split(",")[0];  
			autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
			//一个仓库对应一个采购员
			/* var emp_id=	liger.get("store_code").getValue().split(",")[2];
			var emp_name=liger.get("store_code").getValue().split(",")[3];
			liger.get("stocker").setValue(emp_id);
			liger.get("stocker").setText(emp_name); */
			
			
			autocomplete("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true, "", false, false, 300, false, 350);
			autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true, '', true);
			autocomplete("#app_dept", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, { is_last: '1',is_write:'1' }, false, false, 280);
			
			autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false, 200, false, 240);
			//格式化文本框
			
			$("#in_no").ligerTextBox({ width: 200, disabled: true });
			$("#in_date").ligerTextBox({ width: 200 });
			autodate("#in_date");//默认当前日期
			
			$("#protocol_code").ligerTextBox({width:320,disabled:true,cancelable: false});
			$("#brief").ligerTextBox({ width: 320 });
			$("#bill_date").ligerTextBox({ width: 200 });
			$("#bill_no").ligerTextBox({ width: 160 });
			$("#temperature").ligerTextBox({ width: 160});
			//格式化按钮
			$("#btn_saveColumn").ligerButton({width: 90 });
			$("#save").ligerButton({ click: save, width: 90 });
			$("#close").ligerButton({ click: this_close, width: 90 });
			
			$("#delivery_code").ligerTextBox({width:160});
			var manager = liger.get("store_code").getValue().split(",")[4];
			if(manager){
				autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, true);
			}else{
				autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, false);
				liger.get("examiner").setValue("");
				liger.get("examiner").setText("");
			}
			$("#examiner").ligerTextBox({width:160});
			$("#app_dept").ligerTextBox({ width: 160 });
			$("#sup_code").ligerTextBox({ width: 200 }); 

		}
		 
		//获取仓库采购员
		function charge_store(){
			//即墨需求   2017/04/06  根据仓库有多个采购员  gaopei
	    	//liger.get("store_code").clear(); 
	    	liger.get("store_code").set("parms", {store_id: liger.get("store_code").getValue().split(",")[0]});
	    	liger.get("store_code").reload(); 
	    	var store_id = liger.get("store_code").getValue().split(",")[0];  
			autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
			var manager = liger.get("store_code").getValue().split(",")[4];
			if(manager){
				autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, true);
			}else{
				autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,{manager : manager}, false);
				liger.get("examiner").setValue("");
				liger.get("examiner").setText("");
			}
			
			//一个仓库对应一个采购员
			/* var emp_id=	liger.get("store_code").getValue().split(",")[2];
			var emp_name=liger.get("store_code").getValue().split(",")[3];
			liger.get("stocker").setValue(emp_id);
			liger.get("stocker").setText(emp_name); */
		}
		
		//验收员下拉框
		function change_Examiner(){
			autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false?isCheck=false", "id", "text", true, true,"", false);
		}
		
		function this_refresh() { 
			liger.get("sup_code").clear();
			/* liger.get("stock_type_code").clear(); */
			liger.get("app_dept").clear();
			liger.get("protocol_code").clear();
			liger.get("proj_code").clear();
			autodate("#in_date");//默认当前日期
			$("#brief").val("");
			grid.reload();
			is_addRow();
		}

		function changeBus() {
			if (liger.get("bus_type_code").getValue() != 2 && liger.get("bus_type_code").getValue() != 22) {
				 
				$("#stocker_span").css("display", "none");
				$("#stocker").ligerComboBox({ width: 160, disabled: true, cancelable: false });
				liger.get("stocker").setValue("");
				liger.get("stocker").setText("");

				$("#stock_type_code_span").css("display", "none");
				$("#stock_type_code").ligerComboBox({ width: 160, disabled: true, cancelable: false });
				liger.get("stock_type_code").setValue("");
				liger.get("stock_type_code").setText("");

				$("#sup_code_span").css("display", "none");
				$("#sup_code").ligerComboBox({ width: 160, disabled: true, cancelable: false });
				liger.get("sup_code").setValue("");
				liger.get("sup_code").setText("");
			} else { 	
				$("#stocker_span").css("display", "inline");
				$("#stocker").ligerComboBox({ width: 160, disabled: false });
				
				//即墨需求   2017/04/06  根据仓库有多个采购员   gaopei
				var store_id = liger.get("store_code").getValue().split(",")[0]; 
				autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, false);
				 
				//一个仓库对应一个采购员
				/* var emp_id=	liger.get("store_code").getValue().split(",")[2];
				var emp_name=liger.get("store_code").getValue().split(",")[3];
				liger.get("stocker").setValue(emp_id);
				liger.get("stocker").setText(emp_name); */ 

				$("#stock_type_code_span").css("display", "inline");
				$("#stock_type_code").ligerComboBox({ width: 160, disabled: false });
				liger.get("stock_type_code").selectValue(liger.get("stock_type_code").data[0].id);

				$("#sup_code_span").css("display", "inline");
				$("#sup_code").ligerComboBox({ width: 160, disabled: false });
/* 				liger.get("sup_code").selectValue(liger.get("sup_code").data[0].id); */
			}
		}

		function loadHead() {

			grid = $("#maingrid").ligerGrid({
				columns: [{
					display: '交易编码', name: 'bid_code', width: 100, align: 'left',
				},{
					display: '材料变更号', name: 'inv_no', align: 'left', width: 100, isAllowHide: false, hide: true
				}, {
					display: '材料编码', name: 'inv_code', width: 100, align: 'left',
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>合计：</div>';
						}
					}
				}, {
					display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width: 180, align: 'left',
					editor: {
						type: 'select',
						valueField: 'inv_id',
						textField: 'inv_name',
						selectBoxWidth: '80%',
						selectBoxHeight: 240,
						grid: {
							columns: [{
								display: '交易编码', name: 'bid_code', width: 100, align: 'left', 
							},{
								display: '材料编码', name: 'inv_code', width: 100, align: 'left'
							}, {
								display: '材料名称', name: 'inv_name', width: 240, align: 'left'
							}, {
								display : '别名', name : 'alias', width : 120, align : 'left', 
							}, {
								display: '物资类别', name: 'mat_type_name', width: 100, align: 'left'
							}, {
								display: '规格型号', name: 'inv_model', width: 180, align: 'left'
							}, {
								display: '计量单位', name: 'unit_name', width: 80, align: 'left'
							}, {
								display : '包装规格', name : 'inv_structure', width : 90, align : 'left'
							}, {
								display: '生产厂商', name: 'fac_name', width: 100, align: 'left'
							}, {
								display: '计划单价', name: 'plan_price', width: 90, align: 'left',
								render: function (rowdata, rowindex, value) {
									return formatNumber(value, '${p04006 }', 1);
								}
							}, {
								display: '是否条码', name: 'is_bar', width: 80, align: 'left',
								render: function (rowdata, rowindex, value) {
									return rowdata.is_bar == 1 ? '是' : '否';
								}
							}, { 
								display: '货位', name: 'location_new_name', width: 100, align: 'left'
							}, { 
								display: '零售价', name: 'sell_price', width: 90, align: 'left',
								render: function (rowdata, rowindex, value) {
									return formatNumber(value, '${p04072 }', 1);
								}
							}, {
								display: '包装单位', name: 'pack_name', width: 80, align: 'left'
							}, {
								display: '转换量', name: 'num_exchange', width: 100, align: 'left'
							}, {
								display: '存储编码', name: 'memory_encoding', width: 100, align: 'left'
							}],
							switchPageSizeApplyComboBox: false,
							onSelectRow: function (data) {
								var e = window.event;
								if (e && e.which == 1) {
									f_onSelectRow_detail(data);
								}
							},
							gid: "invGrid", 
							url: '../../queryMatInvListIn.do?isCheck=false&store_id='
							+ liger.get("store_code").getValue().split(",")[0]
							+ '&sup_id=' + liger.get("sup_code").getValue().split(",")[0]
							+ '&is_com=0',
							pageSize: 100,
							onSuccess: function (data, g) { //加载完成时默认选中
								if (grid.editor.editParm) {
									var editor = grid.editor.editParm.record;
									var item = data.Rows.map(function (v, i) {
										return v.inv_name;
									});
									var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
									//加载完执行
									setTimeout(function () {
										g.select(data.Rows[index]);
									}, 80);
								}
							}
						},
						delayLoad: true, keySupport: true, autocomplete: true,// rownumbers : true,
						onSuccess: function (data, grid) {
							this.parent("tr").next(".l-grid-row").find("td:first").focus();
						},
						ontextBoxKeyEnter: function (data) {
							f_onSelectRow_detail(data.rowdata);
						}
					},
					render: function (rowdata, rowindex, value) {
						
						//控制如果不是条码管理材料不能编辑条码 
						if(rowdata.is_bar==0){
							rowdata.notEidtColNames.push("sn");
						}
						
						return rowdata.inv_name;
					}
				}, {
					display: '规格型号', name: 'inv_model', width: 180, align: 'left'
				}, {
					display: '计量单位', name: 'unit_name', width: 60, align: 'left'
				}, {
					display: '生产厂商', name: 'fac_name', width: 180, align: 'left'
				},{
					display: '数量(E)', name: 'amount', width: 80, type: 'float', align: 'right',
					editor: {
						type: 'float'
					}/* ,
					totalSummary: {
						align: 'left',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
						}
					} */
				}, {
					display: '单价(E)', name: 'price', width: 90, align: 'right',
					editor: {
						type: 'numberbox',
						precision: '${p04006 }'
					},
					render: function (rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
				}, {
					display: '金额(E)', name: 'amount_money', width: 90, align: 'right',
					editor: {
						type: 'numberbox',
						precision: '${p04005 }'
					},
					render: function (rowdata, rowindex, value) {
						rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
						return value == null ? "" : formatNumber(value, '${p04005 }', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
						}
					}

				},{
					display: '注册证号(E)', name : 'cert_id',  textField : 'cert_code',width : 300, align : 'left',
					 render : function(rowdata, rowindex, value) {
							return rowdata.cert_code;
						},
					 editor : {
							type : 'select',
							valueField : 'code',
							textField : 'name',
							selectBoxWidth : 250,
							selectBoxHeight : 240,
							keySupport : true,
							autocomplete : true,
							onSelected : function (data){	
						    	if(typeof (data) === "string"){
						    		var formPara="";
								 	formPara = {												 			
								 		cert_id : data
								 	}			 	
						    	}
							}
						}
					
				}, {
					display: '生产批号(E)', name: 'batch_no', width: 100, align: 'left',
					editor: {type: 'text',}
				}, {
					display: '有效日期(E)', name: 'inva_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 90,
					editor: {type: 'date',showSelectBox:false},
				}, {
					display: '批次', name: 'batch_sn', width: 60, align: 'left',
				},{
					display: '灭菌日期(E)', name: 'disinfect_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 90,
					editor: {
						type: 'date',showSelectBox:false
					},
				}, {
					display: '灭菌批号(E)', name: 'disinfect_no', width: 100, align: 'left',
					editor: {
						type: 'text',
					}
				}, {
					display: '条形码(E)', name: 'sn', width: 120, align: 'left',
					editor: {
						type: 'text',
					}
				}, {
					display: '是否个体码', name: 'is_per_bar', width: 100, align: 'left',
					render: function (rowdata, rowindex, value) {
						if (value == 0) {
							return "否";
						} else if (value == 1) {
							return "是";
						} else {
							rowdata.is_per_bar = 0;
							return "否";
						}
					}
				}, {
					display: '是否冷链管理', name: 'is_sec_whg', width: 100, align: 'left',
					render: function (rowdata, rowindex, value) {
						if (value == 0) {
							return "否";
						} else if (value == 1) {
							return "是";
						} else {
							rowdata.is_sec_whg = 0;
							return "否";
						}
					}
				}, {
					display: '院内码', name: 'bar_code', width: 140, align: 'left',
				}, {
					display: '包装单位(E)', name: 'pack_code', textField: 'pack_name', width: 120, align: 'left',
					editor: {
						type: 'select',
						valueField: 'id',
						textField: 'text',
						url: '../../queryMatHosPackage.do?isCheck=false',
						keySupport: true,
						autocomplete: true,
					},
					render: function (rowdata, rowindex, value) {
						var aa = grid.getColumnByName("pack_code");
						aa.editor.parms = { inv_id: rowdata.inv_id };
						return rowdata.pack_name;
					}
				}, {
					display: '转换量(E)', name: 'num_exchange', width: 100, type: 'float', align: 'left',
					editor: {
						type: 'float',
					}
				}, {
					display: '包装件数(E)', name: 'num', width: 100, type: 'float', align: 'left',
					editor: {
						type: 'float',
					},
				}, {
					display: '包装单价', name: 'pack_price', width: 90, align: 'right',
					render: function (rowdata, rowindex, value) {
						rowdata.pack_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
				},  {
					display: '批发单价(E)', name: 'sale_price', width: 90, align: 'right',
					editor: {
						type: 'numberbox',
						precision: '${p04006 }'
					},
					render: function (rowdata, rowindex, value) {
						rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
				}, {
					display: '批发金额', name: 'sale_money', width: 90, align: 'right',
					render: function (rowdata, rowindex, value) {
						rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
						return value == null ? "" : formatNumber(value, '${p04005 }', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
						}
					}
				}, {
					display: '零售单价(E)', name: 'sell_price', width: 90, align: 'right',
					editor: {
						type: 'numberbox',
						precision: '${p04072 }'
					},
					render: function (rowdata, rowindex, value) {
						rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
						return value == null ? "" : formatNumber(value, '${p04072 }', 1);
					}
				}, {
					display: '零售金额', name: 'sell_money', width: 90, align: 'right',
					render: function (rowdata, rowindex, value) {
						//rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
						return value == null ? "" : formatNumber(value, '${p04073 }', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04073 }', 1) + '</div>';
						}
					}
				}, {
					display: '货位名称', name: 'location_name',width: 140, align: 'left',hide: true  
					/*  textField: 'location_name', width: 80, align: 'left',
					editor: {
						type: 'select',
						valueField: 'id',
						textField: 'text',
						url: '../../queryMatLocationDict.do?isCheck=false',
						keySupport: true,
						autocomplete: true,
					} */
				},{
					display: '生产日期(E)', name: 'fac_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 90,
					editor: {
						type: 'date',showSelectBox:false
					}
				},{
					display: '备注(E)', name: 'note', width: 240, align: 'left',
					editor: {
						type: 'text',
					}
				}, 
				
				{
					display: '订单关系', name: 'order_rela', align: 'left', isAllowHide: false, width:100, hide: true
				},
				{
					display: '货位', name: 'location_new_name', width: 240, align: 'left' 
					 
				}],
				usePager: false, width: '100%', height: '98%', 
				checkbox: true, enabledEdit: true, alternatingRow: false,//取消各行变色,否则和材料有效期必填数据的提示色有冲突
				onBeforeEdit: f_onBeforeEdit, onBeforeSubmitEdit: f_onBeforeSubmitEdit, onAfterEdit: f_onAfterEdit,
				isScroll: true, rownumbers: true, delayLoad: true,//初始化明细数据
				selectRowButtonOnly: true,//heightDiff: -10,
				rowAttrRender: function (rowdata,rowid) {//如果材料 有效期 是必填项,则此行数据显示红色
                     if (rowdata.is_quality == 1)
                     {
                         return "style='background:#FFC784;!important'";
                     }
                     return "";
                 },
				toolbar: {
					items: [{
						text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete'
					}, {
						line: true
					}, /* {
						text: '订单导入（<u>O</u>）', id: 'order_imp', click: order_imp, icon: 'up'
					}, {
						line: true
					}, */ {
						text: '配套导入（<u>M</u>）', id: 'match_imp', click: match_imp, icon: 'up'
					}, {
						line: true
					}, {
						text: '协议导入（<u>X</u>）', id: 'protocol_imp', click: protocol_imp, icon: 'up'
					}, {
						line: true
					}, {
						text: '复制材料（<u>C</u>）', id:'copy', click: copy, icon:'copy' 
					}, {
						line: true
					}, { 
						text: '选择材料（<u>Z</u>）', id:'inv_choice', click: inv_choice, icon:'add' 
					}, {
						line: true
					}, {
						text: '新增材料（<u>I</u>）', id:'add_inv', click: add_inv, icon:'add' 
					}
					]
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();

		
		}
		
		function inv_choice(){
			var sup_id ;
	    	var sup_text; 
	    	var sup_id = liger.get("sup_code").getValue();
	    	
			if (sup_id == null || sup_id == "" || sup_id == "undefined") {
				$.ligerDialog.error("请先选择供应商！");
				return;
			}else{
				sup_id = liger.get("sup_code").getValue();
	    		sup_text = liger.get("sup_code").getText();
			}
			
			var store_id;
			var store_text;
			
			var store_id = liger.get("store_code").getValue();
			if (store_id == null || store_id == "" || store_id == "undefined") {
				$.ligerDialog.error("请先选择仓库！");
				return;
			}else{
				store_id = liger.get("store_code").getValue();
				store_text = liger.get("store_code").getText();
			}
			
	    	var para = "sup_id=" + sup_id +"&sup_text=" + sup_text 
	    		+"&store_id=" + store_id +"&store_text=" + store_text;
	    	$.ligerDialog.open({
				title: '选择材料',
				height: 500,
				width: 900,
				url: 'choiceInvBySupPage.do?isCheck=false&'+para,
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
			});
		}
		
		//复制材料
	    function copy () {
			var data_copys = grid.getCheckedRows();
			if (data_copys.length == 0){
				$.ligerDialog.error('请选择行');
				return;
			}else{
				for(var i = 0, length = data_copys.length; i<length; i++){         // 把数组的每个data都复制一遍，即深复制
					data_copys[i] = $.extend({},data_copys[i]) ;
					data_copys[i].amount = '1';
				}
				var gridRowData = grid.getData();
				//删除空行
				for (var i = 0; i < gridRowData.length; i++) {
					if (gridRowData[i] && !gridRowData[i].inv_id) {
						grid.deleteRow(i);
						gridRowData = grid.getData();
						i--;
					}
				}
				//grid.deleteRow(i);
				/* gridRowData.forEach(function (item, index) {
					
				}); */
				grid.addRows(data_copys);
				//grid.addRow();
			}
		}
		
		//键盘事件
		function loadHotkeys() {
			hotkeys('D', remove);
			hotkeys('O', order_imp);
			hotkeys('M', match_imp);
			hotkeys('X', protocol_imp);
		}
		  function btn_saveColumn(){
		  		
			   var path = window.location.pathname+"/maingrid";
			   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
			   saveColHeader({
				   grid:grid,
				   path:path,
				   url:url,
				   callback:function(data){
					   $.ligerDialog.success("保存成功");
				   }
			   });
	}
		function add_rows(data) {
			//先清除数据然后再添加
			grid.deleteAllRows();
			grid.addRows(data);
		}

		function order_imp() {
			var sup_id = liger.get("sup_code").getValue();
			if (sup_id == null || sup_id == "" || sup_id == "defined") {
				$.ligerDialog.error("请先选择供应商！");
				return;
			}
			var para = "sup_id=" + sup_id + "&sup_text=" + liger.get("sup_code").getText();
			$.ligerDialog.open({
				title: '订单导入',
				height: 550,
				width: 1000,
				url: 'orderImpPage.do?isCheck=false&' + para,
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1,
			});
		}
		function match_imp() {
			var para = "store_id=" + liger.get("store_code").getValue() +
				"&store_text=" + liger.get("store_code").getText();
			$.ligerDialog.open({
				title: '配套导入',
				height: 550,
				width: 1000,
				url: 'matchImpPage.do?isCheck=false&' + para,
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
			});
		}
		function protocol_imp() {
			var sup_id = liger.get("sup_code").getValue();
			if (sup_id == null || sup_id == "" || sup_id == "defined") {
				$.ligerDialog.error("请先选择供应商！");
				return;
			}
			var protocol_id = liger.get("protocol_code").getValue();
			if (protocol_id == null || protocol_id == "" || protocol_id == "defined") {
				$.ligerDialog.error("请先选择协议号！");
				return;
			}
			var para = "sup_id=" + liger.get("sup_code").getValue() +
				"&sup_text=" + liger.get("sup_code").getText() +
				"&protocol_id=" + protocol_id;
			$.ligerDialog.open({
				title: '协议导入',
				height: 550,
				width: 1000,
				url: 'protocolImpPage.do?isCheck=false&' + para,
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
			});
		}
		function sn_imp() {

		}

		function remove() {
			grid.deleteSelectedRow();
		}

		var rowindex_id = "";
		var column_name = "";
		function f_onBeforeEdit(e) {
			rowindex_id = e.rowindex;
			column_name = e.column.name;
			if (column_name == "pack_code") {
				var pack = grid.getColumnByName("pack_code");
				pack.editor.url = '../../queryMatHosPackage.do?isCheck=false&inv_id=' + e.record.inv_id;;
			}
			if(column_name=='cert_id'){
				var certId = grid.getColumnByName("cert_id");
				certId.editor.url='../../queryMatInvCertId.do?isCheck=false&inv_id='+e.record.inv_id;
			}
		}
		//选中回充数据
		function f_onSelectRow_detail(data, rowindex, rowobj) {
			selectData = "";
			selectData = data;
			//alert(JSON.stringify(data)); 
			//回充数据 
			if (selectData != "" || selectData != null) {
				if (column_name == "inv_id") {
					grid.updateRow(rowindex_id, {
						bid_code: data.bid_code,
						inv_code: data.inv_code,
						inv_no: data.inv_no,
						inv_model: data.inv_model == null ? "" : data.inv_model,
						unit_name: data.unit_name == null ? "" : data.unit_name,
						pack_code: data.pack_code == null ? "" : data.pack_code,
						pack_name: data.pack_name == null ? "" : data.pack_name,
						num_exchange: data.num_exchange == null ? "" : data.num_exchange,
						is_batch: data.is_batch == null ? 0 : data.is_batch,
						is_bar: data.is_bar == null ? 0 : data.is_bar,
						is_per_bar: data.is_per_bar == null ? 0 : data.is_per_bar,
						is_quality: data.is_quality == null ? 0 : data.is_quality,
						is_disinfect: data.is_disinfect == null ? 0 : data.is_disinfect,
						is_highvalue: data.is_highvalue == null ? 0 : data.is_highvalue,
						price: data.plan_price == null ? "" : data.plan_price,
						location_id: data.location_id == null ? "" : data.location_id,
						location_new_name: data.location_new_name == null ? "" : data.location_new_name,
						sell_price: data.sell_price == null ? "" : data.sell_price,
					    fac_name : data.fac_name == null ? "" : data.fac_name,
						cert_id : data.cert_id == null ? "" : data.cert_id,	
						cert_code : data.cert_code == null ? "" : data.cert_code, 
						is_inv_bar : data.is_inv_bar == null ? "" : data.is_inv_bar,	
						is_sec_whg : data.is_sec_whg == null ? "" : data.is_sec_whg,	
						bar_code_new : data.bar_code_new == null ? "" : data.bar_code_new 
					});
					setTimeout(function (){
						grid.endEditToNext();
					},300)
				}
			}
			//手动移除grid IE下只能移除......   隐藏不生效
			// $('.l-box-select-lookup').remove();
			return true;
		}
		function f_onSelectRow(data, rowindex, rowobj) {
			return true;
		}
		// 编辑单元格提交编辑状态之前作判断限制
		function f_onBeforeSubmitEdit(e) {
			/*
			if (e.column.name == "inv_id" && !e.value){
				//$.ligerDialog.warn('请选择材料！');
				return false;
			}
			if (e.column.name == "amount" && !e.value){
				//$.ligerDialog.warn('数量不能为0！');
				return false;
			}
			if (e.column.name == "price" && !e.value){
				//$.ligerDialog.warn('单价不能为0！');
				return false;
			}
			if (e.column.name == "sn"){
				if(e.record.is_bar == 1 && !e.value){
					//$.ligerDialog.warn('按条码管理的材料必须输入条形码！');
					return false;
				}
			}
			if (e.column.name == "inva_date"){
				if(e.record.is_quality == 1 && !e.value){
					//$.ligerDialog.warn(按保质期管理的材料必须输入有效日期！');
					return false;
				}
			}
			if (e.column.name == "disinfect_date"){
				if(e.record.is_disinfect == 1 && !e.value){
					//$.ligerDialog.warn('行灭菌材料必须输入灭菌日期！');
					return false;
				}
			}
			*/
			return true;
		}
		// 跳转到下一个单元格之前事件
		function f_onAfterEdit(e) {
			if (e.value != "") {
				if (e.column.name == "inv_id") {
					if(e.record.is_quality){
						//判断是否为自动有效日期
						if ('${p04009 }' != 0) {
							grid.updateCell('inva_date', getDateAddDay(new Date(), '${p04009 }'), e.rowindex);
						}
					}
					if(e.record.is_inv_bar){
						//条形码自动默认为材料的品种码
						grid.updateCell('sn', e.record.bar_code_new, e.rowindex);
					}
					
					
				} else if (e.column.name == "amount") {
					//自动计算金额
					if (e.record.price != undefined && e.record.price != "") {
						grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
					}
					//自动计算零售金额
					if (e.record.sell_price != undefined && e.record.sell_price != "") {
						grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
					}
					//自动计算批发金额
					if (e.record.sale_price != undefined && e.record.sale_price != "") {
						grid.updateCell('sale_money', e.value * e.record.sale_price, e.rowindex);
					}
					//自动计算包装件数
					if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
						grid.updateCell('num', e.record.num_exchange ? e.value / e.record.num_exchange : 0, e.rowindex);
					}
				} else if (e.column.name == "price") {
					//自动计算金额
					if (e.record.amount != undefined && e.record.amount != "") {
						grid.updateCell('amount_money', e.record.amount ? e.value * e.record.amount : 0, e.rowindex);
					}
					//计算包装单价
					if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
						grid.updateCell('pack_price', e.value * e.record.num_exchange, e.rowindex);
					}
				} else if (e.column.name == "num_exchange") {
					//自动计算包装件数
					if (e.record.amount != undefined && e.record.amount != "") {
						grid.updateCell('num', e.value ? e.record.amount / e.value : 0, e.rowindex);
					}
					//自动包装单价
					if (e.record.price != undefined && e.record.price != "") {
						grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
					}
				} else if (e.column.name == "num") {
					//自动计算数量与金额
					if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
						grid.updateCell('amount', e.value * e.record.num_exchange, e.rowindex);
						if (e.record.price != undefined && e.record.price != "") {
							grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
						}
						if (e.record.sell_price != undefined && e.record.sell_price != "") {
							grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
						}
						if (e.record.sale_money != undefined && e.record.sale_money != "") {
							grid.updateCell('sale_price', e.record.amount ? e.record.sale_money / e.record.amount : 0, e.rowindex);
						} else if (e.record.sale_price != undefined && e.record.sale_price != "") {
							grid.updateCell('sale_money', e.record.amount * e.record.sale_price, e.rowindex);
						}
					}
				} else if (e.column.name == "sell_price") {
					//自动计算零售金额
					if (e.record.amount != undefined && e.record.amount != "") {
						grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
					}
				} else if (e.column.name == "sale_price") {
					//自动计算批发金额
					if (e.record.amount != undefined && e.record.amount != "") {
						grid.updateCell('sale_money', e.value * e.record.amount, e.rowindex);
					}
				} else if (e.column.name == "sale_money") {
					//自动计算批发单价
					if (e.record.amount != undefined && e.record.amount != "") {
						grid.updateCell('sale_price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
					}
				} else if (e.column.name == "amount_money") {
					//自动计算金额
					if (e.record.price != undefined && e.record.price != "" && e.record.price != 0) {
						//根据四则运算进行处理，计算机默认加减乘除有问题 比如14/0.07
						 
						grid.updateCell('amount', e.record.price ? division(e.value , e.record.price,2) : 0, e.rowindex);
					} 
					if (e.record.amount != undefined && e.record.amount != "") {
						if(formatNumber(e.record.price,2,-1)!=formatNumber(e.record.amount ? e.value / e.record.amount : 0,2,-1))
							{
						grid.updateCell('price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
					    
							}
						
				}
					//计算包装单价
					if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
						grid.updateCell('pack_price', e.record.amount * e.record.num_exchange ? e.value / e.record.amount * e.record.num_exchange : 0, e.rowindex);
					}
				} else if(e.column.name == "batch_no"){
					if(e.record.batch_no){
						$.ajax({
							url:"../../getInvaTimeByBatchNo.do?isCheck=false",
							data:{
								inv_id:e.record.inv_id,
								inv_code:e.record.inv_code,
								inv_no:e.record.inv_no,
								batch_no:e.record.batch_no
							},
							type:"post",
							dataType:"json",
							success:function(msg){
								var inva_date=msg.inva_date?msg.inva_date:"";
								inva_date=inva_date.split(" ")[0];
								grid.updateCell('inva_date', inva_date, e.rowindex);
							},
							async:false
						})
					}
					
				}
			}
			grid.updateTotalSummary();
			return true;
		}

		function getData() {
			var manager = $("#maingrid").ligerGetGridManager();
			var data = manager.getData();
			return JSON.stringify(data);
		}

		function is_addRow() {
			setTimeout(function () { //当数据为空时 默认新增一行
				grid.addRow();
			}, 100);
		}
		
		function add_inv(){
	    	parent.$.ligerDialog.open({
				title: '物资添加',
				height: 550,
				width: 1000,
				url: 'hrp/mat/info/basic/inv/matInvAddPage.do?isCheck=false&',
				modal: true, showToggle: false, showMax: true, showMin:false, isResize: true, top: 10,
				parentframename: window.name,
			});
		}

		function this_close() {
			frameElement.dialog.close();
		}
		function changeIndate(){
			var sup_id = liger.get("sup_code").getValue().split(",")[0];
			var in_date = liger.get("in_date").getValue();
			if(sup_id && in_date){
				$.ajax({
					url:"../../getMatPactFkxyInfo.do?isCheck=false",
					data:{
						sup_id:sup_id,
						in_date :in_date	
					},
					type:"post",
					dataType:"json",
					success:function(msg){
						var pactFkxyInfo=msg.pactFkxyInfo?msg.pactFkxyInfo:"";
						
						liger.get("protocol_code").setValue("");
						liger.get("protocol_code").setValue(pactFkxyInfo);
						is_total_cont =msg.is_total_cont?msg.is_total_cont:0;
						is_price_cont =msg.is_price_cont?msg.is_price_cont:0;
					},
					async:false
				})
			}
			
		}
	</script>

</head>

<body onload="is_addRow()">
<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post" id="form1">
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color:red">*</span>入库单号：
</td>
<td align="left" class="l-table-edit-td">
	<input name="in_no" type="text" id="in_no" value="自动生成" ltype="text" disabled="disabled" required="true" validate="{required:true}"
	/>
</td>
<td align="right" class="l-table-edit-td">
	<span style="color:red">*</span>业务类型：
</td>
<td align="left" class="l-table-edit-td">
	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	<span style="color:red">*</span>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：
</td>
<td align="left" class="l-table-edit-td">
	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
</td>
</tr>
<tr>
	<td align="right" class="l-table-edit-td">
		<span style="color:red">*</span>制单日期：
</td>
<td align="left" class="l-table-edit-td">
	<input class="Wdate" name="in_date" id="in_date" type="text" required="true" onchange="changeIndate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	<span id="stocker_span" style="color:red">*</span>采&nbsp;&nbsp;购&nbsp;&nbsp;员：
</td>
<td align="left" class="l-table-edit-td">
	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	<span id="stock_type_code_span" style="color:red">*</span>采购类型：
</td>
<td align="left" class="l-table-edit-td">
	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
</td>
</tr>
<tr>
	<td align="right" class="l-table-edit-td">
		<span id="sup_code_span" style="color:red">*</span>供&nbsp;&nbsp;应&nbsp;&nbsp;商：
</td>
<td align="left" class="l-table-edit-td">
	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	申请科室：
</td>
<td align="left" class="l-table-edit-td">
	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	协议编号：
</td>
<td align="left" class="l-table-edit-td">
	<input name="protocol_code" type="text" id="protocol_code" ltype="text"  validate="{required:false}" />
</td>
</tr>
<tr>
	<td align="right" class="l-table-edit-td">
		项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：
	</td>
	<td align="left" class="l-table-edit-td">
		<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
	</td>
	
	<td align="right" class="l-table-edit-td" >验&nbsp;&nbsp;收&nbsp;&nbsp;员：</td>
	<td align="left" class="l-table-edit-td" >
		<input name="examiner" type="text" id="examiner" ltype="text" validate="{required:false,maxlength:20}" />
	</td>
	
	
	
	<td align="right" class="l-table-edit-td" >货&nbsp;&nbsp;单&nbsp;&nbsp;号：</td>
	<td align="left" class="l-table-edit-td"  >
		<input name="delivery_code"  type="text" id="delivery_code" ltype="text" validate="{required:false,maxlength:20}" />
	</td>
</tr>
<tr>
	<td align="right" class="l-table-edit-td">发票日期：</td>
	<td align="left" class="l-table-edit-td">
		<input class="Wdate" name="bill_date" id="bill_date" type="text"  onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})"/>
	</td>
	<td align="right" class="l-table-edit-td">发&nbsp;&nbsp;票&nbsp;&nbsp;号：</td>
	<td align="left" class="l-table-edit-td">
		<input name="bill_no" type="text" id="bill_no"  ltype="text" required="true"  />
	</td>
	<td align="right" class="l-table-edit-td">
		摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：
	</td> 
		<td align="left" class="l-table-edit-td" >
		<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
	</td>						
</tr>
<tr>
	<td align="right" class="l-table-edit-td">验收温度：</td>
	<td align="left" class="l-table-edit-td">
		<input name="temperature" type="text" id="temperature"  ltype="text" />
	</td>
</tr>
</table>
</form>
<div style="width: 100%; height: 100%;">
<div id="maingrid"></div>
<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
<tr>
	<td align="center" class="l-table-edit-td">
		<button id="save" accessKey="B"><b>保存（<u>B</u>）</b></button> &nbsp;&nbsp;
		<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
	</td>
</tr>
</table>
</div>
</body>

</html>