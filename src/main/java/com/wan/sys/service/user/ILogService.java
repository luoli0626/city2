package com.wan.sys.service.user;


import com.wan.sys.entity.Log;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.service.common.IBaseService;

/**
 * 日志Service
 * 
 * @author  
 * 
 */
public interface ILogService extends IBaseService<Log> {

	public boolean saveLog(Log log);
	
	public boolean delLog(String logIds);
	/**
	 * 
	 * Description: <br>数据列表
	 * @param dg
	 * @param log
	 * @return
	 */
	public DataGridJson datagrid(DataGridBean dg, Log log);

}
