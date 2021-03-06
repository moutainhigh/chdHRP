<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var query = function () {
            params = [
                { name: 'type_name', value: $("#type_name").val() },
                { name: 'mark', value: $("#mark").val() },
                { name: 'pact_nature', value: $("#pact_nature").val() },
                { name: 'dept_id', value: $("#dept_id").val() },
                { name: 'is_stop', value: $("#is_stop").val() }
            ];
            grid.loadData(params);
        };
        
        var initGrid = function () {
            var columns = [
            	 { display: '类型编码', name: 'type_code',width: '10%'},
                 { display: '类型名称', name: 'type_name',width: '10%'},
                 { display: '类型代号', name: 'mark', width: '10%'},
                 { display: '合同性质', name: 'nature_name', width: '10%'},
                 { display: '主管科室', name: 'dept_name', width: '10%'},
                 { display: '启用日期', name: 'start_date', width: '10%'},
                 { display: '自动凭证方式', name: 'vouch_name', width: '10%'},
                 { display: '备注', name: 'note', width: '10%'},
                 { display: '是否停用', name: 'is_stop', align: 'center',width: '10%',
                	 render: function (obj) {
                 		var is_stop = obj.rowData.is_stop;
                 		if(is_stop == 0){
                 			return '否';
                 		}else{
                 			return '是';
                 		}
                     }	 
                 }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactTypeSKHT.do'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    update(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' }
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        var initSelect=  function(){
        	var deptSelect = $("#dept_id").etSelect({url: "../../select/queryDeptSelect.do?isCheck=false" ,defaultValue: "none"});
    		var deptSelect = $("#pact_nature").etSelect({url: "../../select/queryTypeSKHTNatureSelect.do?isCheck=false" ,defaultValue: "none"});
    		$("#is_stop").etSelect({
    			  options: [
    			    { id: 0, text: '否' },
    			    { id: 1, text: '是' }
    			  ],
    			  defaultValue: "none"
    		})
        }
      
        //跳转保存页面
        var save = function (data) {
        	 $.etDialog.open({
                 url: 'toPactTypeSKHTAddMain.do?isCheck=false',
                 width: 620,
                 height: 380,
                 title: '添加',
                 btn: ['保存', '取消'],
                 modal: true,
                 btn1: function (index, el) {
                     var iframeWindow = window[el.find('iframe').get(0).name];

                     iframeWindow.save()
                 }
             });
        	
        };
        //跳转保存页面
        var update = function (data) {
        	 $.etDialog.open({
                 url: 'toPactTypeSKHTEditMain.do?isCheck=false&type_code=' + data.type_code,
                 width: 620,
                 height: 380,
                 title: '编辑',
                 btn: ['保存', '取消'],
                 modal: true,
                 btn1: function (index, el) {
                     var iframeWindow = window[el.find('iframe').get(0).name];

                     iframeWindow.save()
                 }
             });
        	
        };
        //删除
        var remove = function () {
        	 var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     rowdata.group_id = ${group_id};
                     rowdata.hos_id = ${hos_id};
                     rowdata.copy_code = '${copy_code}';
                     param.push(rowdata);
                 });
                 $.etDialog.confirm('确定删除?', function () {
                     ajaxPostData({
                         url: 'deletePactTypeSKHT.do',
                         data: {
                             paramVo: JSON.stringify(param)
                         },
                         success: function () {
                             grid.deleteRows(data);
                         }
                     })
                 });
             }
        };        
        $(function () {
            initGrid();
            initSelect();
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">合同类型：</td>
            <td class="ipt">
                <input id="type_name" type="text" />
            </td>
            <td class="label" style="width: 100px;">合同代号：</td>
            <td class="ipt">
                <input id="mark" type="text" />
            </td>
            <td class="label" style="width: 100px;">主管科室：</td>
            <td class="ipt">
                <select id="dept_id" style="width: 180px"></select>
            </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">合同性质：</td>
            <td class="ipt">
                <select id="pact_nature" style="width: 180px"></select>
            </td>
        	<td class="label" style="width: 100px;">是否停用：</td>
            <td class="ipt">
                <select id="is_stop" style="width: 180px"></select>
            </td> 
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

