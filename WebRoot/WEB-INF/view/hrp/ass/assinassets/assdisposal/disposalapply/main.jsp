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
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var budg_year;
	var grid;
	var gridManager = null;
	var show_detail ;
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);//加载数据
		loadHotkeys();
		query();
		showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'dispose_type',
			value : liger.get("dispose_type").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'create_date_beg',
			value : liger.get("create_date_beg").getValue()
		});
		grid.options.parms.push({
			name : 'create_date_end',
			value : liger.get("create_date_end").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'apply_date_beg',
			value : liger.get("apply_date_beg").getValue()
		});
		grid.options.parms.push({
			name : 'apply_date_end',
			value : liger.get("apply_date_end").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
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
			name : 'ass_id',
			value : liger.get("ass_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'ass_no',
			value : liger.get("ass_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'ass_ins',
			value : liger.get("ass_ins").getValue()
		});
		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}
	
	//加载表格
	function loadHead() {
		if(show_detail == "1"){	
				var flag;
				grid = $("#maingrid").ligerGrid(
						{
							columns : [ {
								display : '申报单号',
								name : 'dis_a_no',
								align : 'left',
								width : '10%',
								render : function(rowdata, rowindex, value) {
									if(rowdata.note == "合计"){
										return '';
									}
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.dis_a_no + "')>"
											+ rowdata.dis_a_no + "</a>";
								}
							},{
								display : '卡片编码',
								name : 'ass_card_no',
								align : 'left',
								minWidth: 100
							},{
								display : '资产编码',
								name : 'ass_code',
								align : 'left',
								minWidth : '150'
							}, {
								display : '资产名称',
								name : 'ass_name',
								align : 'left',
								minWidth : '150'
							}, {
								display : '资产原值',
								name : 'price',
								align : 'left',
								minWidth : '120',
								render : function(rowdata, rowindex,value) {
									return formatNumber(
									rowdata.price == null ? 0: rowdata.price,'${ass_05006}',1);
									}
											
							}, {
								display : '累计折旧',
								name : 'add_depre',
								align : 'left',
								minWidth : '120',
								render : function(rowdata, rowindex,value) {
									return formatNumber(
									rowdata.add_depre == null ? 0: rowdata.add_depre,'${ass_05005}',1);
									}
											
							}, 
							{
								display : '累计折旧月份',
								name : 'add_depre_month',
								align : 'left',
								minWidth : '120'
							},
							{
								display : '累计分摊',
								name : 'manage_depre_money',
								align : 'left',
								minWidth : '120',
								render : function(rowdata, rowindex,value) {
									return formatNumber(
											value == null ? 0: value,'${ass_05005}',1);
									}
											
							}, {
								display : '资产净值',
								name : 'cur_money',
								align : 'left',
								minWidth : '120',
								render : function(rowdata, rowindex,value) {
									return formatNumber(
									rowdata.cur_money == null ? 0: rowdata.cur_money,'${ass_05006}',1);
									}
											
							}, {
								display : '预留残值',
								name : 'fore_money',
								align : 'left',
								minWidth : '120',
								render : function(rowdata, rowindex,value) {
									return formatNumber(
									rowdata.fore_money == null ? 0: rowdata.fore_money,'${ass_05006}',1);
									}
											
							}, {
								display : '备注',
								name : 'note',
								align : 'left',
								minWidth : '120'
							}, {
								display : '处置类型',
								name : 'dispose_type',
								textField : 'dispose_type_name',
								align : 'left',
								minWidth : '120'
							}, {
								display : '制单人',
								name : 'create_emp',
								textField : 'create_emp_name',
								align : 'left',
								minWidth : '120'
							}, {
								display : '制单日期',
								name : 'create_date',
								align : 'left',
								minWidth : '120'
							}, {
								display : '确认人',
								name : 'audit_emp',
								textField : 'audit_emp_name',
								align : 'left',
								minWidth : '120'
							}, {
								display : '确认日期',
								name : 'apply_date',
								align : 'left',
								minWidth : '120'
							}, {
								display : '状态',
								name : 'state',
								textField : 'state_name',
								align : 'left',
								minWidth : '120'
							}],
							usePager : false,
							url : 'queryAssDisposalApplyInassets.do?isCheck=false&show_detail=1',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							delayLoad : true,
							onBeforeEdit : function() {
								flag = true;
							},
							onDblClickRow : function(rowdata, rowindex, value) {
								openUpdate(rowdata.group_id + "|" + rowdata.hos_id
										+ "|" + rowdata.copy_code + "|" + rowdata.dis_a_no);
							},
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '查询（<u>Q</u>）',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}, {
									text : '添加（<u>A</u>）',
									id : 'add',
									click : add_open,
									icon : 'add'
								}, {
									line : true
								}, {
									text : '删除（<u>D</u>）',
									id : 'delete',
									click : remove,
									icon : 'delete'
								}, {
									line : true
								}, /* {
									text : '导入（<u>I</u>）',
									id : 'import',
									click : imp,
									icon : 'up'
								}, {
									line : true
								},  */{
									text : '申报确认（<u>S</u>）',
									id : 'audit',
									click : audit,
									icon : 'right'
								}, {
									line : true
								} ]
							}
						});
		}else{
			var flag;
			grid = $("#maingrid").ligerGrid(
					{
						columns : [ {
							display : '申报单号',
							name : 'dis_a_no',
							align : 'left',
							width : '10%',
							render : function(rowdata, rowindex, value) {
								return "<a href=javascript:openUpdate('"
										+ rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.dis_a_no + "')>"
										+ rowdata.dis_a_no + "</a>";
							}
						}, {
							display : '备注',
							name : 'note',
							align : 'left',
						}, {
							display : '处置类型',
							name : 'dispose_type',
							textField : 'dispose_type_name',
							align : 'left',
						}, {
							display : '制单人',
							name : 'create_emp',
							textField : 'create_emp_name',
							align : 'left',
						}, {
							display : '制单日期',
							name : 'create_date',
							align : 'left',
						}, {
							display : '确认人',
							name : 'audit_emp',
							textField : 'audit_emp_name',
							align : 'left',
						}, {
							display : '确认日期',
							name : 'apply_date',
							align : 'left',
						}, {
							display : '状态',
							name : 'state',
							textField : 'state_name',
							align : 'left',
						}],
						usePager : false,
						url : 'queryAssDisposalApplyInassets.do?isCheck=false&show_detail=0',
						width : '100%',
						height : '100%',
						checkbox : true,
						rownumbers : true,
						delayLoad : true,
						onBeforeEdit : function() {
							flag = true;
						},
						onDblClickRow : function(rowdata, rowindex, value) {
							openUpdate(rowdata.group_id + "|" + rowdata.hos_id
									+ "|" + rowdata.copy_code + "|" + rowdata.dis_a_no);
						},
						selectRowButtonOnly : true,//heightDiff: -10,
						toolbar : {
							items : [ {
								text : '查询（<u>Q</u>）',
								id : 'search',
								click : query,
								icon : 'search'
							}, {
								line : true
							}, {
								text : '添加（<u>A</u>）',
								id : 'add',
								click : add_open,
								icon : 'add'
							}, {
								line : true
							}, {
								text : '删除（<u>D</u>）',
								id : 'delete',
								click : remove,
								icon : 'delete'
							}, {
								line : true
							}, /* {
								text : '导入（<u>I</u>）',
								id : 'import',
								click : imp,
								icon : 'up'
							}, {
								line : true
							},  */{
								text : '申报确认（<u>S</u>）',
								id : 'audit',
								click : audit,
								icon : 'right'
							}, {
								line : true
							} ]
						}
					});
		}
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//审核
	function audit() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.dis_a_no + "@" + this.dispose_type);
						
					});
			$.ligerDialog.confirm('处置确认?', function (yes){
            	if(yes){
					ajaxJsonObjectByUrl("updateConfirmDisposalApplyInassets.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
            	}
			});
		}
	}

	//导入
	function imp() {
	}
	
	//添加
	function add_open() {
		parent.$.ligerDialog.open({
			url : 'hrp/ass/assinassets/assdisposal/disposalapply/assDisposalApplyInassetsAddPage.do?isCheck=false',
			title : '申报信息录入',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide:false,
			data: {
            },
            parentframename: window.name
		});
	}

	//修改
	function openUpdate(obj) {
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "dis_a_no=" + vo[3];
		parent.$.ligerDialog.open({
			url : 'hrp/ass/assinassets/assdisposal/disposalapply/assDisposalApplyInassetsUpdatePage.do?isCheck=false&' + parm.toString(),
			title : '申报信息修改',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide:false,
			parentframename: window.name
		});
	}
	
	//删除
	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.dis_a_no)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssDisposalApplyInassets.do?isCheck=false",
							{
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
				}
			});
		}
	}
	
	//字典下拉框
	function loadDict() {

		//处置类型
		autocomplete("#dispose_type", "../../../queryAssDisposeTypeDict.do?isCheck=false&dispose_type_codes=41,51,31,32", "id",
				"text", true, true, "", false, '', 150);
		autocomplete("#dept_id","../../../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"150");
		autocomplete("#ass_id","../../../queryAssNoDict.do?isCheck=false","id","text",true,true,null,null,null,"150");
		//状态
		/* $("#state").ligerTextBox({width : 150}); */
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:150
		});
		
		$("#ass_ins").ligerTextBox({width : 150});
		
		$("#create_date_beg,#create_date_end,#apply_date_beg,#apply_date_end").ligerTextBox({width : 85});
		autodate("#create_date_beg","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
	}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('I', imp);
		hotkeys('S', audit);
	}
	
	//是否显示明细
    function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		/* if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
		}
		loadHead(); */
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
			grid.bind('contextmenu', grid.options.onContextmenu);
		}
		loadHead();
		console.log(grid);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">制单日期：</td>
            <td align="left" class="l-table-edit-td" >
            <div style="float:left;">
            	<input name="create_date_beg" class="Wdate" type="text" id="create_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </div>
            <span style="float:left;margin: 0 3px;">至</span>
            <div style="float:left;">
            	<input name="create_date_end" class="Wdate" type="text" id="create_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</div>
			</td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">确认日期：</td>
            <td align="left" class="l-table-edit-td" >
            <div style="float:left;">
            	<input name="apply_date_beg" class="Wdate" type="text" id="apply_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </div>
            <span style="float:left;margin: 0 3px;">至</span>
            <div style="float:left;">
            	<input name="apply_date_end" class="Wdate" type="text" id="apply_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</div>
			</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">处置类型：</td>
			<td align="left" class="l-table-edit-td"><input name="dispose_type"
				type="text" id="dispose_type" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产名称：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_id"
				type="text" id="ass_id" /></td>
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">使用科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td">
            	<input  name="state" type="text" id="state"/>
            </td>
            <td align="right"  class="l-table-edit-td" style="padding-left: 20px;">资产信息：</td>
			 <td align="left"  class="l-table-edit-td" ><input name="ass_ins" type="text" id="ass_ins" /></td>
			<td align="right" class="l-table-edit-td" width="10%">
			</td>
			<td align="left" class="l-table-edit-td" >
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
             </td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
