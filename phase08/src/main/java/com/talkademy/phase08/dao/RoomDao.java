package com.talkademy.phase08.dao;

import com.talkademy.phase08.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomDao extends CrudRepository<Room, Long> {

    Optional<Room> findByRoomName(String roomName);

    void removeByRoomName(String roomName);

    List<Room> findAll();
}
