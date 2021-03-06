<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
/*     var grid,itemgrid;
    var gridManager = null;
    var itemGridManager = null;
    var ptm_rowid ; */
    $(function () {
    	loadDict();
    	loadItemHead();
    });

    // 加载grid
    function loadItemHead(){
		itemgrid = $("#itemgrid").ligerGrid({
			columns: [ 
				{ display: '模板项', name: "be_name", align: 'left',width:'60%'},
				{ display: '标题行号', name: "be_tittlerow", align: 'center',width:'20%',editor:{type:'float'}},
				{ display: '列号', name: "be_columnno", align: 'center',width:'20%',editor:{type:'float'}},
			],
			dataAction: 'server',dataType: 'server',usePager : true, checkbox: true,rownumbers:true,enabledEdit: true,
			width: '100%', height: '100%', isAddRow:false ,
			url:'queryPactTemplateItemSet.do?isCheck=false&flag=2',
			onSelectRow:setInfo,
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow:function (data, rowindex, rowobj) {
				deleteItemRow(data);
			}
		});

        itemGridManager = $("#itemgrid").ligerGetGridManager();
        
    }
  //双击配置项列表  配置项设置列表增加行
    function addItemRow(data){
    	itemgrid.addRow({"be_name":data.mould_config_name,
    		"be_code":data.mould_config_code,
    		"be_itemid":data.mould_config_id,
    		"group_id" : data.group_id ,
    		"hos_id" : data.hos_id ,
    		"copy_code": data.copy_code });
    	grid.deleteRow(data);
    	
    }
    //双击配置项设置列表  配置项设置列表删除行
    function deleteItemRow(data){
    	$.ligerDialog.confirm('确定删除编制项?', function(yes) {
    		if(yes){
    			itemgrid.deleteRow(data)
    	    	grid.addRow({
    	    		"group_id" : data.group_id ,
    	    		"hos_id" : data.hos_id ,
    	    		"copy_code": data.copy_code,
    	    		"mould_config_id" : data.be_itemid ,     
    				"mould_config_code":data.be_code , 
    				"mould_config_name":data.be_name 
    	    	})
    		}
    	})
    	
    }
    // 保存
    function save(){
    	var data = itemgrid.getData();
    	if(data.length == 0){
    		$.ligerDialog.warn('请配置编制项数据!');
    	}else{
   	          if (!validateGrid(data)) {
   	              return false;
   	          }
   	          //按钮数据 
	   	      var table = document.getElementById("newButton");
	   	 	  var buttonInfo = table.getElementsByTagName('input');
	   	 	  var buttonData = "";
	   	 	  $(buttonInfo).each(function (){
	   	 		 buttonData += this.value + ","
	   	 	  })
   	          /* var ptm_position ;
   	       	  var ptm_position_x = '' ;
   	       	  var ptm_position_y = '' ;
	       	  $('input:radio[name="ptm_position"]').each(function (){
	   			if(this.checked){
	   				ptm_position = this.value ;
	   			}
	               
	          });
	       	  if(ptm_position==7){
	       		  ptm_position_x = $("#ptm_position_x").val();
 				  ptm_position_y = $("#ptm_position_y").val();
	       	  } */
	       	  var data = {
	    				pt_rowid :pt_rowid ,
	    				ptm_rowid :ptm_rowid,
	    				et_rowid : (et_rowid?et_rowid:"-1"),
		   				/* ptm_position : ptm_position,
		   				ptm_position_x : ptm_position_x,
		   				ptm_position_y : ptm_position_y, */
		   				gt_istotalrow : ($("#gt_istotalrow").ligerCheckBox().getValue()?1:0),
		   				buttonData : buttonData.substring(0,buttonData.length-1),
		   				detail : JSON.stringify(data),
		   				ptm_showmode : 2 // 1-表格  2-表单
	    			};
	       	 ajaxJsonObjectByUrl('savePactTemplateSheetItemSet.do?isCheck=false',data ,
	       			function(responseData) {
	       		 		if (responseData.state == "true") {
	       		 			location.reload() ; //刷新页面
	       		 		}
	       		 	}
    		)
    	}
    }

       
    function validateGrid(data) { 
    	debugger;
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否为空
 		var targetMap = new HashMap();
 		
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.be_row) {
				rowm+="[行号]、";
			}
			if (!v.be_columnno) {
				rowm+="[列号]、";
			}
			if(rowm != ""){
				rowm = "编制项:"+v.be_name+"-" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
 		});
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
   
    function renderPage(){
    	debugger;
    	if("${gt_istotalrow}" == 1){
    		$("#gt_istotalrow").ligerCheckBox().setValue(true);
    	}else{
    		$("#gt_istotalrow").ligerCheckBox().setValue(false) ;
    	}
    	 val("${gt_istotalrow}") ;
    	 if(buttonStr){
    		 renderButton(buttonStr); // 渲染按钮
    	 }
    	 /* if(ptm_positionValue){
    		 $('input:radio[name="ptm_position"]').each(function () {
     			if(this.value == ptm_positionValue ){
     				$("#"+ptm_positionValue).ligerRadio().setValue(true);
     			}
                 
             });
     		if(ptm_positionValue==7){
     			$("#ptm_position_x").ligerTextBox({disabled: false});
     	    	$("#ptm_position_y").ligerTextBox({ disabled: false});
     	    	$("#ptm_position_x").val("${ptm_position_x}");
     	    	$("#ptm_position_y").val("${ptm_position_x}");
     		}else{
     			$("#ptm_position_x").ligerTextBox({ disabled: true});
     	    	$("#ptm_position_y").ligerTextBox({ disabled: true});
     	    	$("#ptm_position_x").val("");
     	    	$("#ptm_position_y").val("");
     		}
    	 } */
    	 grid.reload();
    }
    
    function renderButton(buttonStr){
    	var data = buttonStr.split(",");
    	$(data).each(function(){
    		$("#newButton").append("<input type=\"button\" id=\""+this.split("@")[0]+"\"value=\""+this.split("@")[1]+"\" ondblclick=\"deleteButton('"+this.split("@")[0]+"')\"  />");
	    	$('#'+this.split("@")[0]).ligerButton({ width: 80 });
    	})
    }
 // 选中 编制项数据  后  渲染 设置信息
    function setInfo(data, rowindex, rowobj){
	 
	  	
		if(data.be_query == 1){
			$("#be_query").ligerCheckBox().setValue(true);
		}else{
			$("#be_query").ligerCheckBox().setValue(false);
		}
		if(data.be_isestablish == 1){
			$("#be_isestablish").ligerCheckBox().setValue(true);
		}else{
			$("#be_isestablish").ligerCheckBox().setValue(false);
		}
		if(data.be_ishide == 1){
			$("#be_ishide").ligerCheckBox().setValue(true);
		}else{
			$("#be_ishide").ligerCheckBox().setValue(false);
		}
		$("#be_alias").val(data.be_alias);
		liger.get("be_establishattribute").setValue(data.be_establishattribute);
		liger.get("cs_rowid").getValue(data.cs_rowid);
		liger.get("be_enter").getValue(data.be_enter);
		liger.get("be_background").setValue(data.be_background);
		liger.get("be_fontcolor").setValue(data.be_fontcolor);
		$("#be_startcolumn").val(data.be_startcolumn);
		$("#be_occupycolumn").val(data.be_occupycolumn);
		liger.get("be_align").setValue(data.be_align)
    }
  //添加按钮
    function addButton(){
    	$.ligerDialog.prompt('按钮名称', '自定义', function (yes, value){
    		if (yes){
    			$("#newButton").append("<input type=\"button\" id=\""+value+"\"value=\""+value+"\" ondblclick=\"deleteButton('"+value+"')\"  />");
    	    	$('#'+value).ligerButton({ width: 80 });
    		}
    	})
    	
    }
    function deleteButton(id){
    	$.ligerDialog.confirm('确定删除按钮?', function(yes) {
    		$("#"+id).remove();
    	})
    }
    function saveSetInfo(){
    	var data = itemgrid.getCheckedRows();
    	if(data.length != 1){
    		$.ligerDialog.warn('请选择一行数据!');
    	}else{
    		$(data).each(function() {
    			this.be_query = $("#be_query").ligerCheckBox().getValue()?1:0;
    			this.be_isestablish = $("#be_isestablish").ligerCheckBox().getValue()?1:0;
  				this.be_ishide = $("#be_ishide").ligerCheckBox().getValue()?1:0;
			  	this.be_alias = $("#be_alias").val();
			  	this.be_establishattribute = liger.get("be_establishattribute").getValue();
			  	this.cs_rowid = liger.get("cs_rowid").getValue();
			  	this.be_enter = liger.get("be_enter").getValue();
			  	this.be_background = liger.get("be_background").getValue();
			  	this.be_fontcolor = liger.get("be_fontcolor").getValue();
			  	this.be_startcolumn = $("#be_startcolumn").val();
			  	this.be_occupycolumn = $("#be_occupycolumn").val();
			  	this.be_align = liger.get("be_align").getValue();
			  	this.be_function=liger.get("cs_rowid").getValue();
    		})
    	}
    }
    function loadDict(){
    	//布局
    	$("#layout1").ligerLayout({leftWidth: 200,allowLeftCollapse:true}); 
    	$("#layout2").ligerLayout({leftWidth: 400,allowLeftCollapse:false,topHeight:110});
    	autoCompleteByData("#be_align",[{id: 'left', text: 'left'}, {id:'center', text: 'center'},{id:  'right', text: 'right'}],
    			"id","text",true,true,'',false,'',100);
    	autoCompleteByData("#be_establishattribute",
    			[{id: 'selectdata', text: '下拉框'}, {id: 'select', text: '下拉数据集'},
    			 {id: 'text', text: '文本框'},{id: 'number', text: '数字框'},
    			 {id: 'date', text: '日期框'},{id: 'datetime', text: '日期时间框'},
    			 {id: 'password', text: '密码框'},{id: 'checkbox', text: '复选框'},
    			],
    			"id","text",true,true,'',false,'',100);
    	autoCompleteByData("#be_background",
    			[{id: 0, text: '无'}, {id: 'red', text: '赤'},
    			 {id: 'orange', text: '橙'},{id:'yellow', text: '黄'},
    			 {id: 'green', text: '绿'},{id: 'Indigo', text: '青'},
    			 {id: 'blue', text: '蓝'},{id: 'purple', text: '紫'},
    			 {id: 'black', text: '黑'}
    			],
    			"id","text",true,true,'',false,'',100);
    	autoCompleteByData("#be_fontcolor",
    			[{id: 0, text: '无'}, {id: 'red', text: '赤'},
    			 {id: 'orange', text: '橙'},{id:'yellow', text: '黄'},
    			 {id: 'green', text: '绿'},{id: 'green', text: '青'},
    			 {id: 'blue', text: '蓝'},{id: 'purple', text: '紫'},
    			 {id: 'black', text: '黑'}
    			],
    			"id","text",true,true,'',false,'',100)
    	
    	
    	$("#query").ligerTextBox({ width:'800'});
    	
    	$("#gt_istotalrow").ligerCheckBox();
    	$("#be_query").ligerCheckBox();
		$("#be_isestablish").ligerCheckBox();
		$("#be_ishide").ligerCheckBox();
    	
    	$("#be_alias").ligerTextBox({ width:'100'});
    
    	$("#be_establishattribute").ligerTextBox({ width:'100'});
       	$("#cs_rowid").ligerComboBox({
    		url: "queryCsCode.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: '200',
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: '100',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true,
    	});
    	$("#be_enter").ligerTextBox({ width:'100'});
    	$("#be_startcolumn").ligerTextBox({ width:'100'});
    	$("#be_occupycolumn").ligerTextBox({ width:'100'});
    	$("#be_background").ligerTextBox({ width:'100'});
    	$("#be_fontcolor").ligerTextBox({ width:'100'});
    	$("#be_align").ligerTextBox({ width:'100'});
    	
    	$(':button').ligerButton({ width: 80 });
    }
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div  id="layout2" position="center">
		<div id="buttonMod" position="top" >
			<table>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left:10px;">查询条件:</td>
					<td align="left"  colspan="18" ><input type="text" id="query" /></td>
				</tr>
			</table>
			<table>
				<tr>
			   		<td align="right" class="l-table-edit-td" style="padding-left:10px;">按钮设置:</td>
					<td align="left" class="l-table-edit-td" >
						<input type="button" value="添加" onclick="addButton();"  /></td>
					<td  id='newButton' align="left" class="l-table-edit-td" colspan="17"></td>
			    </tr> 
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">
							<input type="radio" value="1" name="gt_istotalrow" /></td>
					<td align="left" class="l-table-edit-td">是否合计行</td>
			  		<td align="left" colspan="14"></td>
				</tr>
						
			</table>
		</div>
		<div position="left">
				<div id="itemgrid"></div>
		</div>
		<div   position="center" >
			<div >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
							<input type="radio" value="1" id="be_query" /></td>
						<td align="left" class="l-table-edit-td">是否作为查询条件</td>
						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
							<input type="radio" value="1" id="be_isestablish" /></td>
						<td align="left" class="l-table-edit-td">是否可编辑</td>
						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
							<input type="radio" value="1" id="be_ishide" /></td>
						<td align="left" class="l-table-edit-td">是否隐藏</td>
					</tr>
					<tr>	
						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >展示名:</td>
					<td align="left" ><input type="text" id="be_alias" /></td>
					<td align="left" ></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;" >填报属性:</td>
						<td align="left" ><input type="text" id="be_establishattribute" /></td>
						<td align="left" ></td>
					</tr>
					<tr>	
						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >取值函数:</td>
					<td align="left" ><input type="text" id="cs_rowid" /></td>
					<td align="left" ></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;" >回车跳转:</td>
						<td align="left" ><input type="text" id="be_enter" /></td>
						<td align="left" ></td>
					</tr>
					<tr>	
						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >背景色:</td>
					<td align="left" ><input type="text" id="be_background" /></td>
					<td align="left" ></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;" >字体色:</td>
						<td align="left" ><input type="text" id="be_fontcolor" /></td>
						<td align="left" ></td>
					</tr>
					<tr>	
						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >开始列:</td>
					<td align="left" ><input type="text" id="be_startcolumn" /></td>
					<td align="left" ></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;" >占用列:</td>
						<td align="left" ><input type="text" id="be_occupycolumn" /></td>
						<td align="left" ></td>
					</tr>
					<tr>	
						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >对齐方式:</td>
						<td align="left" ><input type="text" id="be_align" /></td>
						<td align="left" ></td>
						<td align="left" ></td>
						<td align="left" ></td>
						<td align="left" ></td>
					</tr>
				</table>
		 		<br><br><br><br><br><br><br><br>
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				<input type="button" value="保存" onclick="save();"  />
			</div>	
		</div>
	</div>
</body>
</html>