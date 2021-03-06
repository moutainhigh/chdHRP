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
		loadHotkeys();
		$("#struct_code").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerComboBox({
			width : 160
		});
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'struct_code',
			value : $("#struct_code").val()
		});
		grid.options.parms.push({
			name : 'is_stop',
			value : liger.get("is_stop").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '建筑结构代码',
						name : 'struct_code',
						align : 'left',
						render : function(rowdata, rowindex,
								value) {
							return "<a href=javascript:openUpdate('"
									+ rowdata.group_id
									+ "|"
									+ rowdata.hos_id
									+ "|"
									+ rowdata.copy_code
									+ "|"
									+ rowdata.struct_code
									+ "')>"
									+ rowdata.struct_code
									+ "</a>";
						}
					}, {
						display : '建筑结构名称',
						name : 'struct_name',
						align : 'left'
					}, {
						display : '是否停用',
						name : 'is_stop',
						align : 'left',
						render : function(rowdata, rowindex,
								value) {
							if (rowdata.is_stop == 0) {
								return "否";
							} else {
								return "是"
							}
						}
					} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssStructDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询（<u>E</u>）',
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
						}, {
							text : '导入',
							id : 'import',
							click : importdate,
							icon : 'up'
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.struct_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function importdate (){
		var para = {
				"column" : [ {
					"name" : "struct_code",
					"display" : "建筑结构代码",
					"width" : "200",
					"require" : true
				}, {
					"name" : "struct_name",
					"display" : "建筑结构名称",
					"width" : "200",
					"require" : true
				}, {
					"name" : "is_stop",
					"display" : "是否停用",
					"width" : "200"
				}

				]
			};
			importSpreadView("hrp/ass/assstructdict/assStructDictImportPage.do?isCheck=false",para);
	}
	
	function add_open() {

		$.ligerDialog.open({
			url : 'assStructDictAddPage.do?isCheck=false&',
			data : {},
			height: $(window).height() / 1.4,
			width: $(window).width()  / 1.4,
			title : '土地来源',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssStructDict();
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

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.struct_code)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssStructDict.do", {
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

	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "struct_code=" + vo[3]
		$.ligerDialog.open({
			url : 'assStructDictUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height: $(window).height() / 1.4,
			width: $(window).width()  / 1.4,
			title : '土地来源',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssStructDict();
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
				cancelable:true,
				width : 180
		})
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">建筑结构：</td>
			<td align="left" class="l-table-edit-td"><input
				name="struct_code" type="text" id="struct_code"
				ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td">
				<!-- <input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
				<!-- <select name="is_stop" id="is_stop">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select> -->
			<input name="is_stop" type="text" id="is_stop"  />
			</td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
