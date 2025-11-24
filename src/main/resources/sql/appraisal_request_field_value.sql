CREATE TABLE appraisal_request_field_value (
                                               id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính giá trị trường động',
                                               appraisal_request_id BIGINT NOT NULL COMMENT 'ID yêu cầu thẩm định',
                                               field_key VARCHAR(255) NOT NULL COMMENT 'Key định danh duy nhất của trường (vd: partner_name_1)',
                                               field_label NVARCHAR(255) COMMENT 'Nhãn hiển thị của trường (vd: Tên doanh nghiệp/cá nhân)',
                                               field_type VARCHAR(50) COMMENT 'Kiểu dữ liệu: text, number, date, file, select...',
                                               field_value TEXT COMMENT 'Giá trị nhập thực tế',
                                               created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời điểm tạo'
);
