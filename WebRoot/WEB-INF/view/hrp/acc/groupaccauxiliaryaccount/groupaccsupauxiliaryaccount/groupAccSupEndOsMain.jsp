<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script>
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr; 
    
	var query_obj_code;
	var obj_param="";
    var obj_box_data = "";
    var is_end_os = 0;
    var is_state = 99;

	var query_subj_code;
    
    var subj_box_data = "";
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	//注册事件check选中显示栏目 否则隐藏栏目
    	$("#check_column1").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('01', true);grid.toggleCol('01D', true); grid.toggleCol('01C', true);
				
			}else{
				
				grid.toggleCol('01', false);grid.toggleCol('01D', false); grid.toggleCol('01C', false);
				
			}
		});
    	
		$("#check_column2").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('02', true);grid.toggleCol('02D', true); grid.toggleCol('02C', true);
				
			}else{
				
				grid.toggleCol('02', false);grid.toggleCol('02D', false); grid.toggleCol('02C', false);
				
			}
		});
		
		$("#check_column3").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('03', true);grid.toggleCol('03D', true); grid.toggleCol('03C', true);
				
			}else{
				
				grid.toggleCol('03', false);grid.toggleCol('03D', false); grid.toggleCol('03C', false);
				
			}
		});
		
		$("#check_column4").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('04', true);grid.toggleCol('04D', true); grid.toggleCol('04C', true);
				
			}else{
				
				grid.toggleCol('04', false);grid.toggleCol('04D', false); grid.toggleCol('04C', false);
				
			}
		});
		
		$("#check_column5").change(function () { 
    		
			if(this.checked){
				
				grid.toggleCol('05', true);grid.toggleCol('05D', true); grid.toggleCol('05C', true);
				
			}else{
				
				grid.toggleCol('05', false);grid.toggleCol('05D', false); grid.toggleCol('05C', false);
				
			}
		}); 
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	if(liger.get("acc_year_month1").getValue() == '' || liger.get("acc_year_month1").getValue() == undefined){
        	$.ligerDialog.error('起始年月为必填项');
        	return ;
        }
        	
        if(liger.get("acc_year_month2").getValue() == '' || liger.get("acc_year_month2").getValue() == undefined){
        	$.ligerDialog.error('截止年月为必填项');
        	return ;
        } 
        if(liger.get("rela_code").getValue() == '' || liger.get("rela_code").getValue() == undefined){
        	$.ligerDialog.error('账套为必填项');
        	return ;
        } 
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acc_year_b',value:liger.get("acc_year_month1").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_b',value:liger.get("acc_year_month1").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'acc_year_e',value:liger.get("acc_year_month2").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_e',value:liger.get("acc_year_month2").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'cur_code',value:liger.get("cur_code").getValue()}); 
    	grid.options.parms.push({name:'rela_code',value:liger.get("rela_code").getValue()}); 
    	
		if($("#is_state").get(0).checked == true){
    		is_state = 1;
    	}
    	grid.options.parms.push({name:'is_state',value:is_state});
    	
    	if($("#is_end_os").get(0).checked == true){
    		is_end_os = 1;
    	}
    	grid.options.parms.push({name:'is_end_os',value:is_end_os}); 
    	
        var subj_code = liger.get("subj_code").getValue();
    	if(subj_code.split(",").length>1){
    		if($("#subj_flag").val() == "true"){
    			grid.options.parms.push({name:'subj_code',value:subj_code}); 
            	grid.options.parms.push({name:'subj_select_flag',value:"3"});
    		}else{
    			grid.options.parms.push({name:'subj_code',value:subj_code}); 
            	grid.options.parms.push({name:'subj_select_flag',value:"4"});
   			}
        }else{
        	if(subj_code==""){
        		grid.options.parms.push({name:'subj_code',value:""}); 
           	 	grid.options.parms.push({name:'subj_select_flag',value:"1"});
       		}else{
        		var code = subj_code.split(".").length > 1 ? subj_code.split(".")[1] : subj_code;
           		grid.options.parms.push({name:'subj_code',value:code});          	
        	 	grid.options.parms.push({name:'subj_select_flag',value:"2"});
       		}
        }
    	 
    	if($("#sup_code").val() == ""){
		 	grid.options.parms.push({name:'obj_code',value:""}); 
    	 	grid.options.parms.push({name:'obj_select_flag',value:"1"});
		}else{
	 		grid.options.parms.push({name:'obj_code',value:$("#sup_code").val()}); 
	 		grid.options.parms.push({name:'obj_select_flag',value:"2"});
		}

       
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){

        grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '供应商编码', isSort:false, name:'obj_code', align: 'left'},
                      
 					 { display: '供应商名称', isSort:false, name: 'obj_name', align: 'left'
 						 /* ,render : function(rowdata, rowindex, value) {
 								return formatSpace(rowdata.obj_name,rowdata.subj_level - 1);
 							} */
 					 },
 					 
 					 { display: '年初余额', align: 'center', id:'01',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'nc_debit', align: 'right',id:'01D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.nc_debit, 2, 1);}}},
                      		{display: '贷方', isSort:false, name: 'nc_credit', align: 'right',id:'01C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.nc_credit, 2, 1);}}}
 						 ]
 					 },
 					 
 					 { display: '期初余额', align: 'center', id:'02',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'qc_debit', align: 'right',id:'02D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.qc_debit, 2, 1);}}},
                      		{display: '贷方', isSort:false, name: 'qc_credit', align: 'right',id:'02C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.qc_credit, 2, 1);}}}
 						 ]
 					 },
 					 
 					 { display: '本期发生', align: 'center', id:'03',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'bq_debit', align: 'right',id:'03D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.bq_debit, 2, 1);}}},
                     	 	{display: '贷方', isSort:false, name: 'bq_credit', align: 'right',id:'03C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.bq_credit, 2, 1);}}}
 						 ]
 					 },
 					 
 					 { display: '累计发生', align: 'center', id:'04',
 						 columns:[
 							{display: '借方', isSort:false, name: 'lj_debit', align: 'right',id:'04D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.lj_debit, 2, 1);}}},
 							{display: '贷方', isSort:false, name: 'lj_credit', align: 'right',id:'04C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.lj_credit, 2, 1);}}}
 						]
 					 },
 					 
 					 { display: '期末余额', align: 'center', id:'05',
 						 columns:[
 						 	{display: '借方', isSort:false, name: 'end_debit', align: 'right',id:'05D',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.end_debit, 2, 1);}}},
 						 	{display: '贷方', isSort:false, name: 'end_credit', align: 'right',id:'05C',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {if(typeof(value) == 'undefined'){return "";}else{ return formatNumber(rowdata.end_credit, 2, 1);}}}
 						]
 					 }
 				],
 				dataAction: 'server',dataType: 'server',usePager:false,url:'queryGroupAccSupEndOs.do',
 				width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,selectRowButtonOnly:true,heightDiff: 25,
 				toolbar: { items: [
 									{ text: '查询',id:'search', click: queryBtn ,icon:'search'},
 									{ line:true } , 
 									{ text: '打印', id:'print', click: print,icon:'print' },
 									{ line:true } ,
 									{ text: '模板打印', id:'print', click: printDate,icon:'print' },
 									{ line:true } ,
 									{ text: '模板设置', id:'settings', click: myPrintSet,icon:'settings' },
 									{ line:true }
 		    				]} 
 			});

         gridManager = $("#maingrid").ligerGetGridManager();
    }
    
 function queryBtn(){//查询
    	
    	query();
    	
    }
    
	//普通打印数据
	function print(){//打印
    	
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}

		var heads={
		  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  		  "rows": [
			          {"cell":0,"value":"会计期间："+$("#acc_year_month1").val()+"至"+$("#acc_year_month2").val(),"colSpan":"5"} 
		  		  ]
		  	}; 
			 
		 		var printPara={
		 			title: "供应商余额表",//标题
		 			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
		 			class_name: "com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccSupAuxiliaryAccountService",
					method_name: "collectGroupAccSupEndOsPrint",
					bean_name: "groupAccSupAuxiliaryAccountService",
					heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空

					/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		 		};
		 		//执行方法的查询条件
		 		$.each(grid.options.parms,function(i,obj){
		 			printPara[obj.name]=obj.value;
		  	});
		 		
		  	officeGridPrint(printPara);
    	
    }
	
	//模板打印数据
	function printDate(){//打印
    	
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}

	  	var selPara={};
		$.each(grid.options.parms,function(i,obj){
			selPara[obj.name]=obj.value;
		});
		
		selPara["template_code"]="01027";
	   	selPara["class_name"]="com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccSupAuxiliaryAccountService";
	   	selPara["method_name"]="collectGroupAccSupEndOsPrintDate";
	   	selPara["bean_name"]="groupAccSupAuxiliaryAccountService";
	   	if(liger.get("acc_year_month1").getValue()!=""){
	   		selPara["p_acc_year"]=liger.get("acc_year_month1").getValue().split(".")[0];	
	   	}
	   	selPara["p_year_month_b"]=liger.get("acc_year_month1").getValue();
	   	selPara["p_year_month_e"]=liger.get("acc_year_month2").getValue();
	   	selPara["obj_code"]=liger.get("sup_code").getValue();
	   	selPara["p_subj_code"]=liger.get("subj_code").getText();
	   	selPara["p_cur_code"]=liger.get("cur_code").getText();
	   	var isAccStr="不含未记账";
	   	if($("#is_state").is(":checked")){
	   		isAccStr="包含未记账";
		}
	   	selPara["p_is_acc"]=isAccStr;
	   	
		selPara["source_id"]="0";
       	
       	selPara["table_flag"]="6";
	   	
	   	officeTablePrint(selPara);

    }
	
	//打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01027"});
	 }

    function loadDict(){
    	 
		autocomplete("#acc_year_month1","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,90);
    	
    	autocomplete("#acc_year_month2","../../queryYearMonth.do?isCheck=false","id","text",true,true,'',false,false,90);
    	 
    	 var check_column1_manager = $("#check_column1").ligerCheckBox();
  		
  		var check_column2_manager = $("#check_column2").ligerCheckBox();
  		
  		var check_column3_manager = $("#check_column3").ligerCheckBox();
  		
  		var check_column4_manager = $("#check_column4").ligerCheckBox();
  		
  		var check_column5_manager = $("#check_column5").ligerCheckBox();
  		
  		check_column1_manager.setValue(true);
  		
  		check_column2_manager.setValue(true);
  		
  		check_column3_manager.setValue(true);
  		
  		check_column4_manager.setValue(true);
  		
  		check_column5_manager.setValue(true);
    	 
    	 var year_Month = '${yearMonth}';

    	 liger.get("acc_year_month1").setValue(year_Month);
		 
    	 liger.get("acc_year_month1").setText(year_Month);
    	 
    	 liger.get("acc_year_month2").setValue(year_Month);
		 
    	 liger.get("acc_year_month2").setText(year_Month);
         
    	 autocompleteObj({
   			id: '#rela_code',                   
   			urlStr:	"../../../sys/queryHosCopyDict.do?isCheck=false",							
   			valueField: 'id',            
   			textField:   'text' ,            
   			autocomplete:true,			
   			highLight:true,
   			initWidth: '330', 
   			defaultSelect:true,
   			keySupport: true,
   			selectEvent:function(value){
   				//console.log(arguments)
   			var	para = {
            			rela_code : value,
            			sign : "6"
            		}; 
   		 		autocomplete("#subj_code","../../querySubjByAccountRela.do?isCheck=false","id","text",true,true,para,true,false,'250');
   		 		//autocomplete("#emp_code","../../../sys/queryEmpByRela.do?isCheck=false","id","text",true,true,para,true,false,'180');
   		    }
   		}); 

		 $("#sup_code").ligerTextBox({ width : 200 });
    	  
    	 $("#emp_code").ligerComboBox({
		      	url: '../../../sys/queryEmpDict.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 180,
		      	autocomplete: true,
		      	width: 180,
    	      	keySupport:true
			 });
    	 
    	 $("#cur_code").ligerComboBox({
		      	url: '../../queryCur.do?isCheck=false',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 200,
		      	autocomplete: true,
		      	width: 200,

	           	onSuccess:function(data){
	    				if(data.length >0 ){
	    					if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
	               				liger.get("cur_code").setValue(data[0].id);
	               				liger.get("cur_code").setText(data[0].text);
	    					}
	    				}
	           	}

			 });
    	 
         }   
     
function supSelector(){
    	
    	$.ligerDialog.open({ url : '../../groupbookselector/groupAccBookSupSelectorPage.do?isCheck=false&flag='+$("#flag").val()+'&rela_code='+liger.get("rela_code").getValue(),
    			data:{listBoxData : obj_box_data}, height: 470,width: 480, title:'账簿供应商选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ 
 				           { text: '确定', onclick: 
 				        	   function (item, dialog) { 
	 				        	   var boxData = dialog.frame.getListBox();
	 				        	   var param = "";
	 				        	   
	 				        	   //alert($("#flag").val());
	 				        	   if($("#flag").val() == "true"){
	 				        		  obj_box_data = "";
	 				        		  $.each(boxData,function(i,v){
	 				        			  if(boxData.length == (i+1)){
	 				        				 obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
	 				        				 param = param + v.text;
	 				        				obj_param=obj_param+v.id.toString().split(".")[1];
	 				        				
	 				        			  }else{
	 				        				 obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
	 				        				 param = param + v.text + ",";
	 				        				obj_param=obj_param+v.id.toString().split(".")[1]+ ",";
	 				        				
	 				        			  }
	 				        		  });
	 				        	   }else{
	 				        		  obj_box_data = "";
									  $.each(boxData,function(i,v){
										  if(boxData.length == (i+1)){
											obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
		 				        			param = param + v.text;
		 				        			obj_param=obj_param+v.id.toString().split(".")[1];
		 				        		  }else{
		 				        			obj_box_data = obj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
		 				        			param = param + v.text + ",";
		 				        			obj_param=obj_param+v.id.toString().split(".")[1]+ ",";
		 				        		  }
	 				        		  });
	 				        	   }
	 				        	  query_obj_code = param;
								  liger.get("sup_code").setValue(obj_param);
   				        	      liger.get("sup_code").setText(param);
	 				        	   /* $("#dept_code").val(param); */
	 				        	   dialog.close();
 				        	   
 				        	   },cls:'l-dialog-btn-highlight' 
 				        	   }, 
 				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
 				         ] });
    }
function subjSelector(){
	
	$.ligerDialog.open({ url : '../../groupbookselector/groupAccBookSubjSelectorPage.do?isCheck=false&flag='+$("#subj_flag").val()+'&sign=6'+'&rela_code='+liger.get("rela_code").getValue(),
			data:{listBoxData:subj_box_data}, height: $(window).height()-40,width: 600, title:'账簿科目选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
				           { text: '确定', onclick: 
				        	   function (item, dialog) { 
				        	   var boxData = dialog.frame.getListBox();
				        	   var param = "";
				        	   var subj_param="";
				        	   var subj_box_data = "";
				        	   
				        	   if($("#subj_flag").val() == "true"){
				        			
				        			 $.each(boxData,function(i,v){
									  
									 	 if(boxData.length == (i+1)){

									 	  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
										  
										  param = param + v.text;
										   
										  subj_param=subj_param+v.id.split(".")[1].toString();
					        		  
									  }else{ 
										  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
					        			
										  param = param + v.text+ ",";
					        			 
										  subj_param=subj_param+v.id.split(".")[1].toString()+ ",";
					        		  
									  }
				        		  
				        		  });
				        			 
		 		 			}else {
			        			$.each(boxData,function(i,v){
									  
									  if(boxData.length == (i+1)){ 
										  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
										  
										  param = param + v.text;
										   
										  subj_param=subj_param+v.id.split(".")[1].toString();
					        		  
									  }else{ 
										  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
						        			
										  param = param + v.text+ ",";
					        			  
										  subj_param=subj_param+v.id.split(".")[1].toString()+ ",";
									  }
				        		  
				        		  });
		      		 	}
 				        	   query_subj_code = param;
 				        	  liger.get("subj_code").setValue(subj_param);
 				              
				        	   liger.get("subj_code").setText(param);
 				        	   dialog.close(); 
				        	   
				        	   },cls:'l-dialog-btn-highlight' 
				        	   }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				         ] });
}
 
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<input type="hidden" id="flag" name="flag"/>
<input type="hidden" id="query_code" name="query_code"/>
<input type="hidden" id="source_id" name="source_id"/>
<input type="hidden" id="subj_flag" name="subj_flag"/>
<input type="hidden" id="subj_query_code" name="subj_query_code"/>
	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
	
        <tr>
            <td align="right"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td"><input id="acc_year_month1" name="acc_year_month1" /></td>
            <td align="center" >-</td>
            <td align="left" class="l-table-edit-td"  ><input id="acc_year_month2" name="acc_year_month2" /></td>
            <td align="right" class="l-table-edit-td" ><font size="2" color="red">*</font>账套：</td>
            <td align="left" class="l-table-edit-td" colspan="2" ><input name="rela_code" type="text" id="rela_code" ltype="text" /></td>
            <!-- <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">显示栏目：</td>
            <td align="left" class="l-table-edit-td">
				<input name="check_column1" type="checkbox" id="check_column1" ltype="text"  />年初余额
            	<input name="check_column2" type="checkbox" id="check_column2" ltype="text"  />期初余额
            	<input name="check_column3" type="checkbox" id="check_column3" ltype="text"  />本期发生
            	<input name="check_column4" type="checkbox" id="check_column4" ltype="text"  />累计发生
            	<input name="check_column5" type="checkbox" id="check_column5" ltype="text"  />期末余额
            </td>
       </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  >币种：</td>
            <td align="left" class="l-table-edit-td"  colspan="3"><input id="cur_code" name="cur_code"  /></td>
             <td align="right" class="l-table-edit-td"   >科目：</td>
            <td align="left" class="l-table-edit-td" ><input id="subj_code" name="subj_code" /></td>
            <td>
            	<input class="l-button l-button-test" style="float: left;" type="button" value="选择" onclick="subjSelector();"/>
            </td>
            <td align="right" class="l-table-edit-td" colspan="2" >
	            <input name="is_state" type="checkbox" id="is_state" checked="checked" /> 包含未记账 
	            <input name="is_end_os" type="checkbox" id="is_end_os"  /> 显示有余额的供应商
            </td>
        </tr>
	 	<tr>
	 		<td align="right" class="l-table-edit-td"   >供应商：</td>
            <td align="left" class="l-table-edit-td" colspan="3" ><input id="sup_code" name="sup_code" /></td>
	 	</tr>
    </table>
	</form>
	<center><h2>供应商余额表</h2></center>
	<div id="maingrid"></div>
</body>
</html>
