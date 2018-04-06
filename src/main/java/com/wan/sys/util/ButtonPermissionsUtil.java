package com.wan.sys.util;

import java.util.ResourceBundle;

public class ButtonPermissionsUtil {
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("buttonPermissions");
	
	/**
	 * 
	 * @param buttonKey获取页面功能权限按钮
	 * @return
	 */
	public static String buttonPermission(String buttonKey){
		return bundle.getString(buttonKey);
	}
}
