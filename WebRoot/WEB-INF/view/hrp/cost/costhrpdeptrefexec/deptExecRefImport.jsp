<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
var grid;
var fileName;
var dialog = frameElement.dialog;
$(function(){
	loadHead(null);
	function plupload(){  
		$("#uploader").pluploadQueue({           
	       	runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
	       	url : "readDeptRefFiles.do?isCheck=false",    
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
					   	        parent.loadTree();
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
	grid = $("#maingrid").ligerGrid(
					{
				columns : [
							 { display: '系统科室编码', name: 'dept_code', align: 'left'},
							 { display: '系统科室名称', name: 'dept_name', align: 'left'},
							 { display: '自定义科室编码', name: 'cost_custom_dept_code', align: 'left'},
							 { display: '自定义科室名称', name: 'cost_custom_dept_name', align: 'left'},
							 { display: '是否停用', name: 'is_stop', align: 'left'},
					         { display: '错误说明', name: 'error_type', align: 'left',width: '50%',
	               	 	render:function(rowdata, rowindex, value){
	              		return "<span style='color:red'>"+rowdata.error_type+"</span>";
	              		}
              		}],
				width : '99%',
				height: '370',
				enabledEdit: true,
				usePager:false,
				rownumbers:true,
				selectRowButtonOnly:true,
				
					});
	gridManager = $("#maingrid").ligerGetGridManager();
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
