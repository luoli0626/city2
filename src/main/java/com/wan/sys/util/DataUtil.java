package com.wan.sys.util;


/**
 * 
 * 文件名称: 处理数字格式
 * 内容摘要: 
 * 创 建 人:
 * 创建日期: Dec 11, 2013
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
public class DataUtil {
	public static final String[] ATTRAY=new String[]{"01","02","03","04","05","06","07","08","09"};
	public static final String[] ATTRAY1=new String[]{"01","02","03","04","05","06","07","08","09"};
	
	public static 	String formatNum(String numString){
		String result="";
		if(numString!=null && !"".equals(numString)){
			int num = Integer.parseInt(numString);
			if(num<10){
				for(int i=0;i<ATTRAY.length;i++){
					if(numString.equals(ATTRAY[i])){
						result =ATTRAY1[i];
						break;
					}
				}
			}else{
				result=numString;
			}
			
		}
		return result;
	}
	

}
