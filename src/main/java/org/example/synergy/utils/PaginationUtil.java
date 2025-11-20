/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.utils;

import lombok.experimental.UtilityClass;
import org.example.synergy.dto.response.PaginationInfo;

import java.util.Map;

/**
 * PaginationUtils
 */
@UtilityClass
public class PaginationUtil {
    
    /**
     * Applies pagination parameters to the SQL query.
     *
     * @param sqlBuilder StringBuilder containing the SQL query.
     * @param params     Map containing the query parameters.
     * @param page       Pagination information.
     */
    public void applyPagination(StringBuilder sqlBuilder, Map<String, Object> params, PaginationInfo page) {
        if (page != null && page.getPageNumber() != null && page.getPageSize() != null) {
            sqlBuilder.append(" LIMIT :limit OFFSET :offset ");
            params.put("limit", page.calculateLimit());
            params.put("offset", page.calculateOffset());
        }
    }
}
