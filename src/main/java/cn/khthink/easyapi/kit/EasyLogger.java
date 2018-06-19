package cn.khthink.easyapi.kit;

import cn.khthink.easyapi.config.CoreConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    Create by KH at 2017/10/18 14:22 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

/**
 * 日志组件
 *
 * @author kh
 */
public class EasyLogger {
    private static Logger logger;

    private static boolean isLog = false;


    /**
     * 初始化日志
     */
    public static void init() {
        isLog = CoreConfig.isLog;
        logger = LoggerFactory.getLogger(EasyLogger.class);
    }

    public static void info(Object msg) {
        if (isLog) {
            logger.info(msg.toString());
        }
    }

    public static void trace(Object o) {
        if (isLog) {
            logger.trace(o.toString());
        }
    }

    public static void trace(Object o, Throwable throwable) {
        if (isLog) {
            logger.trace(o.toString(), throwable);
        }
    }

    public static void info(Object msg, Throwable throwable) {
        if (isLog) {
            logger.info(msg.toString(), throwable);
        }
    }

    public static void infowithgui(String msg) {
        if (isLog) {
            StringBuilder g = new StringBuilder();
            int n = 20;
            for (int i = 0; i < n; i++) {
                g.append("-");
            }
            logger.info(g.toString());
            logger.info(msg);
            logger.info(g.toString());
        }
    }
}
