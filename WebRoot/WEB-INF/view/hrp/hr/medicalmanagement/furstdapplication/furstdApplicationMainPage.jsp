<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,select,validate,grid,upload,datepicker,dialog,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var app_date, dept_code, emp_id, patient, is_commit;
        var tree, grid;
        var initFrom = function () {
            app_date = $("#app_date").etDatepicker({
                range: true
            });
            emp_id = $("#emp_id").etSelect({
    			url : "../../queryEmpSelect.do?isCheck=false",
    			defaultValue : "none",
    			onChange : query,

            });
            is_commit = $("#is_commit").etSelect({
                options: [
                          { id: 0, text: '新建' },
                          { id: 1, text: '提交' },
                          { id: 2, text: '已审核' },
                          { id: 3, text: '未通过' }
                ],
                defaultValue: "none",
                onChange: query,
            });

        };
        var query = function () {
            var params = [
              { name: 'app_date', value: app_date.getValue()[0] || '' },
               { name: 'end_date', value: app_date.getValue()[1] || '' },
             
           	{
          			name : 'app_no',
          			 value: $("#app_no").val()
          		},{
       			name : 'emp_id',
       			value : emp_id.getValue() 
       		}, 
       		{
       			name : 'furstd_hos',
       			 value: $("#furstd_hos").val()
       		},
       		{
       			name : 'is_commit',
       			 value: is_commit.getValue()
       		},
       		
            ];
            grid.loadData(params,'queryFurstdApplication.do');
        };
        var add = function () {
        	parent.$.etDialog.open({
                url: 'hrp/hr/healthadministration/furstd/addFurstdApplicationPage.do?isCheck=false',
                title: '添加',
                frameName :window.name,
                width: $(window).width(),
                height: $(window).height(),
            });
        };
        var remove = function () {
        	var msg=""
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
            	if(item.rowData.is_commit!=0){
            		msg+="选择新建状态删除"
            	}else{
                param.push({
                	app_no: item.rowData.app_no,
                	dept_id: item.rowData.dept_id,
                	furstd_hos: item.rowData.furstd_hos,
                	app_date: item.rowData.app_date,
                	is_commit: item.rowData.is_commit,
                });
            	}
            })
         if(msg!=""){
        	  $.etDialog.error(msg);
              return;
         }
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deleteFurstdApplication.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                    // tree.reAsyncChildNodes(null, 'refresh');
                }
            })
            })
        };
        // 提交
        var submit = function () {
             var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.is_commit!=0){
                  		msg+='请选择新建状态单据';
                    	  return false;
                      }
                      ParamVo.push(rowdata);
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmHrFurstdApplication.do?isCheck=false',
                        data: {
                            paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
        // 撤回
        var cancel = function () {
            var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.is_commit!=1){
                  		msg+='请选择提交状态单据';
                    	  return false;
                      }
                      ParamVo.push(rowdata);
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定撤回?', function () {
                    ajaxPostData({
                        url: 'reConfirmHrFurstdApplication.do?isCheck=false',
                        data: {
                            paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
        // 审批
        var audit = function () {

            var msg = "";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.is_commit!=1){
                  		msg+='请选择提交状态单据';
                    	  return false;
                      }
                      ParamVo.push(rowdata);
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定审批?', function () {
                    ajaxPostData({
                        url: 'auditHrFurstdApplication.do?isCheck=false',
                        data: {
                            paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
        // 销审
        var unaudit = function () {
        	var msg="";
        	   var ParamVo = [];
               var data = grid.selectGet();
               if (data.length == 0) {
                   $.etDialog.error('请选择行');
               } else {
                     $(data).each(function () {
                         var rowdata = this.rowData;
                         if(rowdata.is_commit!=2){
                     		msg+='请选择审核状态单据';
                       	  return false;
                         }
                         ParamVo.push(rowdata);
                     });
                     if(msg!=""){
                   	  $.etDialog.error(msg);
                   	  return;
                     }
                   $.etDialog.confirm('确定销审?', function () {
                       ajaxPostData({
                           url: 'unauditHrHrFurstdApplication.do?isCheck=false',
                           data: {
                               paramVo: JSON.stringify(ParamVo)
                           },
                           success: function () {
                           	 query();
                           }
                       })
                   });
               }
        };
        // 未通过
        var dispass = function () {
        	var msg="";
        	   var ParamVo = [];
               var data = grid.selectGet();
               if (data.length == 0) {
                   $.etDialog.error('请选择行');
               } else {
                     $(data).each(function () {
                         var rowdata = this.rowData;
                         if(rowdata.is_commit!=2){
                     		msg+='请选择审核状态单据';
                       	  return false;
                         }
                         ParamVo.push(rowdata);
                     });
                     if(msg!=""){
                   	  $.etDialog.error(msg);
                   	  return;
                     }
                   $.etDialog.confirm('确定未通过?', function () {
                       ajaxPostData({
                           url: 'dispassHrHrFurstdApplication.do?isCheck=false',
                           data: {
                               paramVo: JSON.stringify(ParamVo)
                           },
                           success: function () {
                           	 query();
                           }
                       })
                   });
               }
        };
        var print = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
            		]  */}; 
        	var printPara={
                title: " 进修申请打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrFurstdApplicationService",
                method_name: "queryFurstdApplicationByPrint",
                bean_name: "hrFurstdApplicationService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
        var openUpdate = function (rowData) {
        		parent.$.etDialog.open({
                url: 'hrp/hr/healthadministration/furstd/updateFurstdApplicationPage.do?isCheck=false&emp_id='+rowData.emp_id+'&app_no='+rowData.app_no+'&app_date='+rowData.app_date,
                title: '修改',
                frameName :window.name,
                width: $(window).width(),
                height: $(window).height(),
            })
        };
        var initGrid = function () {
            var columns = [
                { display: '申请单号', name: 'app_no', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                { display: '申请日期', name: 'app_date', width: 120 },
                { display: '专业', name: 'profession', width: 120 },
                { display: '姓名', name: 'emp_name', width: 120 },
                { display: '年龄', name: 'age', width: 120 },
                { display: '工龄', name: 'workage', width: 120 },
                { display: '进修开始时间', name: 'beg_date', width: 120 },
                { display: '进修时长', name: 'duration', width: 120 },
                { display: '进修医院', name: 'furstd_hos', width: 120 },
                { display: '状态', name: 'commit_name', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
                dataModel: {
                    //url: ''
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                        { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                        { type: 'button', label: '审批', listeners: [{ click: audit }], icon: 'audit' },
                        { type: 'button', label: '销审', listeners: [{ click: unaudit }], icon: 'unaudit' },
                        { type: 'button', label: '未通过', listeners: [{ click: dispass }], icon: 'cacnel' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];
                openUpdate(currentRowData);
            })
        };

        $(function () {
            initFrom();
            initGrid();
            query();
        })
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="app_date"  type="text" />
                </td>
                <td class="label">申请单号：</td>
                <td class="ipt">
                    <input id="app_no" type="text" />
                </td>
                <td class="label">姓名：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">进修医院：</td>
                <td class="ipt">
                   <input id="furstd_hos" type="text">
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="is_commit" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>