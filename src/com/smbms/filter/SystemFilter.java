package com.smbms.filter;

import com.smbms.entity.User;
import com.smbms.util.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SystemFilter",urlPatterns = "/jsp/*")
public class SystemFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //从session取出用户
        //有用户-->继续后续操作
        //无用户-->返回error.jsp

        HttpServletRequest request=(HttpServletRequest) req;
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute(Constants.USER_SESSION);
        if(user==null){
            HttpServletResponse response=(HttpServletResponse) resp;
            response.sendRedirect("../error.jsp");
            return;
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
