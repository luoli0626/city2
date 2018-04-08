package com.wan.sys.service.message;

import com.wan.sys.entity.message.Message;
import com.wan.sys.entity.message.MessageQuery;

import java.util.List;

public interface IMessageService {

    List<Message> getMessageList(MessageQuery message);

    Message getMessageById(Long id);
}
