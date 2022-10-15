package com.talkademy.phase08.service;

import com.talkademy.phase08.consts.ExceptionMessages;
import com.talkademy.phase08.dao.MessageDao;
import com.talkademy.phase08.model.Message;
import com.talkademy.phase08.model.Room;
import com.talkademy.phase08.model.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void sendMessage(User user, Message message) {
        message.setSenderUserName(user.getUserName());
        message.setLocalDateTime(LocalDateTime.now());
        messageDao.save(message);
    }

    public void editMessage(Message message, Message updatedMessage) {
        message.setContent(updatedMessage.getContent());
        messageDao.save(message);
    }

    public boolean isSender(User user, Message message) {
        return message.getSenderUserName().equals(user.getUserName());
    }

    public boolean isExpired(Message message)  {
        return message.getLocalDateTime().isBefore(LocalDateTime.now().minusMinutes(15));
    }

    public Message getMessageById(Long id) throws Exception {
        Optional<Message> optionalRoom = messageDao.findById(id);
        if (optionalRoom.isEmpty())
            throw new NotFoundException(ExceptionMessages.MESSAGE_NOT_FOUND.getLabel());
       return optionalRoom.get();
    }

    public List<Message> getRoomMessages(Room room) {
        return messageDao.findByRoomName(room.getRoomName());
    }
}
