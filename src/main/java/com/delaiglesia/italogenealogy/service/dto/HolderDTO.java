package com.delaiglesia.italogenealogy.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.delaiglesia.italogenealogy.domain.Holder} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HolderDTO implements Serializable {

    private Long id;

    private String name;

    private String lastName;

    private LocalDate birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HolderDTO)) {
            return false;
        }

        HolderDTO holderDTO = (HolderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, holderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HolderDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
