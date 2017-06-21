package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EventNotification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EventNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotification,Long> {
    
}
