package com.wan.sys.controller.dynamic;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.dynamic.Dynamic;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.dynamic.IDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("cityApp")
public class DynamicController {

    @Autowired
    private IDynamicService dynamicService;


    @ResponseBody
    @RequestMapping("getDynamicList")
    public ResponseHead getDynamicList(Query dynamic) {
        List<Dynamic> dynamics = dynamicService.getDynamicList(dynamic);
        ResponseHead r = new ResponseHead();
        r.setData(dynamics);

        return r;
    }

    @ResponseBody
    @RequestMapping("getDynamicById")
    public ResponseHead getDynamicById(Long id) {
        Dynamic dynamic = dynamicService.getDynamicById(id);
        ResponseHead r = new ResponseHead();
        if (dynamic != null) {
            r.setData(dynamic);
        }

        return r;
    }
}
