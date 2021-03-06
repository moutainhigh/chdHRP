<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param
		value="hr,dialog,datepicker,select,validate,grid,dateSlider,characterList,tree,tab,from,pageOffice"
		name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">
	var year_month, dept_code, emp_code, state;
	var jsonHead, selectSql, detailData;
	var items = {};
	$(function () {
		initDict();
		leftGrid();
		genegrateMonthsColumns();
		query();
	});

	function initDict() {
		/* 考勤周期 */
		year_month = $("#year_month").etDatepicker({
			view: "months",
			minView: "months",
			dateFormat: "yyyy-mm",
			defaultDate: true,
			onChange: function(value) {
				genegrateMonthsColumns()
			}
		});
		
		/* 部门 */
		dept_code = $("#dept_code").etSelect({
		    url: '../../queryHosDeptSelect.do?isCheck=false&is_last=1',
		    defaultValue: "none",
		    onChange: function(value) {
		    	query();
		    }
		});
		
		/* 职工名称 */
		emp_code = $("#emp_code").etSelect({
			url: '../../queryEmpSelect.do?isCheck=false',
		    defaultValue:  "none",
		    onChange: function(value) {
		    	query();
		    }
		});
		
		/* 状态*/
		state = $("#state").etSelect({
			options: [
	          { id: 0, text: '未审核' },
	          { id: 1, text: '已审核' },
	          { id: 2, text: '已提交' }
	        ],
	        defaultValue: "none"
		});
	
	};

	/* 左表 基础列头 */
	var leftcolumns = [
		{display : '考勤周期',name : 'year_month',width : 70,editable: false},
		{display : '出勤科室',name : 'dept_name_c',width : 100,editable: false},
		{display : '职工编码',name : 'emp_code',width : 60,editable: false},
		{display : '职工名称',name : 'emp_name',width : 60,editable: false},
		{display : '编制科室',name : 'dept_name_b',width : 100,editable: false},
		{display : '医护属性',name : 'yh_name',width : 100,editable: false}/*,
		{display : '应出勤天数',name : 'attend_date',width : 60,editable: false} */
	];
	var rightcolumns = [
		//{display : '加班小时数',name : 'add_date',width : 60,editable: false},
		//{display : '积休天数',name : 'jixiu_date',width : 60,editable: false},
		//{display : '实际出勤天数',name : 'result_date',width : 80,editable: false},
		{display : '状态',name : 'state',width : 60,editable: false},
		{display : '上报人',name : 'oper',width : 60,editable: false},
	    {display : '上报日期',name : 'oper_date',width : 60,editable: false},
	    {display : '审核人',name : 'checker',width : 60,editable: false},
	    {display : '审核日期',name : 'check_date',width : 60,editable: false}
	    
	];
    
	/* 生成日期的列 */
	var genegrateMonthsColumns = function() {
		var columns = [];
		var date = year_month.getValue().replace("-", "");
		ajaxPostData({
			url: 'queryAttendResultManageHead.do?isCheck=false',
			async: false,	  
			success: function (data) {
				if(data.state == "true"){
					jsonHead = eval(data.jsonHead);
					selectSql = data.selectSql;
					$.each(jsonHead,function(index, v){
						if(v.name){
							items[v.name] = {attend_code: v.attend_code, attend_types: v.attend_types};
							columns.push({
								display : v.display,
								name : v.name,
								width : 50,
								align: "right", 
							})
						}
					});
				}
				  
				var leftNewColumns = leftcolumns.concat(columns);
				var newColumns = leftNewColumns.concat(rightcolumns);
				leftGrid.option('columns', newColumns);
				leftGrid.refreshView();
			},
		});
	};
		
	var leftGrid = function() {
		 // 基础表格参数
	    var toolbar = {
	        items: [
	            { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
	            { type: "button", label: '生成', icon: 'create', listeners: [{ click: createData }] },
	            { type: "button", label: '清除', icon: 'delete', listeners: [{ click: deleteData }] },
	            { type: "button", label: '上报', icon: 'circle-check', listeners: [{ click: audit }] },
	            { type: "button", label: '取消上报', icon: 'cancel', listeners: [{ click: unAudit }] },
	            { type: "button", label: '审核', icon: 'circle-check', listeners: [{ click: submit }] },
	            { type: "button", label: '销审', icon: 'cancel', listeners: [{ click: unSubmit }] },
	            { type: "button", label: '导入', icon: 'import', listeners: [{ click: importData }] },
	            { type: "button", label: '打印', icon: 'print', listeners: [{ click: printData }] },
	        ]
	    };
		var paramObj = {	
			width : 'auto',
			height: '100%',
	        editable: false,
	        inWindowHeight: '100%',
	        toolbar: toolbar,
	        checkbox: true,
			columns : leftcolumns,
	        freezeCols : 5, //冻结两列
	        cellDblClick: cellDblClick, 
			pageModel: {
                type: 'remote',//local前台分页
            },
		};
		leftGrid = $("#maGrid").etGrid(paramObj);
	};
	
	//单元格双击事件
	function cellDblClick(event, ui){
		//是否为考勤项列
		var col_name = ui.column.dataIndx;
	   	if(items[col_name] && (items[col_name].attend_types == "02" || items[col_name].attend_types == "03")){
	   		if(ui.rowData[col_name] > 0){
	   			//弹出页面
	   			detailData = {
   					year_month:  ui.rowData.year_month, 
   					dept_id_c: ui.rowData.dept_id_c, 
   					dept_name_c: ui.rowData.dept_name_c, 
   					emp_id: ui.rowData.emp_id, 
   					emp_name: ui.rowData.emp_code + " " + ui.rowData.emp_name, 
   					attend_code: items[col_name].attend_code, 
	   			};
	   			var url, title;
				if(items[col_name].attend_types == "02"){
					url = "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageJbPage.do?isCheck=false";
					title = "加班记录";
				}else{
					url = "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageXjPage.do?isCheck=false";
					title = "休假记录";
				}
				
				parent.$.etDialog.open({
					url: url,
					width: 700, 
					height: 400,
					frameName: window.name,
					title: title
				});
	   		}
	   	}
	   	
	   	return true;
	}
	
	//查询
	var query = function () {
		var dept_id;
		if("${dept_id}"!=""){
			dept_id="${dept_id}"
		}else{
			dept_id=dept_code.getValue().split("@")[1]
		}
		var params = [
			{name: 'year_month', value: year_month.getValue().replace("-", "") },
			{name: 'dept_id', value: dept_id }, 
			{name: 'emp_id', value: emp_code.getValue() },
			{name: 'state', value: state.getValue() },
			{name: 'selectSql', value: selectSql }
		]
        leftGrid.loadData(params, "queryAttendResultManage.do");
    };
    
    //生成
    function createData(){

        $.etDialog.confirm('确定生成上报数据?', function () {
        	ajaxPostData({
                url: 'addBatchAttendResultManage.do',
                data: {
                	'year_month': year_month.getValue().replace("-", "")
                },
                success: function () {
                    query();
                }
			});
        });
	}
    
    //清除
    function deleteData(){
		var data = leftGrid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
        	var param = [];
            $(data).each(function () {
                var rowdata = this.rowData;
                param.push({
                	dept_id_c: rowdata.dept_id_c, 
                	emp_id : rowdata.emp_id
                })
            });
            
            $.etDialog.confirm('确定删除?', function () {
                ajaxPostData({
                    url: 'deleteBatchAttendResultManage.do',
                    data: { 
                    	year_month: year_month.getValue().replace("-", ""),
                    	ids: JSON.stringify(param)
                	}, 
                    success: function () {
                        query();
                    }
                 })
            });
        }
	}
    //上报
    function audit(){
    	auditOrUnAudit(0);
	}
    //取消上报
    function unAudit(){
    	auditOrUnAudit(1);
    }
    //上报or取消上报
    function auditOrUnAudit(flag){
		var data = leftGrid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
        	var param = [];
            $(data).each(function () {
                var rowdata = this.rowData;
                param.push({
                	dept_id_c: rowdata.dept_id_c, 
                	emp_id : rowdata.emp_id
                })
            });
            var msg = "", url = "";
            if(flag == 1){
            	msg = "确定取消上报所选数据？";
            	url = "unSubmitAttendResultManage.do";
            }else{
            	msg = "确定上报所选数据？";
            	url = "submitAttendResultManage.do";
            }
            
            $.etDialog.confirm(msg, function () {
                ajaxPostData({
                    url: url,
                    data: { 
                    	year_month: year_month.getValue().replace("-", ""),
                    	state: flag, 
                    	ids: JSON.stringify(param)
                	}, 
                    success: function () {
                        query();
                    }
                 })
            });
        }
    }

    //审核
    function submit(){
    	submitOrUnSubmit(2);
	}
    //销审
    function unSubmit(){
    	submitOrUnSubmit(1);
    }
    //审核or销审
    function submitOrUnSubmit(flag){
		var data = leftGrid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
        	var param = [];
            $(data).each(function () {
                var rowdata = this.rowData;
                param.push({
                	dept_id_c: rowdata.dept_id_c, 
                	emp_id : rowdata.emp_id
                })
            });
            var msg = "", url = "";
            if(flag == 1){
            	msg = "确定销审所选数据？";
            	url = "unAuditAttendResultManage.do";
            }else{
            	msg = "确定审核所选数据？";
            	url = "auditAttendResultManage.do";
            }
            
            $.etDialog.confirm(msg, function () {
                ajaxPostData({
                    url: url, 
                    data: { 
                    	year_month: year_month.getValue().replace("-", ""),
                    	state: flag, 
                    	ids: JSON.stringify(param)
                	}, 
                    success: function () {
                        query();
                    }
                 })
            });
        }
    }
    //导入
    function importData(){
		var columns =[];
		var para;
		columns.push({
			"name" : "dept_code",
			"display" : "出勤科室",
			"width" : "100",
			"require" : true
		},{
			"name" : "emp_code",
			"display" : "职工信息",
			"width" : "100",
			"require" : true
		});
		
		var date = year_month.getValue().replace("-", "");
    	var a = null;
		if(jsonHead.length>0){
			$.each(jsonHead,function(index, v){
				columns.push({
		       		"display" : v.display,
		       		"name" : v.name,
		       		"width" : "60"
		       	});
			});

			para = {
				"year_month": date, 
				"column": columns
			};
			 
			importSpreadView("/hrp/hr/attendancemanagement/resultmanage/importAttendResultManage.do", para);
		}
	}
    
    //打印
	function printData(){	
		if(leftGrid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads = {};
    	
    	var printPara={
      		title: "考勤表审核上报",//标题
      		columns: JSON.stringify(leftGrid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultManageService",
   			bean_name: "hrAttendResultManageService",
   			method_name: "queryAttendResultManagePrint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
    	
       	$.each(leftGrid.getUrlParms(),function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	officeGridPrint(printPara);
	}

</script>
<body>
	<div class="container">
		<div class="center" style="padding: 0">
			<table class="table-layout">
				<tr>
					<td class="label">考勤周期：</td>
					<td><input name="year_month" type="text" id="year_month"
						style="width: 90px" /></td>

					<td class="label">部门：</td>
					<td><select id="dept_code" style="width: 150px"></select></td>

					<td class="label">职工名称：</td>
					<td><select id="emp_code" style="width: 150px"></select></td>

					<td class="label">状态：</td>
					<td><select id="state" style="width: 90px"></select></td>
				</tr>
			</table>

			<div id="maGrid"></div>
		</div>
	</div>
</body>
</html>