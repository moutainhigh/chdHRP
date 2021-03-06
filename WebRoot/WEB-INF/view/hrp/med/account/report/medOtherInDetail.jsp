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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			 
			amount_money:function(value){//金额
				return formatNumber(value, 2, 1);
			},
			price:function(value){//单价
   			 return formatNumber(value, '${p08006 }', 1); 
   		 
			},
			
		 
	};
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
        
        $("#is_last").bind("change",function(){
			//f_setColumns();
			query();
		});
        
        $("#is_showStore").bind("change",function(){
			//f_setColumns();
			query();
		});
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		var begin_date = $("#begin_date").val();
		var end_date = $("#end_date").val();
		
		$("#year_month_span").text(begin_date+"至"+end_date);
			
		if(begin_date == ''){
			$.ligerDialog.error('开始期间不能为空');
			return ;
		}
		
		if(end_date == ''){
			$.ligerDialog.error('结束期间不能为空');
			return ; 
		}
		if(begin_date > end_date){
			$.ligerDialog.error('开始期间不能大于结束期间');
			return;
		}
		
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()}); 
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]});
		grid.options.parms.push({
			name:'bus_type_code', 
			value : liger.get("bus_type_code").getValue() == "" ?  "" :"("+liger.get("bus_type_code").getValue().replace(/;/g,',')+")"
		});
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()}); 
		
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		columns: [
    		          { display: '仓库编码', name: 'store_code', align: 'left', width: '120' 
					},{ display: '仓库名称', name: 'store_name', align: 'left',width: '120' 
			    	},{ display: '药品编码', name: 'inv_code', align: 'left', width: '120'
				 	},{ display: '药品名称', name: 'inv_name', align: 'left', width: '150'
				 	},{ display: '计量单位', name: 'unit_name', align: 'left', width: '60'
				 	},{ display: '规格型号', name: 'inv_model', align: 'left', width: '150'
				 	},{ display: '单价', name: 'price', align: 'right', width: '80',
				 			render: function (rowdata, rowindex, value) {
								return formatNumber(value, '${p08006 }', 1);
							}
				 	},{ display: '数量', name: 'amount', align: 'right', width: '80'
			 		},{ 
			 			display: '金额', name: 'amount_money', align: 'right', width: '100',
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p08005 }', 1);
						}
			 		},{ display: '批号', name: 'batch_no', align: 'left', width: '120'
	 				},{ display: '条形码', name: 'bar_code', align: 'left', width: '80'
	 				}
	 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedOtherInDetail.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false  
			selectRowButtonOnly:true,
			toolbar: { items: [
	  			 { text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				 { line:true },
			     { text: '打印', id:'print', click: print, icon:'print' },
				 { line:true }
	  		]}	
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
     
    function loadDict(){
		//字典下拉框
		autocompleteAsyncMulti("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true,{codes : '8,10'},true, false, 180);
		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true,"", false, false, 180);
		$("#begin_date").ligerTextBox({width:110});
        $("#end_date").ligerTextBox({width:110});
        $("#inv_code").ligerTextBox({width:270});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        
	}  
	
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=$("#begin_date").val()+"至"+$("#end_date").val()+"其他入库明细查询";
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
   		var printPara={};
    	 var printPara={
       			title:'其他入库明细统计表',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
    				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
       			],
       			foot:[
    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
    				{"cell":2,"value":"复核人:","colspan":colspan_num-4,"br":false},
    				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryMedOtherInDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
  	
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	<font color="red" size="2">*</font>确认日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">仓库名称：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text"/>
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%" style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">药品信息：</td>
        	<td align="left" class="l-table-edit-td" width="20%">
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:20}" />
			</td>
        </tr> 
    </table>
    
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>
	    	其他入库明细表
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
