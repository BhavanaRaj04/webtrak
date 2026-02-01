package com.webtrak.controller;

import com.webtrak.entity.User;
import com.webtrak.service.UserService;
import com.webtrak.util.JwtUtil;
import com.webtrak.util.PasswordUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {

        User user = service.getByEmail(req.get("email"));

        if (!user.getStatus().equals("ACTIVE"))
            throw new RuntimeException("User inactive");

        if (!PasswordUtil.matches(req.get("password"), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String token = JwtUtil.generate(
                user.getId().toString(),
                user.getEmail(),
                user.getRole()
        );

        return Map.of("token", token);
    }
}
