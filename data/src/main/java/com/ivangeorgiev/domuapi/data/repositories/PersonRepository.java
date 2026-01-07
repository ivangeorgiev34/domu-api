package com.ivangeorgiev.domuapi.data.repositories;

import com.ivangeorgiev.domuapi.data.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query(value = """
SELECT p.*
FROM people p
JOIN apartment_residents ar ON p.id = ar.person_id
JOIN apartments a ON a.id = ar.apartment_id
JOIN floors f ON f.id = a.floor_id
JOIN buildings b ON f.building_id = b.id
WHERE b.id = :buildingId AND p.first_name LIKE CONCAT('%', :name, '%') AND (:age <= 0 OR p.age = :age)
ORDER BY
      CASE
        WHEN :sortBy = 'name' THEN p.first_name
        WHEN :sortBy = 'age' THEN CAST(p.age AS CHAR)
        ELSE CAST(p.id AS CHAR)
      END ASC
""", nativeQuery = true)
    List<Person> findAllByFilterAndSort(@Param("buildingId") UUID buildingId,
                                        @Param("sortBy") String sortBy,
                                        @Param("name") String name,
                                        @Param("age") Integer age);
}
