package com.talkademy.phase08.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@NoArgsConstructor
@Entity
@IdClass(RoomUserId.class)
public class RoomUser {

    @Id
    private Long roomId;
    @Id
    private Long userId;

    public RoomUser(@JsonProperty("roomId") Long roomId,
                    @JsonProperty("userId") Long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }
}
