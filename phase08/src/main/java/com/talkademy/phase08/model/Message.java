package com.talkademy.phase08.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private LocalDateTime localDateTime;
    private String roomName;
    private String senderUserName;

    public Message(@JsonProperty("id") Long id,
                   @JsonProperty("content") String content,
                   @JsonProperty("localDateTime") LocalDateTime localDateTime,
                   @JsonProperty("roomName") String roomName,
                   @JsonProperty("senderUserName") String senderUserName) {
        this.id = id;
        this.content = content;
        this.localDateTime = localDateTime;
        this.roomName = roomName;
        this.senderUserName = senderUserName;
    }

}
