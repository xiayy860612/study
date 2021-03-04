package com.s2u2m.demo.uc.auth.username.service;

import com.s2u2m.demo.uc.auth.username.repository.UserInfo;
import com.s2u2m.demo.uc.auth.username.repository.UsernameAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsernameAccountService implements UserDetailsService {

    @Autowired
    private UsernameAccountRepository usernameAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo info = usernameAccountRepository.get(username);
        String password = usernameAccountRepository.getPassword(username);
        return new AccountDetails(info, password, Collections.emptyList());
    }
}
