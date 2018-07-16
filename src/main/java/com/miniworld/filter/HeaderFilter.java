package com.miniworld.filter;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.SpringContextHolder;
import com.miniworld.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HeaderFilter implements Filter {

//    @Resource
    private SystemConfig systemConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("init systemFilter,filterConfig={}", filterConfig);
        systemConfig = SpringContextHolder.getBean("systemConfig");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
//        req.setCharacterEncoding("UTF-8");
//        resp.setContentType("text/html;charset=utf-8");
        if (systemConfig.getIsOfficial().equals("0")) {
            String origin = req.getHeader("Origin");

            if (origin == null) {
                resp.addHeader("Access-Control-Allow-Origin", "*");
            } else {
                resp.addHeader("Access-Control-Allow-Origin", origin);
            }
            resp.addHeader("Access-Control-Allow-Credentials", "true");
            resp.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
            resp.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            if ( req.getMethod().equals("OPTIONS") ) {
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }
        chain.doFilter(request, response);
        logger.debug("doFilter set header");
    }

    @Override
    public void destroy() {
        logger.debug("systemFilter destroy");
    }
}
