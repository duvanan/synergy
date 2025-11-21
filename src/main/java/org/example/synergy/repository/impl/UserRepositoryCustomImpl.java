/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearchCondition;
import org.example.synergy.repository.RepositoryCustomUtils;
import org.example.synergy.repository.UserRepositoryCustom;
import org.example.synergy.utils.PaginationUtil;
import org.example.synergy.utils.StringUtil;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryCustomImpl extends RepositoryCustomUtils implements UserRepositoryCustom {

    private final JpaTransactionManager transactionManager;

    @Override
    public List<UserListDTO> getUserList(UserSearchCondition condition) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("""
                SELECT
                  u.id,
                  u.full_name,
                  u.user_code AS user_code,
                  u.email,
                  u.phone_number,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
                  d.id AS department_id,
                  d.`name` AS department_name,
                  d.region_code AS region_code,
                  d.region_name AS region_name,
                  d.region_fullname AS region_fullname,
                  u.type,
                  u.`status`
                FROM
                  `user` u
                  LEFT JOIN department d ON u.department_id = d.id
                WHERE
                  u.is_deleted = FALSE AND 1=1
                """);

        this.appendSearchCondition(condition, params, sqlBuilder);
        sqlBuilder.append(" ORDER BY u.created_at DESC ");
        PaginationUtil.applyPagination(sqlBuilder, params, condition.getPaginationInfo());

        return this.getResultList(sqlBuilder.toString(), params, "GetUserList", transactionManager);
    }

    @Override
    public int countTotalUsers(UserSearchCondition condition) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("""
                    SELECT COUNT(DISTINCT u.id)
                    FROM user u
                        LEFT JOIN department d ON u.department_id = d.id
                    WHERE u.is_deleted = false
                    AND 1=1
                """);

        this.appendSearchCondition(condition, params, sqlBuilder);

        Object total = this.getSingleResult(sqlBuilder.toString(), params, transactionManager);
        return NumberUtils.toInt(String.valueOf(total), 0);
    }

    private void appendSearchCondition(UserSearchCondition condition, Map<String, Object> params,
            StringBuilder sqlBuilder) {
        if (StringUtils.isNotBlank(condition.getSearchKeyword())) {
            sqlBuilder.append("""
                        AND (
                            UPPER(u.username) LIKE UPPER(CONCAT('%', :searchKeyword, '%')) COLLATE :collate
                            OR UPPER(u.email) LIKE UPPER(CONCAT('%', :searchKeyword, '%')) COLLATE :collate
                            OR UPPER(u.full_name) LIKE UPPER(CONCAT('%', :searchKeyword, '%')) COLLATE :collate
                            OR UPPER(d.region_fullname) LIKE UPPER(CONCAT('%', :searchKeyword, '%')) COLLATE :collate
                            OR UPPER(u.phone_number) LIKE UPPER(CONCAT('%', :searchKeyword, '%')) COLLATE :collate
                        )
                    """);

            params.put("searchKeyword", StringUtil.formatLikeClause(condition.getSearchKeyword()));
            params.put("collate", condition.getCollate());
        }

        if (ObjectUtils.isNotEmpty(condition.getStatus())) {
            sqlBuilder.append(" AND (u.status = :status) ");
            params.put("status", condition.getStatus());
        }
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(condition.getDepartmentIds())) {
            sqlBuilder.append(" AND (d.id IN(:departmentIds) ) ");
            params.put("departmentIds", condition.getDepartmentIds());
        }
        if (CollectionUtils.isNotEmpty(condition.getUserTypes())) {
            sqlBuilder.append(" AND (u.type IN(:userTypes) ) ");
            params.put("userTypes", condition.getUserTypes());
        }
        if (CollectionUtils.isNotEmpty(condition.getRegionCodes())) {
            sqlBuilder.append(" AND (d.region_code IN(:regionCodes) ) ");
            params.put("regionCodes", condition.getRegionCodes());
        }
    }
}
