package com.wan.sys.service.photo;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.photo.Photo;

import java.util.List;

public interface IPhotoService {

    long add(Photo photo);

    Photo getById(Long id);

    List<Photo> getList(Query query);
}
