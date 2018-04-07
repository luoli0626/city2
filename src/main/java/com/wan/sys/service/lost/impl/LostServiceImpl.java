package com.wan.sys.service.lost.impl;

import com.wan.sys.dao.lost.ILostDao;
import com.wan.sys.entity.lost.Lost;
import com.wan.sys.entity.lost.LostQuery;
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
    public void save(Lost lost) {

        if (lost != null) {
            lostDao.save(lost);
        }
    }

    @Override
    public List<Lost> getList(LostQuery query) {

        if (query == null) {
            return new ArrayList<Lost>();
        }

        String hql=" from Lost t where 1=1 " ;

        List<Lost> losts;
        if (query.getPage() > 0 && query.getRows() > 0) {
            losts = lostDao.find(hql, query.getPage(), query.getRows());
        } else {
            losts = lostDao.find(hql);
        }

        return losts;

    }
}
