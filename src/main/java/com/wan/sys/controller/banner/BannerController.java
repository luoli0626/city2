package com.wan.sys.controller.banner;

import com.wan.sys.entity.banner.Banner;
import com.wan.sys.entity.image.Image;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.banner.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("cityApp/banner")
public class BannerController {

    @Autowired
    IBannerService bannerService;

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList() {
        List<Banner> banners = bannerService.getList();
        if (banners != null) {
            for (Banner banner : banners) {

                if ("Y".equalsIgnoreCase(banner.getIsUrl()) && banner.getBannerImages() != null && banner.getBannerImages().size() > 0) {
                    banner.setImage(banner.getBannerImages().iterator().next());
                } else if (banner.getArticleImages() != null && banner.getArticleImages().size() > 0) {
                    banner.setImage(banner.getArticleImages().iterator().next());
                }

                banner.setArticleImages(null);
                banner.setBannerImages(null);
            }
        }
        return new ResponseSuccess(banners);
    }
}
