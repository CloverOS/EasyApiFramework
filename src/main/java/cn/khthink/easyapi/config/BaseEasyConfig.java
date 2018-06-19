package cn.khthink.easyapi.config;

/*
    Create by KH at 2017/10/18 14:19 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

/**
 * 配置抽象类
 *
 * @author kh
 */
public abstract class BaseEasyConfig extends Config {

    /**
     * 自定义初始化
     *
     * @throws Exception
     */
    protected abstract void init() throws Exception;
}
