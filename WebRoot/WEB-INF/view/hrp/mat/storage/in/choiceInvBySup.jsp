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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null; 
    var userUpdateStr;
    var sup_id = "${sup_id}"; 
    $(function ()
    {
        loadDict();//加载下拉框
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
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		}); 
        
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'mat_type_id',
			value : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({ 
			name : 'inv_code',
			value : $("#inv_code").val()
		}); 
		grid.options.parms.push({ 
			name : 'is_com',
			value : '0'
		});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [/* { 
	 				display: '材料ID', name: 'inv_id', align: 'left'
	 			},  */{ 
		 			display: '材料编码', name: 'inv_code', align: 'left',width:'120'
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left',width:'200'
		 		}, { 
		 			display: '物资分类', name: 'mat_type_code', align: 'left',width:'100'
		 		}, { 
		 			display: '物资分类', name: 'mat_type_name', align: 'left',width:'120'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left',width:'120'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left',width:'90'
		 		}, { 
		 			display: '价格', name: 'price', align: 'right',width:'90',
		 			render : function(rowdata, rowindex, value) {
						return rowdata.price == null ? "" : formatNumber(rowdata.price, '${p04006 }', 1);
					}
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left',width:'200'
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'left',width:'200'
		 		}, { 
		 			display: '材料证件', name: 'cert_code', align: 'left',width:'230'
		 			
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMatChoiceInvBySup.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers:true,
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
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.warn('请选择材料!');
			return;
		}else{
			var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
				detail_rows.append('"sup_id":').append(liger.get("sup_code").getValue().split(",")[0]).append(',');
				detail_rows.append('"inv_no":').append(data.inv_no).append('}');
			});
			detail_rows.append("]");
			
			var formPara = { 
				allData : detail_rows.toString()
			};
			
			ajaxJsonObjectByUrl("queryChoiceInvBySupData.do?isCheck=false", formPara, function(responseData) {
				if(responseData.Rows.length > 0){
					parent.add_rows(responseData.Rows);
				}
				this_close();
			});
		}
		
    	
    	var data = gridManager.getData();
		var detail_rows = new StringBuffer();
		
		detail_rows.append("[");
		$.each(data, function(index, data){
			if(index != 0){
				detail_rows.append(",");
			}
			detail_rows.append('{"inv_id":').append(data.inv_id).append(',');
			detail_rows.append('"inv_code":"').append(data.inv_code).append('",');
			detail_rows.append('"inv_name":"').append(data.inv_name).append('",');
			detail_rows.append('"inv_no":').append(data.inv_no).append(','); 
			detail_rows.append('"inv_model":"').append(data.inv_model).append('",');
			detail_rows.append('"unit_name":"').append(data.unit_name).append('",');
			detail_rows.append('"fac_name":"').append(data.fac_name).append('",');
			detail_rows.append('"app_amount":').append(data.app_amount*num).append(',');
			detail_rows.append('"price":').append(data.price).append(',');
			detail_rows.append('"is_com":').append(data.is_com).append(',');
			detail_rows.append('"amount_money":').append(data.price*data.app_amount*num).append('}');
		});
		detail_rows.append("]");
		console.log(detail_rows.toString());
		//添加材料
		parent.add_rows(eval(detail_rows.toString()));
		parent.grid.addRow();
		this_close();
		
    }
    
	function this_close(){
		frameElement.dialog.close();
	}
   
    function loadDict(){
		
    	//字典下拉框
		$("#sup_code").ligerComboBox({width:160,disabled:false,cancelable: false});
        liger.get("sup_code").setValue("${sup_id}");
		liger.get("sup_code").setText("${sup_text}");
		
		$("#store_code").ligerComboBox({width:160,disabled:false,cancelable: false});
        liger.get("store_code").setValue("${store_id}");
		liger.get("store_code").setText("${store_text}");
		
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1,is_last :1},false);
		$("#sup_code").ligerTextBox({width:200});
		$("#inv_code").ligerTextBox({width:200});
		
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
			<td align="right" class="l-table-edit-td"  width="10%"> 供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%"> 仓库：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%"> 物资分类：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" required="true" validate="{required:true}" />
            </td>
            
			<td align="right" class="l-table-edit-td"  width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			
		</tr>
    </table>
    
	<div id="maingrid"></div>
</body>
</html>
