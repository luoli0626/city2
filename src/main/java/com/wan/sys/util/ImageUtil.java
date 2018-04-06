package com.wan.sys.util;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.wan.sys.util.ResourceUtil;
/**
 * 
 * 文件名称： 图片工具类
 * 内容摘要： 图片工具类
 * 创建人： tanxinwang
 * 创建日期： 2017年2月15日
 * 版本号： v1.0.0
 * 公  司：金科物业服务有限公司
 * 版权所有： (C)2016-2017     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 *
 */
@Transactional
public class ImageUtil {
	 private final static String keyid="E862J3GQ3FR87ZYZ";
	 private final static String keySecret="DD3F83073C602EDD7028C4B2C93888EE";
	 private static String imgaddress="";
	 private static String imgaddress_="";
	 private static String audioaddress="";
	 private static int audiolen=0;
	 
	
	/**
	 * 解决多张上传回传以逗号形式隔开数据
	 * @param id
	 * @param request
	 * @param typeId
	 * @param imageDao
	 * @param userId
	 * @return Map<String,String>
	 */
	public static Map<String,String> uploadImage(HttpServletRequest request,String size) {
		Map<String,String> result=new HashMap<String, String>();
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		String addessUrl="";
		String addessUrl_="";
		int fileSize=Integer.parseInt(size);
		//Iterator<String> iter = multipartRequest.getFileNames();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		for(int i=1;i<fileSize;i++){
		  //while(iter.hasNext()){  
			try {
				CommonsMultipartFile file=null; 
				file= (CommonsMultipartFile) multipartRequest   
				   .getFile("file"+i); 
				// MultipartFile file = multipartRequest.getFile(iter.next());  
				if(null!=file&&null!=file.getOriginalFilename()&&!"".equals(file.getOriginalFilename())){	  
                      //重命名上传后的文件名  
//                      System.out.println(fileName);
                      //定义上传路径  
//                      String path = "D:/" + fileName;  
//                      File localFile = new File(path);  
//                      file.transferTo(localFile);  
					
					Map<String,String> m1=postOssImage(ResourceUtil.get("oss.imgurl"), file, Math.random()+file.getOriginalFilename());
					addessUrl+="|"+m1.get("addess");
					addessUrl_+="|"+m1.get("addess_");
					
				}
				file=null;
			} catch (Exception e) {
				e.printStackTrace();
				result.put("addess", "");
				result.put("addess_", "");
			}
				
			
		}
		addessUrl=addessUrl.replaceFirst("\\|", "");
		addessUrl_=addessUrl_.replaceFirst("\\|", "");
		result.put("addess", addessUrl);
		result.put("addess_", addessUrl_);
		return result;
		   
	}
	
	
	
	

	/**
	 * 调用oss公共方法
	 * @param ossUrl
	 * @param request
	 * @param isImg
	 * @param fileName
	 * @return Map<String,String>
	 */
	public static Map<String,String> postOssImage(String ossUrl,MultipartFile file,String fileName){
		
		Map<String,String> result=new HashMap<String, String>();
		HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(ossUrl);
        result.put("code", "0");
        String imgurl="";
			try {
				
				InputStream is=null;
					is=file.getInputStream();//输入流
					Long a=file.getSize();
					if(a.longValue()>new Long(3145728L).longValue()){
						result.put("code", "2");
						return result;
					}
				  	BufferedImage sourceImg;
					try {
						sourceImg = ImageIO.read(is);
						int width = sourceImg.getWidth();
						int height = sourceImg.getHeight();
						int n_width =0;
						int n_height =0;
						if(width>height){
							imgurl="?x-oss-process=image/resize,h_100,w_100,m_fixed";
						}else{
							imgurl="?x-oss-process=image/resize,h_100,w_100,m_fill,limit_0";
						}
						if(width > 700)
						{
							 n_width = 700;
							 n_height = height*700/width;
						}else{
							n_width = width;
							 n_height = height;
						}
						sourceImg=ImageUtil.resize(n_width,n_height,sourceImg);
						ByteArrayOutputStream os = new ByteArrayOutputStream();  
						ImageIO.write(sourceImg, "gif", os);  
						is = new ByteArrayInputStream(os.toByteArray());
											
					} catch (IOException e1) {
						e1.printStackTrace();
					}		
				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				int len = 0;  
				byte[] b = new byte[1024];  
				while ((len = is.read(b, 0, b.length)) != -1) {  
				    baos.write(b, 0, len);  
				}  
				int i=0;//s
				byte[] buffer =  baos.toByteArray();  			
				Part[] parts = {new StringPart("applicationKeyId", keyid),new StringPart("applicationKeyKeySecret", keySecret),new FilePart("files", new ByteArrayPartSource(fileName, buffer)) };  
				MultipartRequestEntity mre = new MultipartRequestEntity(parts, method.getParams());  
				method.setRequestEntity(mre);
				int statusCode = client.executeMethod(method);	
				if (statusCode == HttpStatus.SC_OK) {  
					String resultjson = method.getResponseBodyAsString();
					JSONObject resJson = JSONObject.fromObject(resultjson);   
					JSONArray objs=resJson.getJSONArray("obj");
					if(objs!=null && objs.size()>0){//imgaddress="";audioaddress="";imgaddress_="";
						 JSONObject jsonObject2 = (JSONObject)objs.opt(0); 
						 String url=jsonObject2.getString("url");
								result.put("addess",url);
								result.put("addess_",url+imgurl);
						 		//result.put("addess", imgaddress=="" ? url:imgaddress);
								//result.put("addess_", imgaddress_==""? url+imgurl : imgaddress_);
								//imgaddress=url;
								//imgaddress_=url+imgurl;
					}else{
//							result.put("addess", imgaddress=="" ? "":imgaddress);
//							result.put("addess_", imgaddress_==""? "" : imgaddress_);
							result.put("addess","");
							result.put("addess_","");
					}	
				}else{
					
						result.put("addess","");
						result.put("addess_","");
					
				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	
	
	/**
	 * 
	 * @param ossUrl  oss地址
	 * @param request request对象
	 * @param isImg 是否图片
	 * @param fileName 文件名
	 * @return Map<String,String>
	 */
	public static void download(String url, HttpServletResponse response) {
		
		//File file = new File("D:\\2.jpg");
		File file = new File(ResourceUtil.get("img.Photo")+url);
		InputStream inputStream=null;
		try {
			inputStream = new FileInputStream(file);
		
		byte[] b = new byte[1024];
		int len = -1;
		try {
			while ((len = inputStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void resize(int w, int h,BufferedImage img,String type,String fileName) throws IOException {  
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
        OutputStream out = new FileOutputStream(fileName); 
    	ImageIO.write(image,type.replace(".", ""),out); 
		out.flush();
        
        //FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
        // 可以正常实现bmp、png、gif转jpg  
        //JPEGImageEncoder encoder = png.createJPEGEncoder(out);  
        //encoder.encode(image); // JPEG编码  
        out.close();  
    }
	public static BufferedImage resize(int w,int h,BufferedImage img){
		 BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
	     image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
	     return image;
	}
}
