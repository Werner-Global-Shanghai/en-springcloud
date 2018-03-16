package com.werner.knowledge.management.rest;

import com.werner.knowledge.management.model.EventType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventTypeInterfaceFallback extends EventTypeInterfaceAdapter {

    @Override
    public Optional<EventType> getEventTypeByCode(String code) {

        EventType eventType = new EventType();
        eventType.setName("MANAGEMENT_SERVICE_DOWN");

        return Optional.of(eventType);
    }
}
