package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.EventTypeService;
import com.mycompany.myapp.domain.EventType;
import com.mycompany.myapp.repository.EventTypeRepository;
import com.mycompany.myapp.service.dto.EventTypeDTO;
import com.mycompany.myapp.service.mapper.EventTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EventType.
 */
@Service
@Transactional
public class EventTypeServiceImpl implements EventTypeService{

    private final Logger log = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    private final EventTypeRepository eventTypeRepository;

    private final EventTypeMapper eventTypeMapper;

    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository, EventTypeMapper eventTypeMapper) {
        this.eventTypeRepository = eventTypeRepository;
        this.eventTypeMapper = eventTypeMapper;
    }

    /**
     * Save a eventType.
     *
     * @param eventTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EventTypeDTO save(EventTypeDTO eventTypeDTO) {
        log.debug("Request to save EventType : {}", eventTypeDTO);
        EventType eventType = eventTypeMapper.toEntity(eventTypeDTO);
        eventType = eventTypeRepository.save(eventType);
        return eventTypeMapper.toDto(eventType);
    }

    /**
     *  Get all the eventTypes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventTypeDTO> findAll() {
        log.debug("Request to get all EventTypes");
        return eventTypeRepository.findAll().stream()
            .map(eventTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one eventType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EventTypeDTO findOne(Long id) {
        log.debug("Request to get EventType : {}", id);
        EventType eventType = eventTypeRepository.findOne(id);
        return eventTypeMapper.toDto(eventType);
    }

    /**
     *  Delete the  eventType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventType : {}", id);
        eventTypeRepository.delete(id);
    }
}
