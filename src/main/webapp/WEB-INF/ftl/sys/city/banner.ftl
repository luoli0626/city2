<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>

<link href="${static}/um/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<link id="easyuiTheme" href="${static}/css/stylesContent.css" rel="stylesheet" type="text/css" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script type="text/javascript" charset="utf-8" src="${static}/um/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${static}/um/umeditor.min.js"></script>
    <script type="text/javascript" src="${static}/um/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var positionDialog;
	
		    
	$(function() {
		
		positionForm = $('#positionForm').form();

		datagrid = $('#datagrid').datagrid({
			url : '${ctx}/cityPage/bannerList',
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
				field : 'type',
				title : '文章类型',
				width :$(this).width()*0.15,
			},
			{
				field : 'title',
				title : '文章标题',
				width :$(this).width()*0.15,
			},
			{
				field : 'img',
				title : '预览图片',
				width :$(this).width()*0.2,
				formatter:function(value,rec,i){
					if(value!=null&&value!=""){
						return "<img src='"+value+"' style='width:50px;height:50px;'>";
					}
				}
			},
			{
				field : 'orderNum',
				title : '轮播顺序',
				width :$(this).width()*0.15,
			},
			{
			    title : '操作',
			    field : '1',
			    width : $(this).width() * 0.25,
			    formatter:function(value,rec,i){
			    var btnHtml="";   
			    btnHtml+="<a href='javascript:remove("+i+");' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a >";
			    btnHtml+="<a href=' javascript:isOnline("+i+");' plain='true'  iconcls='icon-reload' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-reload' style='padding-left: 20px;'>更换轮播顺序</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		
		positionDialog = $('#positionDialog').show().dialog({
			modal : true,
			title : '添加外部链接的轮播',
			width:1000,
			height:700,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(positionForm.form('validate')){
						$("#showContent").html(um.getContent());
						var formData={
								"code":"3",
								"remark":$("#messageTitle").val(),
						};	
						if($("#messageId").val()==""){
							var imgs=$("#showContent").find("img");
							console.log(imgs.length);
							if (imgs.length != 0) {
									formData.photoName= imgs[0].src;
									console.log(imgs[0].src);
							}
						}
						$.ajax({
							url:'${ctx}/cityPage/addBanner',
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
								if (result.success) {	
									console.log(result.success);					 
									positionDialog.dialog('close');
									$.messager.alert('成功',"轮播地址添加成功！");
									datagrid.datagrid('reload');
								}else{
									positionDialog.dialog('close');
									$.messager.alert('失败',"轮播数量已经达到上线");
									datagrid.datagrid('reload');
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
		
		
			   //实例化编辑器
		    var um = UM.getEditor('messageContent');
		    um.setWidth("100%");
		    window.um=um;
		    $(".edui-body-container").css("width", "98%");
		
	});
		
	
	function append(flag) {
		
		$('#positionDialog').dialog('setTitle', '<font">添加轮播地址</font>');  
		$('#positionDialog').dialog('open');
		positionForm.form('clear');
		um.setContent("");
	}
	
	
	function remove(index) {
		var ids = [];
		var rows = $("#datagrid").datagrid("getRows")[index];
		if (rows) {
			$.messager.confirm('请确认', '您要删除当前所选轮播（不会删除原文）？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removeBanners',
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
	

    
	function isOnline(index) {
		var ids = [];
		var rows = $("#datagrid").datagrid("getRows")[index];
		if (rows) {
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/changeBanners',
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
			<div class="buttonList">
				<a class="easyui-linkbutton" iconCls="icon-add" plain='true' onclick="append('1');"  href="javascript:void(0);">增加</a> 
			</div>
			</div>
		<table id="datagrid" border="1"></table>
	</div>
	
	<div id="positionDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="positionForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">新增banner</legend>
				<table class="tableForm">
					<tr style="display: none;">
						<td id="messageId" name="messageId">
						</td>
					</tr>
					<tr>
						<th >请添加内容链接：</th>
						<td>
							<input name="messageTitle" id="messageTitle" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
				
				 <tr>
                	 <th>上传仅一张封面展示图：</th>
              	 </tr>
                	 <tr>
                	 <td colspan="4">
			    	 <textarea id="messageContent" name="messageContent" style="width:800px;height:400px;" >
			    	 </textarea>
			    	     <div id="showContent" style="display:none;"></div> 
     					 </div>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>
