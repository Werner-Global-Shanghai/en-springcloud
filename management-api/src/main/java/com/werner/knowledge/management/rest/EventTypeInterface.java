package com.werner.knowledge.management.rest;

import com.werner.knowledge.management.model.EventType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EventType.
 */
public interface EventTypeInterface {

    /**
     * POST  /event-types : Create a new eventType.
     *
     * @param eventType the eventType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventType, or with status 400 (Bad Request) if the eventType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-types")
    ResponseEntity<EventType> createEventType(@RequestBody EventType eventType) throws URISyntaxException;

    /**
     * PUT  /event-types : Updates an existing eventType.
     *
     * @param eventType the eventType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventType,
     * or with status 400 (Bad Request) if the eventType is not valid,
     * or with status 500 (Internal Server Error) if the eventType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-types")
    ResponseEntity<EventType> updateEventType(@RequestBody EventType eventType) throws URISyntaxException;

    /**
     * GET  /event-types : get all the eventTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventTypes in body
     */
    @GetMapping("/event-types")
    ResponseEntity<List<EventType>> getAllEventTypes(Pageable pageable);

    /**
     * GET  /event-types/:id : get the "id" eventType.
     *
     * @param id the id of the eventType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventType, or with status 404 (Not Found)
     */
    @GetMapping("/event-types/byid/{id}")
    Optional<EventType> getEventType(@PathVariable("id") Long id);

    @GetMapping("/event-types/bycode/{code}")
    Optional<EventType> getEventTypeByCode(@RequestHeader("code") String code);

    /**
     * DELETE  /event-types/:id : delete the "id" eventType.
     *
     * @param id the id of the eventType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-types/{id}")
    ResponseEntity<Void> deleteEventType(@PathVariable("id") Long id);
}
