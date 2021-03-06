<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	$(function() {
		loadDict();
		loadForm();
	});

	function save() {
		var formPara = {
			mr_no : $("#mr_no").val(),
			in_hos_no : $("#in_hos_no").val(),
			advice_date : $("#advice_date").val(),
			order_by_id : liger.get("order_by_code").getValue().split(".")[0],
			order_by_no : liger.get("order_by_code").getValue().split(".")[1],
			perform_by_id : liger.get("perform_by_code").getValue().split(".")[0],
			perform_by_no : liger.get("perform_by_code").getValue().split(".")[1],
			mate_code : liger.get("mate_code").getValue(),
			amount : $("#amount").val(),
			price : $("#price").val(),
			recipe_type_code : liger.get("recipe_type_code").getValue(),
			place : $("#place").val()
		};
		ajaxJsonObjectByUrl("updateHtcgMaterialAdvice.do", formPara, function(
				responseData) {
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

	function upateMaterialAdvice() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#order_by_code",
				"../../base/queryHtcgDeptDict.do?isCheck=false", "id", "text",
				true, true);
		liger.get("order_by_code").setValue('${order_by_id}.${order_by_no}');
		liger.get("order_by_code").setText('${order_by_name}');
		autocomplete("#perform_by_code",
				"../../base/queryHtcgDeptDict.do?isCheck=false", "id", "text",
				true, true);
		liger.get("perform_by_code").setValue(
				'${perform_by_id}.${perform_by_no}');
		liger.get("perform_by_code").setText('${perform_by_name}');
		autocomplete("#mate_code",
				"../../base/queryhtcMaterialDict.do?isCheck=false", "id", "text",
				true, true);
		liger.get("mate_code").setValue('${mate_code}');
		liger.get("mate_code").setText('${mate_name}');
		autocomplete("#recipe_type_code",
				"../../base/queryHtcgRecipeTypeDict.do?isCheck=false", "id",
				"text", true, true);
		liger.get("recipe_type_code").setValue('${recipe_type_code}');
		liger.get("recipe_type_code").setText('${recipe_type_name}');
		$("#mr_no").ligerTextBox({
			disabled : true
		});
		$("#in_hos_no").ligerTextBox({
			disabled : true
		});
		$("#advice_date").ligerTextBox({
			disabled : true
		});
		$("#order_by_code").ligerTextBox({
			disabled : true
		});
		$("#perform_by_code").ligerTextBox({
			disabled : true
		});
		$("#mate_code").ligerTextBox({
			disabled : true
		});
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">病案号：</td>
				<td align="left" class="l-table-edit-td"><input name="mr_no" type="text" id="mr_no" ltype="text" value='${mr_no}' validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">住院号：</td>
				<td align="left" class="l-table-edit-td"><input name="in_hos_no" type="text" id="in_hos_no" ltype="text" value='${in_hos_no}' validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">医嘱时间：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate" name="advice_date" type="text" id="advice_date" value='${advice_date}' ltype="text"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">开单科室：</td>
				<td align="left" class="l-table-edit-td"><input name="order_by_code" type="text" id="order_by_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">执行科室：</td>
				<td align="left" class="l-table-edit-td"><input name="perform_by_code" type="text" id="perform_by_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">材料：</td>
				<td align="left" class="l-table-edit-td"><input name="mate_code" type="text" id="mate_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">数量：</td>
				<td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text" value='${amount}' validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">单价：</td>
				<td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" value='${price}' validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">医嘱时效：</td>
				<td align="left" class="l-table-edit-td"><input name="recipe_type_code" type="text" id="recipe_type_code" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">病人地点：</td>
				<td align="left" class="l-table-edit-td"><input name="place" type="text" id="place" ltype="text" value='${place}' /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
