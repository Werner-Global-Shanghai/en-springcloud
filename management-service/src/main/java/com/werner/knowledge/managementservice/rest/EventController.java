package com.werner.knowledge.managementservice.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@RestController
public class EventController {

    @GetMapping("/getdemo")
    public String getDemoEventType() throws UnknownHostException {

        String hostname = Inet4Address.getLocalHost().getCanonicalHostName();

        return hostname + " : Hello_event";
    }
}
