package com.smbms.servlet;

import com.alibaba.fastjson.JSON;
import com.smbms.entity.Role;
import com.smbms.entity.User;
import com.smbms.service.role.RoleService;
import com.smbms.service.role.RoleServiceImpl;
import com.smbms.service.user.UserService;
import com.smbms.service.user.UserServiceImpl;
import com.smbms.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserServlet", urlPatterns = "/jsp/user.do")
public class UserServlet extends AbstractServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取提交参数
        String method = request.getParameter("method");
        if (method == null || method.equals("")) {
            //跳转到错误提示页面
            response.sendRedirect("../error.jsp");
        } else {
            if (method.equals("pwdmodify")) {//-->进入  老密码校验模块
                this.doPwdModify(request, response);
            } else if (method.equals("savepwd")) {//-->进入 更新密码模块
                this.doUpdatePwd(request, response);
            } else if (method.equals("query")) {//-->进入 用户列表模块
                this.doQueryUser(request, response);
            } else if (method.equals("getrolelist")) {//-->进入 ajax查询用户列表
                this.doAjaxQueryRole(request, response);
            } else if (method.equals("ucexist")) {//-->进入 ajax查询用户编码是否存在
                this.doAjaxQueryUserCode(request, response);
            } else if (method.equals("add")) {//-->进入 执行新增用户
                this.dpAddUser(request, response);
            } else if(method.equals("deluser")){//-->进入ajax删除用户
                this.doDeleteUser(request,response);
            } else if(method.equals("view")){//-->进入 查看用户
                this.doViewUser(request,response);
            } else if(method.equals("modify")){//-->进入 更新用户
                this.doModifyUser(request,response);
            } else if(method.equals("modifyexe")){//-->进入 执行更新用户
                this.doExeModifyUser(request,response);
            }
        }
    }

    /**
     * 执行更新用户
     *
     * @param request
     * @param response
     */
    private void doExeModifyUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取数据
        String uid=request.getParameter("uid");

        //userName gender birthday phone address userRole
        String userName=request.getParameter("userName");
        String gender=request.getParameter("gender");
        String birthday=request.getParameter("birthday");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");
        String userRole=request.getParameter("userRole");

        Long id = Long.parseLong(uid);
        int genderId = Integer.parseInt(gender);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = null;
        try {
            birth = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int userRoleId = Integer.parseInt(userRole);

        //当前用户id、当前系统时间-->更新人id、更新时间
        User sessionUser = getUser(request);
        Long modifyBy = sessionUser.getId();
        Date modifyDate = new Date();

        //封装对象
        User user=new User(id,null,userName,null,genderId,birth,phone,address,userRoleId,null,null,modifyBy,modifyDate);

        UserService userService=new UserServiceImpl();
        int num=userService.updateUser(user);

        if(num>0){
            //跳转到列表页面
            this.doQueryUser(request,response);
        }else {
            request.setAttribute("message","更新错误");
            request.getRequestDispatcher("usermodify.jsp").forward(request,response);
        }
        //TODO 更新完之后再点进去查看或者更新功能的返回，会出现session失效问题
        //TODO 去看代码里面user类里 为什么要写userRoleName，忘了为什么了
    }

    /**
     * 更新用户
     *
     * @param request
     * @param response
     */
    private void doModifyUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid=request.getParameter("uid");
        Long id=Long.parseLong(uid);

        UserService userService=new UserServiceImpl();
        User user=userService.queryById(id);

        request.setAttribute("user",user);
        request.getRequestDispatcher("usermodify.jsp").forward(request,response);
    }

    /**
     * 查看用户
     *
     * @param request
     * @param response
     */
    private void doViewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid=request.getParameter("uid");
        Long id=Long.parseLong(uid);

        UserService userService=new UserServiceImpl();
        User user=userService.queryById(id);

        request.setAttribute("user",user);
        request.getRequestDispatcher("userview.jsp").forward(request,response);

    }

    /**
     * ajax删除用户
     *
     * @param request
     * @param response
     */
    private void doDeleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid=request.getParameter("uid");
        Long id=Long.parseLong(uid);

        Map<String,String> map=new HashMap<>();

        try {
            UserService userService=new UserServiceImpl();
            int num=userService.deleteUserById(id);

            if(num>0){
                map.put("delResult","true");
            }else if(num==0){
                map.put("delResult","notexist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("delResult","false");
        } finally {
            String json = JSON.toJSONString(map);

            //返回数据，创建响应体
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        }
    }

    /**
     * 执行新增用户
     *
     * @param request
     * @param response
     */
    private void dpAddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        //userCode userName userPassword gender birthday phone address userRole

        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");

        int genderId = Integer.parseInt(gender);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = null;
        try {
            birth = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int userRoleId = Integer.parseInt(userRole);

        //当前用户id，当前系统时间
        User sessionUser = getUser(request);
        Long createBy = sessionUser.getId();
        Date cateteDate = new Date();

        //封装对象
        User user = new User(null, userCode, userName, userPassword, genderId, birth, phone, address, userRoleId, createBy, cateteDate, null, null);

        UserService userService = new UserServiceImpl();
        int num = userService.addUser(user);

        if (num > 0) {
            //跳转到列表页面
            this.doQueryUser(request, response);
        } else {
            request.setAttribute("message", "新增错误");
            request.getRequestDispatcher("useradd.jsp").forward(request, response);
        }
        //TODO bug 新增之后点刷新，会再执行一次新增，并且有时候显示的用户列表并不是十条
        //TODO bug 新增之后点刷新，会再执行一次新增，并且有时候显示的用户列表并不是十条
    }

    /**
     * ajax查询用户编码是否存在
     *
     * @param request
     * @param response
     */
    private void doAjaxQueryUserCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String userCode = request.getParameter("userCode");
        //调用service
        UserService userService = new UserServiceImpl();
        int num = userService.countByUserCode(userCode);

        Map<String, String> map = new HashMap<>();

        if (num > 0) {
            map.put("userCode", "exist");
        }
        //TODO bug 用户编码输入为空时，也可以通过
        //TODO bug 用户编码输入为空时，也可以通过

        String json = JSON.toJSONString(map);

        //返回数据，创建响应体
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * ajax查询用户角色列表
     *
     * @param request
     * @param response
     */
    private void doAjaxQueryRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.queryRole();

        String json = JSON.toJSONString(roleList);

        //返回数据，创建响应体
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 查询用户列表
     *
     * @param request
     * @param response
     */
    private void doQueryUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数--用户名
        String queryname = request.getParameter("queryname");

        //获取参数--用户角色id
        String queryUserRole = request.getParameter("queryUserRole");
        //获取当前页码
        String pageIndex = request.getParameter("pageIndex");

        //设置后台查询条件
        String username = null;//用户名
        Integer userRole = null;//用户角色
        int currentPage = 1;//当前页码

        if (queryname == null || queryname.equals("")) {
            username = null;
        } else {
            username = queryname;
        }

        if (queryUserRole == null || queryUserRole.equals("0")) {
            userRole = null;
        } else {
            userRole = Integer.parseInt(queryUserRole);
        }

        if (pageIndex == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(pageIndex);
        }

        //业务层对象
        UserService userService = new UserServiceImpl();
        //List<User> userList=userService.queryUser();
//        List<User> userList=userService.queryUser(username,userRole);

        //查询符合要求，一共的总数据量
        int total = userService.countUser(username, userRole);
        //创建分页对象
        PageUtil pageUtil = new PageUtil(currentPage, total, 10);


        List<User> userList = userService.queryUser(username, userRole, pageUtil.getStartNo(), pageUtil.getPageSize());

        //查询用户角色列表
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.queryRole();

        //封装数据
        request.setAttribute("userList", userList);
        request.setAttribute("roleList", roleList);

        //封装数据-数据回显
        request.setAttribute("queryUserName", queryname);
        request.setAttribute("queryUserRole", queryUserRole);
        //封装数据-分页数据totalCount currentPageNo totalPageCount
        request.setAttribute("totalPageCount", pageUtil.getTotalPage());
        request.setAttribute("totalCount", pageUtil.getTotal());
        request.setAttribute("currentPageNo", pageUtil.getCurrentPage());

        request.getRequestDispatcher("userlist.jsp").forward(request, response);

    }

    /**
     * 更新密码
     *
     * @param request
     * @param response
     */
    private void doUpdatePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String newPassword = request.getParameter("newpassword");
        //获取当前登录人id
        User user = getUser(request);//调用父类的方法，获取session对象
        long id = user.getId();

        Date updateDate = new Date();//更新时间
        //调用service-->dao-->mysql
        UserService userService = new UserServiceImpl();
        int result = userService.updatePwd(id, newPassword, id, updateDate);//谁id，新密码newPassword， 谁更新的id，更新时间

        if (result > 0) {
            response.sendRedirect("frame.jsp");
        } else {
            request.setAttribute("message", "后台运行有误，请联系管理员");
            request.getRequestDispatcher("pwdmodify.jsp");
        }
    }

    /**
     * 老表单校验
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void doPwdModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String oldpassword = request.getParameter("oldpassword");
        //定义返回结果
        Map<String, String> map = new HashMap<>();

        //密码为空
        if (oldpassword == null || oldpassword.equals("")) {
            map.put("result", "error");
        } else {

            //从session中取出当前登录人（登录人中就有密码属性）
            //获取当前登录人id
            User user = getUser(request);//调用父类的方法，获取session对象

            if (user == null) {//session中没有用户-->session错误
                map.put("result", "sessionerror");

            } else {
                //调用Service，执行查询， id、pwd-->有用户，说明密码对；无用户，说明密码错误
                UserService userService = new UserServiceImpl();
                User userR = userService.login(user.getUserCode(), oldpassword);

                //用户不为空
                //密码正确
                //用户为空
                //密码错误
                if (userR != null) {
                    map.put("result", "true");
                } else {
                    map.put("result", "false");
                }
            }
        }

        String json = JSON.toJSONString(map);

        //返回数据，创建响应体
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
}
