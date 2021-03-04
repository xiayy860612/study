package com.s2u2m.demo.uc.auth.username.service;

import com.s2u2m.demo.uc.auth.username.repository.UserInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AccountDetails extends User {
    private UserInfo info;

    public AccountDetails(UserInfo info, String password, Collection<? extends GrantedAuthority> authorities) {
        super(info.getUsername(), password, authorities);
        this.info = info;
    }
}
