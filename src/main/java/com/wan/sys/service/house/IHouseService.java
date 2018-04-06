package com.wan.sys.service.house;

import javax.servlet.http.HttpServletRequest;

import com.wan.sys.pojo.HouseBean;
import com.wan.sys.pojo.ResponseHead;

public interface IHouseService {
	
	
	
	/**
	 * 获取token
	 * @param bean
	 * @return
	 */
	public ResponseHead getUserToken(HouseBean bean);
	
	/**
	 * 设置设备所属项目
	 * @param bean
	 * @return
	 */
	public ResponseHead setProject(HouseBean bean);
	
	/**
	 * 发送人证对成功的用户数据
	 * @param bean
	 * @return
	 */
	public ResponseHead checkUser(HouseBean bean);
	
	/**
	 * 存储对比成功的人证信息
	 * @param bean
	 * @param request
	 * @return
	 */
	public ResponseHead saveUserInfo(HouseBean bean,HttpServletRequest request);
	
	/**
	 * 取号，号码自动增长，并加上日期存入数据库
	 * 存储客户表的排队序号和接房状态
	 * @param bean
	 * @return
	 */
	public ResponseHead handleHouse(HouseBean bean);
	
	/**
	 * 开始接房
	 * 修改用户接房状态
	 * @param bean
	 * @return
	 */
	public ResponseHead takeHouse(HouseBean bean);
	
	/**
	 * 重置排队序列号
	 * @return
	 */
	public ResponseHead resetLineNo(HouseBean bean);
	
	/**
	 * 激活设备
	 * @return
	 */
	public ResponseHead activeDevice(HouseBean bean);
	
	/**
	 * 查询所有项目列表和设备列表，并返回数据
	 * @return
	 */
	public ResponseHead findListData(HouseBean bean);
	
	/**
	 * 绑定设备所属项目
	 * @param bean
	 * @return
	 */
	public ResponseHead bindingDevice(HouseBean bean);
	
	/**
	 * 查询用户接房状态
	 * @param bean
	 * @return
	 */
	public ResponseHead findState(HouseBean bean);
	
	/**
	 * 通过排队序列号查到用户唯一一套住房信息
	 * @param bean
	 * @return
	 */
	public ResponseHead getHouseInfo(HouseBean bean);
	
	/**
	 * app端获取token
	 * @param bean
	 * @return
	 */
	public ResponseHead getTokenAndUser(String openId, String projectId);
	
	
	/**
	 * 查询现在正在办理的排队号
	 * @param bean
	 * @return
	 */
	public ResponseHead findNowNo(HouseBean bean);
	
	/**
	 * 存储当前用户的房屋信息
	 * @param bean
	 * @return
	 */
	public ResponseHead addUserInfo(HouseBean bean);
}
