package com.wan.sys.service.view.impl;

import com.wan.sys.dao.view.IViewDao;
import com.wan.sys.entity.view.View;
import com.wan.sys.service.view.IViewService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewServiceImpl implements IViewService {

    @Autowired
    IViewDao viewDao;

    @Override
    public void add(View view) {

        if (view != null) {
            viewDao.save(view);
        }
    }

    @Override
    public Long count(View view) {

        String hql = " select count(*) from View t where 1=1 ";

        List<Object> params = new ArrayList<Object>();
        if (view.getBelongId() != null && view.getBelongId() > 0) {
            hql += " and t.belongId=? ";
            params.add(view.getBelongId());
        }

        if (StringUtils.isNotEmpty(view.getType())) {
            hql += " and t.type=? ";
            params.add(view.getType());
        }

        Long count = viewDao.count(hql, params);
        return count == null ? 0 : count;
    }
}
