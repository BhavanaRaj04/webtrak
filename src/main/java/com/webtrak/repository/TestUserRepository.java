package com.webtrak.repository;

import com.webtrak.entity.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestUserRepository extends JpaRepository<TestUser, UUID> {
}
