package com.joelmaciel.userservice.api.dtos.request;

import com.joelmaciel.userservice.domain.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String description;
    private OffsetDateTime creationDate;
    private OffsetDateTime updateDate;

    public static User toEntity(UserRequestDTO userRequestDTO) {
        return User.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .description(userRequestDTO.getDescription())
                .creationDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .build();
    }
}















