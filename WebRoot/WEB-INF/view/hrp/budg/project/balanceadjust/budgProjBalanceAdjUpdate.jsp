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
<style>
    .form {
        padding: 40px;
        box-sizing: border-box;
        margin-top: 20px;
        margin-bottom: 50px;
    }
    .button-group {
        margin: 10px 0;
        padding-left: 10px;
        text-align: center;
    }
    .button-group > button {
        padding: 2px 15px;
        min-width: 90px;
        height: 30px;
        box-sizing: border-box;
        margin-right: 40px;
        cursor: pointer;
        font-weight: bold;
        background: #e0edff;
        border: 1px solid #a3c0e8;
    }
    .button-group > button:hover {
        background: #ffbe76;
    }
</style>
<script>
var grid;
var gridManager = null;
var userUpdateStr;
var proj_id;
var source_id;
var sum = 0.00;

$(function() {
    loadDict();
    loadHead();
    $("body").keydown(function() {
        if (event.keyCode == "9") {//keyCode=9是Tab
              grid.addRowEdited({
            	  /* proj_name: '' ,
            	  source_name: '' , */
            	  remain_amount:'0',
            	  usable_amount:'0',
            	  type_name: '' ,
            	  level_name: '' ,
            	  con_emp_name : '' ,
            	  set_up_date : '',
            	  complete_date : '',
            	  pay_end_date : '',
            	  sespend_date : '',
            	  proj_state_name : ''
          	  });
        }
    });
    
    
    
 });
        
function loadHead () {
    grid = $("#maingrid").ligerGrid({
    	columns: [ 
					 
                  { display: '项目名称', name: 'proj_name', align: 'left',
                    	  valueField:"id",textField : 'text',
				 			editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../../queryProjName.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
								onChanged: getProjId
							} 
					 		},
                  { display: '资金来源', name: 'source_id', align: 'left',
					 			valueField:"source_id",textField : "source_name",
					 			editor : {
									type : 'select',
									valueField : 'source_id',
									textField : 'source_name',
									url : 'queryBudgSourceByProj.do?isCheck=false&proj_id='+proj_id,
									keySupport : true,
									autocomplete : true,
									onChanged: loadBalance
								} 
					 		},
                  { display: '预算余额', name: 'remain_amount', align: 'right',
					 			render:function(rowdata,rowindex,value){
				 					return formatNumber(value,2,1);
			 				}
					 		},
                  { display: '可用余额', name: 'usable_amount', align: 'right',
					 			render:function(rowdata,rowindex,value){
				 					return formatNumber(value,2,1);
			 				}
					 		},
                  { display: '经费余额调整', name: 'remain_adj', align: 'right',
					 		render:function(rowdata,rowindex,value){
					 			return formatNumber(rowdata.remain_adj,2,1);
			 				}
					 	},
                  { display: '项目类型', name: 'type_name', align: 'left'
					 		},
                  { display: '项目级别', name: 'level_name', align: 'left'
					 		},
                  { display: '项目负责人', name: 'con_emp_name', align: 'left'
					 		},
                  { display: '立项日期', name: 'set_up_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'}
					 		},
                  { display: '结项日期', name: 'complete_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'}
					 		},
                  { display: '报销截止日期', name: 'pay_end_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'}
					 		},
                  { display: '中止日期', name: 'sespend_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'}
					 		},
                  { display: '项目状态', name: 'state_name', align: 'left'
					 		}
                  ],
                  dataAction: 'server',dataType: 'server',usePager:true,isAddRow:false,
                  url:'querBudgProjBalanceDetail.do?isCheck=false&adj_code='+'${adj_code}',
                  width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit :true,
                  selectRowButtonOnly:true,//heightDiff: -10,
                  toolbar: { items: [
                  	{ text: '保存（<u>S</u>）', id:'add', click: save, icon:'add' },
	                { line:true },
	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
	                { line:true },
	                { text: '关闭（<u>R</u>）', id:'close', click: close,icon:'close' },
	                { line:true }
 				]},
    });
    
    function getProjId(value){
    	
    	proj_id = value.record.proj_name.split(",")[0];
    	loadHead();
    	value.record.source_name = "";
    	value.record.source_id = "";
    	grid.updateRow(value.record,value.record);
    }
 	 //当选定项目和资金来源后   调用方法查询调整资金
    function loadBalance (value) {
 		if(!value.record.proj_name){
 			$.ligerDialog.error("该行项目名称不能为空!")
 			
 		}
    	var theRecord = value.record
    	source_id = theRecord.source_id;
        //项目ID和资金来源ID必须都被选中后  触发方法
        if (proj_id && source_id) {
            var data = {
                proj_id: proj_id,
                source_id: source_id
            }
            var url = "queryProjMessage.do?isCheck=false";
            
           	$.ajax({
               	type:"POST",
               	url: url,
               	data: data,
               	dataType: "json",
               	success: function (result) {
               		if (result) {
               			theRecord.remain_amount = result.remain_amount== null ? "" :result.remain_amount;
						theRecord.usable_amount = result.usable_amount== null ? "" :result.usable_amount;
						theRecord.type_name = result.type_name== null ? "" :result.type_name;
						theRecord.level_name = result.level_name == null ? "" :result.level_name;
						theRecord.con_emp_name = result.emp_name == null ? "" :result.emp_name;
						theRecord.set_up_date = result.set_up_date == null ? "" :result.set_up_date.split(" ")[0];
						theRecord.complete_date = result.complete_date == null ? "" :result.complete_date.split(" ")[0];
						theRecord.pay_end_date = result.pay_end_date ==null ? "" :result.pay_end_date.split(" ")[0];
						theRecord.sespend_date = result.sespend_date == null ? "" :result.sespend_date.split(" ")[0];
						theRecord.state_name = result.state_name== null ? "" :result.state_name;
						//重新渲染
                   		grid.reRender();
               		}
               		sum += Number(result.usable_amount);
               		$("#remain_adj_sum").val(sum);
               	},
               	error: function () {
               		$.ligerDialog.error("数据未维护,请检查维护后操作!")
               	}
           })
        }
        
    }
  	
    gridManager = $("#maingrid").ligerGetGridManager();
}

		//获取调整金额总计
		function getRemainAdjSum(){
			var data = grid.getData();
		    var num = 0;
		    for (var i = 0; i < data.length; i++) {
		        var remainAdj = data[i].remain_adj;
		        if (remainAdj) {
		            num += Number(remainAdj)
		        }
		    }
		    $("#remain_adj_sum").val(num)
		}
		
		
		//保存
		function  save () {
			if('${state}' != '01'){
	    		$.ligerDialog.error('该数据不是新建状态，无法执行保存操作！');
	    		return false;
	    	}
			var data = grid.getData();
			console.log(data)
			if(data.length>0){
				if(!validateGrid(data)){
					return false;
				}
				var formPara={
						adj_code : $("#adj_code").val(),
						remark : $("#remark").val() == null ? "" : $("#remark").val(),
						remain_adj_sum : $("#remain_adj_sum").val(),
		                DetailData : JSON.stringify(data)
		           	};
		        ajaxJsonObjectByUrl("updateBudgProjRemainAdj.do?isCheck=false",formPara,function(responseData){		            
		            if(responseData.state=="true"){
		            	parentFrameUse().query();
		            }
		        });
			}else{
				$.ligerDialog.error('没有需要保存的数据!');
			}
		   
		}
		
			//校验数据
		function validateGrid(data) {  
		 	var msg="";
				var rowm = "";
				//判断grid 中的数据是否重复或者为空
				var targetMap = new HashMap();
				$.each(data,function(i, v){
					rowm = "";
					if (v.proj_name == "" || v.proj_name == null || v.proj_name == 'undefined') {
						rowm+="[项目名称]、";
					}
					if (v.source_id == "" || v.source_id == null || v.source_id == 'undefined') {
						rowm+="[资金来源]、";
					}
					if(rowm != ""){
						rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
					}
					msg += rowm;
					var key=v.source_id + v.payment_item_id ;
					var value="第"+(i+1)+"行";
					if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
						targetMap.put(key ,value);
					}else{
						msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
					}
				});
				if(msg != ""){
					$.ligerDialog.warn(msg);  
					return false;  
				}else{
					return true;  
				} 	
		}
			
		 //删除行
		function remove(){
			var data = gridManager.getCheckedRows();
		    if (data.length == 0){
		    	$.ligerDialog.error('请选择行');
		    }else{
		    	gridManager.deleteRange(data);
		    }
		}
			
			//关闭
		function close(){
			frameElement.dialog.close();
		}	
		
		function loadDict(){
		    //字典下拉框
			 $("#adj_code").ligerTextBox({width:300,disabled:true});  
		     $("#remark").ligerTextBox({width:300});  
		     $("#remain_adj_sum").ligerTextBox({width:300,disabled:true})   
		}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>

    <table class="form" cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="left" class="l-table-edit-td" >
               	 调整单号：
            </td>
            <td align="left" class="l-table-edit-td" >
                <input name="adj_code" id="adj_code" type="text" value="${adj_code }" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}"/>
            </td>
            <td align="left" class="l-table-edit-td"></td>
            <td align="right" class="l-table-edit-td">摘要：</td>
            <td align="left" class="l-table-edit-td">
                <input name="remark" type="text" id=remark ltype="text" value="${remark}" validate="{required:true,maxlength:20}"/>
            </td>
            <td align="left" class="l-table-edit-td"></td>
            <td align="right" class="l-table-edit-td">调整金额合计：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="remain_adj_sum" style = "width:200px" type="text" id="remain_adj_sum" disabled="disabled" value="${remain_adj_sum}" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" />
            </td>
        </tr>
    </table>
	<div>
    	<table>
	    	<tr>	
				<td align="center" class="l-table-edit-td" width="20%">
					制单人：${maker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="20%" >
					制单日期：${make_date}
				</td>
				<td align="center" class="l-table-edit-td" width="20%">
					审核人：${checker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="20%" >
					审核日期：${check_date}
				</td>
				<td align="center" class="l-table-edit-td" width="20%" >
					状态：${state_name}
				</td>
			</tr>
		</table>
    </div>
   <div id="maingrid"></div>
</body>
</html>
