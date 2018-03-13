package com.werner.knowledge.event;

import com.werner.knowledge.management.rest.EventTypeInterface;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "management-service", path = "/api/manage")
public interface EventTypeResourceClient extends EventTypeInterface {
}
