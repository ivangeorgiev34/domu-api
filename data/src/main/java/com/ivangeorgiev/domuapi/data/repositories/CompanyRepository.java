package com.ivangeorgiev.domuapi.data.repositories;

import com.ivangeorgiev.domuapi.data.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    List<Company> findByOrderByIncome();
}
