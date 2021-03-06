package com.s2u2m.demo.uc.auth.username.repository;

public interface UsernameAccountRepository {
    UserInfo get(String username);
    String getPassword(String username);
}
