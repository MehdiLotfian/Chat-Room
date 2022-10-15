package com.talkademy.phase08.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String name;
    private String avatarAddress;
    @Transient
    private String status;
    @Transient
    private UUID token;

    public User(@JsonProperty("id") Long id,
                @JsonProperty("userName") String userName,
                @JsonProperty("password") String password,
                @JsonProperty("name") String name,
                @JsonProperty("avatarAddress") String avatarAddress,
                @JsonProperty("status") String status,
                @JsonProperty("token") UUID token) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.avatarAddress = avatarAddress;
        this.status = status;
        this.token = token;
    }
}
