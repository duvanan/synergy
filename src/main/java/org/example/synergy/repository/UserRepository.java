package org.example.synergy.repository;

import org.example.synergy.dto.UserDepartmentDTO;
import org.example.synergy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(
            value = """
            SELECT
                u.id AS userId,
                u.code AS code,
                u.full_name AS fullName,
                u.email AS email,
                u.password AS password,
                u.failed_password_attempts AS failedPasswordAttempts,
                u.phone_number AS phoneNumber,
                u.department_id AS departmentId,
                u.jti AS jti,
                u.status AS status,
                u.is_assign AS isAssign,
                u.is_deleted AS isDeleted,
                u.type AS type,
                u.user_code AS userCode,
                u.gender AS gender,
                u.organization_code AS organizationCode,
                u.staff_role AS staffRole,
                u.staff_level AS staffLevel,
                u.is_active AS isActive,
                u.user_key AS userKey,
                u.directory_id AS directoryId,
                u.jira_active AS jiraActive,
                u.staff_position AS staffPosition,
                u.organization_id AS organizationId,
                u.position_id AS positionId,

                d.id AS deptId,
                d.code AS deptCode,
                d.name AS deptName,
                d.region_code AS regionCode,
                d.region_name AS regionName,
                d.region_fullname AS regionFullname,
                d.description AS description,
                d.organization_name AS organizationName,
                d.organization_code AS deptOrganizationCode,
                d.is_deleted AS deptIsDeleted,
                d.is_active AS deptIsActive,
                d.parent_code AS parentCode
            FROM user u
            LEFT JOIN department d ON u.organization_code = d.organization_code
            WHERE 
                u.is_deleted = 0
                AND (:organizationCode IS NULL OR u.organization_code = :organizationCode)
                AND (:departmentId IS NULL OR u.department_id = :departmentId)
                AND (:status IS NULL OR u.status = :status)
                AND (:type IS NULL OR u.type = :type)
                AND (:regionCode IS NULL OR d.region_code = :regionCode)
            """,

            countQuery = """
            SELECT COUNT(*) 
            FROM user u
            LEFT JOIN department d ON u.organization_code = d.organization_code
            WHERE 
                u.is_deleted = 0
                AND (:organizationCode IS NULL OR u.organization_code = :organizationCode)
                AND (:departmentId IS NULL OR u.department_id = :departmentId)
                AND (:status IS NULL OR u.status = :status)
                AND (:type IS NULL OR u.type = :type)
                AND (:regionCode IS NULL OR d.region_code = :regionCode)
            """,

            nativeQuery = true
    )
    Page<UserDepartmentDTO> filterUsers(
            @Param("organizationCode") String organizationCode,
            @Param("departmentId") Long departmentId,
            @Param("status") Boolean status,
            @Param("type") Integer type,
            @Param("regionCode") String regionCode,
            Pageable pageable
    );

    Optional<User> findByUserCode(String code);
}
