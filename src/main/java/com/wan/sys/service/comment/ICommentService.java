package com.wan.sys.service.comment;

import com.wan.sys.entity.comment.Comment;
import com.wan.sys.entity.comment.CommentQuery;

import java.util.List;

public interface ICommentService {

    void add(Comment comment);
    List<Comment> getList(CommentQuery query);
}