package com.wan.sys.service.dynamic;

import com.wan.sys.entity.dynamic.Dynamic;
import com.wan.sys.entity.dynamic.DynamicQuery;

import java.util.List;

public interface IDynamicService {
    List<Dynamic> getDynamicList(DynamicQuery query);

    Dynamic getDynamicById(Long id);
}