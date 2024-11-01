package com.smbms.servlet;

import com.alibaba.fastjson.JSON;
import com.smbms.entity.Bill;
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

@WebServlet(name = "BillServlet",urlPatterns = "/jsp/bill.do")
public class BillServlet extends AbstractServlet {
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
            if(method.equals("query")){//-->进入 查询列表模块
                this.doQueryBill(request,response);
            }else if(method.equals("getproviderlist")){//-->进入 ajax查询供应商列表
                this.doAjaxQueryProvider(request,response);
            }else if(method.equals("add")){//-->进入 执行新增订单
                this.doAddBill(request,response);
            }else if(method.equals("delbill")){//-->进入 ajax执行删除订单
                this.doDeleteBill(request,response);
            }else if(method.equals("view")){//-->进入 查看用户
                this.doViewBill(request,response);
            }else if(method.equals("modify")){//-->进入 更新用户
                this.doModifyBill(request,response);
            }else if(method.equals("modifysave")){//--> 进入 执行更新用户
                this.doExeModifyBill(request,response);
            }
        }
    }

    /**
     * 进入 执行更新用户
     *
     * @param request
     * @param response
     */
    private void doExeModifyBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billid=request.getParameter("id");

        //billCode  productName productUnit productCount totalPrice providerId isPayment
        String billCode=request.getParameter("billCode");
        String productName=request.getParameter("productName");
        String productUnit=request.getParameter("productUnit");
        String productCount=request.getParameter("productCount");
        String totalPrice=request.getParameter("totalPrice");
        String providerId=request.getParameter("providerId");
        String isPayment=request.getParameter("isPayment");

        int id=Integer.parseInt(billid);

        double count=Double.parseDouble(productCount);
        double price=Double.parseDouble(totalPrice);
        int providerId1=Integer.parseInt(providerId);
        int isPayment1=Integer.parseInt(isPayment);

        //当前用户id、当前系统时间-->更新人id、更新时间
        User sessionUser = getUser(request);
        long modifyBy = sessionUser.getId();
        int modifyById=(int)modifyBy;

        Date modifyDate = new Date();

        Bill bill = new Bill(id, billCode, productName, null, productUnit, count, price, isPayment1, providerId1, null, null, modifyById, modifyDate);

        BillService billService=new BillServiceImpl();
        int num=billService.updateBill(bill);

        if(num>0){
            //跳转到列表页面
            this.doQueryBill(request,response);
        }else {
            request.setAttribute("message","更新错误");
            request.getRequestDispatcher("billmodify.jsp").forward(request,response);
        }

    }

    /**
     * 进入 更新用户
     *
     * @param request
     * @param response
     */
    private void doModifyBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billid=request.getParameter("billid");
        int id=Integer.parseInt(billid);

        BillService billService=new BillServiceImpl();
        Bill bill=billService.queryById(id);

        request.setAttribute("bill",bill);
        request.getRequestDispatcher("billmodify.jsp").forward(request,response);
    }

    /**
     * 查看订单
     *
     * @param request
     * @param response
     */
    private void doViewBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String billid=request.getParameter("billid");
        int id=Integer.parseInt(billid);

        BillService billService=new BillServiceImpl();
        Bill bill=billService.queryById(id);

        request.setAttribute("bill",bill);
        request.getRequestDispatcher("billview.jsp").forward(request,response);
    }

    /**
     * 进入 ajax执行删除订单
     *
     * @param request
     * @param response
     */
    private void doDeleteBill(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String billid=request.getParameter("billid");
        int id=Integer.parseInt(billid);

        Map<String, String> map = new HashMap<>();

        try {
            BillService billService=new BillServiceImpl();
            int num=billService.deleteBillById(id);

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
     * 执行新增订单
     *
     * @param request
     * @param response
     */
    private void doAddBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        //billCode  productName productUnit productCount totalPrice providerId isPayment

        String billCode=request.getParameter("billCode");
        String productName=request.getParameter("productName");
        String productUnit=request.getParameter("productUnit");
        String productCount=request.getParameter("productCount");
        String totalPrice=request.getParameter("totalPrice");
        String providerId=request.getParameter("providerId");
        String isPayment=request.getParameter("isPayment");

        double count=Double.parseDouble(productCount);
        double price=Double.parseDouble(totalPrice);
        int providerId1=Integer.parseInt(providerId);
        int isPayment1=Integer.parseInt(isPayment);

        //当前用户id，当前系统时间
        User sessionUser = getUser(request);
        long createBy = sessionUser.getId();
        int createById=(int)createBy;

        Date createDate = new Date();

        //封装对象
        Bill bill = new Bill(null, billCode, productName, null, productUnit, count, price, isPayment1, providerId1, createById, createDate, null, null);

        BillService billService=new BillServiceImpl();
        int num=billService.addBill(bill);

        if (num > 0) {
            //跳转到列表页面
            this.doQueryBill(request, response);
        } else {
            request.setAttribute("message", "新增错误");
            request.getRequestDispatcher("billadd.jsp").forward(request, response);
        }
    }

    /**
     * 进入 ajax查询供应商列表
     *
     * @param request
     * @param response
     */
    private void doAjaxQueryProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProviderService providerService=new ProviderServiceImpl();
        List<Provider> providerList=providerService.queryProvider();

        String json = JSON.toJSONString(providerList);

        //返回数据，创建响应体
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 查询订单列表
     * @param request
     * @param response
     */
    private void doQueryBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //商品名称
        String queryProductName=request.getParameter("queryProductName");
        //供应商id
        String queryProviderId=request.getParameter("queryProviderId");
        //是否付款
        String queryIsPayment=request.getParameter("queryIsPayment");

        //获取当前页码
        String pageIndex = request.getParameter("pageIndex");

        String productName=null;
        Integer providerId=null;
        Integer isPayment=null;
        int currentPage = 1;//当前页码

        if(queryProductName==null||queryProductName.equals("")){
            productName=null;
        }else {
            productName=queryProductName;
        }

        if(queryProviderId==null||queryProviderId.equals("0")){
            providerId=null;
        }else {
            providerId=Integer.parseInt(queryProviderId);
        }

        if(queryIsPayment==null||queryIsPayment.equals("0")){
            isPayment=null;
        }else {
            isPayment=Integer.parseInt(queryIsPayment);
        }

        if (pageIndex == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(pageIndex);
        }


        BillService billService=new BillServiceImpl();
//        List<Bill> billList=billService.queryBill();
//        List<Bill> billList=billService.queryBill(queryProductName,queryProviderId,queryIsPayment);

        int total=billService.countBill(productName,providerId,isPayment);

        //创建分页对象
        PageUtil pageUtil = new PageUtil(currentPage, total, 10);

        List<Bill> billList=billService.queryBill(productName,providerId,isPayment,pageUtil.getStartNo(),pageUtil.getPageSize());

        //供应商列表查询
        ProviderService providerService=new ProviderServiceImpl();
        List<Provider> providerList=providerService.queryProvider();

        request.setAttribute("billList",billList);
        request.setAttribute("providerList",providerList);

        //封装数据-数据回显
        request.setAttribute("queryProductName", queryProductName);
        request.setAttribute("queryProviderId", queryProviderId);
        request.setAttribute("queryIsPayment", queryIsPayment);

        //封装数据-分页数据totalCount currentPageNo totalPageCount
        request.setAttribute("totalPageCount", pageUtil.getTotalPage());
        request.setAttribute("totalCount", pageUtil.getTotal());
        request.setAttribute("currentPageNo", pageUtil.getCurrentPage());

        request.getRequestDispatcher("billlist.jsp").forward(request,response);
    }


}
