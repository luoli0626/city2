package com.wan.sys.service.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wan.sys.entity.User;
import com.wan.sys.pojo.BirthdayBean;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.common.IBaseService;

/**
 * 用户Service
 * 
 * @author  
 * 
 */
public interface IUserService extends IBaseService<User> {

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public UserBean reg(UserBean user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户信息
	 * @return User
	 */
	public UserBean login(UserBean user);
	
	public UserBean weiXinlogin(String openid);
	
	public UserBean weiXinUserlogin(String userId);

	public DataGridJson datagrid(DataGridBean dg, UserBean user);

	public List<UserBean> combobox(String q);

	public UserBean add(UserBean user);

	public UserBean edit(UserBean user);

	public UserBean getUserInfo(UserBean user);
	
	/**
	 * 修改用户的基本信息
	 * @param user
	 * @return UserBean
	 */
	public UserBean editUserInfo(UserBean user);
	
	
	public UserBean loginPointV(String userName);
	/**
	 * 校验用户登录帐号是否唯一
	 * @Description 
	 * @param loginAcct
	 * @return boolean
	 */
	public boolean checkLoginAcct(String loginAcct);
	/**
	 * 修改用户密码
	 * @param user
	 * @return UserBean
	 */
	public UserBean updateUserPassword(UserBean user);
	
	/**
	 * 第一次登陆修改密码
	 * @param user
	 * @return UserBean
	 */
	
	public UserBean firstUpPassWord(UserBean user);
	
	
	
	/**
	 * 重置密码
	 * @param ids
	 */
	
	public void resetPass(String [] id);
	
	//机构combobox
	public Object orgList();
	
	/**
	 * 
	 * @return
	 */
	public List<BirthdayBean> getBirthday();

	/**
	 * 待审核用户列表数据
	 * @param dg
	 * @param user
	 * @return
	 */
	public DataGridJson checkUserDatagrid(DataGridBean dg, UserBean user);

	/**
	 * 获取审核状态的下拉列表
	 * @return
	 */
	public List<TreeNodeBean> getCheckStatusTree();

	/**
	 * 手机用户注册通过审核
	 * @param ids
	 */
	public void passCheckById(String[] ids);

	public void updateRecordStatus(String []ids);
}
