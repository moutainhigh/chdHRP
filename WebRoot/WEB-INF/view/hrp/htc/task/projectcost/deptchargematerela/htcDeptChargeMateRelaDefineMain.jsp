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
	var dataFormat;
	var grid_charge;
	var grid_mate;
	var gridManager_Charge = null;
	var gridManager_Mate = null;
	var parm = {};
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);
	});
	//查询
	function query() {
		var acc_year = $("#acc_year").val();
		var plan_code = liger.get("plan_code").getValue();
		var proj_dept_no = liger.get("proj_dept_code").getValue().split(".")[1]
		var proj_dept_id = liger.get("proj_dept_code").getValue().split(".")[0]
		if(acc_year == null || acc_year == ""){
			$.ligerDialog.error('请选择年度!')
	       return false;
		}

		if(plan_code == null || plan_code == ""){
			$.ligerDialog.error('请选择方案!')
		   return false;
		}
		if(proj_dept_no == null || proj_dept_no == ""){
			$.ligerDialog.error('请选择核算科室!')
			   return false;
		}
		if(proj_dept_id == null || proj_dept_id == ""){
			$.ligerDialog.error('请选择核算科室!')
			   return false;
		}

		queryCharge();
	}
	function queryCharge() {
		grid_charge.options.parms = [];
		grid_charge.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid_charge.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid_charge.options.parms.push({
			name:'plan_code',
			value:liger.get("plan_code").getValue()
		}); 
		grid_charge.options.parms.push({
			name:'proj_dept_no',
			value:liger.get("proj_dept_code").getValue().split(".")[1]
		}); 
		grid_charge.options.parms.push({
			name:'proj_dept_id',
			value:liger.get("proj_dept_code").getValue().split(".")[0]
		}); 
	    //加载查询条件
	    grid_charge.loadData(grid_charge.where);

	}
	function queryMate(parm) {
		grid_mate.options.parms = [];
		grid_mate.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid_mate.options.parms.push({
			name : 'acc_year',
			value : parm.acc_year
		});
		grid_mate.options.parms.push({
			name:'plan_code',
			value:parm.plan_code
		}); 
		grid_mate.options.parms.push({
			name:'proj_dept_no',
			value:parm.proj_dept_no
		}); 
		grid_mate.options.parms.push({
			name:'proj_dept_id',
			value:parm.proj_dept_id
		});
		grid_mate.options.parms.push({
			name:'charge_item_id',
			value:parm.charge_item_id
		});
		grid_mate.options.parms.push({
			name:'server_dept_no',
			value:parm.server_dept_no
		});
		grid_mate.options.parms.push({
			name:'server_dept_id',
			value:parm.server_dept_id
		}); 
	    //加载查询条件
	    grid_mate.loadData(grid_mate.where);
	}
    
	function loadHead() {
		loadHeadCharge();
		loadHeadMate();
	}

	function loadHeadCharge(){
    	grid_charge = $("#maingrid_charge").ligerGrid({
			columns : [{
				display : '收费项目编码',
				name : 'charge_item_code',
				align : 'left'
			}, {
				display : '收费项目名称',
				name : 'charge_item_name',
				align : 'left'
			}, {
				display : '单价',
				name : 'price',
				align : 'left',
				render: function(rowdata, rowindex, value) {
					return  formatNumber(rowdata.price, 2, 1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			checkbox : 'checkbox',
			url : 'queryHtcDeptChargeMateRelaCharge.do?isCheck=false',
			width : '100%',
			height : '100%',
			//rownumbers : true,
			delayLoad : true,
			onDblClickRow : onDblClickRowCharge

		});
		gridManager_Charge = $("#maingrid_charge").ligerGetGridManager();
     }

	function onDblClickRowCharge(rowdata, rowid, rowobj){
		var server_dept_code = liger.get("server_dept_code").getValue();
		if(server_dept_code == null || server_dept_code == ""){
			$.ligerDialog.error('请选择服务科室!')
			   return false;
		}
		var server_dept_no = liger.get("server_dept_code").getValue().split(".")[1]
		var server_dept_id = liger.get("server_dept_code").getValue().split(".")[0]
		parm = {
				group_id :rowdata.group_id,
		        hos_id :rowdata.hos_id,
		        copy_code :rowdata.copy_code,
		        acc_year :rowdata.acc_year,
		        plan_code :rowdata.plan_code,
		        plan_name :rowdata.plan_name,
		        proj_dept_no :rowdata.proj_dept_no,
		        proj_dept_id :rowdata.proj_dept_id,
		        proj_dept_code :rowdata.proj_dept_code,
		        proj_dept_name :rowdata.proj_dept_name,
		        charge_item_id :rowdata.charge_item_id,
		        charge_item_code :rowdata.charge_item_code,
		        charge_item_name :rowdata.charge_item_name,
		        server_dept_no:server_dept_no,
		        server_dept_id:server_dept_id,
		}
		 queryMate(parm);
    }
    function loadHeadMate(){ 
    	grid_mate = $("#maingrid_mate").ligerGrid({
			columns : [ {
				display : '材料编码',
				name : 'mate_code',
				align : 'left'
			}, {
				display : '材料名称',
				name : 'mate_name',
				align : 'left'
			}, {
				display : '型号',
				name : 'mate_mode',
				align : 'left'
			}, {
				display : '单价',
				name : 'price',
				align : 'left',
				render: function(rowdata, rowindex, value) {
					return  formatNumber(rowdata.price, 2, 1);
				}
			}, {
				display : '消耗当量',
				name : 'ratio',
				align : 'left',
				editor : {
					type : 'int'
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcDeptChargeMateRelaMate.do?isCheck=false',
			width : '100%',
			height :'100%',
			checkbox : 'checkbox',
			//rownumbers : true,
			selectRowButtonOnly : true,
			enabledEdit : true,
			delayLoad : true,
			onAfterEdit:f_onAfterEdit
		});
		gridManager_Mate = $("#maingrid_mate").ligerGetGridManager();
    }

    function f_onAfterEdit(rowdata, rowid, rowobj){
    	var formPara = {
    			group_id :parm.group_id,
    	        hos_id :parm.hos_id,
    	        copy_code :parm.copy_code,
       			acc_year :parm.acc_year,
               	plan_code :parm.plan_code,
               	proj_dept_no :parm.proj_dept_no,
               	proj_dept_id :parm.proj_dept_id,
       			charge_item_id : parm.charge_item_id,
       			server_dept_no :parm.server_dept_no,
       			server_dept_id :parm.server_dept_id,
       			mate_code:rowdata.record.mate_code,
       			ratio:rowdata.record.ratio,
    		};
    		ajaxJsonObjectByUrl("saveHtcDeptChargeMateRela.do", formPara, function(responseData) {
    			if (responseData.state == "true") {
    				 queryMate(parm);
    			}
    		});
    }

   
	function loadDict() {
		 $("#layout").ligerLayout({
			 leftWidth: '50%',
			 rightWidth: '50%',
			 allowLeftCollapse:false,
			 allowRightCollapse:false
         });

		autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true)
		
		autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
		
		autocomplete("#server_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
		  
		$("#acc_year").ligerTextBox({width:160});
		
		autodate("#acc_year", "YYYY");

	    $(':button').ligerButton({width:80});
		
	}
</script>

</head>

<body style="padding: 6px; overflow: hidden; width: 100%; height: 100%;">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
			<td align="left" class="l-table-edit-td"><input name="proj_dept_code" type="text" id="proj_dept_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"></td>
			<td align="left" class="l-table-edit-td"><input  type="button" value="查询" onclick="query();"/></td>
			<td align="left"></td>
		</tr>
		<tr>
		 	<td align="right" class="l-table-edit-td">服务科室：</td>
			<td align="left" class="l-table-edit-td"><input name="server_dept_code" type="text" id="server_dept_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="layout">
        <div position="left" title="收费项目 双击行选中" id="maingrid_charge"></div>
        <div position="right" title="作业列表" id="maingrid_mate"></div>  
    </div> 
</body>
</html>
