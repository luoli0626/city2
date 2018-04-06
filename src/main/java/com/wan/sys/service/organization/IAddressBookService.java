package com.wan.sys.service.organization;

import java.util.List;

import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.DeptBean;
import com.wan.sys.pojo.DeptMentBean;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.OrganBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;

/**
 * 通讯录实现层接口
 * 文件名称： 
 * 内容摘要： 
 * 创建人： 唐君左
 * 创建日期： 2017-5-3
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
public interface IAddressBookService {
	/**
	 * 获取当前人相关部门列表
	 * @param user
	 * @return List<TreeNodeBean>
	 */
	public List<TreeNodeBean> tree(UserBean user);
	
	/**
	 * 通讯录用户列表
	 * @param dg
	 * @param orgId
	 * @return DataGridJson
	 */
	public DataGridJson datagrid(DataGridBean dg,DeptBean bean);
    
	
	/**
	 * 查询子部门
	 * @param deptId
	 * @return List<DeptMentBean>
	 */
	public List<OrganBean> findDept(String deptId);
	
	/**
	 * 添加部门与用户的关联
	 * @param userId
	 * @param deptId
	 * @return Json
	 */
	public Json addDeptUser(String userId,String deptId,String isAdministrator);
	
	/**
	 * 查找项目上的人员
	 * @param dg
	 * @param bean
	 * @return DataGridJson
	 */
	public DataGridJson datagrid1(DataGridBean dg,DeptBean bean);
	
	/**
	 * 删除部门与用户的关联
	 * @param userId
	 * @param deptId
	 * @return Json
	 */
	public Json delDeptUser(String userId,String deptId);
	/**
	 * 设置管理员
	 * @param userId
	 * @param deptId
	 * @return Json
	 */
	public Json editDeptUser(String userId,String deptId,String type);
	
	/**
	 * 查询部门信息
	 * @param deptId
	 * @return Json
	 */
	public Json findDeptMent(String deptId);
	
}
