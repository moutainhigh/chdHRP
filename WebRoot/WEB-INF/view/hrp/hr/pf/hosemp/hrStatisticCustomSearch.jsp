<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>条件查询</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, datepicker, checkbox, grid, hr,form, validate" name="plugins" />
        </jsp:include>
       
        <script>
        	var main_form,child_form, validate,validates;
        	
            $(function () {
                init();
            });

            function init() {
                initForm();
            }

            function initForm() {
            	
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
				                  
				var parentFrameName = parent.$.etDialog.parentFrameName;
				
				var parentWindow = parent.parent.window[parentFrameName];
				
            	var arr = new Array();
            	
            	var data = new Object(parentWindow.searchParam);
            	 
            	Object.keys(data).forEach(function(key){
            		 
            		 arr.push({name: key,value: data[key]})

            	}); 
            	
            	var mainformEle = ${formEle};
            
            	var option=mainformEle.fieldItems;
            	
            	 for (var i=0;i<arr.length;i++){
            		
            		var fieldItems;
            		
            		var name=arr[i].name.slice(7);
            		
            		for(var j=0;j<option.length;j++){
            			
            			var optionName=option[j].id;
            			
            			if(name==optionName){option[j].OPTIONS.defaultValue=arr[i].value;}
            		}
            		
            	}
            	 
            	main_form = $('#main').etForm(mainformEle);
                
            	main_form.initWidget();

                validate = main_form.initValidate();
                
            }
            
           

            function query() {
            	
                var formData = convert_FormData_to_json(main_form.getFormData());
           
            	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                  
				var parentFrameName = parent.$.etDialog.parentFrameName;
				
				var parentWindow = parent.parent.window[parentFrameName];
				
				//parentWindow.searchParam = formData;
				
				parentWindow.reloadMainGrid(formData); 
				
            }
            
            var convert_FormData_to_json = function (formData) {
            	
                var objData = {};
                
                for (var entry of formData.entries()){
                	
                	if(entry[1] != ''){objData["SEARCH_"+entry[0]] = entry[1];}
                    
                }
                
                return objData;
            };
            
            
     /*        function getFormData(form){
            	      var data = form.serialize();
            	      data = decodeURI(data);
            	      var arr = data.split('&');
            	      var item,key,value,newData={};
            	      for(var i=0;i<arr.length;i++){
            	          item = arr[i].split('=');
            	         key = item[0];
            	          value = item[1];
            	          if(key.indexOf('[]')!=-1){
            	          key = key.replace('[]','');
            	          if(!newData[key]){
            	          newData[key] = [];
            	          }
            	          newData[key].push(value);
            	          }else{
            	          newData[key] = value;
            	          }
            	     }
            	      return newData;
            	} */
            	
            	
        </script>
    </head>

    <body style="overflow: auto">
        <form id='main' class="main" >
        </form>
   <!--     
   <form id='child' class="main" >
          <hr style="margin-top:-5px;border:1px solid #d6e4f4;">
        </form> -->
    </body>
    </html>