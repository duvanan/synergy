/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.contants.enums;

import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

/**
 * SortType
 */
@Slf4j
public enum SortType {
    ASC,
    DESC;
    
    /**
     * Converts a string value to a corresponding SortType, defaulting to ASC if the provided value is blank or invalid.
     * @param value the String to convert
     * @return the corresponding SortType
     */
    public static SortType fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return ASC;
        }
        return Stream.of(values())
            .filter(v -> v.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseGet(() -> {
                log.warn("Unknown SortType = {}", value);
                return ASC;
            });
    }
}
