/**
 * 
 */
package com.wan.sys.util;

import java.util.ResourceBundle;

/**
 * @author acer
 *
 */
public class ReportResourceUtil {
	private static 	ResourceBundle resourceBundle = ResourceBundle.getBundle("reporturlconfig");
	
	public static String getReportResourceBundle(String reportConfigName){
		String ReportUrl ="";
		try{
			ReportUrl = resourceBundle.getString(reportConfigName);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return ReportUrl;
		}
	}
}
