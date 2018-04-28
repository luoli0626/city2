package com.wan.sys.service.image.impl;

import com.wan.sys.dao.image.IImageDao;
import com.wan.sys.entity.image.Image;
import com.wan.sys.service.image.IImageService;
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


    @Override
    public List<Image> getList(Image image) {

        if (image == null) {
            return new ArrayList<Image>();
        }

        String hql=" from Image t where 1=1 " ;
        List<Object> params = new ArrayList<Object>();
        if (image.getBelongId() != null && StringUtils.isNotEmpty(image.getType())) {

            hql += " and t.belongId=? and t.type=? ";
            params.add(image.getBelongId());
            params.add(image.getType());
        }

        return imageDao.find(hql, params);
    }
}
