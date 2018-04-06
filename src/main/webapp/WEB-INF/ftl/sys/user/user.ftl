<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<#assign requestURI = springMacroRequestContext.getRequestUri() />
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var organTree;
	var sceneTree;
	var sceneIds=[];
	var roleTree;
	var roleIds=[];
	var organ=[];
	var repeatDialog;
	var userRoleId;
	var userRoleIdForm;
	var orgData;
	$(function(){
	

	
		timeInputBoxBindOpenPanel("birthday");
		userForm = $('#userForm').form();
		//
		$("#SorgId").combotree({
			url : '${ctx}/user/findOrgTreeByLoginUser',
			animate : false,
			lines : false,
			width:200,
			panelWidth:200,
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('expandAll');
						}
					});
				}
			}
		});
		userRoleIdForm = $('#userRoleIdForm').form();
				//批量导入所有
		userRoleId=$('#userRoleId').show().dialog({
					title: '批量导入',
					modal : true,
					width: 600,
					height: 300,
					onClose:function(recode){
						$('#userRoleIdForm')[0].reset();
						document.getElementById('fileName').innerHTML = '';
		                document.getElementById('fileSize').innerHTML = '';
		                document.getElementById('fileType').innerHTML = '';
		                fileFlag = undefined;
					},
					buttons:[
					{
						text:'导入',
						handler:function(){
							if(undefined == fileFlag || !fileFlag)
							{
								myMessage('请检查文件信息!');
								return;
							}
							
							$.messager.progress();
							if(userRoleIdForm.form('validate')){
							$('#userRoleIdForm').form('submit',{
								url:'${ctx}/manual/userRoleList',
								onSubmit:function(){
									var isValid = $(this).form('validate');
									if(!isValid){
										$.messager.progress('close');
									}
									
									return isValid;
								},
								success:function(data){
								     alert(data);
									$.messager.progress('close');
									
										
										 if (data=="批量导入成功!"){
										     datagrid.datagrid('unselectAll');
						                     datagrid.datagrid('reload');
								              myMessage(data);
								            $('#userRoleId').dialog('close');
								         }  
								         else  if (data=="有未导入成功数据!")
								         {
								           
								         	$('#repeatDialog').dialog('open');
								         	$('#repeatView').load('${ctx}/manual/userRoleRepeatView',{},function(){});
								         
								         }
								    
								}
							});
							}
						}
					},
					{
						text:'取消',
						handler:function(){
							$('#userRoleId').dialog('close');
						}
					}]
				}).dialog('close');
		$("#SsceneId").combotree({
			url : '${ctx}/manual/tree',
			animate : false,
			lines : !sy.isLessThanIe8(),
			width:200,
			panelWidth:200,
        	onSelect : function(node) { 
         		$('#SsceneId').val(node.id); 
		        //返回树对象  
		        var tree = $(this).tree;  
		        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
		        var isLeaf = tree('isLeaf', node.target);  
		        if (!isLeaf) {  
		            //清除选中  
		            $('#SsceneId').combotree('clear');  
		        }  
        	}
			
		});
		
		roleTree=$('#Sroles').combotree({
			url : '${ctx}/role/tree',
			animate : false,
			lines : !sy.isLessThanIe8(),
			width:200,
			panelWidth:200,
			panelHeight:200,
			onSelect : function(node) { 
         		$('#Sroles').val(node.id); 
		        //返回树对象  
		        var tree = $(this).tree;  
		        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
		        var isLeaf = tree('isLeaf', node.target);  
		        if (!isLeaf) {  
		            //清除选中  
		            $('#Sroles').combotree('clear');  
		        }  
        	}
		});
		
		organTree=$('[name=orgId]').combotree({
			url : '${ctx}/user/findOrgTreeByLoginUser',
			animate : false,
			lines : !sy.isLessThanIe8(),
			width:200,
			panelWidth:200,
			panelHeight:200,
			onSelect : function(node) { 
			$('#SorgId').val(node.id); 
		        //返回树对象  
		        var tree = $(this).tree;  
		        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
		        var isLeaf = tree('isLeaf', node.target);  
		        if (!isLeaf) {  
		            //清除选中  
		            $('#orgId').combotree('clear');  
		        }  
        	},
        	onLoadSuccess : function(node, data) {
				var t = $(this);
				orgData=data;
			//	console.log(data);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('expandAll');
						}
					});
				}
			}
	
		});
		
		
		
		
		
		userDialog = $('#userDialog').show().dialog({
			modal : true,
			title : '用户信息',
			width:360,
			height:550,
			buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
					if(userForm.form('validate')){
						var formData={
								"loginAcct":$("#loginAcct").val(),
								"sex":$("[name='sex']:checked").val(),
								"birthday":$("#birthday").datetimebox("getValue"),
								"officePhone":$("#officePhone").val(),
								"mobilePhone":$("#mobilePhone").val(),
								"fax":$("#fax").val(),
								"email":$("#email").val(),
								"qq":$("#qq").val(),
								"taskNumber":$("#taskNumber").val(),
								"nickName":$("#nickName").val()
							};	
						var organization=[];
						var roles=[];
						var scenes=[];
						var ids=roleTree.combotree("getValues");
						var ids1=sceneTree.combotree("getValues");
						var orgg=organTree.combotree("getValues");
						for(var i=0;i<ids.length;i++){
							roles.push({"id":ids[i]});
						}
						for(var i=0;i<ids1.length;i++){
							scenes.push({"id":ids1[i]});
						}
						for(var i=0;i<orgg.length;i++){
							organization.push({"id":orgg[i]});
						}
						
						formData.organization=organization;
						formData.roles=roles;
						formData.scenes=scenes;
						var url="${ctx}/user/add";
						var msg="新增";
						if ($('[name=id]').val()) {
							url='${ctx}/user/edit';
							formData.id=$('[name=id]').val();
							msg="修改";
						}else{
							if($("#password").val()){
								formData.password=$("#password").val();
							}
						}
						$.ajax({
							url:url, 
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
				 			  $("#orgId").combotree('clear');  //清楚选中
				 			  roleTree.combotree('clear');
								if (result.success) {						 
									userDialog.dialog('close');
									$.messager.alert('用户'+msg,"保存成功");
									datagrid.datagrid('reload');
								}else{
									if(result.msg){
										$.messager.alert('用户'+msg,result.msg);
									}else{
										$.messager.alert('用户'+msg,"保存失败");
									}
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
		
		repeatDialog=$('#repeatDialog').show().dialog({
				modal : true,
				title : '批量回馈',
				width:750,
				height:450,
				buttons : [ {
					text : '导出数据',
					style:'text-align:center',
					handler : function() {
					 window.open('${ctx}/manual/userRolereports',"","_blank");
					}
				},{
					text : '取消',
					style:'text-align:center',
					handler : function() {
					
					userRoleId.dialog('close');
					repeatDialog.dialog('close');
					$('#repeatView').empty();
					$.messager.progress('close');
					 datagrid.datagrid('unselectAll');
					datagrid.datagrid('reload');
					
					
					}
				}]
			}).dialog('close');
		
		datagrid = $('#datagrid').datagrid({
			url : '${ctx}/user/datagrid',
			rownumbers:true,
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize:30,
			border:1,
			pageList:[10,20,30,50,100],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : $(this).width() * 0.03,
				checkbox : true
			}, {
				title : '操作',
				field : '1',
				width : $(this).width() * 0.11,
				formatter:function(value,rec,i){
					var btnHtml="";			
					<#if fmfn.checkButton(requestURI,"icon-edit")>
						btnHtml="<a href='javascript:edit("+i+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px;'>编辑</span></span></a>";
					</#if>
					<#if fmfn.checkButton(requestURI,"icon-remove")>
						btnHtml+="<a href='javascript:removeUser();' plain='true'  iconcls='icon-remove' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>删除</span></span></a>";
					</#if>
						<#if fmfn.checkButton(requestURI,"icon-edit")>
						if(rec.seal=="N")
						btnHtml+="<a href='javascript:controlUser("+i+");' plain='true'  iconcls='icon-edit' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove' style='padding-left: 20px;'>解封</span></span></a>";
					</#if>
						
					return btnHtml;
				}
			} ,{
				field : 'loginAcct',
				title : '登录帐号',
				width : $(this).width() * 0.06,
				sortable : true
			} ] ],
			columns : [ [ 
				 {
					field : 'nickName',
					title : '昵称',
					width : $(this).width() * 0.06,
					sortable : true
				},
				 {
					field : 'officePhone',
					title : '微信ID',
					width : $(this).width() * 0.06,
					sortable : true
				},
				 {
					field : 'mobilePhone',
					title : '移动电话',
					width : $(this).width() * 0.06,
					sortable : true
				},
				 {
					field : 'email',
					title : '电子邮箱',
					width : $(this).width() * 0.09,
					sortable : true
				},
				{
				field : 'roles',
				title : '所属角色',
				width : $(this).width() * 0.09,
				formatter:function(value,row){
					var result="";
					if(value){
						for(var i=0;i<value.length;i++){
							result+=value[i].text+",";
						}
						result=result.substring(0,result.length-1)
					}
					return result;
				}
			},
				{
				field : 'scenes',
				title : '所属场景',
				width : $(this).width() * 0.09,
				formatter:function(value,row){
				  
					var result="";
					if(value){
						for(var i=0;i<value.length;i++){
							result+=value[i].name+",";
						}
						result=result.substring(0,result.length-1)
					}
					return result;
				}
			}, {
				field : 'organization',
				title : '所属项目',
				width : $(this).width() * 0.09,
				formatter:function(value,row){
					var result="";
					if(value){
						for(var i=0;i<value.length;i++){
							result+=value[i].orgName+",";
						}
						result=result.substring(0,result.length-1)
					}
					return result;
				}
			}, {
				field : 'roleId',
				title : '所属角色',
				width : $(this).width() * 0.06,
				hidden : true
			},{
				field : 'fax',
				title : '描述',
				width : $(this).width() * 0.2,
				sortable : true
			}] ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				$(this).datagrid('selectRow', rowIndex);
			}
		});
	});
	function timeInputBoxBindOpenPanel(id){
		var timeInput=$("#"+id).parent().children()[1];
		timeInput=$(timeInput).children()[0];
		$(timeInput).click(function(){
			$("#"+id).datetimebox("showPanel");
		});
	}
	function userRole() {
	  $('#userRoleId').dialog('open');
	}
	function initRoleTree(){
	   sceneTree=$('#sceneId').combotree({
			url : '${ctx}/manual/tree1',
			animate : false,
			lines : false,
			checkbox : true,
			panelHeight:110,
			multiple : true,
			onLoadSuccess : function(node, data) {
				sceneTree.combotree("clear");
				//设置角色选中
				
				if(sceneIds.length>0){
				   var arr=new Array();
					for(var i=0;i<sceneIds.length;i++){
						for(var j=0;j<data.length;j++){
							if(data[j].id==sceneIds[i]){
								arr.push(sceneIds[i]);
							}else{
								
							}
						}
					}
					if(arr.length>0){
						sceneTree.combotree("setValues",arr);
					}
				}
			}
		});
	    //console.log(orgData);
		$("#orgId").combotree("setValues",orgData);
		
	
	
	
		roleTree=$('#roleId').combotree({
			url : '${ctx}/role/tree',
			animate : false,
			lines : false,
			checkbox : true,
			panelHeight:110,
			multiple : true,
			onLoadSuccess : function(node, data) {
				roleTree.combotree("clear");
				//alert(roleIds);
				//设置角色选中
				
				if(roleIds.length>0){
				
				   //alert(roleIds);
				   var arr=new Array();
					for(var i=0;i<roleIds.length;i++){
						for(var j=0;j<data.length;j++){
							if(data[j].id==roleIds[i]){
								arr.push(roleIds[i]);
								//roleTree.combotree("setValue",roleIds[i]);
							}else{
								
							}
						}
					}
					//alert(arr);
					if(arr.length>0){
						roleTree.combotree("setValues",arr);
					}
				}
			}
		});
		
		

		
		
		
		
		
		
		
		
		
	}
	function append() {
		userDialog.dialog('open');
		userForm.form('clear');
		initRoleTree();
		$("input:radio:[value='private']").attr("checked",true);
		$("[name=loginAcct]").removeAttr("readonly");
		$("[name=password]").removeAttr("disabled");
		$("[name=password1]").removeAttr("disabled");
		organTree.combotree("setValue",null);
		//var orgId=organTree.combotree('getValue');
		orgId=[];
		roleIds=[];
		sceneIds=[];
	}

	function edit(index) {
	
	        var rows = $("#datagrid").datagrid("getRows")[index];
			userForm.find('[name=loginAcct]').attr('readonly', 'true');
			userDialog.dialog('open');
			userForm.form('clear');
			userForm.form('load', {
				id : rows.id,
				loginAcct : rows.loginAcct,
				sex:rows.sex,
				fax:rows.fax,
				officePhone:rows.officePhone,
				mobilePhone:rows.mobilePhone,
				email:rows.email,
				birthday:rows.birthday,
				nickName:rows.nickName,
				qq:rows.qq,
				taskNumber:rows.taskNumber,
				staff:rows.staff
			});
			//$("#password").attr("disabled","true");
			//$("#password1").attr("disabled","true");
			initRoleTree();
			
	
		//项目
		organ=[];
		if(rows.organization){
		
		for(var i=0;i<rows.organization.length;i++){
			organ.push(rows.organization[i].id);
			}
		
		}
		if(organ){
				organTree.combotree("setValues",organ);
			}
		
		
			roleIds=[];
			sceneIds=[];
			if(rows.roles){
				for(var i=0;i<rows.roles.length;i++){
					roleIds.push(rows.roles[i].id);
				}
			}
			
			if(roleIds){
				roleTree.combotree("setValues",roleIds);
			}
			
			if(rows.scenes){
				for(var i=0;i<rows.scenes.length;i++){
				
					sceneIds.push(rows.scenes[i].id);
				}
			}
			if(sceneIds){
				sceneTree.combotree("setValues",sceneIds);
			}
			
			
	}
	function controlUser(index){
			var rows = datagrid.datagrid('getSelections');
			var ids = [];
			if (rows.length > 0) {
			$.messager.confirm('请确认', '您要解封当前所选用户？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${ctx}/user/open',
						data :JSON.stringify(ids),
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(response) {
							datagrid.datagrid('unselectAll');
							datagrid.datagrid('reload');
							myMessage("解封成功");
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要解封的记录！', 'error');
		}
		}
	function removeUser() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${ctx}/user/del',
						data :JSON.stringify(ids),
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(response) {
							datagrid.datagrid('unselectAll');
							datagrid.datagrid('reload');
							myMessage("删除成功");
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}

	function searchFun() {
		datagrid.datagrid('load', {
			loginAcct 	: $('#toolbar input[name=SloginAcct]').val(),
			nickName 	: $('#toolbar input[name=SnickName]').val(),
			officePhone : $('#toolbar input[name=SofficePhone]').val(),
			mobilePhone : $('#toolbar input[name=SmobilePhone]').val(),
			email 		: $('#toolbar input[name=Semail]').val(),
			fax 		: $('#toolbar input[name=Sfax]').val(),
			rolesID		: $("#Sroles").combobox('getValue'),
			scenesID	: $("#SsceneId").combobox('getValue'),
			organId 	: $("#SorgId").combobox('getValue')
		});
	}
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
	/*提示消息弹出框函数*/
	function myMessage(mg)
	{
	   $.messager.alert('提示',mg);
	}
	function fileSelected() {
	        var file = document.getElementById('file').files[0];
	        var fileName = file.name;
	        //alert(fileName);
	        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
	    
	        if (file_typename == '.xls'||file_typename == '.xlsx') {//这里限定上传文件文件类型
	            if (file) {
					fileFlag = true;
	                $("#uploadFile").show();
	                var fileSize = 0;
	                if (file.size > 1024 * 1024)
	                    fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
	                else
	                    fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
	
	                document.getElementById('fileName').innerHTML = '文件名: ' + file.name;
	                document.getElementById('fileSize').innerHTML = '大小: ' + fileSize;
	                document.getElementById('fileType').innerHTML = '类型: ' + file.type;
	            }
	
	        }
	        else {
	        	fileFlag = false;
	            $("#uploadFile").hide();
	            document.getElementById('fileName').innerHTML = "<span style='color:Red'>错误提示:上传文件应该是.xls后缀而不应该是" + file_typename + ",请重新选择文件</span>"
	            document.getElementById('fileSize').innerHTML ="";
	            document.getElementById('fileType').innerHTML ="";
	
	        }
   	 	}
	
	//重置密码
	function resetPass(){
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要重置所选用户的密码？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${ctx}/user/resetPass',
						data :JSON.stringify(ids),
						cache : false,
						dataType : "json",
						contentType:"application/json",
						success : function(response) {
							datagrid.datagrid('unselectAll');
							datagrid.datagrid('reload');
							myMessage("重置成功");
						},
						error: function(re){
						$.messager.alert('提示', '数据异常', 'error');
						
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要重置密码的用户！', 'error');
		}
	}
	
	function userExport(){
			$.messager.confirm('请确认', '您要导出项目？', function(r) {
					if (r) {
				      	window.open('${ctx}/user/export',"","_blank");
					}
				});
		}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
			<fieldset class="my_fieldset" >
				<legend class="my_legend">检索</legend>
				<table>
					<tr>
						<td>登录账号：</td>
						<td colspan="2">
							<input name="SloginAcct" class="basic_input"/>
						</td>
						<td>昵称：</td>
						<td colspan="2">
							<input name="SnickName" class="basic_input"/>
						</td>
						<td>微信ID：</td>
						<td colspan="2">
							<input name="SofficePhone" class="basic_input"/>
						</td>
					</tr>
					<tr>
						<td>移动电话：</td>
						<td colspan="2">
							<input name="SmobilePhone" class="basic_input"/>
						</td>
						<td>电子邮箱：</td>
						<td colspan="2">
							<input name="Semail" class="basic_input"/>
						</td>
						<td>描述：</td>
						<td colspan="2">
							<input name="Sfax" class="basic_input"/>
						</td>
					</tr>
					<tr>
						<td>所属角色：</td>
						<td colspan="2">
							<input name="Sroles" id="Sroles" class="basic_input"/>
						</td>
						<td>所属场景：</td>
						<td colspan="2">
							<input name="SsceneId" id="SsceneId" class="basic_input"/>
						</td>
						<td>所属项目：</td>
						<td colspan="2">
							<input name="SorgId" id="SorgId" class="basic_input"/>
						</td>
						<td>
							&nbsp;
							<a class="easyui-linkbutton" iconCls="icon-search"  onclick="searchFun();" href="javascript:void(0);">查 找</a>
							<a class="easyui-linkbutton" iconCls="icon-empty"  onclick="clearFun();" href="javascript:void(0);">清 空</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<div class="buttonList">
				<#if fmfn.checkButton(requestURI,"icon-add")>
					<a class="easyui-linkbutton" iconCls="icon-add" onclick="append();"  href="javascript:void(0);">增 加</a> 
				</#if>
				<#if fmfn.checkButton(requestURI,"icon-remove")>
					<a class="easyui-linkbutton" iconCls="icon-remove" onclick="removeUser();"   href="javascript:void(0);">删 除</a>
				</#if>
				<a class="easyui-linkbutton" iconCls="icon-print"  onclick="userRole();" href="javascript:void(0);">导入用户角色信息</a>
				<a class="easyui-linkbutton" iconCls="icon-print"  onclick="userExport();" href="javascript:void(0);">导出</a>
				<#if fmfn.checkButton(requestURI,"icon-batch")>
					<a class="easyui-linkbutton" iconCls="icon-batch" onclick="resetPass();"   href="javascript:void(0);">重置密码</a>
				</#if>
				
				
				
				
				
			</div>
		</div>
		<table id="datagrid"></table>
	</div>

	<div id="userDialog" style="display: none;overflow: hidden;background:#FFFFFF">
		<form id="userForm" method="post">
			<table class="tableForm" style="margin-left:30px;margin-top:30px;">
				<tr style="display:none;">
					<th width="80">用户ID</th>
					<td><input name="id"  /></td>
				</tr>
				<tr>
					<th>登录名称：</th>
					<td><input id="loginAcct" name="loginAcct"  style="width: 196px;" class="easyui-validatebox" required="true" validType="length[1,32]" /></td>
				</tr>
				<tr>
					<th>登录密码：</th>
					<td><input id="password" type="password" name="password"  style="width: 196px;" class="easyui-validatebox" validType="length[6,32]" /></td>
				</tr>
				<tr>
					<th>确认密码：</th>
					<td><input id="password1" type="password" name="password1"  style="width: 196px;" class="easyui-validatebox" validType="eqPassword[password]"/></td>
				</tr>
				<tr>
					<th>姓&#12288;&#12288;名：</th>
					<td><input id="nickName" name="nickName"  style="width: 196px;" class="easyui-validatebox" required="true" validType="length[1,20]"/></td>
				</tr>
				<tr>
					<th>性&#12288;&#12288;别：</th>
					<td>
						<input name="sex"  type="radio" value="1" style="width:15px;"/>男
						<input name="sex"  type="radio" value="2" style="width:15px;"/>女
						<input name="sex"  type="radio" value="0" style="width:15px;" />保密
					</td>
				</tr>
				<tr>
					<th>生&#12288;&#12288;日：</th>
					<td><input id="birthday" name="birthday"  style="width: 196px;" class="easyui-datetimebox" /></td>
				</tr>
				<tr>
					<th>微信ID：</th>
					<td><input id="officePhone" name="officePhone"  style="width: 196px;" class="easyui-validatebox" validType="length[1,200]"/></td>
				</tr>
				<tr>
					<th>移动电话：</th>
					<td><input id="mobilePhone" name="mobilePhone"  style="width: 196px;" class="easyui-validatebox" validType="mobile"/></td>
				</tr>
				<tr>
					<th>个人描述：</th>
					<td><input id="fax" name="fax"  style="width: 196px;" validType="length[1,200]" /></td>
				</tr>
				<tr>
					<th>电子邮箱：</th>
					<td><input id="email" name="email"  style="width: 196px;" class="easyui-validatebox" validType="email" /></td>
				</tr>
				<tr>
					<th>q&#12288;&#12288;q：</th>
					<td><input id="qq" name="qq"  style="width: 196px;" class="easyui-validatebox" validType="qq"/></td>
				</tr>
				
				
				
				<tr>
					<th >所属项目：</th>
					<td><select id="orgId"  multiple="true"   cascadeCheck="false"  name="orgId" style="width: 200px;" required="true"></select></td>
				</tr>
			
				
				<tr>
					<th >所属角色：</th>
					<td><select id="roleId" name="roles.id" style="width: 200px;" required="true"></select></td>
				</tr>
				
				<tr>
					<th >场景板块：</th>
					<td><select id="sceneId" name="scenes.id" style="width: 200px;" required="true"></select></td>
				</tr>
				
				<tr>
					<th>任务量</th>
					<td><input id="taskNumber" name="taskNumber"  style="width: 196px;"  validType="length[1,3]" class="easyui-numberbox" /></td>
				</tr>
				
				<!--<tr>
					<th >对应人员：</th>
					<td><select id="staff" name="staff" style="width: 200px;" required="true"></select></td>
				</tr>-->
			</table>
		</form>
	</div>
	
	<!--批量导入人员角色信息-->
		<div id="userRoleId" class="easyui-dialog" closed="true" style="width:730px;height:410px;" title="批量导入人员角色信息">
			<div>
				<form  id="userRoleIdForm" method="post"  enctype="multipart/form-data"  >
			        <table>
			        	
			        	<tr>
			        		<td>请选择文件</td>
			        		<td colspan='2'>
						        <input id="file" type="file" name="file" onchange="fileSelected()"/>
			        		</td>
			        	</tr>
			        	<tr>
							<td>
						        <div id="fileName" style="padding: 2px"></div>
							</td>
			        	</tr>
			        	<tr>
							<td>
				                <div id="fileSize" style="padding: 2px"></div>
							</td>
			        	</tr>
			        	<tr>
							<td>
				                <div id="fileType" style="padding: 2px"></div>   
							</td>
			        	</tr>
			        	<tr>
							<td>
				               
							</td>
			        	</tr>
			        	
			        </table>
	   			</form> 
	   		</div>
		</div>
	<div id="repeatDialog">
			<div id="repeatView">
			</div>
		</div>
</body>
</html>