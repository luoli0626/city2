package com.wan.sys.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author  
 * 
 */
public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config-development");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	  /**
	   * 通过键获取值
	   * 
	   * @param key
	   * @return
	   */
	  public static final String get(String key) {
	    return bundle.getString(key);
	  }
}
