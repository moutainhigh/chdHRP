<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    $(function ()
    {
    	
        loadDict();//加载下拉框
    	
    	loadHead(null);	
    	$(':button').ligerButton({width:80});
    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'io_type',value : liger.get("io_type").getValue()});
		grid.options.parms.push({name : 'pay_type_code',value : liger.get("pay_type_code").getValue()});
		
		var charge_code = $("#charge_code").val();

		if(charge_code){
			
			grid.options.parms.push({name : 'charge_code',value : charge_code});
			
		}
		
		var rep_no = $("#rep_no").val();
		
		if(rep_no){
			
			grid.options.parms.push({name : 'rep_no',value : rep_no});
			
		}
		
		grid.options.parms.push({name : 'charge_date_start',value : $("#charge_date_start").val()});
		
		grid.options.parms.push({name : 'charge_date_end',value : $("#charge_date_end").val()});
		grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
		grid.loadData(grid.where);//加载查询条件
		
     }

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
					columns : [
									{display : '日报序号',name : 'rep_no',width: 120,align : 'left'}, 
									{display : '收费日期',name : 'charge_date',align : 'left',width: 120,align : 'left'},
									{display : '收款员编码',name : 'charge_code',width: 120,align : 'left'},
									{display : '收款员名称',name : 'charge_name',align : 'left',width: 120,align : 'left'},
									{display : '收费金额',name : 'charge_money',align : 'left',width: 120,align : 'right',
										render: function (item) {
											return formatNumber(item.charge_money,2,1);
		                    			}	
									},
									{display : '状态',name : 'state',align : 'left',width: 120,align : 'left'},
									{display : '支付方式',name : 'pay_type_code',align : 'left',width: 120,align : 'left'},
									{display : '卡号',name : 'card_no',align : 'left',width: 120,align : 'left'},
									{display : '患者姓名',name : 'patient_name',align : 'left',width: 120,align : 'left'},
									{display : '诊疗编号',name : 'hospital_no',align : 'left',width: 120,align : 'left'},
									{display : '票据开始号',name : 'begin_no',align : 'left',width: 120,align : 'left'},
									{display : '票据结束号',name : 'end_no',align : 'left',width: 120,align : 'left'}
							],
					dataAction: 'server',dataType: 'server',usePager:true,url:'queryHisAccPreDetail.do',width: '100%', height: '100%', checkbox: false,
					rownumbers:true,delayLoad:true,selectRowButtonOnly:true,pageSize:500//heightDiff: -10,
					
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}

    

    function loadDict(){

    	autocomplete("#pay_type_code", "../../../queryPayType.do?isCheck=false","id", "text", true, true,"",false,"",210);
    	
    	$("#charge_date_start").ligerTextBox({width:90});autodate("#charge_date_start", "yyyy-mm-dd", "month_first");
    	
    	$("#charge_date_end").ligerTextBox({width:90});autodate("#charge_date_end", "yyyy-mm-dd", "month_last");
    	
    	$("#rep_no").ligerTextBox({width:150});
    	
    	$("#charge_code").ligerTextBox({width:150});
    	
    	var io_type_data = [{ id: 0, text: '门诊'},{ id: 1, text: '住院' }];
    	if(${type}==1){
    		//温医个性化需求
    		io_type_data.push({ id: 2, text: '其它' });
    	}
    	$("#io_type").ligerComboBox({data: io_type_data,width:120,valueField:'id',textField:'text',autocomplete: false,cancelable: false}); 
    	
    	liger.get("io_type").setValue(1);
    	
		liger.get("io_type").setText('住院');
		
		$("#state").ligerComboBox({
            width : 120,
            data: [
                { text: '0 缴费', id: '0' },
                { text: '1 退费', id: '1' },
                { text: '2 冲销', id: '2' },
                { text: '3 作废', id: '3' }
            ],
            cancelable: true
        });

	}  
    
    function printDate(){
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
						{"cell":0,"value":"收费日期："+$("#charge_date_start").val()+"至"+$("#charge_date_end").val(),"colSpan":"5"},
						{"cell":3,"value":"医嘱类别："+liger.get("io_type").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
    	var io=liger.get("io_type").getValue();
    	if(io==0){
    		var printPara={
    	   			rowCount:1,
    	   			title:'预交金明细表',
    	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
    	   			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccPreService",
    				method_name: "queryHisAccPreDetailOPrint",
    				bean_name: "hisAccPreService",
    				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
    	   			};
    	}else{
    		var printPara={
    	   			rowCount:1,
    	   			title:'预交金明细表',
    	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
    	   			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccPreService",
    				method_name: "queryHisAccPreDetailIPrint",
    				bean_name: "hisAccPreService",
    				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
    	   			};
    	}
   		
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
	
    }
</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">收费日期：</td>
            <td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input name="charge_date_start" type="text" id="charge_date_start" ltype="text"  style="font-size:12px"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input name="charge_date_start" type="text" id="charge_date_end" ltype="text"  style="font-size:12px"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td"  style=padding-left:20px;">医嘱类别：</td>
            <td align="left" class="l-table-edit-td"><input name="io_type" type="text" id="io_type" ltype="text" /></td>
            
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">日报序号：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_no" type="text" id="rep_no" ltype="text" /></td>
            
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结算方式：</td>
			<td align="left" class="l-table-edit-td">
				<input name="pay_type_code" type="text" id="pay_type_code" ltype="text" />
			</td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
			<td align="left" class="l-table-edit-td">
				<div id="state" style="display:none"></div> 
			</td>
			
			<td align="right" class="l-table-edit-td"  style=padding-left:20px;"> 收款员：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_code" type="text" id="charge_code" ltype="text" /></td>
			
            <td align="right" colspan="4">
            	<button accessKey="Q" onclick="query();"><b>查询（<u>Q</u>）</b></button>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button onclick="printDate();"><b>打 印</b></button>
            </td>
        </tr> 
    </table>
    <div id="maingrid"></div>

</body>
</html>
