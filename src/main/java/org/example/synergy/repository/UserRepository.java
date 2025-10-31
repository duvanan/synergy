package org.example.synergy.repository;

import org.example.synergy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByDeletedIsFalseAndUsername(String username);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE user
            SET failed_password_attempts = COALESCE(failed_password_attempts, 0) + 1,
                status = CASE
                    WHEN COALESCE(failed_password_attempts, 0) + 1 > 5 THEN false
                    ELSE status
                END
            WHERE username = :username
            """, nativeQuery = true)
    void incrementFailedAttemptsAndDeactivateIfNeeded(@Param("username") String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Optional<Long> getIdByUsername(@Param("username") String username);

    String getRegionCodeById(Long id);
}
