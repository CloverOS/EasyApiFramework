package cn.khthink.easyapi.kit.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * Create by kh at 2018/12/1
 *
 * @author kh
 */
@Getter
@Setter
@Builder
public class EasyScheduleTask {
    private String name;
    private Runnable task;
    private long initialDelay;
    private long delay;
    private long period;
    private TimeUnit timeUnit;
    private EasyScheduleTaskKit.ScheduleType scheduleType;
    private String cron;
}
