/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.service;

import org.example.synergy.dto.request.loginhistory.AuthenticationRequest;
import org.example.synergy.dto.response.auth.AuthenticationDTO;

public interface AuthenticationService {
    
    AuthenticationDTO login(AuthenticationRequest authenticationRequest);
}
