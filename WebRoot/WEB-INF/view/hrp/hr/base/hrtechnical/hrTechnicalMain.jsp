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
        var tree, grid;
        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: 'queryTechnicalLevelTree.do?isCheck=false'
                },
                callback: {
                    onClick: function () {
                        $("#technical_code").val('');
                        $("#technical_name").val('');
                        query('tree');
                    }
                }
            })
        };
        var query = function (queryFor) {
            if (queryFor === 'tree') {
                var selectedNode = tree.getSelectedNodes()[0];
                var technical_code = selectedNode ? selectedNode.id : '';

                params = [
                    {
                        name: 'technical_code',
                        value: technical_code
                    }
                ]
            } else {
                params = [
                    {
                        name: 'technical_code',
                        value: $("#technical_code").val()
                    },
                    {
                        name: 'technical_name',
                        value: $("#technical_name").val()
                    }
                ]
            }
            grid.loadData(params);
        };
        var add = function () {
            $.etDialog.open({
                url: 'addTechnicalLevelPage.do?isCheck=false',
                width: 450,
                height: 450,
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
                        url: 'deleteTechnicalLevel.do',
                        data: {
                            paramVo: JSON.stringify(param)
                        },
                        success: function () {
                            grid.deleteRows(data);
                            tree.reAsyncChildNodes(null, 'refresh');
                        }
                    })
                });
            }
        };
    	function importData(){
    		var para = {
    				"column" : [ {
    					"name" : "technical_code",
    					"display" : "技术等级编码",
    					"width" : "200",
    					"require" : true
    				},{
    					"name" : "technical_name",
    					"display" : "技术等级名称",
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
    		importSpreadView("/hrp/hr/base/importData.do?isCheck=false", para);
        }
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'updateTechnicalLevelPage.do?isCheck=false&technical_code=' + openParam.technical_code,
                title: '修改',
                width: 450,
                height: 450,
                btn: ['保存', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.save();
                }
            })
        };
        var initGrid = function () {
            var columns = [{
                    display: '技术等级编码',
                    name: 'technical_code',
                    width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                {
                    display: '技术等级名称',
                    name: 'technical_name',
                    width: 120
                },
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
                inWindowHeight: true,
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        technical_code: rowData.technical_code
                    };

                    openUpdate(openParam);
                },
                dataModel: {
                    url: 'queryTechnicalLevel.do?isCheck=false'
                },
                usePager: true,
                columns: columns,
                toolbar: {
                    items: [{
                            type: 'button',
                            label: '查询',
                            listeners: [{
                                click: query
                            }],
                            icon: 'search'
                        },
                        {
                            type: 'button',
                            label: '添加',
                            listeners: [{
                                click: add
                            }],
                            icon: 'add'
                        },
                        {
                            type: 'button',
                            label: '删除',
                            listeners: [{
                                click: remove
                            }],
                            icon: 'delete'
                        },
                        {
                            type: "button",
                            label: '导入',
                            icon: 'import',
                            listeners: [{
                                click: importData
                            }]
                        }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];

                var openParam = {
                    technical_code: currentRowData.technical_code
                };
                openUpdate(openParam);
            })
        };

        var initForm = function () {
     
        };

        $(function () {
            initTree();
            initForm();
            initGrid();

            // 给输入框绑定搜索树事件
            $(".text-input").on('keyup', function () {
                var $self = $(this)
                searchTree({
                    tree: tree,
                    value: $self.val(),
                    callback: function () {
                        $self.focus();
                    }
                })
            })
        })
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text">
            </div>
            <div id="mainTree"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">等级编码：</td>
                    <td class="ipt">
                        <input id="technical_code" type="text" />
                    </td>

                    <td class="label">等级名称：</td>
                    <td class="ipt">
                        <input id="technical_name" type="text" />
                    </td>

                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>