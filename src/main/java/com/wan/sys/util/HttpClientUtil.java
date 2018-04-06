package com.wan.sys.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class HttpClientUtil {
	
	
	 public static HttpClient httpclient = new HttpClient();
	 
	 public static void writeStrToFile(String xml,String url){  
	        try {    
	            FileOutputStream fos = new FileOutputStream(new File(url));  
	            Writer os = new OutputStreamWriter(fos, "GBK");  
	            os.write(xml);  
	            os.flush();  
	            fos.close();  
	        } catch (FileNotFoundException e) {    
	            // TODO Auto-generated catch block     
	            e.printStackTrace();    
	        } catch (IOException e) {    
	            // TODO Auto-generated catch block     
	            e.printStackTrace();    
	        }    
	} 
	 
	 /**
	  * 易软获取数据接口
	  * @param url
	  * @return
	 * @throws IOException 
	 * @throws HttpException 
	  */
	 public static String eyGet(String url) throws HttpException, IOException {
		  HttpClient httpClient = new HttpClient(); 
		  // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略  
          httpClient.getParams().setCookiePolicy(  
                  CookiePolicy.BROWSER_COMPATIBILITY); 
          httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 60*300); //链接超时60秒
          httpClient.getHttpConnectionManager().getParams().setSoTimeout(1000 * 60*300); //读取超时60秒
          GetMethod getMethod = new GetMethod(url); 
          httpClient.executeMethod(getMethod);  
          // 打印出返回数据，检验一下是否成功  
         InputStream inputStream = getMethod.getResponseBodyAsStream();
          return string2Json(Inputstr2Str_ByteArrayOutputStream(inputStream,""));
          //return getMethod.getResponseBodyAsString();
	 }
	 /** 
	     * JSON字符串特殊字符处理，比如：“\A1;1300” 
	     * @param s 
	     * @return String 
	     */  
	    public static String string2Json(String s) {        
	        StringBuffer sb = new StringBuffer();        
	        for (int i=0; i<s.length(); i++) {  
	            char c = s.charAt(i);    
	             switch (c){  
	             case '\\':        
	                 sb.append("");        
	                 break;        
	             case '/':        
	                 sb.append("");        
	                 break;        
	             case '\b':        
	                 sb.append("\\b");        
	                 break;        
	             case '\f':        
	                 sb.append("");        
	                 break;        
	             case '\n':        
	                 sb.append("");        
	                 break;        
	             case '\r':        
	                 sb.append("");        
	                 break;        
	             case '\t':        
	                 sb.append("");        
	                 break;        
	             default:        
	                 sb.append(c);     
	             }  
	         }      
	        return sb.toString();     
	        } 
	 
	 /** 
	    * 利用ByteArrayOutputStream：Inputstream------------>String <功能详细描述> 
	    *  
	    * @param in 
	    * @return 
	    * @see [类、类#方法、类#成员] 
	    */  
	   public static String Inputstr2Str_ByteArrayOutputStream(InputStream in,String encode)  
	   {  
	       ByteArrayOutputStream out = new ByteArrayOutputStream();  
	       byte[] b = new byte[1024];  
	       int len = 0;  
	       try  
	       {  
	           if (encode == null || encode.equals(""))  
	           {  
	               // 默认以utf-8形式  
	               encode = "utf-8";  
	           }  
	           while ((len = in.read(b)) > 0)  
	           {  
	               out.write(b, 0, len);  
	           }  
	           return out.toString(encode);  
	       }  
	       catch (IOException e)  
	       {  
	           e.printStackTrace();  
	       }  
	       return "";  
	   }  
	 public static String responseString(String url,String phone) {
		 String text = "";
		  String loginUrl = url+"login.jsp";  
	        // 需登陆后访问的 Url  
//	        String dataUrl = "http://218.201.38.62:9090/index.jsp";  
	        String dataUrl = url+"user-properties.jsp?username="+phone;  
	  
	        HttpClient httpClient = new HttpClient();  
	  
	        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式  
	        PostMethod postMethod = new PostMethod(loginUrl);  
	  
	        // 设置登陆时要求的信息，用户名和密码  
	        NameValuePair[] data = { new NameValuePair("login", "true"),  
	                new NameValuePair("password", "123456"),new NameValuePair("url", "/index.jsp"),new NameValuePair("username", "admin") };  
	        postMethod.setRequestBody(data);  
	        try {  
	            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略  
	            httpClient.getParams().setCookiePolicy(  
	                    CookiePolicy.BROWSER_COMPATIBILITY);  
	            httpClient.executeMethod(postMethod);  
	            // 获得登陆后的 Cookie  
	            Cookie[] cookies = httpClient.getState().getCookies();  
	            StringBuffer tmpcookies = new StringBuffer();  
	            for (Cookie c : cookies) {  
	                tmpcookies.append(c.toString() + ";");  
	            }  
	            // 进行登陆后的操作1581,1602,1603,1610,1609,1608,1607,1606,1605,1620,1619,1617,1616,1622,1626,1642,1648,1647,1657  
	            GetMethod getMethod = new GetMethod(dataUrl);  
	            // 每次访问需授权的网址时需带上前面的 cookie 作为通行证  
	            getMethod.setRequestHeader("cookie", tmpcookies.toString());  
	            // 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据  
	            // 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外  
//	            postMethod.setRequestHeader("Referer", "http://218.201.38.62:9090/login.jsp?url=%2Findex.jsp");  
//	            postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");  
	            httpClient.executeMethod(getMethod);  
	            // 打印出返回数据，检验一下是否成功  
	            text = getMethod.getResponseBodyAsString();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        
	        return text;
	 }
	
	 public static String httpClientByPostAndParams(String url,Map<String, String> params) throws IOException {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		// 参数中文乱码问题
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		if(params!=null && params.size()>0){
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				list.add(new NameValuePair(key, params.get(key)));
			}
			NameValuePair[] pairs = new NameValuePair[list.size()];
			for (int i = 0; i < list.size(); i++) {
				pairs[i] = list.get(i);
			}
			method.setRequestBody(pairs);
		}
		// 执行请求并返回结果
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}
	 
		/**
		 * 根据URL和参数请求数据
		 * @param url
		 * @param params
		 * @return
		 * @throws HttpException
		 * @throws IOException
		 */
		public static String getResponseBodyAsString(String url, Map<String, Object> params) throws HttpException, IOException {
			HttpClient client = new HttpClient();
	        PostMethod method = new PostMethod(url);
	        // 超时设置
	        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
	        client.getHttpConnectionManager().getParams().setSoTimeout(3000);
			// 参数中文乱码问题
			HttpMethodParams mparams = method.getParams();
			mparams.setContentCharset("UTF-8");
			// 设置参数
			NameValuePair[] pairs = new NameValuePair[params.size()];
			int i = 0;
			for (String key : params.keySet()) {
				pairs[i] = new NameValuePair(key, params.get(key).toString());
				i++;
			}
			// 执行请求获取数据
			method.setRequestBody(pairs);
		    client.executeMethod(method);
		    return method.getResponseBodyAsString();
		}
}
