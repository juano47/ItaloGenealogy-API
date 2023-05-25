package com.delaiglesia.italogenealogy.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.delaiglesia.italogenealogy.IntegrationTest;
import com.delaiglesia.italogenealogy.domain.Certificate;
import com.delaiglesia.italogenealogy.domain.enumeration.BloodLine;
import com.delaiglesia.italogenealogy.domain.enumeration.CertificateType;
import com.delaiglesia.italogenealogy.repository.CertificateRepository;
import com.delaiglesia.italogenealogy.service.CertificateService;
import com.delaiglesia.italogenealogy.service.dto.CertificateDTO;
import com.delaiglesia.italogenealogy.service.mapper.CertificateMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CertificateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CertificateResourceIT {

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MARRIED_TO = "AAAAAAAAAA";
    private static final String UPDATED_MARRIED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final CertificateType DEFAULT_CERTIFICATE_TYPE = CertificateType.NACIMIENTO;
    private static final CertificateType UPDATED_CERTIFICATE_TYPE = CertificateType.CASAMIENTO;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final BloodLine DEFAULT_BLOOD_LINE = BloodLine.PADRE;
    private static final BloodLine UPDATED_BLOOD_LINE = BloodLine.MADRE;

    private static final String ENTITY_API_URL = "/api/certificates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateRepository certificateRepositoryMock;

    @Autowired
    private CertificateMapper certificateMapper;

    @Mock
    private CertificateService certificateServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificateMockMvc;

    private Certificate certificate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certificate createEntity(EntityManager em) {
        Certificate certificate = Certificate.builder()
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .marriedTo(DEFAULT_MARRIED_TO)
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .date(DEFAULT_DATE)
            .certificateType(DEFAULT_CERTIFICATE_TYPE)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .bloodLine(DEFAULT_BLOOD_LINE).build();
        return certificate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certificate createUpdatedEntity(EntityManager em) {
        Certificate certificate = Certificate.builder()
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .marriedTo(UPDATED_MARRIED_TO)
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .date(UPDATED_DATE)
            .certificateType(UPDATED_CERTIFICATE_TYPE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .bloodLine(UPDATED_BLOOD_LINE).build();
        return certificate;
    }

    @BeforeEach
    public void initTest() {
        certificate = createEntity(em);
    }

    @Test
    @Transactional
    void createCertificate() throws Exception {
        int databaseSizeBeforeCreate = certificateRepository.findAll().size();
        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);
        restCertificateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeCreate + 1);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testCertificate.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testCertificate.getMarriedTo()).isEqualTo(DEFAULT_MARRIED_TO);
        assertThat(testCertificate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCertificate.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCertificate.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCertificate.getCertificateType()).isEqualTo(DEFAULT_CERTIFICATE_TYPE);
        assertThat(testCertificate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCertificate.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testCertificate.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCertificate.getBloodLine()).isEqualTo(DEFAULT_BLOOD_LINE);
    }

    @Test
    @Transactional
    void createCertificateWithExistingId() throws Exception {
        // Create the Certificate with an existing ID
        certificate.setId(1L);
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        int databaseSizeBeforeCreate = certificateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCertificates() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        // Get all the certificateList
        restCertificateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].marriedTo").value(hasItem(DEFAULT_MARRIED_TO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].certificateType").value(hasItem(DEFAULT_CERTIFICATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].bloodLine").value(hasItem(DEFAULT_BLOOD_LINE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCertificatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(certificateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCertificateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(certificateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCertificatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(certificateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCertificateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(certificateRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCertificate() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        // Get the certificate
        restCertificateMockMvc
            .perform(get(ENTITY_API_URL_ID, certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certificate.getId().intValue()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.marriedTo").value(DEFAULT_MARRIED_TO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.certificateType").value(DEFAULT_CERTIFICATE_TYPE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.bloodLine").value(DEFAULT_BLOOD_LINE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCertificate() throws Exception {
        // Get the certificate
        restCertificateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCertificate() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();

        // Update the certificate
        Certificate updatedCertificate = certificateRepository.findById(certificate.getId()).get();
        // Disconnect from session so that the updates on updatedCertificate are not directly saved in db
        em.detach(updatedCertificate);
        updatedCertificate.toBuilder()
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .marriedTo(UPDATED_MARRIED_TO)
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .date(UPDATED_DATE)
            .certificateType(UPDATED_CERTIFICATE_TYPE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .bloodLine(UPDATED_BLOOD_LINE);
        CertificateDTO certificateDTO = certificateMapper.toDto(updatedCertificate);

        restCertificateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testCertificate.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testCertificate.getMarriedTo()).isEqualTo(UPDATED_MARRIED_TO);
        assertThat(testCertificate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCertificate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCertificate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCertificate.getCertificateType()).isEqualTo(UPDATED_CERTIFICATE_TYPE);
        assertThat(testCertificate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCertificate.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testCertificate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCertificate.getBloodLine()).isEqualTo(UPDATED_BLOOD_LINE);
    }

    @Test
    @Transactional
    void putNonExistingCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();
        certificate.setId(count.incrementAndGet());

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();
        certificate.setId(count.incrementAndGet());

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();
        certificate.setId(count.incrementAndGet());

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCertificateWithPatch() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();

        // Update the certificate using partial update
        Certificate partialUpdatedCertificate = new Certificate();
        partialUpdatedCertificate.setId(certificate.getId());

        partialUpdatedCertificate.toBuilder()
            .fatherName(UPDATED_FATHER_NAME)
            .date(UPDATED_DATE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY);

        restCertificateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertificate))
            )
            .andExpect(status().isOk());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testCertificate.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testCertificate.getMarriedTo()).isEqualTo(DEFAULT_MARRIED_TO);
        assertThat(testCertificate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCertificate.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCertificate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCertificate.getCertificateType()).isEqualTo(DEFAULT_CERTIFICATE_TYPE);
        assertThat(testCertificate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCertificate.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testCertificate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCertificate.getBloodLine()).isEqualTo(DEFAULT_BLOOD_LINE);
    }

    @Test
    @Transactional
    void fullUpdateCertificateWithPatch() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();

        // Update the certificate using partial update
        Certificate partialUpdatedCertificate = new Certificate();
        partialUpdatedCertificate.setId(certificate.getId());

        partialUpdatedCertificate.toBuilder()
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .marriedTo(UPDATED_MARRIED_TO)
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .date(UPDATED_DATE)
            .certificateType(UPDATED_CERTIFICATE_TYPE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .bloodLine(UPDATED_BLOOD_LINE);

        restCertificateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertificate))
            )
            .andExpect(status().isOk());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testCertificate.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testCertificate.getMarriedTo()).isEqualTo(UPDATED_MARRIED_TO);
        assertThat(testCertificate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCertificate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCertificate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCertificate.getCertificateType()).isEqualTo(UPDATED_CERTIFICATE_TYPE);
        assertThat(testCertificate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCertificate.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testCertificate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCertificate.getBloodLine()).isEqualTo(UPDATED_BLOOD_LINE);
    }

    @Test
    @Transactional
    void patchNonExistingCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();
        certificate.setId(count.incrementAndGet());

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, certificateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();
        certificate.setId(count.incrementAndGet());

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();
        certificate.setId(count.incrementAndGet());

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCertificate() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        int databaseSizeBeforeDelete = certificateRepository.findAll().size();

        // Delete the certificate
        restCertificateMockMvc
            .perform(delete(ENTITY_API_URL_ID, certificate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
