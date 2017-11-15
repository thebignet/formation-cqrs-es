package com.detoeuf.bootstrap;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info(User.of("Jean").bonjour());

        val userComponent = DaggerUserComponent.create();
        val userService = userComponent.userService();
        log.info("nom : " + userService.name());
    }

}
