<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多方位收益分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
    	  query();
      });
    
       function query(){
       	 grid.options.parms=[];
      	 grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	    grid.options.parms.push({name:'year_month_begin',value:'${year_month_begin}'}); 
       	    grid.options.parms.push({name:'year_month_end',value:'${year_month_end}'});
       	    grid.options.parms.push({name:'dept_id',value:'${dept_id}'=='null'?'':'${dept_id}'}); 
       	    grid.options.parms.push({name:'dept_no',value:'${dept_no}'=='null'?'':'${dept_no}'}); 
 	       	//加载查询条件
 	       grid.loadData(grid.where);
        }

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
					display: '医疗项目',
					name: 'chargename',
					align: 'left',
				}, {
					display: '收入',
					name: 'money',
					align: 'left',
					render: function(rowdata, rowindex, value) {
						  return formatNumber(rowdata.money, 2, 1);
						}
				}, {
					display: '数量',
					name: 'selfmoney',
					align: 'left',
					render: function(rowdata, rowindex, value) {
					  return formatNumber(rowdata.selfmoney, 2, 1);
					}
				},{
					display: '比率',
					name: 'proportion',
					align: 'left',
					render: function(rowdata, rowindex, value) {
					  return formatNumber(rowdata.proportion, 2, 1);
					}
				}
				],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryMultiIncomeByChargeType.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}
	    function loadDict(){};
	    
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="toptoolbar"></div>
	<div id="maingrid" style="margin: 0; padding: 0; border: none;"></div>
</body>
</html>