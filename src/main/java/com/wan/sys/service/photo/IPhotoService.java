package com.wan.sys.service.photo;

import com.wan.sys.entity.photo.Photo;

public interface IPhotoService {
    long addPhoto(Photo photo);
    Photo getPhotoById(Long id);
}
