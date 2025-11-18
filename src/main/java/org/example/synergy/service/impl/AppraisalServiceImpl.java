package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.contants.AppraisalRequestStatus;
import org.example.synergy.dto.*;
import org.example.synergy.entity.*;
import org.example.synergy.exceptions.ResourceNotFoundException;
import org.example.synergy.repository.*;
import org.example.synergy.service.AppraisalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppraisalServiceImpl implements AppraisalService {

    private final AppraisalRequestRepository requestRepository;
    private final AppraisalRequestFieldValueRepository fieldValueRepository;
    private final AppraisalRequestFileRepository fileRepository;
    private final AppraisalRequestHistoryRepository historyRepository;
    private final AppraisalUserRepository appraisalUserRepository;

    // helper generate code
    private String generateRequestCode() {
        return "AR-" + System.currentTimeMillis();
    }

    @Override
    @Transactional
    public AppraisalRequestDetailDto saveDraft(CreateAppraisalRequestDto dto) {
        AppraisalRequest entity = buildRequestEntityFromDto(dto, AppraisalRequestStatus.DRAFT.getCode());
        AppraisalRequest saved = requestRepository.save(entity);

        saveFieldValues(saved.getId(), dto.getDynamicFields());
        saveFiles(saved.getId(), dto.getFiles());
        saveAppraisalUser(saved.getId(), dto.getAppraisalUsers());

        historyRepository.save(AppraisalRequestHistory.builder()
                .appraisalRequestId(saved.getId())
                .action(AppraisalRequestStatus.DRAFT.getCode())
                .actionBy(dto.getCreatedBy())
                .comment("Lưu nháp")
                .createdAt(LocalDateTime.now())
                .build());

        return toDetailDto(saved.getId());
    }

    @Override
    @Transactional
    public AppraisalRequestDetailDto submit(CreateAppraisalRequestDto dto) {
        AppraisalRequest saved;
        if (dto.getAppraisalRequestId() == null) {
            AppraisalRequest entity = buildRequestEntityFromDto(dto, AppraisalRequestStatus.SUBMITTED.getCode());
            saved = requestRepository.save(entity);

            saveFieldValues(saved.getId(), dto.getDynamicFields());
            saveFiles(saved.getId(), dto.getFiles());
            saveAppraisalUser(saved.getId(), dto.getAppraisalUsers());
        } else {
            saved = requestRepository.findById(dto.getAppraisalRequestId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu id=" + dto.getAppraisalRequestId()));
            saved.setStatus(AppraisalRequestStatus.SUBMITTED.getCode());
        }
        saved = requestRepository.save(saved);



        historyRepository.save(AppraisalRequestHistory.builder()
                .appraisalRequestId(saved.getId())
                .action(AppraisalRequestStatus.SUBMITTED.getCode())
                .actionBy(dto.getCreatedBy())
                .comment(dto.getNote() == null ? "Gửi duyệt" : dto.getNote())
                .createdAt(LocalDateTime.now())
                .build());

        return toDetailDto(saved.getId());
    }

    @Override
    @Transactional
    public AppraisalRequestDetailDto approve(Long id, ActionRequestDto dto) {
        AppraisalRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu id=" + id));
        req.setStatus(AppraisalRequestStatus.APPROVED.getCode());
        req.setUpdatedDate(LocalDateTime.now());
        requestRepository.save(req);

        historyRepository.save(AppraisalRequestHistory.builder()
                .appraisalRequestId(id)
                .action(AppraisalRequestStatus.APPROVED.getCode())
                .actionBy(dto.getActionBy())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .build());

        return toDetailDto(id);
    }

    @Override
    @Transactional
    public AppraisalRequestDetailDto reject(Long id, ActionRequestDto dto) {
        AppraisalRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu id=" + id));
        req.setStatus(AppraisalRequestStatus.REJECTED.getCode());
        req.setUpdatedDate(LocalDateTime.now());
        requestRepository.save(req);

        historyRepository.save(AppraisalRequestHistory.builder()
                .appraisalRequestId(id)
                .action(AppraisalRequestStatus.REJECTED.getCode())
                .actionBy(dto.getActionBy())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .build());

        return toDetailDto(id);
    }

    @Override
    public AppraisalRequestDetailDto getById(Long id) {
        return toDetailDto(id);
    }

    @Override
    public List<AppraisalRequestDetailDto> listAll() {
        List<AppraisalRequest> all = requestRepository.findAll();
        return all.stream().map(r -> toDetailDto(r.getId())).collect(Collectors.toList());
    }

    /* ---------- Helpers ---------- */

    private AppraisalRequest buildRequestEntityFromDto(CreateAppraisalRequestDto dto, String status) {
        AppraisalRequest entity = AppraisalRequest.builder()
                .requestCode(generateRequestCode())
                .documentTypeId(dto.getDocumentTypeId())
                .priorityLevel(dto.getPriorityLevel())
                .responseDeadline(dto.getResponseDeadline())
                .note(dto.getNote())
                .status(status)
                .build();
        return entity;
    }

    private void saveFieldValues(Long requestId, List<FieldValueDto> fields) {
        if (fields == null || fields.isEmpty()) return;
        List<AppraisalRequestFieldValue> ents = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (FieldValueDto f : fields) {
            ents.add(AppraisalRequestFieldValue.builder()
                    .appraisalRequestId(requestId)
                    .fieldKey(f.getFieldKey())
                    .fieldLabel(f.getFieldLabel())
                    .fieldType(f.getFieldType())
                    .fieldValue(f.getFieldValue())
                    .createdAt(now)
                    .build());
        }
        fieldValueRepository.saveAll(ents);
    }

    private void saveFiles(Long requestId, List<FileDto> files) {
        if (files == null || files.isEmpty()) return;
        List<AppraisalRequestFile> ents = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (FileDto f : files) {
            ents.add(AppraisalRequestFile.builder()
                    .appraisalRequestId(requestId)
                    .fileName(f.getFileName())
                    .filePath(f.getFilePath())
                    .fileType(f.getFileType())
                    .uploadedAt(now)
                    .build());
        }
        fileRepository.saveAll(ents);
    }

    private void saveAppraisalUser(Long requestId, List<AppraisalUser> appraisalUsers) {
        if (appraisalUsers == null || appraisalUsers.isEmpty()) return;
        List<AppraisalUserEntity> ents = new ArrayList<>();
        for (AppraisalUser f : appraisalUsers) {
            ents.add(AppraisalUserEntity.builder()
                    .appraisalRequestId(requestId)
                    .name(f.getName())
                    .code(f.getCode())
                    .level(f.getLevel())
                    .organization(f.getOrganization())
                    .role(f.getRole())
                    .hostUnit(f.getHostUnit())
                    .build());
        }
        appraisalUserRepository.saveAll(ents);
    }


    private AppraisalRequestDetailDto toDetailDto(Long requestId) {
        AppraisalRequest r = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu id=" + requestId));

        List<AppraisalRequestFieldValue> fvs = fieldValueRepository.findByAppraisalRequestId(requestId);
        List<FieldValueDto> fields = fvs.stream()
                .map(f -> FieldValueDto.builder()
                        .fieldKey(f.getFieldKey())
                        .fieldLabel(f.getFieldLabel())
                        .fieldType(f.getFieldType())
                        .fieldValue(f.getFieldValue())
                        .build())
                .collect(Collectors.toList());

        List<AppraisalRequestFile> files = fileRepository.findByAppraisalRequestId(requestId);
        List<FileDto> fileDtos = files.stream()
                .map(f -> FileDto.builder()
                        .fileName(f.getFileName())
                        .filePath(f.getFilePath())
                        .fileType(f.getFileType())
                        .build())
                .collect(Collectors.toList());

        return AppraisalRequestDetailDto.builder()
                .id(r.getId())
                .requestCode(r.getRequestCode())
                .documentTypeCode(r.getDocumentTypeId())
                .priorityLevel(r.getPriorityLevel())
                .responseDeadline(r.getResponseDeadline())
                .note(r.getNote())
                .status(r.getStatus())
                .dynamicFields(fields)
                .files(fileDtos)
                .build();
    }
}
