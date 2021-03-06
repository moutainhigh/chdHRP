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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
		grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'begin_out_date',value : $("#begin_out_date").val()});
		grid.options.parms.push({name : 'end_out_date',value : $("#end_out_date").val()});
		grid.options.parms.push({name : 'dept_id',value :liger.get("dept_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'out_no',value : $("#out_no").val()});
		grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '出库单号', name: 'out_no', align: 'left', minWidth: '140',
				}, { 
		 			display: '出库日期', name: 'confirm_date', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '领料科室', name: 'dept_name', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '状态', name: 'field_desc', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '药品编码', name: 'inv_code', align: 'left', minWidth: '80',render:
                   	 function(rowdata,rowindex,value){
		 				if(rowdata.inv_code == undefined){
		 					return "";
		 				}
	                   	 return '<a href=javascript:openUpdate("' 
								+ rowdata.inv_id
								+','
								+ rowdata.out_id
								+','
								+rowdata.store_id
								+ '")>'+rowdata.inv_code+'</a>';
	                	 }
		 		},  { 
		 			display: '药品名称', name: 'inv_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
		 			render : function(rowdata, rowindex, value) {
		 				if(value == null){
		 					return "";
		 				}
						return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p08005 }', 1);
					}
		 		},  { 
		 			display: '数量', name: 'amount', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
					}
		 		},{ 
		 			display: '药品分类', name: 'med_type_name', align: 'left', minWidth: '90'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAffiOutDetail.do?isCheck=false',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				       			{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
								{ line:true },
								{ text: '打印', id:'print', click: print, icon:'print' },
				   				{ line:true }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
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
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'出库明细查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_confirm_date").val() +" 至  "+ $("#end_confirm_date").val(),"colspan":colspan_num,"br":true}
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
   		ajaxJsonObjectByUrl("queryMedAffiOutDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true,{is_com : 1},true);
		autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false,'',220);
		autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true);
		autocompleteAsync("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, true);
	//	autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true,'',false,'',220);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#dept_id", "../../queryMedAppDept.do?isCheck=false", "id", "text", true, true);
        $("#begin_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        $("#begin_out_date").ligerTextBox({width:100});
        autodate("#begin_out_date", "yyyy-mm-dd", "month_first");
        $("#end_out_date").ligerTextBox({width:100});
        autodate("#end_out_date", "yyyy-mm-dd", "month_last");
        $("#out_no").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:220});
        $("#med_type_code").ligerTextBox({width:220});
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
			url: 'hrp/med/affi/query/medAffiOutSupMessagePage.do?isCheck=false&' + param.toString(),
			modal: true, showToggle: false,isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ 
	    		{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
	    	] 
		});   
    
    }
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	
		<table>
		<tr>
			<td align="right" class="l-table-edit-td">出库日期： </td>
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
	        <td align="right" class="l-table-edit-td"  width="10%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%"> &nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false}" />
  			</td> 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">制单日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_out_date" id="begin_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_out_date" id="end_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	         <td align="right" class="l-table-edit-td" width="10%">&nbsp;科 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;室： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_id" type="text" id="dept_id" ltype="text" required="true" validate="{required:true,maxlength:100}" />
            </td>
	        <td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
           
       
		</tr>
		<tr>
			 <td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%"> 出库单号：</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="out_no" type="text" id="out_no" ltype="text" validate="{required:false,maxlength:100}" />
  			</td> 
            <td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
		</tr>
		 <tr>
		 	  <td align="right" class="l-table-edit-td"  width="10%"> 药品类别： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
		 </tr>
	</table>
	 
	</div>
	<div id="maingrid"></div>
</body>
</html>
