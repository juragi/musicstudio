package com.ilhak.musicstudio.repository;

import com.ilhak.musicstudio.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
