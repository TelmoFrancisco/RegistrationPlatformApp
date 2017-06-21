package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EventTypeDTO;
import java.util.List;

/**
 * Service Interface for managing EventType.
 */
public interface EventTypeService {

    /**
     * Save a eventType.
     *
     * @param eventTypeDTO the entity to save
     * @return the persisted entity
     */
    EventTypeDTO save(EventTypeDTO eventTypeDTO);

    /**
     *  Get all the eventTypes.
     *
     *  @return the list of entities
     */
    List<EventTypeDTO> findAll();

    /**
     *  Get the "id" eventType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EventTypeDTO findOne(Long id);

    /**
     *  Delete the "id" eventType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
