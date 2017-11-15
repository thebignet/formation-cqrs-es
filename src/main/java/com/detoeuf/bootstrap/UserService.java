package com.detoeuf.bootstrap;

import javax.inject.Inject;

public class UserService {
    private NameService nameService;

    @Inject
    public UserService(NameService nameService) {
        this.nameService = nameService;
    }

    public String name() {
        return nameService.machin();
    }
}
