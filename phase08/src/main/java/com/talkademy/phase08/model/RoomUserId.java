package com.talkademy.phase08.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoomUserId implements Serializable {

    private Long roomId;
    private Long userId;

}
