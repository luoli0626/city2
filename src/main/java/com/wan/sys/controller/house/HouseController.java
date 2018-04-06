package com.wan.sys.controller.house;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.sys.common.BaseController;
import com.wan.sys.pojo.HouseBean;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.house.IHouseService;

/**
 * 智慧接房模块Controller
 * @author pc
 *
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{

	@Autowired
	IHouseService houseService;
	
	
	
	
	
	
	/**
	 * 获取token
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="getUserToken")
	@ResponseBody
	public ResponseHead getUserToken(HouseBean bean,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.getUserToken(bean);
	}
	
	
	/**
	 * 设置设备所属项目
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="setProject")
	@ResponseBody
	public ResponseHead setProject(HouseBean bean,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.setProject(bean);
	}
	
	
	/**
	 * 发送人证对成功的用户数据
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="checkUser")
	@ResponseBody
	public ResponseHead checkUser(HouseBean bean,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.checkUser(bean);
	}
	
	/**
	 * 存储对比成功的人证信息
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="saveUserInfo")
	@ResponseBody
	public ResponseHead saveUserInfo(HouseBean bean,HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.saveUserInfo(bean,request);
	}
	
	/**
	 * 取号，存储客户表的排队序号，设置用户接房状态N
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="handleHouse")
	@ResponseBody
	public ResponseHead handleHouse(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.handleHouse(bean);
	}
	
	/**
	 * 接房，设置用户接房状态为Y
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="takeHouse")
	@ResponseBody
	public ResponseHead takeHouse(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.takeHouse(bean);
	}
	
	/**
	 * 重置排队序列号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="resetLineNo")
	@ResponseBody
	public ResponseHead resetLineNo(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.resetLineNo(bean);
	}
	
	/**
	 * 设备激活
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/activeDevice")
	@ResponseBody
	public ResponseHead activeDevice(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        return houseService.activeDevice(bean);
	}
	
	/**
	 * 查询数据
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/findListData")
	@ResponseBody
	public ResponseHead findListData(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.findListData(bean);
	}
	
	/**
	 * 绑定设备所属项目
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bindingDevice")
	@ResponseBody
	public ResponseHead bindingDevice(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.bindingDevice(bean);
	}
	
	/**
	 * 查询用户接房的状态
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/findState")
	@ResponseBody
	public ResponseHead findState(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.findState(bean);
	}
	
	/**
	 * 接房过程中获取用户排队号对应的房屋信息
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="getHouseInfo")
	@ResponseBody
	public ResponseHead getHouseInfo(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.getHouseInfo(bean);
	}
	
	/**
	 * app获取token
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getTokenAndUser")
	@ResponseBody
	public ResponseHead getTokenAndUser(String openId, String projectId,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.getTokenAndUser(openId, projectId);
	}
	
	/**
	 * 获取当前正在办理的人数
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/findNowNo")
	@ResponseBody
	public ResponseHead findNowNo(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.findNowNo(bean);
	}
	
	/**
	 * 手动存储用户的信息
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addUserInfo")
	@ResponseBody
	public ResponseHead addUserInfo(HouseBean bean,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		return houseService.addUserInfo(bean);
	}
}
