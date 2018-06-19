package cn.khthink.easyapi.factory;

import cn.khthink.easyapi.action.RequestData;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求数据格式化
 * @author kh
 */
public class RequestDataFormat {

    /**
     * 获取数据格式化后的Json
     * @param requestData
     * @param request
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static JSONObject getDatas(Class<? extends RequestData> requestData, HttpServletRequest request) throws IOException, IllegalAccessException, InstantiationException {
        return requestData.newInstance().getDatas(request);
    }
}
