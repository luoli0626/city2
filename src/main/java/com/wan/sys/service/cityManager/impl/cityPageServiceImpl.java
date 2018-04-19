package com.wan.sys.service.cityManager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wan.sys.common.GlobalContext;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.entity.cityManager.Fixed;
import com.wan.sys.entity.cityManager.PartToState;
import com.wan.sys.entity.cityManager.Position;
import com.wan.sys.entity.dynamic.Dynamic;
import com.wan.sys.entity.forum.Forum;
import com.wan.sys.entity.guide.Guide;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.message.Message;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.pojo.CityBean;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.Json;
import com.wan.sys.service.cityManager.IcityPageManagerService;
import com.wan.sys.service.common.impl.CommonServiceImpl;
import com.wan.sys.util.StringUtil;

@Service
public class cityPageServiceImpl extends CommonServiceImpl implements IcityPageManagerService{
	
	@Autowired
	IBaseDao baseDao;

	@Override
	public DataGridJson positionList(DataGridBean dg,CityBean city) {
		DataGridJson j = new DataGridJson();
		String hql=" from Position p where recordStatus='Y' ";
		if(StringUtils.isNotBlank(city.getPositionName())){
			hql+=" and p.type like '%%"+city.getPositionName()+"%%'";
		}
		String totalHql=" select count(*) "+hql;
		j.setTotal(baseDao.count(totalHql));
		if(dg.getOrder()!=null){
			hql+=" order by "+dg.getSort()+" " +dg.getOrder();
		}
		List<Position> positionList=baseDao.find(hql, dg.getPage(),dg.getRows());
		j.setRows(positionList);
		return j;
	}

	@Override
	public Boolean alterPosition(CityBean city) {
		if(StringUtils.isNotBlank(city.getPositionId())){//编辑
			Position p=(Position)baseDao.get(Position.class, Long.valueOf(city.getPositionId()));
			p.setName(city.getPositionName());
			p.setLongitude(city.getLongitude());
			p.setLatitude(city.getLatitude());
			p.setType(city.getPositionType());
			p.setModifyTime(new Date());
			p.setModifyUserId(GlobalContext.getCurrentUser().getId());
			baseDao.update(p);
			return false;
		}else{//添加，地点名不能重复
			Position p=new Position();
			p.setName(city.getPositionName());
			p.setLongitude(city.getLongitude());
			p.setLatitude(city.getLatitude());
			p.setType(city.getPositionType());
			p.setRecordStatus("Y");
			p.setCreateTime(new Date());
			p.setCreateUserId(GlobalContext.getCurrentUser().getId());
			p.setCreateUserName(GlobalContext.getCurrentUser().getNickName());
			baseDao.save(p);
			return true;
		}
	}

	@Override
	public Boolean removePositions(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" update `city_position` set RECORDSTATUS='N' where ID in("+ids+")");
		return true;
	}

	@Override
	public DataGridJson messageList(DataGridBean dg, CityBean city) {
		DataGridJson j = new DataGridJson();
		String hql=" from Message  where recordStatus='Y' ";
		if(StringUtils.isNotBlank(city.getMessageTitle())){
			hql+=" and title like '%%"+city.getMessageTitle()+"%%'";
		}
		if(StringUtils.isNotBlank(city.getMessageIsOnline())){
			hql+=" and isOnline='"+city.getMessageIsOnline()+" '";
		}
		if(StringUtil.isNotBlank(city.getStartTime())){
			hql+=" and createTime>'"+city.getStartTime()+"'";
		}
		if(StringUtil.isNotBlank(city.getEndTime())){
			hql +=" and createTime<'"+city.getEndTime()+"'";
		}
		String totalHql=" select count(*) "+hql;
		j.setTotal(baseDao.count(totalHql));
		if(dg.getOrder()!=null){
			hql+=" order by "+dg.getSort()+" " +dg.getOrder();
		}
		List<Message> positionList=baseDao.find(hql, dg.getPage(),dg.getRows());
		j.setRows(positionList);
		return j;
	}

	@Override
	public Boolean alterMessage(CityBean city) {
		if(StringUtils.isNotBlank(city.getMessageId())){//编辑，不支持更改封面图片，其他图片存在html
			Message p=(Message)baseDao.get(Message.class, Long.valueOf(city.getMessageId()));
			p.setTitle(city.getMessageTitle());
			p.setSubtitle(city.getMessageSubTitle());
			p.setContent(city.getMessageContent());
			p.setModifyTime(new Date());
			p.setModifyUserId(GlobalContext.getCurrentUser().getId());
			baseDao.update(p);
			return false;
		}else{//添加
			Message p=new Message();
			p.setTitle(city.getMessageTitle());
			p.setSubtitle(city.getMessageSubTitle());
			p.setContent(city.getMessageContent());
			p.setIsOnline("N");
			p.setRecordStatus("Y");
			p.setCreateTime(new Date());
			p.setCreateUserName(GlobalContext.getCurrentUser().getNickName());
			p.setCreateUserId(GlobalContext.getCurrentUser().getId());
			baseDao.save(p);
			//添加封面
			if(StringUtils.isNotBlank(city.getMessageImage())){
				Image i=new Image();
				i.setBelongId(p.getId());
				i.setType("1");
				i.setAddress(city.getMessageImage());
				baseDao.save(i);
			}
			return true;
		}
	}

	@Override
	public Boolean removeMessages(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" update `city_message` set RECORDSTATUS='N' where ID in("+ids+")");
		return true;
	}
	
	
	@Override
	public Boolean changeMessages(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" UPDATE `city_message` SET IS_ONLINE = CASE IS_ONLINE  WHEN 'Y' THEN 'N' WHEN 'N' THEN 'Y' END WHERE  ID in("+ids+")");
		return true;
	}

	@Override
	public List<Fixed> positionTypeSelect() {
		List<Fixed> list=baseDao.find(" from Fixed where type='position' ");
		return list;
	}

	@Override
	public DataGridJson photoList(DataGridBean dg, CityBean city) {
		DataGridJson j = new DataGridJson();
		String hql=" from Photo  where 1=1 ";
		if(StringUtil.isNotBlank(city.getPhotoName())){
			hql+=" and content like '%%"+city.getPhotoName()+"%%'";
		}
		if(StringUtil.isNotBlank(city.getState())){
			hql+=" and state='"+city.getState()+"'";
		}
		if(StringUtil.isNotBlank(city.getStartTime())){
			hql+=" and createTime>'"+city.getStartTime()+"'";
		}
		if(StringUtil.isNotBlank(city.getEndTime())){
			hql +=" and createTime<'"+city.getEndTime()+"'";
		}
		String totalHql=" select count(*) "+hql;
		j.setTotal(baseDao.count(totalHql));
		if(dg.getOrder()!=null){
			hql+=" order by "+dg.getSort()+" " +dg.getOrder();
		}
		List<Photo> photoList=baseDao.find(hql, dg.getPage(),dg.getRows());
		j.setRows(photoList);
		return j;
	}

	@Override
	public List<Fixed> photoStateSelect() {
		List<Fixed> list=baseDao.find(" from Fixed where type='photo' ");
		return list;
	}

	@Override
	public Boolean changePhotoState(CityBean city) {
		PartToState p=new PartToState();
		p.setBelongId(Long.valueOf(city.getPhotoId()));
		p.setName(city.getState());
		p.setContent(city.getRemark());
		p.setCreateTime(new Date());
		p.setCreateUserId(GlobalContext.getCurrentUser().getId());
		baseDao.save(p);
		Photo t=(Photo)baseDao.get(Photo.class, Long.valueOf(city.getPhotoId()));
		if(t!=null){
			t.setState(city.getCode());
			baseDao.update(t);
		}
		return true;
	}

	@Override
	public DataGridJson dynamicList(DataGridBean dg, CityBean city) {
		DataGridJson j = new DataGridJson();
		String hql=" from Dynamic  where recordStatus='Y' ";
		if(StringUtils.isNotBlank(city.getMessageTitle())){
			hql+=" and title like '%%"+city.getMessageTitle()+"%%'";
		}
		if(StringUtils.isNotBlank(city.getMessageIsOnline())){
			hql+=" and isOnline='"+city.getMessageIsOnline()+" '";
		}
		if(StringUtil.isNotBlank(city.getStartTime())){
			hql+=" and createTime>'"+city.getStartTime()+"'";
		}
		if(StringUtil.isNotBlank(city.getEndTime())){
			hql +=" and createTime<'"+city.getEndTime()+"'";
		}
		String totalHql=" select count(*) "+hql;
		j.setTotal(baseDao.count(totalHql));
		if(dg.getOrder()!=null){
			hql+=" order by "+dg.getSort()+" " +dg.getOrder();
		}
		List<Dynamic> dynamicList=baseDao.find(hql, dg.getPage(),dg.getRows());
		j.setRows(dynamicList);
		return j;
	}

	@Override
	public Boolean alterDynamic(CityBean city) {
		if(StringUtils.isNotBlank(city.getMessageId())){//编辑，不支持更改封面图片，其他图片存在html
			Dynamic p=(Dynamic)baseDao.get(Dynamic.class, Long.valueOf(city.getMessageId()));
			p.setTitle(city.getMessageTitle());
			p.setSubtitle(city.getMessageSubTitle());
			p.setContent(city.getMessageContent());
			p.setModifyTime(new Date());
			p.setModifyUserId(GlobalContext.getCurrentUser().getId());
			baseDao.update(p);
			return false;
		}else{//添加
			Dynamic p=new Dynamic();
			p.setTitle(city.getMessageTitle());
			p.setSubtitle(city.getMessageSubTitle());
			p.setContent(city.getMessageContent());
			p.setIsOnline("N");
			p.setRecordStatus("Y");
			p.setCreateTime(new Date());
			p.setCreateUserName(GlobalContext.getCurrentUser().getNickName());
			p.setCreateUserId(GlobalContext.getCurrentUser().getId());
			baseDao.save(p);
			//添加封面
			if(StringUtils.isNotBlank(city.getMessageImage())){
				Image i=new Image();
				i.setBelongId(p.getId());
				i.setType("3");
				i.setAddress(city.getMessageImage());
				baseDao.save(i);
			}
			return true;
		}
	}

	@Override
	public Boolean removeDynamics(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" update `city_dynamic` set RECORDSTATUS='N' where ID in("+ids+")");
		return true;
	}

	@Override
	public Boolean changezDynamics(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" UPDATE `city_dynamic` SET IS_ONLINE = CASE IS_ONLINE  WHEN 'Y' THEN 'N' WHEN 'N' THEN 'Y' END WHERE  ID in("+ids+")");
		return true;
	}

	@Override
	public DataGridJson guideList(DataGridBean dg, CityBean city) {
		DataGridJson j = new DataGridJson();
		String hql=" from Guide  where recordStatus='Y' ";
		if(StringUtils.isNotBlank(city.getMessageTitle())){
			hql+=" and title like '%%"+city.getMessageTitle()+"%%'";
		}
		if(StringUtils.isNotBlank(city.getMessageIsOnline())){
			hql+=" and isOnline='"+city.getMessageIsOnline()+" '";
		}
		if(StringUtil.isNotBlank(city.getStartTime())){
			hql+=" and createTime>'"+city.getStartTime()+"'";
		}
		if(StringUtil.isNotBlank(city.getEndTime())){
			hql +=" and createTime<'"+city.getEndTime()+"'";
		}
		String totalHql=" select count(*) "+hql;
		j.setTotal(baseDao.count(totalHql));
		if(dg.getOrder()!=null){
			hql+=" order by "+dg.getSort()+" " +dg.getOrder();
		}
		List<Guide> guideList=baseDao.find(hql, dg.getPage(),dg.getRows());
		j.setRows(guideList);
		return j;
	}

	@Override
	public Boolean alterGuide(CityBean city) {
		if(StringUtils.isNotBlank(city.getMessageId())){//编辑，不支持更改封面图片，其他图片存在html
			Guide p=(Guide)baseDao.get(Guide.class, Long.valueOf(city.getMessageId()));
			p.setTitle(city.getMessageTitle());
			p.setSubtitle(city.getMessageSubTitle());
			p.setContent(city.getMessageContent());
			p.setModifyTime(new Date());
			p.setModifyUserId(GlobalContext.getCurrentUser().getId());
			baseDao.update(p);
			return false;
		}else{//添加
			Guide p=new Guide();
			p.setTitle(city.getMessageTitle());
			p.setSubtitle(city.getMessageSubTitle());
			p.setContent(city.getMessageContent());
			p.setIsOnline("N");
			p.setRecordStatus("Y");
			p.setCreateTime(new Date());
			p.setCreateUserName(GlobalContext.getCurrentUser().getNickName());
			p.setCreateUserId(GlobalContext.getCurrentUser().getId());
			baseDao.save(p);
			return true;
		}
	}

	@Override
	public Boolean removeGuides(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" update `city_guide` set RECORDSTATUS='N' where ID in("+ids+")");
		return true;
	}

	@Override
	public Boolean changeGuides(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" UPDATE `city_guide` SET IS_ONLINE = CASE IS_ONLINE  WHEN 'Y' THEN 'N' WHEN 'N' THEN 'Y' END WHERE  ID in("+ids+")");
		return true;
	}

	@Override
	public DataGridJson forumList(DataGridBean dg, CityBean city) {
		DataGridJson j = new DataGridJson();
		String hql=" from Forum  where 1=1 ";
		if(StringUtil.isNotBlank(city.getPhotoName())){
			hql+=" and title like '%%"+city.getPhotoName()+"%%'";
		}
		if(StringUtil.isNotBlank(city.getState())){
			hql+=" and isCheck='"+city.getState()+"'";
		}
		if(StringUtil.isNotBlank(city.getStartTime())){
			hql+=" and createTime>'"+city.getStartTime()+"'";
		}
		if(StringUtil.isNotBlank(city.getEndTime())){
			hql +=" and createTime<'"+city.getEndTime()+"'";
		}
		String totalHql=" select count(*) "+hql;
		j.setTotal(baseDao.count(totalHql));
		if(dg.getOrder()!=null){
			hql+=" order by "+dg.getSort()+" " +dg.getOrder();
		}
		List<Forum> forumList=baseDao.find(hql, dg.getPage(),dg.getRows());
		j.setRows(forumList);
		return j;
	}

	@Override
	public Boolean changeForumState(CityBean city) {
		PartToState p=new PartToState();
		p.setBelongId(Long.valueOf(city.getPhotoId()));
		p.setName(city.getState());
		p.setCreateTime(new Date());
		p.setCreateUserId(GlobalContext.getCurrentUser().getId());
		baseDao.save(p);
		Forum t=(Forum)baseDao.get(Forum.class, Long.valueOf(city.getPhotoId()));
		if(t!=null){
			t.setIsCheck(city.getCode());
			baseDao.update(t);
		}
		return true;
	}

	@Override
	public Boolean removeForums(String[] id) {
		String ids="";
		for(int i=0;i<id.length;i++){
			if(i==id.length-1){
				ids+=id[i];
			}else{
				ids+=id[i]+",";
			}
		}	
		baseDao.executeSql(" update `city_forum` set RECORDSTATUS='N' where ID in("+ids+")");
		return true;
	}

}
