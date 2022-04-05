package com.springboot.blog.repository;

import com.springboot.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findByEmailOrName(String email, String name);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);
}
