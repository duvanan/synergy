CREATE TABLE appraisal_request_file (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính file đính kèm',
                                        appraisal_request_id BIGINT NOT NULL COMMENT 'ID yêu cầu thẩm định',
                                        file_name VARCHAR(255) COMMENT 'Tên file',
                                        file_path VARCHAR(500) COMMENT 'Đường dẫn file lưu trữ',
                                        file_type VARCHAR(50) COMMENT 'Loại file (pdf, docx, xlsx, ...)',
                                        uploaded_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày upload'
);
