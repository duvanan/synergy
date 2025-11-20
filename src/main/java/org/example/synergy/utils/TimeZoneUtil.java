/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import lombok.experimental.UtilityClass;

/**
 * TimeZoneUtil
 */
@UtilityClass
public class TimeZoneUtil {
    
    /**
     * Build local time zone
     *
     * @return {@link ZoneId}
     */
    public ZoneId buildLocalZoneId() {
        return ZoneId.of(org.example.synergy.security.SecurityUtils.decodeTimeZoneFromToken());
    }
    
    /**
     * Calculate local zone offset
     *
     * @return offset
     */
    public int calculateLocalOffset() {
        ZoneId localZoneId = buildLocalZoneId();
        ZoneOffset currentOffsetForLocalZone = localZoneId.getRules().getOffset(Instant.now());
        return currentOffsetForLocalZone.getTotalSeconds() / 3600;
    }
}
