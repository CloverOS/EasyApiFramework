package cn.khthink.easyapi.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {
    /**
     * 是否开启
     *
     * @return boolean
     */
    boolean enable() default false;

    /**
     * 间隔 单位毫秒
     *
     * @return int
     */
    int value() default 1000;
}
