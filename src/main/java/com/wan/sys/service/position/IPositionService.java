package com.wan.sys.service.position;

import com.wan.sys.entity.cityManager.Position;
import com.wan.sys.entity.position.SearchQuery;

import java.util.List;

public interface IPositionService {

    List<Position> getList(SearchQuery query);
}
