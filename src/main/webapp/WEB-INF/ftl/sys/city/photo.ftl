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
				formatter:function(value,rec,i){
					return timestampToTime(value);					
				}
			},	
			{
				field : 'state',
				title : '跟进状态',
				width :$(this).width()*0.15,
				formatter:function(value,rec,i){
					//return rec.allState[rec.allState.length-1].name;
					
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
		
		
		photoDialog = $('#photoDialog').show().dialog({
			modal : true,
			title : '随手拍详情',
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
		
		
		changeForm=$("#changeForm").form();
		changeDialog=$("#changeDialog").show().dialog({
			modal : true,
			title : '处理进度详情',
			width:700,
			height:280,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(changeForm.form('validate')){
						var formData={
								"photoId":$("#photoId").val(),
								//"state":$("#state").find("option:selected").text(),
								"state":$("#state").combobox('getText'),
								"remark":$("#remark").val(),
								"code":$("#state").combobox('getValue')
						};	
						console.log(formData);
						$.ajax({
							url:'${ctx}/cityPage/changePhotoState',
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
				 				console.log(result);
				 				changeDialog.dialog('close');
								datagrid.datagrid('reload');
							}
						});
					}
				}
			} ]
		}).dialog('close');
		
		$("#state").combobox({
		    url:'${ctx}/cityPage/photoStateSelect',
		    valueField:'id',
		    textField:'name'
		});
		
		$("#state3").combobox({
		    url:'${ctx}/cityPage/photoStateSelect',
		    valueField:'id',
		    textField:'name'
		});
	});
		
	function changeState(index){
		var rows = $("#datagrid").datagrid("getRows")[index];
		console.log(rows);
		$("#photoId").val(rows.id);
		changeForm.form('clear')
		changeDialog.dialog('open');
	}

	function searchFun() {
		datagrid.datagrid('load', {
			photoName : $('#toolbar input[name=photoName]').val(),
			state : $("#state3").combobox('getValue'),
			startTime :  $('#startTime').datebox('getValue'),
			endTime :  $('#endTime').datebox('getValue')
		});
	}
	
	
	function edit(index){
		//$('#positionDialog').dialog('setTitle', '<font">随手拍详情</font>');  
	        var rows = $("#datagrid").datagrid("getRows")[index];
	        top.addTabFun({src:'${ctx}/cityPage/photoDetailPage?id='+rows.id,title:'随手拍详情'});
	        
			//photoDialog.dialog('open');
			//$("#createUserName").text(rows.createUserName.nickName);
			//$("#remark2").text(rows.content);
			//$("#photo").empty();
			//$("#state2").empty();
			//for(var i=0;i<rows.images.length;i++){
			//	$("#photo").append("<td colspan='8'><img style='width:500px;height:500px;margin-left:20px' src='"+rows.images[i].address+"'/></td></br>");
			//}
			//$("#state2").append("<tr style='font-weight:blod;' ><td>处理状态</td><td colspan='2'>处理详情</td><td colspan='2'>处理时间</td></tr>");
			//for(var i=0;i<rows.allState.length;i++){
			//	$("#state2").append("<tr><td>"+rows.allState[i].name+"</td><td colspan='2'>"+(rows.allState[i].content==null?"":rows.allState[i].content)+"</td><td colspan='2'>"+timestampToTime(rows.allState[i].createTime)+"</td></tr>");
			//}
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
						<td>关键内容：</td>
						<td colspan="2"><input name="photoName" class="basic_input" />
						</td>
						<td>上线情况：</td>
						
						<td colspan="2">
						<input name="state" id="state3" class="easyui-validatebox"  />
						<!--
							<select name="state" style="width:164px;height:21px;" >
							<option value="">请选择状态</option>
							  <option value="1">待跟进</option>
							  <option value="2">跟进中</option>
							  <option value="3">处理完成</option>
							</select>
						-->
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
				<legend class="my_legend">处理进度详情</legend>
				<table class="tableForm">
					<tr style="display: none;">
						<td id="photoId" name="photoId">
						</td>
					</tr>
					<tr>
						<th >状&nbsp&nbsp态：</th>
						<td colspan="2">
						<input name="state" id="state" class="easyui-validatebox"  />
						<!--
							<select id="state" name="state" style="width:164px;height:21px;"   >
							<option value="">请选择状态</option>
							  <option value="1">待跟进</option>
							  <option value="2">跟进中</option>
							  <option value="3">处理完成</option>
							</select>
						-->
						</td>
					</tr>
					<tr>
	                	<th>处理进度详情：</th>
				    	<td><textarea  rows=4 cols=50 id="remark" name="remark" ></textarea></td>
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
						<th >上&nbsp传&nbsp者：</th>
							<td colspan="8" id="createUserName" name="createUserName"></td>
					</tr>
					<tr>
						<th >问题描述：</th>
							<td colspan="8" id="remark2" name="remark2"></td>
					</tr>
					<tr>
						<th >照片：</th>
					</tr>
					<tr id="photo">
					</tr>
				 <tr id="process">
                	 <th id=processTh>处理进度：</th>
              	 </tr>
              	 <tr id="state2">
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>
