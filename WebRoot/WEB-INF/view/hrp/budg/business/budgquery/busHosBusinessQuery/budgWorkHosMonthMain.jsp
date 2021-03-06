<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    //设置单元格打印格式
    var printData={
   		sum_year : function(value){ //一月份的预算值
			return formatNumber(value, 2, 1);
		}, 
   		month1 : function(value){ //一月份的预算值
			return formatNumber(value, 2, 1);
		},
		month2 : function(value){ //二月份的预算值
			return formatNumber(value, 2, 1);
		},
		month3 : function(value){ //三月份的预算值
			return formatNumber(value, 2, 1);
		},
		month4 : function(value){ //四月份的预算值
			return formatNumber(value, 2, 1);
		},
		month5 : function(value){ //五月份的预算值
			return formatNumber(value, 2, 1);
		},
		month6 : function(value){ //六月份的预算值
			return formatNumber(value, 2, 1);
		},
		month7 : function(value){ //七月份的预算值
			return formatNumber(value, 2, 1);
		},
		month8 : function(value){ //八月份的预算值
			return formatNumber(value, 2, 1);
		},
		month9 : function(value){ //九月份的预算值
			return formatNumber(value, 2, 1);
		},
		month10 : function(value){ //十月份的预算值
			return formatNumber(value, 2, 1);
		},
		month11 : function(value){ //十一月份的预算值
			return formatNumber(value, 2, 1);
		},
		month12 : function(value){ //十二月份的预算值
			return formatNumber(value, 2, 1);
		}
	};
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
    var year_input,index_code_select;
    
    function init() {
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	initColumns();
                }, 10);
            },
            defaultDate: true
        });

		
		index_code_select = $("#index_code_select").etSelect({
			defaultValue:"none",
			url:"../../queryBudgIndexDict.do?isCheck=false",
			onChange:query
		});
	}
    
	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	};
    
    //查询
    function  query(){
    	if( !year_input.getValue()){
       		$.etDialog.error('预算年度不能为空');
       		return false ;
       	}
    	var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
		];
		//加载查询条件
		grid.loadData(parms, 'queryBudgWorkHosMonth.do?isCheck=false');
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                { display: '年度', name: 'year',width:90
 					},
                { display: '指标编码', name: 'index_code',width:90
 					},
				{ display: '指标名称', name: 'index_name',width:100
				 	},
				/*{ display: '项目', name: 'item',width:90
					},*/
			    { display: '本年合计', name: 'sum_year', align: 'right',width:100,
				 		 render:function(ui){
				 			 if(ui.rowData.b_no == 2){
			                 		 	return "<a href=javascript:openUpdateSum('"+
			                 		 	ui.rowData.year+"!"+ui.rowData.index_code+"!"+ui.rowData.sum_year+ "')>"
			                 				+formatNumber(ui.rowData.sum_year,2,1)+"</a>";		
				 			 }else{
				 				 if(ui.rowData.sum_year){
				 					return formatNumber(ui.rowData.sum_year,2,1) ;
				 				 }
				 				 
				 			 }
						 }
				 		
					},
                { display: '1月', name: 'month1', align: 'right',width:100,
						 render:function(ui){
			               	 var value = ui.cellData;
							 if(ui.rowData.b_no == 2){
									 return "<a href=javascript:openUpdate('"+
								 ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			                 				 "01"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						 }
			 		},
				{ display: '2月', name: 'month2', align: 'right',width:100,
			 			render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "02"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
				 	},
				{ display: '3月', name: 'month3', align: 'right',width:100,
			 			render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "03"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					},
				{ display: '4月', name: 'month4', align: 'right',width:100,
				    	render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "04"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
				 	},
				{ display: '5月', name: 'month5', align: 'right',width:100,
				 		render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "05"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					 },
				{ display: '6月', name: 'month6', align: 'right',width:100,
						 render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "06"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					 },
				{ display: '7月', name: 'month7', align: 'right',width:100,
						 render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "07"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					},
				{ display: '8月', name: 'month8', align: 'right',width:100,
						render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "08"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					},	
				{ display: '9月', name: 'month9', align: 'right',width:100,
						render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "09"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					},
				{ display: '10月', name: 'month10', align: 'right',width:100,
						render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "10"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					},
				{ display: '11月', name: 'month11', align: 'right',width:100,
						render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "11"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					},
				{ display: '12月', name: 'month12', align: 'right',minWidth:100,
						render:function(ui){
			      	 			var value = ui.cellData;
							if(ui.rowData.b_no == 2){
								return "<a href=javascript:openUpdate('"+
								     ui.rowData.year+"!"+ui.rowData.index_code+"!"+
			              				 "12"+ "')>"+formatNumber(value, 2, 1)+"</a>";
							 }else{
								 return formatNumber(value, 2, 1);
							 }
						}
					}
          ],
          dataMedol: {
	          method: 'POST',
	          location: 'remote',
	          url: '',
	          recIndx: 'year'
          },
          usePager: false,
          width: '100%',
          height: '100%',
          checkbox: true,
          freezeCols: 5,
          toolbar: { 
         	  items: [
         		{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
 		      ]
          },
       });
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=printData;
 		grid.options.lodop.title="医院业务预算查询";
    }
    //月份 预算值链接 
    function openUpdate(obj){
		var vo = obj.split("!");
		var parm = 	
			"year="+vo[0]   +"&"+ 
			"index_code="+vo[1]   +"&"+ 
			"month="+vo[2] 
		$.etDialog.open({
            url: 'budgWorkHosLinkPage.do?isCheck=false&'+parm,
            title: '预算值链接',
            isMax: true
        });
    }
    //本年合计值链接
    function  openUpdateSum(obj){
    	var vo = obj.split("!");
		var parm = 	
			"year="+vo[0]   +"&"+ 
			"index_code="+vo[1]   +"&"+ 
			"sum_year="+vo[2]
		$.etDialog.open({
            url: 'budgWorkHosLinkPageSum.do?isCheck=false&'+parm,
            title: '预算值链接',
            isMax: true
        });
    }
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
