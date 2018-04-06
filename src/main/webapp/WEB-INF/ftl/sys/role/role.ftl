<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<link rel="stylesheet" href="${static}/js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${static}/js/zTree_v3-master/js/jquery.ztree.all-3.5.min.js"></script>
<#assign requestURI = springMacroRequestContext.getRequestUri() />
<script type="text/javascript" charset="UTF-8">
	var editRow;
	var datagrid;
	var roleDialog;
	var roleForm;
	var restree;
	var organTree;
	var resdialog;
	var expandAllFlg = true;
	var checkAllTrueOrFalseFlg = true;
	var setting;
	var datagridUser;
	var userDialog;
	var userForm;
	$(function() {
		setting = {
			check: {
				enable: true,
				dblClickExpand: false
			},view: {
				fontCss: getFontCss
			},callback: {
				onClick: onClick
			}
		};

		userForm = $("#userForm").form();
		roleForm = $('#roleForm').form();
		roleDialog = $('#roleDialog').show().dialog({
		modal : true,
		title : '角色信息',
		width:400,
		height:"auto",
		buttons : [ {
			text : '确定',
			style:'text-align:center',
			handler : function() {
				/*var checked = $('#resourcesList').combotree('tree').tree('getChecked',['checked','indeterminate']); 
				//通过js来遍历选中节点的父节点
				for(var i=0;i<checked.length;i++){
					getParentNodeId("resourcesList",checked[i])
				}
				var val = $('#resourcesList').combotree('getValues');
				var str ="";
				if(val.length>0){
					for(var j=0;j<nodeIds.length;j++){
						val.push(nodeIds[j]);
					}
					val=unique(val)
					str+=val[0];
					for(var i=1;i<val.length;i++){
						str+=","+val[i];
					}
				}
				$("#thisResourcesId").val(str);*/
					var ids = "";
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
					var nodes = treeObj.getCheckedNodes(true);
					//alert(nodes.length);
					for(var i=0;i<nodes.length;i++){
						ids+=i==0?nodes[i].id:","+nodes[i].id;
					}
					$("#thisResourcesId").val(ids);
				var formData={
						"text":$('[name=text]').val(),
						"descript":$('[name=descript]').val(),
						"seq":$('[name=seq]').val(),
						"taskNumber":$('[name=taskNumber]').val(),
						"taskRate":$('[name=taskRate]').val(),
						"taskRate1":$('[name=taskRate1]').val(),
						"highestScore":$('[name=highestScore]').val()
				}
				//alert(ids);
				//拥有菜单
				var menus=[];
				if(ids){
					var menuIds=ids.split(",");
					for(var i=0;i<menuIds.length;i++){
						menus.push({"id":menuIds[i]});
					}
				}
				
				
				
				//var organization={"id":$("#orgId").combotree("getValue")}
				
				
				formData.menus=menus;
				
				//formData.organization=organization;
				
				
				if (roleForm.form('validate')) {
					var url="${ctx}/role/add";
					var msg="新增";
					if ($("#roleForm").find('[name=id]').val() != '') {
						url='${ctx}/role/edit';
						formData.id=$('[name=id]').val();
						msg="修改";
					}
					$.ajax({
						url:url, 
						data:JSON.stringify(formData),
						contentType:"application/json",
			 			success:function(result){
							if (result.success) {
								roleDialog.dialog('close');
								$.messager.alert('角色'+msg,"保存成功");
								datagrid.datagrid('reload');
							}else{
								$.messager.alert('角色'+msg,"保存失败");
							}
						}
					});
				}
			}
		}
	]}).dialog('close');

	
	
		userDialog=$("#userDialog").show().dialog({
		title: '查询员工信息',   
   	 	width:600,   
    	height: 450,   
    	closed: false,   
    	cache: false,   
    	modal: true,
    	buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					var user=[];
				    var rows = datagridUser.datagrid('getSelections');
					for ( var i = 0; i < rows.length; i++) {
						user.push({id:rows[i].id});
						
					}
						var formData={
						"id":$('#roleiid').val()
					}
				
				 formData["users"]=user;
					$.ajax({
						url:'${ctx}/role/edit', 
						data:JSON.stringify(formData),
						contentType:"application/json",
			 			success:function(result){
							if (result.success) {
								userDialog.dialog('close');
								$.messager.alert('用户'+msg,"保存成功");
								datagridUser.datagrid('reload');
							}else{
								$.messager.alert('用户'+msg,"保存失败");
							}
						}
					});
				
				
			
				}
			} ] 
		

		}).dialog('close');
	
	

	
	datagridUser = $('#datagridUser').datagrid({
			url : '${ctx}/user/datagrid',
			rownumbers:true,
			toolbar : '',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize:30,
			border:1,
			pageList:[10,20,30,50,100],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				field : 'loginAcct',
				title : '登录帐号',
				width : 100,
				sortable : true
			} ] ],
			columns : [ [ 
				 {
					field : 'nickName',
					title : '昵称',
					width : 80,
					sortable : true
				},
			{
				field : 'organization',
				title : '所属机构',
				width : 100,
				formatter:function(value,row){
					if(value){
						return value.orgName;
					}
					return "";
				}
			} ] ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				$(this).datagrid('selectRow', rowIndex);
			}
		});
	
	
	
	
	
	
	datagrid = $('#datagrid').treegrid({
		url : '${ctx}/role/treegrid',
		rownumbers:true,
		singleSelect:true,
		title : '',
		toolbar:'#toolbar',
		iconCls : 'icon-save',
		fit : true,
		nowrap : false,
		animate : false,
		border : false,
		idField : 'id',
		treeField : 'text',
		frozenColumns : [ [{
			title : 'id',
			field : 'id',
			width : 50,
			hidden : true
		}, {
			field : 'text',
			title : '角色名称',
			width : 200,
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		} ] ],
		columns : [ [{
			field : 'descript',
			title : '描述',
			width : 300,
			editor : {
				type : 'text'
			}
		}, {
			field : 'parentText',
			title : '上级角色',
			width : 80,
			hidden : true
		}
		
	
		
		
		, {
			field : 'organization',
			title : '所属机构',
			width : 100,
			hidden:true,
			formatter:function(value,row){
				if(value){
					return value.orgName;
				}
				return "";
			}
		},
		
		
		
		
		{
			field : 'seq',
			title : '排序',
			width : 50,
			editor : {
				type : 'numberbox',
				options : {
					min : 0,
					max : 999,
					required : true
				}
			}
		},
		{
			field : '1',
			title : '操作',
			width : 350,
			formatter:function(value,row){
					var org=row.organization;
					var orgId="";
					if(org){
						orgId=org.id;
					}
					var str = "{id:\""+row.id+"\",text:\""+row.text+"\",descript:\""+row.descript+"\",seq:\""+row.seq+"\",resourcesText:\""+row.resourcesText+"\",organization:\""+orgId+"\",resourcesId:\""+row.resourcesId+"\",taskNumber:\""+row.taskNumber+"\",taskRate:\""+row.taskRate+"\",taskRate1:\""+row.taskRate1+"\",highestScore:\""+row.highestScore+"\"}";
					var res = "{id:\""+row.id+"\"}";
					var btnHtml="";
					<#if fmfn.checkButton(requestURI,"icon-edit")>
						btnHtml="<a href='javascript:edit("+str+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>编辑</span></span></a>";
					</#if>
					<#if fmfn.checkButton(requestURI,"icon-remove")>
						btnHtml+="<a href='javascript:remove("+str+");' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a>";
					</#if>
					<#if fmfn.checkButton(requestURI,"icon-search")>
						btnHtml+="<a href ='javascript:showResources("+res+")' iconcls='icon-search' class='easyui-linkbutton l-btn l-btn-plain' plain='true'><span class='l-btn-left'><span class='l-btn-text icon-search' style='padding-left: 20px;'>查看菜单</span></span></a>";
					</#if>
					
					//btnHtml+="<a href='javascript:addUser("+str+");' plain='true'  iconcls='icon-add' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>新增用户</span></span></a>";

					return btnHtml; 
			}
		} ] ],
		onContextMenu : function(e, row) {
			e.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('select', row.id);
		},
		onLoadSuccess : function(row, data) {
			var t = $(this);
			if (data) {
				$(data).each(function(index, d) {
					if (this.state == 'closed') {
						t.datagrid('expandAll');
					}
				});
			}
		}
		});
		if(null==organTree){
			organTree=$('[name=orgId]').combotree({
				url : '${ctx}/organ/findOrgTreeByLoginUser',
				animate : false,
				lines : !sy.isLessThanIe8(),
				width:200,
				panelWidth:200,
				onLoadSuccess : function(node, data) {
					var t = $(this);
					if (data) {
						$(data).each(function(index, d) {
							if (this.state == 'closed') {
								t.tree('expandAll');
							}
						});
					}
				}
			});
		}
	});
	
	
	//角色新增用户
	function addUser(row){
	
	if(row){
	  //根据角色新增用户
     	datagridUser.datagrid('load', {

		});
     
     
	  userForm.form('clear');
	  $("#roleiid").attr("value",row.id);  
	   userDialog.dialog('open');
	
	}
	
	 
	  
	
	
	}
	
	
	
	
	//加载菜单树
	function loadMenusTree(id){
		$.ajax({
			url:"${ctx}/menu/getMenusByPid",
			type:"post",
			data:{roleId:id,parentId:0},
			dataType:"text",
			success:function(data, textStatus){
				var zNodes = eval('('+data+')');
				$.fn.zTree.init($("#treeDemo2"), setting, zNodes);
				$("#role_name").focus();
			},
			error:function(){
				alert("error");
			}
		});
			//全部展开	
		$("#expandOrCollapseAllBtn").bind("click", {type:"expandOrCollapse"}, expandNode);
		$("#checkAllTrueOrFalse").bind("click", {type:"checkAllTrueOrFalse"}, expandNode);
	}
	function onClick(e,treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
		zTree.expandNode(treeNode);
	}
			
	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	function expandNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo2"),
		type = e.data.type,
		nodes = zTree.getSelectedNodes();
		if(type == "expandAll") {
			zTree.expandAll(true);
		}else if (type == "collapseAll") {
			zTree.expandAll(false);
		}else if(type == "expandOrCollapse") {
			zTree.expandAll(expandAllFlg);
			expandAllFlg = !expandAllFlg;
		}else if (type == "checkAllTrueOrFalse") {
			zTree.checkAllNodes(checkAllTrueOrFalseFlg);
			checkAllTrueOrFalseFlg = !checkAllTrueOrFalseFlg;
		}else{
			if(type.indexOf("All")<0 && nodes.length == 0) {
				alert("请先选择一个父节点");
			}
			var callbackFlag = $("#callbackTrigger").attr("checked");
			for(var i=0, l=nodes.length; i<l; i++) {
				zTree.setting.view.fontCss = {};
				if(type == "expand") {
					zTree.expandNode(nodes[i], true, null, null, callbackFlag);
				}else if(type == "collapse") {
					zTree.expandNode(nodes[i], false, null, null, callbackFlag);
				}else if(type == "toggle") {
					zTree.expandNode(nodes[i], null, null, null, callbackFlag);
				}else if(type == "expandSon") {
					zTree.expandNode(nodes[i], true, true, null, callbackFlag);
				}else if(type == "collapseSon") {
					zTree.expandNode(nodes[i], false, true, null, callbackFlag);
				}
			}
		}
	}
	function showResources(row){
		var id = row.id;
		resdialog = $("#showRes").show().dialog({
			modal : true,
			title : '菜单信息',
			width:300,
			height:300,
			shadow:false
		});
		resdialog.dialog("close");
		restree = $('#restree').tree({
			url : '${ctx}/menu/showTree/'+id,
			animate : false,
			lines : !sy.isLessThanIe8(),
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if(data=='' || data==null || data==[]){
					alert('该角色没有菜单！');	}
				else {
					resdialog.dialog("open");
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('expandAll');
						}
					});
				}
			}
		});
	}
	function edit() {
		if (editRow) {
		    myMessage("您没有结束之前编辑的数据，请先保存或取消编辑！");
		} else {
			var node = datagrid.datagrid('getSelected');
			if (node && node.id) {
				datagrid.datagrid('beginEdit', node.id);
				editRow = node;
			}
		}
		
	}
	function edit(row){
			//console.log(row);
			roleDialog.dialog('open');
			roleForm.form('clear');
			organTree.combotree('setValue',row.organization);
			/*$("#resourcesList").combotree({
				url : '${ctx}/menu/showTree4check/'+row.id,
				animate : false,
				lines : !sy.isLessThanIe8(),
				onLoadSuccess : function(node, data) {
					var t = $(this);
					if (data) {
						$(data).each(function(index, d) {
							if (this.state == 'closed') {
								t.tree('expandAll');
							}
							if(this.children){
								initCheckedState("resourcesList",this)
							}
						});
					}
				}
			});*/
			
			var str = row.resourcesId+"";
			var strs= new Array(); //定义一数组
			if("null"!=str && null!=str && ""!=str){
				strs=str.split(","); //字符分割 
			} else{
				strs="";
			}
			var descript="";
			if("null"!=row.descript && null!= row.descript&& ""!=row.descript){
			  descript=row.descript;
			}else{
				descript="";
			} 
			//alert(descript+","+strs);
			roleForm.form('load', {
				id:row.id,
				text:row.text,
				descript:descript,
				seq : row.seq,
				taskRate : row.taskRate,
				taskNumber : row.taskNumber,
				taskRate1 : row.taskRate1,
				highestScore : row.highestScore,
				resourcesText:strs
			});
			loadMenusTree($("#roleId").val());
	}
	function append(){
		roleDialog.dialog('open');
		roleForm.form('clear');
		/*$("#resourcesList").combotree({
			url : '${ctx}/menu/treeAll',
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
			}
		});*/
		loadMenusTree($("#roleId").val());
	}
	function remove(node) {
	//console.log(datagrid);
		//var node = datagrid.datagrid('getSelected');
	//	console.log(node);
		var ids = new Array();
		ids.push(node.id);	
		if (node) {
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】角色？', function(b) {
				if (b) {
					$.ajax({
						url : '${ctx}/role/del?ids='+node.id,
						cache : false,
						dataType : "json",
						success : function(r) {
							if (r.success) {
								datagrid.datagrid('reload');
								myMessage(r.msg);
								editRow = undefined;
							} else {
							    myMessage("删除角色失败!");
							}
						}
					});
				}
			});
		}
	}
		/*提示消息弹出框函数*/
	function myMessage(mg)
	{
	   $.messager.alert('提示',mg);
	}
	var nodeIds=[];
	function getParentNodeId(treeId,node){
		var p= $('#'+treeId).combotree('tree').tree('getParent',node.target);
		if(p){
			 nodeIds.push(p.id);
			 var p1=$('#'+treeId).combotree('tree').tree('getParent',p.target);
			 if(p1){
			 	getParentNodeId(treeId,p)
			 }
		}
	}
	function unique(arr) {
		var result = [], isRepeated;
		for (var i = 0;i<arr.length; i++) {
			isRepeated = false;
			for (var j = 0;j < result.length; j++) {
				if (arr[i] == result[j]) {   
					isRepeated = true;
					break;
				}
			}
			if (!isRepeated) {
				result.push(arr[i]);
			}
		}
		return result;
	}
	//初始化权限菜单的选中状态
	function initCheckedState(treeId,node){
		var nodes=node.children;
		if(nodes){
			for(var i=0;i<nodes.length;i++){
				if(nodes[i]){
					if(!nodes[i].children){
						
						if(nodes[i].attributes.check){
							var c=$("#"+treeId).combotree("tree").tree("find",nodes[i].id);
							$("#"+treeId).combotree("tree").tree("check",c.target);
						}
					}else{
						initCheckedState(treeId,nodes[i]);
					}
				}
			}
		}
	}
</script>

</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar">
			<#if fmfn.checkButton(requestURI,"icon-add")>
				<a href="javascript:append();" class="easyui-linkbutton" iconCls="icon-add">新 增</a>
			</#if>
		</div>
		<table id="datagrid"></table>
	</div>
	<div id="roleDialog" style="display: none;overflow: hidden;background:#FFFFFF;padding:20px;">
		<form id="roleForm" method="post">
			<table class="tableForm">
				<tr>
					<th style="display:none;">角色ID</th>
					<td style="display:none;"><input name="id" id="roleId" readonly="readonly" /></td>
				</tr>
				<tr>
					<th width="80">角色名称：</th>
					<td><input name="text" class="easyui-validatebox" required="true" style="width:145px;" validType="length[1,100]" /></td>
				</tr>
				<tr>
					<th>描&#12288;&#12288;述：</th>
					<td><input name="descript" class="easyui-validatebox" style="width:145px;" validType="length[0,100]" /></td>
				</tr>
				<tr>
					<th>排&#12288;&#12288;序：</th>
					<td><input name="seq" class="easyui-numberbox" required="true" style="width:145px;"/></td>
				</tr>
				
				<tr>
					<th>角色类型：</th>
					<td>
					
					<select name="taskNumber" id="taskNumber"  required="true"  class="easyui-combobox"  style="width:145px;">   
					  <option value ="1">总部</option>
					     <option value ="2">分公司</option>
					      <option value ="3">项目</option>
					</select>  

					
					</td>
				</tr>
				
				<!--
				<tr>
					<th>角色任务量：</th>
					<td><input name="taskNumber" class="easyui-numberbox" required="true" style="width:145px;"/></td>
				</tr>
				<tr>
					<th>整改或审核达标率：</th>
					<td><input name="taskRate" class="easyui-numberbox" required="true" style="width:145px;"/></td>
				</tr>
				<tr>
					<th>关闭达标率：</th>
					<td><input name="taskRate1" class="easyui-numberbox" required="true" style="width:145px;"/></td>
				</tr>
				<tr>
					<th>最高得分：</th>
					<td><input name="highestScore" class="easyui-numberbox" required="true" style="width:145px;"/></td>
				</tr>
				
			
				<tr>
					<th>所属机构：</th>
					<td><select id="orgId" name="orgId" style="width: 200px;" required="true"></select></td>
				</tr>
				-->
				
				
				
				<tr>
					<th style="width:60px;">拥有菜单</th>
					<td style="width:250px;">
							<div id="optionDiv">
								[<a id="expandOrCollapseAllBtn" style="cursor:pointer;" title="展开/折叠全部资源" onclick="return false;">展开/折叠</a>]
								[<a id="checkAllTrueOrFalse" style="cursor:pointer;" title="全选/全不选" onclick="return false;">全选/全不选</a>]
							</div>
							<ul id="treeDemo2" style="padding-top:5px;height:215px;width:240px;overflow-x:hidden;" class="ztree"></ul>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div  id="showRes" class="easyui-tree" style="display: none;overflow: auto;background:#FFFFFF;height:300;">
		<div id="restree"></div>
	</div>
	
	<div id="userDialog" style="display: none;overflow: hidden;background:#FFFFFF;padding:20px;">

   	<form id="userForm" method="post">
   	  <input type="hidden" value="" id="roleiid" name="roleiid"/>
   	
	</form>

		<table id="datagridUser"></table>
	</div>
	
	
	
	
	
</body>
</html>