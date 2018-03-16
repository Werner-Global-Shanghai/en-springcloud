package com.werner.knowledge.event;

import com.werner.knowledge.management.rest.EventTypeInterface;
import com.werner.knowledge.management.rest.EventTypeInterfaceFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "management-service", path = "/api/manage", fallback = EventTypeInterfaceFallback.class)
public interface EventTypeResourceClient extends EventTypeInterface {
}
