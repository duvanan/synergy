CREATE TABLE notification_config (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID cấu hình',
                                     document_type_id BIGINT NOT NULL COMMENT 'ID loại văn bản',
                                     sla_content VARCHAR(1000) NOT NULL COMMENT 'Nội dung cảnh báo SLA',
                                     channels VARCHAR(255) NOT NULL COMMENT 'Danh sách kênh thông báo (Email, Web, SMS, Push)',
                                     template_html TEXT NOT NULL COMMENT 'Mẫu HTML hiển thị thông báo',
                                     created_by VARCHAR(100) COMMENT 'Người tạo',
                                     created_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
                                     updated_by VARCHAR(100) COMMENT 'Người cập nhật',
                                     updated_date DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
                                     CONSTRAINT fk_notification_document_type FOREIGN KEY (document_type_id)
                                         REFERENCES document_type(id)
);

