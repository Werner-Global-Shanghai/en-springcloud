package com.werner.knowledge.event.rest;

import com.werner.knowledge.event.EventTypeResourceClient;
import com.werner.knowledge.event.model.Event;
import com.werner.knowledge.event.model.EventLog;
import com.werner.knowledge.event.repository.EventLogRepository;
import com.werner.knowledge.event.repository.EventRepository;
import com.werner.knowledge.event.rest.util.HeaderUtil;
import com.werner.knowledge.event.rest.util.PaginationUtil;
import com.werner.knowledge.management.model.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "event";

    private final EventRepository eventRepository;
    private final EventLogRepository eventLogRepository;

    @Autowired
    private EventTypeResourceClient eventTypeResourceClient;

    public EventResource(EventRepository eventRepository, EventLogRepository eventLogRepository) {
        this.eventRepository = eventRepository;
        this.eventLogRepository = eventLogRepository;
    }

    /**
     * POST  /events : Create a new event.
     *
     * @param event the event to create
     * @return the ResponseEntity with status 201 (Created) and with body the new event, or with status 400 (Bad Request) if the event has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) throws URISyntaxException {
        log.debug("REST request to save Event : {}", event);

        event.setCorrelationId(UUID.randomUUID().toString());

        Optional<EventType> eventType = eventTypeResourceClient.getEventTypeByCode(event.getEventTypeCode());

        try {
            String createdBy = InetAddress.getLocalHost().getCanonicalHostName();
            event.setCreatedBy(createdBy);

        } catch (UnknownHostException e) {

            log.warn(e.getMessage());
        }

        if (!eventType.isPresent()) {
            throw new RuntimeException("fatal error: the event type not exists!");
        }

        boolean managementServiceDown = false;
        if (eventType.get().getName().equalsIgnoreCase("MANAGEMENT_SERVICE_DOWN")) {

            managementServiceDown = true;
            log.warn("Management service is down.");
        }

        Event result = eventRepository.save(event);

        EventLog log = new EventLog();
        log.setStatus("Created");
        log.setCorrelationId(result.getCorrelationId());
        log.setMessage("New message created");

        if (managementServiceDown) {
            log.setStatus("Unknown Type Event");
            log.setMessage("Event management service is down.");
        }
        eventLogRepository.save(log);

        return ResponseEntity.created(new URI("/api/events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * GET  /events : get all the events.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of events in body
     */
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(Pageable pageable) {
        log.debug("REST request to get a page of Events");
        Page<Event> page = eventRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /events/:id : get the "id" event.
     *
     * @param id the id of the event to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the event, or with status 404 (Not Found)
     */
    @GetMapping("/events/{id}")
    public Optional<Event> getEvent(@PathVariable Long id) {
        log.debug("REST request to get Event : {}", id);
        return eventRepository.findById(id);
    }

    /**
     * DELETE  /events/:id : delete the "id" event.
     *
     * @param id the id of the event to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.debug("REST request to delete Event : {}", id);
        eventRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
