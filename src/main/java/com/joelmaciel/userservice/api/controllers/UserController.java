package com.joelmaciel.userservice.api.controllers;

import com.joelmaciel.userservice.api.dtos.request.UserRequestDTO;
import com.joelmaciel.userservice.api.dtos.response.UserDTO;
import com.joelmaciel.userservice.domain.entities.User;
import com.joelmaciel.userservice.domain.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    public static final String MSG_LOG_INFO = "Service unavailable, please wait a few minutes";
    private final UserService userService;

    @GetMapping
    public Page<UserDTO> getAll(
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        return userService.findAll(pageable, name, email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody @Valid UserRequestDTO userRequest) {
        return userService.save(userRequest);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public UserDTO getOne(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    public UserDTO ratingHotelFallback(UUID userId, Throwable exception) {
        log.info(MSG_LOG_INFO, exception.getMessage());
        return UserDTO.builder()
                .userId(UUID.randomUUID())
                .name("root")
                .email("root@email.com")
                .description("Service Unavailable, please wait a few minutes")
                .creationDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }

}
