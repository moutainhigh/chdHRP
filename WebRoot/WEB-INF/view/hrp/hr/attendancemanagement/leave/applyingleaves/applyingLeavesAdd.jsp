<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,select,validate,grid,upload,datepicker,dialog" name="plugins" />
    </jsp:include>
<script>
	var attend_code, emp_id, sex, dept_name, duty, photo_file;;
	var application_date, birth_date, workage, attend_xjbegdate, attend_xjenddate,attend_xjdays,startDateTime,endDateTime,validTime;
    var photo;
    var formValidate;
    var user_hide_data = {};

    var initValidate = function () {
        formValidate = $.etValidate({
        	config: {},
        	items: [
				{ el: $("#emp_id"), required: true },
				{ el: $("#attend_year"), required: true },
				{ el: $("#attend_code"), required: true },
            ]
        });
    };
        
	var initForm = function () {
	    attend_xjreg_date = $("#attend_xjreg_date").etDatepicker({
	        defaultDate: true,
	    });
	    /* 休假年份 */
        var  year = $("#attend_year").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton:false,
            defaultDate: true,
            onChange: function(){
            	if(emp_id.getValue() == ""){
            		$.etDialog.warn('请选择职工名称!');
            	}else{
            		changeVacationType()
            	}
            }
        });
        
       	/* 职工名称 */
      	emp_id = $("#emp_id").etSelect({
			url : "../../queryAppEmpSelect.do?isCheck=false",
   			defaultValue: "none",
            onChange: function (value) {
               	query();
                ajaxPostData({
					url: '../../queryPersonnel.do?isCheck=false',
                    data: {
                        emp_id: value
                    },

					success: function (res) {
						user_hide_data = {
							emp_name: res.emp_name || '',
		                    emp_id: res.emp_id || '',
		                    dept_name: res.dept_name || '',
		                    duty_name: res.duty_name || '',
		                    sex_name: res.sex_name || '',
		                    birthday: res.birthday || '',
		                    sex_name: res.sex_name || '',
		                    worktime: res.worktime || '',
		                    workage: res.workage || '',
							worktime: res.worktime || '',
                   		};
						
	                    $("#emp_name").val(res[0].emp_name || '');
	                    $("#dept_name").val(res[0].dept_name || '');
	                    $("#sex_name").val(res[0].sex_name || '');
	                    $("#birthday").val(res[0].birthday || '');
	                    $("#workage").val(res[0].workage || '');
	                    $("#sex_name").val(res[0].sex_name || '');
	                    $("#duty_name").val(res[0].duty_name || '');
	                    $("#worktime").val(res[0].worktime || '');
	                    photo_file.setValue(res[0].photo);
	                    $("#birthplace").val(res[0].birthplace || '');
					}
    		 	});
                
                if(year.getValue() == ""){
            		$.etDialog.warn('请选择休假年份!');
            	}else{
            		changeVacationType()
            	}
		 	}
		});
      	
	    function query() {
			var param = [ 
	           	{ name: 'emp_id', value: emp_id.getValue()},
			];
			grid.loadData(param,'queryHistroy.do?isCheck=false');
		};
		
		/* 改变休假类型 */
		function changeVacationType(){
			$("#attend_ed").val("");// 休假总额
            $("#residue_days").val("");// 休假余额
            $("#xjdays").val("");// 已休天数
            
            //获取是否限额控制
            var attend_ed_is = attend_code.getOptions()[attend_code.getValue()].attend_ed_is;
            
            ajaxPostData({
            	url: 'queryAttendSum.do?isCheck=false',
                data: {
                	attend_code: attend_code.getValue(),
                	emp_id :emp_id.getValue(),
                	attend_year: year.getValue(),
                },
	   		    success: function (res) {
	                user_hide_data = {
	               		attend_ed: res.attend_ed || '',
	               		residue_days: res.residue_days || '',
	               		attend_ed_is:res.attend_ed_is || '',
	               		emp_id:res.EMP_ID|| ''
	                };
	                
    	            $("#xjdays").val(res.xjdays);  
    	            $("#residue_days").val(res.residue_days );
	                //只有限额控制的休假类型才显示总额和余额
	                if(attend_ed_is){
						$("#attend_ed").val(res.attend_ed );
	                    $("#residue_days").val(res.residue_days );
	                    
	                    var residueDays = user_hide_data.residue_days;
                    	if(parseFloat(residueDays) < parseFloat($("#attend_xjdays").val())){
                    		$.etDialog.warn("假期余额不足!");
                        	return;
                    	}
	                }
               }    
			});
		};
	
		/* 休假类型 */
	   	attend_code = $("#attend_code").etSelect({
		    url: "../../queryAllAttendCode.do?isCheck=false",
		    defaultValue: "none",
			onChange: function (value) {
		       	if(emp_id.getValue()== '' || year.getValue()==''){
		       		$.etDialog.warn('请选择职工名称、休假年份!');
		        	return;
		        };
		        
		        changeVacationType();
	       	}
	   	});
        	 
	    application_date = $("#application_date").etDatepicker();
	    birth_date = $("#birth_date").etDatepicker();
	    workage = $("#workage").etDatepicker();
	    
	    
		/* 计算时间 */
		var computTime = function(){
			ajaxPostData({
				url: 'countXJDays.do?isCheck=false',
				data : {
					beginDate : attend_xjbegdate.getValue(),
            		endDate : attend_xjenddate.getValue(),
            		beginTime : startDateTime.getValue(),
            		endTime : endDateTime.getValue(),
            		attend_code:attend_code.getValue(),
            		emp_id :emp_id.getValue()
				},
           		success:function(data){
           			//总休假天数
					var daysRemain = parseFloat(data.days);
					//休假余额
					var residueDays = parseFloat($("#residue_days").val());
					var attend_ed_is = user_hide_data.attend_ed_is;
					if(daysRemain > residueDays /* && attend_ed_is == 1 */){
						$.etDialog.warn("假期余额不足!");
						$("#attend_xjdays").val('');
					}else{
						$("#attend_xjdays").val(daysRemain);
					}
         		}
			});
		};
		
		//检验时间是否合法
		function isValidDateTime(beginDate, endDate, beginTime, endTime) {
			var bDateTime = Date.parse(beginDate);
			var eDateTime = Date.parse(endDate);
			if(eDateTime < bDateTime) {
				$.etDialog.warn("开始时间必须小于结束时间");
				$("#attend_xjdays").val('')
				return false;	
			}else if(eDateTime == bDateTime){
				if(beginTime == '0'){
					return true;
				}else{
					if(endTime == '0'){
						$.etDialog.warn("开始时间必须小于结束时间");
						$("#attend_xjdays").val()
						return false;
					}else{
						return true;
					}
				}
			}else {
				return true;
			}
		};		
		
	            
		//开始时间
	    attend_xjbegdate = $("#attend_xjbegdate").etDatepicker({
	    	/* onShow: function(){
	    		if(attend_code.getValue()== ""){
   					$.etDialog.warn("请选择休假类型");
	    		}
	    	}, */
	    	onChange: function(){
	    		if( attend_xjenddate.getValue() !=""){
	    			validTime = isValidDateTime(attend_xjbegdate.getValue(), attend_xjenddate.getValue(), startDateTime.getValue(), endDateTime.getValue());
	 			};
	 			if(validTime && attend_xjbegdate.getValue() !="" && attend_xjenddate.getValue() !="" && startDateTime.getValue() !="" && endDateTime.getValue() !=""){
	 				computTime();
	 			};
	    	}
	    });
	
		//结束时间
	    attend_xjenddate = $("#attend_xjenddate").etDatepicker({
	 		onChange: function (date) {
	 			if( attend_xjbegdate.getValue() !=""){
	 				validTime = isValidDateTime(attend_xjbegdate.getValue(), attend_xjenddate.getValue(), startDateTime.getValue(), endDateTime.getValue());
	 			};
	 			if(validTime && attend_xjbegdate.getValue() !="" && attend_xjenddate.getValue() !="" && startDateTime.getValue() !="" && endDateTime.getValue() !=""){
	 				computTime();
	 			};
	 		}
	    });
    
		//开始时间上下午
	  	startDateTime = $("#attend_startDate").etSelect({
	  		showClear: false,
			options: [
				{ id: '0', text: '上午' },
	            { id: '1', text: '下午' },
	        ],
			defaultValue: "none",
			onChange: function(){
				if( attend_xjenddate.getValue() !="" && attend_xjbegdate.getValue() !="" && endDateTime.getValue() !=""){
					validTime = isValidDateTime(attend_xjbegdate.getValue(), attend_xjenddate.getValue(), startDateTime.getValue(), endDateTime.getValue());
	 			};
	 			if(validTime && attend_xjbegdate.getValue() !="" && attend_xjenddate.getValue() !="" && startDateTime.getValue() !="" && endDateTime.getValue() !=""){
	 				computTime();
	 			};
	    	}
	    });
   	
		//结束时间上下午
	   	endDateTime = $("#attend_endDate").etSelect({
	   		showClear: false,
		    options: [
		        { id: '0', text: '上午' },
		        { id: '1', text: '下午' },
		    ],
	        defaultValue: "none",
			onChange:function(){
				if( attend_xjenddate.getValue() !="" && attend_xjbegdate.getValue() !="" && startDateTime.getValue() !=""){
					validTime = isValidDateTime(attend_xjbegdate.getValue(), attend_xjenddate.getValue(), startDateTime.getValue(), endDateTime.getValue());
	 			};
	 			if(validTime && attend_xjbegdate.getValue() !="" && attend_xjenddate.getValue() !="" && startDateTime.getValue() !="" && endDateTime.getValue() !=""){
	 				computTime();
	 			};	
			}  
		});
	
    //  attend_xjdays=$("#attend_xjdays").val(attend_xjenddate-attend_xjbegdate);
      photo_file  = $("#photo").etUpload();

	$("#save").click(function () {
		if (!formValidate.test()) {
			return;
		};
		if($("#attend_xjdays").val() == ""){
	       	 $.etDialog.warn("请重新选择开始时间或结束时间!");
	       	 return;
       	};
       
		if(parseFloat($("#residue_days").val()) < parseFloat($("#attend_xjdays").val())){
         	 $.etDialog.warn("假期余额不足!");
         	 return;
        };
        
      	if(!validTime){
      		$.etDialog.warn("开始时间必须小于结束时间");
      	}else{
      		ajaxPostData({
            	  url: 'addApplyingLeaves.do',
                  data: {
                  	attend_code : attend_code.getValue(),
          			attend_xjreg_date : attend_xjreg_date.getValue(),
         	 		emp_id : emp_id.getValue(),
         	 		attend_year : year.getValue(),
         	 		attend_xjbegdate : attend_xjbegdate.getValue(),
         	 		attend_xjbegdate_ampm : startDateTime.getValue(),
         	 		attend_xjenddate : attend_xjenddate.getValue(),
         	 		attend_xjenddate_ampm : endDateTime.getValue(),
     	 			attend_xjdays : $("#attend_xjdays").val(),
     	 			attend_xj_reason : $("#attend_xj_reason").val(),
     	 			attend_xj_add : $("#attend_xj_add").val()
                  },
                  success: function (responseData) {
                    	$.etDialog.success(
  							'添加成功',
  							 function (index, el) {
  								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
  		                            
  		                            var parentFrameName = parent.$.etDialog.parentFrameName;
  		                            var parentWindow = parent.window[parentFrameName];
  		                            parentWindow.query(); 
  		                            parent.$.etDialog.close(curIndex);
  							    }
  							)
                    }, 
                  //delayCallback: true
      		}) 
      	}
    });
    
    $("#close").click(function () {
         var curIndex = parent.$.etDialog.getFrameIndex(window.name);
         parent.$.etDialog.close(curIndex);
   })
};

     
      

	var initGrid = function () {
    	var columns = [
        	{ display: '登记流水号', name: 'attend_xjapply_code', width: 140 },
        	{ display: '登记日期', name: 'attend_xjreg_date', width: 140 },
        	{ display: '休假项目', name: 'attend_name', width: 140 },
        	{ display: '休假天数', name: 'attend_xjdays', width: 140 },
        	{ display: '休假开始日期', name: 'attend_xjbegdate', width: 140 },
        	{ display: '休假结束日期', name: 'attend_xjenddate', width: 140 }
    	];
      	var paramObj = {
        	height: '100%',
			inWindowHeight: true,           
        	title: '历史休假记录',
        	showTitle: true,
       		dataModel: {
             //	 url: 'queryHistroy.do?isCheck=false'
          	},
          	columns: columns
      	};
      	grid = $("#mainGrid").etGrid(paramObj);
	}

    $(function () {
        initValidate();
        initForm();
        initGrid();
    })
</script>
</head>

<body style="overflow:hidden;">
    <div class="main flex-wrap">
        <table class="flex-item-1 table-layout">
            <tr>
        		<input id="attend_xj_add" type="hidden" value="${attend_xj_add }"/>
                <td class="label">申请编号：</td>
                <td class="ipt">
                    <input value="自动生成" type="text" disabled />
                        
                </td>

                <td class="label">登记日期：</td>
                <td class="ipt">
                    <input id="attend_xjreg_date" type="text" disabled>
                </td>
                  <td class="label"><font size="2" color="red">*</font>职工名称：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
               <td class="label">部门名称：</td>
                <td class="ipt">
                    <input id="dept_name" type="text" disabled/>
                </td>

                <td class="label">职务：</td>
                <td class="ipt">
                    <input id="duty_name" type="text" disabled />
                </td>
                 <td class="label" style="width:90px;">参加工作年月：</td>
                <td class="ipt">
                    <input id="worktime" type="text" disabled/>
                </td>
 				<!--  <td class="label">出生年月：</td>
                <td class="ipt">
                    <input id="birthday" type="text" disabled />
                </td> -->
              
            </tr>
            <tr>
               <!--  <td class="label">性别：</td>
                <td class="ipt">
                  <input id="sex_name" type="text" disabled />
                </td> -->

              

               

                <td class="label">工龄：</td>
                <td class="ipt">
                    <input id="workage" type="text" disabled />
                </td>
            <!-- </tr>
            <tr> -->
                <!--  <td class="label">籍贯：</td>
                <td class="ipt">
                    <input id="birthplace" type="text" disabled />
                </td> -->
                <td class="label"><font size="2" color="red">*</font>休假年份：</td>
                  <td class="ipt">
                    <input id="attend_year" type="text"/>
                </td>
             	<td class="label"><font size="2" color="red">*</font>休假类型：</td>
                  <td class="ipt">
                    <select id="attend_code" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
             <td class="label">休假总额：</td>
                <td class="ipt">
                    <input id="attend_ed"  type="text" disabled>
                </td>
                    <td class="label">已休天数：</td>
                <td class="ipt">
                    <input id="xjdays"  type="text" disabled>
                </td>
                    <td class="label">休假余额：</td>
                <td class="ipt">
                    <input id="residue_days" style="color: red;" type="text" disabled>
                </td>
            </tr>
            <tr>
                <td class="label">开始时间：</td>
                <td class="ipt">
                    <input id="attend_xjbegdate" type="text" style="width:107px;">
                    <select id="attend_startDate" style="width:70px;"></select>
                </td>
                <td class="label">结束时间：</td>
                <td class="ipt">
                    <input id="attend_xjenddate" type="text" style="width:107px;">
                    <select id="attend_endDate" style="width:70px;"></select>
                </td>
                <td class="label">休假天数：</td>
                <td class="ipt">
                    <input id="attend_xjdays" type="text" disabled>
                </td>
            </tr>
            <tr>
                <td class="label">休假原因：</td>
                <td class="ipt" colspan="3">
                    <input id="attend_xj_reason" style="width: 93%;" type="text">
                </td>
            </tr>
        </table>
        <div class="upload-photo-form">
                    <div id="photo"></div>
                    <span>照片</span>
                </div>

    </div>
    <div class="button-group">
        <button id="save">保存</button>
        <button id="close">关闭</button>
    </div>
    <div id="mainGrid"></div>
</body>

</html>