<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>

<script type="text/javascript" charset="UTF-8">

</script>

</head>
<body class="easyui-layout" fit="true">
		<form id="photoForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">随手拍详情</legend>
				<table class="tableForm">
					<tr>
						<th >上&nbsp传&nbsp者：</th>
							<td colspan="8" id="createUserName" name="createUserName">${(photo.createUserName.NICKNAME)!}</td>
					</tr>
					<tr>
						<th >问题描述：</th>
							<td colspan="8" id="remark2" name="remark2">${photo.content}</td>
					</tr>
					<tr>
						<th >照片：</th>
					</tr>
				<#if photo.images?exists>
		  			  <#list photo.images as node>  
			  			  <tr>
			  				<td colspan='8'><img style='width:500px;height:500px;margin-left:20px' src=${node.address}/></td>
			  			</tr>
	           		</#list>
	  			</#if>
				 <tr id="process">
            	 <th id=processTh>处理进度：</th>
              	 </tr>
              	 <tr style='font-weight:blod;' ><td>处理状态</td><td colspan='2'>处理详情</td><td colspan='2'>处理时间</td></tr>
              	 <#if photo.allState?exists>
		  			  <#list photo.allState as node>  
			  			  <tr>
			  				<tr><td>${node.name}</td><td colspan='2'>${(node.content)!}</td><td colspan='2'>timestampToTime(${node.createTime})</td></tr>
			  			</tr>
	           		</#list>
	  			</#if>
				</table>
			</fieldset>
		</form>
</body>
</html>