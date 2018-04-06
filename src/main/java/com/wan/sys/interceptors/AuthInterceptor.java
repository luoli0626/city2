package com.wan.sys.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.entity.Menu;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.menu.IMenuService;
import com.wan.sys.service.user.ILogService;
import com.wan.sys.util.ConfigUtil;
import com.wan.sys.util.HttpClientUtil;
import com.wan.sys.util.RequestUtil;
import com.wan.sys.util.StringUtil;


/**
 * 
 * 文件名称： 权限拦截器 
 * 内容摘要： 
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
public class AuthInterceptor implements HandlerInterceptor {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

	@Autowired
	private ILogService logService;
	@Autowired
	private IMenuService menuService;
	public ParserConfig parserConfig = new ParserConfig();

	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
		request.setAttribute("requestURI", request.getRequestURI());
	}
	

	
	/**
	 * 在调用controller具体方法前拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
String requestPath = RequestUtil.getRequestPath(request);// 用户访问的资源地址

		
		String rqe=requestPath;
		UserBean currUser=(UserBean)request.getSession().getAttribute(GlobalConstant.CURRENT_USER);
		String ticket=request.getParameter("ticket");
		if(null==currUser&&null!=ticket&&!"".equals(ticket)){
			String url = ConfigUtil.get("getUserTicket");
			String url1 = ConfigUtil.get("getMenuTicket");
			//String orgUrl = ConfigUtil.get("getOrgList");
			Map<String, Object> params = new HashMap<String, Object>();
			// 调用开发平台接口获取用户数据
			params.put("ticket", ticket);
			String bodyStr = HttpClientUtil.getResponseBodyAsString(url, params);
			if(null!=bodyStr&&!"".equals(bodyStr)){
			// 解析用户数据
			currUser = JSON.parseObject(bodyStr, UserBean.class, parserConfig, JSON.DEFAULT_PARSER_FEATURE);
			if(null!=currUser){
				String menuStr = HttpClientUtil.getResponseBodyAsString(url1, params);
				List<Menu> menus = new ArrayList<Menu>(); 
				JSONArray jsonArray = JSONArray.fromObject(menuStr);//把String转换为json 
				menus = JSONArray.toList(jsonArray,Menu.class);//这里的t是Class<T>
				currUser.setMenus(menus); 
				//获取登陆人机构
//				String orgStr = HttpClientUtil.getResponseBodyAsString(orgUrl, params);
//				List<Organization> orgs = new ArrayList<Organization>(); 				
//				if(StringUtil.isNotBlank(orgStr)){
//					JSONArray jsonArray2 = JSONArray.fromObject(orgStr);//把String转换为json 
//					orgs = JSONArray.toList(jsonArray2,Organization.class);//这里的t是Class<T>
//					System.out.println("用户的项目..."+orgs.size());
//					
//				}
//				currUser.setOrganization(orgs);
				
			}
		}
		request.getSession().setAttribute(GlobalConstant.CURRENT_USER,
						currUser);
		}

		String [] us=requestPath.split("/");
		requestPath="/"+us[1]+"/";
		if("/main/".equals(requestPath)||"/app/".equals(requestPath)||"/house/".equals(requestPath)
				||"/cityApp/".equals(requestPath)){
			
			
			return true;
		}
		if (currUser == null) {// 没有登录系统，或登录超时
			if(RequestUtil.isAjaxRequest(request)){//ajax请求
				response.setHeader("sessionstatus", "timeout");
			}else{
				forward("您没有登录或登录超时，请重新登录！", "/error/timeout.jsp", request, response);
			}
			return false;
		}
		if("1".equals(currUser.getTempSession()) && !"/main/firstUpdate".equals(requestPath))
		{
			forward(null,"/login.ftl", request, response);
			return false;
		}
		return true;
	}

	private void forward(String msg, String url, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher(url).forward(request, response);
	}

}
