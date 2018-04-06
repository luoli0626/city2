package com.wan.sys.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSON;

/**   
 * 文件名称：字符串工具类 
 * 内容摘要： 
 * 创建人： huangfei
 * 创建日期： 2015年4月29日
 * 版本号： v1.0.0
 * 公  司：亚德科技股份有限公司
 * 版权所有： (C)2001-2015     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 **/ 
public class StringUtil {
	
	public static int countMatches(String str,String rex){
		return StringUtils.countMatches(str, rex);
	}

	public static String uncapitalise(String str){
		return StringUtils.uncapitalize(str);
	}
	
	public static String defaultString(String str){
		return StringUtils.defaultString(str);
	}
	
	public static String defaultIfEmpty(String str,String defaultStr){
		return StringUtils.defaultIfEmpty(str,defaultStr);
	}
	
	public static boolean isBlank(String str){
		return StringUtils.isBlank(str);
	}
	
	public static boolean isNotBlank(String str){
		return StringUtils.isNotBlank(str);
	}
	
	public static boolean isEmpty(String str){
		return StringUtils.isEmpty(str);
	}
	
	public static boolean isNotEmpty(String str){
		return StringUtils.isNotEmpty(str);
	}
	
	public static String leftPad(String str,int size,String padStr){
		return StringUtils.leftPad(str, size, padStr);
	}
	//only for web jsp use
	public static String toHtml(String str){
		return defaultString(StringEscapeUtils.escapeHtml(str));
	}
	
	public static String transferredXml(String strSource, String strFrom, String strTo) {
		if (strSource == null) return null;
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}
	
	public static String abbreviate(String str,int maxWidth){
		return StringUtils.abbreviate(str, maxWidth);
	}
	
	public static String toString(Object object) {
    	return ToStringBuilder.reflectionToString(object,ToStringStyle.MULTI_LINE_STYLE);
    }
	
	public static boolean isEquals(String str1,String str2){
		return StringUtils.equals(str1, str2);
	}
	
	public static boolean isNotEquals(String str1,String str2){
		return !StringUtils.equals(str1, str2);
	}
	
	public static String toLowerCase(String str){
		return str.toLowerCase();
	}
	
	public static boolean contains(String str, String searchStr){
		return StringUtils.contains(str, searchStr);
	}
	
	public static String capitalize(String str){
		return StringUtils.capitalize(str);
	}
	
	public static String uncapitalize(String str){
		return StringUtils.uncapitalize(str);
	}
	
	/**  
	  * 替换一个字符串中的某些指定字符  
	  * @param strData String 原始字符串  
	  * @param regex String 要替换的字符串  
	  * @param replacement String 替代字符串  
	  * @return String 替换后的字符串  
	  */  
	 public static String replaceString(String strData, String regex,   
	         String replacement)   
	 {   
	     if (strData == null)   
	     {   
	         return null;   
	     }   
	     int index;   
	     index = strData.indexOf(regex);   
	     String strNew = "";   
	     if (index >= 0)   
	     {   
	         while (index >= 0)   
	         {   
	             strNew += strData.substring(0, index) + replacement;   
	             strData = strData.substring(index + regex.length());   
	             index = strData.indexOf(regex);   
	         }   
	         strNew += strData;   
	         return strNew;   
	     }   
	     return strData;   
	 }   
	 
	 /**  
	  * 替换字符串中特殊字符  
	  */  
	public static String encodeString(String strData)   
	 {   
	     if (strData == null)   
	     {   
	         return "";   
	     }   
	     strData = replaceString(strData, "&", "&amp;");   
	     strData = replaceString(strData, "<", "&lt;");   
	     strData = replaceString(strData, ">", "&gt;");   
	     strData = replaceString(strData, "&apos;", "&apos;");   
	     strData = replaceString(strData, "\"", "&quot;");   
	     return strData;   
	 }   
	  
	 /**  
	  * 还原字符串中特殊字符  
	  */  
	public static String decodeString(String strData)   
	 {   
	     strData = replaceString(strData, "&lt;", "<");   
	     strData = replaceString(strData, "&gt;", ">");   
	     strData = replaceString(strData, "&apos;", "&apos;");   
	     strData = replaceString(strData, "&quot;", "\"");   
	     strData = replaceString(strData, "&amp;", "&");   
	     return strData;   
	 }  
	
	    public static String escapeCharacters(String xmlStr) {  
	    	       StringBuilder sb = new StringBuilder();  
	    	       char[] chs = xmlStr.toCharArray();  
	    	       //System.out.println("filter before=" +chs.length);  
	    	       for(char ch : chs) {  
	    	           if((ch >= 0x00 && ch <= 0x08)  
	    	               || (ch >= 0x0b && ch <= 0x0c)  
	    	               || (ch >= 0x0e && ch <= 0x1f)) {  
	    	               //eat...  
	    	           } else {  
	    	               sb.append(ch);  
	    	           }  
	    	       }  
	    	       //System.out.println("filter after=" +sb.length());  
	    	       return sb.toString();  
	    	   }  

	    public static String buildFieldName(Field field) {
	    	StringBuffer fieldName = new StringBuffer();
			for (int j = 0; j < field.getName().length(); j++) {
				 char c = field.getName().charAt(j);
				 if (Character.isLowerCase(c)) {
					 fieldName.append(c);
				 } else {
					 fieldName.append("_" + Character.toLowerCase(c));
				 }
			}
			return fieldName.toString();
	    }
	    
	    /**
	      *
	      * @param str
	      *         需要过滤的字符串
	      * @return
	      * @Description:过滤数字以外的字符
	      */
	     public static String filterUnNumber(String str) {
	         // 只允数字
	         String regEx = "[^0-9]";
	         Pattern p = Pattern.compile(regEx);
	         Matcher m = p.matcher(str);
	     //替换与模式匹配的所有字符（即非数字的字符将被""替换）
	         return m.replaceAll("").trim();
	 
	    }

	   //字符串替换
	 	public static String replace(String strSource, String strFrom, String strTo) {
	 		if (strSource == null) {
	 			return null;
	 		}
	 		int i = 0;
	 		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
	 			char[] cSrc = strSource.toCharArray();
	 			char[] cTo = strTo.toCharArray();
	 			int len = strFrom.length();
	 			StringBuffer buf = new StringBuffer(cSrc.length);
	 			buf.append(cSrc, 0, i).append(cTo);
	 			i += len;
	 			int j = i;
	 			while ((i = strSource.indexOf(strFrom, i)) > 0) {
	 				buf.append(cSrc, j, i - j).append(cTo);
	 				i += len;
	 				j = i;
	 			}
	 			buf.append(cSrc, j, cSrc.length - j);
	 			return buf.toString();
	 		}
	 		return strSource;
	 	}
	 	
		/**
		 * 生成指定位数的随机码
		 * @param length 随机码的位数
		 * @return 指定位数的随机码
		 */
		public static String getRandomCode(int length)
		{
			//Remove O and I confuse with zero(0) and one(1);
			String[] source={"0","1","2","3","4","5","6","7","8","9",
					"A","B","C","D","E","F","G","H","J","K","L","M","N",
					"P","Q","R","S","T","U","V","W","X","Y","Z"};
			String code="";
			Random rd = new Random();
			for(int i=0;i<length;i++)
			{
				code+=source [rd.nextInt(source.length)];
			}
			return code;

		}
		
		/**
	     * 去除html代码
	     * @param inputString
	     * @return
	     */
	    public static String Html2Text(String inputString) {
	    	
	    	 String htmlStr = inputString; 
		        String textStr ="";
		        java.util.regex.Pattern p_script;
		        java.util.regex.Matcher m_script;
		        java.util.regex.Pattern p_style;
		        java.util.regex.Matcher m_style;
		        java.util.regex.Pattern p_html;
		        java.util.regex.Matcher m_html;          
		        java.util.regex.Pattern p_ba;
		        java.util.regex.Matcher m_ba;
		        java.util.regex.Pattern p_ba2;
		        java.util.regex.Matcher m_ba2;
		        
		        try {
		            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
		            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
		            String regEx_html = "<[^>]+>";
		            String patternStr = "\\s+";
		            String patternStr2 = "&[a-zA-Z0-9]+;";
		            
		            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
		            m_script = p_script.matcher(htmlStr);
		            htmlStr = m_script.replaceAll(""); 

		            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
		            m_style = p_style.matcher(htmlStr);
		            htmlStr = m_style.replaceAll(""); 
		         
		            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
		            m_html = p_html.matcher(htmlStr);
		            htmlStr = m_html.replaceAll(""); 
		            
		            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
		            m_ba = p_ba.matcher(htmlStr);
		            htmlStr = m_ba.replaceAll(""); 
		            
		            p_ba2 = Pattern.compile(patternStr2,Pattern.CASE_INSENSITIVE);
		            m_ba2 = p_ba2.matcher(htmlStr);
		            htmlStr = m_ba2.replaceAll(""); 
		         
		         textStr = htmlStr;
		         
		        }catch(Exception e) {
		                    System.err.println("Html2Text: " + e.getMessage());
		        }          
		        return textStr;
	    	
	    }

	    
	    /**
	     * 过滤字符串中html代码并截取字符串
	     * @param baseString 原始字符串
	     * @param length 截取长度
	     * @param isAdd 是否加省略号 ture：添加，false：不添加
	     * @return 
	     */
	    public static String cutString(String baseString,int length,boolean isAdd){
	    	String newString = Html2Text(baseString);
	    	if(newString!=null && !"".equals(newString)){
	    		newString = newString.trim();
	    			if(isAdd){
	    				newString = subString(newString, length, "......");
		    		} else {
		    			newString = subString(newString, length, "");
		    		}
	    	}
	    	return newString;
	    }
	    /**
	     * 过滤字符串中html代码并截取字符串
	     * @param baseString 原始字符串
	     * @param length 截取长度
	     * @param isAdd 是否加省略号 ture：添加，false：不添加
	     * @param count 省略号个数：3：（3个），其他（6个）
	     * @return
	     */
	    public static String cutString(String baseString,int length,boolean isAdd,int count){
	    	String newString = Html2Text(baseString);
	    	if(newString!=null && !"".equals(newString)){
	    		newString = newString.trim();
	    			if(isAdd){
	    				if(count==3){
	    					newString = subString(newString, length, "...");
	    				} else{
	    					newString = subString(newString, length, "......");
	    				}
		    		} else {
		    			newString = subString(newString, length, "");
		    		}
	    		
	    	}
	    	return newString;
	    }
	    
	    public static String subString(String text, int length, String endWith) {         
	    	        int textLength = text.length();   
	    	        int byteLength = 0;   
	    	        StringBuffer returnStr =  new StringBuffer();   
	    	        for(int i = 0; i<textLength && byteLength < length*2; i++){   
	    	            String str_i = text.substring(i, i+1);    
	    	            if(str_i.getBytes().length == 1){//英文   
	    	                byteLength++;   
	    	            }else{//中文   
	    	                byteLength += 2 ;   
	    	           }   
	    	            returnStr.append(str_i);   
	    	        }   
	    	        try {   
	    	            if(byteLength<text.getBytes("GBK").length){//getBytes("GBK")每个汉字长2，getBytes("UTF-8")每个汉字长度为3   
	    	                returnStr.append(endWith);   
	    	            }   
	    	        } catch (UnsupportedEncodingException e) {   
	    	            e.printStackTrace();   
	    	        }   
	    	        return returnStr.toString();   
	    	    } 
	    
	    /**
		 * Method split.
		 * 通过分隔符（字符串）将字符串分隔为字符串数组
		 * @param strSc 源字符串
		 * @param separator 分隔符（字符串）
		 * @return String[] 字符串数组
		 */
		public static String[] split(String strSc, String separator) {
			String temp = strSc + "";
			String[] ret = null;
			try {
				if (temp != null && separator != null) {
					int lent = countSeparator(temp, separator) + 1;
					ret = new String[lent];
					int endindex = 0;
					int sptlent = separator.length();
					for (int i = 0; i < lent - 1; i++) {
						endindex = temp.indexOf(separator);
						ret[i] = temp.substring(0, endindex);
						temp = temp.substring(endindex + sptlent);
					}
					ret[lent - 1] = temp;
				}
				else if (temp != null && separator == null) {
					ret = new String[1];
					ret[0] = temp;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return ret;
		}
		/**
		 * Method countSeparator.
		 * 计算一个字符串中分隔符（字符串）的个数
		 * @param strSc 源字符串
		 * @param separator 分隔符（字符串）
		 * @return int 分隔符（字符串）的个数
		 */
		public static int countSeparator(String strSc, String separator) {
			int count = 0;
			if (strSc != null && separator != null) {
				int endindex = 0;
				int sptlent = separator.length();
				while ((endindex = strSc.indexOf(separator, endindex)) > -1) {
					endindex += sptlent;
					count++;
				}
			}
			return count;
		}
		public static String getCodeString(String codeStr){
			if(codeStr==null||"".equals(codeStr)){ 
				return "";
			}
			String[] codes = codeStr.split(",");
			 for (int i = 0; i < codes.length; i++) {    
			   for (int j = i + 1; j < codes.length; j++) {   
				   int n1 = Integer.parseInt((String) codes[i]);
				   int n2 = Integer.parseInt((String) codes[j]);
				   if (n1 > n2) {   
					   codes[i]=n2+"";
					   codes[j]=n1+"";
			    	}   
			   }   
			 }   
			String str = "";
			for(int i=0;i< codes.length;i++){
				int code = Integer.parseInt((String) codes[i]); 
				if(i==0){
					str += code;
				}else {
					int precode = Integer.parseInt((String)codes[i-1]);
					if(code-precode==1){
						str+=","+code;
					}else {
						str+="_"+code;
					}
				}
			}
			String resultStr = "";
			String[] result = str.split("_");
			for(int i=0;i<result.length;i++){
				String temp = result[i];
				String[] fe = temp.split(",");
				if(resultStr.length()>0){
					resultStr +=",";
				}
				if(fe.length==1){
					resultStr +=fe[0];
				}else {
					resultStr += fe[0]+"-"+fe[fe.length-1];
				}
			}
			return resultStr;
		}
		/**
		 * 将Null或空字符串替换为指定字符串
		 * @param baseString 原字符串
		 * @param newString 替换字符串
		 * @return
		 */
		public static  String replaceNull(String baseString,String newString){
			if(StringUtil.isEmpty(baseString)||StringUtil.isBlank(baseString)){
				baseString = newString;
			}
			return baseString;
		}
		
		public static String toJsonString(String json){
			return JSON.toJSONString(json);
		}
		/**
		 * 替换字符串，匹配搜索
		 * @param baseString 原字符串
		 * @return String
		 */
		public static String likeSearch(String beReplaced){
			char[] r = beReplaced.toCharArray();
			StringBuilder ret = new StringBuilder();
			ret.append("%");
			for(char t:r){
				ret.append(String.valueOf(t));
				ret.append("%");
			}
			return ret.toString();
		}
		/**
		 * 获取UUID 
		 * @return String
		 */
		public static String getUUID(){
			return UUID.randomUUID().toString().replaceAll("-", "");
		}
}
