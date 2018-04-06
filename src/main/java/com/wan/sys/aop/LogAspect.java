package com.wan.sys.aop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.common.GlobalContext;
import com.wan.sys.entity.Log;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.user.ILogService;
import com.wan.sys.util.IpUtil;
import com.wan.sys.util.StringUtil;
/**
 * 
 * 文件名称： 日志记录AOP类
 * 内容摘要： 记录用户操作日志信息
 * 创建人 　： lanxiaowei
 * 创建日期： 2015-4-15
 * 版本号　 ： v1.0.0
 * 公司　　  : 亚德科技股份有限公司
 * 版权所有： (C)2001-2015     
 * 修改记录1  
 * 修改日期：
 * 版本号 　：
 * 修改人 　：
 * 修改内容：  
 *
 */
@Component
@Aspect
public class LogAspect {
	private static final Logger logger = Logger.getLogger(LogAspect.class);
	static final Map<String, String> METHODNAMES=new HashMap<String, String>();
	static{
		METHODNAMES.put("add", "新增");
		METHODNAMES.put("insert", "新增");
		METHODNAMES.put("del", "删除");
		METHODNAMES.put("delete", "删除");
		METHODNAMES.put("update", "修改");
		METHODNAMES.put("edit", "修改");
	
		
	}
	@Value("#{logAopSetting}")
	private Properties aopProperties;
	@Autowired
	private ILogService logService;
	@Pointcut("execution(public * com.wan.sys.controller..add*(..))||" +
			"execution(public * com.wan.sys.controller..insert*(..))||" +
			"execution(public * com.wan.sys.controller..update*(..))||" +
			"execution(public * com.wan.sys.controller..edit*(..))||" +
			"execution(public * com.wan.sys.controller.main.MainController.login(..))||" +
			"execution(public * com.wan.sys.controller.main.MainController.logout(..))||" +
			"execution(public * com.wan.sys.controller..del*(..))||" +
			"execution(public * com.wan.user..*.controller..insert*(..))||" +
			"execution(public * com.wan.user..*.controller..add*(..))||" +
			"execution(public * com.wan.user..*.controller..edit*(..))||" +
			"execution(public * com.wan.user..*.controller..update*(..))||" +
			"execution(public * com.wan.user..*.controller..del*(..))")
			
			
			
			
			
	public void pointCut(){}
	/**
	 * 
	 * @Description 日志记录
	 * @param jp void
	 */
	@AfterReturning("pointCut()")
	public void saveLog(JoinPoint jp){
		
		String businessName=jp.getSignature().getDeclaringTypeName();
		businessName=businessName.substring(businessName.lastIndexOf(".")+1,businessName.length());
		UserBean user=GlobalContext.getCurrentUser();
		String methdName=jp.getSignature().getName();
		
		
		Object [] params=jp.getArgs();
		BaseEntity be=null;
		for(int i=0;i<params.length;i++){
			if(params[i] instanceof BaseEntity){
				be=(BaseEntity) params[i];
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String otime=sdf.format(new Date());
		//记录登录日志
		if("login".equals(methdName)){
			Log log=new Log();
			if(user!=null){
				log.setUserId(user.getId()+"");
				log.setUserName(user.getLoginAcct());
				String note="登录系统成功";
				log.setIp(IpUtil.getIpAddr(GlobalContext.getRequest()));
				log.setNote(note);
				log.setUseTime(otime);
			}else{
				String note="登录系统失败";
				log.setIp(IpUtil.getIpAddr(GlobalContext.getRequest()));
				log.setNote(note);
				log.setUseTime(otime);
				log.setUserId(be.getId()+"");
				log.setUserName(((UserBean)be).getLoginAcct());
			}
			logService.saveLog(log);
		}
		//记录登出系统日志
		if("logout".equals(methdName)){
			Log log=new Log();
			UserBean u=(UserBean)be;
			log.setUserId(u.getId()+"");
			log.setUserName(u.getLoginAcct());
			String note="退出系统";
			log.setIp(IpUtil.getIpAddr(GlobalContext.getRequest()));
			log.setNote(note);
			log.setUseTime(otime);
			logService.saveLog(log);
		}
		//操作日志
		String bKey=getBusinessLogKey(businessName);
		String mKey=getMethodLogKey(methdName);
		if(StringUtil.isNotBlank(bKey)){
			Log log=new Log();
			log.setUserId(user.getId()+"");
			log.setUserName(user.getLoginAcct());
		
			log.setIp(IpUtil.getIpAddr(GlobalContext.getRequest()));
			String note=" 对 <font color='red'>"+aopProperties.get(bKey)+"</font> 进行了 <font color='red'>"+LogAspect.METHODNAMES.get(mKey)+"</font> 操作";
			if(be!=null){
				note=note+"，主键为："+be.getId();
			}
			if("删除".equals(LogAspect.METHODNAMES.get(mKey))){
				//删除传入的是id数组
				String [] ids=null;
				for(int i=0;i<params.length;i++){
					if(params[i] instanceof String[]){
						ids=(String[])params[i];
					}
				}
				String temp="";
				if(ids!=null){
					for (int i=0;i<ids.length;i++) {
						if(i==0){
							temp=ids[i];
						}else{
							temp=temp+","+ids[i];
						}
					}
					note=note+"，主键为："+temp+" 的"+ids.length+"条记录";
				}
			}
			log.setNote(note);
			log.setUseTime(otime);
			logService.saveLog(log);
		}
	}
	/**
	 * 
	 * @Description 根据方法名获取否需要操作日志key
	 * @param methodName
	 * @return boolean
	 */
	private String getMethodLogKey(String methodName) {
		Map<String, String> map=LogAspect.METHODNAMES;
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			if(methodName!=null&&methodName.contains(entry.getKey().toString())){
				return entry.getKey().toString();
			}
		}
		return "";
	}

	/**
	 * 
	 * @Description 根据业务类名字获取获取业务类日志key
	 * @param controllerName
	 * @return boolean
	 */
	private String getBusinessLogKey(String businessName){
		for ( Map.Entry<Object, Object> entry : aopProperties.entrySet()) {
			if(entry.getKey().toString().equals(businessName)){
				return entry.getKey().toString();
			}
		}
		return "";
	}
}
