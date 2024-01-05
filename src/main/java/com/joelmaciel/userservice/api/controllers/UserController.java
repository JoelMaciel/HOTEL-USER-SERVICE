package com.joelmaciel.userservice.api.controllers;

import com.joelmaciel.userservice.api.dtos.request.UserRequestDTO;
import com.joelmaciel.userservice.api.dtos.response.UserDTO;
import com.joelmaciel.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserDTO> getAll(
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        return userService.findAll(pageable, name, email);
    }

    @GetMapping("/{userId}")
    public UserDTO getOne(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody @Valid UserRequestDTO userRequest) {
        return userService.save(userRequest);
    }
}
