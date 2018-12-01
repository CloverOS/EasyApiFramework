package cn.khthink.easyapi.kit.database;

import org.apache.ibatis.session.SqlSession;

/**
 * mybatis组件
 *
 * @author kh
 */
public interface Easybatis {
    /**
     * 获取sqlsession
     *
     * @return SqlSession
     */
    SqlSession getSqlSession();

    /**
     * 获取sqlsession
     *
     * @param isAutoCommit 是否自动提交事务
     * @return SqlSession
     */
    SqlSession getSqlSession(boolean isAutoCommit);

    /**
     * 获取mapper
     *
     * @param type 类型
     * @param <T>  泛型
     * @return T
     */
    <T> T getMapper(Class<T> type);

    /**
     * 提交事务
     */
    void commitSqlSession();

    /**
     * 关闭会话并提交事务
     */
    void closeSqlSession();
}
