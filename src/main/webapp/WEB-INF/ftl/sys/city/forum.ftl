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
			url : '${ctx}/cityPage/forumList',
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
				title : '文章标题',
				width :$(this).width()*0.15,
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
				field : 'isCheck',
				title : '审核状态',
				width :$(this).width()*0.15,
				formatter:function(value,rec,i){
					if(value=='Y'){
						return "审核通过";
					}else if(value=='N'){
						return "审核未通过";					
					}else if(value=='D'){
						return "待审核";
					}
				}
			},
			{
			    title : '操作',
			    field : '1',
			    width : $(this).width() * 0.25,
			    formatter:function(value,rec,i){
			    var btnHtml="";   
			    btnHtml+="<a href='javascript: changeState("+i+",1);' plain='true'  iconcls='icon-changeSate' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-changeSate' style='padding-left: 20px;'>审核通过</span></span></a >";
			    btnHtml+="<a href='javascript: changeState("+i+",2);' plain='true'  iconcls='icon-changeSate' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-changeSate' style='padding-left: 20px;'>审核不过</span></span></a >";
			    btnHtml+="<a href=' javascript:edit("+i+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>详情</span></span></a >";
			    btnHtml+="<a href='javascript:remove("+i+");' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a >";
			    btnHtml+="<a href=' javascript:showComment("+i+");' plain='true'  iconcls='icon-haveholder' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-haveholder' style='padding-left: 20px;'>管理评论</span></span></a >";
			    return btnHtml;
		    }
		    }] ],
			onLoadSuccess:function(data) {
				console.log(data);
			}
		});
		
		datagridComment = $('#datagridComment').datagrid({
			url : '${ctx}/cityPage/forumsCommentList',
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
		
		
		photoDialog = $('#photoDialog').show().dialog({
			modal : true,
			title : '论坛详情',
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
		
	});
	
	
	function changeState(index,code){
		var state;
		var isCheck;
		if(code==1){
			state='审核通过';
			isCheck='Y';
		}else{
			state='审核不通过';
			isCheck='N';
		}
		var rows = $("#datagrid").datagrid("getRows")[index];
		console.log(rows);
		var formData={
			"photoId": rows.id,
			"state": state,
			"code": isCheck
		};	
								
		$.ajax({
			url:'${ctx}/cityPage/changeForumState',
			data:JSON.stringify(formData),
			contentType:"application/json",
 			success:function(result){
				datagrid.datagrid('reload');
			}
		});
	}

	function searchFun() {
		datagrid.datagrid('load', {
			photoName : $('#toolbar input[name=photoName]').val(),
			state : $('#toolbar select[name=state]').val(),
			startTime :  $('#startTime').datebox('getValue'),
			endTime :  $('#endTime').datebox('getValue')
		});
	}
	
	
	function edit(index){
		$('#positionDialog').dialog('setTitle', '<font">论坛详情详情</font>');  
	        var rows = $("#datagrid").datagrid("getRows")[index];
	        console.log(rows);
			photoDialog.dialog('open');
			$("#forumName").text(rows.title);
			$("#content").val(rows.content);
			$("#state2").empty();
			$("#state2").append("<tr style='font-weight:blod;' ><td>审核状态</td><td>审核时间</td></tr>");
			for(var i=0;i<rows.allState.length;i++){
				$("#state2").append("<tr><td>"+rows.allState[i].name+"</td><td>"+timestampToTime(rows.allState[i].createTime)+"</td></tr>");
			}
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
			$.messager.confirm('请确认', '您要删除当前所选文章？', function(r) {
				if (r) {	
						ids.push(rows.id);
					$.ajax({
						url : '${ctx}/cityPage/removeForums',
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
						url : '${ctx}/cityPage/removeForumsComment',
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
						<td>上线情况：</td>
						<td colspan="2">
							<select name="state" style="width:164px;height:21px;">
							<option value="">请选择状态</option>
							  <option value="Y">审核通过</option>
							  <option value="N">审核不通过</option>
							  <option value="D">待审核</option>
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
	
	
	<div id="photoDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="photoForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">随手拍详情</legend>
				<table class="tableForm">
					<tr>
						<th >文章标题：</th>
						<td colspan="2">
							<td colspan="2" id="forumName" name="forumName"></td>
						</td>
					</tr>
					<tr>
	                	<th>文章内容：</th>
                	</tr>
                	<tr>
				    	<td  colspan="4"><textarea rows=6 cols=65 id="content" name="content" ></textarea></td>
			    	</tr>
				 <tr id="process">
                	 <th >审核进度：</th>
              	 </tr>
              	 <tr id="state2">
					</tr>
				</table>
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
