package com.talkademy.phase08.controller;

import com.talkademy.phase08.consts.ExceptionMessages;
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

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;
    private final UserService userService;
    private final RoomUserService roomUserService;

    @Autowired
    public RoomController(RoomService roomService, UserService userService, RoomUserService roomUserService) {
        this.roomService = roomService;
        this.userService = userService;
        this.roomUserService = roomUserService;
    }

    @PostMapping(path = "/makeRoom/{token}")
    public void makeRoom(@PathVariable("token") UUID token, @RequestBody Room room) throws Exception {
        User user = userService.authenticate(token);
        roomService.makeRoom(user, room);
        roomUserService.addUserToRoom(new RoomUser(room.getId(), user.getId()));
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.ROOM_CREATED.getLabel());
    }

    @DeleteMapping(path = "/deleteRoom/{token}")
    public void deleteRoom(@PathVariable("token") UUID token, @RequestBody Room room) throws Exception {
        User user = userService.authenticate(token);
        if (!roomService.isCreator(room , user))
            throw new AuthenticationException(ExceptionMessages.ACTION_NOT_ALLOWED.getLabel());
        roomService.deleteRoom(room);
        room = roomService.getRoomByRoomName(room.getRoomName());
        roomUserService.removeRoomUsers(room);
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.ROOM_DELETED.getLabel());
    }

    @GetMapping(path = "/getAllRooms/{token}")
    public List<Room> getAllRooms(@PathVariable UUID token) throws Exception {
        userService.authenticate(token);
        return roomService.getAllRooms();
    }
}
