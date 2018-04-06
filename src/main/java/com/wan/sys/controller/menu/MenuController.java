package com.wan.sys.controller.menu;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.sys.common.BaseController;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.entity.Menu;
import com.wan.sys.entity.Role;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.MenuBean;
import com.wan.sys.pojo.MenuItem;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.menu.IMenuService;
import com.wan.sys.service.role.IRoleService;

/**
 * 
 * 文件名称: 菜单控制器
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
@RequestMapping("/menu")
public class MenuController extends BaseController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MenuController.class);

	private IMenuService menuService;

	public IMenuService getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
    private IRoleService roleService;
    @Autowired
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/menuList")
	public String menu() {
		return "sys/menu/menu";
	}

	/**
	 * 获取菜单树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<TreeNodeBean> tree(String id, HttpSession session) {
		List<TreeNodeBean> list = menuService.tree(id,
				GlobalContext.getCurrentUser().getId(),GlobalConstant.TIMESTAPE);
		//Map map = new HashMap();
		// map.put("menus", list);
		return list;
	}
	@RequestMapping(value = "/treeAll")
	@ResponseBody
	public List<TreeNodeBean> treeAll(String id, HttpSession session) {
		List<TreeNodeBean> list = menuService.treeAll(id);
		return list;
	}
	@RequestMapping(value = "/showTree4check/{roleId}")
	@ResponseBody
	public List<TreeNodeBean> showTree4check(@PathVariable String roleId,HttpSession session){
		List<TreeNodeBean> list = menuService.findTree4Permissions(GlobalContext.getCurrentUser().getId(), roleId);
		return list;
	}
	@RequestMapping(value = "/showTree/{roleId}")
	@ResponseBody
	public List<TreeNodeBean> showTreek(@PathVariable String roleId,HttpSession session){
		List<TreeNodeBean> list = menuService.buildTreeByRole(roleId);
		return list;
	}
	/**
	 * 获取菜单treegrid
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/treegrid")
	@ResponseBody
	public DataGridJson treegrid(HttpSession session) {
		DataGridJson dj=menuService.treegrid(
				GlobalContext.getCurrentUser().getId());;
		return dj;
	}

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/add")
	@ResponseBody
	public Json add(MenuBean menu) {
		Json menuJson = new Json();
		MenuBean m = menuService.addMenu(menu);
		menuJson.setSuccess(true);
		//通过Json的msg字段传递插入后的菜单id
		menuJson.setMsg(m.getId()+"");
		return menuJson;
	}

	/**
	 * 删除菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Json del(MenuBean menu) {
		Json menuJson = new Json();
		menuService.delMenu(menu);
		menuJson.setSuccess(true);
		menuJson.setMsg("删除成功!");
		return menuJson;
	}

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Json edit(@RequestBody MenuBean menu) {
		Json menuJson = new Json();
		MenuBean m = menuService.editMenu(menu);
		menuJson.setSuccess(true);
		menuJson.setMsg("编辑成功!");
		return menuJson;
	}

	
	/**
	 *获取功能点
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/findFunction")
	@ResponseBody
	public Json findFunction(String urlId, HttpSession session) {
		Json j = new Json();
		List<Menu> menuIdList = menuService.findByParentId(urlId, GlobalContext.getCurrentUser().getId());
		j.setObj(menuIdList);
		return j;
	}
	/**
	 * 跳转到系统探针页面
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/toSystemInfo")
	public String toSystemInfo() {
		return "admin/tz/jspinfo";
	}
	/**
	 * 跳转到系统探针页面
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/toTz")
	public String toTz() {
		return "admin/tz/tz";
	}
	/**
	 * 从PID=0开始加载菜单资源
	 * 获取指定节点的全部子菜单（包括当前菜单节点）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMenusByPid")
	@ResponseBody
	public void getMenusByPid(HttpServletRequest request,MenuBean mBean,String roleId,HttpServletResponse response)  {
		String pid = mBean.getParentId()+"";
		if(pid==null || pid.trim().equals("")){
			pid = "0";
		}
		List<MenuItem> menus = menuService.loadMenus("", pid, "");
		// 加载全部的菜单
		if(StringUtils.isNotBlank(roleId)){
			// 加载指定角色的权限
			Role role = this.roleService.getById(Long.valueOf(roleId));
			List<Menu> lists =role.getMenus();
			
			// 拿角色拥有的菜单和全部的菜单做比对，进行勾选
			if(null!=lists && lists.size()>0){
				
				for (int i = 0; i < lists.size(); i++) {
					Menu p = lists.get(i);
//					System.out.println(p.getPid());
					eeee(p, menus);
				}
			}
		}
		writeMenus(menus,response);
	}
	/**
	 * 角色权限和资源菜单进行对比，使checkbox选中
	 * @param p
	 * @param menus
	 */
	private void eeee(Menu p,List<MenuItem> menus){
		for (int j = 0; j < menus.size(); j++) {
			MenuItem menu = menus.get(j);
				if (menu.getId().equals(p.getId()+"")) {
					menu.setChecked(true);
					return;
				}else{
					if(menu.getChildren()!=null && menu.getChildren().size()>0){
						eeee(p, menu.getChildren());
					}
				}
		}
	}
	//输出菜单到页面
	private String writeMenus(List<MenuItem> root,HttpServletResponse response) {
		List<MenuItem> root2 = new ArrayList<MenuItem>();
		for(int i =0;i<root.size();i++){
			MenuItem mi=root.get(i);
			if(mi.getUrl()!=null && !"".equals(mi.getUrl())){
				 mi.setUrl(mi.getUrl());
			}
			 root2.add(mi); 
		}
		JSONArray json = JSONArray.fromObject(root2);
//		System.out.println(json.toString());
		String jsonStr = json.toString();
		try {
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
}
