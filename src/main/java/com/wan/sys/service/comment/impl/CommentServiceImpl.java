package com.wan.sys.service.comment.impl;

import com.wan.sys.dao.comment.ICommentDao;
import com.wan.sys.dao.image.IImageDao;
import com.wan.sys.entity.comment.Comment;
import com.wan.sys.entity.comment.CommentQuery;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.image.ImageTypeEnum;
import com.wan.sys.entity.user.UserInfo;
import com.wan.sys.service.comment.ICommentService;
import com.wan.sys.service.image.IImageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService{

    @Autowired
    ICommentDao commentDao;

    @Autowired
    IImageDao imageDao;

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

        List<Object> params = new ArrayList<Object>();

        String hql=" from Comment t where 1=1 " ;

        if (query.getBelongId() != null && query.getBelongId() > 0) {
            hql += " and t.belongId=? ";
            params.add(query.getBelongId());
        }

        if (StringUtils.isNotEmpty(query.getType())) {
            hql += " and t.type=? ";
            params.add(query.getType());
        }

        hql += " order by t.createTime desc ";

        if (query.getPage() > 0 && query.getRows() > 0) {
            List<Comment> comments = commentDao.find(hql, query.getPage(), query.getRows(), params);
            return comments;
        }
        List<Comment> comments  = commentDao.find(hql, params);
        return comments;
    }
}
