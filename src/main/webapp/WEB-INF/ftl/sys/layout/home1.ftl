<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<script src="${static}/js/echarts.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="${static}/js/jquery1.js" charset="UTF-8" type="text/javascript"></script>
<link rel="stylesheet" href="${static}/bootstrap/css/bootstrap1.css" />




<script type="text/javascript" src="${static}/bootstrap/js/jquery.min.js" ></script>
<script type="text/javascript" src="${static}/bootstrap/js/bootstrap.min.js" ></script>


<!-- jQuery 1.7.2 or higher -->

  <!--[if lte IE 8]>
  <!-- bsie js 补丁只在IE6中才执行 -->
  <script type="text/javascript" src="${static}/bootstrap/js/bootstrap-ie.js" ></script>
  <![endif]-->
<script type="text/javascript" charset="UTF-8">
		<!--tabl切换-->
		$(function(){
			$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
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
                  html1+='<li><div class="row"><div class="col-md-8">'+b+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
                  b++;
                  }
                  if(i>data.length-6){
                   html2+='<li><div class="row"><div class="col-md-8">'+a+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
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
                  html1+='<li><div class="row"><div class="col-md-8">'+b+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
                  b++;
                  }
                  if(i>data.length-6){
                   html2+='<li><div class="row"><div class="col-md-8">'+a+'.'+$data.orgNae+'</div><div class="col-md-4"><span>'+$data.totalNumber+'</span></div></div></li>';
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
			    var html1='<li><div class="row"><div class="col-md-2"><font size="3">流程名称</font></div><div class="col-md-2"><span class=""><font size="3">节点名称</font></span></div><div class="col-md-2"><span class=""><font size="3">检查上传人</font></span></div><div class="col-md-2"><font size="3" >操作</font></div><div class="col-md-2"><span class=""><font size="3" >检查上传时间</font></span></div></div></li>';
                for(var i = 0; i <  data.length; i++) {
                 var $data = data[i];
                 if($data.assignee && $data.assignee !=null ){
                 		if($data.isTimeout && $data.isTimeout=='1'){
                 		html1+='<li><div style="background-color:#FFA07A;" class="row"><div class="col-md-2">'+$data.processName+'</div><div class="col-md-2"><span class="jfuj">'+$data.name+'</span></div><div class="col-md-2"><span class="jfuj">'+$data.superviseName+'</span></div><div class="col-md-2"><font onClick="javascript:taskComplete(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.name+'\',\''+$data.processKey+'\',\''+$data.executionId+'\');">办理</font></div><div class="col-md-2"><span class="jfuj">'+$data.dataTime+'</span></div></div></li>';
                 		}
                 		else{
                 		   html1+='<li><div class="row"><div class="col-md-2">'+$data.processName+'</div><div class="col-md-2"><span class="jfuj">'+$data.name+'</span></div><div class="col-md-2"><span class="jfuj">'+$data.superviseName+'</span></div><div class="col-md-2"><font onClick="javascript:taskComplete(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\',\''+$data.name+'\',\''+$data.processKey+'\',\''+$data.executionId+'\');">办理</font></div><div class="col-md-2"><span class="jfuj">'+$data.dataTime+'</span></div></div></li>';
                 		}
                 
                 }
                 else{
                 	if($data.isTimeout && $data.isTimeout=='1'){
                 		html1+='<li><div style="background-color:#FFA07A;" class="row"><div class="col-md-2">'+$data.processName+'</div><div class="col-md-2"><span class="jfuj">'+$data.name+'</span></div><div class="col-md-2"><span class="jfuj">'+$data.superviseName+'</span></div><div class="col-md-2"><font onClick="javascript:taskSign(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\');">签收</font></div><div class="col-md-2"><span class="jfuj">'+$data.dataTime+'</span></div></div></li>';
                 		}
                 		else{
                 		html1+='<li><div class="row"><div class="col-md-2">'+$data.processName+'</div><div class="col-md-2"><span class="jfuj">'+$data.name+'</span></div><div class="col-md-2"><span class="jfuj">'+$data.superviseName+'</span></div><div class="col-md-2"><font onClick="javascript:taskSign(\''+$data.workId+'\',\''+$data.id+'\',\''+$data.workDefKey+'\');">签收</font></div><div class="col-md-2"><span class="jfuj">'+$data.dataTime+'</span></div></div></li>';
                 		}
                 }
                 
                }            
                $("#todoList").html('');
                $("#todoList").html(html1);
            }             
		 });
		}
		
		
		    //签收
    function taskSign(index,id,taskKey){
	   $.ajax({
			type: "POST",
			url: "${ctx}/process/taskSign?taskId="+index+"&processId="+id+"&taskKey="+taskKey,
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
			    var html1='<li><div class="row" style="color: #1fa1ef;"><div class="col-md-8"><font size="3">标题</font></div><div class="col-md-4"><span class=""><font size="3">时间</font></span></div></div></li>';
			    var b=1;
                for(var i = 0; i < data.length; i++) {
                var $data = data[i];
                if(i < 4){
                 	html1+='<li><a href="#" onclick="announceDetail('+$data.id+')"><div class="row" style="color:#000"><div class="col-md-8">'+b+'.'+$data.title+'</div><div class="col-md-4"><span>'+$data.issuetime+'</span></div></div></a></li>';
                    b++;
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
		<style>
			.pvwg li .col-md-8{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    *zoom:1;
    
}
*{margin:0;padding:0}
.borderno .col-md-2 font{
	cursor: pointer;
}
		 body{
		 	font-family: "微软雅黑";
		 }
			.turb .panel{
				border-radius: 0px;
			}
			.turb .panel-heading{
				border-radius: 0px;
				 background: #1fa1ef;
				 border: none;
			}			
			.turb .panel-primary{
				 border: 1px solid #1fa1ef;
			}
			.panel-body h4{
			text-align: center;
			    color: #ffffff;
			    background: #ffbdbd;
			    line-height: 35px;
			    padding: 0px;
			    color: #ff0000;
			    margin: 0px;
			}
			.tab-content{
			    border-top: 0px;
			        border-radius: 0px 0px 5px 5px;
			}
			.nav-tabs a{
				color: #1fa1ef;
			}
			.pvwg{
			    padding: 0px;
			    margin: auto;
				border: 1px solid #ffbdbd;	
			    margin-top: 5px;
			        border-radius: 5px;
			        height:227px;
			}
			.pvwg .col-md-4{
				text-align: right;
				color: #FF0000;
				/*font-weight: bold;*/
			}
			.carousel-indicators{
				display: none !important; 
			}
			.header{
				height: 100px;
				/*background: #f00;*/
				/*margin-top: 50px;*/ 
			}
			.on1{
			     width: 100%;
			     height: 100px;
			     background: #f00;
			         padding: 0px;
			}
			.on2{
			     width: 100%;
			     height: 100px;
			    background: #428fdf;
			}			
			.on3{
			     width: 100%;
			     height: 100px;
			    background: #428fdf;
			}			
			.on4{
			     width: 100%;
			     height: 100px;
			     background: #32bfff;
			}
			.on5{
			     width: 100%;
			     height: 100px;
			    background: #f18b18;
			}			
			.on6{
			     width: 100%;
			     height: 100px;
			    background: #40cbe7;
			}			
			.on7{
			     width: 100%;
			     height: 100px;
			     background: #ff701e;
			}
			.on8{
			     width: 100%;
			     height: 100px;
			    background: #ff6038;
			}			
			.on9{
			     width: 100%;
			     height: 100px;
			    background: #7e95f7;
			}			
			.on10{
			     width: 100%;
			     height: 100px;
			     background: #ff2848;
			}
			.on11{
			     width: 100%;
			     height: 100px;
			    background: #4097ff;
			}			
			.on12{
			     width: 100%;
			     height: 100px;
			         float: right;
			    background: #129dff;
			        padding: 0px;
			}			
			
			.carousel-control{
				width: 5%;
				line-height: 100px;
			    height:100px;
			}
			.header .col-md-1{
			    text-align: center;
			    height: 100px;
			    color: #fff;
			}
			.header .row{
				border-radius: 5px;
				margin:0 auto;
			}
			.header{
				border-radius: 5px;
			}
			.header h3{
				font-weight: bold;
				color: #FFFFFF;
			}
			.header p{
				color: #FFFFFF;
			}
			👌
			h2{font-size:24px!important;}
			.pvwg li{
				list-style: none;
		  	  padding: 0% 5%;
		  	  line-height: 38px;
		  	  font-size: 1rem;
			}	
			.row{
				    clear: both;
			}	
			.panel-body{
				padding: 15px 0px !important;
			}	
			.turb h2{
			    padding:0% 1%;
			    color: #1fa1ef;
			    font-size: 1.5rem;
		        margin: 0px;
			}
			.borderno{
		  	  padding: 0px;
		  	  margin-bottom: 0px;
		  	  height:227px;
			}
			.borderno li{
			  list-style: none;
		  	  line-height: 45px;
		  	  font-size: 1.2rem;
		  	  border-bottom: 1px solid #ddd;
			}
			.borderno li:last-child{
				border-bottom: 0px;
			}
			.borderno .col-md-3{
				text-align: center;
				color: #FF0000;
				/*font-weight: bold;*/
			}
			.borderno .col-md-2{
				color: #1fa1ef;
				text-align: center;
			}
			.jfuj{
				color: #333;
			}
			.nav-tabs > li.active > a, .nav-tabs > li.active > a:hover, .nav-tabs > li.active > a:focus{
				border: 1px solid #ffbdbd;
				border-top: 0px;
				background: #f00;
  				  color: #fff;
				border-bottom: 0px;
			}
			.turb{
			    border-radius: 5px;
			    margin-top: 2%;
			}
			.turb .modal-title h2{
				line-height: 50px;
				font-weight: bold;
				font-size:20px;
   				border-bottom: 1px solid #ffbdbd;
			}
			.fyf{
				    font-size: 1vw;
				font-weight: bold;
				padding-right: 0.5%;
			}
			.yeler h2{
				color: #ff0000;
			}
			.navxaqc{
				    float: right;
				    border: none;
				    height: 51px;
			}
			.navxaqc a{
				color: #FF0000;
			}
			.navxaqca{
				border: none !important;
			}
			.ralp{
				    border: 1px solid #cdcdcd;
				    border-radius: 5px;
				    padding: 0px;
				    margin-top: 2%;
				    margin-bottom: 2%;
			}
			.bof{
				    margin-bottom: 4%;
  				  border: 1px solid #ffbdbd;
			}
			
			.nav > li > a {
				    position: relative;
				    display: block;
				    border-radius: 0px;
				    padding: 15px 15px;
				}
			.bof-db{
				border: 1px solid #1fa1ef;
			}	
			.bof-db h2{
				color: #1fa1ef;
				border-bottom: 1px solid #1fa1ef !important;
			}
			.bof-db .active a{
				background: #129DFF !important;
				border: none !important;
			}
			.bof-a li a{
				color: #1fa1ef;
			}				
		</style>
	</head>
	<body>
	<!---->
	<div class="container-fluid">
		<div class="row" style="margin-top: 50px;">
			<div class="col-md-12 header">
				<div id="myCarousel" class="carousel slide">
					<!-- 轮播（Carousel）指标 -->
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>   
					<!-- 轮播（Carousel）项目 -->
					<div class="carousel-inner">
						<div class="item active">
							<div class="col-md-1 col-xs-1">
								<div class="row on1">
									<h3>${(task.unfinishedCount)!'0'}</h3>
									<p>当月应发现</p>
								</div>
							</div>
							<div class="col-md-1 col-xs-1">
								<div class=" row on2">
									<h3>${(task.repeatCount)!'0'}</h3>
									<p>当月已发现</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on3">
									<h3>${(task.taskNumber)!'0'}</h3>
									<p>当日应发现</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on4">
									<h3>${(task.problemCount)!'0'}</h3>
									<p>当日已发现</p>
								</div>
							</div>		
							
								<!--			
							<div class="col-md-1 col-xs-1">
								<div class="row on5">
									<h3>${(task.monthprocessingtototal)!'0'}</h3>
									<p>当月整改数</p>
								</div>
							</div>
							<div class="col-md-1 col-xs-1">
								<div class="row on6">
									<h3>${(task.monthrate)!'0'}%</h3>
									<p>当月整改率</p>
								</div>
							</div>
							-->
							
									
								<div class="col-md-1 col-xs-1">
								<div class="row on7">
									<h3>${(task.todayDaiNum)!'0'}</h3>
									<p>当日待整改数</p>
								</div>
							</div>	
														
							<div class="col-md-1 col-xs-1">
								<div class="row on7">
									<h3>${(task.totalNumber2)!'0'}</h3>
									<p>当日整改数</p>
								</div>
							</div>	
							<div class="col-md-1 col-xs-1">
								<div class="row on8">
									<h3>${(task.rate)!'0'}%</h3>
									<p>当日整改率</p>
								</div>
							</div>	
							<div class="col-md-1 col-xs-1">
								<div class="row on9">
									<h3>${(task.monthprocessingtototal)!'0'}</h3>
									<p>当月审核数</p>
								</div>
							</div>
							<div class="col-md-1 col-xs-1">
								<div class="row on10">
									<h3>${(task.monthrate)!'0'}%</h3>
									<p>当月审核率</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on11">
									<h3>${(task.totalNumber2)!'0'}</h3>
									<p>当日审核数</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on12">
									<h3>${(task.rate)!'0'}%</h3>
									<p>当日审核率</p>
								</div>
							</div>								
						</div>
						<div class="item">
							<div class="col-md-1 col-xs-1">
								<div class="row on1">
									<h3>${(task.monthprocessingtototal)!'0'}</h3>
									<p>当月抽查数</p>
								</div>
							</div>
							<div class="col-md-1 col-xs-1">
								<div class=" row on2">
									<h3>${(task.monthrate)!'0'}%</h3>
									<p>当月抽查率</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on3">
									<h3>${(task.totalNumber2)!'0'}</h3>
									<p>当日抽查数</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on4">
									<h3>${(task.rate)!'0'}%</h3>
									<p>当日抽查率</p>
								</div>
							</div>						
							<div class="col-md-1 col-xs-1">
								<div class="row on5">
									<h3>${(task.projecttotal)!'0'}</h3>
									<p>问题总数</p>
								</div>
							</div>
							<div class="col-md-1 col-xs-1">
								<div class="row on6">
									<h3>${(task.projectrectifytotal)!'0'}</h3>
									<p>整改数</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on7">
									<h3>${(task.projectrate)!'0'}</h3>
									<p>整改率</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on8">
									<h3>${(task.closetotal)!'0'}</h3>
									<p>关闭数</p>
								</div>
							</div>	
							<div class="col-md-1 col-xs-1">
								<div class="row on9">
									<h3>${(task.todayGcNum)!'0'}</h3>
									<p>工程数量</p>
								</div>
							</div>
							<div class="col-md-1 col-xs-1">
								<div class="row on10">
									<h3>${(task.todayHjNum)!'0'}</h3>
									<p>环境数量</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on11">
									<h3>${(task.todayKfNum)!'0'}</h3>
									<p>客服数量</p>
								</div>
							</div>							
							<div class="col-md-1 col-xs-1">
								<div class="row on12">
									<h3>${(task.todayAqNum)!'0'}</h3>
									<p>安全数量</p>
								</div>
							</div>								
						</div>
					</div>
					<!-- 轮播（Carousel）导航 -->
					<a class="carousel-control left" href="#myCarousel"  data-slide="prev"> <span>《</span> </a>
					<a class="carousel-control right" href="#myCarousel" data-slide="next"> <span>》</span> </a>
					<!-- 控制按钮 -->
					<!--<div style="text-align:center;">
						<input type="button" class="btn start-slide" value="Start">
						<input type="button" class="btn pause-slide" value="Pause">
						<input type="button" class="btn prev-slide" value="Previous Slide">
						<input type="button" class="btn next-slide" value="Next Slide">
						<input type="button" class="btn slide-one" value="Slide 1">
						<input type="button" class="btn slide-two" value="Slide 2">            
						<input type="button" class="btn slide-three" value="Slide 3">
					</div>-->
				</div> 
				<script>
					$(function(){
						// 初始化轮播
						$(".start-slide").click(function(){
							$("#myCarousel").carousel('cycle');
						});
						// 停止轮播
						$(".pause-slide").click(function(){
							$("#myCarousel").carousel('pause');
						});
						// 循环轮播到上一个项目
						$(".prev-slide").click(function(){
							$("#myCarousel").carousel('prev');
						});
						// 循环轮播到下一个项目
						$(".next-slide").click(function(){
							$("#myCarousel").carousel('next');
						});
						// 循环轮播到某个特定的帧 
						$(".slide-one").click(function(){
							$("#myCarousel").carousel(0);
						});
						$(".slide-two").click(function(){
							$("#myCarousel").carousel(1);
						});
						$(".slide-three").click(function(){
							$("#myCarousel").carousel(2);
						});
					});
				</script>
			</div>
		</div>
		<!--头部-->
		<div class="turb">
			<div class="col-md-6 col-xs-6">
				<div class="bof">
					<div class="modal-title yeler">
						<h2>
							<span class="fyf">|</span>当前发现问题排名
							<ul id="myTab" class="nav nav-tabs navxaqc">
								<li class="active">
									<a href="#home" onClick="dayfxProblems(1);" data-toggle="tab">当日排行榜</a>
								</li>
								<li>
									<a href="#ios" onClick="dayFxProblems(2);" data-toggle="tab">当月排行榜</a>
								</li>
								<li>
									<a href="#jmeter" onClick="dayFxProblems(3);" data-toggle="tab">当年排行榜</a>
								</li>
							</ul>
						</h2>
					</div>
					<div class="panel-body">
						<div id="myTabContent" class="tab-content navxaqca">
							<div class="tab-pane fade in active" id="home">
								<div class="">
									<div class="col-md-6 col-xs-6 right">
										<ul class="pvwg" id="qdiscoverProDay">
											
										</ul>
									</div>
									<div class="col-md-6 col-xs-6 left">
										<ul class="pvwg" id="hdiscoverProDay">
											
										</ul>
									</div>								
								</div>
							</div>
							<div class="tab-pane fade" id="ios">
								<div class="">
									<div class="col-md-6 col-xs-6 right">
										<ul class="pvwg" id="qdiscoverProMonth">
											
										</ul>
									</div>
									<div class="col-md-6 col-xs-6 left">
										<ul class="pvwg" id="hdiscoverProMonth">
											
										</ul>
									</div>								
								</div>							
							</div>
							<div class="tab-pane fade" id="jmeter">
								<div class="">
									<div class="col-md-6 col-xs-6 right">
										<ul class="pvwg" id="qdiscoverProYear">
											
										</ul>
									</div>
									<div class="col-md-6 col-xs-6 left">
										<ul class="pvwg" id="hdiscoverProYear">
											
										</ul>
									</div>								
								</div>						
							</div>
						</div>
				      </div>
				</div>
			</div>
			<div class="col-md-6 col-xs-6">
				<div class="bof">
					<div class="modal-title yeler">
						<h2>
							<span class="fyf">|</span>当前解决问题排名
							<ul id="myTab" class="nav nav-tabs navxaqc">
								<li class="active">
									<a href="#home1" onClick="dayjjProblems(1);" data-toggle="tab">当日排行榜</a>
								</li>
								<li>
									<a href="#ios1" onClick="dayjjProblems(2);" data-toggle="tab">当月排行榜</a>
								</li>
								<li>
									<a href="#jmeter1" onClick="dayjjProblems(3);" data-toggle="tab">当年排行榜</a>
								</li>
							</ul>
						</h2>
					</div>
					<div class="panel-body">
						<div id="myTabContent" class="tab-content navxaqca">
							<div class="tab-pane fade in active" id="home1">
								<div class="">
									<div class="col-md-6 col-xs-6 right">
										<ul class="pvwg" id="qsolveProProDay">
											
										</ul>
									</div>
									<div class="col-md-6 col-xs-6 left">
										<ul class="pvwg" id="hsolveProProDay">
											
										</ul>
									</div>								
								</div>
							</div>
							<div class="tab-pane fade" id="ios1">
								<div class="">
									<div class="col-md-6 col-xs-6 right">
										<ul class="pvwg" id="qsolveProProMonth">
											
										</ul>
									</div>
									<div class="col-md-6 col-xs-6 left">
										<ul class="pvwg" id="hsolveProProMonth">
											
										</ul>
									</div>								
								</div>							
							</div>
							<div class="tab-pane fade" id="jmeter1">
								<div class="">
									<div class="col-md-6 col-xs-6 right">
										<ul class="pvwg" id="qsolveProProYear">
											
										</ul>
									</div>
									<div class="col-md-6 col-xs-6 left">
										<ul class="pvwg" id="hsolveProProYear">
										</ul>
									</div>								
								</div>						
							</div>
						</div>
				      </div>
				</div>
			</div>
		</div>		
	<!--代办-->		
		<div class="turb">
			<div class="col-md-6 col-xs-6">
			  <div class="bof-db">
				<div class="modal-title yeler">
					<h2>
						<span class="fyf">|</span>个人任务情况
						<ul id="myTab" class="nav nav-tabs navxaqc bof-a">
							<li class="active">
								<a href="#home2" onClick="todoList();" data-toggle="tab">代办流程</a>
							</li>
							
							<!--
							<li>
								<a href="#ios2" onClick="hasDoneList();" data-toggle="tab">已办流程</a>
							</li>
							<li>
								<a href="#jmeter2" onClick="launchList();" data-toggle="tab">我发起的流程</a>
							</li>
							-->
							<li>
								<a href="#" onClick="compTask();" data-toggle="tab">更多</a>
							</li>
							
						</ul>
					</h2>
				</div>
				<div class="panel-body">
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="home2">
							<div class="col-md-12 col-xs-12 left">
								<ul class="borderno" id="todoList">
									
								</ul>
							</div>								
						</div>
						<div class="tab-pane fade" id="ios2">
							<div class="col-md-12 col-xs-12 left">
								<ul class="borderno" id="hasDoneList">
									
								</ul>
							</div>							
						</div>
						<div class="tab-pane fade" id="jmeter2">
							<div class="col-md-12 col-xs-12 left">
								<ul class="borderno" id="launchList">
									
								</ul>
							</div>						
						</div>
										
					</div>
			      </div>
			</div>
			</div>
			<div class="col-md-6 col-xs-6">
				<div class="bof-db">
				<div class="modal-title yeler">
					<h2>
						<span class="fyf">|</span>通知公告
						<ul id="myTab" class="nav nav-tabs navxaqc bof-a">
							<li class="active">
								<a href="#" onclick="announceView()" data-toggle="tab">更多</a>
							</li>
							<li style="display:none">
								<a href='javascript:announceList()' data-toggle="tab">已发布的通知</a>
							</li>
						</ul>
					</h2>
				</div>
				<div class="panel-body">
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="home3">
							<div class="col-md-12 col-xs-12 left">
								<ul class="borderno" id="announceList">
									
								</ul>
							</div>	
						</div>
						
					</div>
			      </div>				
			</div>			
		</div>	
		<!---->
	</div>
	</div>
	</body>
	

</html>
