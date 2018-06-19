package cn.khthink.easyapi.action;

/*
    Create by KH at 2017/10/26 23:02 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.config.ResultCode;
import cn.khthink.easyapi.kit.EasyLogger;
import cn.khthink.easyapi.kit.EasyRequestKit;
import cn.khthink.easyapi.kit.EasyResponse;

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
        if (EasyRequestKit.isLimit(request)) {
            EasyRequestKit.addRequest(request);
            if (CoreConfig.verify(request)) {
                EasyActionPool.getInstance().getAction(request.getUriInfo()).verifyParameter(request);
            } else {
                try {
                    sendEmptyDataMsg(request, PROTOCOLERROR, ResultCode.PROTOCOLERROR);
                } catch (IOException e) {
                    EasyLogger.info(PROTOCOLERROR, e);
                }
            }
        } else {
            try {
                sendEmptyDataMsg(request, LIMITERROR, ResultCode.LIMITERROR);
            } catch (IOException e) {
                EasyLogger.info(LIMITERROR, e);
            }
        }
    }
}
