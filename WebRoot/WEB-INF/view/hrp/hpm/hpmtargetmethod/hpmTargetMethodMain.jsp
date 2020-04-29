<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		$("#target_code").ligerTextBox({
			width : 160
		});
		$("#target_name").ligerTextBox({
			width : 160
		});
		$("#method_code").ligerTextBox({
			width : 160
		});
		$("#method_name").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerTextBox({
			width : 160
		});
		
		toolbar();
		loadHotkeys();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'method_code',
			value : liger.get("method_code").getValue()
		});
		grid.options.parms.push({
			name : 'target_code',
			value : liger.get("target_name").getValue()
		});
		grid.options.parms.push({
			name : 'nature_code',
			value : liger.get("nature_code").getValue()
		});
		grid.options.parms.push({
			name : 'is_stop',
			value : $("#is_stop").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [{
								display : '指标编码',
								name : 'target_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.target_code + "|"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "')>"
											+ rowdata.target_code + "</a>";
								}
							}, {
								display : '指标名称',
								name : 'target_name',
								align : 'left'
							}, {
								display : '指标性质',
								name : 'nature_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.nature_name;
								}
							}, {
								display : '取值方法',
								name : 'method_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.method_name;
								}
							}, {
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是";
									}
								}
							},{display : '取值公式',
								name : 'formula_method_chs',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.formula_method_chs;
								}
								
								
							},{display : '取值函数',
								name : 'fun_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.fun_name;
								}
								
								
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHpmTargetMethodNature.do?isCheck=false',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.target_code + "|" + rowdata.group_id
								+ "|" + rowdata.hos_id + "|"
								+ rowdata.copy_code + "|"

						);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addTargetMethod, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteTargetMethod,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addTargetMethod);

		hotkeys('D', deleteTargetMethod);

	}

	function addTargetMethod() {
		
		parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmtargetmethod/hpmTargetMethodAddPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			title : '计算方式设定',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,
		    buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmTargetMethod();
					query();
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

	function deleteTargetMethod() {

		var data = gridManager.getCheckedRows();

		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
							ParamVo.push(
									this.group_id + "@" +
									this.hos_id + "@" + 
									this.copy_code + "@" + 
									this.target_code)
					});

			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHpmTargetMethod.do", {
						ParamVo : ParamVo
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function importTargetMethod() {

		$.ligerDialog.open({
			url : 'hpmTargetMethodImportPage.do?isCheck=false',
			height : 500,
			width : 800,
			title : '导入',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true
		});

	}

	function templateTargetMethod() {

		location.href = "downTemplate.do?isCheck=false";

	}

	//打印
	function print(){
    	if(grid.getData().length==0){
			$.ligerDialog.warn("请先查询数据 ");
			return;
		}
    	
    	var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
    	
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'取值方法维护',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
       			],
       			foot:[
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryHpmTargetMethodNature.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		
    }
	
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&target_code=" + vo[0] + 
				   "&group_id=" + vo[1] + 
				   "&hos_id=" + vo[2] + 
				   "&copy_code=" + vo[3];
		
		
		parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmtargetmethod/hpmTargetMethodUpdatePage.do?isCheck=false'+ parm,
			height: $(window).height(),
			width:$(window).width(),
			title : '计算方式设定',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,
		    buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmTargetMethod();
					query();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		})
	}
	function loadDict() {
		
		loadComboBox({id:"#target_name",url:"../queryTargetCode.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',maxWidth:'260',defaultSelect:false,async:false})
		
		//autocomplete("#target_name", "../queryTargetCode.do?isCheck=false","id", "text", true, true);
		
		autocomplete("#method_code","../queryTargetMethodPara.do?isCheck=false", "id", "text",true, true);
		
		autocomplete("#nature_code","../queryTargetNatureDict.do?isCheck=false", "id", "text", true,true);
		
		$("#is_stop").ligerComboBox({
			width : 160
		});
		MouseOver();
	}
	
	function MouseOver(){
		$("table").mouseleave(function(){
		 	var self = this;
            setTimeout(function() {
                $(self).children('.l-table-edit-td').hide();
            }, 4000);
		});
		//获取改变以后的数值，保存。当再次改变时，判断输入的值是否相同，如果相同将原来的保存值赋值过去。
		if($("#target_name").val() != null && $("#target_name").val() != ""){
			$("#target_name").bind("change",function(){
				var target_name = $("target_name").val();//liger.get("target_name").getValue();
				console.log("liger"+liger.get("target_name").getValue());
				if(target_name == liger.get("target_name").getValue()){
					$("target_name").val(target_name);
				}
			}); 
		}
		
		
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="left" class="l-table-edit-td" style="display:none">
				<input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="target_name" type="text" id="target_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标性质</td>
			<td align="left" class="l-table-edit-td">
				<input name="nature_code" type="text" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">取值方法：</td>
			<td align="left" class="l-table-edit-td">
				<input name="method_code" type="text" id="method_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用:</td>
			<td align="left" class="l-table-edit-td"><select id="is_stop" name="is_stop">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptool"></div>
	<div id="maingrid"></div>
</body>
</html>
