<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title> 
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'recipe_type_code',value:$("#recipe_type_code").val()}); 
    	grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '医嘱分类编码', name: 'recipe_type_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                            return "<a href='#' onclick=\"openUpdate('"
		                             + rowdata.group_id + "|"
		                             + rowdata.hos_id + "|"
		                             + rowdata.copy_code + "|"
                                     + rowdata.recipe_type_code
                                     +"');\" >"+rowdata.recipe_type_code+"</a>";
			           }
					 },
                     { display: '医嘱分类名称', name: 'recipe_type_name', align: 'left'},
                     { display: '是否停用', name: 'is_stop', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                    		    return rowdata.is_stop == 0?"否":"是"
							}	
					 }
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:true,
                     url:'queryHtcgRecipeType.do',
                     width: '100%', 
                     height: '100%',
                     checkbox: true,
                     rownumbers:true,
                     delayLoad:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
			            { line:true },
	    	            { text: '导入', id:'import', click: imp,icon:'up' }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
         
    }

    function add_open(){
    	$.ligerDialog.open({
    		url: 'htcgRecipeTypeAddPage.do?isCheck=false',
    		height: 300,
    		width: 400,
    		title: '添加',
    		modal: true,
    		showToggle: false,
    		showMax: false,
    		showMin: true,
    		isResize: true,
    		buttons: [{
    			text: '确定',
    			onclick: function(item, dialog) {
    				dialog.frame.saveRecipeType();
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
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	var ParamVo =[];
            $(data).each(function (){
            	ParamVo.push(
                    	    this.group_id + "@"
						   +this.hos_id + "@"
						   +this.copy_code + "@"
                    	   +this.recipe_type_code
                    	);//实际代码中temp替换主键
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteHtcgRecipeType.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
        return;
    }
    
    function openUpdate(obj){ 
    	var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1]
		+ "&" + "copy_code=" + vo[2] + "&" +  "recipe_type_code=" + vo[3]
        
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({
    		url: 'htcgRecipeTypeUpdatePage.do?isCheck=false&' + parm,
    		data: {},
    		height: 300,
    		width: 400,
    		title: '修改',
    		modal: true,
    		showToggle: false,
    		showMax: false,
    		showMin: false,
    		isResize: true,
    		buttons: [{
    			text: '确定',
    			onclick: function(item, dialog) {
    				dialog.frame.saveRecipeType();
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

    function imp(){
    	var para = {
				"column": [{
					"name": "recipe_type_code",
					"display": "医嘱分类编码",
					"width": "300",
					"require": true
				}, {
					"name": "recipe_type_name",
					"display": "医嘱分类名称",
					"width": "300",
					"require": true
				} ]
			};
			importSpreadView("hrp/htcg/recipetype/importHtcgRecipeType.do?isCheck=false",para);
    }

    
    function loadDict(){

    	   $("#recipe_type_code").ligerTextBox({width:160});
    	   autocomplete("#is_stop", "../../base/queryHtcgYearOrNo.do?isCheck=false", "id", "text", true, true);
         }   

	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医嘱时效分类：</td>
            <td align="left" class="l-table-edit-td"><input name="recipe_type_code" type="text" id="recipe_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"/></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
