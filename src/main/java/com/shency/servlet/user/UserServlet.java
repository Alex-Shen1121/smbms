package com.shency.servlet.user;

import com.mysql.cj.util.StringUtils;
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
 * @ClassName UserServlet
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/26 - 20:55
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && method.equals("savepwd")) {
            this.updatePwd(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        // 从session中获取id
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        if (attribute != null && !StringUtils.isNullOrEmpty(newpassword)) {
            UserServiceImpl UserService = new UserServiceImpl();

            boolean b = UserService.updatePwd(((User) attribute).getId(), newpassword);

            if (b) {
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
                // 密码修改成功，移除当前session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute("message", "修改密码失败");
            }
        } else {
            req.setAttribute("message", "新密码有问题");
        }

        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
