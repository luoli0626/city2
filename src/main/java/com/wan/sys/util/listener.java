package com.wan.sys.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class listener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent context) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		//侦测jvm环境，并缓存到全局变量中
        String env = System.getProperty("spring.profiles.active");
        if(env!=null&&env.equals("production")) {
        	ConfigUtil.bundle= java.util.ResourceBundle.getBundle("config-production");
        } else if(env!=null&&env .equals("test")){
        	ConfigUtil.bundle= java.util.ResourceBundle.getBundle("config-test");
        }else{
        	ConfigUtil.bundle= java.util.ResourceBundle.getBundle("config-development");
        }
		
	}

}
