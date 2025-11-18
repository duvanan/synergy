package org.example.synergy.service.impl;


import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
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
        DocumentType existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại văn bản ID = " + id));
        DocumentType updated = mapToEntity(request, existing);
        return mapToResponse(repository.save(updated));
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


    // ======================= MAPPING =======================

    private DocumentType mapToEntity(DocumentTypeRequest request, DocumentType existing) {
        DocumentType entity = Optional.ofNullable(existing).orElse(new DocumentType());
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setTemplateFilePath(request.getTemplateFilePath());

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
