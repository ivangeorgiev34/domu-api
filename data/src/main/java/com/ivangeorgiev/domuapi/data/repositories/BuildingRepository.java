package com.ivangeorgiev.domuapi.data.repositories;

import com.ivangeorgiev.domuapi.data.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuildingRepository extends JpaRepository<Building, UUID> {
}
