<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,etValidate" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var year, month, dept_id, mat_type_id;

    $(function() {
        init();

        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#year"), required: true },
                { el: $("#month"), required: true },
                { el: $("#dept_id"), required: true },
                { el: $("#mat_type_id"), required: true }
            ]
        });
    });

    function save () {
        var formPara = {
            year: year.getValue(),
            month: month.getValue(),
            dept_id: dept_id.getValue(),
            mat_type_id: mat_type_id.getValue().split(",")[0],
            cost_budg: $("#cost_budg").val(),
            remark: $("#remark").val()
        };

        formPara.cost_budg = formPara.cost_budg || 0;

        ajaxPostData({
            url: "addBudgChargeMat.do",
            data: formPara,
            success: function(responseData) {
                year.clear('');
                month.clear('');
                dept_id.clearItem('');
                mat_type_id.clearItem('');
                $("#cost_budg").val("");
                $("#remark").val("");

                parent.query();
            }
        });
    }

    function saveBudgChargeMat() {
        if (formValidate.test()) {
            save();
        }
    }

    function init () {
        ajaxPostData({
            url: '../../../queryBudgYear.do?isCheck=false',
            success: function (result) {
                year = $("#year").etDatepicker({
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: result[result.length - 1].text,
                    maxDate: result[0].text,
                    onChanged: function (value){
                    	reloadBudgMatType(value);
                    }
                });
            }
        })
        month = $("#month").etDatepicker({
            view: "months",
            minView: "months",
            showNav: false,
            dateFormat: "mm"
        });
        dept_id = $("#dept_id").etSelect({
            url: "../../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none"
        });
        
        mat_type_id = $("#mat_type_id").etSelect({
            defaultValue: "none",
        })
		function reloadBudgMatType(year){
        	mat_type_id.reload({
				url: "../../../queryBudgMatTypeSubj.do?isCheck=false",
				para:{
					budg_year:year
				}
			})
		}
    }
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">月：</td>
                <td class="ipt">
                    <input id="month" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">部门：</td>
                <td class="ipt">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">物资类别：</td>
                <td class="ipt">
                    <select id="mat_type_id" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">支出预算：</td>
                <td class="ipt">
                    <input id="cost_budg" type="text" />
                </td>
            </tr><tr>
                <td class="label">说明：</td>
                <td class="ipt">
                    <input id="remark" type="text" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>