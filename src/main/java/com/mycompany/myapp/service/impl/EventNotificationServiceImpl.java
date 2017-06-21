package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.EventNotificationService;
import com.mycompany.myapp.domain.EventNotification;
import com.mycompany.myapp.repository.EventNotificationRepository;
import com.mycompany.myapp.service.dto.EventNotificationDTO;
import com.mycompany.myapp.service.mapper.EventNotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EventNotification.
 */
@Service
@Transactional
public class EventNotificationServiceImpl implements EventNotificationService{

    private final Logger log = LoggerFactory.getLogger(EventNotificationServiceImpl.class);

    private final EventNotificationRepository eventNotificationRepository;

    private final EventNotificationMapper eventNotificationMapper;

    public EventNotificationServiceImpl(EventNotificationRepository eventNotificationRepository, EventNotificationMapper eventNotificationMapper) {
        this.eventNotificationRepository = eventNotificationRepository;
        this.eventNotificationMapper = eventNotificationMapper;
    }

    /**
     * Save a eventNotification.
     *
     * @param eventNotificationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EventNotificationDTO save(EventNotificationDTO eventNotificationDTO) {
        log.debug("Request to save EventNotification : {}", eventNotificationDTO);
        EventNotification eventNotification = eventNotificationMapper.toEntity(eventNotificationDTO);
        eventNotification = eventNotificationRepository.save(eventNotification);
        return eventNotificationMapper.toDto(eventNotification);
    }

    /**
     *  Get all the eventNotifications.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventNotificationDTO> findAll() {
        log.debug("Request to get all EventNotifications");
        return eventNotificationRepository.findAll().stream()
            .map(eventNotificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one eventNotification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EventNotificationDTO findOne(Long id) {
        log.debug("Request to get EventNotification : {}", id);
        EventNotification eventNotification = eventNotificationRepository.findOne(id);
        return eventNotificationMapper.toDto(eventNotification);
    }

    /**
     *  Delete the  eventNotification by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventNotification : {}", id);
        eventNotificationRepository.delete(id);
    }
}
