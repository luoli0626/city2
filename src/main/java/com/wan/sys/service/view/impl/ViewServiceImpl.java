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
}
