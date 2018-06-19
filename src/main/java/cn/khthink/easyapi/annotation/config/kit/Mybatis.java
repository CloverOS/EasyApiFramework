package cn.khthink.easyapi.annotation.config.kit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mybatis {
    /**
     * 开启mybatis
     *
     * @return
     */
    boolean enable() default true;

    /**
     * 配置文件
     *
     * @return
     */
    String config() default "";

    /**
     * mappers包名
     *
     * @return
     */
    String[] mappers() default {};
}
