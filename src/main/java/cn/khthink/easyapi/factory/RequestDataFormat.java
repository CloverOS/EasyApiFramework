package cn.khthink.easyapi.factory;

import cn.khthink.easyapi.action.RequestData;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求数据格式化
 *
 * @author kh
 */
public class RequestDataFormat {

    /**
     * 获取数据格式化后的Json
     *
     * @param requestData 请求数据
     * @param request     servlet请求
     * @return JSONObject
     * @throws IOException            IO异常
     * @throws IllegalAccessException 非法异常
     * @throws InstantiationException 实例化异常
     */
    public static JSONObject getDatas(Class<? extends RequestData> requestData, HttpServletRequest request) throws IOException, IllegalAccessException, InstantiationException {
        return requestData.newInstance().getDatas(request);
    }
}
