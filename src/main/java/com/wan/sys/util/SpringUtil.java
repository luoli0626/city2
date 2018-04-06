package com.wan.sys.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring工具类
 * 
 * @author  
 * 
 */
public class SpringUtil {

	private static final ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-hibernate.xml"});

	public static ApplicationContext getApplicationContext() {
		return ac;
	}

	public static Object getBean(String beanName) {
		return ac.getBean(beanName);
	}

}
