package com.joelmaciel.userservice.domain.services.impl;

import com.joelmaciel.userservice.api.dtos.request.UserRequestDTO;
import com.joelmaciel.userservice.api.dtos.response.UserDTO;
import com.joelmaciel.userservice.domain.entities.Hotel;
import com.joelmaciel.userservice.domain.entities.Qualification;
import com.joelmaciel.userservice.domain.entities.User;
import com.joelmaciel.userservice.domain.exceptions.UserNotFoundException;
import com.joelmaciel.userservice.domain.repositories.UserRepository;
import com.joelmaciel.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String URL_QUALIFICATION = "http://QUALIFICATION-SERVICE:8083/api/qualifications/users/";
    public static final String URL_HOTEL_SERVICE = "http://HOTEL-SERVICE:8082/api/hotels/";
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

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

        try {
            Qualification[] qualificationsUsers = restTemplate.getForObject(
                    URL_QUALIFICATION + userId, Qualification[].class);
            log.info("Qualifications: {}", Arrays.toString(qualificationsUsers));

            if (qualificationsUsers != null && qualificationsUsers.length > 0) {
                List<Qualification> qualificationsHotels = Arrays.stream(qualificationsUsers)
                        .map(this::populateQualificationWithHotel)
                        .collect(Collectors.toList());

                user.setQualifications(qualificationsHotels);
            }

        } catch (RestClientException ex) {
            log.error("Error while fetching qualifications for userId: {}", userId, ex);
        }

        return UserDTO.toDTO(user);
    }

    @Override
    public User findByUserId(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Qualification populateQualificationWithHotel(Qualification qualification) {
        try {
            ResponseEntity<Hotel> responseHotel = restTemplate.getForEntity(
                    URL_HOTEL_SERVICE + qualification.getHotelId(), Hotel.class);
            Hotel hotel = responseHotel.getBody();

            log.info("Response with code state: {}", responseHotel.getStatusCode());

            qualification.setHotel(hotel);

        } catch (RestClientException ex) {
            log.error("Error while fetching hotel details for qualification: {}", qualification, ex);
        }
        return qualification;
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
