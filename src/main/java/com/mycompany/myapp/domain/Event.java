package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Column(name = "init_date", nullable = false)
    private ZonedDateTime initDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EventType> eventTypes = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EventNotification> eventNotifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Event date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getInitDate() {
        return initDate;
    }

    public Event initDate(ZonedDateTime initDate) {
        this.initDate = initDate;
        return this;
    }

    public void setInitDate(ZonedDateTime initDate) {
        this.initDate = initDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Event endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public Event person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<EventType> getEventTypes() {
        return eventTypes;
    }

    public Event eventTypes(Set<EventType> eventTypes) {
        this.eventTypes = eventTypes;
        return this;
    }

    public Event addEventType(EventType eventType) {
        this.eventTypes.add(eventType);
        eventType.setEvent(this);
        return this;
    }

    public Event removeEventType(EventType eventType) {
        this.eventTypes.remove(eventType);
        eventType.setEvent(null);
        return this;
    }

    public void setEventTypes(Set<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Event locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Event addLocation(Location location) {
        this.locations.add(location);
        location.setEvent(this);
        return this;
    }

    public Event removeLocation(Location location) {
        this.locations.remove(location);
        location.setEvent(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<EventNotification> getEventNotifications() {
        return eventNotifications;
    }

    public Event eventNotifications(Set<EventNotification> eventNotifications) {
        this.eventNotifications = eventNotifications;
        return this;
    }

    public Event addEventNotification(EventNotification eventNotification) {
        this.eventNotifications.add(eventNotification);
        eventNotification.setEvent(this);
        return this;
    }

    public Event removeEventNotification(EventNotification eventNotification) {
        this.eventNotifications.remove(eventNotification);
        eventNotification.setEvent(null);
        return this;
    }

    public void setEventNotifications(Set<EventNotification> eventNotifications) {
        this.eventNotifications = eventNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", initDate='" + getInitDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
