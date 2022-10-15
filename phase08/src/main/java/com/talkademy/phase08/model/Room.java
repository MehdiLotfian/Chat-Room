package com.talkademy.phase08.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roomName;
    private String name;
    private String creatorUserName;

    public Room(@JsonProperty("id") Long id,
                @JsonProperty("roomName") String roomName,
                @JsonProperty("name") String name,
                @JsonProperty("creatorUserName") String creatorUserName) {
        this.id = id;
        this.roomName = roomName;
        this.name = name;
        this.creatorUserName = creatorUserName;
    }
}
