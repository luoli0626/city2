package com.wan.sys.controller.photo;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.photo.IPhotoService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.Valid;

@Controller
@RequestMapping("cityApp/photo")
public class PhotoController {

    @Autowired
    IPhotoService photoService;

    @ResponseBody
    @RequestMapping(value = "addPhoto")
    public ResponseHead addPhoto(@Valid Photo photo, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }
        photoService.add(photo);

        return ResponseSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {
        return new ResponseHead(photoService.getById(id));
    }

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseHead(photoService.getList(query));
    }
}
