package com.ivangeorgiev.domuapi.core.services;

import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import com.ivangeorgiev.domuapi.core.mappers.BuildingMapper;
import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.models.*;
import com.ivangeorgiev.domuapi.data.repositories.*;
import com.ivangeorgiev.domuapi.data.request.building.AssignCompanyRequest;
import com.ivangeorgiev.domuapi.data.request.building.create.ApartmentRequest;
import com.ivangeorgiev.domuapi.data.request.building.create.CreateBuildingRequest;
import com.ivangeorgiev.domuapi.data.request.building.create.FloorRequest;
import com.ivangeorgiev.domuapi.data.request.building.edit.EditBuildingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final ApartmentRepository apartmentRepository;
    private final FloorRepository floorRepository;
    private final FloorService floorService;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public  BuildingService(BuildingRepository buildingRepository,
                            ApartmentRepository apartmentRepository,
                            FloorRepository floorRepository,
                            FloorService floorService,
                            CompanyRepository companyRepository,
                            EmployeeRepository employeeRepository) {
        this.buildingRepository = buildingRepository;
        this.apartmentRepository = apartmentRepository;
        this.floorRepository = floorRepository;
        this.floorService = floorService;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public Boolean existsById(UUID id) {
        return buildingRepository.existsById(id);
    }

    @Transactional
    public BuildingDto create(CreateBuildingRequest request) {

        Building building = Building.builder()
                .address(request.getAddress())
                .area(request.getArea())
                .build();
        Building savedBuilding = this.buildingRepository.save(building);

        List<FloorRequest> floorRequests = request.getFloors();

        List<Floor> floors = new ArrayList<>();
        for(int i = 0; i < floorRequests.size(); i++) {

            Floor floor = Floor.builder()
                    .number(i+1)
                    .building(building)
                    .build();

            List<ApartmentRequest> apartmentRequests = floorRequests.get(i).getApartments();

            List<Apartment> apartments = new ArrayList<>();
            for(int j = 0; j < apartmentRequests.size(); j++) {

                Integer apartmentNumber = Integer.parseInt((i+1) + String.format("%02d", j+1));
                ApartmentRequest apartmentRequest = apartmentRequests.get(j);
                apartments.add(Apartment.builder()
                        .number(apartmentNumber)
                        .area(apartmentRequest.getArea())
                        .petsCount(apartmentRequest.getPetsCount())
                        .floor(floor)
                        .build());
            }

            this.floorRepository.save(floor);
            this.apartmentRepository.saveAll(apartments);

            floor.setApartments(apartments);
            floors.add(floor);
        }

        savedBuilding.setFloors(floors);

        return BuildingMapper.toBuildingDto(savedBuilding);
    }

    @Transactional
    public void delete(UUID id){

        Building building = this.buildingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Building not found"));

        for (Floor floor :  building.getFloors()) {
            this.floorService.delete(floor.getId());
        }

        this.buildingRepository.delete(building);
    }

    @Transactional
    public BuildingDto edit(UUID id, EditBuildingRequest request) {

        Building building = this.buildingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Building not found"));

        building.setArea(request.getArea());
        building.setAddress(request.getAddress());

       Building savedBuilding = this.buildingRepository.save(building);

       return BuildingMapper.toBuildingDto(savedBuilding);
    }

    @Transactional
    public BuildingDto assignCompany(UUID id, AssignCompanyRequest request) {

        Building building = this.buildingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Building not found"));

        Company company = this.companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Employee employee = this.employeeRepository.findFirstByOrderByBuildingsCountAscNative(company.getId());

        if(employee == null)
            throw new NotFoundException("Employee not found");

        building.setCompany(company);
        building.setEmployee(employee);

        Building savedBuilding = this.buildingRepository.save(building);

        return BuildingMapper.toBuildingDto(savedBuilding);
    }

    public List<BuildingDto> getAll(){

        List<Building> buildings = this.buildingRepository.findAll();

        return buildings.stream().map(b -> {
            BuildingDto dto = BuildingMapper.toBuildingDto(b);

            dto.setFloors(null);

            return dto;
        }).toList();
    }
}
