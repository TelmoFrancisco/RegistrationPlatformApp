package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, })
public interface PersonMapper extends EntityMapper <PersonDTO, Person> {

    @Mapping(source = "location.id", target = "locationId")
    PersonDTO toDto(Person person); 

    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "events", ignore = true)
    Person toEntity(PersonDTO personDTO); 
    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
