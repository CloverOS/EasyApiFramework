package cn.khthink.easyapi.api;

import cn.khthink.easyapi.action.EasyActionPool;
import cn.khthink.easyapi.api.bean.ActionBean;
import cn.khthink.easyapi.config.CoreConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
    Create by KH at 2017/11/21 18:21 
 */

/**
 * 获取api列表
 *
 * @author kh
 */
@WebServlet("/getapi")
public class GetApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(CoreConfig.charset);
        resp.setCharacterEncoding(CoreConfig.charset);
        String r = "returnp";
        String action = req.getParameter("action");
        String group = req.getParameter("group");
        String actionreturn = req.getParameter("r");
        if (action == null || "".equals(action)) {
            JSONArray jsonArray = new JSONArray();
            JSONObject result = new JSONObject();
            if (group != null && !"".equals(group)) {
                List<ActionBean> actionsByGroup = EasyActionPool.getInstance().getActionsByGroup(group);
                for (ActionBean ac : actionsByGroup) {
                    jsonArray.add(JSONObject.toJSON(ac));
                }
            } else {
                List<ActionBean> list = EasyActionPool.getInstance().getAllActions();
                for (ActionBean ac : list) {
                    jsonArray.add(JSONObject.toJSON(ac));
                }
            }
            result.put("code", 0);
            result.put("msg", "");
            result.put("count", jsonArray.size());
            result.put("data", jsonArray);
            resp.getWriter().print(result.toJSONString());
        } else if (r.equals(actionreturn)) {
            JSONArray jsonArray = new JSONArray();
            ActionBean ac = EasyActionPool.getInstance().getActionInfo(action);
            jsonArray.addAll(Arrays.asList(ac.getReturnParam()));
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("msg", "");
            result.put("data", jsonArray);
            resp.getWriter().print(result.toJSONString());
        } else {
            JSONArray jsonArray = new JSONArray();
            ActionBean ac = EasyActionPool.getInstance().getActionInfo(action);
            jsonArray.addAll(Arrays.asList(ac.getActionParam()));
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("msg", "");
            result.put("actionname", ac.getActionname());
            result.put("auther", ac.getAuther());
            result.put("desc", ac.getDesc());
            result.put("group", ac.getGroup().getGroupName());
            result.put("power", ac.getGroup().getPower());
            result.put("session",ac.isSessionOpen());
            result.put("token",ac.isToken());
            result.put("count", jsonArray.size());
            result.put("url", "http://" + CoreConfig.webHost + "/" + ac.getInfo().getTag() + "/" + ac.getInfo().getActionname() + "." + CoreConfig.fix);
            result.put("data", jsonArray);
            resp.getWriter().print(result.toJSONString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
