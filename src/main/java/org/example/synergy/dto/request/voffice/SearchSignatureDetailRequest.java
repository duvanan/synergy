package org.example.synergy.dto.request.voffice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.synergy.dto.request.PaginationRequest;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchSignatureDetailRequest extends PaginationRequest {
    private Long id;
    private String documentCode;
    private String documentName;
    private String programPlanName;
    private String programName;
    private List<Long> programTypeId;
    private List<String> status;
    private Long programId;

}
