<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>

<link id="easyuiTheme" href="${static}/css/stylesContent.css" rel="stylesheet" type="text/css" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var positionDialog;
		    
	$(function() {
		
		positionForm = $('#positionForm').form();

		datagrid = $('#datagrid').datagrid({
			url : '${ctx}/cityPage/photoTypeList',
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
			idField : 'id',
			sortName : 'id',
			singleSelect : true,
			frozenColumns : [ [ {
				field : 'id',
				title : 'id',
				width : $(this).width()*0.1,
				sortable : true,
				hidden:true
			}] ],
			columns : [ [ {
				field : 'name',
				title : '随手拍分类名称',
				width :$(this).width()*0.15,
			},
			{
				field : 'createUserName',
				title : '添加者',
				width :$(this).width()*0.15,
			},
			{
				field : 'createTime',
				title : '添加时间',
				width :$(this).width()*0.15,
				formatter:function(value,rec,i){
					return timestampToTime(value);					
				}
			},
			{
			    title : '操作',
			    field : '1',
			    width : $(this).width() * 0.25,
			    formatter:function(value,rec,i){
			    var btnHtml="";   
			    btnHtml+="<a href=' javascript:edit("+i+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>编辑</span></span></a >";
			    btnHtml+="<a href='javascript:remove("+i+");' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		
		positionDialog = $('#positionDialog').show().dialog({
			modal : true,
			title : '添加随手拍分类',
			width: ($(window).width())*0.7,
   			height:($(window).height())*0.5,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(positionForm.form('validate')){
						var formData={
								"id":$("#id").val(),
								"name":$("#name").val(),
						};
						$.ajax({
							url:'${ctx}/cityPage/alterPhotoType',
							data:JSON.stringify(formData),
							contentType:"application/json",
                            method: 'POST',
				 			success:function(result){
								if (result.success) {	
									console.log(result.success);					 
									positionDialog.dialog('close');
									$.messager.alert('随手拍分类'+result.msg,"成功");
									datagrid.datagrid('reload');
								}else{
									positionDialog.dialog('close');
									$.messager.alert('随手拍分类'+result.msg,"成功");
									datagrid.datagrid('reload');
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
	});
		

	function searchFun() {
		datagrid.datagrid('load', {
			name : $('#toolbar input[name=title]').val(),
		});
	}
	
	function append(flag) {
		
		$('#positionDialog').dialog('setTitle', '<font">新增随手拍分类</font>');
		$('#positionDialog').dialog('open');
		positionForm.form('clear');
	}
	
	function edit(index){

		$('#positionDialog').dialog('setTitle', '<font">编辑随手拍分类</font>');
	        var rows = $("#datagrid").datagrid("getRows")[index];
	        console.log(rows);
			$('#positionDialog').dialog('open');
			positionForm.form('clear');
            $("#id").val(rows.id);
			positionForm.form('load', {
				"name":rows.name,
		});
	}
	
	
	
	function remove(index) {
		var ids = [];
		var rows = $("#datagrid").datagrid("getRows")[index];
		if (rows) {
			$.messager.confirm('请确认', '您要删除当前所选随手拍分类？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removePhotoType',
						data : JSON.stringify(ids),
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(data) {
							if(data.success){
								datagrid.datagrid('unselectAll');
								datagrid.datagrid('reload');
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}
	
    
    function add0(m){return m<10?'0'+m:m }
	function timestampToTime(nows)
	{
		var time = new Date(nows);
		var y = time.getFullYear();
		var m = time.getMonth()+1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
	}
	
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
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
						<td>分类名称：</td>
						<td colspan="2"><input name="title" class="basic_input" />
						</td>
						<td>&nbsp;<a class="easyui-linkbutton" iconCls="icon-search" plain='true' onclick="searchFun();" href="javascript:void(0);">查 找</a>&nbsp;
						<a class="easyui-linkbutton" iconCls="icon-empty"  plain='true' onclick="clearFun();" href="javascript:void(0);">清 空</a>
						</td>
					</tr>
				</table>
			</fieldset>
			
			
		    <div class="buttonList">
				<#if fmfn.checkButton(requestURI,"icon-add")>
					<a class="easyui-linkbutton" iconCls="icon-add" plain='true' onclick="append('1');"  href="javascript:void(0);">增加</a> 
				</#if>
			</div>
			
			
		</div>
		<table id="datagrid" border="1"></table>
	</div>
	
	<div id="positionDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="positionForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">随手拍分类详情</legend>
				<table class="tableForm">
					<tr style="display: none;">
						<td name="id" id="id" value="" >

                        </td>
					</tr>
					<tr>
						<th >分类名称：</th>
						<td>
							<input name="name" id="name" class="easyui-validatebox" required="true" value=""/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>
