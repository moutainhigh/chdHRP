<%@ page language = "java" contentType = "text/html; charset = UTF-8" pageEncoding = "UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns = "http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8" />
<link href = "<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel = "stylesheet" type = "text/css" />
<link href = "<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel = "stylesheet" type = "text/css" />
<script src = "<%=path%>/lib/jquery/jquery-1.9.0.min.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/json2.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/hrp.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/ligerUI/js/core/base.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/ligerUI/js/ligerui.all.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/layer-v2.3/layer/layer.js" type = "text/javascript"></script>

<script type = "text/javascript">
//spread = document.getElementById('spreadFrame').contentWindow.GcSpread.Sheets.designer.wrapper.spread;

var spreadNS;
var parentFrameName = parent.$.etDialog.parentFrameName;
var parentWindow = parent.window[parentFrameName];
var dialog = parentWindow.dialog; //调用页面的dialog对象(ligerui对象)
var columns=parentWindow.grid.columns;
//var rowIndex=dialog.get("data").rowIndex;
var grid=parentWindow.grid;
    $(function (){
    	loadCenterTool();
    	$("#spreadFrame").css("height", $(window).height()-30);
    });

    var $tool;
	function loadCenterTool(){
		$tool = $("#centertoolbar").ligerToolBar({ items: [ 
			{ text: '选择文件',id:'openFileDialog.',icon:'uploadzip', click: openFileDialog},
			{ line:true },
			{ text: '导出Excel',id:'down.',icon:'down', click: exportExcel},
			{ line:true },
   	        {text: '确定', id:'save', icon:'save', click: mySave},   	   	    	
   	        { line:true },
	        { text: '关闭',id:'candle.',icon:'candle', click: myClose}
		]});
	} 
    
	function initSpreadTable() {
		var $spreadFrame = $(window.frames["spreadFrame"].document);
		var $content = $spreadFrame.find(".content .fill-spread-content");    
		$spreadFrame.find(".header").css("height",0);
		$content.css({ top: 2 });
		$content.parent().css({ height: $content.height() });
		var spread = spreadNS.designer.wrapper.spread;
		spread.options.newTabVisible = false;
		initSpreadData();
		spread.refresh();
    }
    
    //初始化值
    function initSpreadData(){
    	var spread = spreadNS.designer.wrapper.spread;
    	var sheet = spread.getSheet(0);
    	
    	sheet.suspendPaint();
    	//设置列
    	sheet.setColumnWidth(0, "100"); sheet.setText(0, 0, "年度"); 
    	sheet.setColumnWidth(1, "100"); sheet.setText(0, 1, "月份");
    	sheet.setColumnWidth(2, "100"); sheet.setText(0, 2, "指标名称");//修改
    	sheet.setColumnWidth(3, "150"); sheet.setText(0, 3, "执行数据");//修改
    	sheet.setColumnWidth(4, "250"); sheet.setText(0, 4, "说明");
    	
    	sheet.getRange(0, 0, 1, 3).hAlign(spreadNS.HorizontalAlign.center);
    	sheet.frozenRowCount(1);
    	sheet.clearSelection();
    	sheet.resumePaint();
    	spread.removeSheet(1);
    }
       
    function myClose(){
    	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
    }
   
    function openFileDialog(){
    	var spread = spreadNS.designer.wrapper.spread;
    	document.getElementById('spreadFrame').contentWindow.openFileDialog(spread,{isSavedWarning:false});
    }

    function exportExcel(){
    	document.getElementById('spreadFrame').contentWindow.exportExcel(spreadNS.designer.wrapper.spread,null);
    }
    
    //确定
    var row = null;
    var cell = null;
    function mySave(){
    	
     	var spread = spreadNS.designer.wrapper.spread;
    	var sheet = spread.getSheet(0);
    	
    	if(sheet.getText(0,0) != "年度"){
    		$.ligerDialog.error("导入模板不正确，请使用系统模板导入！");
			return false;
    	}
    	
     	var para = {
     			content:JSON.stringify(spread.toJSON()),
    			columns:JSON.stringify(columns),
    			//rowIndex:rowIndex
    	};
    	var loadIndex = layer.load(1);
    	ajaxJsonObjectBylayer("budgWorkHosMonthExecuteImport.do?isCheck=false", para, function(responseData){	
    		layer.close(loadIndex);
    		
    		if(row != null){
    			var whiteStyle = new spreadNS.Style();
    			whiteStyle.backColor = "white";
    			whiteStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderTop = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderRight = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
        		sheet.setStyle(row, cell, whiteStyle, spreadNS.SheetArea.viewport);
    		}

    	 	if(responseData.state == "false" ){
    	 		if(responseData.row_cell != undefined){
    	 			row = parseInt(responseData.row_cell.split("：")[0]) - 1;
        			cell = parseInt(responseData.row_cell.split("：")[1]) - 1;
        			var redStyle = new spreadNS.Style();
        			redStyle.backColor = "red";
        	        /* cellStyle.name = 'cellStyle';
        	        sheet.addNamedStyle(cellStyle); */
        	        redStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
        	        redStyle.borderTop = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
        	        redStyle.borderRight = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
        	        redStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
        	        sheet.setStyle(row, cell, redStyle, spreadNS.SheetArea.viewport);
        			sheet.setActiveCell(row, cell);
    	 		}
    			
    		}else if(responseData.state == "true"){
    			$.ligerDialog.success('导入成功！');
    			grid.refreshDataAndView();
    			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
    	        parent.$.etDialog.close(curIndex);
    			
    		} 
    		
		},layer,loadIndex); 
    }
    
    </script>

</head>

<body style = "padding: 0px; overflow: hidden;">
	<div id = "pageloading" class = "l-loading" style = "display: none"></div>
	
	<div id = "centertoolbar" ></div>
	<iframe src = "<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width = "100%" id = "spreadFrame" name = "spreadFrame"></iframe>
			
</body>
</html>
