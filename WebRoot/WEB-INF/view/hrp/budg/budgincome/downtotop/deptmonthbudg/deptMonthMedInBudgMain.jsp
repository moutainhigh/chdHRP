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
		<jsp:param value="select,datepicker,ligerUI,grid,dialog,pageOffice" name="plugins" />
	</jsp:include>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;
		var budg_year;
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
		});
		
		var year_input,subj_name_select,subj_level_select,dept_name_select;

		function init(){
			year_input = $("#year_input").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                clearButton: false,
                onChange: function () {
                    setTimeout(function () {
                    	query();
                    }, 10);
                },
                defaultDate: true
            });


			subj_name_select = $("#subj_name_select").etSelect({
				url:"../../../queryBudgSubj.do?isCheck=false&subj_type=04&budg_year="+year_input.getValue(),
				defaultValue: "none",
				onChange: query
			});
		/* 	function reloadSubjName(value){
				subj_name_select.reload({
					url:"../../../queryBudgSubj.do?isCheck=false",
					para:{
						subj_type:'04',
						budg_year:value
					}
				});
			} */
			subj_level_select = $("#subj_level_select").etSelect({
				url: "../../../queryBudgSubjLevel.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});

			dept_name_select = $("#dept_name_select").etSelect({
				url: "../../../queryBudgDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})
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
			})
		};

		//查询
		function query() {
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value: subj_name_select.getValue() },
				{ name: 'subj_level', value: subj_level_select.getValue() },
				{ name: 'dept_id', value: dept_name_select.getValue() }
			]
			//加载查询条件
			grid.loadData(search, '');
		}

		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{ display: '年度', name: 'year', align: 'left', width: 80 ,editable:setEdit,
						editor:{
							keyField:'year',
							type: 'select',  //编辑框为下拉框时
							url: '../../../queryBudgYear.do?isCheck=false',      //  动态数据接口
							change:function(rowdata,celldata){
								grid.updateRow(rowdata._rowIndx,{subj_code:"",subj_name:""})
	                  		}
			 			}
					},
					{ display: '科室编码', name: 'dept_code', align: 'left', width: 120,editable:false},
					{ display: '科室名称', name: 'dept_name', align: 'left', width: 120,editable:setEdit,
						editor:{
			 				type:'select' ,
			 				keyField:'dept_id',
			 				url:'../deptyearbudg/certenbudg/queryBudgDept.do?isCheck=false',
			 				change:reloadDeptCode,
						}
					},
					{ display: '科目编码', name: 'subj_code', align: 'left', width: 120,editable:false},
					{ display: '科目名称', name: 'subj_name', align: 'left', width: 120,editable:setEdit,
						editor:{
			 				type:'select' ,
			 				keyField:'subj_code',
			 				url:'../../../queryBudgSubj.do?isCheck=false&subj_type='+'04'+'&is_last='+'1'+'&budg_year='+budg_year,
			 				change:queryLastYearWorkload ,
							//与年度联动查询
	                	    create:function(rowdata,celldata,setting){
	                	    	 if(rowdata.year){
	                	    		 setting.url = '../../../queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1&budg_year='+rowdata.year;
	                	    	 }else{
	                	    		 $.etDialog.error('请先填写年度');
	                	    		 return false ;
	                	    	 }
	                	    }
						},
					},
					{display: '科室年度收入预算(元)', name: 'yearValue', align: 'right', width: 120,editable:false,
						render:function(ui) {
							if (ui.rowData.yearValue) {
								return formatNumber(ui.rowData.yearValue, 2, 1);
							}
						}
					},
					{display: '01月(元/E)', name: 'm01', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m01) {
								return formatNumber(ui.rowData.m01, 2, 1);
							}
						}
					},
					{display: '02月(元/E)', name: 'm02', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m02) {
								return formatNumber(ui.rowData.m02, 2, 1);
							}
						}
					},
					{display: '03月(元/E)', name: 'm03', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m03) {
								return formatNumber(ui.rowData.m03, 2, 1);
							}
						}
					},
					{display: '04月(元/E)', name: 'm04', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m04) {
								return formatNumber(ui.rowData.m04, 2, 1);
							}
						}
					},
					{display: '05月(元/E)', name: 'm05', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m05) {
								return formatNumber(ui.rowData.m05, 2, 1);
							}
						}
					},
					{display: '06月(元/E)', name: 'm06', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m06) {
								return formatNumber(ui.rowData.m06, 2, 1);
							}
						}
					},
					{display: '07月(元/E)', name: 'm07', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m07) {
								return formatNumber(ui.rowData.m07, 2, 1);
							}
						}
					},
					{display: '08月(元/E)', name: 'm08', align: 'right', width: 120, dataType:'float',
						render:function(ui) {
							if (ui.rowData.m08) {
								return formatNumber(ui.rowData.m08, 2, 1);
							}
						}
					},
					{display: '09月(元/E)', name: 'm09', align: 'right', width: 120, dataType:'float',
						render:function(ui) {
							if (ui.rowData.m09) {
								return formatNumber(ui.rowData.m09, 2, 1);
							}
						}
					},
					{display: '10月(元/E)', name: 'm10', align: 'right', width: 120,dataType:'float',
						render:function(ui) {
							if (ui.rowData.m10) {
								return formatNumber(ui.rowData.m10, 2, 1);
							}
						}
					},
					{display: '11月(元/E)', name: 'm11', align: 'right', width: 120, dataType:'float',
						render:function(ui) {
							if (ui.rowData.m11) {
								return formatNumber(ui.rowData.m11, 2, 1);
							}
						}
					},
					{display: '12月(元/E)', name: 'm12', align: 'right', width: 120, dataType:'float',
						render:function(ui) {
							if (ui.rowData.m12) {
								return formatNumber(ui.rowData.m12, 2, 1);
							}
						}
					}

				],
				dataModel: {
					method: 'POST',
					location: 'remote',
					url: 'queryDeptMonthMedInBudgUp.do?isCheck=false',
					recIndx: 'year'
				},
				usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
				addRowByKey: true, inWindowHeight: true, freezeCols: 6,
				toolbar: {
					items: [
						{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
						{ type: "button", label: '增量生成', icon: 'plus', listeners: [{ click: generate }] },
						/* { type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_row}] }, */
						{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
						{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
						{ type: "button", label: '下载导入模板', icon: 'arrowthickstop-1-s', listeners: [{ click: downTemplate }] },
						{ type: "button", label: '导入', icon: 'arrowthick-1-n', listeners: [{ click: imp }] },
						{ type: "button", label: '打印', icon: 'script', listeners: [{ click: printDate }] },
						{ type: "button", label: '预算分解维护', icon: 'folder-open', listeners: [{ click: openRatePage }] },
					]
				}
			});
		}

		 //根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
	    function setEdit(ui){
	   		 if(ui.rowData && ui.rowData.group_id){
	   			 return false ;
	   		 }else{
	   			 return true ;
	   		 }
	    }
		
	    //选择指标后 更新指标编码 
	    function reloadDeptCode(rowdata,celldata){
	    	setTimeout(function () {
	    		grid.updateRow(rowdata._rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
	    	}, 10);
	    }	 
		 
		//添加行
		function add_row(){
			grid.addRow();
		}
		
		//选择 科室后 查询其上年业务量 并在行数据中添加上年业务量数据
	    function queryLastYearWorkload(rowdata,celldata){
			setTimeout(function(){   // 加延时定时器 让数据同步
				grid.updateRow(rowdata._rowIndx,{'subj_code':rowdata.subj_name.split(" ")[0]})
				   if(rowdata.year && rowdata.subj_code && rowdata.dept_id){
					   
					   $.post("queryDeptyearValue.do?isCheck=false&year="+rowdata.year+"&subj_code="+rowdata.subj_code+"&dept_id="+rowdata.dept_id,null,function(responseData){
						   if(responseData){
							   var para = eval("("+responseData+")") ;
							   if(para){
								  grid.updateRow(rowdata._rowIndx,{'yearValue':para.yearValue});
							  }
						   }else{
							  $.etDialog.error('该科室科目没有科室年度预算数据!');
						  }
					});
				   }
			},300)
	    }
		
		
		function add_open() {
			$.ligerDialog.open({
				url: 'deptMonthMedInBudgAddPage.do?isCheck=false', data: {}, height: 500, width: 800,
				title: '科室月份医疗收入预算', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			});
		}

		function openRatePage() {
			var year =  year_input.getValue() ;
			var subj_code = subj_name_select.getValue() ;
			
			if( !year){
				$.etDialog.error('预算年度不能为空');
				return false ;
			}
			/* if( !subj_code){
				$.etDialog.error('预算科目不能为空');
				return false ;
			} */
			$.ligerDialog.open({
				url: 'deptMonthMedInBudgResolveMainPage.do?isCheck=false&year='+year+'&subj_code='+subj_code, data: {}, height: 500, width: 800,
				title: '科室月份医疗收入预算分解维护', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,

			});
		}
		function remove() {
			var data = grid.selectGet();
			if (data.length == 0) {
				$.etDialog.error('请选择行');
			} else {
				var ParamVo = [];//后台删除数据
				var deletePageRow = [];// 页面删除数据
				$(data).each(function () {
					if (this.rowData.group_id) {
						ParamVo.push(
							this.rowData.group_id + "@" +
							this.rowData.hos_id + "@" +
							this.rowData.copy_code + "@" +
							this.rowData.year + "@" +
							this.rowData.subj_code + "@" +
							this.rowData.dept_id
						)
					} else {
						deletePageRow.push(this);
					}
				});
				if (ParamVo.length > 0) {
					$.etDialog.confirm('确定删除?',function (){
						ajaxPostData({
							url: "deleteDeptMonthMedInBudgUp.do?isCheck=false",
							data: { ParamVo: ParamVo.toString() },
							success: function (res){
								if (res.state == "true") {
									query();
								}
							}
						})
					});
				} else if (deletePageRow.length > 0) {
					grid.deleteRows(deletePageRow);
					$.etDialog.success("删除成功!");
				}
			}
		}
		
		function imp() {
			$.etDialog.open({
				url: 'deptMonthMedInBudgImportPage.do?isCheck=false',
				title: '导入',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				isMax: true
			});
		}
		function downTemplate() {

			location.href = "downTemplate.do?isCheck=false";
		}

		//保存
	    function save (){
		   
			var data = grid.getChanges();
	    	
			var ParamVo =[];
			if( data.updateList.length > 0){
	        	
	            var updateData = data.updateList ;
                $(updateData).each(function (){	
            	    ParamVo.push(this.year + "@" + "01" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m01 ? this.m01 : "-1"));
					ParamVo.push(this.year + "@" + "02" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m02 ? this.m02 : "-1"));
					ParamVo.push(this.year + "@" + "03" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m03 ? this.m03 : "-1"));
					ParamVo.push(this.year + "@" + "04" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m04 ? this.m04 : "-1"));
					ParamVo.push(this.year + "@" + "05" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m05 ? this.m05 : "-1"));
					ParamVo.push(this.year + "@" + "06" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m06 ? this.m06 : "-1"));
					ParamVo.push(this.year + "@" + "07" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m07 ? this.m07 : "-1"));
					ParamVo.push(this.year + "@" + "08" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m08 ? this.m08 : "-1"));
					ParamVo.push(this.year + "@" + "09" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m09 ? this.m09 : "-1"));
					ParamVo.push(this.year + "@" + "10" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m10 ? this.m10 : "-1"));
					ParamVo.push(this.year + "@" + "11" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m11 ? this.m11 : "-1"));
					ParamVo.push(this.year + "@" + "12" + "@" + this.dept_id + "@" + this.subj_code + "@" + (this.m12 ? this.m12 : "-1"));
	   			});
                ajaxPostData({
					url: "updateDeptMonthMedInBudgUp.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
				})
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
				if (!v.year) {
					rowm+="[年度]、";
				}
				if (!v.subj_name) {
					rowm+="[科目名称]、";
				}
				if (!v.dept_name) {
					rowm+="[科室名称]、";
				}
				if(rowm != ""){
					rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
				}
				msg += rowm;
				var key=v.year + v.subj_code + v.dept_id 
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
		/* 
		//打印
		function printDate() {
			if (grid.getAllData().length == 0) {
				$.etDialog.error("无打印数据！");
				return;
			}

			grid.options.parms = [];
			grid.options.newPage = 1;

			grid.options.parms.push({ name: 'year', value: year_input.getValue() });
			grid.options.parms.push({ name: 'subj_code', value: subj_name_select.getValue() });
			grid.options.parms.push({ name: 'subj_level', value: subj_level_select.getValue() });
			grid.options.parms.push({ name: 'dept_id', value: dept_name_select.getValue() });
			var selPara = {};
			$.each(grid.options.parms, function (i, obj) {
				selPara[obj.name] = obj.value;
			});
			var printPara = {
				headCount: 2,
				title: "",
				type: 3,
				columns: grid.getColumns(1)
			};
			ajaxJsonObjectByUrl("queryDeptMonthMedInBudgUp.do?isCheck=false", selPara, function (responseData) {
				printGridView(responseData, printPara);
			});
		} */
		var printDate = function () {
	    	if(grid.getAllData()==null){
	    		$.etDialog.error("请先查询数据！");
				return;
			}
	    	var heads={
	        		 /* "isAuto":true,//系统默认，页眉显示页码
	        		"rows": [
	    	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
	        		]  */}; 
	    	var printPara={
	            title: "科室月份医疗收入预算信息",//标题
	            columns: JSON.stringify(grid.getPrintColumns()),//表头
	            class_name: "com.chd.hrp.budg.service.budgincome.toptodown.deptmonthinbudg.MedInDMBudgService",
	            method_name: "queryDeptMontBudgUpData",
	            bean_name: "medInDMBudgService",
	            heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	            foots: '',//表尾需要打印的查询条件,可以为空 
	        };
	        $.each(grid.getUrlParms(),function(i,obj){
	            printPara[obj.name]=obj.value;
	        }); 
	     	
	        officeGridPrint(printPara);
	    	
	    	
	    };
		//增量生成
		function generate() {
			var year = year_input.getValue();
			if (year) {
				ajaxPostData({
					url: "generate.do?isCheck=false&year=" + year,
					data: {},
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
				})
			} else {
				$.etDialog.error("预算年度不能为空");
			}
		}
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('A', add);
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

	<div id="toptoolbar"></div>
	
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
	<div id="maingrid"></div>

</body>

</html>