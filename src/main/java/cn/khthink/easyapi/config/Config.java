package cn.khthink.easyapi.config;

/*
    Create by KH at 2017/11/21 13:05 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import java.util.ResourceBundle;

/**
 * 配置类
 *
 * @author kh
 */
public abstract class Config {

    /**
     * 配置文件读取
     */
    public static ResourceBundle EasyApi;

    protected Config() {
        EasyApi = ResourceBundle.getBundle("EasyApi");
    }

    /**
     * 销毁
     */
    public abstract void destory();

    /**
     * 重新加载
     */
    public abstract void reload();

    /**
     * 从配置文件获取字符串
     *
     * @param key   键
     * @param value 默认缺省值
     * @return String
     */
    protected static String getStringValue(String key, String value) {
        if (EasyApi == null || !EasyApi.containsKey(key)) {
            return value;
        } else {
            if ("".equals(EasyApi.getString(key))) {
                return value;
            } else {
                return EasyApi.getString(key).trim();
            }
        }
    }

    /**
     * 从配置文件获取布尔变量
     *
     * @param key   键
     * @param value 默认缺省值
     * @return boolean
     */
    protected static boolean getBooleanValue(String key, boolean value) {
        if (EasyApi == null || !EasyApi.containsKey(key)) {
            return value;
        } else {
            if ("".equals(EasyApi.getString(key))) {
                return value;
            } else {
                return Boolean.valueOf(EasyApi.getString(key).trim());
            }
        }
    }

    /**
     * 从配置文件获取int
     *
     * @param key   键
     * @param value 默认缺省值
     * @return int
     */
    protected static int getIntValue(String key, int value) {
        if (EasyApi == null || !EasyApi.containsKey(key)) {
            return value;
        } else {
            if ("".equals(EasyApi.getString(key))) {
                return value;
            } else {
                return Integer.valueOf(EasyApi.getString(key).trim());
            }
        }
    }


    /**
     * 从配置文件获取long
     *
     * @param key   键
     * @param value 默认缺省值
     * @return long
     */
    protected static long getLongValue(String key, long value) {
        if (EasyApi == null || !EasyApi.containsKey(key)) {
            return value;
        } else {
            if ("".equals(EasyApi.getString(key))) {
                return value;
            } else {
                return Long.valueOf(EasyApi.getString(key).trim());
            }
        }
    }
}
