package org.example.synergy.contants;

import lombok.Getter;

public enum DocumentSignEnum {
    DOCUMENT_SIGN(1,"Văn bản trình ký"),
    DOCUMENT_TAG(2, "Văn bản đính kèm"),
    DOCUMENT_EVIDENCE(3, "Văn bản sở cứ"),
    DOCUMENT_DOC(4, "Văn bản biểu mẫu");

    @Getter
    private final Integer value;

    @Getter
    private final String name;

    public static Integer getValueByName(String name) {
        if (name == null) return null;
        for (DocumentSignEnum option : DocumentSignEnum.values()) {
            if (option.getName().equals(name)) {
                return option.getValue();
            }
        }
        return null; // hoặc ném ngoại lệ nếu cần thiết
    }

    DocumentSignEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
