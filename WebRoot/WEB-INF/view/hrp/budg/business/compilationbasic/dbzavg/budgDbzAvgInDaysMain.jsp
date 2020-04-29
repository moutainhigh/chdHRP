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
		<jsp:param value="grid,select,datepicker,ligerUI,dialog,grid" name="plugins" />
	</jsp:include>
<script type="text/javascript">
	var grid;
	var insurance_code ;
	//打印 单元格格式化 用
	var renderFunc = {
		avg_in_days: function (value) {
			return formatNumber(value, 2, 1)
		}
	};
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	var year_input,insurance_code_select,disease_code_select;

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


		disease_code_select = $("#disease_code_select").etSelect({
			url:"../../../queryBudgSingleDisease.do?isCheck=false",
			defaultValue:"none",
			onChange:query
		});

		insurance_code_select = $("#insurance_code_select").etSelect({
			url:"../../../queryBudgYBType.do?isCheck=false",
			defaultValue:"none",
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
	function query() {
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'disease_code', value: disease_code_select.getValue() },
			{ name: 'insurance_code', value: insurance_code_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,'queryBudgDbzAvgInDays.do?isCheck=false');
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left',width:"10%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'year',
						url: '../../../queryBudgYearTen.do?isCheck=false',
					}
				},
				{display: '单病种编码', name: 'disease_code', align: 'left',width:"20%",editable:false,},
				{display: '单病种名称', name: 'disease_name', align: 'left',width:"25%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'disease_code',
						url: 'queryBudgSingleDC.do?isCheck=false',
						change: function(rowdata,celldata){
							setTimeout(function (){
								grid.updateRow(rowdata._rowIndx, {disease_code:rowdata.disease_name.split(" ")[0],insurance_name:"",insurance_code:""})
							},300)
						},
					}
				},
				{display: '医保类型', name: 'insurance_name', align: 'left',width:"20%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'insurance_code',
						url: 'queryBudgYBTP.do?isCheck=false',
						create:function(rowdata,celldata,setting){
							if(rowdata.disease_code){
								setting.url ="queryBudgYBTP.do?isCheck=false&disease_code="+rowdata.disease_code
							}else{
								$.etDialog.error('请先选择医保类型');
							}
						}
					}
				},
				{display: '平均住院日(E)', name: 'avg_in_days', align: 'right', dataType: "float",width:"22%",
					render:function(ui){
						if (ui.rowData.avg_in_days){
							return formatNumber(ui.rowData.avg_in_days, 2, 1);
						}
					}
				}
			],
			dataModel:{
	           	 method:'POST',
	           	 location:'remote',
	           	 url:'',
	           	 recIndx: 'year'
	        },
			usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
			addRowByKey:true,
			toolbar: {
               items: [
	               { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
	               { type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] },
				   { type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
				   { type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
				   { type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
				   /* { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]}, */
				   { type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: impNew}] },
			   ]
			}
		});
	}

	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
    
    function add_Row(){
    	grid.addRow() ;
    }
	
	function impNew() {
		var paramVo = {
			"column": [
				{
					"name": "a",
					"display": "年度【必填】",
					"width": "200",
					"require": true
				},
				{
					"name": "b",
					"display": "病种名称【必填】",
					"require": true
				},
				{
					"name": "c",
					"display": "医保类型名称【必填】",
					"require": true
				},
				{
					"name": "d",
					"display": "平均住院日(天)【必填】",
					"require": true
				}
			]
		};
		importSpreadView("hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysImportNewPage.do?isCheck=false", paramVo);
	}
	
	function add_open() {
		$.etDialog.open({
			url: 'budgDbzAvgInDaysAddPage.do?isCheck=false',
			height: 300, width: 450, title: '单病种平均住院日', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgDbzAvgInDays()
            }
		});
	}

	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var delData = [];//接受页面端删除数据
			$(data).each(function () {
				if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.year + "@" +
						this.rowData.disease_code + "@" +
						this.rowData.insurance_code
					)
				}else {
					delData.push(this)
				}
			});
			$.etDialog.confirm('删除操作会刷新页面,页面未保存数据可能丢失！确定删除?', function (yes) {
				if (yes) {
					if (ParamVo.length > 0) {
						ajaxPostData({
		                    url: "deleteBudgDbzAvgInDays.do?isCheck=false",
		                    data: { ParamVo: ParamVo.toString() },
		                    success: function(responseData) {
		                    	query();
		                    }
		                });
					} else {
						grid.deleteRows(delData);
						$.etDialog.success("删除成功.");
					}
				}
			});
		}
	}

	function imp() {
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgDbzAvgInDaysImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	
	function downTemplate() {
		location.href = "downTemplate.do?isCheck=false";
	}

	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm =
			"group_id=" + vo[0] + "&" +
			"hos_id=" + vo[1] + "&" +
			"copy_code=" + vo[2] + "&" +
			"year=" + vo[3] + "&" +
			"disease_code=" + vo[4] + "&" +
			"insurance_code=" + vo[5]

		$.etDialog.open({
			url: 'budgDbzAvgInDaysUpdatePage.do?isCheck=false&parm=' + parm,
			height: 300, width: 450, title: '单病种平均住院日', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgDbzAvgInDays()
            }
		});
	}
	//修改保存
	function save() {
		var data = grid.getChanges();
		var ParamVo =[];
		if( data.addList.length > 0 || data.updateList.length > 0 ){
    		if(data.addList.length > 0){
        		var addData = data.addList ;
        		if(!validateGrid(addData)){
        			return  false ;
        		}
        		addData.forEach(function(item) {	
                 	ParamVo.push(
               			item.year + "@" +
               			item.disease_code + "@" +
               			item.insurance_code + "@" +
      					(item.avg_in_days ? item.avg_in_days : "-1") +"@"+
      					//行号 提示错误信息用
      					item._rowIndx +"@"+
      					"1" //添加标识
     				) 
     			});
         	}
 			if( data.updateList.length > 0){
 	        	var updateData = data.updateList ;
 	        	updateData.forEach(function(item) {	
 	               	ParamVo.push(
 	               		item.year + "@" +
             			item.disease_code + "@" +
             			item.insurance_code + "@" +
    					(item.avg_in_days ? item.avg_in_days : "-1") +"@"+
       					//行号 提示错误信息用
       					item._rowIndx +"@"+
       					"2" //修改标识
 	   				) 
 	   			});
         	}
 			ajaxPostData({
                url: "saveBudgDbzAvgInDays.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		}else{
			$.etDialog.warn('没有需要保存的数据!');
		}
	}
	function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
 			if (v.year == "" || v.year == null || v.year == 'undefined') {
				rowm += "[年度]、";
			}
			if (v.insurance_name == "" || v.insurance_name == null || v.insurance_name == 'undefined') {
				rowm += "[医保类型]、";
			}
			if (!v.disease_name) {
				rowm+="[单病种名称]、";
			}
			if (!v.avg_in_days) {
				rowm+="[平均住院日]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.insurance_code + v.disease_code
			var value="第"+(Number(v._rowIndx)+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
			}
 		});
 		if(msg != ""){
 			$.etDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "单病种平均住院日";
	}

	function generate() {
		var year = year_input.getValue();
		if (year) {
			ajaxPostData({
                url: "generate.do?isCheck=false&year=" + year,
                success: function(responseData) {
                	query();
                }
            });
		} else {
			$.ligerDialog.error("预算年度不能为空");
		}
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('G', generate);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>

<div id="toptoolbar"></div>

<div class="main">
	<table class="table-layout">
		<tr>
			<td class="label">年度：</td>
			<td class="ipt">
				<input type="text" id="year_input" />
			</td>
			<td class="label">医保类型：</td>
			<td class="ipt">
				<select name="" id="insurance_code_select" style="width:180px;"></select>
			</td>
			<td class="label">单病种：</td>
			<td class="ipt">
				<select name="" id="disease_code_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>

</html>