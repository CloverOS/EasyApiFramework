package cn.khthink.easyapi.tools;

/*
    Create by KH at 2017/10/18 14:17 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.bean.UriInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源地址检查工具
 *
 * @author kh
 */
public class UriCheckTools {
    private static final String XFORWARDED = "x-forwarded-for";

    /**
     * 获取客户端IP地址
     *
     * @param request servlet请求
     * @return String
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader(XFORWARDED) == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader(XFORWARDED);
    }


    /**
     * 路由信息转换
     *
     * @param uri 路由地址
     * @return UriInfo
     */
    public static UriInfo getActionName(String uri) {
        return new UriInfo(uri.split("/")[1], uri.split("/")[2]);
    }

    /**
     * 请求方法
     */
    public enum Method {
        /**
         * get
         */
        GET,
        /**
         * post
         **/
        POST
    }
}
