<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String empCode=session.getAttribute("emp_code")!=null?session.getAttribute("emp_code").toString():"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/med/med.js" type="text/javascript"></script>
    <script type="text/javascript">
    var grid;
    var gridManager;
    var isAutoCheck = '${p08016 }' == 1 ? true : false;
    var isEmpByDept = '${p08043 }' == 1 ? true : false;
    var cur_date = '';
    var is_zero = 1;
    $(function() {

        var dates = getCurrentDate();
        cur_date = dates.split(";")[2];

        loadDict() //加载下拉框
        //loadForm();
        loadHead();
        $("#store_code").bind("change", function() {
            //loadHead();
            //console.log(grid);
            grid.columns[2].editor.grid.url= '../../../queryMedInvListApply.do?isCheck=false&store_id=' +
            liger.get("store_code").getValue().split(",")[0] +
            '&dept_id=' + liger.get("apply_dept").getValue().split(",")[0] +
            '&cur_date=' + cur_date + '&is_zero=' + is_zero ;
            grid.reRender();
        })
        $("#apply_dept").bind("change", function() {
            //loadHead();
            //console.log(grid);
            grid.columns[2].editor.grid.url= '../../../queryMedInvListApply.do?isCheck=false&store_id=' +
            liger.get("store_code").getValue().split(",")[0] +
            '&dept_id=' + liger.get("apply_dept").getValue().split(",")[0] +
            '&cur_date=' + cur_date + '&is_zero=' + is_zero ;
            grid.reRender();
        })

    });

    function onBeforeSelect() {
        alert('要选择的是' + newvalue);
        return confirm('onBeforeSelect事件可以阻止选择，是否继续');
    }

    function validateGrid() {
        //主表
        if ($("#app_date").val() == null || $("#app_date").val() == "") {
            $.ligerDialog.warn("申请日期不能为空");
            return false;
        }
        if (liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == "") {
            $.ligerDialog.warn("响应仓库不能为空");
            return false;
        }
        if (liger.get("apply_dept").getValue() == null || liger.get("apply_dept").getValue() == "") {
            $.ligerDialog.warn("申请科室不能为空");
            return false;
        }


        //台州医院要求领料人必填
        /*  		if(liger.get("app_emp").getValue() == null || liger.get("app_emp").getValue() == ""){
         			$.ligerDialog.warn("领料人不能为空");  
         			return false;  
         		} */
        //明细
        var msg = "";
        var rowm = "";
        //判断申领数量是否大于库存数量提示
        var rowm = "";
        var rows = 0;
        var data = gridManager.getData();
        //alert(JSON.stringify(data));
        //判断grid 中的数据是否重复或者为空
        var is_control = 0; //默认不控制
        //科室申请是否允许不按仓库库存量控制
        var para = {
            store_id: liger.get("store_code").getValue().split(",")[0]
        }
        ajaxJsonObjectByUrl("../../../queryMedStoreByCode.do?isCheck=false", para, function(responseData) {
            if (responseData.state == "true") {
                is_control = responseData.is_control;
            }
        }, false);


        var targetMap = new HashMap();
        $.each(data, function(i, v) {
            rowm = "";
            if (v.inv_id) {
                if (is_control == 1) {
                    if (!v.app_amount || v.app_amount < 0 | v.app_amount > v.cur_amount) { // || v.app_amount > v.cur_amount) {
                        rowm += "【数量】、";
                    }
                    if (rowm != "") {
                        rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "需大于零,并且申领数量应小于库存数量<br>"; //",并且申领数量应小于库存数量";
                    }
                } else {
                    if (!v.app_amount || v.app_amount < 0) { // || v.app_amount > v.cur_amount) {
                        rowm += "【数量】、";
                    }

                    if (rowm != "") {
                        rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "需大于零<br>"; //",并且申领数量应小于库存数量";
                    }
                }

                msg += rowm;
                var key = v.inv_id;
                var value = "第" + (i + 1) + "行";
                if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
                    targetMap.put(key, value);
                } else {
                    msg += targetMap.get(key) + "与" + value + "药品编码不能重复" + "\n\r";
                }
                rows += 1;
            }
        });
        if (rows == 0) {
            $.ligerDialog.warn("请添加明细数据！");
            return false;
        }
        if (msg != "") {
            $.ligerDialog.warn(msg);
            return false;
        }
        return true;
    }

    function save() {
    	
    	if($("#app_date").val() == ''){
    		$.ligerDialog.warn('申领日期不能为空');
    		return ; 
    	}
    	
    	if(liger.get("store_code").getValue() == ''){
    		$.ligerDialog.warn('响应仓库不能为空');
    		return ; 
    	}
    	
    	if(liger.get("apply_dept").getValue() == ''){
    		$.ligerDialog.warn('申领部门不能为空');
    		return ; 
    	}
    	
    	
    	if(liger.get("app_emp").getValue() == ''){
    		$.ligerDialog.warn('申请人不能为空');
    		return ; 
    	}
    	
        grid.endEdit();
        if (validateGrid()) {
            var formPara = {
                apply_no: $("#apply_no").val(),
                dept_id: liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[0],
                dept_no: liger.get("apply_dept").getValue() == null ? "" : liger.get("apply_dept").getValue().split(",")[1],
                app_date: $("#app_date").val(),
                brief: $("#brief").val(),
                store_id: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
                store_no: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
                app_emp: liger.get("app_emp").getValue() == null ? "" : liger.get("app_emp").getValue(),
                response_emp: liger.get("response_emp").getValue() == null ? "" : liger.get("response_emp").getValue(),
                //state : isAutoCheck ? 2 : 1,
                state: 1,
                detailData: JSON.stringify(gridManager.getData())
            };


            ajaxJsonObjectByUrl("addMedCommonOutApply.do", formPara, function(responseData) {
                if (responseData.state == "true") {
                    parentFrameUse().query();
                    /* $.ligerDialog.confirm('是否继续添加申请单?', function (yes){
 	            		if(yes){
 	            	        autodate("#app_date");//默认当前日期
 	            			$("#brief").val("");
 	            			loadDict();
 	                        grid.reload();
 	                        is_addRow();
 	            		}else{
 	            			parentFrameUse().update_open(responseData.update_para);
 	            			this_close();
 	            		}
 	            	}); */
                    parentFrameUse().update_open(responseData.update_para);
                    this_close();
                }
            });
        }

    }

    function loadForm() {

        $.metadata.setType("attr", "validate");
        var v = $("form").validate({
            errorPlacement: function(lable, element) {
                if (element.hasClass("l-textarea")) {
                    element.ligerTip({ content: lable.html(), target: element[0] });
                } else if (element.hasClass("l-text-field")) {
                    element.parent().ligerTip({ content: lable.html(), target: element[0] });
                } else {
                    lable.appendTo(element.parents("td:first").next("td"));
                }
            },
            success: function(lable) {
                lable.ligerHideTip();
                lable.remove();
            },
            submitHandler: function() {
                $("form .l-text,.l-textarea").ligerHideTip();
            }
        });
        //$("form").ligerForm(); 
    }

    function loadDict() {
        //字典下拉框
        autocompleteAsync("#store_code", "../../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_read:'1'}, true);
        liger.get("store_code").set("onBeforeSelect", function() {
            var manager = $("#maingrid").ligerGetGridManager();
            var data = manager.getData();
            var flag = true;
            $.each(data, function(i, v) {
                if (v.inv_code == "" || v.inv_code == null || v.inv_code == 'undefined') {
                    flag = true;
                } else {
                    flag = false;
                    return false;
                }
            });
            if (!flag) {
                $.ligerDialog.warn("已经存在明细数据不允许改动");
                return false;
            }
        });


        autocompleteAsync("#apply_dept", "../../../queryMedDeptDictDate.do?isCheck=false", "id", "text", true, true, { is_last: 1 ,is_write:'1'}, true, true);


        //响应人
        autocomplete("#response_emp", "../../../queryMedEmp.do?isCheck=false", "id", "text", true, true,"",false);
        //科室绑定change事件
        /*  $("#apply_dept").bind("change",function(){
        	if(liger.get("apply_dept").getValue()){
            	var para = {dept_id : liger.get("apply_dept").getValue() == "" ? "0" : liger.get("apply_dept").getValue().split(",")[0]};
            	liger.get("app_emp").setValue("");
            	liger.get("app_emp").setText("");
            	autocomplete("#app_emp", "../../../queryMedEmp.do?isCheck=false", "id", "text", true, true, para, false);
        	}
        })  */
		//申领人
        autocomplete("#app_emp", "../../../queryMedEmp.do?isCheck=false", "id", "text", true, true,"",false,'${empCode}');
        //autocompleteAsync("#app_emp", "../../../queryMedEmp.do?isCheck=false", "id", "text", true, true, {dept_id : liger.get("apply_dept").getValue().split(",")[0]}, true,true);
        /* if("${user_msg.dept_id}"){  --由于员工所在科室存在问题会与权限科室相冲故暂时取消该默认
        	liger.get("apply_dept").setValue("${user_msg.dept_id},${user_msg.dept_no}");
            liger.get("apply_dept").setText("${user_msg.dept_code} ${user_msg.dept_name}");
        } */
        if ("${user_msg.emp_id}") {
            liger.get("app_emp").setValue("${user_msg.emp_id}");
            liger.get("app_emp").setText("${user_msg.emp_code} ${user_msg.emp_name}");
        }

        //格式化文本框
        $("#apply_no").ligerTextBox({ width: 160, disabled: true });
        $("#app_date").ligerTextBox({ width: 160 });
        autodate("#app_date", "yyyy-MM-dd HH:mm:ss"); //默认当前日期
        $("#brief").ligerTextBox({ width: 320 });
        //格式化按钮
        $("#save").ligerButton({ click: save, width: 90 });
        $("#close").ligerButton({ click: this_close, width: 90 });
    }

    function changeColumn() {
        is_zero = $("#is_zero").is(":checked") ? 1 : 0
        loadHead();
    }

    function loadHead() {

        grid = $("#maingrid").ligerGrid({
            columns: [{
                display: '药品名称(E)',
                name: 'inv_id',
                textField: 'inv_name',
                width: 240,
                align: 'left',
                editor: {
                    type: 'select',
                    valueField: 'inv_id',
                    textField: 'inv_name',
                    selectBoxWidth: "90%",
                    selectBoxHeight: 240,
                    grid: {
                        columns: [{
                            display: '交易编码',
                            name: 'bid_code',
                            width: 100,
                            align: 'left'
                        }, {
                            display: '药品编码',
                            name: 'inv_code',
                            width: 100,
                            align: 'left'
                        }, {
                            display: '药品名称',
                            name: 'inv_name',
                            width: 240,
                            align: 'left'
                        }, {
                            display: '别名',
                            name: 'alias',
                            width: 120,
                            align: 'left'
                        }, {
                            display: '规格型号',
                            name: 'inv_model',
                            width: 180,
                            align: 'left'
                        }, {
                            display: '计划单价',
                            name: 'plan_price',
                            width: 100,
                            align: 'right',
                            render: function(rowdata, rowindex, value) {
                                return formatNumber(value, '${p08006 }', 1);
                            }
                        }, {
                            display: '未处理数量',
                            name: 'out_amount',
                            width: 90,
                            align: 'left',
                            render: function(rowdata, rowindex, value) {
                                return formatNumber(value, 2, 0);
                            }
                        }, {
                            display: '库存',
                            name: 'cur_amount',
                            width: 90,
                            align: 'left',
                            render: function(rowdata, rowindex, value) {
                                return formatNumber(value, 2, 0);
                            }
                        }, {
                            display: '计量单位',
                            name: 'unit_name',
                            width: 60,
                            align: 'left'
                        }, {
                            display: '包装规格',
                            name: 'inv_structure',
                            width: 90,
                            align: 'left'
                        }, {
                            display: '生产厂商',
                            name: 'fac_name',
                            width: 200,
                            align: 'left'
                        }, {
                            display: '是否代销药品',
                            name: 'is_com',
                            width: 90,
                            align: 'left',
                            render: function(rowdata, rowindex, value) {
                                return value == 1 ? '是' : '否';
                            }
                        }, {
                            display: '是否灭菌药品',
                            name: 'is_disinfect',
                            width: 90,
                            align: 'left',
                            render: function(rowdata, rowindex, value) {
                                return value == 1 ? '是' : '否';
                            }
                        }, {
                            display: '零售价',
                            name: 'sell_price',
                            width: 100,
                            align: 'right',
                            render: function(rowdata, rowindex, value) {
                                return formatNumber(value, '${p08006 }', 1);
                            }
                        }, {
                            display: '药品类别',
                            name: 'med_type_name',
                            width: 140,
                            align: 'left'
                        }, {
                            display: '证件号',
                            name: 'cert_code',
                            width: 100,
                            align: 'left'
                        }, {
                            display: '货位名称',
                            name: 'location_new_name',
                            width: 100,
                            align: 'left'
                        }, {
                            display: '包装单位',
                            name: 'pack_name',
                            width: 90,
                            align: 'left'
                        }, {
                            display: '转换量',
                            name: 'num_exchange',
                            width: 90,
                            align: 'left'
                        }],
                        switchPageSizeApplyComboBox: false,
                        onSelectRow: function(data) {
                            var e = window.event;
                            if (e && e.which == 1) {
                                f_onSelectRow_detail(data);
                            }
                        },
                        url: '../../../queryMedInvListApply.do?isCheck=false&store_id=' +
                            liger.get("store_code").getValue().split(",")[0] +
                            '&dept_id=' + liger.get("apply_dept").getValue().split(",")[0] +
                            '&cur_date=' + cur_date + '&is_zero=' + is_zero,
                        pageSize: 500,
                        onSuccess: function(data, g) { //加载完成时默认选中
                            if (grid.editor.editParm) {
                                var editor = grid.editor.editParm.record;
                                var item = data.Rows.map(function(v, i) {
                                    return v.inv_name;
                                });
                                var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
                                //加载完执行
                                setTimeout(function() {
                                    g.select(data.Rows[index]);
                                }, 80);
                            }
                        }
                    },
                    delayLoad: false,
                    keySupport: true,
                    autocomplete: true,
                    onSuccess: function() {
                        this.parent("tr").next(".l-grid-row").find("td:first").focus();
                    },
                    ontextBoxKeyEnter: function(data) {
                        f_onSelectRow_detail(data.rowdata);
                    }
                },
                render: function(rowdata, rowindex, value) {
                    return rowdata.inv_name;
                }
            }, {
                display: '交易编码',
                name: 'bid_code',
                width: 100,
                align: 'left'
            }, {
                display: '药品编码',
                name: 'inv_code',
                width: 120,
                align: 'left',
                totalSummary: {
                    align: 'right',
                    render: function(suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
            }, {
                display: '药品变更号',
                name: 'inv_no',
                align: 'left',
                width: 80,
                hide: true
            }, {
                display: '规格型号',
                name: 'inv_model',
                width: 160,
                align: 'left'
            }, {
                display: '生成厂商',
                name: 'fac_name',
                width: 160,
                align: 'left'
            }, {
                display: '计量单位',
                name: 'unit_name',
                width: 60,
                align: 'left'
            }, {
                display: '包装规格',
                name: 'inv_structure',
                width: 90,
                align: 'left'
            }, {
                display: '库存',
                name: 'cur_amount',
                width: 90,
                align: 'left',
                render: function(rowdata, rowindex, value) {
                    return formatNumber(value, 2, 0);
                }
            }, {
                display: '未处理数量',
                name: 'out_amount',
                width: 90,
                align: 'left',
                render: function(rowdata, rowindex, value) {
                    return formatNumber(value, 2, 0);
                }
            }, {
                display: '请领数量(E)',
                name: 'app_amount',
                width: 90,
                type: 'number',
                align: 'left',
                editor: {
                    type: 'number',
                },
                render: function(rowdata, rowindex, value) {
                    return value == null ? "" : formatNumber(value, 2, 1);
                },
                totalSummary: {
                    align: 'left',
                    render: function(suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
                    }
                }
            }, {
                display: '单价',
                name: 'price',
                width: 100,
                align: 'right',
                render: function(rowdata, rowindex, value) {
                    return value == null ? "" : formatNumber(value, '${p08006 }', 1);
                }
            }, {
                display: '金额',
                name: 'amount_money',
                width: 100,
                align: 'right',
                render: function(rowdata, rowindex, value) {
                    return value == null ? "" : formatNumber(value, '${p08005 }', 1);
                },
                totalSummary: {
                    align: 'right',
                    render: function(suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
                    }
                }
            }, {
                display: '是否代销药品',
                name: 'is_com',
                width: 90,
                align: 'left',
                render: function(rowdata, rowindex, value) {
                    return value == 1 ? '是' : '否';
                }
            }, {
                display: '是否灭菌药品',
                name: 'is_disinfect',
                width: 90,
                align: 'left',
                render: function(rowdata, rowindex, value) {
                    return value == 1 ? '是' : '否';
                }
            }, {
                display: '备注(E)',
                name: 'note',
                width: 160,
                align: 'left',
                editor: {
                    type: 'text',
                }
            }],
            usePager: false,
            width: '100%',
            height: '98%',
            checkbox: true,
            enabledEdit: true,
            alternatingRow: true,
            onBeforeEdit: f_onBeforeEdit,
            onBeforeSubmitEdit: f_onBeforeSubmitEdit,
            onAfterEdit: f_onAfterEdit,
            isScroll: true,
            rownumbers: true,
            delayLoad: true, //初始化明细数据
            selectRowButtonOnly: true, //heightDiff: -10,
            toolbar: {
                items: [{
                    text: '添加行（<u>A</u>）',
                    id: 'add',
                    click: is_addRow,
                    icon: 'add'
                }, {
                    line: true
                }, {
                    text: '配套表导入（<u>M</u>）',
                    id: 'match_imp',
                    click: match_imp,
                    icon: 'up'
                }, {
                    line: true
                }, {
                    text: '删除（<u>D</u>）',
                    id: 'delete',
                    click: remove,
                    icon: 'delete'
                }, {
                    line: true
                }, {
                    text: '选择药品（<u>C</u>）',
                    id: 'choice_inv',
                    click: choice_inv,
                    icon: 'add'
                }]
            }
        });

        gridManager = $("#maingrid").ligerGetGridManager();

        grid.addRow();
    }

    //删除行集合
    function deleteRange(data) {
        grid.deleteRange(data);
    }

    function btn_saveColumn() {

        var path = window.location.pathname + "/maingrid";
        var url = '../../../../sys/addBatchSysTableStyle.do?isCheck=false';
        saveColHeader({
            grid: grid,
            path: path,
            url: url,
            callback: function(data) {
                $.ligerDialog.success("保存成功");
            }
        });

        return false;
    }

    function choice_inv() {

        if (liger.get("store_code").getValue() == null) {
            $.ligerDialog.warn('请选择仓库');
            return;
        }

        var store_id = liger.get("store_code").getValue().split(",")[0];
        var store_no = liger.get("store_code").getValue().split(",")[1];

        $.ligerDialog.open({
            url: "medCommonOutApplyChoiceInvPage.do?isCheck=false&store_id=" + store_id + "&store_no=" + store_no,
            height: 500,
            width: 900,
            title: '选择药品',
            modal: true,
            showToggle: false,
            showMax: true,
            showMin: false,
            isResize: true,
            buttons: [{
                    text: '确定',
                    onclick: function(item, dialog) {
                        dialog.frame.addNew();
                    },
                    cls: 'l-dialog-btn-highlight'
                },
                {
                    text: '取消',
                    onclick: function(item, dialog) {
                        dialog.close();
                    }
                }
            ]
        });
    }

    //添加行
    function is_addRow() {
        grid.addRow();
    }

    //添加行数据
    function add_rows(data) {
       	var gridData = grid.getData();

        grid.addRows(data, gridData, -1);
    }
    //配套表导入
    function match_imp() {
        var para = "store_id=" + liger.get("store_code").getValue() +
            "&store_text=" + liger.get("store_code").getText() +
            "&dept_id=" + liger.get("apply_dept").getValue() +
            "&dept_text=" + liger.get("apply_dept").getText();
        $.ligerDialog.open({
            title: '配套导入',
            height: 500,
            width: 900,
            url: 'matchImpPage.do?isCheck=false&' + para,
            modal: true,
            showToggle: false,
            showMax: true,
            showMin: false,
            isResize: true,
            top: 1
        });
    }
    //删除
    function remove() {
        grid.deleteSelectedRow();

        setTimeout(function () {
        	var gridData = grid.getData()
        	var ishasEmpty = false;

        	gridData.forEach(function (item, index) {
        		if (!item.inv_id) {
        			ishasEmpty = true;
        		}
        	})
        	
        	if (!ishasEmpty) {
        		grid.addRow();
        	}
        }, 100)
    }

    var rowindex_id = "";
    var column_name = "";

    function f_onBeforeEdit(e) {
        rowindex_id = e.rowindex;
        column_name = e.column.name;
    }
    //选中回充数据
    function f_onSelectRow_detail(data, rowindex, rowobj) {
        selectData = "";
        selectData = data;
        //alert(JSON.stringify(data)); 
        //回充数据 
        if (selectData != "" || selectData != null) {
            if (column_name == "inv_id") {
                grid.updateRow(rowindex_id, {
                    inv_id: data.inv_id,
                    bid_code: data.bid_code,
                    inv_name: data.inv_name,
                    inv_code: data.inv_code,
                    inv_no: data.inv_no,
                    inv_model: data.inv_model == null ? "" : data.inv_model,
                    unit_name: data.unit_name == null ? "" : data.unit_name,
                    fac_name: data.fac_name == null ? "" : data.fac_name,
                    is_com: data.is_com == null ? "" : data.is_com,
                    is_disinfect: data.is_disinfect == null ? "" : data.is_disinfect,
                    price: data.price == null ? "" : data.price,
                    cur_amount: data.cur_amount == null ? "" : data.cur_amount,
                    out_amount: data.out_amount == null ? "" : data.out_amount,
                    inv_structure: data.inv_structure == null ? "" : data.inv_structure,
                });
                
                setTimeout(function (){
					grid.endEditToNext();
				},300)
            }
        }
        return true;
    }

    function f_onSelectRow(data, rowindex, rowobj) {
        return true;
    }
    // 编辑单元格提交编辑状态之前作判断限制
    function f_onBeforeSubmitEdit(e) {
        /*
        if (e.column.name == "inv_id" && e.value == ""){
        	//e.column.name.focus();
        	return false;
        }else if (e.column.name == "app_amount" && e.value == 0){
        	return false;
        }else if (e.column.name == "price" && e.value == 0){
        	return false;
        }
        */
        return true;
    }
    // 跳转到下一个单元格之前事件
    function f_onAfterEdit(e) {
        if (e.value != "") {
            if (e.column.name == "app_amount") {
                //自动计算金额
                if (e.record.price != undefined) {
                    grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
                }
            }
        }
        grid.updateTotalSummary();
        return true;
    }
    //获取grid所有数据
    function getData() {
        var manager = $("#maingrid").ligerGetGridManager();
        var data = manager.getData();
        return JSON.stringify(data);
    }
    //添加空行
    function is_addRow() {
        setTimeout(function() { //当数据为空时 默认新增一行
            grid.addRow();
        }, 500);
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('S', save);
        hotkeys('M', match_imp);
        hotkeys('S', audit);
        hotkeys('U', send);
        hotkeys('D', remove);
        hotkeys('P', printDate);
        hotkeys('A', batch_imp);
        hotkeys('C', this_close);
    }
    //打印
    function printDate() {

    }
    //关闭当前弹出框
    function this_close() {
        frameElement.dialog.close();
    }
    </script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <form name="form1" method="post" id="form1">
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
            <tr>
                <td align="right" class="l-table-edit-td">
                    <span style="color:red">*</span>申请单号：
                </td>
                <td align="left" class="l-table-edit-td">
                    <input name="apply_no" type="text" id="apply_no" value="自动生成" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
                </td>
                <td align="right" class="l-table-edit-td">
                    <span style="color:red">*</span>申请日期：
                </td>
                <td align="left" class="l-table-edit-td">
                    <input class="Wdate" name="app_date" id="app_date" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" validate="{required:true}" />
                </td>
                <td align="right" class="l-table-edit-td">
                    <span style="color:red">*</span>响应仓库：
                </td>
                <td align="left" class="l-table-edit-td">
                    <input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">
                    <span style="color:red">*</span>申请部门：
                </td>
                <td align="left" class="l-table-edit-td">
                    <input name="apply_dept" type="text" id="apply_dept" ltype="text" required="true" validate="{required:true}" />
                </td>
                <td align="right" class="l-table-edit-td">
                    <span style="color:red">*</span>申&nbsp;领&nbsp;人：
                </td>
                <td align="left" class="l-table-edit-td">
                    <input name="app_emp" type="text" id="app_emp" ltype="text" />
                </td>
                <td align="right" class="l-table-edit-td">
                    响&nbsp;应&nbsp;人：
                </td>
                <td align="left" class="l-table-edit-td">
                    <input name="response_emp" type="text" id="response_emp" ltype="text" />
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">不显示零库存药品：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="is_zero" type="checkbox" id="is_zero" ltype="text" onclick="changeColumn()" checked="checked" />
                    <span style="color:red">←申领零库存药品，请去掉勾选</span>
                </td>
                <td align="right" class="l-table-edit-td">
                    摘&nbsp;&nbsp;&nbsp;&nbsp;要：
                </td>
                <td align="left" class="l-table-edit-td">
                    <input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:50}" />
                </td>
            </tr>
        </table>
    </form>
    <div style="width: 100%; height: 100%;">
        <div id="maingrid"></div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
            <tr>
                <td align="center" class="l-table-edit-td">
                    <button id="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
                    &nbsp;&nbsp;
                    <button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
                </td>
            </tr>
        </table>
    </div>
</body>

</html>