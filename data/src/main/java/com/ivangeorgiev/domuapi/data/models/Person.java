package com.ivangeorgiev.domuapi.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "uses_elevator", nullable = false)
    private Boolean usesElevator;

    @OneToMany(mappedBy = "owner")
    private List<Apartment> ownedApartments;

    @ManyToMany(mappedBy = "residents")
    private List<Apartment> residentApartments;
}