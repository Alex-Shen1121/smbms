package com.shency.dao.user;

import com.shency.dao.BaseDao;
import com.shency.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName UserDaoImpl
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/19 - 16:14
 */
public class UserDaoImpl implements UserDao {

    @Override
    public User getLoginUser(Connection conn, String userCode, String userPassword) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        if (conn != null) {
            String sql = "select * from smbms_user where userCode=? and userPassword=?";
            Object[] para = {userCode, userPassword};

            rs = BaseDao.execute(conn, ps, rs, sql, para);

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }

            BaseDao.closeResource(conn, ps, rs);
            return user;
        }

        return null;

    }

    @Override
    public int updatePwd(Connection conn, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int execute = 0;
        if (conn != null) {
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object params[] = {password, id};

            execute = BaseDao.execute(conn, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }

        return execute;
    }
}
