package cn.khthink.easyapi.annotation.config.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by kh at 2018/11/25
 * <p>
 * 处理器缓存
 *
 * @author kh
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionCache {
    /**
     * 是否开启
     *
     * @return boolean
     */
    boolean isEnable() default false;

    /**
     * 缓存Key
     *
     * @return String
     */
    String key() default "";

    /**
     * 清空Key
     *
     * @return String
     */
    String clearKey() default "";

}
