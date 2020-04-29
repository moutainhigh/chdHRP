<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,select,grid,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var year, <%--dept_code, title_code, duty_code, --%>teach_type_code, level_code;
        var grid;
        var initFrom = function () {
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                // minDate: data[data.length - 1].text,
                // maxDate: data[0].text,
                onChange: query,
            });
            <%--dept_code = $("#dept_code").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            title_code = $("#title_code").etSelect({
                url: "../queryHrTitle.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            duty_code = $("#duty_code").etSelect({
                url: "../queryDuty.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });--%>
            teach_type_code = $("#teach_type_code").etSelect({
                url: "queryTeachType.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            level_code = $("#level_code").etSelect({
                url: "../queryDicLevel.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            emp_id = $("#emp_id").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
/* 
            $("#emp_code").on('change', query);
            $("#emp_name").on('change', query); */
        };
        var query = function () {
            params = [
                { name: 'year', value: year.getValue() },
                { name: 'emp_id', value: emp_id.getValue()},
                <%--{ name: 'dept_code', value: dept_code.getValue() },
                { name: 'title_code', value: title_code.getValue() },
                { name: 'duty_code', value: duty_code.getValue() },--%>
                { name: 'level_code', value: level_code.getValue() },
                { name: 'teach_type_code', value: teach_type_code.getValue() },
            ];
            grid.loadData(params,"queryTeachingStatistics.do");
        };
        
        var print = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":$('#dept_name').val()},
        	          {"cell":1,"value":dept_code},
            		] */  }; 
        	var printPara={
              		title: " 年度教学能力统计打印",//标题
              		columns: JSON.stringify(grid.getPrintColumns()),//表头
              		class_name: "com.chd.hrp.hr.service.nursing.HrTeachingStatisticsService",
           			method_name: "queryTeachingStatisticsByPrint",
           			bean_name: "hrTeachingStatisticsService",
           			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
           			foots: '',//表尾需要打印的查询条件,可以为空 
               	};
             	$.each(grid.getUrlParms(),function(i,obj){
           			printPara[obj.name]=obj.value;
            	}); 
             	//console.log(printPara);
            	officeGridPrint(printPara);
        	
        };

        var initGrid = function () {
            var columns = [
                { display: '教学日期', name: 'teach_date', width: 120,algin:'left' },
                { display: '职工工号', name: 'emp_code', width: 120,algin:'left' },
                { display: '职工名称', name: 'emp_name', width: 120,algin:'left' },
                { display: '护理等级', name: 'col_name', width: 120,algin:'left' },
                { display: '教学种类', name: 'teach_type_name', width: 120,algin:'left' },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                /* dataModel: {
                     url: 'queryTeachingStatistics.do?isCheck=false'
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initGrid();
            query();
        })
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">统计年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
                     <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                <td class="label">护理等级：</td>
                <td class="ipt">
                    <select id="level_code" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
             
                <td class="label">教学种类：</td>
                <td class="ipt">
                    <select id="teach_type_code" style="width:180px;"></select>
                </td>
            </tr>
            <%--<tr>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>
                
                <td class="label">职务：</td>
                <td class="ipt">
                 <select id="duty_code" style="width:180px;"></select>
                   
                </td>
                <td class="label">职称：</td>
                <td class="ipt">
                    <select id="title_code" style="width:180px;"></select>
                </td>
            </tr> --%>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>