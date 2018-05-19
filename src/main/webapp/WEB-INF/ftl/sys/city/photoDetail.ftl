<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>

<script type="text/javascript" charset="UTF-8">

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
	

</body>

</html>