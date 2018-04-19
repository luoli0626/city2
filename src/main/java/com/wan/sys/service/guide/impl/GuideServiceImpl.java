package com.wan.sys.service.guide.impl;

import com.wan.sys.dao.guide.IGuideDao;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.guide.Guide;
import com.wan.sys.service.guide.IGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideServiceImpl implements IGuideService {

    @Autowired
    IGuideDao guideDao;

    @Override
    public List<Guide> getList(Query query) {

        String hql=" from Guide t where t.isOnline='Y' and t.recordStatus='Y' " +
                " order by t.createTime desc ";

        List<Guide> guides;
        if (query.getPage() > 0 && query.getRows() > 0) {
            guides = guideDao.find(hql, query.getPage(), query.getRows());
        } else {
            guides = guideDao.find(hql);
        }

        return guides;
    }

    @Override
    public Guide getById(Long id) {

        if (id != null && id > 0) {
            return guideDao.get(Guide.class, id);
        }

        return null;
    }
}
