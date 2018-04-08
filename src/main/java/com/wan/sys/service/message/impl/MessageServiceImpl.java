package com.wan.sys.service.message.impl;

import com.wan.sys.dao.message.IMessageDao;
import com.wan.sys.entity.message.Message;
import com.wan.sys.entity.message.MessageQuery;
import com.wan.sys.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService{

    @Autowired
    IMessageDao messageDao;

    @Override
    public List<Message> getMessageList(MessageQuery message) {
        String hql=" from Message t where 1=1 " ;
        if (message.isOnline()) {
            hql += " and t.isOnline=Y ";
        }

        List<Message> messages;

        if (message.getPage() > 0 && message.getRows() > 0) {
            messages = messageDao.find(hql, message.getPage(), message.getRows());
        } else {
            messages = messageDao.find(hql);
        }

        return messages;
    }

    @Override
    public Message getMessageById(Long id) {
        if (id != null && id > 0) {
            return messageDao.get(Message.class, id);
        }

        return null;
    }
}
