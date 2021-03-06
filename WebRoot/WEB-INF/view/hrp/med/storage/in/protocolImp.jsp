<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'sup_id',
			value : "${sup_id}".split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'protocol_id',
			value : "${protocol_id}".split(",")[0]
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
	 				display: '药品ID', name: 'inv_id', align: 'left', hide: true,
	 			}, { 
		 			display: '药品编码', name: 'inv_code', align: 'left', width: '120',
		 		}, { 
		 			display: '药品名称', name: 'inv_name', align: 'left', width: '180',
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: '120',
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left'
		 		}, { 
		 			display: '协议价格', name: 'price', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '备注', name: 'note', align: 'left', 
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStorageInProtocol.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers: false,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
				{ line : true},
				{ text: '生成入库单（<u>A</u>）', id:'add', click: imp, icon:'add' },
				{ line : true},
				{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },
				{ line : true},
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', imp);
		hotkeys('C', this_close);
	}
    
    function imp(){
    	var data = gridManager.getData();
		if (data.length == 0){
			$.ligerDialog.error('该协议没有明细记录！');
		}else{
			//添加药品
			parent.add_rows(data);
			
			parent.grid.addRow();
			
			this_close();
		}
    }
    
	function this_close(){
		frameElement.dialog.close();
	}
   
    function loadDict(){
		//字典下拉框
		$("#sup_code").ligerComboBox({width:240,disabled:true,cancelable: false});
        liger.get("sup_code").setValue("${sup_id}");
		liger.get("sup_code").setText("${sup_text}");
        $("#protocol_code").ligerComboBox({width:240,disabled:true,cancelable: false});
        liger.get("protocol_code").setValue("${protocol_id}");
		liger.get("protocol_code").setText("${protocol_text}");
        $("#num").ligerTextBox({width:160});
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
		$("#close").ligerButton({click: this_close, width:70});
		$("#add").ligerButton({click: imp, width:70});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				供应商：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				协议：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" required="true" validate="{required:true}" />
            </td>
        </tr> 
    </table>
    
	<div id="maingrid"></div>
</body>
</html>
