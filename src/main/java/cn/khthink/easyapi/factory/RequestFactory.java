package cn.khthink.easyapi.factory;

import cn.khthink.easyapi.bean.HttpServletInfo;
import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.bean.UriInfo;
import cn.khthink.easyapi.config.Constant;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.factory.request.FileRequest;
import cn.khthink.easyapi.factory.request.FormRequest;
import cn.khthink.easyapi.factory.request.JsonRequest;
import cn.khthink.easyapi.factory.request.XmlRequest;
import cn.khthink.easyapi.kit.EasyLogger;
import cn.khthink.easyapi.tools.UriCheckTools;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
    Create by KH at 2017/10/19 12:23 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

/**
 * 请求数据类工厂
 *
 * @author kh
 */
public class RequestFactory {
    private static String remoteip;
    private static int remoteport;
    private static String requri;
    private static String method;
    private static UriInfo uriInfo;
    private static JSONObject datas;

    public static Request createRequest(HttpServletInfo httpServletInfo, long time) {
        try {
            remoteip = UriCheckTools.getRemortIP(httpServletInfo.getHttpServletRequest());
            remoteport = httpServletInfo.getHttpServletRequest().getRemotePort();
            requri = httpServletInfo.getHttpServletRequest().getRequestURI();
            method = httpServletInfo.getHttpServletRequest().getMethod();
            if ("".equals(requri) || requri == null) {
                return null;
            }
            uriInfo = UriCheckTools.getActionName(requri.replace("." + CoreConfig.fix, ""));
            datas = getDatas(httpServletInfo.getHttpServletRequest());
            return new Request(remoteip, remoteport, requri, method, uriInfo, datas, time, httpServletInfo);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            EasyLogger.info("无法创建请求类", e);
        }
        return null;
    }

    private static JSONObject getDatas(HttpServletRequest req) throws IOException, InstantiationException, IllegalAccessException {
        req.setCharacterEncoding(CoreConfig.charset);
        String contentType = req.getContentType() == null ? "" : req.getContentType();
        JSONObject jsonObject;
        if (contentType.startsWith(Constant.JSON)) {
            jsonObject = RequestDataFormat.getDatas(JsonRequest.class, req);
        } else if (contentType.startsWith(Constant.XML)) {
            jsonObject = RequestDataFormat.getDatas(XmlRequest.class, req);
        } else if (contentType.startsWith(Constant.FILE)) {
            jsonObject = RequestDataFormat.getDatas(FileRequest.class, req);
        } else {
            jsonObject = RequestDataFormat.getDatas(FormRequest.class, req);
        }
        return jsonObject;
    }
}
