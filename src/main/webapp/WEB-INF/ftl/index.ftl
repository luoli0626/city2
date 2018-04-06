<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${webSiteName}</title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
</head>
<body id="indexLayout" class="easyui-layout" fit="true">
	<div region="north"  href="${ctx}/main/north" style="height:50px;overflow: hidden; background-color:#373d41;"></div>
	<div region="west" href="${ctx}/main/west"split="false" style="width:200px;overflow: hidden;"></div>
	<div region="center"  href="${ctx}/main/center" title="" style="overflow: hidden;"></div>
	<div region="south" href="${ctx}/main/south" style="height:20px;overflow: hidden;background:#F0F0F0;"></div>
</body>
</html>