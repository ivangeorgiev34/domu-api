package com.ivangeorgiev.domuapi.core.services;

import com.ivangeorgiev.domuapi.core.exceptions.AlreadyExistsException;
import com.ivangeorgiev.domuapi.core.exceptions.BadRequestException;
import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import com.ivangeorgiev.domuapi.core.mappers.PersonMapper;
import com.ivangeorgiev.domuapi.data.dtos.person.PersonDto;
import com.ivangeorgiev.domuapi.data.models.Apartment;
import com.ivangeorgiev.domuapi.data.models.Person;
import com.ivangeorgiev.domuapi.data.repositories.ApartmentRepository;
import com.ivangeorgiev.domuapi.data.repositories.PersonRepository;
import com.ivangeorgiev.domuapi.data.request.person.CreatePersonRequest;
import com.ivangeorgiev.domuapi.data.request.person.EditPersonRequest;
import com.ivangeorgiev.domuapi.data.request.resident.get.GetAllResidentsRequestParams;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ApartmentRepository apartmentRepository;

    public PersonService(PersonRepository personRepository,
                         ApartmentRepository apartmentRepository) {
        this.personRepository = personRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Transactional
    public void delete(UUID id){
        List<Apartment> apartmentsByOwnerId = this.apartmentRepository.findAllByOwnerId(id);

        for (Apartment apartment : apartmentsByOwnerId) {
            apartment.setOwner(null);
        }

        List<Apartment> apartmentsByResidentId = this.apartmentRepository.findAllByResidentId(id);

        for (Apartment apartment : apartmentsByResidentId) {
            List<Person> residentsToRemove = new ArrayList<>();

            for (Person resident : apartment.getResidents()) {
                if (resident.getId().equals(id)) {
                    resident.getResidentApartments().remove(apartment);
                    residentsToRemove.add(resident);
                }
            }

            apartment.getResidents().removeAll(residentsToRemove);
        }

        this.apartmentRepository.saveAll(apartmentsByOwnerId);
        this.apartmentRepository.saveAll(apartmentsByResidentId);

        this.personRepository.deleteById(id);
    }

    @Transactional
    public PersonDto assignOwner(UUID apartmentId, CreatePersonRequest request){

        if(request.getAge() < 18)
            throw new BadRequestException("Owner must be at least 18 years old");

        Apartment apartment = this.apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new NotFoundException("Apartment not found"));

        if(apartment.getOwner() != null)
            throw new AlreadyExistsException("There is already an owner for this apartment");

        Person person = Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .usesElevator(request.getUsesElevator())
                .build();
        Person savedPerson = this.personRepository.save(person);

        apartment.setOwner(savedPerson);
        this.apartmentRepository.save(apartment);

        return PersonMapper.toPersonDto(savedPerson);
    }

    @Transactional
    public PersonDto assignResident(UUID apartmentId, CreatePersonRequest request){

        if(request.getAge() <= 0)
            throw new BadRequestException("Age must be greater than 0");

        Apartment apartment = this.apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new NotFoundException("Apartment not found"));

        Person person = Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .usesElevator(request.getUsesElevator())
                .build();
        Person savedPerson = this.personRepository.save(person);

        apartment.getResidents().add(savedPerson);
        this.apartmentRepository.save(apartment);

        return PersonMapper.toPersonDto(savedPerson);
    }

    @Transactional
    public PersonDto edit(UUID id, EditPersonRequest request){

        Person person =  this.personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));

        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setAge(request.getAge());
        person.setUsesElevator(request.getUsesElevator());

        Person savedPerson = this.personRepository.save(person);

        return PersonMapper.toPersonDto(savedPerson);
    }

    public List<PersonDto> getAll(GetAllResidentsRequestParams request){

        List<Person> people = this.personRepository.findAllByFilterAndSort(request.getBuildingId(), request.getSortBy(), request.getName(), request.getAge());

        return people.stream().map(PersonMapper::toPersonDto).toList();
    }
}
