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
        console.log(msg);
        var data =  msg.data;
		var datalen = msg.data.size;

            {
        	    var reader = new FileReader();
        	    
        	    reader.onload = function(evt)
            	{
                	if(evt.target.readyState == FileReader.DONE)
                	{
                	    var data = new Uint8Array(evt.target.result);

                       // 方式1 ,ok
                       audioContext.decodeAudioData(evt.target.result, function(buffer) {//解码成pcm流
                            var audioBufferSouceNode = audioContext.createBufferSource();
                            audioBufferSouceNode.buffer = buffer;
                            audioBufferSouceNode.connect(audioContext.destination);
                            audioBufferSouceNode.start(0);
                        }, function(e) {
                            alert("Fail to decode the file.");
                        });

                        //方式2 ok
                        //audio.src = window.URL.createObjectURL(getBlob2(data,datalen));
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
        if(innerHTML=="WebSocket连接发生错误"){
        	document.getElementById('message').innerHTML += innerHTML + '<br/>';
        }else if(innerHTML=="WebSocket连接成功"){
        	document.getElementById('message').innerHTML += innerHTML + '<br/>';
        }else if(innerHTML=="WebSocket连接关闭"){
        	document.getElementById('message').innerHTML += innerHTML + '<br/>';
        }else{
        	alert(innerHTML);
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
    //转换字节流
    function getBlob2(data,len){
	    var buffer = new ArrayBuffer(len);
	    var dataview = new DataView(buffer);
		writeUint8Array(dataview,0,data,len);
	    return new Blob([dataview], { type: 'audio/wav' });
	}
</script>
<body id="indexLayout" class="easyui-layout" fit="true">
	<div region="north"  href="${ctx}/main/north" style="height:50px;overflow: hidden; background-color:#373d41;"></div>
	<div region="west" href="${ctx}/main/west"split="false" style="width:200px;overflow: hidden;"></div>
	<div region="center"  href="${ctx}/main/center" title="" style="overflow: hidden;"></div>
	<div region="south" href="${ctx}/main/south" style="height:20px;overflow: hidden;background:#F0F0F0;"></div>
</body>
</html>