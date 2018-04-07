package com.wan.sys.controller.lost;

import com.wan.sys.entity.lost.Lost;
import com.wan.sys.entity.lost.LostQuery;
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
@RequestMapping("cityApp")
public class LostController {

    @Autowired
    private ILostService lostService;

    @ResponseBody
    @RequestMapping("addLost")
    public ResponseHead addLost(@Valid Lost lost, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        lostService.save(lost);

        return ResponseSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getLostList")
    public ResponseHead getLostList(LostQuery query) {

        List<Lost> losts = lostService.getList(query);
        ResponseHead r = new ResponseHead();
        r.setData(losts);

        return r;
    }
}
