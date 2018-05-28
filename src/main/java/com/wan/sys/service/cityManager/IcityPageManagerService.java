package com.wan.sys.service.cityManager;

import java.util.List;

import com.wan.sys.entity.photo.PhotoType;
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
	DataGridJson positionList(DataGridBean dg,CityBean city);
	
	/**
	 * 编辑或新添位置信息
	 * @param city
	 * @return
	 */
	Boolean alterPosition(CityBean city);
	
	/**
	 * 删除地址信息
	 * @param ids
	 * @return
	 */
	Boolean removePositions(String[] ids);
	
	
	/**
	 * 政策资讯的列表数据
	 * @param dg
	 * @param city
	 * @return
	 */
	DataGridJson messageList(DataGridBean dg,CityBean city);
	
	Boolean alterMessage(CityBean city);
	
	Boolean removeMessages(String[] ids);
	
	Boolean changeMessages(String[] ids);
	
	List<Fixed> positionTypeSelect();
	
	DataGridJson photoList(DataGridBean dg,CityBean city);
	
	List<Fixed> photoStateSelect();
	
	Boolean changePhotoState(CityBean city);
	
	DataGridJson dynamicList(DataGridBean dg,CityBean city);
	
	Boolean alterDynamic(CityBean city);
	
	Boolean removeDynamics(String[] ids);
	
	Boolean changezDynamics(String[] ids);
	
	DataGridJson guideList(DataGridBean dg,CityBean city);
	
	Boolean alterGuide(CityBean city);
	
	Boolean removeGuides(String[] ids);
	
	Boolean changeGuides(String[] ids);

	DataGridJson forumList(DataGridBean dg, CityBean city);
	
	Boolean changeForumState(CityBean city);
	
	Boolean removeForums(String[] ids);
	
	DataGridJson bannerList(DataGridBean dg,CityBean city);
	
	Boolean addBanner(CityBean city);
	
	Boolean removeBanners(String[] ids);
	
	Boolean changeBanners(String[] ids);
	
	DataGridJson lostList(DataGridBean dg,CityBean city);
	
	Boolean removeLost(String[] ids);
	
	Photo photoDetail(String id);
	
	DataGridJson dynamicCommentList(DataGridBean dg,CityBean city);
	
	Boolean removeDynamicComment(String[] ids);
	
	DataGridJson forumsCommentList(DataGridBean dg,CityBean city);
	
	Boolean removeForumsComment(String[] ids);
	
	Boolean removePhotos(String[] ids);

    DataGridJson photoTypeList(DataGridBean dg, String typeName);

    Boolean removePhotoType(String[] ids);

    Boolean alterPhotoType(PhotoType type);
    
    String createH5(String id);
    
    Boolean removeHandleImage(String id);
    
    Boolean addHandleImage(CityBean city);
    
    void createHistory(String type);
}
