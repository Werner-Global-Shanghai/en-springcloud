package com.werner.knowledge.event.rest;

import com.werner.knowledge.event.model.EventLog;
import com.werner.knowledge.event.repository.EventLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class EventLogResource {

    private final EventLogRepository eventLogRepository;

    public EventLogResource(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @GetMapping("/all")
    public List<EventLog> getAllLogs() {
        return eventLogRepository.findAll();
    }

    @PostMapping("clearall")
    public void clearAll() {

        eventLogRepository.deleteAll();
    }
}
