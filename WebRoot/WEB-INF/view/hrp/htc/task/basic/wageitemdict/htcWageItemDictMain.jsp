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
		
		loadDict(); //加载下拉框
		
		loadHead(null); //加载数据
		
	});
	
	function loadDict() {
		//字典下拉框
		$("#wage_item_code").ligerTextBox({width:160});
		autocomplete("#is_stop", "../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{display : '工资编码',name : 'wage_item_code',align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"+rowdata.wage_item_code+ "');\" >"+ rowdata.wage_item_code + "</a>";
								}
							}, 
							{display : '工资名称',name : 'wage_item_name',align : 'left'},
							{display : '显示顺序',name : 'sort_code',align : 'left'}, 
							{display : '是否停用',name : 'is_stop',align : 'left',				
								render : function(rowdata, rowindex, value) {
									return rowdata.is_stop == 1 ? "是" : "否";
								}
							 },
							 {display : '备注',name : 'remark',align : 'left'}, 
							],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcWageItemDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					enabledEdit: false,
					delayLoad:true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ 
						          {text : '查询',id : 'search',click : query,icon : 'search'},
						          {line : true}, 
						          {text : '添加',id : 'add',click : add_open,icon : 'add'},
						          {line : true}, 
						          {text : '删除',id : 'delete',click : remove,icon : 'delete'},
						        /*   {line : true} ,
								  {text : '导入',id : 'imp',click : imp,icon : 'up'} */
						          ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						
						openUpdate(rowdata.wage_item_code);//实际代码中temp替换主键
						
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
		
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'wage_item_code',value : $("#wage_item_code").val()});
		grid.options.parms.push({name : 'is_stop',value : liger.get("is_stop").getValue()});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//添加
	function add_open(){
		$.ligerDialog.open({
			url: 'htcWageItemDictAddPage.do?isCheck=false',
			height: 300,
			width: 500,
			title: '添加',
			modal: true,
			showToggle: false,
			showMax: false,
			showMin: true,
			isResize: true,
		    buttons: [{
		    	text: '确定',
		    	onclick: function(item, dialog) {
		    		dialog.frame.saveWageItemDict();
		    	},
		    	cls: 'l-dialog-btn-highlight'
		   	},
		    {
		    	text: '取消',
		    	onclick: function(item, dialog) {
		    		dialog.close();
		    	}
		    }]
		});
	}
	
	//删除
	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
					this.wage_item_code
				);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcWageItemDict.do", {
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
	
	//更新
	function openUpdate(obj) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'htcWageItemDictUpdatePage.do?isCheck=false&wage_item_code='+ obj,
			data : {},
			height : 300,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveWageItemDict();
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

	function imp(){
		
		var para={
			    "column": [
			        {
			            "name": "wage_item_code",
			            "display": "工资项编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "wage_item_name",
			            "display": "工资项名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "remark",
			            "display": "备注",
			            "width": "200"
			        },
			    ]
			};
		
			importSpreadView("hrp/htc/basic/wageitemdict/impHtcWageItemDict.do?isCheck=false",para); 
		}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工资项：</td>
			<td align="left" class="l-table-edit-td">
			<input name="wage_item_code" type="text" id="wage_item_code" ltype="text"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td">
				<input name="is_stop" type="text" id="is_stop" ltype="text"/>
			</td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
