package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EventNotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EventNotification and its DTO EventNotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class, })
public interface EventNotificationMapper extends EntityMapper <EventNotificationDTO, EventNotification> {

    @Mapping(source = "event.id", target = "eventId")
    EventNotificationDTO toDto(EventNotification eventNotification); 

    @Mapping(source = "eventId", target = "event")
    EventNotification toEntity(EventNotificationDTO eventNotificationDTO); 
    default EventNotification fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventNotification eventNotification = new EventNotification();
        eventNotification.setId(id);
        return eventNotification;
    }
}
