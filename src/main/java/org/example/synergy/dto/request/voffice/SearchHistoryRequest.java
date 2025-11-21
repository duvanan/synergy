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
public class SearchHistoryRequest extends PaginationRequest {
    private Long id;
    private String documentCode;
    private String documentName;
    private List<String> status;
    private Long programId;
    private String userName;
    private String password;
}
