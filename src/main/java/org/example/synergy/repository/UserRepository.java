package org.example.synergy.repository;

import org.example.synergy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Optional<User> findByIsDeletedIsFalseAndUserCode(String userCode);

    @Query("""
            SELECT d.regionCode
            FROM User u
            JOIN Department d ON d.id = u.departmentId
            WHERE u.id = :id AND u.status = true AND u.isDeleted = false
            """)
    String getRegionCodeById(Long id);
}
