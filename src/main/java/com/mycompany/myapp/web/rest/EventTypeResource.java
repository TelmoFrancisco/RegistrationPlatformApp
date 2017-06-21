package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.EventTypeService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.EventTypeDTO;
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
 * REST controller for managing EventType.
 */
@RestController
@RequestMapping("/api")
public class EventTypeResource {

    private final Logger log = LoggerFactory.getLogger(EventTypeResource.class);

    private static final String ENTITY_NAME = "eventType";

    private final EventTypeService eventTypeService;

    public EventTypeResource(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    /**
     * POST  /event-types : Create a new eventType.
     *
     * @param eventTypeDTO the eventTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventTypeDTO, or with status 400 (Bad Request) if the eventType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-types")
    @Timed
    public ResponseEntity<EventTypeDTO> createEventType(@Valid @RequestBody EventTypeDTO eventTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EventType : {}", eventTypeDTO);
        if (eventTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eventType cannot already have an ID")).body(null);
        }
        EventTypeDTO result = eventTypeService.save(eventTypeDTO);
        return ResponseEntity.created(new URI("/api/event-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-types : Updates an existing eventType.
     *
     * @param eventTypeDTO the eventTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventTypeDTO,
     * or with status 400 (Bad Request) if the eventTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-types")
    @Timed
    public ResponseEntity<EventTypeDTO> updateEventType(@Valid @RequestBody EventTypeDTO eventTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EventType : {}", eventTypeDTO);
        if (eventTypeDTO.getId() == null) {
            return createEventType(eventTypeDTO);
        }
        EventTypeDTO result = eventTypeService.save(eventTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-types : get all the eventTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eventTypes in body
     */
    @GetMapping("/event-types")
    @Timed
    public List<EventTypeDTO> getAllEventTypes() {
        log.debug("REST request to get all EventTypes");
        return eventTypeService.findAll();
    }

    /**
     * GET  /event-types/:id : get the "id" eventType.
     *
     * @param id the id of the eventTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/event-types/{id}")
    @Timed
    public ResponseEntity<EventTypeDTO> getEventType(@PathVariable Long id) {
        log.debug("REST request to get EventType : {}", id);
        EventTypeDTO eventTypeDTO = eventTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventTypeDTO));
    }

    /**
     * DELETE  /event-types/:id : delete the "id" eventType.
     *
     * @param id the id of the eventTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventType(@PathVariable Long id) {
        log.debug("REST request to delete EventType : {}", id);
        eventTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
