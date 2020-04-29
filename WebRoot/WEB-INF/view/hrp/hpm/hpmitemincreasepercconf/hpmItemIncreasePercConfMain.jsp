<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var app_mod_code = '${app_mod_code}';
    var userUpdateStr;
    
    //页面初始化
    $(function (){
    	autocomplete("#item_code","../queryItemAllDict.do?app_mod_code='03','99'&isCheck=false","id","text",true,true);
    	loadHead(null);//加载数据
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'item_code',value:liger.get("item_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
            	{ display: '奖金项目编码', name: 'item_code', align: 'left',render: 
            		function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"+rowdata.item_code+"');\" >"+rowdata.item_code+"</a>";
					}
				},
				
				{ display: '奖金项目名称', name: 'item_name', align: 'left'},
                     
				{ display: '增长比例', name: 'increase_percent', align: 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmItemIncreasePercConf.do',
			width: '100%',height: '100%',   checkbox: true,rownumbers:true,
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : 
				function (rowdata, rowindex, value){
   					openUpdate(rowdata.item_code);//实际代码中temp替换主键
   				} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addItemIncreasePercConf, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteHpmItemIncreasePercConf,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addItemIncreasePercConf);
		
		hotkeys('D',deleteHpmItemIncreasePercConf);
		
		hotkeys('T',downTemplate);
		
		hotkeys('I',importData);
	}
    
    //添加
    function addItemIncreasePercConf(){
  		$.ligerDialog.open({
  			url: 'hpmItemIncreasePercConfAddPage.do?isCheck=false&app_mod_code='+app_mod_code, 
  			height: 250,width: 400, title:'添加',modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveItemIncreasePercConf(); 
  					},cls:'l-dialog-btn-highlight' 
  				}, 
  				{ text: '取消', onclick: 
  					function (item, dialog) { 
  						dialog.close(); 
  					} 
  				} 
  			] 
  		});
    }
    
    //删除
    function deleteHpmItemIncreasePercConf(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
    	
        var checkIds =[]; 
        $(data).each(function (){
        	var map = new Object(); 
        	map.item_code = this.item_code; 
       	    var json = JSON.stringify(map);
        	checkIds.push(json);
        });
        
        var mapVo='['+checkIds+']';
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmItemIncreasePercConf.do",{mapVo:mapVo},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
    //下载导入模板
    function downTemplate(){
    	location.href = "downTemplateItemIncreasePercConf.do?isCheck=false";
    }
    
    //导入
    function importData(){
    	$.ligerDialog.open({
    		url: 'itemIncreasePercConfImportPage.do?isCheck=false', 
    		height: 430,width: 760, isResize:true, title:'导入'}
    	);
    }
	
    //修改
    function openUpdate(obj){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'hpmItemIncreasePercConfUpdatePage.do?isCheck=false&item_code='+obj+'&app_mod_code='+app_mod_code,data:{}, 
    		title:'修改',height: 250,width: 400,modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveItemIncreasePercConf(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目编码：</td>
			<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
