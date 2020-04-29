<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    	
		$("#dept_code").ligerTextBox({width:180});
    	
    	$("#dept_name").ligerTextBox({width:180});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    	grid.options.parms.push({name:'his_dept_code',value:$("#dept_code").val()}); 
    	grid.options.parms.push({name:'his_dept_name',value:$("#dept_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'HIS科室编码', name: 'his_dept_code', align: 'left'
					 },
                     { display: 'HIS科室名称', name: 'his_dept_name', align: 'left'
					 },
					 { display: 'HRP科室编码', name: 'hrp_dept_code', align: 'left'
					 },
                     { display: 'HRP科室名称', name: 'hrp_dept_name', align: 'left'
					 },
					 { display: '操作',  align: 'left',render:function(rowdata,index,value){
						 return "<a href='#' onClick='javascript:addAccAssDeptRef("+index+")'>【设置】</a>&nbsp;&nbsp;&nbsp;&nbsp;"
						 +"<a href='#' onClick='javascript:delAccAssDeptRef("+index+")'>【删除】</a>";
					 }}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHisAccAssDeptRef.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

	function addAccAssDeptRef(obj){
       	
       	var data = gridManager.getRow(obj);

       	$.ligerDialog.open({url: 'hisAccAssDeptRefAddPage.do?isCheck=false&his_dept_code='+data.his_dept_code, height: $(window).height(),width: $(window).width()-100, title:'选择对应关系',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
   				buttons: [ 
   				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccAssDeptRef(); },cls:'l-dialog-btn-highlight' }, 
   				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
   				         ]
   		});
       }
        
      function delAccAssDeptRef(obj){
      	
      	var data = gridManager.getRow(obj);
      	
      	var ParamVo =[];

  			ParamVo.push(
  			//表的主键
  			data.group_id   +"@"+ 
  			data.hos_id   +"@"+ 
  			data.his_dept_code +"@"+ 
  			data.hrp_dept_id +"@"+ 
  			data.copy_code 
  			);
              $.ligerDialog.confirm('确定删除?', function (yes){
              	if(yes){
                  	if(data.hrp_dept_code == null){
                  		$.ligerDialog.error('HRP科室为空!');
                  		return false;
                  	}
                  	ajaxJsonObjectByUrl("deleteHisAccAssDeptRef.do",{ParamVo : ParamVo.toString()},function (responseData){
                  		if(responseData.state=="true"){
                  			query();
                  		}
                  	});
              	}
              }); 
        }
      
    function loadDict(){
            //字典下拉框
    }  
    
    function downTemplate(){
    	
    	location.href = "downTemplate.do";
    	
    }
    
    function importData(){
    	
    	$.ligerDialog.open({url: 'accHisAccAssDeptRefImportPage.do?isCheck=false', height: 500,width: 1135, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
    	
    }
    
    function printData(){
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      	  	         
	      		  ]
	      	};
	   		
   		var printPara={
   			rowCount:1,
   			title:'HIS药库药房对照',
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccAssDeptRefService",
			method_name: "queryHisAccAssDeptRefPrint",
			bean_name: "hisAccAssDeptRefService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   			};
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HIS科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HIS科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_name" type="text" id="dept_name" /></td>
        </tr>
    </table>
	<div style="border: 1px;padding-left: 20px;">
		<input class="l-button" type="button" value=" 查询" onclick="query();" />
        <input class="l-button" style="width: 90px;margin-left: 10px" type="button" value="下载导入模板" onclick="downTemplate();" />
        <input class="l-button" style="margin-left: 10px" type="button" value=" 导入" onclick="importData();" /></td>
        <input class="l-button" style="margin-left: 10px" type="button" value=" 打 印" onclick="printData();" /></td>
	</div>

	<div id="maingrid"></div>
	
</body>
</html>
