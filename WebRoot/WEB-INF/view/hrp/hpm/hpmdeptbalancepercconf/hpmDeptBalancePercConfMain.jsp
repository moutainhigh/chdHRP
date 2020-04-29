<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);//加载数据
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_code").getValue()
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue().split(",")[1]
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '科室编码',name : 'dept_id',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('"
								+ rowdata.dept_id + "','"+rowdata.dept_no+"');\" >"
								+ rowdata.dept_id + "</a>";
					}
				}, 
				
				{display : '科室名称',name : 'dept_name',align : 'left'}, 
				
				{display : '计提比例',name : 'dept_percent',align : 'left'} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmDeptBalancePercConf.do?isCheck=false',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,
			delayLoad:true,selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.dept_id);//实际代码中temp替换主键
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		formatTrueFalse();
	}
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({text : '生成（<u>G</u>）',id : 'create',click : create,icon : 'bookpen'});
       	obj.push({ line:true });
       	
       	obj.push({text : '快速填充（<u>F</u>）',id : 'fast',click : fast,icon : 'back'});
       	obj.push({ line:true });
       	
       	obj.push({text : '添加（<u>A</u>）',id : 'add',click : addData,icon : 'add'});
       	obj.push({ line:true });
       	
       	obj.push({text : '删除（<u>D</u>）',id : 'delete',click : remove,icon : 'delete'});
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('G',create);
		hotkeys('D',remove);
		hotkeys('F',fast);
		hotkeys('A',addData);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
	}
	
	//添加
	function addData(){
		$.ligerDialog.open({
			url : 'hpmDeptBalancePercConfAddPage.do?isCheck=false',
			height : 250,width : 540,title : '添加',modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptBalancePercConf();
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
	
	//快速填充
	function fast(){
		var data = gridManager.getCheckedRows();
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.dept_id+"@"+this.dept_no);//实际代码中temp替换主键
		});
		
		$.ligerDialog.open({
			url : 'hpmDeptBalancePercConfFasePage.do?isCheck=false&checkIds='+ checkIds,
			data:{ is_check:data},height : 250,width : 450,title : '快速填充',
			modal : true,showToggle : false,showMax : false,
			showMin : true,isResize : true,
			buttons : [ 
				{
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveDeptBalancePercConf();
					},cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	//生成
	function create(){
		$.ligerDialog.confirm('确定生成?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("createHpmDeptBalancePercConf.do?isCheck=false", {},function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//删除
	function remove(){
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return ; 
		}
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.dept_id+"@"+this.dept_no);//实际代码中temp替换主键
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmDeptBalancePercConf.do",{checkIds : checkIds.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//下载模板
	function downTemplate(){
		location.href = "downTemplateHpmDeptBalancePercConf.do?isCheck=false";
	}
	
	//导入
	function importData(){
		parent.$.ligerDialog.open({ 
			url:'hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, 
			title:'计提比例维护导入',height: 300,width: 450,
			modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//修改页面
	function openUpdate(obj1,obj2) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'hpmDeptBalancePercConfUpdatePage.do?isCheck=false&dept_id='+ obj1+'&dept_no='+obj2,data : {},
			title : '修改',height : 250,width : 500,modal : true,
			showToggle : false,showMax : false,showMin : false,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptBalancePercConf();
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
	
	//字典下拉框
	function loadDict() {
		autocomplete("#dept_kind_code","../../hpm/queryDeptKindDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#dept_id", "../../hpm/queryDeptDictByPerm.do?isCheck=false","id", "text", true, true);
	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>