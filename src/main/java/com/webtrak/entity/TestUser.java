package com.webtrak.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "test_users")
public class TestUser {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    // getters & setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
