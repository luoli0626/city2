package com.wan.sys.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliMessageSend {  
	      
	    // 产品名称:云通信短信API产品,开发者无需替换  
	    private static final String product = "Dysmsapi";  
	    // 产品域名,开发者无需替换  
	    private static final String domain = "dysmsapi.aliyuncs.com";  
	  
	    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)  
//	    private static String mobile = "电话";  
	    private static String accessKeyId = "LTAIFNXJTWg6Ui0o";  
	    private static String accessKeySecret = "muZKKoae40L5kOYZjdBYkV1VOmB50w";  
	    private static String signName = "石家庄市桥西区城市管理局";     //签名
	    private static String templeteCode1 = "SMS_133590009";  //短信模版Code(一般都是SMS_********格式)
	    private static String templeteCode2 = "SMS_133590008"; 
	  
	    // 调用短信接口  
	    public static void AliSend(String mobile,String code,String flag) {  
	        try {  
	            sendSms(mobile,code,flag);  
	        } catch (ClientException e) {  
	            System.out.println(e);  
	        }  
	    }  
	  
	    // 发送短信方法  
	    public static SendSmsResponse sendSms(String mobile,String code,String flag) throws ClientException {  
	        // 可自助调整超时时间  
	        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");  
	        System.setProperty("sun.net.client.defaultReadTimeout", "10000");  
	  
	        // 初始化acsClient,暂不支持region化  
	        IClientProfile profile = DefaultProfile.getProfile("cn-hebei", accessKeyId, accessKeySecret);  
	        DefaultProfile.addEndpoint("cn-hebei", "cn-hebei", product, domain);  
	        IAcsClient acsClient = new DefaultAcsClient(profile);  
	  
	        // 组装请求对象-具体描述见控制台-文档部分内容  
	        SendSmsRequest request = new SendSmsRequest();  
	  
	        // 必填:待发送手机号  
	        request.setPhoneNumbers(mobile);  
	        // 必填:短信签名-可在短信控制台中找到  
	        request.setSignName(signName);  
	        // 必填:短信模板-可在短信控制台中找到  
	        if(flag.equals("1")){//注册模版
	        	request.setTemplateCode(templeteCode1);  
	        }else if(flag.equals("2")){
	        	request.setTemplateCode(templeteCode2);  
	        }
	  
	        // 可选:模板中的变量替换JSON串,如模板内容为"尊敬的用户,您的验证码为${code}"时,此处的值为  
	        String jsonParam = "{\"code\":\""+code+"\"}";  
	        request.setTemplateParam(jsonParam);  
	  
	        // hint 此处可能会抛出异常，注意catch  
	        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);  
	  
	        return sendSmsResponse;  
	    }  
	  
	}  
