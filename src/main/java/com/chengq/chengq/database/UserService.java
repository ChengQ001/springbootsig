package com.chengq.chengq.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserService {

    public UserBean getUser(String username) {
        // 没有此用户直接返回null
        if (!DataSource.getData().containsKey(username))
            return null;

        UserBean user = new UserBean();
        Map<String, String> detail = DataSource.getData().get(username);

        user.setUsername(username);
        user.setPassword(detail.get("password"));
        user.setRole(detail.get("role"));
        user.setPermission(detail.get("permission"));
        return user;
    }

    public Object test() {

        log.info("info");
        log.debug("debug");
        log.error("error");
        log.warn("error");

        CompletableFuture.runAsync(()->{
            log.info("thread info");
        });
        return null;
    }
}
