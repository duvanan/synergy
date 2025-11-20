CREATE TABLE document_type (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính',
                               name VARCHAR(255) NOT NULL COMMENT 'Tên loại văn bản',
                               code VARCHAR(100) NOT NULL UNIQUE COMMENT 'Mã loại văn bản',
                               description TEXT NULL COMMENT 'Mô tả loại văn bản',
                               template_file_path VARCHAR(500) NULL COMMENT 'Tên hoặc đường dẫn file biểu mẫu',
    -- Thông tin audit kế thừa từ BaseAuthorEntity
                               is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Đã xóa logic hay chưa',
                               created_by VARCHAR(100) NULL COMMENT 'Người tạo',
                               created_date DATETIME NULL COMMENT 'Ngày tạo',
                               updated_user VARCHAR(100) NULL COMMENT 'Người cập nhật',
                               updated_date DATETIME NULL COMMENT 'Ngày cập nhật'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Bảng lưu loại văn bản';
