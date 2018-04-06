package com.wan.sys.service.guide;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.dynamic.Dynamic;
import com.wan.sys.entity.guide.Guide;

import java.util.List;

public interface IGuideService {
    List<Guide> getGuideList(Query guide);

    Guide getGuideById(Long id);
}
