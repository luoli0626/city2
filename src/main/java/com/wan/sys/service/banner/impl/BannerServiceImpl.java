package com.wan.sys.service.banner.impl;

import com.wan.sys.dao.banner.IBannerDao;
import com.wan.sys.entity.banner.Banner;
import com.wan.sys.service.banner.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BannerServiceImpl implements IBannerService {

    @Autowired
    IBannerDao bannerDao;

    @Override
    public List<Banner> getList() {

        String hql = " from Banner t where t.recordStatus='Y' order by t.orderNumber ";
        return bannerDao.find(hql, 1, 5);
    }
}
