<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,grid,select,dialog" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var query = function () {
            params = [
                { name: 'state_name', value: $("#state_name").val() },
                { name: 'is_stop', value: $("#is_stop").val() },
            ];
            grid.loadData(params);
        };
        
        var initGrid = function () {
            var columns = [
            	 { display: '状态编码', name: 'state_code',width: '15%'},
                 { display: '状态名称', name: 'state_name',width: '19%'},
                 { display: '备注', name: 'note', width: '15%'},
                 { display: '是否停用', name: 'is_stop', align: 'center',width: '15%',
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
                    url: 'queryPactState.do'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
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

        //跳转修改页面
        var openUpdate = function (rowData) {
        	if(rowData.is_init==1){
        		$.etDialog.warn('内置合同状态不可修改！');
           	 	return;

        	}else{
            $.etDialog.open({
            	 url: 'pactStateEdit.do?isCheck=false&state_code=' + rowData.state_code,
                 width: 320,
                 height: 280,
                 title: '修改',
                 btn: ['保存', '取消'],
                 modal: true,
                 btn1: function (index, el) {
                	 if (rowData.is_init == 1) {
                		 $.etDialog.error("此数据不可修改");
						return;
					}
                	 
                	 $.ajax({
                         type: 'POST',
                         url: 'updatePactState.do',
                         data : {
              				state_code : rowData.state_code,
              				check : 'false',
              			},
                         dataType: 'json',
                         success: function (res) {
                        	 var result = res.msg;
                        	 if ("数据存在." == result) {
                        		 $.etDialog.confirm("该数据已使用，请确认是否修改?",function(index){
                        			 var iframeWindow = window[el.find('iframe').get(0).name];
				                  	 iframeWindow.update();
				                  	 $.etDialog.close(index);
                        		 });
							}else{
								var iframeWindow = window[el.find('iframe').get(0).name];
			                  	 iframeWindow.update()
							}
                         }
                     })
                 }
            });
        	}
        };
        
       
        //跳转保存页面
        var save = function (data) {
        	 $.etDialog.open({
                 url: 'pactStateAdd.do?isCheck=false',
                 width: 320,
                 height: 280,
                 title: '添加',
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
                	 if (rowdata.is_init == 0) {
                		 rowdata.group_id = ${group_id};
                         rowdata.hos_id = ${hos_id};
                         rowdata.copy_code = '${copy_code}';
	                     param.push(rowdata);
					 }
                 });
                 if (param.length == 0) {
                	 $.etDialog.error("数据不可被删除");
                	 return;
				}
                 
                 $.etDialog.confirm('确定删除?', function () {
                     ajaxPostData({
                         url: 'deletePactState.do',
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
            $("#is_stop").etSelect({
   			  options: [
   			    { id: 0, text: '否' },
   			    { id: 1, text: '是' }
   			  ],
   			  defaultValue: "none"
   			})
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt">
                <input id="state_name" type="text" />
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

