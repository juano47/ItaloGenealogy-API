package com.delaiglesia.italogenealogy.service.mapper;

import com.delaiglesia.italogenealogy.domain.Certificate;
import com.delaiglesia.italogenealogy.service.dto.CertificateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certificate} and its DTO {@link CertificateDTO}.
 */
@Mapper(componentModel = "spring")
public interface CertificateMapper extends EntityMapper<CertificateDTO, Certificate> {
	Certificate toEntity(CertificateDTO dto);
	CertificateDTO toDto(Certificate entity);
	CertificateDTO[] toDto(Certificate[] entity);
	Iterable<CertificateDTO> toDto(Iterable<Certificate> entity);
}
