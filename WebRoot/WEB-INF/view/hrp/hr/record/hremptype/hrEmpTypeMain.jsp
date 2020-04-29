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
        /* var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: 'queryHrEmpTypeTree.do?isCheck=false'
                },
                callback: {
                    onClick: function () {
                        $("#type_code").val('');
                        $("#type_name").val('');
                        query('tree');
                    }
                }
            })
        }; */
        var query = function (queryFor) {
            /* if (queryFor === 'tree') {
                var selectedNode = tree.getSelectedNodes()[0];
                var tree_code = selectedNode ? selectedNode.id : '';

                params = [
                    {
                        name: 'tree_code',
                        value: tree_code
                    }
                ]
            } else { */
                params = [
                    {
                        name: 'type_code',
                        value: $("#type_code").val()
                    },
                    {
                        name: 'type_name',
                        value: $("#type_name").val()
                    }
                ]
            //}
            grid.loadData(params);
        };
        var add = function () {
            $.etDialog.open({
                url: 'addHrEmpTypePage.do?isCheck=false',
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
                        url: 'deleteHrEmpType.do',
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
        function importDate(){
    		var para = {
    			"column" : [ {
    				"name" : "type_code",
    				"display" : "人员分类编码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "type_name",
    				"display" : "人员分类名称",
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
    		  importSpreadView("/hrp/hr/record/importDate.do?isCheck=false", para);  
    	}
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'updateHrEmpTypePage.do?isCheck=false&type_code=' + openParam.type_code,
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
                    display: '人员分类编码',
                    name: 'type_code',
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
                    display: '人员分类名称',
                    name: 'type_name',
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
                    display: '备注',
                    name: 'note',
                    width: 120
                }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                showBottom:false,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        type_code: rowData.type_code
                    };

                    openUpdate(openParam);
                },
                dataModel: {
                    url: 'queryHrEmpType.do'
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
                        },{ 
                        	type: 'button', 
                        	label: '导入', 
                        	listeners: [{ 
                        		click: importDate
                        		}], 
                        	icon: 'import' 
                        }/* ,{ 
                        	type: 'button', 
                        	label: '导出', 
                        	listeners: [{ 
                        		click: exportMainGrid
                        		}], 
                        	icon: 'export' 
                        } */
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];

                var openParam = {
                    type_code: currentRowData.type_code
                };
                openUpdate(openParam);
            })
        };

        var initForm = function () {
     
        };

        $(function () {
            //initTree();
            initForm();
            initGrid();

            // 给输入框绑定搜索树事件
            /* $(".text-input").on('keyup', function () {
                var $self = $(this)
                searchTree({
                    tree: tree,
                    value: $self.val(),
                    callback: function () {
                        $self.focus();
                    }
                })
            }) */
        })
         function exportMainGrid(){
            	exportGrid(grid);
            }

    </script>
</head>

<body>
    <div class="container">
        <!-- <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text">
            </div>
            <div id="mainTree"></div>
        </div> -->
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">分类编码：</td>
                    <td class="ipt">
                        <input id="type_code" type="text" />
                    </td>

                    <td class="label">分类名称：</td>
                    <td class="ipt">
                        <input id="type_name" type="text" />
                    </td>

                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>