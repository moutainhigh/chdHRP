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
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);

		$("#maintain_item_code").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerComboBox({
			width : 160
		});
		$("#maintain_degree").ligerComboBox({
			width : 160
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'maintain_item_code',
			value : $("#maintain_item_code").val()
		});
		grid.options.parms.push({
			name : 'is_stop',
			value : liger.get("is_stop").getValue()
		});
		grid.options.parms.push({
			name : 'maintain_degree',
			value : liger.get("maintain_degree").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '保养项目编码',
								name : 'maintain_item_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.maintain_item_code
											+ "')>"
											+ rowdata.maintain_item_code
											+ "</a>";
								}
							},

							{
								display : '保养项目名称',
								name : 'maintain_item_name',
								align : 'left'
							},
							//{ display: '五笔码', name: 'wbx_code', align: 'left'

							{
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							},
							{
								display : '保养等级',
								name : 'maintain_degree',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.maintain_degree == 0) {
										
										return "一级保养";
										
									} else if (rowdata.maintain_degree == 1) {
										
										return "二级保养";
										
									}else if (rowdata.maintain_degree == 2) {
										
										return "三级保养";
										
									}else if (rowdata.maintain_degree == 3) {
										
										return "四级保养";
										
									}
								}
							}
							],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssMaintainItemDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加',
							id : 'add',
							click : itemclick,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : itemclick,
							icon : 'delete'
						}, {
							line : true
						}, {
							text : '打印',
							id : 'print',
							click : printDate,
							icon : 'print'
						}, {
							line : true
						}, {
							text : '导入',
							id : 'import',
							click : itemclick,
							icon : 'up'
						}, {
							line : true
						},
						// 						                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
						// 						                { line:true },
						]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.maintain_item_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$.ligerDialog.open({
					url : 'assMaintainItemDictAddPage.do?isCheck=false',
					height: $(window).height() / 1.4,
					width: $(window).width()  / 1.4,
					title : '添加',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveAssMaintainItemDict();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
								ParamVo.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.maintain_item_code)
							});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAssMaintainItemDict.do",
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
				return;
			case "import":
				var para = {
					"column" : [ {
						"name" : "maintain_item_code",
						"display" : "保养项目编码",
						"width" : "200",
						"require" : true
					}, {
						"name" : "maintain_item_name",
						"display" : "保养项目名称",
						"width" : "200",
						"require" : true
					}, {
						"name" : "is_stop",
						"display" : "是否停用",
						"width" : "200"
					}

					]
				/* ,
								    isUpdate:true */
				};
				importSpreadView(
						"hrp/ass/assmaintainitemdict/assMaintainItemDictImportPage.do?isCheck=false",
						para);
			case "export":
				return;
			case "downTemplate":
				location.href = "downTemplate.do?isCheck=false";
				return;
			}
		}

	}
	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "&group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code="
				+ vo[2] + "&maintain_item_code=" + vo[3];

		$.ligerDialog.open({
			url : 'assMaintainItemDictUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height: $(window).height() / 1.4,
			width: $(window).width()  / 1.4,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssMaintainItemDict();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	function loadDict() {
		//字典下拉框
		 $('#is_stop').ligerComboBox({
					data:[{id:1,text:'是'},{id:0,text:'否'}],
					valueField: 'id',
		            textField: 'text',
					cancelable:true
				});
		 $('#maintain_degree').ligerComboBox({
				data:[{id:0,text:'一级保养'},{id:1,text:'二级保养'},{id:2,text:'三级保养'},{id:3,text:'四级保养'}],
				valueField: 'id',
		        textField: 'text',
				cancelable:true,
				width:160
			});
	}

	
	function printDate() {

		if (grid.getData().length == 0) {

			$.ligerDialog.error("请先查询数据！");

			return;
		}

		var selPara = {};

		$.each(grid.options.parms, function(i, obj) {

			selPara[obj.name] = obj.value;

		});

		var dates = getCurrentDate();

		var cur_date = dates.split(";")[2];
		//跨所有列:计算列数
		var colspan_num = grid.getColumns(1).length - 1;

		var printPara = {
			title : '保养项目字典',
			head : [ {
				"cell" : 0,
				"value" : "单位: ${hos_name}",
				"colspan" : colspan_num,
				"br" : true
			} ],
			foot : [ {
				"cell" : 0,
				"value" : "主管:",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "复核人:",
				"colspan" : colspan_num - 2,
				"br" : true
			}, {
				"cell" : 0,
				"value" : "制单人： ${user_name}",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "打印日期: " + cur_date,
				"colspan" : colspan_num - 2,
				"br" : true
			} ],
			columns : grid.getColumns(1),
			headCount : 2,//列头行数
			autoFile : true,
			type : 3
		};
		ajaxJsonObjectByUrl("queryAssMaintainItemDict.do?isCheck=false",
				selPara, function(responseData) {
					printGridView(responseData, printPara);
				});

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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">保养项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="maintain_item_code" type="text" id="maintain_item_code"
				ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用
			</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="is_stop"
				name="is_stop" style="width: 135px;">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select> -->
			<input name="is_stop" type="text" id="is_stop"  />
			</td>
			 <td align="right" class="l-table-edit-td"  style="padding-left:30px;">保养等级：</td>
            <td align="left" class="l-table-edit-td">
		       <input name="maintain_degree" type="text" id="maintain_degree"/> 	
            </td> 
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>