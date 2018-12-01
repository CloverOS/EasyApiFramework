package cn.khthink.easyapi.annotation.kit.schedule;

import cn.khthink.easyapi.kit.schedule.EasyScheduleTaskKit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Create by kh at 2018/12/1
 * <p>
 * 任务执行方法注解
 *
 * @author kh
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduleTask {
    /**
     * 初始化延迟时间
     *
     * @return long
     */
    long initialDelay() default 0L;

    /**
     * 延迟执行时间
     *
     * @return long
     */
    long delay() default 0L;

    /**
     * 执行周期
     *
     * @return long
     */
    long period() default 0L;

    /**
     * 时间单位
     *
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 任务类型
     *
     * @return 任务类型
     */
    EasyScheduleTaskKit.ScheduleType scheduleType() default EasyScheduleTaskKit.ScheduleType.Single;
}
