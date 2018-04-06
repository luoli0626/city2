function graphTraceWords(options) {
	
  var _defaults = {
      srcEle: this,
      pid: options
  };
  var opts = $.extend(true, _defaults);
  
  var jsonUrl=ctx+'/workFlowProcessController.ftl?processTraceWords&pid=' + opts.pid+"&timetemp="+new Date();
  
  $.getJSON(jsonUrl, function(infos) {
  	
      var startHtml = "<table border='0' cellpadding='0' cellspacing='0' width='100%'><thead><tr>";
      var endHtml ="</table>";
      var tbodyHtml="";
      // 生成图片
      var varsArray = new Array();
      
      $.each(infos, function(i, v) {
    	  
    	 var  background="";
    	  if(i%2==0){
    		  background="style='background:#f1f6ff'";
          }
            
          if(i==0){
        	startHtml+="<tr >"
        		      +"<td>处理人</td>"
           			  +"<td>处理类型</td>"
		              +"<td>处理结果</td>"
		              +"<td>任务开始时间</td>"
		              +"<td>任务结束时间</td>"
		              +"<td>任务时间(分)</td>"
		              +"</tr>";
        	tbodyHtml+="<tbody>";
          }
                var assignee=v.assignee;
                if(assignee==null){
                	assignee="";
                }
                
                var startTime =v.startTime;
                if(startTime!=null&&startTime!=""){
                	startTime=new Date(v.startTime).format('yyyy-MM-dd hh:mm');
                }else{
                	startTime="";
                }
                
                //时间分钟计算
                var endTime =v.endTime;
                if(endTime!=null&&endTime!=""){
                	endTime=new Date(v.endTime).format('yyyy-MM-dd hh:mm');
                }else{
                	endTime="";
                }
                
                var s=0;
                if(endTime!=""&&startTime!=""){
                	s=(v.endTime*1-v.startTime*1)/60000;
                	s=s.toFixed(0);
                }
           
          tbodyHtml+="<tr "+background+">"
        	        +"<td>"+assignee+"</td>"
                    +"<td>"+v.activityName+"</td>";
                   
                  if(v.deleteReason==""){
                	  tbodyHtml+="<td></td>";
                  }else{
                	  tbodyHtml+="<td>["+v.deleteReason+"]</td>";
                  }  
           tbodyHtml+= "<td>"+startTime+"</td>"
                    +"<td>"+endTime+"</td>"
                    +"<td>"+s+"(分)</td>"
                    +"</tr>";
      });
      tbodyHtml+="</tbody>";
      startHtml+="</tr></thead>";
      
      
      //不存在就加入workflowTraceDialogWords
      if ($('#workflowTraceDialogWords').length == 0) {
          $('<div/>', {
              id: 'workflowTraceDialogWords',
              title: "查看流程"
          }).appendTo('body');
      }
      
      $('#workflowTraceDialogWords').html(startHtml+tbodyHtml+endHtml);
      
  // 打开对话框
     $('#workflowTraceDialogWords').dialog({
         modal: true,
         resizable: false,
         dragable: false,
         open: function() {
             $('#workflowTraceDialogWords').css('padding', '0.2em');
             $('#workflowTraceDialogWords .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialogWords').height() - 75);
         },
         width: document.documentElement.clientWidth * 0.65,
         height: 350
     });
  });

}
