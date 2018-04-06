package com.wan.sys.service.organization;

import java.util.List;
import java.util.Map;

import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.OrganBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.service.common.IBaseService;


/**
 * 
 * 文件名称: 机构管理service
 * 内容摘要: 机构管理service
 * 创 建 人:
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
public interface IOrganizationService extends IBaseService<Organization> {
	
	/**
	 * Description: 查询机构详情<br>
	 * @param organ
	 * @return
	 */
	public OrganBean findOrgan(OrganBean organ);
	
	/**
	 * 
	 * Description:添加机构信息 <br>
	 * @param organBean
	 * @return
	 */
	public OrganBean add(OrganBean organBean);
	/**
	 * Description: 编辑机构信息<br>
	 * @param organBean
	 * @return
	 */
	public String edit(OrganBean organBean);
	/**
	 * Description:获得机构树 <br>
	 * @param id
	 * @return
	 */
	public  List<TreeNodeBean> tree(OrganBean organBean);
	/**
	 * Description: 获取机构树，得到机构编码和机构名称<br>
	 * @param organBean
	 * @return
	 */
	public  List<TreeNodeBean> treeCode(OrganBean organBean);
	/**
	 * Description: 获取机构树，得到机构ID和机构名称<br>
	 * @param organBean
	 * @return
	 */
	public List<TreeNodeBean> treeId(List<Organization> organBean);
	/**
	 * Description: 获取机构树，得到机构ID和机构名称以及角色<br>
	 * @param organBean
	 * @return
	 */
	public List<TreeNodeBean> treeId1(Organization organBean);
	/**
	 *  删除机构并删除该结构的子机构
	 * @param ids
	 * @return
	 */
	public boolean del(String[] ids);
	/**
	 * Description: 查询机构并组装成树<br>
	 * @param organBean
	 * @return
	 */
	public OrganBean search(OrganBean organBean);
	
	
	/**
	 * Description: 根据机构编码查询机构<br>
	 * @param code
	 * @return
	 */
	public Organization findByCode(String code);
	/**
	 * Description:查询子节点 <br>
	 * @param orgId
	 * @return
	 */
	public List<TreeNodeBean> findChildren(String orgId);
	
	
	
	/**
	 * 获取机构的编码和名称并保存到map中
	 * @return
	 */
	public Map<String,Organization> mapOrgan();
	/**
	 * 
	 * @Description 根据组织id获取所有子组织
	 * @param orgId
	 * @return DataGridJson
	 */
	public DataGridJson findTreeGridByPid(String orgId);
	/**
	 * 
	 * @Description 修改机构信息 
	 * @param organ
	 * @return OrganBean
	 */
	public OrganBean update(OrganBean organ);
	/**
	 * 调整排序
	 * @Description 
	 * @param organBean
	 * @param moveFlag
	 * @return Boolean
	 */
	public Boolean updateSort(OrganBean organBean, String moveFlag);
	
	public List<TreeNodeBean> treeIdLift();
	/**
	 * 根据区域id找到所属机构
	 * @param areaId
	 * @return Organization
	 */
	public Long findOrganizationByAreaId(String areaId,String organType);
	/**
	 * 返回特检院下所有的部门
	 * @return List<TreeNodeBean>
	 */
	public List<TreeNodeBean> newOrganTree();
	/**
	 * 
	 * @param orgName
	 * @return
	 */
	public String getOrgByOraName(String orgName);
	
	
	/**
	 * 项目树
	 * @param projectPo -查询条件
	 * @return
	 */
	public List<TreeNodeBean> projectTree(OrganBean projectPo);
	
	/**
	 * 从开放平台拉取项目数据到本地
	 * @return
	 */
	public void pullProjectList() throws Exception;
	
}
