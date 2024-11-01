package com.smbms.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {
    private String encode=null;

    public void destroy() {
        encode=null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //如果请求编码格式没有设定，则设定为UTF-8
//        if(req.getCharacterEncoding()==null){
//            req.setCharacterEncoding(encode);
//        }
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        if(encode==null){
            encode="UTF-8";
        }
    }

}
