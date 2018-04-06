<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	$(function() {
		
		userForm = $('#userForm').form();


		datagrid = $('#datagrid').datagrid({
			url : '${ctx}/houseftl/findProjectList',
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			rownumbers: true,
			pagination : true,
			pageSize:30,
			pageList:[10,20,30,50,100],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'orgCode',
			sortName : 'orgCode',
			frozenColumns : [ [ {
				field : 'orgCode',
				title : '项目id',
				width : 350,
				sortable : true,
			}] ],
			columns : [ [ {
				field : 'orgName',
				title : '项目名称',
				width :580,
			}] ],
			onLoadSuccess:function(data) {
				
			}
		});
	});

	function searchFun() {
		datagrid.datagrid('load', {
			orgName : $('#toolbar input[name=orgName]').val(),
			orgCode : $('#toolbar input[name=orgCode]').val()
		});
	}
</script>



</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
			<fieldset class="my_fieldset">
				<legend class="my_legend">检索</legend>
				<table>
					<tr>
						<td>项目名称：</td>
						<td colspan="2"><input name="orgName" class="basic_input" />
						</td>
						<td >&nbsp;项目id：</td>
						<td colspan="2"><input name="orgCode" class="basic_input" />
						</td>
						<td>&nbsp;<a class="easyui-linkbutton" iconCls="icon-search"  onclick="searchFun();" href="javascript:void(0);">查 找</a>&nbsp;<a class="easyui-linkbutton" iconCls="icon-empty"  onclick="clearFun();" href="javascript:void(0);">清 空</a>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<table id="datagrid" border="1"></table>
	</div>
</body>
</html>