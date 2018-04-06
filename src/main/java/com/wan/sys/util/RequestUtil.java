package com.wan.sys.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.WebRequest;


/**   
 * 文件名称：Request工具类
 * 内容摘要： 
 * 创建人： huangfei
 * 创建日期： 2015年5月28日
 * 版本号： v1.0.0
 * 公  司：亚德科技股份有限公司
 * 版权所有： (C)2001-2015     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 **/ 
public class RequestUtil {
	/**
	 * 获得请求路径
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = null;
		if(!StringUtils.isBlank(request.getQueryString())) {
			requestPath = request.getRequestURI() + "?" + request.getQueryString();
		} else {
			requestPath = request.getRequestURI();
		}
		
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length());// 去掉项目路径
		return requestPath;
	}
	/**
	 * 是否是ajax请求 
	 * @param request：HttpServletRequest
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	/**
	 * 是否是ajax请求 
	 * @param webRequest：spring WebRequest 
	 * @return boolean
	 */
	public static boolean isAjaxRequest(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}
}
