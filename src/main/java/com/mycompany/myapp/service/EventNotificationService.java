package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EventNotificationDTO;
import java.util.List;

/**
 * Service Interface for managing EventNotification.
 */
public interface EventNotificationService {

    /**
     * Save a eventNotification.
     *
     * @param eventNotificationDTO the entity to save
     * @return the persisted entity
     */
    EventNotificationDTO save(EventNotificationDTO eventNotificationDTO);

    /**
     *  Get all the eventNotifications.
     *
     *  @return the list of entities
     */
    List<EventNotificationDTO> findAll();

    /**
     *  Get the "id" eventNotification.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EventNotificationDTO findOne(Long id);

    /**
     *  Delete the "id" eventNotification.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
