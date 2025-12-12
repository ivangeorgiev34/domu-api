package com.ivangeorgiev.domuapi.data.models;

import lombok.*;

import jakarta.persistence.*;

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
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false)
    private UUID id;

    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @Column(name = "area", precision = 10, scale = 2, nullable = false)
    private BigDecimal area;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER)
    private List<Floor> floors;

    @OneToOne(mappedBy = "employee", optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Employee employee;
}
