package com.wan.sys.service.photo;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.entity.photo.PhotoType;

import java.util.List;

public interface IPhotoService {

    Long add(Photo photo);

    Photo getById(Long id);

    List<Photo> getList(Query query);
    
    Boolean remove(Long id);

    List<PhotoType> getTypeList(Query query);

    Long count(int state);
}
