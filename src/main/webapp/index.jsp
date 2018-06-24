<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Java后端WebSocket的Tomcat实现</title>
</head>
<body>
Welcome<br/><input id="text" type="text"/>
<button onclick="send()">发送消息</button>
<hr/>
<button onclick="closeWebSocket()">关闭WebSocket连接</button>
<hr/>
<div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
    	console.log("建立通信链接");
        websocket = new WebSocket("ws://localhost:8080/city/websocket");
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
        //setMessageInnerHTML(msg.data);
        
        var data =  msg.data;
		var datalen = msg.data.size;

            {
        	    var reader = new FileReader();
        	    
        	    reader.onload = function(evt)
            	{
                	if(evt.target.readyState == FileReader.DONE)
                	{
                	    var data = new Uint8Array(evt.target.result);

                       /*
                       // 方式1 ,ok
                       audioContext.decodeAudioData(evt.target.result, function(buffer) {//解码成pcm流
                            var audioBufferSouceNode = audioContext.createBufferSource();
                            audioBufferSouceNode.buffer = buffer;
                            audioBufferSouceNode.connect(audioContext.destination);
                            audioBufferSouceNode.start(0);
                        }, function(e) {
                            alert("Fail to decode the file.");
                        });
                        */

                        //方式2 ok
                        audio.src = window.URL.createObjectURL(getBlob2(data,data.length));
                	}
            	}
            	reader.readAsArrayBuffer(data);
   			 }
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
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
        //alert(innerHTML);
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
    
    //转换字节流
    function getBlob2(data,len){
	    var buffer = new ArrayBuffer(len);
	    var dataview = new DataView(buffer);
		writeUint8Array(dataview,0,data,len);
	    return new Blob([dataview], { type: 'audio/wav' });
	}
    
</script>
</html>