package com.joelmaciel.userservice.domain.services.impl;

import com.joelmaciel.userservice.api.dtos.request.UserRequestDTO;
import com.joelmaciel.userservice.api.dtos.response.UserDTO;
import com.joelmaciel.userservice.domain.entities.User;
import com.joelmaciel.userservice.domain.exceptions.UserNotFoundException;
import com.joelmaciel.userservice.domain.repositories.UserRepository;
import com.joelmaciel.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDTO> findAll(Pageable pageable, String name, String email) {
        Page<User> users = getPageUsers(pageable, name, email);
        return users.map(UserDTO::toDTO);
    }

    @Override
    public UserDTO save(UserRequestDTO userRequestDTO) {
        User user = UserRequestDTO.toEntity(userRequestDTO);
        return UserDTO.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO findById(UUID userId) {
        User user = findByUserId(userId);
        return UserDTO.toDTO(user);
    }

    @Override
    public User findByUserId(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Page<User> getPageUsers(Pageable pageable, String name, String email) {
        Page<User> users;
        if (name != null) {
            users = userRepository.findByNameContaining(pageable, name);
        } else if (email != null) {
            users = userRepository.findByEmailContaining(pageable, email);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users;
    }
}
