package com.wan.sys.service.dynamic;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.dynamic.Dynamic;

import java.util.List;

public interface IDynamicService {
    List<Dynamic> getList(Query query);

    Dynamic getById(Long id);
}