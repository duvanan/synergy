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
public class LitsUserSignWithRoleRequest extends PaginationRequest {
    private List<Long> lstStaff;
    private String userName;
    private String password;

}
