<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    var url;
    var role_id_qj;
    var group_id_qj ;
    var hos_id_qj ;
    $(function ()
    {
    	loadTree();
    	loadHead(null);	//加载数据
    	loadDict();
    });
    //查询
    function  query(){  
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件 
    	grid.options.parms.push({name:'user_id',value:liger.get("user_code").getValue()}); 
       	grid.options.parms.push({name:'role_id',value:role_id_qj}); 
    	grid.options.parms.push({name:'group_id',value:group_id_qj}); 
    	grid.options.parms.push({name:'hos_id',value:hos_id_qj});
    	var node = tree.getSelectedNodes();
    	 
    	if(node.length)
   		{  
	    	
	    	ajaxJsonObjectByUrl("queryUserByRole.do",grid.options.parms,function (responseData){
				grid.loadData(responseData);
	    	});
   		}else
  		{
   			$.ligerDialog.error('请选择角色');
  		}
   
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
        	if($("#user_code").val()!=""){
        		return rowdata.user_code.indexOf($("#user_code").val()) > -1;	
        	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '用户编码', name: 'user_code', align: 'left'
					 },
                     { display: '用户名称', name: 'user_name', align: 'left'
					 },
					 { display: '状态', name: 'is_stop', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "启用";
								}else{
									return "停用"
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:url,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad :true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	var node = tree.getSelectedNodes();
                	if(node == ""){
    	       			$.ligerDialog.error('请先选择角色');
    	       			return;
    	       		}
                	$.each(node,function(index,value){
                		$.ligerDialog.open({url: 'hosRoleUserPage.do?isCheck=false&role_id='+value.id+'&group_id='+value.group_id+'&hos_id='+value.hos_id, height: 400,width: 500, title:'添加用户',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.saveRole();  },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                	});
              		return;
                case "modify":
                    return;
                case "delete":
                	var node = tree.getSelectedNodes();
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择用户');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (index,value){		
                        	$.each(node,function(i,v){
								ParamVo.push(
										v.id+"@"
										+v.group_id+"@"
										+v.hos_id+"@"
								+value.user_id
								
								)
                        	});
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("../roleuser/deleteRoleUser.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			groupDetail();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
            }   
        }
        
    }
    
    function loadDict(){
        //字典下拉框
	autocomplete("#user_code","../queryUserDict.do?isCheck=false","id","text",true,true);    
     }   
    </script>
    <script>
    var tree;
    var setting = {      
		data: {
			simpleData: {
				enable: true
			}
		},
		treeNode:{
			open:true
		},
		callback:{
			onClick:groupDetail
		}
   }; 
    function loadTree(){
    	ajaxJsonObjectByUrl("queryHosRoleByMenu.do",null,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    
	function groupDetail(){
		var node = tree.getSelectedNodes();
		$.each(node,function(index,value){
			role_id_qj=value.id;
			group_id_qj = value.group_id ;
			hos_id_qj = value.hos_id ;
				var param = {
					'role_id':value.id,
					'group_id':value.group_id,
					'hos_id':value.hos_id
				};
				ajaxJsonObjectByUrl("queryUserByRole.do",param,function (responseData){
					grid.loadData(responseData);
		    	});
		});
		
	}
    </script>
	 <script type="text/javascript">
       function itemclick1(item)
       {
    	   var node = tree.getSelectedNodes();
           if(item.text == "增加"){
        	   $.ligerDialog.open({url: 'hosRoleAddPage.do?isCheck=false', height: 250,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveRole(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
           }
           if(item.text == "修改"){
	       		if(node == ""){
	       			$.ligerDialog.error('请先选择角色');
	       			return;
	       		}
	       		$.each(node,function(index,value){
	    			$.ligerDialog.open({url: 'hosRoleUpdatePage.do?isCheck=false&role_id='+value.id, height: 250,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveRole(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	    		});
	       		
           }
           if(item.text == "删除"){
	       		if(node == ""){
	       			$.ligerDialog.error('请先选择角色');
	       			return;
	       		}
	       		$.each(node,function(index,value){
	       		 var ParamVo =[];
						ParamVo.push(
								value.id+"@"+value.group_id+"@"+value.hos_id
						)
                 $.ligerDialog.confirm('确定删除?', function (yes){
                 	if(yes){
                     	ajaxJsonObjectByUrl("deleteHosRole.do",{ParamVo : ParamVo},function (responseData){
                     		if(responseData.state=="true"){
                     			loadTree();
                     		}
                     	});
                 	}
                 }); 
	       		});
           }
       }
       $(function ()
       {
           $("#toptoolbar").ligerToolBar({ items: [
               {text: '增加', icon:'add', click: itemclick1},
               { line:true },
               { text: '修改',icon:'edit', click: itemclick1 },
               { line:true },
               { text: '删除',icon:'delete', click: itemclick1 },
               { line:true },
               { text: '打印', click: itemclick1 },
               { line:true },
               { text: '转换', click: itemclick1 }
           ]
           });
       });
   </script>
   <style type="text/css">
   #menu1,.l-menu-shadow{top:30px; left:50px;}
   #menu1{  width:200px;}
   </style>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	 <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 200px; height: 100%;">
          <div class="l-layout-header"></div>
	       <div class="l-layout-content" position="left">
		       		<div class="ztree"  style="float: left " >
		       		<ul id="tree"></ul>
		       		</div>
	       </div>
          </div>
          <div class="l-layout-center" style="left: 205px; top: 0px; width: 975px; height: 100%;"><div class="l-layout-header"></div>
	           <div title="" class="l-layout-content" style="height: 100%;" position="center">
	           		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				        <tr>
				            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户编码：</td>
				            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
				            <td align="left"></td>
				        </tr> 
			    	</table>
	              	<div id="maingrid"></div>
	           </div>
         </div>  
        </div>  
</body>
</html>
