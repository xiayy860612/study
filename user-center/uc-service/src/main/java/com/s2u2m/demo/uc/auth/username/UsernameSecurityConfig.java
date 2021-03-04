package com.s2u2m.demo.uc.auth.username;

import com.s2u2m.demo.uc.auth.username.service.UsernameAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class UsernameSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsernameAccountService usernameAccountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.antMatcher("/accounts/username/*")
                .authorizeRequests()
                .antMatchers("/accounts/username/login").permitAll()
                .anyRequest().authenticated();
        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usernameAccountService);
    }
}
