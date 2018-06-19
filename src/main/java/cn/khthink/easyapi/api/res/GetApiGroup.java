package cn.khthink.easyapi.api.res;

import cn.khthink.easyapi.action.EasyActionPool;
import cn.khthink.easyapi.api.bean.ActionGroup;
import cn.khthink.easyapi.config.CoreConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 获取接口分组列表
 *
 * @author kh
 */
@WebServlet("/getapigroup")
public class GetApiGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CoreConfig.charset);
        resp.setCharacterEncoding(CoreConfig.charset);
        JSONArray jsonArray = new JSONArray();
        Map<String, ActionGroup> actionGroupList = EasyActionPool.getInstance().getActionGroupList();
        for (String s : actionGroupList.keySet()) {
            JSONObject jsonObject = new JSONObject();
            JSONArray groupdatas = new JSONArray();
            jsonObject.put("groupname", s);
            jsonObject.put("actioncount", actionGroupList.get(s).getActionList().size());
            groupdatas.addAll(actionGroupList.get(s).getActionList());
            jsonObject.put("actionlist", groupdatas);
            jsonArray.add(jsonObject);
        }
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", jsonArray);
        resp.setContentType("");
        resp.getWriter().print(result.toJSONString());
    }
}
