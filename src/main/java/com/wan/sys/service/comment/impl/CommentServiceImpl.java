package com.wan.sys.service.comment.impl;

import com.wan.sys.dao.comment.ICommentDao;
import com.wan.sys.entity.comment.Comment;
import com.wan.sys.entity.comment.CommentQuery;
import com.wan.sys.service.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService{

    @Autowired
    ICommentDao commentDao;

    @Override
    public void add(Comment comment) {
        if (comment != null) {
            commentDao.save(comment);
        }
    }

    @Override
    public List<Comment> getList(CommentQuery query) {
        if (query == null) {
            return new ArrayList<Comment>();
        }

        String hql=" from Comment t where 1=1 " ;

        if (query.getDynamicId() != null && query.getDynamicId() > 0) {
            hql += " and t.dynamicId=" + query.getDynamicId();
        }

        if (query.getPage() > 0 && query.getRows() > 0) {
            return commentDao.find(hql, query.getPage(), query.getRows());
        }
        return commentDao.find(hql);
    }
}
