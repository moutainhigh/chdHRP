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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var show_detail ;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();

		$("#in_date1").ligerTextBox({
			width : 90
		});
		$("#in_date2").ligerTextBox({
			width : 90
		});
		 $("#invoice_no").ligerTextBox({
			 width:160
		});

		$("#create_date_begin").ligerTextBox({
			width : 90
		});
		$("#create_date_end").ligerTextBox({
			width : 90
		});
		$("#state").ligerComboBox({
			width : 160
		});
		$("#store_id").ligerTextBox({
			width : 160
		});
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#proj_id").ligerTextBox({
			width : 230
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		var menu1 = { width: 120, items:
	           [
		           //{ text: '导入合同单', click: itemclick,id:'ImportContract',icon:'import' },
		          // { text: '导入招标单', click: itemclick,id:'importBid',icon:'import' }
	           ]
	       };
	 
	       var menu2 = { width: 120, items:
	           [
	             //{ text: '生成退货单', click: itemclick,id:'initBack',icon:'initial' },
	            // { text: '生成发票单', click: itemclick,id:'initBill',icon:'initial' },
	             
	           ]
	       };

		$("#topmenu").ligerToolBar({ items: [
											{
												text : '查询',
												id : 'search',
												click : query,
												icon : 'search'
											},
											
										     { text: '保存', click: save,id:'save',icon:'add' },
										     
										     {
													text : '关闭',
													id : 'close',
													click : this_close,
													icon : 'candle'
											 }
		                                     
		                                 ]
		});
		query();
	});
	
	function this_close() {
		frameElement.dialog.close();
	}
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'store_no',
			value : liger.get("store_id").getValue().split("@")[1]
		});
		
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue().split("@")[1]
		});
		
		grid.options.parms.push({
			name : 'ven_id',
			value : liger.get("ven_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'ven_no',
			value : liger.get("ven_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'proj_id',
			value : liger.get("proj_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'proj_no',
			value : liger.get("proj_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name:'invoice_no',
			value:liger.get("invoice_no").getValue()
		}); 
		
		grid.options.parms.push({
			name : 'purc_emp',
			value : liger.get("purc_emp").getValue()
		});
		grid.options.parms.push({
			name : 'create_date_beg',
			value : $("#create_date_begin").val()
		});
		grid.options.parms.push({
			name : 'create_date_end',
			value : $("#create_date_end").val()
		});
		grid.options.parms.push({
			name : 'in_date_beg',
			value : $("#in_date1").val()
		});
		grid.options.parms.push({
			name : 'in_date_end',
			value : $("#in_date2").val()
		});
		grid.options.parms.push({
			name : 'accepter',
			value : $("#accepter").val()
		});
		grid.options.parms.push({
			name : 'bill_type',
			value : '${bill_type}'
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function save(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var bill_type = $('input[name="bill_type"]:checked ').val();
			var ass_in_nos = [];
			var dept_ids = [];
			var dept_nos = [];
			var store_ids = [];
			var store_nos = [];
			$.each(data,function(){
				ass_in_nos.push("'"+this.ass_in_no+"'");
				dept_ids.push("'"+this.dept_id+"'");
				dept_nos.push("'"+this.dept_no+"'");
				store_ids.push("'"+this.store_id+"'");
				store_nos.push("'"+this.store_no+"'");
			});
			ajaxJsonObjectByUrl("importAssInMainByOut.do?isCheck=false",
					{
					 'ass_in_nos':ass_in_nos.toString(),
					 'dept_ids':dept_ids.toString(),
					 'dept_nos':dept_nos.toString(),
					 'store_ids':store_ids.toString(),
					 'store_nos':store_nos.toString(),
					 'bill_type':'${bill_type}',
					 'note':'${note}',
					 'purc_emp':'${purc_emp}',
					 //'dept_id':'${dept_id}',
					 //'dept_no':'${dept_no}',
					 //'store_id':'${store_id}',
					 //'store_no':'${store_no}'
					 },
					 function(data){
						 if(data.state == "true"){
							 	parent.parentFrameUse().query();
								//parent.parentFrameUse().openUpdate(data.update_para);
								parent.this_close();
								this_close();
						 }
			});
			
		}
	}
	

	function loadHead() {
			grid = $("#maingrid").ligerGrid(
					{
						columns : [  {
							display : '入库单号',
							name : 'ass_in_no',
							align : 'left',
							width : 120,
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.note == "合计"){
									return '';
								}
								return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.ass_in_no  +"')>"+rowdata.ass_in_no+"</a>";
							}, frozen: true
						},{ display: '发票号',
							name: 'invoice_no', 
							align: 'left',
							width : 150,
							frozen: true
				 		},
	 					{
							display : '摘要',
							name : 'note',
							align : 'left',
							width : 150,
							frozen: true
						},{
							display : '仓库',
							name : 'store_name',
							align : 'left',
							width : 260, frozen: true
						},{
							display : '供应商',
							name : 'ven_name',
							align : 'left',
							width : 260
						}, {
							display : '领用科室',
							name : 'dept_name',
							align : 'left',
							width : 140
						}, {
							display : '入库金额',
							name : 'in_money',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.in_money,'${ass_05005}',1);
				            }
						}, {
							display : '采购员',
							name : 'purc_emp_name',
							align : 'left',
							width : 110
						}, {
							display : '制单人',
							name : 'create_emp_name',
							align : 'left',
							width : 100
						}, {
							display : '制单日期',
							name : 'create_date',
							align : 'left',
							width : 100
						}, {
							display : '确认人',
							name : 'confirm_emp_name',
							align : 'left',
							width : 100
						}, {
							display : '入库确认日期',
							name : 'in_date',
							align : 'left',
							width : 100
						}, {
							display : '状态',
							name : 'state_name',
							align : 'left',
							width : 100
						},{
							display : '用途',
							name : 'ass_purpose_name',
							align : 'left',
							width : 100
						}],
						dataAction : 'server',
						dataType : 'server',
						usePager : true,
						url : 'queryInMainByOutImport.do?isCheck=false',
						width : '100%',
						height : '100%',
						checkbox : true,
						rownumbers : true,
						delayLoad :true,
						checkBoxDisplay : isCheckDisplay,
						selectRowButtonOnly : true,//heightDiff: -10,
						onDblClickRow : function(rowdata, rowindex, value) {
							openUpdate(rowdata.group_id + "|" + rowdata.hos_id
									+ "|" + rowdata.copy_code + "|"
									+ rowdata.ass_in_no);
						}
					});
			
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
	


	function loadDict() {
		var param = {query_key:''};
		autocomplete("#purc_emp",
				"../../../queryMatStockEmp.do?isCheck=false&", "id",
				"text", true, true, null, null);
		
		$("#store_id").change(function(){
			
			var store_id = liger.get("store_id").getValue().split("@")[0];

			if (store_id == null || store_id == "") {
				store_id = "";
			}
			autocomplete("#purc_emp",
					"../../../queryMatStockEmp.do?isCheck=false&store_id="+store_id, "id",
					"text", true, true, null, null);
		});
		
		autocomplete("#store_id", "../../../queryHosStoreDict.do?naturs_code=02&isCheck=false","id", "text",true,true,param,true,null,"300");
    	
		autocomplete("#dept_id", "../../../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, null, null);
		
		autocomplete("#ven_id", "../../../queryHosSupDict.do?isCheck=false","id", "text",true,true,param,true,null,"400");
		
		autocomplete("#proj_id", "../../../queryAssProjDict.do?isCheck=false","id", "text",true,true,param,true,null,"400");
		
		$("#invoice_no").ligerTextBox({width:160});
		
		liger.get("store_id").setValue("${store_id}@${store_no}");
		
		liger.get("store_id").setText("${store_name}");
		
		liger.get("dept_id").setValue("${dept_id}@${dept_no}");
		
		liger.get("dept_id").setText("${dept_name}");
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

	}

	function openUpdate(obj) {
		var vo = obj.split("|");
		if("null"==vo[3] || "undefined"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_in_no=" + vo[3];

		parent.parent.$.ligerDialog.open({
			title: '采购入库查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assinassets/assin/assInMainInassetsUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" id="table1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"  width="5%"><input name="create_date_begin" type="text" id="create_date_begin"
				  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">&nbsp;至：</td>
			<td align="left"><input name="create_date_end" type="text" id="create_date_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"   /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left"  class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" /></td>		 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">确认日期：</td>
			<td align="left" class="l-table-edit-td" ><input name="in_date1" 
				type="text" id="in_date1" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left">&nbsp;至：</td>
			<td align="left"><input name="in_date2" type="text"
				id="in_date2" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'in_date1\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">采&nbsp;&nbsp;购&nbsp;&nbsp;员：</td>
			<td align="left" class="l-table-edit-td"><input name="purc_emp"
				type="text" id="purc_emp" 
				 /></td>
		</tr>
	  	<tr>
	  		 <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
			 <td align="left"  class="l-table-edit-td" colspan = "3"><input name="proj_id" type="text" id="proj_id" /></td>	
			 <td align="right" class="l-table-edit-td" style="padding-left: 20px;">科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"   /></td>
		</tr> 
	</table>
	<div id="topmenu" style="background:#FFFFFF; color:#FFFFFF; border:1px solid #A4D3EE" ></div>
	<div id="maingrid"></div>
</body>
</html>
