package com.joelmaciel.userservice.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    private UUID hotelId;
    private String name;
    private String location;
    private String information;
}
