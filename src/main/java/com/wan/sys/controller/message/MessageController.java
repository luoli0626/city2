package com.wan.sys.controller.message;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.message.Message;
import com.wan.sys.entity.view.View;
import com.wan.sys.entity.view.ViewTypeEnum;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.message.IMessageService;
import com.wan.sys.service.view.IViewService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("cityApp/message")
public class MessageController {

    @Autowired
    IMessageService msgService;

    @Autowired
    IViewService viewService;

    private static final String VIEW_TYPE = ViewTypeEnum.MESSAGE.toString();

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        List<Message> messages = msgService.getList(query);
        for (Message message : messages) {
            message.setViewCount(getViewCount(message));
        }

        return new ResponseSuccess(messages);
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {
        Message message = msgService.getById(id);

        if (message != null) {
            View view = new View();
            view.setBelongId(message.getId());
            view.setType(VIEW_TYPE);
            viewService.add(view);
        }

        return new ResponseSuccess(message);
    }

    private Long getViewCount(Message message) {

        View view = new View();
        view.setBelongId(message.getId());
        view.setType(VIEW_TYPE);

        return viewService.count(view);
    }
}
