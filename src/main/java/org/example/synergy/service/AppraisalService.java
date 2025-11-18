package org.example.synergy.service;

import org.example.synergy.dto.ActionRequestDto;
import org.example.synergy.dto.AppraisalRequestDetailDto;
import org.example.synergy.dto.CreateAppraisalRequestDto;

import java.util.List;

public interface AppraisalService {
    AppraisalRequestDetailDto saveDraft(CreateAppraisalRequestDto dto);
    AppraisalRequestDetailDto submit(CreateAppraisalRequestDto dto);
    AppraisalRequestDetailDto approve(Long id, ActionRequestDto dto);
    AppraisalRequestDetailDto reject(Long id, ActionRequestDto dto);
    AppraisalRequestDetailDto getById(Long id);
    List<AppraisalRequestDetailDto> listAll(); // simple list endpoint
}
