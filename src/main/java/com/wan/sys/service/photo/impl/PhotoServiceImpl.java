package com.wan.sys.service.photo.impl;

import com.wan.sys.common.GlobalContext;
import com.wan.sys.controller.cityManager.WebSocketTest1;
import com.wan.sys.dao.common.IBaseDao;
import com.wan.sys.dao.photo.IPhotoDao;
import com.wan.sys.dao.photo.IPhotoTypeDao;
import com.wan.sys.entity.cityManager.PartToState;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.image.ImageTypeEnum;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.entity.photo.PhotoType;
import com.wan.sys.service.photo.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

@Service
public class PhotoServiceImpl implements IPhotoService{

    @Autowired
    IPhotoDao photoDao;

    @Autowired
    IPhotoTypeDao photoTypeDao;
    @Autowired
    IBaseDao baseDao;

    @Override
    public Long add(Photo photo) {
    	//向web前端发送消息
    	Session s=null;
		WebSocketTest1 ws=new WebSocketTest1();
		ws.onMessage("你有新的随手拍，请及时处理!",s);
		 
		 
    	//根据id判断新增或编辑
        if(photo.getId()==null){//新增
        	
	    	//写入跟进状态表
	        PartToState state = new PartToState();
	        state.setName("待审核");
	        List<PartToState> states = new ArrayList<PartToState>();
	        states.add(state);
	        photo.setAllState(states);
	
	        //保存新增图片
			if (photo.getImages() != null) {
		            for (Image image : photo.getImages()) {
		                image.setType(ImageTypeEnum.PHOTO.getIndex());
		            }
		        }
			
			 if (photo != null) {
		            photo.setState("9");
		            return photoDao.saveAndReturn(photo);
		        }
			 
        }else{//编辑
        	
        	Photo photo2=(Photo)baseDao.get(Photo.class, photo.getId());
        	//状态变为待审核
        	PartToState state = new PartToState();
	        state.setName("待审核");
	        state.setBelongId(photo2.getId());
	        List<PartToState> states = baseDao.find(" from PartToState where belongId=?", photo.getId());
	        states.add(state);
	        photo2.setAllState(states);
    		//删除之前的图片
    		baseDao.executeSql(" delete from city_part_to_images where TYPE='"+ImageTypeEnum.PHOTO.getIndex()+"' and BELONG_ID="+photo.getId());
    		
            //保存新增图片
    		Set<Image> images=new HashSet<Image>();
    		if (photo.getImages() != null) {
    	            for (Image image : photo.getImages()) {
    	                image.setType(ImageTypeEnum.PHOTO.getIndex());
    	                image.setBelongId(photo2.getId());
    	                images.add(image);
    	            }
    	        }
    		
    		photo2.setImages(images);
    		 if (photo2 != null) {
    			 photo2.setState("9");
    			 photo2.setCreateTime(new Date());
	            photoDao.update(photo2);
	        }
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

        String hql=" from Photo t where t.recordStatus='Y' and t.userDel='Y' " ;

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
		//向web前端发送消息，测试
//    	Session s=null;
//		WebSocketTest1 ws=new WebSocketTest1();
//		ws.onMessage("你有新的随手拍，请及时处理!",s);
		
		Photo p=photoDao.get(Photo.class, id);
		if(p.getState().equals("9")){
//			p.setRecordStatus("N");
			p.setUserDel("N");
			photoDao.update(p);
			return true;
		}else{
			return false;
		}
		
		
		
	}

    @Override
    public List<PhotoType> getTypeList(Query query) {
        if (query == null) {
            return new ArrayList<>();
        }

        String hql=" from PhotoType t where t.recordStatus='Y' " ;
        hql += " order by t.createTime desc ";

        List<PhotoType> types;
        if (query.getPage() > 0 && query.getRows() > 0) {
            types = photoTypeDao.find(hql, query.getPage(), query.getRows());
        } else {
            types = photoTypeDao.find(hql);
        }

        return types;
    }

    @Override
    public Long count(int state) {
        String hql = "select count(*) from Photo where recordStatus='Y' ";
        List<Object> params = new ArrayList<>();
        if (state > 0) {
            hql += " and state=? ";
            params.add(String.valueOf(state));
        }

        return photoDao.count(hql, params);
    }

}
