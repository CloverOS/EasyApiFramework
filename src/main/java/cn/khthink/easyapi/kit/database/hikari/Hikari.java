package cn.khthink.easyapi.kit.database.hikari;

import cn.khthink.easyapi.config.CoreConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * HikariCP工具类
 *
 * @author kh
 */
public class Hikari {
    private HikariDataSource ds;

    private static class hikaricp {
        private static Hikari hikari = new Hikari();
    }

    public static Hikari getInstance() {
        return hikaricp.hikari;
    }

    private Hikari() {
    }

    /**
     * 初始化连接池
     */
    public void initPool() {
        HikariConfig config = new HikariConfig(CoreConfig.hikariProperties);
        ds = new HikariDataSource(config);
    }

    /**
     * 获取数据源
     *
     * @return HikariDataSource
     */
    public HikariDataSource getDataSource() {
        return ds;
    }

    /**
     * 获取连接
     *
     * @return Connection
     * @throws SQLException 数据库异常
     */
    public Connection getConnetion() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 关闭连接池
     */
    public void close() {
        ds.close();
    }
}
