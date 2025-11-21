package org.example.synergy.service.impl;


import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.DocumentTypeAttributeRequest;
import org.example.synergy.dto.request.DocumentTypeRequest;
import org.example.synergy.dto.response.DocumentTypeAttributeResponse;
import org.example.synergy.dto.response.DocumentTypeResponse;
import org.example.synergy.entity.DocumentType;
import org.example.synergy.entity.DocumentTypeAttribute;
import org.example.synergy.repository.DocumentTypeRepository;
import org.example.synergy.service.DocumentTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Triển khai các logic nghiệp vụ CRUD cho Loại văn bản
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    @Override
    public DocumentTypeResponse create(DocumentTypeRequest request) {
        DocumentType entity = mapToEntity(request, null);
        DocumentType saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public DocumentTypeResponse update(Long id, DocumentTypeRequest request) {
        DocumentType entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại văn bản ID = " + id));

        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setTemplateFilePath(request.getTemplateFilePath());
        entity.setFileName(request.getFileName());

        if (request.getAttributes() != null) {
            List<DocumentTypeAttribute> existingAttrs = entity.getAttributes();

            // 1. Xóa attributes không còn trong request
            existingAttrs.removeIf(oldAttr ->
                    request.getAttributes().stream()
                            .noneMatch(r -> r.getId() != null && r.getId().equals(oldAttr.getId()))
            );

            // 2. Cập nhật hoặc thêm mới
            for (DocumentTypeAttributeRequest attrReq : request.getAttributes()) {
                if (attrReq.getId() == null) {
                    // Thêm mới
                    DocumentTypeAttribute newAttr = new DocumentTypeAttribute();
                    newAttr.setDocumentType(entity);
                    newAttr.setLabel(attrReq.getLabel());
                    newAttr.setFieldCode(attrReq.getFieldCode());
                    newAttr.setRequired(attrReq.getRequired());
                    newAttr.setDataType(attrReq.getDataType());
                    newAttr.setDefaultValue(attrReq.getDefaultValue());
                    newAttr.setMinValue(attrReq.getMinValue());
                    newAttr.setMaxValue(attrReq.getMaxValue());
                    newAttr.setUnitList(attrReq.getUnitList());
                    newAttr.setTooltip(attrReq.getTooltip());
                    newAttr.setValueList(attrReq.getValueList());
                    existingAttrs.add(newAttr);
                } else {
                    // Cập nhật attribute cũ
                    DocumentTypeAttribute existingAttr = existingAttrs.stream()
                            .filter(e -> e.getId().equals(attrReq.getId()))
                            .findFirst()
                            .orElseThrow();
                    existingAttr.setLabel(attrReq.getLabel());
                    existingAttr.setFieldCode(attrReq.getFieldCode());
                    existingAttr.setRequired(attrReq.getRequired());
                    existingAttr.setDataType(attrReq.getDataType());
                    existingAttr.setDefaultValue(attrReq.getDefaultValue());
                    existingAttr.setMinValue(attrReq.getMinValue());
                    existingAttr.setMaxValue(attrReq.getMaxValue());
                    existingAttr.setUnitList(attrReq.getUnitList());
                    existingAttr.setTooltip(attrReq.getTooltip());
                    existingAttr.setValueList(attrReq.getValueList());
                }
            }
        }

        return mapToResponse(repository.save(entity));
    }


    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Không tồn tại loại văn bản ID = " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentTypeResponse getById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại văn bản ID = " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentTypeResponse> filter(String keyword, String code) {
        List<DocumentType> list = repository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                Predicate p1 = cb.like(root.get("name"), "%" + keyword + "%");
                Predicate p2 = cb.like(root.get("description"), "%" + keyword + "%");
                predicates.add(cb.or(p1, p2));
            }

            if (code != null && !code.isEmpty()) {
                predicates.add(cb.like(root.get("code"), "%" + code + "%"));
            }

            // ⛔ Nếu không có điều kiện, return null -> trả full danh sách
            if (predicates.isEmpty()) {
                return null;
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        return list.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void updateEntity(DocumentType entity, DocumentTypeRequest request) {
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setTemplateFilePath(request.getTemplateFilePath());

        if (request.getAttributes() != null) {
            List<DocumentTypeAttribute> oldAttrs = entity.getAttributes();

            // 1. Xóa attr không còn trong request
            oldAttrs.removeIf(old ->
                    request.getAttributes().stream().noneMatch(r -> r.getId() != null && r.getId().equals(old.getId()))
            );

            // 2. Cập nhật hoặc thêm mới
            for (var a : request.getAttributes()) {
                if (a.getId() == null) {
                    // Thêm mới
                    DocumentTypeAttribute newAttr = new DocumentTypeAttribute();
                    newAttr.setDocumentType(entity);
                    newAttr.setLabel(a.getLabel());
                    newAttr.setFieldCode(a.getFieldCode());
                    newAttr.setRequired(a.getRequired());
                    newAttr.setDataType(a.getDataType());
                    newAttr.setDefaultValue(a.getDefaultValue());
                    newAttr.setMinValue(a.getMinValue());
                    newAttr.setMaxValue(a.getMaxValue());
                    newAttr.setUnitList(a.getUnitList());
                    newAttr.setTooltip(a.getTooltip());
                    newAttr.setValueList(a.getValueList());
                    oldAttrs.add(newAttr);
                } else {
                    // Cập nhật
                    DocumentTypeAttribute existingAttr = oldAttrs.stream()
                            .filter(e -> e.getId().equals(a.getId()))
                            .findFirst()
                            .orElseThrow();
                    existingAttr.setLabel(a.getLabel());
                    existingAttr.setFieldCode(a.getFieldCode());
                    existingAttr.setRequired(a.getRequired());
                    existingAttr.setDataType(a.getDataType());
                    existingAttr.setDefaultValue(a.getDefaultValue());
                    existingAttr.setMinValue(a.getMinValue());
                    existingAttr.setMaxValue(a.getMaxValue());
                    existingAttr.setUnitList(a.getUnitList());
                    existingAttr.setTooltip(a.getTooltip());
                    existingAttr.setValueList(a.getValueList());
                }
            }
        }
    }



    // ======================= MAPPING =======================

    private DocumentType mapToEntity(DocumentTypeRequest request, DocumentType existing) {
        DocumentType entity = Optional.ofNullable(existing).orElse(new DocumentType());
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setTemplateFilePath(request.getTemplateFilePath());
        entity.setFileName(request.getFileName());

        if (request.getAttributes() != null) {
            List<DocumentTypeAttribute> attrs = request.getAttributes().stream().map(a -> {
                DocumentTypeAttribute attr = new DocumentTypeAttribute();
                attr.setDocumentType(entity);
                attr.setLabel(a.getLabel());
                attr.setFieldCode(a.getFieldCode());
                attr.setRequired(a.getRequired());
                attr.setDataType(a.getDataType());
                attr.setDefaultValue(a.getDefaultValue());
                attr.setMinValue(a.getMinValue());
                attr.setMaxValue(a.getMaxValue());
                attr.setUnitList(a.getUnitList());
                attr.setTooltip(a.getTooltip());
                attr.setValueList(a.getValueList());
                return attr;
            }).collect(Collectors.toList());
            entity.setAttributes(attrs);
        }

        return entity;
    }

    private DocumentTypeResponse mapToResponse(DocumentType entity) {
        DocumentTypeResponse dto = new DocumentTypeResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setTemplateFilePath(entity.getTemplateFilePath());
        dto.setFileName(entity.getTemplateFilePath());
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBY());
        dto.setUpdatedAt(entity.getUpdatedDate());
        dto.setUpdatedBy(entity.getUpdatedUser());

        if (entity.getAttributes() != null) {
            dto.setAttributes(entity.getAttributes().stream().map(attr -> {
                DocumentTypeAttributeResponse a = new DocumentTypeAttributeResponse();
                a.setId(attr.getId());
                a.setLabel(attr.getLabel());
                a.setFieldCode(attr.getFieldCode());
                a.setRequired(attr.getRequired());
                a.setDataType(attr.getDataType());
                a.setDefaultValue(attr.getDefaultValue());
                a.setMinValue(attr.getMinValue());
                a.setMaxValue(attr.getMaxValue());
                a.setUnitList(attr.getUnitList());
                a.setTooltip(attr.getTooltip());
                a.setValueList(attr.getValueList());
                return a;
            }).collect(Collectors.toList()));
        }

        return dto;
    }
}
