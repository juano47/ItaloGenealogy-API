package com.delaiglesia.italogenealogy.web.rest;

import com.delaiglesia.italogenealogy.repository.HolderRepository;
import com.delaiglesia.italogenealogy.service.HolderService;
import com.delaiglesia.italogenealogy.service.dto.HolderDTO;
import com.delaiglesia.italogenealogy.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.delaiglesia.italogenealogy.domain.Holder}.
 */
@RestController
@RequestMapping("/api")
public class HolderResource {

    private final Logger log = LoggerFactory.getLogger(HolderResource.class);

    private static final String ENTITY_NAME = "holder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolderService holderService;

    private final HolderRepository holderRepository;

    public HolderResource(HolderService holderService, HolderRepository holderRepository) {
        this.holderService = holderService;
        this.holderRepository = holderRepository;
    }

    /**
     * {@code POST  /holders} : Create a new holder.
     *
     * @param holderDTO the holderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holderDTO, or with status {@code 400 (Bad Request)} if the holder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holders")
    public ResponseEntity<HolderDTO> createHolder(@RequestBody HolderDTO holderDTO) throws URISyntaxException {
        log.debug("REST request to save Holder : {}", holderDTO);
        if (holderDTO.getId() != null) {
            throw new BadRequestAlertException("A new holder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolderDTO result = holderService.save(holderDTO);
        return ResponseEntity
            .created(new URI("/api/holders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holders/:id} : Updates an existing holder.
     *
     * @param id the id of the holderDTO to save.
     * @param holderDTO the holderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holderDTO,
     * or with status {@code 400 (Bad Request)} if the holderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holders/{id}")
    public ResponseEntity<HolderDTO> updateHolder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HolderDTO holderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Holder : {}, {}", id, holderDTO);
        if (holderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HolderDTO result = holderService.update(holderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, holderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /holders/:id} : Partial updates given fields of an existing holder, field will ignore if it is null
     *
     * @param id the id of the holderDTO to save.
     * @param holderDTO the holderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holderDTO,
     * or with status {@code 400 (Bad Request)} if the holderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the holderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the holderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/holders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HolderDTO> partialUpdateHolder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HolderDTO holderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Holder partially : {}, {}", id, holderDTO);
        if (holderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HolderDTO> result = holderService.partialUpdate(holderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, holderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /holders} : get all the holders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holders in body.
     */
    @GetMapping("/holders")
    public List<HolderDTO> getAllHolders() {
        log.debug("REST request to get all Holders");
        return holderService.findAll();
    }

    /**
     * {@code GET  /holders/:id} : get the "id" holder.
     *
     * @param id the id of the holderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holders/{id}")
    public ResponseEntity<HolderDTO> getHolder(@PathVariable Long id) {
        log.debug("REST request to get Holder : {}", id);
        Optional<HolderDTO> holderDTO = holderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holderDTO);
    }

    /**
     * {@code DELETE  /holders/:id} : delete the "id" holder.
     *
     * @param id the id of the holderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holders/{id}")
    public ResponseEntity<Void> deleteHolder(@PathVariable Long id) {
        log.debug("REST request to delete Holder : {}", id);
        holderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
