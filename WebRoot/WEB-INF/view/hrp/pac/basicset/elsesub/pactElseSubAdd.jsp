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
	<jsp:param value="hr,tree,grid,select,dialog,validate" name="plugins" />
</jsp:include>

<script type="text/javascript">
	var save = function() {
		formValidate = $.etValidate({
			items : [ {
				el : $("#sub_code"),
				required : true
			}, {
				el : $("#sub_name"),
				required : true
			}]
		});
		if(!formValidate.test()){
			return;
		};
		ajaxPostData({
			url : 'addPactElseSub.do?isCheck=false',
			data : {
				sub_code : $("#sub_code").val(),
				sub_name : $("#sub_name").val(),
				is_stop : $("#is_stop").val(),
				note : $("#note").val(),

			},
			success : function() {
				var check = $("#checkbox").is(":checked");
				if (check) {
					$("#sub_code").val("");
					$("#sub_name").val("");
					$("#note").val("");
					$("#is_stop").val(0);
				}else{
					var curIndex = parent.$.etDialog.getFrameIndex(window.name);
					parent.$.etDialog.close(curIndex);
				}
				parent.window.query();
			},
			delayCallback : true
		})
	}

	$(function(){
		$("#is_stop").etSelect({
			  options: [
			    { id: 0, text: '否' },
			    { id: 1, text: '是' }
			  ],
			  defaultValue: "0"
		})
	})
</script>
</head>

<body>
	<table class="table-layout">
		<tr>
			<td class="label  no-empty" style="width: 100px;">标的物编码：</td>
			<td class="ipt"><input id="sub_code" type="text" /></td>
		</tr>
		<tr>
			<td class="label  no-empty" style="width: 100px;">标的物名称：</td>
			<td class="ipt"><input id="sub_name" type="text" /></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">是否停用：</td>
			<td class="ipt"><select id="is_stop" style="width: 180px"></select></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">备注：</td>
			<td class="ipt"><input id="note" type="text" /></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;"><input id="checkbox"
				type="checkbox" checked="checked" value="off" />连续添加</td>
		</tr>
	</table>
</body>

</html>

