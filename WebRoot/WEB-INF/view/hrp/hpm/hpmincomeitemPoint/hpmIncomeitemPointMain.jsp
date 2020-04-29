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
    var userUpdateStr;
    
    //页面初始化
    $(function (){
		$("#is_acc").ligerTextBox({width:160 });
    	autocomplete("#income_item_code","../../hpm/queryAphiIncomeItem.do?isCheck=false","id","text",true,true);
    	
    	loadHead(null);//加载数据	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'income_item_code',value:liger.get("income_item_code").getValue()}); 
    	grid.options.parms.push({name:'is_acc',value:$("#is_acc").val()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
	}
    
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
 				{ display: '收入项目编码', name: 'income_item_code', align: 'left',render: 
 					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"
								+rowdata.income_item_code+"');\" >"
								+rowdata.income_item_code+"</a>";
					}
				},
				
				{ display: '收入项目名称', name: 'income_item_name', align: 'left'},
				{ display: '是否参与核算', name: 'is_acc', align: 'left',type:'formatYesNo'},
				{ display: '项目点数', name: 'imcome_point', align: 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmIncomeitemPoint.do',
			width: '100%',height: '100%',   checkbox: true,rownumbers:true,
			selectRowButtonOnly:true,//heightDiff: -10,
    		onDblClickRow : 
    			function (rowdata, rowindex, value){
   					openUpdate(rowdata.income_item_code);//实际代码中temp替换主键
   				} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        formatYesNo();
    }
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: createIncomeitemPoint,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '快速填充（<u>F</u>）', id:'fast', click: fastIncomeitemPoint,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addIncomeitemPoint, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteIncomeitemPoint,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '点值（<u>P</u>）', id:'point', click: point,icon:'add' });
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
		hotkeys('G',createIncomeitemPoint);
		hotkeys('F',fastIncomeitemPoint);
		hotkeys('A',addIncomeitemPoint);
		hotkeys('D',deleteIncomeitemPoint);
		hotkeys('P',point);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
	}
  	
  	
  	//添加
  	function addIncomeitemPoint(){
  		$.ligerDialog.open({
  			url: 'hpmIncomeitemPointAddPage.do?isCheck=false', height:250,width: 400, 
  			title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveIncomeitemPoint(); 
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
 	
  	//快速填充
  	function fastIncomeitemPoint(){
  		
		var data = gridManager.getCheckedRows();
		var checkIds =[];
        $(data).each(function (){
        	checkIds.push(this.income_item_code);//实际代码中temp替换主键
        });
          
   		$.ligerDialog.open({
   			url: 'hpmIncomeitemPointFastPage.do?isCheck=false&checkIds='+checkIds, 
   			title:'快速填充',height: 200,width: 400,modal:true,showToggle:false,
   			showMax:false,showMin: true,isResize:true,
   			buttons: [ 
   				{ text: '确定', onclick: 
   					function (item, dialog) { 
   						dialog.frame.saveDeptBalancePercConf(); 
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
  	
  	//生成
  	function createIncomeitemPoint(){
  		$.ligerDialog.confirm('确定生成?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("createHpmIncomeitemPoint.do?isCheck=false", {}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
  	}
  	
  	
  	//点值
  	function point(){
  		$.ligerDialog.open({
  			url: 'hpmIncomeitemPointPage.do?isCheck=false', height: 200,width: 400, 
  			title:'快速填充',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.savePointValue(); 
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
  	function deleteIncomeitemPoint(){
  		
  		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        
        var checkIds =[];
        $(data).each(function (){
        	checkIds.push(this.income_item_code);//实际代码中temp替换主键
        });
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmIncomeitemPoint.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
  	}
  	
  	
  	//下载导入模板
  	function downTemplate(){
  		location.href = "downTemplateHpmIncomeitemPoint.do?isCheck=false";	
  	}

  	//导入
  	function importData(){
  		parent.$.ligerDialog.open({ url:'hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, 
			title:'核算项目点数维护导入',height: 300,width: 450,modal:true,
			showToggle:false,showMax:true,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
  	}
  	
  	//修改
    function openUpdate(obj){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'hpmIncomeitemPointUpdatePage.do?isCheck=false&income_item_code='+obj,data:{}, 
    		height: 250,width: 400, title:'修改',modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveIncomeitemPoint(); 
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收入项目编码：</td>
			<td align="left" class="l-table-edit-td"><input name="income_item_code" type="text" id="income_item_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><select name="is_acc" id="is_acc" style="width: 160px;">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>