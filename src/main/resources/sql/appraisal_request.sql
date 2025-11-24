CREATE TABLE appraisal_request (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính yêu cầu thẩm định',
                                   request_code VARCHAR(100) NOT NULL UNIQUE COMMENT 'Mã yêu cầu thẩm định sinh tự động',
                                   document_type_id  BIGINT COMMENT 'id loại văn bản tham chiếu sang cấu hình',
                                   priority_level VARCHAR(20) COMMENT 'Mức độ ưu tiên: Bình thường, Cao, Hỏa tốc',
                                   response_deadline DATE COMMENT 'Hạn phản hồi dự kiến',
                                   note TEXT COMMENT 'Ghi chú của người tạo yêu cầu',
                                   status VARCHAR(50) NOT NULL COMMENT 'Trạng thái xử lý: DRAFT, SUBMITTED, APPROVED, REJECTED',
                                   is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Đã xóa logic hay chưa',
                                   created_by VARCHAR(100) NULL COMMENT 'Người tạo',
                                   created_date DATETIME NULL COMMENT 'Ngày tạo',
                                   updated_user VARCHAR(100) NULL COMMENT 'Người cập nhật',
                                   updated_date DATETIME NULL COMMENT 'Ngày cập nhật'
);
