package org.example.synergy.service;

import org.example.synergy.dto.ActionRequestDto;
import org.example.synergy.dto.AppraisalRequestDetailDto;
import org.example.synergy.dto.CreateAppraisalRequestDto;
import org.example.synergy.dto.response.AppraisalUserResponseDto;
import org.example.synergy.dto.response.AttachFileResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppraisalService {
    AppraisalRequestDetailDto saveDraft(CreateAppraisalRequestDto dto);
    AppraisalRequestDetailDto submit(CreateAppraisalRequestDto dto);
    AppraisalRequestDetailDto approve(Long id, ActionRequestDto dto);
    AppraisalRequestDetailDto reject(Long id, ActionRequestDto dto);
    AppraisalRequestDetailDto getById(Long id);
    List<AppraisalRequestDetailDto> listAll(); // simple list endpoint

    Page<AppraisalRequestDetailDto> searchRequests(
            String requestCode,
            Long documentTypeId,
            String status,
            Pageable pageable
    );

    List<AppraisalUserResponseDto> getUsersByRequestId(Long appraisalRequestId);

}
