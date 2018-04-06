package com.wan.sys.common;

import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wan.sys.pojo.UserBean;


 
/**
 * 
 * 文件名称： 通用方法
 * 内容摘要： 
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
public class GeneralMethod {
	private static Logger logger = LoggerFactory.getLogger(GeneralMethod.class);
	
	/**
	 * @Description 设置记录通用属性
	 * @param obj 当前记录对象
	 * @param isAdd 是否是新增记录
	 */
	public static void setRecordInfo(Object obj,boolean isAdd) {
		UserBean currUser = GlobalContext.getCurrentUser();
		Class<?> clazz = obj.getClass();
		try {
			if(isAdd){
				Method setRecordStatusMethod = clazz.getMethod("setRecordStatus",String.class);
				setRecordStatusMethod.invoke(obj,GlobalConstant.FLAG_Y);
				Method setCreateTime = clazz.getMethod("setCreateTime",Date.class);
				setCreateTime.invoke(obj,new Date());
				Method setCreateUserId = clazz.getMethod("setCreateUserId",Long.class);
				if(currUser!=null){
					setCreateUserId.invoke(obj,currUser.getId());					
				}
			}
			Method setModifyTime = clazz.getMethod("setModifyTime",Date.class);
			setModifyTime.invoke(obj,new Date());
			Method setModifyUserId = clazz.getMethod("setModifyUserId",Long.class);
			if(currUser!=null){
				setModifyUserId.invoke(obj,currUser.getId());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
