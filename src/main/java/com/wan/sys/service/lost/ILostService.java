package com.wan.sys.service.lost;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.lost.Lost;

import java.util.List;

public interface ILostService {

    void add(Lost lost);

    List<Lost> getList(Query query);
}