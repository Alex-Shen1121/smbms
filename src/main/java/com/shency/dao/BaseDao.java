package com.shency.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @ClassName BaseDao
 * @Description 操作数据库的公共类
 * @Author alex_shen
 * @Date 2022/11/15 - 21:34
 */
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    // 静态代码块，类加载的时候就初始化了
    static {
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();

        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    /**
     * 获取数据库的链接
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * 编写查询公共类
     */
    public static ResultSet execute(Connection conn, PreparedStatement ps, ResultSet rs, String sql, Object[] params) throws SQLException {
        ps = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        rs = ps.executeQuery();
        return rs;
    }

    /**
     * 编写增删改公共方法
     */
    public static int execute(Connection conn, PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        int updateRow = preparedStatement.executeUpdate();
        return updateRow;
    }

    // 释放资源
    public static boolean closeResource(Connection conn, PreparedStatement ps, ResultSet rs) {
        boolean flag = true;
        if (rs != null) {
            try {
                rs.close();
                // GC回收
                rs = null;
            } catch (SQLException e) {
                flag = false;
                e.printStackTrace();
            }

        }
        if (conn != null) {
            try {
                conn.close();
                // GC回收
                conn = null;
            } catch (SQLException e) {
                flag = false;
                throw new RuntimeException(e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
                // GC回收
                ps = null;
            } catch (SQLException e) {
                flag = false;
                throw new RuntimeException(e);
            }
        }
        return flag;
    }

}
