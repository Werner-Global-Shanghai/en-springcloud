package com.werner.knowledge.management.rest;

import com.werner.knowledge.management.model.EventType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class EventTypeInterfaceAdapter implements EventTypeInterface {

    @Override
    public ResponseEntity<EventType> createEventType(EventType eventType) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseEntity<EventType> updateEventType(EventType eventType) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseEntity<List<EventType>> getAllEventTypes(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<EventType> getEventType(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<EventType> getEventTypeByCode(String code) {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<Void> deleteEventType(Long id) {
        return null;
    }
}
