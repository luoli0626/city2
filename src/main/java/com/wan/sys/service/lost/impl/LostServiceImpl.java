package com.wan.sys.service.lost.impl;

import com.wan.sys.dao.lost.ILostDao;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.lost.Lost;
import com.wan.sys.service.lost.ILostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LostServiceImpl implements ILostService {

    @Autowired
    ILostDao lostDao;

    @Override
    public Long add(Lost lost) {
        if (lost != null) {
            return lostDao.saveAndReturn(lost);
        }

        return 0L;
    }

    @Override
    public List<Lost> getList(Query query) {

        if (query == null) {
            return new ArrayList<Lost>();
        }

        String hql=" from Lost t where 1=1 " ;

        if (query.getCreateUserId() != null && query.getCreateUserId() > 0) {
            hql += " and t.createUserId=" + query.getCreateUserId();
        }

        List<Lost> losts;
        if (query.getPage() > 0 && query.getRows() > 0) {
            losts = lostDao.find(hql, query.getPage(), query.getRows());
        } else {
            losts = lostDao.find(hql);
        }

        return losts;
    }
}
