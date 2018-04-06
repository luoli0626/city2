package com.wan.sys.util;

import java.util.HashMap;
import java.util.Map;

public class PersonTypeUtil {
	
	public static final Map<String,String> map(){
		Map<String,String> mapType=new HashMap<String, String>();
		mapType.put("1", "男");
		mapType.put("2", "女");
		mapType.put("3", "老年人");
		mapType.put("4", "儿童");
		mapType.put("5", "孕产妇");
		mapType.put("6", "高血压");
		mapType.put("7", "2型糖尿病");
		mapType.put("8", "重型精神病");
		return mapType;
		
	}

}
