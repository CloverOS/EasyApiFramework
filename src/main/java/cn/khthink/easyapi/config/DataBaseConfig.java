package cn.khthink.easyapi.config;

/*
    Create by KH at 2017/11/21 7:49 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */


import cn.khthink.easyapi.kit.database.hikari.Hikari;
import cn.khthink.easyapi.kit.EasyLogger;

/**
 * 数据库连接池
 *
 * @author kh
 */
public class DataBaseConfig {

    private static class Config {
        private static DataBaseConfig instance = new DataBaseConfig();
    }

    private DataBaseConfig() {
    }

    public static DataBaseConfig getInstance() {
        return Config.instance;
    }

    public void init() {
        Hikari.getInstance().initPool();
    }

    void destory() {
        EasyLogger.info("--->关闭数据库连接池");
        Hikari.getInstance().close();
    }
}
