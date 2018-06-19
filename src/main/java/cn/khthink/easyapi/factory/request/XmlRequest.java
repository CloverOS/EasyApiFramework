package cn.khthink.easyapi.factory.request;

import cn.khthink.easyapi.action.RequestData;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * xml请求数据
 *
 * @author kh
 */
public class XmlRequest implements RequestData {
    @Override
    public JSONObject getDatas(HttpServletRequest request) throws IOException {
        return null;
    }
}
