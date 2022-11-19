package com.shency.service;

import com.shency.dao.BaseDao;
import com.shency.dao.user.UserDao;
import com.shency.dao.user.UserDaoImpl;
import com.shency.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/19 - 16:31
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    // 登录校验
    public User login(String userCode, String password) {
        Connection conn = null;
        User user = null;

        conn = BaseDao.getConnection();
        try {
            user = userDao.getLoginUser(conn, userCode, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(conn, null, null);
        }

        return user;
    }

    @Test
    public void testLogin(){
        UserService  userService = new UserServiceImpl();
        User user = userService.login("test", "1234");
        System.out.println(user.getUserPassword());
    }


}
