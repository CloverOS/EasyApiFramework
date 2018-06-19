package cn.khthink.easyapi.database.mybatis;

/*
	Create by KH at 2017/12/4 13:23 
 */

import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.database.hikari.Hikari;
import cn.khthink.easyapi.kit.EasyLogger;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * Mybatis
 *
 * @author kh
 */
public class EasyMybatis {

    private SqlSessionFactory sqlSessionFactory;

    private static class Mybatis {
        private static EasyMybatis instance = new EasyMybatis();
    }

    private EasyMybatis() {
    }

    public static EasyMybatis getInstance() {
        return Mybatis.instance;
    }

    public void init() throws IOException {
        if ("".equals(CoreConfig.mybatisConfig)) {
            initDefault();
        } else {
            Reader reader = Resources.getResourceAsReader(CoreConfig.mybatisConfig);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            EasyLogger.info("--->初始化Mybatis配置");
        }
        for (String mapper : CoreConfig.mappers) {
            addMappers(mapper);
        }
    }

    private void initDefault() {
        Configuration configuration = new Configuration();
        JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
        Environment development = new Environment("development", jdbcTransactionFactory, Hikari.getInstance().getDataSource());
        configuration.setEnvironment(development);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        EasyLogger.info("--->初始化默认Mybatis配置");
    }

    /**
     * 添加mapper包
     *
     * @param packageName
     */
    public void addMappers(String packageName) {
        if (sqlSessionFactory != null) {
            sqlSessionFactory.getConfiguration().addMappers(packageName);
        }
    }

    /**
     * 添加单个mapper
     *
     * @param type
     */
    public <T> void addMapper(Class<T> type) {
        if (sqlSessionFactory != null) {
            sqlSessionFactory.getConfiguration().addMapper(type);
        }
    }

    /**
     * 获取SessionFactory
     *
     * @return
     */
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 获取sqlsession
     *
     * @param isAutoComit 是否自动提交
     * @return
     */
    public SqlSession getSqlSession(boolean isAutoComit) {
        return sqlSessionFactory.openSession(isAutoComit);
    }

    /**
     * 获取sqlsession(自动提交)
     *
     * @return
     */
    public SqlSession getSqlSession() {
        return getSqlSession(true);
    }
}
