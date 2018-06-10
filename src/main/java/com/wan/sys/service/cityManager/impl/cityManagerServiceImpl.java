package com.wan.sys.service.cityManager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.entity.User;
import com.wan.sys.entity.cityManager.Code;
import com.wan.sys.entity.forum.Forum;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.cityManager.IcityManagerService;
import com.wan.sys.service.common.impl.CommonServiceImpl;
import com.wan.sys.util.Encrypt;
import com.wan.sys.util.StringUtil;
import com.wan.sys.util.AliMessageSend;

@Service
public class cityManagerServiceImpl extends CommonServiceImpl implements IcityManagerService{

	@Autowired
	IBaseDao baseDao;
	
	/**
	 * token验证
	 * @return
	 */
	public Boolean tokenCheck(Long userId,String token){
		//验证用户名的token是否与传的对应
		User u=(User)baseDao.get(" from User where id=? and token=?", userId,token);
		if(u!=null){//存在，验证时效
			if(System.currentTimeMillis()<u.getExpireTime()){
				return true;
			}
		}
		
		//token验证
//		if(!tokenCheck(bean.getId(),bean.getToken())){
//			return response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
//					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js, 0,null);
//		}
		
		return false;
	}
	
	@Override
	public ResponseHead toLogin(UserBean bean) {
		JSONObject js=new JSONObject();
		// 参数验证，用户名密码
		if(StringUtil.isBlank(bean.getLoginAcct())||StringUtil.isBlank(bean.getPassword())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		String token= Encrypt.md5(bean.getLoginAcct()+bean.getPassword())+(int)(Math.random()*100);
		//查询用户在数据库是否存在
//		User u=(User)baseDao.get(" from User where loginAcct=? ", bean.getLoginAcct());
//		if(u==null){//不存在
//			return response(ErrorCodeEnum.FAIL_NOUSER.getCode(), 
//					ErrorCodeEnum.FAIL_NOUSER.getValue(), js, 0,null);
//		}else{//存在，判断密码是否一致
//			if(Encrypt.md5(bean.getPassword()).equals(u.getPassword())){
//				u.setToken(token);
//				u.setExpireTime((1000*24*60*60+System.currentTimeMillis()));
//				baseDao.update(u);
//			}else{//密码错误
//				return response(ErrorCodeEnum.FAIL_PASS.getCode(), 
//						ErrorCodeEnum.FAIL_PASS.getValue(), js, 0,null);
//			}
//		}
		
		//改为用手机号登录
		User u=(User)baseDao.get(" from User where mobilePhone=?", bean.getLoginAcct());
		if(u==null){//不存在
			return response(ErrorCodeEnum.FAIL_NOUSER.getCode(), 
					ErrorCodeEnum.FAIL_NOUSER.getValue(), js, 0,null);
		}else{//存在，判断密码是否一致
			if(Encrypt.md5(bean.getPassword()).equals(u.getPassword())){//一致就更新token
				u.setToken(token);
				u.setExpireTime((1000*24*60*60+System.currentTimeMillis()));
				baseDao.update(u);
			}else{//密码错误
				return response(ErrorCodeEnum.FAIL_PASS.getCode(), 
						ErrorCodeEnum.FAIL_PASS.getValue(), js, 0,null);
			}
		}
		
		//返回token值，和userid
		js.put("userId",u.getId());
		js.put("token",token);
		js.put("type", u.getType()==null?"0":u.getType());
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead toRegister(UserBean bean) {
		JSONObject js=new JSONObject();
		// 参数验证，根据flag的不同判断
		
		if(StringUtil.isNotBlank(bean.getFlag())){
			if(bean.getFlag().equals("1")){//普通手机号注册
				//参数验证，手机号和密码和验证码
				if(StringUtil.isBlank(bean.getMobilePhone())||StringUtil.isBlank(bean.getPassword())
						||StringUtil.isBlank(bean.getCode())){
					return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
							ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
				}
				//验证手机号是否可用
				List list=baseDao.find(" from User where mobilePhone=?", bean.getMobilePhone());
				if(list.size()>0){
					return response(ErrorCodeEnum.FAIL_EXISTPHONE.getCode(), 
							ErrorCodeEnum.FAIL_EXISTPHONE.getValue(), js, 0,null);
				}
				//验证验证码
				Code c=(Code)baseDao.get(" from Code where phone=?", bean.getMobilePhone());
				if(c!=null&&c.getCode().equals(bean.getCode())){//验证成功
					User u=new User();
					u.setLoginAcct(bean.getMobilePhone());
					u.setPassword(Encrypt.md5(bean.getPassword()));
					u.setMobilePhone(bean.getMobilePhone());
					u.setRecordStatus("Y");
					u.setIfManage("N");
					u.setCreateTime(new Date());
					u.setType("0");
					baseDao.save(u);
					return response(ErrorCodeEnum.SUCCESS.getCode(), 
							ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
				}else{//验证码不正确
					return response(ErrorCodeEnum.FAIL_CODE.getCode(), 
							ErrorCodeEnum.FAIL_CODE.getValue(), js, 0,null);
				}
			}
		}else{
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}


	@Override
	public ResponseHead getCode(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证
		if(StringUtil.isBlank(bean.getMobilePhone())||StringUtil.isBlank(bean.getFlag())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		//生成验证码，6位随机数
		int code=((int)(Math.random()*1000000))-1;
		//查询该手机号有没有存有验证码，有的话只是更新
		Code c1=(Code)baseDao.get(" from Code where phone=?", bean.getMobilePhone());
		if(c1==null){
			Code c=new Code();
			c.setPhone(bean.getMobilePhone());
			c.setCode(code+"");
			c.setCreateTime(new Date());
			baseDao.save(c);
		}else{
			c1.setCode(code+"");
			c1.setCreateTime(new Date());
			baseDao.update(c1);
		}
		
		js.put("code", code);
		
		try {
			AliMessageSend.sendSms(bean.getMobilePhone(), code+"",bean.getFlag());
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);	
	}

	@Override
	public ResponseHead myDetails(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证
		if(StringUtil.isBlank(bean.getId().toString())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		//token验证
		if(!tokenCheck(bean.getId(),bean.getToken())){
			return response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js, 0,null);
		}
		//返回昵称，性别，图片
		User u=(User)baseDao.get(" from User where id=?", bean.getId());
		//查询图片
		Image i=(Image)baseDao.get(" from Image where belongId=? and type='4'", bean.getId());
		String imgAdress="";
		if(i!=null){
			imgAdress=i.getAddress();
		}
		
		js.put("nickName", u.getNickName());
		js.put("sex", u.getSex());
		js.put("imgAdress", imgAdress);
		
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);	
	}

	@Override
	public ResponseHead editInfo(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证，id和token
		if(StringUtil.isBlank(bean.getId().toString())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		//token验证
		if(!tokenCheck(bean.getId(),bean.getToken())){
			return response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js, 0,null);
		}
		//有内容的就添加
		User u=(User)baseDao.get(" from User where id=?", bean.getId());
		
		if(StringUtil.isNotBlank(bean.getNickName())){//昵称
			u.setNickName(bean.getNickName());
			u.setModifyTime(new Date());
		}
		if(StringUtil.isNotBlank(bean.getSex())){//性别
			u.setSex(bean.getSex());
			u.setModifyTime(new Date());
		}
		//图片，存图片表，名字地址和belongid
		if(StringUtil.isNotBlank(bean.getImgAdress())){
			//有这个user的图片就更新，没有就添加
			Image i=(Image)baseDao.get(" from Image where belongId=? and type='4'", bean.getId());
			if(i!=null){
				i.setAddress(bean.getImgAdress());
				baseDao.update(i);
			}else{
				Image i2=new Image();
				i2.setAddress(bean.getImgAdress());
				i2.setType("4");
				i2.setBelongId(bean.getId());
				baseDao.save(i2);;
			}
			u.setModifyTime(new Date());
		}
		
		baseDao.update(u);
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}

	@Override
	public ResponseHead resetPwd(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证，手机号，验证码，密码
		if(StringUtil.isBlank(bean.getMobilePhone())||StringUtil.isBlank(bean.getCode())||StringUtil.isBlank(bean.getPassword())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		//验证手机号是否可用
		User u=(User)baseDao.get(" from User where mobilePhone=?", bean.getMobilePhone());
		if(u==null){
			return response(ErrorCodeEnum.FAIL_NOUSER.getCode(), 
					ErrorCodeEnum.FAIL_NOUSER.getValue(), js, 0,null);
		}
		//验证验证码
		Code c=(Code)baseDao.get(" from Code where phone=?", bean.getMobilePhone());
		if(c!=null&&c.getCode().equals(bean.getCode())){//验证成功，修改密码
			u.setPassword(Encrypt.md5(bean.getPassword()));
			u.setModifyTime(new Date());
			baseDao.update(u);
			return response(ErrorCodeEnum.SUCCESS.getCode(), 
					ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
		}else{//验证码不正确
			return response(ErrorCodeEnum.FAIL_CODE.getCode(), 
					ErrorCodeEnum.FAIL_CODE.getValue(), js, 0,null);
		}
	}

	@Override
	public ResponseHead editPwd(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证，用户id，密码，新密码
		if(StringUtil.isBlank(bean.getId().toString())||StringUtil.isBlank(bean.getPassword())
				||StringUtil.isBlank(bean.getNewPwd())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		//token验证
		if(!tokenCheck(bean.getId(),bean.getToken())){
			return response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js, 0,null);
		}
		//验证密码是否正确
		User u=(User)baseDao.get(" from User where id=? and password=?", bean.getId(),Encrypt.md5(bean.getPassword()));
		if(u!=null){
			u.setPassword(Encrypt.md5(bean.getNewPwd()));
			baseDao.update(u);
		}else{
			return response(ErrorCodeEnum.FAIL_USER.getCode(), 
					ErrorCodeEnum.FAIL_USER.getValue(), js, 0,null);
		}
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}

	@Override
	public ResponseHead myPhoto(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证，id和token
		if(StringUtil.isBlank(bean.getId().toString())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		//token验证
		if(!tokenCheck(bean.getId(),bean.getToken())){
			return response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js, 0,null);
		}
		//查找随手拍的title，内容，图片，状态
		List<Photo> list=baseDao.find(" from Photo where createUserId=?", bean.getId());
		List data=new ArrayList();
		for(int i=0;i<list.size();i++){
			JSONObject j1=new JSONObject();
			List image=baseDao.findBySql(" select group_concat(address)address from Image where type='2' and belongId="+list.get(i).getId()+"");
//			j1.put("title", list.get(i).getTitle());
			j1.put("content", list.get(i).getContent());
			j1.put("images", image.get(0));
			data.add(j1);
		}
		js.put("listData", data);
		
		return response(ErrorCodeEnum.FAIL_CODE.getCode(), 
				ErrorCodeEnum.FAIL_CODE.getValue(), js, 0,null);
	}

	@Override
	public ResponseHead myPublish(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证，id和token
		if(StringUtil.isBlank(bean.getId().toString())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		//token验证
		if(!tokenCheck(bean.getId(),bean.getToken())){
			return response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js, 0,null);
		}
		//查找文章的title，内容，图片
		List<Forum> list=baseDao.find(" from Forum where and createUserId=?", bean.getId());
		List data=new ArrayList();
		for(int i=0;i<list.size();i++){
			JSONObject j1=new JSONObject();
			List image=baseDao.findBySql(" select group_concat(address)address from Image where type='2' and belongId="+list.get(i).getId()+"");
			j1.put("title", list.get(i).getTitle());
			j1.put("content", list.get(i).getContent());
			j1.put("images", image.get(0));
			data.add(j1);
		}
		js.put("listData", data);
		
		return response(ErrorCodeEnum.FAIL_CODE.getCode(), 
				ErrorCodeEnum.FAIL_CODE.getValue(), js, 0,null);
	}

	@Override
	public ResponseHead myFound(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证，id和token
		if(StringUtil.isBlank(bean.getId().toString())||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		//token验证
		if(!tokenCheck(bean.getId(),bean.getToken())){
			return response(ErrorCodeEnum.FAIL_TOKENLOSE.getCode(), 
					ErrorCodeEnum.FAIL_TOKENLOSE.getValue(), js, 0,null);
		}
		
		
		
		
		return response(ErrorCodeEnum.FAIL_CODE.getCode(), 
				ErrorCodeEnum.FAIL_CODE.getValue(), js, 0,null);
	}

	@Override
	public ResponseHead otherLogin(UserBean bean) {
		JSONObject js=new JSONObject();
		//参数验证
		if(StringUtil.isBlank(bean.getNickName())||StringUtil.isBlank(bean.getSex())||StringUtil.isBlank(bean.getImgAdress())
				||StringUtil.isBlank(bean.getToken())){
			return response(ErrorCodeEnum.FAIL_PARAMSISNULL.getCode(), 
					ErrorCodeEnum.FAIL_PARAMSISNULL.getValue(), js, 0,null);
		}
		
		Long userId;
		
		
		//ios用openId验证是否登陆过
		User user=(User)baseDao.get(" from User where token='"+bean.getOpenId()+"'");
		if(user!=null){//脏数据用openid登陆过
			user.setToken(bean.getToken());
			baseDao.update(user);
		}
				
				
		//判断用户是否已经授权过
		user=(User)baseDao.get(" from User where token='"+bean.getToken()+"'");
		
		if(user!=null){
			user.setExpireTime(30L*1000L*24L*60L*60L+System.currentTimeMillis());
			userId=user.getId();
			baseDao.update(user);
		}else{
			//添加用户
			User u=new User();
			u.setNickName(bean.getNickName());
			u.setSex(bean.getSex());
			u.setToken(bean.getToken());
//			u.setExpireTime(9999999999999L);
			u.setExpireTime(30L*1000L*24L*60L*60L+System.currentTimeMillis());
			u.setRecordStatus("Y");
			u.setCreateTime(new Date());
			u.setType("2");
			u.setIfManage("N");
			baseDao.save(u);
			//存头像地址
			Image i=new Image();
			i.setAddress(bean.getImgAdress());
			i.setBelongId(u.getId());
			i.setType("4");
			baseDao.save(i);
			userId=u.getId();
		}
		
		
		//返回参数
		js.put("token", bean.getToken());
		js.put("userId", userId);
		js.put("type", "2");
		
		return response(ErrorCodeEnum.SUCCESS.getCode(), 
				ErrorCodeEnum.SUCCESS.getValue(), js, 0,null);
	}
	
}
