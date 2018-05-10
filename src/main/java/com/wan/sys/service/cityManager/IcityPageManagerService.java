package com.wan.sys.service.cityManager;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.wan.sys.entity.cityManager.Fixed;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.pojo.CityBean;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.Json;

public interface IcityPageManagerService {

	/**
	 * 获取地址表格
	 * @param dg
	 * @param city
	 * @return
	 */
	public DataGridJson positionList(DataGridBean dg,CityBean city);
	
	/**
	 * 编辑或新添位置信息
	 * @param city
	 * @return
	 */
	public Boolean alterPosition(CityBean city);
	
	/**
	 * 删除地址信息
	 * @param city
	 * @return
	 */
	public Boolean removePositions(String[] ids);
	
	
	/**
	 * 政策资讯的列表数据
	 * @param dg
	 * @param city
	 * @return
	 */
	public DataGridJson messageList(DataGridBean dg,CityBean city);
	
	public Boolean alterMessage(CityBean city);
	
	public Boolean removeMessages(String[] ids);
	
	public Boolean changeMessages(String[] ids);
	
	public List<Fixed> positionTypeSelect();
	
	public DataGridJson photoList(DataGridBean dg,CityBean city);
	
	public List<Fixed> photoStateSelect();
	
	public Boolean changePhotoState(CityBean city);
	
	public DataGridJson dynamicList(DataGridBean dg,CityBean city);
	
	public Boolean alterDynamic(CityBean city);
	
	public Boolean removeDynamics(String[] ids);
	
	public Boolean changezDynamics(String[] ids);
	
	public DataGridJson guideList(DataGridBean dg,CityBean city);
	
	public Boolean alterGuide(CityBean city);
	
	public Boolean removeGuides(String[] ids);
	
	public Boolean changeGuides(String[] ids);

	public DataGridJson forumList(DataGridBean dg, CityBean city);
	
	public Boolean changeForumState(CityBean city);
	
	public Boolean removeForums(String[] ids);
	
	public DataGridJson bannerList(DataGridBean dg,CityBean city);
	
	public Boolean addBanner(CityBean city);
	
	public Boolean removeBanners(String[] ids);
	
	public Boolean changeBanners(String[] ids);
	
	public DataGridJson lostList(DataGridBean dg,CityBean city);
	
	public Boolean removeLost(String[] ids);
	
	public Photo photoDetail(String id);
	
}
