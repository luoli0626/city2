package com.wan.sys.controller.lost;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.lost.Lost;
import com.wan.sys.pojo.ImageTypeEnum;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.OperateSuccess;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.image.IImageService;
import com.wan.sys.service.lost.ILostService;
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

@Controller
@RequestMapping("cityApp/lost")
public class LostController {

    @Autowired
    private ILostService lostService;

    @Autowired
    private IImageService imageService;

    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid @RequestBody Lost lost, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        Long id = lostService.add(lost);

        for (Image image : lost.getImages()) {
            image.setType(ImageTypeEnum.LOST.getIndex());
            image.setBelongId(id);
        }

        imageService.addImages(lost.getImages());

        return OperateSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        List<Lost> lostList = lostService.getList(query);
        for (Lost lost : lostList) {
            lost.setImages(getImages(lost));
        }

        return new ResponseSuccess(lostList);
    }

    private List<Image> getImages(Lost lost) {
        Image image = new Image();
        image.setBelongId(lost.getId());
        image.setType(ImageTypeEnum.LOST.getIndex());

        return imageService.getList(image);
    }
}
