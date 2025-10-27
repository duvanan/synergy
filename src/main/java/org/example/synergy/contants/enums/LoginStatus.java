/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.contants.enums;

public enum LoginStatus {
    Success,
    Failed,
    Wrong_password,
    Invalid_username,
    Account_locked,
    Account_temp_locked,
    MFA_failed,
    IP_blocked,
    Device_unauthorized,
    System_error,
    Session_expired,
    Access_denied
}
