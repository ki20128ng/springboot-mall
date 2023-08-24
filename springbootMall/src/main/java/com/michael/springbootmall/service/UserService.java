package com.michael.springbootmall.service;

import com.michael.springbootmall.dto.UserLoginRequest;
import com.michael.springbootmall.dto.UserRegisterRequest;
import com.michael.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User login(UserLoginRequest userLoginRequest);
}
