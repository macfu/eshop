package com.macfu.model;

import lombok.Data;
import lombok.ToString;

/**
 * @description model实体类
 * @author: macfu
 * @create: 2020-05-02
 **/
@Data
@ToString
public class User {
    private String name;
    private Integer age;
}
