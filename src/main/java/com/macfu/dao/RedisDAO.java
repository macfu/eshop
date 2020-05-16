package com.macfu.dao;

/**
 * @description
 * @author: macfu
 * @create: 2020-05-03
 **/
public interface RedisDAO {

    void set(String key, String value);

    String get(String key);
}
