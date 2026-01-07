package com.ivangeorgiev.domuapi.data.repositories;

import com.ivangeorgiev.domuapi.data.models.Employee;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(value = """
SELECT e.*
FROM employees e
LEFT JOIN buildings b ON e.id = b.employee_id
WHERE e.id <> :excludedId AND e.company_id = :companyId
GROUP BY e.id
ORDER BY COUNT(b.id) ASC
LIMIT 1
""", nativeQuery = true)
    Employee findFirstByOrderByBuildingsCountAscNative(@Param("excludedId") UUID excludedId, @Param("companyId") UUID companyId);

    @Query(value = """
SELECT e.*
FROM employees e
LEFT JOIN buildings b ON e.id = b.employee_id
WHERE e.company_id = :companyId
GROUP BY e.id
ORDER BY COUNT(b.id) ASC
LIMIT 1
""", nativeQuery = true)
    Employee findFirstByOrderByBuildingsCountAscNative(@Param("companyId") UUID companyId);

    @Query(value = """
SELECT e.*
FROM employees e
LEFT JOIN buildings b ON e.id = b.employee_id
WHERE e.company_id = :companyId AND e.first_name LIKE CONCAT('%', :name, '%')
GROUP BY e.id
ORDER BY
      CASE
        WHEN :sortBy = 'name' THEN e.first_name
        WHEN :sortBy = 'buildingsCount' THEN CAST(COUNT(b.id) AS CHAR)
        ELSE CAST(e.id AS CHAR)
      END ASC
""", nativeQuery = true)
    List<Employee> findAllBYSortAndFilter(@Param("companyId") UUID companyId,
                                          @Param("sortBy") String sortBy,
                                          @Param("name") String name);
}
