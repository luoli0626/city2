package com.wan.sys.service.photo;

import com.wan.sys.entity.photo.Photo;

public interface IPhotoService {
    void addPhoto(Photo photo);
    Photo getPhotoById(Long id);
}
