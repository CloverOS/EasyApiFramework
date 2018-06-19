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
     * @param action
     * @return
     */
    Register registerService(Action[] action);

}
