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
	var changeForm;
	var photoDialog;
	
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
			url : '${ctx}/cityPage/lostList',
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
				field : 'content',
				title : '内容',
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
				formatter:function(value,rec,i){
					return timestampToTime(value);					
				}
			},	
			{
				field : 'contact',
				title : '联系方式',
				width :$(this).width()*0.15,
			},
			{
			    title : '操作',
			    field : '1',
			    width : $(this).width() * 0.25,
			    formatter:function(value,rec,i){
			    var btnHtml="";   
			    btnHtml+="<a href=' javascript:edit("+i+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>详情</span></span></a >";
			    btnHtml+="<a href='javascript: remove("+i+");' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		
		photoDialog = $('#photoDialog').show().dialog({
			modal : true,
			title : '失物招领详情',
			width: ($(window).width())*0.7,
   			height:($(window).height())*0.8,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					photoDialog.dialog('close');
				}
			} ]
		}).dialog('close');
		
	});
		

	function searchFun() {
		datagrid.datagrid('load', {
			photoName : $('#toolbar input[name=photoName]').val(),
			state : $('#toolbar select[name=state]').val(),
			startTime :  $('#startTime').datebox('getValue'),
			endTime :  $('#endTime').datebox('getValue')
		});
	}
	
	
	function edit(index){
		$('#positionDialog').dialog('setTitle', '<font">失物招领详情</font>');  
	        var rows = $("#datagrid").datagrid("getRows")[index];
	        console.log(rows);
			photoDialog.dialog('open');
			$("#createUserName").text(rows.createUserName.nickName);
			$("#remark2").text(rows.content);
			$("#contact").text(rows.contact);
			$("#photo").empty();
			for(var i=0;i<rows.images.length;i++){
				$("#photo").append("<td colspan='8'><img style='width:500px;height:500px;margin-left:20px' src='"+rows.images[i].address+"'/></td></br>");
			}
	}
	
	
	function remove(index) {
		var ids = [];
		var rows = $("#datagrid").datagrid("getRows")[index];
		if (rows) {
			$.messager.confirm('请确认', '您要删除当前所选失物招领？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removeLost',
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
	
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
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
			
			
		</div>
		<table id="datagrid" border="1"></table>
	</div>
	
	
	<div id="photoDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="photoForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">随手拍详情</legend>
				<table class="tableForm">
					<tr>
						<th >上&nbsp传&nbsp者：</th>
							<td colspan="8" id="createUserName" name="createUserName"></td>
					</tr>
					<tr>
						<th >问题描述：</th>
							<td colspan="8" id="remark2" name="remark2"></td>
					</tr>
					<tr>
					<th >联系电话：</th>
							<td colspan="8" id="contact" name="contact"></td>
					</tr>
					<tr>
						<th >照片：</th>
					</tr>
					<tr id="photo">
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>
