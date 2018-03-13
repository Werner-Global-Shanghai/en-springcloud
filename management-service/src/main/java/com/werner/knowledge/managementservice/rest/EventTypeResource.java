package com.werner.knowledge.managementservice.rest;


import com.werner.knowledge.management.model.EventType;
import com.werner.knowledge.management.rest.EventTypeInterface;
import com.werner.knowledge.managementservice.repository.EventTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class EventTypeResource implements EventTypeInterface{

    private final Logger log = LoggerFactory.getLogger(EventTypeResource.class);

    private final EventTypeRepository eventTypeRepository;

    public EventTypeResource(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    public ResponseEntity<EventType> createEventType(@RequestBody EventType eventType) throws URISyntaxException {

        EventType result = eventTypeRepository.save(eventType);
        return ResponseEntity.created(new URI("/api/event-types/" + result.getId()))
            .body(result);
    }

    public ResponseEntity<EventType> updateEventType(@RequestBody EventType eventType) throws URISyntaxException {
        log.debug("REST request to update EventType : {}", eventType);
        if (eventType.getId() == null) {
            return createEventType(eventType);
        }
        EventType result = eventTypeRepository.save(eventType);
        return ResponseEntity.ok()
            .body(result);
    }

    public ResponseEntity<List<EventType>> getAllEventTypes(Pageable pageable) {
        log.debug("REST request to get a page of EventTypes");
        Page<EventType> page = eventTypeRepository.findAll(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    public Optional<EventType> getEventType(@PathVariable Long id) {
        log.debug("REST request to get EventType : {}", id);
        return eventTypeRepository.findById(id);
    }

    public ResponseEntity<Void> deleteEventType(@PathVariable Long id) {
        log.debug("REST request to delete EventType : {}", id);
        eventTypeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
