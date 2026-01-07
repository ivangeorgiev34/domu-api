package com.ivangeorgiev.domuapi.core.services;

import com.ivangeorgiev.domuapi.core.exceptions.BadRequestException;
import com.ivangeorgiev.domuapi.core.exceptions.InternalServerErrorException;
import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import com.ivangeorgiev.domuapi.core.mappers.CompanyMapper;
import com.ivangeorgiev.domuapi.data.dtos.company.CompanyDto;
import com.ivangeorgiev.domuapi.data.models.Apartment;
import com.ivangeorgiev.domuapi.data.models.Building;
import com.ivangeorgiev.domuapi.data.models.Company;
import com.ivangeorgiev.domuapi.data.repositories.ApartmentRepository;
import com.ivangeorgiev.domuapi.data.repositories.BuildingRepository;
import com.ivangeorgiev.domuapi.data.repositories.CompanyRepository;
import com.ivangeorgiev.domuapi.data.repositories.EmployeeRepository;
import com.ivangeorgiev.domuapi.data.request.company.CreateCompanyRequest;
import com.ivangeorgiev.domuapi.data.request.company.EditCompanyRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final BuildingRepository buildingRepository;
    private final ApartmentRepository apartmentRepository;

    public CompanyService(CompanyRepository companyRepository,
                          EmployeeRepository employeeRepository,
                          BuildingRepository buildingRepository,
                          ApartmentRepository apartmentRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.buildingRepository = buildingRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Transactional
    public CompanyDto createCompany(CreateCompanyRequest request) {
        Company company = Company.builder()
                .name(request.getName())
                .petTax(request.getPetTax())
                .taxPerResident(request.getTaxPerResident())
                .taxPerSquareMeter(request.getTaxPerSquareMeter())
                .income(BigDecimal.ZERO)
                .build();

        Company savedCompany = this.companyRepository.save(company);

        return CompanyMapper.toCompanyDto(savedCompany);
    }

    public Boolean existsCompany(UUID id) {
        return companyRepository.existsById(id);
    }

    @Transactional
    public void deleteCompany(UUID id) {
        Company company  = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        for (Building building : company.getBuildings()) {
            building.setCompany(null);
            building.setEmployee(null);
        }
        this.buildingRepository.saveAll(company.getBuildings());

        this.employeeRepository.deleteAll(company.getEmployees());
        this.companyRepository.delete(company);
    }

    @Transactional
    public CompanyDto editCompany(UUID id, EditCompanyRequest request) {
        Company company = this.companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        company.setName(request.getName());
        company.setPetTax(request.getPetTax());
        company.setTaxPerResident(request.getTaxPerResident());
        company.setTaxPerSquareMeter(request.getTaxPerSquareMeter());
        this.companyRepository.save(company);

        return CompanyMapper.toCompanyDto(company);
    }

    @Transactional
    public CompanyDto pay(UUID id, UUID apartmentId){

        Company company = this.companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Apartment apartment = this.apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new NotFoundException("Apartment not found"));
        if(apartment.getFloor().getBuilding().getCompany().getId() != id)
            throw new BadRequestException("Apartment isn't being serviced by this company");

        if(apartment.getResidents().isEmpty())
            throw new BadRequestException("Apartment doesn't have residents");

        long taxableResidentsCount = apartment.getResidents().stream()
                .filter(r -> r.getAge() > 7 && r.getUsesElevator())
                .count();
        BigDecimal residentsTax = taxableResidentsCount > 0
                ? company.getTaxPerResident().multiply(new BigDecimal(taxableResidentsCount))
                :BigDecimal.ZERO;

        BigDecimal petTax = apartment.getPetsCount() > 0
                ? company.getPetTax().multiply(new BigDecimal(apartment.getPetsCount()))
                : BigDecimal.ZERO;

        BigDecimal squareMetersTax = company.getTaxPerSquareMeter().multiply(apartment.getArea());

        BigDecimal totalTax = residentsTax.add(petTax).add(squareMetersTax);

        company.setIncome(company.getIncome().add(totalTax));
        Company savedCompany = this.companyRepository.save(company);

        writeToFile(
                id,
                apartment.getFloor().getBuilding().getEmployee().getId(),
                apartment.getFloor().getBuilding().getId(),
                apartmentId,
                totalTax);

        return CompanyMapper.toCompanyDto(savedCompany);
    }

    public List<CompanyDto> getAll(){

        List<Company> companies = this.companyRepository.findByOrderByIncome();

        return companies.stream().map(CompanyMapper::toCompanyDto).toList();
    }

    private void writeToFile(UUID companyId, UUID employeeId, UUID buildingId, UUID apartmentId, BigDecimal totalTax) {

        try {

            File receipt = new File("web/src/main/java/com/ivangeorgiev/domuapi/web/receipts/receipts.txt");

            receipt.createNewFile();

            try (FileOutputStream stream = new FileOutputStream(receipt, true)) {

                StringBuilder sb = new StringBuilder();

                sb.append("-------------------------------------------\n")
                        .append("Company ID: ").append(companyId).append("\n")
                        .append("Employee ID: ").append(employeeId).append("\n")
                        .append("Building ID: ").append(buildingId).append("\n")
                        .append("Apartment ID: ").append(apartmentId).append("\n")
                        .append("Sum: ").append(totalTax).append("\n")
                        .append("Date of payment (UTC): ").append(Instant.now().toString()).append("\n");

                stream.write(sb.toString().getBytes());

            }

        } catch (IOException e) {
            throw new InternalServerErrorException("Could not write to file");
        }
    }
}
