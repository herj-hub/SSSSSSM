package com.smbms.servlet;

import com.smbms.entity.User;
import com.smbms.util.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AbstractServlet")
public class AbstractServlet extends HttpServlet {
    public User getUser(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute(Constants.USER_SESSION);
        return user;
    }
}
