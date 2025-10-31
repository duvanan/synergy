package org.example.synergy.service;

import org.example.synergy.entity.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentTypeService {

    List<DocumentType> findAll();

    DocumentType findById(Long id);

    DocumentType save(DocumentType documentType);

    DocumentType update(Long id, DocumentType updated);

    void delete(Long id);

    Page<DocumentType> search(String name, String label, Pageable pageable);
}
