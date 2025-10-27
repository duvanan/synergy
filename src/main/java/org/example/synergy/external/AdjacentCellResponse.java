/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.external;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjacentCellResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 4418587122174157541L;
    
    private int code;
    
    private String message;
    
    @JsonProperty("data")
    private ResponseData data;
    
    @Data
    public static class ResponseData {
        
        @JsonProperty("request_id")
        private String requestId;
        
        @JsonProperty("adjacent_list")
        private List<AdjacentData> adjacentList;
    }
    
    @Data
    public static class AdjacentData {
        
        @JsonProperty("request_longitude")
        private Double requestLongitude;
        
        @JsonProperty("request_latitude")
        private Double requestLatitude;
        
        @JsonProperty("station")
        private List<Station> station;
    }
    
    @Data
    public static class Station {
        
        @JsonProperty("station_code")
        private String stationCode;
        
        @JsonProperty("mnc")
        private Integer mnc;
        
        @JsonProperty("province_code")
        private String provinceCode;
        
        @JsonProperty("district_code")
        private String districtCode;
        
        @JsonProperty("ward")
        private String ward;
        
        @JsonProperty("address")
        private String address;
        
        @JsonProperty("longitude")
        private Double longitude;
        
        @JsonProperty("latitude")
        private Double latitude;
        
        @JsonProperty("morphology")
        private String morphology;
        
        @JsonProperty("contact")
        private String contact;
        
        @JsonProperty("cell")
        private List<Cell> cell;
    }
    
    @Data
    public static class Cell {
        
        @JsonProperty("cell_code")
        private String cellCode;
        
        @JsonProperty("radio_tech")
        private String radioTech;
        
        @JsonProperty("frequency_band")
        @NotNull
        private Integer frequencyBand;
        
        @JsonProperty("arfcn")
        private Integer arfcn;
        
        @JsonProperty("bandwidth")
        private Integer bandwidth;
        
        @JsonProperty("height")
        private Double height;
        
        @JsonProperty("azimuth")
        private Integer azimuth;
        
        @JsonProperty("tilt")
        private Integer tilt;
        
        @JsonProperty("antenna_manufacturer_name")
        private String antennaManufacturerName;
        
        @JsonProperty("antenna_type")
        private String antennaType;
    }
}
