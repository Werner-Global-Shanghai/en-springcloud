package com.werner.knowledge.managementservice.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @GetMapping("/getdemo")
    public String getDemoEventType() {
        return "Hello_event";
    }
}
