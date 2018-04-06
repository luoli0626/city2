package com.wan.sys.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.sys.dao.user.ILogDao;
import com.wan.sys.entity.Log;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.service.common.impl.BaseServiceImpl;
import com.wan.sys.service.user.ILogService;



/**
 * 用户Service
 * 
 * @author  
 * 
 */
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements ILogService {

	private ILogDao logDao;

	
	public ILogDao getLogDao() {
		return logDao;
	}
	@Autowired
	public void setLogDao(ILogDao logDao) {
		this.logDao = logDao;
	}

	/**
	 * 
	 * 保存日志信息
	 * 
	 * 
	 */
	@Override
	public boolean saveLog(Log log) {
		boolean flag = true;
		try {
			logDao.save(log);
		} catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * 删除日志信息
	 * 
	 */
	@Override
	public boolean delLog(String logIds) {
		boolean flag = true;
		String[] logIdA = logIds.split(",");
		try {
			StringBuffer delSql = new StringBuffer();
			if(logIdA.length > 0) {
				delSql.append("delete from sys_log where id in (");
				for(int i = 0; i < logIdA.length; i++) {
					if(i != 0) {
						delSql.append(",");
					}
					delSql.append("'"+logIdA[i]+"'");
				}
				delSql.append(")");
			}
			logDao.executeSql(delSql.toString());
		} catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 
	 */
	@Transactional(readOnly = true)
	public DataGridJson datagrid(DataGridBean dg, Log log) {
		DataGridJson j = new DataGridJson();
		String hql = " from Log t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		if (log != null) {// 添加查询条件
			if (!StringUtils.isBlank(log.getNote())) {
				hql += " and t.note like '%" + log.getNote() + "%' ";
			}
			if (!StringUtils.isBlank(log.getUserName())) {
				hql += " and t.userName like '%" + log.getUserName() + "%' ";
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(logDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}else{
			hql += " order by useTime desc " ;
		}
		List<Log> logs = logDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		j.setRows(logs);// 设置返回的行
		return j;
	}
	


}
