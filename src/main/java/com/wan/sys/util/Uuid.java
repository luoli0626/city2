package com.wan.sys.util;

import java.util.Random;
import java.util.UUID;

public class Uuid {
	public static String getUUID(){ 
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
        return uuid;    
    } 
	
	 /**
	  * 根据md5加密
	  * @param keyId
	  * @param applicationName
	  * @return
	  */
	 public static String getKeyScrect(String keyId,String applicationName) {
	  String mw = "key" +applicationName+ keyId ;
	  String keyScrect =Encrypt.md5(mw).toUpperCase();// 得到以后还要用MD5加密。
	  return keyScrect;
	 }
	 
	
	 
	 public static String getKeyId(int length) { //length表示生成字符串的长度
		    String base = "ABCDEFGHJKMNPQRSTUVWXYZ0123456789";  
		    Random random = new Random();  
		    StringBuffer sb = new StringBuffer();  
		    for (int i = 0; i < length; i++) {  
		        int number = random.nextInt(base.length());  
		        sb.append(base.charAt(number));  
		    }  
		    return sb.toString();  
		}  
     
	 public static void main(String[] args) {
		System.out.println(getKeyScrect("1000000458460420","juhds"));
	}
}
