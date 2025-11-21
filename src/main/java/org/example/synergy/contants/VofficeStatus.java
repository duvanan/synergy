package org.example.synergy.contants;

import lombok.Getter;

public enum VofficeStatus {

    CHUA_TRINH_KY("VO_CTK", "Chưa trình ký"),
    DA_TAO_VAN_BAN_TRINH_KY("VO_DTVBTK","Đã tạo văn bản trình ký"),
    DA_KY_DUYET("VO_DKD","Đã ký duyệt"),
    DA_TU_CHOI("VO_DTC", "Đã từ chối"),

    HUY("VO_HUY", "Đã hủy");

    private final String name;
    @Getter
    private final String value;

    VofficeStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
