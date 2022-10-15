package com.talkademy.phase08.dao;

import com.talkademy.phase08.model.RoomUser;
import com.talkademy.phase08.model.RoomUserId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserDao extends CrudRepository<RoomUser, RoomUserId> {

    void deleteByRoomId(Long roomId);

    void deleteByRoomIdAndUserId(Long roomId, Long userId);

    List<RoomUser> findByRoomId(Long roomId);

    Optional<RoomUser> findByRoomIdAndUserId(Long roomId, Long userId);
}
