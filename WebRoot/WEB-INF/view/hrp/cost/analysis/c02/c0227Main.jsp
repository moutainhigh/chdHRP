<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收入构成分析明细表</title>
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
		           columns: [{ display: '收入构成分析明细表', columns: columns}], pageSize: 10, 
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0227.do',
		           width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,
		           allowHideColumn: false,
		           toolbar: { items: [{ text: '查询', click: query, icon: 'search'},
		                              { line:true },
		                              { text: '打印', click: print, icon: 'print'}
		           					]
		                },
		               width: '100%', height: '100%'
		           });


		           //$("#pageloading").hide();
           gridManager = $("#maingrid").ligerGetGridManager();
		}
		
	   //表头
	  var columns = [
               { display: '科室编码', name: 'dept_code', align : 'left' },
               { display: '科室名称', name: 'dept_name', align : 'left' }, 
               { display: '医疗收入', columns:
	               [
	                   { display: '医疗收入', name: 't_1',
		     				align : 'right',formatter:'###,##0.00',
		     				render : function(rowdata, rowindex, value) {
		     					return formatNumber(rowdata.t_1, 2, 1);
		     				}}, 
	                   { display: '药品收入', name: 't_2',
			     				align : 'right',formatter:'###,##0.00',
			     				render : function(rowdata, rowindex, value) {
			     					return formatNumber(rowdata.t_2, 2, 1);
			     				}},
	                   { display: '材料收入', name: 't_3',
				     				align : 'right',formatter:'###,##0.00',
				     				render : function(rowdata, rowindex, value) {
				     					return formatNumber(rowdata.t_3, 2, 1);
				     				} }
	               ]
               }
               ];

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
       
       //打印
        function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "收入构成分析明细表(医院)",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c02.C02Service",
 	   			method_name: "queryC0227Print",
 	   			bean_name: "c02Service",
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