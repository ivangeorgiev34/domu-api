package com.ivangeorgiev.domuapi.data.repositories;

import com.ivangeorgiev.domuapi.data.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {

    List<Apartment> findAllByOwnerId(UUID ownerId);

    @Query(value = """
SELECT a.*
FROM apartments a
JOIN apartment_residents ar ON a.id = ar.apartment_id
JOIN people p ON p.id = ar.person_id
WHERE p.id = :residentId
""", nativeQuery = true)
    List<Apartment> findAllByResidentId(@Param("residentId") UUID residentId);

    @Query(value = """
SELECT a.*
FROM apartments a
JOIN floors f ON f.id = a.floor_id
JOIN buildings b ON b.id = f.building_id
WHERE b.id = :buildingId
""", nativeQuery = true)
    List<Apartment> findALlByBuildingId(@Param("buildingId") UUID buildingId);
}