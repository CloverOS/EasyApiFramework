package cn.khthink.easyapi.factory.request;

import cn.khthink.easyapi.action.RequestData;
import cn.khthink.easyapi.kit.io.FileKit;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传请求转换
 *
 * @author kh
 */
public class FileRequest implements RequestData, FileKit {
    @Override
    public JSONObject getDatas(HttpServletRequest request) {
        return uploadFile(request);
    }
}
