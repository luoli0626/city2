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
	public Json changePhotoState(CityBean city){
		Json j=new Json();
		j.setSuccess(cityPageService.changePhotoState(city));
		return j;
	}
	
}
