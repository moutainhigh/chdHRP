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
		loadDict();

		loadHead(null); //加载数据
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#check_date_beg").ligerTextBox({
			width : 90
		});
		$("#check_date_end").ligerTextBox({
			width : 90
		});


	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
 		  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split("@")[1]}); 
    	  grid.options.parms.push({name:'check_date_beg',value:$("#check_date_beg").val()}); 
    	  grid.options.parms.push({name:'check_date_end',value:$("#check_date_end").val()}); 
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '资产性质',
								name : 'naturs_name',
								align : 'left'
							}, {
								display : '科室编码',
								name : 'dept_code',
								align : 'left'
							}, {
								display : '科室名称',
								name : 'dept_name',
								align : 'left'
							}, {
								display : '资产编码',
								name : 'ass_code',
								align : 'left'
							}, {
								display : '资产名称',
								name : 'ass_name',
								align : 'left'
							},

							{
								display : '账面数量',
								name : 'ass_amount',
								align : 'left'
							}, {
								display : '盘点数量',
								name : 'check_amount',
								align : 'left'
							}, {
								display : '盘点调整',
								name : 'check_adjust',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssCheckMoblieContrast.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : false,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	//查询
	function generate() {
		ajaxJsonObjectByUrl("generate.do?isCheck=false",{},function (responseData){
    		if(responseData.state=="true"){
    			query();
    		}
    	});
	}
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
                var data = gridManager.getCheckedRows();
                if (data.length == 0){
                	$.ligerDialog.error('请选择行');
                }else{
                    var ParamVo =[];
                    $(data).each(function (){					
						ParamVo.push(
						//表的主键
						this.bar_code 
						)
                    });
                    $.ligerDialog.confirm('确定删除?', function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("assdeleteMoblie.do?isCheck=false",{ParamVo : ParamVo},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                    }); 
                }
                return;
			}
		}

	}

	function loadDict() {

		
		autodate("#check_date_beg", "YYYY-mm-dd", "month_first");

		autodate("#check_date_end", "YYYY-mm-dd", "month_last");

		autocomplete("#dept_id", "../../ass/queryDeptDict.do?isCheck=false",
				"id", "text", true, true, "", false,null);

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	
		<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" id="table1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">盘点日期：</td>
			<td align="left" class="l-table-edit-td"  width="5%"><input name="check_date_beg" type="text" id="check_date_beg"
				  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">&nbsp;至：</td>
			<td align="left"><input name="check_date_end" type="text" id="check_date_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'check_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">盘点科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;"></td>
			<td align="left"  class="l-table-edit-td"></td>		 
		</tr>

	</table>

	<div id="maingrid"></div>

</body>
</html>
