<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>

<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var positionDialog;
	var changeDialog;
	
	$.extend($.fn.validatebox.defaults.rules, {  
       equaldDate: {  
           validator: function (value, param) {  
               var start = $("#startTime").datetimebox('getValue');  //获取开始时间    
               return value > start;                             //有效范围为当前时间大于开始时间    
           },  
           message: '结束日期应大于开始日期!'                     //匹配失败消息  
       }  
   });  
		    
		    
	$(function() {
		
		positionForm = $('#positionForm').form();

		datagrid = $('#datagrid').datagrid({
			url : '${ctx}/cityPage/photoList',
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
			frozenColumns : [ [ {
				field : 'id',
				title : 'id',
				width : $(this).width()*0.1,
				sortable : true,
				hidden:true
			}] ],
			columns : [ [ {
				field : 'content',
				title : '问题描述',
				width :$(this).width()*0.15,
			},
			{
				field : 'images',
				title : '预览图片',
				width :$(this).width()*0.2,
				formatter:function(value,rec,i){
					if(value.length!=0){
						return "<img src='"+value[0].address+"' style='width:50px;height:50px;'>";
					}
				}
			},
			{
				field : 'createUserName',
				title : '上传者',
				width :$(this).width()*0.15,
				formatter:function(value,rec,i){
					return rec.createUserName.nickName;
				}
			},
			{
				field : 'createTime',
				title : '上传时间',
				width :$(this).width()*0.15,
			},	
			{
				field : 'state',
				title : '跟进状态',
				width :$(this).width()*0.15,
				formatter:function(value,rec,i){
					if(value=='1'){
						return "待跟进";
					}else if(value=='2'){
						return "跟进中";					
					}else if(value=='3'){
						return "处理完成";
					}
				}
			},
			{
			    title : '操作',
			    field : '1',
			    width : $(this).width() * 0.25,
			    formatter:function(value,rec,i){
			    var btnHtml="";   
			    btnHtml+="<a href='javascript: changeState("+i+");' plain='true'  iconcls='icon-changeSate' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-changeSate' style='padding-left: 20px;'>更改状态</span></span></a >";
			    btnHtml+="<a href=' javascript:edit("+i+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>详情</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		
		positionDialog = $('#positionDialog').show().dialog({
			modal : true,
			title : '添加政策资讯',
			width:1000,
			height:800,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(positionForm.form('validate')){
						$("#showContent").html(um.getContent());
						var formData={
								"messageId":$("#messageId").val(),
								"messageTitle":$("#messageTitle").val(),
								"messageSubTitle":$("#messageSubTitle").val(),
								"messageContent":$("#messageContent").val(),
						};	
						if($("#messageId").val()==""){
							var imgs=$("#showContent").find("img");
							console.log(imgs.length);
							if (imgs.length != 0) {
									formData.messageImage= imgs[0].src;
									console.log(imgs[0].src);
							}
						}
						console.log($("#showContent").html());
						$.ajax({
							url:'${ctx}/cityPage/alterMessage',
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
								if (result.success) {	
									console.log(result.success);					 
									positionDialog.dialog('close');
									$.messager.alert('政策资讯'+result.msg,"成功");
									datagrid.datagrid('reload');
								}else{
									positionDialog.dialog('close');
									$.messager.alert('政策资讯'+result.msg,"成功");
									datagrid.datagrid('reload');
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
		
		changeDialog=$("#changeDialog").show().dialog({
			modal : true,
			title : '处理详情',
			width:500,
			height:300,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(changeForm.form('validate')){
						var formData={
								"code":$("#messageId").val(),
								"state":$("#messageTitle").val(),
								"remark":$("#messageSubTitle").val(),
								"photoId":$("#messageContent").val(),
						};	
						if($("#messageId").val()==""){
							var imgs=$("#showContent").find("img");
							console.log(imgs.length);
							if (imgs.length != 0) {
									formData.messageImage= imgs[0].src;
									console.log(imgs[0].src);
							}
						}
						console.log($("#showContent").html());
						$.ajax({
							url:'${ctx}/cityPage/alterMessage',
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
								if (result.success) {	
									console.log(result.success);					 
									positionDialog.dialog('close');
									$.messager.alert('政策资讯'+result.msg,"成功");
									datagrid.datagrid('reload');
								}else{
									positionDialog.dialog('close');
									$.messager.alert('政策资讯'+result.msg,"成功");
									datagrid.datagrid('reload');
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
	});
		
	function changeState(){
		var rows = $("#datagrid").datagrid("getRows")[index];
		$("#photoId").val(rows.id);
		changeDialog.dialog('open');
	
	}

	function searchFun() {
		datagrid.datagrid('load', {
			messageTitle : $('#toolbar input[name=title]').val(),
			messageIsOnline : $('#toolbar select[name=isOnline]').val(),
			startTime :  $('#startTime').datebox('getValue'),
			endTime :  $('#endTime').datebox('getValue')
		});
	}
	
	function append(flag) {
		
		$('#positionDialog').dialog('setTitle', '<font">添加政策资讯</font>');  
		$('#positionDialog').dialog('open');
		positionForm.form('clear');
		
	}
	
	function edit(index){
		$('#positionDialog').dialog('setTitle', '<font">政策资讯信息</font>');  
	        var rows = $("#datagrid").datagrid("getRows")[index];
	        console.log(rows);
			$('#positionDialog').dialog('open');
			positionForm.form('clear');
			$("#messageId").val(rows.id);
			console.log($("#messageId").val());
			um.setContent(rows.content);
			positionForm.form('load', {
				"messageTitle":rows.title,
				"messageSubTitle":rows.subtitle,
		});
	}
	
	
	
	function remove(index) {
		var ids = [];
		var rows = $("#datagrid").datagrid("getRows")[index];
		if (rows) {
			$.messager.confirm('请确认', '您要删除当前所选政策资讯？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removeMessages',
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
			$.messager.confirm('请确认', '您要更改当前所选政策资讯上线状态？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/changeMessages',
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
	
</script>



</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
			<fieldset class="my_fieldset">
				<legend class="my_legend">检索</legend>
				<table>
					<tr>
						<td>关键内容：</td>
						<td colspan="2"><input name="photoName" class="basic_input" />
						</td>
						<td>上线情况：</td>
						<td colspan="2">
							<select name="state" style="width:164px;height:21px;">
							<option value="">请选择状态</option>
							  <option value="1">带跟进</option>
							  <option value="2">跟进中</option>
							  <option value="3">处理完成</option>
							</select>
						</td>
						<td>开始时间：</td>
							<td colspan="2"><input id="startTime" name="startTime" class="easyui-datebox" ></td>
						<td>结束时间：</td>	
							<td colspan="2"><input id="endTime" name="endTime" class="easyui-datebox" validType='equaldDate'></td>
										
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
	
	<div id="changeDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="changeForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">处理详情</legend>
				<table class="tableForm">
					<tr style="display: none;">
						<td id="photoId" name="photoId">
						</td>
					</tr>
					<tr>
						<th >状&nbsp&nbsp态：</th>
						<td colspan="2">
							<select name="state" style="width:164px;height:21px;">
							<option value="">请选择状态</option>
							  <option value="1">带跟进</option>
							  <option value="2">跟进中</option>
							  <option value="3">处理完成</option>
							</select>
						</td>
					</tr>
				 <tr>
                	 <th>处理详情：</th>
              	 </tr>
                	 <tr>
			    	 <textarea id="remark" name="remark" style="width:800px;height:400px;" >
			    	 </textarea>
			    	 </tr>
				</table>
			</fieldset>
		</form>
	</div>
	
	<div id="photoDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="photoForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">随手拍详情</legend>
				<table class="tableForm">
					<tr>
						<th >上传者：</th>
						<td colspan="2">
							<td colspan="2"><input id="createUserName" name="createUserName" class="easyui-datebox" ></td>
						</td>
					</tr>
					<tr>
						<th >照片：</th>
					</tr>
					<tr>
						<td colspan="2">
							<td id="photo1"></td>
							<td id="photo2"></td>
							<td id="photo3"></td>
						</td>
					</tr>
				 <tr id="process">
                	 <th>处理进度：</th>
              	 </tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>
