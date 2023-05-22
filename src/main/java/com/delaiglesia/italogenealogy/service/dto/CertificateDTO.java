package com.delaiglesia.italogenealogy.service.dto;

import com.delaiglesia.italogenealogy.domain.enumeration.BloodLine;
import com.delaiglesia.italogenealogy.domain.enumeration.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.delaiglesia.italogenealogy.domain.Certificate} entity.
 */
public class CertificateDTO implements Serializable {

    private Long id;

    private String fatherName;

    private String fatherLastName;

    private String motherName;

    private String motherLastName;

    private String marriedTo;

    private String name;

    private String lastName;

    private LocalDate date;

    private CertificateType certificateType;

    private String city;

    private String province;

    private String country;

    private BloodLine bloodLine;

    private CertificateDTO fatherCertificate;

    private CertificateDTO motherCertificate;

    private CertificateDTO marriedToCertificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getMarriedTo() {
        return marriedTo;
    }

    public void setMarriedTo(String marriedTo) {
        this.marriedTo = marriedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BloodLine getBloodLine() {
        return bloodLine;
    }

    public void setBloodLine(BloodLine bloodLine) {
        this.bloodLine = bloodLine;
    }

    public CertificateDTO getFatherCertificate() {
        return fatherCertificate;
    }

    public void setFatherCertificate(CertificateDTO fatherCertificate) {
        this.fatherCertificate = fatherCertificate;
    }

    public CertificateDTO getMotherCertificate() {
        return motherCertificate;
    }

    public void setMotherCertificate(CertificateDTO motherCertificate) {
        this.motherCertificate = motherCertificate;
    }

    public CertificateDTO getMarriedToCertificate() {
        return marriedToCertificate;
    }

    public void setMarriedToCertificate(CertificateDTO marriedToCertificate) {
        this.marriedToCertificate = marriedToCertificate;
    }
}
