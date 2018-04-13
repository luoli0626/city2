package com.wan.sys.controller.photo;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.photo.Photo;
import com.wan.sys.entity.image.ImageTypeEnum;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.OperateSuccess;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.image.IImageService;
import com.wan.sys.service.photo.IPhotoService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 随手拍接口
 */
@Controller
@RequestMapping("cityApp/photo")
public class PhotoController {

    @Autowired
    IPhotoService photoService;

    @Autowired
    IImageService imageService;

    /**
     * 新增随手拍接口
     * @param photo
     * @param result
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid @RequestBody Photo photo, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }
        Long id = photoService.add(photo);

        // 有图片的需要存图片表
        if (photo.getImages() != null) {

            for (Image image : photo.getImages()) {
                image.setType(ImageTypeEnum.PHOTO.getIndex());
                image.setBelongId(id);
            }

            imageService.addImages(photo.getImages());
        }

        return OperateSuccess.Instance();
    }

    /**
     * 获取随手拍详情接口
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {
        return new ResponseSuccess(photoService.getById(id));
    }

    /**
     * 获取随手拍列表接口
     * @param query
     * @param result
     * @return
     */
    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }
        return new ResponseSuccess(photoService.getList(query));
    }
}
