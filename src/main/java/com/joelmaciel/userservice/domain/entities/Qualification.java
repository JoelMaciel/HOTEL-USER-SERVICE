package com.joelmaciel.userservice.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Qualification {

    private String qualificationId;
    private String userId;
    private String hotelId;
    private Integer qualification;
    private String comments;
    private Hotel hotel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Qualification)) return false;
        Qualification that = (Qualification) o;
        return Objects.equals(qualificationId, that.qualificationId)
                && Objects.equals(userId, that.userId)
                && Objects.equals(hotelId, that.hotelId)
                && Objects.equals(qualification, that.qualification)
                && Objects.equals(comments, that.comments) && Objects.equals(hotel, that.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualificationId, userId, hotelId, qualification, comments, hotel);
    }
}
