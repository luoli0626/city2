<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<#assign requestURI = springMacroRequestContext.getRequestUri() />

<script type="text/javascript" charset="UTF-8">
	var editRow;
	var treegrid;
	var functionData = [{key:'0',value:'菜单'},{key:'1',value:'功能点'}]
	
	var iconData = [ {
		iconcls : '',
		text : '默认图标'
	}, {
		iconcls : 'icon-add',
		text : 'icon-add'
	}, { 
		iconcls : 'icon-edit',
		text : 'icon-edit'
	}, {
		iconcls : 'icon-remove',
		text : 'icon-remove'
	}, {
		iconcls : 'icon-save',
		text : 'icon-save'
	}, {
		iconcls : 'icon-cut',
		text : 'icon-cut'
	}, {
		iconcls : 'icon-ok',
		text : 'icon-ok'
	}, {
		iconcls : 'icon-no',
		text : 'icon-no'
	}, {
		iconcls : 'icon-cancel',
		text : 'icon-cancel'
	}, {
		iconcls : 'icon-reload',
		text : 'icon-reload'
	}, {
		iconcls : 'icon-search',
		text : 'icon-search'
	}, {
		iconcls : 'icon-print',
		text : 'icon-print'
	}, {
		iconcls : 'icon-help',
		text : 'icon-help'
	}, {
		iconcls : 'icon-undo',
		text : 'icon-undo'
	}, {
		iconcls : 'icon-redo',
		text : 'icon-redo'
	}, {
		iconcls : 'icon-back',
		text : 'icon-back'
	}, {
		iconcls : 'icon-sum',
		text : 'icon-sum'
	}, {
		iconcls : 'icon-tip',
		text : 'icon-tip'
	},{
		iconcls : 'icon-up',
		text : 'icon-up'
	},{
		iconcls : 'icon-down',
		text : 'icon-down'
	},{
		iconcls : 'icon-deployment',
		text : 'icon-deployment'
	},{
		iconcls : 'icon-handle',
		text : 'icon-handle'
	},{
		iconcls : 'icon-export',
		text : 'icon-export'
	},{
		iconcls : 'icon-import',
		text : 'icon-import'
	},{
		iconcls : 'icon-return',
		text : 'icon-return'
	},{
		iconcls : 'icon-changeState',
		text : 'icon-changeState'
	},{
		iconcls : 'icon-lend',
		text : 'icon-lend'
	},{
		iconcls : 'icon-sign',
		text : 'icon-sign'
	},{
		iconcls : 'icon-apply',
		text : 'icon-apply'
	} ,{
		iconcls : 'icon-transformation',
		text : 'icon-transformation'
	},{
		iconcls : 'icon-haveholder',
		text : 'icon-haveholder'
	},{
		iconcls : 'icon-quit',
		text : 'icon-quit'
	},{
		iconcls : 'icon-transfer',
		text : 'icon-transfer'
	},{
		iconcls : 'icon-destroy',
		text : 'icon-destroy'
	},{
		iconcls:'icon-batch',
		text:'icon-batch'
	}  ];
	$(function() {
		
		treegrid = $('#treegrid').treegrid({
			url : '${ctx}/menu/treegrid',
			rownumbers:true,
			toolbar : [
			<#if fmfn.checkButton(requestURI,"icon-redo")>
			'-',{
				
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('expandAll', node.id);
					} else {
						treegrid.treegrid('expandAll');
					}
				}
			}, 
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-undo")>
			'-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('collapseAll', node.id);
					} else {
						treegrid.treegrid('collapseAll');
					}
				}
			}, 
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-reload")>
			'-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					editRow = undefined;
					treegrid.treegrid('reload');
				}
			},
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-add")>
			 '-', {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			},
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-remove")>
			 '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove1();
				}
			},
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-edit")>
			 '-', {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			},
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-save")>
			 '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (editRow) {
						treegrid.treegrid('endEdit', editRow.id);
					}
				}
			},
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-compile")>
			 '-', {
				text : '取消编辑',
				iconCls : 'icon-compile',
				handler : function() {
					if (editRow) {
						treegrid.treegrid('cancelEdit', editRow.id);
						editRow = undefined;
					}
				}
			},
			</#if>
			<#if fmfn.checkButton(requestURI,"icon-cancle")>
			 '-', {
				text : '取消选中',
				iconCls : 'icon-cancle',
				handler : function() {
					treegrid.treegrid('unselectAll');
				}
			}, 
			</#if>
			'-' ],
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : true,
			nowrap : true,
			animate : false,
			border : false,
			idField : 'id',
			treeField : 'text',
			frozenColumns : [ [{
				field : 'text',
				title : '菜单名称',
				width : 200,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			columns : [ [  {
				field : 'parentId',
				title : '上级菜单',
				width : 200,
				formatter : function(value, rowData, rowIndex) {
					return rowData.parentText;
				},
				editor : {
					type : 'combotree',
					options : {
						url : '${ctx}/menu/treeAll',
						animate : false,
						lines : !sy.isLessThanIe8(),
						onLoadSuccess : function(node, data) {
							var t = $(this);
							if(editRow._parentId){
								var pNode=t.tree("find",editRow._parentId);
								t.tree("collapseAll");
								t.tree("expandTo",pNode.target);
								t.tree("select",pNode.target);
								
							}else{
								t.tree("collapseAll");
							}
						}
					}
				}
			}, {
				field : 'parentText',
				title : '上级菜单',
				width : 80,
				hidden : true
			},{
				field : 'func',
				title : '类型',
				width : 150,
				formatter : function(v) {
					if('0' == v) {
						return "菜单";
					} else if('1' == v) {
						return "功能点";
					} else {
						return "";
					}
			    },
				editor : {
					type : 'combobox',
					options : {
						valueField : 'key',
						textField : 'value',
						panelHeight : '40',
						data : functionData,
						formatter : function(v) {
							return v.value;
						}
					}
				}
			},{
				field : 'iconCls',
				title : '菜单图标',
				width : 150,
				editor : {
					type : 'combobox',
					options : {
						valueField : 'iconcls',
						textField : 'text',
						panelHeight : '200',
						data : iconData,
						formatter : function(v) {
							return sy.fs('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.iconcls, v.text);
						}
					}
				}
			}, {
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
			
			/*
			{
				title : 'ID',
				field : 'id',
				width : 100
			}, 
			
			*/
			{
				field : 'src',
				title : '菜单地址',
				width : 200,
				editor : {
					type : 'text'
				}
			} ] ],
			onDblClickRow : function(row) {
				if (editRow) {
					myMessage('您没有结束之前编辑的数据，请先保存或取消编辑！');
				} else {
					treegrid.treegrid('beginEdit', row.id);
					editRow = row;
				}
			},
			onAfterEdit : function(row, changes) {
				if (row.parentId != row.id) {
					$.ajax({
						url : '${ctx}/menu/edit',
						data : JSON.stringify(row),
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(r) {
							if (r.success) {
								myMessage(r.msg)
								treegrid.treegrid('reload');
								//parent.tree.tree('reload');
								editRow = undefined;
							} else {
								myMessage("保存菜单失败")
							}
						}
					});
				} else {
				    myMessage("保存失败，上级菜单不可以是自己");
				    editRow =undefined;
				}
			},
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function(row, data) {
				
			},
			onExpand : function(row) {
				treegrid.treegrid('unselectAll');
			},
			onCollapse : function(row) {
				treegrid.treegrid('unselectAll');
			}
		});

	});

	function edit() {
	 
		if (editRow) {
		    myMessage("您没有结束之前编辑的数据，请先保存或取消编辑！");
		} else {
			var node = treegrid.treegrid('getSelected');
			if (node && node.id) {
				treegrid.treegrid('beginEdit', node.id);
				editRow = node;
			}else{
				 myMessage("请选择要编辑的数据!");
			}
		}
	}
	function append() {
		// treegrid.treegrid('collapseAll');
		if (editRow) {
		    myMessage("您没有结束之前编辑的数据，请先保存或取消编辑！");
		} else {
			var node = treegrid.treegrid('getSelected');
			console.log(node);
			var data = [ {
				id : '',
				text : '新菜单',
				src : '',
				seq : 999,
				state:'0',
				func:'0',
				parentId : (node ? node.id : undefined)
			} ];
			var opts = {
				parent : data[0].parentId,
				data : data
			};
			$.ajax({
				url : '${ctx}/menu/add',
				data : data[0],
				cache : false,
				dataType : "json",
				success : function(r) {
					if (r.success) {
						opts.data[0].id=r.msg;
						treegrid.treegrid('append', opts);
						treegrid.treegrid('beginEdit', opts.data[0].id);
						editRow = opts.data[0];
						treegrid.treegrid("expandTo",opts.data[0].id);
					} else {
					    myMessage("添加菜单失败!");
					}
				}
			});
		}
	}
	function remove1() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			if (node.id == '0') {
			    myMessage("不能删除根节点!");
				return;
			}
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】菜单？', function(b) {
				if (b) {
					$.ajax({
						url : '${ctx}/menu/del',
						data : {
							id : node.id
						},
						cache : false,
						dataType : "json",
						success : function(r) {
							if (r.success) {
								treegrid.treegrid('reload');
								//parent.tree.tree('reload');
								myMessage(r.msg);
								editRow = undefined;
							} else {
							    myMessage("删除菜单失败!");
							}
						}
					});
				}
			});
		}
	}
	function myMessage(mg)
	{
	   $.messager.alert('提示',mg);
	}
</script>
</head>
<body id ="menuid" class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;margin-top:10px;">
		<table id="treegrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<#if fmfn.checkButton(requestURI,"icon-add")>
			<div onclick="append();" iconCls="icon-add">增加</div>
		</#if>
		<#if fmfn.checkButton(requestURI,"icon-remove")>
			<div onclick="remove1();" iconCls="icon-remove">删除</div>
		</#if>
		<#if fmfn.checkButton(requestURI,"icon-edit")>
			<div onclick="edit();" iconCls="icon-edit">编辑</div>
		</#if>
	</div>
</body>
</html>