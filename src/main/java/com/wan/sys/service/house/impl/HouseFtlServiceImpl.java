package com.wan.sys.service.house.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.entity.organization.Organization;
import com.wan.sys.pojo.DataGridBean;
import com.wan.sys.pojo.DataGridJson;
import com.wan.sys.pojo.HouseBean;
import com.wan.sys.service.common.impl.CommonServiceImpl;
import com.wan.sys.service.house.IHouseFtlService;
import com.wan.sys.util.wanBeanUtils;

@Service
public class HouseFtlServiceImpl extends CommonServiceImpl implements IHouseFtlService{
	@Autowired
	IBaseDao baseDao;

	@Override
	@SuppressWarnings("unchecked")
	public DataGridJson findProjectList(DataGridBean dg, HouseBean oz) {
		DataGridJson j = new DataGridJson();
		String hql = " from Organization t  ";
		List<Object> values = new ArrayList<Object>();
		if (oz != null) {// 添加查询条件
			if (oz.getOrgCode() != null && !oz.getOrgCode().trim().equals("")) {
				hql += " and t.orgCode=?";
				values.add(oz.getOrgCode());
			}
			if (oz.getOrgName() != null && !oz.getOrgName().trim().equals("")) {
				hql += " and t.orgName like '%%" + oz.getOrgName().trim() + "%%' ";
			}
		} 
		hql=hql.replaceFirst("and", " where ");
		String totalHql = " select count(*) " + hql;
		j.setTotal(baseDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<Organization> syusers = baseDao.find(hql, dg.getPage(), dg.getRows(), values);// 查询分页

		List<Organization> ozs = new ArrayList<Organization>();
		if (syusers != null && syusers.size() > 0) {// 转换模型
			for (Organization syuser : syusers) {
				Organization u = new Organization();
				wanBeanUtils.copyPropertiesIgnoreNull(syuser, u);
				ozs.add(u);
			}
		}
		j.setRows(ozs);// 设置返回的行
		return j;
	}
}
