<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager;
	$(function() {

		loadDict();

		loadForm();
		loadHead(null);
	});

	function save() {
		
		var formPara = {
			cost_type_id :   '${cost_type_id}',
			cost_type_code : $("#cost_type_code").val(),
			cost_type_name : $("#cost_type_name").val(),

		};
		ajaxJsonObjectByUrl("updateHtcCostTypeDict.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				parent.query();
			}
		});
	}

	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}

	function saveCostTypeDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		$("#cost_type_code").ligerTextBox({disabled:true});
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">成本类型编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="cost_type_code" type="text" id="cost_type_code"
					disabled="disabled" ltype="text" value="${cost_type_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">成本类型名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="cost_type_name" type="text" id="cost_type_name" ltype="text" value="${cost_type_name}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>

