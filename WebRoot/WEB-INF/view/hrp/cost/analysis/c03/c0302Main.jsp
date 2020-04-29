<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
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
    	    grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
    	  grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
    	  grid.options.parms.push({name:'item_code',value:liger.get("income_item_id").getValue()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '收入项目', name: 'item_name', align: 'left',width:'20%'},
                     { display: '行次', name: '', align: 'center',width:'10%',
                    	 render:function(rowdata,rowindex,value){
                    		 if(rowdata.item_name=='总计'){
                    			 return '';
                    		 }else{
                    			 return rowindex+1;
                    		 }
                    	 }},
                     { display: '门诊收入', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'医疗收入',name:'t_1',align:'right',formatter:'###,##0.00',
	                    	 			render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_1,2,1);
									}
                      				},
                      				{display:'药品收入',name:'t_2',align:'right',formatter:'###,##0.00',
	                    	 			render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_2,2,1);
									}
                      				},
						 			{display:'材料收入',name:'t_3',align:'right',formatter:'###,##0.00',
                        			 render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.t_3,2,1);
									}
                      			}
                    	         ]
					 },
					 { display: '住院收入', name: '', align: 'center',width:'20%',
						 columns:[
                    	          {display:'医疗收入',name:'t_4',align:'right',formatter:'###,##0.00',
	                    	 			render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_4,2,1);
									}
                      				},
                      				{display:'药品收入',name:'t_5',align:'right',formatter:'###,##0.00',
	                    	 			render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_5,2,1);
									}
                      				},
						 			{display:'材料收入',name:'t_6',align:'right',formatter:'###,##0.00',
                        			 render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.t_6,2,1);
									}
                      			}
                    	         ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0302.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id: 'print',click: print, icon: 'print'}
                     	/* ,
    					{ text: '生成', id:'add', click: itemclick, icon:'add' },
    	                { line:true } */
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costAnalysisC0302AddPage.do?isCheck=false', height: 300,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
            }   
        }
        
    }
   
    function loadDict(){
    	
    	
   	    autodate("#year_month_begin","yyyyMM");
   	 
   	    autodate("#year_month_end","yyyyMM");
    	
   	 $("#year_month_begin").ligerTextBox({ width:120 });
 	$("#year_month_end").ligerTextBox({ width:120 });
    	
    	autocomplete("#income_item_id","../../queryCostIncomeItemArrt.do?isCheck=false","id","text",true,true);
	}   
    
  //打印
//     function print(){
//  	   var exportPara = {
// 				usePager: false,
// 				year_month_begin: $("#year_month_begin").val(),
// 				year_month_end: $("#year_month_end").val()
// 			};
 		  
//  	   $.ajax({
//  		   url:"queryAnalysisC0302.do",
//  		   type:"post",
//  		   data:exportPara,
//  		   dataType:"JSON",
//  		   success:function(res){
 			  
//  			   var data={
//  					   headers:[
//  								{ x: 0, y: 0, rowSpan: 2, colSpan: 1, displayName: "收入项目", name: "item_name",size:120 },
//  								{ x: 1, y: 0, rowSpan: 2, colSpan: 1, displayName: "行次", name: " ",size:40 },
//  								{ x: 2, y: 0, rowSpan: 1, colSpan: 3, displayName: "门诊收入" },
//  								{ x: 2, y: 1, rowSpan: 1, colSpan: 1, displayName: "医保收入", name: "t_1", formatter: "#,##0.00",size:110 },
//  								{ x: 3, y: 1, rowSpan: 1, colSpan: 1, displayName: "药品收入" , name: "t_2", formatter: "#,##0.00",size:110 },
//  								{ x: 4, y: 1, rowSpan: 1, colSpan: 1, displayName: "其他收入", name: "t_3", formatter: "#,##0.00",size:110 },
//  								{ x: 5, y: 0, rowSpan: 1, colSpan: 3, displayName: "住院收入" },
//  								{ x: 5, y: 1, rowSpan: 1, colSpan: 1, displayName: "医保收入", name: "t_4", formatter: "#,##0.00",size:110 },
//  								{ x: 6, y: 1, rowSpan: 1, colSpan: 1, displayName: "药品收入" , name: "t_5", formatter: "#,##0.00",size:110 },
//  								{ x: 7, y: 1, rowSpan: 1, colSpan: 1, displayName: "其他收入", name: "t_6", formatter: "#,##0.00",size:110 },
//  					    ],
//  					    rows: res.Rows
//  			   }
 			  
//  			   viewPrint(data, "收入分类分析明细表");
//  		   },
//  		   error: function (res) {
// 					console.error(res);
// 				}
//  	   });
//     };
         function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "收入分类分析明细表(医院)",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c03.C03Service",
 	   			method_name: "queryC0302Print",
 	   			bean_name: "c03Service",
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);
   		
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<div class="sample-turtorial" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_end" type="text" id="year_month_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
        
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入项目：</td>
            <td align="left" class="l-table-edit-td"><input name="income_item_id" type="text" id="income_item_id" /></td>
        </tr>  
    </table>

	<div id="maingrid"></div>
</body>
</html>