package com.werner.knowledge.event.repository;

import com.werner.knowledge.event.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}
