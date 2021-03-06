<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr; 
    var isHideCheck = '${p04015 }' == 1 ? false : true;
	var isUseAffiStore = '${p04044 }' == 1 ? true : false;
    var show_detail=0; 
    var renderFunc = {
			  
    		amount_money:function(value){//金额
				return formatNumber(value, '${p04005 }', 1);
			} ,
			state : function (value){
				 if(value == 1){
					return "未审核";
				}else if(value == 2){
					return "已审核";
				}else if(value == 3){
					return "已确认";
				}
			}
	}; 
    
    $(function (){
    	loadDict()//加载下拉框
   	 	//加载数据
   	 	//loadHead(null);	
		loadHotkeys();
		showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0 ;
		//query();
    });
    
    function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail==0){
			$("#batch_no").val();
		}
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind("click");
		}
		loadHead();
		//query();
	 }
    //查询
    function  query(){
    		grid.options.parms=[];
			grid.options.newPage=1;
        	//根据表字段进行添加查询条件
       
    	  	grid.options.parms.push({name:'out_date_start',value:$("#out_date_start").val()}); 
    	  	grid.options.parms.push({name:'out_date_end',value:$("#out_date_end").val()}); 
    	  	grid.options.parms.push({name:'bus_type_code',value:liger.get("bus_type_code").getValue()}); 
    	  	grid.options.parms.push({name:'maker_code',value:liger.get("maker_code").getValue()}); 
    	  	grid.options.parms.push({name:'confirm_date_start',value:$("#confirm_date_start").val()}); 
    	  	grid.options.parms.push({name:'confirm_date_end',value:$("#confirm_date_end").val()});
    	  	grid.options.parms.push({name:'affirm',value:liger.get("affirm").getValue()});
		
    	  	grid.options.parms.push({
    			name : 'proj_id',
    			value : liger.get("proj_code").getValue()
    		});
    	  	var store_id = liger.get("store_id").getValue();

        	if(store_id){
        		grid.options.parms.push({name:'store_id',value:store_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'store_no',value:store_id.split(",")[1]}); 
        	}
        	var dept_id = liger.get("dept_id").getValue();
        	if(dept_id){
        		grid.options.parms.push({name:'dept_id',value:dept_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'dept_no',value:dept_id.split(",")[1]}); 
        	}
        	var emp_id = liger.get("emp_id").getValue();
        	if(emp_id){
        		grid.options.parms.push({name:'emp_id',value:emp_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'emp_no',value:emp_id.split(",")[1]}); 
        	}
        	var state = liger.get("state").getValue()
        	if(state){
        		grid.options.parms.push({name:'state',value:state}); 
        	}
        	grid.options.parms.push({
    			name : 'out_no',
    			value : $("#out_no").val()
    		}); 
        	grid.options.parms.push({
    			name : 'brief',
    			value : $("#brief").val()
    		});
        	
   /*      		grid.options.parms.push({name:'inv_code',value:liger.get("inv_code").getText().split(" ")[0]}); */
   				grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()});
   				grid.options.parms.push({name :'bar_code',value : $("#bar_code").val()});
        		grid.options.parms.push({name:'batch_no',value:$("#batch_no").val()});
        		grid.options.parms.push({name :'inv_model',value : $("#inv_model").val()});
        		
        		//grid.options.parms.push({name:'mat_type_code',value:liger.get("mat_type_code").getValue().split(",")[1]});
        	grid.options.parms.push({
			name : 'mat_type_code',//物资类别
			value : liger.get("mat_type_code").getText() == null ? "" :liger.get("mat_type_code").getText().split(" ")[0]
    	});

        	
        	grid.options.parms.push({name : 'print_state',value : $("#print_state").val()}); 

    		//加载查询条件
    		grid.loadData(grid.where);
			$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	if(show_detail=="1"){
    		grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '出库单号', name: 'out_no', align: 'left', width: '130',frozen :true,
    	                    	 render : function(rowdata, rowindex, value) {
    	                    		if(value == '合计'){
    	                    			return value ; 
    	                    		}else{
    	                    			return '<a href=javascript:openUpdate("' 
    	       							+ rowdata.group_id
    	       							+ ',' + rowdata.hos_id 
    	       							+ ',' + rowdata.copy_code
    	       							+ ',' + rowdata.out_id
    	       							+ ',' + rowdata.store_id
    	       							+ '")>'+rowdata.out_no+'</a>';
    	                    		}
    	       					}		 
    	                     },
    	                     { display: '摘要', name: 'brief', align: 'left',width:'200'},
    	                     { display: '仓库', name: 'store_name', align: 'left',width:'120'},
    	                     { display: '领用科室', name: 'dept_name', align: 'left',width:'120'},
    	                      /*{ display: '领料员', name: 'emp_name', align: 'left',width: '80'}, */
    	                     { display: '材料编码', name: 'inv_code', align: 'left', width: '120'},
    	     		 		{ display: '材料名称', name: 'inv_name', align: 'left', width: '120'},
    	     		 		{ display: '计量单位', name: 'unit_name', align: 'left', width: '60'},
    	     		 		{ display: '规格型号', name: 'inv_model', align: 'left', width: '120'},
    	     		 		{ display: '单价', name: 'price', align: 'right', width: '80',
    	     		 			render: function (rowdata, rowindex, value) {
    	 							return formatNumber(value, '${p04006 }', 1);
    	 						}
    	     		 		},
    	     		 		{ display: '数量', name: 'amount', align: 'right', width: '80'},
    	     		 		{ 
    	     		 			display: '金额', name: 'amount_money', align: 'right', width: '90',
    	     		 			render : function(rowdata, rowindex, value) {
    	     						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
    	     					}
    	     		 		},
    	     		 		{ display: '有效日期', name: 'inva_date', align: 'left', width: '90'},
    	     		 		{ display: '批号', name: 'batch_no', align: 'left', width: '80'},
    	     		 		{ display: '条形码', name: 'bar_code', align: 'left', width: '80'},
    	     		 		{ display: '生产厂商', name: 'fac_name', align: 'left', width: '80'},
   	                    /*  { display: '状态', name: 'state', align: 'left',width: '80',hide:true
    	     		 			}, */
   	                    
     	                 	{ display: '状态', name: 'state_name', align: 'left',width: '80'
    	     		 				}, 
    	                   	/* { display: '制单人', name: 'user_name_make', align: 'left',width: '80'}, */
    	                     { display: '制单日期', name: 'out_date', align: 'left',width: '90'},
    	                     { display: '出库日期', name: 'confirm_date', align: 'left',width: '90'},	 
    	                     { display: '确认人', name: 'user_name_confirmer', align: 'left',width: '80'},
    	                     
    	                     { display: '业务类型', name: 'bus_type_name', align: 'left',width: '80'},
    	                     {display: '物资类别', name: 'mat_type_name', align: 'left',width: 100,},
    	                     {display: '是否打印', name: 'print_state', align: 'left',width: 100,hide:true
    	                   
    	                     },
    	                     {display: '是否打印', name: 'print_state_name', align: 'left',width: 100,
    	    	                   
    	                     }
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOutMain.do?isCheck=true&show_detail=1',
    	                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
    	                     gid: 'detailGrid', 
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     checkBoxDisplay:isCheckDisplay,
    	                     onsuccess:function(){
    	         				//is_addRow();    
    	         			},
    						 toolbar: { items: [
    	                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
    	                     	{ line:true },
    							{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    							{ line:true },
    							{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    							{ line:true },
    							{ text: '审核（<u>N</u>）', id:'audit', click: audit,icon:'audit', hide: isHideCheck },
    							{ line:true, hide: isHideCheck },
    							{ text: '消审（<u>U</u>）', id:'unaudit', click: unAudit,icon:'unaudit', hide: isHideCheck },
    							{ line:true, hide: isHideCheck },
    							{ text: '出库确认（<u>F</u>）', id:'confirm', click: confirmData,icon:'account' },
    							{ line:true },
    							{ text: '申请导入（<u>S</u>）', id:'appImp', click: apply_imp,icon:'up' },
    							{ line:true },
    							{ text: '科室需求计划导入（<u>Q</u>）', id:'deptRImp', click: deptR_imp,icon:'up' },
    							{ line:true } ,
    							{ text: '批量打印', id:'print', click: print, icon:'print' },
    							{ line:true } ,
    							{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
    							{ line:true },
    							{ text: '复制（<u>C</u>）', id:'copy', click: copyMatOutMain,icon:'copy' },
    							{ line:true },
    							{ text: '冲账（<u>O</u>）', id:'offset', click: mergeBalanceMatOutMain,icon:'offset' },
    							
    						]},
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    	    					if(rowdata.out_id == null){
    	    						$.ligerDialog.warn('请选择数据 ');
    	    						return ; 
    	    					}
    							openUpdate(
    									rowdata.group_id   + "," + 
    									rowdata.hos_id   + "," + 
    									rowdata.copy_code   + "," + 
    									rowdata.out_id   + "," + 
    									rowdata.store_id 
    								);
    	    				} 
    	                   });
    	}else{
    		grid = $("#maingrid").ligerGrid({ 
    	           columns: [ 
    	                     { display: '出库单号', name: 'out_no', align: 'left', width: '130',frozen :true,
    	                    	 render : function(rowdata, rowindex, value) {
    	                    		if(value == '合计'){
    	                    			return value ; 
    	                    		}
    	       						return '<a href=javascript:openUpdate("' 
    	       							+ rowdata.group_id
    	       							+ ',' + rowdata.hos_id 
    	       							+ ',' + rowdata.copy_code
    	       							+ ',' + rowdata.out_id
    	       							+ ',' + rowdata.store_id
    	       							+ '")>'+rowdata.out_no+'</a>';
    	       					}		 
    	                     },
    	                     { display: '摘要', name: 'brief', align: 'left',width:'200'},
    	                     { display: '制单人', name: 'user_name_make', align: 'left',width: '80'},
    	                     { display: '金额', name: 'amount_money', align: 'right',width:'90',
    	                    	 render : function(rowdata, rowindex, value) {
    	          					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    	          				}
    	                     },
    	                    /*  { display: '状态', name: 'state', align: 'left',width: '80',hide:true,
    	                    	
    	                    	 }, */
    	                      { display: '状态', name: 'state_name', align: 'left',width: '80',}, 
    	                     
    	                     
    	                     { display: '领用科室', name: 'dept_name', align: 'left',width:'120'},
    	                     { display: '领料员', name: 'emp_name', align: 'left',width: '80'},
    	                   
    	                    { display: '制单日期', name: 'out_date', align: 'left',width: '90'},
    	                    
    	                     { display: '出库日期', name: 'confirm_date', align: 'left',width: '90'},	
    	                     { display: '科室申领日期', name: 'app_date', align: 'left',width: '90'},	
    	                     { display: '确认人', name: 'user_name_confirmer', align: 'left',width: '80'},
    	                     { display: '仓库', name: 'store_name', align: 'left',width:'120'},
    	                     { display: '业务类型', name: 'bus_type_name', align: 'left',width: '80'},
    	                     { display: '单据负金额', name: 'countnum', align: 'left',width: '80', hide :true},
    	                     {display: '是否打印', name: 'print_state', align: 'left',width: 100,hide:true
    	                    }, 
    	                    {display: '是否打印', name: 'print_state_name', align: 'left',width: 100
    	                    }
    	                     ], 
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOutMain.do?isCheck=true&show_detail=0',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     checkBoxDisplay:isCheckDisplay,
    	                     onsuccess:function(){
    	         			
    	         				//is_addRow();
    	         			},
    						 toolbar: { items: [
										{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
											{ line:true },
										{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
										{ line:true },
										{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
										{ line:true },
										{ text: '审核（<u>N</u>）', id:'audit', click: audit,icon:'audit', hide: isHideCheck },
										{ line:true, hide: isHideCheck },
										{ text: '消审（<u>U</u>）', id:'unaudit', click: unAudit,icon:'unaudit', hide: isHideCheck },
										{ line:true, hide: isHideCheck },
										{ text: '出库确认（<u>F</u>）', id:'confirm', click: confirmData,icon:'account' },
										{ line:true },
										{ text: '申请导入（<u>S</u>）', id:'appImp', click: apply_imp,icon:'up' },
										{ line:true },
		    							{ text: '科室需求计划导入（<u>Q</u>）', id:'deptRImp', click: deptR_imp,icon:'up' },
										{ line:true } ,
										{ text: '批量打印', id:'print', click: print, icon:'print' },
										{ line:true } ,
										{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
										{ line:true },
										{ text: '复制（<u>C</u>）', id:'copy', click: copyMatOutMain,icon:'copy' },
										{ line:true },
										{ text: '冲账（<u>O</u>）', id:'offset', click: mergeBalanceMatOutMain,icon:'offset' },
    							
    						]},
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    	    					if(rowdata.out_id == null){
    	    						$.ligerDialog.warn('请选择数据 ');
    	    						return ; 
    	    					}
    							openUpdate(
    									rowdata.group_id   + "," + 
    									rowdata.hos_id   + "," + 
    									rowdata.copy_code   + "," + 
    									rowdata.out_id   + "," + 
    									rowdata.store_id 
    								);
    	    				} 
    	                   });
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#out_date_start").val() +" 至  "+ $("#out_date_end").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		if(liger.get("bus_type_code").getValue() == ""){
 			grid.options.lodop.title="材料出库";
 		}else{
 			grid.options.lodop.title=liger.get("bus_type_code").getText()+"材料出库";
 		}
    }
    
    //保存列
    function btn_saveColumn(){
		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../../sys/addBatchSysTableStyle.do?isCheck=false';
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
    
    function isCheckDisplay(rowdata){
       	if(rowdata.out_id == null) return false;
         return true;
    }
    
  //字段类型渲染器
  
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = matOutMain_state.Rows.length; i < l; i++){
            var o = matOutMain_state.Rows[i];
            
            if (o.id == value) return o.text;
        }
        if(value == null){
        	return "";
        }
        return "未审核";
    }
    
	function add_open(){
    	
		parent.$.ligerDialog.open({
			title:'出库单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/outlibrary/matOutMainAddPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
 
	function copyMatOutMain(){
	 	
	 	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要复制的数据');
        }else{
            var ParamVo =[];
            var nos = "";
            $(data).each(function (){		
            	ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.out_id 
			)});
            $.ligerDialog.confirm('确定复制?', function(yes) {
            	if (yes) {
		            ajaxJsonObjectByUrl("copyMatOutMain.do",{ParamVo : ParamVo.toString()},function (responseData){
		        		if(responseData.state=="true"){
		        			query();
		        		}
		        	});
            	}
            });
        }
	 
	}
	function audit(){
	 	
	 	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要审核的数据');
        }else{
            var ParamVo =[];
            var nos = "";
            $(data).each(function (){			
            	if(this.state > 1){nos = nos + this.out_no + ",";}
													ParamVo.push(
													this.group_id   +"@"+ 
													this.hos_id   +"@"+ 
													this.copy_code   +"@"+ 
													this.out_id 
			)});
            if(nos != ""){
				$.ligerDialog.error("审核失败！"+nos+"单据不是未审核状态");
				return;
			}
            $.ligerDialog.confirm('确定审核?', function(yes) {
            	if (yes) {
		            ajaxJsonObjectByUrl("auditMatOutMain.do",{ParamVo : ParamVo.toString()},function (responseData){
		        		if(responseData.state=="true"){
		        			query();
		        		}
		        	});
            	}
            });
        }
	 
	}
	
	function unAudit(){
	 	
	 	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要消审的数据');
        }else{
            var ParamVo =[];
            var nos = "";
            $(data).each(function (){				
            	if(this.state > 2){nos = nos + this.out_no + ",";}
													ParamVo.push(
													this.group_id   +"@"+ 
													this.hos_id   +"@"+ 
													this.copy_code   +"@"+ 
													this.out_id 
			)});
            if(nos != ""){
				$.ligerDialog.error("消审失败！"+nos+"单据是入库确认状态");
				return;
			}
            $.ligerDialog.confirm('确定消审?', function(yes) {
            	if (yes) {
		            ajaxJsonObjectByUrl("unAuditMatOutMain.do",{ParamVo : ParamVo.toString()},function (responseData){
		        		if(responseData.state=="true"){
		        			query();
		        		}
		        	});
            	}
			});
        }
	 
	}
	
	function balanceMatOutMain(){
	 	
	 	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要冲账的数据');
        }else{
            var ParamVo =[];
            var nos = "";
            var nos_btc="";
            //3 科室领用,5 有偿调出,7 无偿调出,9 盘亏出库,11 其他出库,13 报废出库
            $(data).each(function (){	
            	if(this.state != 3){nos = nos + this.out_no + ",";}
            	if(this.bus_type_code != '3' && this.bus_type_code != '5' && this.bus_type_code != '7' && this.bus_type_code != '9' && this.bus_type_code != '11' && this.bus_type_code != '13'){nos_btc = nos_btc + this.out_no + ",";}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.out_id 
			) });
            if(nos != ""){
				$.ligerDialog.error("冲账失败！"+nos+"单据不是出库确认状态");
				return;
			}
            if(nos_btc != ""){
				$.ligerDialog.error("冲账失败！"+nos_btc+"单据业务类型不允许冲账");
				return;
			}
            $.ligerDialog.confirm('确定冲账？', function(yes) {
            	if (yes) {
		            ajaxJsonObjectByUrl("balanceMatOutMain.do",{ParamVo : ParamVo.toString()},function (responseData){
		        		if(responseData.state=="true"){
		        			query();
		        		}
		        	});
            	}
            });
        }
	 
	}
	//合并弹出页面冲账
	function mergeBalanceMatOutMain(){
	 	var group_id="";
	 	var hos_id="";
	 	var copy_code="";
	 	var store_id="";
	 	 var ParamVo =[];
	 	 var v_store =[];
	 	 var v_dept =[];
	 	 var flag=true;
	 	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择要冲账的数据');
        }else{
           
        	var nos = "";
            var nos_btc="";
            var nos_store="";
            var nos_dept="";
            var nos_nums =""; 
            //3 科室领用,5 有偿调出,7 无偿调出,9 盘亏出库,11 其他出库,13 报废出库
            $(data).each(function (){	
            	if(this.state != 3){
            		nos = nos + this.out_no + ",";
            	}
            	if(v_store.length==0){
            		v_store.push(this.store_id);
            	}else{
            		if(v_store.indexOf(this.store_id)<0 ){nos_store = nos_store + this.out_no + ",";}
            	}
            	if(v_dept.length==0){
            		v_dept.push(this.dept_id);
            	}else{
            		if(v_dept.indexOf(this.dept_id)<0 ){nos_dept = nos_dept + this.out_no + ",";}
            	} 
            	
            	if(this.countnum  > 0){
            		nos_nums = nos_nums + this.out_no + ",";
            	}
            	
            	if(this.bus_type_code != '3' && this.bus_type_code != '5' && this.bus_type_code != '7' 
            			&& this.bus_type_code != '9' && this.bus_type_code != '11' && this.bus_type_code != '13'
            			&& this.bus_type_code != '17' && this.bus_type_code != '19' && this.bus_type_code != '23'
            	){
            		nos_btc = nos_btc + this.out_no + ",";
            	}
            		group_id=this.group_id;
            		hos_id=this.hos_id;
            		copy_code=this.copy_code;
            		store_id=this.store_id;
            		ParamVo.push(
						this.out_id +"@"+ 
						this.out_no
					) 
			});
            
            if(nos != ""){
				$.ligerDialog.error("冲账失败！"+nos+"单据不是出库确认状态");
				flag=false;
				return;
			}
            if(nos_btc != ""){
				$.ligerDialog.error("冲账失败！"+nos_btc+"单据业务类型不允许冲账");
				flag=false;
				return;
			}
            if(nos_store != ""){
				$.ligerDialog.error("冲账失败！"+nos_store+"仓库不同不允许汇总冲账");
				flag=false;
				return;
			}
            if(nos_dept != ""){
				$.ligerDialog.error("冲账失败！"+nos_dept+"领用科室不同不允许汇总冲账");
				flag=false;
				return;
			}
            
            if(nos_nums !=""){
            	$.ligerDialog.error("冲账失败！"+nos_nums+" 单据中有金额有小于0的，不允许冲账！");
				flag=false;
				return;
            }
            
            if(flag){
            	 var paras = 
         			"group_id=" + group_id + "&" 
         			+ "hos_id=" + hos_id + "&" 
         			+ "copy_code=" + copy_code + "&" 
         			+ "store_id=" + store_id + "&" 
         			+ "out_ids=" + ParamVo.toString() ;
                
                 parent.$.ligerDialog.open({
         			title:'冲账单',
         			height: $(window).height(),
         			width: $(window).width(),
         			url: 'hrp/mat/storage/out/outlibrary/mergeBalanceMatOutMain.do?isCheck=false&' + paras.toString(),
         			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
         			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
         		});	
                 
            }
        }
	 
	}
	function confirmOut(){
		
		var is_store='${p04045 }';
		
	 	var data = gridManager.getCheckedRows();
	 	var isDept = false;
        if (data.length == 0){
        	$.ligerDialog.error('请选择要确认出库的数据');
        }else{
        	var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var today = todayYear + '-' + todayMonth + '-' + todayDate;
			var confirmDate;
			
			if('${p04047 }'==0){
				confirmData(today);
			}else{
				$.ligerDialog.open({
					content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
					width: 300,
					height: 150,
					buttons: [
						{ text: '确定', onclick: function (item, dialog) {
							confirmDate = $("#confirmDate").val();
							if (confirmDate) {
								dialog.close();
	 
								confirmData(confirmDate);
							}
						}},
		                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
				})
			}
		}
	}
   function confirmData(){
	   

	    var is_store='${p04045 }';
	 	var data = gridManager.getCheckedRows();
	 	var isDept = false;
		var ParamVo =[];
		var nos = "";
		
		if (data.length == 0){
        	$.ligerDialog.error('请选择要确认出库的数据');
        	return false;
        }
		
		/* 1.开始   说明：后期可用于维护用户输入日期操作 */
		var todayDate = new Date();
		var todayYear = todayDate.getFullYear();
		var todayMonth = todayDate.getMonth() + 1;
		var todayDate = todayDate.getDate();
		todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
		todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
		var confirmDate = todayYear + '-' + todayMonth + '-' + todayDate; 
		/* 1.结束 */
		
		$(data).each(function (){	
			if(isHideCheck){
				//不使用审核功能需要判断是否未审核
				if(this.state != 1){nos = nos + this.out_no + ",";}
			}else{
				//使用审核功能需要判断是否已经审核
				if(this.state != 2){nos = nos + this.out_no + ",";}
			}
			if(!isDept && !this.dept_name){
				isDept = true;
			}
			ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.out_id +"@"+ 
				this.out_date + "@" + //confirmDate+ "@" +
				is_store+ "@" +this.store_id+"@"+this.out_no
			) 
		});
		if(isHideCheck){
			if(nos != ""){
				$.ligerDialog.error("确认出库失败！"+nos+"单据不是未审核状态");
				return;
			}
		}else{
			if(nos != ""){
				$.ligerDialog.error("确认出库失败！"+nos+"单据不是审核状态");
				return;
			}
		}
     
		if(isDept){
			$.ligerDialog.error("领用科室为空不能出库确认");
			return;
		}

		$.ligerDialog.confirm('确认出库?', function(yes) {
			if (yes) {
				
				ajaxJsonObjectByUrl("../../in/verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
					if (responseData.state == "true") {
						
						ajaxJsonObjectByUrl("confirmOutMatOutMain.do", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				},false);
				
			}
		});
		
   }
    function remove(){
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        var nos = "";
                        $(data).each(function (){	
                        	if(this.state > 1){nos = nos + this.out_no + ",";}
                        	
							ParamVo.push(
								this.group_id   +"@"+ 
								this.hos_id   +"@"+ 
								this.copy_code   +"@"+ 
								this.out_id 
						) });
                        if(nos != ""){
            				$.ligerDialog.error("删除失败！"+nos+"单据不是未审核状态");
            				return;
            			}
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMatOutMain.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'matOutMainImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	

    function openUpdate(obj){
    		
    	var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "out_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString();
		
		parent.$.ligerDialog.open({
			title:'出库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/outlibrary/matOutMainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
    }
    
	function apply_imp(){
    	
		parent.$.ligerDialog.open({
			title:'科室申请导入',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/outlibrary/matOutMainApplyImpPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function openOutAddByApplyImp(matOutObj){ 
		console.log(matOutObj);
		parent.$.ligerDialog.open({
			title: '汇总生成出库单',
			height: $(window).height()-50,
			width: $(window).width()-100,
			url: 'hrp/mat/storage/out/outlibrary/matOutMainAddByOther.do?isCheck=false',
			data: matOutObj, 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//科室需求计划导入
	function deptR_imp(){
    	
		parent.$.ligerDialog.open({
			title:'科室需求计划导入',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/outlibrary/matOutMainDeptRequireImpPage.do?isCheck=false', 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	function openAddByApplyImp(matOutObj){
		console.log(matOutObj);
		parent.$.ligerDialog.open({
			title: '需求计划汇总生成出库单',
			height: $(window).height()-50,
			width: $(window).width()-100,
			url: 'hrp/mat/storage/out/outlibrary/matOutMainAddByDeptReq.do?isCheck=false',
			data: matOutObj, 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
		
    function loadDict(){ 
            //字典下拉框
        autocomplete("#mat_type_code", "../../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:'1'}, false, false, '254');
    	autoCompleteByData("#state",matOutMain_state.Rows,"id","text",true,true);
    	autocomplete("#maker_code", "../../../querySysUser.do?isCheck=false", "id", "text", true, true,"", false, false, '180');    
    	autocomplete("#store_id", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, isUseAffiStore ? {is_write:'1'} : {is_com : 0,is_write:'1'}, true, false, '180');
    	var bus_type_code_paras={codes : "3,5,7,9,11,17,19,20,23,24,25,49",read_or_write:1};
    	autocompleteAsync("#bus_type_code", "../../../queryMatBusType.do?isCheck=false", "id", "text", true, true,bus_type_code_paras,true,false,'180');
    	
    	autocomplete("#affirm", "../../../querySysUser.do?isCheck=false",  "id", "text", true, true, "", false);
    	autocomplete("#dept_id", "../../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write:'1'}, false, false, '180');
    	autocomplete("#emp_id", "../../../queryMatEmpDictData.do?isCheck=false", "id", "text", true, true, "", false, false, '180');
    	autocomplete("#proj_code", "../../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false, '254', false, '254');
		
    	
    	$("#out_date_start").ligerTextBox({width:110});
    	$("#out_date_end").ligerTextBox({width:110});
    	autodate("#out_date_start", "yyyy-mm-dd", "month_first");
        autodate("#out_date_end", "yyyy-mm-dd", "month_last");
    	$("#confirm_date_start").ligerTextBox({width:110});
    	$("#confirm_date_end").ligerTextBox({width:110});
    	$("#state").ligerComboBox({width:180});
    	$("#out_no").ligerTextBox({width:254});
    	$("#brief").ligerTextBox({width: 180});    
    	/* autocomplete("#inv_code", "../../../queryMatInv.do?isCheck=false", "id", "text", true, true,"", false, false, '180'); */
        $("#inv_code").ligerTextBox({width:180});
        $("#batch_no").ligerTextBox({width:180});
        $("#inv_model").ligerTextBox({width:254});
        $("#bar_code").ligerTextBox({width:180});
        $("#print_state").ligerTextBox({width:180});
	}  
    
    //键盘事件
	 function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		//hotkeys('I', imp);
	 }
	//打印模板设置
	    function printSet(){
		  
	    	var useId=0;//统一打印
			if('${p04018 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}else if('${p04018 }'==2){
				//按仓库打印
				if(liger.get("store_id").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("store_id").getValue().split(",")[0];
			}
	    	
			officeFormTemplate({template_code:"04014",use_id : useId});
	    	/* parent.$.ligerDialog.open({url : 'hrp/mat/storage/out/outlibrary/storageOutPrintSetPage.do?template_code=04014&use_id='+useId,
	    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
	    		}); */
	    }


	    //打印
	    function print(){
	    	
	    	/*  var start_date = $("#begin_in_date").val() + " 00:00"; 
	    	 var end_date = $("#end_in_date").val() + " 00:00"; 
	    	 start_date = new Date(start_date.replace(/-/g, "/")); 
	    	 end_date = new Date(end_date.replace(/-/g, "/")); 
	    	 if(start_date.getMonth() != end_date.getMonth()) { 
	    		  $.ligerDialog.error("不支持跨月打印！"); 
	    	      return false;  
	    	 }  */
	    	 
	    	 var useId=0;//统一打印
	 		if('${p04018 }'==1){
	 			//按用户打印
	 			useId='${sessionScope.user_id }';
	 		}else if('${p04018 }'==2){
	 			//按仓库打印
	 			if(liger.get("store_id").getValue()==""){
	 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
	 				return;
	 			}
	 			useId=liger.get("store_id").getValue().split(",")[0];
	 		}

	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				
				var out_id ="" ;
				var in_nos = "";
				$(data).each(function (){		
					if(this.state != 2){
						in_nos = in_nos + this.in_no + "<br>";
					}
					
					out_id  += this.out_id+","
						
				});
			
				 var para={
		    			paraId :out_id.substring(0,out_id.length-1) ,
		    			class_name:"com.chd.hrp.mat.serviceImpl.storage.out.MatOutMainServiceImpl",
		    			method_name:"queryMatOutByPrintTemlate",
		    			template_code:'04014',
		    			isPrintCount:false,//更新打印次数
		    			isPreview:true,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:1
		    			//isSetPrint:flag
		    	}; 
			 	
			    officeFormPrint(para);
				
		    	//printTemplate("hrp/mat/storage/out/outlibrary/queryMatOutByPrintTemlate.do?isCheck=false",para);
		    	
			}
	    	
	    }

		function show(){

			
		       if($("#out_div").css("height")=="0px"){
			
			      $("#out_div").css("height","auto")

			 }else{
				   
				   $("#out_div").css("height","0px")
				 }
		
		grid._onResize();
		//$(".l-bar-btnload").click();
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
         <tr>
            <td align="right" class="l-table-edit-td"  width="10%">制单日期：</td>
            <td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td>
							<input name="out_date_start" type="text" id="out_date_start" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td>
							<input name="out_date_end" type="text" id="out_date_end" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
			<td align="right" class="l-table-edit-td"  width="10%">业务类型：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
            
            <td align="right" class="l-table-edit-td"  width="10%">状态：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="state" type="text" id="state" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
        </tr> 
        <tr>
        
        	<td align="right" class="l-table-edit-td"  width="10%">出库日期：</td>
            
            <td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td>
							<input name="confirm_date_start" type="text" id="confirm_date_start" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td>
							<input name="confirm_date_end" type="text" id="confirm_date_end" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td"  width="10%">仓库：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true}" /></td>
            
            <td align="right" class="l-table-edit-td"  width="10%">领料科室：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<table>
            		<tr>
            			<td>
            				<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" />
            			</td>
            			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">
            				<a href="javascript:show()">高级查询</a>
            			</td>
            		</tr>
            	</table>
            </td>
        </tr>
    </table>
     <div id="out_div"  style="height:0px; overflow:hidden" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
       <tr>
         	<td align="right" class="l-table-edit-td"> 项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
	        <td align="left" class="l-table-edit-td"><input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  width="10%">领料员：</td>
            <td align="left" class="l-table-edit-td" width="20%">
              <input name="emp_id" type="text" id="emp_id" ltype="text" validate="{maxlength:20}" />
            </td>
			<td align="right" class="l-table-edit-td" width="10%">制单人：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="maker_code" type="text" id="maker_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">单据号：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="out_no" type="text" id="out_no" ltype="text" validate="{required:false,maxlength:100}" /></td>
			<td align="right" class="l-table-edit-td" width="10%">摘要：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" /></td>
            <td align="right" class="l-table-edit-td  demo" width="10%">是否已经打印：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<select name="print_state" id="print_state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="0">否</option>
                		<option value="1">是</option>
            	</select>
            </td>
		</tr>
		<tr>
		   	<td align="right" class="l-table-edit-td" width="10%">物资类别：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
			<td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
			<td align="right" class="l-table-edit-td" width="10%"></td>
		    <td width="20%"><input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细</td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td" width="10%">规格型号：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" /></td>
           	<td align="right" class="l-table-edit-td  demo" width="10%">批号：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%"><input name="batch_no" type="text" id="batch_no" ltype="text" validate="{required:false,maxlength:100}" /></td>
            <td align="right" class="l-table-edit-td" width="10%">条形码：</td>
            <td align="left" class="l-table-edit-td" width="20%"><input name="bar_code" type="text" id="bar_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">确认人：</td>
            <td align="left" class="l-table-edit-td"><input name="affirm" type="text" id="affirm" ltype="text" validate="{maxlength:20}" /></td>
            <td></td>
		</tr>
    </table>
    </div>
	<div id="maingrid"></div>
</body>
</html>
