package com.webtrak.service;

import com.webtrak.entity.User;
import com.webtrak.repository.UserRepository;
import com.webtrak.util.PasswordUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import com.webtrak.util.PasswordUtil;
import com.webtrak.dto.UserResponseDto;
import java.util.stream.Collectors;
import com.webtrak.exception.UserAlreadyExistsException;
import com.webtrak.exception.UserNotFoundException;
import com.webtrak.exception.InvalidCredentialsException;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponseDto createUser(User user, UUID performedBy) {

        if (repo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        if (repo.existsByEmployeeId(user.getEmployeeId())) {
            throw new UserAlreadyExistsException("User with this employee ID already exists");
        }

        user.setPassword(PasswordUtil.hash(user.getPassword()));
        user.setStatus("ACTIVE");
        user.setCreatedBy(performedBy);
        user.setUpdatedBy(performedBy);

        User saved = repo.save(user);
        return mapToDto(saved);
    }

    public User getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public List<UserResponseDto> getAllUsers() {
        return repo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getByEmployeeId(String employeeId) {
        User user = repo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UserNotFoundException("User not found with employeeId: " + employeeId));
        return mapToDto(user);
    }

    public UserResponseDto updateUser(String employeeId, User updatedData, UUID performedBy) {

        User existingUser = repo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("User not found with employeeId: " + employeeId));

        existingUser.setName(updatedData.getName());
        existingUser.setRole(updatedData.getRole());
        existingUser.setStatus(updatedData.getStatus());
        existingUser.setUpdatedBy(performedBy);

        User saved = repo.save(existingUser);
        return mapToDto(saved);
    }

    public UserResponseDto deactivateUser(String employeeId, UUID performedBy) {

        User user = repo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UserNotFoundException("User not found with employeeId: " + employeeId));

        user.setStatus("INACTIVE");
        user.setUpdatedBy(performedBy);

        User saved = repo.save(user);
        return mapToDto(saved);
    }

    public UserResponseDto activateUser(String employeeId, UUID performedBy) {

        User user = repo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UserNotFoundException("User not found with employeeId: " + employeeId));

        user.setStatus("ACTIVE");
        user.setUpdatedBy(performedBy);

        User saved = repo.save(user);
        return mapToDto(saved);
    }

    public void adminChangePassword(
            String employeeId,
            String newPassword,
            UUID performedBy) {
        User user = repo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UserNotFoundException("User not found with employeeId: " + employeeId));

        user.setPassword(PasswordUtil.hash(newPassword));
        user.setUpdatedBy(performedBy);
        repo.save(user);
    }

    public void changeOwnPassword(
            UUID userId,
            String oldPassword,
            String newPassword) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Old password is incorrect");
        }

        user.setPassword(PasswordUtil.hash(newPassword));
        user.setUpdatedBy(userId);
        repo.save(user);
    }

    private UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmployeeId(user.getEmployeeId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setCreatedBy(user.getCreatedBy());
        dto.setUpdatedBy(user.getUpdatedBy());

        return dto;
    }

    public UserResponseDto getById(UUID id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToDto(user);
    }

}
