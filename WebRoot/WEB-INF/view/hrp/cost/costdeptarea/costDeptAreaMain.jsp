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
			var dept_dict = liger.get("dept_id").getValue();
            
			grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
	    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 
        	
    		if(dept_dict !=null && dept_dict !=''){
        		
        		grid.options.parms.push({name:'dept_id',value:dept_dict.split(".")[0]}); 
        		
            	grid.options.parms.push({name:'dept_no',value:dept_dict.split(".")[1]}); 
            	
        	}

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',
                    	 render:function(rowdata){
                    	 	return rowdata.acc_year+rowdata.acc_month;
                     	 }
					 },
                     { display: '科室编码', name: 'dept_code', align: 'left',render : function(rowdata, rowindex,value) {
                    	  if(rowdata.dept_code != null){
                    		  return "<a href=javascript:openUpdate('"+rowdata.acc_year +"|"+rowdata.acc_month+ "|" +rowdata.dept_id + "|" +rowdata.dept_no +"|" +rowdata.dept_code +"')>"+rowdata.dept_code+"</a>";
      					  }
                    	  else{
                    		  return null;
                    	  }
       			  		}
					 },
                     { display: '科室名称', name: 'dept_name', align: 'left'},
                     { display: '面积', name: 'arear', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDeptArea.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     checkBoxDisplay : f_checkBoxDisplay,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
						{ text: '按月删除', id:'delete', click: deleteMonthly,icon:'delete'},
						{ line:true}, 
//     	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                { text: '继承', id:'extend', click: add_extend, icon:'add' },
		                { line:true },
		                
		                /* { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true }, */
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					if(rowdata.dept_code == null){
    						return false ;
    					}
    					
						openUpdate(
								rowdata.acc_year   +"|"+ 
								rowdata.acc_month   +"|"+ 
								rowdata.dept_id   + "|" + 
								rowdata.dept_no  + "|" + 
								rowdata.dept_code
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }


    function f_checkBoxDisplay(rowdata){
        
      	 if (rowdata.acc_year+rowdata.acc_month == "总计")
   			    return false;
   	       }

    function deleteMonthly(){
    	if ($("#b_year_month").val() == '' || $("#e_year_month").val() == '') {
    		$.ligerDialog.question('统计年月不能为空')
    		return false;
    	}
    	$.ligerDialog.confirm('是否确认删除', function(yes) {
    		if (yes) {
    			var formPara = {
					b_year_month: $("#b_year_month").val(),
					e_year_month: $("#e_year_month").val(),
    			};
    			ajaxJsonObjectByUrl("deleteMonthlyCostDeptArea.do", formPara, function(responseData) {
    				if (responseData.state == "true") {
    					query();
    				}
    			});
    		}
    	});
        }

    function add_extend(){
	    	$.ligerDialog.open({
	    		url: 'costDeptAreaExtendMainPage.do?isCheck=false',
	    		height: 270,
	    		width: 500,
	    		title: '继承数据',
	    		modal: true,
	    		showToggle: false,
	    		showMax: false,
	    		showMin: true,
	    		isResize: true,
	    		buttons: [{
	    			text: '确定',
	    			onclick: function(item, dialog) {
	    				dialog.frame.saveCostDeptAreaExtend();
	    			},
	    			cls: 'l-dialog-btn-highlight'
	    		}, {
	    			text: '取消',
	    			onclick: function(item, dialog) {
	    				dialog.close();
	    			}
	    		}]
	    	});
        }
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costDeptAreaAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDeptArea(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.dept_id   +"@"+ 
							this.dept_no 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostDeptArea.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
					//公用import.jsp页面使用
					var para={
					    "column": [
					        {
					            "name": "acc_year",
					            "display": "统计年度",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "acc_month",
					            "display": "统计月份",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "dept_id",
					            "display": "科室编码",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "dept_name",
					            "display": "科室名称",
					            "width": "200",
					            "require":true
					        },{
					            "name": "arear",
					            "display": "面积",
					            "width": "200",
					            "require":true
					        }
					    ]/* ,
					    isUpdate:true */
					};
					importSpreadView("hrp/cost/costdeptarea/costDeptAreaImportPage.do?isCheck=false",para); 
                	
                	//$.ligerDialog.open({url: 'costDeptAreaImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"acc_year="+vo[0]   +"&"+ 
			"acc_month="+vo[1]   +"&"+  
			"dept_id="+vo[2]   +"&"+ 
			"dept_no="+vo[3] +"&"+ 
			"dept_code="+vo[4] ;
    	$.ligerDialog.open({ url : 'costDeptAreaUpdatePage.do?isCheck=false&' + parm,data:{}, height: 220,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDeptArea(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	$("#b_year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#b_year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
         }  
    
  //打印数据
	 function printDate(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		/* if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","科室面积采集",true);
			return;
		} */
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,
			
			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
           
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
    	            
		   dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:''
         };
		ajaxJsonObjectByUrl("queryCostDeptArea.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.dept_code+"</td>"; 
                     trHtml+="<td>"+item.dept_name+"</td>"; 
                     trHtml+="<td>"+item.arear+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","科室面积采集",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		/* if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","科室面积采集.xls",true);
			return;
		} */
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var exportPara={
				
				usePager:false,
				
				b_year_month:$("#b_year_month").val(),
				e_year_month:$("#e_year_month").val(),
		           
		           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
		    	            
				   dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:''
         };
		ajaxJsonObjectByUrl("queryCostDeptArea.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.dept_code+"</td>"; 
                     trHtml+="<td>"+item.dept_name+"</td>"; 
                     trHtml+="<td>"+item.arear+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","科室面积采集.xls",true);
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
	 	      		title: "面积采集",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostDeptAreaService",
	 	   			method_name: "queryCostDeptAreaPrint",
	 	   			bean_name: "costDeptAreaService",
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

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
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
					<th width="200">面积</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
