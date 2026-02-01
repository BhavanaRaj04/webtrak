package com.webtrak.controller;

import com.webtrak.dto.UserResponseDto;
import com.webtrak.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService service;

    public ProfileController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public UserResponseDto me(HttpServletRequest request) {
        UUID userId = UUID.fromString(
                request.getAttribute("userId").toString());
        return service.getById(userId);
    }

    @PatchMapping("/change-password")
    public Map<String, String> changePassword(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {
                
        UUID userId = UUID.fromString(
                request.getAttribute("userId").toString());


        service.changeOwnPassword(
                userId,
                body.get("oldPassword"),
                body.get("newPassword"));

        return Map.of("message", "Password changed successfully");
    }
}
