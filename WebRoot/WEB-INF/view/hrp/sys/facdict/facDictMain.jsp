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
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'fac_no',value:$("#fac_no").val()}); 
    	grid.options.parms.push({name:'group_id',value:$("#group_id").val()}); 
    	grid.options.parms.push({name:'hos_id',value:$("#hos_id").val()}); 
    	grid.options.parms.push({name:'fac_id',value:$("#fac_id").val()}); 
    	grid.options.parms.push({name:'fac_code',value:$("#fac_code").val()}); 
    	grid.options.parms.push({name:'fac_name',value:$("#fac_name").val()}); 
    	grid.options.parms.push({name:'user_code',value:$("#user_code").val()}); 
    	grid.options.parms.push({name:'create_date',value:$("#create_date").val()}); 
    	grid.options.parms.push({name:'note',value:$("#note").val()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#fac_no").val()!=""){
                		return rowdata.fac_no.indexOf($("#fac_no").val()) > -1;	
                	}
                	if($("#group_id").val()!=""){
                		return rowdata.group_id.indexOf($("#group_id").val()) > -1;	
                	}
                	if($("#hos_id").val()!=""){
                		return rowdata.hos_id.indexOf($("#hos_id").val()) > -1;	
                	}
                	if($("#fac_id").val()!=""){
                		return rowdata.fac_id.indexOf($("#fac_id").val()) > -1;	
                	}
                	if($("#fac_code").val()!=""){
                		return rowdata.fac_code.indexOf($("#fac_code").val()) > -1;	
                	}
                	if($("#fac_name").val()!=""){
                		return rowdata.fac_name.indexOf($("#fac_name").val()) > -1;	
                	}
                	if($("#user_code").val()!=""){
                		return rowdata.user_code.indexOf($("#user_code").val()) > -1;	
                	}
                	if($("#create_date").val()!=""){
                		return rowdata.create_date.indexOf($("#create_date").val()) > -1;	
                	}
                	if($("#note").val()!=""){
                		return rowdata.note.indexOf($("#note").val()) > -1;	
                	}
                	if($("#is_stop").val()!=""){
                		return rowdata.is_stop.indexOf($("#is_stop").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'fac_no', name: 'fac_no', align: 'left'
					 },
                     { display: 'group_id', name: 'group_id', align: 'left'
					 },
                     { display: 'hos_id', name: 'hos_id', align: 'left'
					 },
                     { display: 'fac_id', name: 'fac_id', align: 'left'
					 },
                     { display: 'fac_code', name: 'fac_code', align: 'left'
					 },
                     { display: 'fac_name', name: 'fac_name', align: 'left'
					 },
                     { display: 'user_code', name: 'user_code', align: 'left'
					 },
                     { display: 'create_date', name: 'create_date', align: 'left'
					 },
                     { display: 'note', name: 'note', align: 'left'
					 },
                     { display: 'is_stop', name: 'is_stop', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryFacDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.fac_no   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.fac_id   + "|" + 
								rowdata.fac_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'facDictAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveFacDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.fac_no   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.fac_id   +"@"+ 
							this.fac_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteFacDict.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index] 
		
    	$.ligerDialog.open({ url : 'deptIncomeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveFacDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">fac_no：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_no" type="text" id="fac_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">group_id：</td>
            <td align="left" class="l-table-edit-td"><input name="group_id" type="text" id="group_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_id：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">fac_id：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">fac_code：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">fac_name：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_name" type="text" id="fac_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">user_code：</td>
            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">create_date：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">note：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">is_stop：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
