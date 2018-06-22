package cn.khthink.easyapi.kit;

import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.config.CoreConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求处理组件
 *
 * @author kh
 */
public class EasyRequestKit {
    private static Map<String, Long> map;

    private static class RequestKit {
        private static EasyRequestKit easyRequestKit = new EasyRequestKit();
    }

    public static EasyRequestKit getInstance() {
        return RequestKit.easyRequestKit;
    }

    /**
     * 初始化
     */
    public void init() {
        map = new ConcurrentHashMap<>(10000);
        EasyLogger.info("--->接口限制:" + CoreConfig.limit + "ms");
    }

    /**
     * 添加请求
     *
     * @param request 请求
     */
    public static void addRequest(Request request) {
        String req = request.getRemoteip() + request.getMethod() + request.getUriInfo() + request.getDatas();
        map.put(req, request.getRequesttime());
    }

    /**
     * 是否被限制
     *
     * @param request 请求
     * @return boolean
     */
    public static boolean isLimit(Request request) {
        String req = request.getRemoteip() + request.getMethod() + request.getUriInfo() + request.getDatas();
        Long time = null;
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getKey().equals(req)) {
                time = entry.getValue();
                break;
            }
        }
        if (time != null) {
            if (request.getRequesttime() - time < CoreConfig.limit) {
                return false;
            } else {
                map.remove(req);
            }
        }
        return true;
    }

    /**
     * 销毁
     */
    public void destory() {
        map.clear();
        map = null;
    }
}
