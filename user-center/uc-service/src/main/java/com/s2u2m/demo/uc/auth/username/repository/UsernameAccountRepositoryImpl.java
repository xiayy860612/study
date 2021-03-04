package com.s2u2m.demo.uc.auth.username.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsernameAccountRepositoryImpl implements UsernameAccountRepository {
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    private final Map<String, Map<String, String>> users = new HashMap<>();

    @Autowired
    private ApplicationContext context;

    @Getter
    @Setter
    private static class UsersConfig {
        private List<Map<String, String>> users;
    }

    @PostConstruct
    public void init() throws IOException {
        Resource resource = context.getResource("classpath:username-account.json");
        ObjectMapper mapper = new ObjectMapper();
        UsersConfig config = mapper.readValue(resource.getFile(), UsersConfig.class);
        config.getUsers().forEach(user -> users.put(user.get(USERNAME_KEY), user));
    }

    @Override
    public UserInfo get(String username) {
        return Optional.of(users.get(username))
                .map(map -> {
                    var user = new UserInfo();
                    user.setUsername(map.get(USERNAME_KEY));
                    return user;
                })
                .get();
    }

    @Override
    public String getPassword(String username) {
        return Optional.of(users.get(username))
                .map(map -> map.get(PASSWORD_KEY))
                .orElse("");
    }
}
