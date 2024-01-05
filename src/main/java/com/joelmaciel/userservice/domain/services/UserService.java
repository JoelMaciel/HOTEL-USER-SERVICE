package com.joelmaciel.userservice.domain.services;

import com.joelmaciel.userservice.api.dtos.request.UserRequestDTO;
import com.joelmaciel.userservice.api.dtos.response.UserDTO;
import com.joelmaciel.userservice.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<UserDTO> findAll(Pageable pageable, String name, String email);

    UserDTO save(UserRequestDTO userRequestDTO);

    UserDTO findById(UUID userId);

    User findByUserId(UUID userId);

}
