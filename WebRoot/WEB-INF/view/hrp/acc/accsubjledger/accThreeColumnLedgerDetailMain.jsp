<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>	
<link rel="stylesheet" href="<%=path%>/lib/et_components/etCheck/css/icheck.css">
<script src="<%=path%>/lib/et_components/etCheck/js/icheck.js"></script>
<script src="<%=path%>/lib/et_components/etCheck/etCheck.js"></script>

<script type="text/javascript">
	
    var grid,acc_year_month1,acc_year_month2;
    var gridManager = null;
    var userUpdateStr;
    var subj_box_data = "";
    var is_show = 0;
    $(function ()
    {
		loadDict(); 
    	loadHead(null);	//加载数据
    	
		if('${accPara043}'==1){
			
			 grid.set("groupColumnName", "subj_name");
			
			 grid.set("groupColumnDisplay", "科目");
			 
		}else {
			
			 grid.set("groupColumnName", "");
			
			 grid.set("groupColumnDisplay", "");
			 
		}
    	
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		var year_month1= acc_year_month1.getValue();
		var year_month2 = acc_year_month2.getValue();
		var subj_code = liger.get("subj_code").getValue();
		subj_code=subj_code.split(".");
		subj_code = subj_code[1];
		var sch_id = liger.get("sch_id").getValue();
		
		if(year_month1=="" || year_month2==""){
			$.ligerDialog.error('会计期间为必填项，不能为空！');
			return;
		}
	    
		grid.options.parms.push({name:'acc_year_b',value: year_month1.split(".")[0]}); 
        grid.options.parms.push({name:'acc_month_b',value: year_month1.split(".")[1]}); 
        grid.options.parms.push({name:'acc_year_e',value: year_month2.split(".")[0]}); 
        grid.options.parms.push({name:'acc_month_e',value: year_month2.split(".")[1]}); 
       	grid.options.parms.push({name:'sch_id',value: sch_id}); 
       	grid.options.parms.push({name:'subj_code',value: subj_code}); 
       	grid.options.parms.push({name:'isLastChk',value:$("#isLastChk").prop("checked") ? 1 : 0}); 
    	
    	grid.options.parms.push({ name : 'vouch_no_begin', value : $("#vouch_no_b").val() });
		grid.options.parms.push({ name : 'vouch_no_end', value : $("#vouch_no_e").val() });
		grid.options.parms.push({ name : 'summary', value : $("#summary").val() });

        //加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '期间',  align: 'center',columns:[{
                    	 display: '年', name: 'acc_year', align: 'left',width:'4%',isSort:false
                     },{
                    	 display: '月', name: 'acc_month', align: 'left',width:'2%',isSort:false
                     },{
                    	 display: '日', name: 'acc_day', align: 'left',width:'2%',isSort:false
                     }]
					 },
					 
					 { display: '科目', name: 'subj_name', align: 'left',width:'20%',isSort:false
					 },
					 { display: '凭证号', name: 'vouch_no', align: 'left',width:'10%',isSort:false,
						 render : function(rowdata, rowindex, value) {
							 if(rowdata.vouch_id !=null){
								 return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"</div></a>"; 
							 }
							 
          				}
					 },
					 { display: '摘要', name: 'summary', align: 'left',width:'20%',isSort:false
					 },
					 {display: '借方', name: 'debit', align: 'right',width:'10%',formatter:'###,##0.00',isSort:false,
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.debit == 0){return "";}
                    		 else{ return formatNumber(rowdata.debit, 2, 1);}
          				}
					 },
					 {display: '贷方', name: 'credit', align: 'right',width:'10%',formatter:'###,##0.00',isSort:false,
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.credit == 0){return "";}
                    		 else{ return formatNumber(rowdata.credit, 2, 1);}
          				}
					 },
					 { display: '方向', name: 'subj_dire', align: 'left',width:'6%',isSort:false
					 },
                     { display: '余额', name: 'end_os',  align: 'right',width:'10%',formatter:'###,##0.00',isSort:false,
						 render : function(rowdata, rowindex, value) {
							 
							 if(rowdata.end_os==0) 
							 {return "Q";}
							 else{return formatNumber(rowdata.end_os, 2, 1);}
	         					
	         				}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'collectAccThreeColumnLedgerDetail.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     pageSizeOptions:[100, 200, 500],pageSize: 100,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     groupColumnName: "subj_name",groupColumnDisplay: '分组',
                     toolbar: { items: [
							{ text: '查询',id:'search',icon:'search', click: query },
							{ line:true } , 
							{ text: '打印', id:'print', click: print_btn,icon:'print' },
							{ line:true },
							{ text: '打印维护', id:'settings', click: myPrintSet,icon:'settings' },
							{ line:true },
							/* { text: '全年账簿打印', id:'print', click: printDate,icon:'print' },
							{ line:true } */
    				]} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    function print_btn(){
    	var year_month1 = acc_year_month1.getValue();
    	var year_month2 = acc_year_month2.getValue();
      	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}

       	var selPara={};
       	$.each(grid.options.parms,function(i,obj){
       		selPara[obj.name]=obj.value;
       	});
       	
       	selPara["template_code"]="01003";
       	selPara["class_name"]="com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService";
       	selPara["method_name"]="collectThreeColumnLedgerDetailPrint";
       	selPara["bean_name"]="accSubjLedgerService";
       	if( acc_year_month1.getValue()!=""){
       		selPara["p_acc_year"]= year_month1.split(".")[0];	
       	}
       	selPara["p_year_month_b"]= year_month1;
       	selPara["p_year_month_e"]= year_month2;
       	selPara["p_subj_code"]=liger.get("subj_code").getText();
       	selPara["isPreview"]=true;
       	/* selPara["p_cur_code"]=liger.get("cur_code").getText();
       	var isAccStr="不含未记账";
       	if($("#is_state").is(":checked")){
       		isAccStr="包含未记账";
    	}
       	selPara["p_is_acc"]=isAccStr; */
       	
       	officeTablePrint(selPara);
       	
        /*var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
      		//console.log(grid)
   		var printPara={
   			headCount:2,
   			title:'三栏明细账',
   			head:[
  				
  				{"cell":0,"value":
					"期间: " + $("#subj_code").val(),
				"colspan":colspan_num,"br":true}
      		],
   			type:3,
   			columns:grid.getColumns(1),
   			autoFile:true
   		};
   		
   		ajaxJsonObjectByUrl("collectAccThreeColumnLedgerDetail.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
      	}); */
 
      	
      }
   //全年账簿打印
	function printDate(){
		var year_month1= acc_year_month1.getValue();
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    
		var heads={
		  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  		  "rows": [
			          {"cell":0,"value":"会计期间："+year_month1[0]+"至"+year_month1[1],"colSpan":"10","align":"center"} ,
			          {"cell":0,"value":"科目："+liger.get("subj_code").getText(),"br":"true","colSpan":"5"} ,
			          {"cell":8,"value":"币种："+liger.get("cur_code").getText(),"colSpan":"2"} 
			          
		  		  ] 
		  	}; 
			 
		 		var printPara={
		 			title: liger.get("subj_code").getText().split(" ")[1]+"明细账" ,//标题
		 			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
		 			class_name: "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService",
					method_name: "collectThreeColumnLedgerDetailPrintDate",
					bean_name: "accSubjLedgerService",
					heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空

					/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		 		};
		 		//执行方法的查询条件
		 		$.each(grid.options.parms,function(i,obj){
		 			printPara[obj.name]=obj.value;
		  	});
		 		
		  	officeGridPrint(printPara);

	}
    
    function loadDict(){
            //字典下拉框
      	$("#vouch_no_b").ligerTextBox({width:50}  );
		
		$("#vouch_no_e").ligerTextBox({width:50 } ); 
		
		
		$("#summary").ligerTextBox({width:180 });
		
		var isLastChk=Local.get("isLastChk");
		
		if(isLastChk==null || isLastChk=="true"){
			
			isLastChk=true;
			
		}else{
			
			isLastChk=false;
			
		}
		
		$("#isLastChk").etCheck({
			
		checked :isLastChk,
		
		ifChanged: function (status, checked, disabled) {
			
			Local.set("isLastChk",checked);
			
        }
		
	});
		
    	autocomplete("#sch_id", "../queryAccBookSch.do?isCheck=false", "id", "text", true, true, {page: 'SLMXZ'}, false, false, "", "", subjWidth);
		
		var count = 0;
    	$("#subj_code").ligerComboBox({
    		url: "../querySubjBylevel.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: '400',
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: '207',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true, 
    		parms: {pageSize: 100},
    		/* onSuccess: function (data) {
    			if (count == 0) {this.setValue(data[0].id);}
    			count++;
    		} */
			onTextBoxKeySpace: function(value){
				showSubjTree({
					ligerId: "subj_code", 
					idStr: "subj_id", 
					splitStr: ".",
					acc_year: acc_year_month1.getValue().split(".")[0], 
					windowName: window.name
				});
			}
    	});
    	 
    	//会计期间
		acc_year_month1 = $("#acc_year_month1").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});
		acc_year_month2 = $("#acc_year_month2").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});
		
    }
    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	
	 //打印维护
	 function myPrintSet(){
		 officeFormTemplate({template_code:"01003"});
	 }
	 
	 //方案设置
	 function subjIntercalate(){
		 parent.$.ligerDialog.open({
				title : '方案设置',
				width : $(window).width()-100,
				height : $(window).height(),
				url : 'hrp/acc/accbooksch/accBookSchMainPage.do?isCheck=false&page=SLMXZ',
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				buttons : [ {
					text : '保存',
					onclick : function(item, dialog) {
						dialog.frame.saveSch(0);
					},
					cls : 'l-dialog-btn-highlight'
				},{
					text : '查询',
					onclick : function(item, dialog) {
						dialog.frame.saveSch(1);
					},
					cls : 'l-dialog-btn-highlight'
				},{
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			});
	 };
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">

<input type="hidden" id="subj_flag" name="subj_flag"/> 
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
	        <td colspan="2">
	        <table>
	        	<tr>
		            <td align="left" class="l-table-edit-td">
						<input class="Wdate" name="acc_year_month1" type="text" 
							id="acc_year_month1" ltype="text" style="width: 90px;" />
					</td>
					<td align="left" class="l-table-edit-td">至</td>
					<td align="left" class="l-table-edit-td">
						<input class="Wdate" name="acc_year_month2" type="text" 
							id="acc_year_month2" ltype="text" style="width: 90px;" />
					</td>
					<td align="left"></td>
				</tr>
            </table>
            </td>
            <td align="right" class="l-table-edit-td">方&nbsp;&nbsp;&nbsp;&nbsp;案：</td> 
			<td align="left" class="l-table-edit-td" >
				<table>
					<tr>
						<td>
							<input name="sch_id" type="text" id="sch_id" ltype="text" /></td>
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="l-button l-button-test"  type="button" value="设置" onclick="subjIntercalate();"/>
						</td>
					</tr>
				</table>
			</td>
		</tr> 
		<tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
            <td align="left" class="l-table-edit-td" style="width: 100px;"><input name="subj_code" type="text" id="subj_code" ltype="text" /></td>
            <td align="left" class="l-table-edit-td"><input style="vertical-align: middle" name="isLastChk" type="checkbox" id="isLastChk" ltype="text" />&nbsp;含非末级</td>
	     	<td align="right" class="l-table-edit-td" >凭证号：</td>  
             <td align="left" class="l-table-edit-td">
            	<table>
					<tr>
						<td>
						<input name="vouch_no_b" type="text" id="vouch_no_b"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input name="vouch_no_e" type="text" id="vouch_no_e"/>
						</td>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要 ：</td> 
            			<td align="left" class="l-table-edit-td" ><input name="summary" type="text" id="summary" ltype="text"   /></td>
            		</tr>
				</table>
            </td> 
		</tr>
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
