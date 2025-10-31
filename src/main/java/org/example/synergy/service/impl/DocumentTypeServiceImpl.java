package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.entity.DocumentType;
import org.example.synergy.repository.DocumentTypeRepository;
import org.example.synergy.service.DocumentTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    @Override
    public List<DocumentType> findAll() {
        return repository.findAll();
    }

    @Override
    public DocumentType findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        return repository.save(documentType);
    }

    @Override
    public DocumentType update(Long id, DocumentType updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setDocumentTypeName(updated.getDocumentTypeName());
                    existing.setDescription(updated.getDescription());
                    existing.setLabel(updated.getLabel());
                    existing.setIsRequired(updated.getIsRequired());
                    existing.setAttributeDescription(updated.getAttributeDescription());
                    existing.setDataType(updated.getDataType());
                    existing.setDefaultValue(updated.getDefaultValue());
                    existing.setMinValue(updated.getMinValue());
                    existing.setMaxValue(updated.getMaxValue());
                    existing.setValueList(updated.getValueList());
                    existing.setCurrencyUnits(updated.getCurrencyUnits());
                    existing.setTooltip(updated.getTooltip());
                    existing.setTemplateFileUrl(updated.getTemplateFileUrl());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("DocumentType not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<DocumentType> search(String name, String label, Pageable pageable) {
        return repository.search(name, label, pageable);
    }
}
