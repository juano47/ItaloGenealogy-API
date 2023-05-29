package com.delaiglesia.italogenealogy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Holder.
 */
@Entity
@Table(name = "holder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Holder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "holder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "holder" }, allowSetters = true)
    private Set<Certificate> certificates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Holder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Holder name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Holder lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Holder birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Certificate> getCertificates() {
        return this.certificates;
    }

    public void setCertificates(Set<Certificate> certificates) {
        if (this.certificates != null) {
            this.certificates.forEach(i -> i.setHolder(null));
        }
        if (certificates != null) {
            certificates.forEach(i -> i.setHolder(this));
        }
        this.certificates = certificates;
    }

    public Holder certificates(Set<Certificate> certificates) {
        this.setCertificates(certificates);
        return this;
    }

    public Holder addCertificate(Certificate certificate) {
        this.certificates.add(certificate);
        certificate.setHolder(this);
        return this;
    }

    public Holder removeCertificate(Certificate certificate) {
        this.certificates.remove(certificate);
        certificate.setHolder(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Holder)) {
            return false;
        }
        return id != null && id.equals(((Holder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Holder{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
