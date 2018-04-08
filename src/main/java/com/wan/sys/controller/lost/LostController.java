package com.wan.sys.controller.lost;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.lost.Lost;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.OperateSuccess;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.lost.ILostService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("cityApp/lost")
public class LostController {

    @Autowired
    private ILostService lostService;

    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid Lost lost, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
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
}
