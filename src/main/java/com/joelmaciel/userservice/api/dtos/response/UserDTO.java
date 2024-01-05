package com.joelmaciel.userservice.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joelmaciel.userservice.domain.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
@Setter
public class UserDTO {

    private UUID userId;
    private String name;
    private String email;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private OffsetDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private OffsetDateTime updateDate;

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .description(user.getDescription())
                .creationDate(user.getCreationDate())
                .updateDate(user.getUpdateDate())
                .build();
    }


}
