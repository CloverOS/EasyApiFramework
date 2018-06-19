package cn.khthink.easyapi.config;

import cn.khthink.easyapi.filter.EasyFilter;
import cn.khthink.easyapi.kit.EasyLogger;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;

/*
    Create by KH at 2017/11/21 13:27 
 */

/**
 * 服务监听
 *
 * @author kh
 */
@WebListener
public class LoadConfig implements ServletContextListener {

    public LoadConfig() {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (!CoreConfig.isInit) {
            EasyLogger.init();
            CoreConfig.webPath = servletContextEvent.getServletContext().getRealPath("/").replace("%20", " ");
            CoreConfig.getInstance().init();
            servletContextEvent.getServletContext().addFilter("EasyFilter", EasyFilter.class).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*." + CoreConfig.fix);
        } else {
            CoreConfig.getInstance().reload();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        CoreConfig.getInstance().destory();
    }
}
