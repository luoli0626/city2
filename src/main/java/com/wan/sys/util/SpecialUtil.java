package com.wan.sys.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialUtil {
	
	
	
	
	
	
	public static final boolean  special(String str){
		String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
		Pattern   p   =   Pattern.compile(regEx);      
	    Matcher   m   =   p.matcher(str);      
	    return   m.find();

	}

}
