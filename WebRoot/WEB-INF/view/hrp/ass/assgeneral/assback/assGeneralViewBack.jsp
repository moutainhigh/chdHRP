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
	$(function() {
		loadHead(null);
	});
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				 {
				display : '入库单号',
				name : 'ass_in_no',
				align : 'left',
				width : 120,
				 frozen: true
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
				width : 140, frozen: true
			},{
				display : '供应商',
				name : 'ven_name',
				align : 'left',
				width : 260
			}, {
				display : '项目',
				name : 'proj_name',
				align : 'left',
				width : 260
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
			url : 'queryAssBackInMainGeneral.do?isCheck=false&ass_back_no=${ass_back_no}',
			width : '100%',
			height : '100%',
			alternatingRow : true,
			isScroll : true,
			checkbox : false,
			rownumbers : true,
			delayLoad :false,
			toolbar : {
				items : [ {
					text : '关闭',
					id : 'close',
					click : this_close,
					icon : 'candle'
				} ]
			}

		});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	

	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>


	<div id="maingrid"></div>
</body>
</html>
