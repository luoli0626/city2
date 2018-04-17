package com.wan.sys.service.lost.impl;

import com.wan.sys.dao.lost.ILostDao;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.lost.Lost;
import com.wan.sys.service.lost.ILostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LostServiceImpl implements ILostService {

    @Autowired
    ILostDao lostDao;

    @Override
    public Lost add(Lost lost) {
        if (lost != null) {
            lost.setId(lostDao.saveAndReturn(lost));
        }

        return lost;
    }

    @Override
    public List<Lost> getList(Query query) {

        if (query == null) {
            return new ArrayList<Lost>();
        }

        String hql=" from Lost t where t.recordStatus='Y' " ;

        if (query.getCreateUserId() != null && query.getCreateUserId() > 0) {
            hql += " and t.createUserId=" + query.getCreateUserId();
        }

        hql += " order by t.createTime desc ";

        if (query.getPage() > 0 && query.getRows() > 0) {
            return lostDao.find(hql, query.getPage(), query.getRows());
        }
        return lostDao.find(hql);
    }

    @Override
    public Lost getById(Long id) {

        if (id != null && id > 0) {
            return lostDao.get(Lost.class, id);
        }

        return null;
    }
}
