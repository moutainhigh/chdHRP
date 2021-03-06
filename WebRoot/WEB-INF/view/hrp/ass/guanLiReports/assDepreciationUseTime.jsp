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
    $(function (){
		loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
      
        $("#ass_nature").ligerTextBox({width:150});
        $("#depre_year_month").ligerTextBox({width:150});
        $("#ass_card_no").ligerTextBox({ width:160});

    });
 
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
     
		grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
		grid.options.parms.push({name:'depre_year_month',value:$("#depre_year_month").val()}); 
		grid.options.parms.push({name:'ass_card_no',value:liger.get("ass_card_no").getValue()}); 
		 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
    }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '折旧年度', name: 'depre_year', align: 'left',width:100},
                     { display: '折旧期间', name: 'depre_month', align: 'left',width:100},
                     { display: '资产卡片号', name: 'ass_card_no', align: 'left'},
                     { display: '本期折旧', name: 'now_depre_amount', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                    			return formatNumber(
                    			rowdata.now_depre_amount == null ? 0: rowdata.now_depre_amount,'${ass_05005 }',1);
                    			},formatter:'###,##0.00'
 
                     },
                     { display: '累计折旧月份', name: 'add_depre_month', align: 'left'},
                     { display: '累计折旧金额', name: 'add_depre_amount', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                    			return formatNumber(
                    			rowdata.add_depre_amount == null ? 0: rowdata.add_depre_amount,'${ass_05005 }',1);
                    			},formatter:'###,##0.00'

                     }
                    
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../guanLiReports/queryAssDepreciationUseTime.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                             { text: '查   询', id:'search', click: query,icon:'search' },
 						     { line:true },
 						     { text: '打   印', id:'print', click: printDate,icon:'print' },
 						     { line:true },
 						    //{ text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
                     ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
         //字典下拉框
		$("#ass_nature").ligerComboBox({
          	url: '../queryAssNaturs.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
        	onSelected :function(id,text){ 
          		query();
          	}
 		  });
		$("#ass_card_no").ligerTextBox({width:160});

    } 
    //键盘事件
	function loadHotkeys() {

	}
    
    //导出
	function exportExcel(){
		
	}
	
	//打印数据
 	function printDate(){
 		if( grid.getData().length==0  ){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		/* 
 		var dataprint = gridManager.getCheckedRows();
 		$.each(dataprint, function() {
			ass_card_no = this.ass_card_no;
		});
 		 */
 		
 		
 		var time=new Date();
    	var date=$("#depre_year_month").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"资产性质："},
    	          {"cell":1,"value":liger.get("ass_nature").getText().split(" ")[1]},
    	          {"cell":4,"value":"报表日期:"},
  				  {"cell":5,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":4,"value":"制表人:"},
    				{"cell":5,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "已提折旧使用期限报",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssDepreciationUseTimeService",
 				method_name: "queryAssDepreciationUseTimePrint",
 				bean_name: "assDepreciationUseTimeService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年月：</td>
            <td align="left" class="l-table-edit-td"><input name="depre_year_month" type="text" id="depre_year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
       		<td align="left"></td>
       		<td align="right" align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片编码:</td>
       		<td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no" ltype="text"/></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
