package cn.khthink.easyapi.config;

/*
    Create by KH at 2017/10/25 12:55 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

/**
 * 常量配置
 *
 * @author kh
 */
public class Constant {
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String LOCALHOST = "localhost";
    public final static String LIMITERROR = "接口访问频率限制";
    public final static String PROTOCOLERROR = "验证失败";
    public static final String EASYACTION = "cn.khthink.easyapi.action.BaseEasyAction";
    public static final String BASEEASYCONFIG = "cn.khthink.easyapi.config.BaseEasyConfig";
    public static final String EASYPROTOCOL = "cn.khthink.easyapi.protocol.IEasyProtocol";
    public static final String NULLACTION = "nulleasyaction";
    public static final String UTF8 = "UTF-8";
    public static final String JSON = "application/json";
    public static final String XML = "application/xml";
    public static final String FILE = "multipart/form-data";
    public static final String SESSION = "session";
    public static final String DEFFIX = "json";
    /**
     * session组件默认redis块
     */
    public static final int SESSION_REDIS = 5;
    public static final String EASY_SESSION = "EasySession";
}
