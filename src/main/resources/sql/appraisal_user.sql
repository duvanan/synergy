
CREATE TABLE appraisal_user (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính',
                                name VARCHAR(200) COMMENT 'Tên người dùng',
                                appraisal_request_id BIGINT COMMENT 'Tên người dùng',
                                code VARCHAR(100) COMMENT 'Mã người dùng',
                                level INT COMMENT 'Cấp độ thẩm định',
                                organization VARCHAR(255) COMMENT 'Đơn vị',
                                role VARCHAR(200) COMMENT 'Chức vụ',
                                appraised TINYINT COMMENT 'Đã thẩm định hay chưa',
                                file_name VARCHAR(255) COMMENT 'Tên file nhận xét',
                                file_path VARCHAR(255) COMMENT 'path file',
                                note VARCHAR(255) COMMENT 'nhận xét',
                                host_unit TINYINT COMMENT 'Đơn vị chủ trì',
                                is_deleted TINYINT COMMENT 'xóa',
                                appraised_time DATETIME COMMENT 'Thời gian duyệt'
) COMMENT='Bảng lưu thông tin người thẩm định (AppraisalUser)';
