package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EventTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EventType and its DTO EventTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class, })
public interface EventTypeMapper extends EntityMapper <EventTypeDTO, EventType> {

    @Mapping(source = "event.id", target = "eventId")
    EventTypeDTO toDto(EventType eventType); 

    @Mapping(source = "eventId", target = "event")
    EventType toEntity(EventTypeDTO eventTypeDTO); 
    default EventType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventType eventType = new EventType();
        eventType.setId(id);
        return eventType;
    }
}
