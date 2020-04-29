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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function (){
    	loadDict();
    	loadHead(null);	
    	
    	$("#insurance_code").change(function(){
			query();
		})
		
		$("#insurance_nature").change(function(){
			query();
		})
		
		$("#is_stop").change(function(){
			query();
		})
    });
    
    //查询
    function  query(){
    	grid.options.parms = [];
    	grid.options.newPage = 1;
    	
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'insurance_code',value:$("#insurance_code").val()});
        grid.options.parms.push({name:'insurance_nature',value:liger.get("insurance_nature").getValue()});
        grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue() == null ? "" : liger.get("is_stop").getValue()});
        
    	//加载查询条件
    	grid.loadData(grid.where);
    }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '医保类型编码', name: 'insurance_code', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                 			return "<a href=javascript:openUpdate('"+rowdata.group_id+","+ rowdata.hos_id+","+rowdata.copy_code+","+rowdata.insurance_code+"')>"+rowdata.insurance_code+"</a>";
                    	 }
                     },
                     { display: '医保类型名称', name: 'insurance_name', align: 'left'},
                     { display: '医保类型性质', name: 'insurance_nature_name', align: 'left'},
                     { display: '是否停用', name: 'stop', align: 'left'}					 		
                     ],
           dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgYBType.do?isCheck=true',
           width: '100%', height: '100%', checkbox: true,rownumbers:false,enabledEdit : true,
           selectRowButtonOnly:true,//heightDiff: -10,
           toolbar: { items: [
                    { text: '查询', id:'query', click: query,icon:'search' },
                    { line:true },
				    { text: '添加', id:'add', click: add, icon:'add' },
				    { line:true },
				    { text: '删除', id:'delete', click: del,icon:'delete' },
					{ line:true }, 
					{ text: '下载导入模板', id:'downTemplate', click : downTemplate,icon:'down' },
					{ line:true },
					{ text: '导入', id:'import', click: imp,icon:'up' }
			]}
        });
    	
        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
	//添加
	function add(){
		$.ligerDialog.open({
			url: 'budgYBTypeAddPage.do?isCheck=false', 
			height:250,width: 450, title:'添加',
			modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true
		});	
	}
	
	//修改
	function openUpdate(obj){
		var vo = obj.split(",");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&copy_code="+ vo[2]+"&insurance_code="+ vo[3];
		
    	$.ligerDialog.open({ 
    		url : 'budgYBTypeUpdatePage.do?isCheck=false&' + parm,data:{}, 
    		height: 250,width: 450, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    	});

    }
	
	//删除
	function del(){
		 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         	return;
         }else{
             var ParamVo =[];
          	 $(data).each(function (){					
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.insurance_code 
					) });
             $.ligerDialog.confirm('确定删除吗?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteBudgYBType.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             });
		}
	}
    //导入
    function imp(){
    	$.ligerDialog.open({
    		url: 'budgYBTypeImportPage.do?isCheck=false', 
    		height: 500,width: 800, title:'导入',
    		modal:true, showToggle:false, showMax:false, showMin: false, isResize:true 
    	});
    }
    
    
    //下载模板
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }
    
    
    function loadDict(){
       //字典下拉框 
        autocomplete("#insurance_nature","../../../queryBudgYBTypeNature.do?isCheck=false","id","text",true,true);
       
    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
    	
    	$("#insurance_code").ligerTextBox({width:160});
    	$("#insurance_nature").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
    }  
    
 	//打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		/* grid.options.lodop.fn=renderFunc; */
 		grid.options.lodop.title="医保类型维护";
    }
	 
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医保类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="insurance_code" type="text" id="insurance_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医保类型性质：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="insurance_nature" type="text" id="insurance_nature" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{maxlength:20}"/>
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
        