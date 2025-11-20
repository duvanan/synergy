CREATE TABLE document_type_attribute (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính',
                                         document_type_id BIGINT NOT NULL COMMENT 'Khóa ngoại đến bảng document_type',
                                         label VARCHAR(255) NOT NULL COMMENT 'Label hiển thị thuộc tính',
                                         field_code VARCHAR(100) NOT NULL COMMENT 'Mã thuộc tính trong hệ thống',
                                         required BOOLEAN DEFAULT FALSE COMMENT 'Có bắt buộc nhập hay không',
                                         data_type VARCHAR(50) NOT NULL COMMENT 'Kiểu dữ liệu (string, number, date, boolean, ...)',
                                         default_value VARCHAR(255) NULL COMMENT 'Giá trị mặc định',
                                         min_value VARCHAR(255) NULL COMMENT 'Giá trị nhỏ nhất',
                                         max_value VARCHAR(255) NULL COMMENT 'Giá trị lớn nhất',
                                         unit_list VARCHAR(500) NULL COMMENT 'Danh sách đơn vị có thể dùng, cách nhau dấu ;',
                                         tooltip VARCHAR(500) NULL COMMENT 'Tooltip hướng dẫn người dùng nhập liệu',
                                         value_list TEXT NULL COMMENT 'Danh sách giá trị lựa chọn (nếu kiểu ENUM)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Bảng thuộc tính của loại văn bản';
