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
<!-- 资产月报表  （财务制度） -->
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys(); 
    	loadHead(null);	//加载数据
        
    });
    
    //查询
    function  query(){
    	
    	if(isnull($("#acc_year_month_begin").val()) || isnull($("#acc_year_month_end").val())){
   			$.ligerDialog.error("请选择起始会计期间！");
   			return;
   		}
    	
    	if($("#acc_year_month_begin").val() != $("#acc_year_month_end").val()){
   			$.ligerDialog.error("起始日期和结束日期必须相等！");
   			return;
   		}
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
        if(!isnull($("#acc_year_month_begin").val())){
    		grid.options.parms.push({name:'year_month_begin',value:$("#acc_year_month_begin").val()}); 
        }
        if(!isnull($("#acc_year_month_end").val())){
    		grid.options.parms.push({name:'year_month_end',value:$("#acc_year_month_end").val()}); 
        }
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资产分类编码', name: 'ass_type_code', align: 'left',width: '110',frozen: true
                     },
                     { display: '资产分类名称', name: 'ass_type_name', align: 'left',width: '110',frozen: true
					 		},
                     { display: '资产性质', name: 'ass_naturs_name', align: 'left',width: '110',frozen: true
					 		},
					 { display: '资金来源', name: 'source_name', align: 'left',width: '110',frozen: true
					 		},
                     { display: '原值',  align: 'center',columns:[
                                                                {display:'期初',name:'begin_money',align:'right',width:'120',
											                    	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.begin_money,'${ass_05005 }',1);
																		},formatter:'###,##0.00'
                                                                 },
																 {display:'借方',name:'add_money',align:'right',width:'100',
										                           	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.add_money,'${ass_05005 }',1);
																		},formatter:'###,##0.00'
                                                                 },
										                         {display:'贷方',name:'dec_money',align:'right',width:'100',
										                           	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.dec_money,'${ass_05005 }',1);
																		},formatter:'###,##0.00'
                                                                 },
										                         {display:'余额',name:'end_money',align:'right',width:'120',
										                           	 render : function(rowdata, rowindex,
																				value) {
																			 return formatNumber(rowdata.end_money,'${ass_05005 }',1);
																		},formatter:'###,##0.00'
                                                                 }
                     											  ]

					 		},
					 { display: '累计折旧', align: 'center',  
					 		columns:[
                                     {display:'期初',name:'depre_begin_money',align:'right',width:'120',
					                    	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_begin_money,'${ass_05005 }',1);
												},formatter:'###,##0.00'
                                      },
										 {display:'借方',name:'depre_dec_money',align:'right',width:'100',
				                           	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_dec_money,'${ass_05005 }',1);
												},formatter:'###,##0.00'
                                      },
				                         {display:'贷方',name:'depre_add_money',align:'right',width:'100',
				                           	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_add_money,'${ass_05005 }',1);
												},formatter:'###,##0.00'
                                      },
				                         {display:'余额',name:'depre_end_money',align:'right',width:'120',
				                           	 render : function(rowdata, rowindex,
														value) {
													 return formatNumber(rowdata.depre_end_money,'${ass_05005 }',1);
												},formatter:'###,##0.00'
                                      }
											]
					 		},
					 { display: '净值',align: 'center',  
						 		columns:[
	                                     {display:'期初',name:'remain_begin_money',align:'right',width:'120',
						                    	 render : function(rowdata, rowindex,
															value) {
														 return formatNumber(rowdata.remain_begin_money,'${ass_05005 }',1);
													},formatter:'###,##0.00'
	                                      },
											 
					                         {display:'期末',name:'remain_end_money',align:'right',width:'120',
					                           	 render : function(rowdata, rowindex,
															value) {
														 return formatNumber(rowdata.remain_end_money,'${ass_05005 }',1);
													},formatter:'###,##0.00'
	                                      }
												]
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssPropertyMonthMainSource.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     tree:{columnId:'ass_type_code'},
                     toolbar: { items: [
                                        { text: '查   询 （<u>Q</u>）', id:'search', click: query,icon:'search' },
                                        { line:true }, 
            						     { text: '打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' }
                                ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
       	//字典下拉框
       	$("#acc_year_month_begin").ligerTextBox({width:70}); 
       	$("#acc_year_month_end").ligerTextBox({width:70}); 
       

       	//默认年
        if(${ass_year_month}){
			$("#acc_year_month_begin").val('${ass_year_month}')
        }else{
        	autodate("#acc_year_month_begin","YYYYMM"); 
        }
        if(${ass_year_month}){
			$("#acc_year_month_end").val('${ass_year_month}')
        }else{
        	autodate("#acc_year_month_end","YYYYMM"); 
        }
       	
    } 
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', printDate);
	}

	
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
 		var date=$("#acc_year_month_begin").val()+"至"+$("#acc_year_month_end").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"会计期间："},
    	          {"cell":1,"value":date},
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":10,"value":"制表人:"},
    				{"cell":11,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产月报表_资金来源",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssPropertyMonthMainSourceService",
 				method_name: "queryAssPropertyMonthMainSourcePrint",
 				bean_name: "assPropertyMonthMainSourceService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr >
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year_month_begin" type="text" id="acc_year_month_begin" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" 
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="left" >&nbsp;至：</td>
			<td align="left"><input name="acc_year_month_end" type="text" id="acc_year_month_end" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
           
        </tr> 
       
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
