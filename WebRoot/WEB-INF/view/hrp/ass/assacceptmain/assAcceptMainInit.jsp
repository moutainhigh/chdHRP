<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var grid;
	var gridManager = null;
	var userUpdateStr;
	var selectData = "";
	var clicked = 0;
     $(function (){
         loadDict();//加载下拉框
        loadHead(null);	
        loadButton();
        query();
        $("#ins_date").ligerTextBox({width:100}); 
        $("#ven").ligerTextBox({width:160});
        $("#contract_no").ligerTextBox({width:160});
        $("#contract_type").ligerTextBox({width:160});
        $("#contract_ori_no").ligerTextBox({width:160});
        $("#contract_name").ligerTextBox({width:245});
        $("#ass_year").ligerTextBox({width:160});
        $("#ass_month1").ligerTextBox({width:100});
        $("#ass_month2").ligerTextBox({width:100});
        $("#ven_id").ligerTextBox({width:160});
        $("#sign_date1").ligerTextBox({width:100});
        $("#sign_date2").ligerTextBox({width:100});
        $("#buy_type").ligerTextBox({width:160});
        $("#emp_main").ligerTextBox({width:160});
        $("#provider").ligerTextBox({width:160});
        $("#is_bid").ligerTextBox({width:160});
        $("#create_date1").ligerTextBox({width:100});
        $("#create_date2").ligerTextBox({width:100});
        $("#check_date1").ligerTextBox({width:100});
        $("#check_date2").ligerTextBox({width:100});
        $("#state").ligerTextBox({width:160});
        $("#ven_id_in").ligerTextBox({width:160});
        $("#contract_id").ligerTextBox({width:160});
        $("#ven_id").ligerTextBox({width:160});
        $("#dept_id").ligerTextBox({width:160});
        $("#accept_emp").ligerTextBox({width:160});
        $("#accept_date").ligerTextBox({width:160});
        $("#ins_ass_year").ligerTextBox({
			width : 160
		});
		$("#ins_ass_month").ligerTextBox({
			width : 160
		});
     });  
     
     function  query(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
 		grid.options.parms.push({name:'contract_no',value:$("#contract_no").val()}); 
	   	  grid.options.parms.push({name:'contract_type',value:$("#contract_type").val()}); 
	   	  grid.options.parms.push({name:'contract_ori_no',value:$("#contract_ori_no").val()}); 
	   	  grid.options.parms.push({name:'contract_name',value:$("#contract_name").val()}); 
	   	  grid.options.parms.push({name:'ass_year',value:$("#ass_year").val()}); 
	   	  grid.options.parms.push({name:'ass_month1',value:$("#ass_month1").val()}); 
	   	  grid.options.parms.push({name:'ass_month2',value:$("#ass_month2").val()}); 
	   	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
	   	  grid.options.parms.push({name:'sign_date1',value:$("#sign_date1").val()}); 
	   	  grid.options.parms.push({name:'sign_date2',value:$("#sign_date2").val()}); 
	   	  grid.options.parms.push({name:'buy_type',value:$("#buy_type").val()}); 
	   	  grid.options.parms.push({name:'emp_main',value:liger.get("emp_main").getValue()}); 
	   	  grid.options.parms.push({name:'provider',value:$("#provider").val()}); 
	   	  grid.options.parms.push({name:'is_bid',value:$("#is_bid").val()}); 
	   	  grid.options.parms.push({name:'create_date1',value:$("#create_date1").val()}); 
	   	  grid.options.parms.push({name:'create_date2',value:$("#create_date2").val()}); 
	   	  grid.options.parms.push({name:'check_date1',value:$("#check_date1").val()}); 
 	    grid.options.parms.push({name:'check_date2',value:$("#check_date2").val()}); 
 	    grid.options.parms.push({name:'state',value:$("#state").val()}); 
 	    grid.options.parms.push({name:'isExistsAccept',value:"0"}); 
 	grid.loadData(grid.where);
  }
     
     function loadHead(){
    	 grid = $("#maingrid")
			.ligerGrid(
					{
						columns :[
							{ display: '合同号', name: 'contract_no', align: 'left',width: '110',frozen: true
						 		},
						 { display: '统计年度', name: 'ass_year', align: 'left',width: '60',frozen: true
							 		},
		                 { display: '统计月份', name: 'ass_month', align: 'left',width: '60',frozen: true
							 		},
	                     { display: '合同类别', name: 'contract_type', align: 'left',width: '70',
						 			render : function(rowdata, rowindex,
											value) {
										if(rowdata.contract_type == 0){
											return "买卖合同";
										}else if(rowdata.contract_type == 1){
											return "赠与合同";
										}else if(rowdata.contract_type == 2){
											return "借款合同";
										}else if(rowdata.contract_type == 3){
											return "租赁合同";
										}else if(rowdata.contract_type == 4){
											return "融资租赁合同";
										}else if(rowdata.contract_type == 5){
											return "承揽合同";
										}else if(rowdata.contract_type == 6){
											return "建设工程合同";
										}
						 			}
						 		},
	                     { display: '合同原始编号', name: 'contract_ori_no', align: 'left',width: '120'
						 		},
	                     { display: '合同名称', name: 'contract_name', align: 'left',width: '120'
						 		},
	                     { display: '供应商', name: 'ven_name', align: 'left',width: '100'
						 		},
	                     { display: '签订日期', name: 'sign_date', align: 'left',width: '90'
						 		},
	                     { display: '采购方式', name: 'buy_type', align: 'left',width: '70',
						 			render : function(rowdata, rowindex,
											value) {
										if(rowdata.buy_type == 0){
											return "自主采购";
										}
										return "集中采购";
						 			}
						 		},
	                     { display: '开始日期', name: 'start_date', align: 'left',width: '90'
						 		},
	                     { display: '结束日期', name: 'end_date', align: 'left',width: '90'
						 		},
	                     { display: '合同描述', name: 'contract_detail', align: 'left',width: '150'
						 		},
	                     { display: '售后服务说明', name: 'service_detail', align: 'left',width: '150'
						 		},
	                     { display: '我方负责人', name: 'emp_main_name', align: 'left',width: '70'
						 		},
	                     { display: '对方负责人', name: 'provider', align: 'left',width: '70'
						 		},
	                     { display: '是否经过招标', name: 'is_bid', align: 'left',width: '80',
						 			render : function(rowdata, rowindex,
											value) {
										if(rowdata.is_bid == 0){
											return "否";
										}
										return "是";
									}
						 		},
	                     { display: '合同金额', name: 'contract_money', align: 'right', width: '120',
						 			render : function(rowdata, rowindex,
											value) {
										 return formatNumber(
													rowdata.contract_money == null ? 0
															: rowdata.contract_money,
													'${ass_05005}',
													1);
									},totalSummary:{type: 'Sum'}
						 		},
	                     { display: '制单人', name: 'create_emp_name', align: 'left',width: '70'
						 		},
	                     { display: '制单日期', name: 'create_date', align: 'left',width: '90'
						 		},
	                     { display: '审核人', name: 'check_emp_name', align: 'left',width: '70'
						 		},
	                     { display: '审核日期', name: 'check_date', align: 'left',width: '90'
						 		},
	                     { display: '状态', name: 'state', align: 'left',width: '60',
						 			render : function(rowdata, rowindex,
											value) {
						 				if(rowdata.state == 0){
											return "新建";
										}else if(rowdata.state == 1){
											return "审核";
										}else if(rowdata.state == 2){
											return "履行";
										}else if(rowdata.state == 3){
											return "归档";
										}
									}
						 } ],
						dataAction : 'server',
						dataType : 'server',
						usePager : false,
						url : 'queryAssContractByAccept.do?isCheck=false',
						width : '100%',
						height : '98%',
						checkbox : true,
						delayLoad : true,//初始化明细数据
						toolbar : {
							items : [ 
								{ text: '查询', id:'search', click: query,icon:'search' },
		                     	{ line:true },
							          {
								text : '保存',
								id : 'saveDetail',
								click : itemclick,
								icon : 'save'
							},
					        {line : true}, 
					        {
								text : '关闭',
								id : 'close',
								click : this_close,
								icon : 'candle'
						    } 

							]
						}
					});

    	 gridManager = $("#maingrid").ligerGetGridManager();
 		
     }
     function this_close(){
 		frameElement.dialog.close();
 	}
     
 	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			
			case "saveDetail":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					$.ligerDialog.confirm('确认保存?',function(yes) {
					    var ass_year=$("#ass_year").val();
					    var ass_month=$("#ass_month").val();
						var contract_id=liger.get("contract_id_in").getValue();//.split("@")[0];            
						var ven_id=liger.get("ven_id_in").getValue();//.split("@")[0];                  
						var dept_id=liger.get("dept_id").getValue();//.split("@")[0];       	            
						var accept_emp=liger.get("accept_emp").getValue();            
						var accept_date=$("#accept_date").val();
						
						if(accept_emp == ""){
							$.ligerDialog.error('请选择验收人');
							return;
						}
						if(accept_date == ""){
							$.ligerDialog.error('请选择验收日期');
							return;
						}
						if(dept_id == ""){
							$.ligerDialog.error('请选择验收科室');
							return;
						}
						if(contract_id == ""){
							$.ligerDialog.error('请选择行合同');
							return;
						}
						if(ven_id == ""){
							$.ligerDialog.error('请选择供应商');
							return;
						}
						var contract_ids =[];
			        	$(data).each(function (){	
			        		
			        		contract_ids.push(this.contract_id);
								});
							if (yes) {ajaxJsonObjectByUrl(
									"initAssAcceptByContract.do?isCheck=false",
							   {
								  ass_year:ass_year,
								  ass_month:ass_month,
								  contract_id:contract_id,
								  ven_id:ven_id,
								  dept_id:dept_id,
								  accept_emp:accept_emp,
								  accept_date:accept_date,
								  contract_ids : contract_ids
																
									},
									function(responseData) {
										if (responseData.state == "true") {
											query();
											parentFrameUse().query();
											parentFrameUse().openUpdate(responseData.update_para);
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
        
        var param = {query_key:''};
    	
    	autocomplete("#contract_id", "../queryContractMain.do?isCheck=false", "id",
				"text", true, true);
   
    	autocomplete("#contract_id_in", "../queryContractMain.do?isCheck=false", "id",
				"text", true, true);
    	autocomplete("#ven_id_in", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true,null,null,null,"400");
    	autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true);
    	autocomplete("#accept_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
    	
    	  //字典下拉框
        //默认年
		autodate("#ass_year","YYYY");
		//默认月
		autodate("#ass_month1","mm");
		//默认月
		autodate("#ass_month2","mm");
        //供应商
    	autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true, param, true,null,"400");
    	//我方负责人
    	autocomplete("#emp_main","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true, param, true);
    	$("#is_bid").ligerComboBox({
			width : 160
		});
    	//状态
    	$("#state").ligerComboBox({
			width : 160
		});
    	//采购方式
    	$("#buy_type").ligerComboBox({
			width : 160
		});
    	//合同类别
    	$("#contract_type").ligerComboBox({
			width : 160
		});
     }  
//键盘事件

 function loadButton(){
		$("#but_query").ligerButton({click: query, width:90});
		
	}


    </script>
  
  </head>
  
   <body >
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" id="sequence" name="sequence"/>
   <form name="form1" method="post"  id="form1" >
   	 <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="contract_name" type="text" id="contract_name"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同号：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_no" type="text" id="contract_no"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同原始编号：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_ori_no" type="text" id="contract_ori_no"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计月份：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="ass_month1" type="text" id="ass_month1"   class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'ass_month2\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_month2" type="text" id="ass_month2"   class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ass_month1\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同类别：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="contract_type" name="contract_type">
            		<option value="">全部</option>
            		<option value="0">买卖合同</option>
            		<option value="1">赠与合同</option>
            		<option value="2">借款合同</option>
            		<option value="3">租赁合同</option>
            		<option value="4">融资租赁合同</option>
            		<option value="5">承揽合同</option>
            		<option value="6">建设工程合同</option>
            	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year"   class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">签订日期：</td>
            <td align="left" class="l-table-edit-td"><input name="sign_date1" type="text" id="sign_date1"   class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'sign_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="sign_date2" type="text" id="sign_date2"   class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'sign_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">采购方式：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="buy_type" name="buy_type">
            		<option value="">全部</option>
            		<option value="0">自主采购</option>
            		<option value="1">集中采购</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
       
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date1" type="text" id="create_date1"   class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'create_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date2" type="text" id="create_date2"   class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">我方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_main" type="text" id="emp_main"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="provider" type="text" id="provider"   /></td>
            <td align="left"></td>
        </tr> 
        
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="check_date1" type="text" id="check_date1"   class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'check_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
           	<td align="center" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input name="check_date2" type="text" id="check_date2"   class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'check_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否经过招标：</td>
            <td align="left" class="l-table-edit-td">
				<select id="is_bid" name="is_bid">
					<option value="">全部</option>
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
             <td align="left" class="l-table-edit-td">
            	<select id="state" name="state">
						<option value="">全部</option>
                		<option value="1">审核</option>
                		<option value="2">履行</option>
                		<option value="3">归档</option>
                </select>
            </td>
            <td align="left"></td>
        </tr> 
     
    </table>
    <hr size="1" width="1400" color="#A3COE8" align="left" style="position:absolute;top:145px;"/>
   
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
       
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>验收人：</td>
            <td align="left" class="l-table-edit-td"><input name="accept_emp" type="text" id="accept_emp"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>验收日期：</td>
            <td align="left" class="l-table-edit-td"><input name="accept_date" type="text" id="accept_date"   class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>验收科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_id_in" type="text" id="contract_id_in"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_ass_year" type="text" id="ins_ass_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
      
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_ass_month" type="text" id="ins_ass_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id_in" type="text" id="ven_id_in"   /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
     
   <div id="maingrid"></div>
    </body>
</html>
