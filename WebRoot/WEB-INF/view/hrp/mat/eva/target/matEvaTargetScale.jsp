<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	
	$(function(){
		$("#scale_code").ligerTextBox({width:180, disabled: true});
		$("#scale_name").ligerTextBox({width:180});
		$("#scale_point").ligerTextBox({width:180, number: true});
		$("#high_point").ligerTextBox({width:180, number: true}); 
		$("#low_point").ligerTextBox({width:180, number: true});
		$("#sort_code").ligerTextBox({width:180, digits: true});
		
		var matEvaScale = parentData.matEvaScale;
		if(matEvaScale){
			$('#scale_code').val(matEvaScale.scale_code);
			//liger.get("scale_code").setDisabled();
			$('#scale_name').val(matEvaScale.scale_name);
			$('#scale_content').val(matEvaScale.scale_content);
			$('#scale_point').val(matEvaScale.scale_point);
			$('#high_point').val(matEvaScale.high_point);
			$('#low_point').val(matEvaScale.low_point);
			$('#sort_code').val(matEvaScale.sort_code);
			//liger.get("sort_code").setEnabled();
			if(matEvaScale.is_stop == "1"){
				$('#is_stop1').prop("checked", "checked");
			}
		}
	})
	
	function saveScale(flag){
		if(!$("#scale_code").val()) {
			$.ligerDialog.warn("标度代码不能为空！");
			return false;
		}
		if(!$("#scale_name").val()) {
			$.ligerDialog.warn("标度名称不能为空！");
			return false;
		} 
		if(!$("#scale_point").val()) {
			$.ligerDialog.warn("标度比例不能为空！");
			return false;
		}else if($("#scale_point").val() > 1){
			$.ligerDialog.warn("标度比例不能大于1！");
			return false;
		}

		var rowData = {
			_code: parentData._code, 
			scale_code: $("#scale_code").val(),
			scale_name: $("#scale_name").val(),
			scale_content: $("#scale_content").val(),
			scale_point: $("#scale_point").val(),
			high_point: $("#high_point").val(),
			low_point: $("#low_point").val(),
			sort_code: $("#sort_code").val(),
			is_stop: $('.is_stop:checked').val(), 
		};
		parent.grid.updateRow(parentData.rowindex, rowData);
		thisClose();
	}
	
	function thisClose(){
 		frameElement.dialog.close();
	} 
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table class="table-layout" style="margin:10px;border-bottom: 1px solid #f1f1f1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>标度代码：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="scale_code" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>标度名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="scale_name" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				标度内容：
			</td> 
			<td align="left" class="l-table-edit-td">
				<textarea id="scale_content" rows="3" style="width: 180px;"></textarea>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>标度比例：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="scale_point" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				上限值：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="high_point" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				下限值：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="low_point" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				排序号：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="sort_code" />
			</td>
		</tr>
		<tr> 
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>是否停用：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="radio" name="is_stop" class="is_stop" id="is_stop1" value="1" />是 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="is_stop" class="is_stop" id="is_stop0" value="0" checked/>否
			</td>
		</tr>
	</table>
	<div align="center" style="margin-top: 60px;">
		<button class="l-button l-button-test" onclick="saveScale();">保存</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="l-button l-button-test" onclick="thisClose();">关闭</button>
	</div>
</body>
</html>