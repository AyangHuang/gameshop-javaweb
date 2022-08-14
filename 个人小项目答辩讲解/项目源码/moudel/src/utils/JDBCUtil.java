package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    static {
        Properties properties = new Properties();
        try {
//            InputStreamReader inputStreamReader = new FileReader("G:\\coding\\java-project\\shixun\\moudel\\db\\druid.properties");
//            InputStreamReader inputStreamReader = new FileReader("G:\\coding\\java-project\\shixun\\moudel\\db\\druid.properties");
//            properties.load(inputStreamReader);
            InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
//            InputStreamReader in = new FileReader("/opt/tomcat-8.4/apache-tomcat-8.5.59/webapps/web/WEB-INF/classes/db.properties");
            properties.load(in);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return 数据库连接对象，如果为NULL，则说明连接失败
     */
    public static Connection getConnection(Boolean affair) throws SQLException {
        if (affair == false) {
            return dataSource.getConnection();
        } else {
            Connection connection = threadLocal.get();
            if (connection == null) {
                connection = dataSource.getConnection();
                //设置手动管理事务
                threadLocal.set(connection);
                connection.setAutoCommit(false);
            }
            return threadLocal.get();
        }
    }

    /**
     * 并不是真正的关闭连接，而是把使用的Connection放回连接池
     */
    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public static void commitAndClose() throws SQLException {
        Connection connection = threadLocal.get();
        //说明开启过事务
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException sqlException) {
                throw sqlException;
            } finally {
                connection.close();
            }
        }
        //不知道啥意思，还没时间查,好像是线程池，根据意思我觉得应该是放回线程池
        threadLocal.remove();
    }

    public static void rollbackAndClose() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                throw sqlException;
            } finally {
                connection.close();
            }
        }
        threadLocal.remove();
    }

}
