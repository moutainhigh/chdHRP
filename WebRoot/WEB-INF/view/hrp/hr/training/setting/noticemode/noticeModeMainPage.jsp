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
                { name: 'way_code', value: $("#way_code").val() },
                { name: 'way_name', value: $("#way_name").val() }
            ];
            grid.loadData(params);
        };
        var add = function () {
            $.etDialog.open({
                url: 'addNoticeModePage.do?isCheck=false',
                width: 450,
                height: 300,
                title: '添加',
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save()
                }
            });
        };
        var remove = function () {
    		var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
            	var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    param.push(rowdata);
                });

                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: 'deleteNoticeMode.do',
                        data: { paramVo: JSON.stringify(param) },
                        success: function () {
                            query();
                        }
                        })
                });
            }
        };
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'updateNoticeModePage.do?isCheck=false&way_code=' + openParam.way_code,
                title: '修改',
                width: 450,
                height: 300,
                btn: [ '保存', '取消' ],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save();
                }
            })
        };
        var initGrid = function () {
            var columns = [
                { display: '通知方式编码', name: 'way_code', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                { display: '通知方式名称', name: 'way_name', width: 120 },

                {
                    display: '是否停用',
                    name: 'is_stop_name',
                    width: 120,
                    render: function (ui) {
                        var cellData = ui.cellData;
                        if (cellData === "是") {
                            return  '<span style="color:red;"> 是</span>';
                        } else {
                            return '否';
                        }
                    }    
                },
                {
                    display: "备注",
                    width: 120,
                    name: "note"
                }  

            ];
            var paramObj = {
                height: '100%',
                checkbox: true,

                dataModel: {
                    url: 'queryNoticeMode.do?isCheck=false'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        way_code: rowData.way_code
                    };

                    openUpdate(openParam);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'remove' },
                        { type: 'button', label: '导入', listeners: [{ click: importDate }], icon: 'import' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];

                var openParam = {
                    way_code: currentRowData.way_code
                };
                openUpdate(openParam);
            })
        };

        $(function () {
            initGrid();
        })
        //导入数据
        function importDate(){
    		//$("form[name=fileForm]").submit();
    		var para = {
    			"column" : [ {
    				"name" : "way_code",
    				"display" : "通知方式编码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "way_name",
    				"display" : "通知方式名称",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "is_stop",
    				"display" : "是否停用",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			} ]

    		};
    		importSpreadView("/hrp/hr/training/setting/noticemode/importDateHTL.do?isCheck=false", para);
    	}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">通知方式编码：</td>
            <td class="ipt">
                <input id="way_code" type="text" />
            </td>

            <td class="label" style="width: 100px;">通知方式名称：</td>
            <td class="ipt">
                <input id="way_name" type="text" />
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
