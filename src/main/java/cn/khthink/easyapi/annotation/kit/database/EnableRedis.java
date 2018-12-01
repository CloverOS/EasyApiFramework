package cn.khthink.easyapi.annotation.kit.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableRedis {
    boolean value() default false;

    /**
     * 是否开启Session支持
     *
     * @return boolean
     */
    boolean isSessionSupport() default true;

    /**
     * 地址
     *
     * @return String
     */
    String host() default "localhost";

    /**
     * 端口
     *
     * @return int
     */
    int port() default 6379;

    /**
     * 密码
     *
     * @return String
     */
    String passwd() default "";
}
