<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
<jsp:param value="grid,select,dialog,datepicker" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/map.js"></script>
<script>
	var grid, analysis_code,dept_idSelect,start_month,end_month;
	$(function () {
		loadDict();
	    initGrid();
	})
    function query() {
        params = [
            { name: 'analysis_code', value: analysis_code.getValue() },         
            { name: 'dept_id', value: dept_idSelect.getValue() },
            { name: 'start_month', value: start_month.getValue()},
            { name: 'end_month', value: end_month.getValue() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                {display: '科室', name: 'dept_name',width: '12%'},            	
            	{display: '设备名称', name: 'analysis_name',width: '10%'},
	         	{display: '设备编号', name: 'analysis_code', width: '10%'},
	         	{display: '月份', name: 'use_month', width: '10%'},
	         	{display: '预测工作量', name: 'check_amount', width: '10%'},
	         	{ display: '保本工作量', name: 'price', width:'10%',
	            	render : function(ui){
	        			if(ui.rowData.price){
	        				return formatNumber(ui.rowData.price,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}
                } ,
                { display: '实际工作量', name: 'inves_money', width:'10%',
	            	render : function(ui){
	        			if(ui.rowData.inves_money){
	        				return formatNumber(ui.rowData.inves_money,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}
                } ,
                { display: '安全边际量', name: 'income', width:'10%',
	            	render : function(ui){
	        			if(ui.rowData.income){
	        				return formatNumber(ui.rowData.income,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}
                } ,
                { display: '安全边际率(%)', name: 'cost', width:'10%',
	            	render : function(ui){
	        			if(ui.rowData.cost){
	        				return formatNumber(ui.rowData.cost,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}
                } ,
                { display: '同比', name: 'inves_benefit', width:'10%',
	            	render : function(ui){
	        			if(ui.rowData.cost){
	        				return formatNumber(ui.rowData.cost,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}
                } ,
                { display: '投资对比', name: 'pre_benefit', width:'10%',
	            	render : function(ui){
	        			if(ui.rowData.cost){
	        				return formatNumber(ui.rowData.cost,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}
                } ,
                { display: '工作量对比', name: 'rate', width:'10%',
	            	render : function(ui){
	        			if(ui.rowData.cost){
	        				return formatNumber(ui.rowData.cost,2,1) + '%';
	        			}else{
	        				return '0.00%' ;
	        			}
	        		}
                } ,
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryCvpAnalysis.do'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        function loadDict(){
        	analysis_code =  $("#analysis_code").etSelect({
				url: "../queryAssAnalysisObject.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})  ;  
        	dept_idSelect =  $("#dept_id").etSelect({
				url: "../queryDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})  ;    
            start_month = $("#start_month").etDatepicker({
                 view: "months",
                 minView: "months",
                 dateFormat: "mm",
                 clearButton: false,
                 onChange: query
                   
            });
            end_month = $("#end_month").etDatepicker({
                view: "months",
                minView: "months",
                dateFormat: "mm",
                clearButton: false,
                onChange: query
                  
            });
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">分析项:</td>
            <td class="ipt">
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>          
            <td class="label" style="width: 100px;">科室:</td>
            <td class="ipt">
                <select id="dept_id" type="text" style="width: 180px" ></select>
            </td>       
         	<td class="label" style="width: 100px;">月份:</td>
            <td class="ipt">
                <input id="start_month" style="width: 70px" type="text"/>到
                <input id="end_month" style="width: 70px" type="text"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

