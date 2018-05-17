package com.wan.sys.dao.photo.impl;

import com.wan.sys.dao.common.impl.BaseDaoImpl;
import com.wan.sys.dao.photo.IPhotoDao;
import com.wan.sys.dao.photo.IPhotoTypeDao;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.entity.photo.PhotoType;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoTypeDaoImpl extends BaseDaoImpl<PhotoType> implements IPhotoTypeDao{

}
