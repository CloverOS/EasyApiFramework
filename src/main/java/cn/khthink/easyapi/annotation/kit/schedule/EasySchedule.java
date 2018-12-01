package cn.khthink.easyapi.annotation.kit.schedule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by kh at 2018/12/1
 * <p>
 * 定时任务注解
 *
 * @author kh
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasySchedule {
    /**
     * 任务名称
     *
     * @return 任务名称
     */
    String name() default "";
}
