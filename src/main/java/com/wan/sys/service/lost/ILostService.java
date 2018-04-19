package com.wan.sys.service.lost;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.lost.Lost;

import java.util.List;

public interface ILostService {

    Lost add(Lost lost);

    List<Lost> getList(Query query);

    Lost getById(Long id);
}
