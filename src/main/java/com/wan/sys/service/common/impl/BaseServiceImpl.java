package com.wan.sys.service.common.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wan.sys.common.GeneralMethod;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.service.common.IBaseService;
import com.wan.sys.util.wanBeanUtils;
import com.wan.sys.util.StringUtil;

/**   
 * 文件名称：基础Service实现类 
 * 内容摘要：所有ServiceImpl需要继承此Service来获得默认事务的控制,拥有常用services方法 
 * 创建人： huangfei
 * 创建日期： 2015年4月29日
 * 版本号： v1.0.0
 * 公  司：亚德科技股份有限公司
 * 版权所有： (C)2001-2015     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 **/ 
@Service("baseService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BaseServiceImpl<T> implements IBaseService<T> {
	
	

	
	
	

	@Autowired
	private IBaseDao<T> baseDao;
	
	private Class <T> entityClass;
	
	@SuppressWarnings("unchecked")
	public  BaseServiceImpl(){
	    @SuppressWarnings("rawtypes")
	    Class clazz = getClass();
	    String name = BaseServiceImpl.class.getName();
		if(StringUtil.isNotEquals(clazz.getName(), name)){
			entityClass = (Class <T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}
	
	@Override
	public T getById(Long id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public T save(T obj) {
		GeneralMethod.setRecordInfo(obj, true);
		this.baseDao.save(obj);
		return obj;
	}

	@Override
	public void deleteById(String... ids) {
		if(ids!=null){
			try {
				for (String id : ids) {
					T obj = this.getById(Long.valueOf(id));
					Method setRecordStatusMethod = entityClass.getMethod("setRecordStatus",String.class);
					setRecordStatusMethod.invoke(obj, GlobalConstant.FLAG_N);
					GeneralMethod.setRecordInfo(obj, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	@Override
	public void update(T obj) {
		if(obj !=null){
			try {
				Field idField = entityClass.getSuperclass().getDeclaredField("id");
				idField.setAccessible(true);
				Object id = idField.get(obj);
				T findObj = this.getById((Long)id);
				wanBeanUtils.copyPropertiesIgnoreNull(obj, findObj);
				GeneralMethod.setRecordInfo(obj, false);
			} catch (Exception e){
				e.printStackTrace();
			} 
		}
	}

	@Override
	public String getDTBusinessSeriousNo() {
		String sql="select to_char(sysdate,'yy') || replace(lpad(DT_SEQUENCE.nextval,6,'0'),'','0') from dual";
		List<String> seriousNos=(List<String>)baseDao.findBySql(sql);
		if(seriousNos!=null&&seriousNos.size()==1){
			return seriousNos.get(0);
		}
		return "";
	}
	  public static String listToString(List<String> stringList){
	        if (stringList==null) {
	            return null;
	        }
	        StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        }
	        return result.toString();
	    }

	

}
