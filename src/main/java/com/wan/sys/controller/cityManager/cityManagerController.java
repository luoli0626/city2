package com.wan.sys.controller.cityManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.sys.pojo.HouseBean;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.cityManager.IcityManagerService;

/**
 * 智慧城管app接口
 * @author luoli
 *
 */

@Controller
@RequestMapping(value="cityApp")
public class cityManagerController {

	
	@Autowired
	IcityManagerService cityServive;
	/**
	 * 登陆并获取token
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="toLogin")
	@ResponseBody
	public ResponseHead toLogin(UserBean bean,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return cityServive.toLogin(bean);
	}
	
	/**
	 * 获取验证码
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="getCode")
	@ResponseBody
	public ResponseHead getCode(UserBean bean){
		return cityServive.getCode(bean);
	}
	
	/**
	 * 注册接口
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="toRegister")
	@ResponseBody
	public ResponseHead toRegister(UserBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return cityServive.toRegister(bean);
	}
	
	/**
	 * 展示我的资料
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="myDetails")
	@ResponseBody
	public ResponseHead myDetails(UserBean bean){
		return cityServive.myDetails(bean);
	}
	
	/**
	 * 编辑或添加我的资料
	 * @param bean
	 * @return
	 */
	public ResponseHead editInfo(UserBean bean){
		return cityServive.editInfo(bean);
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 */
	public void uploadImage(HttpServletRequest request,HttpServletResponse response){
		
	}
	
	
	
}
