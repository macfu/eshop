package com.macfu.controller;

import com.macfu.model.User;
import com.macfu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description userController
 * @author: macfu
 * @create: 2020-05-03
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/getUserInfo")
    public User getUserInfo() {
        User user = userService.findUserInfo();
        return user;
    }

    @RequestMapping("/getCacheUserInfo")
    public User getCacheUserInfo() {
        User user = userService.getCacheUserInfo();
        return user;
    }
}
