package com.talkademy.phase08.dao;

import com.talkademy.phase08.model.Message;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MessageDao extends CrudRepository<Message, Long> {

    List<Message> findByRoomName(String roomName);
}
