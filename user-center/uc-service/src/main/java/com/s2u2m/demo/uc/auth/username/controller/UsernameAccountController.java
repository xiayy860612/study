package com.s2u2m.demo.uc.auth.username.controller;

import com.s2u2m.demo.uc.auth.username.repository.UserInfo;
import com.s2u2m.demo.uc.auth.username.service.AccountDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/accounts/username")
public class UsernameAccountController {

    @PostMapping("/login")
    public UserInfo login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var account = (AccountDetails) auth.getPrincipal();
        return account.getInfo();
    }
}
