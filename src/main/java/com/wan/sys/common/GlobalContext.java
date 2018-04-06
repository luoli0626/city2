package com.wan.sys.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wan.sys.pojo.UserBean;



/**
 * 
 * 文件名称： 全局上下文
 * 内容摘要： 获取全局的一些参数信息，如 request、response、session、当前登录的用户
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
public class GlobalContext {

	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpSession getSession() {
		if (getRequest()==null) {
			return null;
		}
		return (HttpSession) getRequest().getSession();
	}
	
	/**
	 * @Description 获取当前登录用户
	 * @return UserBean
	 */
	public static UserBean getCurrentUser() {
		if (getSession()==null) {
			return null;
		}
		UserBean user = (UserBean) getSession().getAttribute(GlobalConstant.CURRENT_USER);
		return user;
	}
	/**
	 * @Description 获取当前登录用户
	 * @return UserBean
	 */
	public static UserBean getWenxinUser() {
		if (getSession()==null) {
			return null;
		}
		UserBean user = (UserBean) getSession().getAttribute(GlobalConstant.WEIXIN_USER);
		return user;
	}
	
	public static String getWenxinUserId() {
		if (getSession()==null) {
			return null;
		}
		 String userId= (String) getSession().getAttribute(GlobalConstant.WEIXIN_USER_ID);
		return userId;
	}
	
	public static void setSessionAttribute(String key,Object obj){
		getSession().setAttribute(key, obj);
	}
	
	public static Object getSessionAttribute(String key){
		return getSession().getAttribute(key);
	}

}
