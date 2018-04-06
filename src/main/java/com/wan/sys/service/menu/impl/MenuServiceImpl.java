package com.wan.sys.service.menu.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.sys.common.GeneralMethod;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.dao.menu.IMenuDao;
import com.wan.sys.dao.role.IRoleDao;
import com.wan.sys.entity.Menu;
import com.wan.sys.entity.Role;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.MenuBean;
import com.wan.sys.pojo.MenuItem;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.common.impl.BaseServiceImpl;
import com.wan.sys.service.menu.IMenuService;
import com.wan.sys.util.StringUtil;

/**
 * 菜单Service实现类
 * 
 * @author  
 * 
 */
/**
 * 
 * 文件名称: 题目名称
 * 内容摘要: //简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 创 建 人: mominglong
 * 创建日期: 2013-1-30
 * 公    司: 亚德科技股份有限公司
 * 版权所有: 版权所有(C)2001-2004
 * 
 * 修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容
 *   修改日期：
 *   版 本 号：
 *   修 改 人：
 *   修改内容：
 * 修改记录2：…
 * 
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);
	
	private IMenuDao menuDao;
	public IMenuDao getMenuDao() {
		return menuDao;
	}
	
	@Autowired
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	private IRoleDao roleDao;
	@Autowired
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	/**
	 * 根据用户ID获得当前菜单下用户所拥有的菜单树 权限
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
//	@Cacheable(value = "syproMenuCache", key = "'tree'+#id+#userId+#time")
//	@Transactional(readOnly = true)
	public List<TreeNodeBean> tree(String id,Long userId,String time) {
		String hql = "";
		//存放当前用户所拥有的菜单
		String condition=findMenuIdsStrByUserId(userId);
		if(!"".equals(condition)){
			condition = "where t.id in ("+condition+")";
		}else{
			condition=" where t.id=-1";
		}
		//查出当前用户所拥有的菜单
		hql = " from Menu t "+condition+") and function=0  order by t.seq";
		
		List<Menu> menus = menuDao.find(hql);
		Collections.sort(menus,new Comparator<Menu>(){
			@Override
			public int compare(Menu o1, Menu o2) {
				
				if(o1.getSeq()!=null){
					return  o1.getSeq().compareTo(o2.getSeq());
				}
				return 0;
			}
			
		});
		List<TreeNodeBean> tree = menuListToTreeList(menus, false);
		return tree;
	}
	/**
	 * 
	 * @Description 将查询出来的菜单转换为树形结构的数据
	 * @param menus
	 * @param isShowFunction 是否包含功能点
	 * @return List<TreeNodeBean>
	 */
	private List<TreeNodeBean> menuListToTreeList(List<Menu> menus,boolean isShowFunction){
		List<TreeNodeBean> results=new ArrayList<TreeNodeBean>(0);
		if(menus==null){
			return results;
		}
		//获取所有顶级菜单
		List<Menu> parents=new ArrayList<Menu>();
		for (Menu menu : menus) {
			if(menu.getPid()==null&"0".equals(menu.getFunction())){
				parents.add(menu);
			}
		}
		for (Menu p : parents) {
			TreeNodeBean tnb=new TreeNodeBean();
			tnb.setText(p.getText());
			tnb.setId(p.getId()+"");
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("src", p.getSrc());
			attributes.put("check", p.isCheck());
			tnb.setAttributes(attributes);
			if(isMenuHaveChilds(p, menus,isShowFunction)){
				tnb.setChildren(getChildsByParent(p, menus, isShowFunction));
			}
			results.add(tnb);
		}
		return results;
	}
	
	/**
	 * 
	 * @Description 由父级菜单找到所有子菜单
	 * @param parent
	 * @param menus
	 * @param flag 是否包含功能点
	 * @return List<TreeNodeBean>
	 */
	private List<TreeNodeBean> getChildsByParent(Menu parent,List<Menu> menus,boolean isShowFunction){
		List<TreeNodeBean> results=new ArrayList<TreeNodeBean>(0);
		for (Menu menu : menus) {
			if(parent.getId().equals(menu.getPid())){
				TreeNodeBean tnb=new TreeNodeBean();
				if(!isShowFunction){
					if("0".equals(menu.getFunction())){
						tnb.setId(menu.getId()+"");
						tnb.setIconCls(menu.getIconcls());
						tnb.setText(menu.getText());
						Map<String, Object> attributes = new HashMap<String, Object>();
						attributes.put("src", menu.getSrc());
						attributes.put("check", menu.isCheck());
						tnb.setAttributes(attributes);
					}
				}else{
					tnb.setId(menu.getId()+"");
					tnb.setIconCls(menu.getIconcls());
					tnb.setText(menu.getText());
					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("src", menu.getSrc());
					attributes.put("check", menu.isCheck());
					tnb.setAttributes(attributes);
				}
				if(isMenuHaveChilds(menu, menus,isShowFunction)){
					tnb.setChildren(getChildsByParent(menu,menus,isShowFunction));
				}
				results.add(tnb);
			}
		}
		return results;
	}
	/**
	 * 判断菜单是否有子菜单
	 * @Description 
	 * @param menu
	 * @param menus
	 * @param isShowFunction
	 * @return boolean
	 */
	private boolean isMenuHaveChilds(Menu menu,List<Menu> menus,boolean isShowFunction){
		for (Menu m : menus) {
			if(!isShowFunction){
				if(m.getPid()!=null&&menu.getId().equals(m.getPid())&&"0".equals(m.getFunction())){
					return true;
				}
			}else{
				if(m.getPid()!=null&&menu.getId().equals(m.getPid())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @Description 角色赋菜单权限使用的查询（用户给角色赋权只能赋用户能够查看的全部权限）
	 * @param userId 当前登录用户的Id
	 * @param roleID 当前操作的角色Id
	 * @return List<TreeNodeBean>
	 */
//	@Cacheable(value = "syproMenuCache", key = "'treeAll'+#id")
//	@Transactional(readOnly = true)
	public List<TreeNodeBean> treeAll(String userId) {
		String hql1 = "from Menu t order by t.seq";
		List<Menu> menus = menuDao.find(hql1);
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		tree=menuListToTreeList(menus, true);
		return tree;
	}
	/**
	 * 根据角色ID查询角色拥有的菜单并组成树结构: <br>
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
//	@Cacheable(value = "syproMenuCache", key = "'showTree'+#roleId")
//	@Transactional(readOnly = true)
	public List<TreeNodeBean> findTreeByRole(String roleId) {
		List<Menu> permenulist = new ArrayList<Menu>();
		String condition =findMenuIdsStrByRoleId(roleId);
		if(!"".equals(condition)){
			condition=" and id in ("+condition+")";
		}else{
			condition=" and id=-1";
		}
		//根据权限查询菜单
		//String hql = "from Menu t where t.symenu is null order by t.seq ";
		String hql = " from Menu t where t.symenu is null "+condition;
		
		Map<String,Menu> mapMenu = new HashMap<String,Menu>();
		if(null!=permenulist && permenulist.size()>0){
			for(int k=0;k<permenulist.size();k++){
				mapMenu.putAll(this.getParentMenuMap(permenulist.get(k), mapMenu));
				if("0".equals(permenulist.get(k).getFunction())){
					mapMenu.put(permenulist.get(k).getId()+"", permenulist.get(k));
				}
			}
		}
		List<Menu> symenus = menuDao.find(hql);
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Menu symenu : symenus) {
			tree.add(tree(symenu,true,mapMenu));
		}
		return tree;
	}
//	@Override
//	@SuppressWarnings("unchecked")
//	@Cacheable(value = "syproMenuCache", key = "'showTree'+#roleId")
//	@Transactional(readOnly = true)
	public List<TreeNodeBean> buildTreeByRole(String roleId) {
		String condition= findMenuIdsStrByRoleId(roleId);
		if(StringUtil.isBlank(condition)){
			condition="-1";
		}
		//根据权限查询菜单
		String hql2 = "from Menu t where t.id in("+condition+") order by t.seq";
		List<Menu> menus = menuDao.find(hql2);
		List<TreeNodeBean> tree=menuListToTreeList(menus, true);
		return tree;
	}
	/**
	 * 
	 * Description: <br>获取上级菜单集合
	 * @param menu
	 * @param mapMenu
	 * @return
	 */
	private Map<String,Menu> getParentMenuMap(Menu menu,Map<String,Menu> mapMenu){
		return mapMenu;
	}
	/**
	 * 
	 * Description: <br>生成树
	 * @param symenu
	 * @param recursive
	 * @param mapMenu
	 * @return
	 */
	private TreeNodeBean tree(Menu symenu, boolean recursive,Map<String,Menu> mapMenu) {
		TreeNodeBean node = new TreeNodeBean();
		node.setId(symenu.getId()+"");
		node.setText(symenu.getText());
		node.setIconCls(symenu.getIconcls());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", symenu.getSrc());
		node.setAttributes(attributes);
		return node;
	}
	
	/**
	 * 
	 * Description: <br>根据菜单id获取所有的下级菜单
	 * @param id
	 * @return
	 */
//	@Cacheable(value = "syproMenuCache", key = "'treegrid'+#id")
//	@Transactional(readOnly = true)
	public DataGridJson treegrid(Long userId) {
		DataGridJson j = new DataGridJson();
		String condition=findMenuIdsStrByUserId(userId);
		if(!"".equals(condition)){
			condition=" where id in("+condition+") ";
		}else{
			condition=" where id=-1";
		}
		List<MenuBean> treegrid=new ArrayList<MenuBean>();
		String hql="from Menu "+condition+"   order by seq";
		List<Menu> menus = menuDao.find(hql);
		for (Menu menu : menus) {
			MenuBean mb=new MenuBean();
			BeanUtils.copyProperties(menu, mb);
			mb.setFunc(menu.getFunction());
			mb.set_parentId(menu.getPid()==null?"":menu.getPid()+"");
			mb.setId(menu.getId());
			if(isMenuHaveChilds(menu, menus,true)){
				mb.setState("closed");
			}
			mb.setIconCls(menu.getIconcls());
			treegrid.add(mb);
		}
		j.setTotal((long)menus.size());
		j.setRows(treegrid);
		//treegrid=menList2TreeGrid(menus);
		return j;
	}
	
//	@CacheEvict(value = "syproMenuCache", allEntries = true)
	public MenuBean addMenu(MenuBean menu) {
		Menu symenu = new Menu();
		BeanUtils.copyProperties(menu, symenu);
		symenu.setFunction(menu.getFunc());
		//如果menu的父级菜单ID不为空的，为其设置父级菜单
		if (menu.getParentId() != null) {
			Menu parent=menuDao.load(Menu.class, menu.getParentId());
			if(parent!=null){
				symenu.setPid(menu.getParentId());
			}
		}
		try{
			//设置通用信息
			GeneralMethod.setRecordInfo(symenu, true);
			
			menuDao.save(symenu);
			menu.setId(symenu.getId());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return menu;
	}
	/**
	 * 
	 * Description: <br>根据MenuBean找到菜单并且删除当前菜单以及其所有的下级菜单
	 * @param symenu
	 */
//	@CacheEvict(value = "syproMenuCache", allEntries = true)
	public void delMenu(MenuBean menu) {
		Menu symenu = menuDao.get(Menu.class, Long.valueOf(menu.getId()));
		if (symenu != null) {
			recursiveDelete(symenu);
		}
	}

	/**
	 * 
	 * Description: <br>删除当前菜单以及其所有的下级菜单
	 * @param symenu
	 */
	@SuppressWarnings("unchecked")
	private void recursiveDelete(Menu symenu) {
		symenu.setRecordStatus(GlobalConstant.FLAG_N);
		GeneralMethod.setRecordInfo(symenu, false);
		menuDao.update(symenu);
	}
	/**
	 * 
	 * Description: <br>修改菜单
	 * @param menu
	 * @return
	 */
//	@CacheEvict(value = "syproMenuCache", allEntries = true)
	public MenuBean editMenu(MenuBean menu) {
		Menu symenu = menuDao.get(Menu.class,menu.getId());
		if (symenu != null) {
			symenu.setText(menu.getText());
			symenu.setFunction(menu.getFunc());
			if(menu.getParentId()!=null&&!"".equals(menu.getParentId())){
				symenu.setPid(Long.valueOf(menu.getParentId()));
			}
			symenu.setId(menu.getId());
			symenu.setSrc(menu.getSrc());
			symenu.setSeq(menu.getSeq());
			symenu.setIconcls(menu.getIconCls());
		}
		//设置通用信息
		GeneralMethod.setRecordInfo(symenu, false);
		menuDao.save(symenu);
		return menu;
	}
	/**
	 * 根据上级页面的ID查询此页面下的功能点Id
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> findByParentId(String parentId,Long userId) {
		
		//当前用户所拥有的菜单ID
		String condition=findMenuIdsStrByUserId(userId);
		if(!"".equals(condition)){
			condition = " and id in ("+condition+") ";
		}else{
			condition=" and id=-1";
		}
		//查寻出当前用户所拥有的并且上级菜单ID为parentId的值的菜单ID
		String hql = "select id from sys_menu where PID='"+parentId+"' "+condition+" and function = '1' order by seq";
		List<Menu> menuIds =menuDao.findBySql(hql);
		return menuIds;
	}
	/**
	 * 
	 * Description: <br>查出所有的菜单以及功能点，以资源的路径作为每个菜单的KEY，Value为一个菜单实体
	 * @return
	 */
//	@CacheEvict(value = "syproAllMenuCache", allEntries = true)
	@Override
	public Map<String,Menu> treegrid() {
		String hql = "from Menu t  order by t.seq ";
		List<Menu> symenus = menuDao.find(hql);
		Map<String,Menu> map = new HashMap<String,Menu>();
		//将菜单的URL作为KEY，菜单为value存放在symenus集合中
		for (Menu symenu : symenus) {
			if(!StringUtils.isBlank(symenu.getSrc())) {
				map.put(symenu.getSrc(), symenu);
			}
			
		}
		return map;
	}

	@Override
	public List< Menu> findMenuMapByUserId(Long id) {
		String condition = findMenuIdsStrByUserId(id);
		if(StringUtil.isNotBlank(condition)){
			condition=" where id in ("+condition+")";
		}else{
			condition = "where id =-1";
		}
		String hql="from Menu "+condition;
		return  menuDao.find(hql);
	}

	@Override
	public List<TreeNodeBean> findTree4Permissions(Long userId, String roleId) {
		//用户拥有的权限
		String condition =findMenuIdsStrByUserId(userId);
		if(!"".equals(condition)){
			condition=" where id in("+condition+") ";
		}else{
			condition=" where id=-1";
		}
		String hql1="from Menu "+condition+"";
		List<Menu> userMenus=menuDao.find(hql1);
		//角色拥有权限
		condition=findMenuIdsStrByRoleId(roleId);
		if(!"".equals(condition)){
			condition=" where id in("+condition+") ";
		}else{
			condition=" where id=-1";
		}
		String hql2="from Menu "+condition+"";
		List<Menu> roleMenus=menuDao.find(hql2);
		
		for (Menu menu : userMenus) {
			for (Menu m : roleMenus) {
				if(menu.getId().equals(m.getId())){
					menu.setCheck(true);
				}
			}
		}
		return menuListToTreeList(userMenus, true);
	}
	/**
	 * 根据用户id查找用户拥有的所有菜单id
	 * @Description 
	 * @param userId
	 * @return String 结构如 '1','2','3'
	 */
	private String findMenuIdsStrByUserId(Long userId){
		String sql1="select rm.SYMENU_ID from sys_role_to_menu rm where  rm.ROLE_ID in (select ur.SYROLE_ID from sys_user_to_role ur where ur.SYUSER_ID='"+userId+"')";
		Long l=0l;
		if(l.equals(userId)){
			 sql1="select id from sys_menu ";
		}
		
		//用户拥有的权限
		List<Menu> menuIds =  menuDao.findBySql(sql1);
		//组装成（'id1','menuId2'）格式的字符传，用作SQL，Hql中in的参数
		StringBuffer menuIdsql = new StringBuffer();
		if(null!=menuIds & menuIds.size()>0){
			for(int i = 0; i < menuIds.size();i++) {
				if(i != 0) {
					menuIdsql = menuIdsql.append(",");
				}
				menuIdsql = menuIdsql.append("'"+menuIds.get(i)+"'");
			}
		}
		return menuIdsql.toString();
	}
	/**
	 * 根据角色id获取角色拥有的所有菜单
	 * @Description 
	 * @param roleId
	 * @return String 结构如 '1','2','3'
	 */
	private String findMenuIdsStrByRoleId(String roleId){
		String sql = "select rm.SYMENU_ID from sys_role_to_menu rm where rm.ROLE_ID ='"+roleId+"'";
		List<Menu> menuIds =menuDao.findBySql(sql);
		StringBuffer menuIdsql = new StringBuffer();
		
		for(int i = 0; i < menuIds.size();i++) {
			if(i != 0) {
				menuIdsql = menuIdsql.append(",");
			}
			menuIdsql = menuIdsql.append("'"+menuIds.get(i)+"'");
		}
		return menuIdsql.toString();
	}

	@Override
	public List<TreeNodeBean> buildTreeByUserId(Long id) {
		String hql = "";
		//存放当前用户所拥有的菜单
		String condition=findMenuIdsStrByUserId(id);
		if(!"".equals(condition)){
			condition = "where t.id in ("+condition+")";
		}else{
			condition=" where t.id =-1";
		}
		//查出当前用户所拥有的菜单
		hql = " from Menu t "+condition+") and function=0  order by t.seq";
		List<Menu> menus = menuDao.find(hql);
		Collections.sort(menus,new Comparator<Menu>(){
			@Override
			public int compare(Menu o1, Menu o2) {
				
				if(o1.getSeq()!=null){
					return  o1.getSeq().compareTo(o2.getSeq());
				}
				return 0;
			}
			
		});
		List<TreeNodeBean> tree = menuListToTreeList(menus, true);
		return tree;
	}
	@Override
	public List<MenuItem> loadMenus(String roleId, String pid, String url) {
		String hql=" from Menu t  where t.pid is null ";
		if(StringUtils.isNotBlank(roleId)){
			Role role = this.roleDao.get(Role.class,Long.valueOf(roleId));
			String menuIds = "";
			if(null!=role && null!=role.getMenus() && role.getMenus().size()>0){
				for(int i=0;i<role.getMenus().size();i++){
					menuIds+=i==0?"'"+role.getMenus().get(i).getId()+"'":",'"+role.getMenus().get(i).getId()+"'";
				}
			}
			if(StringUtils.isNotBlank(menuIds)){
				hql+=" and t.id in ("+menuIds+")";
			}
		}
		List<Menu> menus = menuDao.find(hql);
		// 创建菜单集合
		List<MenuItem> root = new ArrayList<MenuItem>();
		// 循环添加菜单到菜单集合
		if(null!=menus && menus.size()>0){
			for(int i=0;i<menus.size();i++){
				Menu menu = menus.get(i);
				MenuItem item = new MenuItem(menu.getText(), null);
				item.setId(menu.getId()+"");
				item.setPid(null!=menu.getPid()?menu.getPid()+"":"");
				if (url != null) {
					item.setUrl(url);
				} else {
					item.setUrl(menu.getSrc());
				}
				root.add(item);
			}
		}
		if(null!=root&&root.size()>0){
			// 加载子菜单
			for (int i = 0; i < root.size(); i++) {
				MenuItem ee = root.get(i);
				loadChildrenByPid(ee, url, roleId);
			}
		}
		return root;
	}
	/**
	 * 根据父ID加载子节点
	 * @param item
	 * @param menu
	 * @param url
	 * @param u
	 */
	private void loadChildrenByPid(MenuItem item,String url, String roleId) {
		// 加载菜单节点
		String hql=" from Menu t where 1=1" ;
		String menuIds = "";
		if(StringUtils.isNotBlank(roleId)){
			Role role = this.roleDao.get(Role.class,Long.valueOf(roleId));
			if(null!=role && null!=role.getMenus() && role.getMenus().size()>0){
				for(int i=0;i<role.getMenus().size();i++){
					menuIds+=i==0?"'"+role.getMenus().get(i).getId()+"'":",'"+role.getMenus().get(i).getId()+"'";
				}
			}
		}
		if(StringUtils.isNotBlank(menuIds)){
			hql+=" and t.id in ("+menuIds+")";
		}
		if(StringUtils.isNotBlank(item.getId())){
			hql+=" and t.pid='"+item.getId()+"'";
		}
		
		List<Menu> data = menuDao.find(hql);
		if (data != null && data.size() > 0) {
			if (item.getChildren() == null)
				item.setChildren(new ArrayList<MenuItem>());
			// 创建菜单节点
			for (int i = 0; i < data.size(); i++) {
				Menu entry = data.get(i);
				MenuItem addItem = new MenuItem(entry.getText(), null);
				addItem.setId(entry.getId()+"");
				addItem.setPid(item.getPid());
				if (url != null) {
					addItem.setUrl(url);
				} else {
					addItem.setUrl(entry.getSrc());
				}
				item.getChildren().add(addItem);
			}
			// 根据菜单节点进行递归加载
			if(null!=item.getChildren() && item.getChildren().size()>0){
				for (int i = 0; i < item.getChildren().size(); i++) {
					loadChildrenByPid(item.getChildren().get(i), url, roleId);
				}
			}
		}
		
	}


	
}
