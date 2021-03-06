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
	<jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
var sup_no;
	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#sign_date"),required : true},
				{el : $("#break_money"),required : true,type: 'number'},
				{el : $("#pact_code"),required : true},
				{el : $("#start_date"),required : true},
				{el : $("#end_date"),required : true},
				{el : $("#sup_no"),required : true},
				{el : $("#party"),required : true},
				{el : $("#first_man"),required : true},
				{el : $("#second_man"),required : true}
				]
		});
		if(!formValidate.test()){
			return;
		};
		ajaxPostData({
			url : 'updatePactBreakFKHT.do',
			data : {
				break_code: $("#break_code").val(),
				sign_date: sign_date.getValue(),
				break_money: $("#break_money").val(),
				party : party.getValue(),
				start_date : start_date.getValue(),
				end_date : end_date.getValue(),
				sup_no : sup_no.getValue(),
				pact_code : '${entity.pact_code }',
				note : $("#note").val(),
				first_man : $("#first_man").val(),
				second_man : $("#second_man").val(),
				break_reason : $("#break_reason").val(),
				break_process : $("#break_process").val(),
				break_result : $("#break_result").val(),
			},
			success : function(data) {
				var parentFrameName = parent.$.etDialog.parentFrameName;
				var parentWindow = parent.window[parentFrameName];
				parentWindow.query(); 
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        parent.$.etDialog.close(curIndex); 
			},
			delayCallback : true
		})
	}

    var initSelect=  function(){
    	first_man = $("#first_man").etSelect({url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',defaultValue: "${entity.first_man}"});
    	party = $("#party").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=PARTY',defaultValue: "${entity.party}"});
    	sup_no = $("#sup_no").etSelect({options: [ { id: 0, text: '${entity.sup_name }' }], defaultValue: "0"});
    	pact_code = $("#pact_code").etSelect({options: [ { id: 0, text: '${entity.pact_name }' }], defaultValue: "0" });
      }
    
	$(function(){
    	initSelect();
   		initfrom();
   		
		$("#save").on("click", function () {
			save();
		})
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
		
		if ("${entity.state}" != "01") {
			$("#sign_date").attr("disabled" , "disabled");
   			$("#start_date").attr("disabled" , "disabled");
   			$("#end_date").attr("disabled" , "disabled");
   			party.disabled();
   			$("#break_money").attr("disabled" , "disabled");
   			$("#break_money").attr("style","background-color:#EAEAEA");
   			$("#second_man").attr("disabled" , "disabled");
   			$("#second_man").attr("style","background-color:#EAEAEA");
   			first_man.disabled();
   			$("#note").attr("disabled" , "disabled");
   			$("#note").attr("style","background-color:#EAEAEA");
   			$("#break_reason").attr("disabled" , "disabled");
   			$("#break_process").attr("disabled" , "disabled");
   			$("#break_result").attr("disabled" , "disabled");
   			$("#save").attr("disabled" , "disabled");
   			$("#save").attr("style","background-color:#EAEAEA");
		}
	})
	
	  //日期
	var initfrom = function(){
		sign_date = $("#sign_date").etDatepicker({
			defaultDate: true,
		});
		start_date = $("#start_date").etDatepicker({
			defaultDate: true,
			onChange(date){
				end_date = $("#end_date").etDatepicker({
					defaultDate: true,
					minDate:date
				});
				end_date.setValue(date);
			}
		});
		end_date = $("#end_date").etDatepicker({
			defaultDate: true,
			onChange(date){
				var start = start_date.getValue();
				if(start > date){
					end_date.setValue(start);
				}
			}
		});
	};
</script>
</head>

<body style="overflow: scroll; ">
	<div>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">登记单号：</td>
				<td class="ipt"><input id="break_code" type="text" disabled="disabled" value="${entity.break_code }" style="background-color: #EAEAEA"/></td>
				<td class="label no-empty" style="width: 100px;">委托人：</td>
				<td class="ipt"><select id="sup_no" style="width: 180px;" disabled="disabled"></select> </td>
				<td class="label no-empty" style="width: 100px;">合同名称：</td>
				<td class="ipt"><select id="pact_code" style="width: 180px;" disabled="disabled"></select> </td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">登记日期：</td>
				<td class="ipt"><input id="sign_date" style="width: 180px;"/></td>
				<td class="label no-empty" style="width: 100px;">违约方：</td>
				<td class="ipt"><select id="party" style="width: 180px;"></select></td>
				<td class="label no-empty" style="width: 100px;">违约金额：</td>
				<td class="ipt"><input id="break_money" type="text" value="${entity.break_money }"/></td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 120px;">甲方负责人：</td>
				<td class="ipt"><input id="first_man" type="text" style="width: 180px"> </td>
				<td class="label no-empty" style="width: 100px;">乙方负责人：</td>
				<td class="ipt"><input id="second_man" type="text" value="${entity.second_man }"> </td>
				<td class="label" style="width: 100px;">备注：</td>
				<td class="ipt" colspan="5"><input id="note" type="text" value="${entity.note }"> </td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">开始日期：</td>
				<td class="ipt"><input id="start_date" style="width: 180px;"/></td>
				<td class="label no-empty" style="width: 100px;">结束日期：</td>
				<td class="ipt"><input id="end_date" style="width: 180px;"/></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">违约原因：</td>
				<td class="ipt" colspan="5"><textarea id="break_reason" style="height: 110px;width: 840px;margin-top: 15px">${entity.break_reason }</textarea></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">处理程序：</td>
				<td class="ipt" colspan="5"><textarea id="break_process" style="height: 110px;width: 840px;margin-top: 15px">${entity.break_process }</textarea></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">处理结果：</td>
				<td class="ipt" colspan="5"><textarea id="break_result" style="height: 110px;width: 840px;margin-top: 15px">${entity.break_result }</textarea></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>
</html>

