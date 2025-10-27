/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.contants;

import java.util.regex.Pattern;

/**
 * Application constants.
 */
public final class Constants {
    
    private Constants() {
    }
    
    // Regex for acceptable logins
    public static final String LOGIN_REGEX =
            "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    
    public static final String LOGIN_VALID_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    
    public static final String SYSTEM = "system";
    
    public static final String DEFAULT_LANGUAGE = "en";
    
    public static final String DEV_PROFILE = "dev";
    
    public static final String PROD_PROFILE = "prod";
    
    public static final String RFIAS_REDISSON = "rfias_redisson";
    
    public static final String RFIAS_APP_NAME = "rfias";
    
    public static String DEFAULT_TIME_ZONE = "Asia/Ho_Chi_Minh";
    
    public static final String UTF8MB4_UNICODE_520_CI = "utf8mb4_unicode_520_ci";
    
    public static final String UTF8MB4_VIETNAMESE_CI = "utf8mb4_vietnamese_ci";
    
    public static final String IMAGE_FILE_TYPE = "image";
    
    public static final String DOCUMENT_FILE_TYPE = "document";
    
    public static final String CSV_FILE_TYPE = "csv";
    
    public static final String EXCEL_FILE_TYPE = "excel";
    
    public static final String PDF_FILE_TYPE = "pdf";
    
    public static final String VIDEO_FILE_TYPE = "video";
    
    public static final String UPLOADS_DIR = "uploads";
    
    public static final String UPLOADS_PATH = "/uploads/";
    
    public static final String SLASH = "/";
    
    public static final String NUMBER_FORMAT = "^-?\\d+(\\.\\d+)?$";
    
    public static final String INTERFERENCE_DATA_ENDPOINT = "/API/InterferenceData";
    
    public static final String LOGIN_ENDPOINT = "/API/Login";
    
    public static final String REQUEST_ADJACENT_CELL_ENDPOINT = "/API/RequestAdjacentCell";
    
    public static final String REQUEST_ADJACENT_CELL_MBF_ENDPOINT = "/cts_call_mbf/external/RequestAdjacentCell";
    
    public static final String NOTIFICATION_MBF_ENDPOINT = "/cts_call_mbf/external/notification";
    
    public static final String LOGIN_MBF_ENDPOINT = "/cts_call_mbf/external/auth/login";
    
    public static final String ADJACENT_DATA_ENDPOINT = "/API/AdjacentData";
    
    public static final String NOTIFICATION_ENDPOINT = "/API/Notification";
    
    public static final String QUOTA_REQUEST_ENDPOINT = "/API/RequestQuota";
    
    public static final Double DAILY_INTERFERENCE_AVG_DEFAULT = -999.0;
    
    public static final int MAX_CONSECUTIVE_ERRORS = 3;
    
    public static final double EPSILON = 1e-6;
    
    public static final int MAX_CHARACTERS_255 = 255;
    
    public static final int MAX_CHARACTERS_10 = 10;
    
    public static final int MAX_CHARACTERS_20 = 20;
    
    public static final int MAX_CHARACTERS_30 = 30;
    
    public static final int MAX_CHARACTERS_50 = 50;
    
    public static final int MAX_CHARACTERS_2000 = 2000;
    
    public static final int MAX_CHARACTERS_100 = 100;
    
    public static final int MAX_FAILED_PASSWORD_ATTEMPTS = 5;
    
    public static final int MAX_FAILED_OTP_ATTEMPTS = 5;
    
    public static final Pattern FREQUENCY_PATTERN = Pattern.compile("^\\d+(,\\d)?MHz(?:;\\s{1}\\d+(,\\d)?MHz)*$");
}
