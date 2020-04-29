<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();
	});


	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'apply_code',
			value : $("#apply_code").val() == ""?"0":$("#apply_code").val()
		});
 		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资金来源',
										name : 'source_id_no',
										textField : 'source_name',
										align : 'left',
										width: '200',
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>合计</div>';
					                        },
					                        align: 'center'
					                    }
									},
									{
										display : '支出项目',
										name : 'payment_item_id_no',
										textField : 'payment_item_name',
										align : 'left',
										width: '200'
									},
									{
										display : '用款申请金额',
										name : 'use_payment',
										align : 'left',
										width: '150',
										editor : {
											type : 'numberbox',
											precision : 2
										},
										align : 'right',
										render: function(item)
							            {
							                    return formatNumber(item.use_payment,2,1);
							            }
									},
									{
										display : '报销金额',
										name : 'payment_amount',
										width: '150',
										align : 'right',
										render: function(item)
							            {
							                    return formatNumber(item.payment_amount,2,1);
							            },
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					                        }
					                    } 
									},
									{
										display : '说明',
										name : 'remark',
										align : 'left',
										width: '300'
									},
									{ display: '预算额度', name: 'budg_money', width: 120,editable: false},
									{ display: '已执行额度', name: 'already_execute', width: 120,editable: false}],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryBudgPaymentApplyDet.do?isCheck=false&apply_code=${apply_code}',
							width : '100%',
							height : '85%',
							checkbox : false,
							enabledEdit : false,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							rownumbers : true,
							delayLoad : false,//初始化明细数据
							selectRowButtonOnly : false
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		gridManager.toggleCol("budg_money",'${isDisplay}');
		gridManager.toggleCol("already_execute",'${isDisplay}');
		
	}
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		return  true; 
	}
	function toExamine(){
		var ParamVo = [];
		ParamVo.push('${group_id}' + "@" + '${hos_id}' + "@"
									+ '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
						
		$.ligerDialog.confirm('确定审核?', function (yes){
            if(yes){
				ajaxJsonObjectByUrl("updateToExaminePaymentApply.do", {
						ParamVo : ParamVo.toString()
				}, function(responseData) {
						if (responseData.state == "true") {
							parentFrameUse().query();
							query();
							location.reload();
						}
				});
            }
		});
	}
	function notToExamine(){
		var ParamVo = [];
		ParamVo.push('${group_id}' + "@" + '${hos_id}' + "@"
									+ '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
			$.ligerDialog.confirm('确定消审?', function (yes){
            	if(yes){
					ajaxJsonObjectByUrl("updateNotToExaminePaymentApply.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							parentFrameUse().query();
							query();
							location.reload();
						}
					});
            	}
			});
	}
	
	function this_close(){
		frameElement.dialog.close();
	}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', toExamine);
		hotkeys('D', notToExamine);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		
		/* $("#dept_id").ligerComboBox({
          	url: "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
			onSuccess : function(data) {
				this.setValue("${dept_id}.${dept_no}");
			}
 		 }); */
 		 
 		var para = {emp_id:'',emp_no:''};
 		autocomplete("#use_apply_code","queryUseApplyCode.do?isCheck=false", "id", "text", true, true, para);
  		liger.get("use_apply_code").setValue("${use_apply_code}");
  		liger.get("use_apply_code").setText("${use_apply_code}");
  		$("#use_apply_code").ligerTextBox({disabled:true,cancelable: false,width : 230});
    	var isDisplay = '${isDisplay}';
    	if(!isDisplay){
    		$("#this_td1").hide();
        	$("#this_td2").hide();
    	}
 		 
 		autocomplete("#dept_id","../../../queryDeptDict.do?isCheck=false", "id","text", true, true,null,false);
 		
  		liger.get("dept_id").setValue("${dept_id}.${dept_no}");
  		liger.get("dept_id").setText("${dept_code} ${dept_name}");
  		 
		autocomplete("#emp_id","../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false);
  		liger.get("emp_id").setValue("${emp_id}.${emp_no}");
		liger.get("emp_id").setText("${emp_code} ${emp_name}");
    	
    	autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	liger.get("proj_id").setValue("${proj_id}.${proj_no}");
		liger.get("proj_id").setText("${proj_code} ${proj_name}");
		
		$("#dept_id").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#dept_id").attr("disabled","disabled");
		$("#proj_id").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#proj_id").attr("disabled","disabled");
		$("#emp_id").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#emp_id").attr("disabled","disabled");
		$("#phone").ligerTextBox({disabled:true,cancelable: false,width : 230});
		$("#phone").attr("disabled","disabled");
		$("#card_no").ligerTextBox({disabled:true,cancelable: false,width : 230});
		$("#card_no").attr("disabled","disabled");
		$("#apply_date").ligerTextBox({disabled:true,cancelable: false,width : 230});
		$("#apply_date").attr("disabled","disabled");
		$("#remark").attr("disabled","disabled");
		
    	
		
		$("#payment_amount").val("${payment_amount }");
		$("#payment_amount").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#offset_amount").val("${offset_amount }");
		$("#offset_amount").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#pay_amount").val("${pay_amount }");
		$("#pay_amount").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#apply_code").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#phone").ligerTextBox({width : 230});
		
		$("#card_no").ligerTextBox({width : 230});
		
		$("#apply_date").ligerTextBox({width : 230});
    	
		$("#audit").ligerButton({click: toExamine, width:90});
		$("#unAudit").ligerButton({click: notToExamine, width:90});
		$("#close").ligerButton({click: this_close, width:90});
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
    	
    	var param = {
        		'group_id':'${group_id}',
        		'hos_id':'${hos_id}',
        		'copy_code':'${copy_code}',
        		'proj_id':'${proj_id}',
        		'emp_id':'${emp_id}',
        		'dept_id':'${dept_id}'
        	};
        	$.post("getBorrowAmount.do?isCheck=false",param,function(data){
        		if(data.result != null){
        			$("#is_apply").text("是");
            		$("#not_price").text(data.result.remain_amount);
        		}else{
        			$("#is_apply").text("否");
        		}
        	},"json");
	}
	
	
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${02004}'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${02004}'==2){
			//按科室打印
			 if(liger.get("dept_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按科室打印，请选择科室！');
				return;
			}
			useId=liger.get("dept_id").getValue().split(".")[0];
		}
		var para={
				apply_code:$("#apply_code").val(),
				template_code:'02002',
				use_id:useId
			};
			printTemplate("queryPaymentCheckPrintTemlate.do",para);
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${02004}'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${02004}'==2){
			//按科室打印
			 if(liger.get("dept_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按科室打印，请选择科室！');
				return;
			}
			
			useId=liger.get("dept_id").getValue().split(".")[0];
		}
		
		parent.parent.$.ligerDialog.open({url : 'hrp/acc/payable/reimbursemt/apply/paymentApplyPrintSetPage.do?isCheck=false&template_code=02002&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		});
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>单号：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_code" disabled="disabled" value="${apply_code }"
				type="text" id="apply_code"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" 
				type="text" id="dept_id"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>申请日期：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_date" 
				type="text" id="apply_date" class="Wdate" value="${apply_date }" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>报销人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="emp_id" type="text" id="emp_id" 
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">银行帐号：</td>
			<td align="left" class="l-table-edit-td"><input name="card_no" 
				type="text" id="card_no"  value="${card_no }"
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">联系电话：</td>
			<td align="left" class="l-table-edit-td"><input name="phone" 
				type="text" id="phone"  value="${phone }"
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">报销金额：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_amount" disabled="disabled"
				type="text" id="payment_amount" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">冲抵借款金额：</td>
			<td align="left" class="l-table-edit-td"><input name="offset_amount" disabled="disabled"
				type="text" id="offset_amount" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">实际支付金额：</td>
			<td align="left" class="l-table-edit-td"><input name="pay_amount" disabled="disabled"
				type="text" id="pay_amount" 
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">申请事由：</td>
			<td align="left" class="l-table-edit-td" colspan="4">
				<textarea rows="3" cols="90" id="remark" name="remark">${remark }</textarea>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;" id="this_td1">用款申请：</td>
			<td align="left" class="l-table-edit-td" id="this_td2"><input name="use_apply_code" 
				type="text" id="use_apply_code"  
				 /></td>
		</tr>
	</table>
	
	<hr size="1" width="1400" color="#A3COE8" align="left" style="position:absolute;">
	
	<!-- <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 10px;">
		<tr style="display: none">
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">项目总预算：</td>
			<td align="left" class="l-table-edit-td"></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">预算余额：</td>
			<td align="left" class="l-table-edit-td"></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">可用余额：</td>
			<td align="left" class="l-table-edit-td"></td>
			<td align="left"></td>
		</tr>	
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">是否有借款：</td>
			<td align="left" class="l-table-edit-td"><font id="is_apply"></font></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">借款余额：</td>
			<td align="left" class="l-table-edit-td"><font id="not_price"></font></td>
			<td align="left"></td>
		</tr>
	</table> -->
	
	
	<div id="maingrid"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">制单人：</td>
			<td align="left" class="l-table-edit-td">${maker_name }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">制单日期：</td>
			<td align="left" class="l-table-edit-td">${make_date }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">审核人：</td>
			<td align="left" class="l-table-edit-td">${checker_name }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">审核日期：</td>
			<td align="left" class="l-table-edit-td">${check_date }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">支付人：</td>
			<td align="left" class="l-table-edit-td">${payer_name }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">支付日期：</td>
			<td align="left" class="l-table-edit-td">${pay_date }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">状态：</td>
			<td align="left" class="l-table-edit-td">
			${state_name }
			</td>
			<td align="left"></td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="audit" accessKey="A"><b>审核（<u>A</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="unAudit" accessKey="U"><b>消审（<u>U</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
</body>
</html>
