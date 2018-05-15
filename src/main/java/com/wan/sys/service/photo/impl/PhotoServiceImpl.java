package com.wan.sys.service.photo.impl;

import com.wan.sys.dao.photo.IPhotoDao;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.service.photo.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements IPhotoService{

    @Autowired
    IPhotoDao photoDao;

    @Override
    public Long add(Photo photo) {
        if (photo != null) {
            photo.setState("9");
            return photoDao.saveAndReturn(photo);
        }
        return 0L;
    }

    @Override
    public Photo getById(Long id) {
        if (id != null && id > 0) {
            return photoDao.get(Photo.class, id);
        }

        return null;
    }

    @Override
    public List<Photo> getList(Query query) {

        if (query == null) {
            return new ArrayList<Photo>();
        }

        String hql=" from Photo t where t.recordStatus='Y' " ;

        if (query.getCreateUserId() != null && query.getCreateUserId() > 0) {
            hql += " and t.createUserId=" + query.getCreateUserId();
        }

        hql += " order by t.createTime desc ";

        List<Photo> photos;
        if (query.getPage() > 0 && query.getRows() > 0) {
            photos = photoDao.find(hql, query.getPage(), query.getRows());
        } else {
            photos = photoDao.find(hql);
        }

        return photos;
    }

	@Override
	public Boolean remove(Long id) {
		Photo p=photoDao.get(Photo.class, id);
		if(p.getState().equals("9")&&p.getAllState().size()==1&&p.getAllState().get(0).getName().equals("待审核")){
			p.setRecordStatus("N");
			photoDao.update(p);
			return true;
		}else{
			return false;
		}
		
	}

}
