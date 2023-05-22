package com.delaiglesia.italogenealogy.service;

import com.delaiglesia.italogenealogy.service.dto.CertificateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.delaiglesia.italogenealogy.domain.Certificate}.
 */
public interface CertificateService {
    /**
     * Save a certificate.
     *
     * @param certificateDTO the entity to save.
     * @return the persisted entity.
     */
    CertificateDTO save(CertificateDTO certificateDTO);

    /**
     * Updates a certificate.
     *
     * @param certificateDTO the entity to update.
     * @return the persisted entity.
     */
    CertificateDTO update(CertificateDTO certificateDTO);

    /**
     * Partially updates a certificate.
     *
     * @param certificateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CertificateDTO> partialUpdate(CertificateDTO certificateDTO);

    /**
     * Get all the certificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CertificateDTO> findAll(Pageable pageable);

    /**
     * Get all the certificates with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CertificateDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" certificate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CertificateDTO> findOne(Long id);

    /**
     * Delete the "id" certificate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
