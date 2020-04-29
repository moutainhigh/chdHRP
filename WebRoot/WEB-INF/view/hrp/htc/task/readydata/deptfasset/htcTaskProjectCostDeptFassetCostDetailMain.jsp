<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	    grid.options.parms.push({name : 'start_month',value : $("#start_month").val()});
		grid.options.parms.push({name : 'end_month',value : $("#end_month").val()});
		grid.options.parms.push({name : 'proj_dept_no',value:liger.get("proj_dept_code").getValue().split(".")[1]});
		grid.options.parms.push({name : 'proj_dept_id',value:liger.get("proj_dept_code").getValue().split(".")[0]});
		grid.options.parms.push({name : 'cost_item_no',value:liger.get("cost_item_code").getValue().split(".")[1]});
		grid.options.parms.push({name : 'cost_item_id',value:liger.get("cost_item_code").getValue().split(".")[0]});
		grid.options.parms.push({name : 'asset_code',value:liger.get("asset_code").getValue()});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '年月',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return  rowdata.acc_year+rowdata.acc_month;
								}
							}, {
								display : '科室编码',
								name : 'proj_dept_code',
								align : 'left'
							}, {
								display : '科室名称',
								name : 'proj_dept_name',
								align : 'left'
							}, {
								display : '成本项目编码',
								name : 'cost_item_code',
								align : 'left'
							}, {
								display : '成本项目名称',
								name : 'cost_item_name',
								align : 'left'
							},{
								display : '卡片号',
								name : 'asset_code',
								align : 'left'
							}, {
								display : '资产名称',
								name : 'asset_name',
								align : 'left'
							}, {
								display : '折旧额',
								name : 'depre_amount', align: 'right',
								render: function(rowdata, rowindex, value) {
									return  formatNumber(rowdata.depre_amount, 2, 1);
								}
							}, {
								display : '资金来源',
								name : 'source_name',
								align : 'left'
							}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcTaskProjectCostDeptFassetCostDetail.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					delayLoad :true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						},{
							text : '数据处理',
							id : 'dataDispose',
							click : data_open,
							icon : 'back'
						}]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function data_open()
    {
        $.ligerDialog.open({
            height:200,
            width: 350,
            title : '数据处理',
            target:$("#data_open"),
            showMax: false,
            showToggle: false,
            showMin: false,
            isResize: false,
            slide: false,
            buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dataDispose();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.hide();
				}
			} ]
        });
    } 


	function dataDispose(){
		var plan_code = liger.get("plan_code").getValue();
		if(plan_code == ""){
			$.ligerDialog.error("请选择方案!");
			return false;
		}
		var formPara = {
				plan_code:plan_code
			};
			ajaxJsonObjectByUrl("disposeHtcTaskProjectCostDeptFassetCostDetail.do?isCheck=false",formPara,function(responseData) {
				if (responseData.state == "true") {query()}
			});
	}
	
	function loadDict() {
		
		autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false","id","text",true,true);
		autocomplete("#cost_item_code","../../../info/base/queryHtcCostItemDict.do?isCheck=false","id","text",true,true);
		autocomplete("#asset_code","../../../info/base/queryHtcFassetDict.do?isCheck=false","id","text",true,true);
		autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true);
		autodate("#start_month", "yyyyMM");
		autodate("#end_month", "yyyyMM");
		$("#start_month").ligerTextBox({width:100});
		$("#end_month").ligerTextBox({width:100});	 	
	}

	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间：</td>
			<td>
				<table style="font-size:12px;">
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" 
								name="start_month" type="text" id="start_month" ltype="text"  style="width:70px;"
								onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" />
						</td>
						<td align="right"><span>至&nbsp;</span></td>
						<td>
							<input class="Wdate"
								name="end_month" type="text" id="end_month" ltype="text"  style="width:70px;"
								onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" />
						</td>
					</tr>				
				</table>          	
          	</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input name="proj_dept_code" type="text" id="proj_dept_code" ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产：</td>
			<td align="left" class="l-table-edit-td"><input name="asset_code" type="text" id="asset_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
	<div id="data_open">               
        <table>
           <tr>
                <td align="left"></td>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案：</td>
          <td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text"  /></td>
          <td align="left"></td>      
           </tr>
        </table>
    </div>
</body>
</html>