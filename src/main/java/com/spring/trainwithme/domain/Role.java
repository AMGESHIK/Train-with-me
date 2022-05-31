package com.spring.trainwithme.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    PREM_USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
