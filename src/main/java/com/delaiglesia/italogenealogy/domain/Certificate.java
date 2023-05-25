package com.delaiglesia.italogenealogy.domain;

import com.delaiglesia.italogenealogy.domain.enumeration.BloodLine;
import com.delaiglesia.italogenealogy.domain.enumeration.CertificateType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "certificate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class Certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "father_lastName")
    private String fatherLastName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "mother_lastName")
    private String motherLastName;

    @Column(name = "married_to")
    private String marriedTo;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "certificate_type")
    private CertificateType certificateType;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_line")
    private BloodLine bloodLine;

    @JsonIgnoreProperties(value = "fatherNameCertificate", allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true,  name="fatherNameCertificate", referencedColumnName = "id")
    private Certificate fatherCertificate;

    @JsonIgnoreProperties(value = "motherNameCertificate", allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true, name="motherNameCertificate", referencedColumnName = "id")
    private Certificate motherCertificate;

    @JsonIgnoreProperties(value = "marriedToCertificate", allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true, name="marriedToCertificate", referencedColumnName = "id")
    private Certificate marriedToCertificate;

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

    public Certificate getFatherCertificate() {
        return fatherCertificate;
    }

    public void setFatherCertificate(Certificate fatherCertificate) {
        this.fatherCertificate = fatherCertificate;
    }

    public Certificate getMotherCertificate() {
        return motherCertificate;
    }

    public void setMotherCertificate(Certificate motherCertificate) {
        this.motherCertificate = motherCertificate;
    }

    public Certificate getMarriedToCertificate() {
        return marriedToCertificate;
    }

    public void setMarriedToCertificate(Certificate marriedToCertificate) {
        this.marriedToCertificate = marriedToCertificate;
    }
}
