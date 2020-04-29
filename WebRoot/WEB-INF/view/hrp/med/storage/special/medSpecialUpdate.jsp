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
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
	 <link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
    var grid;
    var invGrid;
    var gridManager;
    var falg = "";
    var state = '${medInMain.state}';
    var group_id = '${medInMain.group_id}';
    var hos_id = '${medInMain.hos_id}';
    var copy_code = '${medInMain.copy_code}';
    var special_id = '${medInMain.special_id}';
    var special_no = '${medInMain.special_no}';
    var in_id = '${medInMain.in_id}';
    var year = '${medInMain.year}';
    var month = '${medInMain.month}';
    var store_id = '${medInMain.store_id}';
    var bus_type_code = '${medInMain.bus_type_code}';
    var by_sup_inv = '${p08021 }';
    var by_batch_price = '${p08030 }';
	 var isEmpByDept = '${p08043 }' == 1 ? true : false;
     $(function (){
		loadDict()//加载下拉框
        //loadForm();
		loadHead();
		
		$("#store_id").bind("change",function(){
			
	    	//liger.get("store_id").clear();
	    	liger.get("store_id").set("parms", {store_id: liger.get("store_id").getValue().split(",")[0]});
	    	liger.get("store_id").reload();
	    	
	    	loadHead();
	    	grid.reRender();
		})
		
		$("#sup_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
	    	liger.get("protocol_code").clear();
	    	liger.get("protocol_code").set("parms", {sup_id: liger.get("sup_id").getValue().split(",")[0]});
	    	liger.get("protocol_code").reload();
		})
		
		$("#dept_id").bind("change",function(){
			if(isEmpByDept && liger.get("dept_id").getValue()){
		    	liger.get("dept_emp").clear();
		    	liger.get("dept_emp").set("parms", {dept_id: liger.get("dept_id").getValue().split(",")[0]});
		    	liger.get("dept_emp").reload();
			}
		})
     });  
 	
 	function validateGrid() {  
 		//主表
 		if(liger.get("store_id").getValue() == null || liger.get("store_id").getValue() == ""){
 			$.ligerDialog.warn("仓库不能为空");  
 			return false;  
 		}
 		if($("#make_date").val() == null || $("#make_date").val() == ""){
 			$.ligerDialog.warn("编制日期不能为空");  
 			return false;  
 		}
 		if(liger.get("sup_id").getValue() == null || liger.get("sup_id").getValue() == ""){
 			$.ligerDialog.warn("供应商不能为空");  
 			return false;  
 		} 
 		if(liger.get("stocker").getValue() == null || liger.get("stocker").getValue() == ""){
 			$.ligerDialog.warn("采购员不能为空");  
 			return false;  
 		}
 		if(liger.get("dept_id").getValue() == null || liger.get("dept_id").getValue() == ""){
 			$.ligerDialog.warn("领料科室不能为空");  
 			return false;  
 		}
 		/* if(liger.get("dept_emp").getValue() == null || liger.get("dept_emp").getValue() == ""){
 			$.ligerDialog.warn("领料人不能为空");  
 			return false;  
 		} */ 
 		//明细
 		var amount_money_true="";
 		var amount_money_false="";
 		var rowm = "";
 		var msg="";
 		var rows = 0;
 		var store_inv = "";  //用于判断是否属于该仓库
 		var sup_inv = ""; //用于判断唯一供应商
 		var priceMsg = "";
 		var certMsg = "";
 		var data = gridManager.getData();
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.inv_id) {
				if (!v.amount) {
					rowm+="[数量]、";
				}  
// 				if (v.price == "" || v.price == null  || v.price == 'undefined') {  
// 					rowm+="[单价]、"; 
// 				} 
				if(rowm != ""){
					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且数量不能为0。" + "<br>";
				}
				msg += rowm;
				if(v.is_bar == 1 && !v.sn){
					msg += "第"+(i+1)+"行按条码管理的药品必须输入条形码。<br>";
				}
				if(v.is_quality == 1){
					if(!v.inva_date){
						msg += "第"+(i+1)+"行按保质期管理的药品必须输入有效日期。<br>";
					}else{
						//如果药品按保质期管理，则判断有效日期是否与上一批号一致
						var para = {
							inv_id: v.inv_id, 
							batch_no: !v.batch_no ? "-" : v.batch_no,   
							inva_date: v.inva_date 
						}
						ajaxJsonObjectByUrl("../../queryMedInvBatchInva.do?isCheck=false", para, function (responseData){
							if(responseData.state=="false"){
								msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的有效日期应为"+responseData.inva_date+"<br>";
							}
						}, false);
					}
				}
				if(v.cert_id != null || v.cert_id != ''){
					
					//如果所选药品带有注册证 判断注册证是否过期并提示
					var para = {
						cert_id: v.cert_id
						
					}
					ajaxJsonObjectByUrl("../../queryMedCertDate.do?isCheck=false", para, function (responseData){
						if(responseData.state=="false"){
							certMsg += "第"+(i+1)+"行【证件号："+v.cert_code+"到期日期为"+responseData.end_date+",已过期"+Math.abs(responseData.days)+"天】;";
						}
					}, false);
				}
				if(v.is_disinfect == 1){
					if(!v.disinfect_date){
						msg += "第"+(i+1)+"行灭菌药品必须输入灭菌日期。<br>";
					}else{
						//如果药品是灭菌药品，则判断灭菌日期是否与上一批号一致
						var para = {
							inv_id: v.inv_id, 
							batch_no: !v.batch_no ? "-" : v.batch_no,   
							disinfect_date: v.disinfect_date 
						}
						ajaxJsonObjectByUrl("../../queryMedInvBatchDisinfect.do?isCheck=false", para, function (responseData){
							if(responseData.state=="false"){
								msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的灭菌日期应为"+responseData.disinfect_date+"<br>";
							}
						}, false);
					}
				}
				if (v.batch_no != "-") {
					//同一批号的单价必须一致
					var para = {
						inv_id: v.inv_id,
						batch_no: v.batch_no,
						price: v.price
					}
					ajaxJsonObjectByUrl("../../queryMedInvBatchPrice.do?isCheck=false", para, function (responseData) {
						if (responseData.state == "false") {
							priceMsg += "第" + (i + 1) + "行批号" + v.batch_no + "对应的单价应为" + responseData.price + ", ";// + ", <br>";
						}
					}, false);
				}

				//如果条码为空给默认条码用于判断
				if (!v.sn) {
					v.sn = '-';
					v.bar_code = '-';
				}
				var key= v.batch_no == '-' ? v.inv_id+"|"+v.price+"|"+v.batch_no+"|"+v.sn+"|"+v.location_id : v.inv_id+"|"+v.batch_no+"|"+v.sn+"|"+v.location_id;
				var value="第"+(i+1)+"行";
				if(!targetMap.get(key)){
					targetMap.put(key ,value);
				}else{
					msg += targetMap.get(key)+"与"+value + v.batch_no == '-' ? "药品编码、单价、生产批号、条形码、货位不能重复" : "药品编码、生产批号、条形码、货位不能重复" + "<br>";
				}
				rows = rows + 1;
				store_inv += v.inv_id + ",";
				if(v.is_single_ven == 1){
					sup_inv += v.inv_id + ",";
				}
			}
			if(v.amount_money>0){
				amount_money_true="1";
			}
			if(v.amount_money<0){
				amount_money_false="-1";
			}
			if(amount_money_true!="" && amount_money_false!= "" && amount_money_true!=amount_money_false  ){
				msg = "金额不能同时存在正数和负数，请修改！！！";
			}
			if(amount_money_true != "" ){
				//fag为1是证明是正数，业务类型为专购物入库
				falg="1"
			}
			if(amount_money_false != "" ){
				//fag为1是证明是正数，业务类型为专购品退货
				falg="2"
			}
 		});
 		if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}
 		//判断仓库药品关系
 		if(store_inv.length > 0){
 			//仓库药品对应关系
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_id").getValue().split(",")[0]
			}
			var is_flag = ajaxJsonObjectByUrl("../../existsMedStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					msg += "药品"+responseData.inv_ids+"不在该仓库中！<br>";
				}
			}, false);
			//供应商药品对应关系
			if (by_sup_inv == 1) {
				para = {
						inv_ids: store_inv.substring(0, store_inv.length-1), 
						sup_id: liger.get("sup_id").getValue().split(",")[0]
					}
					is_flag = ajaxJsonObjectByUrl("../../existsMedSupIncludeInv.do?isCheck=false", para, function (responseData){
						if(responseData.state=="false"){
							//$.ligerDialog.warn("药品"+responseData.inv_ids+"不属于该供应商！");  
							//return false;
							msg += "药品"+responseData.inv_ids+"不属于该供应商！<br>";
						}
				}, false);
			}
			
		}
		//如果存在唯一供应商的药品则判断是否是唯一供应商
		if(sup_inv.length > 0){
			var para = {
				inv_ids: sup_inv.substring(0, sup_inv.length-1), 
				sup_id: liger.get("sup_id").getValue().split(",")[0]
			}
			var is_flag = ajaxJsonObjectByUrl("../../existsMedInvOnlySup.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("药品"+responseData.inv_ids+"不符合唯一供应商要求！");  
					//return false;
					msg += "药品"+responseData.inv_ids+"不符合唯一供应商要求！<br>";
				}
			}, false);
		}
		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		}
 		if(certMsg != ""){
       		if(!confirm(certMsg+'是否继续保存？')){
       			
       			return false;
       		}
 		}
 		if(priceMsg){
			//提示单价不同是否继续保存
			if(by_batch_price  == 0){
				/* $.ligerDialog.confirm(priceMsg+'确定继续保存单据?', function (yes){
					if(!yes){
						return false;
					}
				}); */
				if(!confirm(priceMsg+'确定继续保存单据?')){
					return false;
				}
			}else{//提示单价不同并中断保存操作
				$.ligerDialog.warn(priceMsg);
				return false;
			}
		} 
 		return true ;
 	}
     
     function save(){
		grid.endEdit();
    	if(validateGrid()){
	        var formPara={
	        		falg:falg,
	        		special_id : '${medInMain.special_id}',
	        		special_no : '${medInMain.special_no}',
	        		in_no : $("#in_no").val(),
					store_id : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0],
					store_no : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[1],
					make_date : $("#make_date").val(),
					dept_id : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0],
					dept_no : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[1],
					stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
					sup_id : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0],
					sup_no : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[1],
					stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
					dept_emp : liger.get("dept_emp").getValue() == null ? "" : liger.get("dept_emp").getValue().split(",")[0],		
					//ctrl_type_code : liger.get("ctrl_type_code").getValue() == null ? "" : liger.get("ctrl_type_code").getValue(),
					protocol_code :liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue().split(",")[1],
					proj_id : liger.get("proj_id").getValue() == null ? "" : liger.get("proj_id").getValue().split(",")[0],
					brief : $("#brief").val(),
					bill_date : $("#bill_date").val(),
					bill_no : $("#bill_no").val(),
					old_money_sum : $("#money_sum").val(),
					detailData : JSON.stringify(gridManager.getData())
				};
		        ajaxJsonObjectByUrl("updateMedSpecial.do?isCheck=false",formPara,function(responseData){
		            if(responseData.state=="true"){
		            	queryDetail();
		            	parentFrameUse().query();
		            }
		        });
	    	}
    }
 	
 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name : 'special_id',
 			value : '${medInMain.special_id}'
 		});

     	//加载查询条件
     	grid.loadData(grid.where);
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
   
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
						display : '交易编码', name : 'bid_code', width : 100, align : 'left',
					}, {
			        	display : '药品编码', name : 'inv_code', width : 110, align : 'left',
			        	totalSummary:{ 
			        		render: function (suminf, column, cell){
		                            return '<div>合计</div>';
		                    },align: 'left'},
					}, {
						display : '药品名称(E)', name : 'inv_id', textField : 'inv_name', width : 240, align : 'left',
						editor : {
							type : 'select',
							valueField : 'inv_id',
							textField : 'inv_name',
							selectBoxWidth : "80%",
							selectBoxHeight : 240,
							grid : {
								columns : [ 
								        {display : '交易编码', name : 'bid_code', width : 100, align : 'left'
											}, 
								        {display : '药品编码', name : 'inv_code', width : 110, align : 'left'
											}, 
										{display : '药品名称', name : 'inv_name', width : 240, align : 'left'
											}, 
										{display : '药品类别', name : 'med_type_name', width : 100, align : 'left'
											},
										{display : '规格型号', name : 'inv_model', width : 200, align : 'left'
											}, 
										{display : '计量单位', name : 'unit_name', width : 60, align : 'left'
											}, 
										{display : '计划单价', name : 'plan_price', width : 110, align : 'right',
												render : function(rowdata, rowindex, value) {
													return formatNumber(rowdata.plan_price, '${p08006}', 1);
												}
										}, 
										{display : '供应商', name : 'sup_name', width : 200, align : 'left', hide: true
											}, 
										{display : '生产厂商', name : 'fac_name', width : 200, align : 'left'
											}, 
										{display : '包装单位', name : 'pack_name', width : 80, align : 'left'
											}, 
										{display : '转换量', name : 'num_exchange', width : 80, align : 'left'
											}, 
										{display : '是否条码', name : 'is_bar', width : 80, align : 'left',
												render : function(rowdata, rowindex, value) {
													return rowdata.is_bar == 1 ? '是' : '否';
												}
											}, 
										{display : '证件号', name : 'cert_code', width : 200, align : 'left'
											}, 
										{display : '货位名称', name : 'location_name', width : 100, align : 'left',hide:true
											}, 
										{display : '货位名称', name : 'location_new_name', width : 100, align : 'left'
											},
										{display : '零售价', name : 'sell_price', width : 110, align : 'left'
											} 
										],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : '../../queryMedInvListSpecial.do?isCheck=false&store_id='+ 
											liger.get("store_id").getValue().split(",")[0] +
											'&sup_id=' + liger.get("sup_id").getValue().split(",")[0],
									pageSize : 500,
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
								
								delayLoad : false, keySupport : true, autocomplete : true,
								onSuccess : function() {
									this.parent("tr").next(".l-grid-row").find("td:first").focus();
								},
								onSuccess : function() {
									this.parent("tr").next(".l-grid-row").find("td:first").focus();
								},
								ontextBoxKeyEnter: function (data) {
									f_onSelectRow_detail(data.rowdata);
								}
							},
							render : function(rowdata, rowindex, value) {
								return rowdata.inv_name;
							}
					}, {
						display : '规格型号', name : 'inv_model', width : 200, align : 'left'
					}, {
						display : '计量单位', name : 'unit_name', width : 60, align : 'left'
					}, {
						display : '数量(E)', name : 'amount', width : 80, type : 'number', align : 'left',editor : {type : 'number'},
							render : function(rowdata, rowindex, value) {
								return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
							}
					}, {
						display : '单价(E)', name : 'price', width : 110, align : 'right', type : 'number',editor : {type : 'number'},
							render : function(rowdata, rowindex, value) {
								rowdata.price = value == null ? "" : formatNumber(value, '${p08006}', 0);
								return value == null ? "" : formatNumber(value, '${p08006 }', 1);
							}
					}, {
						display : '金额', name : 'amount_money', width : 130, type : 'number', align : 'right',
						editor : {
							type : 'numberbox',
							precision : '${p08005}'
						},
							render : function(rowdata, rowindex, value) {
								rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005}', 0);
								return value == null ? "" : formatNumber(value, '${p08005 }', 1);
							},
							totalSummary:{render: function (suminf, column, cell){
	                            return '<div>'+formatNumber(suminf.sum ==null ? 0 :suminf.sum, '${p08005}', 1)+'</div>';
		                    },align: 'left'},
					}, {
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
						display : '生产批号(E)', name : 'batch_no', width : 150, align : 'left',editor : {type : 'text'}
					}, {
						display : '有效日期(E)', name : 'inva_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', width : 100,editor : {type : 'date',showSelectBox:false},
					}, {
						display: '生产日期(E)', name: 'fac_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 100,
						editor: {
							type: 'date',showSelectBox:false
						}
					}, {
						display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', width : 100,editor : {type : 'date',showSelectBox:false},
					}, {
						display : '条形码(E)', name : 'sn', width : 80, align : 'left',editor : {type : 'text'}
					}, {
						display : '院内码', name : 'bar_code', width : 80, align : 'left',editor : {type : 'text'}
					}, {
						display: '生产厂商', name: 'fac_name', width: 200, align: 'left'
					}, {
						display : '是否个体码(E)', name : 'is_per_bar', textField : 'text', width : 80, align : 'left',
							editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								data : yes_or_no.Rows,
								keySupport : true,
								autocomplete : true,
							},
							render : function(rowdata, rowindex, value) {
								return (rowdata.is_per_bar == null ? 0 : rowdata.is_per_bar) == 0 ? '否' : '是';
							}
					}, {
						display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width : 80, align : 'left',
						editor : {
							type : 'select',
							valueField : 'id',
							textField : 'text',
							url : '../../queryMedHosPackage.do?isCheck=false',
							keySupport : true,
							autocomplete : true,
						},
						render : function(rowdata, rowindex, value) {
							return rowdata.pack_name;
						}
					}, {
						display : '转换量(E)', name : 'num_exchange', width : 80, type : 'int', align : 'left',editor : {type : 'int',},
							render : function(rowdata, rowindex, value) {
								return rowdata.num_exchange == null ? "" : formatNumber(rowdata.num_exchange, 2, 1);
							}
					}, {
						display : '包装件数(E)', name : 'num', width : 80, type : 'number', align : 'left',editor : {type : 'number'},
					}, {
						display : '包装单价', name : 'pack_price', type : 'number', width : 110, align : 'right',
							render : function(rowdata, rowindex, value) {
								return rowdata.pack_price == null ? "" : formatNumber(rowdata.pack_price, '${p08006}', 1);
							}
					}, {
						display : '零售单价(E)', name : 'sell_price', width : 110, align : 'right', type : 'number',editor : {type : 'number'},
							render : function(rowdata, rowindex, value) {
								return rowdata.sell_price == null ? "" : formatNumber(rowdata.sell_price, '${p08006}', 1);
							}
					}, {
						display : '零售金额', name : 'sell_money', width : 130, type : 'number', align : 'right',
							render : function(rowdata, rowindex, value) {
								return rowdata.sell_money == null ? "" : formatNumber(rowdata.sell_money, '${p08005}', 1);
							},
					}, {
						display : '货位名称', name : 'location_name', width : 120, align : 'left',hide:true
						/* textField : 'location_name', width : 120, align : 'left',
							editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../../queryMedLocationDict.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
							},
							render : function(rowdata, rowindex, value) {
								return rowdata.location_name;
							} */
						},
						{
							display : '货位', name : 'location_new_name', width : 80, align : 'left'
						},{
							display: '序列号(E)', name: 'serial_no', width: 80, align: 'left',
							editor: {
								type: 'text',
							}
						}, {
							display : '备注(E)', name : 'note', width : 80, align : 'left',editor : {type : 'text'}
						}
					],
					dataAction : 'server', dataType : 'server', usePager : false, url : 'queryMedSpecialDetail.do?isCheck=false&special_id=${medInMain.special_id}&store_id='+liger.get("store_id").getValue().split(",")[0], width : '100%', height : '93%',
					checkbox : true, enabledEdit : state == 1 ? true : false, alternatingRow : true, 
					onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
					onsuccess:function(){
					
						//is_addRow();
					},
					isScroll : true, rownumbers : true, delayLoad : false,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ 
				    {text : '删除（<u>D</u>）', id : 'delete', click : remove, icon : 'delete', disabled: state == 1 ? false : true}, 
					{line : true},
					{text : '审核（<u>A</u>）', id : 'audit', click : audit, icon : 'audit', disabled: state == 1 ? false : true},
					{line : true}, 
					{text : '消审（<u>X</u>）', id : 'unAudit', click : unAudit, icon : 'unaudit', disabled: state == 2 ? false : true}, 
					{line : true}, 
					{text : '确认（<u>F</u>）', id : 'confirmDate', click : confirmDate, icon : 'account', disabled: state == 2 ? false : true},
					{line : true},
					{text : '发票补登(<u>W</u>)', id : 'update_invoice', click : update_invoice, icon : 'edit',disabled: state == 3 ? false : true}, 
					{line : true}, 
					{text : '生成发票(<u>W</u>)', id : 'create_invoice', click : create_invoice, icon : 'edit',disabled: state == 3 ? false : true}, 
					{line : true}, 
					{text : '上一张（<u>B</u>）', id : 'add_inv', click : before_no, icon : 'before'}, 
					{line : true}, 
					{text : '下一张（<u>N</u>）', id : 'open_add', click : next_no, icon : 'next'}, 
					{line : true},
					{text: '复制药品（<u>W</u>）', id:'copyInv', click: copyInv, icon:'add' }
					
					/* {text : '订单导入（<u>O</u>）', id : 'order_imp', click : order_imp, icon : 'up'},
					{line : true},
					{text : '协议导入（<u>X</u>）', id : 'protocol_imp', click : protocol_imp, icon : 'up'}, 
					{line : true}  */
					]
				}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		grid.toggleCol("inv_no", false);
		grid.toggleCol("order_rela", false);
	}
    
    //复制行
    function copyInv(){
    	var data_copys = grid.getCheckedRows();
		if (data_copys.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			for(var i = 0, length = data_copys.length; i<length; i++){         // 把数组的每个data都复制一遍，即深复制
				data_copys[i] = $.extend({},data_copys[i]) ;
				data_copys[i].amount = '1';
			}
			grid.addRows(data_copys);
		}
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
	  
	   return false;
}
    function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
    
    function order_imp(){
    	var sup_id = liger.get("sup_id").getValue();
    	if(sup_id == null || sup_id == "" || sup_id == "defined"){
    		$.ligerDialog.error("请先选择供应商！");
    		return;
    	}
    	var para = "sup_id=" + sup_id + "&sup_text=" + liger.get("sup_id").getText();
    	$.ligerDialog.open({
			title: '订单导入',
			height: 550,
			width: 1000,
			url: 'orderImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1,
		});
    }
    function protocol_imp(){
    	var sup_id = liger.get("sup_id").getValue();
    	if(sup_id == null || sup_id == "" || sup_id == "defined"){
    		$.ligerDialog.error("请先选择供应商！");
    		return;
    	}
    	var protocol_id = liger.get("protocol_code").getValue();
    	if(protocol_id == null || protocol_id == "" || protocol_id == "defined"){
    		$.ligerDialog.error("请先选择协议号！");
    		return;
    	}
    	var para = "sup_id=" + liger.get("sup_id").getValue() +
    		"&sup_text=" + liger.get("sup_id").getText() +
    		"&protocol_id=" + liger.get("protocol_code").getValue() + 
    		"&protocol_text=" + liger.get("protocol_code").getText();
    	$.ligerDialog.open({
			title: '协议导入',
			height: 550,
			width: 1000,
			url: 'protocolImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
    }
    function sn_imp(){
    	
    }
    
    function remove(){
    	grid.deleteSelectedRow();
    }
    
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
		if(column_name=='cert_id'){
			var certId = grid.getColumnByName("cert_id");
			certId.editor.url='../../queryMedInvCertId.do?isCheck=false&inv_id='+e.record.inv_id;
		}
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					bid_code : data.bid_code,
					inv_code : data.inv_code,
					inv_no : data.inv_no,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					price : data.plan_price == null ? "" : data.plan_price,
					pack_code : data.pack_code == null ? "" : data.pack_code,
					pack_name : data.pack_name == null ? "" : data.pack_name,
					num_exchange : data.num_exchange == null ? "" : data.num_exchange,
					is_batch : data.is_batch == null ? 0 : data.is_batch,
					is_bar : data.is_bar == null ? 0 : data.is_bar,
					is_per_bar : data.is_per_bar == null ? 0 : data.is_per_bar,
					is_quality : data.is_quality == null ? 0 : data.is_quality,
					is_disinfect : data.is_disinfect == null ? 0 : data.is_disinfect,
					is_highvalue : data.is_highvalue == null ? 0 : data.is_highvalue,
				    location_new_id : data.location_new_id == null ? "" : data.location_new_id,
				    location_new_name : data.location_new_name == null ? "" : data.location_new_name,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					fac_name : data.fac_name == null ? "" : data.fac_name,
					cert_id : data.cert_id == null ? "" : data.cert_id,	
					cert_code : data.cert_code == null ? "" : data.cert_code
				});
			}
			
		}
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			//e.column.name.focus();
			return false;
		}else if (e.column.name == "amount" && e.value == 0){
			return false;
		}
// 		else if (e.column.name == "price" && e.value == 0){
// 			return false;
// 		}
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != "" && e.value != 0){
			if(e.column.name == "inv_id"){
				//判断是否为自动有效日期
				if("${p08009}" != 0){
					grid.updateCell('inva_date', getDateAddDay(new Date(), "${p08009}"), e.rowindex);
				}
				if(e.record.amount != undefined && e.record.amount != ""){
					//数量不为空自动计算金额
					if(e.record.price != undefined && e.record.price != ""){
						grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
					}
					if(e.record.sell_price != undefined && e.record.sell_price != ""){
						grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
					}
					if(e.record.sale_price != undefined && e.record.sale_price != ""){
						grid.updateCell('sale_money', e.record.amount * e.record.sale_price, e.rowindex);
					}
				}
				//药品更改应重置药品批次
				grid.updateCell('batch_sn', "", e.rowindex);
			}else if (e.column.name == "amount"){
				//自动计算金额
				if(e.record.price != undefined && e.record.price != ""){
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算零售金额
				if(e.record.sell_price != undefined && e.record.sell_price != ""){
					grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
				}
				//自动计算包装件数
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" ){
					grid.updateCell('num', e.record.num_exchange ? e.value / e.record.num_exchange : 0, e.rowindex);
				}
			}else if (e.column.name == "price"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != ""){
					grid.updateCell('amount_money', e.record.amount ? e.value * e.record.amount : 0, e.rowindex);
				}
				//计算包装单价
				if(e.record.num_exchange != undefined && e.record.num_exchange != ""){
					grid.updateCell('pack_price', e.value * e.record.num_exchange, e.rowindex);
				}
			}else if (e.column.name == "num_exchange"){
				//自动计算包装件数
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('num', e.record.amount / e.value, e.rowindex);
				}
			}else if (e.column.name == "num"){
				//自动计算数量与金额
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
					grid.updateCell('amount', e.value * e.record.num_exchange, e.rowindex);
					if(e.record.price != undefined && e.record.price != "" ){
						grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
					}
					if(e.record.sell_price != undefined && e.record.sell_price != "" && e.record.sell_price != 0){
						grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
					}
				}
				//自动包装单价
				if(e.record.price != undefined && e.record.price != "" ){
					grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
				}
			}else if (e.column.name == "sell_price"){
				//自动计算零售金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "amount_money"){
				if (e.record.price != undefined && e.record.price != "") {
					grid.updateCell('amount', e.record.price ? e.value / e.record.price : 0, e.rowindex);
				}
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != ""){
					grid.updateCell('price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
				//计算包装单价
				if(e.record.num_exchange != undefined && e.record.num_exchange != ""){
					grid.updateCell('pack_price', e.record.amount * e.record.num_exchange ? e.value / e.record.amount * e.record.num_exchange : 0, e.rowindex);
				}
			}else if (e.column.name == "batch_no"){
				//批号更改应重置药品批次
				grid.updateCell('batch_sn', "", e.rowindex);
			}else if (e.column.name == "sn"){
				//修改条形码需要重置院内码
				grid.updateCell('bar_code', "", e.rowindex);
			}
		}
		grid.updateTotalSummary();
		return true;
	}
	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
			//grid.beginEdit(0);
		}, 1000);
	}
	// 审核
	function audit(){
		if (state != 1){
			$.ligerDialog.error('审核失败！'+special_no+'单据不是未审核状态,不能审核');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id   +"@"+ 
					copy_code    +"@"+ 
					special_id  +"@"+ 
					special_no   +"@"+ 
					state   +"@"+ 2
				) 
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							state = 2;
					    	loadHead();
					    	grid.reRender();
					    	change_button();
					    	parentFrameUse().query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		if (state != 2){
			$.ligerDialog.error('消审失败！'+special_no+'单据不是已审核状态,不能消审');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id  +"@"+ 
					copy_code    +"@"+ 
					special_id   +"@"+ 
					special_no   +"@"+ 
					state  +"@"+ 1
				) 
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							state = 1;
					    	loadHead();
					    	grid.reRender();
					    	change_button();
					    	parentFrameUse().query();
						}
					});
				}
			}); 
		}
	}
    //确认	
    function confirmDate(){
    	if (state != 2){
			$.ligerDialog.error('确认失败！'+special_no+'单据不是已审核状态,不能确认');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id   +"@"+ 
					copy_code   +"@"+ 
					special_id  +"@"+ 
					special_no  +"@"+ 
					year  +"@"+ 
					month  +"@"+ 
					store_id
			) 
			$.ligerDialog.confirm('您确定要进行确认操作吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMedSpecial.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							state = 3;
					    	loadHead();
					    	grid.reRender();
					    	change_button();
					    	parentFrameUse().query();
						}
					});
				}
			}); 
		}
	}
    
	//更新发票号
    function update_invoice(){
		
    	if(state != 3){
			$.ligerDialog.error("单据未入库确认，该功能不可用");
			return;
		}
    	var para = "group_id=" + group_id + "&hos_id=" + hos_id + "&copy_code=" + copy_code + 
    		"&special_id=" + special_id + "&in_id=" + in_id;
    	$.ligerDialog.open({
			title: '更新发票号',
			height: 300,
			width: 500,
			url: 'updateSpecialInvoicePage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, top: "20%",
		});
    }
	
	function updateBill(bill_no, bill_date){
		$("#bill_no").val(bill_no);
		$("#bill_date").val(bill_date);
	}
	
	//生成发票
    function create_invoice(){
    	if(state != 3){
			$.ligerDialog.error("单据未入库确认，该功能不可用");
			return;
		}
    	var para = "group_id=" + group_id + "&hos_id=" + hos_id + 
			"&copy_code=" + copy_code + "&in_id=" + in_id;
    	
    	$.ligerDialog.confirm('确定生成发票?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("../in/addMedBillByInvoice.do?isCheck=false&"+para, "", function (responseData){});
			}
		}); 
    }
    //上一张
	function before_no(){
		var paras = 
			"group_id="+group_id +"&"+ 
			"hos_id="+hos_id +"&"+ 
			"copy_code="+ copy_code +"&"+ 
			"special_id="+special_id +"&"+ 
			"special_no="+special_no  +"&"+ 
			"bus_type_code="+bus_type_code;
		ajaxJsonObjectByUrl("queryMedSpecialBeforeNo.do?isCheck=false",paras.toString(),function(responseData){
			if(responseData.state=="true"){
				parentFrameUse().update_open(responseData.update_para);
				this_close();
			}else{
				$.ligerDialog.warn('该单据是第一单据，没有上一张单据'); 
			}
		});   
	}
    //下一张
	function next_no(){
		var paras = 
			"group_id="+group_id +"&"+ 
			"hos_id="+hos_id +"&"+ 
			"copy_code="+ copy_code +"&"+ 
			"special_id="+special_id +"&"+ 
			"special_no="+special_no  +"&"+ 
			"bus_type_code="+bus_type_code;
		ajaxJsonObjectByUrl("queryMedSpecialNextNo.do?isCheck=false",paras.toString(),function(responseData){
			if(responseData.state=="true"){
				parentFrameUse().update_open(responseData.update_para);
				this_close();
			}else{
				$.ligerDialog.confirm('已经是最后一张单据了,是否添加新单据?', function (yes){
					if(yes){
						parentFrameUse().add_open();
				    	this_close();
					}
				}); 
			}
		});   
		
	}
	

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('P', printDate);
		hotkeys('H', printCollect);
		hotkeys('I', imp);
		hotkeys('C', this_close);
		hotkeys('B', before_no);
		hotkeys('N', unAudit);
		hotkeys('A', next_no);
		hotkeys('X', unAudit);
		hotkeys('F', confirmDate);
	}
	
	function loadDict(){
    	//字典下拉框
    	//仓库下拉框
		autocompleteAsync("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,'', false,'',180);
    	liger.get("store_id").setValue('${medInMain.store_id},${medInMain.store_no}');
    	liger.get("store_id").setText('${medInMain.store_code} ${medInMain.store_name}');
    	
		//供应商
    	autocomplete("#sup_id", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true,'', false,'',280);
    	liger.get("sup_id").setValue('${medInMain.sup_id},${medInMain.sup_no}');
    	liger.get("sup_id").setText('${medInMain.sup_code} ${medInMain.sup_name}');
	
    	//采购员 
    	var store_id = liger.get("store_id").getValue().split(",")[0]; 
		autocomplete("#stocker", "../../queryMedStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, false, '', 180);
		liger.get("stocker").setValue('${medInMain.stocker}');
    	liger.get("stocker").setText('${medInMain.stocker_name}');
    	//领料科室
		autocompleteAsync("#dept_id", "../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,{is_last:1}, false,'',180);
		liger.get("dept_id").setValue('${medInMain.dept_id},${medInMain.dept_no}');
    	liger.get("dept_id").setText('${medInMain.dept_code} ${medInMain.dept_name}');
		//领料人queryMedEmp
		autocomplete("#dept_emp", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true,
				isEmpByDept ? {dept_id : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0]} : "", false,'',180);
	 	liger.get("dept_emp").setValue('${medInMain.dept_emp}');
    	liger.get("dept_emp").setText('${medInMain.emp_code} ${medInMain.dept_emp_name}'); 
		//采购类型
		autocomplete("#stock_type_code", "../../queryMedStockType.do?isCheck=false", "id", "text", true, true,'', false,'',180);
		liger.get("stock_type_code").setValue('${medInMain.stock_type_code}');
    	liger.get("stock_type_code").setText('${medInMain.stock_type_name}');
		
	/* 	//结算方式
		autocomplete("#ctrl_type_code", "../../queryMedPayType.do?isCheck=false", "id", "text", true, true);
		liger.get("ctrl_type_code").setValue('${medInMain.ctrl_type_code}');
    	liger.get("ctrl_type_code").setText('${medInMain.pay_name}');
    	//采购部门
    	autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);
    	liger.get("dept_id").setValue('${medInMain.dept_id},${medInMain.dept_no}');
    	liger.get("dept_id").setText('${medInMain.dept_no} ${medInMain.dept_name}'); */
		//协议编号
		autocomplete("#protocol_code", "../../queryMedProtocolMain.do?isCheck=false", "id", "text", true, true,
				{sup_id:liger.get("sup_id").getValue() ==null?'':liger.get("sup_id").getValue().split(",")[0]}, false,'',280);
		liger.get("protocol_code").setValue('${medInMain.protocol_id}');
    	liger.get("protocol_code").setText('${medInMain.protocol_code} ${medInMain.protocol_name}');
    	
    	//项目下拉框
		autocomplete("#proj_id", "../../queryMedProj.do?isCheck=false", "id", "text", true, true,'', false,'',280);
		liger.get("proj_id").setValue('${medInMain.proj_id}');
    	liger.get("proj_id").setText('${medInMain.proj_code} ${medInMain.proj_name}');
    	
		//格式化文本框
        $("#in_no").ligerTextBox({width:280, disabled:true});
        $("#make_date").ligerTextBox({width:180});
        $("#brief").ligerTextBox({width:280});
        $("#bill_date").ligerTextBox({width:180});
        $("#bill_no").ligerTextBox({width:180});
        //格式化按钮
        if(state > 1){
    		$("#save").ligerButton({click:save, width:90, disabled:true});
        }else{
    		$("#save").ligerButton({click:save, width:90});
        }
        $("#print").ligerButton({click: printDate, width:90});
        $("#printCollect").ligerButton({click: printCollect, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
		
		
     } 

	
	function this_close(){
		frameElement.dialog.close();
	}
	
	

	//打印
	function printDate(){
		
	var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			 if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按调出仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
		/* var para={
				special_id:'${medInMain.special_id}',
				template_code:'08017',
				use_id:useId,
				p_num:0
			}; */
		
			//printTemplate("queryMedSpecialByPrintTemlate.do",para);
		var para={
				special_id:'${medInMain.special_id}',
    			template_code:'08015',
    			class_name:"com.chd.hrp.med.serviceImpl.storage.special.MedSpecialServiceImpl",
    			method_name:"queryMedSpecialByPrintTemlate1",
    			isPrintCount:false,//更新打印次数
    			isPreview:false,//预览窗口，传绝对路径
    			use_id:useId,
    			p_num:0
    	}; 
		officeFormPrint(para);
	}
	//汇总打印
	function printCollect(){
		
	var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			 if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按调出仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
		/* var para={
				special_id:'${medInMain.special_id}',
				template_code:'08017',
				use_id:useId,
				p_num:0, 
				is_collect: 1
			};
			printTemplate("queryMedSpecialByPrintTemlate.do",para); */
			
		var para={
				special_id: '${medInMain.special_id}',
    			template_code:'08017',
    			class_name:"com.chd.hrp.med.serviceImpl.storage.special.MedSpecialServiceImpl",
    			method_name:"queryMedSpecialByPrintTemlate1",
    			isPrintCount:false,//更新打印次数
    			isPreview:true,//预览窗口，传绝对路径
    			use_id:useId,
    			p_num:0,
    			is_collect: 1
    	}; 
	 	
	  officeFormPrint(para);
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按调出仓库打印，请选择仓库！');
				return;
			} 
			
			useId=liger.get("store_id").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"08015",use_id:useId});	
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/storage/special/medSpecialPrintSetPage.do?template_code=08017&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
		}); */
	}
	
	function change_button(){
		if(state == 1){
			$("#save").ligerButton({click: save, width:90, disabled:false});
		}else{
			$("#save").ligerButton({click: save, width:90, disabled:true});
		}
	}
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%"><b>入库单号<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="in_no" type="text" id="in_no" value="${medInMain.in_no}" ltype="text" disabled="disabled"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><b>编制日期<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input class="Wdate" name="make_date" id="make_date" value="${medInMain.make_date}"type="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%"><b>仓库<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="store_id" type="text" id="store_id" ltype="text"  validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	           <td align="right" class="l-table-edit-td"  width="10%"><b>供应商<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="sup_id" type="text" id="sup_id" ltype="text"  validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%"><b>领料科室<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%"><b>领料人<!-- <font color="red">*</font> -->:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="dept_emp" type="text" id="dept_emp" ltype="text"  validate="{required:true}" />
	            </td>
	            
	        </tr> 
			<tr>
			    <td align="right" class="l-table-edit-td" width="10%"><b>协议编号:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%"><b>采购员<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stocker" type="text" id="stocker" ltype="text"  validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%"><b>采购类型:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%"><b>项&nbsp;&nbsp;目:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:false}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><b>发票日期:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input class="Wdate" name="bill_date" type="text" id="bill_date" ltype="text" value="${medInMain.bill_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  validate="{required:false}" />
	            </td>
	            
	             <td style="display: none;">
	            	<input name="money_sum" type="text" id="money_sum" value="${medInMain.money_sum}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" width="10%"><b>备&nbsp;注:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="brief" type="text" id="brief" value='${medInMain.brief}' ltype="text" validate="{maxlength:100}" />
	            </td>
	     
	            
	            <td align="right" class="l-table-edit-td" width="10%"><b>发票号:</b></td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="bill_no" type="text" id="bill_no" ltype="text" value="${medInMain.bill_no}" validate="{maxlength:50}" />
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					制单人：${medInMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					审核人：${medInMain.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					确认人：${medInMain.confirmer_name}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id ="save" accessKey="S"><b>保存（<u>S</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="printCollect" accessKey="H"><b>汇总打印（<u>H</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>