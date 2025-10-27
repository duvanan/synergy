/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.contants;

public final class ErrorKeyConstants {
    
    private ErrorKeyConstants() {
    }
    
    public static final String FORBIDDEN = "FORBIDDEN";
    
    public static final String EXCEL_INCORRECT_FORMAT = "EXCEL_INCORRECT_FORMAT";
    
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    
    public static final String RESOURCE_ALREADY_EXISTS = "RESOURCE_ALREADY_EXISTS";
    
    public static final String EXCEL_FILE_EMPTY = "EXCEL_FILE_EMPTY";
    
    public static final String EXCEL_OVER_SIZE = "EXCEL_OVER_SIZE";
    
    public static final String OTP_INCORRECT = "OTP_INCORRECT";
    
    public static final String OTP_EXPIRED = "OTP_EXPIRED";
    
    public static class File {
        
        public static final String FILE_EMPTY = "FILE_EMPTY";
        
        public static final String INVALID_FILE_NAME = "INVALID_FILE_NAME";
        
        public static final String INVALID_EXTENSION = "INVALID_EXTENSION";
        
        public static final String OVER_SIZE_FILE = "OVER_SIZE_FILE";
        
        public static final String INVALID_CONTENT_TYPE = "INVALID_CONTENT_TYPE";
        
        public static final String FAILED_FILE_UPLOAD = "FAILED_FILE_UPLOAD";
    }
    
    public static class Auth {
        
        public static final String BAD_CREDENTIALS = "BAD_CREDENTIALS";
        
        public static final String USER_LOCKED = "USER_LOCKED";
        
        public static final String TOKEN_INVALID_OR_EXPIRED = "TOKEN_INVALID_OR_EXPIRED";
        
        public static final String UNAUTHORIZED = "UNAUTHORIZED";
        
        public static final String PASSWORDS_MUST_MATCH = "PASSWORDS_MUST_MATCH";
        
        public static final String NEW_PASSWORD_CANNOT_BE_SAME_AS_CURRENT = "NEW_PASSWORD_CANNOT_BE_SAME_AS_CURRENT";
        
        public static final String CURRENT_PASSWORD_INCORRECT = "CURRENT_PASSWORD_INCORRECT";
    }
    
    public static class User {
        
        public static final String INVALID_USER_TYPE = "INVALID_USER_TYPE";
    }
    
    public static class Department {
        
        public static final String DEPARTMENT_HAS_ACTIVE_USERS = "DEPARTMENT_HAS_ACTIVE_USERS";
    }
    
    public static class InterferenceNotification {
        
        public static final String INVALID_LICENSE = "INVALID_LICENSE";
        
        public static final String MOBILE_RENEWAL_DATE_INVALID = "MOBILE_RENEWAL_DATE_INVALID";
        
        public static final String LICENSED_RENEWAL_DATE_INVALID = "LICENSED_RENEWAL_DATE_INVALID";
        
        public static final String STATUS_INVALID = "STATUS_INVALID";
    }
    
    public static class License {
        
        public static final String INVALID_FREQUENCY_FORMAT = "INVALID_FREQUENCY_FORMAT";
        
        public static final String LICENSE_NUMBER_NOT_FOUND = "LICENSE_NUMBER_NOT_FOUND";
        
        public static final String FREQUENCY_NOT_FOUND = "FREQUENCY_NOT_FOUND";
        
        public static final String EXPIRED_LICENSE = "EXPIRED_LICENSE";
    }
    
    public static class Task {
        
        public static final String INVALID_TASK_PHASE = "INVALID_TASK_PHASE";
        
        public static final String INVALID_LEAD_USER = "INVALID_LEAD_USER";
        
        public static final String TASK_RENEWAL_DATE_INVALID = "TASK_RENEWAL_DATE_INVALID";
        
        public static final String TASK_DETAIL_TYPE_EMPTY = "TASK_DETAIL_TYPE_EMPTY";
    }
}
