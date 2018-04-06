package com.wan.sys.service.role.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wan.sys.entity.User;
import com.wan.sys.pojo.RoleBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.common.impl.BaseServiceImpl;
import com.wan.sys.service.role.IRoleService;
import com.wan.sys.util.wanBeanUtils;

/**
 * 角色Service实现类
 * 
 * @author
 * 
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements
		IRoleService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(RoleServiceImpl.class);

	private IRoleDao roleDao;
	private IMenuDao menuDao;

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IMenuDao getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/**
	 * 
	 * Description: <br>
	 * 根据id查询出定级菜单或者其子菜单的树
	 * 
	 * @param id
	 * @return
	 */
//	@Cacheable(value = "syproRoleCache", key = "'tree'+#id")
//	@Transactional(readOnly = true)
	public List<TreeNodeBean> tree(String id) {
		String hql = "from Role t  order by t.seq";
		List<Role> syroleList = null;
		if (id != null && !id.trim().equals("")) {
			hql = "from Role t  where   t.organization.id=? order by t.seq";
			syroleList = roleDao.find(hql, Long.valueOf(id));
		} else {
			syroleList = roleDao.find(hql);
		}

		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Role syrole : syroleList) {
			tree.add(tree(syrole, false));
		}
		return tree;
	}

	/**
	 * 
	 * Description: <br>
	 * 根据角色和recursive（是否递归查询子节点） 查询出角色或者其子角色的树
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TreeNodeBean tree(Role syrole, boolean recursive) {
		TreeNodeBean node = new TreeNodeBean();
		node.setId(syrole.getId() + "");
		node.setText(syrole.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);

		return node;
	}

	/**
	 * 
	 * Description: <br>
	 * 根据id查询出定级菜单或者其子菜单的树
	 * 
	 * @param id
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	@Cacheable(value = "syproRoleCache", key = "'treegrid'+#id")
//	@Transactional(readOnly = true)
	public List<RoleBean> treegrid(String id) {
		List<RoleBean> treegrid = new ArrayList<RoleBean>();
		// 查询顶级角色
		String hql = "from Role  order by seq";

		List<Role> syroleList = roleDao.find(hql);
		// 循环遍历角色
		for (Role syrole : syroleList) {
			RoleBean roleBean = new RoleBean();
			BeanUtils.copyProperties(syrole, roleBean);
			// 处理角色 避免出现 用户角色 角色用户出现死循环情况
			List<User> users = roleBean.getUsers();
			List<User> temp = new ArrayList<User>();
			if (users != null) {
				for (User u : users) {
				  User user = new User();
				  user.setId(u.getId());
				  user.setNickName(u.getNickName());
				  user.setLoginAcct(u.getLoginAcct());
				  user.setPassword(u.getPassword());
				  temp.add(user);
				}
			}
			roleBean.setUsers(temp);
			treegrid.add(roleBean);
		}
		return treegrid;
	}

	/**
	 * 添加角色
	 * 
	 */
//	@CacheEvict(value = "syproRoleCache", allEntries = true)
	public RoleBean addRole(RoleBean role) {
		Role syrole = new Role();
		BeanUtils.copyProperties(role, syrole);
		// 设置通用信息
		GeneralMethod.setRecordInfo(syrole, true);
		roleDao.save(syrole);
		role.setId(syrole.getId());
		return role;
	}

	/**
	 * 
	 * 编辑角色
	 * 
	 */
//	@CacheEvict(value = "syproRoleCache", allEntries = true)
	public RoleBean editRole(RoleBean role) {
		Role r = roleDao.get(Role.class, role.getId());
		// 设置通用属性
		GeneralMethod.setRecordInfo(r, false);
		wanBeanUtils.copyPropertiesIgnoreNull(role, r);
		r.setMenus(role.getMenus());
		return role;
	}
	
	public Role findByText(String text){
		String hql="from Role r where r.text = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(text);
		return this.roleDao.get(hql, param);
	}

}
