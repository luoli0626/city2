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
	@RequestMapping(value="editInfo")
	@ResponseBody
	public ResponseHead editInfo(UserBean bean){
		return cityServive.editInfo(bean);
	}
	
	
	/**
	 * 重置密码
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="resetPwd")
	@ResponseBody
	public ResponseHead resetPwd(UserBean bean){
		return cityServive.resetPwd(bean);
	}
	
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="editPwd")
	@ResponseBody
	public ResponseHead editPwd(UserBean bean){
		return cityServive.editPwd(bean);
	}
	
	
	/**
	 * 我的随手拍及详情
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="myPhoto")
	@ResponseBody
	public ResponseHead myPhoto(UserBean bean){
		return cityServive.myPhoto(bean);
	}
	
	/**
	 * 我的发表及详情，评论
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="myPublish")
	@ResponseBody
	public ResponseHead myPublish(UserBean bean){
		return cityServive.myPublish(bean);
	}
	
	/**
	 * 我的失物招领
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="myFound")
	@ResponseBody
	public ResponseHead myFound(UserBean bean){
		return cityServive.myFound(bean);
	}
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 */
	public void uploadImage(HttpServletRequest request,HttpServletResponse response){
		
	}
	
	/**
	 * 三方登陆
	 * @return
	 */
	@RequestMapping(value="otherLogin")
	@ResponseBody
	public ResponseHead otherLogin(UserBean bean){
		return cityServive.otherLogin(bean);
	}
	
	
}
