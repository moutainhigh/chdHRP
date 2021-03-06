<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,grid,select,dialog,datepicker,validate,pageOffice,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'rec_code', value: $("#rec_code").val() },
                { name: 'pay_way', value: $("#pay_way").val() },
                { name: 'cheq_no', value: $("#cheq_no").val() },
                { name: 'note', value: $("#note").val() },
                { name: 'state', value: $("#state").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
          	sup_no = $("#sup_no").etSelect({url: '../../../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
            state = $("#state").etSelect({url: '../../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=STATE',defaultValue: "none"});
            pay_way = $("#pay_way").etSelect({url: '../../../../basicset/select/queryPayTypeDictSelect.do?isCheck=false',defaultValue: "none"});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '收款编号', name: 'rec_code',width: '150px'},
            	 { display: '收款日期', name: 'date', align: 'center', width: '90px'},
                 { display: '供应商', name: 'sup_name',width: '200px'},
            	 { display: '合同编号', name: 'pact_code',width: '150px'},
                 { display: '合同名称', name: 'pact_name',width: '200px'},
                 { display: '收款金额', name: 'money', align: 'right', width: '100px'},
                 { display: '摘要', name: 'note', width: '150px'},
                 { display: '收款方式', name: 'pay_way', width: '90px'},
                 { display: '支票号码', name: 'cheq_no', width: '150px'},
                 { display: '制单人', name: 'maker_name', align: 'center', width: '80px'},
                 { display: '审核人', name: 'checker_name', align: 'center', width: '80px'},
                 { display: '确认人', name: 'confirmer_name', align: 'center', width: '80px'},
                 { display: '状态', name: 'state', align: 'center', width: '30px'}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                	url: '../init/queryPactDepRecFKHT.do?isCheck=false&is_init=0'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: del }], icon: 'del' },
                        { type: 'button', label: '审核',   listeners: [{ click: check }],  icon: 'audit' },
                        { type: 'button', label: '消审',   listeners: [{ click: uncheck }],  icon: 'back' },
                        { type: 'button', label: '确认',   listeners: [{ click: myConfirm }],  icon: 'audit' },
                        { type: 'button', label: '取消确认',  listeners: [{ click: unconfirm }],  icon: 'back' },
                        { type: 'button', label: '打印',  listeners: [{ click: print }],  icon: 'print' },
                        { type: 'button', label: '模板打印',  listeners: [{ click: printModel }],  icon: 'print' },
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        var printModel = function(){
 			useId='${user_id }';
 			officeFormTemplate({template_code:"11002",use_id:useId});
        };
           
        
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("请先查询数据！");
       			return;
       		}
           	var printPara={
                   	title: "保证金收款",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.fkht.guarantee.performance.PactDepRecFKHTService",
   					method_name:"queryPactDepRecFKHTPrint",
   					bean_name:"pactDepRecFKHTService",
   				 	pact_type_code : $("#pact_type_code").val(),
                 	sup_no : $("#sup_no").val(),
                 	pact_code : $("#pact_code").val(),
                 	pact_name : $("#pact_name").val(),
                 	rec_code : $("#rec_code").val(),
                 	pay_way : $("#pay_way").val(),
                 	cheq_no : $("#cheq_no").val(),
                 	note : $("#note").val(),
                 	state : $("#state").val(),
                 	start_date : startpicker.getValue(),
                 	end_date : endpicker.getValue(),
                 	is_init: 0
               };
               officeGridPrint(printPara);
           };
           
        
        var check = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
	                 var rowdata = this.rowData;
                	 if(rowdata.state == '新建'){
	                     param.push(rowdata.rec_code);
                	 }
                 });
                if(param.length == 0){$.etDialog.error('当前状态不可审核'); return;}
                ajaxPostData({
             	   	url: '../init/checkPactDepRecFKHT.do?isCheck=false',
                    data: {mapVo: JSON.stringify(param),state: 'check'},
                    success:function(){query();}
                })
             }
        }
        var uncheck = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.state == '审核'){
	                     param.push(rowdata.rec_code);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消审核'); return;}
               ajaxPostData({
            	   	url: '../init/checkPactDepRecFKHT.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'uncheck'},
                   success:function(){query();}
               })
            }
       }
        var myConfirm = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.state == '审核'){
	                     param.push(rowdata.rec_code);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可确认'); return;}
               ajaxPostData({
            	   	url: '../init/checkPactDepRecFKHT.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'confirm'},
                   success:function(){query();}
               })
            }
       }
        
        var unconfirm = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.state == '确认'){
	                     param.push(rowdata.rec_code);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消确认'); return;}
               ajaxPostData({
            	   	url: '../init/checkPactDepRecFKHT.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'unconfirm'},
                   success:function(){query();}
               })
            }
       }
        var del = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
	                if(rowdata.state == '新建'){
		                rowdata.group_id = ${group_id};
	                    rowdata.hos_id = ${hos_id};
	                    rowdata.copy_code = '${copy_code}';
		               	param.push(rowdata);
	                }
                });
                if(param.length == 0){$.etDialog.error('暂无数据可删除'); return;}
               $.etDialog.confirm('确定删除?', function () {
                   ajaxPostData({
                	   url: '../init/deletePactDepRecFKHT.do?isCheck=false',
                       data: { mapVo: JSON.stringify(param) },
                       success: function () {grid.deleteRows(data);}
                   })
               });
            }
       }
        //跳转修改页面
         var edit = function (data) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/fkht/guarantee/performance/init/toPactDepRecInitFKHTEditPage.do?isCheck=false&rec_code='+data.rec_code + '&pact_code='+data.pact_code,
                 width: '680px',
                 height: '400px',
                 title: '编辑',
                 modal: true,
             });
        };
        var add = function (data) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/fkht/guarantee/performance/init/toPactDepRecInitFKHTAddPage.do?isCheck=false&is_init=0',
                 width: '680px',
                 height: '400px',
                 title: '添加',
                 modal: true,
                 frameName: window.name
             });
        };

        
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: "yyyy-fm-fd",
    		  	onChange: function (date) {
    		  		var end = endpicker.getValue();
    		  		if(end < date){
    		  			endpicker.setValue(end);
    		  		}
    		  	}
    		});
    		endpicker = $("#end_date").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = startpicker.getValue();
    		  		if(start > date){
    		  			endpicker.setValue(start);
    		  		}
    		  	}
    		});
		}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">收款日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同类别：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">收款编号：</td>
            <td class="ipt"><input id="rec_code" type="text" /> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">收款方式：</td>
            <td class="ipt"><select id="pay_way" style="width: 180px"></select> </td>
        	<td class="label" style="width: 100px;">支票号码：</td>
            <td class="ipt"><input id="cheq_no" type="text" /></td>
        	<td class="label" style="width: 100px;">摘要：</td>
            <td class="ipt"><input id="note" type="text" /> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">状态：</td>
            <td class="ipt"><select id="state" style="width: 180px"></select></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

