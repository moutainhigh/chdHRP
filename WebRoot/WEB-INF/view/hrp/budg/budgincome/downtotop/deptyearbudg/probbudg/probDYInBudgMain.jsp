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
<script src="/CHD-HRP/lib/et_assets/hr/common.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var budg_year ;
    $(function (){
    	loadHead(null);
    	loadHeadDetail()
		loadHotkeys();
		init();
    });
    
	var year_input,subj_name_select,subj_level_select,dept_name_select

	function init(){
		getData("../../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
			/* 	minDate: data[data.length - 1].text,
				maxDate: data[0].text, */
				todayButton: false,
				onSelect: function (value) {
					reloadSubjName(value);
					setTimeout(function () {
						query();
					}, 10);
				}
			});
			reloadSubjName(data[0].text);
		});

		subj_name_select = $("#subj_name_select").etSelect({
			defaultValue: "none",
			onChange: query
		});
		function reloadSubjName(value){
			subj_name_select.reload({
				url:"../../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:'04',
					budg_year:value
				}
			})
		}
		
		subj_level_select = $("#subj_level_select").etSelect({
			url: "../../../../queryBudgSubjLevel.do?isCheck=false&insurance_natrue='01'",
			defaultValue: "none",
			onChange: query
		});

		dept_name_select=$("#dept_name_select").etSelect({
			url: "../../../../queryBudgDeptDict.do?isCheck=false",
			defaultValue: "none",
			onChange: query
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
    
    //查询左侧
    function  query(){
    	var search = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_name_select.getValue() },
			{ name: 'subj_level', value: subj_level_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() }
		]
		//加载查询条件
		grid.loadData(search,"");
     }
    
   	// 查询右侧 
    function  queryRight(){
    	search = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_name_select.getValue() },
			{ name: 'subj_level', value: subj_level_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() }
		]
		//加载查询条件
		gridRight.loadData(search,"");
	}
    
    function loadHead(){
    	 var yearEditor = getRecentYearForSelect();
    	grid = $("#maingrid").etGrid({
           columns: [ 
    	       {display: '预算年度', name: 'year', align: 'center',width:"10%",editable:setEdit,
    	    	   editor: yearEditor,
			    },
				{ display: '科室编码', name: 'dept_code', align: 'left', width: 150,editable:false},
				{ display: '科室名称', name: 'dept_name', align: 'left', width: 150,editable:setEdit,
					editor:{
						type:'select' ,
						keyField:'dept_id',
						url:'queryBudgDept.do?isCheck=false',
						change:reloadDeptCode,
					}
				},
				{display: '科目编码', name: 'subj_code', align: 'left',width:"15%",editable:false},
				{display: '科目名称', name: 'subj_name', align: 'left',width:"20%",editable:setEdit,
					editor: {
						 keyField:'subj_code',
		            	     type: 'select',  //编辑框为下拉框时
		            	     //source:[],   //  静态数据接口  也可以是回调函数返回值
		            	     url: '../../../../qureySubjIndexFromPlan.do?isCheck=false&edit_method=04&budg_year='+budg_year,      //  动态数据接口
		             	 change:function(rowdata,celldata){
		          	    	 grid.updateRow(celldata.rowIndx,{subj_code:rowdata.subj_code})
		          	     },
	            	     //与年度联动查询
	            	     create:function(rowdata,celldata,setting){
	            	    	 if(rowdata.year){
	            	    		 setting.url = '../../../../qureySubjIndexFromPlan.do?isCheck=false&edit_method=04&budg_year='+rowdata.year;
	            	    	 }else{
	            	    		 $.etDialog.error('请先填写年度');
	            	    		 return false ;
	            	    	 }
	            	     }
	            	}
				},
				{ display: '计算值', name: 'count_value', align: 'right',width:120,editable:false ,
					render:function(ui){
						if(ui.rowData.count_value){
							return formatNumber(ui.rowData.count_value,2,1);
						}
					}
				},
        		{ display: '预算值(E)', name: 'budg_value', align: 'right',dataType: "float",width:120,
					render:function(ui){
						if(ui.rowData.budg_value){
							return formatNumber(ui.rowData.budg_value,2,1);
						}
					}
				},
				{ display: '说明(E)', name: 'remark', align: 'left',dataType:"string",minWidth:200,}
			],
			dataModel:{
	         	method:'POST',
	         	location:'remote',
	         	url:'queryProbDYInBudgUp.do?isCheck=false&edit_method=04',
	         	recIndx: 'year'
  	        },
            usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
            addRowByKey:true,inWindowHeight: true ,
         	rowSelect : queryRightDate,
			toolbar: {
	            items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		           	{ type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] },
					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					/* { type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }, */
	           	]
  	        }
    	});
    }
    function loadHeadDetail() {
		gridRight = $("#maingridRight").etGrid({
			columns: [
				{
					display: '运营尺度(E)', name: 'measure_name', align: 'center', dataType: "string", width: "25%"
				},
				{
					display: '运营预期(E)', name: 'measure_value', align: 'right', dataType: "float", width: 100,
					render:function(ui) {
						if (ui.rowData.measure_value) {
							return formatNumber(ui.rowData.measure_value, 2, 1);
						}
					},
				},
				{
					display: '概率(E)', name: 'rate', align: 'right', dataType: "float", width: 80,
					render:function(ui) {
						if (ui.rowData.rate) {
							return formatNumber(ui.rowData.rate, 2, 0) + "%";
						}
					}
				},
				{
					display: '计算值', name: 'count_value', align: 'right', minWidth: "100",
					render:function(ui) {
						return formatNumber(ui.rowData.count_value == null ? 0 : ui.rowData.count_value, 2, 1);
					}
				}
			],
			dataModel: {
				method: 'POST',
				location: 'remote',
				url: 'queryBudgMedIncomeDYRate.do?isCheck=false',
				recIndx: 'measure_name'
			},
			usePager: false, width: '100%', height: '100%', checkbox: false, editable: true,
			addRowByKey: true, inWindowHeight: true,
			toolbar: {
				items: [
					{ type: "button", label: '生成', icon: 'plus', listeners: [{ click: generateRight }] },
					{ type: "button", label: '添加行', icon: 'plus', listeners: [{ click: addRow }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: removeRows }] },
					{ type: "button", label: '计算', icon: 'calculator', listeners: [{ click: count }] },
				]
			},
			summary: { //  前台渲染摘要行    摘要行集合    
				totalColumns: ['rate', 'count_value'], //合计冻结行 
				keyWordCol: 'measure_name', //关键字所在列的列名
			},

			load: function () {
				gridRight.refreshSummary();
			}
		});
	}
    
    //选择科室后 更新科室编码 
    function reloadDeptCode(rowdata,celldata){
    	setTimeout(function () {
    		grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
    	}, 10);
    }
	
    //添加行
	function add_Row() {
		grid.addRow();
	}
    //添加可编辑行
    function addRow(){
    	gridRight.addRow();
    }
    
    // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
	function setEdit(ui) {
		if (ui.rowData && ui.rowData.group_id) {
			return false;
		} else {
			return true;
		}
	}
	// 选中 左侧行数据 加载右侧概率数据
	function queryRightDate(event, ui) {
		year = ui.rowData.year;
		index_code = ui.rowData.index_code;
		queryRight();
	}
    
	//保存数据
	function save() {
		var data = grid.getChanges();

		var ParamVo = [];

		if (data.addList.length > 0 || data.updateList.length > 0) {

			if (data.addList.length > 0) {

				var addData = data.addList;

				if (!validateGrid(addData)) {
					return false;
				}
				$(addData).each(function () {
					ParamVo.push(
						this.year   +"@"+ 
						this.subj_code  +"@"+ 
						this.dept_id  +"@"+ 
						(this.count_value? this.count_value:"")  +"@"+ 
						(this.budg_value? this.budg_value:"")  +"@"+ 
	   					(this.remark?this.remark:"")   	+"@"+ 
						this._rowIndx + "@" +
						//添加数据标识
						'1' + "@" +
						(this.detail?JSON.stringify(this.detail.Rows):"-1")+"@"
					)
				});
			}
			if (data.updateList.length > 0) {
				var updateData = data.updateList;

				if (!validateGrid(updateData)) {
					return false;
				}
				$(updateData).each(function () {
					ParamVo.push(
						this.year   +"@"+ 
						this.subj_code  +"@"+ 
						this.dept_id  +"@"+ 
						(this.count_value? this.count_value:"")  +"@"+ 
						(this.budg_value? this.budg_value:"")  +"@"+ 
	   					(this.remark?this.remark:"")   	+"@"+ 
						this._rowIndx + "@" +
						//修改数据标识
						'2' + "@" +
						(this.detail?JSON.stringify(this.detail.Rows):"-1")+"@"
					)
				});
			}
			ajaxPostData({
				url: "saveProbDYInBudgUp.do?isCheck=false",
				data: { ParamVo: ParamVo.toString() },
				success: function (res){
					if (res.state == "true") {
						query();
						year = "";
						index_code = "";
						queryRight()
					}
				}
			})
		} else {
			$.etDialog.warn('没有需要保存的数据!');
		}
	}
 
	function remove() {
		var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];//后台删除数据
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
           				this.rowData.group_id   +"@"+ 
           				this.rowData.hos_id   +"@"+ 
           				this.rowData.copy_code   +"@"+ 
           				this.rowData.year   +"@"+ 
           				this.rowData.subj_code + "@" +
   						this.rowData.dept_id
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
			});
            if(ParamVo.length > 0){
            	$.etDialog.confirm('确定删除?',function (){
					ajaxPostData({
						url: "deleteProbDYInBudgUp.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function (res){
							if (res.state == "true") {
								query();
							}
						}
					})
				});
            }else if(deletePageRow.length > 0 ){
            	grid.deleteRows(deletePageRow);
            	$.etDialog.success("删除成功!");
            }
		}
	}
	
    //导入
    function imp(){
    	$.etDialog.open({
			url: 'probDYInBudgImportPage.do?isCheck=false',
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			isMax: true
		});
    }	
    //下载模板
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
    
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"year="+vo[3]   +"&"+ 
			"subj_code="+vo[4] +"&"+ 
			"dept_id="+vo[5]
		 $.ligerDialog.open({ url : 'probDYInBudgUpdatePage.do?isCheck=false&' + parm ,data:{}, height: 300,width: 450, title:'科室年度医疗收入独立核算科目预算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgHosIndependentSubj(); },cls:'l-dialog-btn-highlight' },
			    { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			]
    	}); 
    }
    //打印数据
    function printDate(){
    	if(grid.getAllData().length==0){
			$.etDialog.error("无打印数据！");
			return;
		}
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name:'year',value:liger.get("year").getValue()}); 
		grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
		grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()});
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue()});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"科室年度医疗收入预算概率预算信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryProbDYInBudgUp.do?isCheck=false&edit_method=04", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    }
    
    //增量生成
    function generate(){
    	var year = year_input.getValue();
 	   	if(year){
	 	   	ajaxPostData({
				url: "generate.do?isCheck=false&year="+year,
				data: {},
				success: function (res){
					if (res.state == "true") {
						query();
					}
				}
			})
 	   	}else{
 	   		$.etDialog.warn("预算年度不能为空");
 	   	}
    }
    
    //右侧grid 生成
    function generateRight(){
    	
		var data = grid.selectGet();
		if(data.length == 0){
    		$.etDialog.error('请选择需要生成的数据!');
    		return
    	}
		
		var rightData = gridRight.getAllData();
    	if(rightData != null ){
    		$.etDialog.error('所选数据已生成概率数据,不能再生成');
    	}else{
    		if(data.length == 1){
    			ajaxPostData({
    				url: "../../../../business/compilationplan/uptodown/hosyearbudg/prob/setProbBudgRate.do?isCheck=false",
    				data: {},
    				success: function (res){
    					gridRight.deleteRows(gridRight.getAllData());
    					gridRight.addRows(responseData.Rows);
    					gridRight.refreshSummary();
    				}
    			})
        	}else{
        		$.etDialog.error('请选择一行数据进行生成');
        	}
    	}
    }
    
    //右侧grid 删除
	function removeRows(){
		var data = gridRight.selectGet();
        if (data.length == 0){
        	$.etDialog.warn('请选择行');
        }else{
        	gridRight.deleteRows(data);
        }
        gridRight.refreshSummary();
    }
    
	//右侧grid 计算
	function count(){
		
		var data = gridRight.getAllData();

		var dataL = grid.selectGet();
		
		if(data.length == 0){
			$.etDialog.warn('没有需要计算的数据');
		}else{
			if(dataL.length != 1){
				$.etDialog.warn('请在左侧选择一行数据再操作');
			}else{
				var count_value = 0; //存储 总计算值
				//var countValue = 0;// 存储 右侧表格每行 计算值
				var falg= 0 ; // 记录 总概率
				$(data).each(function (){
					falg = falg + Number(this.rate) ;
				})
				if(falg == 100){
					$(data).each(function (){
						this.count_value = Number(this.measure_value) * Number(this.rate) / 100 ;//计算右侧每行 计算值
						
						count_value = count_value + this.count_value ;
						//gridRight.updateCell('count_value',countValue,this);
					})
					
					grid.updateRow(dataL[0].rowData._rowIndx, { "count_value": count_value, "budg_value": count_value, detail: { "Rows": data, "Total": data.length } });

					gridRight.refreshView();

					gridRight.refreshSummary();

				}else{
					$.etDialog.warn('总概率不等于100%,不能计算');
				}
			}
		}
    }
	
	function updateTotal(){
		gridRight.updateTotalSummary();
	}
	function validateGrid(data) {
		var msg = "";
		var rowm = "";
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();
		$.each(data, function (i, v) {
			rowm = "";
			if (!v.year) {
				rowm += "[年度]、";
			}
			if (!v.subj_name) {
				rowm += "[科目名称]、";
			}
			if (!v.dept_name) {
				rowm += "[科室名称]、";
			}
			if (!v.budg_value) {
				rowm += "[预算值]、";
			}
			/* if (!v.count_value) {
				rowm += "[计算值]、";
			} */

			if (rowm != "") {
				rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key = v.year + v.month + v.index_code + v.dept_id
			var value = "第" + (Number(v._rowIndx) + 1) + "行";
			if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
				targetMap.put(key, value);
			} else {
				msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
			}
		});
		if (msg != "") {
			$.etDialog.warn(msg);
			return false;
		} else {
			return true;
		}
	}
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('P', printDate);
		hotkeys('I', imp);
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_name_select" style="width:180px;"></select>
				</td>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name=" " id="subj_level_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
 	<div id="toptoolbar" ></div>
	 <div >
	 	<div  style="float: left; width: 65%;">
			<div id="maingrid"></div>
		</div>
		<div  style="float: left; width: 35%;">
			 <div id="maingridRight"></div>
		</div>
    </div> 
</body>
</html>
