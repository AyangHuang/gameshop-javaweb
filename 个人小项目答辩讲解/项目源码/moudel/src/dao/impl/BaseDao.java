package dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 封装一般的SQL操作，因为都是连接，操作SQL，关闭连接
 * @param <T>
 */
public class BaseDao<T> {
    //利用DbUtils 操作数据库
    private QueryRunner queryRunner = new QueryRunner();


    /**
     * 通用的SQL 通用增删改查语句
     * @param affair 是否开启事务
     * @param sql 语句，可包含 ？？
     * @param parameters ？？参数
     * @return 更新数据库行数
     * 如果插入失败，那么会抛出异常
     */
    public int update(Boolean affair, String sql, Object ...parameters) throws SQLException {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection(affair);
            return queryRunner.update(connection, sql, parameters);
        } catch (SQLException exception){
            throw exception;
        } finally {
            //事务不用关闭连接
            if (affair == false) {
                JDBCUtil.close(null, null, connection);
            }
        }
    }

    /**
     * 查询多行多列的通用方法
     * @param sql 语句，可包含 ？？
     * @param tClass JavaBean.class 对象
     * @param parameters ？？参数
     * @return 返回封装JavaBean 的ArrayList对象
     * @throws SQLException
     */
    public <T> List<T> queryMulti(String sql, Class<T> tClass, Object ... parameters) throws SQLException {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection(false);
            return queryRunner.query(connection, sql, new BeanListHandler<>(tClass), parameters);
        } catch (SQLException exception) {
            throw exception;
        } finally {
                JDBCUtil.close(null, null, connection);
        }
    }


    /**
     * 查询单行多列的通用方法
     *@param sql 语句，可包含 ？？
     * @param tClass JavaBean.class 对象
     * @param parameters ？？参数
     * @return 返回封装JavaBean对象
     * @throws SQLException
     */
    public <T> T querySingle(String sql, Class<T> tClass, Object ...parameters) throws SQLException{
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection(false);
            return queryRunner.query(connection, sql, new BeanHandler<>(tClass), parameters);
        } catch (SQLException exception) {
            throw exception;
        } finally {
                JDBCUtil.close(null, null, connection);
        }
    }

    /**
     * 查询当行单列的通用方法
     * @param sql 语句
     * @param parameters ？？ 参数
     * @return Object，建议向下转型回所查列对象
     * @throws SQLException
     */
    public Object  queryObject(String sql, Object ...parameters) throws SQLException {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection(false);
            return queryRunner.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException exception) {
            throw exception;
        } finally {
            JDBCUtil.close(null, null, connection);
        }
    }

    // 或许可以直接封装
    //没必要，就一个，直接Object，然后直接向下转就可以了，没必要用反射，反而影响性能
    //public <T> T  queryObject(String sql, Object ...parameters) throws SQLException {




}
