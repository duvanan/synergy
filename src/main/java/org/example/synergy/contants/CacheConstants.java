/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.contants;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Cache constants.
 */
@Slf4j
@UtilityClass
public final class CacheConstants {
    
    public static final String CELL_DETAIL_STATISTICS_CACHE_NAME = "cell_detail_statistics";
    
    public static final String OPERATOR_CELL_STATISTICS_CACHE_NAME = "operator_cell_statistics";
    
    public static final String INTERFERENCE_CLUSTER_STATISTICS_CACHE_NAME = "interference_cluster_statistics";
    
    public static final String STATION_CELL_MAP_CACHE_NAME = "station_cell_map";
    
    public static final String INTERFERENCE_STATION_CELL_MAP_CACHE_NAME = "interference_station_cell_map";
}
