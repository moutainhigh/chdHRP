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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day;
    var grid;  
    var gridManager = null;
    var userUpdateStr; 
    var show_detail=0;
    var setOrStore=0;//1按仓库查询,0按虚仓查询,默认按虚仓
    
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
		loadHotkeys();
        showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
		grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
		grid.options.parms.push({name : 'inv_msg',value : $("#inv_msg").val()});
		grid.options.parms.push({name : 'set_or_store',value :setOrStore});
		grid.options.parms.push({name : 'set_or_store_id',value : setOrStore===1?liger.get("set_or_store_code").getValue().split(",")[0]:liger.get("set_or_store_code").getValue()}); 
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'dept_id',value :liger.get("dept_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'out_no',value : $("#out_no").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'inv_model',value : $("#inv_model").val()});
		grid.options.parms.push({name : 'agent_name',value : $("#agent_name").val()});
		grid.options.parms.push({name : 'is_sup',value : $("#is_sup").is(":checked") ? 1 : ''});
		grid.options.parms.push({name : 'is_agent',value : $("#is_agent").is(":checked") ? 1 : ''});
		grid.options.parms.push({name : 'is_dept',value : $("#is_dept").is(":checked") ? 1 : ''});
		
		//业务类型
		if(liger.get("bus_type_code").getValue().length>0){
			
			var bus_type_codes=liger.get("bus_type_code").getValue().split(";");
			var bus_type_code="";
			for(var code of bus_type_codes){
				if(code==='15'){
					grid.options.parms.push({name : 'tran_code',value : '15'});
				}else{
					bus_type_code+=bus_type_code.length>0?","+code:code;
				}
			}
			if(bus_type_code.length>0){
				grid.options.parms.push({name : 'bus_type_code',value : bus_type_code});
			}
		}
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if (show_detail != "1") {
    		grid = $("#maingrid").ligerGrid({
    			columns: [{ 
    					display: '仓库名称', name: 'store_name', align: 'left', minWidth: '90'
    				},{ 
    		 			display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '90'
    		 		},{ 
    		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'},
    		 		{ 
    		 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '出库数量', name: 'amount', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
    		 			render : function(rowdata, rowindex, value) {
    		 				if(rowdata.price == null ){
    		 					return "";
    		 				}
    						return formatNumber(rowdata.price, '${p04006 }', 1);
    					},formatter:"###,##0.00"
    		 		},{ 
    		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
    					},formatter:"###,##0.00"
    		 		},{ 
    		 			display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '130'
    		 		},{ 
    		 			display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '代理商名称', name: 'agent_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '领料科室', name: 'dept_name', align: 'left', minWidth: '80'
    		 		}],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageOutInvCollection.do?isCheck=false',
    			width: '100%', height: '100%',rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '打印', id:'print', click:print, icon:'print'},
    				{ line:true }
    			]}
    		});
    	}else{
    		grid = $("#maingrid").ligerGrid({
    			columns: [{ 
    		 			display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '80'
    		 		},{ 
    					display: '仓库名称', name: 'store_name', align: 'left', minWidth: '90'
    				},{ 
    		 			display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '90'
    		 		},{
    					display: '出库单号', name: 'out_no', align: 'left', minWidth: '140',
    					render : function(rowdata, rowindex, value) {
                    		if(value == '合计'){
                    			return value ; 
                    		}
       						return '<a href=javascript:out_open("' 
       							+ rowdata.group_id
       							+ ',' + rowdata.hos_id 
       							+ ',' + rowdata.copy_code
       							+ ',' + rowdata.out_id
       							+ ',' + rowdata.store_id
       							+ '")>'+rowdata.out_no+'</a>';
       					}	
    				},{ 
    		 			display: '出库日期', name: 'confirm_date', align: 'left', minWidth: '150', formatter: "yyyy-MM-dd"
    		 		},{ 
    		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 	 		display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '批号', name: 'batch_no', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '有效日期', name: 'inva_date', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '出库数量', name: 'amount', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
    		 			render : function(rowdata, rowindex, value) {
    		 				if(rowdata.price == null ){
    		 					return "";
    		 				}
    						return formatNumber(rowdata.price, '${p04006 }', 1);
    					},formatter:"###,##0.00"
    		 		},{ 
    		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
    					},formatter:"###,##0.00"
    		 		},{ 
    		 			display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '130'
    		 		},{ 
    		 			display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '代理商名称', name: 'agent_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '领料科室', name: 'dept_name', align: 'left', minWidth: '80'
    		 		}],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageQueryOutDetail.do?isCheck=false',
    			width: '100%', height: '100%',rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '打印', id:'print', click:print, icon:'print'},
    				{ line:true }
    			]}
    		});
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function out_open(obj){
		
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
    
  
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
 
	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()},
	  	          {"cell":3,"value":"仓库："},
	  	          {"cell":4,"value":""+liger.get("set_or_store_code").getText()==''?' ':liger.get("set_or_store_code").getText().split(" ")[1]+""}
        	]}; 

    	//表尾
    			var foots = {
    				rows: [
    					{"cell":0,"value":"制单日期:"} ,
    					{"cell":1,"value":date} ,
    				]
    			}; 
		var title="医用耗材出库情况查询表（汇总表）";
		if (show_detail != "1") {
			title="医用耗材出库情况查询表（汇总表）";
		}else{
			title="医用耗材出库情况查询表（明细表）";
		}
    	var printPara={
          		title: title,//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
       			method_name: "queryMatStorageQueryOutDetailPrint",
       			bean_name: "matInDetailService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
       			show_detail:show_detail
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

   		
    }
   
    function loadDict(){
		//字典下拉框
		if(setOrStore===1){
			autocomplete("#set_or_store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false);
		}else{
			autocomplete("#set_or_store_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false,'');
		}
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false,'',280);
		var bus_type_code_paras={codes:"3, 21, 9,11,13,23,49,50,19,15",read_or_worte:1};
		autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true,bus_type_code_paras,false,false,'180');
    	autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"",false,'',280);
		autocomplete("#dept_id", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{is_last : 1,read_or_write : 1});
		
		$("#mat_type_code").ligerTextBox({width:280});
		$("#inv_code").ligerTextBox({width:280});
		$("#agent_name").ligerTextBox({width:280});
		
        $("#begin_confirm_date").ligerTextBox({width:100});
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        
        $("#out_no").ligerTextBox({width:280});
        $("#inv_msg").ligerTextBox({width:280});
        $("#inv_model").ligerTextBox({width:220});
	}  
	
    //跳转到出库单供应商信息页
    function openUpdate(obj){
    	var vo = obj.split(",");
    	var param = "inv_id="+vo[0]+"&"
    				+"out_id="+vo[1]+"&"
    				+"store_id="+vo[2];
    	parent.$.ligerDialog.open({
			title: '出库单供应商信息',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/query/matOutSupMessagePage.do?isCheck=false&' + param.toString(),
			modal: true, showToggle: false,isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ 
	    		{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
	    	] 
		});   
    
    }
    
    function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if (show_detail == 0) {
			$("#cert_code").val("");
			$("#cert_code").prop("disabled",true);
		}else{
			$("#cert_code").prop("disabled",false);
		}
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
			grid.bind('contextmenu', grid.options.onContextmenu);
		}
		loadHead();
	}
    function is_sup() {
    	var showSup = $("#is_sup").is(":checked") ? 1 : 0;
    	
		if(showSup == 1){	
			
			 grid.toggleCol('sup_name', true)
		}else {
			 grid.toggleCol('sup_name', false)
		}
    	query();
    }
    
    function is_dept() {
        var showDept = $("#is_dept").is(":checked") ? 1 : 0;
    	
		if(showDept == 1){	
			
			 grid.toggleCol('dept_name', true)
		}else {
			 grid.toggleCol('dept_name', false)
		}
    	query();
    }
    function is_agent() {
        var showAgent = $("#is_agent").is(":checked") ? 1 : 0;
    	
		if(showAgent == 1){	
			
			 grid.toggleCol('agent_name', true)
		}else {
			 grid.toggleCol('agent_name', false)
		}
    	query();
    }
    //按仓库还是按虚仓查询
    function changeSetOrStore(){
    	if($("#set_or_store").prop("checked")){
    		setOrStore=1;
    		$("#set_or_store_name").html("仓库:");
    		liger.get("set_or_store_code").setValue("");
    		liger.get("set_or_store_code").setText("");
    		autocomplete("#set_or_store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false);
    		query();
    	}else{
    		setOrStore=0;
    		$("#set_or_store_name").html("虚仓:");
    		liger.get("set_or_store_code").setValue("");
    		liger.get("set_or_store_code").setText("");
    		autocomplete("#set_or_store_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false,'');
    		query();
    	}
    }
    
    
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">出库日期：</td>
			
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td"  width="10%"><span id="set_or_store_name">虚仓：</span></td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_or_store_code" type="text" id="set_or_store_code" ltype="text" validate="{required:false}" />
            	<input name="set_or_store" type="checkbox" id="set_or_store" ltype="text" validate="{required:false}" onclick="changeSetOrStore()"/>按仓库查询
            </td>
			
			<td align="right" class="l-table-edit-td"  width="10%">
				物资类别：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">&nbsp;科 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;室：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_id" type="text" id="dept_id" ltype="text" required="true" validate="{required:true,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_msg" type="text" id="inv_msg" ltype="text"  />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">出库单号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="out_no" type="text" id="out_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">供应商：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td"  width="10%">规格型号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text"  required="true"  validate="{required:true}" />
            </td> 
       		<td align="right" class="l-table-edit-td"  width="10%">业务类型：</td>
            <td align="left" class="l-table-edit-td" width="20%">
	            <input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">代理商：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="agent_name" type="text" id="agent_name" ltype="text" validate="{required:false}" /></td>
	        <td></td>
	        <td><input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>显示明细
	        
	        <input name="is_sup" type="checkbox" id="is_sup"  ltype="text" checked="checked" onclick="is_sup();"/>按供应商&nbsp;
	        
	        <input name="is_agent" type="checkbox" id="is_agent"  ltype="text" checked="checked" onclick="is_agent();"/>按代理商&nbsp; 
	        
	        <input name="is_dept" type="checkbox" id="is_dept"  ltype="text" checked="checked" onclick="is_dept();"/>按领料科室&nbsp;
	        </td>
	
		</tr>
	
	</table>
		
	</div>
	<div id="maingrid"></div>
</body>
</html>
