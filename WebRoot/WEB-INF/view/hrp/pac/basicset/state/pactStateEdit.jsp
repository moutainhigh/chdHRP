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
	var update = function() {
		formValidate = $.etValidate({
			items : [ {
				el : $("#state_name"),
				required : true
			}]
		});
		if(!formValidate.test()){
			return;
		};
		
		ajaxPostData({
			url : 'updatePactState.do',
			data : {
				state_code : $("#state_code").val(),
				state_name : $("#state_name").val(),
				is_stop : $("#is_stop").val(),
				note : $("#note").val(),

			},
			success : function() {
				parent.window.query();
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
				parent.$.etDialog.close(curIndex);
			},
			delayCallback : true
		})
	}

	  $(function () {
         $("#is_stop").etSelect({
			  options: [
			    { id: 0, text: '否' },
			    { id: 1, text: '是' }
			  ],
			  defaultValue: "${is_stop }"
		})
     })
</script>
</head>

<body>
	<table class="table-layout">
		<tr>
			<td class="label" style="width: 100px;">状态编码：</td>
			<td class="ipt"><input id="state_code" type="text" disabled="disabled" style="background-color: #DBDBDB;" value="${state_code }"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">状态名称：</td>
			<td class="ipt"><input id="state_name" name="nature_name" type="text" value="${state_name }"/></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">是否停用：</td>
			<td class="ipt"><select id="is_stop"  style="width: 180px"></select></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">备注：</td>
			<td class="ipt"><input id="note" type="text" name="note" value="${note }"/></td>
		</tr>
	</table>
</body>

</html>

