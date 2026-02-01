package com.webtrak.service;

import com.webtrak.entity.User;
import com.webtrak.repository.UserRepository;
import com.webtrak.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User createUser(User user) {

        if (repo.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email already exists");

        if (repo.existsByEmployeeId(user.getEmployeeId()))
            throw new RuntimeException("Employee ID already exists");

        user.setPassword(PasswordUtil.hash(user.getPassword()));
        user.setStatus("ACTIVE");

        return repo.save(user);
    }

    public User getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
