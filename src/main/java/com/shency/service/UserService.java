package com.shency.service;

import com.shency.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/19 - 16:31
 */
public interface UserService {
    public User login(String userCode, String password);

    // 修改当前用户密码
    public boolean updatePwd(int id, String password);

    public int getUserCount(String username,  int userrole);

    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

}
