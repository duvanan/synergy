package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.contants.AppraisalRequestStatus;
import org.example.synergy.dto.*;
import org.example.synergy.dto.response.AppraisalUserResponseDto;
import org.example.synergy.entity.*;
import org.example.synergy.exceptions.ResourceNotFoundException;
import org.example.synergy.repository.*;
import org.example.synergy.service.AppraisalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        AppraisalRequest entity;

        // ========== CASE UPDATE ==========
        if (dto.getAppraisalRequestId() != null) {

            entity = requestRepository.findById(dto.getAppraisalRequestId())
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            // update basic fields
            entity.setDocumentTypeId(dto.getDocumentTypeId());
            entity.setPriorityLevel(dto.getPriorityLevel());
            entity.setResponseDeadline(dto.getResponseDeadline());
            entity.setNote(dto.getNote());
            entity.setStatus(AppraisalRequestStatus.DRAFT.getCode());

            requestRepository.save(entity);

            // delete dynamic data
            fieldValueRepository.deleteByAppraisalRequestId(entity.getId());
            fileRepository.deleteByAppraisalRequestIdAndType(entity.getId(), 1);

            // NOTE: appraisalUser không xoá cứng ở đây.
            // Toàn bộ logic update được xử lý trong saveAppraisalUser().

        } else {
            // ========== CASE CREATE NEW ==========
            entity = buildRequestEntityFromDto(dto, AppraisalRequestStatus.DRAFT.getCode());
            entity = requestRepository.save(entity);
        }

        Long requestId = entity.getId();

        // Insert lại fieldValues + files
        saveFieldValues(requestId, dto.getDynamicFields());
        saveFiles(requestId, dto.getFiles(), 1);

        // Update / Insert / Soft delete appraisalUser
        saveAppraisalUser(requestId, dto.getAppraisalUsers());

        // Save history
        historyRepository.save(AppraisalRequestHistory.builder()
                .appraisalRequestId(requestId)
                .action(AppraisalRequestStatus.DRAFT.getCode())
                .actionBy(dto.getCreatedBy())
                .comment(dto.getAppraisalRequestId() == null ? "Lưu nháp (tạo mới)" : "Lưu nháp (cập nhật)")
                .createdAt(LocalDateTime.now())
                .build()
        );

        return toDetailDto(requestId);
    }

    @Override
    @Transactional
    public AppraisalRequestDetailDto submit(CreateAppraisalRequestDto dto) {
        AppraisalRequest saved;
        if (dto.getAppraisalRequestId() == null) {
            AppraisalRequest entity = buildRequestEntityFromDto(dto, AppraisalRequestStatus.SUBMITTED.getCode());
            saved = requestRepository.save(entity);

            saveFieldValues(saved.getId(), dto.getDynamicFields());
            saveFiles(saved.getId(), dto.getFiles(), 1);
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


        AppraisalUserEntity appraisalUserEntity = appraisalUserRepository.findByCodeAndAndAppraisalRequestId(dto.getActionBy(), id).orElseThrow(() -> new RuntimeException(""));
        appraisalUserEntity.setFileName(dto.getFileName());
        appraisalUserEntity.setFilePath(dto.getFilePath());
        appraisalUserEntity.setNote(dto.getComment());
        appraisalUserEntity.setAppraised(Boolean.TRUE);
        appraisalUserEntity.setAppraisedTime(LocalDateTime.now());
        appraisalUserRepository.save(appraisalUserEntity);

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

        AppraisalUserEntity appraisalUserEntity = appraisalUserRepository.findByCodeAndAndAppraisalRequestId(dto.getActionBy(), id).orElseThrow(() -> new RuntimeException(""));
        appraisalUserEntity.setFileName(dto.getFileName());
        appraisalUserEntity.setFilePath(dto.getFilePath());
        appraisalUserEntity.setNote(dto.getComment());
        appraisalUserRepository.save(appraisalUserEntity);

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

    private void saveFiles(Long requestId, List<FileDto> files, Integer type) {
        if (files == null || files.isEmpty()) return;
        List<AppraisalRequestFile> ents = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (FileDto f : files) {
            ents.add(AppraisalRequestFile.builder()
                    .appraisalRequestId(requestId)
                    .fileName(f.getFileName())
                    .filePath(f.getFilePath())
                    .fileType(f.getFileType())
                    .type(type)
                    .uploadedAt(now)
                    .build());
        }
        fileRepository.saveAll(ents);
    }

    private void saveAppraisalUser(Long requestId, List<AppraisalUser> dtoUsers) {
        // Lấy danh sách user hiện tại trong DB
        List<AppraisalUserEntity> existingUsers =
                appraisalUserRepository.findAllByAppraisalRequestId(requestId);

        // Map để dễ lookup
        Map<String, AppraisalUserEntity> existingMap = existingUsers.stream()
                .collect(Collectors.toMap(AppraisalUserEntity::getCode, u -> u));

        List<AppraisalUserEntity> result = new ArrayList<>();

        for (AppraisalUser dto : dtoUsers) {

            // Nếu user đã tồn tại → update
            if (existingMap.containsKey(dto.getCode())) {
                AppraisalUserEntity ent = existingMap.get(dto.getCode());
                ent.setName(dto.getName());
                ent.setLevel(dto.getLevel());
                ent.setOrganization(dto.getOrganization());
                ent.setRole(dto.getRole());
                ent.setHostUnit(dto.getHostUnit());
                ent.setIsDeleted(false); // revive nếu trước đó đã bị đánh dấu delete
                result.add(ent);

                existingMap.remove(dto.getCode()); // đánh dấu là đã xử lý
            } else {
                // User mới → insert
                result.add(AppraisalUserEntity.builder()
                        .appraisalRequestId(requestId)
                        .code(dto.getCode())
                        .name(dto.getName())
                        .level(dto.getLevel())
                        .organization(dto.getOrganization())
                        .role(dto.getRole())
                        .hostUnit(dto.getHostUnit())
                        .isDeleted(false)
                        .build()
                );
            }
        }

        // Những user còn lại trong existingMap là user không còn trong DTO → soft delete
        for (AppraisalUserEntity toDelete : existingMap.values()) {
            toDelete.setIsDeleted(true);
            result.add(toDelete);
        }

        appraisalUserRepository.saveAll(result);
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
                .createdAt(r.getCreatedDate())
                .createdBy(r.getCreatedBY())
                .updatedAt(r.getUpdatedDate())
                .updatedBy(r.getUpdatedUser())
                .files(fileDtos)
                .build();
    }

    public Page<AppraisalRequestDetailDto> searchRequests(
            String requestCode,
            Long documentTypeId,
            String status,
            Pageable pageable
    ) {
        // 1. Query danh sách request theo phân trang
        Page<AppraisalRequest> page = requestRepository.search(requestCode, documentTypeId, status, pageable);

        List<Long> requestIds = page.getContent().stream()
                .map(AppraisalRequest::getId)
                .collect(Collectors.toList());

        // 2. Load dynamic fields theo danh sách requestIds
        List<AppraisalRequestFieldValue> allFieldValues =
                fieldValueRepository.findByAppraisalRequestIdIn(requestIds);

        Map<Long, List<FieldValueDto>> fieldMap = allFieldValues.stream()
                .collect(Collectors.groupingBy(
                        AppraisalRequestFieldValue::getAppraisalRequestId,
                        Collectors.mapping(f -> FieldValueDto.builder()
                                .fieldKey(f.getFieldKey())
                                .fieldLabel(f.getFieldLabel())
                                .fieldType(f.getFieldType())
                                .fieldValue(f.getFieldValue())
                                .build(), Collectors.toList())
                ));

        // 3. Load file attachments theo danh sách requestIds
        List<AppraisalRequestFile> allFiles =
                fileRepository.findByAppraisalRequestIdIn(requestIds);

        Map<Long, List<FileDto>> fileMap = allFiles.stream()
                .collect(Collectors.groupingBy(
                        AppraisalRequestFile::getAppraisalRequestId,
                        Collectors.mapping(f -> FileDto.builder()
                                .fileName(f.getFileName())
                                .filePath(f.getFilePath())
                                .fileType(f.getFileType())
                                .build(), Collectors.toList())
                ));

        // 4. Convert sang DTO tái sử dụng AppraisalRequestDetailDto
        List<AppraisalRequestDetailDto> dtos = page.getContent().stream()
                .map(r -> AppraisalRequestDetailDto.builder()
                        .id(r.getId())
                        .requestCode(r.getRequestCode())
                        .documentTypeCode(r.getDocumentTypeId())
                        .priorityLevel(r.getPriorityLevel())
                        .responseDeadline(r.getResponseDeadline())
                        .note(r.getNote())
                        .status(r.getStatus())
                        .dynamicFields(fieldMap.getOrDefault(r.getId(), List.of()))
                        .files(fileMap.getOrDefault(r.getId(), List.of()))
                        .createdAt(r.getCreatedDate())
                        .createdBy(r.getCreatedBY())
                        .updatedAt(r.getUpdatedDate())
                        .updatedBy(r.getUpdatedUser())
                        .build()
                )
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    public List<AppraisalUserResponseDto> getUsersByRequestId(Long appraisalRequestId) {

        List<AppraisalUserEntity> entities =
                appraisalUserRepository.findAllByAppraisalRequestIdAndIsDeleted(appraisalRequestId, Boolean.FALSE);

        return entities.stream()
                .map(e -> AppraisalUserResponseDto.builder()
                        .id(e.getId())
                        .appraisalRequestId(e.getAppraisalRequestId())
                        .name(e.getName())
                        .code(e.getCode())
                        .level(e.getLevel())
                        .organization(e.getOrganization())
                        .role(e.getRole())
                        .appraised(e.getAppraised())
                        .filePath(e.getFilePath())
                        .fileName(e.getFileName())
                        .note(e.getNote())
                        .hostUnit(e.getHostUnit())
                        .build())
                .collect(Collectors.toList());
    }

}
