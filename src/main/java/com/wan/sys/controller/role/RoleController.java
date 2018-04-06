package com.wan.sys.controller.role;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.sys.common.BaseController;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.RoleBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.role.IRoleService;


/**
 * 
  * 文件名称: 角色控制器
 * 内容摘要: //简要描述本文件的内容，包括主要模块、函数及其功能的说明 
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
@RequestMapping("/role")
public class RoleController extends BaseController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(RoleController.class);

	private IRoleService roleService;

	public IRoleService getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * 跳转到角色管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/roleList")
	public String role() {
		return "sys/role/role";
	}

	/**
	 * 角色树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<TreeNodeBean> tree(String orgId) {
		return roleService.tree(orgId);
	}

	/**
	 * 角色treegrid
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/treegrid")
	@ResponseBody
	public List<RoleBean> treegrid(String id) {
		
		return roleService.treegrid(id);
	}
	
	
	/**
	 * 查询角色基本信息
	 * @param dg
	 * @return DataGridJson
	 */
	
//	@RequestMapping("/roleDataGrid")
//	public DataGridJson roleDataGrid(DataGridBean dg){
//		
//		
//		
//		
//	}
	
	
	
	
	

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/add")
	@ResponseBody
	public Json add(@RequestBody RoleBean role) {
		Json roleJson = new Json();
		try {
			
			String menuIds="";
			if(null!=role.getMenus() && role.getMenus().size()>0){
				System.out.println(role.getMenus().size());
				for(int i=0;i<role.getMenus().size();i++){
					menuIds+=i==0?"'"+role.getMenus().get(i).getId()+"'":",'"+role.getMenus().get(i).getId()+"'";
				}
			}
			System.out.println(menuIds);
			role = roleService.addRole(role);
			roleJson.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleJson;
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Json del( String[] ids) {
		Json roleJson = new Json();
		roleService.deleteById(ids);
		roleJson.setSuccess(true);
		roleJson.setMsg("删除成功!");
		return roleJson;
	}

	/**
	 * 编辑角色
	 * 
	 * @param role
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Json edit(@RequestBody RoleBean role) {
		Json roleJson = new Json();
		try {
			role = roleService.editRole(role);
			roleJson.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleJson;
	}

}
