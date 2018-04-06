package com.wan.sys.service.house;

import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.HouseBean;

public interface IHouseFtlService {
	/**
	 * 加载智能接房页面的数据网格数据
	 * @return
	 */
	public DataGridJson findProjectList(DataGridBean dg, HouseBean bean);
}
