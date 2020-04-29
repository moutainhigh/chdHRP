<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<style type=""> 
	.l-panel-topbarinner {
		width:200px !important;
	}
</style>
<script type="text/javascript">
		var grid;
		var fileName;
		var dialog = frameElement.dialog;
		$(function(){
			loadHead(null);
		
		function plupload(){  
			
			$("#uploader").pluploadQueue({    
				       
					runtimes : 'flash,html5,gears,browserplus,silverlight,html4', 
					          
					url : "readAccBankTellFiles.do?isCheck=false",    
					
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
					{ display: '科目编码', name: 'subj_code', align: 'left',editor:{type:'text'},width: '5%'},
					{ display: '科目名称', name: 'subj_name', align: 'left',editor:{type:'text'},width: '5%'},
					{ display: '摘要', name: 'summary', align: 'left',editor:{type:'text'},width: '8%'},
					{ display: '借方金额', name: 'debit', align: 'left',editor:{type:'text'},width: '5%'},
					{ display: '贷方金额', name: 'crebit', align: 'left',editor:{type:'text'},width: '5%'},
					{ display: '余额', name: 'bal', align: 'left',editor:{type:'text'},width: '5%'},
					{ display: '票据号', name: 'check_no', align: 'left',editor:{type:'text'},width: '8%'},
					{ display: '单据号', name: 'business_no', align: 'left',editor:{type:'text'},width: '8%'},
					{ display: '发生日期', name: 'occur_date', align: 'left',editor:{type:'text'},width: '5%'},
					{ display: '结算方式编码', name: 'pay_type_code', align: 'left',editor:{type:'text'},width: '10%'},
					{ display: '结算方式名称', name: 'pay_type_name', align: 'left',editor:{type:'text'},width: '10%'},
					{ display: '错误说明', name: 'error_type', align: 'left',width: '35%',
						render:function(rowdata, rowindex, value){
							return "<span style='color:red'>"+rowdata.error_type+"</span>";
						}
					}
				],  
				width : '100%',
				height : '100%',
				delayLoad:true,
				enabledEdit: true,
				usePager:false,
				rownumbers:true,
				selectRowButtonOnly:true,
				/* toolbar: { items: [
					{text : '保存',id : 'save',click : itemclick,icon : 'save'}, 
					{line : true},
					{text : '删除',id : 'delete',click : itemclick,icon : 'delete'},
					{line : true}
				]} */
				});
				gridManager = $("#maingrid").ligerGetGridManager();
		}
		/* function itemclick(item){ 
		if(item.id){
			switch (item.id){
				case "save":
					var data = gridManager.getData();
					ajaxJsonObjectByUrl(".do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
		} */
</script>
</head>

<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
<div style="width:1100px;margin:0px auto;">  
<div id="uploader"></div>  
</div>
<div id="maingrid" style="display: none ;"></div>  
</body>
</html>
