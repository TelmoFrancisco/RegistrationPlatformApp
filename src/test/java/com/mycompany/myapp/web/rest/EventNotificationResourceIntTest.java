package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RegistrationPlatformApp;

import com.mycompany.myapp.domain.EventNotification;
import com.mycompany.myapp.repository.EventNotificationRepository;
import com.mycompany.myapp.service.EventNotificationService;
import com.mycompany.myapp.service.dto.EventNotificationDTO;
import com.mycompany.myapp.service.mapper.EventNotificationMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EventNotificationResource REST controller.
 *
 * @see EventNotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationPlatformApp.class)
public class EventNotificationResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EventNotificationRepository eventNotificationRepository;

    @Autowired
    private EventNotificationMapper eventNotificationMapper;

    @Autowired
    private EventNotificationService eventNotificationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventNotificationMockMvc;

    private EventNotification eventNotification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventNotificationResource eventNotificationResource = new EventNotificationResource(eventNotificationService);
        this.restEventNotificationMockMvc = MockMvcBuilders.standaloneSetup(eventNotificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventNotification createEntity(EntityManager em) {
        EventNotification eventNotification = new EventNotification()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return eventNotification;
    }

    @Before
    public void initTest() {
        eventNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventNotification() throws Exception {
        int databaseSizeBeforeCreate = eventNotificationRepository.findAll().size();

        // Create the EventNotification
        EventNotificationDTO eventNotificationDTO = eventNotificationMapper.toDto(eventNotification);
        restEventNotificationMockMvc.perform(post("/api/event-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the EventNotification in the database
        List<EventNotification> eventNotificationList = eventNotificationRepository.findAll();
        assertThat(eventNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        EventNotification testEventNotification = eventNotificationList.get(eventNotificationList.size() - 1);
        assertThat(testEventNotification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEventNotification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEventNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventNotificationRepository.findAll().size();

        // Create the EventNotification with an existing ID
        eventNotification.setId(1L);
        EventNotificationDTO eventNotificationDTO = eventNotificationMapper.toDto(eventNotification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventNotificationMockMvc.perform(post("/api/event-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EventNotification> eventNotificationList = eventNotificationRepository.findAll();
        assertThat(eventNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventNotificationRepository.findAll().size();
        // set the field null
        eventNotification.setTitle(null);

        // Create the EventNotification, which fails.
        EventNotificationDTO eventNotificationDTO = eventNotificationMapper.toDto(eventNotification);

        restEventNotificationMockMvc.perform(post("/api/event-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<EventNotification> eventNotificationList = eventNotificationRepository.findAll();
        assertThat(eventNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventNotificationRepository.findAll().size();
        // set the field null
        eventNotification.setDescription(null);

        // Create the EventNotification, which fails.
        EventNotificationDTO eventNotificationDTO = eventNotificationMapper.toDto(eventNotification);

        restEventNotificationMockMvc.perform(post("/api/event-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<EventNotification> eventNotificationList = eventNotificationRepository.findAll();
        assertThat(eventNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventNotifications() throws Exception {
        // Initialize the database
        eventNotificationRepository.saveAndFlush(eventNotification);

        // Get all the eventNotificationList
        restEventNotificationMockMvc.perform(get("/api/event-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEventNotification() throws Exception {
        // Initialize the database
        eventNotificationRepository.saveAndFlush(eventNotification);

        // Get the eventNotification
        restEventNotificationMockMvc.perform(get("/api/event-notifications/{id}", eventNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventNotification.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventNotification() throws Exception {
        // Get the eventNotification
        restEventNotificationMockMvc.perform(get("/api/event-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventNotification() throws Exception {
        // Initialize the database
        eventNotificationRepository.saveAndFlush(eventNotification);
        int databaseSizeBeforeUpdate = eventNotificationRepository.findAll().size();

        // Update the eventNotification
        EventNotification updatedEventNotification = eventNotificationRepository.findOne(eventNotification.getId());
        updatedEventNotification
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        EventNotificationDTO eventNotificationDTO = eventNotificationMapper.toDto(updatedEventNotification);

        restEventNotificationMockMvc.perform(put("/api/event-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventNotificationDTO)))
            .andExpect(status().isOk());

        // Validate the EventNotification in the database
        List<EventNotification> eventNotificationList = eventNotificationRepository.findAll();
        assertThat(eventNotificationList).hasSize(databaseSizeBeforeUpdate);
        EventNotification testEventNotification = eventNotificationList.get(eventNotificationList.size() - 1);
        assertThat(testEventNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEventNotification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEventNotification() throws Exception {
        int databaseSizeBeforeUpdate = eventNotificationRepository.findAll().size();

        // Create the EventNotification
        EventNotificationDTO eventNotificationDTO = eventNotificationMapper.toDto(eventNotification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventNotificationMockMvc.perform(put("/api/event-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the EventNotification in the database
        List<EventNotification> eventNotificationList = eventNotificationRepository.findAll();
        assertThat(eventNotificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEventNotification() throws Exception {
        // Initialize the database
        eventNotificationRepository.saveAndFlush(eventNotification);
        int databaseSizeBeforeDelete = eventNotificationRepository.findAll().size();

        // Get the eventNotification
        restEventNotificationMockMvc.perform(delete("/api/event-notifications/{id}", eventNotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EventNotification> eventNotificationList = eventNotificationRepository.findAll();
        assertThat(eventNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventNotification.class);
        EventNotification eventNotification1 = new EventNotification();
        eventNotification1.setId(1L);
        EventNotification eventNotification2 = new EventNotification();
        eventNotification2.setId(eventNotification1.getId());
        assertThat(eventNotification1).isEqualTo(eventNotification2);
        eventNotification2.setId(2L);
        assertThat(eventNotification1).isNotEqualTo(eventNotification2);
        eventNotification1.setId(null);
        assertThat(eventNotification1).isNotEqualTo(eventNotification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventNotificationDTO.class);
        EventNotificationDTO eventNotificationDTO1 = new EventNotificationDTO();
        eventNotificationDTO1.setId(1L);
        EventNotificationDTO eventNotificationDTO2 = new EventNotificationDTO();
        assertThat(eventNotificationDTO1).isNotEqualTo(eventNotificationDTO2);
        eventNotificationDTO2.setId(eventNotificationDTO1.getId());
        assertThat(eventNotificationDTO1).isEqualTo(eventNotificationDTO2);
        eventNotificationDTO2.setId(2L);
        assertThat(eventNotificationDTO1).isNotEqualTo(eventNotificationDTO2);
        eventNotificationDTO1.setId(null);
        assertThat(eventNotificationDTO1).isNotEqualTo(eventNotificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventNotificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventNotificationMapper.fromId(null)).isNull();
    }
}
