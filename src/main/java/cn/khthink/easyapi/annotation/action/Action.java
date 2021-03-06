package cn.khthink.easyapi.annotation.action;

import cn.khthink.easyapi.annotation.config.cache.ActionCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by KH on 2016/12/23.
 */

/**
 * 处理器注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    /**
     * 处理器名称
     *
     * @return String
     */
    String actionName();

    /**
     * 处理器标识
     *
     * @return String
     */
    String actionTag();

    /**
     * 组
     *
     * @return ActionGroup
     */
    ActionGroup group();

    /**
     * 处理器作者
     *
     * @return String
     */
    String Auther() default "kh";

    /**
     * 处理器描述
     *
     * @return String
     */
    String actionDesc() default "";

    /**
     * 处理器参数描述
     *
     * @return ActionParam[]
     */
    ActionParam[] params() default {};

    /**
     * 处理器返回参数描述
     *
     * @return ActionParam[]
     */
    ActionParam[] returnParams() default {};

    /**
     * 是否开启接口缓存
     *
     * @return ActionCache
     */
    ActionCache cache() default @ActionCache;

    /**
     * 是否开启session验证
     *
     * @return boolean
     */
    boolean isSessionOpen() default false;

    /**
     * 是否开启token验证
     *
     * @return boolean
     */
    boolean isToken() default false;
}
