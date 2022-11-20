package com.shency.filter;

import com.shency.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SysFilter
 * @Description TODO
 * @Author alex_shen
 * @Date 2022/11/20 - 21:44
 */
public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if(request.getSession().getAttribute(Constants.USER_SESSION) == null){
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }

        chain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
