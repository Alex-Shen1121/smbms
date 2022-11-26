package com.shency.dao.user;

import com.shency.pojo.User;
import com.shency.util.Constants;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/19 - 16:12
 */
public interface UserDao {
    // 得到要登录的用户
    public User getLoginUser(Connection conn, String userCode, String userPassword) throws SQLException;

    // 修改当前用户密码
    public int updatePwd(Connection conn, int id, String password) throws SQLException;
}
