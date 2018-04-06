package com.wan.sys.service.role;

import java.util.List;

import com.wan.sys.entity.Role;
import com.wan.sys.pojo.RoleBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.common.IBaseService;

/**
 * 角色Service
 * 
 * @author  
 * 
 */
public interface IRoleService extends IBaseService<Role> {

	/**
	 * 
	 * Description: <br>根据id查询出定级菜单或者其子菜单的树
	 * @param id
	 * @return
	 */
	public List<TreeNodeBean> tree(String id);

	public List<RoleBean> treegrid(String id);

	public RoleBean addRole(RoleBean role);

	public RoleBean editRole(RoleBean role);
	public Role findByText(String text);

}
