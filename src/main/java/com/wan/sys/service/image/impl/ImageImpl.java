package com.wan.sys.service.image.impl;

import com.wan.sys.dao.image.IImageDao;
import com.wan.sys.entity.image.Image;
import com.wan.sys.service.image.IImageService;
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
        imageDao.saveList(images);
    }

    @Override
    public List<Image> getImagesByBelongId(Long belongId) {
        if (belongId != null && belongId > 0) {
            String hsql = " from Image t where belongId=" + belongId;

            return imageDao.find(hsql);
        }

        return new ArrayList<Image>();
    }
}
