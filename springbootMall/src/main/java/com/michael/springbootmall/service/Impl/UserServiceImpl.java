package com.michael.springbootmall.service.Impl;

import com.michael.springbootmall.dao.UserDao;
import com.michael.springbootmall.dto.UserRegisterRequest;
import com.michael.springbootmall.model.User;
import com.michael.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
