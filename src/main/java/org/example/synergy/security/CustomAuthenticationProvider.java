/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.synergy.contants.ErrorKeyConstants;
import org.example.synergy.exceptions.ExceptionTranslator;
import org.example.synergy.exceptions.UnauthorizedException;
import org.example.synergy.exceptions.UserAccountLockedException;
import org.example.synergy.exceptions.UserNotActivatedException;
import org.example.synergy.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ExceptionTranslator exp;
    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            } else {
                userRepository.incrementFailedAttemptsAndDeactivateIfNeeded(username);


                throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.bad_credentials",
                        ErrorKeyConstants.Auth.BAD_CREDENTIALS);
            }
        } catch (UsernameNotFoundException e) {

            throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.bad_credentials",
                    ErrorKeyConstants.Auth.BAD_CREDENTIALS);
        } catch (UserNotActivatedException e) {

            throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.user_locked",
                    ErrorKeyConstants.Auth.USER_LOCKED);
        } catch (UserAccountLockedException e) {
            throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.user_locked",
                    ErrorKeyConstants.Auth.USER_LOCKED);
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
