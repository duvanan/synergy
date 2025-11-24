-- Bảng lưu lịch sử/thao tác (audit)
CREATE TABLE appraisal_request_history (
                                   id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                   appraisal_request_id BIGINT NOT NULL COMMENT 'Tham chiếu đến appraisal_request.id',
                                   action VARCHAR(150) COMMENT 'hành động',
                                   action_by VARCHAR(150) COMMENT 'Người thực hiện hành động',
                                   action_type VARCHAR(100) COMMENT 'Hành động: SAVE_DRAFT, SUBMIT, APPROVE, REJECT',
                                   comment TEXT COMMENT 'Ghi chú/phản hồi khi thực hiện hành động',
                                   created_at DATETIME NOT NULL COMMENT 'Thời gian hành động'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Lịch sử các hành động đối với yêu cầu thẩm định';
