package com.wan.sys.dao.comment.impl;

import com.wan.sys.dao.comment.ICommentDao;
import com.wan.sys.dao.common.impl.BaseDaoImpl;
import com.wan.sys.entity.comment.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements ICommentDao {
}
