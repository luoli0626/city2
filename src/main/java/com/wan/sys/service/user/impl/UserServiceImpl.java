package com.wan.sys.service.user.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wan.sys.common.GeneralMethod;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.dao.organization.IOrganizationDao;
import com.wan.sys.dao.organization.impl.OrganizationDaoImpl;
import com.wan.sys.dao.role.IUseToRoleDao;
import com.wan.sys.dao.user.IUserDao;
import com.wan.sys.dao.user.IUserToProjectDao;
import com.wan.sys.entity.Role;
import com.wan.sys.entity.TUserProject;
import com.wan.sys.entity.User;
import com.wan.sys.entity.UserRole;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.BirthdayBean;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.common.impl.BaseServiceImpl;
import com.wan.sys.service.user.IUserService;
import com.wan.sys.util.wanBeanUtils;
import com.wan.sys.util.DateUtil;
import com.wan.sys.util.Encrypt;
import com.wan.sys.util.StringUtil;



/**
 * 用户Service
 * 
 * @author  
 * 
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	@Autowired
	IBaseDao<Object[]> baseDao;
	private IUserDao userDao;
	@Autowired
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	private IUseToRoleDao<UserRole> userRoleDao;
	@Autowired
	public void setUserRoleDao(IUseToRoleDao<UserRole> userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	
	private IOrganizationDao organizationDao;
	@Autowired
	public void setOrganizationDao(IOrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}
	
	//	@CacheEvict(value = "syproUserCache", allEntries = true)
	public UserBean reg(UserBean user) {
		user.setPassword(Encrypt.e(user.getPassword()));
		User u = new User();
		BeanUtils.copyProperties(user, u);
		GeneralMethod.setRecordInfo(u, true);
		userDao.save(u);
		return user;
	}

//	@Cacheable(value = "syproUserCache", key = "'login'+#user.loginAcct+#user.password")
//	@Transactional(readOnly = true)
	public UserBean login(UserBean user) {
		User u = userDao.get("from User u where u.loginAcct=? and u.password=?", user.getLoginAcct(), Encrypt.e(user.getPassword()));
		if (u != null) {
			user.setId(u.getId());
			user.setLoginAcct(u.getLoginAcct());
			user.setNickName(u.getNickName());
			user.setOrganization(u.getOrganization());
			//user.setStaff(u.getStaff());
			user.setIsFirstLog(u.getIsFirstLog());
			//处理角色避免出现角色拥有用户 ，用户拥有角色的死循环情况
			List<Role> roles=u.getRoles();
			List<Role> temp = new ArrayList<Role>();
			
			List<Organization> listOrg=u.getOrganization();
			List<Organization> temp1=new ArrayList<Organization>();
			if(listOrg!=null){
				for (Organization org : listOrg) {
					Organization o = new Organization();
						o.setId(org.getId());
						o.setOrgName(org.getOrgName());
						temp1.add(o);
				}
			}
			if(roles!=null){
				for (Role role : roles) {
					
						Role r = new Role();
						r.setId(role.getId());
						r.setText(role.getText());
						temp.add(r);
				}
			}
			user.setOrganization(temp1);
			user.setRoles(temp);
			return user;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public DataGridJson datagrid(DataGridBean dg, UserBean user) {
		DataGridJson j = new DataGridJson();
		String hql = " from User t  ";
		List<Object> values = new ArrayList<Object>();
		if (user != null) {// 添加查询条件
			if (user.getLoginAcct() != null && !user.getLoginAcct().trim().equals("")) {
				hql += " and t.loginAcct like '%%" + user.getLoginAcct().trim() + "%%' ";
			}
			if (user.getNickName() != null && !user.getNickName().trim().equals("")) {
				hql += " and t.nickName like '%%" + user.getNickName().trim() + "%%' ";
			}
			if (user.getFax()!= null && !user.getFax().trim().equals("")) {
				hql += " and t.fax like '%%" + user.getFax().trim() + "%%' ";
			}
			if(user.getMobilePhone()!=null && !user.getMobilePhone().trim().equals("")){
				hql += " and t.mobilePhone like '%" + user.getMobilePhone().trim() + "%' ";
			}
			if(user.getOfficePhone()!=null && !user.getOfficePhone().trim().equals("")){
				hql += " and t.officePhone like '%" + user.getOfficePhone().trim() + "%' ";
			}
			if(user.getEmail()!=null && !user.getEmail().trim().equals("")){
				hql += " and t.email like '%" + user.getEmail().trim() + "%' ";
			}
			if (user.getCreatedatetimeStart() != null && !user.getCreatedatetimeStart().equals("")) {
				hql += " and t.createdateTime>=? ";
				values.add(DateUtil.format(user.getCreatedatetimeStart()));
			}
			if (user.getCreatedatetimeEnd() != null && !user.getCreatedatetimeEnd().equals("")) {
				hql += " and t.createdateTime<=? ";
				values.add(DateUtil.format(user.getCreatedatetimeEnd()));
			}
			if (user.getModifydatetimeStart() != null && !user.getModifydatetimeStart().equals("")) {
				hql += " and t.modifyDateTime>=? ";
				values.add(DateUtil.format(user.getModifydatetimeStart()));
			}
			if (user.getModifydatetimeEnd() != null && !user.getModifydatetimeStart().equals("")) {
				hql += " and t.modifyDateTime<=? ";
				values.add(DateUtil.format(user.getModifydatetimeEnd()));
			}
			if(null!=user.getOrganId()&&!"".equals(user.getOrganId())){
				hql += " and t in(select elements(r.users) from Organization r where r.id= ? )";
				values.add(user.getOrganId());
			}
			if(null!=user.getRolesID() &&!"".equals(user.getRolesID())){
				hql += " and t in(select elements(r.users) from Role r where r.id= ? )";
				values.add(user.getRolesID());
			}
			if(null!=user.getScenesID() &&!"".equals(user.getScenesID())){
				hql += " and t in(select elements(r.users) from XSceneCategory r where r.id= ? )";
				values.add(user.getScenesID());
			}
		}
		hql=hql.replaceFirst("and", " where ");
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
				//处理角色避免出现角色拥有用户 ，用户拥有角色的死循环情况
//				List<Role> roles=u.getRoles();
//				List<Role> temp = new ArrayList<Role>();
//				if(roles!=null){
//					for (Role role : roles) {
//						
//							Role r = new Role();
//							r.setId(role.getId());
//							r.setText(role.getText());
//							temp.add(r);
//					}
//				}
//				//处理场景
//				List<XSceneCategory> scencs=u.getScenes();
//				List<XSceneCategory> temp1 = new ArrayList<XSceneCategory>();
//				if(scencs!=null){
//					for (XSceneCategory scene : scencs) {
//						
//						    XSceneCategory r = new XSceneCategory();
//							r.setId(scene.getId());
//							r.setName(scene.getName());
//							temp1.add(r);
//					}
//				}
//				
//				u.setScenes(temp1);
			//	u.setRoles(temp);
				users.add(u);
			}
		}
		j.setRows(users);// 设置返回的行
		return j;
	}

//	@Cacheable(value = "syproUserCache", key = "'combobox'+#q")
//	@Transactional(readOnly = true)
	public List<UserBean> combobox(String q) {
		if (q == null) {
			q = "1";
		}
		String hql = " from User t where name like '%%" + q.trim() + "%%'";
		List<User> l = userDao.find(hql,new Object[]{});
		List<UserBean> ul = new ArrayList<UserBean>();
		if (l != null && l.size() > 0) {
			for (User syuser : l) {
				UserBean u = new UserBean();
				BeanUtils.copyProperties(syuser, u);
				ul.add(u);
			}
		}
		return ul;
	}

//	@CacheEvict(value = "syproUserCache", allEntries = true)
	public UserBean add(UserBean user) {
		//user.setId(UUID.randomUUID().toString());
		if(user.getPassword()==null){
			user.setPassword(Encrypt.e("123456"));
		}else{
			user.setPassword(Encrypt.e(user.getPassword()));
		}
		User syuser = new User();
		wanBeanUtils.copyPropertiesIgnoreNull(user, syuser);
		//通用信息设置
		GeneralMethod.setRecordInfo(syuser, true);
		userDao.save(syuser);
		user.setId(syuser.getId());

		return user;
	}
//	@CacheEvict(value = { "syproAuthCache", "syproUserCache" }, allEntries = true)
	public UserBean edit(UserBean user) {
		User syuser = userDao.get(User.class, Long.valueOf(user.getId()));
		if (syuser == null) {
			return user;
		}
		//通用信息设置
		wanBeanUtils.copyPropertiesIgnoreNull(user, syuser);
		GeneralMethod.setRecordInfo(syuser, false);
//		System.out.println(syuser.getRoles().get(0).getId());
		
//		this.userDao.update(syuser);
//		String hql=" delete from UserRole t where t.user.id="+syuser.getId();
//		this.userDao.executeHql(hql);
//		 if(null!=syuser.getRoles()&& syuser.getRoles().size()>0){
//			 for(Role role:syuser.getRoles()){
//				 UserRole ur= new UserRole();
//				 ur.setRole(role);
//				 ur.setUser(syuser);
//				 this.userRoleDao.save(ur);
//			 }
//		 }
		return user;
	}
	@SuppressWarnings("unchecked")
//	@Cacheable(value = "syproUserCache", key = "'getUserInfo'+#user.id")
	public UserBean getUserInfo(UserBean user) {
		User syuser = userDao.get(User.class, user.getId());
		BeanUtils.copyProperties(syuser, user);
		//处理角色避免出现角色拥有用户 ，用户拥有角色的死循环情况
		List<Role> roles=syuser.getRoles();
		List<Role> temp = new ArrayList<Role>();
		if(roles!=null){
			for (Role role : roles) {
				
					Role r = new Role();
					r.setId(role.getId());
					r.setText(role.getText());
					temp.add(r);
			}
		}
		List<Organization> listOrg=syuser.getOrganization();
		List<Organization> temp1=new ArrayList<Organization>();
		if(listOrg!=null){
			for (Organization org : listOrg) {
				Organization o = new Organization();
					o.setId(org.getId());
					o.setOrgName(org.getOrgName());
					temp1.add(o);
			}
		}
		user.setOrganization(temp1);
		user.setRoles(temp);
		return user;
	}

//	@CacheEvict(value = "syproUserCache", allEntries = true)
	public UserBean editUserInfo(UserBean user) {
		if(user==null){
			return user;
		}
		if(user.getId()==null){
			user.setFlag("2");
			return user;
		}
		User u=getById(user.getId());
		if(u==null){
			user.setFlag("2");
			return user;
		}
		wanBeanUtils.copyPropertiesIgnoreNull(user, u);
		//设置通用信息
		GeneralMethod.setRecordInfo(u, false);
		user.setFlag("1");
		return user;
	}
	@Override
	public UserBean loginPointV(String userName) {
       User user =   userDao.get("from User u where u.loginAcct=?", userName);
        if(null != user){
        	UserBean ub = new UserBean();
        	ub.setId(user.getId());
        	ub.setLoginAcct(user.getLoginAcct());
			return ub;
        }
        	return null;
       
		
	}

	@Override
	public boolean checkLoginAcct(String loginAcct) {
		String hql="from User where loginAcct=?";
		List<User> users=userDao.find(hql,loginAcct);
		if(users==null||users.size()==0){
			return true;
		}
		return false;
	}

	@Override
	public UserBean updateUserPassword(UserBean user) {
		if(user==null){
			return user;
		}
		if(user.getId()==null){
			user.setFlag("2");
			return user;
		}
		String hql="from User where id=?";
		//User u=this.userDao.get(hql,Encrypt.e(user.getOldPassword()));
		User u=this.userDao.get(hql,GlobalContext.getCurrentUser().getId());
		if(u==null){
			user.setFlag("2");
			return user;
		}
		// String iid= GlobalContext.getSession().getId();
		
		u.setPassword(Encrypt.e(user.getPassword()));
		GeneralMethod.setRecordInfo(u, false);
		user.setFlag("1");
		return user;
	}
	

	@Override
	public UserBean firstUpPassWord(UserBean user) {
			User u= userDao.get(User.class, user.getId());
			if(null != u){
				u.setPassword(Encrypt.e(user.getPassword()));
				u.setMobilePhone(user.getMobilePhone());
				u.setIsFirstLog("2");
				userDao.update(u);
		}
		return user;
	}

	@Override
	public void resetPass(String []ids) {
		
		String str="";
		for(int i=0;i<ids.length;i++){
			if(i==ids.length-1){
				str+=ids[i];
			}
			else{
				str+=ids[i]+",";
			}	
		}
		
		String sql = "update SYS_USER2 t set t.PASSWORD = '"+Encrypt.e("123456")+"' where t.id in("+str+")";
		userDao.executeSql(sql);
	}

	public Object orgList() {
		return userDao.searchMapBySql("select id,org_name orgName from sys_organization where parent_id is null  order by org_name");
	}

	@Override
	public List<BirthdayBean> getBirthday() {
		List<BirthdayBean> result=new ArrayList<BirthdayBean>();
        List<Object[]> list=baseDao.findBySql("SELECT a.BRANCH_NAME,a.AREA_NAME,a.BUILD_NAME,a.CELL_NAME,a.HOUSE_NAME,t.BIRTHDAY_DATE,t.NAME,DAYOFYEAR(t.BIRTHDAY_DATE)-DAYOFYEAR(now()),t.PHONE,t.RELATIONSHIP FROM v_personnel_info t,v_house_info a  where t.HOUSE_ID=a.ID and DAYOFYEAR(t.BIRTHDAY_DATE)-DAYOFYEAR(now())<20 and DAYOFYEAR(t.BIRTHDAY_DATE)-DAYOFYEAR(now())>=0 order by DAYOFYEAR(t.BIRTHDAY_DATE)-DAYOFYEAR(now()) ");
		if(null!=list&&list.size()>0){
			for(Object[] obj:list){
				BirthdayBean bean=new BirthdayBean();
				bean.setBranchName(obj[0]+"");
				bean.setAreaName(obj[1]+"");
				bean.setBuildName(obj[2]+"");
				bean.setCellName(obj[3]+"");
				bean.setHouseName(obj[4]+"");
				bean.setBirthdayDay(obj[5]+"");
				bean.setOwerName(obj[6]+"");
				bean.setCountDay(obj[7]+"");
				bean.setPhone(obj[8]+"");
				bean.setRelationship(obj[9]+"");
				result.add(bean);
			}
			
		}
        return result;
	}


	@Override
	public UserBean weiXinUserlogin(String userId) {
		UserBean user=new UserBean();
		User u = userDao.get("from User u where u.loginAcct=? ", userId);
		if(null!=u&&!"".equals(u)){
			wanBeanUtils.copyPropertiesIgnoreNull(u, user);
			return user;
		}
		return null;
	}

	@Override
	public DataGridJson checkUserDatagrid(DataGridBean dg, UserBean user) {
		// 获取当前登录用户的信息
		UserBean userBean = GlobalContext.getCurrentUser();
		User currentUser = userDao.get(User.class, userBean.getId());
		// 组装查询条件
		List<String> organId = new ArrayList<String>();
		List<Long> rolesID = new ArrayList<Long>();
		List<Long> scenesID = new ArrayList<Long>();
		// 固定角色为项目人员
		rolesID.add(1481791946675L);
//		if (currentUser.getRoles()!=null&&currentUser.getRoles().size()>0) {
//			for (Role r : currentUser.getRoles()) {
//				rolesID.add(r.getId());
//			}
//		}
		
		if (currentUser.getOrganization()!=null&&currentUser.getOrganization().size()>0) {
			for (Organization r : currentUser.getOrganization()) {
				organId.add(r.getId());
			}
		}
		// 根据角色、场景、项目查询用户信息
		DataGridJson j = new DataGridJson();
		String hql = " from User t  ";
		List<Object> values = new ArrayList<Object>();
		if (user != null) {// 添加查询条件
			if (user.getLoginAcct() != null && !user.getLoginAcct().trim().equals("")) {
				hql += " and t.loginAcct like '%%" + user.getLoginAcct().trim() + "%%' ";
			}
			if (user.getNickName() != null && !user.getNickName().trim().equals("")) {
				hql += " and t.nickName like '%%" + user.getNickName().trim() + "%%' ";
			}
			if (user.getFax()!= null && !user.getFax().trim().equals("")) {
				hql += " and t.fax like '%%" + user.getFax().trim() + "%%' ";
			}
			if (user.getCreatedatetimeStart() != null && !user.getCreatedatetimeStart().equals("")) {
				hql += " and t.createdateTime>=? ";
				values.add(DateUtil.format(user.getCreatedatetimeStart()));
			}
			if (user.getCreatedatetimeEnd() != null && !user.getCreatedatetimeEnd().equals("")) {
				hql += " and t.createdateTime<=? ";
				values.add(DateUtil.format(user.getCreatedatetimeEnd()));
			}
			if (user.getModifydatetimeStart() != null && !user.getModifydatetimeStart().equals("")) {
				hql += " and t.modifyDateTime>=? ";
				values.add(DateUtil.format(user.getModifydatetimeStart()));
			}
			if (user.getModifydatetimeEnd() != null && !user.getModifydatetimeStart().equals("")) {
				hql += " and t.modifyDateTime<=? ";
				values.add(DateUtil.format(user.getModifydatetimeEnd()));
			}
			if(null!=organId&&organId.size()>0){
				hql += " and t in(select elements(r.users) from Organization r where r.id in(?";
				for (int i = 0; i < organId.size(); i++) {
					if (i>0) {
						hql += ",?";
					}
					values.add(organId.get(i));
				}
				hql += "))";
			}
			if(null!=rolesID &&rolesID.size()>0){
				hql += " and t in(select elements(r.users) from Role r where r.id in(?";
				for (int i = 0; i < rolesID.size(); i++) {
					if (i>0) {
						hql += ",?";
					}
					values.add(rolesID.get(i));
				}
				hql += "))";
			}
			if(null!=scenesID &&scenesID.size()>0){
				hql += " and t in(select elements(r.users) from XSceneCategory r where r.id in(?";
				for (int i = 0; i < scenesID.size(); i++) {
					if (i>0) {
						hql += ",?";
					}
					values.add(scenesID.get(i));
				}
				hql += "))";
			}
			if (null!=user.getCheckStatus() &&!"".equals(user.getCheckStatus())) {
				hql += " and t.checkStatus=? ";
				values.add(user.getCheckStatus());
			}
		}
		hql=hql.replaceFirst("and", " where ");
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
	public List<TreeNodeBean> getCheckStatusTree() {
		// 获取审核状态数据
		UserBean user = new UserBean();
		List<TreeNodeBean> tree = new ArrayList<TreeNodeBean>();
		// 组装审核状态列表
		if (user.checkStatusStr!=null && user.checkStatusStr.length>0) {
			for (String chechStr : user.checkStatusStr) {
				// 分割审核状态的id和名称
				String[] str = chechStr.split("-");
				// 把id和名称存入tree中
				TreeNodeBean node = new TreeNodeBean();
				node.setId(str[0]);
				node.setText(str[1]);
				Map<String, Object> attributes = new HashMap<String, Object>();
				node.setAttributes(attributes);
				tree.add(node);
			}
		}
		return tree;
	}

//	
//	@Override
//	public HSSFSheet export(HttpServletResponse response,
//			HttpServletRequest request) {
//
//		HSSFWorkbook workbook = new HSSFWorkbook();//建立工作簿
//		HSSFSheet sheet = workbook.createSheet("User");//建立工作表
//		//PoiUtil.export(sheet);//建立表头
//		
//		HSSFCellStyle columnStyle1 = sheet.getWorkbook().createCellStyle();
//		columnStyle1.setAlignment(CellStyle.ALIGN_CENTER);
//		columnStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//	
//		List<Organization> organization = organizationDao.find("from Organization t where sortNumber=3");
//		List<Object[]> sceneCategory = baseDao.findBySql("select id,code_name from x_scene_category where parent_id is null");
//		int m = 2;
//		for(int i=0;i<organization.size();i++){
//			for(int j=0;j<sceneCategory.size();j++){
//					
//				List<Object[]> userId = baseDao.findBySql("SELECT w.USER_ID FROM (select t.user_id from sys_user_project t where t.project_id='"+organization.get(i).getId()+"'and t.user_id in(select SYUSER_ID from sys_user_to_scene where SYSCENE_ID='"+sceneCategory.get(j)[0]+"') and t.user_id in"+
//                 "(select syuser_id from sys_user_to_role t where syrole_id = 1434607453881)) w left join sys_user2 s on w.user_id = s.id where s.recordstatus='Y'");
//
//				if (userId.size() != 1) {
//					HSSFRow row = sheet.createRow(m);
//					
//					sheet.setColumnWidth(0, 100 * 80);
//					row.createCell(0).setCellValue(organization.get(i).getParentOrgan().getOrgName());
//					
//					sheet.setColumnWidth(1, 100 * 80);
//					row.createCell(1).setCellValue(organization.get(i).getOrgName().toString());
//					
//					sheet.setColumnWidth(2, 100 * 80);
//					row.createCell(2).setCellValue(sceneCategory.get(j)[1].toString());
//					
//					sheet.setColumnWidth(3, 100 * 80);
//					row.createCell(3).setCellValue(userId.size());
//					
//					m++;
//				}
//			}
//		}
//		return sheet;
//	}

	@Override
	public void updateRecordStatus(String[] ids) {
		// TODO Auto-generated method stub
		if(ids!=null){
			try {
				for (String id : ids) {
					User user = userDao.get(User.class, Long.parseLong(id));
					user.setRecordStatus(GlobalConstant.FLAG_Y);
					//user.setSeal(GlobalConstant.FLAG_Y);
					userDao.update(user);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	@Override
	public UserBean weiXinlogin(String openid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void passCheckById(String[] ids) {
		// TODO Auto-generated method stub
		
	}

}
