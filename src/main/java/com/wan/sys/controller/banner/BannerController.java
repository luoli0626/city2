package com.wan.sys.controller.banner;

import com.wan.sys.entity.banner.Banner;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.image.ImageTypeEnum;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.banner.IBannerService;
import com.wan.sys.service.image.IImageService;
import org.apache.http.entity.ContentType;
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

    @Autowired
    IImageService imageService;

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList() {
        List<Banner> banners = bannerService.getList();
        for (Banner banner : banners) {
            Image image = new Image();
            if ("Y".equals(banner.getIsUrl())) {
                image.setType(ImageTypeEnum.BANNER.getIndex());
                image.setBelongId(banner.getId());
            } else if ("1".equals(banner.getArticleType())) {
                image.setType(ImageTypeEnum.DYNAMIC.getIndex());
                image.setBelongId(banner.getArticleId());
            } else {
                image.setType(ImageTypeEnum.MESSAGE.getIndex());
                image.setBelongId(banner.getArticleId());
            }

            List<Image> images = imageService.getList(image);
            if (image != null && images.size() > 0) {
                banner.setImage(images.get(0));
            }
        }
        return new ResponseSuccess(banners);
    }
}
