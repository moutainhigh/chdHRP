<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/map.js"></script>
    <script type="text/javascript">
    var dataFormat;
    var inMainGrid;
    var detailGrid;
    var gridManager;
    var data;
    var grid;
    var year;
    var month;
    var group_id = "${group_id}";
    var hos_id = "${hos_id}";
    var copy_code = "${copy_code}";
    var pay_id = "${pay_id}";
    var pay_bill_no = "${pay_bill_no}";
    var state = "${state}";
    var acc_check_state = "${acc_check_state}";
    var targetMap = new HashMap(); //存放明细数据
    $(function() {
        loadDict() //加载下拉框
        loadForm();
        loadHotkeys();
        loadHead();
        liger.get("sup_id").set("onSelected", function() {
            query();
        })
        liger.get("store_code").set("onSelected", function() {
            query();
        })
    });
    
    function query() {
        if (!liger.get("sup_id").getValue()) {
            $.ligerDialog.error("请先选择供应商");
        } else {
            inMainGrid.options.parms = [];
            inMainGrid.options.newPage = 1;
            //根据表字段进行添加查询条件
            inMainGrid.options.parms.push({ name: 'sup_id', value: liger.get("sup_id").getValue().split(",")[0] });
            inMainGrid.options.parms.push({ name: 'sup_no', value: liger.get("sup_no").getValue().split(",")[1] });
            inMainGrid.options.parms.push({ name: 'store_id', value: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0] });
            //加载查询条件
            inMainGrid.loadData(inMainGrid.where);
        }
    }

    function loadHead() {
        inMainGrid = $("#maingrid").ligerGrid({
            columns: [
                { display: '发票编号', name: 'bill_no', align: 'left', width: 120 },
                { display: '开票日期', name: 'bill_date', align: 'left', width: 100 },
                { display: '供应商', name: 'sup_name', align: 'left', width: 200 },
                { display: '制单人', name: 'maker_name', align: 'left', width: 100 },
                { display: '审核人', name: 'checker_name', align: 'left', width: 100 },
                { display: '审核日期', name: 'chk_date', align: 'left', width: 91 },
                {
                    display: '发票金额',
                    name: 'payable_money',
                    align: 'right',
                    width: 110,
                    render: function(rowdata, rowindex, value) {
                        return formatNumber(rowdata.payable_money, '${p04005 }', 1);
                    }
                },
                {
                    display: '已付金额',
                    name: 'payed_money',
                    align: 'right',
                    width: 110,
                    render: function(rowdata, rowindex, value) {
                        if (rowdata.payed_money == null | rowdata.payed_money == '' | rowdata.payed_money == 'undefined') {
                            rowdata.payed_money = 0;
                            return formatNumber(rowdata.payed_money, '${p04005 }', 1);
                        } else {
                            return formatNumber(rowdata.payed_money, '${p04005 }', 1);
                        }
                    }
                },
                {
                    display: '本次付款金额',
                    name: 'pay_money',
                    align: 'right',
                    width: 110,
                    render: function(rowdata, rowindex, value) {
                        if (rowdata.pay_money == null | rowdata.pay_money == '' | rowdata.pay_money == 'undefined') {
                            rowdata.pay_money = 0;
                            return formatNumber(rowdata.pay_money, '${p04005 }', 1);
                        } else {
                            return formatNumber(rowdata.pay_money, '${p04005 }', 1);
                        }
                    }
                }, {
                    display: '优惠金额',
                    name: 'dis_money',
                    align: 'right',
                    width: 90,
                 
                    render: function(rowdata, rowindex, value) {
                        if (!rowdata.dis_money) {
                            rowdata.dis_money = 0;
                        }
                        return formatNumber(rowdata.dis_money, '${p04005 }', 1);
                    }
                }

            ],
            dataAction: 'server',
            dataType: 'server',
            usePager: true,
            url: 'queryMatPayDetailNew.do?isCheck=false&pay_id=${pay_id}&state=${state}',
            width: '99%',
            height: '100%',
            checkbox: true,
            rownumbers: true,
            enabledEdit: true,
            heightDiff: -40,
            selectRowButtonOnly: true,
            allowAdjustColWidth: false,
            frozen: false,
            onsuccess: function() {

                //is_addRow();
            },
            showTitle: true,
            detail: { onShowDetail: showDetail, height: "auto", reload: true, single: true }, //入库单明细
            onAfterAddRow: updateMoney, // 新增行后  自动更新 要生成的付款单的  金额
            onAfterShowData: updateMoneyAfterSHow, //  显示完数据后   更新相应的 金额
            //onSelectRow: updatePay_money, // 勾选发票后  自动填写 要生成的付款单的  金额
            //onUnSelectRow: updatePay_money, // 取消勾选入库单后    更新相应的 金额
            toolbar: {
                items: [
                    { text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add', disabled: state == 1 ? false : true },
                    { line: true },
                    { text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete', disabled: state == 1 ? false : true },
                    { line: true },
                    //                          { text: '保存（<u>D</u>）', id:'save', click: saveMatPaylMain,icon:'save',disabled:state ==1?false:true },
                    //                      { line:true },
                    { text: '审核（<u>Z</u>）', id: 'audit', click: audit, icon: 'bluebook', disabled: state == 1 ? false : true },
                    { line: true },
                    { text: '消审（<u>U</u>）', id: 'unaudit', click: unAudit, icon: 'bookpen' },
                    { line: true },
                    { text: '关闭（<u>L</u>）', id: 'close', click: this_close, icon: 'close' }
                ]
            }
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function this_close() {
        frameElement.dialog.close();
    }

    function showDetail(row, detailPanel, callback) {
        var firstLoad = true;
        data = row;
        detailGrid = document.createElement('div');
        $(detailPanel).append(detailGrid);

        grid = $(detailGrid).css('margin', 10).ligerGrid({
                columns: [
                    { display: '入库单号', name: 'in_no', align: 'left', width: 120 },
                    { display: '材料编码', name: 'inv_code', width: 100 },
                    { display: '材料名称', name: 'inv_name', width: 130 },
                    { display: '计量单位', name: 'unit_name', width: 62 },
                    { display: '规格型号', name: 'inv_model', width: 140 },
                    { display: '批号', name: 'batch_no', width: 90 },
                    { display: '条形码', name: 'sn', width: 90 },
                    {
                        display: '单价',
                        name: 'price',
                        width: 90,
                        align: 'right',
                        render: function(rowdata, rowindex, value) {
                            return formatNumber(rowdata.price, '${p04006 }', 1);
                        }
                    },
                    { display: '数量', name: 'amount', width: 90 },
                    {
                        display: '发票金额',
                        name: 'bill_money',
                        align: 'right',
                        width: 90,
                        render: function(rowdata, rowindex, value) {
                            return formatNumber(rowdata.bill_money, '${p04005 }', 1);
                        }
                    },
                    {
                        display: '已付金额',
                        name: 'payed_money',
                        align: 'right',
                        width: 90,
                        render: function(rowdata, index, value) {
                            return formatNumber(rowdata.payed_money, '${p04005 }', 1);
                        }
                    },
                    {
                        display: '付款金额',
                        name: 'pay_money',
                        align: 'right',
                        width: 90,
                        editor: { type: 'float' },
                        render: function(rowdata, rowindex, value) {
                            return formatNumber(rowdata.pay_money, '${p04005 }', 1);
                        }
                    }, {
                        display: '优惠金额',
                        name: 'dis_money',
                        align: 'right',
                        width: 90,
                        editor: {
                        type: 'string',
                            // 编辑完优惠金额后，更改本次付款金额。- update - wsj.2017.8.14
                            onChanged: function (param) {
                                var value = param.value;
                                var pay_money = param.record.bill_money - param.record.payed_money - value;
                                grid.updateCell('pay_money', pay_money, param.record);
                            }
                        },
                        render: function(rowdata, rowindex, value) {
                            if (!rowdata.dis_money) {
                                rowdata.dis_money = 0;
                            }
                            return formatNumber(rowdata.dis_money, '${p04005 }', 1);
                        }
                    }
                ],
                dataAction: 'server',
                dataType: 'server',
                usePager: false,
                data: row.detail,
                width: '99%',
                checkbox: true,
                frozen: false,
                // url : 'queryMatBillDetail_Pay.do?isCheck=false&bill_id='+row.bill_id + '&pay_id='+row.pay_id+'&is_init='+row.is_init,columnWidth: 80,
                enabledEdit: true,
                fixedCellHeight: true,
                isAddRow: false,
                onAfterEdit: getPayMoney,
                //onSelectRow: detailData,
                //onUnSelectRow: removeDetail
            }

        );
        /*    console.log(1111, grid.getData()) */
        
        

    }


    function btn_saveColumn() {

        var path = window.location.pathname + "/maingrid";
        var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
        saveColHeader({
            grid: inMainGrid,
            path: path,
            url: url,
            callback: function(data) {
                $.ligerDialog.success("保存成功");
            }
        });

    }
    //勾选明细后  存储数据  更新 主表格发票金额
    function detailData(rowdata, rowid, rowobj) {
        var payed_money = 0.00; //存储  主表格 已付款金额
        var pay_money = 0.00; //存储  主表格本次付款金额
        var payable_money = 0.00; //存储  主表格本次付款金额
        var detailDate = grid.getSelectedRows();
        if (detailDate.length > 0) {
            $.each(detailDate, function(index, content) {
                //已付金额
                if (content.payed_money == '') {
                    content.payed_money = 0;
                    payed_money += 0;
                } else {
                    payed_money += content.payed_money;
                }
                //未付金额
                if (content.pay_money == '') {
                    content.pay_money = 0;
                    pay_money += 0;
                } else {
                    pay_money += content.pay_money;
                }
                //发票金额  应付合计
                if (content.bill_money == '') {
                    content.bill_money = 0;
                    payable_money += 0;
                } else {
                    payable_money += content.bill_money;
                }
            })
        }
        gridManager.updateCell('payable_money', parseFloat(payable_money), data);
        gridManager.updateCell('payed_money', parseFloat(payed_money), data);
        gridManager.updateCell('pay_money', parseFloat(pay_money), data);

        targetMap.put(data.__id + '|' + rowid, rowdata);
        if (!gridManager.isSelected(data)) {
            gridManager.select(data);
        }
        updatePay_money();
    }

    // 勾选、取消勾选发票后  自动填写 更新的付款单的  金额
    function updatePay_money(rowdata, rowId, rowEl) {
        var payable_money = 0.00;
        var payed_money = 0.00;
        var pay_money = 0.00;
        var dataMain = inMainGrid.getSelectedRows();
        if (dataMain.length > 0) {
            $(dataMain).each(function() {

                payable_money += this.payable_money;
                if (!this.payed_money) {
                    this.payed_money = 0;
                    payed_money += 0;
                } else {
                    payed_money += this.payed_money;
                }
                if (this.pay_money == '') {
                    this.pay_money = 0;
                    pay_money += 0;
                } else {
                    pay_money += this.pay_money;
                }
            })
        }

        $("#payable_money").val(parseFloat(payable_money.toFixed(2)));
        $("#payed_money").val(parseFloat(payed_money.toFixed(2)));
        $("#pay_money").val(parseFloat(pay_money.toFixed(2)));
        //alert(JSON.stringify(rowdata));
    }

    // 取消勾选明细后  移除存储的数据  更新 主表格本次付款金额
    function removeDetail(rowdata, rowid, rowobj) {
        var payed_money = 0.00; //存储  主表格 已付款金额
        var pay_money = 0.00; //存储  主表格本次付款金额
        var payable_money = 0.00; //付款合计

        var detailDate = grid.getSelectedRows();
        if (detailDate.length > 0) {
            $.each(detailDate, function(index, content) {
                //已付金额
                if (content.payed_money == '') {
                    content.payed_money = 0.00;
                    payed_money += 0;
                } else {
                    payed_money += content.payed_money;
                }

                //未付金额
                if (content.pay_money == '') {
                    content.pay_money = 0.00;
                    pay_money += 0;
                } else {
                    pay_money += content.pay_money;
                }

                //发票金额  应付合计
                if (content.bill_money == '') {
                    content.bill_money = 0;
                    payable_money += 0;
                } else {
                    payable_money += content.bill_money;
                }
            });

            gridManager.updateCell('payable_money', parseFloat(payable_money), data);
            gridManager.updateCell('payed_money', parseFloat(payed_money), data);
            gridManager.updateCell('pay_money', parseFloat(pay_money), data);

            targetMap.remove(data.__id + '|' + rowid, rowdata);
            if (!gridManager.isSelected(data)) {
                gridManager.select(data);
            }

        } else {
            updateMoney();
            gridManager.updateCell('payable_money', data.payable_moneys, data);
            gridManager.updateCell('payed_money', data.payed_moneys, data);

            targetMap.remove(data.__id + '|' + rowid, rowdata);
            if (gridManager.isSelected(data)) {
                gridManager.unselect(data);
            }
        }
        updatePay_money();
    }
    // 编辑完明细表格单元格后   选择该行数据
    function getPayMoney(e) {
        var payed_money = 0; //存储  主表格 已付款金额
        var pay_money = 0; //存储  主表格本次付款金额
        var dis_money_sum = 0;

        var total = e.record.payed_money + e.record.pay_money; //存储 明细表格 已付金额 与付款金额 的和 ，与明细单据金额对比（不能大于单据金额）
        if (Math.abs(total) > Math.abs(e.record.bill_money)) {
            $.ligerDialog.error('<span style="color: red">填写数据错误!该条数据的本次付款金额与已付金额之和大于单据金额.</span>(<span style="color:blue">请您重新填写本次付款金额</span>)');
            return;
        }

        // 增加判断，如果当前的detail grid有被选择的行， 那么按照选择的行计算。- update - wsj.2017.8.14
        var detailSelectRows = grid.getSelectedRows();
        if (detailSelectRows.length > 0) {
            detailSelectRows.forEach(function (item, index) {
                item.pay_money ? pay_money += item.pay_money : pay_money += 0;
                
                dis_money_sum += item.dis_money ?  item.dis_money : 0;
            })
        } else {
            var detailDate = grid.getData();
            if (detailDate.length > 0) {
                $.each(detailDate, function(index, content) {
                    payed_money += content.payed_money;
                    if (content.pay_money == "") {
                        content.pay_money = 0;
                        pay_money += 0;
                    } else {
                        pay_money += content.pay_money;
                    }
                    
                    dis_money_sum += content.dis_money ?  content.dis_money : 0;
                })
            }

            data.detail = { "Rows": detailDate, "Total": detailDate.length };
        }
        if (!gridManager.isSelected(data)) {
            gridManager.select(data);
        }
        gridManager.updateCell('payed_money', parseFloat(payed_money), data);
        gridManager.updateCell('pay_money', parseFloat(pay_money), data);
        gridManager.updateCell('dis_money', parseFloat(dis_money_sum), data);
        updatePay_money();
    }
    //新增行后  自动填写 更新的付款单的  金额
    function updateMoney() {
        var payable_money = 0;
        var payed_money = 0;
        var pay_money = 0;
        var dataMain = inMainGrid.getData();
        if (dataMain.length > 0) {
            $(dataMain).each(function() {
                payable_money += this.payable_money;
                if (this.payed_money == '') {
                    this.payed_money = 0;
                    payed_money += 0;
                } else {
                    payed_money += this.payed_money;
                }
                if (this.pay_money == '') {
                    this.pay_money = 0;
                    pay_money += 0;
                } else {
                    pay_money += this.pay_money;
                }

            })
        }
        $("#payable_money").val(payable_money);
        $("#payed_money").val(payed_money);
        $("#pay_money").val(pay_money);
    }

    //删除行后  自动更新 付款单的  金额
    function updateDeleteMoney() {
        var payable_money = 0; // 最新发票金额
        var payed_money = 0; // 最新已付金额   
        var pay_money = 0; // 最新付款金额
        var s = 0; // 删除的发票金额
        var t = 0; // 删除的已付金额   
        var p = 0; // 删除的付款金额
        var dataMain = inMainGrid.getCheckedRows();
        if (dataMain.length > 0) {
            $(dataMain).each(function() {
                s += this.payable_money;
                if (this.payed_money == '') {
                    this.payed_money = 0;
                    t += 0;
                } else {
                    t += this.payed_money;
                }
                if (this.pay_money == '') {
                    this.pay_money = 0;
                    p += 0;
                } else {
                    p += this.pay_money;
                }

            })
        }
        payable_money = $("#payable_money").val() - s;
        payed_money = $("#payed_money").val() - t;
        pay_money = $("#pay_money").val() - p;
        $("#payable_money").val(payable_money);
        $("#payed_money").val(payed_money);
        $("#pay_money").val(pay_money);
    }

    // 显示完数据 后  更新相应的 金额
    function updateMoneyAfterSHow() {
        // 
        var dataMain = inMainGrid.getData();
        $.each(dataMain, function(index, content) {
            var mainPayed_money = 0; //存储 该条入库单 所有材料的  已付金额 总额
            var mainPay_money = 0; //存储 该条入库单 所有材料的  本次付款金额 总额
            var detail = content.detail;
            $(detail.Rows).each(function() {
                mainPayed_money += this.payed_money;
                mainPay_money += this.pay_money;
            })
            gridManager.updateCell('payed_money', mainPayed_money, index);
            gridManager.updateCell('pay_money', mainPay_money, index);
        })
    }
    /* 生成付款单号 */
    var curDate = new Date();
    year = curDate.getFullYear();
    month = curDate.getMonth();

    function setPayBillNo() {
        var parm = {
            year: year,
            month: month
        };
        ajaxJsonObjectByUrl("setPayBillNo.do?isCheck=false", parm, function(responseData) {
            $("#pay_bill_no").val(responseData.prefixe + responseData.year + responseData.month + responseData.max_no);
        })
    }

    //添加
    function add_open() {

        if (liger.get("sup_id").getValue().split(",")[0] == '') {
            $.ligerDialog.error('请先选择供应商后再添加发票');
        } else {
            $.ligerDialog.open({
                url: 'selectBillPage.do?isCheck=false&sup_id=' +
                    liger.get("sup_id").getValue().split(",")[0] +
                    '&sup_no=' + liger.get("sup_id").getValue().split(",")[1] +
                    "&sup_code=" + liger.get("sup_id").getText().split(" ")[0] +
                    "&sup_name=" + liger.get("sup_id").getText().split(" ")[1],
                height: 500,
                width: 1000,
                top: 0,
                title: '选择发票',
                modal: true,
                showToggle: false,
                showMax: true,
                showMin: false,
                isResize: true
            });
        }

    }
    //删除
    function remove() {
        var data = inMainGrid.getCheckedRows();
        if (data.length == 0) {
            $.ligerDialog.error('选择要删除的行!');
            return;
        } else {
            $.ligerDialog.confirm('确定删除?', function(yes) {
                if (yes) {
                    var ParamVo = [];
                    for (var i = 0; i < data.length; i++) {
                        inMainGrid.remove(data[i]);
                    }
                }
            });
            //删除行后  自动更新 付款单的  金额
            updateDeleteMoney();
        }
    }

    function save() {
        var formPara = {
            pay_id: '${pay_id}',
            pay_bill_no: $("#pay_bill_no").val(),
            pay_date: $("#pay_date").val(),
            pay_bill_type: $("#pay_bill_type").val(),
            sup_id: liger.get("sup_id").getValue().split(",")[0],
            sup_no: liger.get("sup_id").getValue().split(",")[1],
            pay_code: liger.get("pay_code").getValue(),
            pay_type_code: liger.get("pay_type_code").getValue(),
            cheq_no: $("#cheq_no").val(),
            payable_money: $("#payable_money").val(),
            payed_money: $("#payed_money").val(),
            pay_money: $("#pay_money").val(),
            note: $("#note").val(),
            maker: '${maker}',
            make_date: '${make_date}',
            state: '${state}',
            is_init: '${is_init}',
            pay_dept_id: liger.get("dept_code").getValue().split(",")[0]
            /* ,
                      bank_name:$("#bank_name").val(),
                      bank_no:$("#bank_no").val() */
        };
        var data = gridManager.getData();
        var ParamVo = [];
        var str = '';
        var error = '';
        $.each(data, function(index, content) {
            if (content.pay_money == 0) {
                str += content.bill_no + ",";
            } else {
                var data_detail = content.detail;
                var dis_money = 0;
                $(data_detail.Rows).each(function() {
                    //alert(this.dis_money);
                    if (this.pay_money != null) {
                        if (Math.abs(this.pay_money) + Math.abs(this.payed_money) > Math.abs(this.bill_money)) {
                            error += content.bill_no + ",";
                        }
                        if (this.dis_money == null || this.dis_money == '' || this.dis_money == 'undefined') {
                            dis_money = 0;
                        } else {
                            dis_money = this.dis_money;
                        }
                        ParamVo.push(
                            content.group_id + "@" +
                            content.hos_id + "@" +
                            content.copy_code + "@" +
                            $("#pay_id").val() + "@" +
                            $("#pay_bill_no").val() + "@" +
                            this.pay_detail_id + "@" +
                            this.bill_id + "@" +
                            this.bill_detail_id + "@" +
                            this.bill_money + "@" +
                            this.payed_money + "@" +
                            this.pay_money + "@" +
                            dis_money
                        )
                    }
                })
            }

        })

        if (str != '') {
            $.ligerDialog.error('保存失败！发票编号:<span style="color: red">' + str + '本次付款金额为0</span>,<span style="color: blue">不能保存.请您仔细核对数据</span>');
            return;
        } else {
            if (error != '') {
                $.ligerDialog.error('保存失败！发票编号:<span style="color: red">' + error + '有明细数据的本次付款金额数据错误</span>,<span style="color: blue">不能保存.请您仔细核对数据</span>');
            } else {
                $.ligerDialog.confirm('确定要保存付款单及付款单明细信息吗?', function(yes) {
                    if (yes) {
                        ajaxJsonObjectByUrl("updateMatPayMain.do?isCheck=false", formPara, function(responseData) {
                            if (responseData.state == "true") {
                                parent.query();
                                ajaxJsonObjectByUrl("updateMatPayDetail.do?isCheck=false", { ParamVo: ParamVo.toString() }, function(responseData) {
                                    if (responseData.state == "true") {}
                                });
                            }
                        });
                    }
                });
            }

        }
    };

    // 审核
    function audit() {
        if (state != 1) {
            $.ligerDialog.error('审核失败！' + deliver_no + '单据不是未审核状态,不能审核');
            return;
        } else {
            var ParamVo = [];
            ParamVo.push(
                group_id + "@" +
                hos_id + "@" +
                copy_code + "@" +
                pay_id + "@" +
                pay_bill_no + "@" +
                state + "@" + 2
            )
            $.ligerDialog.confirm('确定审核?', function(yes) {
                if (yes) {
                    ajaxJsonObjectByUrl("updatePayState.do?isCheck=false", { ParamVo: ParamVo.toString() }, function(responseData) {
                        if (responseData.state == "true") {
                            self.location.reload(); //刷新本页
                            parent.query();
                        }
                    });
                }
            });
        }
    }
    //消审
    function unAudit() {

        if (state != 2 || acc_check_state == 1) {
            $.ligerDialog.error('消审失败！' + $("#pay_bill_no").val() + '单据不是已审核状态,不能消审');
            return;
        } else {
            var ParamVo = [];
            ParamVo.push(
                group_id + "@" +
                hos_id + "@" +
                copy_code + "@" +
                pay_id + "@" +
                pay_bill_no + "@" +
                state + "@" + 1
            )
            $.ligerDialog.confirm('确定消审?', function(yes) {
                if (yes) {
                    ajaxJsonObjectByUrl("updatePayState.do?isCheck=false", { ParamVo: ParamVo.toString() }, function(responseData) {
                        if (responseData.state == "true") {
                            self.location.reload(); //刷新本页
                            parent.query();
                        }
                    });
                }
            });
        }
    }

    function loadHotkeys() {

        hotkeys('S', saveMatPaylMain);
        hotkeys('P', printDate);
        hotkeys('M', printSet);
        hotkeys('D', printDetail);
        hotkeys('T', printDetailSet);
        hotkeys('A', add_open);
        hotkeys('C', this_close);
        hotkeys('D', remove);
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
        // $("form").ligerForm();
    }

    function saveMatPaylMain() {
        if ($("form").valid()) {
            save();
        }
    }

    function loadDict() {
        //字典下拉框
        //供应商下拉框
        autocomplete("#sup_id", "../../queryHosSupDictMethod.do?isCheck=false", "id", "text", true, true, '', false, '', 240);
        liger.get("sup_id").setValue('${sup_id},${sup_no}');
        liger.get("sup_id").setText('${sup_code} ${sup_name}');
        /* //科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
      autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true,'',false,'',200);
       liger.get("dept_id").setValue('${dept_id},${dept_no}');
           liger.get("dept_id").setText('${dept_code} ${dept_name}');
      //采购员下拉框
      autocomplete("#stocker", "../../queryMatStoctEmpDict.do?isCheck=false", "id", "text", true, true,'',false,'',200);
      liger.get("stocker").setValue('${stocker}');
          liger.get("stocker").setText('${emp_code} ${emp_name}'); */
        //付款条件
        autocomplete("#pay_code", "../../queryMatPayTerm.do?isCheck=false", "id", "text", true, true, '', false, '', 200);
        liger.get("pay_code").setValue('${pay_code}');
        liger.get("pay_code").setText('${pay_term_code} ${pay_term_name}');

        //付款方式下拉框 

        autocomplete("#pay_type_code", "../../queryMatPayType.do?isCheck=false", "id", "text", true, true, '', true, '', 200);
        liger.get("pay_type_code").setValue('${pay_type_code}');
        liger.get("pay_type_code").setText('${pay_name}');

        $("#pay_bill_type").val('${pay_bill_type}');

/*         autocomplete("#dept_code", "../../queryHosDeptDict.do?isCheck=false", "id", "text", true, true, { is_last: '1' }, false, '${pay_dept_code}', 205); */
        autocomplete("#dept_code", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, { is_last: '1',is_write:1}, false, '${pay_dept_code}', 205);
        autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_write:1}, true, '', 200);

   
        $("#pay_bill_no").ligerTextBox({ width: 200, disabled: true });
        $("#pay_date").ligerTextBox({ width: 200 });
        $("#pay_bill_type").ligerTextBox({ width: 200 });
        $("#sup_id").ligerTextBox({ width: 240 });
        $("#pay_code").ligerTextBox({ width: 200 });
        $("#pay_type_code").ligerTextBox({ width: 240 });
        $("#cheq_no").ligerTextBox({ width: 200 });
        $("#note").ligerTextBox({ width: 535 });
        $("#count").ligerTextBox({ width: 240 });
        $("#payable_money").ligerTextBox({ width: 200, disabled: true });
        $("#pay_money").ligerTextBox({ width: 200, disabled: true });
        $("#payed_money").ligerTextBox({ width: 240, disabled: true });
        /* $("#bank_name").ligerTextBox({width:200});
        $("#bank_no").ligerTextBox({width:240}); */

        //      if("${state}" != 1 ){
        //            $("#toptoolbar").hide();
        //          }
        if (state > 1) {
            $("#save").ligerButton({ click: saveMatPaylMain, width: 90, disabled: true });
        } else {
            $("#save").ligerButton({ click: saveMatPaylMain, width: 90 });
        }
        $("#print").ligerButton({ click: printDate, width: 90 });
        $("#printSet").ligerButton({ click: printSet, width: 100 });
        $("#printDetail").ligerButton({ click: printDetail, width: 90 });
        $("#printDetailSet").ligerButton({ click: printDetailSet, width: 100 });
        $("#close").ligerButton({ click: this_close, width: 90 });
    }


    //打印
    function printDate() {

        var useId = 0; //统一打印
        if ('${p04028}' == 1) {
            //按用户打印
            useId = '${sessionScope.user_id}';
        }
        else if('${p04028 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
        var para = {
            pay_id: $("#pay_id").val(), 
            template_code: '04026',
            class_name: "com.chd.hrp.mat.serviceImpl.matpay.MatPayMainServiceImpl",
            method_name: "queryMatPayMainByPrintTemlateNew", 
            isSetPrint: false, //是否套打，默认非套打
            isPreview: false, //是否预览，默认直接打印
            store_id:liger.get("store_code").getValue().split(",")[0], 
            use_id: useId,
            p_num: 0
        }; 
        ajaxJsonObjectByUrl("queryMatPayState.do?isCheck=false",{pay_id:$("#pay_id").val(),state:2},function(responseData){
			if (responseData.state == "true") {
       			 officeFormPrint(para);
			}
        });

        /**
        printTemplate("queryMatPayMainByPrintTemlate.do",para);
    
        */
    }

    //打印设置
    function printSet() {

        var useId = 0; //统一打印
        
        if ('${p04028 }' == 1) {
            //按用户打印
            useId = '${sessionScope.user_id }';
        }else if('${p04028 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
        
       officeFormTemplate({template_code: "04026", use_id: useId});

        /**
        parent.parent.$.ligerDialog.open({url : 'hrp/mat/matpay/matpaymain/matPayMainPrintSetPage.do?template_code=04026&use_id='+useId,
          data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
        });*/
    }
    //打印明细 
    function printDetail() {
        var useId = 0; //统一打印
        if ('${p04048 }' == 1) {
            //按用户打印
            useId = '${sessionScope.user_id }';
        }else if('${p04048 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
        var para = {
            pay_id: $("#pay_id").val(),
            template_code: '041327',
            class_name: "com.chd.hrp.mat.serviceImpl.matpay.MatPayMainServiceImpl",
            method_name: "queryMatPayDetailByPrintTemlateNew",
            isSetPrint: false, //是否套打，默认非套打
            isPreview: true, //是否预览，默认直接打印
            use_id: useId,
            store_id:liger.get("store_code").getValue().split(",")[0], 
            p_num: 0
        };
        ajaxJsonObjectByUrl("queryMatPayState.do?isCheck=false",{pay_id:$("#pay_id").val(),state:2},function(responseData){
			if (responseData.state == "true") {
        		officeFormPrint(para);
			}
        });
    }

    //明细模板设置
    function printDetailSet() {
        var useId = 0; //统一打印
        if ('${p04048 }' == 1) {
            //按用户打印
            useId = '${sessionScope.user_id }';
        }else if('${p04048 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
        //alert(useId);
        officeFormTemplate({ template_code: "041327", use_id: useId });
    }
    //关闭当前页面
    function this_close() {
        frameElement.dialog.close();
    }
    </script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
        <div class="l-layout-content" position="top">
            <input type="hidden" id="curentDate" name="curentDate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            <input type="hidden" id="pay_id" name="pay_id" value='${pay_id}' />
            <form name="form1" method="post" id="form1">
                <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款单号<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="pay_bill_no" type="text" id="pay_bill_no" ltype="text" value='${pay_bill_no}' disabled="disabled" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>供应单位<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:100}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>制单日期<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input class="Wdate" name="pay_date" type="text" id="pay_date" ltype="text" value='${pay_date}' style="width: 200px;" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>单据类别<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <select name="pay_bill_type" id="pay_bill_type" style="width: 135px;">
                                <option value="0">付款单</option>
                                <option value="1">退款单</option>
                            </select>
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款方式<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="pay_type_code" type="text" id="pay_type_code" ltype="text" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>票据号:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="cheq_no" type="text" id="cheq_no" value='${cheq_no}' ltype="text" validate="{maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款条件:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="pay_code" type="text" id="pay_code" ltype="text" validate="{maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>发票张数:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="count" type="text" id="count" ltype="text" value='${count}' validate="{maxlength:100}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款部门:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="dept_code" type="text" id="dept_code" ltype="text" validate="{maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>应付合计<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="payable_money" type="text" id="payable_money" value='${payable_money}' disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>已付金额<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="payed_money" type="text" id="payed_money" ltype="text" value='${payed_money}' disabled="disabled" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款金额<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="pay_money" type="text" id="pay_money" disabled="disabled" value='${pay_money}' ltype="text" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <%--  
             2017-03-06 银行信息取供应商默认的银行账号信息
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开户银行:</b></td>
                  <td align="left" class="l-table-edit-td"><input name="bank_name" type="text" id="bank_name" ltype="text" value="${bank_name}"  validate="{required:true,maxlength:20}" /></td>
                  <td align="left"></td>
                  
                  <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>银行账号:</b></td>
                  <td align="left" class="l-table-edit-td"><input name="bank_no" type="text" id="bank_no"  ltype="text" value="${bank_no}" validate="{required:true,maxlength:20}" /></td>
                  <td align="left"></td>
              </tr> 
              --%>
                        <tr>
                            <td align="right" class="l-table-edit-td" style="padding-left:20px;">仓&nbsp;&nbsp;库:</b>
                            </td>
                            <td align="left" class="l-table-edit-td">
                            <input name="store_code" type="text" id="store_code" ltype="text" />
                            </td>
                            <td align="left"></td>
                            <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>摘要:</b></td>
                            <td align="left" class="l-table-edit-td" colspan="7">
                                <input name="note" type="text" id="note" ltype="text" value='${note}' validate="{maxlength:100}" />
                            </td>
                            <td align="left"></td>
                            
                        </tr>
                        
                </table>
            </form>
        </div>
        <div class="l-layout-header" id="toptoolbar" position="center"></div>
        <div title="" class="l-layout-content" style="" position="centerbottom">
            <div id="maingrid"></div>
            <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
                <tr>
                    <td align="center" class="l-table-edit-td" colspan="3">
                        <button id="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
                        &nbsp;&nbsp;
                        <button id="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
                        &nbsp;&nbsp;
                        <button id="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
                        &nbsp;&nbsp;
                        <button id="printDetail" accessKey="D"><b>打印明细（<u>D</u>）</b></button>
                        &nbsp;&nbsp;
                        <button id="printDetailSet" accessKey="T"><b>明细模板（<u>T</u>）</b></button>
                        &nbsp;&nbsp;
                        <button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>

</html>