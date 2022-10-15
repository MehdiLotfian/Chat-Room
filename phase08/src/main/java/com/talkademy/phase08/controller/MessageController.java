package com.talkademy.phase08.controller;

import com.talkademy.phase08.consts.ExceptionMessages;
import com.talkademy.phase08.consts.LoggerInfoMessages;
import com.talkademy.phase08.exception.ExceptionHelper;
import com.talkademy.phase08.exception.ExpiredException;
import com.talkademy.phase08.model.Message;
import com.talkademy.phase08.model.Room;
import com.talkademy.phase08.model.User;
import com.talkademy.phase08.service.MessageService;
import com.talkademy.phase08.service.RoomService;
import com.talkademy.phase08.service.RoomUserService;
import com.talkademy.phase08.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;
    private final UserService userService;
    private final RoomUserService roomUserService;
    private final RoomService roomService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, RoomUserService roomUserService,
                             RoomService roomService) {
        this.messageService = messageService;
        this.userService = userService;
        this.roomUserService = roomUserService;
        this.roomService = roomService;
    }

    @PostMapping(path = "/sendMessage/{token}")
    public void sendMessage(@PathVariable("token") UUID token, @RequestBody Message message) throws Exception {
        User user = userService.authenticate(token);
        Room room = roomService.getRoomByRoomName(message.getRoomName());
        roomUserService.getRoomUser(room, user);
        messageService.sendMessage(user, message);
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.MESSAGE_SENT.getLabel());
    }

    @PutMapping(path = "/editMessage/{token}")
    public void editMessage(@PathVariable("token") UUID token, @RequestBody Message updatedMessage) throws Exception {
        User user = userService.authenticate(token);
        Message message = messageService.getMessageById(updatedMessage.getId());
        if (messageService.isExpired(message))
            throw new ExpiredException(ExceptionMessages.ACTION_EXPIRED.getLabel());
        if (!messageService.isSender(user, message))
            throw new AuthenticationException(ExceptionMessages.ACTION_NOT_ALLOWED.getLabel());
        messageService.editMessage(message, updatedMessage);
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.MESSAGE_EDITED.getLabel());
    }

    @GetMapping(path = "/getRoomMessages/{token}")
    public List<Message> getRoomMessages(@PathVariable("token") UUID token, @RequestBody Room room) throws Exception {
        User user = userService.authenticate(token);
        room = roomService.getRoomByRoomName(room.getRoomName());
        roomUserService.getRoomUser(room, user);
        return messageService.getRoomMessages(room);
    }
}
