package com.wan.sys.dao.common.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.util.MapKeyComparatorUtil;

/**
 * 
 * 文件名称：基础dao实现类 
 * 内容摘要： 所有dao继承该dao，实现基础功能
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
@Transactional
@Repository("baseDao")
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements IBaseDao<T> {

    private SessionFactory sessionFactory;
	
	boolean flag =false;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	   
	public Session getCurrentSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	public void save(T o) {
		this.getCurrentSession().save(o);
	}

	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	public void refresh(T o) {
		this.getCurrentSession();
		this.getCurrentSession().evict(o);
	}

	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	public void merge(T o) {
		this.getCurrentSession().merge(o);
	}

	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	public List<T> find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		List<T> list = q.list();
		return list;
	}

	public List<T> find(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}

		return q.list();
	}

	public List<T> find(String hql, int page, int rows, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<T> find(String hql, int page, int rows, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<Object[]> findBySql(String sql, int page, int rows,
			Object... param) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@SuppressWarnings("rawtypes")
	public T get(String hql, Object... param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return (T) l.get(0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public T get(String hql, List<Object> param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return (T) l.get(0);
		}
		return null;
	}
	 public List<T> find(String hql, int page, int rows) {
		    Query q = getCurrentSession().createQuery(hql);
		    
		    return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		  }
	public T load(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().load(c, id);
	}

	public Long count(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	public Long countBySql(String sql, Object... param) {
		Long result = new Long(0);
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		List<?> lists = q.list();
		if (null != lists && lists.size() > 0) {
			if(lists.get(0)!=null){//增一个判断 2015-08-21 yy 
				result = Long.valueOf(lists.get(0) + "");
			}
		}
		return result;
	}
	public List<T> findBySql(String sql, List<Object> param) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}

		return q.list();
	}
	public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	public Integer executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	public List<T> findBySql(String sql) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		return query.list();
	}

	public List<T> findBySql(String sql, Class<T> clazz) {
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(
				clazz);
		return query.list();
	}

	@Override
	public void executeSql(String sql) {
		this.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	public List<Map<String, Object>> searchMapBySql(String sql) {
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<?> lists = query.list();
		List<Map<String, Object>> results = null;
		if (null != lists && lists.size() > 0) {
			results = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < lists.size(); i++) {
				Map<String, Object> temp = (Map<String, Object>) lists.get(i);
				// System.out.println("ee:"+temp.get("ID")+"ff:"+temp.get("NAME"));
				results.add(temp);
			}
		}
		return results;
	}

	@Override
	public void removeObjFromSession(Object object) {
		this.getCurrentSession().evict(object);
	}
	
	
	@Override
	public Map<String, String> executeUtil(String code, String codName,
			String tablename) {
		String sql="select "+code+","+codName+" FROM "+tablename +" ";
		List<Object[]> list=this.getCurrentSession().createSQLQuery(sql).list();
		Map<String, String>  map=new HashMap<String, String>();
		if(list!=null && list.size()>0){
			for(Object[] objects:list){
				map.put(objects[0]+"", objects[1]+"");
			}
		}
		return  sortMapByKey(map);
	}
	
	
	/** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public  Map<String, String> sortMapByKey(Map<String, String> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparatorUtil());  
        sortMap.putAll(map);  
        return sortMap;  
    } 

	@Override
	public void saveList(List<T> list) {
		Session session = null;
		if(null != list && list.size()>0){
			try {  
				session = sessionFactory.openSession(); // 获取Session  
				session.beginTransaction(); // 开启事物  
	
				for (int i = 0; i < list.size(); i++) {  
					session.save(list.get(i));   
					// 批插入的对象立即写入数据库并释放内存  
					if (i % 10 == 0) {  
						session.flush();  
						session.clear();  
					}  
				}  
				session.getTransaction().commit(); // 提交事物  
			} catch (Exception e) {  
				e.printStackTrace(); // 打印错误信息  
				session.getTransaction().rollback(); // 出错将回滚事物  
			}
			finally{
				session.close();
			}
		}
		
		
	}

	@Override
	public void updateList(List<T> list) {
		Session session = null;
		if(null != list && list.size()>0){
			try {  
				session = sessionFactory.openSession(); // 获取Session  
				session.beginTransaction(); // 开启事物  
	
				for (int i = 0; i < list.size(); i++) {  
					session.update(list.get(i));   
					// 批插入的对象立即写入数据库并释放内存  
					if (i % 10 == 0) {  
						//session.flush();  
						session.clear();  
					}  
				}  
				session.getTransaction().commit(); // 提交事物  
			} catch (Exception e) {  
				e.printStackTrace(); // 打印错误信息  
				session.getTransaction().rollback(); // 出错将回滚事物  
			}
			finally{
				session.close();
			}
		}
		
	}
	
	@Override
	public String getIncrementIdByType(String type) {
		String result="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sql ="select nvl(max(t.increment_value),0) from sys_increment t where t.increment_type='"+type+"'";
		List<?> lists = this.getCurrentSession().createSQLQuery(sql).list();
		Double d = new Double(0);
		if(null!=lists && lists.size()>0){
			d=Double.valueOf(lists.get(0)+"");
		}
		if(d.longValue()==0l){
			result=sdf.format(new Date())+"00001";
		}else{
			d=d.doubleValue()+1;
			result=d.longValue()+"";
		}
		String saveSql=" insert into SYS_INCREMENT(INCREMENT_ID,INCREMENT_VALUE,INCREMENT_TYPE) values('" +
				result+"','"+result+"','"+type+"')";
		this.getCurrentSession().createSQLQuery(saveSql).executeUpdate();
		return result;
	}
	//执行得到 List
	@Override
	public List<?> findSql(String sql) {
		// TODO Auto-generated method stub
		return this.getCurrentSession().createSQLQuery(sql).list();
	}

	@Override
	public List<T> findBySqlThred(String sql) {
		Session session = null;
		List<T> list = null;
			try {  
				session = sessionFactory.openSession(); // 获取Session  
				session.beginTransaction(); // 开启事物  
				Query query =session.createSQLQuery(sql);
				list = query.list();
				session.getTransaction().commit(); // 提交事物  
			} catch (Exception e) {  
				e.printStackTrace(); // 打印错误信息  
				session.getTransaction().rollback(); // 出错将回滚事物  
			}
			finally{
				session.close();
			}
		
		
			return list;	
	}

	@Override
	public List<T> findThred(String hql, Object... param) {
		Session session = null;
		List<T> list = null;
		try {  
			session = sessionFactory.openSession(); // 获取Session  
			session.beginTransaction(); // 开启事物  
			Query query =session.createQuery(hql);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					query.setParameter(i, param[i]);
				}
			}
			list = query.list();
			session.getTransaction().commit(); // 提交事物  
		} catch (Exception e) {  
			e.printStackTrace(); // 打印错误信息  
			session.getTransaction().rollback(); // 出错将回滚事物  
		}
		finally{
			session.close();
		}
		
		return list;
	}

	@Override
	public void executeThreadSql(String sql) {
		Session session = null;
		try {  
			session = sessionFactory.openSession(); // 获取Session  
			session.beginTransaction(); // 开启事物  
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit(); // 提交事物  
		} catch (Exception e) {  
			e.printStackTrace(); // 打印错误信息  
			session.getTransaction().rollback(); // 出错将回滚事物  
		}
		finally{
			session.close();
		}
		
	}

	
	
	
}
