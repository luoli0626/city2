<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${webSiteName}</title>
<#include "/inc/meta.ftl"/>
<#include "/inc/easyui.ftl"/>
<link href="${static}/css/styles.css" rel="stylesheet" type="text/css" media="screen"/>
<script type="text/javascript" charset="UTF-8">	
  var updatePasswordDialog;
  var passwordForm;

      	$.extend($.fn.validatebox.defaults.rules, {
        /*必须和某个字段相等*/
        equalTo: { 
        validator: function (value, param) 
        { return $(param[0]).val() == value; }, message: '字段不匹配' 
        },
      
        mobile: {// 验证手机号码
                validator: function (value) {
                    return /^(13|15|18|17)\d{9}$/i.test(value);
                },
                message: '手机号码格式不正确'
            }

       });
   	$(function() {
   	passwordForm = $("#passwordForm").form();
   	  updatePasswordDialog=$("#updatePasswordDialog").show().dialog({
		title: '第一次登录，请修改密码!',   
   	 	width:350,   
    	height: 200,   
    	closed: false,   
    	cache: false,   
    	modal: true,
    	buttons : [ {
				text : '确定',
				style:'text-align:center',
				handler : function() {
				if(passwordForm.form('validate')){
				if($("#id").val() ==''){
				$.messager.alert('提示信息',"数据异常，请联系管理员!");
				return ;
				}
				var formData={
						"id":$("#id").val(),
						 "password":$("#password").val(),
						"mobilePhone":$("#mobilePhone").val()
	
							};
					    var url="${ctx}/main/firstUpdate";
						$.ajax({
						  type: 'POST', 
							url:url, 
							data:JSON.stringify(formData),
							contentType:"application/json",
				 			success:function(result){
								if (result.success) {
								updatePasswordDialog.dialog('close');
								//$.messager.confirm('提示', '密码修改成功!', function(r){
								//if (r){
								  
								
										var w = window.open("","_self");
									    w.location.href="${ctx}/index";
									  // }
								//});

							
								}else{
								
									if(result.msg){
										$.messager.alert('密码修改',"密码修改失败，请联系管理员！");
									}else{
										$.messager.alert('密码修改',"密码修改失败，请联系管理员！");
									}
								}
							}
						});
	
					}
				}
			} ] 
		

		}).dialog('close');
	

   	   var msg = '${msg!""}';
   	    if(null!=msg && ""!=msg){
   	    	alert(msg);
   	    }
   		$("#loginAcct").focus();
		var f = $("#loginInputForm");
		f.on('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				$.post('${ctx}/main/login', f.serialize(), function(result) {
				 //判断是否第一次登陆
					if(result.obj){
					if(null == result.obj.isFirstLog || '1' == result.obj.isFirstLog || '' == result.obj.isFirstLog){
					  passwordForm.form('clear');
					  updatePasswordDialog.dialog('open');
					  $("#id").attr("value",result.obj.id);
				}
				}
				else{
					if (result.success) {
						var w = window.open("","_self");
						w.location.href="${ctx}/index";
					}else {
					   myMessage(result.msg);
					  $("#loginAcct").blur();
					}
				}
					
				});
			}
		});
    });
   	function myMessage(mg)
	{
	   $.messager.alert('提示',mg);
	}
	
	function login(){
			var f = $('#loginInputForm');
				$.post('${ctx}/main/login', 
				f.serialize(), 
				function(result) {
				//判断是否第一次登陆
				if(result.obj){
					if(null == result.obj.isFirstLog || '1' == result.obj.isFirstLog || '' == result.obj.isFirstLog){
					  passwordForm.form('clear');
					  updatePasswordDialog.dialog('open');
					  $("#id").attr("value",result.obj.id);
				}
				}
				else{
					if (result.success) {
				
						var w = window.open("","_self");
						w.location.href="${ctx}/index";
					}else {
					   myMessage(result.msg);
					  $("#loginAcct").focus();
					}
				}
				
	
			
					
					
					
				});						
	}
</script>
</head>
<body style="height:100%;" class="body1"> 
 <div class="login">
 	<div class="login_mid" id="loginAndRegDialog">
   <div class="dlk">
   	<form id="loginInputForm" method="post" contentType="text/html">  
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table1">
  <tr>
    <td width="25%" class="st1">&nbsp;</td>
    <td width="75%" class="st1"><span style="display:none;">密码输入错误，请重新输入！</span></td>
  </tr>
  <tr>
    <td class="st2">用户名</td>
    <td><input name="loginAcct" id="loginAcct" type="text" required="true" value="" class="textbox1" /></td>
  </tr>
  <tr>
    <td class="st2">密&nbsp;&nbsp;&nbsp;&nbsp;码</td>
    <td><input id="inputthispwd" name="password" type="password" required="true" value="" class="textbox1"/></td>
  </tr>
  
  
  
  
  
  
  <tr>
    <td>&nbsp;</td>
    <td>
      <div class="btnbox">
        
        <a style="cursor:pointer;"  class="link1" onclick="login();"></a>
        <!--
        	<a href="#" class="link2"></a>
        	<a href="#" class="link1"></a> 
        -->
      </div>
    </td>
  </tr>
   <tr>
    <td ></td>
    <td>
   <font color="#FFFF">  温馨提示：为保证您的使用体验，建议使用<a href="http://download.firefox.com.cn/releases/stub/official/zh-CN/Firefox-latest.exe" title="点击下载" style="text-decoration:underline;" ><font color="red"> 火狐浏览器</font></a></font>
    </td>
  </tr>
 <!-- <tr>
    <td colspan="2">
    <img src="${static}/images/app.png" style="width:100px;"/><br/>
             手机app扫码下载
    </td>
  </tr>-->
</table>





 </form> 
   </div>
 </div>
	  <!-- <div class="login_box">
	    <div class="login_box_mid" id="loginAndRegDialog">  
	         <div class="loginbox">         
	         	<form id="loginInputForm" method="post" contentType="text/html">     
		           <div class="line1 line1_h">
		             <input id="loginAcct" name="loginAcct" type="text" required="true" value="admin" class="txtbox1"/>
		           </div>
		           
		           <div class="line1 line1_h2">
		             <input input id="inputthispwd" name="password" type="password"  required="true" value=""   class="txtbox1"/>
		           </div>     
		          <div class="line2">
		             <div class="zhmm"></div>
		             <input name="input" type="button" class="loginbtn" onclick="login();"/>
		           </div>   
		         </form>  
	         </div>
	    </div>
	   </div>-->
 </div>
 	<div id="updatePasswordDialog" style="display: none;background:#FFFFFF;overflow:scroll;">
		<form id="passwordForm" method="post">
			<table class="tableForm" style="margin-left:30px;margin-top:30px;">
					<tr style="display:none;">
					<th width="80">ID</th>
					<td><input id="id" name="id" type="hidden" />
					</td>
				</tr>
				<tr>
					<th>新密码：</th> 
					<td><input id="password" name="password" class="easyui-validatebox"   type="password"   required="true" /></td>
				</tr>
				<tr>
					<th>重复新密码：</th>
					<td><input id="password1"   name="password1" class="easyui-validatebox"   type="password"   required="true" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"   /></td>
				</tr>
				
					<tr>
					<th>手机号码：</th>
					<td><input id="mobilePhone"  name="mobilePhone"  class="easyui-validatebox" validtype="mobile"  required="true"    /></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>