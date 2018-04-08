package com.wan.sys.controller.photo;

import com.wan.sys.entity.photo.Photo;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.image.IImageService;
import com.wan.sys.service.photo.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("cityApp")
public class PhotoController {

    @Autowired
    IPhotoService photoService;

    @Autowired
    IImageService imageService;

    @ResponseBody
    @RequestMapping(value = "addPhoto", method = POST)
    public ResponseHead addPhoto(Photo photo) {
        //保存随手拍
        photoService.addPhoto(photo);

        return ResponseSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getPhotoById")
    public ResponseHead getPhotoById(Long id) {
        Photo photo = photoService.getPhotoById(id);
        ResponseHead r = new ResponseHead();
        if (photo != null) {
           r.setData(photo);
        }

        return r;
    }
}
