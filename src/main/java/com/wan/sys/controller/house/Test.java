package com.wan.sys.controller.house;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpException;

import com.wan.sys.util.DateUtil;

public class Test {
	
	public static void main(String[] args) throws HttpException, IOException {
		
		//login();
		//deviceType();
		//deviceInfo();
		//patrolProject();
		//getTask();
		//addPoint();
		//completeTask();
		//getNoPoint();
		//getStatistics();
		//readWatch();
		//getParameter();
		//getElectricData();
		//getElectricMonthData();
		//智慧接房
//		getUserToken();
//		setProject();
//		getTokenAndUser();
//		checkUser();
//		saveUserInfo();
//		handleHouse();
//		takeHouse();
//		resetLineNo();
//		activeDevice();
		findListData();
//		bindingDevice();
//		findState();
//		getHouseInfo();
//		findUserList();
//		findNowNo();
//		addUserInfo();
		//后台
//		findProjectList();
		//Date date = new Date();
        Calendar calendar = Calendar.getInstance();
       // calendar.setTime(date); // 设置为当前时间
        int year= calendar.YEAR;
        System.out.println(calendar.get(Calendar.YEAR)-1);//得到年
        System.out.println("上月数据："+year);
        System.out.println("上月数据："+year+(year-1));
        String test="hsdjk,jfdj,";
        String[] test1=test.split(",");
        System.out.println(test1.length);
		
		
		
//	     Calendar calendar = Calendar.getInstance();  
//	        int year = calendar.YEAR;  
//	        int month = calendar.MONTH;  
//	        int date = 1;  
//	  
//	        calendar.set(year, month, date);  
//	  
//	        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
//	        System.out.println("Max Day: " + maxDay);  
//	  
//	        int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);  
//	        System.out.println("Min Day: " + minDay);  
//	  
//	        for (int i = minDay; i <= maxDay; i++) {  
//	            calendar.set(year, month, i);  
//	            System.out.println("Day: " + calendar.getTime().toLocaleString());  
//	        }  
//	        
	        
	        

	        
	        
	        

	        
	        
		
		
	}
	
	
	public static void login() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getToken");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[2];
		pairs[0] = new NameValuePair("openId", "1505895227664");
		pairs[1] = new NameValuePair("projectId", "45");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	}
	public static void getNoPoint() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getNoPoint");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[2];
		pairs[0] = new NameValuePair("token", "b0ca0113ac3649bfa06b94489a7e5f65");
		pairs[1] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	}
	
	
	public static void getStatistics() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getStatistics");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[2];
		pairs[0] = new NameValuePair("token", "b0ca0113ac3649bfa06b94489a7e5f65");
		pairs[1] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	}
	
	public static void deviceType() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getDeviceType");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		
		
		
		
		NameValuePair[] pairs = new NameValuePair[4];
		pairs[0] = new NameValuePair("token", "71f59e8acaa8499bac643e2556338d28");
		pairs[1] = new NameValuePair("parentId", "1504492099001");
		pairs[2] = new NameValuePair("typeId", "3");
		pairs[3] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	}
	
	
	public static void deviceInfo() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/deviceInfo");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		
		
		
		
		NameValuePair[] pairs = new NameValuePair[3];
		pairs[0] = new NameValuePair("token", "71f59e8acaa8499bac643e2556338d28");
		pairs[1] = new NameValuePair("deviceId", "1504862238161");
		pairs[2] = new NameValuePair("cardId", "");
		pairs[2] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	}
	
	
	public static void patrolProject() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/patrolProject");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		
		NameValuePair[] pairs = new NameValuePair[3];
		pairs[0] = new NameValuePair("token", "71f59e8acaa8499bac643e2556338d28");
		pairs[1] = new NameValuePair("typeId", "1504746839822");
		pairs[2] = new NameValuePair("projectId", "208");
		method.setRequestBody(pairs);		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	}

	public static void getTask() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getTask");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[2];
		pairs[0] = new NameValuePair("token", "b0ca0113ac3649bfa06b94489a7e5f65");
		pairs[1] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	    
	    

	    
	}
	
	
	public static void addPoint() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/addPoint");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		String ss="[{\"cardId\": \"046281a61\",\"deviceId\": \"1504862238161\"},{\"cardId\": \"046281a6\",\"deviceId\": \"1505371951584\"}]";
		NameValuePair[] pairs = new NameValuePair[3];
		pairs[0] = new NameValuePair("token", "b0ca0113ac3649bfa06b94489a7e5f65");
		pairs[1] = new NameValuePair("projectId", "128");
		pairs[2] = new NameValuePair("pointList", ss);
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());

	    
	}
	public static void completeTask() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/completeTask");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		String ss="[{\"taskId\":\"12\",\"completeTime\":\"2017-10-01 12:12:03\",\"listData\": [{\"pointId\": \"1\",\"inspecTime\": \"2017-10-01 12:12:03\",\"result\": 1,\"remark\":\"水电费水电费\"},{\"pointId\": \"1505371951585\",\"inspecTime\": \"2017-08-01 12:12:03\",\"result\":1,\"remark\":\"正常\"}]}]";
		NameValuePair[] pairs = new NameValuePair[3];
		pairs[0] = new NameValuePair("token", "b0ca0113ac3649bfa06b94489a7e5f65");
		pairs[1] = new NameValuePair("projectId", "128");
		pairs[2] = new NameValuePair("resultList", ss);
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());

	    
	}
	
	public static void readWatch() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/readWatch");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		
		
		
		
		NameValuePair[] pairs = new NameValuePair[4];
		pairs[0] = new NameValuePair("token", "71f59e8acaa8499bac643e2556338d28");
		pairs[1] = new NameValuePair("parentId", "1504492099001");
		pairs[2] = new NameValuePair("typeId", "3");
		pairs[3] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());

	    
	}
	public static void getParameter() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getParameter");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		
		
		
		
		NameValuePair[] pairs = new NameValuePair[4];
		pairs[0] = new NameValuePair("token", "71f59e8acaa8499bac643e2556338d28");
		pairs[1] = new NameValuePair("parentId", "1504492099001");
		pairs[2] = new NameValuePair("deviceId", "1509420258004");
		pairs[3] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());

	    
	}
	
	public static void getElectricData() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getElectricData");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[4];
		pairs[0] = new NameValuePair("token", "71f59e8acaa8499bac643e2556338d28");
		pairs[1] = new NameValuePair("parentId", "1504492099001");
		pairs[2] = new NameValuePair("deviceId", "1509420258004");
		pairs[3] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());

	    
	}
	
	public static void getElectricMonthData() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8089/DeviceMvn/app/getElectricMonthData");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[4];
		pairs[0] = new NameValuePair("token", "71f59e8acaa8499bac643e2556338d28");
		pairs[1] = new NameValuePair("parentId", "1504492099001");
		pairs[2] = new NameValuePair("deviceId", "1509420258004");
		pairs[3] = new NameValuePair("projectId", "128");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());

	    
	}
	public static void getUserToken() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/getUserToken");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[1];
		pairs[0] = new NameValuePair("deviceSerial", "123123");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	public static void setProject() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/setProject");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[2];
		pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[1] = new NameValuePair("token", "4297f44b13955235245b2497399d7a9328");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	public static void checkUser() throws HttpException, IOException{
		HttpClient client = new HttpClient();
//	    PostMethod method = new PostMethod("http://dev-oa.tq-service.com/jiefang/house/checkUser");
		PostMethod method = new PostMethod("http://localhost:8080/device/house/checkUser");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		NameValuePair[] pairs = new NameValuePair[5];
		pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[1] = new NameValuePair("token", "4297f44b13955235245b2497399d7a9372");
		pairs[2] = new NameValuePair("projectId", "209");
		pairs[3] = new NameValuePair("userId", "500221199211303418");
		pairs[4] = new NameValuePair("userName", "霍铭豪");
		method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	public static void saveUserInfo() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/saveUserInfo");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[6];
		File file1 = new File("C:\\1.jpg");
		File file2 = new File("C:\\2.jpg");
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "4297f44b13955235245b2497399d7a93");
		pairs[1] = new StringPart("projectId", "741d88ba-9b16-44b7-970a-6efb32539969");
		pairs[2] = new StringPart("userId", "412724198009290969");
		pairs[3] = new StringPart("username", "");
		pairs[4] = new FilePart("file1",file1);
		pairs[5] = new FilePart("file2",file2);

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void getTokenAndUser() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/getTokenAndUser");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[2];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("openId", "1511850611307");
		pairs[1] = new StringPart("projectId", "209");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	public static void handleHouse() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/handleHouse");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[3];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "27b11d5e4ae94b4eb3e9ec4b4c89af59");
//		pairs[1] = new StringPart("userId", "500221199211303418");
		pairs[1] = new StringPart("lineNo", "4");
		pairs[2] = new StringPart("projectId", "209");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	public static void takeHouse() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/takeHouse");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[4];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "e1b9e684a35b4aecbbdc9d3293abe2c3");
		pairs[1] = new StringPart("userId", "500221199211303418");
		pairs[2] = new StringPart("lineNo", "2");
		pairs[3] = new StringPart("projectId", "209");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void resetLineNo() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/resetLineNo");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[2];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "4297f44b13955235245b2497399d7a93");
		pairs[1] = new StringPart("projectId", "741d88ba-9b16-44b7-970a-6efb32539969");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));
 
		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void activeDevice() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/activeDevice");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[3];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("deviceName", "143245");
		pairs[1] = new StringPart("deviceSerial", "HYGBGKJKKUUFDSD");
		pairs[2] = new StringPart("token", "c65f0cfd21b045449216c46264ae5950");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void findListData() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/findListData");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[2];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "9e5a99b5e689405faab285f8ec30fc4a");
		pairs[1] = new StringPart("userId", "1511744336702");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	
	public static void bindingDevice() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/bindingDevice");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[3];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "4297f44b13955235245b2497399d7a9328");
		pairs[1] = new StringPart("projectId", "10");
		pairs[2] = new StringPart("deviceSerial", "DFHGRDSTJHFGB344TDV");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void findState() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/findState");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[3];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "27b11d5e4ae94b4eb3e9ec4b4c89af59");
		pairs[1] = new StringPart("projectId", "209");
		pairs[2] = new StringPart("lineNo", "4");

		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void getHouseInfo() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/getHouseInfo");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[4];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "6ee0cfc225ec45589d76969a325998da");
		pairs[1] = new StringPart("projectId", "209");
		pairs[2] = new StringPart("lineNo", "15");
		pairs[3] = new StringPart("userId", "610629198904124256");
		
		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void findProjectList() throws HttpException, IOException{
		HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod("http://localhost:8080/device/houseftl/findProjectList");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[0];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		
		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	public static void findUserList() throws HttpException, IOException{
		HttpClient client = new HttpClient();
//	    PostMethod method = new PostMethod("http://10.15.208.180:8080/Cruiselch4/APIInterface/getUserList");
	    PostMethod method = new PostMethod("http://api-development.tq-service.com/oa/APIInterface/getUserList");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[3];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("appSecret", "DF6A3B7483001979464F6F5D7A1B5D11");
		pairs[1] = new StringPart("projectId", "209");
//		pairs[1] = new StringPart("projectId", "38e70023-9bb1-4874-855f-6f46ac61fff8");
		pairs[2] = new StringPart("appKey", "qF7jQxnUJRR9PtBk2Uig");
		
		
		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	
	public static void findNowNo() throws HttpException, IOException{
		HttpClient client = new HttpClient();
//	    PostMethod method = new PostMethod("http://10.15.208.180:8080/Cruiselch4/APIInterface/getUserList");
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/findNowNo");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[2];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
//		pairs[0] = new StringPart("token", "7af8ad1f56a440e3a624a72a0d154ff7");
		pairs[0] = new StringPart("projectId", "209");
		pairs[1] = new StringPart("lineNo", "15");
		
		
		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
	
	
	public static void addUserInfo() throws HttpException, IOException{
		HttpClient client = new HttpClient();
//	    PostMethod method = new PostMethod("http://10.15.208.180:8080/Cruiselch4/APIInterface/getUserList");
	    PostMethod method = new PostMethod("http://localhost:8080/device/house/addUserInfo");
		//PostMethod method = new PostMethod("http://doormaster.me:8059/dmserver/getUserEKey?{\"devKey\":\"5bd963b99dab580b50bc0ffb0b9ed5b1\"}");
		// 参数中文乱码问题  http://doormaster.me:8059/dmserver/getUserEKey 
		HttpMethodParams mparams = method.getParams();  
		mparams.setContentCharset("UTF-8");
		// 设置参数
		//String ss="{\"listData\": [{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",\"faultDesc\": 24},{\"pointId\": \"046281a6\",\"punchTime\": \"2017-08-01 12:12:03\",faultDesc: 3}]}";
		Part[] pairs = new Part[4];
		//pairs[0] = new NameValuePair("deviceSerial", "123123");
		pairs[0] = new StringPart("token", "6ee0cfc225ec45589d76969a325998da");
		pairs[1] = new StringPart("projectId", "209");
		pairs[2] = new StringPart("lineNo", "14");
		pairs[3] = new StringPart("userId", "610629198904124256");
		
		
		method.setRequestEntity(new MultipartRequestEntity(pairs, method.getParams()));

		//method.setRequestBody(pairs);
		
	    client.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString()); 
	}
}
