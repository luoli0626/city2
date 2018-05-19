package com.wan.sys.dao.photo.impl;

import com.wan.sys.dao.common.impl.BaseDaoImpl;
import com.wan.sys.dao.photo.IPhotoDao;
import com.wan.sys.entity.photo.Photo;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoDaoImpl extends BaseDaoImpl<Photo> implements IPhotoDao{

    @Override
    public Long saveAndReturn(Photo photo) {
        return (Long) (this.getCurrentSession().save(photo));
    }

	@Override
	public void updateAndReturn(Photo photo) {
		this.getCurrentSession().update(photo);
	}
}
