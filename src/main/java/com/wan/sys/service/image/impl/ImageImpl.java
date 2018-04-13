package com.wan.sys.service.image.impl;

import com.wan.sys.dao.image.IImageDao;
import com.wan.sys.entity.image.Image;
import com.wan.sys.service.image.IImageService;
import com.wan.sys.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageImpl implements IImageService {

    @Autowired
    IImageDao imageDao;

    @Override
    public void addImages(List<Image> images) {
        if (images != null) {
            imageDao.saveList(images);
        }
    }

//    @Override
//    public List<Image> getList(Image image) {
//
//        String hsql = " from Image t where 1=1 ";
//
//        if (image.getBelongId() != null && image.getBelongId() > 0) {
//            hsql += " and t.belongId=" + image.getBelongId();
//        }
//
//        if (StringUtils.isNotBlank(image.getType())) {
//            hsql += " and t.type=" + image.getType();
//        }
//
//        return imageDao.find(hsql);
//    }
}
