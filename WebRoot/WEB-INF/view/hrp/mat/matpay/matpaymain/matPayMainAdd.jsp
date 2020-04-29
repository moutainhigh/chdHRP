<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/jquery.cookie.js"></script>   
<style>
    body {
        min-width: 1200px;
    }
</style>
<script src="<%=path%>/lib/map.js"></script>
<script type="text/javascript">
    var dataFormat;
    var inMainGrid;
    var pay_id = "${pay_id}";
    var detailGrid;
    var gridManager;
    var data;
    var grid;
    var year;
    var month;
    var is_init = '0';
    var targetMap = new HashMap(); //存放明细数据

    $(function() {
        loadDict() //加载下拉框
        loadForm();
        loadHotkeys();
        loadHead();
        //读取cookie
        if ($.cookie("chd_hrp_matpay_begindate") != null) {
			$("#beginDate").val($.cookie("chd_hrp_matpay_begindate"));
		}
        if ($.cookie("chd_hrp_matpay_enddate") != null) {
			$("#endDate").val($.cookie("chd_hrp_matpay_enddate"));
		}
		
        liger.get("sup_id").set("onSelected", function() {
            query();
        })

        /* $("#pay_type_code").change(function(){
             setPayBillNo();
         }) */
   

    });
    //查询
    function query() {
        if (!liger.get("sup_id").getValue()) {
            $.ligerDialog.error("请先选择供应商");
        } else {
            inMainGrid.options.parms = [];
            inMainGrid.options.newPage = 1;
            //根据表字段进行添加查询条件
            inMainGrid.options.parms.push({ name: 'sup_id', value: liger.get("sup_id").getValue().split(",")[0] });
            
            inMainGrid.options.parms.push({ name: 'sup_no', value: liger.get("sup_id").getValue().split(",")[1] });
            //是否只显示期初发票
            inMainGrid.options.parms.push({ name: 'is_init', value: is_init });
            //
            inMainGrid.options.parms.push({ name: 'pay_bill_type', value: $("#pay_bill_type").val() });

            inMainGrid.options.parms.push({ name: 'beginDate', value: $("#beginDate").val() });
            inMainGrid.options.parms.push({ name: 'endDate', value: $("#endDate").val() });
            //记录cookie
            $.cookie("chd_hrp_matpay_begindate", $("#beginDate").val(), {
				expires : 7
			});
			$.cookie("chd_hrp_matpay_enddate", $("#endDate").val(), {
				expires : 7
			});
            inMainGrid.options.parms.push({ name: 'maker', value: liger.get("maker").getValue() });
         //   inMainGrid.options.parms.push({ name: 'set_id', value: liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue() });
            inMainGrid.options.parms.push({ name: 'store_id', value: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0] });
            //加载查询条件
            inMainGrid.loadData(inMainGrid.where);
        }
    }

    function loadHead() {
        inMainGrid = $("#maingrid").ligerGrid({
            columns: [
                { display: '发票编号', name: 'bill_no', align: 'left', width: 120 },
                { display: '开票日期', name: 'bill_date', align: 'left', width: 120 },
                { display: '供应商', name: 'sup_name', align: 'left', width: 250 },
                { display: '制单人', name: 'maker_name', align: 'left', width: 120 },
                { display: '审核人', name: 'checker_name', align: 'left', width: 120 },
                { display: '审核日期', name: 'chk_date', align: 'left', width: 120 },
                {
                    display: '发票金额',
                    name: 'payable_money',
                    align: 'right',
                    width: 120,
                    render: function(rowdata, rowindex, value) {
                        return formatNumber(rowdata.payable_money, '${p04005 }', 1);
                    }
                },
                {
                    display: '已付金额',
                    name: 'payed_money',
                    align: 'right',
                    width: 120,
                    render: function(rowdata, rowindex, value) {
                        return formatNumber(rowdata.payed_money, '${p04005 }', 1);
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
                }
            ],
            dataAction: 'server',
            dataType: 'server',
            usePager: true,
            url: 'queryMatBillMain_PayAdd.do?isCheck=false&pay_id=${pay_id}',
            width: '100%',
            height: '100%',
            checkbox: true,
            rownumbers: true,
            enabledEdit: true,
            frozen: false,
            delayLoad: true, //初始化不加载，默认false
            selectRowButtonOnly: true,
            heightDiff: 0,
            allowAdjustColWidth: false,
            showTitle: true,
            detail: { onShowDetail: showDetail, height: 'auto', reload: true, single: false }, //入库单明细
            onSelectRow: updatePay_money, // 勾选发票后  自动填写 要生成的付款单的  金额
            onUnSelectRow: updatePay_money, // 取消勾选入库单后    更新相应的 金额
            onAfterShowData: updateMoney, //  显示完数据后   更新相应的 金额
            toolbar: {
                items: [
                    /*  { text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
                    { line:true },
                    { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
                    { line:true }, */
                    { text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' },
                    { line: true },
                    { text: '保存（<u>S</u>）', id: 'save', click: saveMatBillMain, icon: 'save' },
                    { line: true },
                    { text: '<input type="checkbox" id="initOnly" />只显示期初发票', id: '', click: setInit, icon: '' },
                    { line: true },
                    { text: '关闭（<u>L</u>）', id: 'close', click: this_close, icon: 'close' }
                ]
            },
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function this_close() {
        frameElement.dialog.close();
    }

    function showDetail(row, detailPanel, callback) {
        data = row;
        detailGrid = document.createElement('div');
        $(detailPanel).append(detailGrid);
        grid = $(detailGrid).css('margin', 10).ligerGrid({
            columns: [
                { display: '入库单据号', name: 'in_no', align: 'left', width: 150 },
                { display: '材料编码', name: 'inv_code', align: 'left', width: 100 },
                { display: '材料名称', name: 'inv_name', align: 'left', width: 150 },
                { display: '计量单位', name: 'unit_name', width: 50 },
                { display: '规格型号', name: 'inv_model', align: 'left', width: 170 },
                { display: '批号', name: 'batch_no', align: 'left', width: 100 },
                { display: '条形码', name: 'sn', align: 'left', width: 90 },
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
                    render: function(rowdata, rowindex, value) {

                        return formatNumber(rowdata.payed_money, '${p04005 }', 1);
                    }
                },
                {
                    display: '优惠金额',
                    name: 'dis_money',
                    align: 'right',
                    width: 90,
                    editor: {
                        type: 'string',
                        // 编辑完优惠金额后，更改本次付款金额。- update - wsj.2017.8.1
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
                },

                {
                    display: '本次付款金额',
                    name: 'pay_money',
                    align: 'right',
                    width: 90,
                    editor: { type: 'string' },
                    render: function(rowdata, rowindex, value) {
                        return formatNumber(rowdata.pay_money, '${p04005 }', 1);
                    }
                }

            ],

            dataAction: 'server',
            dataType: 'server',
            usePager: false,
            checkbox: true,
            selectRowButtonOnly: true,
            delayLoad: false, //初始化不加载，默认false
            width: '99%',
            height: 'auto',
            isScoll: true,
            allowAdjustColWidth: false,
            frozen: false,
            isAddRow: false,
            //url : 'queryMatInDetail.do?isCheck=false&in_id='+row.in_id+'&pre_pay_id='+row.pre_pay_id+'&init='+row.init,columnWidth:80,
            data: row.detail,
            enabledEdit: true,
            fixedCellHeight: true,
            onAfterEdit: getPayMoney,
            onSelectRow: detailData,
            onUnSelectRow: removeDetail,
            // onCheckAllRow: detailAllCheck
        });
    }

    //明细的全选事件
    // function detailAllCheck(checked, element) {
    //     console.log("明细全选")
    //     if (checked) {
    //         var payable_money = 0.00;
    //         var payed_money = 0.00;
    //         var pay_money = 0.00;
    //         var detailDate = grid.getSelectedRows();
    //         if (detailDate.length > 0) {
    //             $(detailDate).each(function() {
    //                 payable_money += this.payable_money;
    //                 if (!this.payed_money) {
    //                     this.payed_money = 0;
    //                     payed_money += 0;
    //                 } else {
    //                     payed_money += this.payed_money;
    //                 }
    //                 if (this.pay_money == '') {
    //                     this.pay_money = 0;
    //                     pay_money += 0;
    //                 } else {
    //                     pay_money += this.pay_money;
    //                 }
    //             });
    //         }
            
    //     } else {
    //         updateMoney();
    //     }
    // }

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
            var _payable_moneys = 0;

            grid.rows.forEach(function (item, index) {
                _payable_moneys += Number(item.bill_money);
            })
            data.payable_moneys = _payable_moneys;

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
        var total = e.record.payed_money + e.record.pay_money; //存储 明细表格 已付金额 与付款金额 的和
        if (Math.abs(total) > Math.abs(e.record.bill_money)) {
            $.ligerDialog.error('<span style="color: red">填写数据错误!该条数据的本次付款金额与已付金额之和与单据金额冲突.</span>(<span style="color:blue">请您重新填写本次付款金额</span>)');
            return;
        }

        // 增加判断，如果当前的detail grid有被选择的行， 那么按照选择的行计算。- update - wsj.2017.8.1
        var detailSelectRows = grid.getSelectedRows();
        if (detailSelectRows.length > 0) {
            detailSelectRows.forEach(function (item, index) {
                payed_money += item.payed_money;

                item.pay_money ? pay_money += item.pay_money : pay_money += 0;
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
                })
            }

            data.detail = { "Rows": detailDate, "Total": detailDate.length };
        }
        if (!gridManager.isSelected(data)) {
            gridManager.select(data);
        }
        gridManager.updateCell('payed_money', parseFloat(payed_money), data);
        gridManager.updateCell('pay_money', parseFloat(pay_money), data);
        
        updatePay_money();
    }

    // 勾选、取消勾选发票后  自动填写 更新的付款单的  金额
    function updatePay_money () {
        var payable_money = 0;
        var payed_money = 0;
        var pay_money = 0;
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
    }

    // 显示完数据 后  更新相应的 金额
    function updateMoney() {

        var dataMain = inMainGrid.getData();
        $.each(dataMain, function(index, content) {
            var mainPayed_money = 0.00; //存储 该条入库单 所有材料的  已付金额 总额
            var mainPay_money = 0.00; //存储 该条入库单 所有材料的  本次付款金额 总额
            var detail = content.detail;
            $(detail.Rows).each(function() {
                mainPayed_money += this.payed_money;
                mainPay_money += this.pay_money;
            })
            if (!content.payed_money) {
                gridManager.updateCell('payed_money', parseFloat(mainPayed_money), index);
            }
            gridManager.updateCell('pay_money', parseFloat(mainPay_money), index);
        })
    }

    /* 生成付款单号 */
    var curDate = new Date();
    year = curDate.getFullYear();
    month = curDate.getMonth();
    day = curDate.getDate();

    function setPayBillNo() {
        var pay_bill_type = $("#pay_bill_type").val();
        var parm = {
            year: year,
            month: month,
            day: day,
            pay_bill_type: pay_bill_type,
            pay_id :pay_id
        };

        ajaxJsonObjectByUrl("setPayBillNo.do?isCheck=false", parm, function(responseData) {
            $("#pay_bill_no").val(responseData.prefixe + responseData.year.substring(2) + responseData.month + responseData.day + responseData.max_no);
            //alert("11111:"+$("#pay_bill_no").val());
            if ($("form").valid()) {
                save();
            }
        })
    }
    //是否只显示期初发票
    function setInit() {
        if ($("#initOnly").prop("checked") == true) {
            is_init = "1";
        } else {
            is_init = "0";
        }
        query();
    }
    /*  //添加
     function add_open(){
         
         if( liger.get("sup_id").getValue().split(",")[0] == '' | liger.get("dept_id").getValue().split(",")[0] ==''){
             $.ligerDialog.error('请先选择供应商和科室后再添加发票');
         }else{
             $.ligerDialog.open({url: 'selectBillPage.do?isCheck=false',height: 500,width: 1000, 
                title:'选择发票',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true
                });
         }
        
     } */
    //删除
    function remove() {
        var data = inMainGrid.getCheckedRows();
        if (data.length == 0) {
            $.ligerDialog.error('选择要删除的行!');
            return;
        } else {
            $.ligerDialog.confirm('确定删除?', function(yes) {
                if (yes) {
                    var delData = [];
                    for (var i = 0; i < data.length; i++) {
                        inMainGrid.remove(data[i]);
                    }
                }
            });
        }
    }

    function save() {
      // alert("1212:"+$("#bill_id").val());
        var formPara = {
            pay_id: '${pay_id}',
            pay_bill_no: $("#pay_bill_no").val(),
            sup_id: liger.get("sup_id").getValue().split(",")[0],
            sup_no: liger.get("sup_id").getValue().split(",")[1],
            pay_date: $("#pay_date").val(),
            pay_bill_type: $("#pay_bill_type").val(),
            pay_code: liger.get("pay_code").getValue(),
            pay_type_code: liger.get("pay_type_code").getValue(),
            cheq_no: $("#cheq_no").val(),
            payable_money: $("#payable_money").val(),
            payed_money: $("#payed_money").val(),
            pay_money: $("#pay_money").val(),
            note: $("#note").val(),
            /* bank_name:$("#bank_name").val(),
                bank_no:$("#bank_no").val(), */
            pay_dept_id: liger.get("dept_code").getValue().split(",")[0],
            is_init: is_init,
            dis_money: $("#dis_money").val()
        };
        var data = gridManager.getSelectedRows();
        if (data.length == 0) {
            $.ligerDialog.error('请选择发票后再保存');
            return;
        } else {
            var ParamVo = [];
            var str = '';
            var error = '';
            if (JSON.stringify(targetMap.map) != "{}") {
                //alert(JSON.stringify(targetMap.map));
                $.each(data, function(index, content) {
                    var flag = false; // 判断该条数据  是否有明细数据 标志 （false：没有(整单保存)   true： 有（保存选择的明细）
                    if (content.pay_money == 0) {
                        str += content.bill_no + ",";
                    } else {
                        var key = targetMap.keySet();
                        for (var i = 0; i < key.length; i++) {
                            var data_detail = null;
                            if (content.__id == key[i].split("|")[0]) {
                                if (!flag) {
                                    flag = true;
                                }
                                data_detail = targetMap.get(key[i]);
                                $(data_detail).each(function() {
                                    /*      if(this.pay_money >0){ */
                                    if (Math.abs(this.pay_money) + Math.abs(this.payed_money) > Math.abs(this.bill_money)) {
                                        error += content.bill_no + ",";
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
                                        this.dis_money
                                    )
                                    /* } */

                                })
                            }
                        }
                        if (!flag) {
                            $.each(data, function(index, content) {
                                var data_detail = content.detail;
                                $(data_detail.Rows).each(function() {
                                    /* if(this.pay_money > 0){ */
                                    if (Math.abs(this.pay_money) + Math.abs(this.payed_money) > Math.abs(this.bill_money)) {
                                        error += content.bill_no + ",";
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
                                        this.dis_money
                                    )
                                    /* } */

                                });
                            });
                        }
                    }
                });
                
                if(str != ''){
            		$.ligerDialog.error(str+'保存失败！发票编号:<span style="color: red">' + str + '本次付款金额为0</span>,<span style="color: blue">不能保存.请您仔细核对数据</span>');
            	} else {
            		$.ligerDialog.confirm('<span style="color : red">您有采购发票单选择了明细记录,该采购发票单的发票明细将只保存您选择的明细.</span>确定要保存吗?', function (yes){
            		if(yes){
            			ajaxJsonObjectByUrl("queryMatMainlYesNo.do?isCheck=false",{ ParamVo: ParamVo.toString() }, function(responseData) {
                      	  if (responseData.state == "true") {
                      		  $.ligerDialog.confirm('确定要保存付款单及付款单明细信息吗?', function(yes) {
                                    if (yes) {
                                        ajaxJsonObjectByUrl("addMatPayMain.do?isCheck=false", formPara, function(responseData) {
                                            if (responseData.state == "true") {
                                          	   parentFrameUse().query(); 
                                          	var payMsg = responseData.update_para;
                                              ajaxJsonObjectByUrl("addMatPayDetail.do?isCheck=false", { ParamVo: ParamVo.toString(), pay_id: payMsg.split("|")[3], pay_bill_no: payMsg.split("|")[4]}, function(responseData) {
                                                    if (responseData.state == "true") {
                                                      parentFrameUse().openUpdate(payMsg);
                                                      //parentFrameUse().openUpdate(responseData.update_para);
                                                        this_close();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                      	  }
                      		 
                      	 });
            		}
            		
            		
            	});
                
                
                
            	}
                
            } else {

                $.each(data, function(index, content) {
                    if (content.pay_money == 0) {
                        str += content.bill_no + ",";
                    } else {
                        var data_detail = content.detail;
                        $(data_detail.Rows).each(function() {
                            //if(this.pay_money > 0 ){
                            if (Math.abs(this.pay_money) + Math.abs(this.payed_money) > Math.abs(this.bill_money)) {
                                error += content.bill_no + ",";
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
                                this.dis_money
                            )
                            //}
                        })
                    }
                });
                if (str != '') {
                    $.ligerDialog.error('保存失败！发票编号:<span style="color: red">' + str + '本次付款金额为0</span>,<span style="color: blue">不能保存.请您仔细核对数据</span>');
                    return;
                } else {
                    if (error != '') {
                        $.ligerDialog.error('保存失败！发票编号:<span style="color: red">' + error + '有明细数据的本次付款金额数据错误</span>,<span style="color: blue">不能保存.请您仔细核对数据</span>');
                    } else {
                      ajaxJsonObjectByUrl("queryMatMainlYesNo.do?isCheck=false",{ ParamVo: ParamVo.toString() }, function(responseData) {
                    	  if (responseData.state == "true") {
                    		  $.ligerDialog.confirm('确定要保存付款单及付款单明细信息吗?', function(yes) {
                                  if (yes) {
                                      ajaxJsonObjectByUrl("addMatPayMain.do?isCheck=false", formPara, function(responseData) {
                                          if (responseData.state == "true") {
                                        	   parentFrameUse().query(); 
                                        	var payMsg = responseData.update_para;
                                            ajaxJsonObjectByUrl("addMatPayDetail.do?isCheck=false", { ParamVo: ParamVo.toString(), pay_id: payMsg.split("|")[3], pay_bill_no: payMsg.split("|")[4]}, function(responseData) {
                                                  if (responseData.state == "true") {
                                                    parentFrameUse().openUpdate(payMsg);
                                                    //parentFrameUse().openUpdate(responseData.update_para);
                                                      this_close();
                                                  }
                                              });
                                          };
                                      });
                                  }
                              });
                    	  }
                    		 
                    	 }); 
                        
                    }
                }
            }

        }

    }

    function this_close() {
        frameElement.dialog.close();
    }

    function loadHotkeys() {

        hotkeys('S', saveMatBillMain);
        hotkeys('Q', query);
        //hotkeys('A', add_open);
        //hotkeys('D', remove);
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

    function saveMatBillMain() {
        setPayBillNo();
    }

    function loadDict() {
        //字典下拉框
        //供应商下拉框
        autocomplete("#sup_id", "../../queryHosSupDictMethod.do?isCheck=false", "id", "text", true, true, '', false, '', 240);
        //科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
        autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true, '', false, '', 200);
        //采购员下拉框
        autocomplete("#stocker", "../../queryMatStoctEmpDict.do?isCheck=false", "id", "text", true, true, '', false, '', 200);
        //付款条件 
        autocomplete("#pay_code", "../../queryMatPayTerm.do?isCheck=false", "id", "text", true, true, '', false, '', 200);
        //付款方式下拉框
        autocomplete("#pay_type_code", "../../queryMatPayType.do?isCheck=false", "id", "text", true, true, '', true, "003", 240);
        //付款部门下拉框
/*         autocomplete("#dept_code", "../../queryHosDeptDict.do?isCheck=false", "id", "text", true, true, { is_last: '1' }, false, '', 200); */
        autocomplete("#dept_code", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, { is_last: '1',is_write:1 }, false, '', 200);
        //采购员下拉框
        autocomplete("#maker", "../../querySysUser.do?isCheck=false", "id", "text", true, true, '', false, '', 205);

/*         autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true, '', false, '', 200);
 *//*         autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true, '', false, '', 200); */
        autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_write:1}, true, '', 200);

        autodate("#pay_date", 'yyyy-MM-dd')
        $("#pay_bill_no").ligerTextBox({ width: 200, disabled: true });
        $("#pay_date").ligerTextBox({ width: 200 });
        $("#pay_bill_type").ligerTextBox({ width: 200 });
        $("#sup_id").ligerTextBox({ width: 240 });
        $("#pay_code").ligerTextBox({ width: 200 });
        $("#pay_type_code").ligerTextBox({ width: 240 });
        $("#cheq_no").ligerTextBox({ width: 240 });
        $("#note").ligerTextBox({ width: 607 });
        $("#count").ligerTextBox({ width: 200 });
        $("#payable_money").ligerTextBox({ width: 200, disabled: true });
        $("#pay_money").ligerTextBox({ width: 200, disabled: true });
        $("#payed_money").ligerTextBox({ width: 240, disabled: true });
        /* $("#bank_no").ligerTextBox({width:200});
        $("#bank_name").ligerTextBox({width:240}); */
        //autodate("#beginDate", "yyyy-mm-dd", "month_first");
        //autodate("#endDate", "yyyy-mm-dd", "month_last");
        $("#beginDate").ligerTextBox({ width: 100 });
        $("#endDate").ligerTextBox({ width: 100 });
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
                            <input name="pay_bill_no" type="text" id="pay_bill_no" disabled="disabled" value="自动生成" ltype="text" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>供应单位<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>制单日期<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input class="Wdate" name="pay_date" type="text" id="pay_date" ltype="text" style="width: 200px;" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>单据类别<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <select name="pay_bill_type" id="pay_bill_type" style="width: 135px;" onChange="query()">
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
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款部门:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="dept_code" type="text" id="dept_code" ltype="text" validate="{maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款条件:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="pay_code" type="text" id="pay_code" ltype="text" validate="{maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>票据号:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="cheq_no" type="text" id="cheq_no" ltype="text" validate="{maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>发票张数:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="count" type="text" id="count" ltype="text" validate="{maxlength:100}" />
                        </td>
                        <td align="left"></td>
                        <!-- 
                        2017-03-06 银行信息取供应商默认的银行账号信息
                        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开户银行:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="bank_name" type="text" id="bank_name" ltype="text" validate="{maxlength:100}" />
                        </td>
                        <td align="left"></td>
                        
                        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>银行账号:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="bank_no" type="text" id="bank_no" ltype="text" validate="{maxlength:100}" />
                        </td>
                        <td align="left"></td> -->
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>应付合计<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="payable_money" type="text" id="payable_money" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>已付金额<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="payed_money" type="text" id="payed_money" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>付款金额<font color="red">*</font>:</b></td>
                        <td align="left" class="l-table-edit-td">
                            <input name="pay_money" type="text" id="pay_money" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b>摘要:</b></td>
                        <td align="left" class="l-table-edit-td" colspan="4">
                            <input name="note" type="text" id="note" ltype="text" validate="{maxlength:100}" />
                        </td>
                        <td align="left"></td>
                     <!--    <td align="right" class="l-table-edit-td" style="padding-left:20px;">虚&nbsp;&nbsp;仓:</b>
                        </td>
                        <td align="left" class="l-table-edit-td">
                            <input name="set_code" type="text" id="set_code" ltype="text" />
                        </td>
                        <td align="left"></td> -->
                            <td align="right" class="l-table-edit-td" style="padding-left:20px;">仓&nbsp;&nbsp;库:</b>
                        </td>
                        <td align="left" class="l-table-edit-td">
                            <input name="store_code" type="text" id="store_code" ltype="text" />
                        </td>
                        <td align="left"></td>
                    </tr>
                    <tr>
                        <td align="right" class="l-table-edit-td" width="10%">入库确认日期：</td>
                        <td align="left" class="l-table-edit-td" width="20%">
                            <table>
                                <tr>
                                    <td align="left">
                                        <input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
                                    </td>
                                    <td align="right" class="l-table-edit-td">
                                        至：
                                    </td>
                                    <td align="left" class="l-table-edit-td">
                                        <input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td align="left"></td>
                        <td align="right" class="l-table-edit-td">制单人:</td>
                        <td align="left" class="l-table-edit-td">
                            <input name="maker" type="text" id="maker" ltype="text" validate="{maxlength:20}" />
                        </td>
                        <td align="left"></td>
                    
                    </tr>
                </table>
            </form>
        </div>
        <div title="" class="l-layout-content" style="" position="center">
            <div id="maingrid"></div>
        </div>
    </div>
</body>

</html>