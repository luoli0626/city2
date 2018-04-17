package com.wan.sys.service.position.impl;

import com.wan.sys.dao.position.IPositionDao;
import com.wan.sys.entity.cityManager.Position;
import com.wan.sys.entity.position.SearchQuery;
import com.wan.sys.service.position.IPositionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionServiceImpl implements IPositionService {

    @Autowired
    IPositionDao positionDao;

    @Override
    public List<Position> getList(SearchQuery query) {

        String hql = " from Position t where t.recordStatus='Y' ";

        List<Object> params = new ArrayList<Object>();
        if (query != null && StringUtils.isNotBlank(query.getKeywords())) {
            hql += " and t.name like ? ";
            params.add("%" + query.getKeywords() +"%");
        }

        return positionDao.find(hql, params);
    }
}