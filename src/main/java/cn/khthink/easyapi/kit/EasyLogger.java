package cn.khthink.easyapi.kit;

import cn.khthink.easyapi.config.CoreConfig;
import lombok.extern.slf4j.Slf4j;


/**
 * 日志组件
 *
 * @author kh
 */
@Slf4j
public class EasyLogger {

    private static boolean isLog = false;

    /**
     * 日志等级
     */
    public enum Level {
        /**
         * 调试
         */
        DEBUG,
        /**
         * 信息
         */
        INFO,
        /**
         * 错误
         */
        ERROR,
        /**
         * 警告
         */
        WARNING
    }


    /**
     * 初始化日志
     */
    public static void init() {
        isLog = CoreConfig.isLog;
    }

    public static void info(Object msg) {
        if (isLog) {
            log.info(msg.toString());
        }
    }

    public static void trace(Object o) {
        if (isLog) {
            log.trace(o.toString());
        }
    }

    public static void trace(Object o, Throwable throwable) {
        if (isLog) {
            log.trace(o.toString(), throwable);
        }
    }

    public static void info(Object msg, Throwable throwable) {
        if (isLog) {
            log.info(msg.toString(), throwable);
        }
    }

    public static void infowithgui(String msg) {
        if (isLog) {
            StringBuilder g = new StringBuilder();
            int n = 60;
            for (int i = 0; i < n; i++) {
                g.append("-");
            }
            log.info(g.toString());
            log.info(msg.substring(0, n));
        }
    }
}
