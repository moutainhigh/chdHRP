<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>hrTableTypeUpdate</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,validate,dialog,grid" name="plugins" />
        </jsp:include>
        <script>
            var validate;
            var is_change=0;
            $(function () {
                //loadDict();
                loadForm();
            })

            function loadDict() {
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#type_tab_code"),
                            required: true
                        },
                        {
                            el: $("#type_tab_name"),
                            required: true
                        }
                    ]
                })
            }

            function saveData() {
                if (validate.test()) {
                    save();
                }
            }

            function save() {
            	//var formData = $('form').serializeArray();
            	
            	if($("#type_tab_name").val() != '${type_tab_name}'){
                	is_change = 1;
                }else{
                	is_change = 0;
                }
            	
            	var formData = {
            		type_tab_code: $("#type_tab_code").val(),
            		type_tab_name: $("#type_tab_name").val(),
            		table_sort: $("#table_sort").val(),
                    table_note: $("#table_note").val(),
                    is_change : is_change
                };
            	
            	console.log(formData);
            	ajaxPostData({
                    url: 'updateHrTableType.do',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                    }
                })
            }
          //封装一个限制字数方法
            var checkStrLengths = function (str, maxLength) {
                var maxLength = maxLength;
                var result = 0;
                if (str && str.length > maxLength) {
                    result = maxLength;
                } else {
                    result = str.length;
                }
                return result;
            }

            //监听输入
            $(".wishContent").on('input propertychange', function () {

                //获取输入内容
                var userDesc = $(this).val();

                //判断字数
                var len;
                if (userDesc) {
                    len = checkStrLengths(userDesc, 100);
                } else {
                    len = 0
                }

                //显示字数
                $(".wordsNum").html(len + '/100');
            });
        </script>
    </head>

    <body>
        <div class="main">
        	<form>
	            <table class="table-layout">
	                <tr>
	                    <td class="label no-empty">类型代码：</td>
	                    <td class="ipt">
	                        <input type="text" name="type_tab_code" id="type_tab_code" disabled="disabled" value="${type_tab_code}" style="width:180px;"/>
	                    </td>
	                    <td class="label no-empty">类型名称：</td>
	                    <td class="ipt">
	                        <input type="text" name="type_tab_name" id="type_tab_name" value="${type_tab_name}" style="width:180px;"/>
	                    </td>
	                </tr>
	                <tr>
	                	<td class="label no-empty">排序号：</td>
	                    <td class="ipt">
	                        <input type="text" name="table_sort" id="table_sort" style="width:180px;" value="${table_sort}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label">备注：</td>
	                    <td class="ipt" colspan="3">
	                        <textarea name="table_note" id="table_note" style='width:488px' class="wishContent" placeholder="请输入不超过100个字" maxlength="100">${table_note}</textarea>
	                    </td>
	                </tr>
	            </table>
            </form>
        </div>
    </body>

    </html>