<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
		var grid;
		var fileName;
		var dialog = frameElement.dialog;
		$(function(){
		loadHead(null);
		function plupload(){  
			
			$("#uploader").pluploadQueue({    
				       
					runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
					          
					url : "readMatPayMainFiles.do?isCheck=false",    
					
					chunk_size : '2mb',           
					
					filters : {                
						
					mime_types: [  
					
					{title : "excel files", extensions : "xls,xlsx"}
					
					]  
					},  
					flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',       
					          
					silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',     
					
					init: {
						
							FileUploaded: function(uploader,file,responseObject) {
								
									var res=responseObject.response;
									
									var json = eval('(' + res + ')');
									
									if(json.Rows!= null&&json.Rows!= ""){
										
									$("#uploader").css("display","none");
									
									$("#maingrid").css("display","block");
									
									grid.loadData(json);
									
									}else{
										
									$.ligerDialog.success('导入成功！');
									
									$("#uploader").css("display","block");
									
									$("#maingrid").css("display","none");
									
									plupload();
									
									parent.query();
									
									}
									
									}
							
							}
					
					});
			
			}
			plupload();
		});
		
		function getJsonObjLength(jsonObj) {
				var Length = 0;
				for (var item in jsonObj) {
				Length++;
				}
				return Length;
		}
		function loadHead() {
				grid = $("#maingrid").ligerGrid({
				columns: [ 
				{ display: '付款单ID', name: 'pay_id', align: 'left',editor:{type:'text'}},
				{ display: '付款单号', name: 'pay_bill_no', align: 'left',editor:{type:'text'}},
				{ display: '付款日期', name: 'pay_date', align: 'left',editor:{type:'text'}},
				{ display: '单据类型', name: 'pay_bill_type', align: 'left',editor:{type:'text'}},
				{ display: '付款方式', name: 'pay_type_code', align: 'left',editor:{type:'text'}},
				{ display: '票据号码', name: 'cheq_no', align: 'left',editor:{type:'text'}},
				{ display: '付款账号', name: 'acct_code', align: 'left',editor:{type:'text'}},
				{ display: '付款金额', name: 'pay_money', align: 'left',editor:{type:'text'}},
				{ display: '优惠金额', name: 'fav_money', align: 'left',editor:{type:'text'}},
				{ display: '可用额度', name: 'ctrl_money', align: 'left',editor:{type:'text'}},
				{ display: '是否超预算', name: 'is_exce', align: 'left',editor:{type:'text'}},
				{ display: '供应商ID', name: 'sup_id', align: 'left',editor:{type:'text'}},
				{ display: '供应商变更ID', name: 'sup_no', align: 'left',editor:{type:'text'}},
				{ display: '职能科室ID', name: 'dept_id', align: 'left',editor:{type:'text'}},
				{ display: '职能科室变更ID', name: 'dept_no', align: 'left',editor:{type:'text'}},
				{ display: '采购员', name: 'stocker', align: 'left',editor:{type:'text'}},
				{ display: '付款条件', name: 'pay_code', align: 'left',editor:{type:'text'}},
				{ display: '记帐日期', name: 'acc_date', align: 'left',editor:{type:'text'}},
				{ display: '财务记账人', name: 'accounter', align: 'left',editor:{type:'text'}},
				{ display: '备注', name: 'note', align: 'left',editor:{type:'text'}},
				{ display: '制单人', name: 'maker', align: 'left',editor:{type:'text'}},
				{ display: '审核人', name: 'checker', align: 'left',editor:{type:'text'}},
				{ display: '审核日期', name: 'chk_date', align: 'left',editor:{type:'text'}},
				{ display: '期初标志', name: 'is_init', align: 'left',editor:{type:'text'}},
				{ display: '状态', name: 'state', align: 'left',editor:{type:'text'}}
				,{ display: '错误说明', name: 'error_type', align: 'left',width: '35%',
				render:function(rowdata, rowindex, value){
				return "<span style='color:red'>"+rowdata.error_type+"</span>";
				}
				}
				],  
				width : '99%',
				height: '370',
				enabledEdit: true,
				usePager:false,
				rownumbers:true,
				selectRowButtonOnly:true,
				toolbar: { items: [
				{text : '保存',
				id : 'save',
				click : itemclick,
				icon : 'add',
				}, 
				{line : true},
				{text : '删除',
				id : 'delete',
				click : itemclick,
				icon : 'delete'},
				{line : true}
				
				]}
				});
				gridManager = $("#maingrid").ligerGetGridManager();
		}
		function itemclick(item){ 
		if(item.id)
		{
		switch (item.id)
		{
		case "save":
		var data = gridManager.getData();
		ajaxJsonObjectByUrl("addBatchMatPayMain.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
		if(responseData.state=="true"){
		$("#uploader").css("display","block");
		$("#maingrid").css("display","none");
		parent.query();
		$.ligerDialog.confirm('导入成功！是否关闭当前页面', function (yes) {
		if(yes==true){
		dialog.close();
		}else{
		document.location.reload();
		}
		});
		}else if(responseData.state=="err"){
		$("#uploader").css("display","block");
		$("#maingrid").css("display","none");
		plupload();
		document.location.reload();
		}else{
		grid.loadData(responseData);
		}
		});
		return;
		case "delete":  
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
		$.ligerDialog.error('请选择行');
		}else{
		var data = gridManager.deleteSelectedRow();
		}
		return;
		}   
		}
		}
</script>
</head>

<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
<div style="width:775px;margin:0px auto;">  
<div id="uploader"></div>  
</div>
<div id="maingrid" style="display: none ; padding-top: 20px" ></div>  
</body>
</html>
