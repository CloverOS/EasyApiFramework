package cn.khthink.easyapi.annotation.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by KH on 2017/1/5.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Proxy {
    /**
     * 代理器名字
     * @return
     */
    String proxy();

    /**
     * 处理器标识
     * @return
     */
    String actionTag();

    /**
     * 代理目标处理器
     * @return
     */
    String actionName();
}
