<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script src="<%=path%>/lib/hrp/med/med.js" type="text/javascript"></script>
	<script src="<%=path%>/lib/Lodop/barcode.js"	type="text/javascript"></script>
	<script src="<%=path%>/lib/Lodop/LodopFuncs.js"	type="text/javascript"></script>
<script type="text/javascript">
     var grid;
     var detailGrid;
     var gridManager = null;
     var state = "${state}";
	 var is_apply = '${is_apply}';
     
     $(function (){
		$("#layout1").ligerLayout({
			topHeight: 140,
			centerWidth: 888
		});
        loadDict();
        //loadForm();
        loadHead(null);	
        queryDetail();
        
        changeTranType();
		//联动事件
		$("#tran_type").bind("change", function(){changeTranType()});
		//联动事件
		$("#out_hos_id").bind("change",function(){
			var para = {
				hos_id : liger.get("out_hos_id").getValue()
			}
			liger.get("out_store_id").set("parms", para);
			liger.get("out_store_id").reload();
			if(liger.get("out_store_id").data[0]){
				liger.get("out_store_id").selectValue(liger.get("out_store_id").data[0].id);
			}else{
				liger.get("out_store_id").setValue("");
				liger.get("out_store_id").setText("");
			}
			//autocompleteAsync("#in_store_id", "../../../queryMedStore.do?isCheck=false", "id","text", true, true, para, true, false, '180');	    	
		})
		$("#out_store_id").bind("change",function(){
	    	//loadHead();
	    	grid.columns[5].editor.grid.url = '../../queryMedStockInvListTran.do?isCheck=false&store_id=' 
					         					+ liger.get("out_store_id").getValue().split(",")[0];
	    	grid.reRender();
		})
		$("#in_hos_id").bind("change",function(){
			var para = {
				hos_id : liger.get("in_hos_id").getValue()
			}
			liger.get("in_store_id").set("parms", para);
			liger.get("in_store_id").reload();
			if(liger.get("in_store_id").data[0]){
				liger.get("in_store_id").selectValue(liger.get("in_store_id").data[0].id);
			}else{
				liger.get("in_store_id").setValue("");
				liger.get("in_store_id").setText("");
			}
			//autocompleteAsync("#in_store_id", "../../../queryMedStore.do?isCheck=false", "id","text", true, true, para, true, false, '180');	    	
		})
     });  
     
     function  save(){
		grid.endEdit();
		
    	 if(liger.get("tran_type").getValue() == 1){
    		 if(liger.get("out_store_id").getValue() == liger.get("in_store_id").getValue()){
    			 $.ligerDialog.warn('院内调拨调出、调入仓库不能一致！');
    			 return false;
    		 }
    	 }else if(liger.get("tran_type").getValue() == 2){
    		 if(liger.get("out_hos_id").getValue() == liger.get("in_hos_id").getValue()){
    			 $.ligerDialog.warn('院外调拨调出单位、调入单位不能一致！');
    			 return false;
    		 }
        	 if(!liger.get("bus_type").getValue()){
        		 $.ligerDialog.warn('院外调拨业务类型不能为空！');
    			 return false;
        	 }
    	 }else{
    		 $.ligerDialog.warn('调拨类型不能为空！');
			 return false;
    	 }
    	 if(!liger.get("tran_date").getValue()){
    		 $.ligerDialog.warn('编制日期不能为空！');
			 return false;
    	 }
    	 if(!liger.get("out_hos_id").getValue()){
    		 $.ligerDialog.warn('调出单位不能为空！');
			 return false;
    	 }
    	 if(!liger.get("in_hos_id").getValue()){
    		 $.ligerDialog.warn('调入单位不能为空！');
			 return false;
    	 }
    	 if(!liger.get("out_store_id").getValue()){
    		 $.ligerDialog.warn('调出仓库不能为空！');
			 return false;
    	 }
    	 if(!liger.get("in_store_id").getValue()){
    		 $.ligerDialog.warn('调入仓库不能为空！');
			 return false;
    	 }
    	 if(liger.get("out_store_id").getValue() == liger.get("in_store_id").getValue()){
			 $.ligerDialog.warn('调出、调入仓库不能一致！');
			 return false;
		 }
    	 
		var tran_detail_data = gridManager.rows;
		var targetMap = new HashMap();
		var validate_detail_buf = new StringBuffer();
		var rows = 0;
		var store_inv = "";  //用于判断是否 药品与调入仓库是否存在对应关系 
		if(tran_detail_data.length > 0){
  			$.each(tran_detail_data, function(d_index, d_content){ 
	  	      	if(d_content.inv_id){
	  				store_inv += d_content.inv_id + ",";
	  	      		var value="第"+(d_index+1)+"行";
	  	      		if(!d_content.amount && d_content < 0){
						validate_detail_buf.append(value+"数量为零或空 请输入\n");
					}
	  	      		
	  	      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
	  				if(!targetMap.get(key)){
	  					targetMap.put(key ,value);
	  				}else{
	  					validate_detail_buf.append(targetMap.get(key)+"与"+value+"药品编码、生产批号、条形码单价不能重复" + "\n\r");
	  				}
	  				rows += 1;
	  	      	}
 			}); 
  		}
		//仓库药品对应关系
		var para = {
			inv_ids: store_inv.substring(0, store_inv.length-1), 
			store_id: liger.get("in_store_id").getValue().split(",")[0]
		}
		var is_flag = ajaxJsonObjectByUrl("../../existsMedStoreIncludeInv.do?isCheck=false", para, function (responseData){
			if(responseData.state=="false"){
				validate_detail_buf.append("药品"+responseData.inv_ids+"不在该仓库中！<br>");
			}
		}, false);
		if(validate_detail_buf.toString()  != ""){
 			$.ligerDialog.warn(validate_detail_buf.toString(),'','',{width:450});
 			return false;
 		}

 		if(rows == 0){
 			$.ligerDialog.warn('请选择药品');
 			return false;
 		}
 		
        var formPara={
        		hos_id:$("#hos_id").val(),
        		group_id:$("#group_id").val(),
        		copy_code:$("#copy_code").val(),
        		tran_id:$("#tran_id").val(),
        		tran_no:$("#tran_no").val(),
 				brief:$("#brief").val(),
 				tran_date:$("#tran_date").val(),
 				out_hos_id:liger.get("out_hos_id").getValue().split(",")[0],
 				in_hos_id:liger.get("in_hos_id").getValue().split(",")[0],
 				out_store_id:liger.get("out_store_id").getValue().split(",")[0],
 				out_store_no:liger.get("out_store_id").getValue().split(",")[1],
 				in_store_id:liger.get("in_store_id").getValue().split(",")[0],
 				in_store_no:liger.get("in_store_id").getValue().split(",")[1],
 				tran_type:liger.get("tran_type").getValue(),
 				tran_detail_data:JSON.stringify(tran_detail_data)
         };
        ajaxJsonObjectByUrl("updateMedTranMain.do",formPara,function(responseData){
            if(responseData.state=="true"){
            	queryDetail();
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
   
    function saveMedOutMain(){
        if($("form").valid()){
            save();
        }
   }
	function loadDict(){
        //字典下拉框
    	$("#tran_no").ligerTextBox({width:180,disabled: true }); 
    	autoCompleteByData("#tran_type", medTranMain_tranType.Rows, "id", "text",true, true,'',false,"${tran_type}",'180');
    	autoCompleteByData("#bus_type", medTranMain_busType.Rows, "id", "text",true, true,'',false,"${bus_type}",'180');
		
    	//autoCompleteByData("#tran_method", medTranMain_tranMethod.Rows, "id", "text",true, true,'',true,false,'180');
		$("#tran_method").ligerComboBox({width:180,disabled:true,cancelable: false});
        liger.get("tran_method").setValue("${tran_method}");
        if("${tran_method}" == 1){
    		liger.get("tran_method").setText("同价调拨");
        }else{
    		liger.get("tran_method").setText("异价调拨");
        }
        
    	//$("#brief").ligerTextBox({width:480}); 
    	
    	$("#out_store_id").ligerComboBox({disabled: true }); 
    	$("#out_hos_id").ligerComboBox({disabled: true}); 
    	 
		//字典下拉框
		autocompleteAsync("#out_hos_id", "../../queryMedHosInfo.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		liger.get("out_hos_id").setValue('${out_hos_id}');
		liger.get("out_hos_id").setText('${out_hos_code} ${out_hos_name}');
		autocompleteAsync("#out_store_id", "../../queryMedStoreDictDate.do?isCheck=false", "id","text", true, true, {is_write:'1',hos_id : "${out_hos_id}"}, false, false, '180');
		liger.get("out_store_id").setValue('${out_store_id},${out_store_no}');
		liger.get("out_store_id").setText('${out_store_code} ${out_store_name}');

		autocompleteAsync("#in_hos_id", "../../queryMedHosInfo.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		liger.get("in_hos_id").setValue('${in_hos_id}');
		liger.get("in_hos_id").setText('${in_hos_code} ${in_hos_name}');
		autocompleteAsync("#in_store_id", "../../queryMedStoreDictDate.do?isCheck=false", "id","text", true, true, {is_read:'1',hos_id : "${in_hos_id}"}, false, false, '180');
		liger.get("in_store_id").setValue('${in_store_id},${in_store_no}');
		liger.get("in_store_id").setText('${in_store_code} ${in_store_name}');

		$("#tran_date").ligerTextBox({width : 180});
		//autodate("#tran_date");
		
		$("#print").ligerButton({click: printDate, width:90});
		$("#barcodePrint").ligerButton({click: barcodePrint, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
	} 
	
    function changeTranType(){
		if(liger.get("tran_type").getValue() == 1){
			$("#bus_type_span").css("display", "none");
			$("#bus_type").attr("readonly", "readonly").ligerComboBox({width:180,disabled:true, cancelable: false});
	        liger.get("bus_type").setValue("");
			liger.get("bus_type").setText("");
			$("#out_hos_id").attr("readonly", "readonly").ligerComboBox({width:180,disabled:true, cancelable: false});
			$("#in_hos_id").attr("readonly", "readonly").ligerComboBox({width:180,disabled:true, cancelable: false});
	        liger.get("in_hos_id").setValue(liger.get("out_hos_id").getValue());
			liger.get("in_hos_id").setText(liger.get("out_hos_id").getText());
		}else{
			$("#bus_type_span").css("display", "inline");
			$("#bus_type").removeAttr("readonly").prop("disabled", false).ligerComboBox({width:180,disabled:false, cancelable: true});
			$("#out_hos_id").removeAttr("readonly").prop("disabled", false).ligerComboBox({width:180,disabled:false, cancelable: true});
			$("#in_hos_id").removeAttr("readonly").prop("disabled", false).ligerComboBox({width:180,disabled:false, cancelable: true});
		}
    }

 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name : 'tran_id',
 			value : '${tran_id}'
 		});
 		grid.options.parms.push({
 			name : 'store_id',
 			value : '${out_store_id}'.split(",")[0]
 		});
 		grid.options.parms.push({
 			name : 'in_store_id',
 			value : '${in_store_id}'.split(",")[0]
 		});

     	//加载查询条件
     	grid.loadData(grid.where);
 	}

    function loadHead(){ 
		grid = $("#maingrid").ligerGrid({
			
	           columns: [ 
	                     { display: '交易编码', name: 'bid_code', align: 'left',width:100},
	                     { display: '药品编码', name: 'inv_code', align: 'left',width:100,
	         				totalSummary: {
	        					align : 'right',
	                            render: function (suminf, column, cell) {
	                                return '<div>合计：</div>';
	                            }
	                        }
	                     },
	                     { display: '药品名称(E)', name : 'inv_id', textField : 'inv_name',align: 'left',width:240,
	                    	 editor : {
									type : 'select',
									valueField: 'inv_id', 
									textField: 'inv_name',
									selectBoxWidth: "80%",
									selectBoxHeight: 240,
								    keySupport:true,
							      	autocomplete: true,
							      	highLight : true,
							      	grid :{
								    	columns : [ 
								    	        {display : '交易编码', name : 'bid_code', width : 100, align : 'left'}, 
				                	            {display : '药品编码', name : 'inv_code', width : 100, align : 'left'}, 
				                	            {display : '药品名称', name : 'inv_name', width : 240, align : 'left'}, 
				                	            {display : '别名', name : 'alias', width : 120, align : 'left'}, 
				                	            {display : '规格型号', name : 'inv_model', width : 100, align : 'left'},
				                	            {display : '计量单位', name : 'unit_name', width : 60, align : 'left'}, 
				                	            {display : '单价', name : 'price', width : 100, align : 'right'}, 
				                	            {display : '批号', name : 'batch_no', width : 120, align : 'left'}, 
				                	            {display : '条码', name : 'bar_code', width : 120, align : 'left'}, 
				                	            {display : '库存', name : 'cur_amount', width : 90, align : 'left'},
				                	            {display : '即时库存', name : 'imme_amount', width : 90, align : 'left'},
				                	            {display : '有效日期', name : 'inva_date', width : 100, align : 'left'}, 
				    	         				{display : '是否收费', name : 'is_charge', width : 60, align : 'left',
					    	         				render : function(rowdata, rowindex, value) {
					    	         					return value == 1 ? '是' : '否';
					    	         				}
				    	         				},
				    	         				{display : '药品类别', name : 'med_type_name', width : 120, align : 'left'},
				                	            {display : '生产厂商', name : 'fac_name', width : 180, align : 'left'}, 
				                	            {display : '零售单价', name : 'sell_price', width : 100, align : 'right'}, 
				                	            {display : '货位编码', name : 'location_code', width : 100, align : 'left',hide:true}, 
				                	            {display : '货位名称', name : 'location_new_name', width : 140, align : 'left'}
				                	        ],
				        	         		switchPageSizeApplyComboBox : false,
				        	         		onSelectRow: function (data) { 
				    							var e = window.event;
				    							if (e && e.which == 1) {
				    								f_onSelectRow_detail(data);
				    							}
				    						},
				        	         		url : '../../queryMedStockInvListTran.do?isCheck=false&store_id=' 
						         					+ liger.get("out_store_id").getValue().split(",")[0],
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
							      		onSuccess : function() {
							            	this.parent("tr").next(".l-grid-row").find("td:first").focus();
							            },
							            ontextBoxKeyEnter: function (data) {
											f_onSelectRow_detail(data.rowdata);
										}
	                    	 }
							      	 
	                     },
	                     { display: '规格型号', name: 'inv_model', align: 'left',width:180},
	                     { display: '计量单位', name: 'unit_name', align: 'left',width:60},
	                     { display: '批号', name: 'batch_no', align: 'left',width:120},
	                     { display: '当前库存', name: 'cur_amount', align: 'left',width:90},
	                     { display: '数量(E)', name: 'amount', align: 'left',width:90,editor : {type : 'float'},
	         				totalSummary: {
	        					align : 'left',
	                            render: function (suminf, column, cell) {
	                                return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
	                            }
	                        }
	                     },
	                     { display: '单价', name: 'price', align: 'right',width:100,
	                    	 render : function(rowdata, rowindex, value) {
	         					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
	         				}
	                     },
	                     { display: '金额', name: 'amount_money', align: 'right',width:100,
	                    	 render : function(rowdata, rowindex, value) {
	                    		rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
	          					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
	          				},
	        				totalSummary: {
	        					align : 'right',
	                            render: function (suminf, column, cell) {
	                                return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
	                            }
	                        }
	                     },
	                     { display: '有效期', name: 'inva_date', align: 'left',width:90},
	                     { display: '灭菌日期', name: 'disinfect_date', align: 'left',width:90},
	                     { display: '条形码', name: 'bar_code', align: 'left',width:120},
	                     //{ display: '院内码', name: 'sn', align: 'left',width:120},
	                     { display: '移出仓库货位', name: 'location_new_name', align: 'left',width:120},
	                     { display: '移入仓库货位(E)', name: 'location_in_id', textField: 'location_in_name', align: 'left',width:120
	                     },
	                     { display: '生产厂商', name: 'fac_name', align: 'left',width:180},
	                     { display: '备注(E)',name : 'note',align: 'left',width:180,
	                    	 editor  : {type : 'text'}}, 
	                     { display: '批次数量合计', name: 'sum_amount',width:140, hide: true }, 
	                     { display: '批次明细', name: 'inv_detail_data',width:140, hide: true } 
					],
					usePager : false,width : '100%',height : '100%',
					enabledEdit : state == 1 ? true : false,
					isAddRow : (is_apply == 1 ) ? false : true,
					fixedCellHeight:true,heightDiff:-20,
					dataAction : 'server', dataType : 'server', url : 'queryMedTranDetailById.do?isCheck=false', delayLoad : true,//初始化明细数据
					onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
					onsuccess:function(){
					
						//is_addRow();
					},
					selectRowButtonOnly:true,checkbox: true,rownumbers:true,isScroll:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
					detail: { onShowDetail: showBatchSn, reload: true, single: true},//药品批次明细
					toolbar: { 
						items: [
						        { text: '保存', id:'add', click: saveMedOutMain,icon:'save', disabled: state == 1 ? false : true},
						        { line:true },
						        { text: '删除', id:'delete', click: deleteRow,icon:'delete', disabled: state == 1 ? false : true },
						        { line:true },
						        { text: '整单调拨', id:'imp', click: medTranBySingleImport,icon:'up' },
						        { line:true },
						        { text: '调出确认', id:'audit', click: outConfirm,icon:'audit', disabled: state == 1 ? false : true },
						        { line:true },
						        { text: '取消调出确认', id:'unAudit', click: unOutConfirm,icon:'unaudit', disabled: state == 2 ? false : true },
						        { line:true },
						        { text: '调入确认', id:'confirm', click: inConfirm,icon:'account', disabled: state == 2 ? false : true }
						]
					}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;		
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
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					fac_name : data.fac_name,
					bid_code : data.bid_code,
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					batch_no : data.batch_no == null ? "" : data.batch_no,
					bar_code : data.bar_code == null ? "" : data.bar_code,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					inva_date : data.inva_date == null ? "" : data.inva_date,
					disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
					location_id : data.location_id == null ? "" : data.location_id,
					location_new_name : data.location_new_name == null ? "" : data.location_new_name
				});
				setTimeout(function (){
					grid.endEditToNext();
				},300)
			}
		}
		return true;
	}
    function f_onAfterEdit(e){
    	if("inv_id" == column_name){
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    	}else if("amount" == column_name){
    		grid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		grid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    	}
		grid.updateTotalSummary();
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择药品！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		*/
		return true;
	}
    
	function deleteRow(){ 
		
		gridManager.deleteSelectedRow();
    }
    
	//批量添加明细数据
    function add_Rows(data){
    	grid.deleteAllRows();
    	grid.addRows(data);
    }
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '药品编码', name: 'inv_code',width:100,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
    		}, { 
    			display: '药品名称(E)', name: 'inv_id', textField: 'inv_name',width:240,
    			editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : '70%',
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '药品编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '药品名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 140, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:140,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 100, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow : f_detail_onSelectRow_detail,
	         			url : '../../queryMedStockInvDetailList.do?isCheck=false&store_id=' 
	         					+ liger.get("out_store_id").getValue().split(",")[0] + '&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price,
	         			pageSize : 500
	         		},
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', minWidth : 100, align : 'left'
     		}, { 
     			display : '即时库存', name : 'imme_amount', minWidth : 100, align : 'left', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 100, align : 'left', editor : {type : 'float'},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '单价', name: 'price', width: 140, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:140,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 140, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:140,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 140, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:140,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : state == 1 ? true : false, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '70%',data : f_getInvDetailData(row),columnWidth:140, 
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true }
    		]} 
		});
		
		detailGridAddRow();
    }
    
    //添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 500);
    }
    
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
    	//alert("是否存在数据："+JSON.stringify(rowdata.inv_detail_data));
		if(validateStr(rowdata.inv_id) && validateStr(rowdata.amount) && rowdata.amount != 0){
			//明细中有批次信息并且主数量和明细数量相等
			if(validateStr(rowdata.inv_detail_data) && validateStr(rowdata.sum_amount) && rowdata.amount == rowdata.sum_amount){
				var rows = jsonRowsToObject(rowdata.inv_detail_data); 
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
        			tran_id : "${tran_id}",
    				store_id : liger.get("out_store_id").getValue().split(",")[0],
            		inv_id : rowdata.inv_id,
            		batch_no : rowdata.batch_no,
            		bar_code : rowdata.bar_code,
            		price : rowdata.price,
            		amount : rowdata.amount
            	}
        		ajaxJsonObjectByUrl("../../queryMedInvByFifo.do?isCheck=false",invPara,function(responseData){
        			data = responseData;
                }, false);
				//变更主数据中药品批次信息
        		grid.updateCell('sum_amount', gridRowData.amount, gridRowData); 
        		grid.updateCell('inv_detail_data', JSON.stringify(data.Rows), gridRowData); 
        	}
    	}
        return data;
    }
    
    //改变明细数量更新主数据数量
    function changeDetailAmount(){
    	//获取明细总数量，并更新主数据数量
		var sumAmount =0;
		var dataDetail =  detailGrid.getData();
		if(dataDetail.length > 0){
			$(dataDetail).each(function(){
				if(validateStr(this.amount)){
					sumAmount += this.amount;
				}
			})
    		grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', JSON.stringify(dataDetail), gridRowData); 
		}else{
			grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', "", gridRowData); 
		}
    }

	var detail_id = "";
	var detail_name = "";
	function f_detail_onBeforeEdit(e) {
		detail_id = e.rowindex;
		detailClicked = 0;
		detail_name = e.column.name;		
	}
	//选中回充数据
	function f_detail_onSelectRow_detail(data, rowindex, rowobj) {
		detailSelectData = "";
		detailSelectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (detailSelectData != "" || detailSelectData != null) {
			if (detail_name == "inv_id") {
				detailGrid.updateRow(detail_id, {
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					batch_sn : data.batch_sn == null ? "" : data.batch_sn,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price
				});
			}
		}
		return true;
	}
    function f_detail_onAfterEdit(e){ 
    	if("amount" == detail_name){ 
    		detailGrid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		detailGrid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		detailGrid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record); 
    		changeDetailAmount();
    	} 
    } 
    // 编辑单元格提交编辑状态之前作判断限制
	function f_detail_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择药品！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		return true;
	}
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
	
	
    
    function outConfirm(){

    	if(state > 1){
			$.ligerDialog.error("调出确认失败！单据不是未调出确认状态");
			return false;
		}
    	var ParamVo = [];
    	ParamVo.push($("#hos_id").val() + "@" + $("#group_id").val()+ "@"+ $("#copy_code").val() + "@" +$("#tran_id").val());

		$.ligerDialog.confirm('确定调出确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("outConfirmMedTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 2;
				    	loadHead();
				    	grid.reRender();
						parentFrameUse().query();
					}
				});
			}
		});
		
	}
    
    function unOutConfirm(){

		if(state != 2){
			$.ligerDialog.error("取消调出确认失败！单据不是调出确认状态");
			return false;
		}
		
    	var ParamVo = [];
		
    	ParamVo.push($("#hos_id").val() + "@" + $("#group_id").val()+ "@"+ $("#copy_code").val() + "@" +$("#tran_id").val());
		
		$.ligerDialog.confirm('确定取消调出确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("unOutConfirmMedTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 1;
				    	loadHead();
				    	grid.reRender();
						parentFrameUse().query();
					}
				});
			}
		});
	}
    
    function inConfirm(){

		if(state != 2){
			$.ligerDialog.error("调入确认失败！单据不是调出确认状态");
			return false;
		}
    	var ParamVo = [];
		
    	ParamVo.push($("#hos_id").val() + "@" + $("#group_id").val()+ "@"+ $("#copy_code").val() + "@" +$("#tran_id").val());
		
		$.ligerDialog.confirm('确定调入确认?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("inConfirmMedTranMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						state = 3;
				    	loadHead();
				    	grid.reRender();
		            	parentFrameUse().query();
					}
				});
			}
		});
		
	}

  	
	 function medTranBySingleImport(){//整单出库
			
	    	/* var store_id = liger.get("out_store_id").getValue()?liger.get("out_store_id").getValue():'null';
	    	
	    	var store_code = liger.get("out_store_id").getText().split(" ")[0];
	    	
	    	var store_name = liger.get("out_store_id").getText().split(" ")[1];

			var paras = store_id+"@"+store_code+"@"+store_name; */
			
			var rowLength = gridManager.rows.length;
			
			for(var i = 0 ;i<rowLength;i++){ 
				gridManager.deleteRow(0);
			} 
				var store_id = liger.get("out_store_id").getValue()?liger.get("out_store_id").getValue().split(',')[0]:'null';
		    	var store_code = liger.get("out_store_id").getText()?liger.get("out_store_id").getText().split(' ')[0]:'null';
		    	var store_name = liger.get("out_store_id").getText()?liger.get("out_store_id").getText().split(' ')[1]:'null';

				var paras=[] ;
				paras.push(store_id)
				paras.push(store_code)
				paras.push(store_name)
				
				$.ligerDialog.open({
					title:'整单调拨',
					height: 600,width: 1000, 
					url: "medTranMainBySinglePage.do?isCheck=false&paras="+paras, 
					modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				})
			 
		

	}

	//当单据未审核 默认新增一行
	function is_addRow(){
		if(state > 1){
			return;
		}
		setTimeout(function () {
				grid.addRow();
			}, 500);
		 if(is_apply == 1){
	    		return;
	    	}
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('P', printDate);
		hotkeys('M', printSet);
		hotkeys('C', this_close);
	}
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${p08021 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08021 }'==2){
			//按仓库打印
			 if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按调出仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("out_store_id").getValue().split(",")[0];
		}
		var para={
				template_code:'08013',
				class_name:"com.chd.hrp.med.serviceImpl.storage.tran.MedTranMainServiceImpl",
				method_name:"queryMedTranByPrintTemlateMain",
				isPreview: false,//是否预览，默认直接打印 */
				tran_id :$("#tran_id").val(),
	    		use_id:useId,
	    		p_num:0
				
		}; 
	
	 officeFormPrint(para);
	
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${p08021 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08021 }'==2){
			//按仓库打印
			if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按调出仓库打印，请选择仓库！');
				return;
			} 
			
			useId=liger.get("out_store_id").getValue().split(",")[0];
		}
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/storage/tran/medTranPrintSetPage.do?template_code=08015&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
		officeFormTemplate({template_code:"08013",useId:useId});
	}

	var begin_bar;
	//按条码自动填充药品信息
	function paddingInv_beginBar(){
		if(event.keyCode==13){  
			begin_bar = $("#begin_bar").val();
			var store_id = liger.get("out_store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar){
				var para={
						flag: 1, //出单独药品
						store_id: store_id, 
						bar_code: begin_bar 
		    	}
				ajaxJsonObjectByUrl("../../queryMedOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
					}
				});
			}
		}
	}
	
	//按条码自动填充药品信息
	function paddingInv_endBar(){
		if(event.keyCode==13){  
			var end_bar = $("#end_bar").val();
			var store_id = liger.get("out_store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar && end_bar){
				var para={
						flag: 2, 
						store_id: store_id, 
						bar_code: begin_bar, 
						bar_code_end: end_bar 
		    	}
				ajaxJsonObjectByUrl("../../queryMedOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
				    	$("#end_bar").val("");
					}
				});
			}
		}
	}
	
	function barcodePrint(){
		var arr = [];
		var data = grid.getCheckedRows();
		if(data==""){

			$.ligerDialog.warn("没有勾选条码打印数据.");  
			return;
		}
		
		$(data).each(function(i,v){
			
			var obj = {
				bar_code:v.bar_code,
				other1:{
					name:"商品信息",
					value:v.inv_name//+"("+v.fac_name+")"
				},
				other2:{
					name:"单价",
					value:v.sell_price
				},
				other3:{
					name:"有效期",
					value:(v.inva_date==null?" ":v.inva_date)
				} 
			} 

			arr.push(obj);
		});
		//console.log(arr);
		lodopBarCode(arr);
	}
	
    </script>

  </head>
  
   <body onload="is_addRow();">
   <div id="pageloading" class="l-loading" style="display: none"></div>
	<input name="hos_id"  type="hidden" id="hos_id" value="${hos_id}" />
	<input name="group_id"  type="hidden" id="group_id" value="${group_id}" />
	<input name="copy_code"  type="hidden" id="copy_code" value="${copy_code}" />
	<input name="tran_id"  type="hidden" id="tran_id" value="${tran_id}" />
	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			       <tr>
			       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调拨单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="tran_no" type="text" id="tran_no" disabled="disabled" ltype="text" value="${tran_no}"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调拨类型：</td>
			            <td align="left" class="l-table-edit-td" ><input name="tran_type" type="text" id="tran_type" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>编制日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="tran_date" type="text" id="tran_date" ltype="text"  validate="{required:true}"  value="${tran_date}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;display:none;"><span id="bus_type_span" style="color:red;display:none;">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td" style="display:none" ><input name="bus_type" type="text" id="bus_type" ltype="text" disabled="disabled" validate="{required:true}" /></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调出仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_store_id" type="text" id="out_store_id" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>调入仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="in_store_id" type="text" id="in_store_id" ltype="text"  validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;display:none;"><span style="color:red;display:none;">*</span>调出单位：</td>
			            <td align="left" class="l-table-edit-td" style="display:none"><input name="out_hos_id" type="text" id="out_hos_id" ltype="text" disabled="disabled" validate="{required:true}" /></td>
			            <td align="left"></td>
			        </tr>  
			         <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;display:none;"><span style="color:red;display:none;">*</span>调拨方式：</td>
			            <td align="left" class="l-table-edit-td" style="display:none"><input name="tran_method" type="text" id="tran_method" ltype="text" disabled="disabled"  validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;display:none;"><span style="color:red;display:none;">*</span>调入单位：</td>
			            <td align="left" class="l-table-edit-td" style="display:none"><input name="in_hos_id" type="text" id="in_hos_id" ltype="text" disabled="disabled"  validate="{required:true}" /></td>
			            <td align="left"></td>
			          
			        </tr> 
			        <tr>
			            
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;">${brief}</textarea>
			            </td>
			            <td align="center" class="l-table-edit-td" colspan="4" ><!-- style="display: none;"> -->
							<fieldset style="border:1px solid #00CC00; width: 450px;">
								<legend style="color: #000">扫码出库(注：录入条码敲回车)</legend>
								<table cellpadding="0" cellspacing="0" class="l-table-edit">
									<tr>
										<td align="right" class="l-table-edit-td">
											条形码：
										</td>
										<td align="left" class="l-table-edit-td">
											<input name="begin_bar" type="text" id="begin_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_beginBar()"/>
										</td>
										<td align="right" class="l-table-edit-td">
											至：
										</td>
										<td align="left" class="l-table-edit-td">
											<input name="end_bar" type="text" id="end_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_endBar()"/>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
			        </tr>
			    </table>
			    </form>
		</div>
	
		<div position="center" >
			<div id="maingrid"></div>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="barcodePrint" accessKey="T"><b>条码打印(<u>T</u>)</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
		</div>
		
	</div>

    </body>
</html>
