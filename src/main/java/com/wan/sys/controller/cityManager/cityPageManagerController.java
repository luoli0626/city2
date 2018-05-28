package com.wan.sys.controller.cityManager;

import java.util.List;

import com.wan.sys.entity.photo.PhotoType;
import com.wan.sys.service.photo.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.sys.entity.cityManager.Fixed;
import com.wan.sys.pojo.CityBean;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.Json;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.cityManager.IcityPageManagerService;

@Controller
@RequestMapping(value="cityPage")
public class cityPageManagerController {
	
	@Autowired
	IcityPageManagerService cityPageService;

    @Autowired
    IPhotoService photoService;
	//---------------------------------------------便民导航------------------------------------------
	
	
	
	/**
	 * 便民导航页面
	 * @return
	 */
	@RequestMapping(value="positionPage")
	public String positionPage(){
		return "/sys/city/position";
	}
	
	/**
	 * 地址的datagrid
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="positionList")
	public DataGridJson positionList(DataGridBean dg,CityBean city){
		return cityPageService.positionList(dg,city);
	}
	
	
	/**
	 * 修改地址信息，添加或编辑
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="alterPosition")
	public Json alterPosition(@RequestBody CityBean city){
		Json j=new Json();
		if(cityPageService.alterPosition(city)){
			j.setSuccess(true);
			j.setMsg("新增");
		}else{
			j.setSuccess(false);
			j.setMsg("编辑");
		}
		return j;
	}
	
	/**
	 * 删除位置
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removePositions")
	public Json removePositions(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removePositions(ids));
		return j;
	}
	
	/**
	 * 获取type。选择下拉框
	 * @return
	 */
	@RequestMapping(value="positionTypeSelect")
	@ResponseBody
	public List<Fixed> positionTypeSelect(){
		return cityPageService.positionTypeSelect();
	}
	
	//---------------------------------------------政策资讯----------------------------------------------
	
	/**
	 * 政策资讯页面
	 * @return
	 */
	@RequestMapping(value="messagePage")
	public String messagePage(){
		return "/sys/city/message";
	}
	
	
	/**
	 * 政策资讯的datagrid
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="messageList")
	public DataGridJson messageList(DataGridBean dg,CityBean city){
		return cityPageService.messageList(dg,city);
	}

	/**
	 * 政策资讯的新增编辑
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="alterMessage")
	public Json alterMessage(@RequestBody CityBean city){
		Json j=new Json();
		if(cityPageService.alterMessage(city)){
			j.setSuccess(true);
			j.setMsg("新增");
		}else{
			j.setSuccess(false);
			j.setMsg("编辑");
		}
		return j;
	}
	
	
	/**
	 * 删除政策资讯
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeMessages")
	public Json removeMessages(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeMessages(ids));
		return j;
	}
	
	
	/**
	 * 下线政策资讯
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="changeMessages")
	public Json changeMessages(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.changeMessages(ids));
		return j;
	}
	
	
	//------------------------------------------随手拍---------------------------------------------
	
	
	/**
	 * 随手拍页面
	 * @return
	 */
	@RequestMapping(value="photoPage")
	public String photoPage(){
		return "/sys/city/photo";
	}
	
	/**
	 * 随手拍详情页面
	 * @return
	 */
	@RequestMapping(value="photoDetailPage",method={RequestMethod.GET})
	public String photoDetailPage(Model model,String id){
		model.addAttribute("photo", cityPageService.photoDetail(id));
		return "/sys/city/photoDetail";
	}
	
	
	/**
	 * 随手拍列表
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="photoList")
	public DataGridJson photoList(DataGridBean dg,CityBean city){
		return cityPageService.photoList(dg,city);
	}

	@ResponseBody
    @RequestMapping(value = "photoCount")
	public Long photoCount() {
	    return photoService.count(9);
    }

	/**
	 * 获取随手拍状态下拉框
	 * @return
	 */
	@RequestMapping(value="photoStateSelect")
	@ResponseBody
	public List<Fixed> photoStateSelect(){
		return cityPageService.photoStateSelect();
	}
	
	/**
	 * 更改随手拍状态
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="changePhotoState")
	public Json changePhotoState(@RequestBody CityBean city){
		Json j=new Json();
		j.setSuccess(cityPageService.changePhotoState(city));
		return j;
	}
	
	/**
	 * 详情页添加处理留存图片
	 * @param imgAddr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addHandleImage")
	public Json addHandleImage(@RequestBody CityBean city){
		Json j=new Json();
		j.setSuccess(cityPageService.addHandleImage(city));
		return j;
	}
	
	/**
	 * 删除随手拍
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removePhotos")
	public Json removePhotos(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removePhotos(ids));
		return j;
	}

    /**
     * 随手拍类型页面
     * @return
     */
    @RequestMapping(value="photoTypePage")
    public String photoTypePage() {
        return "/sys/city/photoType";
    }

    /**
     * 随手拍类型列表
     * @param dg
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value="photoTypeList")
    public DataGridJson photoTypeList(DataGridBean dg, String name){
        return cityPageService.photoTypeList(dg, name);
    }

    @ResponseBody
    @RequestMapping(value="photoTypeSelect")
    public List<PhotoType> photoTypeSelect (){
        return cityPageService.photoTypeList(new DataGridBean(), null).getRows();
    }

    /**
     * 删除随手拍分类
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value="removePhotoType")
    public Json removePhotoType(@RequestBody String[] ids){
        Json j = new Json();
        j.setSuccess(cityPageService.removePhotoType(ids));
        return j;
    }

    /**
     * 随手拍类型的新增编辑
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value="alterPhotoType")
    public Json alterPhotoType(@RequestBody PhotoType type){
        Json j=new Json();
        if(cityPageService.alterPhotoType(type)){
            j.setSuccess(true);
            j.setMsg("新增");
        }else{
            j.setSuccess(false);
            j.setMsg("编辑");
        }
        return j;
    }
    
    /**
     * 随手拍生成H5
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value="createH5")
    public Json createH5(@RequestBody String id){
    	Json j=new Json();
    	j.setSuccess(true);
    	j.setMsg(cityPageService.createH5(id));
    	return j;
    }
    
    /**
     * 删除留存图片
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value="removeHandleImage")
    public Json removeHandleImage(@RequestBody String id){
    	Json j=new Json();
    	j.setSuccess(cityPageService.removeHandleImage(id));
    	return j;
    }
	
	//---------------------------------------------城管动态-------------------------------------------------
	
	/**
	 * 城管动态页面
	 * @return
	 */
	@RequestMapping(value="dynamicPage")
	public String dynamicPage(){
		return "/sys/city/dynamic";
	}
	
	
	/**
	 * 城管动态的datagrid
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="dynamicList")
	public DataGridJson dynamicList(DataGridBean dg,CityBean city){
		return cityPageService.dynamicList(dg,city);
	}

	/**
	 * 城管动态的新增编辑
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="alterDynamic")
	public Json alterDynamic(@RequestBody CityBean city){
		Json j=new Json();
		if(cityPageService.alterDynamic(city)){
			j.setSuccess(true);
			j.setMsg("新增");
		}else{
			j.setSuccess(false);
			j.setMsg("编辑");
		}
		return j;
	}
	
	
	/**
	 * 删除城管动态
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeDynamics")
	public Json removeDynamics(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeDynamics(ids));
		return j;
	}
	
	
	/**
	 * 下线城管动态
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="changeDynamics")
	public Json changezDynamics(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.changezDynamics(ids));
		return j;
	}
	
	/**
	 * 动态评论列表
	 * @param dg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="dynamicCommentList")
	public DataGridJson dynamicCommentList(DataGridBean dg,CityBean city){
		return cityPageService.dynamicCommentList(dg,city);
	}
	
	/**
	 * 删除动态的评论
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeDynamicComment")
	public Json removeDynamicComment(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeDynamicComment(ids));
		return j;
	}
	
	
	//----------------------------------------------------办事指南--------------------------------------------------------
	
	
	/**
	 * 办事页面
	 * @return
	 */
	@RequestMapping(value="guidePage")
	public String guidePage(){
		return "/sys/city/guide";
	}
	
	
	/**
	 * 办事指南的datagrid
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="guideList")
	public DataGridJson guideList(DataGridBean dg,CityBean city){
		return cityPageService.guideList(dg,city);
	}

	/**
	 * 办事指南的新增编辑
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="alterGuide")
	public Json alterGuide(@RequestBody CityBean city){
		Json j=new Json();
		if(cityPageService.alterGuide(city)){
			j.setSuccess(true);
			j.setMsg("新增");
		}else{
			j.setSuccess(false);
			j.setMsg("编辑");
		}
		return j;
	}
	
	
	/**
	 * 删除办事指南
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeGuides")
	public Json removeGuides(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeGuides(ids));
		return j;
	}
	
	
	/**
	 * 下线办事指南
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="changeGuides")
	public Json changeGuides(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.changeGuides(ids));
		return j;
	}
	
	
	
	
	//--------------------------------------------------社区论坛--------------------------------------------------------
	
	/**
	 * 社区论坛页面
	 * @return
	 */
	@RequestMapping(value="forumPage")
	public String forumPage(){
		return "/sys/city/forum";
	}
	
	/**
	 * 社区论坛列表
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="forumList")
	public DataGridJson forumList(DataGridBean dg,CityBean city){
		return cityPageService.forumList(dg,city);
	}
	
	
	
	/**
	 * 更改社区论坛状态
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="changeForumState")
	public Json changeForumState(@RequestBody CityBean city){
		Json j=new Json();
		j.setSuccess(cityPageService.changeForumState(city));
		return j;
	}
	
	/**
	 * 删除社区论坛
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeForums")
	public Json removeForums(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeForums(ids));
		return j;
	}
	
	/**
	 * 论坛评论列表
	 * @param dg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="forumsCommentList")
	public DataGridJson forumsCommentList(DataGridBean dg,CityBean city){
		return cityPageService.forumsCommentList(dg,city);
	}
	
	/**
	 * 删除论坛的评论
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeForumsComment")
	public Json removeForumsComment(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeForumsComment(ids));
		return j;
	}
	
	
	//------------------------------------------------------banner--------------------------------------------------------
	/**
	 * 轮播设置页面
	 * @return
	 */
	@RequestMapping(value="bannerPage")
	public String bannerPage(){
		return "/sys/city/banner";
	}
	
	/**
	 * 轮播设置列表
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="bannerList")
	public DataGridJson bannerList(DataGridBean dg,CityBean city){
		return cityPageService.bannerList(dg,city);
	}
	
	
	/**
	 * 新增banner
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addBanner")
	public Json addBanner(@RequestBody CityBean city){
		Json j=new Json();
		j.setSuccess(cityPageService.addBanner(city));
		return j;
	}
	
	/**
	 * 删除轮播
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeBanners")
	public Json removeBanners(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeBanners(ids));
		return j;
	}
	
	
	/**
	 * 更改轮播顺序
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="changeBanners")
	public Json changeBanners(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.changeBanners(ids));
		return j;
	}
	
	
	//------------------------------------------失物招领---------------------------------------------
	
	
	/**
	 * 失物招领页面
	 * @return
	 */
	@RequestMapping(value="lostPage")
	public String lostPage(){
		return "/sys/city/lost";
	}
	
	
	
	/**
	 * 失物招领列表
	 * @param dg
	 * @param city
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="lostList")
	public DataGridJson lostList(DataGridBean dg,CityBean city){
		return cityPageService.lostList(dg,city);
	}
	
	
	
	/**
	 * 删除失物招领
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeLost")
	public Json removeLost(@RequestBody String[] ids){
		Json j=new Json();
		j.setSuccess(cityPageService.removeLost(ids));
		return j;
	}

	/**
	 * 生成咨询和动态的历史数据的h5
	 * 1,dynamic
	 * 2.message
	 * @param type
	 */
	@RequestMapping(value="createHistory")
	@ResponseBody
	public void createHistory(String type){
		cityPageService.createHistory(type);
	}
}
