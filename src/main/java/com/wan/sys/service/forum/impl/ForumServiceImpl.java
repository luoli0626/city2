package com.wan.sys.service.forum.impl;

import com.wan.sys.dao.forum.IForumDao;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.forum.Forum;
import com.wan.sys.service.forum.IForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ForumServiceImpl implements IForumService {

    @Autowired
    IForumDao forumDao;

    @Override
    public Long add(Forum forum) {
        if (forum != null) {
            return forumDao.saveAndReturn(forum);
        }
        return 0L;
    }

    @Override
    public List<Forum> getList(Query query) {

        if (query == null || query.getPage() <= 0 && query.getRows() <= 0) {
            return new ArrayList<Forum>();
        }

        String hql=" from Forum t where  t.recordStatus='Y' " ;

        if (query.getCreateUserId() != null && query.getCreateUserId() > 0) {
            hql += " and t.createUserId=" + query.getCreateUserId();
        } else {
            hql += " and t.isCheck='Y' ";
        }

        hql += " order by t.createTime desc ";
        List<Forum> forums = forumDao.find(hql, query.getPage(), query.getRows());

        for (Forum forum : forums) {
            forum.setAllState(null);
            forum.setCreateUserName(null);
        }
        return forums;
    }

    @Override
    public Forum getById(Long id) {

        if (id != null && id > 0) {
            return forumDao.get(Forum.class, id);
        }

        return null;
    }
}
