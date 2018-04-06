package com.wan.sys.service.organization.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import net.sf.json.util.JSONUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wan.sys.common.GeneralMethod;
import com.wan.sys.dao.organization.IOrganizationDao;
import com.wan.sys.dao.role.IRoleDao;
import com.wan.sys.entity.Role;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.OrganBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.common.impl.BaseServiceImpl;
import com.wan.sys.service.organization.IOrganizationService;
import com.wan.sys.util.wanBeanUtils;
import com.wan.sys.util.ConfigUtil;
import com.wan.sys.util.HttpClientUtil;
import com.wan.sys.util.IdGenerator;
import com.wan.sys.util.Uuid;

/**
 * 
 * 文件名称: 机构管理的service实现类
 * 内容摘要: 机构管理的service实现类
 * 创 建 人:zhph
 * 创建日期: Dec 6, 2013
 * 公    司: 亚德科技股份有限公司
 * 版权所有: 版权所有(C)2001-2004
 * 
 * 修改记录1: 
 *   修改日期：
 *   版 本 号：
 *   修 改 人：
 *   修改内容：
 * 修改记录2：…
 * 
 */
@Service("organService")
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements
		IOrganizationService {
	private IRoleDao roleDao;
	@Autowired
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	private IOrganizationDao organDao;
	@Autowired
	public void setOrganDao(IOrganizationDao organDao) {
		this.organDao = organDao;
	}
	
	@Override
	public OrganBean add(OrganBean organBean) {
		Organization o=new Organization();
		String id=Uuid.getUUID();
		BeanUtils.copyProperties(organBean, o);
		Organization p=organDao.get(Organization.class, organBean.getParentId());
		o.setParentOrgan(p);
		o.setSort(IdGenerator.getLongValue());
		o.setSortNumber(p.getSortNumber()+1);
		o.setId(id);
		o.setOrgCode(id);
		//设置通用信息
		//GeneralMethod.setRecordInfo(o, true);
		organDao.save(o);
		organBean.setId(o.getId());
		return organBean;
	}

	@Override
	public String edit(OrganBean organBean) {
		Organization organ = this.organDao.get(Organization.class, organBean.getId());
		if(null!=organ){
			Organization org= this.findByCode(organBean.getOrgCode());
			if(null!=org && !org.getId().equals(organ.getId())){
				return "1";
			}else{
				try {
					organ.setOrgName(organBean.getOrgName());
					organ.setOrgDesc(organBean.getOrgDesc());
					organ.setIsLeaf("0");
					organ.setNodeLevel(1l);
					Organization temp = new Organization();
					temp.setId(organBean.getParentId());
					organ.setParentOrgan(temp);
					this.organDao.update(organ);
					return "4";
				} catch (Exception e) {
					return "5";
				}
			}
		}else{
			return "6";
		}
	}

	@Override
	public List<TreeNodeBean> tree(OrganBean organBean) {
		String hql = "from Organization d where d.parentOrgan.id=0 ";
		List<Organization> organList = this.organDao.find(hql);
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Organization organ:organList)
		{     
			TreeNodeBean node = this.buildTree(organ,true);
			if(node!= null)
			{
				tree.add(node);
			}
		}
		return tree;
	}
	@Override
	public boolean del(String[] ids) {
		boolean flag=true;
		for(String id:ids){
			Organization organ = this.organDao.get(Organization.class, id);
			if(null != organ){
				try {
					if(organ.getChildsCount()>0){
						delChildsWithPid(organ.getId());
					}
					//删除机构
					this.organDao.delete(organ);
					//GeneralMethod.setRecordInfo(organ, false);
				} catch (Exception e) {
					e.printStackTrace();
					flag=false;
				}
				
			}
		}
		return flag;
	}
	/**
	 * 
	 * @Description 根据pid迭代删除所有子节点信息
	 * @param pid void
	 */
	private void delChildsWithPid(String pid){
		String hql="from Organization where parentOrgan.id=?";
		List<Organization> parents=organDao.find(hql,pid);
		for (Organization organization : parents) {
			if(organization.getChildsCount()>0){
				delChildsWithPid(organization.getId());
			}	
			organDao.delete(organization);
			//设置通用信息
			//GeneralMethod.setRecordInfo(organization, false);
		}
	}
	
	/**
	 * Description: 添加机构前先查询是否存在该名称的机构<br>
	 * @param name
	 * @return
	 */
//	private Organization findByName(String name){
//		String hql="from Organization org where org.orgName = ? ";
//		List<Object> param = new ArrayList<Object>();
//		param.add(name);
//		return this.organDao.get(hql, param);
//	}
	public Organization findByCode(String code){
		String hql="from Organization org where org.orgCode = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(code);
		return this.organDao.get(hql, param);
	}

	
	/**
	 * Description: 将该机构的子机构组装成树<br>
	 * @param organ
	 * @param recursive
	 * @param name
	 * @return
	 */
	private TreeNodeBean buildTree(Organization organ,boolean recursive,String name){
		
		boolean isExist = false;
		if(organ.getOrgName().contains(name)){//如果包含
			isExist = true;
		}
		TreeNodeBean node = new TreeNodeBean();
		node.setId(organ.getId()+"");   	 
		node.setText(organ.getOrgName());
		String hql=" from Organization t where t.parentOrgan.id=?";
		List<Object> param = new ArrayList<Object>();
		param.add(organ.getId());
		List<Organization> childList = this.organDao.find(hql, param);
		if(childList!=null&&childList.size()>0){
			// node.setState("closed");//关闭节点 	
			if(recursive){	
				List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();   			 
				for (Organization org : childList){
					TreeNodeBean t = buildTree(org,true,name);
					if(t!=null){
						isExist = true;
						children.add(t);
					}		 
					node.setState("open"); 
				}
				node.setChildren(children);
			}   		 
		}    	 
		if(isExist){
			return node;
		}else{
			return null;
		}
	}
	/**
	 * 生成机构树，包含顶级机构
	 * Description: <br>
	 * @param organ
	 * @param recursive
	 * @return
	 */
	private TreeNodeBean buildTree(Organization organ,boolean recursive){
		TreeNodeBean node = new TreeNodeBean();
		node.setId(organ.getId()+"");   	 
		node.setText(organ.getOrgName());
		String hql=" from Organization t where t.parentOrgan.id=?";
		List<Object> param = new ArrayList<Object>();
		param.add(organ.getId());
		List<Organization> childList = this.organDao.find(hql, param);
		if(childList!=null&&childList.size()>0){
			// node.setState("closed");//关闭节点 	
			if(recursive){	
				List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();   			 
				for (Organization org : childList){
//   				TreeNodeBean t = buildTree(org,true);
//   				if(t!=null){
//   					children.add(t);
//   				}
					TreeNodeBean t = new TreeNodeBean();
					t.setId(org.getId()+"");
					t.setText(org.getOrgName());
					t.setIconCls("tree-folder");
					t.setState("close");
					children.add(t);
				}
				node.setState("open"); 
				node.setChildren(children);
			}   		 
		}    	 
		return node;
	}
	private TreeNodeBean buildTree2(Organization organ,boolean recursive){
		TreeNodeBean node = new TreeNodeBean();
		node.setText(organ.getOrgName());
		String hql=" from Organization t where t.parentOrgan.orgCode=?";
		List<Object> param = new ArrayList<Object>();
		List<Organization> childList = this.organDao.find(hql, param);
		if(childList!=null&&childList.size()>0){
			// node.setState("closed");//关闭节点 	
			if(recursive){	
				List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();   			 
				for (Organization org : childList){
					TreeNodeBean t = buildTree2(org,true);
					if(t!=null){
						children.add(t);
					}
				}
				node.setState("close"); 
				node.setChildren(children);
			}   		 
		}    	 
		return node;
	}
	private TreeNodeBean buildTree3(Organization organ,boolean recursive){
		TreeNodeBean node = new TreeNodeBean();
   	 	node.setId(organ.getId()+"");   	 
   	 	node.setText(organ.getOrgName());
   	 	String hql=" from Organization t where t.parentOrgan.id=? ";
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
   	 }    	 
   		 return node;
    }
	
	private TreeNodeBean buildTree4(Organization organ,boolean recursive){
		TreeNodeBean node = new TreeNodeBean();
   	 	node.setId(organ.getId()+"");   	 
   	 	node.setText(organ.getOrgName());
   	 	String hql=" from Organization t where t.parentOrgan.id=? ";
   	 	List<Organization> childList = this.organDao.find(hql,organ.getId());
   	 	if(childList!=null&&childList.size()>0){
   		// node.setState("closed");//关闭节点 	
   		 if(recursive){	
   			List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();   			 
   			for (Organization org : childList){
   				TreeNodeBean t = buildTree4(org,true);
   				if(t!=null){
   					children.add(t);
   				}
			}
   			 node.setState("close"); 
   			 node.setChildren(children);
   		 }	 
   	 }else{
   			node.setState("close"); 
   			List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();
   			List<Role> syroleList = null;
   			if (organ.getId() != null && !organ.getId().equals("")) {
   				hql = "from Role t  where t.organization.id=? order by t.seq";
   				syroleList = roleDao.find(hql, Long.valueOf(organ.getId()));
   				if(syroleList!=null&&syroleList.size()>0){
   					for (Role syrole : syroleList) {
   						children.add(tree(syrole, false));
   					}
   				}
   			}
  			 node.setChildren(children);
   			
   		 } 	    	 
   		 return node;
    }
	/**
	 * 
	 * Description: <br>
	 * 根据角色和recursive（是否递归查询子节点） 查询出角色或者其子角色的树
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TreeNodeBean tree(Role syrole, boolean recursive) {
		TreeNodeBean node = new TreeNodeBean();
		node.setId(syrole.getId() + "");
		node.setText(syrole.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);

		return node;
	}

	@Override
	public OrganBean search(OrganBean organBean) {
		String hql = "from Organization d where d.parentOrgan.id=0";
		Organization o=organDao.get(Organization.class, organBean.getId());
	    BeanUtils.copyProperties(o, organBean);
	    if(o.getParentOrgan()!=null){
	    	organBean.setParentId(o.getParentOrgan().getId());
	    	organBean.setParentName(o.getParentOrgan().getOrgName());
	    }
		return organBean;
	}

	
	

	@Override
	public OrganBean findOrgan(OrganBean organ) {
		if(null!=organ.getId()&& !"".equals(organ.getId())){
			Organization org= this.organDao.get(Organization.class, organ.getId());
			if(null!=org){
				OrganBean temp = new OrganBean();
				temp.setId(org.getId());
				temp.setOrgName(org.getOrgName());
				temp.setIsLeaf(org.getIsLeaf());
				temp.setNodeLevel(org.getNodeLevel());
				temp.setParentId(null!=org.getParentOrgan()?org.getParentOrgan().getId():"0");
				temp.setParentName(null!=org.getParentOrgan()?org.getParentOrgan().getOrgName():"");
				return temp;
			}
		}
		return null;
	}

	@Override
	public List<TreeNodeBean> findChildren(String orgId) {
   	 	String hql=" from Organization t where t.parentOrgan.id=?";
   	 	List<Object> param = new ArrayList<Object>();
   	 	param.add(Long.valueOf(orgId));
   	 	List<Organization> childList = this.organDao.find(hql, param);
   	 	List<TreeNodeBean> children = new ArrayList<TreeNodeBean>(); 
   	 	if(childList!=null&&childList.size()>0){
   			for (Organization org : childList){
   				TreeNodeBean t = new TreeNodeBean();
   				t.setId(org.getId()+"");
   				t.setText(org.getOrgName());
   				children.add(t);
			}
   		 }   		 
   		 return children;
    }

	@Override
	public List<TreeNodeBean> treeCode(OrganBean organBean) {
		String hql = "from Organization d where d.orgCode='10000' ";
		List<Organization> organList = this.organDao.find(hql);
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Organization organ:organList)
		{     
			TreeNodeBean node = this.buildTree2(organ,true);
			if(node!= null)
			{
				tree.add(node);
			}
		}
		return tree;
	}
	public List<TreeNodeBean> treeId(List<Organization> organBean) {
		String hql = "from Organization d  ";
		List<Organization> organList=null;
		if(organBean!=null){
			//List<Object> list=new ArrayList<Object>();
			String str="";
			for(Organization obj :organBean){
				//list.add(obj.getId());
				str+=",'"+obj.getId()+"'";
			}
			hql=hql+" where d.id in("+str.substring(1)+")";
			organList = this.organDao.find(hql);
		}else{
			hql=hql+" where d.parentOrgan=null";
			organList = this.organDao.find(hql);
		}
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Organization organ:organList){
			TreeNodeBean node = this.buildTree3(organ,true);
			if(node!= null){
				tree.add(node);
			}
		}
		return tree;
	}
	
	public List<TreeNodeBean> treeId1(Organization organBean) {
		String hql = "from Organization d  ";
		List<Organization> organList=null;
		if(organBean!=null&&organBean.getId()!=null){
			hql=hql+" where d.id=?";
			organList = this.organDao.find(hql,organBean.getId());
		}else{
			hql=hql+" where d.parentOrgan=null";
			organList = this.organDao.find(hql);
		}
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Organization organ:organList){
			TreeNodeBean node = this.buildTree4(organ,true);
			if(node!= null){
				tree.add(node);
			}
		}
		return tree;
	}

	

	@Override
	public Map<String, Organization> mapOrgan() {
		Map<String,Organization> result=new HashMap<String, Organization>();
		String hql ="  from Organization t ";
		List<Organization> orgList = this.organDao.find(hql);
		return result;
	}

	@Override
	public DataGridJson findTreeGridByPid(String orgId) {
		
		String hql ="from Organization ";
		List<Organization> orgs=null;
		//sql中null判断方式不一样
		if(orgId==null){
			hql = hql + " where parentOrgan ='0' order by sort ";
			orgs=organDao.find(hql);
		}else{
			hql = hql + " where parentOrgan.id =? order by sort ";
			orgs=organDao.find(hql,orgId);
		}
		List<OrganBean> obs=new ArrayList<OrganBean>(0);
		for (Organization org : orgs) {
			OrganBean ob=new OrganBean();
			BeanUtils.copyProperties(org, ob);
			if(org.getChildsCount()>0){
				ob.setState("closed");
			}else{
				ob.setState("open");
			}
			if(org.getParentOrgan()!=null){
				ob.set_parentId(org.getParentOrgan().getId());
				ob.setParentName(org.getParentOrgan().getOrgName());
				
			}
			obs.add(ob);
		}
		
		DataGridJson dgj=new DataGridJson();
		if(orgs==null){
			dgj.setTotal(0l);
		}else{
			dgj.setTotal((long)orgs.size());
		}
		
		dgj.setRows(obs);
		return dgj;
	}

	@Override
	public OrganBean update(OrganBean organ) {
		Organization o=organDao.get(Organization.class, organ.getId());
		//设置通用信息
		GeneralMethod.setRecordInfo(o, false);
		wanBeanUtils.copyPropertiesIgnoreNull(organ, o);
		organDao.update(o);
		Organization p=organDao.get(Organization.class, organ.getParentId());
		o.setParentOrgan(p);
		o.setSort(IdGenerator.getLongValue());
		o.setSortNumber(p.getSortNumber()+1);
		o.setId(organ.getId());
		o.setOrgCode(organ.getId());
		return organ;
	}

	@Override
	public Boolean updateSort(OrganBean organBean, String moveFlag) {
		boolean flag=false;
		if(organBean==null&&organBean.getId()==null&&StringUtil.isNotBlank(moveFlag)){
			return flag;
		}
		StringBuffer hql=new StringBuffer();
		Organization nowOrg=organDao.get(Organization.class,organBean.getId());
		if(nowOrg==null){
			return flag;
		}
		hql.append(" FROM Organization As i ");
		try{
			//生成查询目标组织的 hql
			
			
			//当前节点同父节点的组织
			List<Organization> orgList=null;
			if(nowOrg.getParentOrgan()!=null){
				hql.append(" and parentOrgan.id =? ");
			}else{
				hql.append(" and parentOrgan = null ");
			}
			if(moveFlag.equals("1")){
				hql.append(" and i.sort <? order by sort desc)");
			}else if(moveFlag.equals("0")){
				hql.append(" and i.sort >? order by sort asc )");
			}else{
				 return flag;
			 
			}
			String tHql=hql.toString();
			tHql=tHql.replaceFirst(" and ", " where ");
			//根据节点有无父节点调用不同的查询
			if(nowOrg.getParentOrgan()!=null){
				orgList=organDao.find(tHql,nowOrg.getParentOrgan().getId(),nowOrg.getSort());
			}else{
				orgList=organDao.find(tHql,nowOrg.getSort());
			}
			//查询出需要调换顺序的目标组织
		 	
		 	if(orgList==null||orgList.size()==0){
		 		return flag;
		 	}
		 	Organization targetOrg=orgList.get(0);
			Long temp=targetOrg.getSort();
			targetOrg.setSort(nowOrg.getSort());
			nowOrg.setSort(temp);
			 /*设置通用信息*/
			GeneralMethod.setRecordInfo(nowOrg, false);
			GeneralMethod.setRecordInfo(targetOrg, false);
			flag=true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public List<TreeNodeBean> treeIdLift() {
		String hql = "from Organization d  ";
		List<Organization> organList=null;
//		if(organBean!=null&&organBean.getId()!=null){
//			hql=hql+" where d.id=?";
//			organList = this.organDao.find(hql,organBean.getId());
//		}else{
			hql=hql+" where d.parentOrgan=null";
			organList = this.organDao.find(hql);
		//}
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		for (Organization organ:organList){
			TreeNodeBean node = this.buildTree3(organ,true);
			if(node!= null){
				tree.add(node);
			}
		}
		return tree;
	}
	
	@Override
	public Long findOrganizationByAreaId(String areaId,String organType) {
		List<Organization> orgs = new ArrayList<Organization>();
		try {
				String sql="select t.id from (select * from sys_organization where org_type = '"+organType+"') t " +
						"where t.id in (select org_id from t_area_to_org where area_id='"+areaId+"') ";
			
				List<Object> values = new ArrayList<Object>();
				orgs=organDao.findBySql(sql,values); 
				String str = orgs.toString();
				str = str.substring(1, str.length()-1);
				Long orgId = Long.valueOf(str);
				return orgId;
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public List<TreeNodeBean> newOrganTree() {
		String sql="select ID,ORG_NAME from sys_organization t where t.parent_id = '1434446783638' and t.recordstatus = 'Y' order by t.sort";
		List<Map<String, Object>> orgs=organDao.searchMapBySql(sql);
		List<TreeNodeBean> result=new ArrayList<TreeNodeBean>();
		for (Map<String, Object> map : orgs) {
			TreeNodeBean tnb=new TreeNodeBean();
			tnb.setId(map.get("ID")+"");
			tnb.setText(map.get("ORG_NAME")+"");
			result.add(tnb);
		}
		return result;
	}
	@Override
	public String getOrgByOraName(String orgName){
		String orgId = "";
		String sql = "select * from sys_organization o where o.org_name='" + orgName + "'";
		String Hql = "From Organization o where o.orgName='" + orgName + "'";
		List<Organization> list = organDao.find(Hql);
		if(list != null && list.size()>0){
			orgId = (String)list.get(0).getId();
		}
		return orgId;
	}

	@Override
	public List<TreeNodeBean> projectTree(OrganBean projectPo) {
		//String hql = "from TProject t where t.parent.id is null ";
				String hql = "from Organization t where t.parentOrgan.id ='0'";
				List<Organization> projectList = organDao.find(hql);
				List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
				for (Organization project : projectList){
					TreeNodeBean node = this.buildTreeNew(project, true);
					if(node != null){
						tree.add(node);
					}
				}
				return tree;
	}
	
	
	private TreeNodeBean buildTreeNew(Organization project,boolean recursive){
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("isProject", project.getSortNumber()!=null &&project.getSortNumber()==3?"是":"否");
		
		TreeNodeBean node = new TreeNodeBean();
		node.setId(project.getId());   	 
		node.setText(project.getOrgName());
		node.setAttributes(attributes);
		
		String hql="from Organization t where t.parentOrgan.id = ?";
		List<Organization> childList = this.organDao.find(hql, project.getId());
		if(childList!=null && childList.size()>0){
			if(recursive){
				List<TreeNodeBean> children = new ArrayList<TreeNodeBean>();   			 
				for (Organization pro : childList){
					Map<String, Object> attr = new HashMap<String, Object>();
					attr.put("isProject", pro.getSortNumber()!=null &&pro.getSortNumber()==3?"是":"否");
					
					TreeNodeBean t = new TreeNodeBean();
					t.setId(pro.getId()+"");
					t.setText(pro.getOrgName());
					t.setIconCls("tree-folder");
					t.setAttributes(attr);
					children.add(t);
				}
				node.setState("closed"); 
				node.setChildren(children);
			}   		 
		}    	 
		return node;
	}

	@Override
	public void pullProjectList() throws Exception {

		
		parsingProjectData("");

		
		
	}
	
	private void parsingProjectData(String id) throws Exception {

	// 1、配置访问请求
	String url = ConfigUtil.get("getProjectListFromOpen");
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("prentId", id);
	params.put("appKey", ConfigUtil.get("appKey"));
	params.put("appSecret", ConfigUtil.get("appSecret"));
	String bodyStr = HttpClientUtil.getResponseBodyAsString(url, params);
	// 判断是否成功
	
	if(StringUtil.isBlank(bodyStr)){
		throw new IOException("获取项目数据失败");
	}
	// 解析项目数据
	JSONObject bodyData = JSONObject.parseObject(bodyStr);
	String code = bodyData.getString("errcode");
	String msg = bodyData.getString("errmsg");
	if(!"1".equals(code)){
		throw new Exception(msg);
	}
	JSONObject data = bodyData.getJSONObject("data");
	if(JSONUtils.isNull(data)){
		throw new Exception("data数据为空");
	}
	JSONArray listDate = data.getJSONArray("listDate");
	if(JSONUtils.isNull(listDate)){
		throw new Exception("listDate数据为空");
	}
	// 轮询获取项目数据
	for (Object obj : listDate) {
		JSONObject jsonObj = (JSONObject) obj;
		String orgId = jsonObj.getString("orgId");
		String orgName = jsonObj.getString("orgName");
		String isProject = jsonObj.getString("isProject");
		
		Organization tProject = organDao.get(Organization.class, orgId);
		if(tProject==null){
			tProject = new Organization();
		}
		tProject.setId(orgId);
		tProject.setOrgName(orgName);
		tProject.setSortNumber(StringUtil.isNotBlank(isProject)&&isProject.equals("1")?3L:4L);
		tProject.setOrgCode(orgId);
		//tProject.setProjectState(true);
		Organization parent = organDao.get(Organization.class, id);
		if(parent!=null){
			tProject.setParentOrgan(parent);
		}
		tProject.setEasyId(jsonObj.getString("easyId"));
		
		
		organDao.saveOrUpdate(tProject);
		parsingProjectData(orgId);
	}
	}
	
	
	
	
	
}
