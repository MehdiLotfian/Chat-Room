package com.talkademy.phase08.service;

import com.talkademy.phase08.consts.ExceptionMessages;
import com.talkademy.phase08.dao.RoomDao;
import com.talkademy.phase08.exception.UniqueViolationException;
import com.talkademy.phase08.model.Room;
import com.talkademy.phase08.model.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomDao roomDao;

    @Autowired
    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Room makeRoom(User user, Room room) throws Exception {
        if (roomDao.findByRoomName(room.getRoomName()).isPresent())
            throw new UniqueViolationException(ExceptionMessages.UNIQUE_ROOM_NAME_VIOLATION.getLabel());
        room.setCreatorUserName(user.getUserName());
        roomDao.save(room);
        return room;
    }

    @Transactional
    public void deleteRoom(Room room) {
        roomDao.removeByRoomName(room.getRoomName());
    }

    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }

    public boolean isCreator(Room room, User user) throws Exception {
        Optional<Room> optionalRoom = roomDao.findByRoomName(room.getRoomName());
        if (optionalRoom.isEmpty())
            throw new NotFoundException(ExceptionMessages.ROOM_NOT_FOUND.getLabel());
        room = optionalRoom.get();
        return room.getCreatorUserName().equals(user.getUserName());
    }

    public Room getRoomByRoomName(String roomName) throws Exception {
        Optional<Room> optionalRoom = roomDao.findByRoomName(roomName);
        if (optionalRoom.isEmpty())
            throw new NotFoundException(ExceptionMessages.ROOM_NOT_FOUND.getLabel());
        return optionalRoom.get();
    }
}
