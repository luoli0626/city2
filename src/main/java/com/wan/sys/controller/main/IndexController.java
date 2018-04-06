package com.wan.sys.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wan.sys.common.GlobalContext;
import com.wan.sys.pojo.UserBean;

/**
 * 
 * 文件名称： 首页Controller
 * 内容摘要： 首页和登陆
 * 创建人： 唐君左
 * 创建日期： 2017-6-26
 * 版本号： v1.0.0
 * 公  司：金科物业服务有限公司
 * 版权所有： (C)2016-2017     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 *
 */
@Controller
public class IndexController {
	

	@RequestMapping("/index")
	public String index() {
		
		//判断是否第一次登陆密码还在修改中
		
		// System.out.println("..."+requestPath);
			//System.out.println("=="+GlobalContext.getCurrentUser().getTempSession());
		if(null !=GlobalContext.getCurrentUser() && "1".equals(GlobalContext.getCurrentUser().getTempSession())){
			return "login";
		}

		
		return "index";
		
		
	}
	@RequestMapping("/toLogin")
	public String toLogin() {
		UserBean currUser = GlobalContext.getCurrentUser();
		if(currUser!=null){
			//System.out.println("==11.."+currUser.getTempSession());
			if("1".equals(currUser.getTempSession())){
				return "login";
				
			}
			return "redirect:/index";
		}
		return "login";
	}	
}
