package cn.khthink.easyapi.action;

/*
    Create by KH at 2017/10/25 13:21 
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved. 
 */

import cn.khthink.easyapi.api.bean.ActionBean;
import cn.khthink.easyapi.api.bean.ActionParamBean;
import cn.khthink.easyapi.bean.Request;
import cn.khthink.easyapi.config.Constant;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.config.ResultCode;
import cn.khthink.easyapi.kit.communication.EasyResponse;
import cn.khthink.easyapi.kit.database.Easybatis;
import cn.khthink.easyapi.kit.database.mybatis.EasyMybatis;
import cn.khthink.easyapi.kit.protocol.EasySessionKit;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * 处理器
 *
 * @author kh
 */
public abstract class BaseEasyAction implements Action, EasyResponse, Easybatis {
    /**
     * 请求数据
     */
    protected JSONObject datas = new JSONObject();

    /**
     * Session数据
     */
    protected JSONObject sessionDatas;

    /**
     * session密钥
     */
    protected String sessionKey;

    /**
     * Mybatis SqlSession Thread-Safe
     */
    private ThreadLocal<SqlSession> sqlSessionThreadLocal = new ThreadLocal<>();

    /**
     * 验证参数
     *
     * @param request 请求
     * @throws IOException IO异常
     */
    void verifyParameter(Request request) throws Exception {
        if (CoreConfig.enableVerifyParamter) {
            if (isPass(request)) {
                processData(request);
                closeSqlSession();
            }
        }
    }

    /**
     * 验证参数
     *
     * @return boolean
     */
    private boolean isPass(Request request) throws IOException {
        JSONObject data = request.getDatas();
        ActionBean action = EasyActionPool.getInstance().getActionInfo(request.getUriInfo());
        if (action != null) {
            if (action.isSessionOpen()) {
                if (!data.containsKey(Constant.SESSION)) {
                    sendEmptyDataMsg(request, "无效Session", ResultCode.INVALIDSESSION);
                    return false;
                } else {
                    String session = EasySessionKit.getInstance().getSession(data.getString(Constant.SESSION));
                    if (session == null) {
                        sendEmptyDataMsg(request, "过期Session", ResultCode.EXPIRESESSION);
                        return false;
                    } else {
                        sessionKey = data.getString(Constant.SESSION);
                        sessionDatas = JSON.parseObject(session);
                    }
                }
            }
            ActionParamBean[] actionParams = action.getActionParam();
            if (actionParams != null && actionParams.length > 0) {
                for (ActionParamBean ap : actionParams) {
                    if (!"".equals(ap.getDefaultValue()) && data.get(ap.getParam()) == null) {
                        datas.put(ap.getParam(), ap.getDefaultValue());
                    } else {
                        if (data.containsKey(ap.getParam())) {
                            datas.put(ap.getParam(), data.get(ap.getParam()));
                        }
                    }
                    if (!datas.containsKey(ap.getParam())) {
                        if (ap.isNeed()) {
                            sendEmptyDataMsg(request, "缺少参数" + ap.getParam(), ResultCode.PARAMTERLESS);
                            return false;
                        } else {
                            datas.put(ap.getParam(), "");
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取接收数据转换为javabean
     *
     * @param type 类型
     * @param <T>  泛型
     * @return T
     */
    protected <T> T getReciveData(Class<T> type) {
        return getData(datas, type);
    }

    /**
     * 获取session数据Bean
     *
     * @param type 类型
     * @param <T>  泛型
     * @return T
     */
    protected <T> T getSessionData(Class<T> type) {
        return getData(sessionDatas, type);
    }

    /**
     * 数据转换
     *
     * @param datas 数据
     * @param type  类型
     * @return T
     */
    private <T> T getData(JSONObject datas, Class<T> type) {
        return JSON.toJavaObject(datas, type);
    }

    @Override
    public SqlSession getSqlSession() {
        return getSqlSession(true);
    }

    @Override
    public SqlSession getSqlSession(boolean isAutoCommit) {
        sqlSessionThreadLocal.set(EasyMybatis.getInstance().getSqlSession(isAutoCommit));
        return sqlSessionThreadLocal.get();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return getSqlSession().getMapper(type);
    }

    @Override
    public void commitSqlSession() {
        if (sqlSessionThreadLocal != null) {
            sqlSessionThreadLocal.get().commit();
        }
    }

    @Override
    public void closeSqlSession() {
        if (sqlSessionThreadLocal.get() != null) {
            sqlSessionThreadLocal.get().close();
            sqlSessionThreadLocal.remove();
        }
    }
}
