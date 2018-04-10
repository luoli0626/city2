package com.wan.sys.config;

import com.wan.sys.entity.SaveOrUpdateDateListener;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HibernateEventWiring {

    @Autowired
    private SessionFactory sessionFactory;

    @PostConstruct
    public void registerListeners() {

        EventListenerRegistry registry = ((SessionFactoryImpl)sessionFactory)
                                            .getServiceRegistry()
                                            .getService(EventListenerRegistry.class);
        SaveOrUpdateDateListener listener = new SaveOrUpdateDateListener();
        registry.getEventListenerGroup(EventType.SAVE_UPDATE).appendListener(listener);
    }

}
