package com.wan.sys.service.menu;

import java.util.List;
import java.util.Map;

import com.wan.sys.entity.Menu;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.MenuBean;
import com.wan.sys.pojo.MenuItem;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.common.IBaseService;


/**
 * 菜单Service
 * 
 * @author  
 * 
 */
public interface IMenuService extends IBaseService<Menu> {

	/**
	 * 根据用户ID获得当前菜单下用户所拥有的菜单树权限
	 * 
	 * @param id
	 * @return
	 */
	public List<TreeNodeBean> tree(String id,Long userId,String time);

	/**
	 * 
	 * Description: <br>根据菜单id获取所有的下级菜单
	 * @param id
	 * @return
	 */
	public DataGridJson treegrid(Long id);

	public MenuBean addMenu(MenuBean menu);

	public void delMenu(MenuBean menu);

	/**
	 * 
	 * Description: <br>修改菜单
	 * @param menu
	 * @return
	 */
	public MenuBean editMenu(MenuBean menu);
	/**
	 * 根据上级页面的ID查询此页面下的功能点Id
	 * @param parentId
	 * @return
	 */
	public List<Menu> findByParentId(String parentId,Long userId);
	
	/**
	 * 
	 * Description: <br>根据ID获取前台展示树的结构
	 * @param id
	 * @return
	 */
	public List<TreeNodeBean> treeAll(String id) ;
	/**
	 * 
	 * Description: <br>查出所有的菜单以及功能点，以资源的路径作为每个菜单的KEY，Value为一个菜单实体
	 * @return
	 */
	public Map<String,Menu> treegrid();
	/**
	 * 根据角色ID查询角色拥有的菜单并组成树结构: <br>
	 * @param roleId
	 * @return
	 */
	public List<TreeNodeBean> findTreeByRole(String roleId) ;
	/**
	 * Description: 根据角色ID查询角色拥有的菜单并组成树结构<br>
	 * @param roleId
	 * @return
	 */
	public List<TreeNodeBean> buildTreeByRole(String roleId) ;
	/**
	 * 
	 * @Description 根据用户id查找拥有权限的所有菜单
	 * @param id
	 * @return Map<String,Menu>
	 */
	public List<Menu> findMenuMapByUserId(Long id);
	/**
	 * 
	 * @Description 获取角色权限树(用户只能将自己的权限赋权给管理的角色)
	 * @param userId 用户Id
	 * @param roleId 操作角色Id
	 * @return List<TreeNodeBean>
	 */
	public List<TreeNodeBean> findTree4Permissions(Long userId,String roleId);
	/**
	 * 根据用户id查找用户拥有的资源树
	 * @param id
	 * @return List<TreeNodeBean>
	 */
	public List<TreeNodeBean> buildTreeByUserId(Long id);
	/**
	 * 加载页面菜单
	 * @param roleId
	 * @param pid
	 * @param url
	 * @return
	 */
	public List<MenuItem>  loadMenus(String roleId, String pid, String url);
	

}
