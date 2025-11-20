/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.example.synergy.contants.enums.UserType;
import org.example.synergy.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
public class UserDetailsImpl implements UserDetails {
    
    private final Long id;
    
    private final String code;
    
    private final String username;

    private final String password;
    
    private final String email;
    
    private final String fullName;
    
    private final String phoneNumber;
    
    private String jti;
    
    private final Boolean status;
    
    private  Boolean isAssign;
    
    private Long jobTitleId;
    
    private Long departmentId;
    
    private final List<GrantedAuthority> authorities;
    
    private Integer userType;
    
    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.code = user.getCode();
        this.username = user.getUserCode();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.phoneNumber = user.getPhoneNumber();
        this.status = user.getStatus();
        this.userType = user.getType();
        this.authorities = UserType.ADMIN.getValue() == userType
                ? List.of(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN)) : new ArrayList<>();
    }
    
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
