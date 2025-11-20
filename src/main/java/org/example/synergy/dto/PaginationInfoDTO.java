/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.synergy.contants.enums.SortType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;

/**
 * PaginationInfo
 */
@Data
@NoArgsConstructor
public class PaginationInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7416926292216814653L;

    private Integer pageNumber;

    private Integer pageSize = Integer.MAX_VALUE;

    private String sortBy;

    private SortType sortType = SortType.DESC;

    public PaginationInfoDTO(Integer pageNumber, Integer pageSize, String sortBy, SortType sortType) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize != null ? pageSize : Integer.MAX_VALUE;
        this.sortBy = sortBy;
        this.sortType = sortType != null ? sortType : SortType.DESC;
    }

    public PaginationInfoDTO(Integer pageNumber, Integer pageSize, String sortBy, SortType sortType,
                             String defaultSortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize != null ? pageSize : Integer.MAX_VALUE;
        this.sortBy = StringUtils.isNotBlank(sortBy) ? sortBy : defaultSortBy;
        this.sortType = sortType != null ? sortType : SortType.DESC;
    }

    public PaginationInfoDTO(String sortBy, SortType sortType, String defaultSortBy) {
        this.sortBy = StringUtils.isNotBlank(sortBy) ? sortBy : defaultSortBy;
        this.sortType = sortType != null ? sortType : SortType.DESC;
    }

    public PaginationInfoDTO(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize != null ? pageSize : Integer.MAX_VALUE;
    }
    
    /**
     * Calculate offset value
     *
     * @return offset
     */
    public int calculateOffset() {
        return Optional.ofNullable(pageNumber)
            .filter(p -> p > 0)
            .map(p -> (p - 1) * pageSize)
            .orElse(0);
    }
    
    /**
     * Calculate limit value
     *
     * @return limit
     */
    public int calculateLimit() {
        return Optional.ofNullable(pageNumber)
            .filter(p -> p > 0)
            .map(p -> Math.max(p * pageSize - calculateOffset(), 0))
            .orElse(pageSize);
    }
    
    /**
     * Generate order by
     *
     * @return sort
     */
    public String generateSort() {
        return String.format(Locale.ENGLISH, "%s %s", sortBy, sortType.name());
    }
}
