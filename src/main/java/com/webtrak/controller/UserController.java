package com.webtrak.controller;

import com.webtrak.entity.User;
import com.webtrak.service.UserService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.webtrak.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponseDto createUser(
            @RequestBody User user,
            HttpServletRequest request) {
        UUID adminId = UUID.fromString(request.getAttribute("userId").toString());
        return service.createUser(user, adminId);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{employeeId}")
    public UserResponseDto getUserByEmployeeId(@PathVariable String employeeId) {
        return service.getByEmployeeId(employeeId);
    }

    @PutMapping("/{employeeId}")
    public UserResponseDto updateUser(
            @PathVariable String employeeId,
            @RequestBody User user,
            HttpServletRequest request) {
        UUID adminId = UUID.fromString(request.getAttribute("userId").toString());
        return service.updateUser(employeeId, user, adminId);
    }

    @PatchMapping("/{employeeId}/deactivate")
    public UserResponseDto deactivateUser(
            @PathVariable String employeeId,
            HttpServletRequest request) {
        UUID adminId = UUID.fromString(request.getAttribute("userId").toString());
        return service.deactivateUser(employeeId, adminId);
    }

    @PatchMapping("/{employeeId}/activate")
    public UserResponseDto activateUser(
            @PathVariable String employeeId,
            HttpServletRequest request) {
        UUID adminId = UUID.fromString(request.getAttribute("userId").toString());
        return service.activateUser(employeeId, adminId);
    }

    @PatchMapping("/{employeeId}/change-password")
    public Map<String, String> adminChangePassword(
            @PathVariable String employeeId,
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {
        UUID adminId = UUID.fromString(
                request.getAttribute("userId").toString());

        service.adminChangePassword(
                employeeId,
                body.get("newPassword"),
                adminId);

        return Map.of("message", "Password changed successfully");
    }

}
