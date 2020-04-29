<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
			peop_type_code : $("#peop_type_code").val(),
			peop_type_name : $("#peop_type_name").val(),
			peop_type_desc : $("#peop_type_desc").val(),
			is_stop:liger.get("is_stop").getValue()
		};
		ajaxJsonObjectByUrl("updateHtcPeopleTypeDict.do", formPara, function(responseData) {
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
					element.ligerTip({content : lable.html(),target : element[0]});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({content : lable.html(),target : element[0]});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form.l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}

	function savePeopleTypeDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		
		$("#peop_type_code").ligerTextBox({ disabled: true });
		
		autocomplete("#is_stop", "../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,"${is_stop}");
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">人员类别编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="peop_type_code" type="text" id="peop_type_code" ltype="text"
					value="${peop_type_code}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">人员类别名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="peop_type_name" type="text" id="peop_type_name" ltype="text"
					value="${peop_type_name}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td">
				 <input name="is_stop" type="text" id="is_stop" ltype="text"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">描述：</td>
				<td align="left" class="l-table-edit-td" colspan="2"> 
                <textarea cols="100" rows="4" class="l-textarea" id="peop_type_desc" name="peop_type_desc" style="width:300px;resize:none" validate="{maxlength:100}">${peop_type_desc}</textarea>
                </td>
			</tr>
			
		</table>
	</form>
</body>
</html>
