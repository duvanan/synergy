CREATE TABLE workflow_step (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID tự sinh của bước',

                               workflow_config_id BIGINT COMMENT 'ID của workflow config liên kết',

                               step_number INT COMMENT 'Số bước (1, 2, 3,...)',
                               label VARCHAR(255) COMMENT 'Tên hiển thị của bước',
                               step_type VARCHAR(100) COMMENT 'Loại bước (Tất cả phê duyệt / Chỉ cần 1 người phê duyệt)',

                               min_value DOUBLE COMMENT 'Giá trị nhỏ nhất (nếu có)',
                               max_value DOUBLE COMMENT 'Giá trị lớn nhất (nếu có)',
                               unit VARCHAR(50) COMMENT 'Đơn vị (VD: %, ngày,...)',
                               tooltip VARCHAR(500) COMMENT 'Gợi ý hướng dẫn người dùng',

                               step_max_sla INT COMMENT 'Tiêu chuẩn thời gian thẩm định (ngày)',
                               step_warning_sla INT COMMENT 'Cảnh báo trước (ngày)',
                               step_warning_person VARCHAR(500) COMMENT 'Danh sách nhân viên cảnh báo',

                               department_id VARCHAR(50) COMMENT 'Mã phòng ban',
                               pic VARCHAR(50) COMMENT 'Mã nhân viên phụ trách'


) COMMENT='Bảng lưu thông tin các bước của workflow';
