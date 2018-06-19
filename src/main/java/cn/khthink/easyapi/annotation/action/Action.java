package cn.khthink.easyapi.annotation.action;

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
     * @return
     */
    String actionName();

    /**
     * 处理器标识
     *
     * @return
     */
    String actionTag();

    /**
     * 组
     *
     * @return
     */
    ActionGroup group();

    /**
     * 处理器作者
     *
     * @return
     */
    String Auther() default "kh";

    /**
     * 处理器描述
     *
     * @return
     */
    String actionDesc() default "";

    /**
     * 处理器参数描述
     *
     * @return
     */
    ActionParam[] params() default {};

    /**
     * 处理器返回参数描述
     *
     * @return
     */
    ActionParam[] returnParams() default {};

    /**
     * 是否开启接口缓存
     *
     * @return
     */
    boolean isCache() default false;

    /**
     * 是否开启session验证
     *
     * @return
     */
    boolean isSessionOpen() default false;

    /**
     * 是否开启token验证
     *
     * @return
     */
    boolean isToken() default false;
}
