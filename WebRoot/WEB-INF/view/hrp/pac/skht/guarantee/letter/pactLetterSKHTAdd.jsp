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
var cus_id,cus_no;
	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#letter_code"),required : true},
				{el : $("#sign_date"),required : true},
				{el : $("#money"),required : true,type: 'number'},
				{el : $("#sp_cond"),required : true},
				{el : $("#start_date"),required : true},
				{el : $("#end_date"),required : true},
				{el : $("#bank_code"),required : true},
				{el : $("#bank_man"),required : true},
				{el : $("#hospital_man"),required : true},
				{el : $("#pact_code"),required : true},
				]
		});
		if(!formValidate.test()){
			return;
		};
		ajaxPostData({
			url : 'addPactLetterSKHT.do',
			data : {
				letter_code: $("#letter_code").val(),
				sign_date: sign_date.getValue(),
				money: $("#money").val(),
				sp_cond : sp_cond.getValue(),
				start_date : start_date.getValue(),
				end_date : end_date.getValue(),
				bank_code : bank_code.getValue(),
				bank_man : $("#bank_man").val(),
				hospital_man : $("#hospital_man").val(),
				hospital_tel : $("#hospital_tel").val(),
				pact_code : pact_code.getValue(),
				note : $("#note").val(),
				content : $("#content").val(),
				bank_tel : $("#bank_tel").val(),
				bank_no : $("#bank_no").val(),
				sup_tel : $("#sup_tel").val(),
				cus_id :cus_id,
				cus_no : cus_no.getValue(),
				
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
    	hospital_man = $("#hospital_man").etSelect({url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',defaultValue: "none"});
      	sp_cond = $("#sp_cond").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=SP_COND',defaultValue: "none"});
      	bank_code = $("#bank_code").etSelect({
      		url: '../../../basicset/select/queryPactBankDictSelect.do?isCheck=false',
      		defaultValue: "none",
      		onChange:function(value){
         		ajaxPostData({
        		 	url: 'queryPactBankDetailInfo.do?isCheck=false',
        		 	data:{
        		 		bank_code:value
        		 	},
        			success: function (result) {
        				$("#bank_no").val(result.bank_zh);
        				$("#bank_man").val(result.bank_man);
        				$("#bank_tel").val(result.phone);
        			}
        		});
         	}	 
      	 });
      	
      	ajaxPostData({
      		url: '../../../basicset/select/queryHosCusDictSelect.do?isCheck=false',
			  success: function (result) {
				  cus_no = $("#cus_no").etSelect({
					 defaultValue: 'none',
					 options:result ,
					 onItemAdd:function(value, $item){
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 cus_id = obj.cus_id;
							 }
						 }
						pact_code.setValue(0);
		    			pact_code.reload({url: 'queryPactSKHTSelectForLetter.do?isCheck=false&cus_no='+value,defaultValue: "none"})
		    			
			      	 }  
				 });
			  },
		});
    	pact_code = $("#pact_code").etSelect({
		  	url: 'queryPactSKHTSelectForLetter.do?isCheck=false&cus_no=0',
            defaultValue: "none"
        });
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
		<span style="margin-left: 25px">基本信息</span>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">保函编号：</td>
				<td class="ipt"><input id="letter_code" type="text"/></td>
				<td class="label no-empty" style="width: 100px;">开具日期：</td>
				<td class="ipt"><input id="sign_date" style="width: 180px;"/></td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">索赔条件：</td>
				<td class="ipt"><select id="sp_cond" style="width: 180px;"></select></td>
				<td class="label no-empty" style="width: 100px;">担保金额：</td>
				<td class="ipt"><input id="money" type="text"/></td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">开始日期：</td>
				<td class="ipt"><input id="start_date" style="width: 180px;"/></td>
				<td class="label no-empty" style="width: 100px;">结束日期：</td>
				<td class="ipt"><input id="end_date" style="width: 180px;"/></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">备注：</td>
				<td class="ipt" colspan="5"><input id="note" type="text" style="width: 510px"> </td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">保函内容：</td>
				<td class="ipt" colspan="5"><textarea id="content" style="height: 60px;width: 510px"></textarea></td>
			</tr>
		</table>
		<span style="margin-left: 25px">银行信息</span>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">银行信息：</td>
				<td class="ipt"><select id="bank_code" type="text"  style="width: 180px;"></select> </td>
				<td class="label no-empty" style="width: 100px;">银行负责人：</td>
				<td class="ipt"><input id="bank_man" type="text"/></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">联系电话：</td>
				<td class="ipt"><input id="bank_tel" type="text"/></td>
				<td class="label" style="width: 100px;">银行账号：</td>
				<td class="ipt"><input id="bank_no" type="text"/></td>
			</tr>
		</table>
		<span style="margin-left: 25px">委托方信息</span>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">委托单位：</td>
				<td class="ipt"><input type="text" value="${hos_name }" disabled="disabled" style="background-color: #EAEAEA"></td>
				<td class="label no-empty" style="width: 100px;">委托方负责人：</td>
				<td class="ipt"><select id="hospital_man" style="width: 180px;"></select></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">联系电话：</td>
				<td class="ipt"><input id="hospital_tel" type="text"/></td>
			</tr>
		</table>
		<span style="margin-left: 25px">受益方信息</span>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">客户：</td>
				<td class="ipt"><select id="cus_no" style="width: 180px;"></select></td>
				<td class="label no-empty" style="width: 100px;">合同名称：</td>
				<td class="ipt"><select id="pact_code" style="width: 180px;"></select> </td>
			</tr>
		</table>
	</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>
</html>

