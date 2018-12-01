package cn.khthink.easyapi.config;

/*
    Create by KH at 2017/10/18 14:19 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.action.EasyActionPool;
import cn.khthink.easyapi.annotation.config.EasyApiConfig;
import cn.khthink.easyapi.annotation.config.EnableAutoConfig;
import cn.khthink.easyapi.annotation.config.Log;
import cn.khthink.easyapi.annotation.config.protocol.EasyProtocol;
import cn.khthink.easyapi.annotation.config.schedule.EnableEasyScheduleTask;
import cn.khthink.easyapi.annotation.config.web.*;
import cn.khthink.easyapi.annotation.kit.FileUpload;
import cn.khthink.easyapi.annotation.kit.database.DataBase;
import cn.khthink.easyapi.annotation.kit.database.EnableRedis;
import cn.khthink.easyapi.annotation.kit.database.HikariProperties;
import cn.khthink.easyapi.annotation.kit.database.Mybatis;
import cn.khthink.easyapi.annotation.kit.protocol.SessionOpen;
import cn.khthink.easyapi.api.bean.ActionBean;
import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.kit.database.mybatis.EasyMybatis;
import cn.khthink.easyapi.kit.EasyLogger;
import cn.khthink.easyapi.kit.communication.EasyRequestKit;
import cn.khthink.easyapi.kit.protocol.EasySessionKit;
import cn.khthink.easyapi.kit.schedule.EasyScheduleTaskKit;
import cn.khthink.easyapi.protocol.IEasyProtocol;
import cn.khthink.easyapi.redis.EasyRedis;
import cn.khthink.easyapi.tools.ClassScannerTools;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 核心配置类
 *
 * @author kh
 */
public class CoreConfig extends Config {

    /**
     * 开启日志输出
     */
    public static boolean isLog = true;

    /**
     * 是否初始化
     */
    static boolean isInit = false;

    /**
     * 默认编码
     */
    public static String charset = Constant.UTF8;

    /**
     * 当前web项目域名
     */
    public static String webHost;

    /**
     * 是否开启接口访问频率限制
     */
    public static boolean enableRequestLimit = false;

    /**
     * 默认接口访问频率限制
     */
    public static long limit = 1000;

    /**
     * 协议
     */
    private static IEasyProtocol protocol;

    /**
     * 自定义配置类
     */
    private static BaseEasyConfig baseEasyConfig;

    /**
     * 项目目录
     */
    public static String webPath;

    /**
     * 请求后缀
     */
    public static String fix;

    /**
     * 是否开启参数验证
     */
    public static boolean enableVerifyParamter;

    /**
     * 是否开启session验证
     */
    private static boolean enableSessionVerify = false;

    /**
     * 是否开启数据库
     */
    private static boolean enableDatabase;

    /**
     * hikariCP配置文件
     */
    public static String hikariProperties;

    /**
     * 是否开启Mybatis
     */
    private static boolean enableMybatis;

    /**
     * mybatis配置文件
     */
    public static String mybatisConfig;

    /**
     * mapper映射包名
     */
    public static String[] mappers;

    /**
     * Redis服务地址
     */
    public static String redisAds;

    /**
     * Redis密码
     */
    public static String redisPasswd;

    /**
     * Redis端口
     */
    public static int redisPort;

    /**
     * 是否开启接口Redis缓存
     */
    public static boolean enableRedis;

    /**
     * 是否开启RedisSession组件
     */
    public static boolean enableRedisSession;

    /**
     * 上传组件配置
     */
    /**
     * 上传组件保存路径
     */
    public static String uploadPath;

    /**
     * 内存临界值
     */
    public static int memoryThreshold;

    /**
     * 最大文件上传值
     */
    public static int maxFileSize;

    /**
     * 最大请求值
     */
    public static int maxRequestSize;

    /**
     * 是否开启定时任务组件
     */
    private static boolean enableEasyScheduleTask;

    /**
     * 项目中全部类
     */
    public static List<Class> classList = new ArrayList<>();

    private static class Config {
        private static CoreConfig instatnce = new CoreConfig();
    }

    private CoreConfig() {
        super();
    }

    public static CoreConfig getInstance() {
        return Config.instatnce;
    }

    /**
     * 初始化框架
     */
    public void init() {
        if (!isInit) {
            isInit = true;
            EasyLogger.info("--->初始化框架配置");
            initScanConfig();
            if (baseEasyConfig != null) {
                try {
                    EasyLogger.info("--->初始化自定义配置");
                    baseEasyConfig.init();
                } catch (Exception e) {
                    EasyLogger.info("初始化自定义配置失败", e);
                }
            }
            if (enableRequestLimit) {
                EasyRequestKit.getInstance().init();
            }
            EasyLogger.info("--->WebPath:" + webPath);
            if (enableDatabase) {
                DataBaseConfig.getInstance().init();
            }
            if (enableRedis) {
                EasyRedis.getInstance().init();
            }
            if (enableMybatis) {
                try {
                    EasyMybatis.getInstance().init();
                    EasyMybatis.getInstance().getSqlSession(true).getConnection().close();
                } catch (IOException e) {
                    EasyLogger.info("加载Mybatis失败", e);
                } catch (SQLException e) {
                    EasyLogger.info("测试数据库连接失败", e);
                }
            }
            if (enableSessionVerify) {
                EasySessionKit.getInstance().initSessionKit();
            }
            if (enableEasyScheduleTask) {
                EasyScheduleTaskKit.getInstance().init();
            }
            EasyLogger.info("--->初始化处理池");
            EasyActionPool.getInstance().init();
        }
    }


    /**
     * 初始化注解配置
     */
    private void initScanConfig() {
        List<String> allClassName = new ArrayList<>();
        ClassScannerTools.getInstance().getClazzs(null, allClassName);
        for (String s : allClassName) {
            try {
                classList.add(Class.forName(s));
                if (Class.forName(s).getAnnotation(EasyApiConfig.class) != null) {
                    if (checkScanSuperType(s, Constant.BASEEASYCONFIG)) {
                        baseEasyConfig = (BaseEasyConfig) Class.forName(s).newInstance();
                        initScan(baseEasyConfig);
                    }
                }
                if (Class.forName(s).getAnnotation(EasyProtocol.class) != null) {
                    if (checkScanInterfaceType(s, Constant.EASYPROTOCOL)) {
                        protocol = (IEasyProtocol) Class.forName(s).newInstance();
                    }
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                EasyLogger.info("加载配置类错误", e);
            }
        }
    }

    /**
     * 扫描注解配置
     *
     * @param config 配置类
     */
    private void initScan(BaseEasyConfig config) {
        autoConfig(config);
        Annotation[] annotations = config.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof SessionOpen) {
                enableSessionVerify = ((SessionOpen) annotation).value();
            } else if (annotation instanceof Charset) {
                charset = ((Charset) annotation).value();
            } else if (annotation instanceof Limit) {
                enableRequestLimit = ((Limit) annotation).enable();
                limit = ((Limit) annotation).value();
            } else if (annotation instanceof Log) {
                isLog = ((Log) annotation).value();
            } else if (annotation instanceof Fix) {
                fix = ((Fix) annotation).value();
            } else if (annotation instanceof VerifyParamter) {
                enableVerifyParamter = ((VerifyParamter) annotation).value();
            } else if (annotation instanceof DataBase) {
                enableDatabase = ((DataBase) annotation).value();
            } else if (annotation instanceof EnableRedis) {
                enableRedis = ((EnableRedis) annotation).value();
                redisAds = ((EnableRedis) annotation).host();
                redisPasswd = ((EnableRedis) annotation).passwd();
                redisPort = ((EnableRedis) annotation).port();
                if (enableRedis) {
                    enableRedisSession = ((EnableRedis) annotation).isSessionSupport();
                }
            } else if (annotation instanceof WebHost) {
                webHost = ((WebHost) annotation).host();
            } else if (annotation instanceof FileUpload) {
                uploadPath = ((FileUpload) annotation).uploadPath();
                memoryThreshold = ((FileUpload) annotation).memoryThreshold();
                maxFileSize = ((FileUpload) annotation).maxFileSize();
                maxRequestSize = ((FileUpload) annotation).maxRequestSize();
            } else if (annotation instanceof Mybatis) {
                enableMybatis = ((Mybatis) annotation).enable();
                mybatisConfig = ((Mybatis) annotation).config();
                mappers = ((Mybatis) annotation).mappers();
            } else if (annotation instanceof HikariProperties) {
                hikariProperties = "/" + ((HikariProperties) annotation).value() + ".properties";
            } else if (annotation instanceof EnableEasyScheduleTask) {
                enableEasyScheduleTask = ((EnableEasyScheduleTask) annotation).enable();
            }
        }
    }

    /**
     * 自动配置默认
     *
     * @param config 配置类
     */
    private void autoConfig(BaseEasyConfig config) {
        if (config.getClass().getAnnotation(EnableAutoConfig.class) != null) {
            enableRequestLimit = false;
            limit = 1000;
            enableSessionVerify = true;
            charset = Constant.UTF8;
            fix = Constant.DEFFIX;
            enableVerifyParamter = true;
            enableDatabase = true;
            enableRedis = false;
            enableRedisSession = false;
            redisAds = Constant.LOCALHOST;
            redisPasswd = null;
            redisPort = 6379;
            webHost = Constant.LOCALHOST;
            uploadPath = "upload";
            memoryThreshold = 1024 * 1024 * 3;
            maxFileSize = 1024 * 1024 * 40;
            maxRequestSize = 1024 * 1024 * 50;
            enableMybatis = false;
            mybatisConfig = "";
            mappers = new String[]{};
            hikariProperties = "/EasyApi.properties";
            enableEasyScheduleTask = false;
        }
    }

    /**
     * 检查是子类是否正确
     *
     * @param className 子类名
     * @param tagName   父类名
     * @return
     */
    private boolean checkScanSuperType(String className, String tagName) {
        try {
            if (Class.forName(className).getSuperclass() == null) {
                return false;
            } else {
                return Class.forName(className).getSuperclass().getName().equals(tagName);
            }
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 检查实现类是否正确
     *
     * @param className 实现类名
     * @param tagName   接口名
     * @return
     */
    private boolean checkScanInterfaceType(String className, String tagName) {
        try {
            Class<?>[] interfaces = Class.forName(className).getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface.getName().equals(tagName)) {
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            return false;
        }
        return false;
    }

    /**
     * 协议验证
     *
     * @param request 请求
     * @return 是否通过
     */
    public static boolean verify(Request request) {
        if (protocol != null) {
            ActionBean actionBean = EasyActionPool.getInstance().getActionInfo(request.getUriInfo());
            return protocol.verify(actionBean, request);
        } else {
            return true;
        }
    }

    @Override
    public void destory() {
        if (enableSessionVerify) {
            EasySessionKit.getInstance().destory();
        }
        if (enableDatabase) {
            DataBaseConfig.getInstance().destory();
        }
        if (baseEasyConfig != null) {
            baseEasyConfig.destory();
        }
        if (enableRequestLimit) {
            EasyRequestKit.getInstance().destory();
        }
        if (enableEasyScheduleTask) {
            EasyScheduleTaskKit.getInstance().shotdownNow();
        }
        try {
            //注销驱动防止内存泄漏
            EasyLogger.info("--->注销Driver");
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                EasyLogger.info("--->已注销:" + driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
        EasyLogger.info("--->AbandonedConnectionCleanupThread:ShutDown...");
    }

    @Override
    public void reload() {

    }
}
