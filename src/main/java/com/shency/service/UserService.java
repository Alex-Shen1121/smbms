package com.shency.service;

import com.shency.pojo.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/19 - 16:31
 */
public interface UserService {
    public User login(String userCode, String password);
}
