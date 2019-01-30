package cn.khthink.easyapi.filter;

import cn.khthink.easyapi.action.ProxyAction;
import cn.khthink.easyapi.bean.HttpServletInfo;
import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.factory.RequestFactory;
import cn.khthink.easyapi.kit.EasyLogger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Create by KH at 2017/10/18 14:15 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

/**
 * 核心拦截器
 *
 * @author kh
 */
public class EasyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (!req.getRequestURI().endsWith(CoreConfig.fix)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        String characterEncoding = req.getCharacterEncoding();
        Request request = RequestFactory.createRequest(new HttpServletInfo(req, resp), System.currentTimeMillis());
        if (request == null) {
            return;
        }
        request.getInfo();
        if (characterEncoding != null) {
            if (!characterEncoding.equalsIgnoreCase(CoreConfig.charset)) {
                resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "编码不一致");
                EasyLogger.info(request.getRemote() + "--->编码不一致");
                return;
            }
        } else {
            characterEncoding = CoreConfig.charset;
            req.setCharacterEncoding(characterEncoding);
            resp.setCharacterEncoding(characterEncoding);
        }
        try {
            ProxyAction.getInstance().processData(request);
        } catch (Exception e) {
            resp.getWriter().print("{\"msg\":\"处理器无法处理请求\",\"code\": 500}");
            EasyLogger.info("处理器无法处理请求", e);
        }
    }

    @Override
    public void destroy() {
    }
}
