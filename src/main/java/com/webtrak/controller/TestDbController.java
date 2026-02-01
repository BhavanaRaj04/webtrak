package com.webtrak.controller;

import com.webtrak.entity.TestUser;
import com.webtrak.repository.TestUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/db")
public class TestDbController {

    private final TestUserRepository repository;

    public TestDbController(TestUserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/user")
    public TestUser createUser(@RequestBody TestUser user) {
        return repository.save(user);
    }

    @GetMapping("/users")
    public List<TestUser> getAllUsers() {
        return repository.findAll();
    }
}
