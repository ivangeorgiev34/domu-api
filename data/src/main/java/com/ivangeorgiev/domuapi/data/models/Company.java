package com.ivangeorgiev.domuapi.data.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.Update;

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
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "petTax", nullable = false)
    private BigDecimal petTax;

    @Column(name = "taxPerResident", nullable = false)
    private BigDecimal taxPerResident;

    @Column(name = "taxPerSquareMeter", nullable = false)
    private BigDecimal taxPerSquareMeter;

    @Column(name = "income", nullable = false)
    private BigDecimal income;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    @OneToMany(mappedBy = "company")
    private List<Building> buildings;
}
