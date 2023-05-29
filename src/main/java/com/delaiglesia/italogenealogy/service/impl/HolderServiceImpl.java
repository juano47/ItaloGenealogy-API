package com.delaiglesia.italogenealogy.service.impl;

import com.delaiglesia.italogenealogy.domain.Holder;
import com.delaiglesia.italogenealogy.repository.HolderRepository;
import com.delaiglesia.italogenealogy.service.HolderService;
import com.delaiglesia.italogenealogy.service.dto.HolderDTO;
import com.delaiglesia.italogenealogy.service.mapper.HolderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Holder}.
 */
@Service
@Transactional
public class HolderServiceImpl implements HolderService {

    private final Logger log = LoggerFactory.getLogger(HolderServiceImpl.class);

    private final HolderRepository holderRepository;

    private final HolderMapper holderMapper;

    public HolderServiceImpl(HolderRepository holderRepository, HolderMapper holderMapper) {
        this.holderRepository = holderRepository;
        this.holderMapper = holderMapper;
    }

    @Override
    public HolderDTO save(HolderDTO holderDTO) {
        log.debug("Request to save Holder : {}", holderDTO);
        Holder holder = holderMapper.toEntity(holderDTO);
        holder = holderRepository.save(holder);
        return holderMapper.toDto(holder);
    }

    @Override
    public HolderDTO update(HolderDTO holderDTO) {
        log.debug("Request to update Holder : {}", holderDTO);
        Holder holder = holderMapper.toEntity(holderDTO);
        holder = holderRepository.save(holder);
        return holderMapper.toDto(holder);
    }

    @Override
    public Optional<HolderDTO> partialUpdate(HolderDTO holderDTO) {
        log.debug("Request to partially update Holder : {}", holderDTO);

        return holderRepository
            .findById(holderDTO.getId())
            .map(existingHolder -> {
                holderMapper.partialUpdate(existingHolder, holderDTO);

                return existingHolder;
            })
            .map(holderRepository::save)
            .map(holderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolderDTO> findAll() {
        log.debug("Request to get all Holders");
        return holderRepository.findAll().stream().map(holderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HolderDTO> findOne(Long id) {
        log.debug("Request to get Holder : {}", id);
        return holderRepository.findById(id).map(holderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Holder : {}", id);
        holderRepository.deleteById(id);
    }
}
