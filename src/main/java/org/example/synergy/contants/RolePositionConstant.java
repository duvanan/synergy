package org.example.synergy.contants;

import java.util.Map;

public class RolePositionConstant {

    public static final Map<String, String> POSITION_MAP = Map.ofEntries(
        Map.entry("CVKD", "Chuyên viên kinh doanh"),
        Map.entry("KSKTPM", "Kỹ sư kiểm thử phần mềm"),
        Map.entry("CVQLTS", "Chuyên viên quản lý tài sản"),
        Map.entry("KSPTPM", "Kỹ sư phát triển phần mềm"),
        Map.entry("CVDT", "Chuyên viên đào tạo"),
        Map.entry("NVHCTH", "Nhân viên hành chính tổng hợp"),
        Map.entry("CVTKUIUX", "Chuyên viên thiết kế UI/UX"),
        Map.entry("GĐTT", "Giám đốc trung tâm"),
        Map.entry("CVQLQT", "Chuyên viên quản lý chất lượng"),
        Map.entry("TPDHKD", "Trưởng phòng điều hành kinh doanh"),
        Map.entry("KSGPNV", "Kỹ sư giải pháp nghiệp vụ"),
        Map.entry("CVQTDA", "Chuyên viên quản trị dự án"),
        Map.entry("NVDT", "Nhân viên đào tạo"),
        Map.entry("CVTC", "Chuyên viên tài chính"),
        Map.entry("CVCN", "Chuyên viên công nghệ"),
        Map.entry("CVQLTD", "Chuyên viên quản lý tuyển dụng"),
        Map.entry("NVTD", "Nhân viên tuyển dụng"),
        Map.entry("CVQTHT", "Chuyên viên quản trị hệ thống"),
        Map.entry("NVTLVTTNCN", "Nhân viên truyền thông"),
        Map.entry("TPTVGP", "Trưởng phòng tư vấn giải pháp"),
        Map.entry("NVTCLD", "Nhân viên tổ chức lao động"),
        Map.entry("PPCNRD", "Phó phòng công nghệ R&D"),
        Map.entry("CVDTPT", "Chuyên viên đào tạo & phát triển"),
        Map.entry("CVHTDT", "Chuyên viên hỗ trợ đào tạo"),
        Map.entry("PGDBU", "Phó giám đốc đơn vị"),
        Map.entry("PPCNAI", "Phó phòng công nghệ AI")
    );

    public static String getPositionName(String code) {
        return POSITION_MAP.getOrDefault(code, code);
    }
}
