package com.wan.sys.service.message;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.message.Message;

import java.util.List;

public interface IMessageService {

    List<Message> getList(Query message);

    Message getById(Long id);
}
