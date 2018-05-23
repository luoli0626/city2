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
	var changeDialog;
	var changeForm;

		function createH5(){
			var id=$("#photoId").val();
			console.log(id);
				$.ajax({
						url : '${ctx}/cityPage/createH5',
						data : id,
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(data) {
							if(data.success){
								top.addTabFun({src:'${ctx}/cityPage/photoDetailPage?id='+id,title:'随手拍详情'});
							}
						}
					});
		}
		
		function remove(obj){
			$.messager.confirm('请确认', '确认要删除所选留存图片？', function(r) {
				if (r) {
					var id=$(obj).prev().val();
						$.ajax({
								url : '${ctx}/cityPage/removeHandleImage',
								data : id,
								cache : false,
								dataType : "json",
								contentType:"application/json",
								success : function(data) {
									if(data.success){
										top.addTabFun({src:'${ctx}/cityPage/photoDetailPage?id='+$("#photoId").val(),title:'随手拍详情'});
									}
								}
							});
						}
					});
		}
		
		function add(){
		
		changeForm=$("#changeForm").form();
		changeDialog=$("#changeDialog").show().dialog({
			modal : true,
			title : '处理进度详情',
			width: ($(window).width())*0.6,
   			height:($(window).height())*0.8,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(changeForm.form('validate')){
						var formData={
							"photoId":$("#photoId").val(),
						};	
						$("#showContent").html(um.getContent());
						var imgs=$("#showContent").find("img");
						var imgaddr=[];
							if (imgs.length != 0) {
								for(var i=0;i<imgs.length;i++){
									imgaddr.push(imgs[i].src);
								}
								formData.messageImage= imgaddr.join(",");
								
								$.ajax({
									url : '${ctx}/cityPage/addHandleImage',
									data : JSON.stringify(formData),
									cache : false,
									dataType : "json",
									contentType:"application/json",
									success : function(data) {
										if(data.success){
											top.addTabFun({src:'${ctx}/cityPage/photoDetailPage?id='+$("#photoId").val(),title:'随手拍详情'});
										}
									}
								});
							}else{
								$.messager.alert('提示', '请至少上传一张图片');
							}
					}
				}
			} ]
		}).dialog('close');
		
		//实例化编辑器
		    var um = UM.getEditor('messageContent');
		    um.setWidth("100%");
		    um.setHeight("70%");
		    window.um=um;
		    $(".edui-body-container").css("width", "98%");
		
			changeDialog.dialog('open');
			
			
		
		}
		
		
		
	</script>
</head>
<body class="easyui-layout" fit="true">
	<div id="photoDialog" style="overflow-y:auto;background:#FFFFFF;padding:20px 20px;width:90%;height:90%">
	<a class="easyui-linkbutton" iconCls="icon-add" plain='true' onclick="createH5();" href="javascript:void(0);">生成H5链接</a>
	<span id='h5url'>${(photo.h5url)!}</span>
		<form id="photoForm" method="post" enctype="multipart/form-data">
				<table class="tableForm">
					<tr style="display:none;">
						<th >id：</th>
						<td colspan="8"><input type="text"  id="photoId" name="photoId" value=${photo.id} /></td>
					</tr>
					<tr>
						<th >上&nbsp传&nbsp者：</th>
							<td colspan="8" id="createUserName" name="createUserName">${photo.createUserName.nickName !photo.createUserName.mobilePhone}</td>
					</tr>
					<tr>
						<th >问题描述：</th>
							<td colspan="8" id="remark2" name="remark2">${(photo.content)!}</td>
					</tr>
					<tr>
						<th >上传地址：</th>
							<td colspan="8" id="remark2" name="remark2">${(photo.addrName)!}</td>
					</tr>
					<tr>
						<th >照片：</th>
					</tr>
					<tr>
				<#if photo.images?exists>
		  			  <#list photo.images as node1>  
			  				<td colspan='8'><img style='width:380px;height:380px;margin-left:20px' src="${node1.address}"></td>
	           		</#list>
	  			</#if>
	  			</tr>
				 <tr id="process">
            	 <th id=processTh>处理进度：</th>
              	 </tr>
              	 <tr style='font-weight:blod;' ><td>处理状态</td><td colspan='2'>处理详情</td><td colspan='2'>处理时间</td></tr>
              	 <#if photo.allState?exists>
		  			  <#list photo.allState as node2>  
			  			  <tr>
			  				<tr><td>${(node2.name)!}</td><td colspan='2'>${(node2.content)!}</td><td colspan='2'>${(node2.createTime)!}</td></tr>
			  			</tr>
	           		</#list>
	  			</#if>
	  			
	  			
              	 <#if photo.handles?exists>
              	 	<tr>
		            	 <th>处理完成留存照片：</th>
		            	 	<td>
		            	 	<a style="color:'red';" class="easyui-linkbutton" iconCls="icon-add" plain='true' onclick="add();" href="javascript:void(0);">添加图片</a>
		              	 	</td>
		              	 </tr>
	          	 	<tr>
		  			  <#list photo.handles as node3>  
			  				<td colspan='8'><img style='width:400px;height:400px;margin-left:20px' src="${node3.address}"></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  				<input style="display:none;" type="text" name="photoId" value=${node3.id} />
			  				<a class="easyui-linkbutton" iconCls="icon-remove" plain='true' onclick="remove(this);" href="javascript:void(0);">删除图片</a>
	           				</td>
	           		</#list>
	  			</#if>
	  			</tr>
				</table>
		</form>
	</div>
	
		<div id="changeDialog" style="display: none;overflow-y:auto;background:#FFFFFF;padding:20px 20px;">
		<form id="changeForm" method="post" enctype="multipart/form-data">
			<fieldset class="my_fieldset">
				<legend class="my_legend">添加留存图片</legend>
				<table class="tableForm">
			    	<tr>
                	 <th>添加图片：</th>
              	 </tr>
              	 </table>
			    	 <div id="messageContent" name="messageContent"  >
			    	     <div id="showContent" style="display:none;"></div> 
				
			</fieldset>
		</form>
	</div>
	

</body>

</html>