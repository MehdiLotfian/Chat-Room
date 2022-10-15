package com.talkademy.phase08.service;

import com.talkademy.phase08.consts.ExceptionMessages;
import com.talkademy.phase08.dao.UserDao;
import com.talkademy.phase08.model.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserDao userDao;
    private final Jedis jedis;

    @Autowired
    public UserService(UserDao userDao, Jedis jedis) {
        this.userDao = userDao;
        this.jedis = jedis;
    }

    public User authenticate(UUID token) throws Exception {
        if (!jedis.exists(token.toString()))
            throw new Exception("not logged in");
        String userName = jedis.get(token.toString());
        Optional<User> user = userDao.findByUserName(userName);
        jedis.setex(token.toString(), 600, userName);
        jedis.setex("token:" + userName, 600, token.toString());
        if (user.isPresent())
            return user.get();
        throw new NotFoundException(ExceptionMessages.USER_NOT_FOUND.getLabel());
    }

    public User signup(User user) throws Exception {
        if(userDao.findByUserName(user.getUserName()).isPresent())
            throw new Exception(ExceptionMessages.UNIQUE_USER_NAME_VIOLATION.getLabel());
        userDao.save(user);
        jedis.set(user.getUserName(), user.getStatus());
        return user;
    }

    public UUID login(User user) throws Exception {
        if (userDao.findByUserNameAndPassword(user.getUserName(), user.getPassword()).isEmpty())
            throw new NotFoundException(ExceptionMessages.USER_NOT_FOUND.getLabel());
        if (jedis.exists("token:" + user.getUserName()))
            return UUID.fromString(jedis.get("token:" + user.getUserName()));
        user.setToken(UUID.randomUUID());
        jedis.setex(user.getToken().toString(), 600, user.getUserName());
        jedis.setex("token:" + user.getUserName(), 600, user.getToken().toString());
        return user.getToken();
    }

    public User changeStatus(User user, User updatedStatusUser) {
        user.setStatus(updatedStatusUser.getStatus());
        jedis.set(user.getUserName(), user.getStatus());
        return user;
    }

    public User getUserProfile(User user) throws Exception {
        Optional<User> fullUser = userDao.findByUserName(user.getUserName());
        if (fullUser.isEmpty())
            throw new NotFoundException(ExceptionMessages.USER_NOT_FOUND.getLabel());
        user.setName(fullUser.get().getName());
        user.setStatus(jedis.get(user.getUserName()));
        user.setAvatarAddress(fullUser.get().getAvatarAddress());
        return user;
    }

    public List<String> getNamesByUserIds(List<Long> userIds) {
        List<String> names = new ArrayList<>();
        for (Long userId : userIds)
            names.add(userDao.findById(userId).get().getName());
        return names;
    }

    public void logout(UUID token) {
        jedis.del(token.toString());
    }
}
