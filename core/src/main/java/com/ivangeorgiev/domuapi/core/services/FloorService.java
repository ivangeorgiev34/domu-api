package com.ivangeorgiev.domuapi.core.services;

import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import com.ivangeorgiev.domuapi.data.models.Apartment;
import com.ivangeorgiev.domuapi.data.models.Floor;
import com.ivangeorgiev.domuapi.data.repositories.FloorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FloorService {

    private final FloorRepository floorRepository;
    private final ApartmentService apartmentService;

    public FloorService(FloorRepository floorRepository,
                        ApartmentService apartmentService) {
        this.floorRepository = floorRepository;
        this.apartmentService = apartmentService;
    }

    @Transactional
    public void delete(UUID id){

        Floor floor = this.floorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Floor not found"));

        for(Apartment apartment : floor.getApartments()){
            this.apartmentService.delete(apartment.getId());
        }

        this.floorRepository.delete(floor);
    }
}
