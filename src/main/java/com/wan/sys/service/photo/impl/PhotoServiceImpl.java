package com.wan.sys.service.photo.impl;

import com.wan.sys.dao.photo.IPhotoDao;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.service.photo.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PhotoServiceImpl implements IPhotoService{

    @Autowired
    IPhotoDao photoDao;

    @Override
    public void addPhoto(Photo photo) {
        if (photo != null) {
            Date now = new Date();
            photo.setCreateTime(now);
            photo.setModifyTime(now);
            photoDao.save(photo);
        }
    }

    @Override
    public Photo getPhotoById(Long id) {
        if (id != null && id > 0) {
            return photoDao.get(Photo.class, id);
        }

        return null;
    }
}
