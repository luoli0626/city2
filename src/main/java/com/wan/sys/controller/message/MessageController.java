package com.wan.sys.controller.message;

import com.wan.sys.entity.message.Message;
import com.wan.sys.entity.message.MessageQuery;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("cityApp")
public class MessageController {

    @Autowired
    IMessageService messageService;

    @ResponseBody
    @RequestMapping("getMessageList")
    public ResponseHead getMessageList(MessageQuery message) {
        List<Message> messages = messageService.getMessageList(message);
        ResponseHead r = new ResponseHead();
        r.setData(messages);

        return r;
    }

    @ResponseBody
    @RequestMapping("getMessageById")
    public ResponseHead getMessageById(Long id) {
        Message message = messageService.getMessageById(id);
        ResponseHead r = new ResponseHead();
        if (message != null) {
            r.setData(message);
        }

        return r;
    }
}
