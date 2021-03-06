<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery/jquery-smartMenu-min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/js/vouchCheck.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
 
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/jquery/smartMenu.css"/>

<!-- CSS dependencies -->
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.date.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/fontawesome/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.css"/>

<!-- Sensei Grid CSS -->
<link rel="stylesheet" type="text/css"  href="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.css"/>

<script src="<%=path%>/lib/hrp/acc/superVouch/grid/lodash/lodash.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.date.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/js/bootstrap.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/isInViewport/lib/isInViewport.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/typeahead.js/dist/typeahead.jquery.js"></script>

<!-- Sensei Grid JS -->
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.js?v=2020413"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-editors.js"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-row-actions.js"></script>


<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var summary=dialog!=null?dialog.get("data").summary:"";
var pGrid = dialog!=null?dialog.get("data").grid:null;
var openPage=dialog!=null?dialog.get("data").page:null;
var checkJson='${checkJson}';//核算类型
var subjAttr;//科目属性
var jsonKey;
var title;

	$(function() {
		if(existsChromeVer42(44)){
			//谷歌43以下版本滚动条的宽度多1px
			$("#vouchCheckDiv").css("padding-right","1px");
		}
		
		subjAttr=parent.frameObj.getSubjAttr($("#subj_code").val());
		
		if(dialog){
			title=dialog.options.title;
			$("#checkBlance").hide();
		}
		var loadIndex = layer.load(1);
		jsonKey=$("#detail_id").val();
		if(!jsonKey){
			jsonKey=$("#uniqueNumber").val();
		}
		loadDict();
		loadVouchCheckTable();
		$(':button').ligerButton({width:60});
		$("#vouchCheckDiv").css("height", $(window).height()-200);
		//alert(JSON.stringify(parent.vouchCheckMap));
		//index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		  //  console.log($("#vouchCheckDiv").width())
		  //  console.log($(window).width());
		
		//快捷键处理
		BindKeyBoard([
			{keyCode:112,fn:function(){}},//F1
			{keyCode:113,fn:function(){myDel()}},//删除F2
			{keyCode:114,fn:function(){changeDire()}},//转换借贷方F3
			{keyCode:115,fn:function(){}},//F4
			{keyCode:116,fn:function(){}},//F5
			{keyCode:117,fn:function(){}},//F6
			{keyCode:118,fn:function(){}},//F7
			{keyCode:119,fn:function(){}},//F8
			{keyCode:120,fn:function(){}},//F9
			{keyCode:121,fn:function(){}},//F10
			{keyCode:122,fn:function(){myImport()}},//导入F11
			{keyCode:123,fn:function(){mySave()}},//保存F12
			{keyCode:27,fn:function(){myClose()}}//关闭ESC
		]);
		
		//金额运算 
		evelMoney();
		
		layer.close(loadIndex);
		$("body").scrollTop(0);
		
	});
	
	function changeDire(){
		if(is_cash_show==1 || $("input[name='controllerButton']").attr("disabled")=="disabled"){		
			//按钮置灰状态下返回
			return;
		}
		
		var subj_dire = liger.get("subj_dire").getValue();
		if(subj_dire == 0){
			liger.get("subj_dire").setValue("1");
			liger.get("subj_dire").setText("贷");
		}else{
			liger.get("subj_dire").setValue("0");
			liger.get("subj_dire").setText("借");
		}
		
		//设置焦点
		var $activeCell=grid.getActiveCell();
		if(!$activeCell[0]){
			return;
		}
		
        grid.setActiveCell($activeCell);
        //grid.$el.focus();
        grid.editCell();
	}

	function myDel(){		
		if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
			//按钮置灰状态下返回
			return;
		}
		
		if(grid.getSelectedRows().length>1){
			$.ligerDialog.confirm("确定要删除吗？", function(yes) {
				if(yes) {
					
					grid.removeActiveRow();
					sumMoney();//合计金额
				}
			});
		}else{
			
			grid.removeActiveRow();
			sumMoney();//合计金额
		}
		
		if(grid.getRows().length==0){
			grid.assureEmptyRow();
		}

	}
	
	function mySave(){
		/* var sumDebitCredit=dialog.get("data").sumDebitCredit;
		console.log(sumDebitCredit)
		return; */
		if($("input[name='controllerButton']").attr("disabled")=="disabled"){		
			//按钮置灰状态下返回
			return;
		}
		
		var rowData=[];
		var msg="";
		var checkItemVal="";
		var summary="";
		var cash_dire = liger.get("subj_dire").getValue();
		var cash_data_dire=0;
		// grid.removeRow($row);//删除空数据
		//grid.editorBlur(grid.getActiveCell());//光标离开保存数据
		grid.saveEditor(true);//光标离开保存数据
		$.each(grid.getGridData(),function (n,obj) {
			 
			// console.log("value.cells[0].firstChild.type:",value.cells[0]);//value.cells[0].firstChild.type
			
			/* if(grid.getCellDataByKey(n, "money")==""){ 
				grid.removeRow(grid.getRowByIndex(n));//删除空数据
				return true;
			} */
			
			/*var $row=grid.getRowByIndex(n);
			 if($row.find(">td.selectable").html().indexOf("checkbox")==-1){
				return true;
			} */
			//金额都为空不处理 
			if(obj.money=="")obj.money=0;
			if(parseFloat(obj.money)==0){
				return true;
			}
			
			//验证不能为空。
			$.each(obj,function (col,val) {
				if(col=="paper_type_code" || col=="check_no" || col=="con_no"  || col=="business_no"){
		        	 //票据类型、票据号、合同编号、单据号不检查
		        	 return;
		        }
				 
				if(col=="summary" && val==""){
					msg="摘要，不能为空！";
					return false;
				}
				if(col=="money" && (isNaN(val.replace(/\,/g,"")) || val=="")){
					msg="金额，不能为空！";
					return false;
				}
				if(parent.paraList["026"]==1 && col=="cash_name" && val==""){
					msg="现金流量，不能为空！";
					return false;
				}
				/*if(col=="pay_name" && val==""){
					msg="结算方式不能为空！";
					return false;
				}*/
				
				//取第一行第一个辅助核算的内容
				if(col.indexOf("check")!=-1 && checkItemVal==""){
					checkItemVal=val;
				}
				
				if(col.indexOf("summary")!=-1 && summary==""){
					summary=val;
				}
				
				if(col.indexOf("check")!=-1 && (val.replace(/(^\s*)|(\s*$)/g, "")=="" || val=="@")){
					
					$.each(grid.columns,function (cn,column) {
						if(column.name==col){
							msg=column.display+"，不能为空！";
							return false;
						}
					});
					return false;
				}
					
			});
			//obj.subj_code=$("#subj_code").val();//不需要存科目编码
			
			//如果存在现金流量则判断
			if(obj.cash_item_id){
				var attr=getCashAttr(obj.cash_item_id);
				if(n>0 && cash_data_dire!=attr.type){
					msg=attr.name+"，与第一个项目方向不一致！";
					return false;
				 }
				
				 if(n==0){
					 cash_data_dire=attr.type;	 
				 }
			} 
			
			rowData.push(obj);
		 });
		
		if(msg!=""){
			$.ligerDialog.error(msg); 
			return false;
		}
		
		if(rowData.length==0){
			$.ligerDialog.error("数据不能为空！");
			//layer.alert("数据不能为空.");
			return false;
		}
		
		
		/*json格式：
		[{"id":1,"cid":"329758688745608","summary":"测试","check1_name":"AAAAAAAAAA 公用部门","check1":"1415@588","money":"200.00","subj_code":"5001010101"},{"id":2,"cid":"","summary":"测试","check1_name":"1008 泌尿外科门诊","check1":"1128@301","money":"300.00","subj_code":"5001010101"}]
		*/
		//console.log(JSON.stringify(rowData));
		if(openPage=="vouchKind"){
			parent.kindCheckJson[jsonKey] = rowData;
		}else{
			parent.vouchCheckJson[jsonKey] = rowData;
		}
		
		var debit="0.00";
		var credit="0.00";
		if(liger.get("subj_dire").getValue()==0){
			debit=$("#sum_money").text().replace(/,/g,"");//替换千分符号;
		}else{
			credit=$("#sum_money").text().replace(/,/g,"");//替换千分符号;
		}
		var parm={
			flag: "save",
			rowNumber: $("#rowNumber").val(),
			cellNumber: $("#cellNumber").val(),
			subj_dire: liger.get("subj_dire").getValue(),
			debit: debit,
			credit: credit,
			checkItemVal: checkItemVal,//第一条辅助核算的值
			summary: summary,
			subj_code: $("#subj_code").val(),
			checkJson: checkJson,
			is_cash: is_cash_show,
			is_bill: subjAttr.is_bill
		}
		if(openPage=="vouchKind"){
			parent.callSaveKindCheck(parm);
		}else{
			parent.callSaveVouchCheck(parm);
		}
		setTimeout(function() {
			dialog.close();
		}, 50);
	}

	
	function myClose() {
		//frameElement.dialog.close();
		//parent.layer.close(index);
		if ((initRow == 1 && initMoney != parseFloat($("#sum_money").text())) || (initRow > 1 && (initRow != grid
				.getRows().length || initMoney != parseFloat($("#sum_money")
				.text())))) {
			if (!confirm("是否关闭？")) {
				return;
			}
		}
		var parm={
				flag: "close",
				rowNumber: $("#rowNumber").val(),
				cellNumber: $("#cellNumber").val(),
		};
		if(openPage=="vouchKind"){
			parent.callSaveKindCheck(parm);
		}else{
			parent.callSaveVouchCheck(parm);
		}
	 	setTimeout(function() {
			/* if (dialog != null) {
				pGrid.editCell();
			} */
			dialog.close();
		}, 50); 
	}

	function myImport() {
		if ($("input[name='controllerButton']").attr("disabled") == "disabled") {
			//按钮置灰状态下返回
			return;
		}
		
		var para = {
			url : 'hrp/acc/accvouch/superVouch/importCheckPage.do?isCheck=false',
			title : '导入辅助核算',
			width : 0,
			height : 0,
			isShowMin : false,
			isModal : true,
			isDrag: false,
			data : {
				columns : grid.columns,
				grid : grid,
				rowIndex : rowIndex,
				out_code:subjAttr.out_code
			}
		};
		parent.parent.openDialog(para);
		//parent.$.ligerDialog.open({url : 'importCheckPage.do?isCheck=false',
		//data:{columns:grid.columns,rowIndex:rowIndex,grid:grid}, height:$(parent.window).height(),width: $(parent.window).width()+10, title:'导入辅助核算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
	}
	
	function myPrint(){
		
		/* if($("#vouch_id").val()==""){
			$.ligerDialog.error("请先保存凭证！");
			return;
		} */
  		var girdData=grid.getGridData();
    	if(girdData.lengh==0){
    		$.ligerDialog.error("没有要打印的数据！");
			return;
    	}
   		
    	var columnObj={};
    	var columnArray=[];
    	var dataArray=[];
    	
    	//表头
    	var columnindex=0;
    	$.each(columns,function(i,obj){
    		if(obj.hide){
    			return true;
    		}
    		columnArray.push({columnindex: columnindex,name: obj.name,display: obj.display,align: obj.align,width: obj.width,level: 1});
    		columnindex++;
    	});
    	
    	//数据
    	$.each(girdData,function(d,dd){
    		var dataObj={};
    		var isEmpty=true;
    		$.each(columns,function(i,obj){
    			if(obj.hide){
    				return true;
    			}
    			if(dd[obj.name]!=""){
    				isEmpty=false;
    				dataObj[obj.name]=dd[obj.name];
    			}
    			
    		});
    		
    		if(!isEmpty){
    			dataArray.push(dataObj);
    		}
    		
		});
    	
    	if(dataArray.length==0){
    		$.ligerDialog.error("没有要打印的数据！");
			return;
    	}
    	dataArray.push({summary: "合计",money: $("#sum_money").text()});
    	
    	columnObj["rows"]=columnArray;
    	columnObj["maxcolumnindex"]=columnArray.length-1;
    	columnObj["maxlevel"]=1;
    	
    	var heads={
        		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
        		  "rows": [
  			  	  {"cell":0,"value":$("#vouch_type_code",parent.document).val()+"-"+$("#vouch_no",parent.document).val()},
    	          {"cell":1,"value":"凭证日期："+$("#vouch_date",parent.document).val()}
        		  ]
        	};
    	//console.log(columnObj)
    	//console.log(dataArray)
    	var subjAttr=parent.frameObj.getSubjAttr($("#subj_code").val());
    	var subjName=subjAttr.name;
    	subjName=subjName.substring(subjName.lastIndexOf("-")+1,subjName.length);
    	
   		var printPara={
   			rowCount:1,
   			title:subjName+""+$("#subj_code").val(),
   			columns: JSON.stringify(columnObj),//表头
   			data: JSON.stringify(dataArray),
   			class_name: "com.chd.hrp.acc.service.vouch.SuperVouchService",
			method_name: "queryCheckPrintByDid",
			bean_name: "superVouchService",
			//vouch_id: $("#vouch_id").val(),
			//vouch_detail_id: $("#detail_id").val(),
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   		};
   		
    	officeGridPrint(printPara);
   		
	}
</script>
</head>

<body style="padding: 0px;overflow-x:auto; overflow-y:scroll;"  onload="">

	<input  type="hidden" id="uniqueNumber" value="${id}"></input>
	<input  type="hidden" id="rowNumber" value="${rowNumber}"></input>
	<input  type="hidden" id="cellNumber" value="${cellNumber}"></input>
	<input  type="hidden" id="vouch_id" value="${vouch_id}"></input>
	<input  type="hidden" id="state" value="${state}"></input>
	<input  type="hidden" id="detail_id" value="${detail_id}"></input>
	<input  type="hidden" id="subj_code" value="${subj_code}"></input>
	<input  type="hidden" id="column_name" value="${coulmn_name}"></input>
	<input  type="hidden" id="debit" value="${debit}"></input>
	<input  type="hidden" id="credit" value="${credit}"></input>

	<div style="margin:0;position:fixed;z-index:3;top:0px;width:100%;background:white;padding:0px 0px;">
		<table id="headTable" cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="height:25px;">
			<tr>
				<td align="left" width="80px">
					<input type="button" name="controllerButton" value=" 删除 F2" onclick="myDel();"/>
				</td>
				<td align="right" class="l-table-edit-td" width="90px">方向 F3：</td>
				<td align="left" class="l-table-edit-td" width="80px"><input name="subj_dire"  type="text"  id="subj_dire"  /></td>
				<td align="left" class="l-table-edit-td" width="200px">合计金额：<span id="sum_money">0.00</span></td>
				<td align="right">
				<input type="button" value=" 打印 F10" onclick="myPrint();"/>
				<input type="button" name="controllerButton" value=" 导入 F11" onclick="myImport();"/>
				<input type="button" value=" 关闭 ESC" onclick="myClose();"/>
				<input type="button" name="controllerButton" value=" 确定 F12" onclick="mySave();"/>
				</td>
			</tr>
		</table>
		<div id="checkBlance" style="background:#C8EAFA;font-weight:bold">辅助核算</div>
	</div>
	<div id="vouchCheckDiv" class="sensei-grid sensei-grid-default" style="position:absolute;top:25px;"></div>
	 
</body>
</html>
