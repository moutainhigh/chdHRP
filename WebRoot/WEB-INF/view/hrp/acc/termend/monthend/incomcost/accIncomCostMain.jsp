<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%  
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var subjGrid;
	var gridManager = null;
	var year_month = '${acc_year_month}';
	var is_exec_budg = '${is_exec_budg}';
	var userUpdateStr;
	
	$(function() {
		$("#layout1").ligerLayout({ leftWidth: 265, centerBottomHeight: $(window).height()-250});
		$("#navtab1").ligerTab({contextmenu: false});
		loadForm();
		loadDict();
		loadHead(null); //加载数据
		loadSubjGrid(null);
		query();
		if(is_exec_budg == "2"){
			$("#budgNode").show();
		}
		if(${copy_nature} == "02"){
			$("#subjCode2").hide();
		}
	});
	
	//查询模板信息
	function query() {
		 
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		/* grid.options.parms.push({
			name : 'acc_year',
			value : liger.get("acc_year_month").getValue().split(".")[0]
		}); */
		grid.options.parms.push({
			name : 'acc_month',
			value : liger.get("acc_year_month").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'template_type_code',
			value : "Z006"
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载模板主表信息
	function queryMain(rowdata) {
		rowdata = JSON.parse(rowdata);
		 
		$("#template_id").val(rowdata.template_id);
		$("#template_name").val(rowdata.template_name);
		if(rowdata.vouch_type_code){
			liger.get("vouch_type_code").setValue(rowdata.vouch_type_code);
			liger.get("vouch_type_code").setText(rowdata.vouch_type_name);
		}
		$("#summary").val(rowdata.summary);
		if(rowdata.source_id){
			liger.get("source_id").setValue(rowdata.source_code+"."+rowdata.source_id);
			liger.get("source_id").setText(rowdata.source_code+" "+rowdata.source_name);
		}else{
			liger.get("source_id").setValue("");
			liger.get("source_id").setText("");
		}
		if(rowdata.credit_subj_code1){
			liger.get("credit_subj_code1").setValue(rowdata.credit_subj_code1);
			liger.get("credit_subj_code1").setText(rowdata.credit_subj_code1+" "+rowdata.credit_subj_name1);
		}else{
			liger.get("credit_subj_code1").setValue("");
			liger.get("credit_subj_code1").setText("");
		}
		if(rowdata.credit_subj_code2){
			liger.get("credit_subj_code2").setValue(rowdata.credit_subj_code2);
			liger.get("credit_subj_code2").setText(rowdata.credit_subj_code2+" "+rowdata.credit_subj_name2);
		}else{
			liger.get("credit_subj_code2").setValue("");
			liger.get("credit_subj_code2").setText("");
		}

		queryDetail();
	}
	
	//查询模板明细信息
	function queryDetail() {
		subjGrid.options.parms = [];
		subjGrid.options.newPage = 1;
		//根据表字段进行添加查询条件
		subjGrid.options.parms.push({
			name : 'template_id',
			value : $("#template_id").val()
		});
		//加载查询条件
		subjGrid.loadData(subjGrid.where);
	}
	
	function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
		//$("form").ligerForm();
	}  
	//模板Grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证模板', name : 'template_name', align : 'left', width: '120', 
				render : function(rowdata, rowindex,value) {
					return '<a href=javascript:queryMain(\''+ JSON.stringify(rowdata) +'\')>'+rowdata.template_name+'</a>';
				}
			}, {
				display : '凭证类型', name : 'vouch_type_name', align : 'left', width: '80'
			} ],
			dataAction : 'server', dataType : 'server', width : '100%', height : $(window).height()-1,
			usePager : false, url : 'queryAccIncomCost.do', checkbox : true, isSingleCheck: true,  
			rownumbers : true, delayLoad: true, selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '模板添加', id : 'add', click : itemclick, icon : 'add'
				}, {
					line : true
				}, {
					text : '模板删除', id : 'delete', click : itemclick, icon : 'delete'
				} ]
			},
			onDblClickRow : function(rowdata, rowindex, value) {
				queryMain(rowdata);
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$("#template_id").val("");
				$("#template_name").val("");
				$("#summary").val("");
				liger.get("credit_subj_code1").clear();
				liger.get("credit_subj_code2").clear();
				subjGrid.deleteAllRows();
				return;
			case "delete":
				var data = grid.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.warn('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.template_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccIncomCost.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
									$("#template_id").val("");
								}
							});
						}
					});
				}
				return;
			}
		}
	}	
	
	//科目Grid
	function loadSubjGrid() {
		//subjGrid = $("#subjgrid").css({"margin-left" : 40}).ligerGrid({
		subjGrid = $("#subjgrid").ligerGrid({
			columns : [ {
				display : '科目编码', name : 'subj_code', align : 'left', width: "120"
			}, {
				display : '科目名称', name : 'subj_name_all', align : 'left'
			}],
			dataAction : 'server', dataType : 'server', width : '100%', height : '100%',
			usePager : false, url : '../../queryAccTermendTemplateDetail.do?isCheck=false',
			checkbox : true, rownumbers : false, delayLoad: true, selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '科目设置', id : 'add', click : setSubj, icon : 'add'
				}, {
					line : true
				}, {
					text : '科目删除', id : 'delete', click : removeSubj, icon : 'delete'
				} ] 
			},
		});
	}

	//科目删除
	function removeSubj(){
		//subjGrid.deleteSelectedRow();
		
		var data = subjGrid.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.template_id   +"@"+ 
						this.template_detail_id
				) 
				
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("../incomcost/deleteAccTermendTemplateDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		//alert($("#template_id").val())
                		console.log(responseData)
                		if(responseData.state=="true"){
                			 
                			queryDetail();
                		}
                	});
            	}
            }); 
        }
        
	}

	//字典下拉框
	function loadDict() {
		$("#acc_year_month").ligerComboBox({
	      	url: '../../../queryYearMonth.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 90,
	      	autocomplete: true,
	      	width: 90,
	      	onChangeValue: function(value){
	      		if(grid){
		      		query();
	      		}
	      	}
		});
		liger.get("acc_year_month").setValue(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());
		liger.get("acc_year_month").setText(year_month.substring(0,4)+"."+year_month.substring(4,6).toString());
		
		var paras;
		if(${copy_nature} == "02"){
			autocomplete("#credit_subj_code1", "querySubjAllFirm.do?isCheck=false", "id", "text", true, true, null, false, "", "360", "", subjWidth);
		}else{
			paras ={
			 	subj_type_code : '03',
			 	is_last : "1"
			};
			autocomplete("#credit_subj_code1", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		}

		paras ={
		 	subj_type_code : '08',
		 	is_last : "1"
		};

		autocomplete("#credit_subj_code2", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "360", "", subjWidth);
		autocomplete("#vouch_type_code", "../../../queryVouchType.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#source_id", "../../../../sys/querySourceDict.do?isCheck=false", "id", "text", true, true, "", false, "", "185");
		
		$("#template_name").ligerTextBox({width:360,disabled: false });
		$("#vouch_type_code").ligerTextBox({width:90,disabled: false });
		$("#summary").ligerTextBox({width:360,disabled: false });
		
		//格式化按钮
		$("#but_add").ligerButton({click: createVouch, width:90});
		$("#but_vouch").ligerButton({click: showVhouchList, width:90});
		$("#but_save").ligerButton({click: saveTemplate, width:90});
	}

	//保存模板   来进行更新主表
	function saveTemplate(){
		if($("form").valid()){
			if(getSubjData() == "[]"){
				$.ligerDialog.warn('请选择科目');
				return false;
			}
			
			var credit_subj_code1 = liger.get("credit_subj_code1").getValue();
			var credit_subj_code2 = liger.get("credit_subj_code2").getValue();
			if(!credit_subj_code1 && !credit_subj_code2){
				$.ligerDialog.warn('财务结余科目与预算结余科目不能同时为空！');
				return false;
			}
			
			var formPara ={
				template_id : $("#template_id").val(),
				template_name : $("#template_name").val(),
				template_type_code : "Z006",
				template_type_name : "收支结转",
				vouch_type_code : liger.get("vouch_type_code").getValue(),
				summary : $("#summary").val(),
				source_id: liger.get("source_id").getValue() ? liger.get("source_id").getValue().split(".")[1] : "", 
				credit_subj_code1 : liger.get("credit_subj_code1").getValue(),
				credit_subj_code2 : liger.get("credit_subj_code2").getValue(),
				detailData : getSubjData()
			};
			ajaxJsonObjectByUrl("saveAccIncomCost.do",formPara,function (responseData){
        		if(responseData.state=="true"){
        			$("#template_id").val(responseData.template_id);
        			query();
        		}
        	});
		}
	}
	
	//设置科目   通过设置科目 点击确定来进行保存主表和明细表
	var subjList;
	function setSubj(){
		if ($("#template_name").val() == "" ){  
			$.ligerDialog.error('模板名称不能为空！');
			return false ;
		}
		 
		if ($("#summary").val() == "" ){
			$.ligerDialog.error('摘要不能为空！');
			return false ;
		}

		parent.$.ligerDialog.open({ 
			title: '转出科目设置', 
			width: $(window).width() - 20,  
			height: $(window).height() - 50, 
			url: 'hrp/acc/termend/monthend/incomcost/accIncomCostSubjPage.do?isCheck=false&template_id='+$("#template_id").val(), 
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			buttons: [
				{ text: '确定', onclick: writeSubj, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: closeDialog }
			]
		}); 
	}
	//写入选择的科目  进行保存操作
	function writeSubj(item, dialog){ 
		var rows = dialog.frame.getSelectRows();
        if (!rows){
            alert('请选择行!');
            return;
        }  
		//写入数据 
    	subjGrid.addRows(rows); 
		//保存数据 
    	saveTemplate();
        dialog.close();
    }	
	//关闭选择科目窗口
    function closeDialog(item, dialog){ 
        dialog.close();
    }
	//获取科目数据
    function getSubjData(){
        var manager = $("#subjgrid").ligerGetGridManager();
        var data = manager.getData();
        return JSON.stringify(data);
    }

	//生成凭证
	function createVouch(){
		var rows = gridManager.getCheckedRows();
		if (rows.length == 0) {
			$.ligerDialog.warn('请选择行');
            return;
        } 
		//如选多个模板则循环生成凭证
		$.ligerDialog.confirm('确定生成凭证?', function (yes){
	        if(yes){
	        		var para;
			        for (var i = 0; i < rows.length; i++){
			        	template_ids = rows[i].template_id + ",";
			        	para={
			    			acc_year : liger.get("acc_year_month").getValue().split(".")[0],
			    			acc_month : liger.get("acc_year_month").getValue().split(".")[1],
			        		vouch_type_code : rows[i].vouch_type_code,
			        		template_id : rows[i].template_id
			        	}
			
	        		var loadIndex = layer.load(1);
	        		ajaxJsonObjectBylayer("addAccIncomCostVouch.do",para,function(responseData){	
	        			//console.log(responseData);
	        			layer.close(loadIndex);
	        			
	        			var paraVouch={
	        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
	        				title:'会计凭证',
	        				width:0,
	        				height:0,
	        				isShowMin:true,
	        				isModal:true,
	        				data:{auto_id:responseData.vouch_id, busi_log_table:'ACC_BUSI_LOG_ZZ', busi_type_code:'Z006',busi_no:para.template_id}
	        			};
	        			parent.openDialog(paraVouch);
	          		},layer,loadIndex);
	        	}
	        
        	} 
        });
	}
	//凭证维护
	function showVhouchList(){
		parent.$.ligerDialog.open({ 
			title: '凭证维护', 
			width: $(window).width() - 20, 
			height: $(window).height() - 50, 
			url: 'hrp/acc/termend/accTermendTemplateVouchPage.do?isCheck=false&template_type_code=Z006&acc_year='+liger.get("acc_year_month").getValue().split(".")[0]+'&acc_month='+liger.get("acc_year_month").getValue().split(".")[1],
			model: true, showMax: false, showToggle: false, showMin: false, isResize: true
		}); 
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<div id="layout1" style="width:100%;margin:0; padding:0;">
		<div id="maingrid" position="left" title="模板列表"></div>
		<div position="center" title="模板信息">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
					<td align="left" class="l-table-edit-td"><input name="acc_year_month"
						type="text" id="acc_year_month" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
						<input type="button" id="but_add" accessKey="T"  value="收支结转(T)"/>
						&nbsp;&nbsp;
						<input type="button" id="but_vouch" accessKey="Z"  value="凭证维护(Z)"/>
						&nbsp;&nbsp;
						<input type="button" id="but_save" accessKey="S"  value="模板保存(S)"/>
					</td>
				</tr>
			</table>
			<hr style="border:1px solid #A3C0E8; "/>
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
						<td align="right" class="l-table-edit-td">
							<span style="color:red">*</span>模板名称：
						</td>
						<td align="left" class="l-table-edit-td"  colspan="3">
							<input name="template_id" type="hidden" id="template_id" ltype="text" value="${template_id }" validate="{required:false,maxlength:20}" />
							<input name="template_name" type="text" id="template_name" ltype="text" required="true" validate="{required:true,maxlength:40}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							<span style="color:red">*</span>凭证类型：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" required="true" validate="{required:true}" />
						</td>
						
						<td align="right" class="l-table-edit-td">
							&nbsp;&nbsp;资金来源：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="source_id" type="text" id="source_id" ltype="text" />
						</td>
					</tr>
					<tr>
						<td align="left" class="l-table-edit-td" >
						</td>
						<td align="right" colspan="3" class="l-table-edit-td">
							<span style="color:red">注：选择后会根据辅助核算资金来源过滤科目数据</span>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">*</span>摘要：
						</td>
						<td align="left" colspan="3" class="l-table-edit-td" >
							<input name="summary" type="text" id="summary" ltype="text" required="true" validate="{required:true,maxlength:80}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							<!-- <span style="color:red">*</span> -->财务结余科目：
						</td>
						<td align="left" colspan="3" class="l-table-edit-td" >
							<input name="credit_subj_code1" type="text" id="credit_subj_code1" ltype="text" />
						</td>
					</tr>
					<tr id="subjCode2">
						<td align="right" class="l-table-edit-td" >
							<!-- <span style="color:red">*</span> -->预算结余科目：
						</td>
						<td align="left" colspan="3" class="l-table-edit-td" >
							<input name="credit_subj_code2" type="text" id="credit_subj_code2" ltype="text"  />
						</td>
					</tr>
					<tr id="budgNode" style="display: none;">
						<td align="right" class="l-table-edit-td" >
							<span style="color:red">注：</span>
						</td>
						<td align="left" colspan="3" class="l-table-edit-td" >
							<span style="color:red">只有在年末时才生成预算科目相关凭证</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div position="centerbottom" id="navtab1" style="border:1px solid #A3C0E8; ">
			<div tabid="subjDiv" title="转出科目" lselected="true" >
				<div id="subjgrid" style="margin-top: 5px;"></div>
			</div>
		</div>
	</div>
</body>
</html>
