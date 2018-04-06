package com.wan.sys.service.cityManager;

import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.UserBean;

public interface IcityManagerService {
	
	/**
	 * 登陆验证，并获取token
	 * @param bean
	 * @return
	 */
	public ResponseHead toLogin(UserBean bean);
	
	/**
	 * 获取短信验证码
	 * @param bean
	 * @return
	 */
	public ResponseHead getCode(UserBean bean);
	
	/**
	 * 注册接口
	 * @param bean
	 * @return
	 */
	public ResponseHead toRegister(UserBean bean);
	
	/**
	 * 展示我的资料
	 * @param bean
	 * @return
	 */
	public ResponseHead myDetails(UserBean bean);
	
	/**
	 * 编辑个人资料
	 * @param bean
	 * @return
	 */
	public ResponseHead editInfo(UserBean bean);
	
	/**
	 * 重置密码
	 * @param bean
	 * @return
	 */
	public ResponseHead resetPwd(UserBean bean);
}
