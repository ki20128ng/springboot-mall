package com.michael.springbootmall.dao;

import com.michael.springbootmall.dto.UserRegisterRequest;
import com.michael.springbootmall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
