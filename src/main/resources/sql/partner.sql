CREATE TABLE partner (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Khóa chính',
                         type VARCHAR(20) NOT NULL COMMENT 'Loại đối tác: BUSINESS hoặc PERSONAL',
                         name VARCHAR(255) NOT NULL COMMENT 'Tên đối tác',
                         partner_type VARCHAR(255) COMMENT 'Loại đối tác chi tiết (theo danh mục)',
                         tax_code VARCHAR(50) COMMENT 'Mã số thuế (dành cho tổ chức)',
                         cccd VARCHAR(50) COMMENT 'CCCD (dành cho cá nhân)',
                         contact_info VARCHAR(255) COMMENT 'Thông tin liên hệ (dành cho cá nhân)',
                         invoice_address VARCHAR(255) COMMENT 'Địa chỉ xuất hóa đơn (doanh nghiệp)',
                         invoice_email VARCHAR(255) COMMENT 'Email nhận hóa đơn điện tử',
                         legal_representative_name VARCHAR(255) COMMENT 'Tên người đại diện pháp luật',
                         legal_representative_id VARCHAR(50) COMMENT 'CCCD người đại diện pháp luật',
                         legal_representative_address VARCHAR(255) COMMENT 'Địa chỉ người đại diện pháp luật',
                         legal_representative_phone VARCHAR(50) COMMENT 'SĐT người đại diện pháp luật',
                         connected BOOLEAN DEFAULT TRUE COMMENT 'Trạng thái kết nối: true=còn, false=ngắt',
    -- Thông tin audit kế thừa từ BaseAuthorEntity
                         is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Đã xóa logic hay chưa',
                         created_by VARCHAR(100) NULL COMMENT 'Người tạo',
                         created_date DATETIME NULL COMMENT 'Ngày tạo',
                         updated_user VARCHAR(100) NULL COMMENT 'Người cập nhật',
                         updated_date DATETIME NULL COMMENT 'Ngày cập nhật'
);