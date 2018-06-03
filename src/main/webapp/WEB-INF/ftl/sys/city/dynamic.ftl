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
	var datagridComment;
	var CommentDialog;
	
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
			url : '${ctx}/cityPage/dynamicList',
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
				field : 'title',
				title : '动态标题',
				width :$(this).width()*0.15,
			},
			{
				field : 'images',
				title : '预览图片',
				width :$(this).width()*0.2,
				formatter:function(value,rec,i){
					if(value.length!=0){
						return "<img src='"+value[0].address+"_50x50.jpg' style='width:50px;height:50px;'>";
					}
				}
			},
			{
				field : 'createUserName',
				title : '上传者',
				width :$(this).width()*0.15,
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
				field : 'isOnline',
				title : '上线状态',
				width :$(this).width()*0.15,
				formatter:function(value,rec,i){
					if(value=='Y'){
						return "上线";
					}else{
						return "未上线";					
					}
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
			    btnHtml+="<a href=' javascript:isOnline("+i+");' plain='true'  iconcls='icon-reload' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-reload' style='padding-left: 20px;'>上/下线</span></span></a >";
			    btnHtml+="<a href=' javascript:isBanner("+i+");' plain='true'  iconcls='icon-reload' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-reload' style='padding-left: 20px;'>设置为首页轮播</span></span></a >";
			    btnHtml+="<a href=' javascript:showComment("+i+");' plain='true'  iconcls='icon-haveholder' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-haveholder' style='padding-left: 20px;'>管理评论</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		datagridComment = $('#datagridComment').datagrid({
			url : '${ctx}/cityPage/dynamicCommentList',
			title : '',
			iconCls : 'icon-save',
			rownumbers: true,
			pagination : true,
			pageSize:10,
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
				field : 'createUserName',
				title : '评论人',
				width :$(this).width()*0.2,
			},
			{
				field : 'createTime',
				title : '评论时间',
				width :$(this).width()*0.2,
			},
			{
				field : 'content',
				title : '评论内容',
				width :$(this).width()*0.2,
			},
			{
			    title : '操作',
			    field : '1',
			    width : $(this).width() * 0.25,
			    formatter:function(value,rec,i){
			    var btnHtml="";   
			    btnHtml+="<a href='javascript:removeComment("+i+");' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		
		positionDialog = $('#positionDialog').show().dialog({
			modal : true,
			title : '添加城管动态',
			width: ($(window).width())*0.7,
   			height:($(window).height())*0.8,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(positionForm.form('validate')){
						$("#showContent").html(um.getContent());
						console.log($("#showContent").html());
						var formData={
								"messageId":$("#messageId").val(),
								"messageTitle":$("#messageTitle").val(),
								"messageSubTitle":$("#messageSubTitle").val(),
								"messageContent":$("#showContent").html(),
						};	
						//if($("#messageId").val()==""){
							var imgs=$("#showContent").find("img");
							if (imgs.length != 0) {
									formData.messageImage= imgs[0].src;
									console.log(imgs[0].src);
							}
							//else{
							//	$.messager.alert('提示',"请至少添加一张图片");
							//	return false;
							//}
						//}
						console.log($("#showContent").html());
						$.ajax({
							url:'${ctx}/cityPage/alterDynamic',
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
								if (result.success) {	
									console.log(result.success);					 
									positionDialog.dialog('close');
									$.messager.alert('城管动态'+result.msg,"成功");
									datagrid.datagrid('reload');
								}else{
									positionDialog.dialog('close');
									$.messager.alert('城管动态'+result.msg,"成功");
									datagrid.datagrid('reload');
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
		
		
		CommentDialog= $('#CommentDialog').show().dialog({
			modal : true,
			title : '城管动态评论详情',
			width: ($(window).width())*0.7,
   			height:($(window).height())*0.8,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					CommentDialog.dialog('close');
				}
			} ]
		}).dialog('close');
		
			   //实例化编辑器
		    var um = UM.getEditor('messageContent');
		    um.setWidth("100%");
		    window.um=um;
		    $(".edui-body-container").css("width", "98%");
		
	});
		

	function searchFun() {
		datagrid.datagrid('load', {
			messageTitle : $('#toolbar input[name=title]').val(),
			messageIsOnline : $('#toolbar select[name=isOnline]').val(),
			startTime :  $('#startTime').datebox('getValue'),
			endTime :  $('#endTime').datebox('getValue')
		});
	}
	
	function append(flag) {
		
		$('#positionDialog').dialog('setTitle', '<font">添加城管动态</font>');  
		$('#positionDialog').dialog('open');
		positionForm.form('clear');
		um.setContent("");
	}
	
	function edit(index){
		$('#positionDialog').dialog('setTitle', '<font">城管动态信息</font>');  
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
	
	function showComment(index){
		var rows = $("#datagrid").datagrid("getRows")[index];
		datagridComment.datagrid('load', {
			messageId : rows.id
		});
		$("#CommentDialog").dialog('open');
	}
	
	
	function remove(index) {
		var ids = [];
		var rows = $("#datagrid").datagrid("getRows")[index];
		if (rows) {
			$.messager.confirm('请确认', '您要删除当前所选城管动态？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removeDynamics',
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
	
	
	function removeComment(index) {
		var ids = [];
		var rows = $("#datagridComment").datagrid("getRows")[index];
		if (rows) {
			$.messager.confirm('请确认', '您要删除当前所选评论？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removeDynamicComment',
						data : JSON.stringify(ids),
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(data) {
							if(data.success){
								datagridComment.datagrid('unselectAll');
								datagridComment.datagrid('reload');
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
			$.messager.confirm('请确认', '您要更改当前所选城管动态上线状态？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/changeDynamics',
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
	
	function isBanner(index){
		var rows = $("#datagrid").datagrid("getRows")[index];
		var formdata={
			"code" : "1",
			"messageId" : rows.id
		}
			if (rows) {
				$.messager.confirm('请确认', '您要更改当前所选文章为首页轮播？', function(r) {
					if (r) {	
						$.ajax({
							url : '${ctx}/cityPage/addBanner',
							data : JSON.stringify(formdata),
							cache : false,
							dataType : "json",
							contentType:"application/json",
							success : function(data) {
								if(data.success){
									$.messager.alert('成功',"轮播设置成功，您可以在轮播设置界面查看");
									datagrid.datagrid('unselectAll');
									datagrid.datagrid('reload');
								}else{
									$.messager.alert('失败',"轮播数量已经达到上线");
									datagrid.datagrid('unselectAll');
									datagrid.datagrid('reload');
								}
							}
						});
					}
				});
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
						<td>标题：</td>
						<td colspan="2"><input name="title" class="basic_input" />
						</td>
						<td>上线情况：</td>
						<td colspan="2">
							<select name="isOnline" style="width:164px;height:21px;">
							<option value="">请选择上线状态</option>
							  <option value="Y">上线</option>
							  <option value="N">未上线</option>
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
	
	<div id="positionDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="positionForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">城管动态详情</legend>
				<table class="tableForm">
					<tr style="display: none;">
						<td id="messageId" name="messageId">
						</td>
					</tr>
					<tr>
						<th >标&nbsp&nbsp题：</th>
						<td>
							<input name="messageTitle" id="messageTitle" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
				<tr>
					<th>副标题：</th>
					<td><input id="messageSubTitle" name="messageSubTitle" class="easyui-validatebox"  style="width: 150px;" /></td>
				</tr>
				
				 <tr>
                	 <th>内&nbsp&nbsp容：</th>
              	 </tr>
              	 </table>
			
			    	 <div  id="messageContent" name="messageContent"  ></div>
		    	     <div id="showContent" style="display:none;"></div> 
				</fieldset>
		</form>
	</div>
	
	
	<div id="CommentDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<div id="toolbar2" class="datagrid-toolbar" style="height: auto;display: none;">
		</div>
		<table id="datagridComment" border="1"></table>
	</div>
</body>
</html>
