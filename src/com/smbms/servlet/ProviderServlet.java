package com.smbms.servlet;

import com.alibaba.fastjson.JSON;
import com.smbms.entity.Provider;
import com.smbms.entity.User;
import com.smbms.service.bill.BillService;
import com.smbms.service.bill.BillServiceImpl;
import com.smbms.service.provider.ProviderService;
import com.smbms.service.provider.ProviderServiceImpl;
import com.smbms.util.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProviderServlet",urlPatterns = "/jsp/provider.do")
public class ProviderServlet extends AbstractServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取提交参数
        String method=request.getParameter("method");
        if(method==null||method.equals("")){
            response.sendRedirect("../error.jsp");
        }else {
            if(method.equals("query")){//进入 查询供应商
                this.doQueryProvider(request,response);
            }else if (method.equals("add")) {//-->进入 执行新增供应商
                this.doAddProvider(request, response);
            }else if(method.equals("delprovider")){//进入 ajax删除供应商
                this.doDeleteProvider(request,response);
            }else if(method.equals("view")){//进入 查看供应商
                this.doViewProvider(request,response);
            }else if(method.equals("modify")){//-->进入 更新供应商
                this.doModifyProvider(request,response);
            }else if(method.equals("modifyexe")){//-->进入 执行更新供应商
                this.doExeModifyProvider(request,response);
            }
        }
    }

    /**
     * 执行更新供应商
     * @param request
     * @param response
     */
    private void doExeModifyProvider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        //proid proCode proName proContact proPhone proAddress proFax proDesc
        String proid=request.getParameter("proid");
        String proCode=request.getParameter("proCode");
        String proName=request.getParameter("proName");
        String proContact=request.getParameter("proContact");
        String proPhone=request.getParameter("proPhone");
        String proAddress=request.getParameter("proAddress");
        String proFax=request.getParameter("proFax");
        String proDesc=request.getParameter("proDesc");

        int id=Integer.parseInt(proid);

        User sessionUser=getUser(request);
        long modifyBy=sessionUser.getId();
        int modifyById=(int)modifyBy;

        Date modifyDate=new Date();

        //封装对象
        Provider provider=new Provider(id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,null,null,modifyById,modifyDate);

        ProviderService providerService=new ProviderServiceImpl();
        int num=providerService.updateProvider(provider);
        if(num>0){
            //跳转到列表页面
            this.doQueryProvider(request,response);
        }else {
            request.setAttribute("message","更新错误");
            request.getRequestDispatcher("providermodify.jsp").forward(request,response);
        }
    }

    /**
     * 更新供应商
     * @param request
     * @param response
     */
    private void doModifyProvider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String proid=request.getParameter("proid");
        int id=Integer.parseInt(proid);

        ProviderService providerService=new ProviderServiceImpl();
        Provider provider=providerService.queryById(id);

        request.setAttribute("provider",provider);
        request.getRequestDispatcher("providermodify.jsp").forward(request,response);
        //TODO 失去焦点问题
    }

    /**
     * 查看供应商
     *
     * @param request
     * @param response
     */
    private void doViewProvider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String proid=request.getParameter("proid");
        int id=Integer.parseInt(proid);

        ProviderService providerService=new ProviderServiceImpl();
        Provider provider=providerService.queryById(id);

        request.setAttribute("provider",provider);
        request.getRequestDispatcher("providerview.jsp").forward(request,response);
        //TODO 失去焦点问题
    }

    /**
     * ajax删除供应商
     *
     * @param request
     * @param response
     */
    private void doDeleteProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String proid=request.getParameter("proid");
        int providerId=Integer.parseInt(proid);

        Map<String,String> map=new HashMap<>();

        try {
            BillService billService=new BillServiceImpl();
            int num=billService.countProvider(providerId);
            //TODO bug 供应商下有订单，不能删除页面点击确定 出不去，会一直循环下去，点取消才行
            if(num>0){
                map.put("delResult","repetition");
            }else {
                ProviderService providerService=new ProviderServiceImpl();
                int num1=providerService.deleteProviderById(providerId);

                if(num1>0){
                    map.put("delResult","true");
                }else if(num==0){
                    map.put("delResult","notexist");
                }
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
     * 新增供应商
     *
     * @param request
     * @param response
     */
    private void doAddProvider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        //proCode proName proContact proPhone proAddress proFax proDesc
        String proCode=request.getParameter("proCode");
        String proName=request.getParameter("proName");
        String proContact=request.getParameter("proContact");
        String proPhone=request.getParameter("proPhone");
        String proAddress=request.getParameter("proAddress");
        String proFax=request.getParameter("proFax");
        String proDesc=request.getParameter("proDesc");

        //当前用户id，当前系统时间
        User sessionUser=getUser(request);
        long createBy=sessionUser.getId();
        //TODO long 和 Long的区别是什么
        int createById=(int)createBy;
        Date createDate=new Date();

        //封装对象
        Provider provider=new Provider(null,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createById,createDate,null,null);

        ProviderService providerService=new ProviderServiceImpl();
        int num=providerService.addProvider(provider);

        if(num>0){
            //跳转到列表页面
            this.doQueryProvider(request,response);
        }else {
            request.setAttribute("message","新增错误");
            request.getRequestDispatcher("provideradd.jsp").forward(request,response);
        }
        //TODO 点击返回的session失效问题

    }

    /**
     * 查询供应商，并且分页
     *
     * @param request
     * @param response
     */
    private void doQueryProvider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //供应商编码
        String queryProCode=request.getParameter("queryProCode");
        //供应商名称
        String queryProName=request.getParameter("queryProName");
        //获取当前页码
        String pageIndex=request.getParameter("pageIndex");

        //设置后台查询条件
        String proCode=null;//供应商编码
        String proName=null;//供应商名称
        int currentPage=1;//当前页码

        if(queryProCode==null||queryProCode.equals("")){
            proCode=null;
        }else {
            proCode=queryProCode;
        }

        if(queryProName==null||queryProName.equals("")){
            proName=null;
        }else {
            proName=queryProName;
        }

        if(pageIndex==null){
            currentPage=1;
        }else {
            currentPage=Integer.parseInt(pageIndex);
        }

        //业务层对象
        ProviderService providerService=new ProviderServiceImpl();

        //查询符合要求，一共的总数据量
        int total=providerService.countProvider(proCode,proName);
        //创建分页对象
        PageUtil pageUtil=new PageUtil(currentPage,total,10);


        //查询供应商列表

        List<Provider> providerList=providerService.queryProvider(proCode,proName,pageUtil.getStartNo(),pageUtil.getPageSize());

        //封装数据
        request.setAttribute("providerList",providerList);

        //封装数据-数据回显
        request.setAttribute("queryProCode", queryProCode);
        request.setAttribute("queryProName", queryProName);

        //封装数据-分页数据totalCount currentPageNo totalPageCount
        request.setAttribute("totalPageCount", pageUtil.getTotalPage());
        request.setAttribute("totalCount", pageUtil.getTotal());
        request.setAttribute("currentPageNo", pageUtil.getCurrentPage());

        request.getRequestDispatcher("providerlist.jsp").forward(request,response);
    }
}
