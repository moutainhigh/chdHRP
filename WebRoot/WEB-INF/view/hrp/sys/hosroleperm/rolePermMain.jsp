<%@page import="com.chd.base.SessionManager"%>
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
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>

	<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
		loadToolbar();
    	//loadHead(null);	//加载数据
    });
    
    var setting = {      
    		check: {
			enable: true,
			nocheckInherit:{ "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
			}
		},
		treeNode:{
			open:true
		}
   }; 
    
   
    function loadTree(){
    var 	hos_code=liger.get("hos_code").getValue();
    var 	copy_code=liger.get("copy_code").getValue();
    var 	role_id=liger.get("role_id").getValue();
    var 	mod_code=liger.get("mod_code").getValue();
    /* if(hos_code==null || hos_code=="" ){
		$.ligerDialog.error("请选择医院.");
		return false;
	} */
    if(copy_code==null || copy_code=="" ){
		$.ligerDialog.error("请选择账套.");
		return false;
	}
    if(role_id==null || role_id=="" ){
		$.ligerDialog.error("请选择角色.");
		return false;
	}
    if(mod_code==null || mod_code=="" ){
		$.ligerDialog.error("请选择系统模块.");
		return false;
	}
    ajaxJsonObjectByUrl("queryHosRolePermByMenu.do",{hos_id:hos_code,role_id:role_id,mod_code:mod_code,copy_code:copy_code},function (responseData){
   	if(responseData!=null){
    		tree=$.fn.zTree.init($("#tree1"), setting, responseData.Rows);
	     }
        });
    }
    
    function saveSubmit(){
    	var zTree= $.fn.zTree.getZTreeObj("tree1");
    	if(zTree.getCheckedNodes(true)==0){
    		$.ligerDialog.error("请查询");
    		return false;
    	}
    	
    	if(liger.get("role_id").getValue()==""){
    		$.ligerDialog.error("请选择角色");
    		return false;
    	}
        var notes =zTree.getCheckedNodes(true);
        var checkedIds='';
        for (var i = 0; i < notes.length; i++){
            if(typeof(notes[i].permid) !="undefined" && notes[i].permid!="null" && notes[i].permid!=""){
            	checkedIds+=notes[i].permid+',';
            }
        }
        ajaxJsonObjectByUrl("addHosRolePerm.do?",{listVo:checkedIds,hos_id:liger.get("hos_code").getValue(),role_id:liger.get("role_id").getValue(),mod_code:liger.get("mod_code").getValue(),copy_code:liger.get("copy_code").getValue()},function (responseData){
    		
        });
        //alert('选择的节点数：' + text);
    }
    
    function f_selectNode(){
   	 $.fn.zTree.getZTreeObj("tree1").checkAllNodes(true);
	}

	function f_cancelSelect(){
		$.fn.zTree.getZTreeObj("tree1").checkAllNodes(false);
	}
	
	function collapseAll(){
		 $.fn.zTree.getZTreeObj("tree1").expandAll(false);
	}
	function expandAll(){
		  $.fn.zTree.getZTreeObj("tree1").expandAll(true);
	}
	function undoAll(){
		var zTree= $.fn.zTree.getZTreeObj("tree1");
    	if(zTree.getCheckedNodes(true)==0){
    		$.ligerDialog.error("请查询");
    		return false;
    	}
    	
    	if(liger.get("role_id").getValue()==""){
    		$.ligerDialog.error("请选择角色");
    		return false;
    	}
        ajaxJsonObjectByUrl("deleteHosRolePerm.do?",{hos_id:liger.get("hos_code").getValue(),role_id:liger.get("role_id").getValue(),mod_code:liger.get("mod_code").getValue(),copy_code:liger.get("copy_code").getValue()},function (responseData){
        	if(responseData.state=="true")loadTree();
        });
	}
    function loadToolbar(){
   	 //工具条
       $("#toptoolbar").ligerToolBar({ items: [
       	{ text: '查询', id:'true', click: loadTree, icon:'search' },
       	{ line:true },
           { text: '保存', id:'save', click: saveSubmit,icon:'save' },
           { line:true },
           { text: '全选', id:'true', click: f_selectNode, icon:'ok' },
           { line:true },
           { text: '全消', id:'candle', click: f_cancelSelect,icon:'candle' },
           { line:true },
           { text: '全部展开', id:'true',click: expandAll, icon:'down' },
           { line:true },
           { text: '全部折叠', id:'true', click: collapseAll, icon:'up' },
           { line:true },
           { text: '撤销赋权', id:'true', click: undoAll, icon:'up' }
       ]
       });
   }
   
    function loadDict(){
    	autocomplete("#hos_code","../queryHosInfoDict.do?isCheck=false","id","text",true,true);    
    	autocomplete("#copy_code","../queryCopyCodeDict.do?isCheck=false","id","text",true,true);    
    	autocomplete("#mod_code","../queryModDictAdminPerm.do?isCheck=false","id","text",true,true);    
    	autocomplete("#role_id","../queryRoleDict.do?isCheck=false","id","text",true,true);    
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医院：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_code" type="text" id="hos_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账套：</td>
            <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医院角色：</td>
            <td align="left" class="l-table-edit-td"><input name="role_id" type="text" id="role_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">系统模块：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="toptoolbar" ></div>
 
	<div id="treeDiv" class="ztree"  style="width:400px; height:480px; margin:10px;  position: relative;float:left; border:1px solid #ccc; overflow:auto;">
	    <ul id="tree1"></ul>
	 </div>

</body>
</html>
