package com.detoeuf.bootstrap;

import io.vavr.collection.List;

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

    public List<String> names() {
        return List.of("Jean", "Pierre", "Jacques");
    }
}
