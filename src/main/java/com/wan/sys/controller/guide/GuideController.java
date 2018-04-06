package com.wan.sys.controller.guide;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.dynamic.Dynamic;
import com.wan.sys.entity.guide.Guide;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.guide.IGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("cityApp")
public class GuideController {

    @Autowired
    IGuideService guideService;

    @ResponseBody
    @RequestMapping("getGuideList")
    public ResponseHead getGuideList(Query guide) {

        List<Guide> guides = guideService.getGuideList(guide);
        ResponseHead r = new ResponseHead();
        r.setData(guides);

        return r;
    }

    @ResponseBody
    @RequestMapping("getGuideById")
    public ResponseHead getGuideById(Long id) {

        Guide guide = guideService.getGuideById(id);
        ResponseHead r = new ResponseHead();
        if (guide != null) {
            r.setData(guide);
        }

        return r;
    }
}
