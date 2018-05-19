package com.wan.sys.dao.photo;

import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.entity.photo.Photo;

public interface IPhotoDao extends IBaseDao<Photo>{

    Long saveAndReturn(Photo photo);
    
    void updateAndReturn(Photo photo);
}
