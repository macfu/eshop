package com.macfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.macfu.dao.RedisDAO;
import com.macfu.mapper.UserMapper;
import com.macfu.model.User;
import com.macfu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author: macfu
 * @create: 2020-05-02
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisDAO redisDAO;

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCacheUserInfo() {
        redisDAO.set("cache_user_list", "{\"name\": \"lisi\", \"age\":28}");
        String userJson = redisDAO.get("cache_user_list");
        JSONObject userJSONobject = JSONObject.parseObject(userJson);
        User user = new User();
        user.setName(userJSONobject.getString("name"));
        user.setAge(userJSONobject.getInteger("age"));
        return user;
    }
}
