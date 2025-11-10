CREATE TABLE partner_relation (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính',
                                  partner_id BIGINT NOT NULL COMMENT 'ID đối tác',
                                  employee_code VARCHAR(100) COMMENT 'Mã nhân viên',
                                  employee_name VARCHAR(255) COMMENT 'Tên nhân viên',
                                  relationship VARCHAR(255) COMMENT 'Mối quan hệ'
) COMMENT='Bảng nhân sự liên quan đối tác';