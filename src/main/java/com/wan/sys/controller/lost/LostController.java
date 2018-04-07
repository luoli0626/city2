package com.wan.sys.controller.lost;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.lost.Lost;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.lost.ILostService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("cityApp/lost")
public class LostController {

    @Autowired
    private ILostService lostService;

    @ResponseBody
    @RequestMapping("add")
    public ResponseHead add(@Valid Lost lost, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        lostService.add(lost);

        return ResponseSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseHead(lostService.getList(query));
    }
}
