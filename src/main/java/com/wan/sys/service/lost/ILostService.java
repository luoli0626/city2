package com.wan.sys.service.lost;

import com.wan.sys.entity.lost.Lost;
import com.wan.sys.entity.lost.LostQuery;

import java.util.List;

public interface ILostService {

    void save(Lost lost);

    List<Lost> getList(LostQuery query);
}
