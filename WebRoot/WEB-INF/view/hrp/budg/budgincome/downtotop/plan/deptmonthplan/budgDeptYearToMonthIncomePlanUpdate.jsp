<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    var num = 0 ;
    $(function (){
        loadDict();
        loadForm();
$("#resolve_way").change(function(){
        	
	    	if(num > 0 ){
	    		$("#reference_years").val("") ;
	    		liger.get("resolve_data").setValue("");
	    	}
        	
        	if(liger.get("resolve_way").getValue() =='01'){
        		
				$("#reference_years").ligerTextBox({disabled:false,cancelable:true});
            	
            	$("#reference_years").removeClass("notValid");
            	
 				$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:true});
				 
				$("#resolve_data").addClass("notValid");
        		
        	}else if(liger.get("resolve_way").getValue() =='05'){
        		
        		$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:false});
				 
				$("#resolve_data").removeClass("notValid");
				
				$("#reference_years").ligerTextBox({disabled:true,cancelable:false});
            	
            	$("#reference_years").addClass("notValid");
        		
        	}else{
        		
        		$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:true});
				 
				$("#resolve_data").addClass("notValid");
				
				$("#reference_years").ligerTextBox({disabled:true,cancelable:false});
            	
            	$("#reference_years").addClass("notValid");
        	}
       		num = num + 1 ;
		})  
    });  
  //选择自定义分解系数页面 
   	function selectResolve(){
   		
   		parent.$.ligerDialog.open({
  			url : '../../../../common/budgresolvedatadict/budgResolveDataPage.do?isCheck=false&budg_level=04',
  				height : 500,width : 750,title : '自定义分解系数',
  			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
  			parentframename:window.name,  //用于parent弹出层调用本页面的方法或变量
  		});
   		
   	}
    function save(){
    	 var formPara={
    		budg_year:$("#budg_year").val(),
	        subj_code:liger.get("subj_code").getValue().split(",")[0],
	        resolve_way:liger.get("resolve_way").getValue(),
	        reference_years : $("#reference_years").val(),
			resolve_data : liger.get("resolve_data").getValue()
        };
        ajaxJsonObjectByUrl("updateBudgDeptYearToMonthIncomePlan.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
           //提示信息  显示2秒后消失
	           setTimeout(function(){
		          	  lable.ligerHideTip();
		                lable.remove();
		           },2000)
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         },
	         ignore: ".notValid"
     });
     $("form").ligerForm();
    }       
   
    function saveBudgDeptYearToMonthIncomePlan(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	 //字典下拉框
       // $("#budg_year").val("${budg_year}") ;
    	autocomplete("#subj_code","../../../../queryBudgSubj.do?isCheck=false&budg_year="+"${budg_year}&subj_type=04&type_code=01&is_last=1","id","text",true,true,'',false,'${subj_code}',180);
    	//分解方法 下拉框
        autocomplete("#resolve_way","../../../../queryBudgResolveWay.do?isCheck=false","id","text",true,true,'',false,'${resolve_way}',180);
      //自定义分解系数 下拉框
        autocomplete("#resolve_data","../../../../queryBudgResolveDataDict.do?isCheck=false&budg_level=04","id","text",true,true,'',false,'${resolve_data}',180);
        $("#budg_year").ligerTextBox({ width:180,disabled:true,cancelable:false});
    	$("#subj_code").ligerTextBox({ width:180,disabled:true,cancelable:false });
		
    	$("#resolve_data").ligerTextBox({width:180,disabled:true});
    	
    	$("#reference_years").ligerTextBox({width:180})
    	
    	if(!"${reference_years}"){
    		
    		$("#reference_years").ligerTextBox({disabled:true});
    		
    		$("#reference_years").val("")
    		
    	}else{
    		$("#reference_years").ligerTextBox({disabled:false});
    		
    		$("#reference_years").val("${reference_years}") ;
    	}
    	
    	if(!"${resolve_data}"){
    		
    		 $("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:true});
    		 
    		 $("#resolve_data").addClass("notValid");
    		 
    	}else{
    		
    		$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:false});
    		
    		$("#resolve_data").removeClass("notValid");
    	}
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
	   <form name="form1" method="post"  id="form1" >
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
		          <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text"  validate="{required:true,maxlength:20}" value="${budg_year}" /></td>
		            <td align="left"></td>
		        </tr> 
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码：</td>
		            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" disabled="disabled" ltype="text"  validate="{required:true,maxlength:200}" /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分解方法：</td>
		            <td align="left" class="l-table-edit-td"><input name="resolve_way" type="text" id="resolve_way" ltype="text"  validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		        </tr> 
		        </tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">自定义分解系数：</td>
		            <td align="left" class="l-table-edit-td"><input name="resolve_data" type="text" id="resolve_data" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="center" class="l-table-edit-td" colspan="3">
							<button id ="selectResolve" ><b>分解系数</b></button>
					</td>
	        	</tr> 
	         	</tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参考年限：</td>
		            <td align="left" class="l-table-edit-td"><input name="reference_years" type="text" id="reference_years" ltype="text" validate="{required:true,digits:true,range:[1,3],maxlength:4}" /></td>
		            <td align="left"></td>
	        	</tr>  
				
	        </table>
	    </form>
    </body>
</html>
