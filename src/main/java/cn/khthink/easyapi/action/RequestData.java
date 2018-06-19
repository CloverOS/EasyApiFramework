package cn.khthink.easyapi.action;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求数据
 *
 * @author kh
 */
public interface RequestData {
    /**
     * 获取请求数据
     *
     * @param request
     * @return
     * @throws IOException
     */
    JSONObject getDatas(HttpServletRequest request) throws IOException;
}
