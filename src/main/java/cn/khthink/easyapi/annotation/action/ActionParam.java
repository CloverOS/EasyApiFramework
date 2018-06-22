package cn.khthink.easyapi.annotation.action;

/**
 * Created by kh on 2017/7/6.
 */

import cn.khthink.easyapi.api.ParamType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理器参数描述
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionParam {

    /**
     * 参数名
     *
     * @return String
     */
    String param() default "";

    /**
     * 参数类型
     *
     * @return ParamType
     */
    ParamType type() default ParamType.STRING;

    /**
     * 参数描述
     *
     * @return String
     */
    String paramDesc() default "";

    /**
     * 参数例子
     *
     * @return String
     */
    String paramExmple() default "";

    /**
     * 参数默认值
     *
     * @return String
     */
    String defaultValue() default "";

    /**
     * 参数是否必须
     *
     * @return boolean
     */
    boolean isNeed() default true;
}
