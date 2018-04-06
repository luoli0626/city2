package com.wan.sys.service.message;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.message.Message;

import java.util.List;

public interface IMessageService {

    List<Message> getMessageList(Query message);

    Message getMessageById(Long id);
}
