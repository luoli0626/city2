<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title></title>
</head>
<style>
.timeout{ margin-left:auto;margin-right:auto; text-align:center; font-size:20px; margin-top:150px; font-family:"微软雅黑"; color:#666;}
.timeout img{ opacity:0.7;}
.timeout a{ font-size:20px;}
</style>
<body class="noback">

<div class="tabbable">
<p class="timeout">
<img src="<%=request.getContextPath() %>/static/images/clock.png"><br /><br /><br />
<span>您没有登录或登录超时，请<a href="javascript:timeOut();" style='color:blue;'>重新登录</a>!</span>
</p>
<script type="text/javascript">
   function timeOut(){
   		window.parent.location.href ="<%=request.getContextPath()%>/toLogin";
   }




</script>
</div>


</body>
</html>
