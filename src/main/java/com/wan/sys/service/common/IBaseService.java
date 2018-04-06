package com.wan.sys.service.common;





/**
 * 
 * 文件名称： 基础Service类 
 * 内容摘要： 
 * 创建人： 唐君左
 * 创建日期： 2017-2-21
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
public interface IBaseService<T> {
	
	/**根据主键查找实体 
	 * @param id
	 * @return T
	 */
	public T getById(Long id);
	/**
	 * 保存实体 
	 * @param obj void
	 */
	public T save(T obj);
	/**
	 * 根据主键删除实体 
	 * @param ids
	 * @return boolean
	 */
	public void deleteById(String... ids);
	/** 
	 * 更新实体
	 * @param obj void
	 */
	public void update(T obj);
	/**
	 * 获取一个电梯业务流水号
	 * @return String
	 */
	public String getDTBusinessSeriousNo();
}
