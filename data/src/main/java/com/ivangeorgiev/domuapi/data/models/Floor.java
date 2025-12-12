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
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false)
    private UUID id;

    @Column(name = "number", length = 500, nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "floor", fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Apartment> apartments;
}
