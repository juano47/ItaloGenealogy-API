package com.delaiglesia.italogenealogy.service.impl;

import com.delaiglesia.italogenealogy.domain.Certificate;
import com.delaiglesia.italogenealogy.repository.CertificateRepository;
import com.delaiglesia.italogenealogy.service.CertificateService;
import com.delaiglesia.italogenealogy.service.dto.CertificateDTO;
import com.delaiglesia.italogenealogy.service.mapper.CertificateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Certificate}.
 */
@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final Logger log = LoggerFactory.getLogger(CertificateServiceImpl.class);

    private final CertificateRepository certificateRepository;

    private final CertificateMapper certificateMapper;

    public CertificateServiceImpl(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public CertificateDTO save(CertificateDTO certificateDTO) {
        log.debug("Request to save Certificate : {}", certificateDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        log.debug("Request to update Certificate : {}", certificateDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    @Override
    public Optional<CertificateDTO> partialUpdate(CertificateDTO certificateDTO) {
        log.debug("Request to partially update Certificate : {}", certificateDTO);

        return certificateRepository
            .findById(certificateDTO.getId())
            .map(existingCertificate -> {
                certificateMapper.partialUpdate(existingCertificate, certificateDTO);

                return existingCertificate;
            })
            .map(certificateRepository::save)
            .map(certificateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CertificateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Certificates");
        return certificateRepository.findAll(pageable).map(certificateMapper::toDto);
    }

    public Page<CertificateDTO> findAllWithEagerRelationships(Pageable pageable) {
        return certificateRepository.findAllWithEagerRelationships(pageable).map(certificateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CertificateDTO> findOne(Long id) {
        log.debug("Request to get Certificate : {}", id);
        return certificateRepository.findOneWithEagerRelationships(id).map(certificateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Certificate : {}", id);
        certificateRepository.deleteById(id);
    }
}
