<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<link rel="stylesheet" type="text/css" href="${static}/css/stylesContent.css"/>

	<font color="red" size="12">
		导入失败信息,请核对格式!
	</font>
	<table border="0" cellpadding="0"  cellspacing="0" bordercolor="#cccccc">
		<tr>
			<th width="25%">登录名称</th>
			<th width="25%">项目名称</th>
			<th width="25%">场景名称</th>
			<th width="25%">角色名称</th>
		</tr>
		<#list repeat1 as map>	
			<tr>
				<td width="25%">${map.userName!''}
				</td>
				<td width="25%">${map.orgName!''}
				</td>
				<td width="25%">${map.sceneName!''}
				</td>
				<td width="25%">${map.roleName!''}
				</td>
			<tr>
		</#list>
	</table>