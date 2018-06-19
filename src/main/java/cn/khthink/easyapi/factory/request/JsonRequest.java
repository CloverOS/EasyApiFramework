package cn.khthink.easyapi.factory.request;

import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.action.RequestData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Json请求数据转换
 *
 * @author kh
 */
public class JsonRequest implements RequestData {

    @Override
    public JSONObject getDatas(HttpServletRequest request) throws IOException {
        JSONObject jsonObject;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ServletInputStream sis = request.getInputStream();
        int buff;
        while ((buff = sis.read()) != -1) {
            bos.write(buff);
        }
        bos.close();
        sis.close();
        jsonObject = JSON.parseObject(new String(bos.toByteArray(), CoreConfig.charset));
        return jsonObject;
    }
}
