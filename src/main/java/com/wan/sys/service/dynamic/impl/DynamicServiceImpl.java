package com.wan.sys.service.dynamic.impl;

import com.wan.sys.dao.dynamic.IDynamicDao;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.dynamic.Dynamic;
import com.wan.sys.service.dynamic.IDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicServiceImpl implements IDynamicService {

    @Autowired
    IDynamicDao dynamicDao;

    @Override
    public List<Dynamic> getList(Query query) {

        String hql=" from Dynamic t where t.isOnline='Y' order by t.createTime desc ";

        List<Dynamic> dynamics;
        if (query.getPage() > 0 && query.getRows() > 0) {
            dynamics = dynamicDao.find(hql, query.getPage(), query.getRows());
        } else {
            dynamics = dynamicDao.find(hql);
        }

        return dynamics;
    }

    @Override
    public Dynamic getById(Long id) {

        if (id != null && id > 0) {
            return dynamicDao.get(Dynamic.class, id);
        }

        return null;
    }
}
