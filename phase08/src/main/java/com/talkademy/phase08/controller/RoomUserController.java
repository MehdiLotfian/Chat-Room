package com.talkademy.phase08.controller;

import com.talkademy.phase08.consts.LoggerInfoMessages;
import com.talkademy.phase08.exception.ExceptionHelper;
import com.talkademy.phase08.model.Room;
import com.talkademy.phase08.model.RoomUser;
import com.talkademy.phase08.model.User;
import com.talkademy.phase08.service.RoomService;
import com.talkademy.phase08.service.RoomUserService;
import com.talkademy.phase08.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class RoomUserController {

    private static final Logger logger = LoggerFactory.getLogger(RoomUserController.class);

    private final RoomUserService roomUserService;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public RoomUserController(RoomUserService roomUserService, UserService userService, RoomService roomService) {
        this.roomUserService = roomUserService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @PostMapping(path = "/addUserToRoom/{token}")
    public void addUserToRoom(@PathVariable UUID token, @RequestBody Room room) throws Exception {
        User user = userService.authenticate(token);
        room = roomService.getRoomByRoomName(room.getRoomName());
        roomUserService.addUserToRoom(new RoomUser(room.getId(), user.getId()));
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.USER_JOINED_THE_ROOM.getLabel());
    }

    @DeleteMapping(path = "/leaveRoom/{token}")
    public void leaveRoom(@PathVariable UUID token, @RequestBody Room room) throws Exception {
        User user = userService.authenticate(token);
        room = roomService.getRoomByRoomName(room.getRoomName());
        roomUserService.leaveRoom(user, room);
        if (roomService.isCreator(room, user)) {
            roomService.deleteRoom(room);
            roomUserService.removeRoomUsers(room);
        }
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.USER_LEFT_THE_ROOM.getLabel());
    }

    @GetMapping(path = "/getRoomUsers/{token}")
    public List<String> getRoomUsers(@PathVariable UUID token, @RequestBody Room room) throws Exception {
        userService.authenticate(token);
        room = roomService.getRoomByRoomName(room.getRoomName());
        List<Long> roomUserIds = roomUserService.getRoomUserNames(room);
        return userService.getNamesByUserIds(roomUserIds);
    }

}