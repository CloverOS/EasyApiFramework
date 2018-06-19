package cn.khthink.easyapi.factory.request;

import cn.khthink.easyapi.action.RequestData;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 表单请求数据转换
 *
 * @author kh
 */
public class FormRequest implements RequestData {
    @Override
    public JSONObject getDatas(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            if (entry.getValue().length == 1) {
                jsonObject.put(entry.getKey(), entry.getValue()[0]);
            } else {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        }
        return jsonObject;
    }
}
