<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<script type="text/javascript" charset="utf-8">
	$(function() {
		initPage();
		showResource();
	});
	function initPage(){
		sy.ajaxSubmit({
			url:'${ctx}/main/getUserInfo',
			dataType : "json",
			success:function(d){
				var data=d.obj;
				$("#userForm").form('load', {
					id : data.id,
					loginAcct : data.loginAcct,
					sex:data.sex,
					fax:data.fax,
					officePhone:data.officePhone,
					mobilePhone:data.mobilePhone,
					email:data.email,
					birthday:data.birthday,
					nickName:data.nickName,
					qq:data.qq,
					taskNumber:data.taskNumber
				});
				var roles=data.roles;
				var roleName="";
				for(var i=0;i<roles.length;i++){
					roleName=roleName+roles[i].text+",";
				}
				var scenesId=data.scenes;
				var scenesName="";
				for(var i=0;i<scenesId.length;i++){
					scenesName=scenesName+scenesId[i].name+",";
				}
				var orgname="";
				for(var j=0;j<data.organization.length;j++){
					orgname+=","+data.organization[j].orgName;
				}
				if(orgname!="")
					orgname=orgname.substring(1,orgname.length);
				$('#roles').html(roleName.substring(0,roleName.length-1));
				$("#organization").html(orgname);
				$("#scene").html(scenesName.substring(0,scenesName.length-1));
				
			}
		});
	}
	function editPwd(){
		$('#pwdDialog').show().dialog({
			modal : true,
			title : '修改密码',
			width:300,
			height:200,
			top:200,
			left:500,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if($("#pwdForm").form('validate')){
						var formData={"id":$("#id").val(),"password":$("#password").val(),"oldPassword":$("#oldPassword").val()}
						console.log(formData)
						sy.ajaxSubmit({
							url : '${ctx}/main/updatePwd',
							data:JSON.stringify(formData),
							contentType:"application/json",
							success : function(data) {
								if(data.success){
									$.messager.alert('提示',"修改成功,3秒后跳转到登录页面");
									 setInterval("window.parent.logout()",3000);
								}else{
									$.messager.alert('提示',"旧密码错误");
								}
							}
						});
					}
					
				}			
			} ]
		});
	}
	function showResource(){
		$('#restree').tree({
			url : '${ctx}/main/getUserMenuTree',
			animate : false,
			lines : !sy.isLessThanIe8(),
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						t.tree('collapseAll');
					});
				}
			}
		});
	}
	function save(){
		if($("#userForm").form('validate')){
			var formData={
				"id":$("#id").val(),
				"sex":$("[name='sex']:checked").val(),
				"birthday":$("#birthday").datetimebox("getValue"),
				"officePhone":$("#officePhone").val(),
				"mobilePhone":$("#mobilePhone").val(),
				"fax":$("#fax").val(),
				"email":$("#email").val(),
				"qq":$("#qq").val(),
				"nickName":$("#nickName").val()
			};
			console.log(formData);
			sy.ajaxSubmit({
				url:'${ctx}/main/editUserInfo', 
				data:JSON.stringify(formData),
				contentType:"application/json",
	 			success:function(result){
					if (result.success) {
						$.messager.alert('提示',"修改信息成功");
					}else{
						$.messager.alert('提示',"修改信息失败");
					}
				}
			});
		}
	}
	function mycancel(){
		window.parent.refreshTab(window.parent.centerTabs);
	}
	//跳转到我的资料
	function myEdit(){
		 var ct=window.parent.getCurrentTabTitle();
		var url;
	    url="${ctx}/main/toAdd?id=${(currentUser.staff)!}&ct="+encodeURI(encodeURI(ct));
		window.parent.addTabFun({src :url,title : "修改我的信息"});
	}
	
	
</script>
</head>
<body class="easyui-layout">
	<div region="center" border="false">
		<div class="baseInfo" style="display:block;">
			<fieldset class="my_fieldset">
				<legend class="my_legend">个人信息</legend>
				<form id="userForm" method="post">
					<table class="tableForm" style="margin-left:30px;margin-top:30px;">
						<tr style="display:none;">
							<th width="80">用户ID</th>
							<td><input name="id" id="id"/></td>
						</tr>
						<tr>
							<th>登录名称：</th>
							<td><input id="loginAcct" name="loginAcct"  style="width: 196px;"  readonly /></td>
						</tr>
						<tr>
							<th>姓&#12288;&#12288;名：</th> 
							<td><input id="nickName" name="nickName"  style="width: 196px;" readonly  class="easyui-validatebox" required="true" validType="length[1,20]"/></td>
						</tr>
						<tr>
							<th>性&#12288;&#12288;别：</th>
							<td>
								<input name="sex"  type="radio" value="male" style="width:15px;"/>男
								<input name="sex"  type="radio" value="female" style="width:15px;"/>女
								<input name="sex"  type="radio" value="private" style="width:15px;" />保密
							</td>
						</tr>
						<tr>
							<th>生&#12288;&#12288;日：</th>
							<td><input id="birthday" name="birthday"  style="width: 196px;" class="easyui-datetimebox" /></td>
						</tr>
						<tr>
							<th>微信ID：</th>
							<td><input id="officePhone" name="officePhone"  style="width: 196px;" class="easyui-validatebox" validType="phone"/></td>
						</tr>
						<tr>
							<th>移动电话：</th>
							<td><input id="mobilePhone" name="mobilePhone"  style="width: 196px;" class="easyui-validatebox" validType="mobile"/></td>
						</tr>
						
						<!--
						<tr>
							<th>传&#12288;&#12288;真：</th>
							<td><input id="fax" name="fax"  style="width: 196px;" validType="phone" /></td>
						</tr>
						
						-->
						<tr>
							<th>电子邮箱：</th>
							<td><input id="email" name="email"  style="width: 196px;" class="easyui-validatebox" validType="email" /></td>
						</tr>
						<tr>
							<th>q&#12288;&#12288;q：</th>
							<td><input id="qq" name="qq"  style="width: 196px;" class="easyui-validatebox" validType="qq"/></td>
						</tr>
						<tr>
					<th>任务量</th>
					<td><input id="taskNumber" name="taskNumber"  style="width: 196px;" class="easyui-numberbox" readonly /></td>
				</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<th>&#12288;</th>
							<td  style="text-algin:center;">
								<a class="easyui-linkbutton" iconCls="icon-edit"  onclick="editPwd();" href="javascript:void(0);">修改密码 </a>
								<a class="easyui-linkbutton" iconCls="icon-save"  onclick="save();" href="javascript:void(0);">保  存</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel"  onclick="mycancel();" href="javascript:void(0);">取  消 </a>
								<!--<a class="easyui-linkbutton" iconCls="icon-edit"  onclick="myEdit();" href="javascript:void(0);">我的资料 </a>-->
							</td>
						</tr>
					</table>
				</form>
			</fieldset>
		</div>
		<div class="resourceInfo" style="display:block;">
			<fieldset class="my_fieldset">
				<legend class="my_legend">拥有资源</legend>
				<div id="restree" style="margin-left:30px;"></div>
			</fieldset>
		</div>
		
		<div class="roleInfo">
			<fieldset class="my_fieldset">
				<legend class="my_legend">拥有角色</legend>
				<div id="roles" style="margin-left:30px;">
					
				</div>
			</fieldset>
		</div>
		<div class="orgInfo">
			<fieldset class="my_fieldset">
				<legend class="my_legend">机构信息</legend>
				<div id="organization" style="margin-left:30px;">
					
				</div>
			</fieldset>
		</div>
		<div class="orgInfo">
			<fieldset class="my_fieldset">
				<legend class="my_legend">所属场景板块</legend>
				<div id="scene" style="margin-left:30px;">
					
				</div>
			</fieldset>
		</div>
	</div>
	<div id="pwdDialog" style="display: none;overflow: hidden;background:#FFFFFF;padding:20px 20px;">
		<form id="pwdForm" method="post">
			<table class="tableForm">
				<tr>
					<th width="100" >原密码：</th>
					<td><input name="oldPassword" id="oldPassword" type="password" class="easyui-validatebox" validType="length[6,32]" required="true" missingMessage="此项必须填写"/></td>
				</tr>
				<tr>
					<th width="100" >新密码：</th>
					<td><input name="password" type="password" id="password" class="easyui-validatebox" validType="length[6,32]" required="true" /></td>
				</tr>
				<tr>
					<th width="100" >确认密码：</th>
					<td><input name="password1" type="password" id="password1" class="easyui-validatebox" validType="eqPassword[password]" required="true" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>