<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医疗全成本构成分析明细表</title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script> 
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });
	       
      function loadDict(){
    	  //年月的初始化
    	  autodate("#year_month_begin","yyyyMM");
    	  autodate("#year_month_end","yyyyMM");
    	  $("#year_month_begin").ligerTextBox({width:120});
          $("#year_month_end").ligerTextBox({width:120});
      };
      
		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
		           columns: [
		                	{ display: '科室名称', name: 'dept_name',align:'left', width : 140 },
						{ display: '人员经费', name: 't_1',align:'right',formatter:'###,##0.00',
            	        	  render : function(rowdata, rowindex,
										value) {
							 	return formatNumber(rowdata.t_1,2,1);
							}, width : 90 },
						{ display: '卫生材料费', name: 't_2',align:'right',formatter:'###,##0.00',
                	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_2,2,1);
								}, width : 90 },
						{ display: '药品费', name: 't_3',align:'right',formatter:'###,##0.00',
	                  	        	  render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_3,2,1);
									},width : 90},
						{ display: '固定资产折旧费', name: 't_4',align:'right',formatter:'###,##0.00',
		                  	        	  render : function(rowdata, rowindex,
													value) {
										 	return formatNumber(rowdata.t_4,2,1);
										}, width : 90 },
						{ display: '无形资产摊销费', name: 't_5',align:'right',formatter:'###,##0.00',
			                  	        	  render : function(rowdata, rowindex,
														value) {
											 	return formatNumber(rowdata.t_5,2,1);
											}, width : 90 },
						{ display: '提取医疗风险基金', name: 't_6',align:'right',formatter:'###,##0.00',
				                  	        	  render : function(rowdata, rowindex,
															value) {
												 	return formatNumber(rowdata.t_6,2,1);
												}, width : 90 },
						{ display: '其他费用', name: 't_7',align:'right',formatter:'###,##0.00',
					                  	        	  render : function(rowdata, rowindex,
																value) {
													 	return formatNumber(rowdata.t_7,2,1);
													}, width : 90 },
						{ display: '合计', name: 't_8',align:'right',formatter:'###,##0.00',
						                  	        	  render : function(rowdata, rowindex,
																	value) {
														 	return formatNumber(rowdata.t_8,2,1);
														}, width : 90 }
		                     ], pageSize: 10, 
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0204.do?isCheck=false',
		           width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,
		           //allowHideColumn: false,
		           toolbar: { items: [{ text: '查询', click: query, icon: 'query'},
		                              { line:true },
		                              /* { text: '生成', click: creat_data, icon: 'creat_data'},
		                              { line:true }, */
		                              { text: '打印', click: print, icon: 'print'}
		           					]
		                },
		               width: '100%', height: '100%'
		           });


		           //$("#pageloading").hide();
           gridManager = $("#maingrid").ligerGetGridManager();
		}
		

       //查询
       function query(){
    	   grid.options.parms=[];
      	   grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	   grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
       	   grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
       	   
	       	//加载查询条件
	       	grid.loadData(grid.where);
       };
       
       //生成
       function creat_data(){};
       
 
     function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		           {"cell":0,"value":"统计年月："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"} 
 	    	]};
 	       var printPara={
 	      		title: "临床服务类科室医疗全成本构成分析明细表",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.AnalysisService", 
 	   			method_name: "queryAnalysisC0204print",
 	   			bean_name: "analysisService", 
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);	
	

   		
    }
</script>
</head>
<body>
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	     <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	  
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
	  <div class="sample-turtorial" style="display: none"></div>
</body>
</html>