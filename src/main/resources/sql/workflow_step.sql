CREATE TABLE workflow_step (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính bước workflow',

                               workflow_config_id BIGINT NOT NULL COMMENT 'Khóa ngoại liên kết tới cấu hình workflow cha',

                               step_number INT NOT NULL COMMENT 'Số thứ tự của bước trong quy trình',
                               sub_step_number INT DEFAULT 1 COMMENT 'Thứ tự phụ trong bước (nếu có)',

                               department_id VARCHAR(50) NOT NULL COMMENT 'Mã phòng ban phụ trách bước này',
                               pic_code VARCHAR(50) NOT NULL COMMENT 'Mã nhân viên PIC (người chịu trách nhiệm chính)',

                               step_max_sla INT NOT NULL COMMENT 'SLA tối đa cho bước này',
                               step_warning_sla INT NOT NULL COMMENT 'Ngưỡng cảnh báo SLA của bước (phải nhỏ hơn step_max_sla)',

                               step_warning_persons VARCHAR(500) COMMENT 'Danh sách người nhận cảnh báo tại bước này (mã nhân viên, phân cách bằng dấu phẩy)',

                               created_by VARCHAR(100) COMMENT 'Người tạo bước workflow',
                               created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo bước',
                               updated_by VARCHAR(100) COMMENT 'Người cập nhật bước workflow cuối cùng',
                               updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Thời gian cập nhật bước cuối cùng',

) COMMENT='Bảng lưu chi tiết các bước trong Workflow thẩm định';