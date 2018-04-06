package com.wan.sys.controller.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wan.sys.common.BaseController;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.entity.Menu;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.TreeNodeBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.menu.IMenuService;
import com.wan.sys.service.user.IUserService;
import com.wan.sys.util.ExceptionUtil;
import com.wan.sys.util.IpUtil;

/**
 * 
 * 文件名称：  主页controller
 * 内容摘要：  成功登录后的主页布局
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
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
	private static final Logger logger = Logger.getLogger(MainController.class);
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IUserService userService;
	

	@RequestMapping(value = "/north")
	public String north(Model model) {
		UserBean user=GlobalContext.getCurrentUser();
		user=userService.getUserInfo(user);
        model.addAttribute("user",user);
		return "sys/layout/north";
	}

	@RequestMapping(value = "/west")
	public String west() {
		return "sys/layout/west";
	}

	@RequestMapping(value = "/center")
	public String center() {
		return "sys/layout/center";
	}

	@RequestMapping(value = "/south")
	public String south() {
		return "sys/layout/south";
	}

	/**
	 * 跳转到home页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/home")
	public String home(Model model) {
		UserBean user=GlobalContext.getCurrentUser();
//		JSONObject  task= manualService.taskCount(user);
//        model.addAttribute("task",task);
        return "sys/layout/home2";
	}

	/**
	 * 跳转到用户信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userInfo")
	public String userInfo() {
		return "sys/user/userInfo";
	}

	/**
	 * Description:跳转到登录页面 <br>
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toLogin")
	public String toLogin() {
		return "redirect:/toLogin";
	}
	/**
	 * 获得知识库详情内容
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAnnounce",method={RequestMethod.GET})
	public String getAnnounce(Model model,String id){
//		Announce announce = announceService.getAnnounceDetail(id);
//		model.addAttribute("announce", announce);
		return "cruise/announcement/announceDetailH5";
	}
	
	/**
	 * 获得知识库详情内容
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getId",method={RequestMethod.GET})
	public String getId(Model model,String id){
//		XManualInfoBean manual=manualService.getManual(id);
//		model.addAttribute("manual",manual);
		return "cruise/manual/manualId";
	}
	/**
	 * 获得用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	public Json getUserInfo(HttpSession session) {
		Json j = new Json();
		UserBean su = GlobalContext.getCurrentUser();
		if (su != null) {
			UserBean u = userService.getUserInfo(su);
			j.setObj(u);
			j.setSuccess(true);
		} else {
			j.setMsg("您没有登录或登录超时，请重新登录后重试！");
		}
		return j;
	}

	/**
	 * 编辑用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editUserInfo")
	@ResponseBody
	public Json editUserInfo(@RequestBody UserBean user) {
		Json j = new Json();
		UserBean u = userService.editUserInfo(user);
		if (u != null) {
			if (u.getFlag().equals("1")) {
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}

		}else{
			j.setSuccess(false);
		}
		return j;
	}

	/**
	 * 用户注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public Json logout(HttpSession session, UserBean user) {
		if(null!=GlobalContext.getCurrentUser()&&!"".equals(GlobalContext.getCurrentUser())){
		user.setLoginAcct(GlobalContext.getCurrentUser().getLoginAcct());
		user.setId(GlobalContext.getCurrentUser().getId());
		}
		Json j = new Json();
		if (session != null) {
			session.invalidate();
		}
		j.setSuccess(true);
		return j;
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户的信息
	 * @return json
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/reg")
	@ResponseBody
	public Json reg(UserBean user) {
		// @RequestMapping(value="/
		Json j = new Json();
		try {
			UserBean u = userService.reg(user);
			j.setSuccess(true);
			j.setMsg("注册成功！");
		} catch (Exception e) {
			j.setMsg("用户名已存在！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		return j;
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户的信息
	 * @return json
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Json login(UserBean user, HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {
		// response.setContentType("text/html;charset=utf-8");
		// request.setCharacterEncoding("utf-8");
		Json j = new Json();
		if (null != user.getLoginAcct() && !"".equals(user.getLoginAcct())
				&& null != user.getPassword() && !"".equals(user.getPassword())) {
			UserBean u = userService.login(user);
			if (u != null) {
				//判断用户是否第一次登陆
				if( null ==u.getIsFirstLog() ||  "1".equals(u.getIsFirstLog()) || "".equals(u.getIsFirstLog())){
					j.setMsg("第一次登陆，请修改密码!");
					u.setTempSession("1");
					u.setIp(IpUtil.getIpAddr(request));
					/* 获取用户拥有的菜单权限 */
					List<Menu> menus = this.menuService.findMenuMapByUserId(u
							.getId());
					u.setMenus(menus);
					request.getSession().setAttribute(GlobalConstant.CURRENT_USER,
							u);
					//request.getSession().setAttribute("isfirst","11");
					j.setObj(u);
				    return j;
				}
				j.setSuccess(true);
				j.setMsg("登录成功!");
				u.setIp(IpUtil.getIpAddr(request));
				/* 获取用户拥有的菜单权限 */ 
				List<Menu> menus = this.menuService.findMenuMapByUserId(u
						.getId());
				u.setMenus(menus);
				request.getSession().setAttribute(GlobalConstant.CURRENT_USER,
						u);
				//j.setObj(u);
			} else {
				j.setMsg("用户名或密码错误!");
			}
		} else {
			j.setMsg("请输入用户名和密码!");
		}
		return j;
	}

	/**
	 * 获取菜单树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<TreeNodeBean> tree(String id, HttpSession session) {
		List<TreeNodeBean> list = menuService.tree(id, GlobalContext
				.getCurrentUser().getId(), GlobalConstant.TIMESTAPE);
		return list;
	}
	/**
	 * 获取菜单树
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getUserMenuTree")
	@ResponseBody
	public List<TreeNodeBean> getUserMenuTree(String id, HttpSession session) {
		List<TreeNodeBean> list = menuService.buildTreeByUserId(GlobalContext.getCurrentUser().getId());
		return list;
	}
	/**
	 * 修改用户密码
	 * @param user
	 * @return Json
	 */
	@RequestMapping(value = "/updatePwd")
	@ResponseBody
	public Json updatePwd(@RequestBody UserBean user){
		user=userService.updateUserPassword(user);
		Json j=new Json();
		if(user!=null){
			if(user.getFlag()=="1"){
				GlobalContext.getSession().removeAttribute(GlobalConstant.CURRENT_USER);
				j.setSuccess(true);
			}else{
				j.setSuccess(false);
			}
		}else{
			j.setSuccess(false);
		}
		return j;
	}

	@RequestMapping(value = "/taskList")
	public String taskList(Model model) {
		UserBean user=GlobalContext.getCurrentUser();
//		List<JSONObject>  taskList= manualService.processList(user);
//        model.addAttribute("taskList",taskList);
		return "sys/layout/taskList";
	}

	@RequestMapping(value = "/reportFrom")
	public String reportFrom(Model model) {
		UserBean user=GlobalContext.getCurrentUser();
		return "sys/layout/reportFrom";
	}
	
	/**
	 * 第一次登陆修改密码
	 * @return Json
	 */
	
	@RequestMapping(value="/firstUpdate")
	@ResponseBody
	public Json firstLoginUpWord(@RequestBody UserBean user,HttpServletRequest request){
		Json json = new Json();
		
		if(null!=GlobalContext.getCurrentUser() &&  null != user && !"".equals((user.getId()+"").trim())&& !"".equals((user.getPassword()+"").trim())){
			try {
				userService.firstUpPassWord(user);
				json.setMsg("密码修改成功");
				json.setSuccess(true);
				//request.getSession().setAttribute("isfirst","");
				GlobalContext.getCurrentUser().setTempSession("");
				System.out.println(".."+GlobalContext.getCurrentUser().getTempSession());
			} catch (Exception e) {
				e.printStackTrace();
				json.setSuccess(false);
			}
		}
		else{
			json.setSuccess(false);
			json.setMsg("数据异常");
		}
		return json;
	}
	
	
	
	
	

	
	
	/**
	 * 查看公告的具体信息
	 * @return
	 */
	@RequestMapping(value ="/noticeListView")
	public String listInfoView(Long id,Model model){
		//model.addAttribute("notice",noticeService.getById(id));
		return "notice/noticeShow";
	}
	
	/**
	 * 列表页面--用于首页显示的
	 * @return
	 */
	@RequestMapping(value ="/noticeListInfo")
	public String listView(Model model){
		UserBean user=GlobalContext.getCurrentUser();
//		JSONObject  task= manualService.taskCount(user);
//        model.addAttribute("task",task);
		return "notice/noticeListInfo";
	}
	
	/**
	 * 用户查看公告信息列表界面（前台用户查看）
	 * @return
	 */
	@RequestMapping("/noticeForUserView")
	public ModelAndView noticeUserViewModel(){
		return new ModelAndView("/notice/userView");
	}

	/*
	 * 返回：信息列表数据(用户查看公告界面的数据来源)
	 */
//	@RequestMapping("/dateDatagrid")
//	@ResponseBody
//	public DataGridJson Data(DataGridBean dg,TJyNotice no){
//		//return noticeService.noticeGridJson(dg, no);
//	}
	//流程信息
	@RequestMapping(value = "/showHistory")
	public String showHistory(Model model, String taskId,
			HttpServletResponse response) {
		//根据流程id查找流程信息
		//List<TaskHisNodeInfo> taskHisNodeInfos=inspectionService.findTaskHisByBusinessKey(taskId);
		//model.addAttribute("taskHisNodeInfos",taskHisNodeInfos);
		return "insp/showHistory";
	}
	
	
	
	
	/*
	 * 修改  基本信息 跳转
	 */
	@RequestMapping(value="/toAdd",method={RequestMethod.GET} )
	public String toEdit(Long id,String ct,Model model) {
		//所有的部门
		//model.addAttribute("departs", staffInfoService.searchAllDept((long) 0));
		//所有的单位
		// model.addAttribute("units", staffInfoService.searchAllUnit("jy"));
		//地区代码
		//model.addAttribute("addcodes", staffInfoService.searchAlladdCode());
		//民簇
		//model.addAttribute("nations", staffInfoService.searchAllNation());
		//学历
		//model.addAttribute("educations", staffInfoService.searchAllEducation());
		//人员类型
		//model.addAttribute("userTypes",staffInfoService.executeUtil("code", "name", "T_GB_6270"));
		//岗位
		//model.addAttribute("station", staffInfoService.executeUtil("code", "name", "T_GB_6286"));
		//model.addAttribute("types", staffInfoService.executeUtil("code", "name", "T_GB_6237"));
		
		
		
//		try {
//			TJyStaffInfo staff=staffInfoService.getById(id);
//			model.addAttribute("ct",URLDecoder.decode(ct,"UTF-8"));
//			if(staff.getId()!=null){
//			System.out.println(staff.getId());}
//			model.addAttribute("staffInfo", staff);
//		} catch (Exception e) {
//			return "error/lift500";
//		}
			
		
		
		return "staffInfo/jy/MyAdd";
	}
	 
	 
	
//	/**
//	 * 修改  人员信息
//	 */
//	@RequestMapping(value="/update")
//	@ResponseBody
//	public Json update(TJyStaffInfo staffInfo){
//		Json j=new Json();
//		
//		//删除原来的图片  得到原来的路径
//		String path = GlobalContext.getRequest().getSession().getServletContext().getRealPath("");
//	    TJyStaffInfo tjs=staffInfoService.getById(staffInfo.getId());
////	    if(tjs.getPhotoUpname()!=null&&!tjs.getPhotoUpname().equals(""))
////	    {
////	    	new File(path+"\\static\\uploadFiles\\"+tjs.getPhotoUrl()+tjs.getPhotoUpname()).delete(); 
////		}
//	    if(tjs.getGy()!=null&&!tjs.getGy().equals(""))
//	    {
//	    	new File(path+"\\static\\uploadFiles\\"+tjs.getGy()).delete(); 
//		}
//	    //修改sys_user2 中人员的部门
//	    User user=userService.getById(staffInfo.getId());
//	    if(user!=null)
//	    {
//	    	user.getOrganization().setId(staffInfo.getOrganizationId());
//	    	userService.update(user);
//	    }
//	    
//		staffInfoService.updateStaff(staffInfo);
//		j.setSuccess(true);
//		return j;
//	}
	
	/**
	 * 查询电梯设备类别
	 * @param id
	 * @param session
	 * @return List<TreeNodeBean>
	 */
	
//	@RequestMapping(value = "/treeDH")
//	@ResponseBody
//	public List<TreeNodeBean> findDhTree() {
//
//		return this.jyPersonaptitudeServier.treeIdDH();
//	}
	/**
	 * 返回类别
	 * @return List<Map<String,String>>
	 */
//	@RequestMapping("/typeNature")
//	@ResponseBody
//	public List<Map<String, Object>> typeNature(){
//		List<Map<String, Object>> resuList= staffInfoService.testClasses("T_GB_6272");
//		return resuList; 
//	}
	
	/**
	 * 下载图片
	 */
	@RequestMapping(value="/downloadPicture" ,method={RequestMethod.GET})
	public  void downloadPicture(String[] urls,HttpServletRequest request,HttpServletResponse response) {
		Json json = new Json();
		
		for(String imgUrl:urls){
			 try {
				 FileInputStream in =null;
					java.io.OutputStream out=null;
						//得到要下载的文件名
					imgUrl = new String(imgUrl.getBytes("iso8859-1"),"UTF-8");
			         //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
			         String path=request.getSession().getServletContext().getRealPath("/static/uploadFiles/personaptitude/"); 
			         //通过文件名找出文件的所在目录
			         //得到要下载的文件
			         File file = new File(path + "\\" + imgUrl);
			         //如果文件不存在
			         if(!file.exists()){
			             //request.setAttribute("message", "您要下载的资源已被删除！！");
						 //request.getRequestDispatcher("/message.jsp").forward(request, response);
			        	 json.setSuccess(false); 
			         }
			         //处理文件名
			         String realname = imgUrl.substring(imgUrl.indexOf("_")+1);
			         //设置响应头，控制浏览器下载该文件
			         response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
			         //读取要下载的文件，保存到文件输入流
			         in = new FileInputStream(path + "\\" + imgUrl);
			         //创建输出流
			         out = response.getOutputStream();
			         //创建缓冲区
			         byte buffer[] = new byte[1024];
			         int len = 0;
			         //循环将输入流中的内容读取到缓冲区当中
			         while((len=in.read(buffer))>0){
			             //输出缓冲区的内容到浏览器，实现文件下载
			             out.write(buffer, 0, len);
			         }
			         //关闭文件输入流
			         if(in!=null){
			         in.close();}
			         //关闭输出流
			         if(in!=null){
			         out.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json.setSuccess(true);
		//return json;
	}
	
	
	/**
	 * <p>用于文件上传照片</p>
	 * @param apkFile
	 * @param request
	 * @param response
	 * @return Json
	 */
//	@SuppressWarnings("finally")
//	@RequestMapping(value="/toUpFileP",method={RequestMethod.POST})
//	@ResponseBody
//	public Json toUpFileP( @RequestParam(value = "apkFile") MultipartFile[] apkFile,HttpServletRequest request){
//		Json json=new Json();
//		TJyPersonaptitude jyPersonaptitude=new TJyPersonaptitude();
//		boolean flag = false;
//		String msg="";
//		try {
//			jyPersonaptitude=jyPersonaptitudeServier.upFile(apkFile, request );
//			if(jyPersonaptitude==null){
//				msg="上传失败！";
//				flag=false;
//			}else{
//				flag=true;
//			}
//		} catch (Exception e) {
//			msg="文件过大";
//			flag=false;
//		}finally{
//			json.setMsg(msg);
//			json.setObj(jyPersonaptitude);	
//			json.setSuccess(flag);
//			return json;
//		}
//	}
	/**
	 * 保存chizhen记录
	 */
//	@RequestMapping(value = "/savePa")
//	@ResponseBody
//	public Json saveBorrowRecord(@RequestBody List<TJyPersonaptitude> personaptitude){
//		Json j=new Json();
//		j=jyPersonaptitudeServier.savePd(personaptitude);
//		j.setSuccess(true);
//		return j;
//	}
	
	/**
	 * 删除
	 */
//	@RequestMapping(value="/delPersonaptitudeByIds" ,method={RequestMethod.POST})
//	public @ResponseBody Json delPersonaptitudeByIds(@RequestBody String[] ids,HttpServletRequest request) {
//		Json json = new Json();
//		jyPersonaptitudeServier.deleteById(ids);
//		json.setSuccess(true);
//		return json;
//	}
	
	/*
	 * 返回 基本信息列表数据
	 */
//	@RequestMapping("/StaffDatagrid")
//	@ResponseBody
//	public DataGridJson userData(DataGridBean dg,TJyPersonaptitude jyPersonAptitude, Long card){
//		Map<String,Date> timeParams=new HashMap<String, Date>();
//		//jyPersonAptitude.setCertificateNumber(card);
//		if(card!=null)
//		{jyPersonAptitude.setStaffId(card);}
//		return jyPersonaptitudeServier.personAptitudeData(dg, jyPersonAptitude,timeParams, "");
//	}
	/**
	 * 返回类别
	 * @return List<Map<String,String>>
	 */
//	@RequestMapping("/type")
//	@ResponseBody
//	public List<Map<String, Object>> test(){
//		List<Map<String, Object>> resuList=staffInfoService.testClasses("T_GB_6237");
//		return resuList;
//	}
	
	/**
	 * 查找部门
	 */
//	@RequestMapping(value="/searchDept" ,method={RequestMethod.POST})
//	@ResponseBody
//	public Json searchDept(String id,HttpServletRequest request) {
//		Json json = new Json();
//		List<TJyDepart> depts=staffInfoService.searchAllDept(Long.valueOf(id));
//		json.setSuccess(true);
//		json.setObj(depts);	
//		return json;
//	}
	

}
