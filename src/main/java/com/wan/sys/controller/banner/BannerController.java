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
import java.util.Set;

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
                Set<Image> articleImages = banner.getArticleImages();
                Set<Image> bannerImages =  banner.getBannerImages();
                if ("Y".equalsIgnoreCase(banner.getIsUrl()) && articleImages != null && articleImages.size() > 0) {
                    banner.setImage(articleImages.iterator().next());
                } else if (bannerImages != null && bannerImages.size() > 0) {
                    banner.setImage(bannerImages.iterator().next());
                }

                banner.setArticleImages(null);
                banner.setBannerImages(null);
            }
        }
        return new ResponseSuccess(banners);
    }
}
