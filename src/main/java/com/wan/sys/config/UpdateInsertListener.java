package com.wan.sys.config;

import com.wan.sys.entity.CreateModifiedable;
import org.hibernate.event.spi.*;
import org.hibernate.tuple.StandardProperty;

import java.util.Date;

public class UpdateInsertListener implements PreUpdateEventListener, PreInsertEventListener{

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {

        if (event.getEntity() instanceof CreateModifiedable) {
            StandardProperty[] properties = event.getPersister().getEntityMetamodel().getProperties();
            Object[] state = event.getState();
            for (int i = 0; i < properties.length; i++) {
                StandardProperty property = properties[i];
                if ("modifyTime".equalsIgnoreCase(property.getName())) {
                    state[i] = new Date();
                }
            }
        }

        return false;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {

        if (event.getEntity() instanceof CreateModifiedable) {
            StandardProperty[] properties = event.getPersister().getEntityMetamodel().getProperties();
            Object[] state = event.getState();
            for (int i = 0; i < properties.length; i++) {
                StandardProperty property = properties[i];
                Date now = new Date();
                if ("modifyTime".equalsIgnoreCase(property.getName()) ||
                        "createTime".equalsIgnoreCase(property.getName())) {
                    state[i] = now;
                }
            }
        }

        return false;
    }
}
