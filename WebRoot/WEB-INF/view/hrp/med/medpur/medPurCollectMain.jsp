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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
	var renderFunc = {
			price:function(value){//单价
				return formatNumber(value, '${p08006 }', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			},
			amount:function(value){//数量
				return formatNumber(value, '${p08005 }', 1);
			}
	}; 
	
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
		$("#set_code").bind("change",function(){
	    	if(liger.get("set_code").getValue()){
	    		liger.get("store_code").setValue("");
				liger.get("store_code").setText("");
	   	 		$("#store_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('02', true);
	    	}else{
	    		$("#store_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('02', false);
	    	}
	    	
		});
		$("#store_code").bind("change",function(){
	    	if(liger.get("store_code").getValue()){
	    		liger.get("set_code").setValue("");
				liger.get("set_code").setText("");
	   	 		$("#set_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('03', true);
	    	}else{
	    		$("#set_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('03', false);
	    	}
	    	
		});

		
		$("#is_showStore").bind("change",function(){
			f_setColumns();
			query();
		});
    });
    //查询
    function  query(){
   		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
        var begin_confirm_date = $("#begin_confirm_date").val();
        var end_confirm_date =  $("#end_confirm_date").val();
        
        if(begin_confirm_date == ''){
        	$.ligerDialog.warn('开始期间不能为空 ');
        }
        
        if(end_confirm_date == ''){
        	$.ligerDialog.warn('结束期间不能为空 ');
        }
        
        grid.options.parms.push({
        	name : 'begin_in_date',
        	value : $("#begin_in_date").val()
        });
        
		grid.options.parms.push({
			name : 'end_in_date',
			value : $("#end_in_date").val()
		});
        
		grid.options.parms.push({name : 'begin_confirm_date',value : begin_confirm_date});
		grid.options.parms.push({name : 'end_confirm_date',value : end_confirm_date});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue()});
		grid.options.parms.push({name : 'is_showStore',value : $("#is_showStore").is(":checked") ? "1":""});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()});
		grid.options.parms.push({name : 'is_back',value : $('#is_back').is(":checked") ? "1":"0"});
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPurCollectMain.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				/* { text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' },
				{ line:true }, */
				{ text: '打印', id:'print', click: print, icon:'print' },
   				{ line:true }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function f_setColumns(){
    	var show_store = $("#is_showStore").is(":checked") ? true:false;
    	
    	if(show_store){
	    	columns = [
				{display: '虚仓名称', name: 'set_name', align: 'left', minWidth: '150',render:
					function(rowdata, rowindex, value){
						if(rowdata.set_name == null){
							return "";
						}
						return rowdata.set_name;
					}
				},
				{display: '仓库名称', name: 'store_name', align: 'left', minWidth: '150'},
				{display: '供应商', name: 'sup_name', align: 'left', minWidth: '150'},  
				{display: '药品名称', name: 'inv_name', align: 'left', minWidth: '150'},
				{display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'},  
				{display: '单价', name: 'price', align: 'right', minWidth: '70',formatter:"###,##0.00",
					render : function(rowdata, rowindex, value) {
						if(rowdata.price == null ){
							return "";
						}
					return formatNumber(rowdata.price, '${p08005 }', 1);
					}
				},  
				{display: '数量', name: 'amount', align: 'right', minWidth: '45'},  
				{display: '金额', name: 'amount_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
					render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
					}
				},
				{display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '90'}
	    	];
    	}else{
    		columns = [
				{display: '虚仓名称', name: 'set_name', align: 'left', minWidth: '150',render:
					function(rowdata, rowindex, value){
						if(rowdata.set_name == null){
							return "";
						}
						return rowdata.set_name;
					}
				},
				{display: '供应商', name: 'sup_name', align: 'left', minWidth: '150'},  
				{display: '药品名称', name: 'inv_name', align: 'left', minWidth: '150'},
				{display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'},  
				{display: '单价', name: 'price', align: 'right', minWidth: '70',
					render : function(rowdata, rowindex, value) {
						if(rowdata.price == null ){
							return "";
						}
					return formatNumber(rowdata.price, '${p08005 }', 1);
					}
				},  
				{display: '数量', name: 'amount', align: 'right', minWidth: '45'},  
				{display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
					render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
					}
				},
				{display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '90'}
    		];
    	}
    	grid.set('columns', columns); 
        //grid.reRender();
        //query();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>查询期间："+$("#begin_confirm_date").val()+"至"+$("#end_confirm_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="药品采购汇总查询";
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
 
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocomplete("#med_type_code", "../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false);
		//autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true);
		//autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, true);
		autocomplete("#sup_code", "../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		//autocomplete("#inv_code", "../queryMedInv.do?isCheck=false", "id", "text", true, true,'',false,'',220);
		autocomplete("#set_code", "../queryMedVirStore.do?isCheck=false", "id", "text", true, true);<%-- 虚仓 --%>
		autocomplete("#is_charge", "../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		//确认日期
        $("#begin_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        
        //制单日期
        /* autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last"); */
        $("#begin_in_date").ligerTextBox({width:100});
        $("#end_in_date").ligerTextBox({width:100});
        $("#inv_model").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:220});
        $("#sup_code").ligerTextBox({width:160});
	}
    
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'药品采购汇总查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"制单日期: " + $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMedPurCollectMain.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;" onload="f_setColumns()">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
		
	<table>
		<tr>
			<td align="right" class="l-table-edit-td">确认日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td" width="10%">虚仓名称：</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
  			</td> 
	        <td align="right" class="l-table-edit-td"  width="10%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">制单日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	         <td align="right" class="l-table-edit-td" width="10%">供&nbsp;&nbsp;应&nbsp;&nbsp;商： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
             <td align="right" class="l-table-edit-td"  width="10%"> 药品类别： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
	       
           
       
		</tr>
		<tr>
			 <td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
             <td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr>
		 <tr>
		 	 
            <td align="right" class="l-table-edit-td" width="10%"> </td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="is_showStore" type="checkbox" id="is_showStore" checked="checked"/>是否显示仓库
					&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="is_back" type="checkbox" id="is_back" checked="checked"/>包含退货

  			</td> 
		 </tr>
	</table>
	
		<!-- <div class="cell w1">
			<div>确认日期：</div>
			<div>
				<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				至
				<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
		
		<div class="cell w1">
			<div>虚仓名称：</div>
			<div>
				<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
			</div>
		</div>
		
	    <div class="cell w1">
			<div>仓库名称：</div>
			<div>
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
			</div>
		</div>
		
		<div class="cell w1">
			<div>制单日期：</div>
			<div>
				<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
				至
				<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</div>
		</div>
		
		<div class="cell w1">
			<div>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</div>
			<div>
				<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
			</div>
		</div>
		
		<div class="cell w1">
			<div>药品类别：</div>
			<div>
				<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</div>
		</div>
	    
		<div class="cell w1">
			<div>药品信息：</div>
			<div>
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</div>
		</div>
		
		<div class="cell w1">
			<div>是否收费：</div>
			<div>
				<input name="is_charge" type="text" id="is_charge" ltype="text" />
			</div>
		</div>
		
		<div class="cell w1">
			<div>
				<input name="is_showStore" type="checkbox" id="is_showStore" checked="checked"/>是否显示仓库
			</div>
			
			<div>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="is_back" type="checkbox" id="is_back" checked="checked"/>包含退货
			</div>
		</div>
		<div class="cell w1">
		<div>规格型号:</div>
		<div>
			<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		</div>
		
		</div > -->
	</div>
	<div id="maingrid"></div>
</body>
</html>
