package com.wan.sys.controller.dynamic;

import com.wan.sys.entity.common.Query;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.dynamic.IDynamicService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("cityApp/dynamic")
public class DynamicController {

    @Autowired
    private IDynamicService dynamicService;


    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseHead(dynamicService.getList(query));
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {
        return new ResponseHead(dynamicService.getById(id));
    }
}
