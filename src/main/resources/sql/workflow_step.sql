-- XÓA BẢNG NẾU TỒN TẠI
DROP TABLE IF EXISTS workflow_step;

-- TẠO BẢNG MỚI THEO ENTITY MỚI
CREATE TABLE workflow_step (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,

                               workflow_config_id BIGINT NOT NULL,

                               parent_step INT NULL,     -- Bước cha: 1,2,3...
                               child_step INT NULL,      -- Bước con: 1,2,3...

                               department_id BIGINT NULL,   -- Mã phòng ban thẩm định
                               pic VARCHAR(50) NULL,             -- Nhân sự thẩm định

                               is_lead_unit TINYINT(1) DEFAULT 0, -- Đơn vị chủ trì (boolean)

                               step_max_sla INT NULL,        -- Giới hạn thời gian
                               step_warning_sla INT NULL,    -- Cảnh báo trước
                               step_warning_person VARCHAR(255) NULL, -- Người nhận cảnh báo

                               tooltip VARCHAR(500) NULL     -- Gợi ý hướng dẫn


);
