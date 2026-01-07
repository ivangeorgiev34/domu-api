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
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "number" ,nullable = false)
    private Integer number;

    @Column(name = "area", precision = 10, scale = 2, nullable = false)
    private BigDecimal area;

    @Column(name = "pets_count", nullable = false)
    private Integer petsCount;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;

    @ManyToMany
    @JoinTable(
            name = "apartment_residents",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<Person> residents;

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;
}