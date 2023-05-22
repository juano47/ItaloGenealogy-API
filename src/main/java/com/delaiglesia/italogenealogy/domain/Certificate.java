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
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "certificate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@EqualsAndHashCode
@ToString
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

    @JsonIgnoreProperties(value = "certificate", allowSetters = true)
    @OneToOne
    @JoinColumns(
            {
                    @JoinColumn(updatable = false, insertable = false, referencedColumnName = "father_name"),
                    @JoinColumn(updatable = false, insertable = false, referencedColumnName = "father_lastName"),
            })
    private Certificate fatherCertificate;

    @JsonIgnoreProperties(value = "certificate", allowSetters = true)
    @OneToOne
    @JoinColumns(
            {
                    @JoinColumn(unique = true, updatable = false, insertable = false, referencedColumnName = "mother_name"),
                    @JoinColumn(unique = true, updatable = false, insertable = false, referencedColumnName = "mother_lastName"),
            })
    private Certificate motherCertificate;

    @JsonIgnoreProperties(value = "certificate", allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true, name="marriedToCertificate", referencedColumnName = "married_to")
    private Certificate marriedToCertificate;
}
