package cn.khthink.easyapi.kit.schedule;

import cn.khthink.easyapi.annotation.kit.schedule.EasySchedule;
import cn.khthink.easyapi.annotation.kit.schedule.ScheduleTask;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.kit.EasyLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by kh at 2018/11/30
 * <p>
 * 定时任务组件
 *
 * @author kh
 */
public class EasyScheduleTaskKit {

    private static final int CORE_COUNT = 10;
    private EasyScheduleThreadFactory easyScheduleThreadFactory = new EasyScheduleThreadFactory();
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(CORE_COUNT, easyScheduleThreadFactory);
    private List<EasyScheduleTask> taskList = new ArrayList<>();

    private static class Kit {
        private static EasyScheduleTaskKit easyScheduleTaskKit = new EasyScheduleTaskKit();
    }

    public static EasyScheduleTaskKit getInstance() {
        return Kit.easyScheduleTaskKit;
    }

    /**
     * 初始化
     */
    public void init() {
        EasyLogger.info("EasyScheduleTask--->开启");
        int tempName = 0;
        for (Class taskClass : CoreConfig.classList) {
            EasySchedule easySchedule = (EasySchedule) taskClass.getAnnotation(EasySchedule.class);
            if (easySchedule != null) {
                Method[] methods = taskClass.getMethods();
                for (Method method : methods) {
                    ScheduleTask scheduleTask = method.getAnnotation(ScheduleTask.class);
                    if (scheduleTask != null) {
                        method.setAccessible(true);
                        EasyScheduleTask task = EasyScheduleTask
                                .builder()
                                .delay(scheduleTask.delay())
                                .initialDelay(scheduleTask.initialDelay())
                                .period(scheduleTask.period())
                                .timeUnit(scheduleTask.timeUnit())
                                .scheduleType(scheduleTask.scheduleType())
                                .task(() -> {
                                    try {
                                        method.invoke(taskClass.newInstance());
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        EasyLogger.trace("定时任务组件方法执行失败", e);
                                    } catch (InstantiationException e) {
                                        EasyLogger.trace("定时任务组件实例创建失败", e);
                                    }
                                })
                                .name(easySchedule.name().isEmpty() ? "task" + tempName++ : easySchedule.name())
                                .build();
                        taskList.add(task);
                        EasyLogger.info("EasyScheduleTask--->添加:" + task.getName());
                    }
                }
            }
        }
        taskList.forEach(this::runTask);
    }

    /**
     * 添加并运行任务
     *
     * @param easyScheduleTask 任务
     */
    private synchronized void runTask(EasyScheduleTask easyScheduleTask) {
        switch (easyScheduleTask.getScheduleType()) {
            case AtFixedRate:
                scheduledThreadPoolExecutor.scheduleAtFixedRate(easyScheduleTask.getTask(), easyScheduleTask.getInitialDelay(), easyScheduleTask.getPeriod(), easyScheduleTask.getTimeUnit());
                break;
            case WithFixedDelay:
                scheduledThreadPoolExecutor.scheduleWithFixedDelay(easyScheduleTask.getTask(), easyScheduleTask.getInitialDelay(), easyScheduleTask.getDelay(), easyScheduleTask.getTimeUnit());
                break;
            default:
                scheduledThreadPoolExecutor.schedule(easyScheduleTask.getTask(), easyScheduleTask.getDelay(), easyScheduleTask.getTimeUnit());
                break;
        }
        EasyLogger.info("EasyScheduleTask--->运行:" + easyScheduleTask.getName());
    }

    /**
     * 线程工厂
     */
    static class EasyScheduleThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        EasyScheduleThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "EasyScheduleTaskKit-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 立即停止
     */
    public void shotdownNow() {
        if (scheduledThreadPoolExecutor != null && !scheduledThreadPoolExecutor.isShutdown()) {
            scheduledThreadPoolExecutor.shutdownNow();
            EasyLogger.info("EasyScheduleTask--->关闭");
        }
    }


    /**
     * 定时任务类型
     */
    public enum ScheduleType {
        /**
         * 创建并执行在给定的初始延迟之后，随后以给定的时间段首先启用的周期性动作
         */
        AtFixedRate,
        /**
         * 创建并执行在给定的初始延迟之后首先启用的定期动作，随后在一个执行的终止和下一个执行的开始之间给定的延迟。
         */
        WithFixedDelay,
        /**
         * 创建并执行在给定延迟后启用的单次操作。
         */
        Single
    }
}
