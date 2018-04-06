package com.wan.sys.service.house.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.entity.HouseClient;
import com.wan.sys.entity.HouseDevice;
import com.wan.sys.entity.User;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.pojo.HouseBean;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.common.impl.CommonServiceImpl;
import com.wan.sys.service.house.IHouseService;
import com.wan.sys.util.ConfigUtil;
import com.wan.sys.util.Encrypt;
import com.wan.sys.util.HttpClientUtil;
import com.wan.sys.util.ImageUtil;
import com.wan.sys.util.StringUtil;

@Service
public class HouseServiceImpl extends CommonServiceImpl implements IHouseService{

	@Autowired
	IBaseDao baseDao;
	
	@Override
	public ResponseHead getUserToken(HouseBean bean) {
		JSONObject js=new JSONObject();
		// 参数验证
		if(StringUtil.isBlank(bean.getDeviceSerial())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		String token= Encrypt.md5(bean.getDeviceSerial())+(int)(Math.random()*100);
		//查询设备序列号在数据库是否存在
		HouseDevice hd=(HouseDevice)baseDao.get("from HouseDevice h where h.deviceSerial=?", bean.getDeviceSerial());
		if(hd==null){//不存在
			//存储设备序列号
//			HouseDevice t = new HouseDevice();
//			t.setDeviceSerial(bean.getDeviceSerial());
//			t.setToken(token);
//			t.setVailTime((1000*24*60*60+System.currentTimeMillis()));
//			baseDao.save(t);
			return response(ErrorCodeEnum.FAIL_NOREGISTER.getCode(), 
					ErrorCodeEnum.FAIL_NOREGISTER.getValue(), js, 0,null);
		}else{//存在
			hd.setToken(token);
			hd.setVailTime((1000*24*60*60+System.currentTimeMillis()));
			baseDao.update(hd);
		}
		//返回token值
		js.put("token",token);
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead setProject(HouseBean bean) {
		
		JSONObject js=new JSONObject();
		// 参数验证
		if(StringUtil.isBlank(bean.getToken())
				||StringUtil.isBlank(bean.getDeviceSerial())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		// 验证token是否过期、存在
		HouseDevice tb= (HouseDevice) baseDao.get("from HouseDevice t where t.deviceSerial=? and t.token=?",bean.getDeviceSerial(),bean.getToken());
		if(null ==tb){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}else{//存在判断时效
			if(System.currentTimeMillis()>tb.getVailTime()){//过期
				return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
						ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
			}
		}

		//返回项目设备绑定关系
		js.put("deviceSerial", bean.getDeviceSerial());
		js.put("projectId", tb.getProjectId());
		js.put("projectName", tb.getProjectName());
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead checkUser(HouseBean bean) {
		System.out.println("checkUser");
		System.out.println(bean.getDeviceSerial());
		System.out.println(bean.getUserId());
		System.out.println(bean.getUserName()+"用户名");
		int lineNo = 0;
		JSONObject js=new JSONObject();
		int index=0;
		String result1=null;
		List list1=new ArrayList();
		List list2=new ArrayList();
		HouseClient client=new HouseClient();
		String mobile=null;
		// 参数验证
		if(StringUtil.isBlank(bean.getToken())
				||StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getDeviceSerial())
				||StringUtil.isBlank(bean.getUserId())||StringUtil.isBlank(bean.getUserName())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		// 验证token是否过期
		HouseDevice tb= (HouseDevice) baseDao.get("from HouseDevice t where t.token=?",bean.getToken());
		if(null ==tb){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}else{//存在判断时效
			if(System.currentTimeMillis()>tb.getVailTime()){//过期
				return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
						ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
			}
		}
		
		
		//查询数据库最后一条排队序列号，如果为空，生成1
		List<String> line=baseDao.findBySql("SELECT lineNo FROM  `sys_house_client` where projectId='"+bean.getProjectId()+"' ORDER BY id DESC ");
		if (line != null && line.size() > 0){
			String no=line.get(0).toString();
			lineNo=(Integer.parseInt(no)+1);
		}else{
			lineNo=1;
		}
		
		//查询此项目A等待接房的人员号码数
		int nowNo = 0;
		List line1=baseDao.findBySql("select count(id) from `sys_house_client` where house_state='A' and projectId='"+bean.getProjectId()+"'");
		if (line1 != null && line1.size() > 0){
			String no=line1.get(0).toString();
			nowNo=Integer.parseInt(no);
		}
		
		//查询对应易软的项目id
		Organization oz= (Organization) baseDao.get("from Organization t where t.id=?",bean.getProjectId());
		String projectId=null;
		if(oz!=null){
			projectId=oz.getEasyId();
		}else{//查不到项目返回参数错误
			return response(ErrorCodeEnum.FAIL_NOPROJECT.getCode(), 
					ErrorCodeEnum.FAIL_NOPROJECT.getValue(), js, 0,null);
		}
		//查询当前用户是否已经取号
		HouseClient hc= (HouseClient) baseDao.get("from HouseClient t where t.projectId=? and t.userId=? and t.houseInfo!='物业帮取'",
				bean.getProjectId(),bean.getUserId());
		if(hc!=null){//已经取号
			if(hc.getHouseState().equals("Z")){//完成接房
				return response(ErrorCodeEnum.FAIL_NOUSER.getCode(), 
						"该用户已经完成接房", js, 0,null);
			}else{//没有接房，还需要判断号码是否已经过了
//				HouseClient hc1= (HouseClient) baseDao.get("from HouseClient t where t.projectId=? and t.userId=?",
//						bean.getProjectId(),bean.getUserId());
				lineNo=Integer.parseInt(hc.getLineNo());//得到排队号
				//算出当前等待人数
				List line2=baseDao.findBySql("select count(id) from `sys_house_client` where house_state='A' and projectId='"+bean.getProjectId()+"' and lineNo<"+lineNo);
				if (line2 != null && line2.size() > 0){
					String no=line2.get(0).toString();
					nowNo=Integer.parseInt(no);
				}
//				nowNo=nowNo-1;
			}
		}
		
		
		//调用外部post接口，及参数
		try {
			Map<String,String> map=new HashMap<String,String>();
//				map.put("name", bean.getUserName());
				map.put("code", bean.getUserId());
				
				map.put("areaId", projectId);
				
 				result1=HttpClientUtil.httpClientByPostAndParams("http://api.tq-service.com/uc/Api/Community/getUserHouseAll", map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(result1!=null){
			//转换为json格式
			JSONObject jsonObject = JSONObject.parseObject(result1);
			if(jsonObject.getInteger("errcode")==0){
				//将data转换为数组
				JSONArray array=JSONArray.parseArray(jsonObject.get("data")+"");
				if(array==null||array.size()==0){
					return response(ErrorCodeEnum.FAIL_NOUSER.getCode(), 
							"该用户在此项目没有房", js, 0,null);
				}
				index=array.size();
				String unit="";
				String building="";
				String room="";
				String userName="";
				String userPhone="";
				for(int i=0;i<array.size();i++){
					//设置返回数据
					JSONObject js1=new JSONObject();
//					int lineNoi=lineNo+i;
					js1.put("serialNumber", bean.getDeviceSerial());
					js1.put("projectName", oz.getOrgName());
					js1.put("unit",array.getJSONObject(i).get("unit"));
					unit+=array.getJSONObject(i).get("unit")+",";
					js1.put("building", array.getJSONObject(i).get("build"));
					building+=array.getJSONObject(i).get("build")+",";
					js1.put("room", array.getJSONObject(i).get("houseName"));
					room+=array.getJSONObject(i).get("houseName")+",";
					js1.put("userName", array.getJSONObject(i).get("name"));
					userName=array.getJSONObject(i).get("name")+"";
					js1.put("userPhone", array.getJSONObject(i).get("phone"));
					userPhone=array.getJSONObject(i).get("phone")+"";
					mobile=(String) array.getJSONObject(i).get("phone");
					js1.put("userId", bean.getUserId());
					js1.put("lineNo", lineNo);
					js1.put("waitPerson", nowNo);
					js1.put("projectId", bean.getProjectId());
					list1.add(js1);
				}
				if(hc==null&&array.size()>0){//客户没有取过号。并且查到有房才可以取号
					//通过参数userId，存储一个客户信息，包括userId，lineNo，isTakeHouse
					
					client.setUserId(bean.getUserId());
					client.setLineNo(lineNo+"");
					client.setIsTakeHouse("N");
					client.setHouseState("A");
					client.setProjectId(bean.getProjectId());
//					client.setHouseInfo(array.getJSONObject(i).get("unit")+","+array.getJSONObject(i).get("build")+","+array.getJSONObject(i).get("houseName"));
					client.setHouseInfo(index+"套房");
					client.setUnit(unit);
					client.setBuilding(building);
					client.setRoom(room);
					client.setUserName(userName);
					client.setUserPhone(userPhone);
//					baseDao.save(client);
				}
			}
		}
		
		//判断是否为物业人员
		try {
			Map<String,String> map1=new HashMap<String,String>();
			map1.put("appSecret", "DF6A3B7483001979464F6F5D7A1B5D11");
			map1.put("appKey", "qF7jQxnUJRR9PtBk2Uig"); 
			map1.put("projectId", bean.getProjectId());
			
			String result=HttpClientUtil.httpClientByPostAndParams("http://api.tq-service.com/oa/APIInterface/getUserList", map1);
			
			if(result!=null){
				//转换为json格式
				JSONObject jsonObject1 = JSONObject.parseObject(result);
					if(jsonObject1.getInteger("errcode")==1){
					JSONObject jsonObject2 = JSONObject.parseObject(jsonObject1.get("data")+"");
					//将data里面转换为data
					JSONArray array1=JSONArray.parseArray(jsonObject2.get("listDate")+"");
					
					for(int j=0;j<array1.size();j++){
						if(array1.getJSONObject(j).get("mobile").equals(mobile)){//查到数据
							//设置返回数据
							JSONObject js1=new JSONObject();
							js1.put("projectName", oz.getOrgName());
							js1.put("userPhone", array1.getJSONObject(j).get("mobile"));
							js1.put("lineNo", lineNo);
							js1.put("waitPerson", nowNo);
							js1.put("projectId", bean.getProjectId());
							
							js1.put("serialNumber", "");
							js1.put("unit","");
							js1.put("building", "");
							js1.put("room", "");
							js1.put("userName", "");
							js1.put("userId", "");
							list2.add(js1);
							//物管人员存号
							//通过参数userId，存储一个客户信息，包括userId，lineNo，isTakeHouse
//							HouseClient client=new HouseClient();
							client.setUserId(bean.getUserId());
							client.setLineNo(lineNo+"");
							client.setIsTakeHouse("N");
							client.setHouseState("A");
							client.setProjectId(bean.getProjectId());
//									client.setHouseInfo(array.getJSONObject(i).get("unit")+","+array.getJSONObject(i).get("build")+","+array.getJSONObject(i).get("houseName"));
							client.setHouseInfo("物业帮取");
							client.setUnit("");
							client.setBuilding("");
							client.setRoom("");
							client.setUserName("");
							client.setUserPhone("");
//								baseDao.save(client);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//不为空表示为业主或者物业人员
		if(client.getHouseInfo()!=null){
			baseDao.save(client);
		}
		if(list2.size()==0){
			js.put("listData", list1);
		}else{
			js.put("listData", list2);
		}
		System.out.println(js);
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
		
	}


	@Override
	public ResponseHead saveUserInfo(HouseBean bean,HttpServletRequest request) {
		
		JSONObject js=new JSONObject();
		// 参数验证
				if(StringUtil.isBlank(bean.getToken())
						||StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getUserId())){
					return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
							ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
				}
				// 验证token是否过期
				HouseDevice tb= (HouseDevice) baseDao.get("from HouseDevice t where t.token=?",bean.getToken());
				if(null ==tb){
					return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
							ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
				}else{//存在判断时效
					if(System.currentTimeMillis()>tb.getVailTime()){//过期
						return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
								ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
					}
				}
				
				//解析图片
				Map<String, String> uploadImage = ImageUtil.uploadImage(request, "3");
					
				HouseClient hc= (HouseClient) baseDao.get("from HouseClient t where t.userId=?",bean.getUserId());
//				   “projectId”:”项目ID”,
//				   “userId”:”身份证号”,
//				   “userName”:”用户名称”,
//				   “file1”:”身份证照片文件流”,
//				   “file2”:” 用户照片文件流”
				
				//存储图片地址
				if(null!=uploadImage&&hc!=null){
					String[] paths=uploadImage.get("addess").split("\\|");
					if(paths!=null&&paths.length==1){
						hc.setImgIdCard(paths[0]);
					}
					if(paths!=null&&paths.length==2){
						hc.setImgIdCard(paths[0]);
						hc.setImgFace(paths[1]);
					}
				}
				
				return response(ErrorCodeEnum.SUCCESS.getCode(), 
						ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead handleHouse(HouseBean bean) {
		
		JSONObject js=new JSONObject();
		// 参数验证，物业人员没有身份证
		if(StringUtil.isBlank(bean.getProjectId())
				||StringUtil.isBlank(bean.getLineNo())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		// 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		// 获取token，再加一个随机数
//		Long token=1000*24*60*60+System.currentTimeMillis();
		HouseClient client=(HouseClient)baseDao.get("from HouseClient where  projectId=? and lineNo=? and houseState='A'", bean.getProjectId(),bean.getLineNo());
		
		if(client==null){//为空说明号码已经用过
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
//		client.setToken(token.toString());
		client.setHouseState("B");
		baseDao.update(client);
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead takeHouse(HouseBean bean) {
		JSONObject js=new JSONObject();
		// 参数验证，物业人员没有身份证
		if(StringUtil.isBlank(bean.getLineNo())
				||StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		// 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		//获取B的排队号，并修改用户的接房状态
		HouseClient client=(HouseClient)baseDao.get("from HouseClient t where  t.houseState='B' and t.lineNo=? and t.projectId=?", 
				bean.getLineNo(),bean.getProjectId());
		if(client==null){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		client.setHouseState("Z");
		client.setIsTakeHouse("Y");
		baseDao.update(client);
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead resetLineNo(HouseBean bean) {
		JSONObject js=new JSONObject();
		// 参数验证
		if(StringUtil.isBlank(bean.getToken())
				||StringUtil.isBlank(bean.getProjectId())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		// 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		
		HouseClient client=new HouseClient();
		client.setProjectId(bean.getProjectId());
		client.setLineNo("0");
		baseDao.save(client);
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead activeDevice(HouseBean bean) {
		JSONObject js=new JSONObject();
		//参数验证
		if(StringUtil.isBlank(bean.getDeviceName())||StringUtil.isBlank(bean.getDeviceSerial())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		// 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		//修改设备激活状态
		HouseDevice hd=new HouseDevice();
		hd.setIsALive("Y");
		hd.setDeviceSerial(bean.getDeviceSerial());
		hd.setDeviceName(bean.getDeviceName());
		baseDao.save(hd);
		
		//返回操作状态码
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead findListData(HouseBean bean) {
		JSONObject js=new JSONObject();
		//参数验证
		if(StringUtil.isBlank(bean.getToken())||StringUtil.isBlank(bean.getUserId())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		// 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		
		
		List projectListData=new ArrayList();
		List<Object[]> project = baseDao.findBySql("select o.ID,o.ORG_NAME FROM sys_user_project u LEFT JOIN  sys_organization o "+
 													" ON u.PROJECT_ID=o.ID WHERE u.USER_ID="+bean.getUserId()+" AND o.SORT_NUMBER=3");//项目列表
		for(int i=0;i<project.size();i++){
			//设置返回数据
			JSONObject js1=new JSONObject();
			js1.put("projectId",project.get(i)[0]);
			if(project.get(i)[1]==null){
				js1.put("projectName", "");
			}else{
				js1.put("projectName",project.get(i)[1]);
			}
			projectListData.add(js1);
		}
		js.put("projectListData", projectListData);//项目列表
		
		List deviceListData=new ArrayList();
		List<Object[]> device = baseDao.findBySql("select d.deviceSerial,d.deviceName from sys_house_device d");//设备列表
		for(int i=0;i<device.size();i++){
			//设置返回数据
			JSONObject js1=new JSONObject();
			js1.put("deviceSerial",device.get(i)[0]);
			if(device.get(i)[1]==null){
				js1.put("deviceName", "");
			}else{
				js1.put("deviceName",device.get(i)[1]);
			}
			deviceListData.add(js1);
		}
		js.put("deviceListData", deviceListData);
		
		
		//返回操作状态码
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead bindingDevice(HouseBean bean) {
		JSONObject js=new JSONObject();
		//参数验证
		if(StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getDeviceSerial())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		// 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		
		//绑定数据
		HouseDevice hd=(HouseDevice)baseDao.get("from HouseDevice h where h.deviceSerial=?", bean.getDeviceSerial());
		if(hd==null){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		//通过项目id查询项目名字并更新
		Organization oz= (Organization) baseDao.get("from Organization t where t.id=?",bean.getProjectId());
		String projectName=null;
		if(oz!=null){
			projectName=oz.getOrgName();
		}
		
		hd.setProjectName(projectName);
		hd.setProjectId(bean.getProjectId());
		baseDao.update(hd);
		
		//返回操作成功状态码
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead findState(HouseBean bean) {
		JSONObject js=new JSONObject();
		//参数验证
		if(StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getLineNo())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		// 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		
		//查询
		HouseClient client=(HouseClient)baseDao.get("from HouseClient t where t.projectId=? and t.lineNo=?", bean.getProjectId(),bean.getLineNo());
		if(client==null){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		
		//处理结果
		js.put("HouseState",client.getHouseState());
		
		
		//返回操作成功状态码
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead getHouseInfo(HouseBean bean) {
		JSONObject js=new JSONObject();
		List list=new ArrayList();
		String result1=null;
		//参数验证
		if(StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getLineNo())
				||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
//		 验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		
		//查询对应易软的项目id
		Organization oz= (Organization) baseDao.get("from Organization t where t.id=?",bean.getProjectId());
		String projectId=null;
		if(oz!=null){
			projectId=oz.getEasyId();
		}else{
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		//查询号码是否存在
		HouseClient client1= (HouseClient) baseDao.get("from HouseClient t where t.projectId=? and t.lineNo=?",bean.getProjectId(),bean.getLineNo());
		if(client1==null){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					"用户不存在请先取号", js, 0,null);
		}
		
		
//		//调用外部post接口，及参数
//		try {
//			Map<String,String> map=new HashMap<String,String>();
//			if(!StringUtil.isBlank(bean.getUserId())){
//				map.put("code", bean.getUserId());
//			}
//				map.put("code", bean.getUserId());
//				map.put("areaId", projectId);
////				map.put("unit",houseInfo[0] );
////				map.put("build", houseInfo[1]);
////				map.put("houseName", houseInfo[2]);
//				result1=HttpClientUtil.httpClientByPostAndParams("http://api-development.tq-service.com/uc/Api/Community/getUserHouseAll", map);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if(result1!=null){
//			//转换为json格式
//			JSONObject jsonObject = JSONObject.parseObject(result1);
//			if(jsonObject.getInteger("errcode")==0){
//				//将data转换为数组
//				JSONArray array=JSONArray.parseArray(jsonObject.get("data")+"");
//				for(int i=0;i<array.size();i++){
//					if(array.getJSONObject(i).get("unit").equals(houseInfo[0])&&array.getJSONObject(i).get("build").equals(houseInfo[1])&&array.getJSONObject(i).get("houseName").equals(houseInfo[2])){
//						//设置返回数据
//						js.put("projectName", array.getJSONObject(i).get("project"));
//						js.put("unit",array.getJSONObject(i).get("unit"));
//						js.put("building", array.getJSONObject(i).get("build"));
//						js.put("room", array.getJSONObject(i).get("houseName"));
//						js.put("userName", array.getJSONObject(i).get("name"));
//						js.put("userPhone", array.getJSONObject(i).get("phone"));
//						js.put("userId", array.getJSONObject(i).get("code"));
//						js.put("lineNo", bean.getLineNo());//返回序列号，可能有多个，以逗号结尾
//						js.put("projectId", bean.getProjectId());
//					}
//				}
//			}
//		}
		
		//物业人员不传身份证号
		if(StringUtil.isBlank(bean.getUserId())){
			JSONObject js1=new JSONObject();
			js1.put("lineNo",bean.getLineNo());
			js1.put("unit","");
			js1.put("building","");
			js1.put("room","");
			js1.put("userName","");
			js1.put("userPhone","");
			js1.put("userId","");
			js1.put("projectId", bean.getProjectId());
			js1.put("projectName",oz.getOrgName());
			list.add(js1);
			js.put("listData", list);
			//返回操作成功状态码
			return response(ErrorCodeEnum.SUCCESS.getCode(), 
					ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
		}
		
		//通过排队号查出数据库中唯一的用户信息
		HouseClient client=(HouseClient)baseDao.get("from HouseClient t where t.projectId=? and t.lineNo=?", bean.getProjectId(),bean.getLineNo());
		if(null!=client&&client.getUnit()!=null&&client.getBuilding()!=null&&client.getRoom()!=null){//查询到用户
			String unit=client.getUnit();
			String[] units=unit.split(",");
			for(int i=0;i<units.length;i++){
				JSONObject js1=new JSONObject();
				js1.put("lineNo",bean.getLineNo());
				js1.put("unit",client.getUnit().split(",")[i]);
				js1.put("building",client.getBuilding().split(",")[i]);
				js1.put("room",client.getRoom().split(",")[i]);
				js1.put("userName",client.getUserName());
				js1.put("userPhone",client.getUserPhone());
				js1.put("userId",bean.getUserId());
				js1.put("projectId", bean.getProjectId());
				js1.put("projectName",oz.getOrgName());
				list.add(js1);
			}
		}
		
		
		//调用外部post接口，及参数
//		try {
//			Map<String,String> map=new HashMap<String,String>();
//			if(!StringUtil.isBlank(bean.getUserName())){
//				map.put("name", bean.getUserName());
//			}
//				map.put("code", bean.getUserId());
//				map.put("areaId", projectId);
//				
//				
//				result1=HttpClientUtil.httpClientByPostAndParams("http://api-development.tq-service.com/uc/Api/Community/getUserHouseAll", map);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		if(result1!=null){
//			//转换为json格式
//			JSONObject jsonObject = JSONObject.parseObject(result1);
//			if(jsonObject.getInteger("errcode")==0){
//				//将data转换为数组
//				JSONArray array=JSONArray.parseArray(jsonObject.get("data")+"");
//				for(int i=0;i<array.size();i++){
//					//设置返回数据
//					JSONObject js1=new JSONObject();
//					js1.put("lineNo", bean.getLineNo());
//					js1.put("projectName",oz.getOrgName());
//					js1.put("unit",array.getJSONObject(i).get("unit"));
//					js1.put("building", array.getJSONObject(i).get("build"));
//					js1.put("room", array.getJSONObject(i).get("houseName"));
//					js1.put("userName", array.getJSONObject(i).get("name"));
//					js1.put("userPhone", array.getJSONObject(i).get("phone"));
//					js1.put("userId", bean.getUserId());
//					js1.put("projectId", bean.getProjectId());
//					
//					list.add(js1);
//				}
//			}
//		}
		js.put("listData", list);
		//返回操作成功状态码
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}
	
	//----------------------------------------app获取token------------------------------------------------------------------------------------------
	
	public ResponseHead getTokenAndUser(String openId, String projectId) {
		//JSONArray js=new JSONArray();
		
		JSONObject data = new JSONObject();
		// 参数验证
				if(StringUtil.isBlank(openId) || StringUtil.isBlank(projectId)){
					return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
							ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), data, 0,null);
				}
				// 定义好需要使用的变量
				String token = "";
				Long expireTime = null;
				User user = null;
				// 根据openId查询员工在本地是否存在
				user = (User) baseDao.get("from User t where t.openId='"+openId+"' and t.recordStatus='Y' ");
				// 如果存在就生成token并返回
				Object[] tokenObj = generateToken();
				token = (String) tokenObj[0];
				expireTime = (Long) tokenObj[1];
				if(user!=null){					
					try {
						user=getUserInfoByOpenIdBean(openId,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
					user.setToken(token);
					user.setExpireTime(expireTime);
					
					
					baseDao.update(user);
					
					//更新部门
//					//新增用户部门
//					baseDao.executeSql("delete from t_user_dept  where user_id="+user.getId()+"");
//					for(String str:user.getDeptId()){
//						baseDao.executeSql("insert into t_user_dept(user_id,dept_id) values("+user.getId()+","+str+")");
//					}
					
					
					data.put("token", token);
					data.put("staffName", user.getNickName());
					data.put("position", user.getPosition());
					data.put("ifManage", user.getIfManage());
					data.put("userId", user.getId());
					
					//js.add(data);
					
					return  response(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getValue(), data, 0,null);
				}
				// 如果不存在就去开放平台拉取员工数据
				try {
					user = getUserInfoByOpenId(openId);
				} catch (Exception e) {
					e.printStackTrace();
					return  response(ErrorCodeEnum.FAIL.getCode(), "获取用户信息失败", data, 0,null);
				}
				// 拉取完成后就生成token,并保存
				user.setToken(token);
				user.setExpireTime(expireTime);
				//得到人员的电话号码
				//通过电话号码查询是否为管理员 
			   
				user.setRecordStatus("Y");
				user.setPassword(Encrypt.md5("123456"));
				
				baseDao.save(user);
				
				//新增用户部门
				
//				baseDao.executeSql("delete from t_user_dept  where user_id="+user.getId()+"");
//				for(String str:user.getDeptId()){
//					baseDao.executeSql("insert into t_user_dept(user_id,dept_id) values("+user.getId()+","+str+")");
//				}
				
				
				// 返回数据
				data.put("token", token);
				data.put("staffName", user.getNickName());
				data.put("position", user.getPosition());
				data.put("ifManage", user.getIfManage());
				data.put("userId", user.getId());
				//js.add(data);
				return  response(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getValue(), data,0, null);
	}

	
	public Object[] generateToken() {
		// 定义token有效时长(一天的时长)
		Long validTime = 86400000L;
		// 生成uuid作为token
		String token = StringUtil.getUUID();
		// 根据当前时间毫秒数和有效时长生成过期时间毫秒数
		Long currentTime = System.currentTimeMillis();
		Long expireTime = currentTime+validTime;
		Object[] obj = {token,expireTime};
		return obj;
	}
	
	
	public User getUserInfoByOpenIdBean(String openId,User user) throws HttpException, IOException {
		// 定义需要的变量
		//User user = us;
		String appKey = ConfigUtil.get("appKey");
		String appSecret = ConfigUtil.get("appSecret");
		String url = ConfigUtil.get("getUserFromOpen");
		Map<String, Object> params = new HashMap<String, Object>();
		// 调用开发平台接口获取用户数据
		params.put("openId", openId);
		params.put("appKey", appKey);
		params.put("appSecret", appSecret);
		String bodyStr = HttpClientUtil.getResponseBodyAsString(url, params);
		// 解析用户数据
		JSONObject body = JSON.parseObject(bodyStr);
		String errcode = body.getString("errcode");
		String errmsg = body.getString("errmsg");
		if(!"1".equals(errcode)){
			throw new HttpException(errmsg);
		}
		JSONObject data = body.getJSONObject("data");
		if(net.sf.json.util.JSONUtils.isNull(data)){
			throw new HttpException("data中数据为空");
		}
		JSONArray listData = data.getJSONArray("listDate");
		if(net.sf.json.util.JSONUtils.isNull(listData)){
			throw new HttpException("listData中数据为空");
		}
		for (Object user1 : listData) {
			JSONObject jsonUser = (JSONObject) user1;
			user.setEmail(jsonUser.getString("email"));
			user.setMobilePhone(jsonUser.getString("mobile"));
			user.setOpenId(openId);
			user.setPosition(jsonUser.getString("position"));
			user.setNickName(jsonUser.getString("name"));
			//user.setStaffState(true);
			user.setUnionId(jsonUser.getString("unionid"));
			//user.setUserId(jsonUser.getString("userid"));
			user.setLoginAcct(jsonUser.getString("userid"));
			
			JSONArray orgList = jsonUser.getJSONArray("orgList");
			JSONArray deptList = jsonUser.getJSONArray("deptList");

			 if(jsonUser.getString("power").equals("1")){
				 user.setIfManage("1"); 
			 }
			 else{
				 user.setIfManage(""); 
			 }
			 
			 
			if(!net.sf.json.util.JSONUtils.isNull(listData)){
				//Set<TProject> tProjects = new HashSet<TProject>(0);
				List<Organization> listP = new ArrayList<Organization>();
				for (Object project : orgList) {
					JSONObject jsonProject = (JSONObject) project;
					Organization pro = (Organization) baseDao.get(Organization.class, jsonProject.getString("orgId"));
					if(pro!=null){
						listP.add(pro);
					}
				}
//				List<String> dept= new ArrayList<String>();
//				for (Object project : deptList) {
//					JSONObject jsonProject = (JSONObject) project;
//					if(StringUtil.isNotBlank(jsonProject.getString("deptId"))){
//						dept.add(jsonProject.getString("deptId"));
//					}
//				}
				
				
				//user.setDeptId(dept);
				//user.setTProjects(tProjects);
				
				user.setOrganization(listP);
			}
		}
		return user;
	}	
	public User getUserInfoByOpenId(String openId) throws HttpException, IOException {
		// 定义需要的变量
		User user = null;
		String appKey = ConfigUtil.get("appKey");
		String appSecret = ConfigUtil.get("appSecret");
		String url = ConfigUtil.get("getUserFromOpen");
		Map<String, Object> params = new HashMap<String, Object>();
		// 调用开发平台接口获取用户数据
		params.put("openId", openId);
		params.put("appKey", appKey);
		params.put("appSecret", appSecret);
		String bodyStr = HttpClientUtil.getResponseBodyAsString(url, params);
		// 解析用户数据
		JSONObject body = JSON.parseObject(bodyStr);
		String errcode = body.getString("errcode");
		String errmsg = body.getString("errmsg");
		if(!"1".equals(errcode)){
			throw new HttpException(errmsg);
		}
		JSONObject data = body.getJSONObject("data");
		if(net.sf.json.util.JSONUtils.isNull(data)){
			throw new HttpException("data中数据为空");
		}
		JSONArray listData = data.getJSONArray("listDate");
		if(net.sf.json.util.JSONUtils.isNull(listData)){
			throw new HttpException("listData中数据为空");
		}
		for (Object user1 : listData) {
			JSONObject jsonUser = (JSONObject) user1;
			user = new User();
			user.setEmail(jsonUser.getString("email"));
			user.setMobilePhone(jsonUser.getString("mobile"));
			user.setOpenId(openId);
			user.setPosition(jsonUser.getString("position"));
			user.setNickName(jsonUser.getString("name"));
			//user.setStaffState(true);
			user.setUnionId(jsonUser.getString("unionid"));
			//user.setUserId(jsonUser.getString("userid"));
			user.setLoginAcct(jsonUser.getString("userid"));
			
			JSONArray orgList = jsonUser.getJSONArray("orgList");
			
			JSONArray deptList = jsonUser.getJSONArray("deptList");
			 if(jsonUser.getString("power").equals("1")){
				 user.setIfManage("1"); 
			 }
			 else{
				 user.setIfManage(""); 
			 }
			 
			 
			if(!net.sf.json.util.JSONUtils.isNull(listData)){
				//Set<TProject> tProjects = new HashSet<TProject>(0);
				List<Organization> listP = new ArrayList<Organization>();
				for (Object project : orgList) {
					JSONObject jsonProject = (JSONObject) project;
					Organization pro = (Organization) baseDao.get(Organization.class, jsonProject.getString("orgId"));
					if(pro!=null){
						listP.add(pro);
					}
				}
//				List<String> dept= new ArrayList<String>();
//				for (Object project : deptList) {
//					JSONObject jsonProject = (JSONObject) project;
//					if(StringUtil.isNotBlank(jsonProject.getString("deptId"))){
//						dept.add(jsonProject.getString("deptId"));
//					}
//				}
				
				
				//user.setDeptId(dept);
				
				//user.setTProjects(tProjects);
				
				user.setOrganization(listP);
			}
		}
		return user;
	}

	//-----------------------------------------------------微信-------------------------------------------------------
	
	
	
	@Override
	public ResponseHead findNowNo(HouseBean bean) {
		JSONObject js=new JSONObject();
		List list=new ArrayList();
		String result1=null;
		int nowNo=0;
		//参数验证
		if(StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getLineNo())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		//查询对应易软的项目id
		Organization oz= (Organization) baseDao.get("from Organization t where t.id=?",bean.getProjectId());
		String projectId=null;
		if(oz!=null){
			projectId=oz.getEasyId();
		}else{
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		//查询号码是否存在
		HouseClient client1= (HouseClient) baseDao.get("from HouseClient t where t.projectId=? and t.lineNo=?",bean.getProjectId(),bean.getLineNo());
		if(client1==null){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					"用户不存在请先取号", js, 0,null);
		}
		
		//查询该项目现在正在办理的号
		List<String> line=baseDao.findBySql("select lineNo from `sys_house_client` where house_state='B' and projectId="+bean.getProjectId()+" ORDER BY id desc ");
		if (line != null && line.size() > 0){
			String no=line.get(0).toString();
			nowNo=Integer.parseInt(no);
		}else{//没有查询到正在办理的号，就查询已经办理的号+1
			List<String> line1=baseDao.findBySql("select lineNo from `sys_house_client` where house_state='Z' and projectId="+bean.getProjectId()+" ORDER BY id desc  ");
			if (line1 != null && line1.size() > 0){
				String no=line1.get(0).toString();
				nowNo=(Integer.parseInt(no)+1);
			}
		}
		
		
		//统计等待人数
		int waitPerson=0;
		List wait=baseDao.findBySql("select count(id) from `sys_house_client` where house_state='A' and projectId="+bean.getProjectId()+" and lineNo<"+Integer.parseInt(bean.getLineNo()));
		if (wait != null && wait.size() > 0){
			String no=wait.get(0).toString();
			waitPerson=Integer.parseInt(no);
		}else{
			waitPerson=0;
		}

		
		//通过排队号查出数据库中唯一的用户信息
		HouseClient client=(HouseClient)baseDao.get("from HouseClient t where t.projectId=? and t.lineNo=?", bean.getProjectId(),bean.getLineNo());
		if(null!=client&&client.getUnit()!=null&&client.getBuilding()!=null&&client.getRoom()!=null){//查询到用户
			String unit=client.getUnit();
			String[] units=unit.split(",");
			for(int i=0;i<units.length;i++){
				JSONObject js1=new JSONObject();
				js1.put("lineNo",bean.getLineNo());
				js1.put("unit",client.getUnit().split(",")[i]);
				js1.put("building",client.getBuilding().split(",")[i]);
				js1.put("room",client.getRoom().split(",")[i]);
				js1.put("userName",client.getUserName());
				js1.put("userPhone",client.getUserPhone());
				js1.put("userId",client.getUserId());
				js1.put("projectId", bean.getProjectId());
				js1.put("projectName",oz.getOrgName());
				js1.put("nowNo", nowNo+"");
				js1.put("waitPerson", waitPerson+"");
				String state="";
				if("A".equals(client.getHouseState())){
					state="等待接房";
				}else if("B".equals(client.getHouseState())){
					state="正在接房";
				}else if("Z".equals(client.getHouseState())){
					state="完成接房";
				}else{
					state="请先取号";
				}
				js1.put("houseState", state);
				list.add(js1);
			}
		}
		
		js.put("listData", list);
		
//		 验证token是否过期
//		if(null == tokenIsExpire(bean.getToken())){
//			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
//					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
//		}
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead addUserInfo(HouseBean bean) {
		JSONObject js=new JSONObject();
		String result1="";
		List list1=new ArrayList();
		//参数验证
		if(StringUtil.isBlank(bean.getProjectId())||StringUtil.isBlank(bean.getLineNo())
				||StringUtil.isBlank(bean.getUserId())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		//验证token是否过期
		if(null == tokenIsExpire(bean.getToken())){
			return  response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js,0, null);
		}
		
		//查询号码是否存在
		HouseClient client= (HouseClient) baseDao.get("from HouseClient t where t.projectId=? and t.lineNo=?",bean.getProjectId(),bean.getLineNo());
		if(client==null){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					"用户不存在请先取号", js, 0,null);
		}else{//确保还没有接房
			if(client.getIsTakeHouse().equals("Y")){//已经接房
				return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
						"用户已经接房", js, 0,null);
			}
			if(!(client.getHouseInfo().equals("物业帮取"))){//不为物业帮取
				return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
						"该操作仅由物业人员取号操作", js, 0,null);
			}
		}
		
		
		//查询对应易软的项目id
		Organization oz= (Organization) baseDao.get("from Organization t where t.id=?",bean.getProjectId());
		String projectId=null;
		if(oz!=null){
			projectId=oz.getEasyId();
		}else{//查不到项目返回参数错误
			return response(ErrorCodeEnum.FAIL_NOPROJECT.getCode(), 
					ErrorCodeEnum.FAIL_NOPROJECT.getValue(), js, 0,null);
		}
		//调用外部post接口，及参数查询当前用户是否为业主，是否有房
		try {
			Map<String,String> map=new HashMap<String,String>();
//				map.put("name", bean.getUserName());
				map.put("code", bean.getUserId());
				
				map.put("areaId", projectId);
				
 				result1=HttpClientUtil.httpClientByPostAndParams("http://api.tq-service.com/uc/Api/Community/getUserHouseAll", map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int index=0;
		if(result1!=null){
			//转换为json格式
			JSONObject jsonObject = JSONObject.parseObject(result1);
			if(jsonObject.getInteger("errcode")==0){
				//将data转换为数组
				JSONArray array=JSONArray.parseArray(jsonObject.get("data")+"");
				index=array.size();
				String unit="";
				String building="";
				String room="";
				String userName="";
				String userPhone="";
				for(int i=0;i<array.size();i++){
					//设置返回数据
					JSONObject js1=new JSONObject();
//					int lineNoi=lineNo+i;
					js1.put("projectName", oz.getOrgName());
					js1.put("unit",array.getJSONObject(i).get("unit"));
					unit+=array.getJSONObject(i).get("unit")+",";
					js1.put("building", array.getJSONObject(i).get("build"));
					building+=array.getJSONObject(i).get("build")+",";
					js1.put("room", array.getJSONObject(i).get("houseName"));
					room+=array.getJSONObject(i).get("houseName")+",";
					js1.put("userName", array.getJSONObject(i).get("name"));
					userName=array.getJSONObject(i).get("name")+"";
					js1.put("userPhone", array.getJSONObject(i).get("phone"));
					userPhone=array.getJSONObject(i).get("phone")+"";
					js1.put("userId", bean.getUserId());
					js1.put("lineNo", bean.getLineNo());
					js1.put("projectId", bean.getProjectId());
					list1.add(js1);
				}
				client.setUserId(bean.getUserId());
				client.setIsTakeHouse("N");
				client.setHouseState("A");
				client.setProjectId(bean.getProjectId());
//					client.setHouseInfo(array.getJSONObject(i).get("unit")+","+array.getJSONObject(i).get("build")+","+array.getJSONObject(i).get("houseName"));
				client.setHouseInfo(index+"套房");
				client.setUnit(unit);
				client.setBuilding(building);
				client.setRoom(room);
				client.setUserName(userName);
				client.setUserPhone(userPhone);
				baseDao.update(client);
			}
		}
		js.put("listData", list1);
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}
}
