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
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
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
    	grid.options.parms.push({name:'fac_code',value:liger.get("fac_code").getValue()});
        
		grid.options.parms.push({name:'fac_message',value:$('#fac_message').val()});
    	
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()});

    	grid.options.parms.push({
			name : 'is_disable',
			value :liger.get("is_disable").getValue()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '生产厂商编码', name: 'fac_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.fac_id+"|"+rowdata.fac_code+"')>"+rowdata.fac_code+"</a>";
							}
					 },
                     { display: '生产厂商名称', name: 'fac_name', align: 'left'
					 },
					 { display: '生产厂商类别', name: 'type_name', align: 'left'
					 },
                     { display: '是否停用', name: 'is_disable', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_disable == 0){
									return "否";
								}else{
									return "是"
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../facdict/queryFacDictList.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: -17,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                
    	                { text: '打印', id:'print', click: print,icon:'print' },
    	                { line:true },
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' },
		                { line:true },
    	                { text: '继承', id:'extend', click: itemclick,icon:'extend' },
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
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
              		$.ligerDialog.open({url: 'facAddPage.do?isCheck=false', height: 300,width: 750, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveFac(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.fac_id   +"@"+ 
							this.fac_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteFac.do",{ParamVo : ParamVo.toString()},function (responseData){
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
            	case "downTemplate":location.href = "downTemplate.do?isCheck=false";return;
            	case "import":
                   	$.ligerDialog.open({url: 'sysFacImportPage.do?isCheck=false', height: 550,width: 1000, isResize:true, title:'导入'});
                   	return;
            	case "extend":
                	var formPara = {
				};
				$.ligerDialog.confirm('是否确认继承！', function(yes) {
					if(yes){
						ajaxJsonObjectByUrl("addGroupFac.do?isCheck=false",formPara,function(responseData){
	    		            
	    		            if(responseData.state=="true"){
	    		                query();
	    		                this_close();
	    		            }
	    		        });
					}
				});
                	return;

            }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&fac_id="+ vo[2]+"&fac_code="+ vo[3];
		
    	$.ligerDialog.open({ url : 'facUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 900, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveFac(); },cls:'l-dialog-btn-highlight' },
    			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    			          ]
    	});

    }
	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'生产厂商维护',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
			/* 	{"cell":0,"value":"统计日期: " + $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val(),"colspan":colspan_num,"br":true} */
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("../facdict/queryFacDictList.do.do?isCheck=false", selPara, function (responseData) {
   			for(var i = 0; i < responseData.Total; i++){
                if(responseData.Rows[i].is_disable == 0){
                    responseData.Rows[i].is_disable = "否";
                }
                else{
                    responseData.Rows[i].is_disable = "是";
                }
            }
   			printGridView(responseData,printPara);
		});

   		
    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#fac_code","../queryFacDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#type_code","../queryFacTypeDict.do?isCheck=false","id","text",true,true);
    	$("#fac_code").ligerTextBox({width:160});
    	$("#fac_message").ligerTextBox({width:160});
    	 $('#is_disable').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		})
    }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">厂商信息：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_message" type="text" id="fac_message" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left" class="l-table-edit-td"  style="padding-left:20px;"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">厂商分类：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"class="l-table-edit-td"  style="padding-left:20px;"></td>
            
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left" class="l-table-edit-td"  style="padding-left:20px;"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
           		<input name="is_disable" type="text" id="is_disable"  />
           	</td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>