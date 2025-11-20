/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.synergy.contants.Constants;
import org.example.synergy.exceptions.UserAccountLockedException;
import org.example.synergy.exceptions.UserNotActivatedException;
import org.example.synergy.model.User;
import org.example.synergy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.BooleanUtils;

/**
 * Authenticate a user from the database.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(final String login) {
        Optional<User> userOptional = userRepository.findByIsDeletedIsFalseAndUserCode(login);
        
        if (userOptional.isEmpty()) {
            log.error("User not found with username: {}", login);
            throw new UsernameNotFoundException("User not found with username: " + login);
        }
        User user = userOptional.get();
        if (BooleanUtils.isFalse(user.getStatus())) {
            throw new UserNotActivatedException("User not active with username: " + login);
        }
        return new UserDetailsImpl(user);
    }
}
