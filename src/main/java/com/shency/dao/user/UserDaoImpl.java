package com.shency.dao.user;

import com.mysql.cj.util.StringUtils;
import com.shency.dao.BaseDao;
import com.shency.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int getUserCount(Connection conn, String userName, int userRole) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        int count = 0;

        if (conn != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
//            存放sql参数
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");  //index：0
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);            //index：1
            }
            //List转换为数组
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(conn, ps, rs, sql.toString(), params);
            if (rs.next()) {
                //从结果集中获得的最终的数量
                count = rs.getInt("count");
            }
            BaseDao.closeResource(conn, ps, rs);
        }

        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        // TODO Auto-generated method stub
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");
            }
            if(userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }

            //在数据库中，分页使用 limit startIndex pageSize
            //当前页  （当前页-1）*页面大小
            //0,5    1   0    012345
            //6,5    2   5    26789
            //11,5   3   10
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while(rs.next()){
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return userList;
    }
}
