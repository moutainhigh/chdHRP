<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null; 
    var userUpdateStr;
    
    $(function (){
        loadDict();//加载数据
    	loadHead(null);
		loadHotkeys();
		query();
    });
    
    //查询
    function  query(){
    		grid.options.parms=[];
			grid.options.newPage=1;
        	//根据表字段进行添加查询条件
    	  	var store_id = liger.get("store_id").getValue();

        	if(store_id){
        		grid.options.parms.push({name:'store_id',value:store_id.split(",")[0]}); 
        	  	grid.options.parms.push({name:'store_no',value:store_id.split(",")[1]}); 
        	}
        	
        	/* grid.options.parms.push({
        		name:'inv_id',
        		value:liger.get("inv_code").getValue() == null ?"":liger.get("inv_code").getValue().split(",")[0]
        	});  */
        	
        	grid.options.parms.push({
        		name:'inv_code',
        		value: $("#inv_code").val() 
        	}); 
    		grid.options.parms.push({
    			name : 'is_zero',
    			value : $("#is_zero").prop("checked") ? 1 : 0
    		}); 
    		grid.options.parms.push({
    			name : 'mat_type_code',
    			value : liger.get("mat_type_id").getText().split(" ")[0]
    		}); 
    		grid.options.parms.push({name : 'begin_price',value : liger.get("begin_price").getValue()});
    		grid.options.parms.push({name : 'end_price',value : liger.get("end_price").getValue()});
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
				{ 
					display: '材料编码', name: 'inv_code', align: 'left', width: '120'
				}, { 
					display: '材料名称', name: 'inv_name', align: 'left',width:'240'
				}, { 
					display: '规格', name: 'inv_model', align: 'left',width:'140'
				}, { 
					display: '单位', name: 'unit_name', align: 'left',width: '80'
				}, { 
					display: '生成厂商', name: 'fac_name', align: 'left',width: '180'
				}, { 
					display: '批号', name: 'batch_no', align: 'left',width:'130'
				}, { 
					display: '当前库存', name: 'cur_amount', align: 'right',width: '80'
				}, { 
					display: '即时库存', name: 'imme_amount', align: 'right',width: '80'
				}, { 
					display: '单价', name: 'price', align: 'left',width: '90',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
				}, { 
					display: '出库数量(E)', name: 'out_amount', align: 'right',width: '80',
					editor : {
						type : 'text'
					}
				}
			],
			dataAction: 'local', dataType: 'server', usePager:true, url:'queryMatInvBatchList.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers:true, delayLoad:true, isAddRow:false,
			selectRowButtonOnly:true, enabledEdit : true, //heightDiff: -10,
			toolbar: { 
				items: [
					{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
					{ line:true }, 
					{ text: '复制库存（<u>C</u>）', id:'copy', click: copyStock, icon:'copy' },
					{ line:true }, 
					{ text: '选择（<u>A</u>）', id:'add', click: addNew, icon:'add' },
				]
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //确定添加
	function addNew(){
		var data = gridManager.getCheckedRows();
		var nos = "";
		if (data.length == 0){
			$.ligerDialog.warn('请选择材料!');
			return;
		}else{
            var ParamVo =[];
            var inv_codes = "";
			$.each(data, function(index, data){
				if(validateStr(this.out_amount) && validateStr(this.out_amount) && this.out_amount != 0){
					ParamVo.push(
						this.group_id +"@"+ 
						this.hos_id +"@"+ 
						this.copy_code +"@"+ 
						liger.get("store_id").getValue().split(",")[0] +"@"+ 
						this.inv_id +"@"+ 
						this.inv_code +"@"+ 
						this.inv_name +"@"+ 
						this.batch_no +"@"+ 
						this.out_amount +"@"
					);
				}else{
					inv_codes = inv_codes + this.inv_code + ",";
				}
			});
			
			if(inv_codes != ""){
				$.ligerDialog.error("材料 "+inv_codes+" 的出库数量为空，请填写！");
				return;
			}
			
			$.ligerDialog.confirm('确定生成出库单?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("queryOutInvListByChoiceInv.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		//清空父页面表格数据
                		parent.delete_allRows();
                		//添加材料
                		parent.grid.options.data = {Rows:responseData.Rows};
						parent.grid._setData();
						parent.grid.addRow();
							
                		//parent.add_Rows(responseData.Rows);
                	    //parent.is_addRow();
                	    //关闭
                	    frameElement.dialog.close();
                	});
            	}
            }); 
		}
	}
	
	//复制账面数量
    function copyStock(){
    	var check_detail_data = gridManager.rows;
    	$.each(check_detail_data, function(d_index, d_content){ 
    		gridManager.updateCell('out_amount', d_content.imme_amount, d_content);
		}); 
    	
    }
    
    function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
    
    function loadDict(){
		//字典下拉框
		$("#store_id").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_id").setValue("${store_id}");
		liger.get("store_id").setText("${store_text}");
		autocomplete("#mat_type_id", "../../../queryMatType.do?isCheck=false", "id", "text", true, true,null,false,null,"180");
		   $("#begin_price").ligerTextBox({width:105,currency:true,precision:2});
	        //currency 货币格式 precision 显示几位小数
	        $("#end_price").ligerTextBox({width:105,currency:true,precision:2});
    	//autocomplete("#inv_code", "../../queryMatInvDict.do?isCheck=false", "id", "text", true, true);
		$("#inv_code").ligerTextBox({width:180}); 
	}  
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('C', copyStock);
		hotkeys('A', addNew);
	}

    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr>
        <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" readonly="readonly" disabled="disabled"/></td>
			
			<td align="right" class="l-table-edit-td" >物资材料：</td>
			<td align="left" class="l-table-edit-td"><input name="inv_code" type="text" id="inv_code" ltype="text"/></td>
			<td align="right" class="l-table-edit-td" >物资材分类：</td>
			<td align="left" class="l-table-edit-td"><input name="mat_type_id" type="text" id="mat_type_id" ltype="text"/></td>
			
			<td align="right">
				<input name="is_zero" type="checkbox" id="is_zero" ltype="text" />显示零库存
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr> 
		<tr>
		 <td align="right" class="l-table-edit-td"  width="10%">
            	价格区间：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input  name="begin_price" id="begin_price" type="text" />
						</td>
						<td>元</td>
						<td align="right" class="l-table-edit-td"  > 至  </td>
						<td>
							<input  name="end_price" id="end_price" type="text" />
						</td>
						<td>元</td>
            		</tr>
				</table> 
	        </td>
		</tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
