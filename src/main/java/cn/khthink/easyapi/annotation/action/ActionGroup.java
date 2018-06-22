package cn.khthink.easyapi.annotation.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by KH on 2016/12/23.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionGroup {
    /**
     * 权限Id
     *
     * @return int
     */
    int power() default 1000;

    /**
     * 组名
     *
     * @return String
     */
    String groupName() default "";
}
