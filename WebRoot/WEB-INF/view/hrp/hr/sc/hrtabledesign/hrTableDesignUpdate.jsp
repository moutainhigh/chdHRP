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
	var super_code;
	$(function() {
		loadDict()//加载下拉框
		loadForm();
	});

	function save() {

		var formPara = {

				design_code : $("#design_code").val(),
				design_name : $("#design_name").val(),
				super_code : liger.get("super_code").getValue(),
				design_sort : $("#design_sort").val(),
				is_stop : liger.get("is_stop").getValue(),
				design_note : $("#design_note").val()

		};
		ajaxJsonObjectByUrl("updateHrTableDesign.do?isCheck=false", formPara, function(
				responseData) {
			if (responseData.state == "true") {
				parentFrameUse().tree.reload();
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

	function saveDesign() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		/* comboTree = $("#super_code").ligerComboBox({
			treeLeafOnly:false,
			tree: {url : 'queryHrTableDesignTree.do?isCheck=false', checkbox: false, ajaxType: 'post', idField: 'id', idFieldName: 'text', parentIDField:'pid'},
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160,
			readonly: true
		}); */
		
		super_code = $("#super_code").ligerComboBox({
            url: '../../queryHrTableType.do?is_copy_codeheck=false',
            valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
            autocomplete: false,keySupport:true,
            width: 160 
        });
		
		super_code.setValue("${design.super_code}");

		$("#is_stop").ligerComboBox({
			data: [
                { text: '否', id: '0' },
                { text: '是', id: '1' }
            ],
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			value : "${design.is_stop}",
			width : 160
		});
		
		/* $("#is_last").ligerComboBox({
			data: [
                { text: '否', id: '0' },
                { text: '是', id: '1' }
            ],
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			value : ${design.is_last},
			width : 160,
			readonly: true
		}); */
		
		$("#design_code").ligerTextBox({
			width : 160,
			disabled: true
		});
		$("#design_name").ligerTextBox({
			width : 160
		});

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">上级编码：</td>
				<td align="left" class="l-table-edit-td">
					<input name="super_code" type="text" id="super_code" ltype="text" required="true" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">设计器编码：</td>
				<td align="left" class="l-table-edit-td">
					<input name="design_code" type="text" id="design_code" value="${design.design_code}" ltype="text" required="true" validate="{required:true,maxlength:100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">设计器名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="design_name" type="text" id="design_name" value="${design.design_name}" ltype="text" required="true" validate="{required:true,maxlength:100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">排序号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="design_sort" type="text" id="design_sort" value="${design.design_sort}" ltype="text" required="true" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
				<!-- <td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否末级：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td> -->
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">备注：</td>
				<td align="left" class="l-table-edit-td" colspan="5">
					<textarea name="design_note" id="design_note" rows="4" cols="5" style="width: 100%;">${design.design_note}</textarea>
				</td>

			</tr>
		</table>
	</form>

</body>
</html>
