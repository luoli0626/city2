package com.wan.sys.controller.organization;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.AreaBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.OrganBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.organization.IOrganizationService;
import com.wan.sys.service.user.IUserService;

/**
 * 
 * 文件名称: 组织机构controller 内容摘要: //简要描述本文件的内容，包括主要模块、函数及其功能的说明 创 建 人: 创建日期: Dec 6,
 * 2013 公 司: 亚德科技股份有限公司 版权所有: 版权所有(C)2001-2004
 * 
 * 修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容 修改日期： 版 本 号： 修 改 人： 修改内容： 修改记录2：…
 * 
 */
/**
 * 
 * 文件名称： 组织机构controller
 * 内容摘要： 简要描述本文件的内容，包括主要模块、函数及其功能的说明 
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
@RequestMapping("/organ")
public class OrganizationController {
	
	@Autowired
	private IBaseDao baseDao;
	private IOrganizationService organService;
	@Autowired
	private IUserService userService;
	@Autowired
	public void setOrganService(IOrganizationService organService) {
		this.organService = organService;
	}

	@RequestMapping(value = "/main")
	public String toOrgan(HttpServletRequest request) {
		return "sys/organization/organization";
		//return "device/project/projectTree";
	}
	
	@RequestMapping(value = "/tMain")
	public String tMain(HttpServletRequest request) {
		//return "sys/organization/organization";
		return "device/project/projectTree";
	}
	
	
	/**
	 * 项目树
	 * @param projectPo -查询对象
	 * @return
	 */
	@RequestMapping(value = "/projectTree")
	@ResponseBody
	public List<TreeNodeBean> projectTree(OrganBean organBean) {
		return organService.projectTree(organBean);
	}
	
	
	
	

	/**
	 * Description:查询机构树 <br>
	 * 
	 * @param organBean
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<TreeNodeBean> tree(OrganBean organBean) {
		return this.organService.tree(organBean);
	}

	/**
	 * Description:查询机构树 <br>
	 * 
	 * @param organBean
	 * @return
	 */
	@RequestMapping(value = "/treeCode")
	@ResponseBody
	public List<TreeNodeBean> treeCode(OrganBean organBean) {
		return this.organService.treeCode(organBean);
	}

	@RequestMapping(value = "/findOrgTreeByLoginUser")
	@ResponseBody
	public List<TreeNodeBean> findOrgTreeByLoginUser() {
		//根据登录用户查找组织机构信息
		UserBean user=GlobalContext.getCurrentUser();
		//重新从数据库中再次查找用户的组织机构信息
		List<Organization> org=null;
		if(user!=null){
			org=user.getOrganization();
		}
		//判断是否是超级管理员
		if(user.getId()==0){
			org=null;
		}
		return this.organService.treeId(org);
	}
	@RequestMapping(value = "/findOrgTreeByLoginUser1")
	@ResponseBody
	public List<TreeNodeBean> findOrgTreeByLoginUser1() {
		//根据登录用户查找组织机构信息
		UserBean user=GlobalContext.getCurrentUser();
		//重新从数据库中再次查找用户的组织机构信息
		//user=userService.getUserInfo(user);
		List<Organization> org=null;
		if(user!=null){
			org=user.getOrganization();
		}
		
		//判断是否是超级管理员
		if(user.getId()==0){
			org=null;
		}
		return this.organService.treeId(org);
	}
	
	@RequestMapping(value = "/findOrgTreeByLoginRole",method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeNodeBean> findOrgTreeByLoginRole() {
		Organization org=null;
		return this.organService.treeId1(org);
	}

	@RequestMapping(value = "/findChildrens")
	@ResponseBody
	public List<TreeNodeBean> findChildrens(String orgId) {
		return this.organService.findChildren(orgId);
	}

	@RequestMapping(value = "/findChildren")
	@ResponseBody
	public void tree(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		String orgId = request.getParameter("orgId");
		List<TreeNodeBean> lists = this.organService.findChildren(orgId);
		String orgData = "[";
		if (null != lists && lists.size() > 0) {
			for (int i = 0; i < lists.size(); i++) {
				orgData += i == 0 ? "{id:'" + lists.get(i).getId() + "',text:'"
						+ lists.get(i).getText() + "'}" : ",{id:'"
						+ lists.get(i).getId() + "',text:'"
						+ lists.get(i).getText() + "'}";
			}
		}
		orgData += "]";
		StringBuilder sbResult = new StringBuilder();
		sbResult.append("{");
		sbResult.append("orgData:{0}".replace("{0}", orgData));
		sbResult.append("}");
		response.getWriter().print(sbResult.toString());
	}

	/**
	 * Description: 查询机构树<br>
	 * 
	 * @param organBean
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public OrganBean search(OrganBean organBean) {
		return this.organService.search(organBean);
	}

	/**
	 * Description: 添加或修改机构<br>
	 * 
	 * @param organ
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Json add(@RequestBody OrganBean organ) {
		Json j = new Json();
		j.setSuccess(true);
		organ=organService.add(organ);
		return j;
	}
	/**
	 * Description: 添加或修改机构<br>
	 * 
	 * @param organ
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Json update(@RequestBody OrganBean organ) {
		Json j = new Json();
		j.setSuccess(true);
		organ=organService.update(organ);
		return j;
	}
	/**
	 * 删除
	 */
	@RequestMapping(value="/del")
	public @ResponseBody Boolean del(@RequestBody String[] ids,HttpServletRequest request) {
		return organService.del(ids);
	}
	/**
	 * Description: 查询机构详情<br>
	 * 
	 * @param organ
	 * @return
	 */
	@RequestMapping(value = "/findOrgan")
	@ResponseBody
	public Json findOrgan(OrganBean organ) {
		OrganBean org = this.organService.findOrgan(organ);
		Json j = new Json();
		j.setSuccess(true);
		j.setObj(org);
		return j;
	}
	/**
	 * 
	 * @Description 获取treegrid
	 * @param organ
	 * @return Json
	 */
	@RequestMapping(value = "/treegrid")
	@ResponseBody
	public DataGridJson treegrid(String orgId) {
		DataGridJson dgj=organService.findTreeGridByPid(orgId);
		return dgj;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @Description  调整排序
	 * @param organ
	 * @return Boolean
	 */
	@RequestMapping(value = "/updateSort")
	@ResponseBody
	public Boolean updateSort(OrganBean organBean,String moveFlag) {
		return organService.updateSort(organBean,moveFlag);
	}
	
	//机构类型combo
	@RequestMapping(value = "/typeCombo")
	@ResponseBody
	public List<Map<String,Object>> typeCombo(){
		return baseDao.find("select b from business b,sys_organization o where b in elements(o.orgType)");
		//return areaService.typeCombo();
	}
	
	
	@RequestMapping(value = "/findOrgTreeLift")
	@ResponseBody
	public List<TreeNodeBean> findOrgTree() {

		return this.organService.treeIdLift();
	}
	
	
	/**
	 * 同步项目数据
	 * @return
	 */
	@RequestMapping(value="pullProjectList")
	@ResponseBody
	public Json pullProjectList() {
		Json j = new Json();
		try {
			organService.pullProjectList();
			j.setSuccess(true);
			j.setMsg("同步成功！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	
}
