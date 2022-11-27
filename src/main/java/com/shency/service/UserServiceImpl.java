package com.shency.service;

import com.shency.dao.BaseDao;
import com.shency.dao.user.UserDao;
import com.shency.dao.user.UserDaoImpl;
import com.shency.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
    public void testLogin() {
        UserService userService = new UserServiceImpl();
        User user = userService.login("test", "1234");
        System.out.println(user.getUserPassword());
    }

    public boolean updatePwd(int id, String password) {
        Connection conn = null;
        boolean flag = false;
        try {
            conn = BaseDao.getConnection();
            if (userDao.updatePwd(conn, id, password) > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(conn, null, null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String username, int userrole) {
        Connection conn = null;
        int userCount = 0;
        try {
            conn = BaseDao.getConnection();
            userCount = userDao.getUserCount(conn, username, userrole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(conn, null, null);
        }
        return userCount;

    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        // TODO Auto-generated method stub
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }
}
