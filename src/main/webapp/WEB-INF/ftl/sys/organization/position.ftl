<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<#assign requestURI = springMacroRequestContext.getRequestUri() />
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var positionDialog;
	var positionForm;
	$(function(){
		positionForm = $('#positionForm').form();
		datagrid=$('#datagrid').datagrid({    
    	url:'${ctx}/position/datagrid',
    	rownumbers:true, 
    	toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize:30,//每页显示的记录条数，默认为10 
        	pageList:[10,20,30,50,100],//可以设置每页记录条数的列表 
        	beforePageText: '第',//页数文本框前显示的汉字 
       		afterPageText: '页    共 {pages} 页', 
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
		fit : true,  
    	fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',	
			frozenColumns : [ [  
			{	title : 'id',
				field : 'id',
				width : 100,
				checkbox : true
			}
			] ], 
    	columns:[[   
        {
	        field:'positionName',
	        title:'岗位名称',
	        width:100,
	        sortable: true},    
        {
	        field:'described',
	        title:'描述',
	        width:100},    
        {
			title : '操作',
			field : '1',
			width : 50,
			formatter:function(value,rec,i){					
				var btnHtml="";
				<#if fmfn.checkButton(requestURI,"icon-edit")>
					btnHtml="<a href='javascript:EditPosition(\"${ctx}/position/updatePosition\")' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>编辑</span></span></a>";
				</#if>
				<#if fmfn.checkButton(requestURI,"icon-remove")>
					btnHtml+="<a href='javascript:removeMorer();' plain='true' iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a>";
				</#if>
				return btnHtml;
			}
			}]]    
		}); 
	});
	
	/*工具栏里面类容清空*/
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
	/*条件查找*/
	function searchFun() {
		datagrid.datagrid('load', {
			positionName : $('#toolbar input[name=spositionName]').val()
		});
	}
	/*提示消息弹出框函数*/
	function myMessage(mg)
	{
	   $.messager.alert('提示',mg);
	}
	/*新增消息*/
     	function AddPosition(url){
     		clearForm();
            openDialog(url);
     }
    /*清除所有选择项*/
    function clearSel(){
    	datagrid.datagrid('clearSelections');
    }
    /*清除表单缓存*/
    function clearForm(){
    	    positionForm.form('clear');
    		$('#positionNames').val('');
			$('#described').val('');
			$('#ids').val('');
    }
	/*批量删除*/
		function removeMorer() {	
			var ids = [];
			var rows = datagrid.datagrid('getSelections');
			if (rows.length > 0) {
				$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : '${ctx}/position/delPositionById',
							data : {
								 ids: ids
							},
							cache : false,
							dataType : "json",
							success : function(response) {
								datagrid.datagrid('unselectAll');
								datagrid.datagrid('reload');
								myMessage("删除成功");
							}
						});
					}
				});
			} else {
				$.messager.alert('提示', '请选择要删除的记录！', 'error');
			}
	 }
	 
	 /*打开Dialog*/		
	 function openDialog(urls){
			//positionDialog添加/编辑
			clearForm(); 
			positionDialog = $('#positionDialog').show().dialog({
			modal : true,
			title : '岗位管理',
			width:300,
			height:170,
			onClose:clearSel(),
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {						
					if(positionForm.form('validate')){
					$.ajax({
						url : urls,
						data : 
							 positionForm.serialize(),
						cache : false,
						dataType : "json",
						success : function(data) {
							datagrid.datagrid('reload');
							myMessage("操作成功");
							$('#positionDialog').dialog('close');
							clearSel();
						}
					});
					}
					
				}			
			} ]
		})
	}
    		
	/*编辑消息*/
	function EditPosition(url){    		
		var rows = datagrid.datagrid('getSelected'); 
		$.ajax({
			url : '${ctx}/position/searchPositionById',
			data : {id:rows.id},
			cache : false,
			dataType : "json",
			success : function(data) {
					$('#positionNames').val(data.obj.positionName);
					$('#described').val(data.obj.described);
					$('#ids').val(data.obj.id);
			}
		});
		openDialog(url);
     }
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
	
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;">
			<fieldset class="my_fieldset" >
				<legend class="my_legend">检索</legend>
				<table  id="table1" name="table1">
					<tr>
						<td width="70px;">岗位名称：</td>
						<td colspan="2">
						<input name="spositionName" id="spositionName" class="basic_input" />
						</td>
						<td align="left">
							&nbsp;
							<a class="easyui-linkbutton" iconCls="icon-search"  onclick="searchFun();" href="javascript:void(0);">查 找</a>
							<a class="easyui-linkbutton" iconCls="icon-empty"  onclick="clearFun();" href="javascript:void(0);">清 空</a>
						</td>			
					</tr>
				</table>
			</fieldset>
			<div class="buttonList">
				<#if fmfn.checkButton(requestURI,"icon-add")>
					<a href='javascript:AddPosition("${ctx}/position/savePosition")'  class="easyui-linkbutton" iconCls="icon-add"  >新  增</a>				
				</#if>
				<#if fmfn.checkButton(requestURI,"icon-remove")>
					<a fid="1111" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeMorer();"   href="javascript:void(0);">删 除</a>
				</#if>
			</div>	
		</div>
		<table id="datagrid"></table>
	</div>
		<div id="positionDialog" style="display: none;overflow: hidden;background:#FFFFFF;padding:20px 30px;">
			<form id="positionForm" method="post">
				<table class="tableForm">
					<tr>
						<th width="100" >岗位名称:</th>
						<td><input name="positionName" id="positionNames" class="easyui-validatebox" required="true" missingMessage="此项必须填写"/></td>
					</tr>
					<tr>
						<th width="100" >描&#12288;&#12288;述:</th>
						<td><input name="described" id="described" class="easyui-validatebox" required="true" missingMessage="此项必须填写" /></td>
					</tr>
					<tr>
						<td><input name="id" id="ids" style="display:none;" ></td>
					</tr>
				</table>
			</form>
	</div>
</body>
</html>