package cn.khthink.easyapi.kit.communication;

/*
    Create by KH at 2017/11/10 14:25 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.config.ResultCode;
import cn.khthink.easyapi.kit.EasyLogger;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应组件
 *
 * @author kh
 */
public interface EasyResponse {

    /**
     * 发送成功空数据
     *
     * @param request 请求
     * @param msg     消息
     * @throws IOException IO异常
     */
    default void sendSuccessedEmptyDataMsg(Request request, String msg) throws IOException {
        sendEmptyDataMsg(request, msg, ResultCode.SUCCESSED);
    }

    /**
     * 发送失败空数据
     *
     * @param request 请求
     * @param msg     消息
     * @throws IOException IO异常
     */
    default void sendFailedEmptyDataMsg(Request request, String msg) throws IOException {
        sendEmptyDataMsg(request, msg, ResultCode.FAILED);
    }

    /**
     * 发送空数据
     *
     * @param request 请求
     * @param msg     消息
     * @param code    返回码
     * @throws IOException IO异常
     */
    default void sendEmptyDataMsg(Request request, String msg, int code) throws IOException {
        sendMsg(request, null, msg, code);
    }

    /**
     * 发送成功信息数据
     *
     * @param request 请求
     * @param datas   数据
     * @param msg     消息
     * @throws IOException IO异常
     */
    default void sendSuccessMsg(Request request, Object datas, String msg) throws IOException {
        sendMsg(request, datas, msg, ResultCode.SUCCESSED);
    }

    /**
     * 发送失败信息数据
     *
     * @param request 请求
     * @param datas   数据
     * @param msg     消息
     * @throws IOException IO异常
     */
    default void sendFailedMsg(Request request, Object datas, String msg) throws IOException {
        sendMsg(request, datas, msg, ResultCode.FAILED);
    }

    /**
     * 发送成功数据
     *
     * @param request 请求
     * @param datas   数据
     * @throws IOException IO异常
     */
    default void sendSuccess(Request request, Object datas) throws IOException {
        sendMsg(request, datas, null, ResultCode.SUCCESSED);
    }

    /**
     * 发送失败数据
     *
     * @param request 请求
     * @param datas   数据
     * @throws IOException IO异常
     */
    default void sendFailed(Request request, Object datas) throws IOException {
        sendMsg(request, datas, null, ResultCode.FAILED);
    }

    /**
     * 发送数据信息
     *
     * @param request 请求
     * @param datas   数据
     * @param msg     消息
     * @param code    返回码
     * @throws IOException IO异常
     */
    default void sendMsg(Request request, Object datas, String msg, int code) throws IOException {
        HttpServletResponse httpServletResponse = request.getHttpServletInfo().getHttpServletResponse();
        httpServletResponse.setCharacterEncoding(CoreConfig.charset);
        httpServletResponse.addHeader("content-type", "application/json;charset=utf-8");
        JSONObject responseJson = createResponseJson(msg, datas, code);
        httpServletResponse.getWriter().print(responseJson);
        EasyLogger.infowithgui("IP:" + request.getRemote() + "\t返回数据:" + responseJson.toString());
    }

    /**
     * 创建返回数据json包
     *
     * @param msg   消息
     * @param datas 数据
     * @param code  返回码
     * @return JSONObject
     */
    default JSONObject createResponseJson(String msg, Object datas, int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        jsonObject.put("datas", datas);
        jsonObject.put("code", code);
        return jsonObject;
    }
}
