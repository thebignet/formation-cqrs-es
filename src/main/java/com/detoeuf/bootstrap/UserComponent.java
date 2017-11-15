package com.detoeuf.bootstrap;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component
public interface UserComponent {
    UserService userService();
}
