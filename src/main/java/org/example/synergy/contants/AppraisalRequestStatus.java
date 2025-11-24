package org.example.synergy.contants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppraisalRequestStatus {
    DRAFT("DRAFT", "Lưu nháp"),
    SUBMITTED("SUBMITTED", "Gửi duyệt"),
    APPROVED("APPROVED", "Đã duyệt"),
    REJECTED("REJECTED", "Từ chối");

    private final String code;
    private final String description;
}
