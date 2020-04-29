<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'scheme_code',
			value : liger.get("scheme_code").getValue()
		});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '病种编码',
				name : 'drgs_code',
				align : 'left',
				minWidth : 80
			}, {
				display : '病种名称',
				name : 'drgs_name',
				align : 'left',
				minWidth : 80
			}, {
				display : '主诊断',
				name : 'icd10_name',
				align : 'left',
				minWidth : 120
			}, {
				display : '主手术',
				name : 'icd9_name',
				align : 'left',
				minWidth : 120
			}, {
				display : '类别',
				name : 'charge_kind_code',
				align : 'left',
				minWidth : 80
			}, {
				display : '项目编码',
				name : 'item_code',
				align : 'left',
				minWidth : 120
			}, {
				display : '项目名称',
				name : 'item_name',
				align : 'left',
				minWidth : 120
			}, {
				display : '规格',
				name : 'mate_mode',
				align : 'left',
				minWidth : 80
			}, {
				display : '计量单位',
				name : 'meas_code',
				align : 'left',
				minWidth : 80
			}, {
				display : '数量',
				name : 'amount',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.amount, 2, 1);
				}
			}, {
				display : '单价',
				name : 'price',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.price, 2, 1);
				}
			}, {
				display : '收费总额',
				name : 'income_money',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.income_money, 2, 1);
				}
			}, {
				display : '单位成本',
				name : 'unit_cost',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.unit_cost, 2, 1);
				}
			}, {
				display : '总成本',
				name : 'cost_money',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.cost_money, 2, 1);
				}
			}, {
				display : '单位收益',
				name : 'unit_cost_income',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.unit_cost_income, 2, 1);
				}
			}, {
				display : '总收益',
				name : 'cost_income',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.cost_income, 2, 1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryPatientDrgsAnalysisCostDetail.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				} ]
			},
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {
		//字典下拉框
		autocomplete("#scheme_code",
				"../querySchemeSelectAll.do?isCheck=false", "id", "text", true,
				true);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 方案编码：</td>
            <td align="left" class="l-table-edit-td"><input name="scheme_code" type="text" id="scheme_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	</table>
	<div id="maingrid"></div>
</body>
</html>
