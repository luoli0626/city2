package com.wan.sys.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.wan.sys.common.BaseController;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.organization.IOrganizationService;
import com.wan.sys.service.user.IUserService;

/**
 * 
 * 文件名称： 用户管理controller
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
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	 private static final String CONTENT_TYPE = "text/html; charset=GBK";
	
	private static final Logger logger = Logger.getLogger(UserController.class);

	private IUserService userService;
	
	@Autowired
	private IOrganizationService organService;

	public IUserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	/**
	 * 跳转到系统探针页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jspinfo",method={RequestMethod.GET})
	public String jspinfo() {
		return "admin/tz/jspinfo";
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userList",method={RequestMethod.GET})
	public String user(HttpServletRequest request) {
		Object o = userService.orgList();
		if(null!=o){
			request.setAttribute("orgList",JSONArray.toJSONString(o));
		}
		return "sys/user/user";
	}
	
	
	
	
	
	
	/**
	 * 跳转到待审核的用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkUserList",method={RequestMethod.GET})
	public String checkUser(HttpServletRequest request) {
//		Object o = userService.orgList();
//		if(null!=o){
//			request.setAttribute("orgList",JSONArray.toJSONString(o));
//		}
		return "sys/user/check_user";
	}

	/**
	 * 跳转到用户信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userInfo",method={RequestMethod.GET})
	public String userInfo() {
		return "sys/user/userInfo";
	}
	
	
	
	@RequestMapping(value = "/findOrgTreeByLoginUser")
	@ResponseBody
	public List<TreeNodeBean> findOrgTreeByLoginUser() {
		//根据登录用户查找组织机构信息
		UserBean user=GlobalContext.getCurrentUser();
		//重新从数据库中再次查找用户的组织机构信息
		//user=userService.getUserInfo(user);
		List<Organization> org= null;
		if(user!=null){
			org=user.getOrganization();
		}
		
		//判断是否是超级管理员
		if(user.getId()==0){
			org=null;
		}
		System.out.println("进入查询...."+user.getLoginAcct());
		return this.organService.treeId(org);
	}

	

	
	
	
	
	
	

	/**
	 * 获得用户表格
	 * 
	 * @param dg
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/loginDatagrid",method={RequestMethod.POST})
	@ResponseBody
	public DataGridJson loginDatagrid(DataGridBean dg, UserBean user) {
		return userService.datagrid(dg, user);
	}

	/**
	 * 获得用户列表
	 * 
	 * @param q
	 * @return
	 */
	@RequestMapping(value = "/loginCombobox",method={RequestMethod.POST})
	@ResponseBody
	public List<UserBean> loginCombobox(String q) {
		return userService.combobox(q);
	}

	/**
	 * 用户表格
	 * 
	 * @param dg
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/datagrid")
	@ResponseBody
	public DataGridJson datagrid(DataGridBean dg, UserBean user) {
		DataGridJson dgj=userService.datagrid(dg, user);
		return dgj;
	}
	
	
	
	
	/**
	 * 待审核用户表格
	 * 
	 * @param dg
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/checkUserDatagrid")
	@ResponseBody
	public DataGridJson checkUserDatagrid(DataGridBean dg, UserBean user) {
		DataGridJson dgj=userService.checkUserDatagrid(dg, user);
		return dgj;
	}
	
	/**
	 * 审核状态下拉列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/checkStatusTree")
	@ResponseBody
	public List<TreeNodeBean> checkStatusTree() {
		return userService.getCheckStatusTree();
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method={RequestMethod.POST})
	@ResponseBody
	public Json add(@RequestBody UserBean user) {
		Json j = new Json();
		try{
			//判断用户登录帐号唯一
			if(userService.checkLoginAcct(user.getLoginAcct())){
				user=userService.add(user);
				j.setSuccess(true);	 
			}else{
				j.setSuccess(false);
				j.setMsg("该帐号已经存在！");
			}
		 }
		 catch(Exception e){
			 
			 j.setSuccess(false);
		 }
		 return j; 
	}

	/**
	 * 编辑用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method={RequestMethod.POST})
	@ResponseBody
	public Json  edit(@RequestBody UserBean user) {
		Json j = new Json();
		 try{
			 userService.edit(user);
			 j.setSuccess(true);
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 j.setSuccess(false);
			 
		}
		return j;
	}
	
	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/del",method={RequestMethod.POST})
	@ResponseBody
	public Json del(@RequestBody String[] ids) {
		Json j = new Json();
		userService.deleteById(ids);
		j.setSuccess(true);
		return j;
	}
	/**
	 * 解封用户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/open",method={RequestMethod.POST})
	@ResponseBody
	public Json open(@RequestBody String[] ids) {
		Json j = new Json();
		userService.updateRecordStatus(ids);
		j.setSuccess(true);
		return j;
	}
	/**
	 * 通过审核
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/passCheck",method={RequestMethod.POST})
	@ResponseBody
	public Json passCheck(@RequestBody String[] ids) {
		Json j = new Json();
		userService.passCheckById(ids);
		j.setSuccess(true);
		return j;
	}
	/**
	 * 重置密码
	 * @param ids
	 * @return
	 */
	
	@RequestMapping(value = "/resetPass",method={RequestMethod.POST})
	@ResponseBody
	public Json resetPass(@RequestBody String[] ids) {
		
		Json j = new Json();
		if(null != ids && ids.length>0){
			try {
				userService.resetPass(ids);
				j.setSuccess(true);
			} catch (Exception e) {
				j.setSuccess(false);
			}
			
			
		}
	
		return j;
	}

	
	
	
	@RequestMapping(value = "/aaaa")
	public String aaa() {
		return "portal/index";
	}
	
	
//	@RequestMapping(value = "/export",method={RequestMethod.GET})
//	@ResponseBody
//	public void exportUserInfo(HttpServletResponse response,HttpServletRequest request){
//		HSSFSheet sheet = userService.export(response, request);
//		try {
//			response.setHeader("Content-Disposition","attachment;filename=ExportUserInfo.xls");
//			response.setContentType("application/vnd.ms-excel");
//			ServletOutputStream outputStream = response.getOutputStream();
//			sheet.getWorkbook().write(outputStream);
//			// 确保发送的当前文本格式
//			outputStream.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	

	
	
	
}
