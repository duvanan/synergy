package org.example.synergy.service;

import org.example.synergy.dto.request.DocumentTypeRequest;
import org.example.synergy.dto.response.DocumentTypeResponse;

import java.util.List;

/**
 * Service interface quản lý loại văn bản (DocumentType)
 */
public interface DocumentTypeService {

    /**
     * Tạo mới loại văn bản
     */
    DocumentTypeResponse create(DocumentTypeRequest request);

    /**
     * Cập nhật loại văn bản
     */
    DocumentTypeResponse update(Long id, DocumentTypeRequest request);

    /**
     * Xóa loại văn bản theo ID
     */
    void delete(Long id);

    /**
     * Lấy chi tiết loại văn bản theo ID
     */
    DocumentTypeResponse getById(Long id);

    /**
     * Lọc hoặc lấy danh sách loại văn bản theo từ khóa, mã, tên
     */
    List<DocumentTypeResponse> filter(String keyword, String code);
}
