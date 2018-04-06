package com.wan.sys.controller.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.HouseBean;
import com.wan.sys.pojo.UserBean;
import com.wan.sys.service.house.IHouseFtlService;
@Controller
@RequestMapping("/houseftl")
public class HouseFtlController {
	/**
	 * 跳转到系统探针页面
	 * 
	 * @return
	 */
	
	@Autowired
	IHouseFtlService houseFtlService;
	
	@RequestMapping(value = "/projectList",method={RequestMethod.GET})
	public String projectList() {
		return "sys/house/projectList";
	}
	
	@RequestMapping(value = "/findProjectList",method={RequestMethod.POST})
	@ResponseBody
	public DataGridJson loginDatagrid(DataGridBean dg, HouseBean bean) {
		return houseFtlService.findProjectList(dg,bean);
	}
	
}
