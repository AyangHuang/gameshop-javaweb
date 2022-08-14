package dao;

import entity.User;

import java.sql.SQLException;

public interface UserDao {

    /**
     * 判断有没有该用户
     * @return 有则返回密码字符串， 没有则返回"false"
     */
    public String isExistUsername(String username) throws SQLException;

    /**
     * 该方法被弃用，使用上面的方法
     * 通过账户名查找秘密（加盐加密后的）
     * @return 账户秘密（加盐加密后的）
     */
    public  String queryPasswordByUsername(String username) throws SQLException;


    /**
     * 通过username 查user_id
     * @param username
     * @return user_id
     * @throws SQLException
     */
    public Long queryUserIdByUsername(String username) throws SQLException;

    /**
     * 查找username 通过uer_id
     * @param user_id
     * @return user， 找不到返回null
     * @throws SQLException
     */
    public User queryUserByUserId(Long user_id) throws SQLException;

    /**
     * 注册加入用户
     * @param username 用户名称
     * @param passwordMD5 用户密码加密后
     * @param salt 盐值
     * @return true 插入成功，false插入失败
     * @throws SQLException
     */
    public boolean insertUserAndSalt(String username, String passwordMD5, String salt) throws SQLException;
}
