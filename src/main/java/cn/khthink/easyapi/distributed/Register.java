package cn.khthink.easyapi.distributed;


import cn.khthink.easyapi.action.Action;

/**
 * 分布式注册
 *
 * @author kh
 */
public interface Register {

    /**
     * 注册服务
     *
     * @param action 处理器
     * @return Register
     */
    Register registerService(Action[] action);

}
