<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager = null;
	var editor;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
		}
		
		
		$("#tran_no").ligerTextBox({
			width : 160
		});
		$("#in_dept_id").ligerTextBox({
			width : 160
		});
		$("#out_dept_id").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#tran_no").ligerTextBox({
			disabled : true,
			cancelable : false
		});
		query();
	});
	
	function cardSelect(){
		var data = gridManager.getData();
		var ass_card_nos = [];
		$.each(data, function(i, v) {
			if (!isnull(v.ass_card_no)) {
				ass_card_nos.push("'"+v.ass_card_no+"'");
			}
		});
		var dept_id = liger.get("out_dept_id").getValue().split("@")[0];
		var dept_no = liger.get("out_dept_id").getValue().split("@")[1];

		var str = "select * from ASS_TRAN_DEPT_DETAIL_Inassets p "+
		  "left join ASS_TRAN_DEPT_Inassets b on"+
			  "  p.group_id = b.group_id"+
			  "  and p.hos_id = b.hos_id"+
			  "  and p.copy_code = b.copy_code"+
			  "  and p.tran_no = b.tran_no"+

	        " where p.group_id = a.group_id "+
	        " and p.hos_id = a.hos_id "+
	        "and p.ass_card_no = a.ass_card_no"+
	        "  and b.state = 0";     
		
		if (dept_no == null || dept_id == null || dept_id == ""
				|| dept_no == "") {
			dept_no = "";
			dept_id = "";
		}
		editor.grid.url = '../../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val()+'&is_dept=1&dept_id='+dept_id+'&dept_no='+dept_no+'&ass_card_not_exists='+ass_card_nos.toString()+"&sql="+str;

}
	
	function changeCreateDate() {
		var dept_id = liger.get("out_dept_id").getValue().split("@")[0];
		var dept_no = liger.get("out_dept_id").getValue().split("@")[1];

		if (dept_no == null || dept_id == null || dept_id == ""
				|| dept_no == "") {
			dept_no = "";
			dept_id = "";
		}
		editor.grid.url = '../../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val()+'&is_dept=1&dept_id='+dept_id+'&dept_no='+dept_no;
	}
	
	$("#out_dept_id").change(function(){
		var dept_id = liger.get("out_dept_id").getValue().split("@")[0];
		var dept_no = liger.get("out_dept_id").getValue().split("@")[1];

		if (dept_no == null || dept_id == null || dept_id == ""
				|| dept_no == "") {
			dept_no = "";
			dept_id = "";
		}
		editor.grid.url = '../../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val()+'&is_dept=1&dept_id='+dept_id+'&dept_no='+dept_no;
	});
	
	function save() {
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("out_dept_id").getValue() == "") {
			$.ligerDialog.error('移出科室不能为空');
			return;
		}
		if (liger.get("in_dept_id").getValue() == "") {
			$.ligerDialog.error('移入科室不能为空');
			return;
		}
		if (liger.get("out_dept_id").getValue() == liger.get("in_dept_id")
				.getValue()) {
			$.ligerDialog.error('移出科室和移入科室不能相同');
			return;
		}
		var data = gridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_card_no) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		var formPara = {

			tran_no : $("#tran_no").val() == "" ? '0' : $("#tran_no").val(),

			in_dept_id : liger.get("in_dept_id").getValue().split("@")[0],

			in_dept_no : liger.get("in_dept_id").getValue().split("@")[1],

			out_dept_id : liger.get("out_dept_id").getValue().split("@")[0],

			out_dept_no : liger.get("out_dept_id").getValue().split("@")[1],

			create_date : $("#create_date").val(),
			
			note : $("#note").val(),

			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssTranDeptInassets.do", formPara, function(responseData) {
				if (responseData.state == "true") {
					parentFrameUse().query();
					$("#tran_no").val(responseData.tran_no);
					query();
				}
			});
		}
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'tran_no',
			value : $("#tran_no").val() == "" ? "0" : $("#tran_no").val()
		});
		grid.loadData(grid.where);
	}
	function loadHead() {
		var dept_id = liger.get("out_dept_id").getValue().split("@")[0];
		var dept_no = liger.get("out_dept_id").getValue().split("@")[1];

		if (dept_no == null || dept_id == null || dept_id == ""
				|| dept_no == "") {
			dept_no = "";
			dept_id = "";
		}
		editor = {
			type : 'select',
			valueField : 'ass_card_no',
			textField : 'ass_card_no',
			selectBoxWidth : 1000,
			selectBoxHeight : 240,
			grid : {
				columns : [ {
					display : '卡片编码',
					name : 'ass_card_no',
					align : 'left'
				},{
					display : '原始卡片编码',
					name : 'ass_ori_card_no',
					align : 'left'
				}, {
					display : '资产编码',
					name : 'ass_code',
					align : 'left'
				}, {
					display : '资产名称',
					name : 'ass_name',
					align : 'left'
				}, {
					display : '资产规格',
					name : 'ass_spec',
					align : 'left'
				}, {
					display : '资产原值',
					name : 'price',
					align : 'left',
					render : function(rowdata, rowindex,value) {
					return formatNumber(
					rowdata.price == null ? 0: rowdata.price,'${ass_05006}',1);
					}
					
				},{
					display : '资产型号',
					name : 'ass_model',
					align : 'left'
				}, {
					display : '资产品牌',
					name : 'ass_brand',
					align : 'left'
				}, {
					display : '计量单位',
					name : 'ass_unit_name',
					align : 'left'
				}, {
					display : '生产厂商',
					name : 'fac_name',
					align : 'left'
				}, {
					display : '供应商',
					name : 'ven_name',
					align : 'left'
				}, {
					display : '累计折旧',
					name : 'depre_money',
					align : 'left',
					render : function(rowdata, rowindex,value) {
					return formatNumber(
					rowdata.depre_money == null ? 0: rowdata.depre_money,'${ass_05005}',1);
					}
					
				}, {
					display : '累计分摊',
					name : 'manage_depre_money',
					align : 'left',
					render : function(rowdata, rowindex,value) {
					return formatNumber(
					rowdata.manage_depre_money == null ? 0: rowdata.manage_depre_money,'${ass_05005}',1);
					}
					
				}, {
					display : '资产净值',
					name : 'cur_money',
					align : 'left',
					render : function(rowdata, rowindex,value) {
					return formatNumber(
					rowdata.cur_money == null ? 0: rowdata.cur_money,'${ass_05005}',1);
					}
					
				}, {
					display : '预留残值',
					name : 'fore_money',
					align : 'left',
					render : function(rowdata, rowindex,value) {
					return formatNumber(
					rowdata.fore_money == null ? 0: rowdata.fore_money,'${ass_05005}',1);
					}
					
				}  ,
				 {
					display : '累计折旧月份',
					name : 'add_depre_month',
					align : 'left'
				} ,
				 {
					display : '累计分摊月份',
					name : 'add_manage_month',
					align : 'left'
				}],
				switchPageSizeApplyComboBox : false,
				onSelectRow : f_onSelectRow_detail,
				url : '../../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&in_date='
					+ $("#create_date").val()+'&is_dept=1&dept_id='+dept_id+'&dept_no='+dept_no,
				pageSize : 30
			},
			alwayShowInDown : false,
			keySupport : true,
			autocomplete : true,
			onSuccess : function() {
				this.parent("tr").next(".l-grid-row").find("td:first").focus();
			},
			onBeforeOpen: cardSelect
		};

		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '卡片编码',
										name : 'ass_card_no',
										align : 'left',
										textField : 'ass_card_no',
										valueField : 'ass_card_no',
										editor : editor,
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.ass_card_no == null
													|| rowdata.ass_card_no == "") {
												return "";
											}
											return "<a href=javascript:openCardUpdate('"
													+ rowdata.group_id
													+ "|"
													+ rowdata.hos_id
													+ "|"
													+ rowdata.copy_code
													+ "|"
													+ rowdata.ass_card_no
													+ "')>"
													+ rowdata.ass_card_no
													+ "</a>";
										}
									}, {
										display : '资产编码',
										name : 'ass_code',
										align : 'left',
									}, {
										display : '资产名称',
										name : 'ass_name',
										align : 'left',
									}, {
										display : '备注',
										name : 'note',
										align : 'left',
										editor : {
											type : 'text'
										},
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							width : '100%',
							url : 'queryAssTranDeptDetailInassets.do',
							height : '100%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							checkbox : true,
							delayLoad : true,//初始化明细数据
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '保存',
									id : 'save',
									click : save,
									icon : 'save'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'delete',
									click : remove,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								},
								{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
								{ line:true } ,
								{ text: '打印', id:'print', click: print, icon:'print' }
								]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();

	}

	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=05";
		parent.$.ligerDialog.open({
			title : '资产卡片维护',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'
					+ parm,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide : false,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}

	function this_close() {
		frameElement.dialog.close();
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
		return true;
	}

	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if (column_name == "ass_card_no") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					ass_card_no : data.ass_card_no,
					ass_code : data.ass_code,
					ass_name : data.ass_name
				});
			}
		}
		return true;
	}

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var i = 0;
			$(data).each(
					function() {
						if (isnull(this.group_id)) {
							gridManager.deleteSelectedRow();
						} else {
							ParamVo
							.push(this.group_id + "@" 
									+ this.hos_id+ "@" 
									+ this.copy_code + "@"
									+ this.tran_no + "@"
									+ this.ass_card_no);
						}
						i++;
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog
					.confirm(
							'确定删除?',
							function(yes) {
								if (yes) {
									ajaxJsonObjectByUrl(
											"deleteAssTranDeptDetailInassets.do",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
													is_addRow();
												}
											});
								}
							});
		}
	}
	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code + "|" +v.ass_card_no;
				var value = "第" + (i + 1) + "行";

				if (v.ass_card_no == '@' || isnull(v.ass_card_no)) {
					msg += "[原始卡片号]、";
				}
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		return true;
	}
	function loadDict() {
		//autodate("#create_date");
		$("#in_dept_id").ligerComboBox({
			url : '../../../queryDeptDict.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160,
			onSelected : function(id, text) {
			}
		});
		
		liger.get("in_dept_id").setValue("${in_dept_id}@${in_dept_no}");
		liger.get("in_dept_id").setText("${in_dept_code} ${in_dept_name}");
		
		$("#out_dept_id").ligerComboBox({
			url : '../../../queryDeptDict.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160,
			onSelected : function(id, text) {
			}
		});
		
		liger.get("out_dept_id").setValue("${out_dept_id}@${out_dept_no}");
		liger.get("out_dept_id").setText("${out_dept_code} ${out_dept_name}");
	}
	
	//打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05049}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05049}'==2){
			//按仓库打印
			if(liger.get("store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_id").getValue().split(",")[0];
		}
    	
		officeFormTemplate({template_code:"0509102",use_id : useId});
    }


    //打印
    function print(){
    	var useId=0;//统一打印
 		if('${ass_05049}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}else if('${ass_05049}'==2){
 			//按仓库打印
 			if(liger.get("store_id").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_id").getValue().split(",")[0];
 		}
 		
	    var para={
	    	tran_no:$("#tran_no").val(),
	    	class_name:"com.chd.hrp.ass.serviceImpl.tran.dept.AssTranDeptInassetsServiceImpl",
			method_name:"assTranDeptInassetsByPrintTemlate",
			template_code:'0509102',
   			isPrintCount:false,//更新打印次数
   			isPreview:false,//预览窗口，传绝对路径
   			use_id:useId,
   			p_num:0
   			//isSetPrint:flag
   	    }; 
	 
       	ajaxJsonObjectByUrl("queryState.do?isCheck=false",{tran_no:$("#tran_no").val()},function (responseData){
       		if(responseData.state=="true"){
       		   officeFormPrint(para);
       		}
       	});
    }


	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);

	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="transter_id" name="transter_id" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>移库单号：</td>
			<td align="left" class="l-table-edit-td"><input name="tran_no" value="${tran_no }"
				type="text" id="tran_no" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})" value="${create_date }"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>移出科室：</td>
			<td align="left" class="l-table-edit-td"><input
				name="out_dept_id" type="text" id="out_dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>移入科室：</td>
			<td align="left" class="l-table-edit-td"><input
				name="in_dept_id" type="text" id="in_dept_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note" style="border-color: #aecaf0;">${note }</textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
