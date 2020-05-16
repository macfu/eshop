package com.macfu.service;

import com.macfu.model.User;

/**
 * @description
 * @author: macfu
 * @create: 2020-05-02
 **/
public interface UserService {

    /**
     * 查询用户信息
     *
     * @return
     */
    User findUserInfo();

    /**
     * 查询redis中缓存中的用户信息
     *
     * @return
     */
    User getCacheUserInfo();
}
