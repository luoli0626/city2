package com.wan.sys.entity;

import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import java.util.Date;

public class SaveOrUpdateDateListener extends DefaultSaveOrUpdateEventListener {

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {

        if (event.getObject() instanceof LastModifiable) {
            LastModifiable record = (LastModifiable) event.getObject();
            record.setModifyTime(new Date());
        }

        super.onSaveOrUpdate(event);
    }
}
