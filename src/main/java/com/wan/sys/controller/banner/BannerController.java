package com.wan.sys.controller.banner;

import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.banner.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("cityApp/banner")
public class BannerController {

    @Autowired
    IBannerService bannerService;

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList() {
        return new ResponseSuccess(bannerService.getList());
    }
}
