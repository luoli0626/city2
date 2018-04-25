package com.wan.sys.controller.cityManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * @param city
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
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="changePhotoState")
	public Json changePhotoState(@RequestBody CityBean city){
		Json j=new Json();
		j.setSuccess(cityPageService.changePhotoState(city));
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
	 * @param bean
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
}
