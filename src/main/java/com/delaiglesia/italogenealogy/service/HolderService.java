package com.delaiglesia.italogenealogy.service;

import com.delaiglesia.italogenealogy.service.dto.HolderDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.delaiglesia.italogenealogy.domain.Holder}.
 */
public interface HolderService {
    /**
     * Save a holder.
     *
     * @param holderDTO the entity to save.
     * @return the persisted entity.
     */
    HolderDTO save(HolderDTO holderDTO);

    /**
     * Updates a holder.
     *
     * @param holderDTO the entity to update.
     * @return the persisted entity.
     */
    HolderDTO update(HolderDTO holderDTO);

    /**
     * Partially updates a holder.
     *
     * @param holderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HolderDTO> partialUpdate(HolderDTO holderDTO);

    /**
     * Get all the holders.
     *
     * @return the list of entities.
     */
    List<HolderDTO> findAll();

    /**
     * Get the "id" holder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HolderDTO> findOne(Long id);

    /**
     * Delete the "id" holder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
