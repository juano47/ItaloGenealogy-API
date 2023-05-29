package com.delaiglesia.italogenealogy.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.delaiglesia.italogenealogy.IntegrationTest;
import com.delaiglesia.italogenealogy.domain.Holder;
import com.delaiglesia.italogenealogy.repository.HolderRepository;
import com.delaiglesia.italogenealogy.service.dto.HolderDTO;
import com.delaiglesia.italogenealogy.service.mapper.HolderMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HolderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HolderResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/holders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HolderRepository holderRepository;

    @Autowired
    private HolderMapper holderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHolderMockMvc;

    private Holder holder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holder createEntity(EntityManager em) {
        Holder holder = new Holder().name(DEFAULT_NAME).lastName(DEFAULT_LAST_NAME).birthDate(DEFAULT_BIRTH_DATE);
        return holder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holder createUpdatedEntity(EntityManager em) {
        Holder holder = new Holder().name(UPDATED_NAME).lastName(UPDATED_LAST_NAME).birthDate(UPDATED_BIRTH_DATE);
        return holder;
    }

    @BeforeEach
    public void initTest() {
        holder = createEntity(em);
    }

    @Test
    @Transactional
    void createHolder() throws Exception {
        int databaseSizeBeforeCreate = holderRepository.findAll().size();
        // Create the Holder
        HolderDTO holderDTO = holderMapper.toDto(holder);
        restHolderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeCreate + 1);
        Holder testHolder = holderList.get(holderList.size() - 1);
        assertThat(testHolder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHolder.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testHolder.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    @Transactional
    void createHolderWithExistingId() throws Exception {
        // Create the Holder with an existing ID
        holder.setId(1L);
        HolderDTO holderDTO = holderMapper.toDto(holder);

        int databaseSizeBeforeCreate = holderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHolders() throws Exception {
        // Initialize the database
        holderRepository.saveAndFlush(holder);

        // Get all the holderList
        restHolderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holder.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));
    }

    @Test
    @Transactional
    void getHolder() throws Exception {
        // Initialize the database
        holderRepository.saveAndFlush(holder);

        // Get the holder
        restHolderMockMvc
            .perform(get(ENTITY_API_URL_ID, holder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(holder.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHolder() throws Exception {
        // Get the holder
        restHolderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHolder() throws Exception {
        // Initialize the database
        holderRepository.saveAndFlush(holder);

        int databaseSizeBeforeUpdate = holderRepository.findAll().size();

        // Update the holder
        Holder updatedHolder = holderRepository.findById(holder.getId()).get();
        // Disconnect from session so that the updates on updatedHolder are not directly saved in db
        em.detach(updatedHolder);
        updatedHolder.name(UPDATED_NAME).lastName(UPDATED_LAST_NAME).birthDate(UPDATED_BIRTH_DATE);
        HolderDTO holderDTO = holderMapper.toDto(updatedHolder);

        restHolderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, holderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isOk());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
        Holder testHolder = holderList.get(holderList.size() - 1);
        assertThat(testHolder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHolder.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testHolder.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void putNonExistingHolder() throws Exception {
        int databaseSizeBeforeUpdate = holderRepository.findAll().size();
        holder.setId(count.incrementAndGet());

        // Create the Holder
        HolderDTO holderDTO = holderMapper.toDto(holder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, holderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHolder() throws Exception {
        int databaseSizeBeforeUpdate = holderRepository.findAll().size();
        holder.setId(count.incrementAndGet());

        // Create the Holder
        HolderDTO holderDTO = holderMapper.toDto(holder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHolder() throws Exception {
        int databaseSizeBeforeUpdate = holderRepository.findAll().size();
        holder.setId(count.incrementAndGet());

        // Create the Holder
        HolderDTO holderDTO = holderMapper.toDto(holder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolderMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHolderWithPatch() throws Exception {
        // Initialize the database
        holderRepository.saveAndFlush(holder);

        int databaseSizeBeforeUpdate = holderRepository.findAll().size();

        // Update the holder using partial update
        Holder partialUpdatedHolder = new Holder();
        partialUpdatedHolder.setId(holder.getId());

        partialUpdatedHolder.name(UPDATED_NAME).birthDate(UPDATED_BIRTH_DATE);

        restHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHolder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHolder))
            )
            .andExpect(status().isOk());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
        Holder testHolder = holderList.get(holderList.size() - 1);
        assertThat(testHolder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHolder.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testHolder.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void fullUpdateHolderWithPatch() throws Exception {
        // Initialize the database
        holderRepository.saveAndFlush(holder);

        int databaseSizeBeforeUpdate = holderRepository.findAll().size();

        // Update the holder using partial update
        Holder partialUpdatedHolder = new Holder();
        partialUpdatedHolder.setId(holder.getId());

        partialUpdatedHolder.name(UPDATED_NAME).lastName(UPDATED_LAST_NAME).birthDate(UPDATED_BIRTH_DATE);

        restHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHolder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHolder))
            )
            .andExpect(status().isOk());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
        Holder testHolder = holderList.get(holderList.size() - 1);
        assertThat(testHolder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHolder.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testHolder.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingHolder() throws Exception {
        int databaseSizeBeforeUpdate = holderRepository.findAll().size();
        holder.setId(count.incrementAndGet());

        // Create the Holder
        HolderDTO holderDTO = holderMapper.toDto(holder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, holderDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHolder() throws Exception {
        int databaseSizeBeforeUpdate = holderRepository.findAll().size();
        holder.setId(count.incrementAndGet());

        // Create the Holder
        HolderDTO holderDTO = holderMapper.toDto(holder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHolder() throws Exception {
        int databaseSizeBeforeUpdate = holderRepository.findAll().size();
        holder.setId(count.incrementAndGet());

        // Create the Holder
        HolderDTO holderDTO = holderMapper.toDto(holder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Holder in the database
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHolder() throws Exception {
        // Initialize the database
        holderRepository.saveAndFlush(holder);

        int databaseSizeBeforeDelete = holderRepository.findAll().size();

        // Delete the holder
        restHolderMockMvc
            .perform(delete(ENTITY_API_URL_ID, holder.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Holder> holderList = holderRepository.findAll();
        assertThat(holderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
