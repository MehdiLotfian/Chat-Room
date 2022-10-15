package com.talkademy.phase08.service;

import com.talkademy.phase08.consts.ExceptionMessages;
import com.talkademy.phase08.dao.RoomUserDao;
import com.talkademy.phase08.model.Room;
import com.talkademy.phase08.model.RoomUser;
import com.talkademy.phase08.model.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomUserService {

    private final RoomUserDao roomUserDao;

    @Autowired
    public RoomUserService(RoomUserDao roomUserDao) {
        this.roomUserDao = roomUserDao;
    }

    public void addUserToRoom(RoomUser roomUser) {
        roomUserDao.save(roomUser);
    }

    @Transactional
    public void removeRoomUsers(Room room) {
        roomUserDao.deleteByRoomId(room.getId());
    }

    @Transactional
    public void leaveRoom(User user, Room room) {
        roomUserDao.deleteByRoomIdAndUserId(room.getId(), user.getId());
    }

    public List<Long> getRoomUserNames(Room room) {
        List<RoomUser> roomUsers = roomUserDao.findByRoomId(room.getId());
        List<Long> userIds = new ArrayList<>();
        for (RoomUser roomUser : roomUsers)
            userIds.add(roomUser.getUserId());
        return userIds;
    }

    public RoomUser getRoomUser(Room room, User user) throws Exception {
        Optional<RoomUser> optionalRoomUser = roomUserDao.findByRoomIdAndUserId(room.getId(), user.getId());
        if (optionalRoomUser.isEmpty())
            throw new NotFoundException(ExceptionMessages.USER_NOT_FOUND.getLabel());
        return optionalRoomUser.get();
    }
}
