package com.wan.sys.controller.lost;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.lost.Lost;
import com.wan.sys.entity.image.ImageTypeEnum;
import com.wan.sys.entity.message.Message;
import com.wan.sys.entity.view.View;
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

    private static final String IMG_TYPE = ImageTypeEnum.LOST.getIndex();

    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid @RequestBody Lost lost, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        if (lost.getImages() != null) {
            for (Image image : lost.getImages()) {
                image.setType(IMG_TYPE);
            }
        }
        lostService.add(lost);

        return OperateSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseSuccess(lostService.getList(query));
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {
        return new ResponseSuccess(lostService.getById(id));
    }
}
