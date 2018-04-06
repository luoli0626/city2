<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>
     <link rel="stylesheet" href="${static}/css/bootstrap.css" />
    <link rel="stylesheet" href="${static}/css/index.css" />
</head>

<body style="min-width:1024px;">
  
</body>
<!--tabl切换-->
<script type="text/javascript" src="${static}/bootstrap/js/jquery.min.js" ></script>
<script type="text/javascript" src="${static}/bootstrap/js/bootstrap.min.js" ></script>
<script src="${static}/js/echarts.min.js" charset="UTF-8" type="text/javascript"></script>



<script>
    $(function() {
        $('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
            // 获取已激活的标签页的名称
            var activeTab = $(e.target).text();
            // 获取前一个激活的标签页的名称
            var previousTab = $(e.relatedTarget).text();
            $(".active-tab span").html(activeTab);
            $(".previous-tab span").html(previousTab);
            
        });

		 dayFxProblems('1');
			 dayjjProblems('1');
			 todoList();
			 announceList();



        // 初始化轮播
        $(".start-slide").click(function() {
            $("#myCarousel").carousel('cycle');
        });
        // 停止轮播
        $(".pause-slide").click(function() {
            $("#myCarousel").carousel('pause');
        });
        // 循环轮播到上一个项目
        $(".prev-slide").click(function() {
            $("#myCarousel").carousel('prev');
        });
        // 循环轮播到下一个项目
        $(".next-slide").click(function() {
            $("#myCarousel").carousel('next');
        });
        // 循环轮播到某个特定的帧 
        $(".slide-one").click(function() {
            $("#myCarousel").carousel(0);
        });
        $(".slide-two").click(function() {
            $("#myCarousel").carousel(1);
        });
        $(".slide-three").click(function() {
            $("#myCarousel").carousel(2);
        });

    });
    
    function dayFxProblems(dateTime){
		    $.ajax({
			type: "POST",
			url: "${ctx}/main/discoverPro?dateTime="+dateTime,
			dataType : "json",
			success: function(data){
			    var html1="<h4>前五名</h4>";
			    var html2="<h4>后五名</h4>";
			    var a=1;
			    var b=1;
			   
                for(var i = 0; i < data.length; i++) {
                 var $data = data[i];
                 if(i<5){
                 
                 //  html1+='<li><div class="row"><div class="col-md-8 pvwg-title pull-left" title="在苏州枫景颐庭小区出入口场景发现问题">1、在苏州枫景颐庭小区出入asdfasda口场景发现问题</div> <div class="col-md-4"><span>111，111112</span></div></div> </li>';
                 
                  html1+='<li><div class="row"><div class="col-md-8 pvwg-title  pull-left">'+b+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
                  b++;
                  }
                  if(i>data.length-6){
                   html2+='<li><div class="row"><div class="col-md-8  pvwg-title  pull-left">'+a+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
                   // html2+='<li><div class="row"><div class="col-md-8 pvwg-title" title="在苏州枫景颐庭小区出入口场景发现问题">1、在苏州枫景颐庭小区出入口场景发现问题</div> <div class="col-md-4"><span>111，111112</span></div></div> </li>';
                   
                   a++;
                 
                  }
                }
                if(dateTime=="1"){
	                $("#qdiscoverProDay").html('');
	                $("#hdiscoverProDay").html('');
	                $("#qdiscoverProDay").append(html1);
	                $("#hdiscoverProDay").append(html2);
                }else if(dateTime=="2"){
                    $("#qdiscoverProMonth").html('');
	                $("#hdiscoverProMonth").html('');
	                $("#qdiscoverProMonth").append(html1);
	                $("#hdiscoverProMonth").append(html2);
                }else if(dateTime=="3"){
                    $("#qdiscoverProYear").html('');
	                $("#hdiscoverProYear").html('');
	                $("#qdiscoverProYear").append(html1);
	                $("#hdiscoverProYear").append(html2);
                }
                
                
            }             
		 });
		}
		
		
		function dayjjProblems(dateTime){
		    $.ajax({
			type: "POST",
			url: "${ctx}/main/solvePro?dateTime="+dateTime,
			dataType : "json",
			success: function(data){
			    var html1="<h4>前五名</h4>";
			    var html2="<h4>后五名</h4>";
			    var a=1;
			    var b=1;
                for(var i = 0; i < data.length; i++) {
                 var $data = data[i];
                 if(i<5){
                  html1+='<li><div class="row"><div class="col-md-8  pull-left">'+b+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
                  b++;
                  }
                  if(i>data.length-6){
                   html2+='<li><div class="row"><div class="col-md-8  pull-left">'+a+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
                   a++;
                  }
                }
                if(dateTime=="1"){
	                $("#qsolveProProDay").html('');
	                $("#hsolveProProDay").html('');
	                $("#qsolveProProDay").append(html1);
	                $("#hsolveProProDay").append(html2);
                }else if(dateTime=="2"){
                    $("#qsolveProProMonth").html('');
	                $("#hsolveProProMonth").html('');
	                $("#qsolveProProMonth").append(html1);
	                $("#hsolveProProMonth").append(html2);
                }else if(dateTime=="3"){
                     $("#qsolveProProYear").html('');
	                $("#hsolveProProYear").html('');
	                $("#qsolveProProYear").append(html1);
	                $("#hsolveProProYear").append(html2);
                }
                
            }             
		 });
		}
		
		
		function todoList(){
		   $.ajax({
			type: "POST",
			url: "${ctx}/main/todoList",
			dataType : "json",
			success: function(data){
			    //var html1='<li><div class="row"><div class="col-md-2"><font size="3">流程名称</font></div><div class="col-md-2"><span class=""><font size="3">节点名称</font></span></div><div class="col-md-2"><span class=""><font size="3">检查上传人</font></span></div><div class="col-md-2"><font size="3" >操作</font></div><div class="col-md-2"><span class=""><font size="3" >检查上传时间</font></span></div></div></li>';
               var html1='';
                for(var i = 0; i <4; i++) {
                 var $data = data[i];
                 if($data != null){
         
                   if($data.assignee && $data.assignee !=null ){
                 //判断是否超时
                 		if($data.isTimeout && $data.isTimeout=='1'){
                 		html1+=' <tr style="background-color:#FFA07A;"><td>  '+$data.processName+'</td><td>'+$data.name+'</td><td>'+$data.superviseName+'</td><td><font style="cursor: pointer;" color="#1fa1ef" onClick="javascript:taskComplete(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.name+'\',\''+$data.processKey+'\',\''+$data.executionId+'\');">办理</font></td><td>'+$data.dataTime+'</td></tr>';
                 		}
                 		else{
                 		//判断是否延时
                 			if($data.isHangup && $data.isHangup=='Y'){
                 			                 		   html1+='<tr style="background-color:#FFFFAA;"><td>'+$data.processName+'</td><td>'+$data.name+'</td><td>'+$data.superviseName+'</td><td><font style="cursor: pointer;" color="#1fa1ef" onClick="javascript:taskComplete(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.name+'\',\''+$data.processKey+'\',\''+$data.executionId+'\');">办理</font></td><td>'+$data.dataTime+'</td></tr>';
                 			}
                 			else{
                 			                 		   html1+='<tr><td>'+$data.processName+'</td><td>'+$data.name+'</td><td>'+$data.superviseName+'</td><td><font style="cursor: pointer;" color="#1fa1ef" onClick="javascript:taskComplete(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.name+'\',\''+$data.processKey+'\',\''+$data.executionId+'\');">办理</font></td><td>'+$data.dataTime+'</td></tr>';
                 			
                 			}
                 		
                 		}
                 }
                 else{
                 	if($data.isTimeout && $data.isTimeout=='1'){
                 		html1+='<tr style="background-color:#FFA07A;"><td>'+$data.processName+'</td><td>'+$data.name+'</td><td>'+$data.superviseName+'</td><td><font color="#1fa1ef" style="cursor: pointer;" onClick="javascript:taskSign(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.processKey+'\');">签收</font></td><td>'+$data.dataTime+'</td></tr>';
                 		}
                 		else{
                 		         if($data.isHangup && $data.isHangup=='Y'){
                 		           html1+='<tr style="background-color:#FFFFAA;"><td>'+$data.processName+'</td><td>'+$data.name+'</td><td>'+$data.superviseName+'</td><td><font style="cursor: pointer;" color="#1fa1ef" onClick="javascript:taskSign(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.processKey+'\');">签收</font></td><td>'+$data.dataTime+'</td></tr>';
                 		         }
                 		         else{
                 		           html1+='<tr><td>'+$data.processName+'</td><td>'+$data.name+'</td><td>'+$data.superviseName+'</td><td><font style="cursor: pointer;" color="#1fa1ef" onClick="javascript:taskSign(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.processKey+'\');">签收</font></td><td>'+$data.dataTime+'</td></tr>';
                 		         }
                 		}
                 }
                 }
                 else{
              
              html1+='<tr><td></td><td></td><td></td><td><font style="" onClick=""></font></td><td></td></tr>';
                 
                 }
                 

                 
                }            
                $("#todoList").html('');
                $("#todoList").html(html1);
            }             
		 });
		}
		
		
		    //签收
    function taskSign(index,id,taskKey,processKey){
	   $.ajax({
			type: "POST",
			url: "${ctx}/process/taskSign?taskId="+index+"&processId="+id+"&taskKey="+taskKey+"&processKey="+processKey,
			dataType : "json",
			success: function(data){
				alert(data.msg);
				 window.location.reload();
				
			}	             
		 });
	   
	}
		//办理
			function taskComplete(taskId,id,taskName,name,processKey,exceptionId){
			  top.addTabFun({src:'${ctx}/process/taskComplete?taskId='+taskId+'&id='+id+'&taskName='+taskName+'&processKey='+processKey+'&exceptionId='+exceptionId+'&biao=1',title:name});
			}
			//更多任务
			function compTask(){
			  top.addTabFun({src:'${ctx}/process/main',title:'任务列表'});
			}
			
			//更多通知
			function announceTask(){
			  top.addTabFun({src:'${ctx}/announcement/announcementView',title:'公告管理'});
			}
		
		
		
		
		
		function hasDoneList(){
		   $.ajax({
			type: "POST",
			url: "${ctx}/main/hasDoneList",
			dataType : "json",
			success: function(data){
			    var html1="";
                for(var i = 0; i < data.length; i++) {
                 var $data = data[i];
                  html1+='<li><div class="row"><div class="col-md-8">'+$data.name+'（'+$data.processType+'）在'+$data.sceneName+'发现问题</div><div class="col-md-2"><span class="jfuj">'+$data.dataTime+'</span></div><div class="col-md-2"><font onClick="javascript:view('+$data.id+');">查看</font></div></div></li>';
                }
                $("#hasDoneList").html('');
                $("#hasDoneList").html(html1);
            }             
		 });
		}
		function launchList(){
		   $.ajax({
			type: "POST",
			url: "${ctx}/main/launchList",
			dataType : "json",
			success: function(data){
			    var html1="";
                for(var i = 0; i < data.length; i++) {
                 var $data = data[i];
                  html1+='<li><div class="row"><div class="col-md-8">'+$data.name+'（'+$data.processType+'）在'+$data.sceneName+'发现问题</div><div class="col-md-2"><span class="jfuj">'+$data.dataTime+'</span></div><div class="col-md-2"><font onClick="javascript:view('+$data.id+');">查看</font></div></div></li>';
                }
                $("#launchList").html('');
                $("#launchList").html(html1);
            }             
		 });
		}

	//已发布的通知
	function announceList(){
		 $.ajax({
			type: "POST",
			url: "${ctx}/main/announceList",
			dataType : "json",
			success: function(data){
			
			    //var html1='<li><div class="row" style="color: #1fa1ef;"><div class="col-md-8"><font size="3">标题</font></div><div class="col-md-4"><span class=""><font size="3">时间</font></span></div></div></li>';
			    var html1='';
			    var b=1;
                for(var i = 0; i < 4; i++) {
                
                var $data = data[i];
                if($data != null){
                  	html1+='<tr ><td><a href="#"    onclick="announceDetail('+$data.id+')">'+b+'.'+$data.title+'</a></td><td>'+$data.issuetime+'</td></tr>';
                }
                else{
                
                  	html1+='<tr ><td><a href="#"    onclick=""></a></td><td></td></tr>';
                }
               
                 $("#announceList").html('');
	             $("#announceList").append(html1);
                }
            }             
		 });
	}
  //查看详细公告
  function announceDetail(id){
	top.addTabFun({src:'${ctx}/announcement/announceDetail?id='+id,title:'查看详细公告'});
  }
  function announceView(){
  	 top.addTabFun({src:'${ctx}/announcement/announcementView',title:'公告管理'});
  }
  //编辑
    function editProcess(index,state){
      if(state=="1"){
	    top.addTabFun({src:'${ctx}/process/editProcess?id='+index,title:'问题确认'});
	    }else{
	    top.addTabFun({src:'${ctx}/process/checkProcess?id='+index,title:'审核项目'});
	    }
	}
	 //编辑
    function view(id){
	    top.addTabFun({src:'${ctx}/manual/view?id='+id,title:'查看流程信息'});
	}
    
    
    
    
    
    
    
    
    
    
    
</script>

</html>