package com.ivangeorgiev.domuapi.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false)
    private UUID id;

    @Column(name = "number" ,nullable = false)
    private Integer number;

    @Column(name = "area", precision = 10, scale = 2, nullable = false)
    private BigDecimal area;

    @Column(name = "pets_count", nullable = false)
    private Integer petsCount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false)
    private Person owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "apartment_residents",
            joinColumns = @JoinColumn(name = "apartment_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false)
    )
    private List<Person> residents;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;
}