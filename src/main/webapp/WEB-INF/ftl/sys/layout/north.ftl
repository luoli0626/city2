<#include "/inc/meta.ftl"/>
<script type="text/javascript" charset="UTF-8">
	function logout(b) {
		$.post('${ctx}/main/logout', function() {
			if (b) {
					location.replace(sy.bp()+'/toLogin');
			} else {
				location.replace(sy.bp()+'/toLogin');
			}
		});
	}
    $(document).ready(function(){
      $(".header_right .r_iconbox:nth-of-type(2) ").hover(function(){
          $(".header-lw-hint").css("display","block");
      },function(){

            $(".header-lw-hint").css("display","none");
      });
      
      var myDate = new Date();
     

       
       $("#dataTimes").text(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
       
    });
	function userInfo() {
		addTabFun({
			src : '${ctx}/main/userInfo',
			title : '个人中心'
		});
	}
	function toIndex(){
		window.location.href="${ctx}/index";
	}
	
	function toEditPassword(){
		addTabFun({
			src : '${ctx}	/user/updatePwd',
			title : '修改密码'
		});
	}
	
	

	
</script>
<div class="left_logo">
<img src="static/images/bpmx3_logo_1.png">
</div>

<div class="header_right">
     <div class="r_iconbox">
        <a href="javascript:userInfo();">
           <div class="txt1" id="dataTimes">2017-06-04</div>
        </a>
     </div>
     <div class="r_iconbox">
          <a href="javascript:userInfo();">
             <div class="txt1">${(user.nickName)!''} ∨</div>
          </a>
          <div class="header-lw-hint">
                 <div class="lw-list">
                     <a href="javascript:toIndex();" >
                        <span class="l-btn-text icon-lw-home lw-icon" ></span>
                        <span>返回首页<span>
                     </a>
                     <a  href="javascript:userInfo();" >
                        <span class="l-btn-text icon-lw-mine lw-icon" ></span>
                        <span>个人信息<span>
                     </a>
                     <a href="javascript:toEditPassword();" >
                        <span class="l-btn-text icon-lw-chang-password lw-icon" ></span>
                        <span>修改密码<span>
                     </a>
                 </div>

                 <div class="close-login"><a  href="javascript:logout(true);" >退出管理控制台</a></div>

           </div>
      </div>
	  <div class="r_iconbox">
	     <a>
              <div class="txt1">  <#if (user.roles)??&&(user.roles)?size gt 0>
    		
    			<#list user.roles as r >
    		 	   ${(r.text)!''}
    		 	   <#break>
    			</#list>
    	
    	<#else> 
     			无角色
    	</#if></div>
         </a>
       </div>
</div>

