<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,select,validate,grid,upload,datepicker,dialog"
		name="plugins" />
</jsp:include>
<script>
	var leftGrid, RightGrid;

	var initForm = function() {
		app_date = $("#app_date").etDatepicker();
		//主表保存
		$("#save").click(function() {
			var param = [];
		      var gridAllData = leftgrid.getAllData();
		      if(gridAllData!=null){
		    	  gridAllData.forEach(function (item) {
		    		  	if(item.dept_code!=null || item.dept_code!=undefined){
			          		md_dept_id =item.dept_code.split('@')[1]
			          		param.push({md_dept_id})
			          	}
			          }) 
		      }
		    

			if ($("#bill_no").val() == "") {
				$.etDialog.error('申请单号不能为空');
				return;
			}
			if ($("#app_date").val() == "") {
				$.etDialog.error('申请日期不能为空');
				return;
			}
			if ($("#dept_code").val() == "") {
				$.etDialog.error('科室不能为空');
				return;
			}
			if ($("#case_no").val() == "") {
				$.etDialog.error('病案号不能为空');
				return;
			}
		
			var formPara = {
				bill_no : $("#bill_no").val(),
				app_date : $("#app_date").val(),
				dept_id : $("#dept_id").val(),
				patient : $("#patient").val(),
				diagnose : $("#diagnose").val(),
				case_no : $("#case_no").val(),
				reason : $("#reason").val(),
				bed_no : $("#bed_no").val(),
				 Param: JSON.stringify(param),

			};

			ajaxPostData({
				url : 'updateMeetDiagApplication.do',
				data : formPara,
				success : function() {
					 var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                     
                     var parentFrameName = parent.$.etDialog.parentFrameName;
                     var parentWindow = parent.window[parentFrameName];
                     parentWindow.query(); 
                     parent.$.etDialog.close(curIndex);
				}
			})
		});

		//提交
		$("#submit").click(function() {
			if ($("#bill_no").val() == "") {
				$.etDialog.error('申请单号不能为空');
				return;
			}
			if ($("#app_date").val() == "") {
				$.etDialog.error('申请日期不能为空');
				return;
			}
			if ($("#dept_code").val() == "") {
				$.etDialog.error('科室不能为空');
				return;
			}
			if ($("#case_no").val() == "") {
				$.etDialog.error('病案号不能为空');
				return;
			}
			var formPara = {
					bill_no : $("#bill_no").val(),
					app_date : $("#app_date").val(),
					dept_id : $("#dept_id").val(),
					patient : $("#patient").val(),
					diagnose : $("#diagnose").val(),
					case_no : $("#case_no").val(),
					reason : $("#reason").val(),
					bed_no : $("#bed_no").val(),
				};

		
				$.etDialog.confirm('确定提交?', function() {
					ajaxPostData({
						url : 'confirmMeetDiagAdd.do?isCheck=false',
						data : formPara,
						success : function() {
						}
					})
				});
			


		});
		//撤回
		$("#cancel").click(function() {
			var formPara = {
					bill_no : $("#bill_no").val(),
					app_date : $("#app_date").val(),
					dept_id : $("#dept_id").val(),
					patient : $("#patient").val(),
					diagnose : $("#diagnose").val(),
					case_no : $("#case_no").val(),
					reason : $("#reason").val(),
					bed_no : $("#bed_no").val(),
				};
			
				$.etDialog.confirm('确定撤回?', function() {
					ajaxPostData({
						url : 'reConfirmMeetDiagAdd.do?isCheck=false',
						data : formPara,
						success : function() {
						}
					})
				});
		

		});

		// 添加行
		$("#leftAdd").click(function() {
			leftgrid.addRow();
		});
	
		// 历史记录查询
		$("#queryHistroy").click(function() {
			if ($("#bill_no").val() == "") {
				$.etDialog.error('申请单号不能为空');
				return;
			}
		
			if ($("#dept_code").val() == "") {
				$.etDialog.error('科室不能为空');
				return;
			}
			if ($("#case_no").val() == "") {
				$.etDialog.error('病案号不能为空');
				return;
			}

			ajaxPostData({
				url : 'queryHistroy.do?isCheck=false',
				data : {
					bill_no : $("#bill_no").val(),
					dept_id : $("#dept_id").val(),
					case_no : $("#case_no").val()
				},
				success : function() {
					leftgrid.deleteRows();
				}
			})
		});
	
	};

	var initLeftGrid = function() {
		var columns = [  {
			display : '会诊科室',
			name : 'md_dept_name',
			width : 160,	
        	editor: {
                type: 'select',
                keyField: 'md_dept_code',
                url:'../../queryHosDeptSelect.do?isCheck=false',
            }  

		}, ];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			selectionModel : {
				type : 'row'
			},
			showBottom : false,
			dataModel : {
		      url: 'queryMeetDetail.do?isCheck=false',
		      postData:{
					bill_no : $("#bill_no").val(),
					app_date : $("#app_date").val(),
					dept_id : $("#dept_id").val(),
					patient : $("#patient").val(),
					diagnose : $("#diagnose").val(),
					case_no : $("#case_no").val(),
					reason : $("#reason").val(),
					bed_no : $("#bed_no").val(),
				},
			},
			
			editable : true,
			columns : columns,
		};
		leftgrid = $("#leftGrid").etGrid(paramObj);
	};
	var initRightGrid = function() {
		var columns = [ {
			display : '申请单号',
			name : 'bill_no',
			width : 300
		},
		{
			display : '会诊时间',
			name : 'app_date',
			width : 300
		},
		{
			display : '会诊结果',
			name : 'diagnose',
			width : 300
		}];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			selectionModel : {
				type : 'row'
			},
			showBottom : false,
			dataModel : {
			// url: '.do?isCheck=false'
			},
			editable : true,
			columns : columns,
		};
		rightgrid = $("#rightGrid").etGrid(paramObj);
	};

	$(function() {
		// initValidate();
		initForm();
		initLeftGrid();
		initRightGrid();
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout" style="width: 100%;">
			<tr>
			<input id=dept_id   value="${dept_id}" type="hidden" />
				<td class="label">申请单号：</td>
				<td class="ipt"><input id="bill_no" type="text" disabled 
					value="${bill_no}" /></td>
				<td class="label">科室名称：</td>
				<td class="ipt"><input id=dept_name  disabled  value="${dept_name}" type="text" /></td>
				<td class="label">病案号：</td>
				<td class="ipt"><input id="case_no" type="text"  value="${case_no}" disabled /></td>
			</tr>
			<tr>
				<td class="label">患者姓名：</td>
				<td class="ipt"><input id="patient" type="text" value="${patient}" /></td>
				<td class="label">病床号：</td>
				<td class="ipt"><input id="bed_no" type="text" value="${bed_no}" /></select>
				</td>
				<td class="label">诊断：</td>
				<td class="ipt"><input id="diagnose" type="text" value="${diagnose}" /></td>
			</tr>
			<tr>
			<td class="label">申请日期：</td>
				<td class="ipt"><input id="app_date" type="text" value="${app_date}" disabled /></td>
			<td class="label">申请原因：</td>
				<td class="ipt"><textarea id="reason" rows="10" cols="25">${reason}</textarea></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
		<button id="save">保存</button>
		<button id="submit">提交</button>
	<!-- 	<button id="cancel">撤回</button> -->
		<!-- <button id="print">打印</button> -->
	</div>
	<div class="flex-wrap">
		<div class="flex-item-1 single-block">
			<div class="flex-wrap">
			<div class="button-group align-left small">
					<button id="leftAdd">添加</button>
				</div>
				<span class="flex-item-1 align-right" style="line-height: 30px;">>>会诊科室</span>
			</div>
			<div id="leftGrid"></div>
		</div>
		<div class="flex-item-1 single-block">
			<div class="flex-wrap">
				<div class="button-group align-left small">
					<button id="queryHistroy">查询会诊记录</button>
				</div>
				<span class="flex-item-1 align-right" style="line-height: 30px;">>>历次会诊记录</span>
			</div>
			<div id="rightGrid"></div>
		</div>
	</div>
</body>

</html>