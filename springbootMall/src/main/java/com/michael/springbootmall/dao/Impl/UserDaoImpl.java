package com.michael.springbootmall.dao.Impl;

import com.michael.springbootmall.dao.UserDao;
import com.michael.springbootmall.dto.UserRegisterRequest;
import com.michael.springbootmall.model.User;
import com.michael.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate NP;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "insert into user(email, password, created_date, last_modified_date) " +
                "values (:email, :password, :created_date, :last_modified_date)";

        Map<String,Object> map = new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());

        Date date = new Date();
        map.put("created_date",date);
        map.put("last_modified_date",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NP.update(sql,new MapSqlParameterSource(map),keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id,email,password,created_date,last_modified_date " +
                "from user where email = :email";

        Map<String,Object> map = new HashMap<>();
        map.put("email",email);

        List<User> userList = NP.query(sql,map,new UserRowMapper());

        if(userList.size()>0){
            return userList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id,email,password,created_date,last_modified_date " +
                "from user where user_id = :userId";

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        List<User> userList = NP.query(sql,map,new UserRowMapper());

        if(userList.size()>0){
            return userList.get(0);
        }else {
            return null;
        }

    }
}
