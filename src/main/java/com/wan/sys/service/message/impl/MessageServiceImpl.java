package com.wan.sys.service.message.impl;

import com.wan.sys.dao.message.IMessageDao;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.message.Message;
import com.wan.sys.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService{

    @Autowired
    IMessageDao messageDao;

    @Override
    public List<Message> getMessageList(Query query) {

        String hql=" from Message t where 1=1 " ;
        if (query.isOnline()) {
            hql += " and t.isOnline=Y ";
        }

        List<Message> messages;
        if (query.getPage() > 0 && query.getRows() > 0) {
            messages = messageDao.find(hql, query.getPage(), query.getRows());
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
