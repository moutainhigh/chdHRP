<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
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
    		var dept_code = liger.get("dept_code").getValue();
      	  
       	if(dept_code !=null && dept_code!=''){
   	         grid.options.parms.push({name:'dept_code',value:dept_code.split(".")[2]}); 
        }
       	grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()});
    	grid.options.parms.push({name:'asset_type_code',value:liger.get("asset_type_code").getValue()}); 
    	grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                              
                    		 return rowdata.acc_year + rowdata.acc_month
                    	 }
						,totalSummary:{
							type:'sum',
							render:function(suminf,column,cell){
								return '<div>合计</div>';
							}
						}
					 },
					 { display: '科室编码', name: 'dept_code', align: 'left'
					 },
					 { display: '科室名称', name: 'dept_name', align: 'left'
					 },
                     { display: '资产分类编码', name: 'asset_type_code', align: 'left'
					 },
					 { display: '资产分类名称', name: 'asset_type_name', align: 'left'
					 },
                  	 { display: '资金来源', name: 'source_code', align: 'left'
					 },
					 { display: '资金来源名称', name: 'source_name', align: 'left'
					 },
					 { display: '折旧额', name: 'depre_amount', align: 'left'
						 ,totalSummary: {
	                      type: 'sum',
	                      render: function (suminf, column, cell) {
	                         return '<div>' + suminf.sum ==null ? 0 : suminf.sum+ '</div>';
	                      }
	                 	}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostFassetDetail.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
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
		                { text: '同步', id:'syn', click: itemclick,icon:'add' },
						{ line:true }
		                
		                
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year   + "|" + 
								rowdata.acc_month   + "|" + 
								rowdata.dept_code   + "|" + 
								rowdata.asset_type_code   + "|" + 
								rowdata.source_id 
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
              		$.ligerDialog.open({url: 'costFassetDetailAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostFassetDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
									this.copy_code   +"@"+ 
									this.acc_year   +"@"+ 
									this.acc_month   +"@"+ 
									this.dept_code   +"@"+  
									this.asset_type_code   +"@"+ 
									this.source_id 
							)
                        });
                       
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostFassetDetail.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "syn":
                	$.ligerDialog.open({url: 'synPage.do?isCheck=false', height: 300,width: 300, title:'采集',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                	return; 
				case "import":
                	$.ligerDialog.open({url: 'costFassetDetailImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
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
            }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"acc_year="+vo[3]   +"&"+ 
			"acc_month="+vo[4]   +"&"+
			"dept_code="+vo[5]   +"&"+ 
			"asset_type_code="+vo[6]   +"&"+ 
			"source_id="+vo[7];
    	$.ligerDialog.open({ url : 'costFassetDetailUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostFassetDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	
    	autocomplete("#dept_code","../queryDeptDictCode.do?isCheck=false","id","text",true,true);
    	
    	 autocomplete("#asset_type_code","../queryFassetTypeArrt.do?isCheck=false","id","text",true,true);
         
         autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true);
         
         $("#b_year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#b_year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
    }  
	
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","固定资产折旧明细采集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var dept = liger.get("dept_id").getValue();
		
		var printPara={			
			usePager:false,
		 	dept_id:$("#dept_id").val(),
           	asset_id:$("#asset_id").val(),
           	asset_type_id:$("#asset_type_id").val(),   
           	source_id:$("#source_id").val()   ,
           	b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val()
         };
		ajaxJsonObjectByUrl("queryCostFassetDetail.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>";
	                 trHtml+="<td>"+item.dept_code+"</td>";
	                 trHtml+="<td>"+item.dept_name+"</td>";
	                 trHtml+="<td>"+item.asset_code+"</td>"; 
	                 trHtml+="<td>"+item.asset_name+"</td>";
	                 trHtml+="<td>"+item.asset_type_code+"</td>";
	                 trHtml+="<td>"+item.asset_type_name+"</td>";
	                 trHtml+="<td>"+item.depre_amount+"</td>"; 
	                 trHtml+="<td>"+item.source_code+"</td>";
	                 trHtml+="<td>"+item.source_name+"</td>";
					 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","固定资产折旧明细采集.xls",true);
	    },true,manager);
		return;
	 }	
	      function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#b_year_month").val()+"至"+$("#e_year_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "固定资产折旧明细采集",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostFassetDetailService",
	 	   			method_name: "queryCostFassetDetailPrint",
	 	   			bean_name: "costFassetDetailService"
	 	   			
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

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
           <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
            <td align="left" class="l-table-edit-td"><input name="asset_type_code" type="text" id="asset_type_code" /></td>
        	
        </tr> 
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
            <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">科室编码</th>
					<th width="200">科室名称</th>
					<th width="200">资产编码</th>
					<th width="200">资产名称</th>
					<th width="200">资产分类编码</th>
					<th width="200">资产分类名称</th>
					<th width="200">折旧额</th>
					<th width="200">资金来源</th>
					<th width="200">资金来源名称</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
