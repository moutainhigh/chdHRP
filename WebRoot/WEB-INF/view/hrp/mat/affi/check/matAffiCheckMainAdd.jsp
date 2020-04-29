<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
	<script type="text/javascript">
	var grid;
	var gridManager = null;
	document.documentElement.style.overflow='hidden';
     $(function (){
 		$("#layout1").ligerLayout({
 			topHeight:60,
 			centerWidth:888
 		});
         loadDict();
         loadForm();
         loadHead(null);	
         
         $("#store_id").bind("change",function(){
 	    	/* loadHead(null); 
 	    	grid.reRender(); */ 
 	    	grid.columns[3].editor.grid.url = '../../queryMatAffiOutInvList.do?isCheck=false&store_id=' 
					+ liger.get("store_id").getValue().split(",")[0];
 	    	grid.reRender();
 		})
 		$("#dept_id").bind("change",function(){
	    	var para = {
	    			dept_id: liger.get("dept_id").getValue().split(",")[0]
	    	}
	    	liger.get("emp_id").clear();
	    	liger.get("emp_id").set("parms", para);
	    	liger.get("emp_id").reload();
		})
      });  
     
     function  save(){
		grid.endEdit();  	 
 		var check_detail_data = gridManager.rows;     	 
 		var targetMap = new HashMap();     	 
 		var validate_detail_buf = new StringBuffer();
 		var rows = 0;
 		var inva_date="";
		var disinfect_date="";
		
 		if(check_detail_data.length > 0){
   			$.each(check_detail_data, function(d_index, d_content){ 
   	      		if(d_content.inv_id){
	   	      		var value="第"+(d_index+1)+"行";   	      		
	  	      		
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
	   	      		
	   	      		/* var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price+"|"+d_content.inva_date+"|"+d_content.disinfect_date;
	   				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){   					
	   					targetMap.put(key ,value);   					
	   				}else{   					
	   					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价、有效日期、灭菌日期不能重复" + "\n\r");   					  					
	   				} */
	   				rows = rows + 1;
   	      		}	      		    	      		
  			}); 
   		}
 		
 		if(validate_detail_buf.toString()  != ""){  			
  			$.ligerDialog.warn(validate_detail_buf.toString());  			
  			return false;
  		}
 		
  		if(rows == 0){ 			
  			$.ligerDialog.warn('没有明细材料，不能保存！');  			
  			return false;
  		}
  		
         var formPara={
  				dept_id:liger.get("dept_id").getValue().split(",")[0],
  				dept_no:liger.get("dept_id").getValue().split(",")[1],
         		store_id:liger.get("store_id").getValue().split(",")[0],
         		store_no:liger.get("store_id").getValue().split(",")[1],
         		emp_id:liger.get("emp_id").getValue().split(",")[0],
         		brif:$("#brif").val(),
         		create_date:$("#create_date").val(),
         		check_detail_data:JSON.stringify(check_detail_data)
          };
         ajaxJsonObjectByUrl("addMatAffiCheckMain.do?isCheck=true",formPara,function(responseData){
        	 if(responseData.state=="true"){
             	parentFrameUse().query();
             	$.ligerDialog.confirm('是否继续添加盘点单?', function (yes){
             		if(yes){
             			this_refresh();
             		}else{
             			parentFrameUse().openUpdate(responseData.update_para);
             			this_close();
             		}
             	});
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
   
 	function saveMatOutMain(){
     	if($("form").valid()){
         	save();
     	}
	}
 	function loadDict(){
        //字典下拉框
    	$("#copy_code").ligerTextBox({width:180,disabled: true });            
    	$("#create_date").ligerTextBox({width:180});     	
    	autodate("#create_date");           
    	$("#brif").ligerTextBox({width:180});        
    	autocompleteAsync("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_com : '1',is_write:1},true,false,'180');
/*     	autocompleteAsync("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true,{is_com : '1'},true,false,'180'); */
    	//优先加入change事件
    	$("#dept_id").bind("change",function(){
	    	var para = {
	    			dept_id: liger.get("dept_id").getValue().split(",")[0]
	    	}
	    	autocomplete("#emp_id", "../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true,para,false,false,'180');
		})
/*     	autocompleteAsync("#dept_id", "../../queryMatDeptDict.do?isCheck=false", "id", "text", true, true,{is_last : '1'},true,false,'180'); */
    	autocompleteAsync("#dept_id", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last : '1',is_write:1},false,false,'180');
    }  
 	
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '材料编码', name: 'inv_code', align: 'left',width:140,
                    	totalSummary: {
			                    align : 'right',
			                    render: function (suminf, column, cell) {
			                    	return '<div>合计：</div>';
			                    }
		             	}  
                      },
                      { display: '材料名称(E)', name : 'inv_id', textField : 'inv_name',align: 'left',width:240,
                     	 editor : {
 								type : 'select',
 								valueField: 'inv_id', 
 								textField: 'inv_name',
 							    keySupport:true,
 								selectBoxWidth: '80%',
 								selectBoxHeight: 240,
 						      	autocomplete: true,
 						      	highLight : true,
 							    delayLoadGrid : false,
 						                grid: {
 						                	columns : [ 
 						                	            {display : '材料编码', name : 'inv_code', width : 100, align : 'left'}, 
 						                	            {display : '材料名称', name : 'inv_name', width : 240, align : 'left'}, 
 						                	            {display : '规格型号', name : 'inv_model', width : 160, align : 'left'}, 
 						                	            {display : '计量单位', name : 'unit_name', width : 140, align : 'left'}, 
 						                	            {display : '单价', name : 'price', width : 70, align : 'left', 
	 						                               	render : function(rowdata, rowindex, value) { 
	 						                  					return value == null ? "" : formatNumber(value, '${p04006}', 1);
	 						                  				}
 						                	            }, 
 						                	            {display : '条码', name : 'bar_code', width : 80, align : 'left'}, 
 						                	            {display : '账面数量', name : 'cur_amount', width : 140, align : 'left'}, 
 						                	            {display : '账面金额', name : 'cur_money', width : 140, align : 'left',
	 						                               	render : function(rowdata, rowindex, value) {
		 						                  				return value == null ? "" : formatNumber(value, '${p04005}', 1);
		 						                  			}
 						                	           	}, 
 						                	            {display : '有效日期', name : 'inv_date', width : 140, align : 'left'}, 
 						                	            {display : '生产厂商', name : 'fac_name', width : 140, align : 'left'}, 
 						                	            {display : '货位编码', name : 'location_code', width : 140, align : 'left'}, 
 						                	            {display : '货位名称', name : 'location_name', width : 140, align : 'left'}
 						                	            ],
 						                    usePager:false,
 						                    onSelectRow: function (data) {
 												var e = window.event;
 												if (e && e.which == 1) {
 													f_onSelectRow_detail(data);
 												}
 											},
 						         			url : '../../queryMatAffiOutInvList.do?isCheck=false&store_id=' 
 						         					+ liger.get("store_id").getValue().split(",")[0],
 						         			pageSize : 10,
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
 						   					   },
 						         					switchPageSizeApplyComboBox : false,
 						                    selectRowButtonOnly:true
 						                },
 						                delayLoad : true,keySupport : true,autocomplete : true,
 										onSuccess: function (data, grid) {
 											this.parent("tr").next(".l-grid-row").find("td:first").focus();
 										},
 										ontextBoxKeyEnter: function (data) {
 											f_onSelectRow_detail(data.rowdata);
 										}
 						               
                     	 } 
                      },
                      { display: '规格型号', name: 'inv_model', align: 'left',width:100},
                      { display: '计量单位', name: 'unit_name', align: 'left',width:140},
                      { display: '批号', name: 'batch_no', align: 'left',width:100},
                      { display: '条形码', name: 'bar_code', align: 'left',width:120},
                      { display: '有效期', name: 'inva_date', align: 'left',width:120,type: 'date',format: 'yyyy-MM-dd'},
                      { display: '灭菌日期', name: 'disinfect_date', align: 'left',width:120,type: 'date',format: 'yyyy-MM-dd'},
                      { display: '单价', name: 'price', align: 'right',width:140,
                     	 render : function(rowdata, rowindex, value) {
                     		 rowdata.price = value == null ? "" : formatNumber(value, '${p04006}', 0);
           					return value == null ? "" : formatNumber(value, '${p04006}', 1);
           				}
                      },
                      { display: '账面数量', name: 'cur_amount', align: 'left',width:140,
                    	  totalSummary: {
       						align: 'left',
       						render: function (suminf, column, cell) {
       							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
       						}
       					} 	  
                      },
                      { display: '账面金额', name: 'cur_money', align: 'right',width:140,
                     	 render : function(rowdata, rowindex, value) {
                     		 rowdata.cur_money = value == null ? "" : formatNumber(value, '${p04005}', 0);
           					return value == null ? "" : formatNumber(value, '${p04005}', 1);
           				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005}', 1) + '</div>';
    						}
    					}
                      },
                      { display: '盘点数量(E)', name: 'chk_amount', align: 'left',width:90,editor : {type : 'float'},
                     	 render: function (rowdata, rowindex, value) {
                     		 if(rowdata.chk_amount==0){
                     			 rowdata.chk_amount = "0";
                     			 return "0" ;
                     		 }else{
                     			 return value;
                     		 }
                     	 },
                     	 totalSummary: {
      						align: 'left',
      						render: function (suminf, column, cell) {
      							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
      						}
      					} 		 
                      },
                    /*   { display: '盘点数量(E)', name: 'chk_amount', align: 'left',width:140,editor : {type : 'float'},
                    	  totalSummary: {
       						align: 'left',
       						render: function (suminf, column, cell) {
       							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
       						}
       					} 	  
                      }, */
                      { display: '盘点金额', name: 'chk_money', align: 'right',width:140,
                     	 render : function(rowdata, rowindex, value) {
                     		 rowdata.chk_money = value == null ? "" : formatNumber(value, '${p04005}', 0);
           					return value == null ? "" : formatNumber(value, '${p04005}', 1);
           				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005}', 1) + '</div>';
    						}
    					}
                      },
                      { display: '盈亏数量', name: 'pl_amount', align: 'left',width:140,
                    	  totalSummary: {
       						align: 'left',
       						render: function (suminf, column, cell) {
       							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
       						}
       					} 	  
                      },
                      { display: '盈亏金额', name: 'pl_money', align: 'right',width:140,
                     	 render : function(rowdata, rowindex, value) {
                     		 rowdata.pl_money = value == null ? "" : formatNumber(value, '${p04005}', 0);
           					return value == null ? "" : formatNumber(value, '${p04005}', 1);
           				},totalSummary: {
    						align: 'right',
    						render: function (suminf, column, cell) {
    							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005}', 1) + '</div>';
    						}
    					}
                      },
                      { display: '货位', name: 'location_name', align: 'left',width:180},
                      { display: '盈亏说明(E)',name : 'note', align: 'left',width:180,editor : {type : 'text'}}
                      ],
                      usePager : false, width : '100%', height : '100%',  enabledEdit : true, fixedCellHeight:true,
 					selectRowButtonOnly:true, checkbox: true, rownumbers:true, isScroll:true, alternatingRow: true, 
 					onBeforeEdit: f_onBeforeEdit, onAfterEdit: f_onAfterEdit, heightDiff:-20,
 					toolbar: { items: [
				                     	{ text: '保存', id:'add', click: saveMatOutMain,icon:'add' },
				                     	{ line:true },
				                     	{ text: '删除', id:'add', click: deleteRow,icon:'delete' },
				                     	{ line:true },
				                     	{ text: '引入仓库材料', id:'add', click: matCheckByStoreImport,icon:'up' },
				                     	{ line:true },
				                     	{ text: '复制账面数量', id:'copyNum', click: copyNum,icon:'up' },
				                     	{ line:true },
				                     	{ text: '关闭', id:'colse', click: this_close,icon:'close' }
					]}
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
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					batch_no : data.batch_no == null ? "" : data.batch_no,
					bar_code : data.bar_code == null ? "" : data.bar_code,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					cur_money : data.cur_money == null ? "" : data.cur_money,
					price : data.price == null ? "" : data.price,
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
    	if("chk_amount" == e.column.columnname){
    		gridManager.updateCell('chk_money', e.record.chk_amount*e.record.price, e.record); 
    		gridManager.updateCell('pl_amount', (e.record.chk_amount -e.record.cur_amount), e.record); 
    		gridManager.updateCell('pl_money', (e.record.chk_amount -e.record.cur_amount)*e.record.price, e.record); 
    	}
		grid.updateTotalSummary();
    }

	function deleteRow(){ 
		gridManager.deleteSelectedRow();
    }
	
	//复制账面数量
    function copyNum(){
    	var check_detail_data = gridManager.rows;
    	$.each(check_detail_data, function(d_index, d_content){ 
    		gridManager.updateCell('chk_amount', d_content.cur_amount, d_content);
    		gridManager.updateCell('chk_money', d_content.cur_amount * d_content.price, d_content); 
    		gridManager.updateCell('pl_amount', 0, d_content);
    		gridManager.updateCell('pl_money', 0, d_content); 
		}); 
    }
	
 	function is_addRow(){//默认新增一行
		setTimeout(function () {
				grid.addRow();
		}, 100);
	} 
 	
 	//关闭
	function this_close(){
 		frameElement.dialog.close();
 	}
  	
	function matCheckByStoreImport(){//引入仓库材料			
	    var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';			
		var paras = store_id;			
		$.ligerDialog.open({
			url: "matAffiCheckMainByStorePage.do?isCheck=false&paras="+paras, height: 450,width: 900,
					title:'引入仓库材料',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
					buttons: [ 
						{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMatOutFifo(); },cls:'l-dialog-btn-highlight' }, 
						{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
					] 
			});
	}
 	
    </script>

  </head>
  
   <body onload="is_addRow();">
   <div id="pageloading" class="l-loading" style="display: none"></div>
  	<input name="is_dir" type="hidden" id="is_dir" />
	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>盘点单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text" value="自动生成"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>盘点科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>盘点日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">经  办  人：</td>
			            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="4"><input name="brif" type="text" id="brif" ltype="text" /></td>
			            <td align="left"></td>
			        </tr> 
				</table>
			</form>
		
		</div>
	
		<div position="center" >
			<div id="maingrid"></div>
		</div>		
	</div>
    </body>
</html>