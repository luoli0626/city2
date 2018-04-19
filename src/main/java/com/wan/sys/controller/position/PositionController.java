package com.wan.sys.controller.position;

import com.wan.sys.entity.position.SearchQuery;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.position.IPositionService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("cityApp/position")
public class PositionController {

    @Autowired
    IPositionService positionService;

    @ResponseBody
    @RequestMapping("search")
    public ResponseHead search(@Valid SearchQuery query, BindingResult result, ServletRequest request) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseSuccess(positionService.getList(query));
    }
}
