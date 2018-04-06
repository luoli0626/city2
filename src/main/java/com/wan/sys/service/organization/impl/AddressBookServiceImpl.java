package com.wan.sys.service.organization.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wan.sys.common.GeneralMethod;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.dao.organization.IOrganizationDao;
import com.wan.sys.dao.user.IUserDao;
import com.wan.sys.entity.User;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.DeptBean;
import com.wan.sys.pojo.DeptMentBean;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.OrganBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.common.impl.BaseServiceImpl;
import com.wan.sys.service.organization.IAddressBookService;
import com.wan.sys.util.wanBeanUtils;
import com.wan.sys.util.Uuid;

@Service("addressBookService")
public class AddressBookServiceImpl extends BaseServiceImpl<Organization> implements IAddressBookService {
	@Autowired
	private IOrganizationDao organDao;
	@Autowired
	IBaseDao<Object[]> baseDao;
	@Autowired
	IUserDao userDao;
	
	@Override
	public List<TreeNodeBean> tree(UserBean user) {
		List<Organization> orgList=user.getOrganization();
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Organization organ:orgList)
		{     
			TreeNodeBean node = this.buildTree3(organ,true);
			if(node!= null)
			{
				tree.add(node);
			}
		}
		return tree;
	}
	private TreeNodeBean buildTree3(Organization organ,boolean recursive){
		TreeNodeBean node = new TreeNodeBean();
		Map<String,Object> attributes=new HashMap<String, Object>();
   	 	node.setId(organ.getId()+"");  
   	    attributes.put("text", organ.getOrgName());
   	 	node.setText(organ.getOrgName()+"("+getUserNum(organ.getId()+"")+"人)");
   	    node.setAttributes(attributes);
   	 	String hql=" from Organization t where t.parentOrgan.id=?  ORDER BY convert_gbk(t.orgName)";
   	 	List<Organization> childList = this.organDao.find(hql,organ.getId());
   	 	if(childList!=null&&childList.size()>0){
   		// node.setState("closed");//关闭节点 	
   		 if(recursive){	
   			List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();   			 
   			for (Organization org : childList){
   				TreeNodeBean t = buildTree3(org,true);
   				if(t!=null){
   					children.add(t);
   				}
			}
   			 node.setState("close"); 
   			 node.setChildren(children);
   		 }   		 
   	 }else{
//   		String hql1=" from DeptMent t where t.parentId=?  ORDER BY t.sort";
//   		List<DeptMent> childList1 = this.deptDao.find(hql1,organ.getId());
//   		if(childList1!=null&&childList1.size()>0){
//
//   	   		// node.setState("closed");//关闭节点 	
//   	   		 if(recursive){	
//   	   			List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();   			 
//   	   			for (DeptMent dept : childList1){
//   	   			Organization org=new Organization();
//   	   			org.setId(dept.getId());
//   	   		    org.setOrgName(dept.getDeptName());
//   	   		    org.setStatus("1");
//   	   				TreeNodeBean t = buildTree3(org,true);
//   	   				if(t!=null){
//   	   					children.add(t);
//   	   				}
//   				}
//   	   			 node.setState("close"); 
//   	   			 node.setChildren(children);
//   	   		 }   
//   		}
   	 }   	 
   		 return node;
    }
	private String getUserNum(String orgId){
		return baseDao.countBySql("select count(t.id) from sys_user2 t LEFT JOIN sys_user_project tup on t.ID=tup.USER_ID where tup.PROJECT_ID='"+orgId+"'")+"";
	}
	@Override
	public DataGridJson datagrid(DataGridBean dg,DeptBean bean) {
		DataGridJson j = new DataGridJson();
		String hql = "";
		if(null!=bean.getOrgId1()&&!"".equals(bean.getOrgId1())){
		hql+= " from User t where  t in(select elements(r.users) from Organization r where r.id= '"+bean.getOrgId1()+"' ) ";
			 if(null!=bean.getNickName()&&!"".equals(bean.getNickName())){
				 hql+=" and t.nickName like  '%"+bean.getNickName().trim()+"%'";
	    }
		List<Object> values = new ArrayList<Object>();
		String totalHql = " select count(*) " + hql;
		j.setTotal(userDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<User> syusers = userDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页

		List<UserBean> users = new ArrayList<UserBean>();
		if (syusers != null && syusers.size() > 0) {// 转换模型
			for (User syuser : syusers) {
				UserBean u = new UserBean();
				wanBeanUtils.copyPropertiesIgnoreNull(syuser, u);
				if(null!=syuser.getOrganization()&&syuser.getOrganization().size()>0){
					String sql="select t.user_id,t.is_administrator from sys_user_project t where t.USER_ID="+syuser.getId()+" and t.PROJECT_ID='"+bean.getOrgId1()+"'";
					List<Object[]> listarr=baseDao.findBySql(sql);
					if(null!=listarr&&listarr.size()>0){
						if(null!=listarr.get(0)[1]&&!"".equals(listarr.get(0)[1])&&"1".equals(listarr.get(0)[1])){
						   u.setIsAdmin("是");
						}else{
						   u.setIsAdmin("否");
						}
					}
				}
				users.add(u);
			}
		}
		
		j.setRows(users);// 设置返回的行
		return j;
		}
		return j;
	}

	@Override
	public List<OrganBean> findDept(String deptId) {
		List<OrganBean>  litDept=new ArrayList<OrganBean>();
		List<Organization> list=organDao.find(" from Organization t where t.parentOrgan.id='"+deptId+"' order by t.sort asc");
		if(null!=list&&list.size()>0){
			for(Organization dept:list){
				OrganBean bean=new OrganBean();
				BeanUtils.copyProperties(dept, bean);
				litDept.add(bean);
			}
		}
		return litDept;
	}
	
	@Override
	public Json addDeptUser(String userId, String deptId,String isAdministrator) {
		Json json=new Json();
	    if(null!=userId&&!"".equals(userId)){
	    	String[] userIds=userId.split(",");
	    	for(String id:userIds){
	    		User user=userDao.get(User.class, Long.parseLong(id));
	    		Organization deptMent=organDao.get(Organization.class, deptId);
	    		if(null!=user&&!"".equals(user)&&null!=deptMent){
	    			List<Object[]> list=baseDao.findBySql("select * from sys_user_project t where t.USER_ID="+id+" and t.PROJECT_ID='"+deptId+"'");
	    			if(null==list||"".equals(list)||list.size()<=0){
	    			   baseDao.executeSql("insert into sys_user_project(USER_ID,PROJECT_ID,IS_ADMINISTRATOR) values("+id+",'"+deptId+"','"+isAdministrator+"')");
	    			}
	    			
	    			
	    		}
	    		json.setSuccess(true);
    			json.setMsg("成功");
	    	}
	    }
	    return json;
	}
	@Override
	public DataGridJson datagrid1(DataGridBean dg,DeptBean bean) {
		DataGridJson j = new DataGridJson();
		String hql = "";
		UserBean user=GlobalContext.getCurrentUser();
		String orgId="";
		List<Organization> listOrg=user.getOrganization();
		if(null!=listOrg&&listOrg.size()>0){
			for(Organization org:listOrg){
				List<Object[]> listObj=baseDao.findBySql("select id,org_name  FROM sys_organization WHERE FIND_IN_SET(id, getChildList('"+org.getId()+"'))");
				if(null!=listObj&&listObj.size()>0){
					for(Object[] dept:listObj){
						orgId+=",'" + dept[0]+"'";
					}
				}
			}
			orgId=orgId.replaceFirst(",", "");
		}
		hql+= " from User t where  t in(select elements(r.users) from Organization r where r.id in ("+orgId+") ) ";
		 if(null!=bean.getNickName()&&!"".equals(bean.getNickName())){
			 hql+=" and t.nickName like  '%"+bean.getNickName().trim()+"%'";
		 }
		List<Object> values = new ArrayList<Object>();
		String totalHql = " select count(*) " + hql;
		j.setTotal(userDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<User> syusers = userDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页

		List<UserBean> users = new ArrayList<UserBean>();
		if (syusers != null && syusers.size() > 0) {// 转换模型
			for (User syuser : syusers) {
				UserBean u = new UserBean();
				wanBeanUtils.copyPropertiesIgnoreNull(syuser, u);
				users.add(u);
			}
		}
		j.setRows(users);// 设置返回的行
		return j;
	}
	@Override
	public Json delDeptUser(String userId,String deptId) {

		Json json=new Json();
	    if(null!=userId&&!"".equals(userId)){
	    	String[] userIds=userId.split(",");
	    	for(String id:userIds){
	    		User user=userDao.get(User.class, Long.parseLong(id));
	    		if(null!=user&&!"".equals(user)){
	    			 baseDao.executeSql("delete from sys_user_project  where USER_ID="+id+" and PROJECT_ID='"+deptId+"'");
	    			
	    		}
	    	}
	    	json.setSuccess(true);
			json.setMsg("成功");
			return json;
	    }
		return null;
	
	}
	@Override
	public Json findDeptMent(String deptId) {
		Json json=new Json();
		DeptMentBean bean=new DeptMentBean();
		
		if(null!=deptId&&!"".equals(deptId)){
		   Organization deptMent=organDao.get(Organization.class, deptId);
		   if(null!=deptMent&&!"".equals(deptMent)){
			   json.setSuccess(true);
			   json.setObj(deptMent);
		   }else{
			   json.setSuccess(false);
			   json.setMsg("请联系管理员");
		   }
		}
		return json;
	}
	
	@Override
	public Json editDeptUser(String userId, String deptId,String type) {

		Json json=new Json();
	    if(null!=userId&&!"".equals(userId)){
	    	String[] userIds=userId.split(",");
	    	for(String id:userIds){
	    		User user=userDao.get(User.class, Long.parseLong(id));
	    		if(null!=user&&!"".equals(user)){
	    			 baseDao.executeSql("update  sys_user_project set is_administrator='"+type+"'  where USER_ID="+id+" and PROJECT_ID='"+deptId+"'");
	    		}
	    	}
	    	json.setSuccess(true);
			json.setMsg("成功");
			return json;
	    }
		return null;
	
	}
	
	
}
