package org.example.synergy.dto.request.voffice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.synergy.dto.request.PaginationRequest;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRequest extends PaginationRequest {
    private String keyword;
    private Long staffImageSignId;
    private String isRequestToSignText;
    private String userName;
    private String password;

}
