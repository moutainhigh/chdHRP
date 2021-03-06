<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var grid; 
    var gridManager = null;
    var userUpdateStr;
    
	var is_check = 0;
    $(function ()
    {	
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);
		loadHotkeys();
		//全部
		$("#is_all").bind('change', function (){
			if($('#is_all').is(':checked')) {
				$("#is_yes").ligerCheckBox({ disabled: true });
	        	$("#is_no").ligerCheckBox({ disabled: true });
	        	is_check = 1;
			}else{
				$("#is_yes").ligerCheckBox({ disabled: false });
	        	$("#is_no").ligerCheckBox({ disabled: false });
	        	is_check = 0;
			} 
		}); 
		
		//已设置
		$("#is_yes").bind('change', function (){
			if($('#is_yes').is(':checked')) {
				$("#is_all").ligerCheckBox({ disabled: true });
	        	$("#is_no").ligerCheckBox({ disabled: true });
	        	is_check = 2;
			}else{
				$("#is_all").ligerCheckBox({ disabled: false });
	        	$("#is_no").ligerCheckBox({ disabled: false });
	        	is_check = 0;
			} 
		});
		
		//未设置
		$("#is_no").bind('change', function (){
			if($('#is_no').is(':checked')) {
				$("#is_yes").ligerCheckBox({ disabled: true });
	        	$("#is_all").ligerCheckBox({ disabled: true });
	        	is_check = 3;
			}else{
				$("#is_yes").ligerCheckBox({ disabled: false });
	        	$("#is_all").ligerCheckBox({ disabled: false });
	        	is_check = 0;
			} 
		});
		
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	 if(!liger.get("store_id").getValue()){
         	$.ligerDialog.warn("请选择库房！");
         	return false;
         }
        
    	grid.options.parms.push({name:'store_id',value : liger.get("store_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'mat_type_id',value : liger.get("mat_type_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'inv_model',value : $("#inv_model").val()}); 
    	grid.options.parms.push({name:'inv_code',value : $("#inv_code").val()}); 
    	grid.options.parms.push({name:'is_check',value : is_check}); 
    	grid.options.parms.push({name:'source_plan',value : liger.get("source_plan").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 		 { display: '材料编码', name: 'inv_code', align: 'left',width:'100',frozen: true},
		                     { display: '材料名称', name: 'inv_name', align: 'left',width:'180',frozen: true},
		                     { display: '规格型号', name: 'inv_model', align: 'left',width:'200',frozen: true},
		                     { display: '计量单位', name: 'unit_name', align: 'left',width:'80',frozen: true},
		                     { display: '生产厂商', name: 'fac_name', align: 'left',width:'200'},
		                     { display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width:'100', align : 'left',
									editor : {
										type : 'select',
										valueField : 'id',
										textField : 'text',
										url : '../../../../queryMatHosPackage.do?isCheck=false',
										keySupport : true,
										autocomplete : true,
									},
									render : function(rowdata, rowindex, value) {
										return rowdata.pack_name;
									} 
							 },
							 { display: '转换量(E)', name: 'num_exchange', align: 'right',width:'80',
								 editor : {type : 'number'},
								 render:function(rowdata){
					            		return formatNumber(rowdata.num_exchange ==null ? 0 : rowdata.num_exchange,2,1);
					             	}
							 },
		                     { display: '安全周期数(E)', name: 'period_num', align: 'right',width:'100',editor : { type : 'number' },
								 render:function(rowdata){
					            		return formatNumber(rowdata.period_num ==null ? 0 : rowdata.period_num,2,1);
					             	}	 
		                     },               
		                     {display: '周期单位(E)', name: 'period', textField : 'text', minWidth : 80,
		     					editor : {
		     						type : 'select',
		     						valueField : 'id',
		     						textField : 'text',
		     						data:matStoreInv_state.Rows,
		     						keySupport : true,
		     						autocomplete : true,
		     					},
		     					align : 'left',
		     					render : function(rowdata, rowindex, value) {
		     						if(rowdata.period == 1){
		     							return "年";
		     						}else if(rowdata.period == 2){
		     							return "季";
		     						}else if(rowdata.period == 3){
		     							return "月";
		     						}else if(rowdata.period == 4){
		     							return "天";
		     						}else{
		     							return "";
		     						}
		     						
		     					}
		     				 },
		                     { display: '最低库存(E)', name: 'low_limit', align: 'right',width:'100',editor : { type : 'number' },
		     					render:function(rowdata){
				            		return formatNumber(rowdata.low_limit ==null ? 0 : rowdata.low_limit,2,1);
				             	}	 
		                     },
		                     { display: '安全库存(E)', name: 'secu_limit', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.secu_limit ==null ? 0 : rowdata.secu_limit,2,1);
					             }	 
		                     },
		                     { display: '最高库存(E)', name: 'high_limit', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.high_limit ==null ? 0 : rowdata.high_limit,2,1);
					             }	 
		                     },
		                     { display: '临近预警量(E)', name: 'warn_amount', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.warn_amount ==null ? 0 : rowdata.warn_amount,2,1);
					             }	 
		                     },{ display: '临近预警包装量(E)', name: 'pack_amount', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.pack_amount ==null ? 0 : rowdata.pack_amount,2,1);
					             }	
		                     }, { 
		                    	 display: '配送周期(E)', name: 'ps_period', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.ps_period ==null ? 0 : rowdata.ps_period,2,1);
					             }
		                     }, { 
		                    	 display: '采购周期(E)', name: 'cg_period', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.cg_period ==null ? 0 : rowdata.cg_period,2,1);
					             }
		                     }, { 
		                    	 display: '最小采购批量(E)', name: 'min_pur', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.min_pur ==null ? 0 : rowdata.min_pur,2,1);
					             }
		                     }, { 
		                    	 display: '日消耗量周期(E)', name: 'rxhl_period', align: 'right',width:'100',editor : { type : 'number' },
		                    	 render:function(rowdata){
					            		return formatNumber(rowdata.rxhl_period ==null ? 0 : rowdata.rxhl_period,2,1);
					             }
		                     },{
		          				display: '计划来源', name: 'source_plan', align: 'left', width:80, reg:"1=科室申领,else=仓库报备",
		          				render : function(rowdata, rowindex, value) {
		          					return rowdata.source_plan == 1 ? "科室申领" : "仓库报备";
		          				}
		          			},{
		          				display: '库存', name: 'cur_amount', align: 'right', width:80,
		          				render:function(rowdata){
				            		return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
				             	}
		          			}
                     ],
                     
                    dataAction: 'server',
                    dataType: 'server',
                    usePager:true,
                    enabledEdit : true,
                    noInsertByEnter:false,
                	alternatingRow : true,
                   	onBeforeEdit : f_onBeforeEdit,
                   	onBeforeSubmitEdit : f_onBeforeSubmitEdit,
                   	onAfterEdit : f_onAfterEdit,
                   	isAddRow : false,
                   	isScroll : true,
                   	url:'querySafeMatStoreInv.do?isCheck=true',
                    width: '100%', height: '100%', checkbox: true,
                    rownumbers:true,delayLoad:true,
                    selectRowButtonOnly:true,//heightDiff: -13e0,
                    toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '全部保存（<u>S</u>）', id:'save', click: save, icon:'save' },
    	                { line:true }, 
    	                { text: '删除（<u>D</u>）', id:'del', click: del, icon:'close' },
    	                { line:true }, 
    	                { text: '批量设置（<u>D</u>）', id:'batchSet', click: batchSet, icon:'edit' },
    				]}
                   });
    	
        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    //获得明细数据	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 
 	
    var rowindex_id = "";
	var column_name = "";
	
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}		
	
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {		
		return true;
	}
	
	// 编辑单元格之后的动作
	function f_onAfterEdit(e) {
		if(e.value != "" && e.value != 0 && e.value != undefined){
			if(e.column.name == "num_exchange"){
				if(e.record.pack_amount != undefined && e.record.pack_amount != "" && e.record.pack_amount != 0){
					grid.updateCell('warn_amount', e.record.pack_amount * e.value, e.rowindex);
				}
				if(e.record.warn_amount != undefined && e.record.warn_amount != "" && e.record.warn_amount != 0){
					grid.updateCell('pack_amount', e.record.warn_amount / e.value, e.rowindex);
				}
				
			}else if(e.column.name == "pack_amount"){
				
					if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
						grid.updateCell('warn_amount', e.value * e.record.num_exchange, e.rowindex);
					}
					if(e.record.warn_amount != undefined && e.record.warn_amount != "" && e.record.warn_amount != 0){
						grid.updateCell('num_exchange', e.record.warn_amount / e.value, e.rowindex);
					}
				
			}else if(e.column.name == "warn_amount"){
				
					if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
						grid.updateCell('pack_amount', e.value / e.record.num_exchange, e.rowindex);
					}
					if(e.record.pack_amount != undefined && e.record.pack_amount != "" && e.record.num_exchange != 0){
						grid.updateCell('num_exchange', e.value / e.record.pack_amount, e.rowindex);
					}
				
			}
		}
		
		return true;
	}
	
	//删除
    function del(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要删除的行！');
        	return;
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
					//表的主键
					this.group_id +"@"+ 
					this.hos_id +"@"+ 
					this.copy_code +"@"+ 
					this.store_id +"@"+ 
					this.inv_id 
				)
            });
            
            $.ligerDialog.confirm('确定要删除吗?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteSafeMatStoreInv.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    } 

	// 保存
    function save(){
		$.ligerDialog.confirm('确定要保存吗?', function (yes){
			var data = grid.getData();
			var ParamVo =[];
			$(data).each(function (i,o){
				if(this.inv_code){
   					ParamVo.push(
						//表的主键
						this.group_id   +"@"+ 
						this.hos_id   + "@"+ 
						this.copy_code   +"@"+ 
						this.store_id  +"@"+ 
						this.inv_id +"@"+
						this.pack_code +"@"+ 
						this.num_exchange +"@"+ 
						this.period_num +"@"+ 
						this.low_limit +"@"+ 
						this.secu_limit +"@"+ 
						this.high_limit +"@"+ 
						this.pack_amount +"@"+ 
						this.warn_amount +"@"+ 
						this.period +"@"+ 
						this.ps_period +"@"+ 
						this.cg_period +"@"+ 
						this.min_pur +"@"+ 
						this.rxhl_period
					)
				}
			});
			
			if(yes){
				ajaxJsonObjectByUrl("addSafeMatStoreInv.do?isCheck=true",{ParamVo : ParamVo.toString()},function(responseData){
					if(responseData.state=="true"){
						query();
					}
				});
			}
		});
	}

    function loadDict(){
        //字典下拉框
    	autocompleteAsync("#store_id","../../../../queryMatStore.do?isCheck=false","id","text",true,true,'',true,'','160');
    	autocomplete("#mat_type_id","../../../../queryMatTypeDict.do?isCheck=false","id","text",true,true,'',false,'','160');
    	   
        $("#inv_code").ligerTextBox({width:160}); 
        $("#inv_model").ligerTextBox({width:160});
        
        $("#is_all").ligerCheckBox({ disabled: false });
        $("#is_yes").ligerCheckBox({ disabled: false });
        $("#is_no").ligerCheckBox({ disabled: false });
        
        autoCompleteByData("#source_plan", matInv_sourcePlan.Rows, "id", "text", true, true, "", false);

         
     }  
    //键盘事件
	  function loadHotkeys(){
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', del);
    }
	
	function batchSet(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要修改的材料！');
			return false;
		}
		
		var invData = [];
		$.each(data, function(){
			if(this.inv_id){
				invData.push({
					inv_id: this.inv_id
				});
			}
		})
		
		$.ligerDialog.open({
			title:'批量设置',
			url: 'matSafeStoreInvUpdateBatchPage.do?isCheck=false',
			width: 600, 
			height: 320, 
			data: {invData: invData, store_id: liger.get("store_id").getValue().split(",")[0]}, 
			modal: true, showToggle: false, isResize: true 
		}); 
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"> 
    	<tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>仓库信息<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>物资类别:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="mat_type_id" type="text" id="mat_type_id" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>物资材料信息:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr>
      
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>规格型号:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>计划来源:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="source_plan" type="text" id="source_plan" ltype="text" validate="{maxlength:20}" />
            </td>
            <td align="left"></td>
            
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>查询范围:</b></td>
          <td align="left" style="padding-left:20px;">
          		<input type="checkbox" id="is_all" />&nbsp;全部&nbsp;&nbsp;&nbsp;&nbsp;
          		<input type="checkbox" id="is_yes" />&nbsp;已设置&nbsp;&nbsp;&nbsp;&nbsp;
          		<input type="checkbox" id="is_no" />&nbsp;未设置
          </td>   
        </tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
