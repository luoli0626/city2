package com.wan.sys.controller.organization;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.sys.common.BaseController;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.dao.organization.IOrganizationDao;
import com.wan.sys.dao.user.IUserDao;
import com.wan.sys.entity.User;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.DeptBean;
import com.wan.sys.pojo.DeptMentBean;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.OrganBean;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.organization.IAddressBookService;
import com.wan.sys.service.organization.IOrganizationService;
/**
 * 
 * 文件名称： 通讯录controller
 * 内容摘要： 基本增删改查管理
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
@Controller
@RequestMapping("/addressBook")
public class AddressBookController  extends BaseController{
	@Autowired
	private IAddressBookService addressBookService;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IOrganizationService organService;
	@Autowired
	private IOrganizationDao organDao;
	@RequestMapping(value = "/main",method={RequestMethod.GET})
	public String maininfo() {
		return "sys/addressBook/addressBookInfo";
	}
	
	
	/**
	 * 当前人部门列表
	 * @return
	 */
	@RequestMapping(value = "/tree",method={RequestMethod.POST})
	@ResponseBody
	public List<TreeNodeBean> tree() {
		UserBean user = GlobalContext.getCurrentUser();
		return addressBookService.tree(user);
	}
	
	
	/**
	 * 用户表格
	 * 
	 * @param dg
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/datagrid")
	@ResponseBody
	public DataGridJson datagrid(DataGridBean dg,DeptBean bean) {
		UserBean user = GlobalContext.getCurrentUser();
		if(null==bean.getOrgId1()||"".equals(bean.getOrgId1())){
			if(null!=user.getOrganization()&&user.getOrganization().size()>0){
				bean.setOrgId1(user.getOrganization().get(0).getId());
			}else{
				bean.setOrgId1("");
			}
		}
		DataGridJson dgj=addressBookService.datagrid(dg, bean);
		return dgj;
	}
	
	
	/**
	 * 用户表格
	 * 
	 * @param dg
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/datagrid1")
	@ResponseBody
	public DataGridJson datagrid1(DataGridBean dg,DeptBean bean) {
		DataGridJson dgj=addressBookService.datagrid1(dg, bean);
		return dgj;
	}
	
	/**
	 * 查询子部门
	 * @param deptId
	 * @return Json
	 */
	@RequestMapping(value = "/findDept")
	@ResponseBody
	public Json findDept(String deptId) {
		Json j = new Json();
		JSONObject jo=new JSONObject();
		UserBean user = GlobalContext.getCurrentUser();
		User user1=userDao.get(User.class, user.getId());
//		if(null==deptId||"".equals(deptId)){
//			if(null!=user1.getOrganization()&&user1.getOrganization().size()>0){
//				if(null!=user1.getOrganization().get(0).getSortNumber()&&user1.getOrganization().get(0).getSortNumber()==3){
//				  deptId=user1.getOrganization().get(0).getId();
//				  jo.put("id", user1.getOrganization().get(0).getId());
//					jo.put("name", user1.getOrganization().get(0).getOrgName());
//					j.setObj_other(jo);
//				}else{
//					j.setSuccess(false);
//					j.setMsg("请定位到具体项目");
//					jo.put("id", user1.getOrganization().get(0).getId());
//					jo.put("name", user1.getOrganization().get(0).getOrgName());
//					j.setObj_other(jo);
//					return j;
//				}
//			}else{
//				deptId="";
//			}
//		}else{
//			Organization org=organDao.get(Organization.class, deptId);
//			if(null!=org&&org.getSortNumber()!=3){
//				j.setSuccess(false);
//				j.setMsg("请定位到具体项目");
//				jo.put("id", org.getId());
//				jo.put("name", org.getOrgName());
//				j.setObj_other(jo);
//				return j;
//			}
//		}
		if(null==deptId||"".equals(deptId)){
			if(null!=user1.getOrganization()&&user1.getOrganization().size()>0){
				  deptId=user1.getOrganization().get(0).getId();
				  jo.put("id", user1.getOrganization().get(0).getId());
				  jo.put("name", user1.getOrganization().get(0).getOrgName());
				  j.setObj_other(jo);
			}
		}
		List<OrganBean> listDept = this.addressBookService.findDept(deptId);
		j.setSuccess(true);
		j.setObj(listDept);
		return j;
	}
	/**
	 * 添加部门
	 * @param maualInfo
	 * @param request
	 * @return Json
	 */
	@RequestMapping(value = "/addDept",method={RequestMethod.POST})
	@ResponseBody
	public Json addDept(DeptMentBean dept) {
		Json j = new Json();
		OrganBean organ=new OrganBean();
		organ.setId(dept.getId());
		organ.setOrgName(dept.getDeptName());
		organ.setParentId(dept.getParentId());
		organ.setOrgDesc(dept.getDeptDesc());
		try{
			if(null!=organ.getId()&&!"".equals(organ.getId())){
			   organ=organService.update(organ);
			}else{
				organ=organService.add(organ);
			}
			j.setSuccess(true);
			j.setMsg("成功");
			
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 j.setSuccess(false);
			 j.setMsg("添加失败");
		 }
		 return j; 
	}
	
	/**
	 * 添加部门与用户关联
	 * @param dept
	 * @return Json
	 */
	@RequestMapping(value = "/addDeptUser",method={RequestMethod.POST})
	@ResponseBody
	public Json addDeptUser(String userId,String deptId,String isAdministrator) {
		Json j = new Json();
		
		try{
			j=addressBookService.addDeptUser(userId,deptId,isAdministrator);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 j.setSuccess(false);
			 j.setMsg("添加失败");
		 }
		 return j; 
	}
	
	/**
	 * 修改部门与用户关联
	 * @param dept
	 * @return Json
	 */
	@RequestMapping(value = "/updateDeptUser",method={RequestMethod.POST})
	@ResponseBody
	public Json updateDeptUser(String deptUserId,String deptMentId,String isAdministrator) {
		Json j = new Json();
		
		try{
			j=addressBookService.addDeptUser(deptUserId,deptMentId,isAdministrator);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 j.setSuccess(false);
			 j.setMsg("添加失败");
		 }
		 return j; 
	}

	
	
	
	
	/**
	 * 添加后删除管理员
	 * @param dept
	 * @return Json
	 */
	@RequestMapping(value = "/editDeptUser",method={RequestMethod.POST})
	@ResponseBody
	public Json editDeptUser(String userId,String deptId,String type) {
		Json j = new Json();
		
		try{
			j=addressBookService.editDeptUser(userId,deptId,type);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 j.setSuccess(false);
			 j.setMsg("添加失败");
		 }
		 return j; 
	}
	
	
	/**
	 * 删除部门与用户关联
	 * @param dept
	 * @return Json
	 */
	@RequestMapping(value = "/deleteDept",method={RequestMethod.POST})
	@ResponseBody
	public Json deleteDept(String userId,String deptId) {
		Json j = new Json();
		
		try{
			j=addressBookService.delDeptUser(userId,deptId);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 j.setSuccess(false);
			 j.setMsg("添加失败");
		 }
		 return j; 
	}
	

	

	
	/**
	 * 查询部门信息
	 * @param dept
	 * @return Json
	 */
	@RequestMapping(value = "/findDeptMent",method={RequestMethod.POST})
	@ResponseBody
	public Json findDeptMent(String deptId) {
		Json j = new Json();
		
		try{
			j=addressBookService.findDeptMent(deptId);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 j.setSuccess(false);
			 j.setMsg("添加失败");
		 }
		 return j; 
	}
	
	
	
	
}
