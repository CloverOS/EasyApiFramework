package cn.khthink.easyapi.annotation.config.kit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableRedis {
    boolean value() default false;

    /**
     * 地址
     *
     * @return
     */
    String host() default "localhost";

    /**
     * 端口
     *
     * @return
     */
    int port() default 6379;

    /**
     * 密码
     *
     * @return
     */
    String passwd() default "";
}
