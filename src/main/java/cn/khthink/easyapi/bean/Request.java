package cn.khthink.easyapi.bean;

import cn.khthink.easyapi.kit.EasyLogger;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
    Create by KH at 2017/10/18 14:21 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

/**
 * 请求信息
 *
 * @author kh
 */
@Getter
@Setter
@ToString
public class Request {
    private String remoteip;
    private int remoteport;
    private String requri;
    private String method;
    private UriInfo uriInfo;
    private JSONObject datas;
    private long requesttime;
    private HttpServletInfo httpServletInfo;

    public Request(String remoteip, int remoteport, String requri, String method, UriInfo uriInfo, JSONObject datas, long requesttime, HttpServletInfo httpServletInfo) {
        this.remoteip = remoteip;
        this.remoteport = remoteport;
        this.requri = requri;
        this.method = method;
        this.uriInfo = uriInfo;
        this.datas = datas;
        this.requesttime = requesttime;
        this.httpServletInfo = httpServletInfo;
    }

    public String getRemote() {
        return remoteip + ":" + remoteport;
    }


    public void getInfo() {
        EasyLogger.infowithgui(getRemoteip() + ":" + getRemoteport() + "--->" + getRequri() + "\tMethod:" + getMethod() + "\tData:" + getDatas().toString());
    }

    public JSONObject getDatas() {
        return datas;
    }

    /**
     * 是否同一个用户
     *
     * @param request
     * @return
     */
    public boolean isSameUser(Request request) {
        return getRemote().equals(request.getRemote());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Request) {
            Request request = (Request) obj;
            return request.datas == this.datas && request.method.equals(this.method) && request.requri.equals(this.requri) && request.getRemote().equals(getRemote()) && request.uriInfo == this.uriInfo;
        }
        return false;
    }
}
