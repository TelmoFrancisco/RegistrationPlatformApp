package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, })
public interface EventMapper extends EntityMapper <EventDTO, Event> {

    @Mapping(source = "person.id", target = "personId")
    EventDTO toDto(Event event); 

    @Mapping(source = "personId", target = "person")
    @Mapping(target = "eventTypes", ignore = true)
    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "eventNotifications", ignore = true)
    Event toEntity(EventDTO eventDTO); 
    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
