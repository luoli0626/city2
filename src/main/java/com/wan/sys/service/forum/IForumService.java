package com.wan.sys.service.forum;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.forum.Forum;

import java.util.List;

public interface IForumService {

    List<Forum> getList(Query query);

    Long add(Forum forum);

    Forum getById(Long id);
}
