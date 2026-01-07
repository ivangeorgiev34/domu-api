package com.ivangeorgiev.domuapi.data.repositories;

import com.ivangeorgiev.domuapi.data.models.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FloorRepository extends JpaRepository<Floor, UUID> {
}
