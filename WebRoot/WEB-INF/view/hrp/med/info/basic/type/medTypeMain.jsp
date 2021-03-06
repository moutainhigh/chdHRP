<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;

    $(function (){

    	$("#layout1").ligerLayout({ leftWidth : 255 , centerBottomHeight : 350});
    	$("#toptoolbar").ligerToolBar({
    		items: [{
    			text: '添加（<u>A</u>）', id:'add', icon:'add', click: add_mt
    		},{ line:true },{
    			text: '保存（<u>S</u>）',id:'save', icon:'save', click: save
    		},{ line:true },{
    			text: '变更（<u>C</u>）',id:'openChange', icon:'edit', click: openChange
    		},{ line:true },{
    			text: '删除（<u>D</u>）',id:'delete', icon:'delete', click: remove
    		},{ line:true },{ 
    			text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate, icon:'down' 
    		},{ line:true },{ 
				text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' },
				{ line:true },{ 
				text: '导出（<u>E</u>）', id:'exp', click: exp,icon:'down' }
				]
    	});
    	ajaxJsonObjectByUrl("getRules.do?isCheck=false", {}, function(responseData) {
			$("#font2").text(responseData.ruless);
        }, false);
    	loadTree();
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
		loadHotkeys();
		loadForm();
    });

    function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element){
	             if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] });
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
		$("form").ligerForm();
    }

    /* 设置树形菜单 */
    function onSelect(note){
        query(note.data.id);
    }

    function loadTree(){
		ajaxJsonObjectByUrl("queryMedTypeByTree.do?isCheck=false", {},
			function(responseData) {
				if (responseData != null) {
			tree = $("#tree").css({"height": $(window).height()-60}).ligerTree({
						data : responseData.Rows,
						parentIcon: null,
						childIcon: null ,
						checkbox : false,
						idFieldName : 'code',
						parentIDFieldName : 'pcode',
						textFieldName : 'text',
						onSelect: onSelect,
						isExpand: true,
						nodeWidth:400,
					});
			$("#tree").css({"width":"100%"});
					treeManager = $("#tree").ligerGetTreeManager();
					treeManager.collapseAll(); //全部收起
				}
			}
		);
	}

    //查询
    function  query(tid){

    	$("#med_type_code").attr("disabled", true);
    	$("#med_type_name").attr("disabled", true);
    	//主表查询
    	paras = {
    		"med_type_id" : tid
    	};
    	ajaxJsonObjectByUrl("queryMedTypeById.do?isCheck=false", paras, function (responseData){
    		$("#med_type_id").val(responseData.med_type_id);
    		$("#med_type_code").val(responseData.med_type_code);
    		$("#med_type_name").val(responseData.med_type_name);
    		liger.get("super_code").setValue(responseData.super_code + ',' + responseData.super_code);
    		if(responseData.super_code != '0'){
        		liger.get("super_code").setText(responseData.super_code + ' ' + responseData.super_name);
    		}
    		if(responseData.is_last == '1'){
        		liger.get("is_last").setValue(responseData.is_last);
        		liger.get("is_last").setText("是");
    		}else{
        		liger.get("is_last").setValue(responseData.is_last);
        		liger.get("is_last").setText("否");
    		}
    		if(responseData.is_stop == '1'){
        		liger.get("is_stop").setValue(responseData.is_stop);
        		liger.get("is_stop").setText("是");
    		}else{
        		liger.get("is_stop").setValue(responseData.is_stop);
        		liger.get("is_stop").setText("否");
    		}
    		if(responseData.is_auto_exp == '1'){
        		liger.get("is_auto_exp").setValue(responseData.is_auto_exp);
        		liger.get("is_auto_exp").setText("是");
    		}else{
        		liger.get("is_auto_exp").setValue(responseData.is_auto_exp);
        		liger.get("is_auto_exp").setText("否");
    		}
    		$("#spell_code").val(responseData.spell_code);
    		$("#wbx_code").val(responseData.wbx_code);
    		$("#note").val(responseData.note == null?"":responseData.note);
    		$("#group_id").val(responseData.group_id);
    		$("#hos_id").val(responseData.hos_id);
    		$("#copy_code").val(responseData.copy_code);
    		liger.get("fim_type_code").setValue(responseData.fim_type_code);
		});
    	//变更记录查询
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name : 'med_type_id', value : tid});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [{
        	   display: '类别编码', name: 'med_type_code', align: 'left'
			},{
				display: '类别名称', name: 'med_type_name', align: 'left'
			},{
				display: '是否末级', name: 'is_last', align: 'left',
				render : function(rowdata, rowindex, value) {
					return rowdata.is_last == 1 ? "是" : "否";
				}
			},{
				display: '是否停用', name: 'is_stop', align: 'left',
				render : function(rowdata, rowindex, value) {
					return rowdata.is_stop == 1 ? "是" : "否";
				}
			},{
				display: '是否自动有效期', name: 'is_auto_exp', align: 'left',
				render : function(rowdata, rowindex, value) {
					return rowdata.is_auto_exp == 1 ? "是" : "否";
				}
			},{
				display: '操作人员', name: 'change_user', align: 'left'
			},{
				display: '操作时间', name: 'change_date', align: 'left'
			},{
				display: '变更原因', name: 'change_note', align: 'left'
			}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedTypeDict.do?isCheck=false',
			delayLoad: true,width: '98%', height: '50%', checkbox: false,rownumbers:false,
			selectRowButtonOnly:false,//heightDiff: -10,
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_mt(){
    	$("#med_type_id").val("");
    	$("#med_type_code").val("");
    	$("#med_type_name").val("");
		$("#med_type_code").removeAttr("disabled");
		$("#med_type_name").removeAttr("disabled");
    	liger.get("super_code").setValue("");
    	liger.get("super_code").setText("");
    	liger.get("is_last").setValue("1");
    	liger.get("is_last").setText("是");
    	$("#spell_code").val("");
    	$("#wbx_code").val("");
    	$("#note").val("");
    	//grid.loadData();
    }

    function remove(){
    	var med_type_id = $("#med_type_id").val();
		if (med_type_id == ""){
 			$.ligerDialog.error('请选择药品类别');
 		}else{
        	$.ligerDialog.confirm('确定删除?', function (yes){
        		if(yes){
        			paras = {
        				med_type_id : med_type_id,
        				group_id : $("#group_id").val(),
        				hos_id : $("#hos_id").val(),
        				copy_code : $("#copy_code").val()
        			}
        			ajaxJsonObjectByUrl("deleteMedType.do", paras, function (responseData){
        				if(responseData.state=="true"){
        					loadTree();
        					add_mt();
        				}
        			});
        		}
        	});
        }
    }

    function save(){
		if($("form").valid()){
    		var formPara ={
				med_type_id : $("#med_type_id").val(),
				med_type_code : $("#med_type_code").val(),
				med_type_name : $("#med_type_name").val(),
				supper_code : liger.get("super_code").getValue() ==null?"":liger.get("super_code").getValue().split(",")[0],
				is_last : liger.get("is_last").getValue(),
				is_stop : liger.get("is_stop").getValue(),
				is_auto_exp : liger.get("is_auto_exp").getValue(),
				note : $("#note").val(),
				fim_type_code : liger.get("fim_type_code").getValue(),
			};
			ajaxJsonObjectByUrl("saveMedType.do",formPara,function (responseData){
        		if(responseData.state=="true"){
        			loadTree();
        			//query(responseData.med_type_id);
        		}
        	});
		}
		add_mt();
    }

    function openChange(obj){
    	if($("#med_type_id").val() == ""){
    		$.ligerDialog.error('请选择药品类别');
    		return;
    	}
		var paras =
			"group_id=" + $("#group_id").val() + "&"+
			"hos_id=" + $("#hos_id").val() +"&"+
			"copy_code=" + $("#copy_code").val() + "&"+
			"med_type_id=" + $("#med_type_id").val() ;
		$.ligerDialog.open({
			title:'药品类别变更',
			height: 400,
			width: 800,
			url: 'medTypeDictPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			buttons: [
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			]
		});
    }
    function loadDict(){
		//字典下拉框
		$("#super_code").ligerComboBox({disabled:true,cancelable: false});
        $("#is_last").ligerComboBox({disabled:true,cancelable: false});
        liger.get("is_last").setValue("1");
		liger.get("is_last").setText("是");
    	//autocomplete("#super_code", "../../../queryMedType.do?isCheck=false", "id", "text", true, true, "", false);
    	//autocomplete("#is_last", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "1");
    								
    	autocomplete("#is_stop", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true, "", true);
    	autocomplete("#is_auto_exp", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true, "", true);
    	
    	autocomplete("#fim_type_code", "../../../queryMedFimTypeDict.do?isCheck=false", "id", "text", true, true,"",false,"",180);

    	$("#med_type_code").ligerTextBox({width:160});
    	$("#med_type_name").ligerTextBox({width:160});
    	$("#spell_code").ligerTextBox({width:160});
    	$("#wbx_code").ligerTextBox({width:160});
	}
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('S', save);
		hotkeys('C', openChange);
	   /*  hotkeys('E', exportExcel);
		hotkeys('P', printDate); */
		hotkeys('B', downTemplate); 
		hotkeys('I', imp); 
	 }

    function imp(){
    	var index = layer.open({
					type : 2,
					title : '药品分类',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medTypeImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}
    function downTemplate(){

    	location.href = "downTemplate.do?isCheck=false";
    }
    
    
    function exp(){
    	
    	location.href = "expMedTypeToExcel.do?isCheck=false";
    }

	//打印数据
	function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","08102 药品分类字典",true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
			med_type_id:$("#med_type_id").val(),
			med_type_code:$("#med_type_code").val(),
			med_type_name:$("#med_type_name").val(),
			super_code:$("#super_code").val(),
			level:$("#level").val(),
			is_last:$("#is_last").val(),
			is_stop:$("#is_stop").val(),
			note:$("#note").val(),
			is_budg:$("#is_budg").val(),
			cost_subj_code:$("#cost_subj_code").val(),
			is_auto_exp:$("#is_auto_exp").val(),
			group_id : $("#group_id").val(),
			hos_id : $("#hos_id").val(),
			copy_code : $("#copy_code").val()
         };
		ajaxJsonObjectByUrl("queryMedType.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.med_type_id+"</td>";
					 trHtml+="<td>"+item.med_type_code+"</td>";
					 trHtml+="<td>"+item.med_type_name+"</td>";
					 trHtml+="<td>"+item.super_code+"</td>";
					 trHtml+="<td>"+item.level+"</td>";
					 trHtml+="<td>"+item.is_last+"</td>";
					 trHtml+="<td>"+item.is_stop+"</td>";
					 trHtml+="<td>"+item.note+"</td>";
					 trHtml+="<td>"+item.is_budg+"</td>";
					 trHtml+="<td>"+item.cost_subj_code+"</td>";
					 trHtml+="<td>"+item.is_auto_exp+"</td>";
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08102 药品分类字典",true);
	    },true,manager);
		return;
	 }


	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08102 药品分类字典.xls",true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           med_type_id:$("#med_type_id").val(),
           med_type_code:$("#med_type_code").val(),
           med_type_name:$("#med_type_name").val(),
           super_code:$("#super_code").val(),
           level:$("#level").val(),
           is_last:$("#is_last").val(),
           is_stop:$("#is_stop").val(),
           note:$("#note").val(),
           is_budg:$("#is_budg").val(),
           cost_subj_code:$("#cost_subj_code").val(),
           is_auto_exp:$("#is_auto_exp").val()
         };
		ajaxJsonObjectByUrl("queryMedType.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.med_type_id+"</td>";
					 trHtml+="<td>"+item.med_type_code+"</td>";
					 trHtml+="<td>"+item.med_type_name+"</td>";
					 trHtml+="<td>"+item.super_code+"</td>";
					 trHtml+="<td>"+item.level+"</td>";
					 trHtml+="<td>"+item.is_last+"</td>";
					 trHtml+="<td>"+item.is_stop+"</td>";
					 trHtml+="<td>"+item.note+"</td>";
					 trHtml+="<td>"+item.is_budg+"</td>";
					 trHtml+="<td>"+item.cost_subj_code+"</td>";
					 trHtml+="<td>"+item.is_auto_exp+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08102 药品分类字典.xls",true);
	    },true,manager);
		return;
	 }
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1">
		<div position="left" title="药品类别" >
			<h2><font id="font1">编码规则：<font id="font2" color="red"></font></font></h2>
			<div class="l-scroll" style="width: 100%">
				<ul id="tree" style="overflow: auto;margin-top:3px;"/>
			</div>
		</div>
		<div position="center" >
			<div id="toptoolbar" ></div>
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" style="width:100%" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							<span style="color:red">*</span>药品类别编码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="med_type_id" type="hidden" id="med_type_id" ltype="text" />
							<input name="med_type_code" disabled="disabled" type="text" id="med_type_code" ltype="text" required="true" validate="{required:true,maxlength:40}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							<span style="color:red">*</span>药品类别名称：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="med_type_name" disabled="disabled" type="text" id="med_type_name" ltype="text" required="true" validate="{required:true,maxlength:80}" />
						</td>
					</tr>
					<tr style="display:none">
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							上级编码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="super_code" disabled="disabled" type="text" id="super_code" ltype="text" validate="{required:false}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 0px;">
							<span style="color:red">*</span>是否末级：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="is_last" disabled="disabled" type="text" id="is_last" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							<span style="color:red">*</span>是否停用：
						</td>
						<td align="left"  class="l-table-edit-td" >
							<input name="is_stop" type="text" id="is_stop" ltype="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 0px;">
							<span style="color:red">*</span>是否自动有效期：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="is_auto_exp" type="text" id="is_auto_exp" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							拼音码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="spell_code" disabled="disabled" type="text" id="spell_code" ltype="text" validate="{required:false,maxlength:40}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							五笔码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="wbx_code" disabled="disabled" type="text" id="wbx_code" ltype="text" validate="{required:false,maxlength:40}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >药品财务分类:</td>
						<td align="left" class="l-table-edit-td" >
							<input  name="fim_type_code"  id="fim_type_code" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							备注：
						</td>
						<td align="left" colspan="3" class="l-table-edit-td">
							<textarea name="note" id="note" rows="3" cols="100%" ></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input name="group_id" type="hidden" id="group_id" ltype="text" />
							<input name="hos_id" type="hidden" id="hos_id" ltype="text" />
							<input name="copy_code" type="hidden" id="copy_code" ltype="text" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div position="centerbottom" title="变更记录">
			<div id="maingrid" style="margin-top:10px;margin-left: 10px;"></div>
		</div>
    </div>
</body>
</html>