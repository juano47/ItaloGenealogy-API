package com.delaiglesia.italogenealogy.service.mapper;

import com.delaiglesia.italogenealogy.domain.Certificate;
import com.delaiglesia.italogenealogy.service.dto.CertificateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certificate} and its DTO {@link CertificateDTO}.
 */
@Mapper(componentModel = "spring")
public interface CertificateMapper extends EntityMapper<CertificateDTO, Certificate> {


/*    @Named("certificateFatherName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fatherName", source = "fatherName")
    CertificateDTO toDtoCertificateFatherName(Certificate certificate);

    @Named("certificateMotherName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "motherName", source = "motherName")
    CertificateDTO toDtoCertificateMotherName(Certificate certificate);

    @Named("certificateMarriedTo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "marriedTo", source = "marriedTo")
    CertificateDTO toDtoCertificateMarriedTo(Certificate certificate);*/
}
