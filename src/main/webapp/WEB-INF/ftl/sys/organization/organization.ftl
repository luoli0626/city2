<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<#assign requestURI = springMacroRequestContext.getRequestUri() />
<script type="text/javascript" charset="UTF-8">
	var organDialog;
	var organForm;
	var selectedNodeId;
	var parentName;
	var url;
	var orgTypeData=${orgType!'[]'};
	$(function() {
		organForm=$("#organForm").form();
		organDialog = $('#organDialog').show().dialog({
			modal : true,
			title : "新增项目",
			width:400,
			height:380,
			onBeforeClose:function(){
				organForm.form('clear');
				$('#orgName').val('');
				$("#orgCode").val('');
				$('#described').val('');
				$('#parentName').val('');
				$('#parentId').val('');
			},
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					save();
				}
			} ]
		}).dialog('close');
		
		 /* 机构树的下拉选择*/
     	parentName=$('[name=parentName]').combotree({
			url : '${ctx}/organ/findOrgTreeByLoginUser',
			animate : false,
			lines : !sy.isLessThanIe8(),
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('expandAll');
						}
					});
				}
			},
	        onSelect : function(node) {
	          
	          $('#parentId').val(node.id);
		    }
		});
		
		var treegrid = $('#treegrid').treegrid({
			title : '',
			iconCls : 'icon-save',
			toolbar : '#toolbar',
			rownumbers:true,
			fit : true,
			nowrap : true,
			animate : false,
			border : false,
			idField : 'id',
			treeField : 'orgName',
			frozenColumns : [
			[
			{
				checkbox:true
			},
			{
				field : 'orgName',
				title : '项目名称',
				width : 200
			}]],
			columns : [[
			{
				field : 'orgDesc',
				title : '项目简介',
				width : 200
			}
			]],
			onLoadSuccess:function(row,data){
				$("#treegrid").treegrid("select",selectedNodeId)
			},
			onBeforeExpand:function(node){
				var root=$("#treegrid").treegrid("getRoot");
				if(node.id!=root.id){
					$("#treegrid").treegrid("options").url='${ctx}/organ/treegrid?orgId='+node.id;
					//$("#treegrid").treegrid("reload",node.id);
				}
				
			}
		});
		buildTreeGrid();
		
		
	});
	function buildTreeGrid(id){
		//销毁已存在的树
		$("#treegrid").empty();
		if(id){
			selectedNodeId=id;
		}else{
			selectedNodeId=0;
		}
		var rootData={total:1,rows:[{"id":0,"orgName":"金科物业服务有限公司","state":"closed"}]};
		//获取一级菜单信息
		$.ajax({
			url : '${ctx}/organ/treegrid',
			cache : false,
			dataType : "json",
			success : function(data) {
				rootData.rows[0].children=data.rows
				$("#treegrid").treegrid("loadData",rootData);
				$("#treegrid").treegrid("expand",0);
			}
		});
	}
	function remove(){
		var node=$("#treegrid").treegrid("getSelected");
		selectedNodeId=node.id;
		var root=$("#treegrid").treegrid("getRoot");
		if(!node){
			$.messager.alert('提示',"当前没有选中任何项目！");
			return false;
		}
		if(node.id==root.id){
			$.messager.alert('提示',"不能删除根节点！");
			return false;
		}
		$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
			if (r) {
				var ids = new Array();
			    ids.push(node.id);	
				$.ajax({
					url : '${ctx}/organ/del',
					data :JSON.stringify(ids),
					cache : false,
					dataType : "json",
					contentType:"application/json",
					success : function(d) {
						if(d){
							$.messager.alert('提示',"操作成功");
							var pid=$('#parentId').val();
							var root=$("#treegrid").treegrid("getRoot");
							var n=$("#treegrid").treegrid("getSelected");
							if(pid==root.id||n._parentId==null){
								buildTreeGrid(n.id);
							}else{
								selectedNodeId=n.id;
								$("#treegrid").treegrid("reload",n._parentId);
								$("#treegrid").treegrid("expandTo",n._parentId);
							}
							$('#organDialog').dialog('close');
							$(".validatebox-tip").css("display","none");
						}else{
								$.messager.alert('提示',"操作失败");
						}
					}
				});
			}
		});
	}
	function edit(){
		var node=$("#treegrid").treegrid("getSelected");
		var root=$("#treegrid").treegrid("getRoot");
		if(!node){
			$.messager.alert('提示',"当前没有选中任何项目！");
			return;
		}
		if(node.id==root.id){
			$.messager.alert('提示',"不能修改根节点！");
			return;
		}
		if(node._parentId==null){
			$.messager.alert('提示',"不能修改分公司！");
			return;
		}
		var values=[];
		$("#orgType").combobox({
			data:orgTypeData,
			valueField:'ID',
			textField:'NAME',
			onLoadSuccess:function(){
			   $("#orgType").combobox('setValue',node.orgType);
				
			},
			onChange:function(record){
				/*$("#area").combotree({
					url : '${ctx}/organ/areaTree4Org?orgId='+node.id+'&types='+$("#orgType").combobox('getValues').join(","),
					animate : false,
					lines : !sy.isLessThanIe8()
				});*/
			}
		});
		/*$("#area").combotree({
			url : '${ctx}/organ/areaTree4Org?orgId='+node.id+'&types='+values.join(","),
			animate : false,
			lines : !sy.isLessThanIe8()
		});*/
		if(!node._parentId){
			node.parentName="巡航管家";
		}
		organForm.form('load', {
			"id":node.id,
			"orgName":node.orgName,
			orgCode:node.orgCode,
			"orgDesc":node.orgDesc,
			"parentName":node.parentName,
			"parentId":node._parentId,
			"sort":node.sort,
			"correctionFactor":node.correctionFactor,
			"repeatNumber":node.repeatNumber
		});
		/*$.ajax({
			url : '${ctx}/organ/search',
			data : {id:node.id},
			cache : false,
			dataType : "json",
			success : function(data) {
				if(!data.parentId){
					data.parentName="根节点";
				}
				organForm.form('load', {
					"id":data.id,
					"orgName":data.orgName,
					orgCode:data.orgCode,
					"orgDesc":data.orgDesc,
					"parentName":data.parentName,
					"parentId":data.pid
				});
			}
		});*/
		parentName.combotree('setValue',node._parentId);
		openDialog(true);
	}
	function add(){
		var node=$("#treegrid").treegrid("getSelected");
		if(!node){
			$.messager.alert('提示',"当前没有选中任何项目！");
			return;
		}
		/*$("#area").combotree({
			url : '${ctx}/organ/areaTree',
			animate : false,
			lines : !sy.isLessThanIe8()
		});*/
		$("#orgType").combobox({
			data:orgTypeData,
			valueField:'ID',
			textField:'NAME',
			onLoadSuccess:function(){
			 $("#orgType").combobox('select',node.orgType);
				/*var values=[];
				if(node.orgType){
					$.each(node.orgType,function(){
						values.push(this.id);
					});
					if(values.length>0){
						$("#orgType").combobox('setValue',values);
					}
				}*/
			}
		});
		$('#parentId').val(node.id);
		//$('#parentName').val(node.orgName);
		parentName.combotree("reload");
		parentName.combotree('setValue',node.id);
		openDialog(false);
	}
	function save(){
		var formData={
			id:$("#id").val(),
			orgName:$("#orgName").val(),
			orgCode:$("#orgCode").val(),
			orgDesc:$("#orgDesc").val(),
			orgType:$("#orgType").combobox('getValue'),
			parentId:$("#parentId").val(),
			sort:$("#sort").val(),
			correctionFactor:$("#correctionFactor").val(),
			repeatNumber:$("#repeatNumber").val()
		};
		/*var checked = $('#area').combotree('tree').tree('getChecked',['checked','indeterminate']);
		var areas=[];
		$.each(checked,function(){
			areas.push({id:this.id});
		});
		formData.areas=areas;
		var orgType=$("#orgType").combobox('getValues');
		var orgTypes=[];
		$.each(orgType,function(){
			orgTypes.push({id:this});
		});
		formData.orgType=orgTypes;*/
		if(organForm.form('validate')){
			$.ajax({
				url : url,
				data : JSON.stringify(formData),
				cache : false,
				contentType:"application/json",
				dataType : "json",
				success : function(data) {
					$.messager.alert('提示',"操作成功");
					var n=$("#treegrid").treegrid("getSelected");
					buildTreeGrid(n.id);
					$('#organDialog').dialog('close');
				}
			});
		}
	}
    function openDialog(flag){
		var title="添加项目"
		url='${ctx}/organ/add'
		if(flag){
			title="修改项目"
			url='${ctx}/organ/update'
		}
		organDialog.panel({"title":title});
		$("#orgName").validatebox({
			required:true,
			missingMessage:"此项必须填写"
		});
		organDialog.dialog("open");
	}
	function move(flag){
		var node=$("#treegrid").treegrid("getSelected");
		var root=$("#treegrid").treegrid("getRoot");
		if(!node){
			$.messager.alert('提示',"当前没有选中任何项目！");
			return;
		}
		if(node.id==root.id){
			$.messager.alert('提示',"不能修改根节点！");
			return;
		}
		$.ajax({
			url : '${ctx}/organ/updateSort',
			data : {"id":node.id,"moveFlag":flag},
			cache : false,
			dataType : "json",
			success : function(data) {
				var root=$("#treegrid").treegrid("getRoot");
				var n=$("#treegrid").treegrid("getSelected");
				if(n._parentId==null){
					buildTreeGrid(n.id);
				}else{
					selectedNodeId=n.id;
					$("#treegrid").treegrid("reload",n._parentId);
					
				}
				
			}
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div region="center" border="false">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;">
			<div class="buttonList_no_search">
				<#if fmfn.checkButton(requestURI,"icon-add")>
					<a href="javascript:add();" class="easyui-linkbutton" iconCls="icon-add"  >新  增</a>
				</#if>
				<#if fmfn.checkButton(requestURI,"icon-edit")>	
					<a href="javascript:edit();" class="easyui-linkbutton" iconCls="icon-edit"  >修  改</a>
				</#if>
				<#if fmfn.checkButton(requestURI,"icon-remove")>
					<a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-remove"  >删 除</a>
				</#if>
				<!--<#if fmfn.checkButton(requestURI,"icon-up")>
					<a href="javascript:move(1);" class="easyui-linkbutton" iconCls="icon-up"  >上 移</a>
				</#if>
				<#if fmfn.checkButton(requestURI,"icon-down")>
					<a href="javascript:move(0);" class="easyui-linkbutton" iconCls="icon-down"  >下 移</a>
				</#if>-->
			</div>
		</div>
		<table id="treegrid" ></table>
	</div>
	<div id="organDialog" style="display: none;overflow: hidden;background:#FFFFFF;padding:20px 20px;">
			<form id="organForm" method="post">
				<table class="tableForm">
					<tr>
						<th width="100" >项目名称：</th>
						<td>
							<input name="orgName" id="orgName"/>
							<input name="id" id="id" type="hidden" >
						</td>
					</tr>
					<tr>
						<th width="100" >项目编码：</th>
						<td><input name="orgCode" id="orgCode" class="textbox" readonly="readonly" /></td>
					</tr>
					<tr>
						<th width="100" >描&#12288;&#12288;述：</th>
						<td><input name="orgDesc" id="orgDesc" class="textbox" /></td>
					</tr>
					<tr>
						<th width="100" >上级项目：</th>
						<td>
							<input id="parentName" name="parentName"  style="width:200px;" class="textbox" readonly="true" />
							<input name="parentId" id="parentId" type="hidden" >
						</td>
					</tr>
					<tr>
						<th width="100" >业务类型：</th>
						<td>
							<input id="orgType" name="orgType.code" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<th width="100" >业务基数数量：</th>
						<td>
							<input id="sort" name="sort"  class="easyui-numberbox" max="100"   style="width:200px;"/>
						</td>
					</tr>
					<!--<tr>
						<th width="100" >修正系数：</th>
						<td>
							<input id="correctionFactor" name="correctionFactor"  class="easyui-numberbox" max="100"   style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<th width="100" >允许重复条数：</th>
						<td>
							<input id="repeatNumber" name="repeatNumber"  class="easyui-numberbox" max="10"   style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<th width="100" >区域：</th>
						<td>
							<input id="area" name="areas.id" class="easyui-combotree" multiple style="width:200px;"/>
						</td>
					</tr>-->
				</table>
			</form>
	</div>
</body>
</html>