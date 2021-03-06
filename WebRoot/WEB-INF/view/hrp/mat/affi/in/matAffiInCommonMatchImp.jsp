<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	$(function() {
		
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]});
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1]});
    	grid.options.parms.push({name : 'store_match_id',value : liger.get("store_match").getValue()}); 
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		      columns: [ {display: '材料ID', name: 'inv_id', align: 'left'},
		      	{ display: '材料编码', name: 'inv_code', align: 'left'  },
				{ display: '材料名称', name: 'inv_name', align: 'left'  },
				{ display: '规格型号', name: 'inv_model', align: 'left'},
				{ display: '计量单位', name: 'unit_name', align: 'left'},
				{ display: '价格', name: 'price', align: 'right' , 
					render:function(rowdata){ 
				    	return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p04006}',1);
				    } 	 
				},
				{ display: '数量', name: 'amount', align: 'right' ,type :'float'}
		     ],
		     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiInMatchDetail.do?isCheck=false',
		     width: '95%', height: '80%', checkbox: false, rownumbers:true,delayLoad:true,
		     selectRowButtonOnly:true, isScroll :true ,
		     toolbar: { items: [
		     	{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
		        { line : true},
		        { text: '生成入库单（<u>I</u>）', id:'add', click: imp, icon:'add' },
		        { line : true},
		        { text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' }
		     ]}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
		grid.toggleCol("inv_id", false);
	}
	
	//获得选中行
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	
	//生成入库单
	function imp(){
		var data = gridManager.getData();
    	//alert(JSON.stringify(data));
		if (data.length == 0){
			$.ligerDialog.error('请配置！');
			return;
		}else{
			var num = $("#num").val();
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
				detail_rows.append('"unit_code":"').append(data.unit_code).append('",');
				detail_rows.append('"unit_name":"').append(data.unit_name).append('",');
				detail_rows.append('"is_highvalue":').append(data.is_highvalue).append(',');
				detail_rows.append('"is_batch":').append(data.is_batch).append(','); 
				detail_rows.append('"is_bar":').append(data.is_bar).append(','); 
				detail_rows.append('"is_per_bar":').append(data.is_per_bar).append(','); 
				detail_rows.append('"is_quality":').append(data.is_quality).append(','); 
				detail_rows.append('"is_single_ven":').append(data.is_single_ven).append(','); 
				detail_rows.append('"is_disinfect":').append(data.is_disinfect).append(','); 
				detail_rows.append('"amount":').append(data.amount*num).append(',');
				detail_rows.append('"price":').append(formatNumber(data.price, '${p04006 }', 0)).append(',');
				detail_rows.append('"amount_money":').append(formatNumber(data.price*data.amount*num, '${p04005 }', 0)).append('}');
			});
			detail_rows.append("]");
			//添加材料
			parent.add_rows(eval(detail_rows.toString()));
			this_close();
		}
		
    }
    
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
    
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('I', imp);
		hotkeys('C', this_close);
	}
	
	//字典
	function loadDict() {
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_code").setValue("${store_id}");
		liger.get("store_code").setText("${store_text}");
		autocompleteAsync("#store_match", "../../queryMatStoreMatch.do?isCheck=false", "id", "text", true, true, "", true);
		
		$("#num").ligerTextBox({width:160});
       
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				配套表：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_match" type="text" id="store_match" ltype="text" required="true" validate="{required:true,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" width="10%">
				添加套数：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="num" type="text" id="num" ltype="text" value="1" required="true" validate="{required:true}" />
            </td>
        </tr>
		
	</table>
    <div id="maingrid"></div>
	
</body>
</html>
