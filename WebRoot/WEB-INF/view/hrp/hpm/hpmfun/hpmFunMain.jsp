<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
  <jsp:include page="${path}/inc.jsp"/>

<script type="text/javascript">
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
        loadDict();
         
    	loadHead(null);	
        
        loadTree();
        
        toolbar();
        
        loadHotkeys();
    });
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '函数编码', name: 'fun_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
							return "<a href='#' onclick=\"openUpdate('"+rowdata.fun_code+ "|" + 
							rowdata.group_id   + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   +"');\" >"+rowdata.fun_code+"</a>";
			           },width:80
					 },
                     { display: '函数名称', name: 'fun_name', align: 'left',width:120},
                      
                     
                     { display: '取值函数(中文)', name: 'fun_method_chs', align: 'left'}
                      
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmFun.do',
                     width: '100%',height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();

    }
    
  	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addFun, icon:'add' });
       	obj.push({ line:true });
       	
    	obj.push({ text: '重新加载存储过程（<u>S</u>）', id:'initProc', click: initProc,icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteFun,icon:'delete' });
       	obj.push({ line:true });
       	
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}
    
    function initProc(){
		$.ligerDialog.confirm('确定重新加载?',function(yes){
			if(yes){
				ajaxJsonObjectByUrl("initHpmFunProc.do?isCheck=false",null,function (responseData){
		    		if(responseData.state=="true"){
		    		}
		    	});
			}
		});
    }

  //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addFun);
		
		hotkeys('D',deleteFun);
		
		hotkeys('S',initProc);
		 
	}
  
 	 function addFun (){
 		 
 		parent.$.ligerDialog.open({url: 'hrp/hpm/hpmfun/hpmFunAddPage.do?isCheck=false', height: $(window).height(),
			width: $(window).width(), title:'添加',modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name });
			
 	 }
 	 
 	 function deleteFun (){
 		 
 		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){
            	ParamVo.push(this.fun_code +"@"+
            			this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+
						this.pkg_name);//实际代码中temp替换主键
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		ajaxJsonObjectByUrl("deleteHpmFun.do?",{ParamVo : ParamVo},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});

            	}
            }); 
        }
 		 
 	 }
  
   
    function openUpdate(obj){
    	var vo = obj.split("|");
		var parm = "&fun_code="+
		vo[0]   +"&group_id="+ 
		vo[1]   +"&hos_id="+ 
		vo[2]   +"&copy_code="+ 
		vo[3];
		
		parent.$.ligerDialog.open({url: 'hrp/hpm/hpmfun/hpmFunUpdatePage.do?isCheck=false&'+parm, height: $(window).height(),
			width: $(window).width(), title:'修改',modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name });
		

    }
    function loadDict(){
    	
    	$("#layout1").ligerLayout({ leftWidth: 200,topHeight:30,centerBottomHeight:190 }); 

    } 
    
    function onSelect(note){
        
        grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        if(note.data.pid != '-1'){
        	grid.options.parms.push({name:'fun_code',value:note.data.id}); 
        }
        
        if(note.data.pid == '-1'){
        	grid.options.parms.push({name:'type_code',value:note.data.id}); 
        }
    	//加载查询条件
    	grid.loadData(grid.where);
    }
    
   
    function loadTree(){

		ajaxJsonObjectByUrl("../hpmfuntype/queryHpmFunTypeTree.do?isCheck=false", {},
		
			function(responseData) {
			
					if (responseData != null) {

						tree = $("#tree1").ligerTree({
							
							data : responseData.Rows,
							
							checkbox : false,
							
							idFieldName : 'id',
							
							parentIDFieldName : 'pid',
							
							textFieldName:'text',
							
							onSelect: onSelect,
							
							nodeWidth:400

						});
						
						treeManager = $("#tree1").ligerGetTreeManager();
						
						treeManager.collapseAll();
					}
					
				});

	}
</script>

</head>

<body style="padding: 0px; overflow:hidden;">
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<div id="layout1" style="width:100%; margin:40px;  height:400px;margin:0; padding:0;">
		   	
            <div position="left" title="指标">
            	<ul id="tree1"></ul>
            </div>
            <div position="center" >
            	<div id="toptool" ></div>
				<div id="maingrid"></div>
            </div>  
        </div>
	
</body>
</html>
