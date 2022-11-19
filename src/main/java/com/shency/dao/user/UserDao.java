package com.shency.dao.user;

import com.shency.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/19 - 16:12
 */
public interface UserDao {
    User getLoginUser(Connection conn, String userCode, String userPassword) throws SQLException;
}
