<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
		<title></title>
		<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
		<script type="text/javascript">
			var myTime=window.setInterval(resetTime,1000);
			var i = 5;
			function resetTime(){
				if(i>0){
					document.getElementById("txtTime").innerHTML=i;
					i--;
					
				}else{
					window.history.go(-1);
				}
			}
		</script>
		<style>
			.timeout {
				margin-left: auto;
				margin-right: auto;
				text-align: center;
				font-size: 20px;
				margin-top: 150px;
				font-family: "微软雅黑";
				color: #666;
			}
			
			.timeout img {
				opacity: 0.7;
			}
			
			.timeout a {
				font-size: 20px;
			}
		</style>
	</head>
	<body class="noback">
		<div class="tabbable">
			<p class="timeout">
				<img src="<%=request.getContextPath()%>/static/images/clock.png">
				<br/>
				<br/>
				<br/>
				<span>您没有该资源的访问权限 请联系管理员!<label id="txtTime" style="color:red;">5</label>秒后返回上页！</span>
			</p>
		</div>
	</body>
</html>
