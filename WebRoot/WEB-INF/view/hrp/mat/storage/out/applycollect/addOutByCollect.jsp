<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
     var grid;
     var detailGrid;  
     var gridManager = null;
     var is_com = "${matOutMain.is_com}";
		
     $(function (){
		$("#layout1").ligerLayout({
			topHeight:125,
        	centerBottomHeight:80
		});
        loadDict();
        //loadForm();
        loadHead(null);	
        loadHotkeys();
      	//仓库绑定change事件
		$("#bus_type_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
		$("#store_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
		//科室绑定change事件
		$("#dept_id").bind("change",function(){
			if(liger.get("dept_id").getValue()){
		    	var para = {dept_id : liger.get("dept_id").getValue() == "" ? "0" : liger.get("dept_id").getValue().split(",")[0]};
		    	liger.get("dept_emp").set("parms", para);
		    	liger.get("dept_emp").reload();
		    	liger.get("dept_emp").setValue("");
		    	liger.get("dept_emp").setText("");
		    	//autocomplete("#dept_emp", "../../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true, para, true);
			}
		});
     });  
     function validateGrid(){
    	 var validate_detail_buf = new StringBuffer();
     	
 		//弹出框验证 
     	var bus_type_code = liger.get("bus_type_code").getValue();if(!bus_type_code){validate_detail_buf.append("请选择业务类型<br>");}
      	
     	var store_id = liger.get("store_id").getValue();if(!store_id){validate_detail_buf.append("请选择业务仓库<br>");}
     	
     	var out_date = $("#out_date").val();if(!out_date){validate_detail_buf.append("请选择出库日期<br>");}
     	
     	var dept_id = liger.get("dept_id").getValue();if(!dept_id){validate_detail_buf.append("请选择领用科室<br>");}
     	
     	var dept_emp = liger.get("dept_emp").getValue();//if(!dept_emp){validate_detail_buf.append("请选择领料人<br>");}
 		
     	var out_detail_data = gridManager.rows;
     	var targetMap = new HashMap();
  		var store_inv = "";  //用于判断是否属于该仓库
  		var rows = 0;
  		var inva_date="";
  	    var disinfect_date="";
  	    
  		if(out_detail_data.length > 0){

  			$.each(out_detail_data, function(d_index, d_content){ 
 				if(typeof(d_content.inv_id) != "undefined" && d_content.inv_id != null && d_content.inv_id != ""){  		  
 	 	      		var value="第"+(d_index+1)+"行";

 	 	      		if(typeof(d_content.amount) == "undefined" || d_content.amount == "" || d_content.amount == 0){
 						validate_detail_buf.append(value+"数量不能为空或零 请输入<br>");
 	       		  	}
 	 	      		
 	 	      		if(bus_type_code == "21"){
 		 	      		if(d_content.amount > 0){
 		 	      			validate_detail_buf.append(value+"退库数量不能大于0 请重新输入<br>");
 		   		  		}
 	 	      		}else{
 		 	      		if(d_content.amount < 0){
 		 	      			validate_detail_buf.append(value+"出库数量不能小于0 请重新输入<br>");
 		   		  		}
 		 	      		if(d_content.imme_amount - d_content.amount <0){
 		 	      			validate_detail_buf.append(value+"数量不能大于即时库存数据数量 请重新输入<br>");
 		   		  		}
 	 	      		}
 	 	      		
	 	 	      	if(typeof(d_content.inva_date) != "undefined" && d_content.inva_date != null && d_content.inva_date != ""){
		      			inva_date = d_content.inva_date;
		      		}else{
		      			inva_date = "";
		      		}
		      		
		      		if(typeof(d_content.disinfect_date) != "undefined" && d_content.disinfect_date != null && d_content.disinfect_date != ""){
		      			disinfect_date = d_content.disinfect_date;
		      		}else{
		      			disinfect_date = "";
		      		}
		      		
		      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price+"|"+inva_date+"|"+disinfect_date;
		      		if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
						targetMap.put(key ,value);
					}else{
						validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价、有效日期、灭菌日期不能重复"+ "\n\r"); 
					}
	      		
 	 	      		/* var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
 	 				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 	 					targetMap.put(key ,value);
 	 				}else{
 	 					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价不能重复<br>");
 	 				} */
 	 				store_inv += d_content.inv_id + ",";
 	 				rows += 1;
 				}
 			}); 
  		}

  		if(rows == 0){
  			$.ligerDialog.warn('请选择材料');
  			return false;
  		}
  		
  		//判断仓库材料关系
  		if(store_inv.length > 0){
 			var para = {
 				inv_ids: store_inv.substring(0, store_inv.length-1), 
 				store_id: liger.get("store_id").getValue().split(",")[0]
 			}
 			ajaxJsonObjectByUrl("../../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData){
 				if(responseData.state=="false"){
 					validate_detail_buf.append("以下材料不在该仓库中：<br>"+responseData.inv_ids);
 				}
 			}, false);
  		}

  		if(validate_detail_buf.toString()  != ""){
  			$.ligerDialog.warn(validate_detail_buf.toString());
  			return false;
  		}
    	 
    	 return true;
     }
     function  save(){
		grid.endEdit();
    	if(validateGrid()){
    		$("#save").ligerButton({disabled : true});
    		var formPara={
            		bus_type_code:liger.get("bus_type_code").getValue(),
            		store_id:liger.get("store_id").getValue().split(",")[0],
            		store_no:liger.get("store_id").getValue().split(",")[1],
            		brief:$("#brief").val(),
            		out_date:$("#out_date").val(),
            		dept_id:liger.get("dept_id").getValue().split(",")[0],
            		dept_no:liger.get("dept_id").getValue().split(",")[1],
            		dept_emp:liger.get("dept_emp").getValue() == null ? "" : liger.get("dept_emp").getValue().split(",")[0],
            		use_code : liger.get("use_code").getValue() == null ? "" : liger.get("use_code").getValue(),
            		proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
            		is_dir: $("#is_dir").val(),
            		detailData: JSON.stringify(gridManager.rows)
    		};
            var saveUrl;
            var pageUrl;
            var pageTitle;
            if(is_com == "0"){
            	pageTitle = "出库单修改";
            	saveUrl = "../applycheck/addMatOutMainByApp.do?isCheck=false";
            	pageUrl = "hrp/mat/storage/out/outlibrary/matOutMainUpdatePage.do?isCheck=false";
            }else{
            	pageTitle = "代销出库单修改";
            	saveUrl = "../applycheck/addMatAffiOutByApp.do?isCheck=false";
            	pageUrl = "hrp/mat/affi/out/matAffiOutCommonUpdatePage.do?isCheck=false";
            }
            console.log(formPara);
            ajaxJsonObjectByUrl(saveUrl, formPara, function(responseData){
                if(responseData.state=="true"){
                	parentFrameUse().query();
                	var voStr = responseData.update_para.split(",");
            		var paras = "group_id=" + voStr[0].toString() + "&" 
            			+ "hos_id=" + voStr[1].toString() + "&" 
            			+ "copy_code=" + voStr[2].toString() + "&" 
            			+ "out_id=" + voStr[3].toString() + "&" 
            			+ "store_id=" + voStr[4].toString();
                	parent.$.ligerDialog.open({
    					title: pageTitle,
    					height: $(window).height(),
    					width: $(window).width(),
    					url: pageUrl + "&" + paras.toString(),
    					modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
    				});
                	this_close();
                }
            });
    	} 
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
   
    function saveMatOutMain(){
        if($("form").valid()){
            save();
        }
	}
    function loadDict(){
    	//字典下拉框
    	$("#out_no").ligerTextBox({width:160,disabled: true }); 
            
    	$("#out_date").ligerTextBox({width:160}); 
    	autodate("#out_date");
    	
    	$("#brief").ligerTextBox({width:465}); 
            
    	autocompleteAsync("#store_id", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true, {is_com : is_com});
    	if("${matOutMain.store_id}"){
	    	liger.get("store_id").setValue("${matOutMain.store_id},${matOutMain.store_no}");
	    	liger.get("store_id").setText("${matOutMain.store_code} ${matOutMain.store_name}");
    	}
    	var bus_type_code_paras={sel_flag : is_com == "0" ? "out" : "aout"};
    	autocomplete("#bus_type_code", "../../../queryMatBusType.do?isCheck=false", "id", "text", true, true,bus_type_code_paras, false, "${matOutMain.bus_type_code}");
		
    	autocompleteAsync("#dept_id", "../../../queryMatDeptDict.do?isCheck=false", "id", "text", true, true,{is_last: 1});
    	if("${matOutMain.dept_id}"){
	    	liger.get("dept_id").setValue("${matOutMain.dept_id},${matOutMain.dept_no}");
	    	liger.get("dept_id").setText("${matOutMain.dept_code} ${matOutMain.dept_name}");
    	}
    	//autocomplete("#dept_emp", "../../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true, {dept_id: '${matOutMain.dept_id}'},false,"${matOutMain.dept_emp},${matOutMain.emp_no}"); 
    	autocomplete("#dept_emp", "../../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true); 
    	if("${matOutMain.dept_emp}"){
	    	liger.get("dept_emp").setValue("${matOutMain.dept_emp},${matOutMain.emp_no}");
	    	liger.get("dept_emp").setText("${matOutMain.emp_code} ${matOutMain.emp_name}");
    	} 
    	autocomplete("#use_code", "../../../queryMatOutUse.do?isCheck=false", "id", "text", true, true);
    	if("${matOutMain.use_code}"){
    		liger.get("use_code").setValue("${matOutMain.use_code}");
        	liger.get("use_code").setText("${matOutMain.use_name}");
    	}
    	autocomplete("#proj_code", "../../../queryMatProj.do?isCheck=false", "id", "text", true, true);
    	if("${matOutMain.proj_id}"){
    		liger.get("proj_code").setValue("${matOutMain.proj_id}");
        	liger.get("proj_code").setText("${matOutMain.proj_code} ${matOutMain.proj_name}");
    	} 
		$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
    
	function loadHead(){ 
    	var matOutDetail  = ${matOutDetail};
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '材料编码', name: 'inv_code', align: 'left',width:150,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left',width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : '80%',
	         		selectBoxHeight : 370,
	         		grid : {
	         			columns : [ {
	         				display : '材料编码', name : 'inv_code', width : 150, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	         				display : '物资类别', name : 'mat_type_name', width : 80, align : 'left'
	         			}, {
	         				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
	         			}, 
	         			{ 
	        				display: '条形码', name: 'bar_code', align: 'left',width:120
	        			},
	         			{
	         				display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 180 
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 80, align : 'right'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 80, align : 'right'
	         			}, {
	         				display : '是否收费', name : 'is_charge', width : 80, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
	         			}, {
	         				display : '货位', name : 'location_name', width : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
	         			url : (is_com == "0" ? '../../../queryMatStockInvList.do?isCheck=false&is_com=0' : '../../../queryMatAffiOutInvList.do?isCheck=false&is_com=1')
	         					+'&store_id=' + liger.get("store_id").getValue().split(",")[0] 
	         					+ '&bus_type_code=' + liger.get("bus_type_code").getValue(),
	         			pageSize : 200,
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
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		},
	         		ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
	         	}
	         }, { 
	         	display: '规格型号', name: 'inv_model', align: 'left',width:180
	         }, { 
	         	display: '计量单位', name: 'unit_name', align: 'left',width:80
	         }, { 
	         	display: '批号', name: 'batch_no', align: 'left',width:180
	         }, { 
	         	display: '当前库存', name: 'cur_amount', align: 'right',width:80
	         }, { 
	         	display: '即时库存', name: 'imme_amount', align: 'right',width:80
	         }, { 
	         	display: '数量(E)', name: 'amount', align: 'right', width:80, editor : {type : 'number'},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
	         }, { 
				display: '单价', name: 'price', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '有效日期', name: 'inva_date', align: 'left',width:120
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:120
			}, { 
				display: '条形码', name: 'bar_code', align: 'left',width:120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right',width:100,
				/*editor : {
					type : 'numberbox',
					precision : '${p04006 }'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '零售价格', name: 'sell_price', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
					return value == null ? "" : formatNumber(value, '${p04072 }', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
					return value == null ? "" : formatNumber(value, '${p04073 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04073 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '货位名称', name: 'location_name', align: 'left', width:180
			}, { 
				display: '供应商', name: 'sup_id', textField: 'sup_name', align: 'left', width:180, hide: is_com? false : true,
			}, { 
				display: '供应商', name: 'sup_no', align: 'left', width:80, hide: true,
			}, { 
				display: '备注(E)', name: 'note', align: 'left', width:180,
				editor : {
					type : 'text'
				}
			}, { 
				display: '批次数量合计', name: 'sum_amount', width:80, hide: true 
			}, { 
				display: '批次明细', name: 'inv_detail_data', width:80, hide: true 
			},{ 
				display: '对应关系', name: 'other_ids', width:150, hide: true 
			} ],
			usePager : false, inWindow: false, width : '100%',height : '100%',
			enabledEdit : true,fixedCellHeight:true, heightDiff: 28, 
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			data: matOutDetail, checkbox: true, rownumbers:true, frozen:false,isAddRow:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
			onLoaded: function(){this.addRowEdited()}, 
			toolbar: { items: [
				{ text: '删除（<u>D</u>）', id:'delete', click: deleteRow,icon:'delete' },
				{ line:true },
				/* { text: '定向出库（<u>I</u>）', id:'dirImp', click: matOutDirImport,icon:'up' },
				{ line:true },
				{ text: '配套导入（<u>M</u>）', id:'matchImp', click: matOutMatchedImport,icon:'up' },
				{ line:true },
				{ text: '历史使用导入（<u>H</u>）', id:'hisImp', click: matOutHistoryImport,icon:'up' },
				{ line:true } */
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }    
	
    //键盘事件
	function loadHotkeys() {
		hotkeys('D', deleteRow);
		hotkeys('I', matOutDirImport);
		hotkeys('M', matOutMatchedImport);
		hotkeys('H', matOutHistoryImport);
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;		
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
					location_name : data.location_name == null ? "" : data.location_name
				});
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
    		grid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record);
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    	}
    	grid.updateTotalSummary();
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		*/
		return true;
	} 
    
    //删除
    function deleteRow(){ 
    	
    	gridManager.deleteSelectedRow();
    }
    
	//批量添加明细数据
    function add_Rows(data){
    	delete_allRows();
    	grid.addRows(data);
    }
    //清空表格
    function delete_allRows(){
		for (var i = 0, l = gridManager.rows.length; i < l; i++) {  
			var o = gridManager.getRow(i);
			if (o['__id'] in gridManager.records)
				gridManager._deleteData.ligerDefer(gridManager, 10, [ o ]); 
		}  
		gridManager.reRender.ligerDefer(gridManager, 20);
	}
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '材料编码', name: 'inv_code',width:100,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : 800,
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '材料编码', name : 'inv_code', width : 150, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 80, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 80, align : 'right'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 80, align : 'right'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow : f_detail_onSelectRow_detail,
	         			url : (is_com == "0" ? '../../../queryMatStockInvDetailList.do?isCheck=false&is_com=0' : '../../../queryMatAffiOutDetailInvList.do?isCheck=false&is_com=1')
	         					+'&store_id=' + liger.get("store_id").getValue().split(",")[0]  + '&bus_type_code=' 
	         					+ liger.get("bus_type_code").getValue() + '&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price,
	         			pageSize : 10
	         		},
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' ,width : 80
    		}, { 
     			display : '库存', name : 'cur_amount', width : 80, align : 'right'
     		}, { 
     			display : '即时库存', name : 'imme_amount', width : 80, align : 'right', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 60, align : 'right', editor : {type : 'float'},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '单价', name: 'price', width: 100, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:100,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 100, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:100,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 100, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04072 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:100,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04073 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04073 }', 1)+ '</div>';
                    }
                }
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true, checkbox: true,
    		rownumbers: true, enabledEdit : true, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',columnWidth:80, data : f_getInvDetailData(row),
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    		]} 
		});

		//默认添加行
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
				var rows = jsonRowsToObject(rowdata.inv_detail_data, "out_detail_id"); 
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("store_id").getValue().split(",")[0], 
					bus_type_code : liger.get("bus_type_code").getValue(),
            		inv_id : rowdata.inv_id,
            		batch_no : rowdata.batch_no,
            		bar_code : rowdata.bar_code,
            		price : rowdata.price,
            		amount : rowdata.amount
            	}
        		ajaxJsonObjectByUrl("../../../queryMatInvByFifo.do?isCheck=false",invPara,function(responseData){
        			data = responseData;
                }, false);
				//变更主数据中材料批次信息
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
		//alert(dataDetail.length);
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
    		var arr = gridRowData.other_ids.split('@');
    		grid.updateCell('other_ids', arr[0]+"@"+arr[1]+"@"+sumAmount, gridRowData); 
    		//console.log(gridRowData.other_ids);
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
		/* if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		} */
		return true;
	}
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
    
    
 	
	function matOutFifoImport(){//选择材料

		var store_id = liger.get("store_id").getValue();
		var store_text = liger.get("store_id").getText();
    	
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		
    	//var dept_id = liger.get("dept_id").getValue()?liger.get("dept_id").getValue():'null';
    	
    	//var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
		var out_id = "null";
		var paras = store_id+"@"+out_id+"@"+store_text;
		
		$.ligerDialog.open({url: "matOutMainFifoPage.do?isCheck=false&paras="+paras, height: 500,width: 900, title:'选择材料',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function matOutMatchedImport(){//配套导入
    	
    	var dept_id = liger.get("dept_id").getValue();
    	
    	if(!dept_id){
    		
    		$.ligerDialog.warn('请选择领用科室');
    		
    		return false;
    	}
    	
		var store_id = liger.get("store_id").getValue();
    	
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		
    	//var dept_id = liger.get("dept_id").getValue()?liger.get("dept_id").getValue():'null';
    	
    	//var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
		
		var paras = dept_id+"@"+store_id;
		
		$.ligerDialog.open({url: "matOutMainMatchedPage.do?isCheck=false&paras="+paras, height: 500,width: 900, title:'配套导入',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function matOutDirImport(){//定向导入
		
    	var dept_id = liger.get("dept_id").getValue();
    	
    	if(!dept_id){
    		
    		$.ligerDialog.warn('请选择领用科室');
    		
    		return false;
    	}
    	
		var store_id = liger.get("store_id").getValue();
    	
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		
		var paras = dept_id+"@"+store_id;
		
		$.ligerDialog.open({
			title:'定向导入',
			url: "matOutMainDirPage.do?isCheck=false&paras="+paras, 
			height: $(window).height()-100,
			width: $(window).width()-200,
			//height: 500,
			//width: 900, 
			modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,
			buttons: [ 
			           { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}

    function matOutHistoryImport(){//历史导入
		
		var dept_id = liger.get("dept_id").getValue();
    	if(!dept_id){
    		$.ligerDialog.warn('请选择领用科室');
    		return false;
    	}
    	
		var store_id = liger.get("store_id").getValue();
    	if(!store_id){
    		$.ligerDialog.warn('请选择仓库');
    		return false;
    	}

    	var para = "store_id=" + liger.get("store_id").getValue() +
    		"&store_text=" + liger.get("store_id").getText() +
    		"&dept_id=" + liger.get("dept_id").getValue() +
    		"&dept_text=" + liger.get("dept_id").getText();;
		
		$.ligerDialog.open({
			title:'历史导入',
			height: $(window).height()-100,
			width: $(window).width()-200,
			url: "matOutMainHistoryPage.do?isCheck=false&"+para, 
			modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,
			buttons: [ 
				{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}
 
	function is_addRow(){//默认新增一行
		setTimeout(function () {
			grid.addRow();
		}, 100);
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}

	//关闭当前弹出框
	function this_close(){
		frameElement.dialog.close();
	}
    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
  	<input name="is_dir" type="hidden" id="is_dir" />
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_no" type="text" id="out_no" ltype="text" value="自动生成"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>制单日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date" type="text" id="out_date" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><!-- <span style="color:red">*</span> -->领料人：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_emp" type="text" id="dept_emp" ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	物资用途：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="use_code" type="text" id="use_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	项目：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="4"><input name="brief" type="text" id="brief" ltype="text" value="${matOutMain.brief}" validate="{maxlength:50}" /></td>
			            <td align="left"></td>
			        </tr> 
			    </table>
			</form>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		<div position="bottom" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
				<tr>	
					<td align="center" class="l-table-edit-td" >
						<button id ="save" accessKey="S"><b>保存（<u>S</u>）</b></button>
						&nbsp;&nbsp;
						<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
	</div>

    </body>
</html>
