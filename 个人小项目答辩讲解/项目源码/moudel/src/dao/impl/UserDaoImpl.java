package dao.impl;

import dao.UserDao;
import entity.User;
import utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public String isExistUsername(String username) throws SQLException {
        String sql = "select username, password from t_user where username=?";
        User user = querySingle(sql, User.class, username);
        if (user == null) {
            return "false";
        }
        return user.getPassword();
    }


    @Override
    public String queryPasswordByUsername(String username) throws SQLException {
        String sql = "select password from t_user where username=?";
        return (String) queryObject(sql, username);
    }

    @Override
    public Long queryUserIdByUsername(String username) throws SQLException {
        String  sql = "select user_id from t_user where username=?";
        return (Long) queryObject(sql, username);
    }

    @Override
    public User queryUserByUserId(Long user_id) throws SQLException {
        String sql = "select * from t_user where user_id = ?";
        return (User)querySingle(sql, User.class, user_id);
    }

    @Override
    //没用工具类的事务管理
    public boolean insertUserAndSalt(String username, String passwordMD5, String salt) throws SQLException {
        if (username != null || username.length() == 0 || username.length() > 16 ||
                passwordMD5 != null || username.length() == 0 || username.length() > 32) {
            String sql1 = "insert into t_user values(null,? , ?)";
            String sql2 = "insert into t_salt values(null, ?, ?)";
            //事务进行注册加入t_user 和t_salt
            Connection connection= null;
            PreparedStatement preparedStatement1 = null;
            PreparedStatement preparedStatement2 = null;
            try {
                connection = JDBCUtil.getConnection(false);
                //开启事务
                connection.setAutoCommit(false);
                //插入用户表
                preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setString(1, username);
                preparedStatement1.setString(2, passwordMD5);
                preparedStatement1.executeUpdate();
                //插入t_salt表
                preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setString(1, username);
                preparedStatement2.setString(2, salt);
                preparedStatement2.executeUpdate();
                //事务提交
                connection.commit();
                return  true;
            } catch (SQLException sqlException) {
                //有问题，事务回滚
                connection.rollback();
                throw sqlException;
            } finally {
                //关闭连接
                //第一个statement记得关闭，韩顺平没关闭，应该是错的
                JDBCUtil.close(null, preparedStatement2, null);
                JDBCUtil.close(null, preparedStatement1, connection);
            }
        }
        return false;
    }


}
