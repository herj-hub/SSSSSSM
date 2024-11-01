package com.smbms.servlet;

import com.smbms.entity.User;
import com.smbms.service.user.UserService;
import com.smbms.service.user.UserServiceImpl;
import com.smbms.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet",urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取参数
        String userCode=request.getParameter("userCode");
        String userPassword=request.getParameter("userPassword");


        //调用Service，执行查询
        UserService userService=new UserServiceImpl();
        User user=userService.login(userCode,userPassword);

        if(user==null){
            request.setAttribute("error","账号或登录密码错误");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }else {
            //向session存入当前登录用户
            HttpSession session=request.getSession();
            //存入到常量工具类中定义的变量名上
            session.setAttribute(Constants.USER_SESSION,user);
            //跳转到主页面
            response.sendRedirect("jsp/frame.jsp");
        }
    }
}
