package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.EventNotificationService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.EventNotificationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EventNotification.
 */
@RestController
@RequestMapping("/api")
public class EventNotificationResource {

    private final Logger log = LoggerFactory.getLogger(EventNotificationResource.class);

    private static final String ENTITY_NAME = "eventNotification";

    private final EventNotificationService eventNotificationService;

    public EventNotificationResource(EventNotificationService eventNotificationService) {
        this.eventNotificationService = eventNotificationService;
    }

    /**
     * POST  /event-notifications : Create a new eventNotification.
     *
     * @param eventNotificationDTO the eventNotificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventNotificationDTO, or with status 400 (Bad Request) if the eventNotification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-notifications")
    @Timed
    public ResponseEntity<EventNotificationDTO> createEventNotification(@Valid @RequestBody EventNotificationDTO eventNotificationDTO) throws URISyntaxException {
        log.debug("REST request to save EventNotification : {}", eventNotificationDTO);
        if (eventNotificationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eventNotification cannot already have an ID")).body(null);
        }
        EventNotificationDTO result = eventNotificationService.save(eventNotificationDTO);
        return ResponseEntity.created(new URI("/api/event-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-notifications : Updates an existing eventNotification.
     *
     * @param eventNotificationDTO the eventNotificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventNotificationDTO,
     * or with status 400 (Bad Request) if the eventNotificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventNotificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-notifications")
    @Timed
    public ResponseEntity<EventNotificationDTO> updateEventNotification(@Valid @RequestBody EventNotificationDTO eventNotificationDTO) throws URISyntaxException {
        log.debug("REST request to update EventNotification : {}", eventNotificationDTO);
        if (eventNotificationDTO.getId() == null) {
            return createEventNotification(eventNotificationDTO);
        }
        EventNotificationDTO result = eventNotificationService.save(eventNotificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventNotificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-notifications : get all the eventNotifications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eventNotifications in body
     */
    @GetMapping("/event-notifications")
    @Timed
    public List<EventNotificationDTO> getAllEventNotifications() {
        log.debug("REST request to get all EventNotifications");
        return eventNotificationService.findAll();
    }

    /**
     * GET  /event-notifications/:id : get the "id" eventNotification.
     *
     * @param id the id of the eventNotificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventNotificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/event-notifications/{id}")
    @Timed
    public ResponseEntity<EventNotificationDTO> getEventNotification(@PathVariable Long id) {
        log.debug("REST request to get EventNotification : {}", id);
        EventNotificationDTO eventNotificationDTO = eventNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventNotificationDTO));
    }

    /**
     * DELETE  /event-notifications/:id : delete the "id" eventNotification.
     *
     * @param id the id of the eventNotificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventNotification(@PathVariable Long id) {
        log.debug("REST request to delete EventNotification : {}", id);
        eventNotificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
