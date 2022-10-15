package com.talkademy.phase08.controller;

import com.talkademy.phase08.consts.LoggerInfoMessages;
import com.talkademy.phase08.exception.ExceptionHelper;
import com.talkademy.phase08.model.User;
import com.talkademy.phase08.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/signup")
    public void signup(@RequestBody User user) throws Exception {
        userService.signup(user);
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.USER_SIGNED_UP.getLabel());
    }

    @GetMapping(path = "/login")
    public UUID login(@RequestBody User user) throws Exception {
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.USER_LOGGED_IN.getLabel());
        return userService.login(user);
    }

    @GetMapping(path = "/getUserProfile/{token}")
    public User getUserProfile(@PathVariable("token") UUID token, @RequestBody User user) throws Exception {
        userService.authenticate(token);
        return userService.getUserProfile(user);
    }

    @PutMapping(path = "/changeStatus/{token}")
    public void changeStatus(@PathVariable UUID token, @RequestBody User updatedUser) throws Exception {
        User user = userService.authenticate(token);
        userService.changeStatus(user, updatedUser);
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.USER_STATUS_UPDATED.getLabel());
    }

    @DeleteMapping(path = "/logout/{token}")
    public void logout(@PathVariable UUID token) throws Exception {
        userService.authenticate(token);
        userService.logout(token);
        logger.info(LocalDateTime.now() + ": " + LoggerInfoMessages.USER_LOGGED_OUT.getLabel());
    }
}
