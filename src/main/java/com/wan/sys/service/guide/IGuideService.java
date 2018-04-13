package com.wan.sys.service.guide;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.guide.Guide;

import java.util.List;

public interface IGuideService {

    List<Guide> getList(Query guide);

    Guide getById(Long id);
}
