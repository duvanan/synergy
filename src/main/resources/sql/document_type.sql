CREATE TABLE document_type (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính',

    -- Thông tin chung
                               document_type_name VARCHAR(255) NOT NULL COMMENT 'Tên loại văn bản',
                               description TEXT NULL COMMENT 'Mô tả loại văn bản',

    -- Thông tin thuộc tính
                               label VARCHAR(255) NOT NULL COMMENT 'Tên thuộc tính hiển thị',
                               is_required BOOLEAN DEFAULT FALSE COMMENT 'Thuộc tính có bắt buộc nhập hay không',
                               attribute_description TEXT NULL COMMENT 'Mô tả thuộc tính',
                               data_type VARCHAR(100) NOT NULL COMMENT 'Kiểu dữ liệu của thuộc tính (string, number, date...)',
                               default_value VARCHAR(500) NULL COMMENT 'Giá trị mặc định của thuộc tính',
                               min_value VARCHAR(100) NULL COMMENT 'Giá trị nhỏ nhất (nếu có)',
                               max_value VARCHAR(100) NULL COMMENT 'Giá trị lớn nhất (nếu có)',
                               value_list TEXT NULL COMMENT 'Danh sách giá trị, cách nhau bởi dấu “;”',
                               currency_units VARCHAR(255) NULL COMMENT 'Đơn vị tiền tệ có thể sử dụng',
                               tooltip VARCHAR(500) NULL COMMENT 'Tooltip hướng dẫn người dùng',

    -- File biểu mẫu
                               template_file_url VARCHAR(500) NULL COMMENT 'Đường dẫn hoặc tên file biểu mẫu tải lên',

    -- Metadata
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo',
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Thời gian cập nhật'
) COMMENT='Bảng lưu thông tin loại văn bản và các thuộc tính của nó';
