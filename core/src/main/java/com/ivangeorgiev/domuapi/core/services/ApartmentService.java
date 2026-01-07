package com.ivangeorgiev.domuapi.core.services;

import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import com.ivangeorgiev.domuapi.core.mappers.ApartmentMapper;
import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import com.ivangeorgiev.domuapi.data.models.Apartment;
import com.ivangeorgiev.domuapi.data.models.Person;
import com.ivangeorgiev.domuapi.data.repositories.ApartmentRepository;
import com.ivangeorgiev.domuapi.data.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final PersonService personService;

    public ApartmentService(ApartmentRepository apartmentRepository,
                            PersonService personService) {
        this.apartmentRepository = apartmentRepository;
        this.personService = personService;
    }

    @Transactional
    public void delete(UUID id){
        Apartment apartment = this.apartmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Apartment not found"));

        if(apartment.getOwner() != null)
            this.personService.delete(apartment.getOwner().getId());

        for (Person person : apartment.getResidents()){
            this.personService.delete(person.getId());
        }

        this.apartmentRepository.delete(apartment);
    }

    public List<ApartmentDto> getAll(UUID buildingId){

        List<Apartment> apartments = this.apartmentRepository.findALlByBuildingId(buildingId);

        return apartments.stream().map(ApartmentMapper::toApartmentDto).toList();
    }


}
