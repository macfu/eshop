package com.macfu.dao.impl;

import com.macfu.dao.RedisDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

/**
 * @description
 * @author: macfu
 * @create: 2020-05-03
 **/
@Repository("redisDAO")
public class RedisDAOImpl implements RedisDAO {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        this.jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return this.jedisCluster.get(key);
    }

    @Override
    public void delete(String key) {
        this.jedisCluster.del(key);
    }
}
