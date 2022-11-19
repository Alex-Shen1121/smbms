package com.shency.servlet.user;

import com.shency.pojo.User;
import com.shency.service.UserService;
import com.shency.service.UserServiceImpl;
import com.shency.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName LoginServlet
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/19 - 16:53
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet -- start...");

        // 从前端获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        // 和数据库中的密码进行对比， 调用业务层
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        if (user != null) { // 查有此人
            // 将用户的信息放到Session中;
            req.getSession().setAttribute(Constants.USER_SESSION, user);

            // 跳转到frame主页
            resp.sendRedirect("jsp/frame.jsp");

        } else {
            // 转发会登录页面，顺带提示用户名、密码错误
            req.setAttribute("error", "用户名或者密码错误");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
