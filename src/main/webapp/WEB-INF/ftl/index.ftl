<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${webSiteName}</title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
</head>
<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
    	console.log("建立通信链接");
        websocket = new WebSocket("ws://111.231.222.163:8080/websocket");
        // websocket = new WebSocket("ws://localhost:8080/city/websocket");本地
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (msg) {
        setMessageInnerHTML(msg.data);
	}
	
    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        if(innerHTML=="WebSocket连接发生错误"){
        	console.log(innerHTML);
        }else if(innerHTML=="WebSocket连接成功"){
        	console.log(innerHTML);
        }else if(innerHTML=="WebSocket连接关闭"){
        	console.log(innerHTML);
        }else{
        	//alert(innerHTML);
        	console.log(innerHTML);
			$("#audio").get(0).play();
        }
        
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>
<body id="indexLayout" class="easyui-layout" fit="true">
	<audio id="audio" src="http://111.231.222.163:8080/data/uploads/photo/photo2.mp3" controls="controls"></audio>
	<div region="north"  href="${ctx}/main/north" style="height:50px;overflow: hidden; background-color:#373d41;"></div>
	<div region="west" href="${ctx}/main/west"split="false" style="width:200px;overflow: hidden;"></div>
	<div region="center"  href="${ctx}/main/center" title="" style="overflow: hidden;"></div>
	<div region="south" href="${ctx}/main/south" style="height:20px;overflow: hidden;background:#F0F0F0;"></div>
</body>
</html>