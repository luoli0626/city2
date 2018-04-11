package com.wan.sys.config;

import com.wan.sys.entity.CreateModifiedable;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import java.util.Date;

public class UpdateInsertListener implements PreUpdateEventListener, PreInsertEventListener{

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {

        if (event.getEntity() instanceof CreateModifiedable) {
            CreateModifiedable e = (CreateModifiedable) event.getEntity();
            e.setModifyTime(new Date());
        }

        return false;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {

        if (event.getEntity() instanceof CreateModifiedable) {
            CreateModifiedable e = (CreateModifiedable) event.getEntity();
            Date now = new Date();
            e.setCreateTime(now);
            e.setModifyTime(now);
        }

        return false;
    }
}
