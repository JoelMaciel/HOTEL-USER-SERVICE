package com.joelmaciel.userservice.domain.repositories;

import com.joelmaciel.userservice.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByNameContaining(Pageable pageable, String name);

    Page<User> findByEmailContaining(Pageable pageable, String email);
}
