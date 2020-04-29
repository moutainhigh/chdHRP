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
    
    var userUpdateStr;
    
    var clicked = 0;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
        toobar();
        
        loadHotkeys();

    });
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		grid.options.parms.push({name:'acc_year',value:'${acc_year}'}); 
		grid.loadData(grid.where);
   }
    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '区间', name: 'section', align: 'left',
                    	 render: function (rowdata) {
                    		 
                    		 if(rowdata.section !='' &&  rowdata.section !=undefined){
                    			
                    			 return '第'+parseInt(rowdata.section)+'区间';
                    		 }else{
                    			 rowdata.section = parseInt(rowdata.__index+1);
                    			 return '第'+parseInt(rowdata.__index+1)+'区间';
                    			 
                    		 }
                    		 
                         } 
                    },
					 { display: '指标值', columns:[
								{ display: '起始', name: 'kpi_beg_value', align: 'left',type : 'int',editor: { type: 'float' ,
									precision : 6},
		                    	 render: function (rowdata) {
		                    		 if(rowdata.kpi_beg_value == null || rowdata.kpi_beg_value == ""){
		                    			 rowdata.kpi_beg_value = 0;
		                    			 return "0";
		                    		 }else{
		                    			 return rowdata.kpi_beg_value;
		                    		 }
		                    	 }
								},
								{ display: '终止', name: 'kpi_end_value', align: 'left',type : 'int',editor: {  type: 'float' ,
									precision : 6 },
			                    	 render: function (rowdata) {
			                    		 if(rowdata.kpi_end_value == null || rowdata.kpi_end_value == ""){
			                    			 rowdata.kpi_end_value = 0;
			                    			 return "0";
			                    		 }else{
			                    			 return rowdata.kpi_end_value;
			                    		 }
			                    	 }}
					 		]
					 },
					 { display: '指标得分', columns:[
								{ display: '起始', name: 'kpi_beg_score', align: 'left',type : 'int',editor: {  type: 'float' ,
									precision : 6 },
			                    	 render: function (rowdata) {
			                    		 if(rowdata.kpi_beg_score == null || rowdata.kpi_beg_score == ""){
			                    			rowdata.kpi_beg_score = 0;
			                    			 return "0";
			                    		 }else{
			                    			 return rowdata.kpi_beg_score;
			                    		 }
			                    	 }},
								{ display: '终止', name: 'kpi_end_score', align: 'left',type : 'int',editor: {  type: 'float' ,
									precision : 6 },
				                    	 render: function (rowdata) {
				                    		 if(rowdata.kpi_end_score == null || rowdata.kpi_end_score == ""){
				                    			 rowdata.kpi_end_score = 0;
				                    			 return "0";
				                    		 }else{
				                    			 return rowdata.kpi_end_score;
				                    		 }
				                    	 }}
							]
					}
                     ],
                     usePager:false,enabledEdit : true,url:'queryPrmDeptSectionByEditerGrid.do?group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&acc_year=${acc_year}&acc_month=${acc_month}&goal_code=${goal_code}&kpi_code=${kpi_code}&dept_id=${dept_id}&dept_no=${dept_no}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

  
  //键盘事件
	function loadHotkeys(){
		
		hotkeys('C',addGridRow);
 
	}
    
	function addGridRow(){ 
		
		grid.addRow();
		
    }
    
	function is_addRow(){
		setTimeout(function () { 
			//当数据为空时 默认新增一行
			var data = grid.getData();
			if (data.length==0)
				grid.addRow();
			}, 500);
		
	}
	
	function saveSchemeSection(){
		var dataSection = gridManager.rows;
		var index = 0;
		var msg = "";
		if(dataSection.length > 0){
			$.each(dataSection, function(d_index, d_content){ 
				if(d_content.section != 1){
					if((d_content.kpi_end_value == "undefined" || d_content.kpi_end_value == "")
			      			&&
			      			(d_content.kpi_beg_value == "undefined" || d_content.kpi_beg_value == "")
			      			&&
			      			(d_content.kpi_beg_score == "undefined" || d_content.kpi_beg_score == "")
			      			&&
				      		(d_content.kpi_end_score == "undefined" || d_content.kpi_end_score == "")
			      		  ){
			      			grid.deleteRow(d_content);//删除选择的行
			         		return true; 
			      		  }
			      		index++;
				}
			})
			
			for(var i = 0 ; i < index; i ++){
			    if (i < index -1) {
			    	if(dataSection[i].kpi_end_value != dataSection[i+1].kpi_beg_value){
						msg = '期间必须连续！';
					}
			    } 
			}
		}
		if(msg != ""){
			$.ligerDialog.error(msg);
			return;
		}
		
		
		var formPara = {
				
				dataSection : JSON.stringify(dataSection),
				acc_year : $("#acc_year").val(),
				acc_month : $("#acc_month").val(),
				goal_code :"${goal_code}",
				dept_no : liger.get("dept_no").getValue().split(".")[1],
				dept_id : liger.get("dept_no").getValue().split(".")[0],
				kpi_code : "${kpi_code}",
		};
		
		ajaxJsonObjectByUrl("saveDeptSchemeSection.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				query();
				parent.query();
			}
		});

	}
	
	
	function DeleteGridRow(){
		
		
	     var data = gridManager.getCheckedRows();
	     var dataSection = gridManager.rows;

	     if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){	
            	 if(isnull(this.group_id)){
					gridManager.deleteSelectedRow();
				}else{				
														ParamVo.push(
														this.group_id   +"@"+ 
														this.hos_id   +"@"+ 
														this.copy_code   +"@"+ 
														this.acc_year   +"@"+ 
														this.acc_month   +"@"+ 
														this.goal_code   +"@"+ 
														this.kpi_code   +"@"+ 
														this.dept_no +"@"+ 
														this.dept_id +"@"+ 
														this.section
														); 
				}		
				});
             
             if(ParamVo == ""){
            	 return;
             }
             
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deletePrmDeptKpiSection.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
	}
	
    function loadDict(){
        //字典下拉框

    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false);
        
		liger.get("hos_id").setValue("${hos_id}");
        
        liger.get("hos_id").setText("${hos_code} ${hos_name}");
    	
    	autocomplete("#dept_no","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false);
    	
		liger.get("dept_no").setValue("${dept_id}.${dept_no}");
        
        liger.get("dept_no").setText("${dept_code} ${dept_name}");
        
		$("#hos_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#dept_no").ligerTextBox({width : 160,disabled: true});
    	
    	$("#acc_year").ligerTextBox({width : 70,disabled: true});
    	
    	$("#acc_month").ligerTextBox({width : 70,disabled: true});
    	
    	$("#kpi_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#nature_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#goal_value").ligerTextBox({width : 70,disabled: true});
    	
    	$("#ratio").ligerTextBox({width : 70,disabled: true});
	}  

    function toobar(){
    	$("#toptoolmod").ligerToolBar({ items: [
				{ text: '添加（<u>C</u>）', id:'add', click: addGridRow, icon:'add' },
                { line:true },
                { text: '删除', id:'delete', click: DeleteGridRow,icon:'delete' },
				{ line:true }, 
    	]});
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;"  onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" disabled="disabled"  ltype="text"  value="${acc_year}"  validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" disabled="disabled"  value="${acc_month}"   ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text"  value="${kpi_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">指标性质：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text"  value="${nature_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">目标值：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_value" type="text" id="goal_value" value="${goal_value}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">满分：</td>
            <td align="left" class="l-table-edit-td"><input name="ratio" type="text" id="ratio"  value="${full_score}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="toptoolmod"></div>
	<div id="maingrid"></div>

</body>
</html>
