package cn.khthink.easyapi.action;


import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.config.ResultCode;
import cn.khthink.easyapi.kit.EasyLogger;
import cn.khthink.easyapi.kit.communication.EasyRequestKit;
import cn.khthink.easyapi.kit.communication.EasyResponse;

import java.io.IOException;

import static cn.khthink.easyapi.config.Constant.LIMITERROR;
import static cn.khthink.easyapi.config.Constant.PROTOCOLERROR;

/**
 * 处理器代理类
 *
 * @author kh
 */
public class ProxyAction implements Action, EasyResponse {

    private static class Proxy {
        private static ProxyAction proxyAction = new ProxyAction();
    }

    private ProxyAction() {
    }

    public static ProxyAction getInstance() {
        return Proxy.proxyAction;
    }

    @Override
    public void processData(Request request) throws Exception {
        if (CoreConfig.enableRequestLimit) {
            if (EasyRequestKit.isLimit(request)) {
                EasyRequestKit.addRequest(request);
                process(request);
            } else {
                try {
                    sendEmptyDataMsg(request, LIMITERROR, ResultCode.LIMITERROR);
                } catch (IOException e) {
                    EasyLogger.info(LIMITERROR, e);
                }
            }
        } else {
            process(request);
        }
    }

    /**
     * 处理请求
     *
     * @param request 请求
     */
    private void process(Request request) throws Exception {
        if (CoreConfig.verify(request)) {
            EasyActionPool.getInstance().getAction(request.getUriInfo()).verifyParameter(request);
        } else {
            try {
                sendEmptyDataMsg(request, PROTOCOLERROR, ResultCode.PROTOCOLERROR);
            } catch (IOException e) {
                EasyLogger.info(PROTOCOLERROR, e);
            }
        }
    }
}
