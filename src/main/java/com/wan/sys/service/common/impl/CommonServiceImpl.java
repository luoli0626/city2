package com.wan.sys.service.common.impl;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.entity.User;
import com.wan.sys.pojo.PageVo;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.common.CommonServiceI;


@Service(value="commonService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CommonServiceImpl implements CommonServiceI {

	@Autowired
	IBaseDao baseDao;
	
	
	public PageVo pageVo;
	public String  userId;
	
	public PageVo getPageVo() {
		return pageVo;
	}

	public void setPageVo(PageVo pageVo) {
		this.pageVo = pageVo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public User tokenIsExpire(String token) {
		User staff=null;
		try {
			 staff = (User) baseDao.get("from User t where t.token='"+token+"' and t.recordStatus='Y'");
			if(staff==null){
				return null;
			}
			Long expireTime = staff.getExpireTime();
			// 获取当前时间的毫秒数
			Long currentTime = System.currentTimeMillis();
			// 比较当前时间和过期时间，判断是否过期
			if(currentTime>expireTime){
				return null;
			}
			
		} catch (Exception e) {
			return null;
		}
		// 使用token查询员工信息和过期时间
		
		return staff;
	}
	
	
	/**
	 * 组装返回消息
	 * @param result
	 * @param resultMessage
	 * @param listObj
	 * @param total
	 * @param sessionId
	 * @return
	 */
	public ResponseHead response(String result,String resultMessage,Object listObj,int total ,String sessionId){
		ResponseHead head =  new ResponseHead();
		if(null==result){
			head.setErrcode("-99");
		}else{
			head.setErrcode(result);
		}
		if(null==resultMessage){
			head.setErrmsg("");
		}else{
			head.setErrmsg(resultMessage);
		}
			
		if(null==listObj){
			head.setData(new JSONArray());
		}else{
			head.setData(listObj);
		}
		
		if(null!=pageVo){
			head.setPageInfo(JSON.toJSONString(pageVo));
		}else{
			head.setPageInfo(JSON.toJSONString(new PageVo()));
		}

		return head;
		
	}
	

}
