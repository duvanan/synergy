package org.example.synergy.repository;

import org.example.synergy.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE " +
            "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Department> findByNameContaining(@Param("name") String name);
}
