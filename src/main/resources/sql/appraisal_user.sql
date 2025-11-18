
CREATE TABLE appraisal_user (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính',
                                name VARCHAR(200) COMMENT 'Tên người dùng',
                                appraisal_request_id BIGINT COMMENT 'Tên người dùng',
                                code VARCHAR(100) COMMENT 'Mã người dùng',
                                level INT COMMENT 'Cấp độ thẩm định',
                                organization VARCHAR(255) COMMENT 'Đơn vị',
                                role VARCHAR(200) COMMENT 'Chức vụ',
                                appraised TINYINT COMMENT 'Đã thẩm định hay chưa',
                                host_unit VARCHAR(255) COMMENT 'Đơn vị chủ trì'
) COMMENT='Bảng lưu thông tin người thẩm định (AppraisalUser)';
