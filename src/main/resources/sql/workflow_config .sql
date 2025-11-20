CREATE TABLE workflow_config (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính tự tăng',
                                 name VARCHAR(150) NOT NULL COMMENT 'Tên cấu hình workflow',
                                 document_type_id BIGINT NOT NULL COMMENT 'ID loại văn bản',
                                 description VARCHAR(500) NULL COMMENT 'Mô tả chi tiết',
                                 max_sla INT NULL COMMENT 'Thời gian xử lý tối đa (ngày)',
                                 warning_sla INT NULL COMMENT 'Cảnh báo trước bao nhiêu ngày',
                                 warning_person VARCHAR(500) NULL COMMENT 'Danh sách mã nhân viên cảnh báo (phân cách dấu phẩy)',

    -- Thông tin audit kế thừa từ BaseAuthorEntity
                                 is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Đã xóa logic hay chưa',
                                 created_by VARCHAR(100) NULL COMMENT 'Người tạo',
                                 created_date DATETIME NULL COMMENT 'Ngày tạo',
                                 updated_user VARCHAR(100) NULL COMMENT 'Người cập nhật',
                                 updated_date DATETIME NULL COMMENT 'Ngày cập nhật'
) COMMENT='Cấu hình quy trình xử lý văn bản';