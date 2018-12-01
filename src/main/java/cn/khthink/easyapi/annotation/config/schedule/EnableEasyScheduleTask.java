package cn.khthink.easyapi.annotation.config.schedule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by kh at 2018/12/1
 * <p>
 * 定时任务配置注解
 *
 * @author kh
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableEasyScheduleTask {

    /**
     * 是否开启定时任务
     *
     * @return boolean
     */
    boolean enable() default false;
}
